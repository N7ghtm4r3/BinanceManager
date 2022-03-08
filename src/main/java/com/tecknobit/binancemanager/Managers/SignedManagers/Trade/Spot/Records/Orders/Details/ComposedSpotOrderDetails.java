package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ComposedSpotOrderDetails} class is useful to format a ComposedSpotOrderDetails object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedSpotOrderDetails extends OrderDetails {

    private ArrayList<DetailSpotOrder> orderReports;

    public ComposedSpotOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        loadOrderReports(jsonObject.getJSONArray("orderReports"));
    }

    /** Method to load DetailSpotOrder list
     * @param #list: obtained from Binance's request
     * @return an ArrayList<DetailSpotOrder> with response data
     * **/
    private void loadOrderReports(JSONArray list){
        orderReports = new ArrayList<>();
        for (int j=0; j < list.length(); j++)
            orderReports.add(DetailSpotOrder.assembleDetailSpotOrderObject(list.getJSONObject(j)));
    }

    public ArrayList<DetailSpotOrder> getOrderReports() {
        return orderReports;
    }

    public DetailSpotOrder getOrderReport(int index){
        return orderReports.get(index);
    }

}
