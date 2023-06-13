package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code TradeFee} class is useful to format a {@code "Binance"}'s trade fee
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
 * Trade Fee (USER_DATA)</a>
 */
public class TradeFee {

    /**
     * {@code symbol} is instance that memorizes symbol value
     */
    private final String symbol;

    /**
     * {@code makerCommission} is instance that memorizes maker commission value
     */
    private final double makerCommission;

    /**
     * {@code takerCommission} is instance that memorizes taker commission value
     */
    private final double takerCommission;

    /** Constructor to init {@link TradeFee} object
     * @param symbol: symbol value
     * @param makerCommission: maker commission value
     * @param takerCommission: taker commission value
     */
    public TradeFee(String symbol, double makerCommission, double takerCommission) {
        this.symbol = symbol;
        this.makerCommission = makerCommission;
        this.takerCommission = takerCommission;
    }

    /**
     * Constructor to init {@link TradeFee} object
     *
     * @param jTradeFee: trade fee details as {@link JSONObject}
     */
    public TradeFee(JSONObject jTradeFee) {
        this(jTradeFee.getString("symbol"), jTradeFee.getDouble("makerCommission"),
                jTradeFee.getDouble("takerCommission"));
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #makerCommission} instance <br>
     * No-any params required
     *
     * @return {@link #makerCommission} instance as double
     */
    public double getMakerCommission() {
        return makerCommission;
    }

    /**
     * Method to get {@link #makerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #makerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMakerCommission(int decimals) {
        return roundValue(makerCommission, decimals);
    }

    /**
     * Method to get {@link #takerCommission} instance <br>
     * No-any params required
     *
     * @return {@link #takerCommission} instance as double
     */
    public double getTakerCommission() {
        return takerCommission;
    }

    /**
     * Method to get {@link #takerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTakerCommission(int decimals) {
        return roundValue(takerCommission, decimals);
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


