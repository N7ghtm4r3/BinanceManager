package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Details;

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
