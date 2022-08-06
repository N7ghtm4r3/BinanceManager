package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 * The {@code MarginInterestRate} class is useful to format Binance Margin Interest Rate request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
 * **/

public class MarginInterestRate {

    /**
     * {@code asset} is instance that memorizes asset
     * **/
    private final String asset;

    /**
     * {@code dailyInterestRate} is instance that memorizes daily interest rate value
     * **/
    private double dailyInterestRate;

    /**
     * {@code timestamp} is instance that memorizes timestamp value
     * **/
    private long timestamp;

    /**
     * {@code vipLevel} is instance that memorizes vip level value
     * **/
    private int vipLevel;

    /** Constructor to init {@link MarginInterestRate} object
     * @param asset: asset
     * @param dailyInterestRate: interest on the asset
     * @param timestamp: accurate time value
     * @param vipLevel: interest rate value
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginInterestRate(String asset, double dailyInterestRate, long timestamp, int vipLevel) {
        this.asset = asset;
        if(dailyInterestRate < 0)
            throw new IllegalArgumentException("Daily interest rate value cannot be less than 0");
        else
            this.dailyInterestRate = dailyInterestRate;
        if(timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        else
            this.timestamp = timestamp;
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        else
            this.vipLevel = vipLevel;
    }

    public String getAsset() {
        return asset;
    }

    public double getDailyInterestRate() {
        return dailyInterestRate;
    }

    /** Method to set {@link #dailyInterestRate}
     * @param dailyInterestRate: daily interest rate value
     * @throws IllegalArgumentException when timestamp level value is less than 0
     * **/
    public void setDailyInterestRate(double dailyInterestRate) {
        if(dailyInterestRate < 0)
            throw new IllegalArgumentException("Daily interest rate value cannot be less than 0");
        this.dailyInterestRate = dailyInterestRate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /** Method to set {@link #timestamp}
     * @param timestamp: timestamp value
     * @throws IllegalArgumentException when timestamp level value is less than 0
     * **/
    public void setTimestamp(long timestamp) {
        if(timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        this.timestamp = timestamp;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    /** Method to set {@link #vipLevel}
     * @param vipLevel: vip level
     * @throws IllegalArgumentException when vip level value is less than 0
     * **/
    public void setVipLevel(int vipLevel) {
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        this.vipLevel = vipLevel;
    }

    @Override
    public String toString() {
        return "MarginInterestRate{" +
                "asset='" + asset + '\'' +
                ", dailyInterestRate=" + dailyInterestRate +
                ", timestamp=" + timestamp +
                ", vipLevel=" + vipLevel +
                '}';
    }

}
