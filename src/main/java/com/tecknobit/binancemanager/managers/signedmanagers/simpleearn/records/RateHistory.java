package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.RateHistory.RateRecord;

/**
 * The {@code RateHistory} class is useful to format a {@code Binance}'s rate history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
 * Get Rate History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class RateHistory extends BinanceRowsList<RateRecord> {

    /**
     * Constructor to init {@link RateHistory} object
     *
     * @param rows : list of the items
     */
    public RateHistory(ArrayList<RateRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link RateHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public RateHistory(int total, ArrayList<RateRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link RateHistory}
     *
     * @param jRateHistory : list details as {@link JSONObject}
     */
    public RateHistory(JSONObject jRateHistory) {
        super(jRateHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new RateRecord((JSONObject) row));
    }

    /**
     * The {@code RateRecord} class is useful to format a {@code Binance}'s rate record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class RateRecord extends BinanceItem {

        /**
         * {@code productId} product identifier
         */
        private final String productId;

        /**
         * {@code asset} of the record
         */
        private final String asset;

        /**
         * {@code annualPercentageRate} annual percentage rate value
         */
        private final double annualPercentageRate;

        /**
         * {@code time} of the record
         */
        private final long time;

        /**
         * Constructor to init a {@link RateRecord} object
         *
         * @param productId:            product identifier
         * @param asset:                asset of the record
         * @param annualPercentageRate: annual percentage rate value
         * @param time:                 time of the record
         */
        public RateRecord(String productId, String asset, double annualPercentageRate, long time) {
            super(null);
            this.productId = productId;
            this.asset = asset;
            this.annualPercentageRate = annualPercentageRate;
            this.time = time;
        }

        /**
         * Constructor to init a {@link RateRecord} object
         *
         * @param jRateRecord: rate record details as {@link JSONObject}
         */
        public RateRecord(JSONObject jRateRecord) {
            super(jRateRecord);
            productId = hItem.getString("productId");
            asset = hItem.getString("asset");
            annualPercentageRate = hItem.getDouble("annualPercentageRate", 0);
            time = hItem.getLong("time", -1);
        }

        /**
         * Method to get {@link #productId} instance <br>
         * No-any params required
         *
         * @return {@link #productId} instance as {@link String}
         */
        public String getProductId() {
            return productId;
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
