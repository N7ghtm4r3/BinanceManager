package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
        if(!forceLiquidationAssetsList.contains(forceLiquidationAsset))
            forceLiquidationAssetsList.add(forceLiquidationAsset);
        setTotal(total + 1);
    }

    public boolean removeForceLiquidationAsset(ForceLiquidationAsset forceLiquidationAsset){
        boolean removed = forceLiquidationAssetsList.remove(forceLiquidationAsset);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public ForceLiquidationAsset getForceLiquidationAsset(int index){
        return forceLiquidationAssetsList.get(index);
    }

    /**
     * The {@code ForceLiquidationAsset} class is useful to obtain and format ForceLiquidationAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * **/

    public static class ForceLiquidationAsset {

        private final double avgPrice;
        private final double executedQty;
        private final long orderId;
        private final double price;
        private final double qty;
        private final String side;
        private final String symbol;
        private final String timeInForce;
        private final boolean isIsolated;
        private final long updatedTime;

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

        public double getExecutedQty() {
            return executedQty;
        }

        public long getOrderId() {
            return orderId;
        }

        public double getPrice() {
            return price;
        }

        public double getQty() {
            return qty;
        }

        public String getSide() {
            return side;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getTimeInForce() {
            return timeInForce;
        }

        public boolean isIsolated() {
            return isIsolated;
        }

        public long getUpdatedTime() {
            return updatedTime;
        }

    }

}
