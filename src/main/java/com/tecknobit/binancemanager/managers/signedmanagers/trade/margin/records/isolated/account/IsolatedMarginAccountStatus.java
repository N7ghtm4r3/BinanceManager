package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account;

import org.json.JSONObject;

/**
 * The {@code IsolatedMarginAccountStatus} class is useful to format {@code "Binance"}'s isolated margin account status
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
 * Disable Isolated Margin Account (TRADE)</a>
 **/
public class IsolatedMarginAccountStatus {

    /**
     * {@code success} is instance that memorizes if account status is success
     **/
    private boolean success;

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     **/
    private final String symbol;

    /**
     * Constructor to init {@link IsolatedMarginAccountStatus} object
     *
     * @param success: account status is success
     * @param symbol:  symbol used in the order
     **/
    public IsolatedMarginAccountStatus(boolean success, String symbol) {
        this.success = success;
        this.symbol = symbol;
    }

    /**
     * Constructor to init {@link IsolatedMarginAccountStatus} object
     *
     * @param isolatedMarginAccountStatus: isolated margin account status details as {@link JSONObject}
     **/
    public IsolatedMarginAccountStatus(JSONObject isolatedMarginAccountStatus) {
        success = isolatedMarginAccountStatus.getBoolean("success");
        symbol = isolatedMarginAccountStatus.getString("symbol");
    }

    /**
     * Method to get {@link #success} instance <br>
     * Any params required
     *
     * @return {@link #success} instance as boolean
     **/
    public boolean isSuccess() {
        return success;
    }

    /**
     * Method to set {@link #success}
     *
     * @param success: if account status is success
     **/
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * Any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
