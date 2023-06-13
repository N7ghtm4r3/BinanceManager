package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.marketstreams.records.WbsMarketItem;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsTicker} class is useful to format a websocket ticker
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-ticker-streams">
 * Individual Symbol Ticker Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 * @see WbsMarketItem
 * @see WbsMiniTicker
 * @see WbsRollingWindowTicker
 */
public class WbsTicker extends WbsRollingWindowTicker {

    /**
     * {@code firstTradePrice} first trade price of the ticker
     */
    private final double firstTradePrice;

    /**
     * {@code lastQuantity} last quantity of the ticker
     */
    private final double lastQuantity;

    /**
     * {@code bestBidPrice} best bid price of the ticker
     */
    private final double bestBidPrice;

    /**
     * {@code bestBidQuantity} best bid quantity of the ticker
     */
    private final double bestBidQuantity;

    /**
     * {@code bestAskPrice} best ask price of the ticker
     */
    private final double bestAskPrice;

    /**
     * {@code bestAskQuantity} best ask quantity of the ticker
     */
    private final double bestAskQuantity;

    /**
     * Constructor to init {@link WbsTicker} object
     *
     * @param eventType : type of the ticker
     * @param eventTime : when the rolling window ticker
     * @param symbol : symbol of the ticker
     * @param closePrice : close price of the ticker
     * @param openPrice : open price of the ticker
     * @param highPrice : high price of the ticker
     * @param lowPrice : low price of the ticker
     * @param totalTradedBaseAssetVolume : total traded base asset volume of rolling window ticker
     * @param totalTradedQuoteAssetVolume : total traded quote asset volume of rolling window ticker
     * @param priceChange : price change of the ticker
     * @param priceChangePercent : price change percent of the ticker
     * @param lastPrice : last price of the ticker
     * @param weightedAveragePrice : weighted average price of the ticker
     * @param statisticsOpenTime : statistics open time of the ticker
     * @param statisticsCloseTime : statistics close time of the ticker
     * @param firstTradeId : first trade id of the ticker
     * @param lastTradeId : last trade id of the ticker
     * @param totalNumberOfTrades : total number of trades of the ticker
     * @param firstTradePrice : first trade price of the ticker
     * @param lastQuantity : last quantity of the ticker
     * @param bestBidPrice : best bid price of the ticker
     * @param bestBidQuantity : best bid quantity of the ticker
     * @param bestAskPrice : best ask price of the ticker
     * @param bestAskQuantity : best ask quantity of the ticker
     */
    public WbsTicker(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                     double highPrice, double lowPrice, double totalTradedBaseAssetVolume,
                     double totalTradedQuoteAssetVolume, double priceChange, double priceChangePercent, double lastPrice,
                     double weightedAveragePrice, long statisticsOpenTime, long statisticsCloseTime, long firstTradeId,
                     long lastTradeId, int totalNumberOfTrades, double firstTradePrice, double lastQuantity,
                     double bestBidPrice, double bestBidQuantity, double bestAskPrice, double bestAskQuantity) {
        super(eventType, eventTime, symbol, closePrice, openPrice, highPrice, lowPrice, totalTradedBaseAssetVolume,
                totalTradedQuoteAssetVolume, priceChange, priceChangePercent, lastPrice, weightedAveragePrice,
                statisticsOpenTime, statisticsCloseTime, firstTradeId, lastTradeId, totalNumberOfTrades);
        this.firstTradePrice = firstTradePrice;
        this.lastQuantity = lastQuantity;
        this.bestBidPrice = bestBidPrice;
        this.bestBidQuantity = bestBidQuantity;
        this.bestAskPrice = bestAskPrice;
        this.bestAskQuantity = bestAskQuantity;
    }

    /**
     * Constructor to init {@link WbsTicker} object
     *
     * @param jWbsTicker : websocket ticker details as {@link JSONObject}
     */
    public WbsTicker(JSONObject jWbsTicker) {
        super(jWbsTicker);
        firstTradePrice = hItem.getDouble("x", 0);
        lastQuantity = hItem.getDouble("Q", 0);
        bestBidPrice = hItem.getDouble("b", 0);
        bestBidQuantity = hItem.getDouble("B", 0);
        bestAskPrice = hItem.getDouble("a", 0);
        bestAskQuantity = hItem.getDouble("A", 0);
    }

    /**
     * Method to get {@link #lastQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #lastQuantity} instance as double
     */
    public double getLastQuantity() {
        return lastQuantity;
    }

    /**
     * Method to get {@link #lastQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLastQuantity(int decimals) {
        return roundValue(lastQuantity, decimals);
    }

    /**
     * Method to get {@link #bestBidPrice} instance <br>
     * No-any params required
     *
     * @return {@link #bestBidPrice} instance as double
     */
    public double getBestBidPrice() {
        return bestBidPrice;
    }

    /**
     * Method to get {@link #bestBidPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestBidPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBestBidPrice(int decimals) {
        return roundValue(bestBidPrice, decimals);
    }

    /**
     * Method to get {@link #bestBidQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #bestBidQuantity} instance as double
     */
    public double getBestBidQuantity() {
        return bestBidQuantity;
    }

    /**
     * Method to get {@link #bestBidQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestBidQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBestBidQuantity(int decimals) {
        return roundValue(bestBidQuantity, decimals);
    }

    /**
     * Method to get {@link #bestAskPrice} instance <br>
     * No-any params required
     *
     * @return {@link #bestAskPrice} instance as double
     */
    public double getBestAskPrice() {
        return bestAskPrice;
    }

    /**
     * Method to get {@link #bestAskPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestAskPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBestAskPrice(int decimals) {
        return roundValue(bestAskPrice, decimals);
    }

    /**
     * Method to get {@link #bestAskQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #bestAskQuantity} instance as double
     */
    public double getBestAskQuantity() {
        return bestAskQuantity;
    }

    /**
     * Method to get {@link #bestAskQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestAskQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBestAskQuantity(int decimals) {
        return roundValue(bestAskQuantity, decimals);
    }

}
