package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.SavingActivityStructure.SavingActivityType;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SavingInterest} class is useful to format a saving interest
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2">
 * Get Interest History (USER_DATA) </a>
 * @see BinanceItem
 * @see SavingStructure
 **/
public class SavingInterest extends SavingStructure {

    /**
     * {@code interest} value of the saving
     **/
    private final double interest;

    /**
     * {@code lendingType} lending type of the saving interest
     **/
    private final SavingActivityType lendingType;

    /**
     * {@code productName} product name of the saving interest
     **/
    private final String productName;

    /**
     * {@code time} of the saving interest
     **/
    private final long time;

    /**
     * Constructor to init {@link SavingInterest} object
     *
     * @param asset:       asset of the saving interest
     * @param interest:    interest value of the saving
     * @param lendingType: lending type of the saving interest
     * @param productName: product name of the saving interest
     * @param time:        time of the saving interest
     **/
    public SavingInterest(String asset, double interest, SavingActivityType lendingType, String productName, long time) {
        super(asset);
        this.interest = interest;
        this.lendingType = lendingType;
        this.productName = productName;
        this.time = time;
    }

    /**
     * Constructor to init {@link SavingInterest} object
     *
     * @param jSavingInterest: saving interest details as {@link JSONObject}
     **/
    public SavingInterest(JSONObject jSavingInterest) {
        super(jSavingInterest);
        interest = hItem.getDouble("interest", 0);
        lendingType = SavingActivityType.valueOf(hItem.getString("lendingType"));
        productName = hItem.getString("productName");
        time = hItem.getLong("time", 0);
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
     * Method to get {@link #lendingType} instance <br>
     * No-any params required
     *
     * @return {@link #lendingType} instance as {@link SavingActivityType}
     **/
    public SavingActivityType getLendingType() {
        return lendingType;
    }

    /**
     * Method to get {@link #productName} instance <br>
     * No-any params required
     *
     * @return {@link #productName} instance as {@link String}
     **/
    public String getProductName() {
        return productName;
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
