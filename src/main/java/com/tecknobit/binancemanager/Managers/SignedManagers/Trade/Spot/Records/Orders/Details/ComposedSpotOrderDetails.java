package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ComposedSpotOrderDetails} class is useful to format a ComposedSpotOrderDetails object
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedSpotOrderDetails extends OrderDetails {

    /**
     * {@code orderReportsList} is instance that memorizes order reports list
     * **/
    private ArrayList<DetailSpotOrder> orderReportsList;

    /** Constructor to init {@link ComposedSpotOrderDetails} object
     * @param orderListId: list order identifier
     * @param contingencyType: contingency type of the order
     * @param listStatusType: list status type of the order
     * @param listOrderStatus: list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime: transaction time of the order
     * @param symbol: symbol used in the order
     * @param jsonSpotOrder: order details in JSON format
     * **/
    public ComposedSpotOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, JSONObject jsonSpotOrder) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonSpotOrder);
        loadOrderReports(jsonSpotOrder.getJSONArray("orderReportsList"));
    }

    /** Method to load DetailSpotOrder list
     * @param list: obtained from Binance's request
     * any return
     * **/
    private void loadOrderReports(JSONArray list){
        orderReportsList = new ArrayList<>();
        for (int j=0; j < list.length(); j++)
            orderReportsList.add(DetailSpotOrder.assembleDetailSpotOrderObject(list.getJSONObject(j)));
    }

    public ArrayList<DetailSpotOrder> getOrderReportsList() {
        return orderReportsList;
    }

    public void setOrderReportsList(ArrayList<DetailSpotOrder> orderReportsList) {
        this.orderReportsList = orderReportsList;
    }

    public void insertOrderReport(DetailSpotOrder orderReport){
        if(!orderReportsList.contains(orderReport))
            orderReportsList.add(orderReport);
    }

    public boolean removeOrderReport(DetailSpotOrder orderReport){
        return orderReportsList.remove(orderReport);
    }

    public DetailSpotOrder getOrderReport(int index){
        return orderReportsList.get(index);
    }

    @Override
    public String toString() {
        return "ComposedSpotOrderDetails{" +
                "orderReportsList=" + orderReportsList +
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
