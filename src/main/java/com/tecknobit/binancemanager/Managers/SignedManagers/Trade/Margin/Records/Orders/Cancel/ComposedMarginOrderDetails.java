package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code ComposedMarginOrderDetails} class is useful to format Binance Margin Account Cancel all Open Orders on a Symbol request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedMarginOrderDetails extends OrderDetails {

    private ArrayList<CancelMarginOrder> cancelMarginOrders;
    private final boolean isIsolated;

    public ComposedMarginOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol,boolean isIsolated,
                                      JSONObject jsonObject) {
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
