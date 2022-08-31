package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

import com.tecknobit.binancemanager.Managers.Market.Records.Tickers.OrderBookTicker;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.BinanceMarginManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.BinanceSpotManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Formatters.JsonHelper.getJSONArray;

/**
 * The {@code OrderDetails} class is useful to manage and format all Binance's requests with order detailed
 *
 * @implNote used by {@link BinanceSpotManager} and {@link BinanceMarginManager}
 **/

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
     **/
    protected final String symbol;

    /**
     * {@code orderValues} is instance that memorizes order values
     **/
    protected ArrayList<OrderValues> orderValues;

    /**
     * Constructor to init {@link OrderDetails} object
     *
     * @param orderListId:       list order identifier
     * @param contingencyType:   contingency type of the order
     * @param listStatusType:    list status type of the order
     * @param listOrderStatus:   list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime:   transaction time of the order
     * @param symbol:            symbol used in the order
     * @param orderValues:       list of {@link OrderValues}
     **/
    public OrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                        String listClientOrderId, long transactionTime, String symbol, ArrayList<OrderValues> orderValues) {
        this.orderListId = orderListId;
        this.contingencyType = contingencyType;
        this.listStatusType = listStatusType;
        this.listOrderStatus = listOrderStatus;
        this.listClientOrderId = listClientOrderId;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        this.orderValues = orderValues;
    }

    /**
     * Constructor to init {@link OrderDetails} object
     *
     * @param orderDetails: order details as {@link JSONObject}
     **/
    public OrderDetails(JSONObject orderDetails) {
        orderListId = orderDetails.getLong("orderListId");
        contingencyType = orderDetails.getString("contingencyType");
        listStatusType = orderDetails.getString("listStatusType");
        listOrderStatus = orderDetails.getString("listOrderStatus");
        listClientOrderId = orderDetails.getString("listClientOrderId");
        transactionTime = orderDetails.getLong("transactionTime");
        symbol = orderDetails.getString("symbol");
        loadOrderDetails(getJSONArray(orderDetails, "orders", new JSONArray()));
    }

    /**
     * Method to load OrderValues list
     *
     * @param list: obtained from Binance's request
     *              load list with response data as ArrayList<OrderValues>
     **/
    private void loadOrderDetails(JSONArray list) {
        orderValues = new ArrayList<>();
        for (int j = 0; j < list.length(); j++)
            orderValues.add(new OrderValues(list.getJSONObject(j)));
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
     * The {@code OrderValues} class is useful to adding values to an order
     *
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     **/

    public static class OrderValues {

        /**
         * {@code symbol} is instance that memorizes symbol used in the order
         * **/
        private final String symbol;

        /**
         * {@code orderId} is instance that memorizes order identifier
         **/
        private final long orderId;

        /**
         * {@code orderId} is instance that memorizes client order identifier
         **/
        private final String clientOrderId;

        /**
         * Constructor to init {@link OrderValues} object
         *
         * @param symbol:        symbol used in the order
         * @param orderId:       order identifier
         * @param clientOrderId: client order identifier
         **/
        public OrderValues(String symbol, long orderId, String clientOrderId) {
            this.symbol = symbol;
            this.orderId = orderId;
            this.clientOrderId = clientOrderId;
        }

        /**
         * Constructor to init {@link OrderBookTicker} object
         *
         * @param orderValues: fill details as {@link JSONObject}
         **/
        public OrderValues(JSONObject orderValues) {
            symbol = orderValues.getString("symbol");
            orderId = orderValues.getLong("orderId");
            clientOrderId = orderValues.getString("clientOrderId");
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
