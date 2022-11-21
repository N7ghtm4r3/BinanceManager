package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account;

import org.json.JSONObject;

/**
 * The {@code IsolatedMarginAccountLimit} class is useful to format a {@code "Binance"}'s isolated margin account limit
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
 * Query Enabled Isolated Margin Account Limit (USER_DATA)</a>
 **/
public class IsolatedMarginAccountLimit {

    /**
     * {@code enabledAccount} is instance that memorizes account enabled
     * **/
    private int enabledAccount;

    /**
     * {@code maxAccount} is instance that memorizes max account enabled
     * **/
    private int maxAccount;

    /** Constructor to init {@link IsolatedMarginAccountLimit} object
     * @param enabledAccount: account enabled
     * @param maxAccount: max account enabled
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public IsolatedMarginAccountLimit(int enabledAccount, int maxAccount) {
        if (enabledAccount < 0)
            throw new IllegalArgumentException("Enabled account value cannot be less than 0");
        else
            this.enabledAccount = enabledAccount;
        if (maxAccount < 0)
            throw new IllegalArgumentException("Max account value cannot be less than 0");
        else
            this.maxAccount = maxAccount;
    }

    /**
     * Constructor to init {@link IsolatedMarginAccountLimit} object
     *
     * @param isolatedMarginAccountLimit: isolated margin account limit details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginAccountLimit(JSONObject isolatedMarginAccountLimit) {
        enabledAccount = isolatedMarginAccountLimit.getInt("enabledAccount");
        if (enabledAccount < 0)
            throw new IllegalArgumentException("Enabled account value cannot be less than 0");
        maxAccount = isolatedMarginAccountLimit.getInt("maxAccount");
        if (maxAccount < 0)
            throw new IllegalArgumentException("Max account value cannot be less than 0");
    }

    /**
     * Method to get {@link #enabledAccount} instance <br>
     * Any params required
     *
     * @return {@link #enabledAccount} instance as int
     **/
    public int getEnabledAccount() {
        return enabledAccount;
    }

    /**
     * Method to set {@link #enabledAccount}
     *
     * @param enabledAccount: account enabled
     * @throws IllegalArgumentException when account enabled is less than 0
     **/
    public void setEnabledAccount(int enabledAccount) {
        if (enabledAccount < 0)
            throw new IllegalArgumentException("Enabled account value cannot be less than 0");
        this.enabledAccount = enabledAccount;
    }

    /**
     * Method to get {@link #maxAccount} instance <br>
     * Any params required
     *
     * @return {@link #maxAccount} instance as int
     **/
    public int getMaxAccount() {
        return maxAccount;
    }

    /**
     * Method to set {@link #maxAccount}
     *
     * @param maxAccount: max account enabled
     * @throws IllegalArgumentException when max account enabled is less than 0
     **/
    public void setMaxAccount(int maxAccount) {
        if (maxAccount < 0)
            throw new IllegalArgumentException("Max account value cannot be less than 0");
        this.maxAccount = maxAccount;
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
