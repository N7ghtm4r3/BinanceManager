package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.websocket;

import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

public class WbsBLVTCandlestick extends BinanceWebsocketResponse {

    private final String BLVTName;
    private final long startTime;
    private final long closeTime;
    private final Interval interval;
    private final long firstUpdateTime;
    private final long lastUpdateTime;
    private final double openPrice;
    private final double closePrice;
    private final double highestPrice;
    private final double lowestPrice;
    private final double realLeverage;
    private final int numberOfUpdate;

    public WbsBLVTCandlestick(EventType eventType, long eventTime, String BLVTName, long startTime, long closeTime,
                              Interval interval, long firstUpdateTime, long lastUpdateTime, double openPrice,
                              double closePrice, double highestPrice, double lowestPrice, double realLeverage,
                              int numberOfUpdate) {
        super(eventType, eventTime);
        this.BLVTName = BLVTName;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.interval = interval;
        this.firstUpdateTime = firstUpdateTime;
        this.lastUpdateTime = lastUpdateTime;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.realLeverage = realLeverage;
        this.numberOfUpdate = numberOfUpdate;
    }

    public WbsBLVTCandlestick(JSONObject jWbsBLVTCandlestick) {
        super(jWbsBLVTCandlestick);
        BLVTName = hItem.getString("s");
        hItem.setJSONObjectSource(hItem.getJSONObject("k"));
        startTime = hItem.getLong("t", 0);
        closeTime = hItem.getLong("T", 0);
        interval = Interval.valueOf("_" + hItem.getString("i"));
        firstUpdateTime = hItem.getLong("f", 0);
        lastUpdateTime = hItem.getLong("L", 0);
        openPrice = hItem.getDouble("o", 0);
        closePrice = hItem.getDouble("c", 0);
        highestPrice = hItem.getDouble("h", 0);
        lowestPrice = hItem.getDouble("l", 0);
        realLeverage = hItem.getDouble("v", 0);
        numberOfUpdate = hItem.getInt("n", 0);
    }

    public String getBLVTName() {
        return BLVTName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public Interval getInterval() {
        return interval;
    }

    public long getFirstUpdateTime() {
        return firstUpdateTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public double getRealLeverage() {
        return realLeverage;
    }

    public int getNumberOfUpdate() {
        return numberOfUpdate;
    }

}
