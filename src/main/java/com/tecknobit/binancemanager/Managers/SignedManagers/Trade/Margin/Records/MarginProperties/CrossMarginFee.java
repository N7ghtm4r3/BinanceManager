package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * The {@code CrossMarginFee} class is useful to format Binance Cross Margin Fee request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
 * **/

public class CrossMarginFee {

    private int vipLevel;
    private final String coin;
    private boolean transferIn;
    private boolean borrowable;
    private double dailyInterest;
    private double yearlyInterest;
    private double borrowLimit;
    private ArrayList<String> marginablePairsList;

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
        marginablePairsList = new ArrayList<>();
        for(int j=0; j < jsonArray.length(); j++)
            marginablePairsList.add(jsonArray.getString(j));
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        this.vipLevel = vipLevel;
    }

    public String getCoin() {
        return coin;
    }

    public boolean isTransferIn() {
        return transferIn;
    }

    public void setTransferIn(boolean transferIn) {
        this.transferIn = transferIn;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

    public double getDailyInterest() {
        return dailyInterest;
    }

    public void setDailyInterest(double dailyInterest) {
        if(dailyInterest < 0)
            throw new IllegalArgumentException("Daily interest value cannot be less than 0");
        this.dailyInterest = dailyInterest;
    }

    public double getYearlyInterest() {
        return yearlyInterest;
    }

    public void setYearlyInterest(double yearlyInterest) {
        if(yearlyInterest < 0)
            throw new IllegalArgumentException("Yearly interest value cannot be less than 0");
        this.yearlyInterest = yearlyInterest;
    }

    public double getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(double borrowLimit) {
        if(borrowLimit < 0)
            throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        this.borrowLimit = borrowLimit;
    }

    public ArrayList<String> getMarginablePairsList() {
        return marginablePairsList;
    }

    public void setMarginablePairsList(ArrayList<String> marginablePairsList) {
        this.marginablePairsList = marginablePairsList;
    }

    public void insertMarginablePair(String marginablePair){
        if(!marginablePairsList.contains(marginablePair))
            marginablePairsList.add(marginablePair);
    }

    public boolean removeMarginablePair(String marginablePair){
        return marginablePairsList.remove(marginablePair);
    }

    public String getMarginablePair(int index){
        return marginablePairsList.get(index);
    }

}
