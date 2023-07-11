package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.TierAnnualPercentageRate;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.TierAnnualPercentageRate.getInstance;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.SimpleEarnFlexibleProductList.SimpleEarnFlexibleProduct;

/**
 * The {@code SimpleEarnFlexibleProductList} class is useful to format a {@code Binance}'s simple earn flexible product list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-flexible-product-list-user_data">
 * Get Simple Earn Flexible Product List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class SimpleEarnFlexibleProductList extends BinanceRowsList<SimpleEarnFlexibleProduct> {

    /**
     * Constructor to init {@link SimpleEarnFlexibleProductList} object
     *
     * @param rows : list of the items
     */
    public SimpleEarnFlexibleProductList(ArrayList<SimpleEarnFlexibleProduct> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link SimpleEarnFlexibleProductList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public SimpleEarnFlexibleProductList(int total, ArrayList<SimpleEarnFlexibleProduct> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link SimpleEarnFlexibleProductList}
     *
     * @param jSimpleEarnFlexibleProductList : list details as {@link JSONObject}
     */
    public SimpleEarnFlexibleProductList(JSONObject jSimpleEarnFlexibleProductList) {
        super(jSimpleEarnFlexibleProductList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new SimpleEarnFlexibleProduct((JSONObject) row));
    }

    /**
     * The {@code SimpleEarnFlexibleProduct} class is useful to format a {@code Binance}'s simple earn flexible product
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SimpleEarnProductStructure
     */
    public static class SimpleEarnFlexibleProduct extends SimpleEarnProductStructure {

        /**
         * {@code latestAnnualPercentageRate} latest annual percentage rate of the flexible product
         */
        private final double latestAnnualPercentageRate;

        /**
         * {@code tierAnnualPercentageRate} tier annual percentage rate of the flexible product
         */
        private final TierAnnualPercentageRate tierAnnualPercentageRate;

        /**
         * {@code airDropPercentageRate} air drop percentage rate of the flexible product
         */
        private final double airDropPercentageRate;

        /**
         * {@code canPurchase} whether is allowed to purchase
         */
        private final boolean canPurchase;

        /**
         * {@code canRedeem} whether is allowed to redeem
         */
        private final boolean canRedeem;

        /**
         * {@code hot} whether this product is hot
         */
        private final boolean hot;

        /**
         * {@code minPurchaseAmount} min purchase amount value
         */
        private final double minPurchaseAmount;

        /**
         * {@code productId} product identifier
         */
        private final String productId;

        /**
         * Constructor to init a {@link SimpleEarnFlexibleProduct} object
         *
         * @param asset:                      asset of the simple earn product
         * @param isSoldOut:                  whether the simple earn product is sold out
         * @param status:                     status of the simple earn product
         * @param subscriptionStartTime:      when the simple earn product has been subscribed
         * @param latestAnnualPercentageRate: latest annual percentage rate of the flexible product
         * @param tierAnnualPercentageRate:   tier annual percentage rate of the flexible product
         * @param airDropPercentageRate:      air drop percentage rate of the flexible product
         * @param canPurchase:                whether is allowed to purchase
         * @param canRedeem:                  whether is allowed to redeem
         * @param hot:                        whether this product is hot
         * @param minPurchaseAmount:          min purchase amount value
         * @param productId:                  product identifier
         */
        public SimpleEarnFlexibleProduct(String asset, boolean isSoldOut, SimpleEarnStatus status, long subscriptionStartTime,
                                         double latestAnnualPercentageRate, TierAnnualPercentageRate tierAnnualPercentageRate,
                                         double airDropPercentageRate, boolean canPurchase, boolean canRedeem, boolean hot,
                                         double minPurchaseAmount, String productId) {
            super(asset, isSoldOut, status, subscriptionStartTime);
            this.latestAnnualPercentageRate = latestAnnualPercentageRate;
            this.tierAnnualPercentageRate = tierAnnualPercentageRate;
            this.airDropPercentageRate = airDropPercentageRate;
            this.canPurchase = canPurchase;
            this.canRedeem = canRedeem;
            this.hot = hot;
            this.minPurchaseAmount = minPurchaseAmount;
            this.productId = productId;
        }

        /**
         * Constructor to init a {@link SimpleEarnFlexibleProduct} object
         *
         * @param jSimpleEarnFlexibleProduct: simple earn flexible product details as {@link JSONObject}
         */
        public SimpleEarnFlexibleProduct(JSONObject jSimpleEarnFlexibleProduct) {
            super(jSimpleEarnFlexibleProduct);
            latestAnnualPercentageRate = hItem.getDouble("latestAnnualPercentageRate", 0);
            tierAnnualPercentageRate = getInstance(hItem.getJSONObject("tierAnnualPercentageRate"));
            airDropPercentageRate = hItem.getDouble("airDropPercentageRate", 0);
            canPurchase = hItem.getBoolean("canPurchase");
            canRedeem = hItem.getBoolean("canRedeem");
            hot = hItem.getBoolean("hot");
            minPurchaseAmount = hItem.getDouble("minPurchaseAmount", 0);
            productId = hItem.getString("productId");
        }

        /**
         * Method to get {@link #latestAnnualPercentageRate} instance <br>
         * No-any params required
         *
         * @return {@link #latestAnnualPercentageRate} instance as double
         */
        public double getLatestAnnualPercentageRate() {
            return latestAnnualPercentageRate;
        }

        /**
         * Method to get {@link #latestAnnualPercentageRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #latestAnnualPercentageRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLatestAnnualPercentageRate(int decimals) {
            return roundValue(latestAnnualPercentageRate, decimals);
        }

        /**
         * Method to get {@link #tierAnnualPercentageRate} instance <br>
         * No-any params required
         *
         * @return {@link #tierAnnualPercentageRate} instance as {@link TierAnnualPercentageRate}
         */
        public TierAnnualPercentageRate getTierAnnualPercentageRate() {
            return tierAnnualPercentageRate;
        }

        /**
         * Method to get {@link #airDropPercentageRate} instance <br>
         * No-any params required
         *
         * @return {@link #airDropPercentageRate} instance as double
         */
        public double getAirDropPercentageRate() {
            return airDropPercentageRate;
        }

        /**
         * Method to get {@link #airDropPercentageRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #airDropPercentageRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAirDropPercentageRate(int decimals) {
            return roundValue(airDropPercentageRate, decimals);
        }

        /**
         * Method to get {@link #canPurchase} instance <br>
         * No-any params required
         *
         * @return {@link #canPurchase} instance as boolean
         */
        public boolean isCanPurchase() {
            return canPurchase;
        }

        /**
         * Method to get {@link #canRedeem} instance <br>
         * No-any params required
         *
         * @return {@link #canRedeem} instance as boolean
         */
        public boolean canRedeem() {
            return canRedeem;
        }

        /**
         * Method to get {@link #hot} instance <br>
         * No-any params required
         *
         * @return {@link #hot} instance as boolean
         */
        public boolean isHot() {
            return hot;
        }

        /**
         * Method to get {@link #minPurchaseAmount} instance <br>
         * No-any params required
         *
         * @return {@link #minPurchaseAmount} instance as double
         */
        public double getMinPurchaseAmount() {
            return minPurchaseAmount;
        }

        /**
         * Method to get {@link #minPurchaseAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minPurchaseAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMinPurchaseAmount(int decimals) {
            return roundValue(minPurchaseAmount, decimals);
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

    }

}
