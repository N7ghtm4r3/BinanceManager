package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsBookTicker} class is useful to format a websocket book ticker
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-book-ticker-streams">
 * Individual Symbol Book Ticker Streams</a>
 * @see BinanceItem
 **/
public class WbsBookTicker extends BinanceItem {

    /**
     * {@code orderBookUpdateId} order book update id of the book ticker
     **/
    private final long orderBookUpdateId;

    /**
     * {@code symbol} of the book ticker
     **/
    private final String symbol;

    /**
     * {@code bestBidPrice} best bid price of the book ticker
     **/
    private final double bestBidPrice;

    /**
     * {@code bestBidQuantity} best bid quantity of the book ticker
     **/
    private final double bestBidQuantity;

    /**
     * {@code bestAskPrice} best ask price of the book ticker
     **/
    private final double bestAskPrice;

    /**
     * {@code bestAskQuantity} best ask quantity of the book ticker
     **/
    private final double bestAskQuantity;

    /**
     * Constructor to init {@link WbsBookTicker} object
     *
     * @param orderBookUpdateId : order book update id of the book ticker
     * @param symbol            : symbol of the book ticker
     * @param bestBidPrice      : best bid price of the book ticker
     * @param bestBidQuantity   : best bid quantity of the book ticker
     * @param bestAskPrice      : best ask price of the book ticker
     * @param bestAskQuantity   : best ask quantity of the book ticker
     **/
    public WbsBookTicker(long orderBookUpdateId, String symbol, double bestBidPrice, double bestBidQuantity,
                         double bestAskPrice, double bestAskQuantity) {
        super(null);
        this.orderBookUpdateId = orderBookUpdateId;
        this.symbol = symbol;
        this.bestBidPrice = bestBidPrice;
        this.bestBidQuantity = bestBidQuantity;
        this.bestAskPrice = bestAskPrice;
        this.bestAskQuantity = bestAskQuantity;
    }

    /**
     * Constructor to init {@link WbsBookTicker} object
     *
     * @param jWbsBookTicker : websocket book ticker details as {@link JSONObject}
     **/
    public WbsBookTicker(JSONObject jWbsBookTicker) {
        super(jWbsBookTicker);
        orderBookUpdateId = hItem.getLong("u", 0);
        symbol = hItem.getString("s");
        bestBidPrice = hItem.getDouble("b", 0);
        bestBidQuantity = hItem.getDouble("B", 0);
        bestAskPrice = hItem.getDouble("a", 0);
        bestAskQuantity = hItem.getDouble("A", 0);
    }

    /**
     * Method to get {@link #orderBookUpdateId} instance <br>
     * No-any params required
     *
     * @return {@link #orderBookUpdateId} instance as long
     **/
    public long getOrderBookUpdateId() {
        return orderBookUpdateId;
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
     * Method to get {@link #bestBidPrice} instance <br>
     * No-any params required
     *
     * @return {@link #bestBidPrice} instance as double
     **/
    public double getBestBidPrice() {
        return bestBidPrice;
    }

    /**
     * Method to get {@link #bestBidPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestBidPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBestBidPrice(int decimals) {
        return roundValue(bestBidPrice, decimals);
    }

    /**
     * Method to get {@link #bestBidQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #bestBidQuantity} instance as double
     **/
    public double getBestBidQuantity() {
        return bestBidQuantity;
    }

    /**
     * Method to get {@link #bestBidQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestBidQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBestBidQuantity(int decimals) {
        return roundValue(bestBidQuantity, decimals);
    }

    /**
     * Method to get {@link #bestAskPrice} instance <br>
     * No-any params required
     *
     * @return {@link #bestAskPrice} instance as double
     **/
    public double getBestAskPrice() {
        return bestAskPrice;
    }

    /**
     * Method to get {@link #bestAskPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestAskPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBestAskPrice(int decimals) {
        return roundValue(bestAskPrice, decimals);
    }

    /**
     * Method to get {@link #bestAskQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #bestAskQuantity} instance as double
     **/
    public double getBestAskQuantity() {
        return bestAskQuantity;
    }

    /**
     * Method to get {@link #bestAskQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #bestAskQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBestAskQuantity(int decimals) {
        return roundValue(bestAskQuantity, decimals);
    }

}
