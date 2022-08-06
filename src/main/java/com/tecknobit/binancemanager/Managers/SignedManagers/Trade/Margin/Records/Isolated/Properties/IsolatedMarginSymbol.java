package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code IsolatedMarginSymbol} class is useful to format Binance Isolated Margin Symbol request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginSymbol {

    /**
     * {@code symbol} is instance that memorizes symbol of asset
     * **/
    private final String symbol;

    /**
     * {@code base} is instance that memorizes base asset
     * **/
    private final String base;

    /**
     * {@code quote} is instance that memorizes quote asset
     * **/
    private final String quote;

    /**
     * {@code isMarginTrade} is instance that memorizes if is margin trade
     * **/
    private final boolean isMarginTrade;

    /**
     * {@code isBuyAllowed} is instance that memorizes if is buy allowed
     * **/
    private final boolean isBuyAllowed;

    /**
     * {@code isSellAllowed} is instance that memorizes if is sell allowed
     * **/
    private final boolean isSellAllowed;

    /** Constructor to init {@link IsolatedMarginSymbol} object
     * @param symbol: symbol of asset
     * @param base: base asset
     * @param quote: quote asset
     * @param isMarginTrade: is margin trade
     * @param isBuyAllowed: is buy allowed
     * @param isSellAllowed: is sell allowed
     * **/
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

    @Override
    public String toString() {
        return "IsolatedMarginSymbol{" +
                "symbol='" + symbol + '\'' +
                ", base='" + base + '\'' +
                ", quote='" + quote + '\'' +
                ", isMarginTrade=" + isMarginTrade +
                ", isBuyAllowed=" + isBuyAllowed +
                ", isSellAllowed=" + isSellAllowed +
                '}';
    }

}
