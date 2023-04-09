package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.LoanableAssetsData.LoanableAsset;

/**
 * The {@code LoanableAssetsData} class is useful to create a loanable assets data
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-loanable-assets-data-user_data">
 * Get Loanable Assets Data (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class LoanableAssetsData extends BinanceRowsList<LoanableAsset> {

    /**
     * Constructor to init {@link LoanableAssetsData} object
     *
     * @param total  : number of assets
     * @param assets :  list of the assets
     **/
    public LoanableAssetsData(int total, ArrayList<LoanableAsset> assets) {
        super(total, assets);
    }

    /**
     * Constructor to init {@link LoanableAssetsData}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public LoanableAssetsData(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanableAsset((JSONObject) row));
    }

    /**
     * The {@code LoanableAsset} class is useful to create a loanable asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class LoanableAsset extends BinanceItem {

        /**
         * {@code loanCoin} loan coin of the loanable asset
         **/
        private final String loanCoin;

        /**
         * {@code _7dHourlyInterestRate} 7 hourly interest rate of the loanable asset
         **/
        private final double _7dHourlyInterestRate;

        /**
         * {@code _7dDailyInterestRate} 7 daily interest rate of the loanable asset
         **/
        private final double _7dDailyInterestRate;

        /**
         * {@code _14dHourlyInterestRate} 14 hourly interest rate of the loanable asset
         **/
        private final double _14dHourlyInterestRate;

        /**
         * {@code _14dDailyInterestRate} 14 daily interest rate of the loanable asset
         **/
        private final double _14dDailyInterestRate;

        /**
         * {@code _30dHourlyInterestRate} 30 hourly interest rate of the loanable asset
         **/
        private final double _30dHourlyInterestRate;

        /**
         * {@code _30dDailyInterestRate} 30 daily interest rate of the loanable asset
         **/
        private final double _30dDailyInterestRate;

        /**
         * {@code _90dHourlyInterestRate} 90 hourly interest rate of the loanable asset
         **/
        private final double _90dHourlyInterestRate;

        /**
         * {@code _90dDailyInterestRate} 90 daily interest rate of the loanable asset
         **/
        private final double _90dDailyInterestRate;

        /**
         * {@code _180dHourlyInterestRate} 180 hourly interest rate of the loanable asset
         **/
        private final double _180dHourlyInterestRate;

        /**
         * {@code _180dDailyInterestRate} 180 daily interest rate of the loanable asset
         **/
        private final double _180dDailyInterestRate;

        /**
         * {@code minLimit} min limit of the loanable asset
         **/
        private final double minLimit;

        /**
         * {@code maxLimit} max limit of the loanable asset
         **/
        private final double maxLimit;

        /**
         * {@code vipLevel} vip level of the loanable asset
         **/
        private final int vipLevel;

        /**
         * Constructor to init {@link LoanableAsset}
         *
         * @param loanCoin                : loan coin of the loanable asset
         * @param _7dHourlyInterestRate   : 7 hourly interest rate of the loanable asset
         * @param _7dDailyInterestRate    : 7 daily interest rate of the loanable asset
         * @param _14dHourlyInterestRate  : 14 hourly interest rate of the loanable asset
         * @param _14dDailyInterestRate   : 14 daily interest rate of the loanable asset
         * @param _30dHourlyInterestRate  : 30 hourly interest rate of the loanable asset
         * @param _30dDailyInterestRate   : 30 daily interest rate of the loanable asset
         * @param _90dHourlyInterestRate  : 90 hourly interest rate of the loanable asset
         * @param _90dDailyInterestRate   : 90 daily interest rate of the loanable asset
         * @param _180dHourlyInterestRate : 180 hourly interest rate of the loanable asset
         * @param _180dDailyInterestRate  : 180 daily interest rate of the loanable asset
         * @param minLimit                : min limit of the loanable asset
         * @param maxLimit                : max limit of the loanable asset
         * @param vipLevel                : vip level of the loanable asset
         **/
        public LoanableAsset(String loanCoin, double _7dHourlyInterestRate, double _7dDailyInterestRate,
                             double _14dHourlyInterestRate, double _14dDailyInterestRate, double _30dHourlyInterestRate,
                             double _30dDailyInterestRate, double _90dHourlyInterestRate, double _90dDailyInterestRate,
                             double _180dHourlyInterestRate, double _180dDailyInterestRate, double minLimit,
                             double maxLimit, int vipLevel) {
            super(null);
            this.loanCoin = loanCoin;
            this._7dHourlyInterestRate = _7dHourlyInterestRate;
            this._7dDailyInterestRate = _7dDailyInterestRate;
            this._14dHourlyInterestRate = _14dHourlyInterestRate;
            this._14dDailyInterestRate = _14dDailyInterestRate;
            this._30dHourlyInterestRate = _30dHourlyInterestRate;
            this._30dDailyInterestRate = _30dDailyInterestRate;
            this._90dHourlyInterestRate = _90dHourlyInterestRate;
            this._90dDailyInterestRate = _90dDailyInterestRate;
            this._180dHourlyInterestRate = _180dHourlyInterestRate;
            this._180dDailyInterestRate = _180dDailyInterestRate;
            this.minLimit = minLimit;
            this.maxLimit = maxLimit;
            this.vipLevel = vipLevel;
        }

        /**
         * Constructor to init {@link LoanableAsset}
         *
         * @param jLoanableAsset : loanable asset details as {@link JSONObject}
         **/
        public LoanableAsset(JSONObject jLoanableAsset) {
            super(jLoanableAsset);
            loanCoin = hItem.getString("loanCoin");
            _7dHourlyInterestRate = hItem.getDouble("_7dHourlyInterestRate", 0);
            _7dDailyInterestRate = hItem.getDouble("_7dDailyInterestRate", 0);
            _14dHourlyInterestRate = hItem.getDouble("_14dHourlyInterestRate", 0);
            _14dDailyInterestRate = hItem.getDouble("_14dDailyInterestRate", 0);
            _30dHourlyInterestRate = hItem.getDouble("_30dHourlyInterestRate", 0);
            _30dDailyInterestRate = hItem.getDouble("_30dDailyInterestRate", 0);
            _90dHourlyInterestRate = hItem.getDouble("_90dHourlyInterestRate", 0);
            _90dDailyInterestRate = hItem.getDouble("_90dDailyInterestRate", 0);
            _180dHourlyInterestRate = hItem.getDouble("_180dHourlyInterestRate", 0);
            _180dDailyInterestRate = hItem.getDouble("_180dDailyInterestRate", 0);
            minLimit = hItem.getDouble("minLimit", 0);
            maxLimit = hItem.getDouble("maxLimit", 0);
            vipLevel = hItem.getInt("vipLevel", 0);
        }

        /**
         * Method to get {@link #loanCoin} instance <br>
         * No-any params required
         *
         * @return {@link #loanCoin} instance as {@link String}
         **/
        public String getLoanCoin() {
            return loanCoin;
        }

        /**
         * Method to get {@link #_7dHourlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_7dHourlyInterestRate} instance as double
         **/
        public double get7dHourlyInterestRate() {
            return _7dHourlyInterestRate;
        }

        /**
         * Method to get {@link #_7dHourlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_7dHourlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get7dHourlyInterestRate(int decimals) {
            return roundValue(_7dHourlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_7dDailyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_7dDailyInterestRate} instance as double
         **/
        public double get7dDailyInterestRate() {
            return _7dDailyInterestRate;
        }

        /**
         * Method to get {@link #_7dDailyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_7dDailyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get7dDailyInterestRate(int decimals) {
            return roundValue(_7dDailyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_14dHourlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_14dHourlyInterestRate} instance as double
         **/
        public double get14dHourlyInterestRate() {
            return _14dHourlyInterestRate;
        }

        /**
         * Method to get {@link #_14dHourlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_14dHourlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get14dHourlyInterestRate(int decimals) {
            return roundValue(_14dHourlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_14dDailyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_14dDailyInterestRate} instance as double
         **/
        public double get14dDailyInterestRate() {
            return _14dDailyInterestRate;
        }

        /**
         * Method to get {@link #_14dDailyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_14dDailyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get14dDailyInterestRate(int decimals) {
            return roundValue(_14dDailyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_30dHourlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_30dHourlyInterestRate} instance as double
         **/
        public double get30dHourlyInterestRate() {
            return _30dHourlyInterestRate;
        }

        /**
         * Method to get {@link #_30dHourlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_30dHourlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get30dHourlyInterestRate(int decimals) {
            return roundValue(_30dHourlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_30dDailyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_30dDailyInterestRate} instance as double
         **/
        public double get30dDailyInterestRate() {
            return _30dDailyInterestRate;
        }

        /**
         * Method to get {@link #_30dDailyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_30dDailyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get30dDailyInterestRate(int decimals) {
            return roundValue(_30dDailyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_90dHourlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_90dHourlyInterestRate} instance as double
         **/
        public double get90dHourlyInterestRate() {
            return _90dHourlyInterestRate;
        }

        /**
         * Method to get {@link #_90dHourlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_90dHourlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get90dHourlyInterestRate(int decimals) {
            return roundValue(_90dHourlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_90dDailyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_90dDailyInterestRate} instance as double
         **/
        public double get90dDailyInterestRate() {
            return _90dDailyInterestRate;
        }

        /**
         * Method to get {@link #_90dDailyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_90dDailyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get90dDailyInterestRate(int decimals) {
            return roundValue(_90dDailyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_180dHourlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_180dHourlyInterestRate} instance as double
         **/
        public double get180dHourlyInterestRate() {
            return _180dHourlyInterestRate;
        }

        /**
         * Method to get {@link #_180dHourlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_180dHourlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get180dHourlyInterestRate(int decimals) {
            return roundValue(_180dHourlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_180dDailyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_180dDailyInterestRate} instance as double
         **/
        public double get180dDailyInterestRate() {
            return _180dDailyInterestRate;
        }

        /**
         * Method to get {@link #_180dDailyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_180dDailyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get180dDailyInterestRate(int decimals) {
            return roundValue(_180dDailyInterestRate, decimals);
        }

        /**
         * Method to get {@link #minLimit} instance <br>
         * No-any params required
         *
         * @return {@link #minLimit} instance as double
         **/
        public double getMinLimit() {
            return minLimit;
        }

        /**
         * Method to get {@link #minLimit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minLimit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMinLimit(int decimals) {
            return roundValue(minLimit, decimals);
        }

        /**
         * Method to get {@link #maxLimit} instance <br>
         * No-any params required
         *
         * @return {@link #maxLimit} instance as double
         **/
        public double getMaxLimit() {
            return maxLimit;
        }

        /**
         * Method to get {@link #maxLimit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maxLimit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMaxLimit(int decimals) {
            return roundValue(maxLimit, decimals);
        }

        /**
         * Method to get {@link #vipLevel} instance <br>
         * No-any params required
         *
         * @return {@link #vipLevel} instance as int
         **/
        public int getVipLevel() {
            return vipLevel;
        }

    }

}
