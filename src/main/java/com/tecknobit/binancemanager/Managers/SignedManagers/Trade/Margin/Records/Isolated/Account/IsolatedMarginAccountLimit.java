package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

/**
 * The {@code IsolatedMarginAccountLimit} class is useful to format Binance Isolated Margin Account Limit request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

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
        if(enabledAccount < 0)
            throw new IllegalArgumentException("Enabled account value cannot be less than 0");
        else
            this.enabledAccount = enabledAccount;
        if(maxAccount < 0)
            throw new IllegalArgumentException("Max account value cannot be less than 0");
        else
            this.maxAccount = maxAccount;
    }

    public int getEnabledAccount() {
        return enabledAccount;
    }

    /** Method to set {@link #enabledAccount}
     * @param enabledAccount: account enabled
     * @throws IllegalArgumentException when account enabled is less than 0
     * **/
    public void setEnabledAccount(int enabledAccount) {
        if(enabledAccount < 0)
            throw new IllegalArgumentException("Enabled account value cannot be less than 0");
        this.enabledAccount = enabledAccount;
    }

    public int getMaxAccount() {
        return maxAccount;
    }

    /** Method to set {@link #maxAccount}
     * @param maxAccount: max account enabled
     * @throws IllegalArgumentException when max account enabled is less than 0
     * **/
    public void setMaxAccount(int maxAccount) {
        if(maxAccount < 0)
            throw new IllegalArgumentException("Max account value cannot be less than 0");
        this.maxAccount = maxAccount;
    }

}
