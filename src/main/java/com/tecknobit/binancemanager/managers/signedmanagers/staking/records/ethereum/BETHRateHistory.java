package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.BETHRateHistory.BETHRate;

/**
 * The {@code BETHRateHistory} class is useful to format a {@code Binance}'s BETH rate history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-beth-rate-history-user_data">
 * Get BETH Rate History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class BETHRateHistory extends BinanceRowsList<BETHRate> {

    /**
     * Constructor to init {@link BETHRateHistory} object
     *
     * @param rows : list of the items
     */
    public BETHRateHistory(ArrayList<BETHRate> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BETHRateHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public BETHRateHistory(int total, ArrayList<BETHRate> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BETHRateHistory}
     *
     * @param jBETHRateHistory : list details as {@link JSONObject}
     */
    public BETHRateHistory(JSONObject jBETHRateHistory) {
        super(jBETHRateHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new BETHRate((JSONObject) row));
    }

    /**
     * The {@code BETHRate} class is useful to format a {@code Binance}'s BETH rate
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class BETHRate extends BinanceItem {

        /**
         * {@code annualPercentageRate} BETH APR
         */
        private final double annualPercentageRate;

        /**
         * {@code exchangeRate} BETH value per 1 WBETH
         */
        private final double exchangeRate;

        /**
         * {@code time} of the rate
         */
        private final long time;

        /**
         * Constructor to init a {@link BETHRate} object
         *
         * @param annualPercentageRate: BETH APR
         * @param exchangeRate:         BETH value per 1 WBETH
         * @param time:                 time of the rate
         */
        public BETHRate(double annualPercentageRate, double exchangeRate, long time) {
            super(null);
            this.annualPercentageRate = annualPercentageRate;
            this.exchangeRate = exchangeRate;
            this.time = time;
        }

        /**
         * Constructor to init a {@link BETHRate} object
         *
         * @param jBETHRate: BETH rate as {@link JSONObject}
         */
        public BETHRate(JSONObject jBETHRate) {
            super(jBETHRate);
            annualPercentageRate = hItem.getDouble("annualPercentageRate", 0);
            exchangeRate = hItem.getDouble("exchangeRate", 0);
            time = hItem.getLong("time", 0);
        }

        /**
         * Method to get {@link #annualPercentageRate} instance <br>
         * No-any params required
         *
         * @return {@link #annualPercentageRate} instance as double
         */
        public double getAnnualPercentageRate() {
            return annualPercentageRate;
        }

        /**
         * Method to get {@link #annualPercentageRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #annualPercentageRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAnnualPercentageRate(int decimals) {
            return roundValue(annualPercentageRate, decimals);
        }

        /**
         * Method to get {@link #exchangeRate} instance <br>
         * No-any params required
         *
         * @return {@link #exchangeRate} instance as double
         */
        public double getExchangeRate() {
            return exchangeRate;
        }

        /**
         * Method to get {@link #exchangeRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #exchangeRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getExchangeRate(int decimals) {
            return roundValue(exchangeRate, decimals);
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
