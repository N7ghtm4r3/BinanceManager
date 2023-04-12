package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import org.json.JSONObject;

public class FlexibleProductPosition extends SavingProductStructure {

    private final double annualInterestRate;
    private final double dailyInterestRate;
    private final double freeAmount;
    private final String productName;
    private final double redeemingAmount;
    private final double todayPurchaseAmount;
    private final double totalAmount;
    private final double totalInterest;

    public FlexibleProductPosition(String asset, TierAnnualInterestRate tierAnnualInterestRate, boolean canRedeem,
                                   String productId, double averageAnnualInterestRate, double annualInterestRate,
                                   double dailyInterestRate, double freeAmount, String productName, double redeemingAmount,
                                   double todayPurchaseAmount, double totalAmount, double totalInterest) {
        super(asset, tierAnnualInterestRate, canRedeem, productId, averageAnnualInterestRate);
        this.annualInterestRate = annualInterestRate;
        this.dailyInterestRate = dailyInterestRate;
        this.freeAmount = freeAmount;
        this.productName = productName;
        this.redeemingAmount = redeemingAmount;
        this.todayPurchaseAmount = todayPurchaseAmount;
        this.totalAmount = totalAmount;
        this.totalInterest = totalInterest;
    }

    public FlexibleProductPosition(JSONObject jFlexibleProductPosition) {
        super(jFlexibleProductPosition);
        annualInterestRate = hItem.getDouble("annualInterestRate", 0);
        dailyInterestRate = hItem.getDouble("dailyInterestRate", 0);
        freeAmount = hItem.getDouble("freeAmount", 0);
        productName = hItem.getString("productName");
        redeemingAmount = hItem.getDouble("redeemingAmount", 0);
        todayPurchaseAmount = hItem.getDouble("todayPurchasedAmount", 0);
        totalAmount = hItem.getDouble("totalAmount", 0);
        totalInterest = hItem.getDouble("totalInterest", 0);
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public double getDailyInterestRate() {
        return dailyInterestRate;
    }

    public double getFreeAmount() {
        return freeAmount;
    }

    public String getProductName() {
        return productName;
    }

    public double getRedeemingAmount() {
        return redeemingAmount;
    }

    public double getTodayPurchaseAmount() {
        return todayPurchaseAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

}
