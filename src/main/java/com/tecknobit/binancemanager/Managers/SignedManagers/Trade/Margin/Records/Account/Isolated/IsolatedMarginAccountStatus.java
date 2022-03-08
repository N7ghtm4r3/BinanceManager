package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.Isolated;

public class IsolatedMarginAccountStatus {

    private final boolean success;
    private final String symbol;

    public IsolatedMarginAccountStatus(boolean success, String symbol) {
        this.success = success;
        this.symbol = symbol;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSymbol() {
        return symbol;
    }

}
