package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code TickerPriceChange} class is useful to manage TickerPriceChange requests
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
 *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class TickerPriceChange extends OrderBookTicker{

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

    public double getPriceChange() {
        return priceChange;
    }

    public double getPriceChangePercent() {
        return priceChangePercent;
    }

    public double getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    public double getPrevClosePrice() {
        return prevClosePrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public double getLastQty() {
        return lastQty;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getVolume() {
        return volume;
    }

    public double getQuoteVolume() {
        return quoteVolume;
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

