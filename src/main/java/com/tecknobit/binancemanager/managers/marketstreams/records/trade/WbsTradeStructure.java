package com.tecknobit.binancemanager.managers.marketstreams.records.trade;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsTradeStructure} class is useful to format a websocket trade structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#aggregate-trade-streams">
 *             Aggregate Trade Streams</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-streams">
 *             Trade Streams</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 */
@Structure
public abstract class WbsTradeStructure extends BinanceWebsocketResponse {

    /**
     * {@code symbol} of the websocket trade structure
     */
    protected final String symbol;

    /**
     * {@code price} of the websocket trade structure
     */
    protected final double price;

    /**
     * {@code quantity} of the websocket trade structure
     */
    protected final double quantity;

    /**
     * {@code tradeTime} trade time of the websocket trade structure
     */
    protected final long tradeTime;

    /**
     * {@code isBuyerMarketMaker} whether the websocket trade structure is buyer market taker
     */
    protected final boolean isBuyerMarketMaker;

    /**
     * Constructor to init {@link WbsTradeStructure} object
     *
     * @param eventType          : type of the trade structure
     * @param eventTime          : when the trade structure
     * @param symbol             : symbol of the websocket trade structure
     * @param price              : price of the websocket trade structure
     * @param quantity           : quantity of the websocket trade structure
     * @param tradeTime          : trade time of the websocket trade structure
     * @param isBuyerMarketMaker : whether the websocket trade structure is buyer market taker
     */
    public WbsTradeStructure(EventType eventType, long eventTime, String symbol, double price, double quantity,
                             long tradeTime, boolean isBuyerMarketMaker) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.tradeTime = tradeTime;
        this.isBuyerMarketMaker = isBuyerMarketMaker;
    }

    /**
     * Constructor to init {@link WbsTradeStructure} object
     *
     * @param jWbsTradeStructure: websocket trade structure details as {@link JSONObject}
     */
    public WbsTradeStructure(JSONObject jWbsTradeStructure) {
        super(jWbsTradeStructure);
        symbol = hItem.getString("s");
        price = hItem.getDouble("p", 0);
        quantity = hItem.getDouble("q", 0);
        tradeTime = hItem.getLong("T", 0);
        isBuyerMarketMaker = hItem.getBoolean("m");
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #price} instance <br>
     * No-any params required
     *
     * @return {@link #price} instance as double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to get {@link #price} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #price} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPrice(int decimals) {
        return roundValue(price, decimals);
    }

    /**
     * Method to get {@link #quantity} instance <br>
     * No-any params required
     *
     * @return {@link #quantity} instance as double
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Method to get {@link #quantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQuantity(int decimals) {
        return roundValue(quantity, decimals);
    }

    /**
     * Method to get {@link #tradeTime} instance <br>
     * No-any params required
     *
     * @return {@link #tradeTime} instance as long
     */
    public long getTradeTime() {
        return tradeTime;
    }

    /**
     * Method to get {@link #tradeTime} instance <br>
     * No-any params required
     *
     * @return {@link #tradeTime} instance as {@link Date}
     */
    public Date getTradeDate() {
        return TimeFormatter.getDate(tradeTime);
    }

    /**
     * Method to get {@link #isBuyerMarketMaker} instance <br>
     * No-any params required
     *
     * @return {@link #isBuyerMarketMaker} instance as boolean
     */
    public boolean isBuyerMarketMaker() {
        return isBuyerMarketMaker;
    }

}
