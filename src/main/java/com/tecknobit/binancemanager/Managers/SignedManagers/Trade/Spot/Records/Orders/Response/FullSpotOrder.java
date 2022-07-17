package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Fill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code FullOrder} class is useful to format all SpotOrder Binance request in FullOrder format
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class FullSpotOrder extends ResultSpotOrder {

    /**
     * {@code fillsList} is instance that memorizes fills list
     * **/
    private ArrayList<FillSpot> fillsList;

    /** Constructor to init {@link FullSpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * @param transactTime: transaction time
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * @param fills: fills details in JSON format
     * **/
    public FullSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime, double price,
                         double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                         String type, String side, JSONArray fills) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        loadFills(fills);
    }

    /** Method to load fillsList list
     * @param fillsArray: obtained from Binance's request
     * any return
     * **/
    private void loadFills(JSONArray fillsArray) {
        fillsList = new ArrayList<>();
        for(int j=0; j < fillsArray.length(); j++){
            JSONObject fill = fillsArray.getJSONObject(j);
            fillsList.add(new FillSpot(fill.getDouble("price"),
                    fill.getDouble("qty"),
                    fill.getDouble("commission"),
                    fill.getString("commissionAsset"),
                    fill.getLong("tradeId")
            ));
        }
    }

    public ArrayList<FillSpot> getFillsList() {
        return fillsList;
    }

    public void setFillsList(ArrayList<FillSpot> fillsList) {
        this.fillsList = fillsList;
    }

    public void insertFill(FillSpot fillSpot){
        if(!fillsList.contains(fillSpot))
            fillsList.add(fillSpot);
    }

    public boolean removeFill(FillSpot fillSpot){
        return fillsList.remove(fillSpot);
    }

    public FillSpot getFill(int index){
        return fillsList.get(index);
    }

    /**
     * The {@code FillSpot} class is useful to obtain and format FillSpot object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *      https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * **/
    public static class FillSpot extends Fill {

        /**
         * {@code tradeId} is instance that memorizes trade identifier
         * **/
        private final long tradeId;

        /** Constructor to init {@link Fill} object
         * @param price: price of a fill
         * @param qty: quantity of a fill
         * @param commission: commission of a fill
         * @param commissionAsset: commission asset of a fill
         * @param tradeId: trade identifier
         * **/
        public FillSpot(double price, double qty, double commission, String commissionAsset, long tradeId) {
            super(price, qty, commission, commissionAsset);
            this.tradeId = tradeId;
        }

        public long getTradeId() {
            return tradeId;
        }

    }

}

