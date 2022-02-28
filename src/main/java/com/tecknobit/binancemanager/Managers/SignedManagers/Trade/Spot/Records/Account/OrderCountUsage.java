package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account;

/**
 *  The {@code OrderCountUsage} class is useful to format OrderCountUsage object
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class OrderCountUsage {

    private final String rateLimitType;
    private final String interval;
    private final double intervalNum;
    private final double limit;
    private final int count;

    public OrderCountUsage(String rateLimitType, String interval, double intervalNum, double limit, int count) {
        this.rateLimitType = rateLimitType;
        this.interval = interval;
        this.intervalNum = intervalNum;
        this.limit = limit;
        this.count = count;
    }

    public String getRateLimitType() {
        return rateLimitType;
    }

    public String getInterval() {
        return interval;
    }

    public double getIntervalNum() {
        return intervalNum;
    }

    public double getLimit() {
        return limit;
    }

    public int getCount() {
        return count;
    }

}
