package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ComposedSpotOrderDetails} class is useful to format a ComposedSpotOrderDetails object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedSpotOrderDetails extends OrderDetails {

    private ArrayList<DetailSpotOrder> orderReportsList;

    public ComposedSpotOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        loadOrderReports(jsonObject.getJSONArray("orderReportsList"));
    }

    /** Method to load DetailSpotOrder list
     * @param #list: obtained from Binance's request
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
        try{
            return orderReportsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

}
