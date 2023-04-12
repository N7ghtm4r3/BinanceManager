package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import org.json.JSONObject;

public class FlexibleProduct extends SavingProductStructure {

    private final boolean canPurchase;
    private final boolean featured;
    private final double minPurchaseAmount;
    private final double purchaseAmount;
    private final SavingStatus status;
    private final double upLimit;
    private final double upLimitPerUser;

    public FlexibleProduct(String asset, TierAnnualInterestRate tierAnnualInterestRate, boolean canRedeem, String productId,
                           double averageAnnualInterestRate, boolean canPurchase, boolean featured, double minPurchaseAmount,
                           double purchaseAmount, SavingStatus status, double upLimit, double upLimitPerUser) {
        super(asset, tierAnnualInterestRate, canRedeem, productId, averageAnnualInterestRate);
        this.canPurchase = canPurchase;
        this.featured = featured;
        this.minPurchaseAmount = minPurchaseAmount;
        this.purchaseAmount = purchaseAmount;
        this.status = status;
        this.upLimit = upLimit;
        this.upLimitPerUser = upLimitPerUser;
    }

    public FlexibleProduct(JSONObject jFlexibleProduct) {
        super(jFlexibleProduct);
        canPurchase = hItem.getBoolean("canPurchase");
        featured = hItem.getBoolean("featured");
        minPurchaseAmount = hItem.getDouble("minPurchaseAmount", 0);
        purchaseAmount = hItem.getDouble("purchasedAmount", 0);
        status = SavingStatus.valueOf(hItem.getString("status"));
        upLimit = hItem.getDouble("upLimit", 0);
        upLimitPerUser = hItem.getDouble("upLimitPerUser", 0);
    }

    public boolean canPurchase() {
        return canPurchase;
    }

    public boolean isFeatured() {
        return featured;
    }

    public double getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public SavingStatus getStatus() {
        return status;
    }

    public double getUpLimit() {
        return upLimit;
    }

    public double getUpLimitPerUser() {
        return upLimitPerUser;
    }

}
