package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.SpotBalance;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginAccountSnapshot} class is useful to format a {@code "Binance"}'s margin account snapshot
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * Daily Account Snapshot (USER_DATA)</a>
 * @see AccountSnapshot
 **/
public class MarginAccountSnapshot extends AccountSnapshot {

    /**
     * {@code marginData} is instance that memorizes list of {@link MarginData}
     **/
    private ArrayList<MarginData> marginData;

    /**
     * Constructor to init {@link MarginAccountSnapshot} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param accountDetails: details as {@link JSONObject}
     * @param marginData:     list of {@link MarginData}
     **/
    public MarginAccountSnapshot(int code, String msg, JSONArray accountDetails, ArrayList<MarginData> marginData) {
        super(code, msg, AccountType.MARGIN, accountDetails);
        this.marginData = marginData;
    }

    /**
     * Constructor to init {@link MarginAccountSnapshot} object
     *
     * @param marginAccount : margin account snapshot details as {@link JSONObject}
     **/
    public MarginAccountSnapshot(JSONObject marginAccount) {
        super(marginAccount, AccountType.MARGIN);
        marginData = new ArrayList<>();
        for (int j = 0; j < snapshotVos.length(); j++)
            marginData.add(new MarginData(snapshotVos.getJSONObject(j)));
    }

    /**
     * Method to assemble a {@link UserMarginAsset} list
     *
     * @param jsonAssets: snapshotVos obtain by AccountSnapshot {@code "Binance"} request
     * @return list as {@link ArrayList} of {@link UserMarginAsset}
     **/
    // TODO: 22/11/2022 CHECK TO REMOVE OR MODIFY
    @Returner
    public static ArrayList<UserMarginAsset> assembleUserMarginAssetsList(JSONArray jsonAssets) {
        ArrayList<UserMarginAsset> userMarginAssets = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            userMarginAssets.add(new UserMarginAsset(jsonAssets.getJSONObject(j)));
        return userMarginAssets;
    }

    /**
     * Method to get {@link #marginData} instance <br>
     * Any params required
     *
     * @return {@link #marginData} instance as {@link ArrayList} of {@link MarginData}
     **/
    public ArrayList<MarginData> getDataMarginsList() {
        return marginData;
    }

    /**
     * Method to set {@link #marginData} instance <br>
     *
     * @param marginsListData: list of {@link MarginData} to set
     **/
    public void setDataMarginsList(ArrayList<MarginData> marginsListData) {
        this.marginData = marginsListData;
    }

    /**
     * Method to add a margin data  to {@link #marginData}
     *
     * @param marginData: margin data to add
     **/
    public void insertMarginData(MarginData marginData) {
        if (!this.marginData.contains(marginData))
            this.marginData.add(marginData);
    }

    /**
     * Method to remove a margin data  from {@link #marginData}
     *
     * @param marginData: margin data  to remove
     * @return result of operation as boolean
     **/
    public boolean removeMarginData(MarginData marginData) {
        return this.marginData.remove(marginData);
    }

    /**
     * Method to get a margin data from {@link #marginData} list
     *
     * @param index: index to fetch the futures data
     * @return margin data as {@link MarginData}
     **/
    public MarginData getMarginData(int index) {
        return marginData.get(index);
    }

    /**
     * The {@code MarginData} class is useful to format a margin data
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see MarginAccount
     **/
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
         * {@code userMarginAssets} is instance that memorizes list of {@link UserMarginAsset}
         **/
        private ArrayList<UserMarginAsset> userMarginAssets;

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
            this.userMarginAssets = userAssetsMargin;
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
            userMarginAssets = assembleUserMarginAssetsList(hMarginAccount.getJSONArray("userAssets",
                    new JSONArray()));
        }

        /**
         * Method to get {@link #marginLevel} instance <br>
         * Any params required
         *
         * @return {@link #marginLevel} instance as double
         **/
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

        /**
         * Method to get {@link #updateTime} instance <br>
         * Any params required
         *
         * @return {@link #updateTime} instance as long
         **/
        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * Method to set {@link #updateTime}
         *
         * @param updateTime: update time value
         * @throws IllegalArgumentException when update time value is less than 0
         **/
        public void setUpdateTime(long updateTime) {
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        /**
         * Method to get {@link #updateTime} instance <br>
         * Any params required
         *
         * @return {@link #updateTime} instance as {@link Date}
         **/
        public Date getUpdateDate() {
            return TimeFormatter.getDate(updateTime);
        }

        /**
         * Method to get {@link #userMarginAssets} instance <br>
         * Any params required
         *
         * @return {@link #userMarginAssets} instance as {@link ArrayList} of {@link UserMarginAsset}
         **/
        public ArrayList<UserMarginAsset> getUserAssetMarginsList() {
            return userMarginAssets;
        }

        /**
         * Method to set {@link #userMarginAssets} instance <br>
         *
         * @param userMarginAssets: list of {@link UserMarginAsset} to set
         **/
        public void setUserAssetsMargin(ArrayList<UserMarginAsset> userMarginAssets) {
            this.userMarginAssets = userMarginAssets;
        }

        /**
         * Method to add a margin asset  to {@link #userMarginAssets}
         *
         * @param userMarginAsset: margin asset to add
         **/
        public void insertUserAssetMargin(UserMarginAsset userMarginAsset) {
            if (!userMarginAssets.contains(userMarginAsset))
                userMarginAssets.add(userMarginAsset);
        }

        /**
         * Method to remove a margin asset  from {@link #userMarginAssets}
         *
         * @param userMarginAsset: margin data  to remove
         * @return result of operation as boolean
         **/
        public boolean removeUserAssetMargin(UserMarginAsset userMarginAsset) {
            return userMarginAssets.remove(userMarginAsset);
        }

        /**
         * Method to get a margin asset from {@link #userMarginAssets} list
         *
         * @param index: index to fetch the futures data
         * @return margin asset as {@link UserMarginAsset}
         **/
        public UserMarginAsset getUserAssetMargin(int index) {
            return userMarginAssets.get(index);
        }

    }

    /**
     * The {@code UserMarginAsset} class is useful to create a user margin asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see SpotBalance
     **/
    public static class UserMarginAsset extends SpotBalance {

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

        /**
         * Method to get {@link #borrowed} instance <br>
         * Any params required
         *
         * @return {@link #borrowed} instance as double
         **/
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

        /**
         * Method to get {@link #interest} instance <br>
         * Any params required
         *
         * @return {@link #interest} instance as double
         **/
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

        /**
         * Method to get {@link #netAsset} instance <br>
         * Any params required
         *
         * @return {@link #netAsset} instance as double
         **/
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

    }

}
