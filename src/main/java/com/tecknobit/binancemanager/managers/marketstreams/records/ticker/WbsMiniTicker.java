package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.marketstreams.records.WbsMarketItem;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsMiniTicker} class is useful to format a websocket mini ticker
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">
 * Individual Symbol Mini Ticker Stream</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 * @see WbsMarketItem
 */
public class WbsMiniTicker extends WbsMarketItem {

    /**
     * {@code totalTradedBaseAssetVolume} total traded base asset volume of the mini ticker
     */
    protected final double totalTradedBaseAssetVolume;

    /**
     * {@code totalTradedQuoteAssetVolume} total traded quote asset volume of the mini ticker
     */
    protected final double totalTradedQuoteAssetVolume;

    /**
     * Constructor to init {@link WbsMiniTicker} object
     *
     * @param eventType : type of the websocket mini ticker
     * @param eventTime : when the websocket mini ticker
     * @param symbol : symbol of the websocket mini ticker
     * @param closePrice : close price of the websocket mini ticker
     * @param openPrice : open price of the websocket mini ticker
     * @param highPrice : high price of the websocket mini ticker
     * @param lowPrice : low price of the websocket mini ticker
     * @param totalTradedBaseAssetVolume : total traded base asset volume of the mini ticker
     * @param totalTradedQuoteAssetVolume : total traded quote asset volume of the mini ticker
     */
    public WbsMiniTicker(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                         double highPrice, double lowPrice, double totalTradedBaseAssetVolume,
                         double totalTradedQuoteAssetVolume) {
        super(eventType, eventTime, symbol, closePrice, openPrice, highPrice, lowPrice);
        this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume;
        this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume;
    }

    /**
     * Constructor to init {@link WbsMiniTicker} object
     *
     * @param jWbsMiniTicker : websocket mini ticker details as {@link JSONObject}
     */
    public WbsMiniTicker(JSONObject jWbsMiniTicker) {
        super(jWbsMiniTicker);
        totalTradedBaseAssetVolume = hItem.getDouble("v", 0);
        totalTradedQuoteAssetVolume = hItem.getDouble("q", 0);
    }

    /**
     * Method to get {@link #totalTradedBaseAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #totalTradedBaseAssetVolume} instance as double
     */
    public double getTotalTradedBaseAssetVolume() {
        return totalTradedBaseAssetVolume;
    }

    /**
     * Method to get {@link #totalTradedBaseAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalTradedBaseAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalTradedBaseAssetVolume(int decimals) {
        return roundValue(totalTradedBaseAssetVolume, decimals);
    }

    /**
     * Method to get {@link #totalTradedQuoteAssetVolume} instance <br>
     * No-any params required
     *
     * @return {@link #totalTradedQuoteAssetVolume} instance as double
     */
    public double getTotalTradedQuoteAssetVolume() {
        return totalTradedQuoteAssetVolume;
    }

    /**
     * Method to get {@link #totalTradedQuoteAssetVolume} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalTradedQuoteAssetVolume} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalTradedQuoteAssetVolume(int decimals) {
        return roundValue(totalTradedQuoteAssetVolume, decimals);
    }

}
