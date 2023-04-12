package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import org.json.JSONObject;

public class RedemptionQuota extends PurchaseQuota {

    private final double dailyQuota;
    private final double minRedemptionAmount;

    public RedemptionQuota(String asset, double leftQuota, double dailyQuota, double minRedemptionAmount) {
        super(asset, leftQuota);
        this.dailyQuota = dailyQuota;
        this.minRedemptionAmount = minRedemptionAmount;
    }

    public RedemptionQuota(JSONObject jRedemptionQuota) {
        super(jRedemptionQuota);
        dailyQuota = hItem.getDouble("dailyQuota", 0);
        minRedemptionAmount = hItem.getDouble("minRedemptionAmount", 0);
    }

    public double getDailyQuota() {
        return dailyQuota;
    }

    public double getMinRedemptionAmount() {
        return minRedemptionAmount;
    }

}
