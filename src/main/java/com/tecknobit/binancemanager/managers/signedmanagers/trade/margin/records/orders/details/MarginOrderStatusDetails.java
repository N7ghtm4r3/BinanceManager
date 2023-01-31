package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.OrderDetails;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code MarginOrderStatusDetails} class is useful to format a {@code "Binance"}'s margin order status
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
 * Margin Account Cancel Order (TRADE)</a>
 * @see OrderDetails
 **/
public class MarginOrderStatusDetails extends OrderDetails {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /**
     * Constructor to init {@link MarginOrderStatusDetails} object
     *
     * @param orderListId:        list order identifier
     * @param contingencyType:    contingency type of the order
     * @param listStatusType:list status type of the order
     * @param listOrderStatus:    list order status
     * @param listClientOrderId:  list client order id
     * @param transactionTime:    transaction time of the order
     * @param symbol:             symbol used in the order
     * @param isIsolated:         is isolated
     **/
    public MarginOrderStatusDetails(long orderListId, String contingencyType, Status listStatusType, Status listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol,
                                    ArrayList<Order> orders, boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                orders);
        this.isIsolated = isIsolated;
    }

    /**
     * Constructor to init {@link MarginOrderStatusDetails} object
     *
     * @param marginOrderStatus: margin order status details as {@link JSONObject}
     **/
    public MarginOrderStatusDetails(JSONObject marginOrderStatus) {
        super(marginOrderStatus);
        isIsolated = hOrder.getBoolean("isIsolated");
    }

    /**
     * Method to get {@link #isIsolated} instance <br>
     * No-any params required
     *
     * @return {@link #isIsolated} instance as boolean
     **/
    public boolean isIsolated() {
        return isIsolated;
    }

}
