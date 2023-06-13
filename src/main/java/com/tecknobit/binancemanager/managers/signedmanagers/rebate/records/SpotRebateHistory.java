package com.tecknobit.binancemanager.managers.signedmanagers.rebate.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SpotRebateHistory} class is useful to format a spot rebate history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-spot-rebate-history-records-user_data">
 * Get Spot Rebate History Records (USER_DATA)</a>
 * @see BinanceItem
 */
public class SpotRebateHistory extends BinanceItem {

    /**
     * {@code status} of the response
     */
    private final String status;

    /**
     * {@code type} of the response
     */
    private final String type;

    /**
     * {@code code} of the response
     */
    private final int code;

    /**
     * {@code rebate} data
     */
    private final Rebate rebate;

    /**
     * Constructor to init {@link SpotRebateHistory} object
     *
     * @param status: status of the response
     * @param type:   type of the response
     * @param code:   code of the response
     * @param rebate: rebate data
     */
    public SpotRebateHistory(String status, String type, int code, Rebate rebate) {
        super(null);
        this.status = status;
        this.type = type;
        this.code = code;
        this.rebate = rebate;
    }

    /**
     * Constructor to init {@link SpotRebateHistory} object
     *
     * @param jSpotRebateHistory: spot rebate history details as {@link JSONObject}
     */
    public SpotRebateHistory(JSONObject jSpotRebateHistory) {
        super(jSpotRebateHistory);
        status = hItem.getString("status");
        type = hItem.getString("type");
        code = hItem.getInt("code", 0);
        rebate = new Rebate(hItem.getJSONObject("data", new JSONObject()));
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link String}
     */
    public String getStatus() {
        return status;
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

    /**
     * Method to get {@link #code} instance <br>
     * No-any params required
     *
     * @return {@link #code} instance as int
     */
    public int getCode() {
        return code;
    }

    /**
     * Method to get {@link #rebate} instance <br>
     * No-any params required
     *
     * @return {@link #rebate} instance as {@link Rebate}
     */
    public Rebate getRebate() {
        return rebate;
    }

    /**
     * The {@code Rebate} class is useful to format a rebate
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class Rebate extends BinanceItem {

        /**
         * {@code page} current page
         */
        private final int page;

        /**
         * {@code totalRecords} total records
         */
        private final int totalRecords;

        /**
         * {@code totalPageNum} total pages
         */
        private final int totalPageNum;

        /**
         * {@code data} list of {@link RebateRecord}
         */
        private final ArrayList<RebateRecord> data;

        /**
         * Constructor to init {@link Rebate} object
         *
         * @param page:         current page
         * @param totalRecords: total records
         * @param totalPageNum: total pages
         * @param data:         list of {@link RebateRecord}
         */
        public Rebate(int page, int totalRecords, int totalPageNum, ArrayList<RebateRecord> data) {
            super(null);
            this.page = page;
            this.totalRecords = totalRecords;
            this.totalPageNum = totalPageNum;
            this.data = data;
        }

        /**
         * Constructor to init {@link Rebate} object
         *
         * @param jRebate: rebate details as {@link JSONObject}
         */
        public Rebate(JSONObject jRebate) {
            super(jRebate);
            page = hItem.getInt("page", 0);
            totalRecords = hItem.getInt("totalRecords", 0);
            totalPageNum = hItem.getInt("totalPageNum", 0);
            data = new ArrayList<>();
            JSONArray jData = hItem.getJSONArray("data", new JSONArray());
            for (int j = 0; j < jData.length(); j++)
                data.add(new RebateRecord(jData.getJSONObject(j)));
        }

        /**
         * Method to get {@link #page} instance <br>
         * No-any params required
         *
         * @return {@link #page} instance as int
         */
        public int getPage() {
            return page;
        }

        /**
         * Method to get {@link #totalRecords} instance <br>
         * No-any params required
         *
         * @return {@link #totalRecords} instance as int
         */
        public int getTotalRecords() {
            return totalRecords;
        }

        /**
         * Method to get {@link #totalPageNum} instance <br>
         * No-any params required
         *
         * @return {@link #totalPageNum} instance as int
         */
        public int getTotalPageNum() {
            return totalPageNum;
        }

        /**
         * Method to get {@link #data} instance <br>
         * No-any params required
         *
         * @return {@link #data} instance as {@link ArrayList} of {@link RebateRecord}
         */
        public ArrayList<RebateRecord> getData() {
            return data;
        }

        /**
         * The {@code RebateRecord} class is useful to format a rebate record
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         */
        public static class RebateRecord extends BinanceItem {

            /**
             * {@code asset} rebate asset
             */
            private final String asset;

            /**
             * {@code type} rebate type: 1 is commission rebate,2 is referral kickback
             */
            private final int type;

            /**
             * {@code amount} of the rebate
             */
            private final double amount;

            /**
             * {@code updateTime} when the rebate has been updated
             */
            private final long updateTime;

            /**
             * Constructor to init {@link RebateRecord} object
             *
             * @param asset:      rebate asset
             * @param type:       rebate type: 1 is commission rebate,2 is referral kickback
             * @param amount:     amount of the rebate
             * @param updateTime: when the rebate has been updated
             */
            public RebateRecord(String asset, int type, double amount, long updateTime) {
                super(null);
                this.asset = asset;
                this.type = type;
                this.amount = amount;
                this.updateTime = updateTime;
            }

            /**
             * Constructor to init {@link RebateRecord} object
             *
             * @param jRebateRecord: rebate record details as {@link JSONObject}
             */
            public RebateRecord(JSONObject jRebateRecord) {
                super(jRebateRecord);
                asset = hItem.getString("asset");
                type = hItem.getInt("type");
                amount = hItem.getDouble("amount", 0);
                updateTime = hItem.getLong("updateTime", 0);
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
             * Method to get {@link #type} instance <br>
             * No-any params required
             *
             * @return {@link #type} instance as int
             */
            public int getType() {
                return type;
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

        }

    }

}
