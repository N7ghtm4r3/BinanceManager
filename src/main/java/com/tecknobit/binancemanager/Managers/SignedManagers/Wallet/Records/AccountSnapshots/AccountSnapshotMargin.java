package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code AccountSnapshotMargin} class is useful to obtain and format AccountSnapshotMargin object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshotMargin extends AccountSnapshot{

    /**
     * {@code dataMarginsList} is instance that memorizes list of {@link DataMargin}
     * **/
    private ArrayList<DataMargin> dataMarginsList;

    /** Constructor to init {@link AccountSnapshotMargin} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details in JSON format
     * **/
    public AccountSnapshotMargin(int code, String msg, String type, JSONArray accountDetails) {
        super(code, msg, type, accountDetails);
        dataMarginsList = new ArrayList<>();
        if(accountDetails != null) {
            for (int j = 0; j < accountDetails.length(); j++){
                JSONObject dataRow = accountDetails.getJSONObject(j);
                long updateTime = dataRow.getLong("updateTime");
                dataRow = dataRow.getJSONObject("data");
                double marginLevel = dataRow.getDouble("marginLevel");
                double totalAssetOfBtc = dataRow.getDouble("totalAssetOfBtc");
                double totalLiabilityOfBtc = dataRow.getDouble("totalLiabilityOfBtc");
                double totalNetAssetOfBtc = dataRow.getDouble("totalNetAssetOfBtc");
                dataMarginsList.add(new DataMargin(marginLevel,
                        totalAssetOfBtc,
                        totalLiabilityOfBtc,
                        totalNetAssetOfBtc,
                        updateTime,
                        assembleUserMarginAssetsList(dataRow.getJSONArray("userAssets"))
                ));
            }
        }
    }

    /** Constructor to init {@link AccountSnapshotMargin} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details in JSON format
     * @param dataMargins: list of {@link DataMargin}
     * **/
    public AccountSnapshotMargin(int code, String msg, String type, JSONArray accountDetails, ArrayList<DataMargin> dataMargins) {
        super(code, msg, type, accountDetails);
        this.dataMarginsList = dataMargins;
    }

    /** Method to assemble an UserAssetMargin list
     * @param jsonAssets: accountDetails obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return userAssetMargin list as ArrayList<UserAssetMargin>
     * **/
    public static ArrayList<UserAssetMargin> assembleUserMarginAssetsList(JSONArray jsonAssets) {
        ArrayList<UserAssetMargin> userAssetMargins = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            userAssetMargins.add(UserAssetMargin.assembleUserMarginAsset(jsonAssets.getJSONObject(j)));
        return userAssetMargins;
    }

    public ArrayList<DataMargin> getDataMarginsList() {
        return dataMarginsList;
    }

    public void setDataMarginsList(ArrayList<DataMargin> dataMarginsList) {
        this.dataMarginsList = dataMarginsList;
    }

    @Override
    public String toString() {
        return "AccountSnapshotMargin{" +
                "dataMarginsList=" + dataMarginsList +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", accountDetails=" + accountDetails +
                '}';
    }

    /**
     *  The {@code DataMargin} class is useful to obtain and format DataMargin object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataMargin extends MarginAccount {

        /**
         * {@code marginLevel} is instance that memorizes margin level value
         * **/
        private double marginLevel;

        /**
         * {@code updateTime} is instance that memorizes update time value
         * **/
        private long updateTime;

        /**
         * {@code userAssetMarginsList} is instance that memorizes list of {@link UserAssetMargin}
         * **/
        private ArrayList<UserAssetMargin> userAssetMarginsList;

        /** Constructor to init {@link DataMargin} object
         * @param totalAssetOfBtc: total asset of Bitcoin
         * @param totalLiabilityOfBtc: total liability of Bitcoin
         * @param totalNetAssetOfBtc: total net asset of Bitcoin
         * @param updateTime: update time value
         * @param userAssetsMargin: list of {@link UserAssetMargin}
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public DataMargin(double marginLevel, double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                          long updateTime, ArrayList<UserAssetMargin> userAssetsMargin) {
            super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
            if(marginLevel < 0)
                throw new IllegalArgumentException("Margin level value cannot be less than 0");
            else
                this.marginLevel = marginLevel;
            if(updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            else
                this.updateTime = updateTime;
            this.userAssetMarginsList = userAssetsMargin;
        }

        public double getMarginLevel() {
            return marginLevel;
        }

        /** Method to set {@link #marginLevel}
         * @param marginLevel: margin level value
         * @throws IllegalArgumentException when margin level value is less than 0
         * **/
        public void setMarginLevel(double marginLevel) {
            if(marginLevel < 0)
                throw new IllegalArgumentException("Margin level value cannot be less than 0");
            this.marginLevel = marginLevel;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        /** Method to set {@link #updateTime}
         * @param updateTime: update time value
         * @throws IllegalArgumentException when update time value value is less than 0
         * **/
        public void setUpdateTime(long updateTime) {
            if(updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        public ArrayList<UserAssetMargin> getUserAssetMarginsList() {
            return userAssetMarginsList;
        }

        public void setUserAssetsMargin(ArrayList<UserAssetMargin> userAssetMargins) {
            this.userAssetMarginsList = userAssetMargins;
        }

        public void insertUserAssetMargin(UserAssetMargin userAssetMargin){
            if(!userAssetMarginsList.contains(userAssetMargin))
                userAssetMarginsList.add(userAssetMargin);
        }

        public boolean removeUserAssetMargin(UserAssetMargin userAssetMargin){
            return userAssetMarginsList.remove(userAssetMargin);
        }

        public UserAssetMargin getUserAssetMargin(int index) {
            return userAssetMarginsList.get(index);
        }

        @Override
        public String toString() {
            return "DataMargin{" +
                    "marginLevel=" + marginLevel +
                    ", updateTime=" + updateTime +
                    ", userAssetMarginsList=" + userAssetMarginsList +
                    ", totalAssetOfBtc=" + totalAssetOfBtc +
                    ", totalLiabilityOfBtc=" + totalLiabilityOfBtc +
                    ", totalNetAssetOfBtc=" + totalNetAssetOfBtc +
                    '}';
        }

    }

    /**
     *  The {@code UserAssetMargin} class is useful to obtain and format UserAssetMargin object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class UserAssetMargin extends AccountSnapshotSpot.BalanceSpot {

        /**
         * {@code borrowed} is instance that memorizes amount of borrow from asset
         * **/
        protected double borrowed;

        /**
         * {@code interest} is instance that memorizes amount of interest in asset
         * **/
        protected double interest;

        /**
         * {@code netAsset} is instance that memorizes net asset
         * **/
        protected double netAsset;

        /** Constructor to init {@link UserAssetMargin} object
         * @param asset: asset
         * @param borrowed: amount of borrow from asset
         * @param free: free amount of asset
         * @param interest: amount of interest in asset
         * @param locked: amount locked for asset
         * @param netAsset: net asset
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public UserAssetMargin(String asset, double borrowed, double free, double interest, double locked, double netAsset) {
            super(asset, free, locked);
            if(borrowed < 0)
                throw new IllegalArgumentException("Borrowed value cannot be less than 0");
            else
                this.borrowed = borrowed;
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
            if(netAsset < 0)
                throw new IllegalArgumentException("Net asset value cannot be less than 0");
            else
                this.netAsset = netAsset;
        }

        /** Method to assemble {@link UserAssetMargin} object
         * @param userAsset: obtain from Binance's request
         * @return {@link UserAssetMargin} object
         * **/
        public static UserAssetMargin assembleUserMarginAsset(JSONObject userAsset){
            return new UserAssetMargin(userAsset.getString("asset"),
                    userAsset.getDouble("borrowed"),
                    userAsset.getDouble("free"),
                    userAsset.getDouble("interest"),
                    userAsset.getDouble("locked"),
                    userAsset.getDouble("netAsset")
            );
        }

        public double getBorrowed() {
            return borrowed;
        }

        /** Method to set {@link #borrowed}
         * @param borrowed: amount of borrow from asset
         * @throws IllegalArgumentException when borrowed is less than 0
         * **/
        public void setBorrowed(double borrowed) {
            if(borrowed < 0)
                throw new IllegalArgumentException("Borrowed value cannot be less than 0");
            this.borrowed = borrowed;
        }

        public double getInterest() {
            return interest;
        }

        /** Method to set {@link #interest}
         * @param interest: amount of interest in asset
         * @throws IllegalArgumentException when interest value is less than 0
         * **/
        public void setInterest(double interest) {
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

        public double getNetAsset() {
            return netAsset;
        }

        /** Method to set {@link #netAsset}
         * @param netAsset: net asset
         * @throws IllegalArgumentException when net asset value is less than 0
         * **/
        public void setNetAsset(double netAsset) {
            if(netAsset < 0)
                throw new IllegalArgumentException("Net asset value cannot be less than 0");
            this.netAsset = netAsset;
        }

        @Override
        public String toString() {
            return "UserAssetMargin{" +
                    "borrowed=" + borrowed +
                    ", interest=" + interest +
                    ", netAsset=" + netAsset +
                    ", asset='" + asset + '\'' +
                    ", free=" + free +
                    ", locked=" + locked +
                    '}';
        }

    }

}
