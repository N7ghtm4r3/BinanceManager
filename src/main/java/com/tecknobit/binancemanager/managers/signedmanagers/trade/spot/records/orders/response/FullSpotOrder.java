package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Fill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code FullOrder} class is useful to format all SpotOrder {@code "Binance"} request in FullOrder format
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 **/

public class FullSpotOrder extends ResultSpotOrder implements BinanceManager.BinanceResponse {

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
     * @param fills: fills details as {@link JSONObject}
     * **/
    public FullSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime, double price,
                         double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                         String type, String side, JSONArray fills) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        loadFills(fills);
    }

    /**
     * Constructor to init {@link FullSpotOrder} object
     *
     * @param fullSpotOrder: full spot order details as {@link JSONObject}
     **/
    public FullSpotOrder(JSONObject fullSpotOrder) {
        super(fullSpotOrder);
        loadFills(hOrder.getJSONArray("fills", new JSONArray()));
    }

    /**
     * Method to load fillsList list
     *
     * @param fillsArray: obtained from {@code "Binance"}'s request
     *                    any return
     **/
    private void loadFills(JSONArray fillsArray) {
        fillsList = new ArrayList<>();
        for (int j = 0; j < fillsArray.length(); j++) {
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

    public boolean removeFill(FillSpot fillSpot) {
        return fillsList.remove(fillSpot);
    }

    public FillSpot getFill(int index) {
        return fillsList.get(index);
    }

    /**
     * Method to get error code <br>
     * Any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
     **/
    @Override
    public int getCode() {
        if (hOrder != null)
            return hOrder.getInt("code", -1);
        return -1;
    }

    /**
     * Method to get error message <br>
     * Any params required
     *
     * @return message of error as {@link String}
     * *
     * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
     **/
    @Override
    public String getMessage() {
        if (hOrder != null)
            return hOrder.getString("msg", null);
        return null;
    }

    @Override
    public String toString() {
        return "FullSpotOrder{" +
                "fillsList=" + fillsList +
                ", price=" + price +
                ", origQty=" + origQty +
                ", executedQty=" + executedQty +
                ", cummulativeQuoteQty=" + cummulativeQuoteQty +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", transactTime=" + transactTime +
                ", orderListId=" + orderListId +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

    /**
     * The {@code FillSpot} class is useful to obtain and format FillSpot object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
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

        @Override
        public String toString() {
            return "FillSpot{" +
                    "tradeId=" + tradeId +
                    ", price=" + price +
                    ", qty=" + qty +
                    ", commission=" + commission +
                    ", commissionAsset='" + commissionAsset + '\'' +
                    '}';
        }

    }

}

