package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records.PortfolioMarginInterestHistory.MarginInterest;

/**
 * The {@code PortfolioMarginInterestHistory} class is useful to create a portfolio margin interest history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-history-user_data">
 * Query Portfolio Margin Interest History(USER_DATA)</a>
 * @see BinanceItem
 */
public class PortfolioMarginInterestHistory extends BinanceRowsList<MarginInterest> {

    /**
     * Constructor to init {@link PortfolioMarginInterestHistory} object
     *
     * @param total           : number of interest
     * @param marginInterests :  list of the interest
     */
    public PortfolioMarginInterestHistory(int total, ArrayList<MarginInterest> marginInterests) {
        super(total, marginInterests);
    }

    /**
     * Constructor to init {@link PortfolioMarginInterestHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public PortfolioMarginInterestHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new MarginInterest((JSONObject) row));
    }

    /**
     * The {@code MarginInterest} class is useful to create a portfolio margin interest
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class MarginInterest extends BinanceItem {

        /**
         * {@code asset} of the margin interest
         */
        private final String asset;

        /**
         * {@code interest} of the margin interest
         */
        private final double interest;

        /**
         * {@code interestAccruedTime} interest accrued time of the margin interest
         */
        private final long interestAccruedTime;

        /**
         * {@code interestRate} interest rate of the margin interest
         */
        private final double interestRate;

        /**
         * {@code principal} of the margin interest
         */
        private final double principal;

        /**
         * {@code type} of the margin interest
         */
        private final String type;

        /**
         * Constructor to init {@link MarginInterest}
         *
         * @param asset               : asset of the margin interest
         * @param interest            : interest of the margin interest
         * @param interestAccruedTime : interest accrued time of the margin interest
         * @param interestRate        : interest rate of the margin interest
         * @param principal           : principal of the margin interest
         * @param type                : type of the margin interest
         */
        public MarginInterest(String asset, double interest, long interestAccruedTime, double interestRate,
                              double principal, String type) {
            super(null);
            this.asset = asset;
            this.interest = interest;
            this.interestAccruedTime = interestAccruedTime;
            this.interestRate = interestRate;
            this.principal = principal;
            this.type = type;
        }

        /**
         * Constructor to init {@link MarginInterest}
         *
         * @param jMarginInterest : margin interest details as {@link JSONObject}
         */
        public MarginInterest(JSONObject jMarginInterest) {
            super(jMarginInterest);
            asset = hItem.getString("asset");
            interest = hItem.getDouble("interest", 0);
            interestAccruedTime = hItem.getLong("interestAccruedTime", 0);
            interestRate = hItem.getDouble("interestRate", 0);
            principal = hItem.getDouble("principal", 0);
            type = hItem.getString("type");
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
         * Method to get {@link #interestAccruedTime} instance <br>
         * No-any params required
         *
         * @return {@link #interestAccruedTime} instance as long
         */
        public long getInterestAccruedTime() {
            return interestAccruedTime;
        }

        /**
         * Method to get {@link #interestAccruedTime} instance <br>
         * No-any params required
         *
         * @return {@link #interestAccruedTime} instance as {@link Date}
         */
        public Date getInterestAccruedDate() {
            return TimeFormatter.getDate(interestAccruedTime);
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
         * Method to get {@link #principal} instance <br>
         * No-any params required
         *
         * @return {@link #principal} instance as double
         */
        public double getPrincipal() {
            return principal;
        }

        /**
         * Method to get {@link #principal} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #principal} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPrincipal(int decimals) {
            return roundValue(principal, decimals);
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         */
        public String getType() {
            return type;
        }

    }

}
