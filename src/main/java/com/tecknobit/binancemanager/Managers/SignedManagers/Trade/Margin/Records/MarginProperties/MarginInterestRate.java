package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

public class MarginInterestRate {

    private final String asset;
    private final double dailyInterestRate;
    private final long timestamp;
    private final int vipLevel;

    public MarginInterestRate(String asset, double dailyInterestRate, long timestamp, int vipLevel) {
        this.asset = asset;
        this.dailyInterestRate = dailyInterestRate;
        this.timestamp = timestamp;
        this.vipLevel = vipLevel;
    }

    public String getAsset() {
        return asset;
    }

    public double getDailyInterestRate() {
        return dailyInterestRate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getVipLevel() {
        return vipLevel;
    }

}
