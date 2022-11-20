package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginAccountSnapshot} class is useful to obtain and format MarginAccountSnapshot object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 **/

public class MarginAccountSnapshot extends AccountSnapshot {

    /**
     * {@code marginsListData} is instance that memorizes list of {@link MarginData}
     **/
    private ArrayList<MarginData> marginsListData;

    /**
     * Constructor to init {@link MarginAccountSnapshot} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param type:           type of account
     * @param accountDetails: details as {@link JSONObject}
     * @param marginData:     list of {@link MarginData}
     **/
    public MarginAccountSnapshot(int code, String msg, String type, JSONArray accountDetails, ArrayList<MarginData> marginData) {
        super(code, msg, type, accountDetails);
        this.marginsListData = marginData;
    }

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param marginAccount : margin account snapshot details as {@link JSONObject}
     **/
    public MarginAccountSnapshot(JSONObject marginAccount) {
        super(marginAccount, MARGIN);
        marginsListData = new ArrayList<>();
        for (int j = 0; j < snapshotVos.length(); j++)
            marginsListData.add(new MarginData(snapshotVos.getJSONObject(j)));
    }

    /**
     * Method to assemble a {@link UserMarginAsset} list
     *
     * @param jsonAssets: snapshotVos obtain by AccountSnapshot {@code "Binance"} request
     * @return list as {@link ArrayList} of {@link UserMarginAsset}
     **/
    public static ArrayList<UserMarginAsset> assembleUserMarginAssetsList(JSONArray jsonAssets) {
        ArrayList<UserMarginAsset> userMarginAssets = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            userMarginAssets.add(new UserMarginAsset(jsonAssets.getJSONObject(j)));
        return userMarginAssets;
    }

    public ArrayList<MarginData> getDataMarginsList() {
        return marginsListData;
    }

    public void setDataMarginsList(ArrayList<MarginData> marginsListData) {
        this.marginsListData = marginsListData;
    }

    @Override
    public String toString() {
        return "MarginAccountSnapshot{" +
                "marginsListData=" + marginsListData +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     *  The {@code MarginData} class is useful to create margin data object
     * **/

    public static class MarginData extends MarginAccount {

        /**
         * {@code marginLevel} is instance that memorizes margin level value
         **/
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
         * Constructor to init {@link MarginData} object
         *
         * @param totalAssetOfBtc:     total asset of Bitcoin
         * @param totalLiabilityOfBtc: total liability of Bitcoin
         * @param totalNetAssetOfBtc:  total net asset of Bitcoin
         * @param updateTime:          update time value
         * @param userAssetsMargin:    list of {@link UserMarginAsset}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public MarginData(double marginLevel, double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
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

        /**
         * Constructor to init {@link MarginData} object
         *
         * @param marginData: margin data details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public MarginData(JSONObject marginData) {
            super(marginData);
            marginLevel = hMarginAccount.getDouble("marginLevel", 0);
            if (marginLevel < 0)
                throw new IllegalArgumentException("Margin level value cannot be less than 0");
            updateTime = hMarginAccount.getLong("updateTime", 0);
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            userMarginsListAsset = assembleUserMarginAssetsList(hMarginAccount.getJSONArray("userAssets", new JSONArray()));
        }

        public double getMarginLevel() {
            return marginLevel;
        }

        /**
         * Method to get {@link #marginLevel} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #marginLevel} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMarginLevel(int decimals) {
            return roundValue(marginLevel, decimals);
        }

        /**
         * Method to set {@link #marginLevel}
         *
         * @param marginLevel: margin level value
         * @throws IllegalArgumentException when margin level value is less than 0
         **/
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
            return "MarginData{" +
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
     * The {@code UserMarginAsset} class is useful to create a user margin asset type
     **/

    public static class UserMarginAsset extends SpotAccountSnapshot.SpotBalance {

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
