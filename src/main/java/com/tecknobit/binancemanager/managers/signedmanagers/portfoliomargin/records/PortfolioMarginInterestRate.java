package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PortfolioMarginInterestRate} class is useful to create a portfolio margin interest rate
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-rate-market_data">
 * Query Portfolio Margin Interest Rate (MARKET_DATA)</a>
 * @see BinanceItem
 * @deprecated this object will be removed in the next release
 */
@Deprecated
public class PortfolioMarginInterestRate extends BinanceItem {

    /**
     * {@code asset} of the margin interest rate
     */
    private final String asset;

    /**
     * {@code dailyInterest} daily interest rate
     */
    private final double dailyInterest;

    /**
     * {@code yearlyInterest} annual interest rate
     */
    private final double yearlyInterest;

    /**
     * Constructor to init {@link PortfolioMarginInterestRate}
     *
     * @param asset          : asset of the margin interest rate
     * @param dailyInterest  : daily interest rate
     * @param yearlyInterest : annual interest rate
     */
    public PortfolioMarginInterestRate(String asset, double dailyInterest, double yearlyInterest) {
        super(null);
        this.asset = asset;
        this.dailyInterest = dailyInterest;
        this.yearlyInterest = yearlyInterest;
    }

    /**
     * Constructor to init {@link PortfolioMarginInterestRate}
     *
     * @param jPortfolioMarginInterestRate : margin interest rate details as {@link JSONObject}
     */
    public PortfolioMarginInterestRate(JSONObject jPortfolioMarginInterestRate) {
        super(jPortfolioMarginInterestRate);
        asset = hItem.getString("asset");
        dailyInterest = hItem.getDouble("dailyInterest", 0);
        yearlyInterest = hItem.getDouble("yearlyInterest", 0);
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
     * Method to get {@link #dailyInterest} instance <br>
     * No-any params required
     *
     * @return {@link #dailyInterest} instance as double
     */
    public double getDailyInterest() {
        return dailyInterest;
    }

    /**
     * Method to get {@link #dailyInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getDailyInterest(int decimals) {
        return roundValue(dailyInterest, decimals);
    }

    /**
     * Method to get {@link #yearlyInterest} instance <br>
     * No-any params required
     *
     * @return {@link #yearlyInterest} instance as double
     */
    public double getYearlyInterest() {
        return yearlyInterest;
    }

    /**
     * Method to get {@link #yearlyInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #yearlyInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getYearlyInterest(int decimals) {
        return roundValue(yearlyInterest, decimals);
    }

}
