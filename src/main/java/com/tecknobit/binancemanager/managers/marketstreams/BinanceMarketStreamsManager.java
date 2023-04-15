package com.tecknobit.binancemanager.managers.marketstreams;

import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.market.records.OrderBook;
import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.marketstreams.records.DiffDepth;
import com.tecknobit.binancemanager.managers.marketstreams.records.WbsKline;
import com.tecknobit.binancemanager.managers.marketstreams.records.ticker.WbsBookTicker;
import com.tecknobit.binancemanager.managers.marketstreams.records.ticker.WbsMiniTicker;
import com.tecknobit.binancemanager.managers.marketstreams.records.ticker.WbsRollingWindowTicker;
import com.tecknobit.binancemanager.managers.marketstreams.records.ticker.WbsRollingWindowTicker.WindowSize;
import com.tecknobit.binancemanager.managers.marketstreams.records.ticker.WbsTicker;
import com.tecknobit.binancemanager.managers.marketstreams.records.trade.AggregateTrade;
import com.tecknobit.binancemanager.managers.marketstreams.records.trade.WbsTrade;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.BinanceWebsocketManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getString;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.marketstreams.BinanceMarketStreamsManager.Streams.*;
import static com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType.*;
import static java.lang.Thread.onSpinWait;

/**
 * The {@code BinanceMarketStreamsManager} class is useful to manage market streams endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-market-streams">
 * Websocket Market Streams</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see BinanceWebsocketManager
 **/
public class BinanceMarketStreamsManager extends BinanceManager {

    public enum Streams {

        aggTrade("@aggTrade"),
        trade("@trade"),
        kline_("@kline_"),
        miniTicker("@miniTicker"),
        miniTickerArr("!miniTicker@arr"),
        ticker("@ticker"),
        tickersArr("!ticker@arr"),
        rollingTickersArr("!ticker_"),
        ticker_("@ticker_"),
        bookTicker("@bookTicker"),
        depth("@depth");

        private final String stream;

        Streams(String stream) {
            this.stream = stream;
        }

        public String getStream() {
            return stream;
        }

        @Override
        public String toString() {
            return stream;
        }

    }

    /**
     * {@code WEB_SOCKET_DATA_STREAM_ENDPOINT} is constant for WEB_SOCKET_DATA_STREAM_ENDPOINT's endpoint
     **/
    public static final String WEB_SOCKET_DATA_STREAM_ENDPOINT = "wss://data-stream.binance.com";

    /**
     * {@code webSocketResponse} response obtained from the websocket connection
     **/
    private volatile String webSocketResponse;
    private volatile boolean streamAvailable = true;

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     **/
    public BinanceMarketStreamsManager(String baseEndpoint, String defaultErrorMessage,
                                       int timeout) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout);
    }

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     **/
    public BinanceMarketStreamsManager(String baseEndpoint,
                                       String defaultErrorMessage) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage);
    }

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :     custom timeout for request
     **/
    public BinanceMarketStreamsManager(String baseEndpoint, int timeout) throws SystemException, IOException {
        super(baseEndpoint, timeout);
    }

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     **/
    public BinanceMarketStreamsManager(String baseEndpoint) throws SystemException, IOException {
        super(baseEndpoint);
    }

    /**
     * Constructor to init a {@link BinanceManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceManager firstManager = new BinanceManager("defaultErrorMessage", timeout);
     *        //You don't need to insert all credentials to make manager work
     *        BinanceManager secondManager = new BinanceManager(); //same credentials used
     *     }
     * </pre>
     **/
    public BinanceMarketStreamsManager() {
        super();
    }

    public void connectToMultipleStreams(String... streams) throws Exception {
        StringBuilder reqUrl = new StringBuilder(WEB_SOCKET_DATA_STREAM_ENDPOINT + "/stream?streams=");
        for (int j = 0; j < streams.length; j++) {
            String stream = streams[j];
            if (j > 0)
                stream = "/" + stream;
            reqUrl.append(stream);
        }
        startWebsocket(reqUrl.toString());
    }

    public void connectToAggTradeStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + Streams.aggTrade);
    }

    public AggregateTrade getAggregateTrade() {
        return getAggregateTrade(LIBRARY_OBJECT);
    }

    public <T> T getAggregateTrade(ReturnFormat format) {
        waitCorrectResponse(EventType.aggTrade);
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new AggregateTrade(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToTradeStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + Streams.trade);
    }

    public WbsTrade getTrade() {
        return getTrade(LIBRARY_OBJECT);
    }

    public <T> T getTrade(ReturnFormat format) {
        waitCorrectResponse(EventType.trade);
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new WbsTrade(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToKlineCandlestickStream(String symbol, Interval interval) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + kline_ + interval);
    }

    public WbsKline getKlineCandlestick() {
        return getKlineCandlestick(LIBRARY_OBJECT);
    }

    public <T> T getKlineCandlestick(ReturnFormat format) {
        waitCorrectResponse(EventType.kline);
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new WbsKline(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToIndividualSymbolMiniTickerStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + miniTicker);
    }

    public WbsMiniTicker getMiniTicker() {
        return getMiniTicker(LIBRARY_OBJECT);
    }

    public <T> T getMiniTicker(ReturnFormat format) {
        waitCorrectResponse(EventType._24hrMiniTicker, "{");
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new WbsMiniTicker(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToAllMarketMiniTickersStream() throws Exception {
        connectToSingleStream(miniTickerArr.stream);
    }

    public ArrayList<WbsMiniTicker> getMiniTickers() {
        return getMiniTickers(LIBRARY_OBJECT);
    }

    public <T> T getMiniTickers(ReturnFormat format) {
        waitCorrectResponse(EventType._24hrMiniTicker, "[");
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONArray(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                ArrayList<WbsMiniTicker> tickers = new ArrayList<>();
                JSONArray jTickers = new JSONArray(webSocketResponse);
                for (int j = 0; j < jTickers.length(); j++)
                    tickers.add(new WbsMiniTicker(jTickers.getJSONObject(j)));
                mReturn = (T) tickers;
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToIndividualSymbolTickerStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + ticker);
    }

    public WbsTicker getSymbolTicker() {
        return getSymbolTicker(LIBRARY_OBJECT);
    }

    public <T> T getSymbolTicker(ReturnFormat format) {
        waitCorrectResponse(EventType._24hrTicker, "{");
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new WbsTicker(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToAllMarketTickersStream() throws Exception {
        connectToSingleStream(tickersArr.stream);
    }

    public ArrayList<WbsTicker> getSymbolTickers() {
        return getSymbolTickers(LIBRARY_OBJECT);
    }

    public <T> T getSymbolTickers(ReturnFormat format) {
        waitCorrectResponse(EventType._24hrTicker, "[");
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONArray(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                ArrayList<WbsTicker> tickers = new ArrayList<>();
                JSONArray jTickers = new JSONArray(webSocketResponse);
                for (int j = 0; j < jTickers.length(); j++)
                    tickers.add(new WbsTicker(jTickers.getJSONObject(j)));
                mReturn = (T) tickers;
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToIndividualSymbolRollingWindowStatisticsStreams(String symbol, WindowSize size) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + ticker_ + size.getSize());
    }

    public WbsRollingWindowTicker getRollingWindowTicker(WindowSize size) {
        return getRollingWindowTicker(size, LIBRARY_OBJECT);
    }

    public <T> T getRollingWindowTicker(WindowSize size, ReturnFormat format) {
        waitCorrectResponse(reachEnumConstant(size.getSize() + "Ticker"), "{");
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new WbsRollingWindowTicker(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToAllMarketRollingWindowStatisticsStreams(WindowSize size) throws Exception {
        connectToSingleStream(rollingTickersArr + size.getSize() + "@arr");
    }

    public ArrayList<WbsRollingWindowTicker> getRollingWindowTickers(WindowSize size) {
        return getRollingWindowTickers(size, LIBRARY_OBJECT);
    }

    public <T> T getRollingWindowTickers(WindowSize size, ReturnFormat format) {
        waitCorrectResponse(reachEnumConstant(size.getSize() + "Ticker"), "[");
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                ArrayList<WbsRollingWindowTicker> tickers = new ArrayList<>();
                JSONArray jTickers = new JSONArray(webSocketResponse);
                for (int j = 0; j < jTickers.length(); j++)
                    tickers.add(new WbsRollingWindowTicker(jTickers.getJSONObject(j)));
                mReturn = (T) tickers;
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToIndividualSymbolBookTickerStreams(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + bookTicker);
    }

    public WbsBookTicker getBookTicker() {
        return getBookTicker(LIBRARY_OBJECT);
    }

    public <T> T getBookTicker(ReturnFormat format) {
        while (webSocketResponse == null || !webSocketResponse.contains("bookTicker"))
            onSpinWait();
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new WbsBookTicker(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToPartialBookDepthStreams(String symbol, int levels) throws Exception {
        connectToPartialBookDepthStreams(symbol, levels, -1);
    }

    public void connectToPartialBookDepthStreams(String symbol, int levels, int updateSpeed) throws Exception {
        String streamUrl = symbol.toLowerCase() + Streams.depth + levels;
        if (updateSpeed != -1)
            streamUrl += "@" + updateSpeed + "ms";
        connectToSingleStream(streamUrl);
    }

    public OrderBook getPartialBookDepth() {
        return getPartialBookDepth(LIBRARY_OBJECT);
    }

    public <T> T getPartialBookDepth(ReturnFormat format) {
        while (webSocketResponse == null || !webSocketResponse.contains("lastUpdateId"))
            onSpinWait();
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new OrderBook(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    public void connectToDiffDepthStream(String symbol) throws Exception {
        connectToDiffDepthStream(symbol, -1);
    }

    public void connectToDiffDepthStream(String symbol, int updateSpeed) throws Exception {
        String streamUrl = symbol.toLowerCase() + Streams.depth;
        if (updateSpeed != -1)
            streamUrl += "@" + updateSpeed + "ms";
        connectToSingleStream(streamUrl);
    }

    public DiffDepth getDiffDepth() {
        return getDiffDepth(LIBRARY_OBJECT);
    }

    public <T> T getDiffDepth(ReturnFormat format) {
        waitCorrectResponse(depthUpdate);
        T mReturn;
        switch (format) {
            case JSON:
                mReturn = (T) new JSONObject(webSocketResponse);
                break;
            case LIBRARY_OBJECT:
                mReturn = (T) new DiffDepth(new JSONObject(webSocketResponse));
                break;
            default:
                mReturn = (T) webSocketResponse;
        }
        releaseResources();
        return mReturn;
    }

    private void connectToSingleStream(String stream) throws Exception {
        startWebsocket(WEB_SOCKET_DATA_STREAM_ENDPOINT + "/ws/" + stream);
    }

    private void releaseResources() {
        webSocketResponse = null;
        streamAvailable = true;
    }

    /**
     * Method to start the websocket connection
     *
     * @param endpoint: the endpoint of the stream
     **/
    private void startWebsocket(String endpoint) throws Exception {
        new WebSocketClient(new URI(endpoint)) {

            /**
             * Called after an opening handshake has been performed and the given websocket is ready to be
             * written on.
             *
             * @param handshakeData The handshake of the websocket instance
             */
            @Override
            public void onOpen(ServerHandshake handshakeData) {
            }

            /**
             * Callback for string messages received from the remote host
             *
             * @param message The UTF-8 decoded message that was received.
             **/
            @Override
            public void onMessage(String message) {
                if (streamAvailable) {
                    webSocketResponse = message;
                    if (endpoint.endsWith(bookTicker.stream)) {
                        synchronized (webSocketResponse) {
                            webSocketResponse = new JSONObject(webSocketResponse).put("e", "bookTicker").toString();
                        }
                    }
                }
            }

            /**
             * Called after the websocket connection has been closed.
             *
             * @param code   The codes can be looked up here
             * @param reason Additional information string
             * @param remote Returns whether the closing of the connection was initiated by the remote
             *               host.
             **/
            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            /**
             * Called when errors occurs. If an error causes the websocket connection to fail {@link
             * #onClose(int, String, boolean)} will be called additionally.<br> This method will be called
             * primarily because of IO or protocol errors.<br> If the given exception is an RuntimeException
             * that probably means that you encountered a bug.<br>
             *
             * @param ex The exception causing this error
             **/
            @Override
            public void onError(Exception ex) {
                try {
                    throw new Exception(ex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.connect();
    }

    /**
     * Method to wait the correct response to format the correct object or return the correct response
     *
     * @param type: type of the event to wait
     **/
    @Wrapper
    private void waitCorrectResponse(EventType type) {
        waitCorrectResponse(type, null);
    }

    /**
     * Method to wait the correct response to format the correct object or return the correct response
     *
     * @param type:       type of the event to wait
     * @param charToWait: the start char of the {@link #webSocketResponse} to wait
     **/
    private synchronized void waitCorrectResponse(EventType type, String charToWait) {
        while (!streamAvailable)
            onSpinWait();
        if (webSocketResponse != null)
            streamAvailable = false;
        if (charToWait == null) {
            while (webSocketResponse == null || reachEnumConstant(getString(new JSONObject(webSocketResponse), "e",
                    no_content.name())) != type) {
                onSpinWait();
            }
        } else {
            if (charToWait.startsWith("{")) {
                while (webSocketResponse == null || !webSocketResponse.startsWith(charToWait) ||
                        reachEnumConstant(getString(new JSONObject(webSocketResponse), "e", no_content.name())) !=
                                type) {
                    onSpinWait();
                }
            } else {
                while (webSocketResponse == null || !webSocketResponse.startsWith(charToWait) || reachEnumConstant(
                        getString(new JSONArray(webSocketResponse).getJSONObject(0), "e", no_content.name()))
                        != type) {
                    onSpinWait();
                }
            }
        }
    }

}
