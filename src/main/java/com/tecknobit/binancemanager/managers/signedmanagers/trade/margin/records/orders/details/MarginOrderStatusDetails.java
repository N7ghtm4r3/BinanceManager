package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.OrderDetails;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code MarginOrderStatusDetails} class is useful to format all Binance Margin Order Status request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
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
    public MarginOrderStatusDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol,
                                    ArrayList<OrderValues> orderValues, boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                orderValues);
        this.isIsolated = isIsolated;
    }

    /**
     * Constructor to init {@link MarginOrderStatusDetails} object
     *
     * @param marginOrderStatus: margin order status details as {@link JSONObject}
     **/
    public MarginOrderStatusDetails(JSONObject marginOrderStatus) {
        super(marginOrderStatus);
        isIsolated = marginOrderStatus.getBoolean("isIsolated");
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    @Override
    public String toString() {
        return "MarginOrderStatusDetails{" +
                "isIsolated=" + isIsolated +
                ", orderListId=" + orderListId +
                ", contingencyType='" + contingencyType + '\'' +
                ", listStatusType='" + listStatusType + '\'' +
                ", listOrderStatus='" + listOrderStatus + '\'' +
                ", listClientOrderId='" + listClientOrderId + '\'' +
                ", transactionTime=" + transactionTime +
                ", symbol='" + symbol + '\'' +
                ", orderValues=" + orderValues +
                '}';
    }

}
