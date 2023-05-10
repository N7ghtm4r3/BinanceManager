package com.tecknobit.binancemanager.managers.signedmanagers.trade.commons;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.RateLimit.RateLimitInterval;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.RateLimit.RateLimitType;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat;

/**
 * The {@code OrderCountUsage} class is useful to format an orders count usage
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
 *             Query Current Order Count Usage (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-margin-order-count-usage-trade">
 *             Query Current Margin Order Count Usage (TRADE)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public class OrderCountUsage extends BinanceItem {

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
        super(null);
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
        super(orderCountUsage);
        rateLimitType = RateLimitType.valueOf(hItem.getString("rateLimitType"));
        interval = RateLimitInterval.valueOf(hItem.getString("interval"));
        intervalNum = hItem.getInt("intervalNum", 0);
        limit = hItem.getDouble("limit", 0);
        count = hItem.getInt("count", 0);
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
     * Method to create a count usage list
     *
     * @param countUsageResponse: obtained from Binance's response
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return count usage list as {@code "format"} defines
     **/
    @Returner
    public static <T> T returnCountUsageList(String countUsageResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(countUsageResponse);
            case LIBRARY_OBJECT:
                ArrayList<OrderCountUsage> orderCountUsages = new ArrayList<>();
                JSONArray jCounts = new JSONArray(countUsageResponse);
                for (int j = 0; j < jCounts.length(); j++)
                    orderCountUsages.add(new OrderCountUsage(jCounts.getJSONObject(j)));
                return (T) orderCountUsages;
            default:
                return (T) countUsageResponse;
        }
    }

}
