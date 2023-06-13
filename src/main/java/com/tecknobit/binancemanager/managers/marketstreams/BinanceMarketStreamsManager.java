package com.tecknobit.binancemanager.managers.marketstreams;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
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

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
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
 */
public class BinanceMarketStreamsManager extends BinanceManager {

    /**
     * {@code Streams} list of available streams
     */
    public enum Streams {

        /**
         * {@code aggTrade} stream
         */
        aggTrade("@aggTrade"),

        /**
         * {@code trade} stream
         */
        trade("@trade"),

        /**
         * {@code kline_} stream
         */
        kline_("@kline_"),

        /**
         * {@code miniTicker} stream
         */
        miniTicker("@miniTicker"),

        /**
         * {@code miniTickerArr} stream
         */
        miniTickerArr("!miniTicker@arr"),

        /**
         * {@code ticker} stream
         */
        ticker("@ticker"),

        /**
         * {@code tickersArr} stream
         */
        tickersArr("!ticker@arr"),

        /**
         * {@code rollingTickersArr} stream
         */
        rollingTickersArr("!ticker_"),

        /**
         * {@code ticker_} stream
         */
        ticker_("@ticker_"),

        /**
         * {@code bookTicker} stream
         */
        bookTicker("@bookTicker"),

        /**
         * {@code depth} stream
         */
        depth("@depth");

        /**
         * {@code stream} value
         */
        private final String stream;

        /**
         * Constructor to init a {@link Streams}
         *
         * @param stream: stream value
         */
        Streams(String stream) {
            this.stream = stream;
        }

        /**
         * Method to get {@link #stream} instance <br>
         * No-any params required
         *
         * @return {@link #stream} instance as {@link String}
         */
        public String getStream() {
            return stream;
        }

        /**
         * Method to get {@link #stream} instance <br>
         * No-any params required
         *
         * @return {@link #stream} instance as {@link String}
         */
        @Override
        public String toString() {
            return stream;
        }

    }

    /**
     * {@code WEB_SOCKET_DATA_STREAM_ENDPOINT} is constant for WEB_SOCKET_DATA_STREAM_ENDPOINT's endpoint
     */
    public static final String WEB_SOCKET_DATA_STREAM_ENDPOINT = "wss://data-stream.binance.com";

    /**
     * {@code webSocketResponse} response obtained from the websocket connection
     */
    private volatile String webSocketResponse;

    /**
     * {@code streamAvailable} whether the stream chanel is available
     */
    private volatile boolean streamAvailable = true;

    /**
     * Constructor to init a {@link BinanceMarketStreamsManager}
     *
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     */
    public BinanceMarketStreamsManager(String defaultErrorMessage, int timeout) throws SystemException, IOException {
        super(null, defaultErrorMessage, timeout);
    }

    /**
     * Constructor to init a {@link BinanceMarketStreamsManager}
     *
     * @param defaultErrorMessage : custom error to show when is not a request error
     */
    public BinanceMarketStreamsManager(String defaultErrorMessage) throws SystemException, IOException {
        super(null, defaultErrorMessage);
    }

    /**
     * Constructor to init a {@link BinanceMarketStreamsManager}
     *
     * @param timeout :     custom timeout for request
     */
    public BinanceMarketStreamsManager(int timeout) throws SystemException, IOException {
        super(null, timeout);
    }

    /**
     * Constructor to init a {@link BinanceMarketStreamsManager} <br>
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
     */
    public BinanceMarketStreamsManager() {
        super();
    }

    /**
     * Method to connect to multiple streams at the same time
     *
     * @param streams: list of streams
     * @implNote the stream item must concatenate in this way: <br>
     * <b>stream params</b> + {@link Streams} item
     */
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

    /**
     * Method to connect to the aggregate trade stream
     *
     * @param symbol: symbol of the aggregate trade to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#aggregate-trade-streams">
     * Aggregate Trade Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@aggTrade")
    public void connectToAggTradeStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + Streams.aggTrade);
    }

    /**
     * Method to get the aggregate trade stream response <br>
     * No-any params required
     *
     * @return aggregate trade as {@link AggregateTrade} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#aggregate-trade-streams">
     * Aggregate Trade Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToAggTradeStream(String)} to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@aggTrade")
    public AggregateTrade getAggregateTrade() {
        return getAggregateTrade(LIBRARY_OBJECT);
    }

    /**
     * Method to get the aggregate trade stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return aggregate trade as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#aggregate-trade-streams">
     * Aggregate Trade Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToAggTradeStream(String)} to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@aggTrade")
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

    /**
     * Method to connect to the trade stream
     *
     * @param symbol: symbol of the trade to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-streams">
     * Trade Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@trade")
    public void connectToTradeStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + Streams.trade);
    }

    /**
     * Method to get the trade stream response <br>
     * No-any params required
     *
     * @return trade as {@link WbsTrade} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-streams">
     * Trade Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToTradeStream(String)} to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@trade")
    public WbsTrade getTrade() {
        return getTrade(LIBRARY_OBJECT);
    }

    /**
     * Method to get the trade stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return trade as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-streams">
     * Trade Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToTradeStream(String)} to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@trade")
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

    /**
     * Method to connect to the kline stream
     *
     * @param symbol:   symbol of the kline to connect
     * @param interval: interval of the kline to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">
     * Kline/Candlestick Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@kline_<interval>")
    public void connectToKlineCandlestickStream(String symbol, Interval interval) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + kline_ + interval);
    }

    /**
     * Method to get the kline stream response <br>
     * No-any params required
     *
     * @return kline as {@link WbsKline} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">
     * Kline/Candlestick Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToKlineCandlestickStream(String, Interval)}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@kline_<interval>")
    public WbsKline getKlineCandlestick() {
        return getKlineCandlestick(LIBRARY_OBJECT);
    }

    /**
     * Method to get the kline stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return kline as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">
     * Kline/Candlestick Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToKlineCandlestickStream(String, Interval)}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@kline_<interval>")
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

    /**
     * Method to connect to the mini ticker stream
     *
     * @param symbol: symbol of the mini ticker to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-mini-ticker-stream">
     * Individual Symbol Mini Ticker Stream</a>
     */
    @RequestPath(method = GET, path = "<symbol>@miniTicker")
    public void connectToIndividualSymbolMiniTickerStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + miniTicker);
    }

    /**
     * Method to get the mini ticker stream response <br>
     * No-any params required
     *
     * @return mini ticker as {@link WbsMiniTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-mini-ticker-stream">
     * Individual Symbol Mini Ticker Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolMiniTickerStream(String)}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@kline_<interval>")
    public WbsMiniTicker getMiniTicker() {
        return getMiniTicker(LIBRARY_OBJECT);
    }

    /**
     * Method to get the mini ticker stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return mini ticker as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-mini-ticker-stream">
     * Individual Symbol Mini Ticker Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolMiniTickerStream(String)}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@kline_<interval>")
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

    /**
     * Method to connect to the all market mini ticker stream <br>
     * No-any params required
     *
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-mini-tickers-stream">
     * All Market Mini Tickers Stream</a>
     */
    @RequestPath(method = GET, path = "!miniTicker@arr")
    public void connectToAllMarketMiniTickersStream() throws Exception {
        connectToSingleStream(miniTickerArr.stream);
    }

    /**
     * Method to get the mini tickers stream response <br>
     * No-any params required
     *
     * @return mini tickers list as {@link ArrayList} of {@link WbsMiniTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-mini-tickers-stream">
     * All Market Mini Tickers Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToAllMarketMiniTickersStream()}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "!miniTicker@arr")
    public ArrayList<WbsMiniTicker> getMiniTickers() {
        return getMiniTickers(LIBRARY_OBJECT);
    }

    /**
     * Method to get the mini tickers stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return mini tickers list as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-mini-tickers-stream">
     * All Market Mini Tickers Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToAllMarketMiniTickersStream()}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "!miniTicker@arr")
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

    /**
     * Method to connect to the ticker stream
     *
     * @param symbol: symbol of the ticker to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-ticker-streams">
     * Individual Symbol Ticker Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@ticker")
    public void connectToIndividualSymbolTickerStream(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + ticker);
    }

    /**
     * Method to get the ticker response <br>
     * No-any params required
     *
     * @return ticker as {@link WbsTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-mini-tickers-stream">
     * All Market Mini Tickers Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolTickerStream(String)}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@ticker")
    public WbsTicker getSymbolTicker() {
        return getSymbolTicker(LIBRARY_OBJECT);
    }

    /**
     * Method to get the ticker response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ticker as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-mini-tickers-stream">
     * All Market Mini Tickers Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolTickerStream(String)}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@ticker")
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

    /**
     * Method to connect to the all market ticker stream <br>
     * No-any params required
     *
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-tickers-stream">
     * All Market Tickers Stream</a>
     */
    @RequestPath(method = GET, path = "!ticker@arr")
    public void connectToAllMarketTickersStream() throws Exception {
        connectToSingleStream(tickersArr.stream);
    }

    /**
     * Method to get the tickers stream response <br>
     * No-any params required
     *
     * @return tickers list as {@link ArrayList} of {@link WbsTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-tickers-stream">
     * All Market Tickers Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToAllMarketTickersStream()}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "!ticker@arr")
    public ArrayList<WbsTicker> getSymbolTickers() {
        return getSymbolTickers(LIBRARY_OBJECT);
    }

    /**
     * Method to get the tickers stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return tickers list as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-tickers-stream">
     * All Market Tickers Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToAllMarketTickersStream()}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "!ticker@arr")
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

    /**
     * Method to connect to the rolling ticker stream
     *
     * @param symbol: symbol of the rolling ticker to connect
     * @param size:   size of the rolling ticker to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-rolling-window-statistics-streams">
     * Individual Symbol Rolling Window Statistics Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@ticker_<window_size>")
    public void connectToIndividualSymbolRollingWindowStatisticsStreams(String symbol, WindowSize size) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + ticker_ + size.getSize());
    }

    /**
     * Method to get the rolling ticker stream response
     *
     * @param size: size of the rolling ticker requested
     * @return rolling ticker as {@link WbsRollingWindowTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-rolling-window-statistics-streams">
     * Individual Symbol Rolling Window Statistics Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolRollingWindowStatisticsStreams(String, WindowSize)}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@ticker_<window_size>")
    public WbsRollingWindowTicker getRollingWindowTicker(WindowSize size) {
        return getRollingWindowTicker(size, LIBRARY_OBJECT);
    }

    /**
     * Method to get the rolling ticker stream response
     *
     * @param size:   size of the rolling ticker requested
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return rolling tickers as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-rolling-window-statistics-streams">
     * Individual Symbol Rolling Window Statistics Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolRollingWindowStatisticsStreams(String, WindowSize)}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@ticker_<window_size>")
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

    /**
     * Method to connect to the all market rolling tickers stream
     *
     * @param size: size of the rolling tickers to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-rolling-window-statistics-streams">
     * All Market Rolling Window Statistics Streams</a>
     */
    @RequestPath(method = GET, path = "!ticker_<window-size>@arr")
    public void connectToAllMarketRollingWindowStatisticsStreams(WindowSize size) throws Exception {
        connectToSingleStream(rollingTickersArr + size.getSize() + "@arr");
    }

    /**
     * Method to get the rolling tickers stream response
     *
     * @param size: size of the rolling ticker requested
     * @return rolling tickers list as {@link ArrayList} of {@link WbsRollingWindowTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-rolling-window-statistics-streams">
     * All Market Rolling Window Statistics Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToAllMarketRollingWindowStatisticsStreams(WindowSize)}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "!ticker_<window-size>@arr")
    public ArrayList<WbsRollingWindowTicker> getRollingWindowTickers(WindowSize size) {
        return getRollingWindowTickers(size, LIBRARY_OBJECT);
    }

    /**
     * Method to get the rolling tickers stream response
     *
     * @param size:   size of the rolling ticker requested
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return rolling tickers list as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-market-rolling-window-statistics-streams">
     * All Market Rolling Window Statistics Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToAllMarketRollingWindowStatisticsStreams(WindowSize)}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "!ticker_<window-size>@arr")
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

    /**
     * Method to connect to the book-ticker stream
     *
     * @param symbol: symbol of the book-ticker to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-book-ticker-streams">
     * Individual Symbol Book Ticker Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@bookTicker")
    public void connectToIndividualSymbolBookTickerStreams(String symbol) throws Exception {
        connectToSingleStream(symbol.toLowerCase() + bookTicker);
    }

    /**
     * Method to get the book-ticker stream response <br>
     * No-any params required
     *
     * @return book-ticker as {@link WbsBookTicker} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-book-ticker-streams">
     * Individual Symbol Book Ticker Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolBookTickerStreams(String)}
     * to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@bookTicker")
    public WbsBookTicker getBookTicker() {
        return getBookTicker(LIBRARY_OBJECT);
    }

    /**
     * Method to get the book-ticker stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return book-ticker as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-book-ticker-streams">
     * Individual Symbol Book Ticker Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToIndividualSymbolBookTickerStreams(String)}
     * to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@bookTicker")
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

    /**
     * Method to connect to the book-depth stream
     *
     * @param symbol: symbol of the book-depth to connect
     * @param levels: top bids and asks -> valid are 5, 10, or 20
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#partial-book-depth-streams">
     * Partial Book Depth Streams</a>
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@depth<levels>")
    public void connectToPartialBookDepthStreams(String symbol, int levels) throws Exception {
        connectToPartialBookDepthStreams(symbol, levels, -1);
    }

    /**
     * Method to connect to the book-depth stream
     *
     * @param symbol:      symbol of the book-depth to connect
     * @param levels:      top bids and asks -> valid are 5, 10, or 20
     * @param updateSpeed: update speed of the refresh -> valid are 100ms, 1000ms
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#partial-book-depth-streams">
     * Partial Book Depth Streams</a>
     */
    @RequestPath(method = GET, path = "<symbol>@depth<levels>")
    public void connectToPartialBookDepthStreams(String symbol, int levels, int updateSpeed) throws Exception {
        String streamUrl = symbol.toLowerCase() + Streams.depth + levels;
        if (updateSpeed != -1)
            streamUrl += "@" + updateSpeed + "ms";
        connectToSingleStream(streamUrl);
    }

    /**
     * Method to get the book-depth stream response <br>
     * No-any params required
     *
     * @return book-depth as {@link OrderBook} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#partial-book-depth-streams">
     * Partial Book Depth Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToPartialBookDepthStreams(String, int)} or
     * {@link #connectToPartialBookDepthStreams(String, int, int)}  to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@depth<levels>")
    public OrderBook getPartialBookDepth() {
        return getPartialBookDepth(LIBRARY_OBJECT);
    }

    /**
     * Method to get the book-depth stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return book-depth  as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#partial-book-depth-streams">
     * Partial Book Depth Streams</a>
     * @implNote you must at least invoke one time first {@link #connectToPartialBookDepthStreams(String, int)} or
     * {@link #connectToPartialBookDepthStreams(String, int, int)}  to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@depth<levels>")
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

    /**
     * Method to connect to the diff. depth stream
     *
     * @param symbol: symbol of the diff. depth to connect
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream">
     * Diff. Depth Stream</a>
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@depth")
    public void connectToDiffDepthStream(String symbol) throws Exception {
        connectToDiffDepthStream(symbol, -1);
    }

    /**
     * Method to connect to the diff. depth stream
     *
     * @param symbol:      symbol of the diff. depth to connect
     * @param updateSpeed: update speed of the refresh -> valid are 100ms, 1000ms
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream">
     * Diff. Depth Stream</a>
     */
    @RequestPath(method = GET, path = "<symbol>@depth")
    public void connectToDiffDepthStream(String symbol, int updateSpeed) throws Exception {
        String streamUrl = symbol.toLowerCase() + Streams.depth;
        if (updateSpeed != -1)
            streamUrl += "@" + updateSpeed + "ms";
        connectToSingleStream(streamUrl);
    }

    /**
     * Method to get the diff. depth stream response <br>
     * No-any params required
     *
     * @return diff. depth as {@link DiffDepth} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream">
     * Diff. Depth Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToDiffDepthStream(String)} or
     * {@link #connectToDiffDepthStream(String, int)}  to get the correct response
     */
    @Wrapper
    @RequestPath(method = GET, path = "<symbol>@depth")
    public DiffDepth getDiffDepth() {
        return getDiffDepth(LIBRARY_OBJECT);
    }

    /**
     * Method to get the diff. depth stream response
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return diff. depth  as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream">
     * Diff. Depth Stream</a>
     * @implNote you must at least invoke one time first {@link #connectToDiffDepthStream(String)} or
     * {@link #connectToDiffDepthStream(String, int)}  to get the correct response
     */
    @Returner
    @RequestPath(method = GET, path = "<symbol>@depth")
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

    /**
     * Method to connect to one stream
     *
     * @param stream: the stream name to connect
     */
    private void connectToSingleStream(String stream) throws Exception {
        startWebsocket(WEB_SOCKET_DATA_STREAM_ENDPOINT + "/ws/" + stream);
    }

    /**
     * Method to release the resources after websocket communication <br>
     * No-any params required
     */
    private void releaseResources() {
        webSocketResponse = null;
        streamAvailable = true;
    }

    /**
     * Method to start the websocket connection
     *
     * @param endpoint: the endpoint of the stream
     */
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
             */
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
             */
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
             */
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
     */
    @Wrapper
    private void waitCorrectResponse(EventType type) {
        waitCorrectResponse(type, null);
    }

    /**
     * Method to wait the correct response to format the correct object or return the correct response
     *
     * @param type:       type of the event to wait
     * @param charToWait: the start char of the {@link #webSocketResponse} to wait
     */
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
