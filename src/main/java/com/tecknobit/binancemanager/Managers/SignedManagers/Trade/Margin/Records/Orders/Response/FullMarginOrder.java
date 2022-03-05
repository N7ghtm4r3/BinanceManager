package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Fill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code FullMarginOrder} class is useful to format FullMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
 * **/

public class FullMarginOrder extends ResultMarginOrder{

    private ArrayList<Fill> fillMargins;

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
        fillMargins = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++) {
            JSONObject fill = jsonArray.getJSONObject(j);
            fillMargins.add(new Fill(fill.getDouble("price"),
                    fill.getDouble("qty"),
                    fill.getDouble("commission"),
                    fill.getString("commissionAsset")
            ));
        }
    }

    public ArrayList<Fill> getFillMargins() {
        return fillMargins;
    }

    public Fill getFillMargin(int index){
        return fillMargins.get(index);
    }

}
