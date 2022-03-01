package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetails {

    private final long orderListId;
    private final String contingencyType;
    private final String listStatusType;
    private final String listOrderStatus;
    private final String listClientOrderId;
    private final long transactionTime;
    private final String symbol;
    private ArrayList<OrderValues> orderValues;

    public OrderDetails(long orderListId, String contingencyType, String listStatusType,String listOrderStatus,
                        String listClientOrderId, long transactionTime, String symbol,JSONObject jsonObject) {
        this.orderListId = orderListId;
        this.contingencyType = contingencyType;
        this.listStatusType = listStatusType;
        this.listOrderStatus = listOrderStatus;
        this.listClientOrderId = listClientOrderId;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        loadOrderDetails(jsonObject.getJSONArray("orders"));
    }

    /** Method to load OrderValues list
     * @param #list: obtained from Binance's request
     * return an ArrayList<OrderValues> with response data
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

    /**
     * The {@code OrderValues} class is useful to obtain and format OrderValues object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * **/

    public static class OrderValues {

        private final String symbol;
        private final long orderId;
        private final String clientOrderId;

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

    }

}
