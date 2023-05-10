package com.tecknobit.binancemanager.managers.marketstreams.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsMarketItem} class is useful to format a websocket market item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">
 *             Kline/Candlestick Streams</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-mini-ticker-stream">
 *             Individual Symbol Mini Ticker Stream</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
@Structure
public abstract class WbsMarketItem extends BinanceWebsocketResponse {

    /**
     * {@code symbol} of the websocket market item
     **/
    protected final String symbol;

    /**
     * {@code closePrice} close price of the websocket market item
     **/
    protected final double closePrice;

    /**
     * {@code openPrice} open price of the websocket market item
     **/
    protected final double openPrice;

    /**
     * {@code highPrice} high price of the websocket market item
     **/
    protected final double highPrice;

    /**
     * {@code lowPrice} low price of the websocket market item
     **/
    protected final double lowPrice;

    /**
     * Constructor to init {@link WbsMarketItem} object
     *
     * @param eventType  : type of the websocket market item
     * @param eventTime  : when the websocket market item
     * @param symbol     : symbol of the websocket market item
     * @param closePrice : close price of the websocket market item
     * @param openPrice  : open price of the websocket market item
     * @param highPrice  : high price of the websocket market item
     * @param lowPrice   : low price of the websocket market item
     **/
    public WbsMarketItem(EventType eventType, long eventTime, String symbol, double closePrice, double openPrice,
                         double highPrice, double lowPrice) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    /**
     * Constructor to init {@link WbsMarketItem} object
     *
     * @param jWbsMarketItem : websocket market item details as {@link JSONObject}
     **/
    public WbsMarketItem(JSONObject jWbsMarketItem) {
        super(jWbsMarketItem);
        symbol = hItem.getString("s");
        openPrice = hItem.getDouble("o", 0);
        closePrice = hItem.getDouble("c", 0);
        highPrice = hItem.getDouble("h", 0);
        lowPrice = hItem.getDouble("l", 0);
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
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
     * Method to get {@link #highPrice} instance <br>
     * No-any params required
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
     * No-any params required
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

}
