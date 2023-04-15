package com.tecknobit.binancemanager.managers.marketstreams.records;

import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class WbsKline extends WbsMarketItem {

    private final long klineStartTime;
    private final long klineCloseTime;
    private final Interval interval;
    private final long firstTradeId;
    private final long lastTradeId;
    private final double baseAssetVolume;
    private final int numberOfTrades;
    private final boolean isClosed;
    private final double quoteAssetVolume;
    private final double takerBuyBaseAssetVolume;
    private final double takerBuyQuoteAssetVolume;

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param eventType  : type of the event
     * @param eventTime  : when the event occurred
     * @param symbol
     * @param closePrice
     * @param openPrice
     * @param highPrice
     * @param lowPrice
     **/
    public WbsKline(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                    double highPrice, double lowPrice, long klineStartTime, long klineCloseTime, Interval interval,
                    long firstTradeId, long lastTradeId, double baseAssetVolume, int numberOfTrades, boolean isClosed,
                    double quoteAssetVolume, double takerBuyBaseAssetVolume, double takerBuyQuoteAssetVolume) {
        super(eventType, eventTime, symbol, closePrice, openPrice, highPrice, lowPrice);
        this.klineStartTime = klineStartTime;
        this.klineCloseTime = klineCloseTime;
        this.interval = interval;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
        this.baseAssetVolume = baseAssetVolume;
        this.numberOfTrades = numberOfTrades;
        this.isClosed = isClosed;
        this.quoteAssetVolume = quoteAssetVolume;
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
    }

    /**
     * Constructor to init {@link BinanceWebsocketResponse} object
     *
     * @param jWbsKline : Binance websocket response details as {@link JSONObject}
     **/
    public WbsKline(JSONObject jWbsKline) {
        super(jWbsKline);
        hItem.setJSONObjectSource(hItem.getJSONObject("k"));
        klineStartTime = hItem.getLong("t", 0);
        klineCloseTime = hItem.getLong("T", 0);
        interval = Interval.reachEnumConstant(hItem.getString("i"));
        firstTradeId = hItem.getLong("f", 0);
        lastTradeId = hItem.getLong("L", 0);
        baseAssetVolume = hItem.getDouble("v", 0);
        numberOfTrades = hItem.getInt("n", 0);
        isClosed = hItem.getBoolean("x");
        quoteAssetVolume = hItem.getDouble("q", 0);
        takerBuyBaseAssetVolume = hItem.getDouble("V", 0);
        takerBuyQuoteAssetVolume = hItem.getDouble("Q", 0);
    }

    public long getKlineStartTime() {
        return klineStartTime;
    }

    public long getKlineCloseTime() {
        return klineCloseTime;
    }

    public Interval getInterval() {
        return interval;
    }

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

    public double getBaseAssetVolume() {
        return baseAssetVolume;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    public double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

}
