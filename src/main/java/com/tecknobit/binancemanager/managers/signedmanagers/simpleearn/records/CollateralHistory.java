package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.CollateralHistory.CollateralRecord;

/**
 * The {@code CollateralHistory} class is useful to format a {@code Binance}'s collateral history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-record-user_data">
 * Get Collateral Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CollateralHistory extends BinanceRowsList<CollateralRecord> {

    /**
     * Constructor to init {@link CollateralHistory} object
     *
     * @param rows : list of the items
     */
    public CollateralHistory(ArrayList<CollateralRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link CollateralHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public CollateralHistory(int total, ArrayList<CollateralRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link CollateralHistory}
     *
     * @param jCollateralHistory : list details as {@link JSONObject}
     */
    public CollateralHistory(JSONObject jCollateralHistory) {
        super(jCollateralHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CollateralRecord((JSONObject) row));
    }

    /**
     * The {@code CollateralRecord} class is useful to format a {@code Binance}'s collateral record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class CollateralRecord extends BinanceItem {

        /**
         * {@code amount} of the collateral
         */
        private final double amount;

        /**
         * {@code productId} product identifier
         */
        private final String productId;

        /**
         * {@code asset} of the collateral
         */
        private final String asset;

        /**
         * {@code createTime} when the collateral has been created
         */
        private final long createTime;

        /**
         * {@code type} of the collateral
         */
        private final String type;

        /**
         * {@code productName} name of the product
         */
        private final String productName;

        /**
         * {@code orderId} order identifier
         */
        private final long orderId;

        /**
         * Constructor to init a {@link CollateralRecord} object
         *
         * @param amount:amount of the collateral
         * @param productId:    product identifier
         * @param asset:        asset of the collateral
         * @param createTime:   when the collateral has been created
         * @param type:         type of the collateral
         * @param productName:  name of the product
         * @param orderId:      order identifier
         */
        public CollateralRecord(double amount, String productId, String asset, long createTime, String type,
                                String productName, long orderId) {
            super(null);
            this.amount = amount;
            this.productId = productId;
            this.asset = asset;
            this.createTime = createTime;
            this.type = type;
            this.productName = productName;
            this.orderId = orderId;
        }

        /**
         * Constructor to init a {@link CollateralRecord} object
         *
         * @param jCollateralRecord: collateral record details as {@link JSONObject}
         */
        public CollateralRecord(JSONObject jCollateralRecord) {
            super(jCollateralRecord);
            amount = hItem.getDouble("amount", 0);
            productId = hItem.getString("productId");
            asset = hItem.getString("asset");
            createTime = hItem.getLong("createTime", -1);
            type = hItem.getString("type");
            productName = hItem.getString("productName");
            orderId = hItem.getLong("orderId", 0);
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
         * Method to get {@link #createTime} instance <br>
         * No-any params required
         *
         * @return {@link #createTime} instance as long
         */
        public long getCreateTime() {
            return createTime;
        }

        /**
         * Method to get {@link #createTime} instance <br>
         * No-any params required
         *
         * @return {@link #createTime} instance as {@link Date}
         */
        public Date getCreateDate() {
            return TimeFormatter.getDate(createTime);
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
         * Method to get {@link #productName} instance <br>
         * No-any params required
         *
         * @return {@link #productName} instance as {@link String}
         */
        public String getProductName() {
            return productName;
        }

        /**
         * Method to get {@link #orderId} instance <br>
         * No-any params required
         *
         * @return {@link #orderId} instance as long
         */
        public long getOrderId() {
            return orderId;
        }

    }

}
