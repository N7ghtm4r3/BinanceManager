package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralInterestsHistory.CrossCollateralInterest;

/**
 * The {@code CrossCollateralInterestsHistory} class is useful to format a cross collateral interest history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-interest-history-user_data">
 * Cross-Collateral Interest History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CrossCollateralInterestsHistory extends BinanceRowsList<CrossCollateralInterest> {

    /**
     * Constructor to init {@link CrossCollateralInterestsHistory} object
     *
     * @param total     : number of interests
     * @param interests :  list of the interests
     */
    public CrossCollateralInterestsHistory(int total, ArrayList<CrossCollateralInterest> interests) {
        super(total, interests);
    }

    /**
     * Constructor to init {@link CrossCollateralInterestsHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public CrossCollateralInterestsHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralInterest((JSONObject) row));
    }

    /**
     * The {@code CrossCollateralInterest} class is useful to format a cross collateral interest
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class CrossCollateralInterest extends BinanceItem {

        /**
         * {@code collateralCoin} collateral coin of the cross collateral interest
         */
        private final String collateralCoin;

        /**
         * {@code interestCoin} interest coin of the cross collateral interest
         */
        private final String interestCoin;

        /**
         * {@code interest} of the cross collateral interest
         */
        private final double interest;

        /**
         * {@code interestFreeLimitUsed} interest free limit used of the cross collateral interest
         */
        private final double interestFreeLimitUsed;

        /**
         * {@code principalForInterest} principal for interest of the cross collateral interest
         */
        private final double principalForInterest;

        /**
         * {@code interestRate} interest rate of the cross collateral interest
         */
        private final double interestRate;

        /**
         * {@code time} of the cross collateral interest
         */
        private final long time;

        /**
         * Constructor to init {@link CrossCollateralInterest}
         *
         * @param collateralCoin        : collateral coin of the cross collateral interest
         * @param interestCoin          : interest coin of the cross collateral interest
         * @param interest              : interest of the cross collateral interest
         * @param interestFreeLimitUsed : interest free limit used of the cross collateral interest
         * @param principalForInterest  : principal for interest of the cross collateral interest
         * @param interestRate          : interest rate of the cross collateral interest
         * @param time                  : time of the cross collateral interest
         */
        public CrossCollateralInterest(String collateralCoin, String interestCoin, double interest,
                                       double interestFreeLimitUsed, double principalForInterest, double interestRate,
                                       long time) {
            super(null);
            this.collateralCoin = collateralCoin;
            this.interestCoin = interestCoin;
            this.interest = interest;
            this.interestFreeLimitUsed = interestFreeLimitUsed;
            this.principalForInterest = principalForInterest;
            this.interestRate = interestRate;
            this.time = time;
        }

        /**
         * Constructor to init {@link CrossCollateralInterest}
         *
         * @param jCrossCollateralInterest : cross collateral interest details as {@link JSONObject}
         */
        public CrossCollateralInterest(JSONObject jCrossCollateralInterest) {
            super(jCrossCollateralInterest);
            collateralCoin = hItem.getString("collateralCoin");
            interestCoin = hItem.getString("interestCoin");
            interest = hItem.getDouble("interest", 0);
            interestFreeLimitUsed = hItem.getDouble("interestFreeLimitUsed", 0);
            principalForInterest = hItem.getDouble("principalForInterest", 0);
            interestRate = hItem.getDouble("interestRate", 0);
            time = hItem.getLong("time", 0);
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
         * Method to get {@link #interestCoin} instance <br>
         * No-any params required
         *
         * @return {@link #interestCoin} instance as {@link String}
         */
        public String getInterestCoin() {
            return interestCoin;
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
         * Method to get {@link #interestRate} instance <br>
         * No-any params required
         *
         * @return {@link #interestRate} instance as double
         */
        public double getInterestRate() {
            return interestRate;
        }

        /**
         * Method to get {@link #interestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInterestRate(int decimals) {
            return roundValue(interestRate, decimals);
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as long
         */
        public long getTime() {
            return time;
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as {@link Date}
         */
        public Date getDate() {
            return TimeFormatter.getDate(time);
        }

    }

}
