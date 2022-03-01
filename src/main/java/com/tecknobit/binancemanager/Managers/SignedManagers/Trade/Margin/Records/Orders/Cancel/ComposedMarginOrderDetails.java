package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComposedMarginOrderDetails extends OrderDetails {

    private ArrayList<CancelMarginOrder> cancelMarginOrders;
    private final boolean isIsolated;

    public ComposedMarginOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject,
                                      boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,jsonObject);
        this.isIsolated = isIsolated;
        loadCancelOrders(jsonObject.getJSONArray("orderReports"));
    }

    private void loadCancelOrders(JSONArray orderReports) {
        cancelMarginOrders = new ArrayList<>();
        for (int j=0; j < orderReports.length(); j++)
            cancelMarginOrders.add(CancelMarginOrder.assembleCancelMarginOrderObject(orderReports.getJSONObject(j)));
    }

    public ArrayList<CancelMarginOrder> getCancelMarginOrdersList() {
        return cancelMarginOrders;
    }

    public CancelMarginOrder getCancelMarginOrder(int index){
        return cancelMarginOrders.get(index);
    }

    public boolean isIsolated() {
        return isIsolated;
    }

}
