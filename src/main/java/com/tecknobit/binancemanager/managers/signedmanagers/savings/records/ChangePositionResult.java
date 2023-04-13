package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code ChangePositionResult} class is useful to format a change position result
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
 * Change Fixed/Activity Position to Daily Position(USER_DATA) </a>
 * @see BinanceItem
 **/
public class ChangePositionResult extends BinanceItem {

    /**
     * {@code dailyPurchaseId} daily purchase id of the change position result
     **/
    private final long dailyPurchaseId;

    /**
     * {@code success} whether the change position has been successful
     **/
    private final boolean success;

    /**
     * {@code time} of the change position result
     **/
    private final long time;

    /**
     * Constructor to init {@link ChangePositionResult} object
     *
     * @param dailyPurchaseId: daily purchase id of the change position result
     * @param success:         whether the change position has been successful
     * @param time:            time of the change position result
     **/
    public ChangePositionResult(long dailyPurchaseId, boolean success, long time) {
        super(null);
        this.dailyPurchaseId = dailyPurchaseId;
        this.success = success;
        this.time = time;
    }

    /**
     * Constructor to init {@link ChangePositionResult} object
     *
     * @param jChangePositionResult: change position result details as {@link JSONObject}
     **/
    public ChangePositionResult(JSONObject jChangePositionResult) {
        super(jChangePositionResult);
        dailyPurchaseId = hItem.getLong("dailyPurchaseId", 0);
        success = hItem.getBoolean("success");
        time = hItem.getLong("time", 0);
    }

    /**
     * Method to get {@link #dailyPurchaseId} instance <br>
     * No-any params required
     *
     * @return {@link #dailyPurchaseId} instance as long
     **/
    public long getDailyPurchaseId() {
        return dailyPurchaseId;
    }

    /**
     * Method to get {@link #success} instance <br>
     * No-any params required
     *
     * @return {@link #success} instance as boolean
     **/
    public boolean isSuccess() {
        return success;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as long
     **/
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     **/
    public Date getDate() {
        return TimeFormatter.getDate(time);
    }

}
