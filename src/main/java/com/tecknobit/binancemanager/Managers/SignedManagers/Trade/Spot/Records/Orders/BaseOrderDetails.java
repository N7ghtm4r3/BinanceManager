package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code BaseOrderDetails} class is useful to format BaseOrderDetails object
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BaseOrderDetails {

    private final long orderListId;
    private final String contingencyType;
    private final String listStatusType;
    private final String listOrderStatus;
    private final String listClientOrderId;
    private final long transactionTime;
    private final String symbol;
    private ArrayList<ComposedOrderDetails.SpotOrderDetails> spotOrderDetails;

    public BaseOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                            String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject) {
        this.orderListId = orderListId;
        this.contingencyType = contingencyType;
        this.listStatusType = listStatusType;
        this.listOrderStatus = listOrderStatus;
        this.listClientOrderId = listClientOrderId;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        loadOrderDetails(jsonObject.getJSONArray("orders"));
    }

    /** Method to load SpotOrderDetails list
     * @param #list: obtained from Binance's request
     * return an ArrayList<SpotOrderDetails> with response data
     * **/
    private void loadOrderDetails(JSONArray list){
        spotOrderDetails = new ArrayList<>();
        for (int j=0; j < list.length(); j++){
            JSONObject item = list.getJSONObject(j);
            spotOrderDetails.add(new ComposedOrderDetails.SpotOrderDetails(item.getString("symbol"),
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

    public ArrayList<ComposedOrderDetails.SpotOrderDetails> getSpotOrderDetails() {
        return spotOrderDetails;
    }

    public ComposedOrderDetails.SpotOrderDetails getSpotOrderDetail(int index){
        return spotOrderDetails.get(index);
    }

    /**
     * The {@code SpotOrderDetails} class is useful to obtain and format SpotOrderDetails object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * **/

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
