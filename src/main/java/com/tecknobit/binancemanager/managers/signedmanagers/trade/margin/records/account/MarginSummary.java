package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginSummary} class is useful to format a {@code "Binance"}'s summary for a margin account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-margin-account-user_data">
 * Get Summary of Margin account (USER_DATA)</a>
 */
public class MarginSummary {

    /**
     * {@code normalBar} normal bar value
     */
    private final double normalBar;

    /**
     * {@code marginCallBar} margin call bar value
     */
    private final double marginCallBar;

    /**
     * {@code forceLiquidationBar} force liquidation bar value
     */
    private final double forceLiquidationBar;

    /**
     * Constructor to init {@link MarginSummary} object
     *
     * @param normalBar:           normal bar value
     * @param marginCallBar:       margin call bar value
     * @param forceLiquidationBar: force liquidation bar value
     */
    public MarginSummary(double normalBar, double marginCallBar, double forceLiquidationBar) {
        this.normalBar = normalBar;
        this.marginCallBar = marginCallBar;
        this.forceLiquidationBar = forceLiquidationBar;
    }

    /**
     * Constructor to init {@link MarginSummary} object
     *
     * @param jSummary: summary details as {@link JSONObject}
     */
    public MarginSummary(JSONObject jSummary) {
        this(jSummary.getDouble("normalBar"), jSummary.getDouble("marginCallBar"),
                jSummary.getDouble("forceLiquidationBar"));
    }

    /**
     * Method to get {@link #normalBar} instance <br>
     * No-any params required
     *
     * @return {@link #normalBar} instance as double
     */
    public double getNormalBar() {
        return normalBar;
    }

    /**
     * Method to get {@link #normalBar} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #normalBar} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getNormalBar(int decimals) {
        return roundValue(normalBar, decimals);
    }

    /**
     * Method to get {@link #marginCallBar} instance <br>
     * No-any params required
     *
     * @return {@link #marginCallBar} instance as double
     */
    public double getMarginCallBar() {
        return marginCallBar;
    }

    /**
     * Method to get {@link #marginCallBar} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginCallBar} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMarginCallBar(int decimals) {
        return roundValue(marginCallBar, decimals);
    }

    /**
     * Method to get {@link #forceLiquidationBar} instance <br>
     * No-any params required
     *
     * @return {@link #forceLiquidationBar} instance as double
     */
    public double getForceLiquidationBar() {
        return forceLiquidationBar;
    }

    /**
     * Method to get {@link #forceLiquidationBar} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #forceLiquidationBar} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getForceLiquidationBar(int decimals) {
        return roundValue(forceLiquidationBar, decimals);
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
