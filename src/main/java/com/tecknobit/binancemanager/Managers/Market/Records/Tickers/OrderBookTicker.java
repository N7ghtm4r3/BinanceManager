package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONObject;

/**
 * The {@code OrderBookTicker} class is useful to manage OrderBookTicker requests
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
 *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OrderBookTicker extends Ticker {

    /**
     * {@code bidPrice} is instance that contains bids price in the order book ticker
     * **/
    protected final double bidPrice;

    /**
     * {@code bidQty} is instance that contains bids quantity in the order book ticker
     * **/
    protected final double bidQty;

    /**
     * {@code askPrice} is instance that contains ask price in the order book ticker
     * **/
    protected final double askPrice;

    /**
     * {@code askQty} is instance that contains ask quantity in the order book ticker
     * **/
    protected final double askQty;

    /**
     * {@code hTicker} is instance that is useful to work with JSON book details
     * **/
    protected final JsonHelper hTicker;

    /** Constructor to init {@link OrderBookTicker} object
     * @param symbol: symbol of the ticker
     * @param bidPrice: bids price in the order book ticker
     * @param bidQty: bids quantity in the order book ticker
     * @param askPrice: ask price in the order book ticker
     * @param askQty: ask quantity in the order book ticker
     * **/
    public OrderBookTicker(String symbol, double bidPrice, double bidQty, double askPrice, double askQty) {
        super(symbol);
        this.bidPrice = bidPrice;
        this.bidQty = bidQty;
        this.askPrice = askPrice;
        this.askQty = askQty;
        hTicker = null;
    }

    /** Constructor to init {@link OrderBookTicker} object
     * @param book: book ticker details as {@link JSONObject}
     * **/
    public OrderBookTicker(JSONObject book) {
        super(book.getString("symbol"));
        hTicker = new JsonHelper(book);
        bidPrice = hTicker.getDouble("bidPrice");
        bidQty = hTicker.getDouble("bidQty");
        askPrice = hTicker.getDouble("askPrice");
        askQty = hTicker.getDouble("askQty");
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

    @Override
    public String toString() {
        return "OrderBookTicker{" +
                "bidPrice=" + bidPrice +
                ", bidQty=" + bidQty +
                ", askPrice=" + askPrice +
                ", askQty=" + askQty +
                ", symbol='" + symbol + '\'' +
                '}';
    }

}
