package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Fill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code FullMarginOrder} class is useful to format FullMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * **/

public class FullMarginOrder extends ResultMarginOrder {

    /**
     * {@code fillMarginsList} is instance that memorizes fill margin data list
     * **/
    private ArrayList<Fill> fillMarginsList;

    /** Constructor to init {@link FullMarginOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime: transaction time
     * @param isIsolated: is isolated
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * @param jsonMargins: margins details in JSON format
     * **/
    public FullMarginOrder(String symbol, double orderId, String clientOrderId, long transactTime, boolean isIsolated,
                           double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                           String timeInForce, String type, String side, JSONArray jsonMargins) {
        super(symbol, orderId, clientOrderId, transactTime, isIsolated, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        loadFillMargins(jsonMargins);
    }

    /** Method to load FillMargins list
     * @param jsonMargins: obtained from Binance's request
     * any return
     * **/
    private void loadFillMargins(JSONArray jsonMargins) {
        fillMarginsList = new ArrayList<>();
        for (int j = 0; j < jsonMargins.length(); j++) {
            JSONObject fill = jsonMargins.getJSONObject(j);
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
