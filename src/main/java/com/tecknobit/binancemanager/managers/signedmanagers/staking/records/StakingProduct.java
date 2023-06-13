package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code StakingProduct} class is useful to format a staking product
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-list-user_data">
 * Get Staking Product List(USER_DATA)</a>
 * @see BinanceItem
 */
public class StakingProduct extends BinanceItem {

    /**
     * {@code ProductType} list of available product types
     */
    public enum ProductType {

        /**
         * {@code STAKING} product type
         */
        STAKING,

        /**
         * {@code F_DEFI} product type
         */
        F_DEFI,

        /**
         * {@code L_DEFI} product type
         */
        L_DEFI

    }

    /**
     * {@code projectId} project id of the staking product
     */
    private final String projectId;

    /**
     * {@code detail} of the staking product
     */
    private final StakingDetail detail;

    /**
     * {@code quota} of the staking product
     */
    private final StakingQuota quota;

    /**
     * Constructor to init {@link StakingProduct} object
     *
     * @param projectId: project id of the staking product
     * @param detail:    detail of the staking product
     * @param quota:     quota of the staking product
     */
    public StakingProduct(String projectId, StakingDetail detail, StakingQuota quota) {
        super(null);
        this.projectId = projectId;
        this.detail = detail;
        this.quota = quota;
    }

    /**
     * Constructor to init {@link StakingProduct} object
     *
     * @param jStakingProduct: staking product details as {@link JSONObject}
     */
    public StakingProduct(JSONObject jStakingProduct) {
        super(jStakingProduct);
        projectId = hItem.getString("projectId");
        detail = new StakingDetail(hItem.getJSONObject("detail"));
        quota = new StakingQuota(hItem.getJSONObject("quota"));
    }

    /**
     * Method to get {@link #projectId} instance <br>
     * No-any params required
     *
     * @return {@link #projectId} instance as {@link String}
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Method to get {@link #detail} instance <br>
     * No-any params required
     *
     * @return {@link #detail} instance as {@link StakingDetail}
     */
    public StakingDetail getDetail() {
        return detail;
    }

    /**
     * Method to get {@link #quota} instance <br>
     * No-any params required
     *
     * @return {@link #quota} instance as {@link StakingQuota}
     */
    public StakingQuota getQuota() {
        return quota;
    }

    /**
     * The {@code StakingDetail} class is useful to format a staking detail
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class StakingDetail extends BinanceItem {

        /**
         * {@code asset} lock up asset
         */
        protected final String asset;

        /**
         * {@code rewardAsset} earn Asset
         */
        protected final String rewardAsset;

        /**
         * {@code duration} lock period(days)
         */
        protected final int duration;

        /**
         * {@code renewable} project supports renewal
         */
        protected final boolean renewable;

        /**
         * {@code apy} value of the staking product
         */
        protected final double apy;

        /**
         * Constructor to init {@link StakingDetail} object
         *
         * @param asset:       lock up asset
         * @param rewardAsset: earn Asset
         * @param duration:    lock period(days)
         * @param renewable:   project supports renewal
         * @param apy:         value of the staking product
         */
        public StakingDetail(String asset, String rewardAsset, int duration, boolean renewable, double apy) {
            super(null);
            this.asset = asset;
            this.rewardAsset = rewardAsset;
            this.duration = duration;
            this.renewable = renewable;
            this.apy = apy;
        }

        /**
         * Constructor to init {@link StakingDetail} object
         *
         * @param jStakingDetail: staking details as {@link JSONObject}
         */
        public StakingDetail(JSONObject jStakingDetail) {
            super(jStakingDetail);
            asset = hItem.getString("asset");
            rewardAsset = hItem.getString("rewardAsset");
            duration = hItem.getInt("duration", 0);
            renewable = hItem.getBoolean("renewable");
            double dApy = hItem.getDouble("apy", -101);
            if (dApy == -101)
                dApy = hItem.getDouble("APY", 0);
            apy = dApy;
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
         * Method to get {@link #rewardAsset} instance <br>
         * No-any params required
         *
         * @return {@link #rewardAsset} instance as {@link String}
         */
        public String getRewardAsset() {
            return rewardAsset;
        }

        /**
         * Method to get {@link #duration} instance <br>
         * No-any params required
         *
         * @return {@link #duration} instance as int
         */
        public int getDuration() {
            return duration;
        }

        /**
         * Method to get {@link #renewable} instance <br>
         * No-any params required
         *
         * @return {@link #renewable} instance as boolean
         */
        public boolean isRenewable() {
            return renewable;
        }

        /**
         * Method to get {@link #apy} instance <br>
         * No-any params required
         *
         * @return {@link #apy} instance as double
         */
        public double getApy() {
            return apy;
        }

        /**
         * Method to get {@link #apy} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #apy} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getApy(int decimals) {
            return roundValue(apy, decimals);
        }

    }

    /**
     * The {@code StakingQuota} class is useful to format a staking quota
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class StakingQuota extends BinanceItem {

        /**
         * {@code totalPersonalQuota} total Personal quota
         */
        private final double totalPersonalQuota;

        /**
         * {@code minimum} minimum amount per order
         */
        private final double minimum;

        /**
         * Constructor to init {@link StakingQuota} object
         *
         * @param totalPersonalQuota: total Personal quota
         * @param minimum:            minimum amount per order
         */
        public StakingQuota(double totalPersonalQuota, double minimum) {
            super(null);
            this.totalPersonalQuota = totalPersonalQuota;
            this.minimum = minimum;
        }

        /**
         * Constructor to init {@link StakingQuota} object
         *
         * @param jStakingQuota: staking quota details as {@link JSONObject}
         */
        public StakingQuota(JSONObject jStakingQuota) {
            super(jStakingQuota);
            totalPersonalQuota = hItem.getDouble("totalPersonalQuota", 0);
            minimum = hItem.getDouble("minimum", 0);
        }

        /**
         * Method to get {@link #totalPersonalQuota} instance <br>
         * No-any params required
         *
         * @return {@link #totalPersonalQuota} instance as double
         */
        public double getTotalPersonalQuota() {
            return totalPersonalQuota;
        }

        /**
         * Method to get {@link #totalPersonalQuota} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalPersonalQuota} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalPersonalQuota(int decimals) {
            return roundValue(totalPersonalQuota, decimals);
        }

        /**
         * Method to get {@link #minimum} instance <br>
         * No-any params required
         *
         * @return {@link #minimum} instance as double
         */
        public double getMinimum() {
            return minimum;
        }

        /**
         * Method to get {@link #minimum} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minimum} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMinimum(int decimals) {
            return roundValue(minimum, decimals);
        }

    }

}
