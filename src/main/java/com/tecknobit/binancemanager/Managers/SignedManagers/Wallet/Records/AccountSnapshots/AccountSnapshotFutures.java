package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code AccountSnapshotFutures} class is useful to obtain and format AccountSnapshotFutures object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshotFutures extends AccountSnapshot{

    private ArrayList<DataFutures> dataFuturesList;

    public AccountSnapshotFutures(int code, String msg, String type, JSONArray jsonArray) {
        super(code, msg, type, jsonArray);
    }

    public AccountSnapshotFutures(int code, String msg, String type, JSONArray jsonArray, ArrayList<DataFutures> dataFutures) {
        super(code, msg, type, jsonArray);
        this.dataFuturesList = dataFutures;
    }

    /** Method to get AccountSnapshotFutures object
     * any params required
     * @return AccountSnapshotFutures object then to cast
     * **/
    public AccountSnapshotFutures getAccountSnapshotFutures() {
        dataFuturesList = new ArrayList<>();
        if(jsonArray != null){
            for (int j=0; j < jsonArray.length(); j++){
                JSONObject dataRow = jsonArray.getJSONObject(j);
                long updateTime = dataRow.getLong("updateTime");
                dataFuturesList.add(new DataFutures(updateTime,
                            getAssetsList(dataRow.getJSONArray("assets")),
                            getPositionsList(dataRow.getJSONArray("position"))
                        ));
            }
        }
        return new AccountSnapshotFutures(code, msg, type, jsonArray, dataFuturesList);
    }

    /** Method to assemble an AssetFutures list
     * @param #assets: jsonArray obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return assetFuturesList list as ArrayList<AssetFutures>
     * **/
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

    /** Method to assemble an PositionFutures list
     * @param #positions: jsonArray obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return positionFuturesList list as ArrayList<PositionFutures>
     * **/
    private ArrayList<PositionFutures> getPositionsList(JSONArray positions) {
        ArrayList<PositionFutures> positionFutures = new ArrayList<>();
        for (int j=0; j < positions.length(); j++){
            JSONObject position = jsonArray.getJSONObject(j);
            positionFutures.add(new PositionFutures(position.getDouble("entryPrice"),
                    position.getDouble("markPrice"),
                    position.getDouble("positionAmt"),
                    position.getString("symbol"),
                    position.getDouble("unRealizedProfit")
            ));
        }
        return positionFutures;
    }

    public ArrayList<DataFutures> getDataFuturesList() {
        return dataFuturesList;
    }

    public void setDataFuturesList(ArrayList<DataFutures> dataFuturesList) {
        this.dataFuturesList = dataFuturesList;
    }

    public void insertDataFuture(DataFutures dataFutures){
        if(!dataFuturesList.contains(dataFutures))
            dataFuturesList.add(dataFutures);
    }

    public boolean removeDataFuture(DataFutures dataFutures){
        return dataFuturesList.remove(dataFutures);
    }

    public DataFutures getDataFuture(int index){
        try{
            return dataFuturesList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     *  The {@code DataFutures} class is useful to obtain and format DataFutures object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataFutures {

        private final long updateTime;
        private ArrayList<AssetFutures> assetFuturesList;
        private ArrayList<PositionFutures> positionFuturesList;

        public DataFutures(long updateTime, ArrayList<AssetFutures> assetFutures, ArrayList<PositionFutures> positionFutures) {
            this.updateTime = updateTime;
            this.assetFuturesList = assetFutures;
            this.positionFuturesList = positionFutures;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public ArrayList<AssetFutures> getAssetFuturesList() {
            return assetFuturesList;
        }

        public void setAssetFuturesList(ArrayList<AssetFutures> assetFuturesList) {
            this.assetFuturesList = assetFuturesList;
        }

        public void insertAssetFuture(AssetFutures assetFutures){
            if(!assetFuturesList.contains(assetFutures))
                assetFuturesList.add(assetFutures);
        }

        public boolean removeAssetFuture(AssetFutures assetFutures){
            return assetFuturesList.remove(assetFutures);
        }

        public AssetFutures getAssetFutures(int index){
            try{
                return assetFuturesList.get(index);
            }catch (IndexOutOfBoundsException e){
                throw new IndexOutOfBoundsException(index);
            }
        }

        public ArrayList<PositionFutures> getPositionFuturesList() {
            return positionFuturesList;
        }

        public void setPositionFuturesList(ArrayList<PositionFutures> positionFuturesList) {
            this.positionFuturesList = positionFuturesList;
        }

        public void insertAssetFuture(PositionFutures positionFutures){
            if(!positionFuturesList.contains(positionFutures))
                positionFuturesList.add(positionFutures);
        }

        public boolean removeAssetFuture(PositionFutures positionFutures){
            return positionFuturesList.remove(positionFutures);
        }

        public PositionFutures getPositionFuturesList(int index){
            try{
                return positionFuturesList.get(index);
            }catch (IndexOutOfBoundsException e){
                throw new IndexOutOfBoundsException(index);
            }
        }

    }

    /**
     *  The {@code AssetFutures} class is useful to obtain and format AssetFutures object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class AssetFutures {

        private final String asset;
        private double marginBalance;
        private double walletBalance;

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

        public void setMarginBalance(double marginBalance) {
            if(marginBalance < 0)
                throw new IllegalArgumentException("Margin balance value cannot be less than 0");
            this.marginBalance = marginBalance;
        }

        public double getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(double walletBalance) {
            if(walletBalance < 0)
                throw new IllegalArgumentException("Wallet balance value cannot be less than 0");
            this.walletBalance = walletBalance;
        }

    }

    /**
     *  The {@code PositionFutures} class is useful to obtain and format PositionFutures object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class PositionFutures {

        private double entryPrice;
        private double markPrice;
        private double positionAmt;
        private final String symbol;
        private double unRealizedProfit;

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

        public void setEntryPrice(double entryPrice) {
            if(entryPrice < 0)
                throw new IllegalArgumentException("Entry price value cannot be less than 0");
            this.entryPrice = entryPrice;
        }

        public double getMarkPrice() {
            return markPrice;
        }

        public void setMarkPrice(double markPrice) {
            if(markPrice < 0)
                throw new IllegalArgumentException("Mark price value cannot be less than 0");
            this.markPrice = markPrice;
        }

        public double getPositionAmt() {
            return positionAmt;
        }

        public void setPositionAmt(double positionAmt) {
            if(positionAmt < 0)
                throw new IllegalArgumentException("Position amt value cannot be less than 0");
            this.positionAmt = positionAmt;
        }

        public String getSymbol() {
            return symbol;
        }

        public double getUnRealizedProfit() {
            return unRealizedProfit;
        }

        public void setUnRealizedProfit(double unRealizedProfit) {
            if(unRealizedProfit < 0)
                throw new IllegalArgumentException("Unrealize profit value cannot be less than 0");
            this.unRealizedProfit = unRealizedProfit;
        }

    }

}
