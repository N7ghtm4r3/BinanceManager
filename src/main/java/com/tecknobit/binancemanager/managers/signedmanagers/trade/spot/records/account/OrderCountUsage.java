package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account;

import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.RateLimit.RateLimitInterval;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.RateLimit.RateLimitType;
import org.json.JSONObject;

/**
 * The {@code OrderCountUsage} class is useful to format an orders count usage
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
 * Query Current Order Count Usage (TRADE)</a>
 **/
public class OrderCountUsage {

    /**
     * {@code rateLimitType} is instance that memorizes rate limit type
     **/
    private final RateLimitType rateLimitType;

    /**
     * {@code interval} is instance that memorizes interval value
     **/
    private final RateLimitInterval interval;

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
    public OrderCountUsage(RateLimitType rateLimitType, RateLimitInterval interval, int intervalNum, double limit,
                           int count) {
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
        rateLimitType = RateLimitType.valueOf(orderCountUsage.getString("rateLimitType"));
        interval = RateLimitInterval.valueOf(orderCountUsage.getString("interval"));
        intervalNum = orderCountUsage.getInt("intervalNum");
        limit = orderCountUsage.getDouble("limit");
        count = orderCountUsage.getInt("count");
    }

    /**
     * Method to get {@link #rateLimitType} instance <br>
     * No-any params required
     *
     * @return {@link #rateLimitType} instance as {@link RateLimitType}
     **/
    public RateLimitType getRateLimitType() {
        return rateLimitType;
    }

    /**
     * Method to get {@link #interval} instance <br>
     * No-any params required
     *
     * @return {@link #interval} instance as {@link RateLimitInterval}
     **/
    public RateLimitInterval getInterval() {
        return interval;
    }

    /**
     * Method to get {@link #intervalNum} instance <br>
     * No-any params required
     *
     * @return {@link #intervalNum} instance as int
     **/
    public int getIntervalNum() {
        return intervalNum;
    }

    /**
     * Method to get {@link #limit} instance <br>
     * No-any params required
     *
     * @return {@link #limit} instance as double
     **/
    public double getLimit() {
        return limit;
    }

    /**
     * Method to get {@link #count} instance <br>
     * No-any params required
     *
     * @return {@link #count} instance as int
     **/
    public int getCount() {
        return count;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
