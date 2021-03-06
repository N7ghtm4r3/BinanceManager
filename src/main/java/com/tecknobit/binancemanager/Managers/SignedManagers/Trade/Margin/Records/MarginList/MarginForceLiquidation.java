package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.TradeConstants.*;

/**
 *  The {@code MarginForceLiquidation} class is useful to format Binance Margin Force Liquidation request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginForceLiquidation {

    /**
     * {@code total} is instance that memorizes total size of force liquidations
     * **/
    private int total;

    /**
     * {@code forceLiquidationAssetsList} is instance that memorizes force liquidations list
     * **/
    private ArrayList<ForceLiquidationAsset> forceLiquidationAssetsList;

    /** Constructor to init {@link MarginForceLiquidation} object
     * @param jsonLiquidation: margin force liquidation details in JSON format
     * @throws IllegalArgumentException when liquidation details are not recoverable
     * **/
    public MarginForceLiquidation(JSONObject jsonLiquidation) {
        JSONArray jsonForceLiquidation = new JsonHelper(jsonLiquidation).getJSONArray("rows");
        if(jsonForceLiquidation != null) {
            total = jsonForceLiquidation.length();
            loadForceLiquidationAssets(jsonForceLiquidation);
        }else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /** Method to load ForceLiquidationAssets list
     * @param jsonAssets: obtained from Binance's request
     * **/
    private void loadForceLiquidationAssets(JSONArray jsonAssets) {
        forceLiquidationAssetsList = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++){
            JSONObject jsonObject = jsonAssets.getJSONObject(j);
            forceLiquidationAssetsList.add(new ForceLiquidationAsset(jsonObject.getDouble("avgPrice"),
                    jsonObject.getDouble("executedQty"),
                    jsonObject.getLong("orderId"),
                    jsonObject.getDouble("price"),
                    jsonObject.getDouble("qty"),
                    jsonObject.getString("side"),
                    jsonObject.getString("symbol"),
                    jsonObject.getString("timeInForce"),
                    jsonObject.getBoolean("isIsolated"),
                    jsonObject.getLong("updatedTime")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<ForceLiquidationAsset> getForceLiquidationAssetsList() {
        return forceLiquidationAssetsList;
    }

    public void setForceLiquidationAssetsList(ArrayList<ForceLiquidationAsset> forceLiquidationAssetsList) {
        this.forceLiquidationAssetsList = forceLiquidationAssetsList;
        total = forceLiquidationAssetsList.size();
    }

    public void insertForceLiquidationAsset(ForceLiquidationAsset forceLiquidationAsset){
        if(!forceLiquidationAssetsList.contains(forceLiquidationAsset)) {
            forceLiquidationAssetsList.add(forceLiquidationAsset);
            total += 1;
        }
    }

    public boolean removeForceLiquidationAsset(ForceLiquidationAsset forceLiquidationAsset){
        boolean removed = forceLiquidationAssetsList.remove(forceLiquidationAsset);
        if(removed)
            total -= 1;
        return removed;
    }

    public ForceLiquidationAsset getForceLiquidationAsset(int index){
        return forceLiquidationAssetsList.get(index);
    }

    /**
     * The {@code ForceLiquidationAsset} class is useful to obtain and format ForceLiquidationAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * **/

    public static class ForceLiquidationAsset {

        /**
         * {@code avgPrice} is instance that memorizes average price of liquidation asset
         * **/
        private double avgPrice;

        /**
         * {@code executedQty} is instance that memorizes executed quantity of liquidation asset
         * **/
        private double executedQty;

        /**
         * {@code orderId} is instance that memorizes identifier of order
         * **/
        private long orderId;

        /**
         * {@code price} is instance that memorizes price of order
         * **/
        private double price;

        /**
         * {@code qty} is instance that memorizes quantity of order
         * **/
        private double qty;

        /**
         * {@code side} is instance that memorizes side of order
         * **/
        private String side;

        /**
         * {@code symbol} is instance that memorizes symbol of order
         * **/
        private String symbol;

        /**
         * {@code timeInForce} is instance that memorizes time in force of order
         * **/
        private String timeInForce;

        /**
         * {@code isIsolated} is instance that memorizes if order is isolated
         * **/
        private boolean isIsolated;

        /**
         * {@code updatedTime} is instance that memorizes time of order
         * **/
        private long updatedTime;

        /** Constructor to init {@link ForceLiquidationAsset} object
         * @param avgPrice: average price of liquidation asset
         * @param executedQty: executed quantity of liquidation asset
         * @param orderId: identifier of order
         * @param price: price of order
         * @param qty: quantity of order
         * @param side: side of order
         * @param symbol: symbol of order
         * @param timeInForce: time in force of order
         * @param isIsolated: order is isolated
         * @param updatedTime: time of order
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public ForceLiquidationAsset(double avgPrice, double executedQty, long orderId, double price, double qty,
                                     String side, String symbol, String timeInForce, boolean isIsolated, long updatedTime) {
            if(avgPrice < 0)
                throw new IllegalArgumentException("Average price value cannot be less than 0");
            else
                this.avgPrice = avgPrice;
            if(executedQty < 0)
                throw new IllegalArgumentException("Executed quantity value cannot be less than 0");
            else
                this.executedQty = executedQty;
            this.orderId = orderId;
            if(price < 0)
                throw new IllegalArgumentException("Price value cannot be less than 0");
            else
                this.price = price;
            if(qty < 0)
                throw new IllegalArgumentException("Quantity value cannot be less than 0");
            else
                this.qty = qty;
            if(sideIsValid(side))
                this.side = side;
            else
                throw new IllegalArgumentException("Side value can only be BUY or SELL");
            this.symbol = symbol;
            if(timeInForceIsValid(timeInForce))
                this.timeInForce = timeInForce;
            else
                throw new IllegalArgumentException("Time in force value can only be FOK, GTC or IOC");
            this.isIsolated = isIsolated;
            if(updatedTime < 0)
                throw new IllegalArgumentException("Updated time value cannot be less than 0");
            else
                this.updatedTime = updatedTime;
        }

        public double getAvgPrice() {
            return avgPrice;
        }

        /** Method to set {@link #avgPrice}
         * @param avgPrice: average price of liquidation asset
         * @throws IllegalArgumentException when average price of liquidation asset value is less than 0
         * **/
        public void setAvgPrice(double avgPrice) {
            if(avgPrice < 0)
                throw new IllegalArgumentException("Average price value cannot be less than 0");
            this.avgPrice = avgPrice;
        }

        public double getExecutedQty() {
            return executedQty;
        }

        /** Method to set {@link #executedQty}
         * @param executedQty: executed quantity of liquidation asset
         * @throws IllegalArgumentException when executed quantity of liquidation asset value is less than 0
         * **/
        public void setExecutedQty(double executedQty) {
            if(executedQty < 0)
                throw new IllegalArgumentException("Executed quantity value cannot be less than 0");
            this.executedQty = executedQty;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        /** Method to set {@link #price}
         * @param price: price of order
         * @throws IllegalArgumentException when price of order value is less than 0
         * **/
        public void setPrice(double price) {
            if(price < 0)
                throw new IllegalArgumentException("Price value cannot be less than 0");
            this.price = price;
        }

        public double getQty() {
            return qty;
        }

        /** Method to set {@link #qty}
         * @param qty: quantity of order
         * @throws IllegalArgumentException when quantity of order value is less than 0
         * **/
        public void setQty(double qty) {
            if(qty < 0)
                throw new IllegalArgumentException("Quantity value cannot be less than 0");
            this.qty = qty;
        }

        public String getSide() {
            return side;
        }

        /** Method to set {@link #side}
         * @param side: side of order
         * @throws IllegalArgumentException when side is not valid
         * **/
        public void setSide(String side) {
            if(sideIsValid(side))
                this.side = side;
            else
                throw new IllegalArgumentException("Side value can only be BUY or SELL");
        }

        /** Method to check {@link #side} validity
         * @param side: side of order
         * @return validity of side of order as boolean
         * **/
        private boolean sideIsValid(String side){
            return side.equals(BUY) || side.equals(SELL);
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getTimeInForce() {
            return timeInForce;
        }

        /** Method to set {@link #timeInForce}
         * @param timeInForce: time in force of order
         * @throws IllegalArgumentException when time in force is not valid
         * **/
        public void setTimeInForce(String timeInForce) {
            if(timeInForceIsValid(timeInForce))
                this.timeInForce = timeInForce;
            else
                throw new IllegalArgumentException("Time in force value can only be FOK, GTC or IOC");
        }

        /** Method to check {@link #timeInForce} validity
         * @param timeInForce: time in force of order
         * @return validity of time in force of order as boolean
         * **/
        private boolean timeInForceIsValid(String timeInForce){
            return timeInForce.equals(TIME_IN_FORCE_FOK) || timeInForce.equals(TIME_IN_FORCE_GTC)
                    || timeInForce.equals(TIME_IN_FORCE_IOC);
        }

        public boolean isIsolated() {
            return isIsolated;
        }

        public void setIsolated(boolean isolated) {
            isIsolated = isolated;
        }

        public long getUpdatedTime() {
            return updatedTime;
        }

        /** Method to set {@link #updatedTime}
         * @param updatedTime: time of order
         * @throws IllegalArgumentException when time of order value is less than 0
         * **/
        public void setUpdatedTime(long updatedTime) {
            if(updatedTime < 0)
                throw new IllegalArgumentException("Updated time value cannot be less than 0");
            this.updatedTime = updatedTime;
        }

    }

}
