package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

/**
 *  The {@code MarginPair} class is useful to format Binance Get All Cross Margin Pairs request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginPair {

    private final long id;
    private final String symbol;
    private final String base;
    private final String quote;
    private final boolean isMarginTrade;
    private final boolean isBuyAllowed;
    private final boolean isSellAllowed;

    public MarginPair(long id, String symbol, String base, String quote, boolean isMarginTrade, boolean isBuyAllowed,
                      boolean isSellAllowed) {
        this.id = id;
        this.symbol = symbol;
        this.base = base;
        this.quote = quote;
        this.isMarginTrade = isMarginTrade;
        this.isBuyAllowed = isBuyAllowed;
        this.isSellAllowed = isSellAllowed;
    }

    public long getId() {
        return id;
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
