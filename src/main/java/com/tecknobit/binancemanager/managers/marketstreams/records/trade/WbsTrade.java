package com.tecknobit.binancemanager.managers.marketstreams.records.trade;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

/**
 * The {@code WbsTrade} class is useful to format a websocket trade
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-streams">
 * Trade Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 * @see WbsTradeStructure
 **/
public class WbsTrade extends WbsTradeStructure {

    /**
     * {@code tradeId} trade id of the websocket trade
     * **/
    private final long tradeId;

    /**
     * {@code buyerOrderId} buyer order id of the websocket trade
     * **/
    private final long buyerOrderId;

    /**
     * {@code sellerOrderId} seller order id of the websocket trade
     * **/
    private final long sellerOrderId;

    /**
     * Constructor to init {@link WbsTrade} object
     *
     * @param eventType : type of the trade
     * @param eventTime : when the trade
     * @param symbol : symbol of the trade
     * @param price : price of the trade
     * @param quantity : quantity of the trade
     * @param tradeTime : trade time of the trade
     * @param isBuyerMarketMaker : whether the trade is buyer market taker
     * @param tradeId : trade id of the websocket trade
     * @param buyerOrderId : buyer order id of the websocket trade
     * @param sellerOrderId : seller order id of the websocket trade
     **/
    public WbsTrade(EventType eventType, long eventTime, String symbol, double price, double quantity, long tradeTime,
                    boolean isBuyerMarketMaker, long tradeId, long buyerOrderId, long sellerOrderId) {
        super(eventType, eventTime, symbol, price, quantity, tradeTime, isBuyerMarketMaker);
        this.tradeId = tradeId;
        this.buyerOrderId = buyerOrderId;
        this.sellerOrderId = sellerOrderId;
    }

    /**
     * Constructor to init {@link WbsTrade} object
     *
     * @param jWbsTrade : websocket trade details as {@link JSONObject}
     **/
    public WbsTrade(JSONObject jWbsTrade) {
        super(jWbsTrade);
        tradeId = hItem.getLong("t", 0);
        buyerOrderId = hItem.getLong("b", 0);
        sellerOrderId = hItem.getLong("a", 0);
    }

    /**
     * Method to get {@link #tradeId} instance <br>
     * No-any params required
     *
     * @return {@link #tradeId} instance as long
     **/
    public long getTradeId() {
        return tradeId;
    }

    /**
     * Method to get {@link #buyerOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #buyerOrderId} instance as long
     **/
    public long getBuyerOrderId() {
        return buyerOrderId;
    }

    /**
     * Method to get {@link #sellerOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #sellerOrderId} instance as long
     **/
    public long getSellerOrderId() {
        return sellerOrderId;
    }

}
