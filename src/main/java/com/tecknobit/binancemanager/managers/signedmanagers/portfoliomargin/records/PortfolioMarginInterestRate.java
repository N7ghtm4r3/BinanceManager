package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class PortfolioMarginInterestRate extends BinanceItem {

    private final String asset;
    private final double dailyInterest;
    private final double yearlyInterest;

    public PortfolioMarginInterestRate(String asset, double dailyInterest, double yearlyInterest) {
        super(null);
        this.asset = asset;
        this.dailyInterest = dailyInterest;
        this.yearlyInterest = yearlyInterest;
    }

    public PortfolioMarginInterestRate(JSONObject jPortfolioMarginInterestRate) {
        super(jPortfolioMarginInterestRate);
        asset = hItem.getString("asset");
        dailyInterest = hItem.getDouble("dailyInterest", 0);
        yearlyInterest = hItem.getDouble("yearlyInterest", 0);
    }

    public String getAsset() {
        return asset;
    }

    public double getDailyInterest() {
        return dailyInterest;
    }

    public double getYearlyInterest() {
        return yearlyInterest;
    }

}
