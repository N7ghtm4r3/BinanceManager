package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.TierAnnualPercentageRate;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.SimpleEarnFlexibleProductPositions.SimpleEarnFlexibleProductPosition;

/**
 * The {@code SimpleEarnFlexibleProductPositions} class is useful to format a {@code Binance}'s simple earn flexible product positions
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
 * Get Flexible Product Position (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class SimpleEarnFlexibleProductPositions extends BinanceRowsList<SimpleEarnFlexibleProductPosition> {

    /**
     * Constructor to init {@link SimpleEarnFlexibleProductPositions} object
     *
     * @param rows : list of the items
     */
    public SimpleEarnFlexibleProductPositions(ArrayList<SimpleEarnFlexibleProductPosition> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link SimpleEarnFlexibleProductPositions} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public SimpleEarnFlexibleProductPositions(int total, ArrayList<SimpleEarnFlexibleProductPosition> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link SimpleEarnFlexibleProductPositions}
     *
     * @param jSimpleEarnFlexibleProductPositions : list details as {@link JSONObject}
     */
    public SimpleEarnFlexibleProductPositions(JSONObject jSimpleEarnFlexibleProductPositions) {
        super(jSimpleEarnFlexibleProductPositions);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new SimpleEarnFlexibleProductPosition((JSONObject) row));
    }

    /**
     * The {@code SimpleEarnFlexibleProductPosition} class is useful to format a {@code Binance}'s simple earn flexible product position
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class SimpleEarnFlexibleProductPosition extends BinanceItem {

        /**
         * {@code totalAmount} total amount of the position
         */
        private final double totalAmount;

        /**
         * {@code tierAnnualPercentageRate} tier annual percentage rate of the position
         */
        private final TierAnnualPercentageRate tierAnnualPercentageRate;

        /**
         * {@code latestAnnualPercentageRate} latest annual percentage rate of the position
         */
        private final double latestAnnualPercentageRate;

        /**
         * {@code yesterdayAirdropPercentageRate} yesterday airdrop percentage rate of the position
         */
        private final double yesterdayAirdropPercentageRate;

        /**
         * {@code asset} of the position
         */
        private final String asset;

        /**
         * {@code airDropAsset} air drop asset of the position
         */
        private final String airDropAsset;

        /**
         * {@code canRedeem} whether is allowed to redeem
         */
        private final boolean canRedeem;

        /**
         * {@code collateralAmount} collateral amount of the position
         */
        private final double collateralAmount;

        /**
         * {@code productId} product identifier
         */
        private final String productId;

        /**
         * {@code yesterdayRealTimeRewards} yesterday realtime rewards of the position
         */
        private final double yesterdayRealTimeRewards;

        /**
         * {@code cumulativeBonusRewards} cumulative bonus rewards of the position
         */
        private final double cumulativeBonusRewards;

        /**
         * {@code cumulativeRealTimeRewards} cumulative realtime rewards of the position
         */
        private final double cumulativeRealTimeRewards;

        /**
         * {@code cumulativeTotalRewards} cumulative total rewards of the position
         */
        private final double cumulativeTotalRewards;

        /**
         * {@code autoSubscribe} whether is auto-subscribe mode
         */
        private final boolean autoSubscribe;

        /**
         * Constructor to init a {@link SimpleEarnFlexibleProductPosition} object
         *
         * @param totalAmount:                    total amount of the position
         * @param tierAnnualPercentageRate:       tier annual percentage rate of the position
         * @param latestAnnualPercentageRate:     latest annual percentage rate of the position
         * @param yesterdayAirdropPercentageRate: yesterday airdrop percentage rate of the position
         * @param asset:                          asset of the position
         * @param airDropAsset:                   air drop asset of the position
         * @param canRedeem:                      whether is allowed to redeem
         * @param collateralAmount:               collateral amount of the position
         * @param productId:                      product identifier
         * @param yesterdayRealTimeRewards:       yesterday realtime rewards of the position
         * @param cumulativeBonusRewards:         cumulative bonus rewards of the position
         * @param cumulativeRealTimeRewards:      cumulative realtime rewards of the position
         * @param cumulativeTotalRewards:         cumulative total rewards of the position
         * @param autoSubscribe:                  whether is auto-subscribe mode
         */
        public SimpleEarnFlexibleProductPosition(double totalAmount, TierAnnualPercentageRate tierAnnualPercentageRate,
                                                 double latestAnnualPercentageRate, double yesterdayAirdropPercentageRate,
                                                 String asset, String airDropAsset, boolean canRedeem,
                                                 double collateralAmount, String productId,
                                                 double yesterdayRealTimeRewards, double cumulativeBonusRewards,
                                                 double cumulativeRealTimeRewards, double cumulativeTotalRewards,
                                                 boolean autoSubscribe) {
            super(null);
            this.totalAmount = totalAmount;
            this.tierAnnualPercentageRate = tierAnnualPercentageRate;
            this.latestAnnualPercentageRate = latestAnnualPercentageRate;
            this.yesterdayAirdropPercentageRate = yesterdayAirdropPercentageRate;
            this.asset = asset;
            this.airDropAsset = airDropAsset;
            this.canRedeem = canRedeem;
            this.collateralAmount = collateralAmount;
            this.productId = productId;
            this.yesterdayRealTimeRewards = yesterdayRealTimeRewards;
            this.cumulativeBonusRewards = cumulativeBonusRewards;
            this.cumulativeRealTimeRewards = cumulativeRealTimeRewards;
            this.cumulativeTotalRewards = cumulativeTotalRewards;
            this.autoSubscribe = autoSubscribe;
        }

        /**
         * Constructor to init a {@link SimpleEarnFlexibleProductPosition} object
         *
         * @param jSimpleEarnFlexibleProductPosition: simple earn flexible product position details as {@link JSONObject}
         */
        public SimpleEarnFlexibleProductPosition(JSONObject jSimpleEarnFlexibleProductPosition) {
            super(jSimpleEarnFlexibleProductPosition);
            totalAmount = hItem.getDouble("totalAmount", 0);
            tierAnnualPercentageRate = new TierAnnualPercentageRate(hItem.getJSONObject("tierAnnualPercentageRate"));
            latestAnnualPercentageRate = hItem.getDouble("latestAnnualPercentageRate", 0);
            yesterdayAirdropPercentageRate = hItem.getDouble("yesterdayAirdropPercentageRate", 0);
            asset = hItem.getString("asset");
            airDropAsset = hItem.getString("airDropAsset");
            canRedeem = hItem.getBoolean("canRedeem");
            collateralAmount = hItem.getDouble("collateralAmount", 0);
            productId = hItem.getString("productId");
            yesterdayRealTimeRewards = hItem.getDouble("yesterdayRealTimeRewards", 0);
            cumulativeBonusRewards = hItem.getDouble("cumulativeBonusRewards", 0);
            cumulativeRealTimeRewards = hItem.getDouble("cumulativeRealTimeRewards", 0);
            cumulativeTotalRewards = hItem.getDouble("cumulativeTotalRewards", 0);
            autoSubscribe = hItem.getBoolean("autoSubscribe");
        }

        /**
         * Method to get {@link #totalAmount} instance <br>
         * No-any params required
         *
         * @return {@link #totalAmount} instance as double
         */
        public double getTotalAmount() {
            return totalAmount;
        }

        /**
         * Method to get {@link #totalAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalAmount(int decimals) {
            return roundValue(totalAmount, decimals);
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
         * Method to get {@link #yesterdayAirdropPercentageRate} instance <br>
         * No-any params required
         *
         * @return {@link #yesterdayAirdropPercentageRate} instance as double
         */
        public double getYesterdayAirdropPercentageRate() {
            return yesterdayAirdropPercentageRate;
        }

        /**
         * Method to get {@link #yesterdayAirdropPercentageRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #yesterdayAirdropPercentageRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getYesterdayAirdropPercentageRate(int decimals) {
            return roundValue(yesterdayAirdropPercentageRate, decimals);
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
         * Method to get {@link #airDropAsset} instance <br>
         * No-any params required
         *
         * @return {@link #airDropAsset} instance as {@link String}
         */
        public String getAirDropAsset() {
            return airDropAsset;
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
         * Method to get {@link #collateralAmount} instance <br>
         * No-any params required
         *
         * @return {@link #collateralAmount} instance as double
         */
        public double getCollateralAmount() {
            return collateralAmount;
        }

        /**
         * Method to get {@link #collateralAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralAmount(int decimals) {
            return roundValue(collateralAmount, decimals);
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
         * Method to get {@link #yesterdayRealTimeRewards} instance <br>
         * No-any params required
         *
         * @return {@link #yesterdayRealTimeRewards} instance as double
         */
        public double getYesterdayRealTimeRewards() {
            return yesterdayRealTimeRewards;
        }

        /**
         * Method to get {@link #yesterdayRealTimeRewards} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #yesterdayRealTimeRewards} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getYesterdayRealTimeRewards(int decimals) {
            return roundValue(yesterdayRealTimeRewards, decimals);
        }

        /**
         * Method to get {@link #cumulativeBonusRewards} instance <br>
         * No-any params required
         *
         * @return {@link #cumulativeBonusRewards} instance as double
         */
        public double getCumulativeBonusRewards() {
            return cumulativeBonusRewards;
        }

        /**
         * Method to get {@link #cumulativeBonusRewards} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #cumulativeBonusRewards} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCumulativeBonusRewards(int decimals) {
            return roundValue(cumulativeBonusRewards, decimals);
        }

        /**
         * Method to get {@link #cumulativeRealTimeRewards} instance <br>
         * No-any params required
         *
         * @return {@link #cumulativeRealTimeRewards} instance as double
         */
        public double getCumulativeRealTimeRewards() {
            return cumulativeRealTimeRewards;
        }

        /**
         * Method to get {@link #cumulativeRealTimeRewards} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #cumulativeRealTimeRewards} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCumulativeRealTimeRewards(int decimals) {
            return roundValue(cumulativeRealTimeRewards, decimals);
        }

        /**
         * Method to get {@link #cumulativeTotalRewards} instance <br>
         * No-any params required
         *
         * @return {@link #cumulativeTotalRewards} instance as double
         */
        public double getCumulativeTotalRewards() {
            return cumulativeTotalRewards;
        }

        /**
         * Method to get {@link #cumulativeTotalRewards} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #cumulativeTotalRewards} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCumulativeTotalRewards(int decimals) {
            return roundValue(cumulativeTotalRewards, decimals);
        }

        /**
         * Method to get {@link #autoSubscribe} instance <br>
         * No-any params required
         *
         * @return {@link #autoSubscribe} instance as boolean
         */
        public boolean isAutoSubscribe() {
            return autoSubscribe;
        }

    }

}
