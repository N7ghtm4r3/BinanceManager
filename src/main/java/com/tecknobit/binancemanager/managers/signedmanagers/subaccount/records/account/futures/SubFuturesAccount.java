package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubFuturesAccount} class is useful to format a sub futures account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
 * Get Detail on Sub-account's Futures Account (For Master Account)</a>
 * @see BinanceItem
 * @see SubFuturesAccountStructure
 */
public class SubFuturesAccount extends SubFuturesAccountStructure {

    /**
     * {@code email} of the sub futures account
     */
    private final String email;

    /**
     * {@code asset} of the sub futures account
     */
    private final String asset;

    /**
     * {@code assets} of the sub futures account
     */
    private final ArrayList<SubFuturesAsset> assets;

    /**
     * {@code canDeposit} whether the sub futures account can deposit
     */
    private final boolean canDeposit;

    /**
     * {@code canTrade} whether the sub futures account can trade
     */
    private final boolean canTrade;

    /**
     * {@code canWithdraw} whether the sub futures account can withdraw
     */
    private final boolean canWithdraw;

    /**
     * {@code feeTier} fee tier of the sub futures account
     */
    private final int feeTier;

    /**
     * {@code updateTime} update time of the sub futures account
     */
    private final long updateTime;

    /**
     * {@code maxWithdrawAmount} max withdraw amount of the sub futures account
     */
    private final double maxWithdrawAmount;

    /**
     * Constructor to init {@link SubFuturesAccount} object
     *
     * @param totalInitialMargin: total initial margin of the sub futures account
     * @param totalMaintenanceMargin: total maintenance margin of the sub futures account
     * @param totalMarginBalance: total margin balance of the sub futures account
     * @param totalOpenOrderInitialMargin: total open order initial margin of the sub futures account
     * @param totalPositionInitialMargin: total position initial margin of the sub futures account
     * @param totalUnrealizedProfit: total unrealized profit of the sub futures account
     * @param totalWalletBalance: total wallet balance of the sub futures account
     * @param email: email of the sub futures account
     * @param asset: asset of the sub futures account
     * @param assets: assets of the sub futures account
     * @param canDeposit: whether the sub futures account can deposit
     * @param canTrade: whether the sub futures account can trade
     * @param canWithdraw: whether the sub futures account can withdraw
     * @param feeTier: fee tier of the sub futures account
     * @param updateTime: update time of the sub futures account
     * @param maxWithdrawAmount: max withdraw amount of the sub futures account
     */
    public SubFuturesAccount(double totalInitialMargin, double totalMaintenanceMargin, double totalMarginBalance,
                             double totalOpenOrderInitialMargin, double totalPositionInitialMargin,
                             double totalUnrealizedProfit, double totalWalletBalance, String email, String asset,
                             ArrayList<SubFuturesAsset> assets, boolean canDeposit, boolean canTrade,
                             boolean canWithdraw, int feeTier, long updateTime, double maxWithdrawAmount) {
        super(totalInitialMargin, totalMaintenanceMargin, totalMarginBalance, totalOpenOrderInitialMargin,
                totalPositionInitialMargin, totalUnrealizedProfit, totalWalletBalance);
        this.email = email;
        this.asset = asset;
        this.assets = assets;
        this.canDeposit = canDeposit;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.feeTier = feeTier;
        this.updateTime = updateTime;
        this.maxWithdrawAmount = maxWithdrawAmount;
    }

    /**
     * Constructor to init {@link SubFuturesAccount} object
     *
     * @param jSubFuturesAccount: sub futures account detail as {@link JSONObject}
     */
    public SubFuturesAccount(JSONObject jSubFuturesAccount) {
        super(jSubFuturesAccount);
        email = hItem.getString("email");
        asset = hItem.getString("asset");
        assets = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("assets");
        if (jList != null)
            for (JSONObject item : jList)
                assets.add(new SubFuturesAsset(item));
        canDeposit = hItem.getBoolean("canDeposit");
        canTrade = hItem.getBoolean("canTrade");
        canWithdraw = hItem.getBoolean("canWithdraw");
        feeTier = hItem.getInt("feeTier", 0);
        maxWithdrawAmount = hItem.getDouble("maxWithdrawAmount", 0);
        updateTime = hItem.getLong("updateTime", 0);
    }

    /**
     * Method to get {@link #email} instance <br>
     * No-any params required
     *
     * @return {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #assets} instance <br>
     * No-any params required
     *
     * @return {@link #assets} instance as {@link ArrayList} of {@link SubFuturesAsset}
     */
    public ArrayList<SubFuturesAsset> getAssets() {
        return assets;
    }

    /**
     * Method to get {@link #canDeposit} instance <br>
     * No-any params required
     *
     * @return {@link #canDeposit} instance as boolean
     */
    public boolean canDeposit() {
        return canDeposit;
    }

    /**
     * Method to get {@link #canTrade} instance <br>
     * No-any params required
     *
     * @return {@link #canTrade} instance as boolean
     */
    public boolean canTrade() {
        return canTrade;
    }

    /**
     * Method to get {@link #canWithdraw} instance <br>
     * No-any params required
     *
     * @return {@link #canWithdraw} instance as boolean
     */
    public boolean canWithdraw() {
        return canWithdraw;
    }

    /**
     * Method to get {@link #feeTier} instance <br>
     * No-any params required
     *
     * @return {@link #feeTier} instance as int
     */
    public int getFeeTier() {
        return feeTier;
    }

    /**
     * Method to get {@link #maxWithdrawAmount} instance <br>
     * No-any params required
     *
     * @return {@link #maxWithdrawAmount} instance as double
     */
    public double getMaxWithdrawAmount() {
        return maxWithdrawAmount;
    }

    /**
     * Method to get {@link #maxWithdrawAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #maxWithdrawAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMaxWithdrawAmount(int decimals) {
        return roundValue(maxWithdrawAmount, decimals);
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     */
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * The {@code SubFuturesAsset} class is useful to format a sub futures asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class SubFuturesAsset extends BinanceItem {

        /**
         * {@code asset} of the sub futures asset
         */
        private final String asset;

        /**
         * {@code initialMargin} initial margin of the sub futures asset
         */
        private final double initialMargin;

        /**
         * {@code maintenanceMargin} maintenance margin of the sub futures asset
         */
        private final double maintenanceMargin;

        /**
         * {@code marginBalance} margin balance of the sub futures asset
         */
        private final double marginBalance;

        /**
         * {@code maxWithdrawAmount} max withdraw amount of the sub futures asset
         */
        private final double maxWithdrawAmount;

        /**
         * {@code openOrderInitialMargin} open order initial margin of the sub futures asset
         */
        private final double openOrderInitialMargin;

        /**
         * {@code positionInitialMargin} position initial margin of the sub futures asset
         */
        private final double positionInitialMargin;

        /**
         * {@code unrealizedProfit} unrealized profit of the sub futures asset
         */
        private final double unrealizedProfit;

        /**
         * {@code walletBalance} wallet balance of the sub futures asset
         */
        private final double walletBalance;

        /**
         * Constructor to init {@link SubFuturesAsset} object
         *
         * @param asset: asset of the sub futures asset
         * @param initialMargin: initial margin of the sub futures asset
         * @param maintenanceMargin: maintenance margin of the sub futures asset
         * @param marginBalance: margin balance of the sub futures asset
         * @param maxWithdrawAmount: max withdraw amount of the sub futures asset
         * @param openOrderInitialMargin: open order initial margin of the sub futures asset
         * @param positionInitialMargin: position initial margin of the sub futures asset
         * @param unrealizedProfit: unrealized profit of the sub futures asset
         * @param walletBalance: wallet balance of the sub futures asset
         */
        public SubFuturesAsset(String asset, double initialMargin, double maintenanceMargin, double marginBalance,
                               double maxWithdrawAmount, double openOrderInitialMargin, double positionInitialMargin,
                               double unrealizedProfit, double walletBalance) {
            super(null);
            this.asset = asset;
            this.initialMargin = initialMargin;
            this.maintenanceMargin = maintenanceMargin;
            this.marginBalance = marginBalance;
            this.maxWithdrawAmount = maxWithdrawAmount;
            this.openOrderInitialMargin = openOrderInitialMargin;
            this.positionInitialMargin = positionInitialMargin;
            this.unrealizedProfit = unrealizedProfit;
            this.walletBalance = walletBalance;
        }

        /**
         * Constructor to init {@link SubFuturesAsset} object
         *
         * @param jSubFuturesAsset: sub futures asset details as {@link JSONObject}
         */
        public SubFuturesAsset(JSONObject jSubFuturesAsset) {
            super(jSubFuturesAsset);
            asset = hItem.getString("asset");
            initialMargin = hItem.getDouble("initialMargin", 0);
            maintenanceMargin = hItem.getDouble("maintenanceMargin", 0);
            marginBalance = hItem.getDouble("marginBalance", 0);
            maxWithdrawAmount = hItem.getDouble("maxWithdrawAmount", 0);
            openOrderInitialMargin = hItem.getDouble("openOrderInitialMargin", 0);
            positionInitialMargin = hItem.getDouble("positionInitialMargin", 0);
            unrealizedProfit = hItem.getDouble("unrealizedProfit", 0);
            walletBalance = hItem.getDouble("walletBalance", 0);
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #initialMargin} instance <br>
         * No-any params required
         *
         * @return {@link #initialMargin} instance as double
         */
        public double getInitialMargin() {
            return initialMargin;
        }

        /**
         * Method to get {@link #initialMargin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #initialMargin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInitialMargin(int decimals) {
            return roundValue(initialMargin, decimals);
        }

        /**
         * Method to get {@link #maintenanceMargin} instance <br>
         * No-any params required
         *
         * @return {@link #maintenanceMargin} instance as double
         */
        public double getMaintenanceMargin() {
            return maintenanceMargin;
        }

        /**
         * Method to get {@link #maintenanceMargin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maintenanceMargin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMaintenanceMargin(int decimals) {
            return roundValue(maintenanceMargin, decimals);
        }

        /**
         * Method to get {@link #marginBalance} instance <br>
         * No-any params required
         *
         * @return {@link #marginBalance} instance as double
         */
        public double getMarginBalance() {
            return marginBalance;
        }

        /**
         * Method to get {@link #marginBalance} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #marginBalance} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMarginBalance(int decimals) {
            return roundValue(marginBalance, decimals);
        }

        /**
         * Method to get {@link #maxWithdrawAmount} instance <br>
         * No-any params required
         *
         * @return {@link #maxWithdrawAmount} instance as double
         */
        public double getMaxWithdrawAmount() {
            return maxWithdrawAmount;
        }

        /**
         * Method to get {@link #maxWithdrawAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maxWithdrawAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMaxWithdrawAmount(int decimals) {
            return roundValue(maxWithdrawAmount, decimals);
        }

        /**
         * Method to get {@link #openOrderInitialMargin} instance <br>
         * No-any params required
         *
         * @return {@link #openOrderInitialMargin} instance as double
         */
        public double getOpenOrderInitialMargin() {
            return openOrderInitialMargin;
        }

        /**
         * Method to get {@link #openOrderInitialMargin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #openOrderInitialMargin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getOpenOrderInitialMargin(int decimals) {
            return roundValue(openOrderInitialMargin, decimals);
        }

        /**
         * Method to get {@link #positionInitialMargin} instance <br>
         * No-any params required
         *
         * @return {@link #positionInitialMargin} instance as double
         */
        public double getPositionInitialMargin() {
            return positionInitialMargin;
        }

        /**
         * Method to get {@link #positionInitialMargin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #positionInitialMargin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPositionInitialMargin(int decimals) {
            return roundValue(positionInitialMargin, decimals);
        }

        /**
         * Method to get {@link #unrealizedProfit} instance <br>
         * No-any params required
         *
         * @return {@link #unrealizedProfit} instance as double
         */
        public double getUnrealizedProfit() {
            return unrealizedProfit;
        }

        /**
         * Method to get {@link #unrealizedProfit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #unrealizedProfit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getUnrealizedProfit(int decimals) {
            return roundValue(unrealizedProfit, decimals);
        }

        /**
         * Method to get {@link #walletBalance} instance <br>
         * No-any params required
         *
         * @return {@link #walletBalance} instance as double
         */
        public double getWalletBalance() {
            return walletBalance;
        }

        /**
         * Method to get {@link #walletBalance} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #walletBalance} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getWalletBalance(int decimals) {
            return roundValue(walletBalance, decimals);
        }

    }

}
