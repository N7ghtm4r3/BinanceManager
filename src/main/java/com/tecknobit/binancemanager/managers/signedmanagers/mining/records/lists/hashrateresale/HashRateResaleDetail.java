package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale.HashRateResaleDetail.HashRateDetail;

/**
 * The {@code HashRateResaleDetail} class is useful to create a hash rate resale detail
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-detail-user_data">
 * Hashrate Resale Detail (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class HashRateResaleDetail extends MiningResponse<HashRateDetail> {

    /**
     * Constructor to init {@link HashRateResaleDetail} object
     *
     * @param data: hash rate resale detail
     */
    public HashRateResaleDetail(HashRateDetail data) {
        super(data);
    }

    /**
     * Constructor to init {@link HashRateResaleDetail} object
     *
     * @param jHashRateResaleDetail: hash rate resale detail as {@link JSONObject}
     */
    public HashRateResaleDetail(JSONObject jHashRateResaleDetail) {
        super(jHashRateResaleDetail);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new HashRateDetail(jData);
        else
            data = null;
    }

    /**
     * The {@code HashRateDetail} class is useful to create a hash rate detail
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see DataListItem
     */
    public static class HashRateDetail extends DataListItem {

        /**
         * {@code profitTransferDetails} profit transfer details list
         */
        private final ArrayList<ProfitTransferDetail> profitTransferDetails;

        /**
         * Constructor to init {@link HashRateDetail} object
         *
         * @param totalNum: total num of the transfer details
         * @param pageSize: page size of the transfer details
         * @param profitTransferDetails: profit transfer details list
         */
        public HashRateDetail(int totalNum, int pageSize, ArrayList<ProfitTransferDetail> profitTransferDetails) {
            super(totalNum, pageSize);
            this.profitTransferDetails = profitTransferDetails;
        }

        /**
         * Constructor to init {@link HashRateDetail} object
         *
         * @param jHashRateDetail: hashrate detail as {@link JSONObject}
         */
        public HashRateDetail(JSONObject jHashRateDetail) {
            super(jHashRateDetail);
            profitTransferDetails = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("profitTransferDetails");
            if (jList != null)
                for (JSONObject detail : jList)
                    profitTransferDetails.add(new ProfitTransferDetail(detail));
        }

        /**
         * Method to get {@link #profitTransferDetails} instance <br>
         * No-any params required
         *
         * @return {@link #profitTransferDetails} instance as {@link ArrayList} of {@link ProfitTransferDetail}
         */
        public ArrayList<ProfitTransferDetail> getProfitTransferDetails() {
            return profitTransferDetails;
        }

        /**
         * The {@code ProfitTransferDetail} class is useful to create a profit transfer detail
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         * @see HashrateResaleStructure
         */
        public static class ProfitTransferDetail extends HashrateResaleStructure {

            /**
             * {@code day} of the profit transfer detail
             */
            private final long day;

            /**
             * {@code amount} of the profit transfer detail
             */
            private final double amount;

            /**
             * {@code coinName} coin name of the profit transfer detail
             */
            private final String coinName;

            /**
             * Constructor to init {@link ProfitTransferDetail} object
             *
             * @param poolUsername: transfer out of subaccount
             * @param toPoolUsername: transfer into subaccount
             * @param algoName: transfer algorithm
             * @param hashRate: transferred hashrate quantity
             * @param day: day of the profit transfer detail
             * @param amount: amount of the profit transfer detail
             * @param coinName: coin name of the profit transfer detail
             */
            public ProfitTransferDetail(String poolUsername, String toPoolUsername, String algoName, long hashRate,
                                        long day, double amount, String coinName) {
                super(poolUsername, toPoolUsername, algoName, hashRate);
                this.day = day;
                this.amount = amount;
                this.coinName = coinName;
            }

            /**
             * Constructor to init {@link ProfitTransferDetail} object
             *
             * @param jProfitTransferDetail: profit transfer detail as {@link JSONObject}
             */
            public ProfitTransferDetail(JSONObject jProfitTransferDetail) {
                super(jProfitTransferDetail);
                day = hItem.getLong("day", 0);
                amount = hItem.getDouble("amount", 0);
                coinName = hItem.getString("coinName");
            }

            /**
             * Method to get {@link #day} instance <br>
             * No-any params required
             *
             * @return {@link #day} instance as long
             */
            public long getDay() {
                return day;
            }

            /**
             * Method to get {@link #day} instance <br>
             * No-any params required
             *
             * @return {@link #day} instance as {@link Date}
             */
            public Date getDayDate() {
                return TimeFormatter.getDate(day);
            }

            /**
             * Method to get {@link #amount} instance <br>
             * No-any params required
             *
             * @return {@link #amount} instance as double
             */
            public double getAmount() {
                return amount;
            }

            /**
             * Method to get {@link #amount} instance
             *
             * @param decimals: number of digits to round final value
             * @return {@link #amount} instance rounded with decimal digits inserted
             * @throws IllegalArgumentException if decimalDigits is negative
             */
            public double getAmount(int decimals) {
                return roundValue(amount, decimals);
            }

            /**
             * Method to get {@link #coinName} instance <br>
             * No-any params required
             *
             * @return {@link #coinName} instance as {@link String}
             */
            public String getCoinName() {
                return coinName;
            }

        }

    }

}
