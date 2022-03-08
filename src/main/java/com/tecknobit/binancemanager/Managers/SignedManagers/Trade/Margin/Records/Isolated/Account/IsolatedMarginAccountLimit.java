package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

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
