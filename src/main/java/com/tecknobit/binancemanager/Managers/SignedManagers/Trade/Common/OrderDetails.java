package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code OrderDetails} class is useful to manage and format all Binance OrderDetails request
 * @implNote used by BinanceSpotManager, BinanceMarginManager
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 *     https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 * **/

public class OrderDetails {

    /**
     * {@code orderListId} is instance that memorizes list order identifier
     * **/
    protected final long orderListId;

    /**
     * {@code contingencyType} is instance that memorizes contingency type of the order
     * **/
    protected final String contingencyType;

    /**
     * {@code listStatusType} is instance that memorizes list status type of the order
     * **/
    protected final String listStatusType;

    /**
     * {@code listOrderStatus} is instance that memorizes list order status
     * **/
    protected final String listOrderStatus;

    /**
     * {@code listClientOrderId} is instance that list client order id
     * **/
    protected final String listClientOrderId;

    /**
     * {@code transactionTime} is instance that memorizes transaction time of the order
     * **/
    protected final long transactionTime;

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     * **/
    protected final String symbol;

    /**
     * {@code orderValues} is instance that memorizes order values
     * **/
    protected ArrayList<OrderValues> orderValues;

    /** Constructor to init {@link OrderDetails} object
     * @param orderListId: list order identifier
     * @param contingencyType: contingency type of the order
     * @param listStatusType: list status type of the order
     * @param listOrderStatus: list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime: transaction time of the order
     * @param symbol: symbol used in the order
     * @param jsonOrder: order details in JSON format
     * **/
    public OrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                        String listClientOrderId, long transactionTime, String symbol, JSONObject jsonOrder) {
        this.orderListId = orderListId;
        this.contingencyType = contingencyType;
        this.listStatusType = listStatusType;
        this.listOrderStatus = listOrderStatus;
        this.listClientOrderId = listClientOrderId;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        loadOrderDetails(jsonOrder.getJSONArray("orders"));
    }

    /** Method to load OrderValues list
     * @param list: obtained from Binance's request
     * load list with response data as ArrayList<OrderValues>
     * **/
    private void loadOrderDetails(JSONArray list){
        orderValues = new ArrayList<>();
        for (int j=0; j < list.length(); j++){
            JSONObject item = list.getJSONObject(j);
            orderValues.add(new OrderValues(item.getString("symbol"),
                    item.getLong("orderId"),
                    item.getString("clientOrderId")
            ));
        }
    }

    public long getOrderListId() {
        return orderListId;
    }

    public String getContingencyType() {
        return contingencyType;
    }

    public String getListStatusType() {
        return listStatusType;
    }

    public String getListOrderStatus() {
        return listOrderStatus;
    }

    public String getListClientOrderId() {
        return listClientOrderId;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public ArrayList<OrderValues> getOrderValuesList() {
        return orderValues;
    }

    public OrderValues getOrderValue(int index){
        return orderValues.get(index);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderListId=" + orderListId +
                ", contingencyType='" + contingencyType + '\'' +
                ", listStatusType='" + listStatusType + '\'' +
                ", listOrderStatus='" + listOrderStatus + '\'' +
                ", listClientOrderId='" + listClientOrderId + '\'' +
                ", transactionTime=" + transactionTime +
                ", symbol='" + symbol + '\'' +
                ", orderValues=" + orderValues +
                '}';
    }

    /**
     * The {@code OrderValues} class is useful to obtain and format OrderValues object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * **/

    public static class OrderValues {

        /**
         * {@code symbol} is instance that memorizes symbol used in the order
         * **/
        private final String symbol;

        /**
         * {@code orderId} is instance that memorizes order identifier
         * **/
        private final long orderId;

        /**
         * {@code orderId} is instance that memorizes client order identifier
         * **/
        private final String clientOrderId;

        /** Constructor to init {@link OrderValues} object
         * @param symbol: symbol used in the order
         * @param orderId: order identifier
         * @param clientOrderId: client order identifier
         * **/
        public OrderValues(String symbol, long orderId, String clientOrderId) {
            this.symbol = symbol;
            this.orderId = orderId;
            this.clientOrderId = clientOrderId;
        }

        public String getSymbol() {
            return symbol;
        }

        public long getOrderId() {
            return orderId;
        }

        public String getClientOrderId() {
            return clientOrderId;
        }

        @Override
        public String toString() {
            return "OrderValues{" +
                    "symbol='" + symbol + '\'' +
                    ", orderId=" + orderId +
                    ", clientOrderId='" + clientOrderId + '\'' +
                    '}';
        }

    }

}
