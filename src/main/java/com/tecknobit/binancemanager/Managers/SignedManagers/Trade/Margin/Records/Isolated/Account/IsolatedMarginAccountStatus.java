package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

/**
 * The {@code IsolatedMarginAccountStatus} class is useful to format Binance Isolated Margin Account Status request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginAccountStatus {

    private boolean success;
    private final String symbol;

    public IsolatedMarginAccountStatus(boolean success, String symbol) {
        this.success = success;
        this.symbol = symbol;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSymbol() {
        return symbol;
    }

}
