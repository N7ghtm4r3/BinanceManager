package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 * The {@code MarginInterestRate} class is useful to format Binance Margin Interest Rate request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
 * **/

public class MarginInterestRate {

    private final String asset;
    private double dailyInterestRate;
    private long timestamp;
    private int vipLevel;

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

    public void setDailyInterestRate(double dailyInterestRate) {
        if(dailyInterestRate < 0)
            throw new IllegalArgumentException("Daily interest rate value cannot be less than 0");
        this.dailyInterestRate = dailyInterestRate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        if(timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        this.timestamp = timestamp;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        this.vipLevel = vipLevel;
    }

}
