package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code AccountSnapshotMargin} class is useful to obtain and format AccountSnapshotMargin object
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshotMargin extends AccountSnapshot{

    /**
     * {@code dataMarginsList} is instance that memorizes list of {@link DataMargin}
     * **/
    private ArrayList<DataMargin> dataMarginsList;

    /**
     * Constructor to init {@link AccountSnapshotMargin} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param type:           type of account
     * @param accountDetails: details as {@link JSONObject}
     **/
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

    /**
     * Constructor to init {@link AccountSnapshotMargin} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param type:           type of account
     * @param accountDetails: details as {@link JSONObject}
     * @param dataMargins:    list of {@link DataMargin}
     **/
    public AccountSnapshotMargin(int code, String msg, String type, JSONArray accountDetails, ArrayList<DataMargin> dataMargins) {
        super(code, msg, type, accountDetails);
        this.dataMarginsList = dataMargins;
    }

    /**
     * Method to assemble an UserMarginAsset list
     *
     * @param jsonAssets: accountDetails obtain by AccountSnapshot Binance request
     * @return userAssetMargin list as ArrayList<UserMarginAsset>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     **/
    public static ArrayList<UserMarginAsset> assembleUserMarginAssetsList(JSONArray jsonAssets) {
        ArrayList<UserMarginAsset> userMarginAssets = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            userMarginAssets.add(new UserMarginAsset(jsonAssets.getJSONObject(j)));
        return userMarginAssets;
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataMargin extends MarginAccount {

        /**
         * {@code marginLevel} is instance that memorizes margin level value
         * **/
        private double marginLevel;

        /**
         * {@code updateTime} is instance that memorizes update time value
         **/
        private long updateTime;

        /**
         * {@code userMarginsListAsset} is instance that memorizes list of {@link UserMarginAsset}
         **/
        private ArrayList<UserMarginAsset> userMarginsListAsset;

        /**
         * Constructor to init {@link DataMargin} object
         *
         * @param totalAssetOfBtc:     total asset of Bitcoin
         * @param totalLiabilityOfBtc: total liability of Bitcoin
         * @param totalNetAssetOfBtc:  total net asset of Bitcoin
         * @param updateTime:          update time value
         * @param userAssetsMargin:    list of {@link UserMarginAsset}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public DataMargin(double marginLevel, double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                          long updateTime, ArrayList<UserMarginAsset> userAssetsMargin) {
            super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
            if (marginLevel < 0)
                throw new IllegalArgumentException("Margin level value cannot be less than 0");
            else
                this.marginLevel = marginLevel;
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            else
                this.updateTime = updateTime;
            this.userMarginsListAsset = userAssetsMargin;
        }

        public double getMarginLevel() {
            return marginLevel;
        }

        /** Method to set {@link #marginLevel}
         * @param marginLevel: margin level value
         * @throws IllegalArgumentException when margin level value is less than 0
         * **/
        public void setMarginLevel(double marginLevel) {
            if (marginLevel < 0)
                throw new IllegalArgumentException("Margin level value cannot be less than 0");
            this.marginLevel = marginLevel;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * Method to set {@link #updateTime}
         *
         * @param updateTime: update time value
         * @throws IllegalArgumentException when update time value value is less than 0
         **/
        public void setUpdateTime(long updateTime) {
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        public ArrayList<UserMarginAsset> getUserAssetMarginsList() {
            return userMarginsListAsset;
        }

        public void setUserAssetsMargin(ArrayList<UserMarginAsset> userMarginAssets) {
            this.userMarginsListAsset = userMarginAssets;
        }

        public void insertUserAssetMargin(UserMarginAsset userMarginAsset) {
            if (!userMarginsListAsset.contains(userMarginAsset))
                userMarginsListAsset.add(userMarginAsset);
        }

        public boolean removeUserAssetMargin(UserMarginAsset userMarginAsset) {
            return userMarginsListAsset.remove(userMarginAsset);
        }

        public UserMarginAsset getUserAssetMargin(int index) {
            return userMarginsListAsset.get(index);
        }

        @Override
        public String toString() {
            return "DataMargin{" +
                    "marginLevel=" + marginLevel +
                    ", updateTime=" + updateTime +
                    ", userMarginsListAsset=" + userMarginsListAsset +
                    ", totalAssetOfBtc=" + totalAssetOfBtc +
                    ", totalLiabilityOfBtc=" + totalLiabilityOfBtc +
                    ", totalNetAssetOfBtc=" + totalNetAssetOfBtc +
                    '}';
        }

    }

    /**
     * The {@code UserMarginAsset} class is useful to obtain and format UserMarginAsset object
     *
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     **/

    public static class UserMarginAsset extends AccountSnapshotSpot.BalanceSpot {

        /**
         * {@code borrowed} is instance that memorizes amount of borrow from asset
         **/
        protected double borrowed;

        /**
         * {@code interest} is instance that memorizes amount of interest in asset
         **/
        protected double interest;

        /**
         * {@code netAsset} is instance that memorizes net asset
         **/
        protected double netAsset;

        /**
         * Constructor to init {@link UserMarginAsset} object
         *
         * @param asset:    asset
         * @param borrowed: amount of borrow from asset
         * @param free:     free amount of asset
         * @param interest: amount of interest in asset
         * @param locked:   amount locked for asset
         * @param netAsset: net asset
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public UserMarginAsset(String asset, double borrowed, double free, double interest, double locked, double netAsset) {
            super(asset, free, locked);
            if (borrowed < 0)
                throw new IllegalArgumentException("Borrowed value cannot be less than 0");
            else
                this.borrowed = borrowed;
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
            if (netAsset < 0)
                throw new IllegalArgumentException("Net asset value cannot be less than 0");
            else
                this.netAsset = netAsset;
        }

        /**
         * Constructor to init {@link UserMarginAsset} object
         *
         * @param marginAsset: margin asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public UserMarginAsset(JSONObject marginAsset) {
            super(marginAsset);
            borrowed = marginAsset.getDouble("borrowed");
            if (borrowed < 0)
                throw new IllegalArgumentException("Borrowed value cannot be less than 0");
            interest = marginAsset.getDouble("interest");
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            netAsset = marginAsset.getDouble("netAsset");
            if (netAsset < 0)
                throw new IllegalArgumentException("Net asset value cannot be less than 0");
        }

        public double getBorrowed() {
            return borrowed;
        }

        /**
         * Method to get {@link #borrowed} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #borrowed} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getBorrowed(int decimals) {
            return roundValue(borrowed, decimals);
        }


        /**
         * Method to set {@link #borrowed}
         *
         * @param borrowed: amount of borrow from asset
         * @throws IllegalArgumentException when borrowed is less than 0
         **/
        public void setBorrowed(double borrowed) {
            if (borrowed < 0)
                throw new IllegalArgumentException("Borrowed value cannot be less than 0");
            this.borrowed = borrowed;
        }

        public double getInterest() {
            return interest;
        }

        /**
         * Method to get {@link #interest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getInterest(int decimals) {
            return roundValue(interest, decimals);
        }

        /**
         * Method to set {@link #interest}
         *
         * @param interest: amount of interest in asset
         * @throws IllegalArgumentException when interest value is less than 0
         **/
        public void setInterest(double interest) {
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

        public double getNetAsset() {
            return netAsset;
        }

        /**
         * Method to get {@link #netAsset} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #netAsset} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getNetAsset(int decimals) {
            return roundValue(netAsset, decimals);
        }

        /**
         * Method to set {@link #netAsset}
         *
         * @param netAsset: net asset
         * @throws IllegalArgumentException when net asset value is less than 0
         **/
        public void setNetAsset(double netAsset) {
            if (netAsset < 0)
                throw new IllegalArgumentException("Net asset value cannot be less than 0");
            this.netAsset = netAsset;
        }

        @Override
        public String toString() {
            return "UserMarginAsset{" +
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
