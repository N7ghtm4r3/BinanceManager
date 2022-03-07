package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

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

    private ArrayList<DetailMarginOrder> detailMarginOrders;
    private final boolean isIsolated;

    public ComposedMarginOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol, boolean isIsolated,
                                      JSONObject jsonObject) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        this.isIsolated = isIsolated;
        loadOrderReport(jsonObject.getJSONArray("orderReports"));
    }

    private void loadOrderReport(JSONArray orderReports) {
        detailMarginOrders = new ArrayList<>();
        for (int j=0; j < orderReports.length(); j++)
            detailMarginOrders.add(DetailMarginOrder.assembleDetailMarginOrderObject(orderReports.getJSONObject(j)));
    }

    public ArrayList<DetailMarginOrder> getCancelMarginOrdersList() {
        return detailMarginOrders;
    }

    public DetailMarginOrder getCancelMarginOrder(int index){
        return detailMarginOrders.get(index);
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public static ComposedMarginOrderDetails assembleComposedMarginOrderDetails(JSONObject order){
        return new ComposedMarginOrderDetails(order.getLong("orderListId"),
                order.getString("contingencyType"),
                order.getString("listStatusType"),
                order.getString("listOrderStatus"),
                order.getString("listClientOrderId"),
                order.getLong("transactionTime"),
                order.getString("symbol"),
                order.getBoolean("isIsolated"),
                order
        );
    }

}
