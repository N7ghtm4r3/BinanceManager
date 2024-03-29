package com.tecknobit.binancemanager.managers.market;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.market.records.CurrentAveragePrice;
import com.tecknobit.binancemanager.managers.market.records.OrderBook;
import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick;
import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.ExchangePermission;
import com.tecknobit.binancemanager.managers.market.records.tickers.OrderBookTicker;
import com.tecknobit.binancemanager.managers.market.records.tickers.PriceTicker;
import com.tecknobit.binancemanager.managers.market.records.tickers.RollingTicker;
import com.tecknobit.binancemanager.managers.market.records.tickers.Ticker.ResponseType;
import com.tecknobit.binancemanager.managers.market.records.tickers.TickerPriceChange;
import com.tecknobit.binancemanager.managers.market.records.trade.CompressedTrade;
import com.tecknobit.binancemanager.managers.market.records.trade.Trade;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.trading.TradingTools.computeTPTOPIndex;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.JSON;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceMarketManager} class is useful to manage all {@code "Binance"}'s Market Endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#market-data-endpoints">
 * Market Data Endpoints</a>
 * @see BinanceManager
 * @see Manager
 */
public class BinanceMarketManager extends BinanceManager {

    /**
     * {@code TIMESTAMP_ENDPOINT} is constant for TIMESTAMP_ENDPOINT's endpoint
     */
    public static final String TIMESTAMP_ENDPOINT = "/api/v3/time";

    /**
     * {@code TEST_CONNECTIVITY_ENDPOINT} is constant for TEST_CONNECTIVITY_ENDPOINT's endpoint
     */
    public static final String TEST_CONNECTIVITY_ENDPOINT = "/api/v3/ping";

    /**
     * {@code EXCHANGE_INFORMATION_ENDPOINT} is constant for EXCHANGE_INFORMATION_ENDPOINT's endpoint
     */
    public static final String EXCHANGE_INFORMATION_ENDPOINT = "/api/v3/exchangeInfo";

    /**
     * {@code ORDER_BOOK_ENDPOINT} is constant for ORDER_BOOK_ENDPOINT's endpoint
     */
    public static final String ORDER_BOOK_ENDPOINT = "/api/v3/depth";

    /**
     * {@code RECENT_TRADE_LIST_ENDPOINT} is constant for RECENT_TRADE_LIST_ENDPOINT's endpoint
     */
    public static final String RECENT_TRADE_LIST_ENDPOINT = "/api/v3/trades";

    /**
     * {@code OLD_TRADE_LOOKUP_ENDPOINT} is constant for OLD_TRADE_LOOKUP_ENDPOINT's endpoint
     */
    public static final String OLD_TRADE_LOOKUP_ENDPOINT = "/api/v3/historicalTrades";

    /**
     * {@code COMPRESSED_TRADE_LIST_ENDPOINT} is constant for COMPRESSED_TRADE_LIST_ENDPOINT's endpoint
     */
    public static final String COMPRESSED_TRADE_LIST_ENDPOINT = "/api/v3/aggTrades";

    /**
     * {@code CANDLESTICK_DATA_ENDPOINT} is constant for CANDLESTICK_DATA_ENDPOINT's endpoint
     */
    public static final String CANDLESTICK_DATA_ENDPOINT = "/api/v3/klines";

    /**
     * {@code UIKLINES_ENDPOINT} is constant for UIKLINES_ENDPOINT's endpoint
     */
    public static final String UIKLINES_ENDPOINT = "/api/v3/uiKlines";

    /**
     * {@code CURRENT_AVERAGE_PRICE_ENDPOINT} is constant for CURRENT_AVERAGE_PRICE_ENDPOINT's endpoint
     */
    public static final String CURRENT_AVERAGE_PRICE_ENDPOINT = "/api/v3/avgPrice";

    /**
     * {@code TICKER_PRICE_CHANGE_ENDPOINT} is constant for TICKER_PRICE_CHANGE_ENDPOINT's endpoint
     */
    public static final String TICKER_PRICE_CHANGE_ENDPOINT = "/api/v3/ticker/24hr";

    /**
     * {@code PRICE_TICKER_ENDPOINT} is constant for PRICE_TICKER_ENDPOINT's endpoint
     */
    public static final String PRICE_TICKER_ENDPOINT = "/api/v3/ticker/price";

    /**
     * {@code ROLLING_TICKER_ENDPOINT} is constant for ROLLING_TICKER_ENDPOINT's endpoint
     */
    public static final String ROLLING_TICKER_ENDPOINT = "/api/v3/ticker";

    /**
     * {@code BOOK_TICKER_ENDPOINT} is constant for BOOK_TICKER_ENDPOINT's endpoint
     */
    public static final String BOOK_TICKER_ENDPOINT = "/api/v3/ticker/bookTicker";

    /**
     * Constructor to init a {@link BinanceMarketManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     */
    public BinanceMarketManager(String baseEndpoint, String defaultErrorMessage,
                                int timeout) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout);
    }

    /**
     * Constructor to init a {@link BinanceMarketManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     */
    public BinanceMarketManager(String baseEndpoint, String defaultErrorMessage) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage);
    }

    /**
     * Constructor to init a {@link BinanceMarketManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     */
    public BinanceMarketManager(String baseEndpoint, int timeout) throws SystemException, IOException {
        super(baseEndpoint, timeout);
    }

    /**
     * Constructor to init a {@link BinanceMarketManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     */
    public BinanceMarketManager(String baseEndpoint) throws SystemException, IOException {
        super(baseEndpoint);
    }

    /**
     * Constructor to init a {@link BinanceMarketManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceMarketManager}'s manager without re-insert
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
    public BinanceMarketManager() {
        super();
    }

    /**
     * Request to get if service is available
     * No-any params required
     *
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-connectivity">
     * Test Connectivity</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/ping")
    public boolean isMarketServiceWorking() throws IOException {
        return sendGetRequest(TEST_CONNECTIVITY_ENDPOINT, "").equals("{}");
    }

    /**
     * Request to get exchange information <br>
     * No-any params required
     *
     * @return exchange information as {@link ExchangeInformation} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public ExchangeInformation getExchangeInformation() throws IOException {
        return getExchangeInformation(LIBRARY_OBJECT);
    }

    /**
     * Request to get exchange information
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public <T> T getExchangeInformation(ReturnFormat format) throws IOException {
        return returnExchangeInformation(sendGetRequest(EXCHANGE_INFORMATION_ENDPOINT, (String) null), format);
    }

    /**
     * Request to get exchange information
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @return exchange information as {@link ExchangeInformation} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public ExchangeInformation getExchangeInformation(String symbol) throws Exception {
        return getExchangeInformation(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get exchange information
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public <T> T getExchangeInformation(String symbol, ReturnFormat format) throws Exception {
        return returnExchangeInformation(sendGetRequest(EXCHANGE_INFORMATION_ENDPOINT, "?symbol=" + symbol), format);
    }

    /**
     * Request to get exchange information
     *
     * @param symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @return exchange information as {@link ExchangeInformation} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public ExchangeInformation getExchangeInformation(String[] symbols) throws Exception {
        return getExchangeInformation(symbols, LIBRARY_OBJECT);
    }

    /**
     * Request to get exchange information
     *
     * @param symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public <T> T getExchangeInformation(String[] symbols, ReturnFormat format) throws Exception {
        return returnExchangeInformation(sendGetRequest(EXCHANGE_INFORMATION_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /**
     * Request to get exchange information
     *
     * @param permission: permission to use to fetch the information
     * @return exchange information as {@link ExchangeInformation} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public ExchangeInformation getExchangeInformation(ExchangePermission permission) throws Exception {
        return getExchangeInformation(permission, LIBRARY_OBJECT);
    }

    /**
     * Request to get exchange information
     *
     * @param permission: permission to use to fetch the information
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public <T> T getExchangeInformation(ExchangePermission permission, ReturnFormat format) throws Exception {
        return returnExchangeInformation(sendGetRequest(EXCHANGE_INFORMATION_ENDPOINT, "?permissions="
                + permission), format);
    }

    /**
     * Request to get exchange information
     *
     * @param permissions: permissions to use to fetch the information in array of {@link ExchangePermission} format
     * @return exchange information as {@link ExchangeInformation} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public ExchangeInformation getExchangeInformation(ExchangePermission[] permissions) throws Exception {
        return getExchangeInformation(permissions, LIBRARY_OBJECT);
    }

    /**
     * Request to get exchange information
     *
     * @param permissions: permissions to use to fetch the information in array of {@link ExchangePermission} format
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public <T> T getExchangeInformation(ExchangePermission[] permissions, ReturnFormat format) throws Exception {
        return returnExchangeInformation(sendGetRequest(EXCHANGE_INFORMATION_ENDPOINT, "?permissions=[" +
                assembleSymbolsList(permissions) + "]"), format);
    }

    /**
     * Request to get exchange information
     *
     * @param extraParams: extra params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                               {@code "symbol"} symbol from fetch the information - [STRING]
     *                         </li>
     *                         <li>
     *                               {@code "symbols"} list of symbols from fetch the information, you can pass directly
     *                               the list of symbols will be formatted in the correct format to do the request - [STRING]
     *                         </li>
     *                         <li>
     *                               {@code "permissions"} permission to use to fetch the information, constants available
     *                               {@link ExchangePermission} - [STRING]
     *                         </li>
     *                         <li>
     *                               {@code "permissions"} list of permissions to use to fetch the information, constants available
     *                               {@link ExchangePermission}, you can pass directly the list of permissions will be formatted
     *                               in the correct format to do the request - [STRING]
     *                         </li>
     *                     </ul>
     * @return exchange information as {@link ExchangeInformation} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public ExchangeInformation getExchangeInformation(Params extraParams) throws Exception {
        return getExchangeInformation(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get exchange information
     *
     * @param extraParams: extra params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                               {@code "symbol"} symbol from fetch the information - [STRING]
     *                         </li>
     *                         <li>
     *                               {@code "symbols"} list of symbols from fetch the information, you can pass directly
     *                               the list of symbols will be formatted in the correct format to do the request - [STRING]
     *                         </li>
     *                         <li>
     *                               {@code "permissions"} permission to use to fetch the information, constants available
     *                               {@link ExchangePermission} - [STRING]
     *                         </li>
     *                         <li>
     *                               {@code "permissions"} list of permissions to use to fetch the information, constants available
     *                               {@link ExchangePermission}, you can pass directly the list of permissions will be formatted
     *                               in the correct format to do the request - [STRING]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/exchangeInfo")
    public <T> T getExchangeInformation(Params extraParams, ReturnFormat format) throws Exception {
        manageList(extraParams, "symbols");
        manageList(extraParams, "permissions");
        return returnExchangeInformation(sendGetRequest(EXCHANGE_INFORMATION_ENDPOINT,
                extraParams.createQueryString()), format);
    }

    /**
     * Method to create an exchange information object
     *
     * @param exchangeInformationResponse: obtained from Binance's response
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @return exchange information as {@code "format"} defines
     */
    @Returner
    private <T> T returnExchangeInformation(String exchangeInformationResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(exchangeInformationResponse);
            case LIBRARY_OBJECT:
                return (T) new ExchangeInformation(new JSONObject(exchangeInformationResponse));
            default:
                return (T) exchangeInformationResponse;
        }
    }

    /**
     * Request to get order book
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @return order book as {@link OrderBook} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * Order Book</a>
     */
    @Wrapper
    @RequestWeight(weight = "1/5/10/50(IP)")
    @RequestPath(method = GET, path = "/api/v3/depth")
    public OrderBook getOrderBook(String symbol) throws IOException {
        return getOrderBook(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get order book
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order book as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * Order Book</a>
     */
    @RequestWeight(weight = "1/5/10/50(IP)")
    @RequestPath(method = GET, path = "/api/v3/depth")
    public <T> T getOrderBook(String symbol, ReturnFormat format) throws IOException {
        return returnOrderBook(symbol, sendGetRequest(ORDER_BOOK_ENDPOINT, "?symbol=" + symbol),
                format);
    }

    /**
     * Request to get order book
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit:  limit of result to fetch
     * @return order book as {@link OrderBook} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * Order Book</a>
     */
    @Wrapper
    @RequestWeight(weight = "1/5/10/50(IP)")
    @RequestPath(method = GET, path = "/api/v3/depth")
    public OrderBook getOrderBook(String symbol, int limit) throws IOException {
        return getOrderBook(symbol, limit, LIBRARY_OBJECT);
    }

    /**
     * Request to get order book
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit:  limit of result to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order book as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * Order Book</a>
     */
    @RequestWeight(weight = "1/5/10/50(IP)")
    @RequestPath(method = GET, path = "/api/v3/depth")
    public <T> T getOrderBook(String symbol, int limit, ReturnFormat format) throws IOException {
        return returnOrderBook(symbol, sendGetRequest(ORDER_BOOK_ENDPOINT, "?symbol=" + symbol + "&limit=" +
                limit), format);
    }

    /**
     * Method to create an order book object
     *
     * @param symbol:            symbol to fetch exchange information es. BTCBUSD
     * @param orderBookResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return order book as {@code "format"} defines
     */
    @Returner
    private <T> T returnOrderBook(String symbol, String orderBookResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderBookResponse);
            case LIBRARY_OBJECT:
                return (T) new OrderBook(new JSONObject(orderBookResponse).put("symbol", symbol));
            default:
                return (T) orderBookResponse;
        }
    }

    /**
     * Request to get recent trade
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @return recent trade as {@link ArrayList} of {@link Trade}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Recent Trades List</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/trades")
    public ArrayList<Trade> getRecentTradesList(String symbol) throws IOException {
        return getRecentTradesList(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get recent trade
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return recent trade as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Recent Trades List</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/trades")
    public <T> T getRecentTradesList(String symbol, ReturnFormat format) throws IOException {
        return returnTradesList(sendGetRequest(RECENT_TRADE_LIST_ENDPOINT, "?symbol=" + symbol),
                format);
    }

    /**
     * Request to get recent trade
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @return recent trade as {@link ArrayList} of {@link Trade}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Recent Trades List</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/trades")
    public ArrayList<Trade> getRecentTradesList(String symbol, int limit) throws IOException {
        return getRecentTradesList(symbol, limit, LIBRARY_OBJECT);
    }

    /**
     * Request to get recent trade
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return recent trade as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Recent Trades List</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/trades")
    public <T> T getRecentTradesList(String symbol, int limit, ReturnFormat format) throws IOException {
        return returnTradesList(sendGetRequest(RECENT_TRADE_LIST_ENDPOINT, "?symbol=" + symbol + "&limit="
                + limit), format);
    }

    /**
     * Request to get old trade
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @return old trade as {@link ArrayList} of {@link Trade}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Old Trade Lookup (MARKET_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "5(IP)")
    @RequestPath(method = GET, path = "/api/v3/historicalTrades")
    public ArrayList<Trade> getOldTradesList(String symbol) throws IOException {
        return getOldTradesList(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get old trade
     *
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return old trade as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Old Trade Lookup (MARKET_DATA)</a>
     */
    @RequestWeight(weight = "5(IP)")
    @RequestPath(method = GET, path = "/api/v3/historicalTrades")
    public <T> T getOldTradesList(String symbol, ReturnFormat format) throws IOException {
        return returnTradesList(sendGetRequest(OLD_TRADE_LOOKUP_ENDPOINT, "?symbol=" + symbol), format);
    }

    /**
     * Request to get old trade
     *
     * @param symbol:      symbol to fetch exchange information es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> rade id to fetch from. Default gets most recent trades - [LONG]
     *                           </li>
     *                     </ul>
     * @return old trade as {@link ArrayList} of {@link Trade}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Old Trade Lookup (MARKET_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "5(IP)")
    @RequestPath(method = GET, path = "/api/v3/historicalTrades")
    public ArrayList<Trade> getOldTradeList(String symbol, Params extraParams) throws IOException {
        return getOldTradeList(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get old trade
     *
     * @param symbol:      symbol to fetch exchange information es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> rade id to fetch from. Default gets most recent trades - [LONG]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return old trade as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     * Old Trade Lookup (MARKET_DATA)</a>
     */
    @RequestWeight(weight = "5(IP)")
    @RequestPath(method = GET, path = "/api/v3/historicalTrades")
    public <T> T getOldTradeList(String symbol, Params extraParams, ReturnFormat format) throws IOException {
        String payload = "?symbol=" + symbol;
        payload = apiRequest.encodeAdditionalParams(payload, extraParams);
        return returnTradesList(sendGetRequest(OLD_TRADE_LOOKUP_ENDPOINT, payload), format);
    }

    /**
     * Method to create a trades list
     *
     * @param tradesListResponse: obtained from Binance's response
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return trades list as {@code "format"} defines
     */
    @Returner
    private <T> T returnTradesList(String tradesListResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(tradesListResponse);
            case LIBRARY_OBJECT:
                ArrayList<Trade> trades = new ArrayList<>();
                JSONArray jTrades = new JSONArray(tradesListResponse);
                for (int j = 0; j < jTrades.length(); j++)
                    trades.add(new Trade(jTrades.getJSONObject(j)));
                return (T) trades;
            default:
                return (T) tradesListResponse;
        }
    }

    /**
     * Request to get compressed trade list
     *
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @return compressed trade list as {@link ArrayList} of {@link CompressedTrade}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     * Compressed/Aggregate Trades List/a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/aggTrades")
    public ArrayList<CompressedTrade> getCompressedTradesList(String symbol) throws IOException {
        return getCompressedTradesList(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get compressed trade list
     *
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return compressed trade list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     * Compressed/Aggregate Trades List/a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/aggTrades")
    public <T> T getCompressedTradesList(String symbol, ReturnFormat format) throws IOException {
        return returnCompressedTradesList(sendGetRequest(COMPRESSED_TRADE_LIST_ENDPOINT, "?symbol=" + symbol),
                format);
    }

    /**
     * Request to get compressed trade list
     *
     * @param symbol:      symbol to fetch compressed trade es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromId"} -> id to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @return compressed trade list as {@link ArrayList} of {@link CompressedTrade}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     * Compressed/Aggregate Trades List/a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/aggTrades")
    public ArrayList<CompressedTrade> getCompressedTradesList(String symbol, Params extraParams) throws IOException {
        return getCompressedTradesList(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get compressed trade list
     *
     * @param symbol:      symbol to fetch compressed trade es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromId"} -> id to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return compressed trade list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     * Compressed/Aggregate Trades List/a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/aggTrades")
    public <T> T getCompressedTradesList(String symbol, Params extraParams, ReturnFormat format) throws IOException {
        String payload = "?symbol=" + symbol;
        payload = apiRequest.encodeAdditionalParams(payload, extraParams);
        return returnCompressedTradesList(sendGetRequest(COMPRESSED_TRADE_LIST_ENDPOINT, payload), format);
    }

    /**
     * Method to create a compressed trades list
     *
     * @param compressedTradesListResponse: obtained from Binance's response
     * @param format:                       return type formatter -> {@link ReturnFormat}
     * @return compressed trades list as {@code "format"} defines
     */
    @Returner
    private <T> T returnCompressedTradesList(String compressedTradesListResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(compressedTradesListResponse);
            case LIBRARY_OBJECT:
                ArrayList<CompressedTrade> compressedTrades = new ArrayList<>();
                JSONArray jTrades = new JSONArray(compressedTradesListResponse);
                for (int j = 0; j < jTrades.length(); j++)
                    compressedTrades.add(new CompressedTrade(jTrades.getJSONObject(j)));
                return (T) compressedTrades;
            default:
                return (T) compressedTradesListResponse;
        }
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     Kline/Candlestick Data</a>
     * @return candlestick data as {@link ArrayList} of {@link Candlestick}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/klines")
    public ArrayList<Candlestick> getCandlesticksList(String symbol, Interval interval) throws IOException {
        return getCandlesticksList(symbol, interval, LIBRARY_OBJECT);
    }

    /**
     * Request to get candlestick data list
     *
     * @param symbol:   symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return candlestick data as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     * Kline/Candlestick Data</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/klines")
    public <T> T getCandlesticksList(String symbol, Interval interval, ReturnFormat format) throws IOException {
        return returnCandlesticksList(sendGetRequest(CANDLESTICK_DATA_ENDPOINT, "?symbol=" + symbol +
                "&interval=" + interval), format);
    }

    /**
     * Request to get candlestick data list
     *
     * @param symbol:      symbol to fetch compressed trade es. BTCBUSD
     * @param interval:    time period to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @return candlestick data as {@link ArrayList} of {@link Candlestick}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     * Kline/Candlestick Data</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/klines")
    public ArrayList<Candlestick> getCandlesticksList(String symbol, Interval interval, Params extraParams) throws IOException {
        return getCandlesticksList(symbol, interval, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get candlestick data list
     *
     * @param symbol:      symbol to fetch compressed trade es. BTCBUSD
     * @param interval:    time period to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return candlestick data as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     * Kline/Candlestick Data</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/klines")
    public <T> T getCandlesticksList(String symbol, Interval interval, Params extraParams,
                                     ReturnFormat format) throws IOException {
        String payload = "?symbol=" + symbol + "&interval=" + interval;
        payload = apiRequest.encodeAdditionalParams(payload, extraParams);
        return returnCandlesticksList(sendGetRequest(CANDLESTICK_DATA_ENDPOINT, payload), format);
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *    UIKlines</a>
     * @return candlestick data as {@link ArrayList} of {@link Candlestick}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/api/v3/uiKlines")
    public ArrayList<Candlestick> getUIKLinesList(String symbol, Interval interval) throws IOException {
        return getUIKLinesList(symbol, interval, LIBRARY_OBJECT);
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *    UIKlines</a>
     * @return candlestick data as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/uiKlines")
    public <T> T getUIKLinesList(String symbol, Interval interval, ReturnFormat format) throws IOException {
        return returnCandlesticksList(sendGetRequest(UIKLINES_ENDPOINT, "?symbol=" + symbol + "&interval="
                + interval), format);
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                         <li>
     *                              {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                         </li>
     *                         <li>
     *                              {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                         </li>
     *                         <li>
     *                              {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                         </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *    UIKlines</a>
     * @return candlestick data as {@link ArrayList} of {@link Candlestick}
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/uiKlines")
    public ArrayList<Candlestick> getUIKLinesList(String symbol, Interval interval, Params extraParams) throws IOException {
        return getUIKLinesList(symbol, interval, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get candlestick data list
     *
     * @param symbol:      symbol to fetch compressed trade es. BTCBUSD
     * @param interval:    time period to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return candlestick data as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     * UIKlines</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/uiKlines")
    public <T> T getUIKLinesList(String symbol, Interval interval, Params extraParams, ReturnFormat format) throws IOException {
        String params = "?symbol=" + symbol + "&interval=" + interval;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnCandlesticksList(sendGetRequest(UIKLINES_ENDPOINT, params), format);
    }

    /**
     * Method to create a candlesticks list
     *
     * @param candlesticksResponse: obtained from Binance's response
     * @param format:               return type formatter -> {@link ReturnFormat}
     * @return candlesticks list as {@code "format"} defines
     */
    @Returner
    private <T> T returnCandlesticksList(String candlesticksResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(candlesticksResponse);
            case LIBRARY_OBJECT:
                ArrayList<Candlestick> candlesticksList = new ArrayList<>();
                JSONArray jList = new JSONArray(candlesticksResponse);
                for (int j = 0; j < jList.length(); j++)
                    candlesticksList.add(new Candlestick(jList.getJSONArray(j)));
                return (T) candlesticksList;
            default:
                return (T) candlesticksResponse;
        }
    }

    /**
     * Request to get current average price
     *
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @return current average price value as double
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     * Current Average Price</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/avgPrice")
    public double getCurrentAveragePriceValue(String symbol) throws IOException {
        return ((JSONObject) getCurrentAveragePrice(symbol, JSON)).getDouble("price");
    }

    /**
     * Request to get current average price
     *
     * @param symbol:   symbol to fetch current average price es. BTCBUSD
     * @param decimals: number of digits to round final value
     * @return current average price value as double rounded
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     * Current Average Price</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/avgPrice")
    public double getCurrentAveragePriceValue(String symbol, int decimals) throws IOException {
        return roundValue(((JSONObject) getCurrentAveragePrice(symbol, JSON)).getDouble("price"), decimals);
    }

    /**
     * Request to get current average price
     *
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @return current average price as {@link CurrentAveragePrice} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     * Current Average Price</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/avgPrice")
    public CurrentAveragePrice getCurrentAveragePrice(String symbol) throws IOException {
        return getCurrentAveragePrice(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get current average price
     *
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return current average price as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     * Current Average Price</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/api/v3/avgPrice")
    public <T> T getCurrentAveragePrice(String symbol, ReturnFormat format) throws IOException {
        String avgPriceResponse = sendGetRequest(CURRENT_AVERAGE_PRICE_ENDPOINT, "?symbol=" + symbol);
        switch (format) {
            case JSON:
                return (T) new JSONObject(avgPriceResponse);
            case LIBRARY_OBJECT:
                return (T) new CurrentAveragePrice(new JSONObject(avgPriceResponse));
            default:
                return (T) avgPriceResponse;
        }
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change as {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "1/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public TickerPriceChange getTickerPriceChange(String symbol) throws IOException {
        return getTickerPriceChange(symbol, LIBRARY_OBJECT);
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "1/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickerPriceChange(String symbol, ReturnFormat format) throws IOException {
        return returnTickerPriceChange(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?symbol=" + symbol), format);
    }

    /**
     * Request to get ticker price change
     *
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param type:   response formatter
     * @return ticker price change as {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @Wrapper
    @RequestWeight(weight = "1/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public TickerPriceChange getTickerPriceChange(String symbol, ResponseType type) throws IOException {
        return getTickerPriceChange(symbol, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get ticker price change
     *
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param type:   response formatter
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ticker price change {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @RequestWeight(weight = "1/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickerPriceChange(String symbol, ResponseType type, ReturnFormat format) throws IOException {
        return returnTickerPriceChange(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?symbol=" + symbol + "&type="
                + type), format);
    }

    /**
     * Method to create a ticker price change object
     *
     * @param tickerPriceChangeResponse: obtained from Binance's response
     * @param format:                    return type formatter -> {@link ReturnFormat}
     * @return ticker price change as {@code "format"} defines
     */
    @Returner
    private <T> T returnTickerPriceChange(String tickerPriceChangeResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(tickerPriceChangeResponse);
            case LIBRARY_OBJECT:
                return (T) new TickerPriceChange(new JSONObject(tickerPriceChangeResponse));
            default:
                return (T) tickerPriceChangeResponse;
        }
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public ArrayList<TickerPriceChange> getTickersPriceChangeList(String[] symbols) throws IOException {
        return getTickersPriceChangeList(symbols, LIBRARY_OBJECT);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickersPriceChangeList(String[] symbols, ReturnFormat format) throws IOException {
        return returnTickersPriceChangeList(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param type: response formatter
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public ArrayList<TickerPriceChange> getTickersPriceChangeList(String[] symbols, ResponseType type) throws IOException {
        return getTickersPriceChangeList(symbols, type, LIBRARY_OBJECT);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param type: response formatter
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickersPriceChangeList(String[] symbols, ResponseType type, ReturnFormat format) throws IOException {
        return returnTickersPriceChangeList(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?type=" + type
                + "&symbols=[" + assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public ArrayList<TickerPriceChange> getTickersPriceChangeList(ArrayList<String> symbols) throws IOException {
        return getTickersPriceChangeList(symbols, LIBRARY_OBJECT);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickersPriceChangeList(ArrayList<String> symbols, ReturnFormat format) throws IOException {
        return returnTickersPriceChangeList(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param type: response formatter
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     24hr Ticker Price Change Statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public ArrayList<TickerPriceChange> getTickersPriceChangeList(ArrayList<String> symbols,
                                                                  ResponseType type) throws IOException {
        return getTickersPriceChangeList(symbols, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get all requested tickers change list
     *
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param type:    response formatter
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @RequestWeight(weight = "1/20/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickersPriceChangeList(ArrayList<String> symbols, ResponseType type,
                                           ReturnFormat format) throws IOException {
        return returnTickersPriceChangeList(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?type=" + type
                + "&symbols=[" + assembleSymbolsList(symbols) + "]"), format);
    }

    /**
     * Request to get all tickers price change list <br>
     * No-any params required
     *
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @Wrapper
    @RequestWeight(weight = "40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public ArrayList<TickerPriceChange> getTickersPriceChangeList() throws IOException {
        return getTickersPriceChangeList(LIBRARY_OBJECT);
    }

    /**
     * Request to get all tickers price change list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @RequestWeight(weight = "40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickersPriceChangeList(ReturnFormat format) throws IOException {
        return returnTickersPriceChangeList(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, ""),
                format);
    }

    /**
     * Request to get all tickers price change list
     *
     * @param type: response formatter
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @Wrapper
    @RequestWeight(weight = "40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public ArrayList<TickerPriceChange> getTickersPriceChangeList(ResponseType type) throws IOException {
        return getTickersPriceChangeList(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get all tickers price change list
     *
     * @param type:   response formatter
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     * 24hr Ticker Price Change Statistics</a>
     */
    @RequestWeight(weight = "40(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/24hr")
    public <T> T getTickersPriceChangeList(ResponseType type, ReturnFormat format) throws IOException {
        return returnTickersPriceChangeList(sendGetRequest(TICKER_PRICE_CHANGE_ENDPOINT, "?type=" + type
        ), format);
    }

    /**
     * Method to create a tickers price change list
     *
     * @param tickersPriceResponse: obtained from Binance's response
     * @param format:               return type formatter -> {@link ReturnFormat}
     * @return tickers price change list as {@code "format"} defines
     */
    @Returner
    private <T> T returnTickersPriceChangeList(String tickersPriceResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(tickersPriceResponse);
            case LIBRARY_OBJECT:
                ArrayList<TickerPriceChange> tickerPriceStatistics = new ArrayList<>();
                JSONArray jList = new JSONArray(tickersPriceResponse);
                for (int j = 0; j < jList.length(); j++)
                    tickerPriceStatistics.add(new TickerPriceChange(jList.getJSONObject(j)));
                return (T) tickerPriceStatistics;
            default:
                return (T) tickersPriceResponse;
        }
    }

    /**
     * Request to get price ticker
     *
     * @param symbol: symbol to fetch price ticker es. BTCBUSD
     * @return price ticker as {@link PriceTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     * Symbol Price Ticker</a>
     */
    @Wrapper
    @RequestWeight(weight = "1/2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public PriceTicker getPriceTicker(String symbol) throws IOException {
        return getPriceTicker(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get price ticker
     *
     * @param symbol: symbol to fetch price ticker es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return price ticker as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     * Symbol Price Ticker</a>
     */
    @Returner
    @RequestWeight(weight = "1/2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public <T> T getPriceTicker(String symbol, ReturnFormat format) throws IOException {
        String priceTickerResponse = sendGetRequest(PRICE_TICKER_ENDPOINT, "?symbol=" + symbol);
        switch (format) {
            case JSON:
                return (T) new JSONObject(priceTickerResponse);
            case LIBRARY_OBJECT:
                return (T) new PriceTicker(new JSONObject(priceTickerResponse));
            default:
                return (T) priceTickerResponse;
        }
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     Symbol Price Ticker</a>
     * @return price ticker list as {@link ArrayList} of {@link PriceTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public ArrayList<PriceTicker> getPriceTickers(String[] symbols) throws IOException {
        return getPriceTickers(symbols, LIBRARY_OBJECT);
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     Symbol Price Ticker</a>
     * @return price ticker list as {@link ArrayList} of {@link PriceTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public <T> T getPriceTickers(String[] symbols, ReturnFormat format) throws IOException {
        return returnPriceTickersList(sendGetRequest(PRICE_TICKER_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     Symbol Price Ticker</a>
     * @return price ticker list as {@link ArrayList} of {@link PriceTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public ArrayList<PriceTicker> getPriceTickers(ArrayList<String> symbols) throws IOException {
        return getPriceTickers(symbols, LIBRARY_OBJECT);
    }

    /**
     * Request to get all requested price tickers list
     *
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return price ticker list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     * Symbol Price Ticker</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public <T> T getPriceTickers(ArrayList<String> symbols, ReturnFormat format) throws IOException {
        return returnPriceTickersList(sendGetRequest(PRICE_TICKER_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /**
     * Request to get all price tickers list <br>
     * No-any params required
     *
     * @return price ticker list as {@link ArrayList} of {@link PriceTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     * Symbol Price Ticker</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public ArrayList<PriceTicker> getPriceTickers() throws IOException {
        return getPriceTickers(LIBRARY_OBJECT);
    }

    /**
     * Request to get all price tickers list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return price ticker list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     * Symbol Price Ticker</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/price")
    public <T> T getPriceTickers(ReturnFormat format) throws IOException {
        return returnPriceTickersList(sendGetRequest(PRICE_TICKER_ENDPOINT, ""), format);
    }

    /**
     * Method to create a price tickers list
     *
     * @param priceTickersResponse: obtained from Binance's response
     * @param format:               return type formatter -> {@link ReturnFormat}
     * @return price tickers as {@code "format"} defines
     */
    @Returner
    private <T> T returnPriceTickersList(String priceTickersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(priceTickersResponse);
            case LIBRARY_OBJECT:
                ArrayList<PriceTicker> tickerPrices = new ArrayList<>();
                JSONArray jList = new JSONArray(priceTickersResponse);
                for (int j = 0; j < jList.length(); j++)
                    tickerPrices.add(new PriceTicker(jList.getJSONObject(j)));
                return (T) tickerPrices;
            default:
                return (T) priceTickersResponse;
        }
    }

    /**
     * Request to get an order book ticker
     *
     * @param symbol: symbol to fetch book ticker es. BTCBUSD
     * @return order book ticker as {@link OrderBookTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     * Symbol Order Book Ticker</a>
     */
    @Wrapper
    @RequestWeight(weight = "1/2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public OrderBookTicker getOrderBookTicker(String symbol) throws IOException {
        return getOrderBookTicker(symbol, LIBRARY_OBJECT);
    }

    /** Request to get an order book ticker
     * @param symbol: symbol to fetch book ticker es. BTCBUSD
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     Symbol Order Book Ticker</a>
     * @return order book ticker as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Returner
    @RequestWeight(weight = "1/2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public <T> T getOrderBookTicker(String symbol, ReturnFormat format) throws IOException {
        String orderTickerResponse = sendGetRequest(BOOK_TICKER_ENDPOINT, "?symbol=" + symbol);
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderTickerResponse);
            case LIBRARY_OBJECT:
                return (T) new OrderBookTicker(new JSONObject(orderTickerResponse));
            default:
                return (T) orderTickerResponse;
        }
    }

    /** Request to get all requested order book tickers list
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     Symbol Order Book Ticker</a>
     * @return order book ticker list as {@link ArrayList} of {@link OrderBookTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public ArrayList<OrderBookTicker> getOrderBookTickers(String[] symbols) throws IOException {
        return getOrderBookTickers(symbols, LIBRARY_OBJECT);
    }

    /** Request to get all requested order book tickers list
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     Symbol Order Book Ticker</a>
     * @return order book ticker list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public <T> T getOrderBookTickers(String[] symbols, ReturnFormat format) throws IOException {
        return returnOrderBookTickersList(sendGetRequest(BOOK_TICKER_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get all requested order book tickers list
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     Symbol Order Book Ticker</a>
     * @return order book ticker list as {@link ArrayList} of {@link OrderBookTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public ArrayList<OrderBookTicker> getOrderBookTickers(ArrayList<String> symbols) throws IOException {
        return getOrderBookTickers(symbols, LIBRARY_OBJECT);
    }

    /**
     * Request to get all requested order book tickers list
     *
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return order book ticker list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     * Symbol Order Book Ticker</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public <T> T getOrderBookTickers(ArrayList<String> symbols, ReturnFormat format) throws IOException {
        return returnOrderBookTickersList(sendGetRequest(BOOK_TICKER_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /**
     * Request to get all order book tickers list <br>
     * No-any params required
     *
     * @return order book ticker list as {@link ArrayList} of {@link OrderBookTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     * Symbol Order Book Ticker</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public ArrayList<OrderBookTicker> getOrderBookTickers() throws IOException {
        return getOrderBookTickers(LIBRARY_OBJECT);
    }

    /**
     * Request to get all order book tickers list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order book ticker list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     * Symbol Order Book Ticker</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker/bookTicker")
    public <T> T getOrderBookTickers(ReturnFormat format) throws IOException {
        return returnOrderBookTickersList(sendGetRequest(BOOK_TICKER_ENDPOINT, ""), format);
    }

    /**
     * Method to create an order book tickers list
     *
     * @param orderBookTickersResponse: obtained from Binance's response
     * @param format:                   return type formatter -> {@link ReturnFormat}
     * @return order book tickers as {@code "format"} defines
     */
    @Returner
    private <T> T returnOrderBookTickersList(String orderBookTickersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(orderBookTickersResponse);
            case LIBRARY_OBJECT:
                ArrayList<OrderBookTicker> bookTickers = new ArrayList<>();
                JSONArray jList = new JSONArray(orderBookTickersResponse);
                for (int j = 0; j < jList.length(); j++)
                    bookTickers.add(new OrderBookTicker(jList.getJSONObject(j)));
                return (T) bookTickers;
            default:
                return (T) orderBookTickersResponse;
        }
    }

    /**
     * Request to get 24 hours rolling window price change statistics
     *
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @return rolling ticker price change as {@link RollingTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     * Rolling window price change statistics</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public RollingTicker getRollingTicker(String symbol) throws IOException {
        return getRollingTicker(symbol, LIBRARY_OBJECT);
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public <T> T getRollingTicker(String symbol, ReturnFormat format) throws IOException {
        return returnRollingTicker(sendGetRequest(ROLLING_TICKER_ENDPOINT, "?symbol=" + symbol),
                format);
    }

    /**
     * Request to get 24 hours rolling window price change statistics
     *
     * @param symbol:      symbol to fetch ticker price change es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> response type, constants available in {@link ResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "windowSize"} -> windows size, composed by {unit}m, {unit}h or {unit}d, they cannot be combined
     *                                - [STRING, default 1d]
     *                           </li>
     *                     </ul>
     * @return rolling ticker price change as {@link RollingTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     * Rolling window price change statistics</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public RollingTicker getRollingTicker(String symbol, Params extraParams) throws IOException {
        return getRollingTicker(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get 24 hours rolling window price change statistics
     *
     * @param symbol:      symbol to fetch ticker price change es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> response type, constants available in {@link ResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "windowSize"} -> windows size, composed by {unit}m, {unit}h or {unit}d, they cannot be combined
     *                                - [STRING, default 1d]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return rolling ticker price change as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     * Rolling window price change statistics</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public <T> T getRollingTicker(String symbol, Params extraParams, ReturnFormat format) throws IOException {
        return returnRollingTicker(sendGetRequest(ROLLING_TICKER_ENDPOINT, extraParams.createQueryString() +
                "&symbol=" + symbol), format);
    }

    /**
     * Method to create a rolling ticker object
     *
     * @param rollingTickerResponse: obtained from Binance's response
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return rolling ticker as {@code "format"} defines
     */
    @Returner
    private <T> T returnRollingTicker(String rollingTickerResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(rollingTickerResponse);
            case LIBRARY_OBJECT:
                return (T) new RollingTicker(new JSONObject(rollingTickerResponse));
            default:
                return (T) rollingTickerResponse;
        }
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link RollingTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public ArrayList<RollingTicker> getRollingTickers(String[] symbols) throws IOException {
        return getRollingTickers(symbols, LIBRARY_OBJECT);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public <T> T getRollingTickers(String[] symbols, ReturnFormat format) throws IOException {
        return returnRollingTickersList(sendGetRequest(ROLLING_TICKER_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                         <li>
     *                              {@code "type"} -> response type, constants available in {@link ResponseType} - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "windowSize"} -> windows size, composed by {unit}m, {unit}h or {unit}d, they cannot be combined 
     *                              - [STRING, default 1d]
     *                         </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link RollingTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public ArrayList<RollingTicker> getRollingTickers(String[] symbols, Params extraParams) throws IOException {
        return getRollingTickers(symbols, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                         <li>
     *                              {@code "type"} -> response type, constants available in {@link ResponseType} - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "windowSize"} -> windows size, composed by {unit}m, {unit}h or {unit}d, they cannot be combined 
     *                              - [STRING, default 1d]
     *                         </li>
     *                   </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public <T> T getRollingTickers(String[] symbols, Params extraParams, ReturnFormat format) throws IOException {
        return returnRollingTickersList(sendGetRequest(ROLLING_TICKER_ENDPOINT, extraParams.createQueryString()
                + "&symbols=[" + assembleSymbolsList(symbols) + "]"), format);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link RollingTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public ArrayList<RollingTicker> getRollingTickers(ArrayList<String> symbols) throws IOException {
        return getRollingTickers(symbols, LIBRARY_OBJECT);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     Rolling window price change statistics</a>
     * @return rolling ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
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
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public <T> T getRollingTickers(ArrayList<String> symbols, ReturnFormat format) throws IOException {
        return returnRollingTickersList(sendGetRequest(ROLLING_TICKER_ENDPOINT, "?symbols=[" +
                assembleSymbolsList(symbols) + "]"), format);
    }

    /**
     * Request to get 24 hours rolling requested window price change statistics list
     *
     * @param symbols:     symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> response type, constants available in {@link ResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "windowSize"} -> windows size, composed by {unit}m, {unit}h or {unit}d, they cannot be combined
     *                                - [STRING, default 1d]
     *                           </li>
     *                     </ul>
     * @return rolling ticker price change list as {@link ArrayList} of {@link RollingTicker} custom object
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     * Rolling window price change statistics</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public ArrayList<RollingTicker> getRollingTickers(ArrayList<String> symbols, Params extraParams) throws IOException {
        return getRollingTickers(symbols, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get 24 hours rolling requested window price change statistics list
     *
     * @param symbols:     symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> response type, constants available in {@link ResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "windowSize"} -> windows size, composed by {unit}m, {unit}h or {unit}d, they cannot be combined
     *                                - [STRING, default 1d]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return rolling ticker price change list as {@code "format"} defines
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     * Rolling window price change statistics</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/ticker")
    public <T> T getRollingTickers(ArrayList<String> symbols, Params extraParams, ReturnFormat format) throws IOException {
        return returnRollingTickersList(sendGetRequest(ROLLING_TICKER_ENDPOINT, extraParams.createQueryString()
                + "&symbols=[" + assembleSymbolsList(symbols) + "]"), format);
    }

    /**
     * Method to create a rolling tickers list
     *
     * @param rollingTickersResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return rolling tickers as {@code "format"} defines
     */
    @Returner
    private <T> T returnRollingTickersList(String rollingTickersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(rollingTickersResponse);
            case LIBRARY_OBJECT:
                ArrayList<RollingTicker> rollingTickers = new ArrayList<>();
                JSONArray jList = new JSONArray(rollingTickersResponse);
                for (int j = 0; j < jList.length(); j++)
                    rollingTickers.add(new RollingTicker(jList.getJSONObject(j)));
                return (T) rollingTickers;
            default:
                return (T) rollingTickersResponse;
        }
    }

    /**
     * Method to get forecast of a cryptocurrency in base of day's gap inserted
     *
     * @param symbol:         symbol to calculate forecast es. BTCBUSD
     * @param interval:       temporal interval of data for the forecast
     * @param intervalDays:   days gap for the prevision range
     * @param toleranceValue: tolerance for select similar value compared to lastValue inserted
     * @param decimalDigits:  number of digits to round final forecast value
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     */
    @Wrapper
    public double getSymbolForecast(String symbol, Interval interval, int intervalDays, double toleranceValue,
                                    int decimalDigits) throws IOException {
        return roundValue(getSymbolForecast(symbol, interval, intervalDays, toleranceValue), decimalDigits);
    }

    /**
     * Method to get forecast of a cryptocurrency in base of day's gap inserted
     *
     * @param symbol         : symbol to calculate forecast es. BTCBUSD
     * @param interval       : temporal interval of data for the forecast
     * @param intervalDays   : days gap for the prevision range
     * @param toleranceValue : tolerance for select similar value compared to lastValue inserted
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     */
    public double getSymbolForecast(String symbol, Interval interval, int intervalDays,
                                    double toleranceValue) throws IOException {
        ArrayList<Double> historicalValues = new ArrayList<>();
        for (Candlestick candlestick : getCandlesticksList(symbol, interval))
            historicalValues.add(candlestick.getHigh());
        return computeTPTOPIndex(historicalValues, getCurrentAveragePriceValue(symbol), intervalDays, toleranceValue);
    }

}

