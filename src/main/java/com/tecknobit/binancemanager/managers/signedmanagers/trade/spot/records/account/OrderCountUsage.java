package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account;

import org.json.JSONObject;

/**
 * The {@code OrderCountUsage} class is useful to format OrderCountUsage object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
 **/

public class OrderCountUsage {

    /**
     * {@code rateLimitType} is instance that memorizes rate limit type
     * **/
    private final String rateLimitType;

    /**
     * {@code interval} is instance that memorizes interval value
     * **/
    private final String interval;

    /**
     * {@code intervalNum} is instance that memorizes interval number value
     **/
    private final int intervalNum;

    /**
     * {@code limit} is instance that memorizes limit value
     * **/
    private final double limit;

    /**
     * {@code count} is instance that memorizes count value
     * **/
    private final int count;

    /** Constructor to init {@link OrderCountUsage} object
     * @param rateLimitType: rate limit type
     * @param interval: interval value
     * @param intervalNum: interval number value
     * @param limit: limit value
     * @param count: count value
     * **/
    public OrderCountUsage(String rateLimitType, String interval, int intervalNum, double limit, int count) {
        this.rateLimitType = rateLimitType;
        this.interval = interval;
        this.intervalNum = intervalNum;
        this.limit = limit;
        this.count = count;
    }

    /**
     * Constructor to init {@link OrderCountUsage} object
     *
     * @param orderCountUsage: order count usage details as {@link JSONObject}
     **/
    public OrderCountUsage(JSONObject orderCountUsage) {
        rateLimitType = orderCountUsage.getString("rateLimitType");
        interval = orderCountUsage.getString("interval");
        intervalNum = orderCountUsage.getInt("intervalNum");
        limit = orderCountUsage.getDouble("limit");
        count = orderCountUsage.getInt("count");
    }

    public String getRateLimitType() {
        return rateLimitType;
    }

    public String getInterval() {
        return interval;
    }

    public int getIntervalNum() {
        return intervalNum;
    }

    public double getLimit() {
        return limit;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "OrderCountUsage{" +
                "rateLimitType='" + rateLimitType + '\'' +
                ", interval='" + interval + '\'' +
                ", intervalNum=" + intervalNum +
                ", limit=" + limit +
                ", count=" + count +
                '}';
    }

}
