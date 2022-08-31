package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Fill;
import org.json.JSONObject;

/**
 * The {@code MarginAccountTrade} class is useful to format Binance Margin Account Trade request response
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginAccountTrade extends Fill {

    /**
     * {@code id} is instance that memorizes identifier of margin account trade
     * **/
    private final long id;

    /**
     * {@code isBestMatch} is instance that memorizes if is best match of the margin account trade
     * **/
    private boolean isBestMatch;

    /**
     * {@code isBuyer} is instance that memorizes if is buyer of margin account trade
     * **/
    private boolean isBuyer;

    /**
     * {@code isMaker} is instance that memorizes if is maker of margin account trade
     * **/
    private boolean isMaker;

    /**
     * {@code orderId} is instance that memorizes identifier of trade
     * **/
    private final long orderId;

    /**
     * {@code symbol} is instance that memorizes symbol of trade
     * **/
    private final String symbol;

    /**
     * {@code symbol} is instance that memorizes if is isolated trade
     * **/
    private boolean isIsolated;

    /**
     * {@code time} is instance that memorizes time of order trade
     * **/
    private final long time;

    /** Constructor to init {@link MarginAccountTrade} object
     * @param commission: commission of margin account trade
     * @param commissionAsset: commission asset of margin account trade
     * @param id: identifier of margin account trade
     * @param isBuyer: buyer of margin account trade
     * @param isMaker: maker of margin account trade
     * @param orderId: identifier of trade
     * @param price: price of order trade
     * @param qty: quantity of order trade
     * @param symbol: symbol of trade
     * @param isIsolated: if is isolated trade
     * @param time: time of order trade
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginAccountTrade(double commission, String commissionAsset, long id, boolean isBestMatch, boolean isBuyer,
                              boolean isMaker, long orderId, double price, double qty, String symbol, boolean isIsolated,
                              long time) {
        super(price, qty, commission, commissionAsset);
        this.id = id;
        this.isBestMatch = isBestMatch;
        this.isBuyer = isBuyer;
        this.isMaker = isMaker;
        this.orderId = orderId;
        this.symbol = symbol;
        this.isIsolated = isIsolated;
        this.time = time;
    }

    /**
     * Constructor to init {@link MarginAccountTrade} object
     *
     * @param marginAccountTrade: margin account trade details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginAccountTrade(JSONObject marginAccountTrade) {
        super(marginAccountTrade);
        id = marginAccountTrade.getLong("id");
        isBestMatch = marginAccountTrade.getBoolean("isBestMatch");
        isBuyer = marginAccountTrade.getBoolean("isBuyer");
        isMaker = marginAccountTrade.getBoolean("isMaker");
        orderId = marginAccountTrade.getLong("orderId");
        symbol = marginAccountTrade.getString("symbol");
        isIsolated = marginAccountTrade.getBoolean("isIsolated");
        time = marginAccountTrade.getLong("time");
    }

    public long getId() {
        return id;
    }

    public boolean isBestMatch() {
        return isBestMatch;
    }

    public void setBestMatch(boolean bestMatch) {
        isBestMatch = bestMatch;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }

    public boolean isMaker() {
        return isMaker;
    }

    public void setMaker(boolean maker) {
        isMaker = maker;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public void setIsolated(boolean isolated) {
        isIsolated = isolated;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "MarginAccountTrade{" +
                "id=" + id +
                ", isBestMatch=" + isBestMatch +
                ", isBuyer=" + isBuyer +
                ", isMaker=" + isMaker +
                ", orderId=" + orderId +
                ", symbol='" + symbol + '\'' +
                ", isIsolated=" + isIsolated +
                ", time=" + time +
                ", price=" + price +
                ", qty=" + qty +
                ", commission=" + commission +
                ", commissionAsset='" + commissionAsset + '\'' +
                '}';
    }

}
