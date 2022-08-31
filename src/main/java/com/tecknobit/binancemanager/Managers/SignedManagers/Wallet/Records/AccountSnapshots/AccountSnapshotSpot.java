package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code AccountSnapshotSpot} class is useful to obtain and format AccountSnapshotSpot object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 **/

public class AccountSnapshotSpot extends AccountSnapshot {

    /**
     * {@code assetsSpotData} is instance that memorizes list of {@link DataSpot}
     **/
    private ArrayList<DataSpot> assetsSpotData;

    /**
     * Constructor to init {@link AccountSnapshotMargin} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param type:           type of account
     * @param accountDetails: details as {@link JSONObject}
     **/
    public AccountSnapshotSpot(int code, String msg, String type, JSONArray accountDetails) {
        super(code, msg, type, accountDetails);
        assetsSpotData = new ArrayList<>();
        if(accountDetails != null){
            for (int j = 0; j < accountDetails.length(); j++){
                JSONObject dataSpotRow = accountDetails.getJSONObject(j);
                double updateTime = dataSpotRow.getLong("updateTime");
                dataSpotRow = dataSpotRow.getJSONObject("data");
                double totalAssetOfBtc = dataSpotRow.getDouble("totalAssetOfBtc");
                assetsSpotData.add(new DataSpot(totalAssetOfBtc, updateTime, getBalancesSpot(dataSpotRow.getJSONArray("balances"))));
            }
        }
    }

    /** Constructor to init {@link AccountSnapshotMargin} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details as {@link JSONObject}
     * @param assetsSpotData: list of {@link DataSpot}
     * **/
    public AccountSnapshotSpot(int code, String msg, String type, JSONArray accountDetails, ArrayList<DataSpot> assetsSpotData) {
        super(code, msg, type, accountDetails);
        this.assetsSpotData = assetsSpotData;
    }

    /** Method to assemble an BalanceSpot list
     * @param jsonBalances: accountDetails obtain by AccountSnapshot Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return BalanceSpot list as ArrayList<BalanceSpot>
     * **/
    public static ArrayList<BalanceSpot> getBalancesSpot(JSONArray jsonBalances) {
        ArrayList<BalanceSpot> balanceSpots = new ArrayList<>();
        for (int j = 0; j < jsonBalances.length(); j++)
            balanceSpots.add(new BalanceSpot(jsonBalances.getJSONObject(j)));
        return balanceSpots;
    }

    public ArrayList<DataSpot> getAssetsSpotData() {
        return assetsSpotData;
    }

    public void setAssetsSpotData(ArrayList<DataSpot> assetsSpotData) {
        this.assetsSpotData = assetsSpotData;
    }

    public DataSpot getAssetSpotData(int index){
        return assetsSpotData.get(index);
    }

    @Override
    public String toString() {
        return "AccountSnapshotSpot{" +
                "assetsSpotData=" + assetsSpotData +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", accountDetails=" + accountDetails +
                '}';
    }

    /**
     *  The {@code DataSpot} class is useful to obtain and format DataSpot object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataSpot {

        /**
         * {@code totalAssetOfBtc} is instance that memorizes total asset of Bitcoin
         * **/
        private double totalAssetOfBtc;

        /**
         * {@code updateTime} is instance that memorizes update time value
         * **/
        private double updateTime;

        /**
         * {@code balanceSpotList} is instance that memorizes list of {@link BalanceSpot}
         * **/
        private ArrayList<BalanceSpot> balanceSpotList;

        /** Constructor to init {@link DataSpot} object
         * @param totalAssetOfBtc: total asset of Bitcoin
         * @param updateTime: update time value
         * @param balanceSpot: list of {@link BalanceSpot}
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public DataSpot(double totalAssetOfBtc, double updateTime, ArrayList<BalanceSpot> balanceSpot) {
            if(totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            else
                this.totalAssetOfBtc = totalAssetOfBtc;
            if(updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            else
                this.updateTime = updateTime;
            this.balanceSpotList = balanceSpot;
        }

        public double getTotalAssetOfBtc() {
            return totalAssetOfBtc;
        }

        /** Method to set {@link #totalAssetOfBtc}
         * @param totalAssetOfBtc: total asset of Bitcoin
         * @throws IllegalArgumentException when total asset of Bitcoin value is less than 0
         * **/
        public void setTotalAssetOfBtc(double totalAssetOfBtc) {
            if(totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            this.totalAssetOfBtc = totalAssetOfBtc;
        }

        public double getUpdateTime() {
            return updateTime;
        }

        /** Method to set {@link #updateTime}
         * @param updateTime: update time value
         * @throws IllegalArgumentException when update time value is less than 0
         * **/
        public void setUpdateTime(double updateTime) {
            if(updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        public ArrayList<BalanceSpot> getBalancesSpotList() {
            return balanceSpotList;
        }

        public void setBalancesSpotList(ArrayList<BalanceSpot> balanceSpotList) {
            this.balanceSpotList = balanceSpotList;
        }

        public void insertBalanceSpot(BalanceSpot balanceSpot){
            if(!balanceSpotList.contains(balanceSpot))
                balanceSpotList.add(balanceSpot);
        }

        public boolean removeBalanceSpot(BalanceSpot balanceSpot){
            return balanceSpotList.remove(balanceSpot);
        }

        public BalanceSpot getBalanceSpot(int index){
            return balanceSpotList.get(index);
        }

        @Override
        public String toString() {
            return "DataSpot{" +
                    "totalAssetOfBtc=" + totalAssetOfBtc +
                    ", updateTime=" + updateTime +
                    ", balanceSpotList=" + balanceSpotList +
                    '}';
        }

    }

    /**
     *  The {@code BalanceSpot} class is useful to create a balance spot object
     * **/

    public static class BalanceSpot {

        /**
         * {@code asset} is instance that memorizes asset
         * **/
        protected final String asset;

        /**
         * {@code free} is instance that memorizes free amount of asset
         * **/
        protected double free;

        /**
         * {@code locked} is instance that memorizes amount locked for asset
         * **/
        protected double locked;

        /** Constructor to init {@link BalanceSpot} object
         * @param asset: asset
         * @param free: free amount of asset
         * @param locked: amount locked for asset
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public BalanceSpot(String asset, double free, double locked) {
            this.asset = asset;
            if (free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            else
                this.free = free;
            if (locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
            else
                this.locked = locked;
        }

        /**
         * Constructor to init {@link BalanceSpot} object
         *
         * @param balanceSpot: balance spot details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public BalanceSpot(JSONObject balanceSpot) {
            asset = balanceSpot.getString("asset");
            free = balanceSpot.getDouble("free");
            if (free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            locked = balanceSpot.getDouble("locked");
            if (locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
        }

        public double getFree() {
            return free;
        }

        /**
         * Method to get {@link #free} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #free} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getFree(int decimals) {
            return roundValue(free, decimals);
        }


        /**
         * Method to set {@link #free}
         *
         * @param free: free amount of asset
         * @throws IllegalArgumentException when free value is less than 0
         **/
        public void setFree(double free) {
            if (free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            this.free = free;
        }

        public double getLocked() {
            return locked;
        }

        /**
         * Method to get {@link #locked} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #locked} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getLocked(int decimals) {
            return roundValue(locked, decimals);
        }

        /**
         * Method to set {@link #locked}
         *
         * @param locked: amount locked for asset
         * @throws IllegalArgumentException when amount locked value is less than 0
         **/
        public void setLocked(double locked) {
            if (locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
            this.locked = locked;
        }

        @Override
        public String toString() {
            return "BalanceSpot{" +
                    "asset='" + asset + '\'' +
                    ", free=" + free +
                    ", locked=" + locked +
                    '}';
        }

    }

}
