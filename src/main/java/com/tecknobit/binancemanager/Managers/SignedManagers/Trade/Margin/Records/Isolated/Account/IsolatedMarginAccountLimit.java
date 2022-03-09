package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

/**
 * The {@code IsolatedMarginAccountLimit} class is useful to format Binance Isolated Margin Account Limit request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginAccountLimit {

    private final int enabledAccount;
    private final int maxAccount;

    public IsolatedMarginAccountLimit(int enabledAccount, int maxAccount) {
        this.enabledAccount = enabledAccount;
        this.maxAccount = maxAccount;
    }

    public int getEnabledAccount() {
        return enabledAccount;
    }

    public int getMaxAccount() {
        return maxAccount;
    }

}
