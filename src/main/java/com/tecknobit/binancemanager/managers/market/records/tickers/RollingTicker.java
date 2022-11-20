package com.tecknobit.binancemanager.managers.market.records.tickers;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code RollingTicker} class is useful to format a rolling ticker object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
 * Rolling window price change statistics</a>
 * @see Ticker
 **/
public class RollingTicker extends Ticker {

    /**
     * {@code priceChange} is instance that contains price change of the ticker
     * **/
    private final double priceChange;

    /**
     * {@code priceChangePercent} is instance that contains price change percent of the ticker
     * **/
    private final double priceChangePercent;

    /**
     * {@code weightedAvgPrice} is instance that contains weighted average price of the ticker
     * **/
    private final double weightedAvgPrice;

    /**
     * {@code lastPrice} is instance that contains last price of the ticker
     * **/
    private final double lastPrice;

    /**
     * {@code openPrice} is instance that contains open price of the ticker
     * **/
    private final double openPrice;

    /**
     * {@code highPrice} is instance that contains high price of the ticker
     * **/
    private final double highPrice;

    /**
     * {@code lowPrice} is instance that contains low price of the ticker
     * **/
    private final double lowPrice;

    /**
     * {@code volume} is instance that contains volume of the ticker
     * **/
    private final double volume;

    /**
     * {@code quoteVolume} is instance that contains quote volume of the ticker
     * **/
    private final double quoteVolume;

    /**
     * {@code openTime} is instance that contains open time of the ticker
     * **/
    private final long openTime;

    /**
     * {@code closeTime} is instance that contains close time of the ticker
     * **/
    private final long closeTime;

    /**
     * {@code firstId} is instance that contains first id of the ticker
     * **/
    private final long firstId;

    /**
     * {@code lastId} is instance that contains last id of the ticker
     * **/
    private final long lastId;

    /**
     * {@code count} is instance that contains count number in the ticker
     * **/
    private final int count;

    /** Constructor to init {@link RollingTicker} object
     * @param symbol: symbol of the ticker
     * @param priceChange: price change of the ticker
     * @param priceChangePercent: price change percent of the ticker
     * @param weightedAvgPrice: weighted average price of the ticker
     * @param lastPrice: last price of the ticker
     * @param openPrice: open price of the ticker
     * @param highPrice: high price of the ticker
     * @param lowPrice: low price of the ticker
     * @param volume: volume of the ticker
     * @param quoteVolume: quote volume of the ticker
     * @param openTime: open time of the ticker
     * @param closeTime: close time of the ticker
     * @param firstId: first id of the ticker
     * @param lastId: last id of the ticker
     * @param count: count number in the ticker
     * **/
    public RollingTicker(String symbol, double priceChange, double priceChangePercent, double weightedAvgPrice,
                         double lastPrice, double openPrice, double highPrice, double lowPrice, double volume,
                         double quoteVolume, long openTime, long closeTime, long firstId, long lastId, int count) {
        super(symbol);
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.weightedAvgPrice = weightedAvgPrice;
        this.lastPrice = lastPrice;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.firstId = firstId;
        this.lastId = lastId;
        this.count = count;
    }

    /** Constructor to init {@link RollingTicker} object
     * @param rollingTicker: rolling ticker details as {@link JSONObject}
     * **/
    public RollingTicker(JSONObject rollingTicker) {
        super(rollingTicker.getString("symbol"));
        JsonHelper hRollingTicker = new JsonHelper(rollingTicker);
        priceChange = hRollingTicker.getDouble("priceChange", 0);
        priceChangePercent = hRollingTicker.getDouble("priceChangePercent", 0);
        weightedAvgPrice = hRollingTicker.getDouble("weightedAvgPrice", 0);
        lastPrice = hRollingTicker.getDouble("openPrice", 0);
        openPrice = hRollingTicker.getDouble("highPrice", 0);
        highPrice = hRollingTicker.getDouble("lowPrice", 0);
        lowPrice = hRollingTicker.getDouble("lastPrice", 0);
        volume = hRollingTicker.getDouble("volume", 0);
        quoteVolume = hRollingTicker.getDouble("quoteVolume", 0);
        openTime = hRollingTicker.getLong("openTime", 0);
        closeTime = hRollingTicker.getLong("closeTime", 0);
        firstId = hRollingTicker.getLong("firstId", 0);
        lastId = hRollingTicker.getLong("lastId", 0);
        count = hRollingTicker.getInt("count", 0);
    }

    /**
     * Method to get {@link #priceChange} instance <br>
     * Any params required
     *
     * @return {@link #priceChange} instance as double
     * @implSpec if this rolling ticket has been created with {@link ResponseType#MINI} type response this instance will be return
     * 0 by default
     **/
    public double getPriceChange() {
        return priceChange;
    }

    /**
     * Method to get {@link #priceChange} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #priceChange} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link ResponseType#MINI} type response this instance will be return
     * 0 by default
     **/
    public double getPriceChange(int decimals) {
        return roundValue(priceChange, decimals);
    }

    /**
     * Method to get {@link #priceChangePercent} instance <br>
     * Any params required
     *
     * @return {@link #priceChangePercent} instance as double
     * @implSpec if this rolling ticket has been created with {@link ResponseType#MINI} type response this instance will be return
     * 0 by default
     **/
    public double getPriceChangePercent() {
        return priceChangePercent;
    }

    /**
     * Method to get {@link #priceChangePercent} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #priceChangePercent} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link ResponseType#MINI} type response this instance will be return
     * 0 by default
     **/
    public double getPriceChangePercent(int decimals) {
        return roundValue(priceChangePercent, decimals);
    }

    /**
     * Method to get {@link #weightedAvgPrice} instance <br>
     * Any params required
     *
     * @return {@link #weightedAvgPrice} instance as double
     * @implSpec if this rolling ticket has been created with {@link ResponseType#MINI} type response this instance will be return
     * 0 by default
     **/
    public double getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    /**
     * Method to get {@link #weightedAvgPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #weightedAvgPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link ResponseType#MINI} type response this instance will be return
     * 0 by default
     **/
    public double getWeightedAvgPrice(int decimals) {
        return roundValue(weightedAvgPrice, decimals);
    }

    /**
     * Method to get {@link #lastPrice} instance <br>
     * Any params required
     *
     * @return {@link #lastPrice} instance as double
     **/
    public double getLastPrice() {
        return lastPrice;
    }

    /**
     * Method to get {@link #lastPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastPrice(int decimals) {
        return roundValue(lastPrice, decimals);
    }

    /**
     * Method to get {@link #openPrice} instance <br>
     * Any params required
     *
     * @return {@link #openPrice} instance as double
     **/
    public double getOpenPrice() {
        return openPrice;
    }

    /**
     * Method to get {@link #openPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #openPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOpenPrice(int decimals) {
        return roundValue(lastPrice, decimals);
    }

    /**
     * Method to get {@link #highPrice} instance <br>
     * Any params required
     *
     * @return {@link #highPrice} instance as double
     **/
    public double getHighPrice() {
        return highPrice;
    }

    /**
     * Method to get {@link #highPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #highPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getHighPrice(int decimals) {
        return roundValue(highPrice, decimals);
    }

    /**
     * Method to get {@link #lowPrice} instance <br>
     * Any params required
     *
     * @return {@link #lowPrice} instance as double
     **/
    public double getLowPrice() {
        return lowPrice;
    }

    /**
     * Method to get {@link #lowPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lowPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLowPrice(int decimals) {
        return roundValue(lowPrice, decimals);
    }

    /**
     * Method to get {@link #volume} instance <br>
     * Any params required
     *
     * @return {@link #volume} instance as double
     **/
    public double getVolume() {
        return volume;
    }

    /**
     * Method to get {@link #volume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #volume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getVolume(int decimals) {
        return roundValue(volume, decimals);
    }

    /**
     * Method to get {@link #quoteVolume} instance <br>
     * Any params required
     *
     * @return {@link #quoteVolume} instance as double
     **/
    public double getQuoteVolume() {
        return quoteVolume;
    }

    /**
     * Method to get {@link #quoteVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteVolume(int decimals) {
        return roundValue(quoteVolume, decimals);
    }

    /**
     * Method to get {@link #openTime} instance <br>
     * Any params required
     *
     * @return {@link #openTime} instance as long
     **/
    public long getOpenTime() {
        return openTime;
    }

    /**
     * Method to get {@link #closeTime} instance <br>
     * Any params required
     *
     * @return {@link #closeTime} instance as long
     **/
    public long getCloseTime() {
        return closeTime;
    }

    /**
     * Method to get {@link #firstId} instance <br>
     * Any params required
     *
     * @return {@link #firstId} instance as long
     **/
    public long getFirstId() {
        return firstId;
    }

    /**
     * Method to get {@link #lastId} instance <br>
     * Any params required
     *
     * @return {@link #lastId} instance as long
     **/
    public long getLastId() {
        return lastId;
    }

    /**
     * Method to get {@link #count} instance <br>
     * Any params required
     *
     * @return {@link #count} instance as int
     **/
    public int getCount() {
        return count;
    }

}
