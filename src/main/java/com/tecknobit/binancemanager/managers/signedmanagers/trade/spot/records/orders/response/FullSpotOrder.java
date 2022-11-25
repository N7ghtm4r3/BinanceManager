package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Fill;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.SpotOrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ResultSpotOrder} class is useful to format a {@code "Binance"}'s spot order status details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * New Order (TRADE)</a>
 * @see BinanceResponse
 * @see Order
 * @see SpotOrder
 * @see ResultSpotOrder
 **/
// TODO: 22/11/2022 CHECK TO REMOVE
public class FullSpotOrder extends ResultSpotOrder implements BinanceResponse {

    /**
     * {@code fills} is instance that memorizes fills list
     **/
    private ArrayList<FillSpot> fills;

    /** Constructor to init {@link FullSpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * @param transactTime: transaction time
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * @param fills: fills list 
     * **/
    public FullSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime,
                         double price, double origQty, double executedQty, double cummulativeQuoteQty, Status status,
                         TimeInForce timeInForce, OrderType type, Side side, ArrayList<FillSpot> fills) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.fills = fills;
    }

    /**
     * Constructor to init {@link FullSpotOrder} object
     *
     * @param fullSpotOrder: full spot details as {@link JSONObject}
     **/
    public FullSpotOrder(JSONObject fullSpotOrder) {
        super(fullSpotOrder);
        fills = new ArrayList<>();
        JSONArray jFills = hOrder.getJSONArray("fills", new JSONArray());
        for (int j = 0; j < jFills.length(); j++)
            fills.add(new FillSpot(jFills.getJSONObject(j)));
    }

    /**
     * Method to get {@link #fills} instance <br>
     * Any params required
     *
     * @return {@link #fills} instance as {@link ArrayList} of {@link FillSpot}
     **/
    public ArrayList<FillSpot> getFills() {
        return fills;
    }

    /**
     * Method to set {@link #fills} instance <br>
     *
     * @param fills: list of {@link SpotOrderDetails} to set
     **/
    public void setFills(ArrayList<FillSpot> fills) {
        this.fills = fills;
    }

    /**
     * Method to add a fill spot  to {@link #fills}
     *
     * @param fillSpot: fill spot to add
     **/
    public void insertFill(FillSpot fillSpot) {
        if (!fills.contains(fillSpot))
            fills.add(fillSpot);
    }

    /**
     * Method to remove a fill spot  from {@link #fills}
     *
     * @param fillSpot: fill spot  to remove
     * @return result of operation as boolean
     **/
    public boolean removeFill(FillSpot fillSpot) {
        return fills.remove(fillSpot);
    }

    /**
     * Method to get a fill spot from {@link #fills} list
     *
     * @param index: index to fetch the composed fill spot
     * @return fill spot as {@link SpotOrderDetails}
     **/
    public FillSpot getFill(int index) {
        return fills.get(index);
    }

    /**
     * Method to get error code <br>
     * Any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
     **/
    @Override
    public int getCode() {
        if (hOrder != null)
            return hOrder.getInt("code", -1);
        return -1;
    }

    /**
     * Method to get error message <br>
     * Any params required
     *
     * @return message of error as {@link String}
     * *
     * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
     **/
    @Override
    public String getMessage() {
        if (hOrder != null)
            return hOrder.getString("msg", null);
        return null;
    }

    /**
     * The {@code FillSpot} class is useful to obtain and format FillSpot object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *      https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * **/
    public static class FillSpot extends Fill {

        /**
         * {@code tradeId} is instance that memorizes trade identifier
         * **/
        private final long tradeId;

        /** Constructor to init {@link Fill} object
         * @param price: price of a fill
         * @param qty: quantity of a fill
         * @param commission: commission of a fill
         * @param commissionAsset: commission asset of a fill
         * @param tradeId: trade identifier
         * **/
        public FillSpot(double price, double qty, double commission, String commissionAsset, long tradeId) {
            super(price, qty, commission, commissionAsset);
            this.tradeId = tradeId;
        }

        /**
         * Constructor to init {@link Fill} object
         *
         * @param jFillSpot: fill spot details as {@link JSONObject}
         **/
        public FillSpot(JSONObject jFillSpot) {
            super(jFillSpot);
            this.tradeId = jFillSpot.getLong("tradeId");
        }

        /**
         * Method to get {@link #tradeId} instance <br>
         * Any params required
         *
         * @return {@link #tradeId} instance as long
         **/
        public long getTradeId() {
            return tradeId;
        }

    }

}

