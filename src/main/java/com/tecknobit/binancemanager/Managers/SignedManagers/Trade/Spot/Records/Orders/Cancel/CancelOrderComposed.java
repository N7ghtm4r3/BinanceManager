package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CancelOrderComposed {

    private final long orderListId;
    private final String contingencyType;
    private final String listStatusType;
    private final String listOrderStatus;
    private final String listClientOrderId;
    private final long transactionTime;
    private final String symbol;
    private ArrayList<SpotOrderDetails> orderDetails;
    private ArrayList<CancelOrder> cancelOrders;

    public CancelOrderComposed(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                               String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject) {
        this.orderListId = orderListId;
        this.contingencyType = contingencyType;
        this.listStatusType = listStatusType;
        this.listOrderStatus = listOrderStatus;
        this.listClientOrderId = listClientOrderId;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        loadOrderDetails(jsonObject.getJSONArray("orders"));
        loadCancelOrders(jsonObject.getJSONArray("orderReports"));
    }

    private void loadOrderDetails(JSONArray list){
        orderDetails = new ArrayList<>();
        for (int j=0; j < list.length(); j++){
            JSONObject item = list.getJSONObject(j);
            orderDetails.add(new SpotOrderDetails(item.getString("symbol"),
                    item.getLong("orderId"),
                    item.getString("clientOrderId")
            ));
        }
    }

    private void loadCancelOrders(JSONArray list){
        cancelOrders = new ArrayList<>();
        for (int j=0; j < list.length(); j++)
            cancelOrders.add(CancelOrder.assembleCancelOrderObject(list.getJSONObject(j)));
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

    public ArrayList<SpotOrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public SpotOrderDetails getOrderDetail(int index){
        return orderDetails.get(index);
    }

    public ArrayList<CancelOrder> getCancelOrders() {
        return cancelOrders;
    }

    public CancelOrder getCancelOrder(int index){
        return cancelOrders.get(index);
    }

    public static class SpotOrderDetails {

        private final String symbol;
        private final long orderId;
        private final String clientOrderId;

        public SpotOrderDetails(String symbol, long orderId, String clientOrderId) {
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
