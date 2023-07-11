package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.records.products.ProductQuota;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedProductList.LockedProduct;

public class LockedProductList extends BinanceRowsList<LockedProduct> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public LockedProductList(ArrayList<LockedProduct> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedProductList(int total, ArrayList<LockedProduct> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jLockedProductList : list details as {@link JSONObject}
     */
    public LockedProductList(JSONObject jLockedProductList) {
        super(jLockedProductList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedProduct((JSONObject) row));
    }

    public static class LockedProduct extends SimpleEarnProductStructure {

        private final String projectId;

        private final String rewardAsset;

        private final int duration;

        private final boolean renewable;

        private final double apr;

        private final String extraRewardAsset;

        private final double extraRewardAPR;

        private final ProductQuota quota;

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

        public String getProjectId() {
            return projectId;
        }

        public String getRewardAsset() {
            return rewardAsset;
        }

        public int getDuration() {
            return duration;
        }

        public boolean isRenewable() {
            return renewable;
        }

        public double getApr() {
            return apr;
        }

        public String getExtraRewardAsset() {
            return extraRewardAsset;
        }

        public double getExtraRewardAPR() {
            return extraRewardAPR;
        }

        public ProductQuota getQuota() {
            return quota;
        }

    }

}
