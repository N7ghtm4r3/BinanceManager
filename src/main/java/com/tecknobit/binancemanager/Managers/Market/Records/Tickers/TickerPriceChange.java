package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code TickerPriceChange} class is useful to manage TickerPriceChange requests
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class TickerPriceChange extends Ticker{

    private final double priceChange;
    private final double priceChangePercent;
    private final double weightedAvgPrice;
    private final double prevClosePrice;
    private final double lastPrice;
    private final double lastQty;
    private final double bidPrice;
    private final double bidQty;
    private final double askPrice;
    private final double askQty;
    private final double openPrice;
    private final double highPrice;
    private final double lowPrice;
    private final double volume;
    private final double quoteVolume;
    private final long openTime;
    private final long closeTime;
    private final long firstId;
    private final long lastId;
    private final int count;

    public TickerPriceChange(String symbol, double priceChange, double priceChangePercent, double weightedAvgPrice,
                             double prevClosePrice, double lastPrice, double lastQty, double bidPrice, double bidQty,
                             double askPrice, double askQty, double openPrice, double highPrice, double lowPrice,
                             double volume, double quoteVolume, long openTime, long closeTime, long firstId,
                             long lastId, int count) {
        super(symbol);
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.weightedAvgPrice = weightedAvgPrice;
        this.prevClosePrice = prevClosePrice;
        this.lastPrice = lastPrice;
        this.lastQty = lastQty;
        this.bidPrice = bidPrice;
        this.bidQty = bidQty;
        this.askPrice = askPrice;
        this.askQty = askQty;
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

    public double getBidPrice() {
        return bidPrice;
    }

    public double getBidQty() {
        return bidQty;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public double getAskQty() {
        return askQty;
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

}

