package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.TradeConstants.*;

/**
 *  The {@code MarginForceLiquidation} class is useful to format Binance Margin Force Liquidation request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginForceLiquidation {

    private int total;
    private ArrayList<ForceLiquidationAsset> forceLiquidationAssetsList;

    public MarginForceLiquidation(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadForceLiquidationAssets(jsonArray);
    }

    /** Method to load ForceLiquidationAssets list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadForceLiquidationAssets(JSONArray jsonArray) {
        forceLiquidationAssetsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
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

    public void setTotal(int total) {
        if(total < 0)
            throw new IllegalArgumentException("Total value cannot be less than 0");
        this.total = total;
    }

    public ArrayList<ForceLiquidationAsset> getForceLiquidationAssetsList() {
        return forceLiquidationAssetsList;
    }

    public void setForceLiquidationAssetsList(ArrayList<ForceLiquidationAsset> forceLiquidationAssetsList) {
        this.forceLiquidationAssetsList = forceLiquidationAssetsList;
        setTotal(forceLiquidationAssetsList.size());
    }

    public void insertForceLiquidationAsset(ForceLiquidationAsset forceLiquidationAsset){
        if(!forceLiquidationAssetsList.contains(forceLiquidationAsset)) {
            forceLiquidationAssetsList.add(forceLiquidationAsset);
            setTotal(total + 1);
        }
    }

    public boolean removeForceLiquidationAsset(ForceLiquidationAsset forceLiquidationAsset){
        boolean removed = forceLiquidationAssetsList.remove(forceLiquidationAsset);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public ForceLiquidationAsset getForceLiquidationAsset(int index){
        try{
            return forceLiquidationAssetsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

    /**
     * The {@code ForceLiquidationAsset} class is useful to obtain and format ForceLiquidationAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * **/

    public static class ForceLiquidationAsset {

        private double avgPrice;
        private double executedQty;
        private final long orderId;
        private double price;
        private double qty;
        private String side;
        private final String symbol;
        private String timeInForce;
        private boolean isIsolated;
        private long updatedTime;

        public ForceLiquidationAsset(double avgPrice, double executedQty, long orderId, double price, double qty,
                                     String side, String symbol, String timeInForce, boolean isIsolated, long updatedTime) {
            this.avgPrice = avgPrice;
            this.executedQty = executedQty;
            this.orderId = orderId;
            this.price = price;
            this.qty = qty;
            this.side = side;
            this.symbol = symbol;
            this.timeInForce = timeInForce;
            this.isIsolated = isIsolated;
            this.updatedTime = updatedTime;
        }

        public double getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(double avgPrice) {
            if(avgPrice < 0)
                throw new IllegalArgumentException("Average price value cannot be less than 0");
            this.avgPrice = avgPrice;
        }

        public double getExecutedQty() {
            return executedQty;
        }

        public void setExecutedQty(double executedQty) {
            if(executedQty < 0)
                throw new IllegalArgumentException("Executed quantity value cannot be less than 0");
            this.executedQty = executedQty;
        }

        public long getOrderId() {
            return orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            if(price < 0)
                throw new IllegalArgumentException("Price value cannot be less than 0");
            this.price = price;
        }

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            if(qty < 0)
                throw new IllegalArgumentException("Quantity value cannot be less than 0");
            this.qty = qty;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getTimeInForce() {
            return timeInForce;
        }

        public void setTimeInForce(String timeInForce) {
            if(timeInForce.equals(TIME_IN_FORCE_FOK) || timeInForce.equals(TIME_IN_FORCE_GTC)
                    || timeInForce.equals(TIME_IN_FORCE_IOC)) {
                this.timeInForce = timeInForce;
            }else
                throw new IllegalArgumentException("Time in force value can only be FOK,GTC or IOC");
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

        public void setUpdatedTime(long updatedTime) {
            if(updatedTime < 0)
                throw new IllegalArgumentException("Updated time value cannot be less than 0");
            this.updatedTime = updatedTime;
        }

    }

}
