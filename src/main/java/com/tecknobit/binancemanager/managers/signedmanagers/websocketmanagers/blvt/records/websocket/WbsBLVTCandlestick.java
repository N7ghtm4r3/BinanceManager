package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.websocket;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsBLVTCandlestick} class is useful to format a Websocket BLVT Candlestick
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-nav-kline-candlestick-streams">
 * Websocket BLVT NAV Kline/Candlestick Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
public class WbsBLVTCandlestick extends BinanceWebsocketResponse {

    /**
     * {@code BLVTName} BLVT name of the candlestick
     **/
    private final String BLVTName;

    /**
     * {@code startTime} start time of the candlestick
     **/
    private final long startTime;

    /**
     * {@code closeTime} close time of the candlestick
     **/
    private final long closeTime;

    /**
     * {@code interval} of the candlestick
     **/
    private final Interval interval;

    /**
     * {@code firstUpdateTime} first update time of the candlestick
     **/
    private final long firstUpdateTime;

    /**
     * {@code lastUpdateTime} last update time of the candlestick
     **/
    private final long lastUpdateTime;

    /**
     * {@code openPrice} open price of the candlestick
     **/
    private final double openPrice;

    /**
     * {@code closePrice} close price of the candlestick
     **/
    private final double closePrice;

    /**
     * {@code highestPrice} highest price of the candlestick
     **/
    private final double highestPrice;

    /**
     * {@code lowestPrice} lowest price of the candlestick
     **/
    private final double lowestPrice;

    /**
     * {@code realLeverage} real leverage of the candlestick
     **/
    private final double realLeverage;

    /**
     * {@code numberOfUpdate} number of update of the candlestick
     **/
    private final int numberOfUpdate;

    /**
     * Constructor to init {@link WbsBLVTCandlestick} object
     *
     * @param eventType:       type of the event
     * @param eventTime:       when the event occurred
     * @param BLVTName:        BLVT name of the candlestick
     * @param startTime:       start time of the candlestick
     * @param closeTime:       close time of the candlestick
     * @param interval:        interval of the candlestick
     * @param firstUpdateTime: first update time of the candlestick
     * @param lastUpdateTime:  last update time of the candlestick
     * @param openPrice:       open price of the candlestick
     * @param closePrice:      close price of the candlestick
     * @param highestPrice:    highest price of the candlestick
     * @param lowestPrice:     lowest price of the candlestick
     * @param realLeverage:    real leverage of the candlestick
     * @param numberOfUpdate:  number of update of the candlestick
     **/
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

    /**
     * Constructor to init {@link WbsBLVTCandlestick} object
     *
     * @param jWbsBLVTCandlestick: websocket candlestick details as {@link JSONObject}
     **/
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

    /**
     * Method to get {@link #BLVTName} instance <br>
     * No-any params required
     *
     * @return {@link #BLVTName} instance as {@link String}
     **/
    public String getBLVTName() {
        return BLVTName;
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as long
     **/
    public long getStartTime() {
        return startTime;
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as {@link Date}
     **/
    public Date getStartDate() {
        return TimeFormatter.getDate(startTime);
    }

    /**
     * Method to get {@link #closeTime} instance <br>
     * No-any params required
     *
     * @return {@link #closeTime} instance as long
     **/
    public long getCloseTime() {
        return closeTime;
    }

    /**
     * Method to get {@link #closeTime} instance <br>
     * No-any params required
     *
     * @return {@link #closeTime} instance as {@link Date}
     **/
    public Date getCloseDate() {
        return TimeFormatter.getDate(closeTime);
    }

    /**
     * Method to get {@link #interval} instance <br>
     * No-any params required
     *
     * @return {@link #interval} instance as {@link Interval}
     **/
    public Interval getInterval() {
        return interval;
    }

    /**
     * Method to get {@link #firstUpdateTime} instance <br>
     * No-any params required
     *
     * @return {@link #firstUpdateTime} instance as long
     **/
    public long getFirstUpdateTime() {
        return firstUpdateTime;
    }

    /**
     * Method to get {@link #firstUpdateTime} instance <br>
     * No-any params required
     *
     * @return {@link #firstUpdateTime} instance as {@link Date}
     **/
    public Date getFirstUpdateDate() {
        return TimeFormatter.getDate(firstUpdateTime);
    }

    /**
     * Method to get {@link #lastUpdateTime} instance <br>
     * No-any params required
     *
     * @return {@link #lastUpdateTime} instance as long
     **/
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * Method to get {@link #lastUpdateTime} instance <br>
     * No-any params required
     *
     * @return {@link #lastUpdateTime} instance as {@link Date}
     **/
    public Date getLastUpdateDate() {
        return TimeFormatter.getDate(lastUpdateTime);
    }

    /**
     * Method to get {@link #openPrice} instance <br>
     * No-any params required
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
        return roundValue(openPrice, decimals);
    }

    /**
     * Method to get {@link #closePrice} instance <br>
     * No-any params required
     *
     * @return {@link #closePrice} instance as double
     **/
    public double getClosePrice() {
        return closePrice;
    }

    /**
     * Method to get {@link #closePrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #closePrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getClosePrice(int decimals) {
        return roundValue(closePrice, decimals);
    }

    /**
     * Method to get {@link #highestPrice} instance <br>
     * No-any params required
     *
     * @return {@link #highestPrice} instance as double
     **/
    public double getHighestPrice() {
        return highestPrice;
    }

    /**
     * Method to get {@link #highestPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #highestPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getHighestPrice(int decimals) {
        return roundValue(highestPrice, decimals);
    }

    /**
     * Method to get {@link #lowestPrice} instance <br>
     * No-any params required
     *
     * @return {@link #lowestPrice} instance as double
     **/
    public double getLowestPrice() {
        return lowestPrice;
    }

    /**
     * Method to get {@link #lowestPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lowestPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLowestPrice(int decimals) {
        return roundValue(lowestPrice, decimals);
    }

    /**
     * Method to get {@link #realLeverage} instance <br>
     * No-any params required
     *
     * @return {@link #realLeverage} instance as double
     **/
    public double getRealLeverage() {
        return realLeverage;
    }

    /**
     * Method to get {@link #realLeverage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #realLeverage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRealLeverage(int decimals) {
        return roundValue(realLeverage, decimals);
    }

    /**
     * Method to get {@link #numberOfUpdate} instance <br>
     * No-any params required
     *
     * @return {@link #numberOfUpdate} instance as int
     **/
    public int getNumberOfUpdate() {
        return numberOfUpdate;
    }

}
