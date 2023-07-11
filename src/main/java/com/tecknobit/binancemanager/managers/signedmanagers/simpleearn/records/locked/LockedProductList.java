package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.products.ProductQuota;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedProductList.LockedProduct;

/**
 * The {@code LockedProductList} class is useful to format a {@code Binance}'s locked product list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-locked-product-list-user_data">
 * Get Simple Earn Locked Product List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class LockedProductList extends BinanceRowsList<LockedProduct> {

    /**
     * Constructor to init {@link LockedProductList} object
     *
     * @param rows : list of the items
     */
    public LockedProductList(ArrayList<LockedProduct> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link LockedProductList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedProductList(int total, ArrayList<LockedProduct> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link LockedProductList}
     *
     * @param jLockedProductList : list details as {@link JSONObject}
     */
    public LockedProductList(JSONObject jLockedProductList) {
        super(jLockedProductList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedProduct((JSONObject) row));
    }

    /**
     * The {@code LockedProduct} class is useful to format a {@code Binance}'s locked product
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SimpleEarnProductStructure
     */
    public static class LockedProduct extends SimpleEarnProductStructure {

        /**
         * {@code projectId} project identifier
         */
        private final String projectId;

        /**
         * {@code rewardAsset} reward asset
         */
        private final String rewardAsset;

        /**
         * {@code duration} of the locked product
         */
        private final int duration;

        /**
         * {@code renewable} whether the locked product is renewable
         */
        private final boolean renewable;

        /**
         * {@code apr} value of the locked product
         */
        private final double apr;

        /**
         * {@code extraRewardAsset} extra reward asset of the locked product
         */
        private final String extraRewardAsset;

        /**
         * {@code extraRewardAPR} extra reward APR of the locked product
         */
        private final double extraRewardAPR;

        /**
         * {@code quota} of the locked product
         */
        private final ProductQuota quota;

        /**
         * Constructor to init a {@link LockedProduct} object
         *
         * @param asset:                 asset of the locked product
         * @param isSoldOut:             whether the locked product is sold out
         * @param status:                status of the locked product
         * @param subscriptionStartTime: when the locked product has been subscribed
         * @param projectId:             project identifier
         * @param rewardAsset:           reward asset
         * @param duration:              duration of the locked product
         * @param renewable:             whether the locked product is renewable
         * @param apr:                   apr value of the locked product
         * @param extraRewardAsset:      extra reward asset of the locked product
         * @param extraRewardAPR:        extra reward APR of the locked product
         * @param quota:                 quota of the locked product
         */
        public LockedProduct(String asset, boolean isSoldOut, SimpleEarnStatus status, long subscriptionStartTime,
                             String projectId, String rewardAsset, int duration, boolean renewable, double apr,
                             String extraRewardAsset, double extraRewardAPR, ProductQuota quota) {
            super(asset, isSoldOut, status, subscriptionStartTime);
            this.projectId = projectId;
            this.rewardAsset = rewardAsset;
            this.duration = duration;
            this.renewable = renewable;
            this.apr = apr;
            this.extraRewardAsset = extraRewardAsset;
            this.extraRewardAPR = extraRewardAPR;
            this.quota = quota;
        }

        /**
         * Constructor to init a {@link LockedProduct} object
         *
         * @param jLockedProduct: locked product details as {@link JSONObject}
         */
        public LockedProduct(JSONObject jLockedProduct) {
            super(jLockedProduct);
            projectId = hItem.getString("projectId");
            rewardAsset = hItem.getString("rewardAsset");
            duration = hItem.getInt("duration", 0);
            renewable = hItem.getBoolean("renewable");
            apr = hItem.getDouble("apr", 0);
            extraRewardAsset = hItem.getString("extraRewardAsset");
            extraRewardAPR = hItem.getDouble("extraRewardAPR", 0);
            quota = new ProductQuota(hItem.getJSONObject("quota"));
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
         * Method to get {@link #apr} instance <br>
         * No-any params required
         *
         * @return {@link #apr} instance as double
         */
        public double getApr() {
            return apr;
        }

        /**
         * Method to get {@link #apr} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #apr} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getApr(int decimals) {
            return roundValue(apr, decimals);
        }

        /**
         * Method to get {@link #extraRewardAsset} instance <br>
         * No-any params required
         *
         * @return {@link #extraRewardAsset} instance as {@link String}
         */
        public String getExtraRewardAsset() {
            return extraRewardAsset;
        }

        /**
         * Method to get {@link #extraRewardAPR} instance <br>
         * No-any params required
         *
         * @return {@link #extraRewardAPR} instance as double
         */
        public double getExtraRewardAPR() {
            return extraRewardAPR;
        }

        /**
         * Method to get {@link #extraRewardAPR} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #extraRewardAPR} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getExtraRewardAPR(int decimals) {
            return roundValue(extraRewardAPR, decimals);
        }

        /**
         * Method to get {@link #quota} instance <br>
         * No-any params required
         *
         * @return {@link #quota} instance as {@link ProductQuota}
         */
        public ProductQuota getQuota() {
            return quota;
        }

    }

}
