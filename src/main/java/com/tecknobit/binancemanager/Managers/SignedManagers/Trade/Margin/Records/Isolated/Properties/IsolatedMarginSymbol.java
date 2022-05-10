package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code IsolatedMarginSymbol} class is useful to format Binance Isolated Margin Symbol request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginSymbol {

    private final String symbol;
    private final String base;
    private final String quote;
    private final boolean isMarginTrade;
    private final boolean isBuyAllowed;
    private final boolean isSellAllowed;

    public IsolatedMarginSymbol(String symbol, String base, String quote, boolean isMarginTrade, boolean isBuyAllowed,
                                boolean isSellAllowed) {
        this.symbol = symbol;
        this.base = base;
        this.quote = quote;
        this.isMarginTrade = isMarginTrade;
        this.isBuyAllowed = isBuyAllowed;
        this.isSellAllowed = isSellAllowed;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }

    public boolean isMarginTrade() {
        return isMarginTrade;
    }

    public boolean isBuyAllowed() {
        return isBuyAllowed;
    }

    public boolean isSellAllowed() {
        return isSellAllowed;
    }

}
