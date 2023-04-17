package com.tecknobit.binancemanager.managers.marketstreams.records;

import com.tecknobit.binancemanager.managers.market.records.OrderBook.BookOrderDetails;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.market.records.OrderBook.returnOrdersList;

/**
 * The {@code DiffDepth} class is useful to format a diff depth
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream">
 * Diff. Depth Stream</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
public class DiffDepth extends BinanceWebsocketResponse {

    /**
     * {@code symbol} of the diff depth
     * **/
    private final String symbol;

    /**
     * {@code firstUpdateIdInEvent} first update id in event of the diff depth
     * **/
    private final long firstUpdateIdInEvent;

    /**
     * {@code finalUpdateIdInEvent} final update id in event of the diff depth
     * **/
    private final long finalUpdateIdInEvent;

    /**
     * {@code bids} of the diff depth
     * **/
    private final ArrayList<BookOrderDetails> bids;

    /**
     * {@code asks} the diff depth
     * **/
    private final ArrayList<BookOrderDetails> asks;

    /**
     * Constructor to init {@link DiffDepth} object
     *
     * @param eventType : type of the diff depth
     * @param eventTime : when the diff depth
     * @param symbol : symbol of the diff depth
     * @param firstUpdateIdInEvent : first update id in event of the diff depth
     * @param finalUpdateIdInEvent : final update id in event of the diff depth
     * @param bids : bids of the diff depth
     * @param asks : asks of the diff depth
     **/
    public DiffDepth(EventType eventType, long eventTime, String symbol, long firstUpdateIdInEvent,
                     long finalUpdateIdInEvent, ArrayList<BookOrderDetails> bids, ArrayList<BookOrderDetails> asks) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.firstUpdateIdInEvent = firstUpdateIdInEvent;
        this.finalUpdateIdInEvent = finalUpdateIdInEvent;
        this.bids = bids;
        this.asks = asks;
    }

    /**
     * Constructor to init {@link DiffDepth} object
     *
     * @param jDiffDepth : diff depth details as {@link JSONObject}
     **/
    public DiffDepth(JSONObject jDiffDepth) {
        super(jDiffDepth);
        symbol = hItem.getString("s");
        firstUpdateIdInEvent = hItem.getLong("U", 0);
        finalUpdateIdInEvent = hItem.getLong("u", 0);
        bids = returnOrdersList(hItem.getJSONArray("b"));
        asks = returnOrdersList(hItem.getJSONArray("a"));
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
     * Method to get {@link #firstUpdateIdInEvent} instance <br>
     * No-any params required
     *
     * @return {@link #firstUpdateIdInEvent} instance as long
     **/
    public long getFirstUpdateIdInEvent() {
        return firstUpdateIdInEvent;
    }

    /**
     * Method to get {@link #finalUpdateIdInEvent} instance <br>
     * No-any params required
     *
     * @return {@link #finalUpdateIdInEvent} instance as long
     **/
    public long getFinalUpdateIdInEvent() {
        return finalUpdateIdInEvent;
    }

    /**
     * Method to get {@link #bids} instance <br>
     * No-any params required
     *
     * @return {@link #bids} instance as {@link ArrayList} of {@link BookOrderDetails}
     **/
    public ArrayList<BookOrderDetails> getBids() {
        return bids;
    }

    /**
     * Method to get {@link #asks} instance <br>
     * No-any params required
     *
     * @return {@link #asks} instance as {@link ArrayList} of {@link BookOrderDetails}
     **/

    public ArrayList<BookOrderDetails> getAsks() {
        return asks;
    }

}
