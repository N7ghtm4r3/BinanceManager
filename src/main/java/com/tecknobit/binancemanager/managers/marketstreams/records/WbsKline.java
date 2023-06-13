package com.tecknobit.binancemanager.managers.marketstreams.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsKline} class is useful to format a websocket candlestick/kline
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">
 * Kline/Candlestick Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 * @see WbsMarketItem
 */
public class WbsKline extends WbsMarketItem {

    /**
     * {@code klineStartTime} kline start time of the kline
     */
    private final long klineStartTime;

    /**
     * {@code klineCloseTime} kline close time of the kline
     */
    private final long klineCloseTime;

    /**
     * {@code interval} of the kline
     */
    private final Interval interval;

    /**
     * {@code firstTradeId} first trade id of the kline
     */
    private final long firstTradeId;

    /**
     * {@code lastTradeId} last trade id of the kline
     */
    private final long lastTradeId;

    /**
     * {@code baseAssetVolume} base asset volume of the kline
     */
    private final double baseAssetVolume;

    /**
     * {@code numberOfTrades} number of trades of the kline
     */
    private final int numberOfTrades;

    /**
     * {@code isClosed} whether the kline is closed
     */
    private final boolean isClosed;

    /**
     * {@code quoteAssetVolume} quote asset volume of the kline
     */
    private final double quoteAssetVolume;

    /**
     * {@code takerBuyBaseAssetVolume} taker buy base asset volume of the kline
     */
    private final double takerBuyBaseAssetVolume;

    /**
     * {@code takerBuyQuoteAssetVolume} taker buy quote asset volume of the kline
     */
    private final double takerBuyQuoteAssetVolume;

    /**
     * Constructor to init {@link WbsKline} object
     *
     * @param eventType : type of the kline
     * @param eventTime : when the kline
     * @param symbol : symbol of the kline
     * @param closePrice : close price of the kline
     * @param openPrice : open price of the kline
     * @param highPrice : high price of the kline
     * @param lowPrice : low price of the kline
     * @param klineStartTime : kline start time of the kline
     * @param klineCloseTime : kline close time of the kline
     * @param interval : interval of the kline
     * @param firstTradeId : first trade id of the kline
     * @param lastTradeId : last trade id of the kline
     * @param baseAssetVolume : base asset volume of the kline
     * @param numberOfTrades : number of trades of the kline
     * @param isClosed : whether the kline is closed
     * @param quoteAssetVolume : quote asset volume of the kline
     * @param takerBuyBaseAssetVolume : taker buy base asset volume of the kline
     * @param takerBuyQuoteAssetVolume : taker buy quote asset volume of the kline
     */
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
     * Constructor to init {@link WbsKline} object
     *
     * @param jWbsKline : websocket kline details as {@link JSONObject}
     */
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

    /**
     * Method to get {@link #klineStartTime} instance <br>
     * No-any params required
     *
     * @return {@link #klineStartTime} instance as long
     */
    public long getKlineStartTime() {
        return klineStartTime;
    }

    /**
     * Method to get {@link #klineStartTime} instance <br>
     * No-any params required
     *
     * @return {@link #klineStartTime} instance as {@link Date}
     */
    public Date getKlineStartDate() {
        return TimeFormatter.getDate(klineStartTime);
    }

    /**
     * Method to get {@link #klineCloseTime} instance <br>
     * No-any params required
     *
     * @return {@link #klineCloseTime} instance as long
     */
    public long getKlineCloseTime() {
        return klineCloseTime;
    }

    /**
     * Method to get {@link #klineCloseTime} instance <br>
     * No-any params required
     *
     * @return {@link #klineCloseTime} instance as {@link Date}
     */
    public Date getKlineCloseDate() {
        return TimeFormatter.getDate(klineCloseTime);
    }

    /**
     * Method to get {@link #interval} instance <br>
     * No-any params required
     *
     * @return {@link #interval} instance as {@link Interval}
     */
    public Interval getInterval() {
        return interval;
    }

    /**
     * Method to get {@link #firstTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #firstTradeId} instance as long
     */
    public long getFirstTradeId() {
        return firstTradeId;
    }

    /**
     * Method to get {@link #lastTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #lastTradeId} instance as long
     */
    public long getLastTradeId() {
        return lastTradeId;
    }

    /**
     * Method to get {@link #baseAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #baseAssetVolume} instance as double
     */
    public double getBaseAssetVolume() {
        return baseAssetVolume;
    }

    /**
     * Method to get {@link #baseAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #baseAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBaseAssetVolume(int decimals) {
        return roundValue(baseAssetVolume, decimals);
    }

    /**
     * Method to get {@link #numberOfTrades} instance <br>
     * No-any params required
     *
     * @return {@link #numberOfTrades} instance as int
     */
    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    /**
     * Method to get {@link #isClosed} instance <br>
     * No-any params required
     *
     * @return {@link #isClosed} instance as boolean
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Method to get {@link #quoteAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #quoteAssetVolume} instance as double
     */
    public double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    /**
     * Method to get {@link #quoteAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQuoteAssetVolume(int decimals) {
        return roundValue(quoteAssetVolume, decimals);
    }

    /**
     * Method to get {@link #takerBuyBaseAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #takerBuyBaseAssetVolume} instance as double
     */
    public double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    /**
     * Method to get {@link #takerBuyBaseAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerBuyBaseAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTakerBuyBaseAssetVolume(int decimals) {
        return roundValue(takerBuyBaseAssetVolume, decimals);
    }

    /**
     * Method to get {@link #takerBuyQuoteAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #takerBuyQuoteAssetVolume} instance as double
     */
    public double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

    /**
     * Method to get {@link #takerBuyQuoteAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerBuyQuoteAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTakerBuyQuoteAssetVolume(int decimals) {
        return roundValue(takerBuyQuoteAssetVolume, decimals);
    }

}
