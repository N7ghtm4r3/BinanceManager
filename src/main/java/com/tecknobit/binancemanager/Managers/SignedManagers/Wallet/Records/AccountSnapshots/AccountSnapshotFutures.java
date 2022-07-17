package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code AccountSnapshotFutures} class is useful to obtain and format AccountSnapshotFutures object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshotFutures extends AccountSnapshot{

    /**
     * {@code dataFuturesList} is instance that memorizes list of {@link DataFutures}
     * **/
    private ArrayList<DataFutures> dataFuturesList;

    /** Constructor to init {@link AccountSnapshotFutures} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details in JSON format
     * **/
    public AccountSnapshotFutures(int code, String msg, String type, JSONArray accountDetails) {
        super(code, msg, type, accountDetails);
    }

    /** Constructor to init {@link AccountSnapshotFutures} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details in JSON format
     * @param dataFutures: list of {@link DataFutures}
     * **/
    public AccountSnapshotFutures(int code, String msg, String type, JSONArray accountDetails, ArrayList<DataFutures> dataFutures) {
        super(code, msg, type, accountDetails);
        this.dataFuturesList = dataFutures;
    }

    /** Method to get AccountSnapshotFutures object
     * any params required
     * @return AccountSnapshotFutures object then to cast
     * **/
    public AccountSnapshotFutures getAccountSnapshotFutures() {
        dataFuturesList = new ArrayList<>();
        if(accountDetails != null){
            for (int j = 0; j < accountDetails.length(); j++){
                JSONObject dataRow = accountDetails.getJSONObject(j);
                long updateTime = dataRow.getLong("updateTime");
                dataFuturesList.add(new DataFutures(updateTime,
                            getAssetsList(dataRow.getJSONArray("assets")),
                            getPositionsList(dataRow.getJSONArray("position"))
                        ));
            }
        }
        return new AccountSnapshotFutures(code, msg, type, accountDetails, dataFuturesList);
    }

    /** Method to assemble an AssetFutures list
     * @param assets: accountDetails obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return assetFuturesList list as ArrayList<AssetFutures>
     * **/
    private ArrayList<AssetFutures> getAssetsList(JSONArray assets) {
        ArrayList<AssetFutures> assetFutures = new ArrayList<>();
        for (int j=0; j < assets.length(); j++){
            JSONObject asset = accountDetails.getJSONObject(j);
            assetFutures.add(new AssetFutures(asset.getString("asset"),
                    asset.getDouble("marginBalance"),
                    asset.getDouble("walletBalance")
            ));
        }
        return assetFutures;
    }

    /** Method to assemble an PositionFutures list
     * @param positions: accountDetails obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return positionFuturesList list as ArrayList<PositionFutures>
     * **/
    private ArrayList<PositionFutures> getPositionsList(JSONArray positions) {
        ArrayList<PositionFutures> positionFutures = new ArrayList<>();
        for (int j=0; j < positions.length(); j++){
            JSONObject position = accountDetails.getJSONObject(j);
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
        return dataFuturesList.get(index);
    }

    /**
     *  The {@code DataFutures} class is useful to obtain and format DataFutures object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataFutures {

        /**
         * {@code updateTime} is instance that memorizes update time value
         * **/
        private final long updateTime;

        /**
         * {@code assetFuturesList} is instance that memorizes list of {@link AssetFutures}
         * **/
        private ArrayList<AssetFutures> assetFuturesList;

        /**
         * {@code positionFuturesList} is instance that memorizes list of {@link PositionFutures}
         * **/
        private ArrayList<PositionFutures> positionFuturesList;

        /** Constructor to init {@link DataFutures} object
         * @param updateTime: update time value
         * @param assetFutures: list of {@link AssetFutures}
         * @param positionFutures: list of {@link PositionFutures}
         * **/
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
            return assetFuturesList.get(index);
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
            return positionFuturesList.get(index);
        }

    }

    /**
     *  The {@code AssetFutures} class is useful to obtain and format AssetFutures object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class AssetFutures {

        /**
         * {@code asset} is instance that memorizes asset value
         * **/
        private final String asset;

        /**
         * {@code marginBalance} is instance that memorizes margin balance value
         * **/
        private double marginBalance;

        /**
         * {@code walletBalance} is instance that memorizes wallet balance value
         * **/
        private double walletBalance;

        /** Constructor to init {@link AssetFutures} object
         * @param asset: update time value
         * @param marginBalance: list of {@link AssetFutures}
         * @param walletBalance: list of {@link PositionFutures}
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public AssetFutures(String asset, double marginBalance, double walletBalance) {
            this.asset = asset;
            if(marginBalance < 0)
                throw new IllegalArgumentException("Margin balance value cannot be less than 0");
            else
                this.marginBalance = marginBalance;
            if(walletBalance < 0)
                throw new IllegalArgumentException("Wallet balance value cannot be less than 0");
            else
                this.walletBalance = walletBalance;
        }

        public String getAsset() {
            return asset;
        }

        public double getMarginBalance() {
            return marginBalance;
        }

        /** Method to set {@link #marginBalance}
         * @param marginBalance: margin balance value
         * @throws IllegalArgumentException when margin balance value is less than 0
         * **/
        public void setMarginBalance(double marginBalance) {
            if(marginBalance < 0)
                throw new IllegalArgumentException("Margin balance value cannot be less than 0");
            this.marginBalance = marginBalance;
        }

        public double getWalletBalance() {
            return walletBalance;
        }

        /** Method to set {@link #walletBalance}
         * @param walletBalance: wallet balance value
         * @throws IllegalArgumentException when wallet balance value is less than 0
         * **/
        public void setWalletBalance(double walletBalance) {
            if(walletBalance < 0)
                throw new IllegalArgumentException("Wallet balance value cannot be less than 0");
            this.walletBalance = walletBalance;
        }

    }

    /**
     *  The {@code PositionFutures} class is useful to obtain and format PositionFutures object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class PositionFutures {

        /**
         * {@code entryPrice} is instance that memorizes entry price value
         * **/
        private double entryPrice;

        /**
         * {@code markPrice} is instance that memorizes mark price value
         * **/
        private double markPrice;

        /**
         * {@code positionAmt} is instance that memorizes position amt value
         * **/
        private double positionAmt;

        /**
         * {@code symbol} is instance that memorizes symbol value
         * **/
        private final String symbol;

        /**
         * {@code unRealizedProfit} is instance that unrealize profit value
         * **/
        private double unRealizedProfit;

        /** Constructor to init {@link PositionFutures} object
         * @param entryPrice: entry price value
         * @param markPrice: mark price value
         * @param positionAmt: position amt value
         * @param symbol: symbol value
         * @param unRealizedProfit: unrealize profit value
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
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

        /** Method to set {@link #entryPrice}
         * @param entryPrice: entry price value
         * @throws IllegalArgumentException when entry price value is less than 0
         * **/
        public void setEntryPrice(double entryPrice) {
            if(entryPrice < 0)
                throw new IllegalArgumentException("Entry price value cannot be less than 0");
            this.entryPrice = entryPrice;
        }

        public double getMarkPrice() {
            return markPrice;
        }

        /** Method to set {@link #markPrice}
         * @param markPrice: mark price value
         * @throws IllegalArgumentException when mark price value is less than 0
         * **/
        public void setMarkPrice(double markPrice) {
            if(markPrice < 0)
                throw new IllegalArgumentException("Mark price value cannot be less than 0");
            this.markPrice = markPrice;
        }

        public double getPositionAmt() {
            return positionAmt;
        }

        /** Method to set {@link #positionAmt}
         * @param positionAmt: unrealize profit value
         * @throws IllegalArgumentException when unrealize profit value is less than 0
         * **/
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

        /** Method to set {@link #unRealizedProfit}
         * @param unRealizedProfit: position amt value
         * @throws IllegalArgumentException when mark position amt value is less than 0
         * **/
        public void setUnRealizedProfit(double unRealizedProfit) {
            if(unRealizedProfit < 0)
                throw new IllegalArgumentException("Unrealize profit value cannot be less than 0");
            this.unRealizedProfit = unRealizedProfit;
        }

    }

}
