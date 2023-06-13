package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FutureHourlyInterestRate} class is useful to format a {@code "Binance"}'s future hourly interest rate
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-a-future-hourly-interest-rate-user_data">
 * Get a future hourly interest rate (USER_DATA)</a>
 * @see BinanceItem
 */
public class FutureHourlyInterestRate extends BinanceItem {

    /**
     * {@code asset} of the interest rate
     */
    private final String asset;

    /**
     * {@code nextHourlyInterestRate} value of the next interest rate
     */
    private final double nextHourlyInterestRate;

    /**
     * Constructor to init {@link FutureHourlyInterestRate}
     *
     * @param asset                  : asset of the interest rate
     * @param nextHourlyInterestRate : value of the next interest rate
     */
    public FutureHourlyInterestRate(String asset, double nextHourlyInterestRate) {
        super(null);
        this.asset = asset;
        this.nextHourlyInterestRate = nextHourlyInterestRate;
    }

    /**
     * Constructor to init {@link FutureHourlyInterestRate}
     *
     * @param jFutureHourlyInterestRate : future hourly interest rate details as {@link JSONObject}
     */
    public FutureHourlyInterestRate(JSONObject jFutureHourlyInterestRate) {
        super(jFutureHourlyInterestRate);
        asset = hItem.getString("asset");
        nextHourlyInterestRate = hItem.getDouble("nextHourlyInterestRate", 0);
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #nextHourlyInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #nextHourlyInterestRate} instance as double
     */
    public double getNextHourlyInterestRate() {
        return nextHourlyInterestRate;
    }

    /**
     * Method to get {@link #nextHourlyInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #nextHourlyInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getNextHourlyInterestRate(int decimals) {
        return roundValue(nextHourlyInterestRate, decimals);
    }

}
