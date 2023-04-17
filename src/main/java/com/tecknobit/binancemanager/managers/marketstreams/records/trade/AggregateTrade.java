package com.tecknobit.binancemanager.managers.marketstreams.records.trade;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

/**
 * The {@code AggregateTrade} class is useful to format a websocket aggregate trade
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#aggregate-trade-streams">
 * Aggregate Trade Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 * @see WbsTradeStructure
 **/
public class AggregateTrade extends WbsTradeStructure {

    /**
     * {@code aggregateTradeId} aggregate trade id of the websocket aggregate trade
     * **/
    private final long aggregateTradeId;

    /**
     * {@code firstTradeId} first trade id of the websocket aggregate trade
     * **/
    private final long firstTradeId;

    /**
     * {@code lastTradeId} last trade id of the websocket aggregate trade
     * **/
    private final long lastTradeId;

    /**
     * Constructor to init {@link AggregateTrade} object
     *
     * @param eventType : type of the aggregate trade
     * @param eventTime : when the aggregate trade
     * @param symbol : symbol of the websocket aggregate trade
     * @param price : price of the websocket aggregate trade
     * @param quantity : quantity of the websocket aggregate trade
     * @param tradeTime : trade time of the websocket aggregate trade
     * @param isBuyerMarketMaker : whether the websocket aggregate trade is buyer market taker
     * @param aggregateTradeId : aggregate trade id of the websocket aggregate trade
     * @param firstTradeId : first trade id of the websocket aggregate trade
     * @param lastTradeId : last trade id of the websocket aggregate trade
     **/
    public AggregateTrade(EventType eventType, long eventTime, String symbol, double price, double quantity,
                          long tradeTime, boolean isBuyerMarketMaker, long aggregateTradeId, long firstTradeId,
                          long lastTradeId) {
        super(eventType, eventTime, symbol, price, quantity, tradeTime, isBuyerMarketMaker);
        this.aggregateTradeId = aggregateTradeId;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
    }

    /**
     * Constructor to init {@link AggregateTrade} object
     *
     * @param jAggregateTrade : websocket aggregate trade details as {@link JSONObject}
     **/
    public AggregateTrade(JSONObject jAggregateTrade) {
        super(jAggregateTrade);
        aggregateTradeId = hItem.getLong("a", 0);
        firstTradeId = hItem.getLong("f", 0);
        lastTradeId = hItem.getLong("l", 0);
    }

    /**
     * Method to get {@link #aggregateTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #aggregateTradeId} instance as long
     **/
    public long getAggregateTradeId() {
        return aggregateTradeId;
    }

    /**
     * Method to get {@link #firstTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #firstTradeId} instance as long
     **/
    public long getFirstTradeId() {
        return firstTradeId;
    }

    /**
     * Method to get {@link #lastTradeId} instance <br>
     * No-any params required
     *
     * @return {@link #lastTradeId} instance as long
     **/
    public long getLastTradeId() {
        return lastTradeId;
    }

}
