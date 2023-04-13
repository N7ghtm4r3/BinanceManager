package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.SavingRecordStructure;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FixedActivityProductRedemption} class is useful to format a fixed/activity product redemption record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
 * Get Redemption Record (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingRecordStructure
 * @see RedemptionRecord
 **/
public class FixedActivityProductRedemption extends RedemptionRecord {

    /**
     * {@code principal} of the fixed/activity product redemption record
     **/
    private final double interest;

    /**
     * {@code startTime} start time of the fixed/activity product redemption record
     **/
    private final long startTime;

    /**
     * Constructor to init {@link FixedActivityProductRedemption} object
     *
     * @param asset:       asset of the fixed/activity product redemption record
     * @param amount:      amount of the fixed/activity product redemption record
     * @param createTime:  create time of the fixed/activity product redemption record
     * @param status:      status of the fixed/activity product redemption record
     * @param principal:   principal of the fixed/activity product redemption record
     * @param projectId:   project id of the fixed/activity product redemption record
     * @param projectName: project name of the fixed/activity product redemption record
     * @param interest:    interest of the fixed/activity product redemption record
     * @param startTime:   start time of the fixed/activity product redemption record
     **/
    public FixedActivityProductRedemption(String asset, double amount, long createTime, String status, double principal,
                                          String projectId, String projectName, double interest, long startTime) {
        super(asset, amount, createTime, status, principal, projectId, projectName);
        this.interest = interest;
        this.startTime = startTime;
    }

    /**
     * Constructor to init {@link FixedActivityProductRedemption} object
     *
     * @param jFixedActivityProductRedemption: fixed activity product redemption details as {@link JSONObject}
     **/
    public FixedActivityProductRedemption(JSONObject jFixedActivityProductRedemption) {
        super(jFixedActivityProductRedemption);
        interest = hItem.getDouble("interest", 0);
        startTime = hItem.getLong("startTime", 0);
    }

    /**
     * Method to get {@link #interest} instance <br>
     * No-any params required
     *
     * @return {@link #interest} instance as double
     **/
    public double getInterest() {
        return interest;
    }

    /**
     * Method to get {@link #interest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #interest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInterest(int decimals) {
        return roundValue(interest, decimals);
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as long
     **/
    public long getStartTime() {
        return startTime;
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as {@link Date}
     **/
    public Date getStartDate() {
        return TimeFormatter.getDate(startTime);
    }

}
