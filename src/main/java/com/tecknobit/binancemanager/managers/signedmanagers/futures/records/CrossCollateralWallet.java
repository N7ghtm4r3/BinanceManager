package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CrossCollateralWallet} class is useful to create a cross collateral wallet
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-wallet-v2-user_data">
 * Cross-Collateral Wallet V2 (USER_DATA)</a>
 * @see BinanceItem
 */
public class CrossCollateralWallet extends BinanceItem {

    /**
     * {@code totalCrossCollateral} total cross collateral of the cross collateral wallet
     */
    private final double totalCrossCollateral;

    /**
     * {@code totalBorrowed} total borrowed of the cross collateral wallet
     */
    private final double totalBorrowed;

    /**
     * {@code totalInterest} total interest of the cross collateral wallet
     */
    private final double totalInterest;

    /**
     * {@code interestFreeLimit} interest free limit of the cross collateral wallet
     */
    private final double interestFreeLimit;

    /**
     * {@code asset} of the cross collateral wallet
     */
    private final String asset;

    /**
     * {@code crossCollaterals} list of {@link CrossCollateral}
     */
    private final ArrayList<CrossCollateral> crossCollaterals;

    /**
     * Constructor to init {@link CrossCollateralWallet} object
     *
     * @param totalCrossCollateral: total cross collateral of the cross collateral wallet
     * @param totalBorrowed: total borrowed of the cross collateral wallet
     * @param totalInterest: total interest of the cross collateral wallet
     * @param interestFreeLimit: interest free limit of the cross collateral wallet
     * @param asset: asset of the cross collateral wallet
     * @param crossCollaterals: list of {@link CrossCollateral}
     */
    public CrossCollateralWallet(double totalCrossCollateral, double totalBorrowed, double totalInterest,
                                 double interestFreeLimit, String asset, ArrayList<CrossCollateral> crossCollaterals) {
        super(null);
        this.totalCrossCollateral = totalCrossCollateral;
        this.totalBorrowed = totalBorrowed;
        this.totalInterest = totalInterest;
        this.interestFreeLimit = interestFreeLimit;
        this.asset = asset;
        this.crossCollaterals = crossCollaterals;
    }

    /**
     * Constructor to init {@link CrossCollateralWallet} object
     *
     * @param jCrossCollateralWallet: cross collateral wallet details as {@link JSONObject}
     */
    public CrossCollateralWallet(JSONObject jCrossCollateralWallet) {
        super(jCrossCollateralWallet);
        totalCrossCollateral = hItem.getDouble("totalCrossCollateral", 0);
        totalBorrowed = hItem.getDouble("totalBorrowed", 0);
        totalInterest = hItem.getDouble("totalInterest", 0);
        interestFreeLimit = hItem.getDouble("interestFreeLimit", 0);
        asset = hItem.getString("asset");
        crossCollaterals = new ArrayList<>();
        for (Object collateral : hItem.fetchList("crossCollaterals"))
            crossCollaterals.add(new CrossCollateral((JSONObject) collateral));
    }

    /**
     * Method to get {@link #totalCrossCollateral} instance <br>
     * No-any params required
     *
     * @return {@link #totalCrossCollateral} instance as double
     */
    public double getTotalCrossCollateral() {
        return totalCrossCollateral;
    }

    /**
     * Method to get {@link #totalCrossCollateral} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalCrossCollateral} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalCrossCollateral(int decimals) {
        return roundValue(totalCrossCollateral, decimals);
    }

    /**
     * Method to get {@link #totalBorrowed} instance <br>
     * No-any params required
     *
     * @return {@link #totalBorrowed} instance as double
     */
    public double getTotalBorrowed() {
        return totalBorrowed;
    }

    /**
     * Method to get {@link #totalBorrowed} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalBorrowed} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalBorrowed(int decimals) {
        return roundValue(totalBorrowed, decimals);
    }

    /**
     * Method to get {@link #totalInterest} instance <br>
     * No-any params required
     *
     * @return {@link #totalInterest} instance as double
     */
    public double getTotalInterest() {
        return totalInterest;
    }

    /**
     * Method to get {@link #totalInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalInterest(int decimals) {
        return roundValue(totalInterest, decimals);
    }

    /**
     * Method to get {@link #interestFreeLimit} instance <br>
     * No-any params required
     *
     * @return {@link #interestFreeLimit} instance as double
     */
    public double getInterestFreeLimit() {
        return interestFreeLimit;
    }

    /**
     * Method to get {@link #interestFreeLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #interestFreeLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getInterestFreeLimit(int decimals) {
        return roundValue(interestFreeLimit, decimals);
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
     * Method to get {@link #crossCollaterals} instance <br>
     * No-any params required
     *
     * @return {@link #crossCollaterals} instance as {@link ArrayList} of {@link CrossCollateral}
     */
    public ArrayList<CrossCollateral> getCrossCollaterals() {
        return crossCollaterals;
    }

    /**
     * The {@code CrossCollateral} class is useful to create a cross collateral
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class CrossCollateral extends BinanceItem {

        /**
         * {@code loanCoin} loan coin of the cross collateral
         */
        private final String loanCoin;

        /**
         * {@code collateralCoin} collateral coin of the cross collateral
         */
        private final String collateralCoin;

        /**
         * {@code locked} of the cross collateral
         */
        private final double locked;

        /**
         * {@code loanAmount} loan amount of the cross collateral
         */
        private final double loanAmount;

        /**
         * {@code currentCollateralRate} current collateral rate of the cross collateral
         */
        private final double currentCollateralRate;

        /**
         * {@code interestFreeLimitUsed} interest free limit used of the cross collateral
         */
        private final double interestFreeLimitUsed;

        /**
         * {@code principalForInterest} principal for interest of the cross collateral
         */
        private final double principalForInterest;

        /**
         * {@code interest} of the cross collateral
         */
        private final double interest;

        /**
         * Constructor to init {@link CrossCollateral} object
         *
         * @param loanCoin: loan coin of the cross collateral
         * @param collateralCoin: collateral coin of the cross collateral
         * @param locked: locked of the cross collateral
         * @param loanAmount: loan amount of the cross collateral
         * @param currentCollateralRate: current collateral rate of the cross collateral
         * @param interestFreeLimitUsed: interest free limit used of the cross collateral
         * @param principalForInterest: principal for interest of the cross collateral
         * @param interest: interest of the cross collateral
         */
        public CrossCollateral(String loanCoin, String collateralCoin, double locked, double loanAmount,
                               double currentCollateralRate, double interestFreeLimitUsed, double principalForInterest,
                               double interest) {
            super(null);
            this.loanCoin = loanCoin;
            this.collateralCoin = collateralCoin;
            this.locked = locked;
            this.loanAmount = loanAmount;
            this.currentCollateralRate = currentCollateralRate;
            this.interestFreeLimitUsed = interestFreeLimitUsed;
            this.principalForInterest = principalForInterest;
            this.interest = interest;
        }

        /**
         * Constructor to init {@link CrossCollateral} object
         *
         * @param jCrossCollateral: cross collateral details as {@link JSONObject}
         */
        public CrossCollateral(JSONObject jCrossCollateral) {
            super(jCrossCollateral);
            loanCoin = hItem.getString("loanCoin");
            collateralCoin = hItem.getString("collateralCoin");
            locked = hItem.getDouble("locked", 0);
            loanAmount = hItem.getDouble("loanAmount", 0);
            currentCollateralRate = hItem.getDouble("currentCollateralRate", 0);
            interestFreeLimitUsed = hItem.getDouble("interestFreeLimitUsed", 0);
            principalForInterest = hItem.getDouble("principalForInterest", 0);
            interest = hItem.getDouble("interest", 0);
        }

        /**
         * Method to get {@link #loanCoin} instance <br>
         * No-any params required
         *
         * @return {@link #loanCoin} instance as {@link String}
         */
        public String getLoanCoin() {
            return loanCoin;
        }

        /**
         * Method to get {@link #collateralCoin} instance <br>
         * No-any params required
         *
         * @return {@link #collateralCoin} instance as {@link String}
         */
        public String getCollateralCoin() {
            return collateralCoin;
        }

        /**
         * Method to get {@link #locked} instance <br>
         * No-any params required
         *
         * @return {@link #locked} instance as double
         */
        public double getLocked() {
            return locked;
        }

        /**
         * Method to get {@link #locked} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #locked} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLocked(int decimals) {
            return roundValue(locked, decimals);
        }

        /**
         * Method to get {@link #loanAmount} instance <br>
         * No-any params required
         *
         * @return {@link #loanAmount} instance as double
         */
        public double getLoanAmount() {
            return loanAmount;
        }

        /**
         * Method to get {@link #loanAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #loanAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLoanAmount(int decimals) {
            return roundValue(loanAmount, decimals);
        }

        /**
         * Method to get {@link #currentCollateralRate} instance <br>
         * No-any params required
         *
         * @return {@link #currentCollateralRate} instance as double
         */
        public double getCurrentCollateralRate() {
            return currentCollateralRate;
        }

        /**
         * Method to get {@link #currentCollateralRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #currentCollateralRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCurrentCollateralRate(int decimals) {
            return roundValue(currentCollateralRate, decimals);
        }

        /**
         * Method to get {@link #interestFreeLimitUsed} instance <br>
         * No-any params required
         *
         * @return {@link #interestFreeLimitUsed} instance as double
         */
        public double getInterestFreeLimitUsed() {
            return interestFreeLimitUsed;
        }

        /**
         * Method to get {@link #interestFreeLimitUsed} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interestFreeLimitUsed} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInterestFreeLimitUsed(int decimals) {
            return roundValue(interestFreeLimitUsed, decimals);
        }

        /**
         * Method to get {@link #principalForInterest} instance <br>
         * No-any params required
         *
         * @return {@link #principalForInterest} instance as double
         */
        public double getPrincipalForInterest() {
            return principalForInterest;
        }

        /**
         * Method to get {@link #principalForInterest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #principalForInterest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPrincipalForInterest(int decimals) {
            return roundValue(principalForInterest, decimals);
        }

        /**
         * Method to get {@link #interest} instance <br>
         * No-any params required
         *
         * @return {@link #interest} instance as double
         */
        public double getInterest() {
            return interest;
        }

        /**
         * Method to get {@link #interest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInterest(int decimals) {
            return roundValue(interest, decimals);
        }

    }

}
