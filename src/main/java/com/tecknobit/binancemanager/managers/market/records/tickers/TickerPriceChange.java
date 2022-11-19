package com.tecknobit.binancemanager.managers.market.records.tickers;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code TickerPriceChange} class is useful to manage TickerPriceChange requests
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
 * https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
 **/

public class TickerPriceChange extends OrderBookTicker {

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
     * {@code prevClosePrice} is instance that contains previous close price of the ticker
     * **/
    private final double prevClosePrice;

    /**
     * {@code lastPrice} is instance that contains last price of the ticker
     * **/
    private final double lastPrice;

    /**
     * {@code lastQty} is instance that contains last quantity of the ticker
     * **/
    private final double lastQty;

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

    /** Constructor to init {@link TickerPriceChange} object
     * @param symbol: symbol of the ticker
     * @param priceChange: price change of the ticker
     * @param priceChangePercent: price change percent of the ticker
     * @param weightedAvgPrice: weighted average price of the ticker
     * @param prevClosePrice: previous close price of the ticker
     * @param lastPrice: last price of the ticker
     * @param lastQty: last quantity of the ticker
     * @param bidPrice: bids price in the order book ticker
     * @param bidQty: bids quantity in the order book ticker
     * @param askPrice: ask price in the order book ticker
     * @param askQty: ask quantity in the order book ticker
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
    public TickerPriceChange(String symbol, double priceChange, double priceChangePercent, double weightedAvgPrice,
                             double prevClosePrice, double lastPrice, double lastQty, double bidPrice, double bidQty,
                             double askPrice, double askQty, double openPrice, double highPrice, double lowPrice,
                             double volume, double quoteVolume, long openTime, long closeTime, long firstId,
                             long lastId, int count) {
        super(symbol, bidPrice, bidQty, askPrice, askQty);
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.weightedAvgPrice = weightedAvgPrice;
        this.prevClosePrice = prevClosePrice;
        this.lastPrice = lastPrice;
        this.lastQty = lastQty;
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

    /** Constructor to init {@link TickerPriceChange} object
     * @param ticker: ticker price change details as {@link JSONObject}
     * **/
    public TickerPriceChange(JSONObject ticker) {
        super(ticker);
        priceChange = hTicker.getDouble("priceChange");
        priceChangePercent = hTicker.getDouble("priceChangePercent");
        weightedAvgPrice = hTicker.getDouble("weightedAvgPrice");
        prevClosePrice = hTicker.getDouble("prevClosePrice");
        lastPrice = hTicker.getDouble("openPrice");
        lastQty = hTicker.getDouble("lastQty");
        openPrice = hTicker.getDouble("highPrice");
        highPrice = hTicker.getDouble("lowPrice");
        lowPrice = hTicker.getDouble("lastPrice");
        volume = hTicker.getDouble("volume");
        quoteVolume = hTicker.getDouble("quoteVolume");
        openTime = hTicker.getLong("openTime");
        closeTime = hTicker.getLong("closeTime");
        firstId = hTicker.getLong("firstId");
        lastId = hTicker.getLong("lastId");
        count = hTicker.getInt("count");
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getBidPrice() {
        return super.getBidPrice();
    }

    /**
     * Method to get {@link #bidPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #bidPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getBidPrice(int decimals) {
        return super.getBidPrice(decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getBidQty() {
        return super.getBidQty();
    }

    /**
     * Method to get {@link #bidQty} instance
     * @param decimals: number of digits to round final value
     * @return {@link #bidQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getBidQty(int decimals) {
        return super.getBidQty(decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getAskPrice() {
        return super.getAskPrice();
    }

    /**
     * Method to get {@link #askPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #askPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getAskPrice(int decimals) {
        return super.getAskPrice(decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getAskQty() {
        return super.getAskQty();
    }

    /**
     * Method to get {@link #askQty} instance
     * @param decimals: number of digits to round final value
     * @return {@link #askQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    @Override
    public double getAskQty(int decimals) {
        return super.getAskQty(decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getPriceChange() {
        return priceChange;
    }

    /**
     * Method to get {@link #priceChange} instance
     * @param decimals: number of digits to round final value
     * @return {@link #priceChange} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getPriceChange(int decimals) {
        return roundValue(priceChange, decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getPriceChangePercent() {
        return priceChangePercent;
    }

    /**
     * Method to get {@link #priceChangePercent} instance
     * @param decimals: number of digits to round final value
     * @return {@link #priceChangePercent} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getPriceChangePercent(int decimals) {
        return roundValue(priceChangePercent, decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    /**
     * Method to get {@link #weightedAvgPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #weightedAvgPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getWeightedAvgPrice(int decimals) {
        return roundValue(weightedAvgPrice, decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getPrevClosePrice() {
        return prevClosePrice;
    }

    /**
     * Method to get {@link #prevClosePrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #prevClosePrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getPrevClosePrice(int decimals) {
        return roundValue(prevClosePrice, decimals);
    }

    public double getLastPrice() {
        return lastPrice;
    }

    /**
     * Method to get {@link #lastPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #lastPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLastPrice(int decimals) {
        return roundValue(lastPrice, decimals);
    }

    /**
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getLastQty() {
        return lastQty;
    }

    /**
     * Method to get {@link #lastQty} instance
     * @param decimals: number of digits to round final value
     * @return {@link #lastQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * @implSpec if this rolling ticket has been created with {@link Ticker#MINI_TYPE_RESPONSE} type response this instance will be return
     * -1234567890 by default
     **/
    public double getLastQty(int decimals) {
        return roundValue(lastQty, decimals);
    }

    public double getOpenPrice() {
        return openPrice;
    }

    /**
     * Method to get {@link #openPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #openPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOpenPrice(int decimals) {
        return roundValue(lastPrice, decimals);
    }

    public double getHighPrice() {
        return highPrice;
    }

    /**
     * Method to get {@link #highPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #highPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getHighPrice(int decimals) {
        return roundValue(highPrice, decimals);
    }

    public double getLowPrice() {
        return lowPrice;
    }

    /**
     * Method to get {@link #lowPrice} instance
     * @param decimals: number of digits to round final value
     * @return {@link #lowPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLowPrice(int decimals) {
        return roundValue(lowPrice, decimals);
    }

    public double getVolume() {
        return volume;
    }

    /**
     * Method to get {@link #volume} instance
     * @param decimals: number of digits to round final value
     * @return {@link #volume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getVolume(int decimals) {
        return roundValue(volume, decimals);
    }

    public double getQuoteVolume() {
        return quoteVolume;
    }

    /**
     * Method to get {@link #quoteVolume} instance
     * @param decimals: number of digits to round final value
     * @return {@link #quoteVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteVolume(int decimals) {
        return roundValue(quoteVolume, decimals);
    }

    public long getOpenTime() {
        return openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public long getFirstId() {
        return firstId;
    }

    public long getLastId() {
        return lastId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "TickerPriceChange{" +
                "priceChange=" + priceChange +
                ", priceChangePercent=" + priceChangePercent +
                ", weightedAvgPrice=" + weightedAvgPrice +
                ", prevClosePrice=" + prevClosePrice +
                ", lastPrice=" + lastPrice +
                ", lastQty=" + lastQty +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", volume=" + volume +
                ", quoteVolume=" + quoteVolume +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", firstId=" + firstId +
                ", lastId=" + lastId +
                ", count=" + count +
                ", bidPrice=" + bidPrice +
                ", bidQty=" + bidQty +
                ", askPrice=" + askPrice +
                ", askQty=" + askQty +
                ", symbol='" + symbol + '\'' +
                '}';
    }

}

