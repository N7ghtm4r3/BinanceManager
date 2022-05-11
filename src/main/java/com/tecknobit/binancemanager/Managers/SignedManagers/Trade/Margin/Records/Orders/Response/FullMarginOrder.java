package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Fill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code FullMarginOrder} class is useful to format FullMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * **/

public class FullMarginOrder extends ResultMarginOrder{

    private ArrayList<Fill> fillMarginsList;

    public FullMarginOrder(String symbol, double orderId, String clientOrderId, long transactTime, boolean isIsolated,
                           double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                           String timeInForce, String type, String side, JSONArray jsonArray) {
        super(symbol, orderId, clientOrderId, transactTime, isIsolated, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        loadFillMargins(jsonArray);
    }

    /** Method to load FillMargins list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadFillMargins(JSONArray jsonArray) {
        fillMarginsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++) {
            JSONObject fill = jsonArray.getJSONObject(j);
            fillMarginsList.add(new Fill(fill.getDouble("price"),
                    fill.getDouble("qty"),
                    fill.getDouble("commission"),
                    fill.getString("commissionAsset")
            ));
        }
    }

    public ArrayList<Fill> getFillMarginsList() {
        return fillMarginsList;
    }

    public void setFillMarginsList(ArrayList<Fill> fillMarginsList) {
        this.fillMarginsList = fillMarginsList;
    }

    public void insertFill(Fill fill){
        if(!fillMarginsList.contains(fill))
            fillMarginsList.add(fill);
    }

    public boolean removeFill(Fill fill){
        return fillMarginsList.remove(fill);
    }

    public Fill getFillMargin(int index){
        return fillMarginsList.get(index);
    }

}
