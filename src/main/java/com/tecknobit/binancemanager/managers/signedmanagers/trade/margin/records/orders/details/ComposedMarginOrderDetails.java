package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * The {@code ComposedMarginOrderDetails} class is useful to format a {@code "Binance"}'s composed margin order details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
 * Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
 * @see OrderDetails
 **/
public class ComposedMarginOrderDetails extends OrderDetails {

    /**
     * {@code canceledMarginOrders} is instance that memorizes margin order details s list
     **/
    private final ArrayList<MarginOrderDetails> canceledMarginOrders;

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /**
     * Constructor to init {@link ComposedMarginOrderDetails} object
     *
     * @param orderListId:          list order identifier
     * @param contingencyType:      contingency type of the order
     * @param listStatusType:       list status type of the order
     * @param listOrderStatus:      list order status
     * @param listClientOrderId:    list client order id
     * @param transactionTime:      transaction time of the order
     * @param symbol:               symbol used in the order
     * @param canceledMarginOrders: list of {@link MarginOrderDetails}
     * @param isIsolated:           is isolated
     **/
    public ComposedMarginOrderDetails(long orderListId, String contingencyType, Status listStatusType, Status listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol, ArrayList<Order> orders,
                                      ArrayList<MarginOrderDetails> canceledMarginOrders, boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                orders);
        this.canceledMarginOrders = canceledMarginOrders;
        this.isIsolated = isIsolated;
    }

    /**
     * Constructor to init {@link ComposedMarginOrderDetails} object
     *
     * @param marginOrderDetails: margin order details as {@link JSONObject}
     **/
    public ComposedMarginOrderDetails(JSONObject marginOrderDetails) {
        super(marginOrderDetails);
        canceledMarginOrders = new ArrayList<>();
        JSONArray jOrdersReports = hOrder.getJSONArray("orderReports", new JSONArray());
        for (int j = 0; j < jOrdersReports.length(); j++)
            canceledMarginOrders.add(new MarginOrderDetails(jOrdersReports.getJSONObject(j)));
        isIsolated = marginOrderDetails.getBoolean("isIsolated");
    }

    /**
     * Method to get {@link #canceledMarginOrders} instance <br>
     * Any params required
     *
     * @return {@link #canceledMarginOrders} instance as {@link ArrayList} of {@link MarginOrderDetails}
     **/
    public ArrayList<MarginOrderDetails> getCanceledMarginOrders() {
        return canceledMarginOrders;
    }

    /**
     * Method to add a margin order details  to {@link #canceledMarginOrders}
     *
     * @param marginOrderDetails: margin order details to add
     **/
    public void insertDetailMarginOrder(MarginOrderDetails marginOrderDetails) {
        if (!canceledMarginOrders.contains(marginOrderDetails))
            canceledMarginOrders.add(marginOrderDetails);
    }

    /**
     * Method to remove a margin order details  from {@link #canceledMarginOrders}
     *
     * @param marginOrderDetails: margin order details  to remove
     * @return result of operation as boolean
     **/
    public boolean removeDetailMarginOrder(MarginOrderDetails marginOrderDetails) {
        return canceledMarginOrders.remove(marginOrderDetails);
    }

    /**
     * Method to get a margin order details  from {@link #canceledMarginOrders} list
     *
     * @param index: index to fetch the margin order details
     * @return margin order details  as {@link MarginOrderDetails}
     **/
    public MarginOrderDetails getCancelMarginOrder(int index) {
        return canceledMarginOrders.get(index);
    }

    /**
     * Method to get {@link #isIsolated} instance <br>
     * Any params required
     *
     * @return {@link #isIsolated} instance as boolean
     **/
    public boolean isIsolated() {
        return isIsolated;
    }

}
