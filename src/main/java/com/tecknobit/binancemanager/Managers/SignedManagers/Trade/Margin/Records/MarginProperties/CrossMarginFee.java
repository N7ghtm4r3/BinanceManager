package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

import org.json.JSONArray;

import java.util.ArrayList;

public class CrossMarginFee {

    private final int vipLevel;
    private final String coin;
    private final boolean transferIn;
    private final boolean borrowable;
    private final double dailyInterest;
    private final double yearlyInterest;
    private final double borrowLimit;
    private ArrayList<String> marginablePairs;

    public CrossMarginFee(int vipLevel, String coin, boolean transferIn, boolean borrowable, double dailyInterest,
                          double yearlyInterest, double borrowLimit, JSONArray jsonArray) {
        this.vipLevel = vipLevel;
        this.coin = coin;
        this.transferIn = transferIn;
        this.borrowable = borrowable;
        this.dailyInterest = dailyInterest;
        this.yearlyInterest = yearlyInterest;
        this.borrowLimit = borrowLimit;
        loadMarginablePairsList(jsonArray);
    }

    private void loadMarginablePairsList(JSONArray jsonArray) {
        marginablePairs = new ArrayList<>();
        for(int j=0; j < jsonArray.length(); j++)
            marginablePairs.add(jsonArray.getString(j));
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public String getCoin() {
        return coin;
    }

    public boolean isTransferIn() {
        return transferIn;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public double getDailyInterest() {
        return dailyInterest;
    }

    public double getYearlyInterest() {
        return yearlyInterest;
    }

    public double getBorrowLimit() {
        return borrowLimit;
    }

    public ArrayList<String> getMarginablePairsList() {
        return marginablePairs;
    }

    public String getMarginablePairs(int index) {
        return marginablePairs.get(index);
    }

}
