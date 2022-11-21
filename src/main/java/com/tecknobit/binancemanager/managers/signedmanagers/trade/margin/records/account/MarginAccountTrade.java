package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Fill;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code MarginAccountTrade} class is useful to format a {@code "Binance"} margin account trade
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">
 * Enable Isolated Margin Account (TRADE)</a>
 * @see Fill
 **/
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

    /**
     * Method to get {@link #id} instance <br>
     * Any params required
     *
     * @return {@link #id} instance as long
     **/
    public long getId() {
        return id;
    }

    /**
     * Method to get {@link #isBestMatch} instance <br>
     * Any params required
     *
     * @return {@link #isBestMatch} instance as boolean
     **/
    public boolean isBestMatch() {
        return isBestMatch;
    }

    /**
     * Method to set {@link #isBestMatch}
     *
     * @param bestMatch: if is best match of the margin account trade
     **/
    public void setBestMatch(boolean bestMatch) {
        isBestMatch = bestMatch;
    }

    /**
     * Method to get {@link #isBuyer} instance <br>
     * Any params required
     *
     * @return {@link #isBuyer} instance as boolean
     **/
    public boolean isBuyer() {
        return isBuyer;
    }

    /**
     * Method to set {@link #isBuyer}
     *
     * @param buyer: if is buyer of margin account trade
     **/
    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }

    /**
     * Method to get {@link #isMaker} instance <br>
     * Any params required
     *
     * @return {@link #isMaker} instance as boolean
     **/
    public boolean isMaker() {
        return isMaker;
    }

    /**
     * Method to set {@link #isMaker}
     *
     * @param maker: if is maker of margin account trade
     **/
    public void setMaker(boolean maker) {
        isMaker = maker;
    }

    /**
     * Method to get {@link #orderId} instance <br>
     * Any params required
     *
     * @return {@link #orderId} instance as long
     **/
    public long getOrderId() {
        return orderId;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * Any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #isIsolated} instance <br>
     * Any params required
     *
     * @return {@link #isIsolated} instance as boolean
     **/
    public boolean isIsolated() {
        return isIsolated;
    }

    /**
     * Method to set {@link #isIsolated}
     *
     * @param isolated: if is isolated trade
     **/
    public void setIsolated(boolean isolated) {
        isIsolated = isolated;
    }

    /**
     * Method to get {@link #time} instance <br>
     * Any params required
     *
     * @return {@link #time} instance as long
     **/
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * Any params required
     *
     * @return {@link #time} instance as {@link Date}
     **/
    public Date getTransactionDate() {
        return TimeFormatter.getDate(time);
    }

}
