package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code ComposedMarginOrderDetails} class is useful to format Binance Margin Account Cancel all Open Orders on a Symbol request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedMarginOrderDetails extends OrderDetails {

    private ArrayList<DetailMarginOrder> detailMarginOrdersList;
    private final boolean isIsolated;

    public ComposedMarginOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol, boolean isIsolated,
                                      JSONObject jsonObject) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        this.isIsolated = isIsolated;
        loadOrderReport(jsonObject.getJSONArray("orderReports"));
    }

    private void loadOrderReport(JSONArray orderReports) {
        detailMarginOrdersList = new ArrayList<>();
        for (int j=0; j < orderReports.length(); j++)
            detailMarginOrdersList.add(DetailMarginOrder.assembleDetailMarginOrderObject(orderReports.getJSONObject(j)));
    }

    public ArrayList<DetailMarginOrder> getCancelMarginOrdersList() {
        return detailMarginOrdersList;
    }

    public void setDetailMarginOrdersList(ArrayList<DetailMarginOrder> detailMarginOrdersList) {
        this.detailMarginOrdersList = detailMarginOrdersList;
    }

    public void insertDetailMarginOrder(DetailMarginOrder detailMarginOrder){
        if(!detailMarginOrdersList.contains(detailMarginOrder))
            detailMarginOrdersList.add(detailMarginOrder);
    }

    public boolean removeDetailMarginOrder(DetailMarginOrder detailMarginOrder){
        return detailMarginOrdersList.remove(detailMarginOrder);
    }

    public DetailMarginOrder getCancelMarginOrder(int index){
        try{
            return detailMarginOrdersList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
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
