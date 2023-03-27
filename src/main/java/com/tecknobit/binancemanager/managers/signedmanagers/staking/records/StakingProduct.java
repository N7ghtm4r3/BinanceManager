package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class StakingProduct extends BinanceItem {

    public enum ProductType {

        STAKING,
        F_DEFI,
        L_DEFI

    }

    private final String projectId;
    private final StakingDetail detail;
    private final StakingQuota quota;

    public StakingProduct(String projectId, StakingDetail detail, StakingQuota quota) {
        super(null);
        this.projectId = projectId;
        this.detail = detail;
        this.quota = quota;
    }

    public StakingProduct(JSONObject jStakingProduct) {
        super(jStakingProduct);
        projectId = hItem.getString("projectId");
        detail = new StakingDetail(hItem.getJSONObject("detail"));
        quota = new StakingQuota(hItem.getJSONObject("quota"));
    }

    public String getProjectId() {
        return projectId;
    }

    public StakingDetail getDetail() {
        return detail;
    }

    public StakingQuota getQuota() {
        return quota;
    }

    public static class StakingDetail extends BinanceItem {

        private final String asset;
        private final String rewardAsset;
        private final int duration;
        private final boolean renewable;
        private final double apy;

        public StakingDetail(String asset, String rewardAsset, int duration, boolean renewable, double apy) {
            super(null);
            this.asset = asset;
            this.rewardAsset = rewardAsset;
            this.duration = duration;
            this.renewable = renewable;
            this.apy = apy;
        }

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

        public String getAsset() {
            return asset;
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

        public double getApy() {
            return apy;
        }

    }

    public static class StakingQuota extends BinanceItem {

        private final double totalPersonalQuota;
        private final double minimum;

        public StakingQuota(double totalPersonalQuota, double minimum) {
            super(null);
            this.totalPersonalQuota = totalPersonalQuota;
            this.minimum = minimum;
        }

        public StakingQuota(JSONObject jItem) {
            super(jItem);
            totalPersonalQuota = hItem.getDouble("totalPersonalQuota", 0);
            minimum = hItem.getDouble("minimum", 0);
        }

        public double getTotalPersonalQuota() {
            return totalPersonalQuota;
        }

        public double getMinimum() {
            return minimum;
        }

    }

}
