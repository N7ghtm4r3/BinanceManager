package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 * The {@code MarginInterestRate} class is useful to format Binance Margin Interest Rate request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data
 * **/

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
