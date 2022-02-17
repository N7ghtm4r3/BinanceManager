package com.tecknobit.binancemanager.Managers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccountSnapshotFutures extends AccountSnapshot{

    private final int code;
    private final String msg;
    private final String type;
    private JSONArray jsonArray;
    private ArrayList<DataFutures> dataFutures;

    public AccountSnapshotFutures(int code, String msg, String type, JSONArray jsonArray) {
        super(code, msg, type, jsonArray);
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    public AccountSnapshotFutures(int code, String msg, String type, JSONArray jsonArray, ArrayList<DataFutures> dataFutures) {
        super(code, msg, type, jsonArray);
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.dataFutures = dataFutures;
    }

    public AccountSnapshotFutures getAccountSnapshotFutures() {
        dataFutures = new ArrayList<>();
        if(jsonArray != null){
            for (int j=0; j < jsonArray.length(); j++){
                JSONObject dataRow = jsonArray.getJSONObject(j);
                long updateTime = dataRow.getLong("updateTime");
                dataFutures.add(new DataFutures(updateTime,
                            getAssetsList(dataRow.getJSONArray("assets")),
                            getPositionsList(dataRow.getJSONArray("position"))
                        ));
            }
        }
        return new AccountSnapshotFutures(code,msg,type,jsonArray,dataFutures);
    }

    private ArrayList<AssetFutures> getAssetsList(JSONArray assets) {
        ArrayList<AssetFutures> assetFutures = new ArrayList<>();
        for (int j=0; j < assets.length(); j++){
            JSONObject asset = jsonArray.getJSONObject(j);
            assetFutures.add(new AssetFutures(asset.getString("asset"),
                    asset.getDouble("marginBalance"),
                    asset.getDouble("walletBalance")
            ));
        }
        return assetFutures;
    }

    private ArrayList<PositionFutures> getPositionsList(JSONArray positions) {
        ArrayList<PositionFutures> positionFutures = new ArrayList<>();
        for (int j=0; j < positions.length(); j++){
            JSONObject position = jsonArray.getJSONObject(j);
            positionFutures.add(new PositionFutures(position.getDouble("entryPrice"),
                    position.getDouble("markPrice"),
                    position.getDouble("positionAmt"),
                    position.getString("symbol"),
                    position.getDouble("1.24029054")
            ));
        }
        return positionFutures;
    }

    public ArrayList<DataFutures> getDataFutures() {
        return dataFutures;
    }

    public static class DataFutures {

        private final long updateTime;
        private final ArrayList<AssetFutures> assetFutures;
        private final ArrayList<PositionFutures> positionFutures;

        public DataFutures(long updateTime, ArrayList<AssetFutures> assetFutures, ArrayList<PositionFutures> positionFutures) {
            this.updateTime = updateTime;
            this.assetFutures = assetFutures;
            this.positionFutures = positionFutures;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public ArrayList<AssetFutures> getAssetFutures() {
            return assetFutures;
        }

        public ArrayList<PositionFutures> getPositionFutures() {
            return positionFutures;
        }

    }

    public static class AssetFutures {

        private final String asset;
        private final double marginBalance;
        private final double walletBalance;

        public AssetFutures(String asset, double marginBalance, double walletBalance) {
            this.asset = asset;
            this.marginBalance = marginBalance;
            this.walletBalance = walletBalance;
        }

        public String getAsset() {
            return asset;
        }

        public double getMarginBalance() {
            return marginBalance;
        }

        public double getWalletBalance() {
            return walletBalance;
        }

    }

    public static class PositionFutures {

        private final double entryPrice;
        private final double markPrice;
        private final double positionAmt;
        private final String symbol;
        private final double unRealizedProfit;

        public PositionFutures(double entryPrice, double markPrice, double positionAmt, String symbol, double unRealizedProfit) {
            this.entryPrice = entryPrice;
            this.markPrice = markPrice;
            this.positionAmt = positionAmt;
            this.symbol = symbol;
            this.unRealizedProfit = unRealizedProfit;
        }

        public double getEntryPrice() {
            return entryPrice;
        }

        public double getMarkPrice() {
            return markPrice;
        }

        public double getPositionAmt() {
            return positionAmt;
        }

        public String getSymbol() {
            return symbol;
        }

        public double getUnRealizedProfit() {
            return unRealizedProfit;
        }

    }

}
