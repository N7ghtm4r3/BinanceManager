package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Fill;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details.ComposedMarginOrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code FullMarginOrder} class is useful to format a {@code "Binance"}'s {@code "FULL"} response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * Margin Account New Order (TRADE)</a>
 * @see Order
 * @see MarginOrder
 * @see ACKMarginOrder
 * @see ResultMarginOrder
 **/
// TODO: 21/11/2022 CHECK TO REMOVE 
public class FullMarginOrder extends ResultMarginOrder {

    /**
     * {@code fillMargins} is instance that memorizes fill margin data list
     **/
    private ArrayList<Fill> fillMargins;

    /** Constructor to init {@link FullMarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime: transaction time
     * @param isIsolated: is isolated
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * @param fillMargins: list of {@link Fill}
     * **/
    public FullMarginOrder(String symbol, long orderId, String clientOrderId, long transactTime, boolean isIsolated,
                           double price, double origQty, double executedQty, double cummulativeQuoteQty, Status status,
                           TimeInForce timeInForce, OrderType type, Side side, ArrayList<Fill> fillMargins) {
        super(symbol, orderId, clientOrderId, transactTime, isIsolated, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.fillMargins = fillMargins;
    }

    /**
     * Constructor to init {@link FullMarginOrder} object
     *
     * @param fullMarginOrder: full margin order details as {@link JSONObject}
     **/
    public FullMarginOrder(JSONObject fullMarginOrder) {
        super(fullMarginOrder);
        fillMargins = new ArrayList<>();
        JSONArray jFills = hOrder.getJSONArray("fills", new JSONArray());
        for (int j = 0; j < jFills.length(); j++)
            fillMargins.add(new Fill(jFills.getJSONObject(j)));
    }

    /**
     * Method to get {@link #fillMargins} instance <br>
     * Any params required
     *
     * @return {@link #fillMargins} instance as {@link ArrayList} of {@link Fill}
     **/
    public ArrayList<Fill> getFillMargins() {
        return fillMargins;
    }

    /**
     * Method to set {@link #fillMargins} instance <br>
     *
     * @param fillMargins: list of {@link ComposedMarginOrderDetails} to set
     **/
    public void setFillMargins(ArrayList<Fill> fillMargins) {
        this.fillMargins = fillMargins;
    }

    /**
     * Method to add a fill to {@link #fillMargins}
     *
     * @param fill: fill to add
     **/
    public void insertFill(Fill fill) {
        if (!fillMargins.contains(fill))
            fillMargins.add(fill);
    }

    /**
     * Method to remove a fill from {@link #fillMargins}
     *
     * @param fill: fill to remove
     * @return result of operation as boolean
     **/
    public boolean removeFill(Fill fill) {
        return fillMargins.remove(fill);
    }

    /**
     * Method to get a fill from {@link #fillMargins} list
     *
     * @param index: index to fetch the fill
     * @return fill as {@link ComposedMarginOrderDetails}
     **/
    public Fill getFillMargin(int index) {
        return fillMargins.get(index);
    }

}
