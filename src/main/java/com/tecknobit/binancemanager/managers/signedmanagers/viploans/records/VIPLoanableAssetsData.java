package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.loan.LoanAssetStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.LoanableAssetsData;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanableAssetsData.VIPLoanableAsset;

/**
 * The {@code VIPLoanableAssetsData} class is useful to create a vip loanable assets data
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-loanable-assets-data-user_data-2">
 * Get Loanable Assets Data (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class VIPLoanableAssetsData extends BinanceRowsList<VIPLoanableAsset> {

    /**
     * Constructor to init {@link LoanableAssetsData} object
     *
     * @param total  : number of assets
     * @param assets :  list of the assets
     */
    public VIPLoanableAssetsData(int total, ArrayList<VIPLoanableAsset> assets) {
        super(total, assets);
    }

    /**
     * Constructor to init {@link LoanableAssetsData}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public VIPLoanableAssetsData(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new VIPLoanableAsset((JSONObject) row));
    }

    /**
     * The {@code VIPLoanableAsset} class is useful to create a VIP loanable asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see LoanAssetStructure
     */
    public static class VIPLoanableAsset extends LoanAssetStructure {

        /**
         * {@code _30dYearlyInterestRate} 30 yearly interest rate of the loanable asset
         */
        private final double _30dYearlyInterestRate;

        /**
         * {@code _60dDailyInterestRate} 60 daily interest rate of the loanable asset
         */
        private final double _60dDailyInterestRate;

        /**
         * {@code _60dYearlyInterestRate} 60 yearly interest rate of the loanable asset
         */
        private final double _60dYearlyInterestRate;

        /**
         * Constructor to init a {@link LoanableAssetsData.LoanableAsset} object
         *
         * @param loanCoin:               {@code loanCoin}
         * @param _30dDailyInterestRate:  30 daily interest rate of the loanable asset
         * @param _30dYearlyInterestRate: 30 yearly interest rate of the loanable asset
         * @param _60dDailyInterestRate:  60 daily interest rate of the loanable asset
         * @param _60dYearlyInterestRate: 60 yearly interest rate of the loanable asset
         * @param minLimit:               min limit of the loanable asset
         * @param maxLimit:               max limit of the loanable asset
         * @param vipLevel:               vip level of the loanable asset
         */
        public VIPLoanableAsset(String loanCoin, double _30dDailyInterestRate, double _30dYearlyInterestRate,
                                double _60dDailyInterestRate, double _60dYearlyInterestRate, double minLimit,
                                double maxLimit, int vipLevel) {
            super(null);
            this._30dYearlyInterestRate = _30dYearlyInterestRate;
            this._60dDailyInterestRate = _60dDailyInterestRate;
            this._60dYearlyInterestRate = _60dYearlyInterestRate;
        }

        /**
         * Constructor to init {@link LoanableAssetsData.LoanableAsset}
         *
         * @param jLoanableAsset : loanable asset details as {@link JSONObject}
         */
        public VIPLoanableAsset(JSONObject jLoanableAsset) {
            super(jLoanableAsset);
            _30dYearlyInterestRate = hItem.getDouble("_30dYearlyInterestRate", 0);
            _60dDailyInterestRate = hItem.getDouble("_60dDailyInterestRate", 0);
            _60dYearlyInterestRate = hItem.getDouble("_60dYearlyInterestRate", 0);
        }

        /**
         * Method to get {@link #_30dYearlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_30dYearlyInterestRate} instance as double
         */
        public double get30dYearlyInterestRate() {
            return _30dYearlyInterestRate;
        }

        /**
         * Method to get {@link #_30dYearlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_30dYearlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double get30dYearlyInterestRate(int decimals) {
            return roundValue(_30dYearlyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_60dDailyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_60dDailyInterestRate} instance as double
         */
        public double get60dDailyInterestRate() {
            return _60dDailyInterestRate;
        }

        /**
         * Method to get {@link #_60dDailyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_60dDailyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double get60dDailyInterestRate(int decimals) {
            return roundValue(_60dDailyInterestRate, decimals);
        }

        /**
         * Method to get {@link #_60dYearlyInterestRate} instance <br>
         * No-any params required
         *
         * @return {@link #_60dYearlyInterestRate} instance as double
         */
        public double get60dYearlyInterestRate() {
            return _60dYearlyInterestRate;
        }

        /**
         * Method to get {@link #_60dYearlyInterestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_60dYearlyInterestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double get60dYearlyInterestRate(int decimals) {
            return roundValue(_60dYearlyInterestRate, decimals);
        }

    }

}
