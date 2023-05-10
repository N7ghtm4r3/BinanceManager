package com.tecknobit.binancemanager.managers.signedmanagers.algo.spotalgo;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.RequestWeight;
import com.tecknobit.apimanager.annotations.WrappedRequest;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.algo.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.AlgoNewOrderOperation.returnAlgoOrderNewOperation;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.AlgoOrdersList.returnAlgoOrdersList;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.CancelAlgoOrderResult.returnCancelAlgoOrder;

/**
 * The {@code BinanceSpotAlgoManager} class is useful to manage spot algo endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-algo-endpoints">
 * Spot Algo Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceSpotAlgoManager extends BinanceSignedManager {

    /**
     * {@code ALGO_SPOT_NEW_ORDER_TWAP_ENDPOINT} is constant for ALGO_SPOT_NEW_ORDER_TWAP_ENDPOINT's endpoint
     **/
    public static final String ALGO_SPOT_NEW_ORDER_TWAP_ENDPOINT = "/sapi/v1/algo/spot/newOrderTwap";

    /**
     * {@code ALGO_SPOT_ORDER_ENDPOINT} is constant for ALGO_SPOT_ORDER_ENDPOINT's endpoint
     **/
    public static final String ALGO_SPOT_ORDER_ENDPOINT = "/sapi/v1/algo/spot/order";

    /**
     * {@code ALGO_SPOT_OPEN_ORDERS_ENDPOINT} is constant for ALGO_SPOT_OPEN_ORDERS_ENDPOINT's endpoint
     **/
    public static final String ALGO_SPOT_OPEN_ORDERS_ENDPOINT = "/sapi/v1/algo/spot/openOrders";

    /**
     * {@code ALGO_SPOT_HISTORICAL_ORDERS_ENDPOINT} is constant for ALGO_SPOT_HISTORICAL_ORDERS_ENDPOINT's endpoint
     **/
    public static final String ALGO_SPOT_HISTORICAL_ORDERS_ENDPOINT = "/sapi/v1/algo/spot/historicalOrders";

    /**
     * {@code ALGO_SPOT_SUB_ORDERS_ENDPOINT} is constant for ALGO_SPOT_SUB_ORDERS_ENDPOINT's endpoint
     **/
    public static final String ALGO_SPOT_SUB_ORDERS_ENDPOINT = "/sapi/v1/algo/spot/subOrders";

    /**
     * Constructor to init a {@link BinanceSpotAlgoManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceSpotAlgoManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotAlgoManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceSpotAlgoManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotAlgoManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceSpotAlgoManager(String baseEndpoint, int timeout, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotAlgoManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceSpotAlgoManager(String baseEndpoint, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotAlgoManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceSignedManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceSignedManager firstManager = new BinanceSignedManager("apiKey", "apiSecret");
     *        //You don't need to insert all credentials to make manager work
     *        BinanceSignedManager secondManager = new BinanceSignedManager(); //same credentials used
     *     }
     * </pre>
     **/
    public BinanceSpotAlgoManager() {
        super();
    }

    /**
     * Request to place a new spot TWAP order with Algo service
     *
     * @param symbol:   trading symbol
     * @param side:     trading side
     * @param quantity: quantity of base asset; The notional (quantity * last price(base asset)) must be more than the
     *                  equivalent of 1,000 USDT and less than the equivalent of 100,000 USDT
     * @param duration: duration for TWAP orders in seconds. [300, 86400]
     * @return algo new order operation as {@link AlgoNewOrderOperation} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade-2">
     * Time-Weighted Average Price (Twap) New Order (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/algo/spot/newOrderTwap")
    public AlgoNewOrderOperation createTwapOrder(String symbol, Side side, double quantity, long duration) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, LIBRARY_OBJECT);
    }

    /**
     * Request to place a new spot TWAP order with Algo service
     *
     * @param symbol:   trading symbol
     * @param side:     trading side
     * @param quantity: quantity of base asset; The notional (quantity * last price(base asset)) must be more than the
     *                  equivalent of 1,000 USDT and less than the equivalent of 100,000 USDT
     * @param duration: duration for TWAP orders in seconds. [300, 86400]
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return algo order operation as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade-2">
     * Time-Weighted Average Price (Twap) New Order (TRADE)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/algo/spot/newOrderTwap")
    public <T> T createTwapOrder(String symbol, Side side, double quantity, long duration,
                                 ReturnFormat format) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, null, format);
    }

    /**
     * Request to place a new spot TWAP order with Algo service
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset; The notional (quantity * last price(base asset)) must be more than the
     *                     equivalent of 1,000 USDT and less than the equivalent of 100,000 USDT
     * @param duration:    duration for TWAP orders in seconds. [300, 86400]
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "clientAlgoId"} -> a unique id among Algo orders (length should be 32
     *                                characters)， If it is not sent, we will give default value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "limitPrice"} -> limit price of the order; If it is not sent, will place
     *                                order by market price by default - [DECIMAL]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return algo new order operation as {@link AlgoNewOrderOperation} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade-2">
     * Time-Weighted Average Price (Twap) New Order (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/algo/spot/newOrderTwap")
    public AlgoNewOrderOperation createTwapOrder(String symbol, Side side, double quantity, long duration,
                                                 Params extraParams) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to place a new spot TWAP order with Algo service
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset; The notional (quantity * last price(base asset)) must be more than the
     *                     equivalent of 1,000 USDT and less than the equivalent of 100,000 USDT
     * @param duration:    duration for TWAP orders in seconds. [300, 86400]
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "clientAlgoId"} -> a unique id among Algo orders (length should be 32
     *                                characters)， If it is not sent, we will give default value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "limitPrice"} -> limit price of the order; If it is not sent, will place
     *                                order by market price by default - [DECIMAL]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return algo order operation as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade-2">
     * Time-Weighted Average Price (Twap) New Order (TRADE)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/algo/spot/newOrderTwap")
    public <T> T createTwapOrder(String symbol, Side side, double quantity, long duration, Params extraParams,
                                 ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("quantity", quantity);
        extraParams.addParam("duration", duration);
        return returnAlgoOrderNewOperation(sendPostSignedRequest(ALGO_SPOT_NEW_ORDER_TWAP_ENDPOINT, extraParams), format);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoOrder: the algo order to cancel
     * @return cancel algo order result as {@link CancelAlgoOrderResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public CancelAlgoOrderResult cancelAlgoOrder(AlgoOrderStructure algoOrder) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoOrder: the algo order to cancel
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return cancel algo order as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public <T> T cancelAlgoOrder(AlgoOrderStructure algoOrder, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), format);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoId: the algo identifier of the order to cancel
     * @return cancel algo order result as {@link CancelAlgoOrderResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public CancelAlgoOrderResult cancelAlgoOrder(long algoId) throws Exception {
        return cancelAlgoOrder(algoId, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoId: the algo identifier of the order to cancel
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cancel algo order as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public <T> T cancelAlgoOrder(long algoId, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoId, -1, format);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoOrder:  the algo order to cancel
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return cancel algo order result as {@link CancelAlgoOrderResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public CancelAlgoOrderResult cancelAlgoOrder(AlgoOrderStructure algoOrder, long recvWindow) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoOrder:  the algo order to cancel
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return cancel algo order as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public <T> T cancelAlgoOrder(AlgoOrderStructure algoOrder, long recvWindow, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), recvWindow, format);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoId:     the algo identifier of the order to cancel
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return cancel algo order result as {@link CancelAlgoOrderResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public CancelAlgoOrderResult cancelAlgoOrder(long algoId, long recvWindow) throws Exception {
        return cancelAlgoOrder(algoId, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an open TWAP order
     *
     * @param algoId:     the algo identifier of the order to cancel
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return cancel algo order as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
     * Cancel Algo Order (TRADE)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/spot/order")
    public <T> T cancelAlgoOrder(long algoId, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("algoId", algoId);
        payload.addParam("timestamp", getServerTime());
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return returnCancelAlgoOrder(sendDeleteSignedRequest(ALGO_SPOT_ORDER_ENDPOINT, payload), format);
    }

    /**
     * Request to get all open SPOT TWAP orders <br>
     * No-any params required
     *
     * @return algo orders list as {@link AlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data-2">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/openOrders")
    public AlgoOrdersList getCurrentAlgoOpenOrders() throws Exception {
        return getCurrentAlgoOpenOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get all open SPOT TWAP orders
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data-2">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/openOrders")
    public <T> T getCurrentAlgoOpenOrders(ReturnFormat format) throws Exception {
        return getCurrentAlgoOpenOrders(-1, format);
    }

    /**
     * Request to get all open SPOT TWAP orders
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return algo orders list as {@link AlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data-2">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/openOrders")
    public AlgoOrdersList getCurrentAlgoOpenOrders(long recvWindow) throws Exception {
        return getCurrentAlgoOpenOrders(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get all open SPOT TWAP orders
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data-2">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/openOrders")
    public <T> T getCurrentAlgoOpenOrders(long recvWindow, ReturnFormat format) throws Exception {
        String query = getTimestampParam();
        if (recvWindow != -1)
            query += "&recvWindow=" + recvWindow;
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_SPOT_OPEN_ORDERS_ENDPOINT, query), format);
    }

    /**
     * Request to get all SPOT TWAP orders <br>
     * No-any params required
     *
     * @return algo orders list as {@link AlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data-2">
     * Query Historical Algo Orders (USER_DATA))</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/historicalOrders")
    public AlgoOrdersList getHistoricalAlgoOrders() throws Exception {
        return getHistoricalAlgoOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get all SPOT TWAP orders
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data-2">
     * Query Historical Algo Orders (USER_DATA))</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/historicalOrders")
    public <T> T getHistoricalAlgoOrders(ReturnFormat format) throws Exception {
        return getHistoricalAlgoOrders(null, format);
    }

    /**
     * Request to get all SPOT TWAP orders
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "symbol"} -> trading symbol - [STRING, default BOTH]
     *                           </li>
     *                           <li>
     *                                {@code "side"} -> side of the orders, constants available {@link Side} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> the page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> size of the results per page, min 1 and max 100
     *                                - [INT, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     * @return algo orders list as {@link AlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data-2">
     * Query Historical Algo Orders (USER_DATA))</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/historicalOrders")
    public AlgoOrdersList getHistoricalAlgoOrders(Params extraParams) throws Exception {
        return getHistoricalAlgoOrders(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get all SPOT TWAP orders
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "symbol"} -> trading symbol - [STRING, default BOTH]
     *                           </li>
     *                           <li>
     *                                {@code "side"} -> side of the orders, constants available {@link Side} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> the page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> size of the results per page, min 1 and max 100
     *                                - [INT, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data-2">
     * Query Historical Algo Orders (USER_DATA))</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/historicalOrders")
    public <T> T getHistoricalAlgoOrders(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_SPOT_HISTORICAL_ORDERS_ENDPOINT, extraParams), format);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoOrder: the algo order from fetch the list
     * @return sub algo orders list as {@link SubAlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public SubAlgoOrdersList getSubOrders(AlgoOrderStructure algoOrder) throws Exception {
        return getSubOrders(algoOrder.getAlgoId(), LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoOrder: the algo order from fetch the list
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public <T> T getSubOrders(AlgoOrderStructure algoOrder, ReturnFormat format) throws Exception {
        return getSubOrders(algoOrder.getAlgoId(), format);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoId: the algo identifier from fetch the list
     * @return sub algo orders list as {@link SubAlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public SubAlgoOrdersList getSubOrders(long algoId) throws Exception {
        return getSubOrders(algoId, LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoId: the algo identifier from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public <T> T getSubOrders(long algoId, ReturnFormat format) throws Exception {
        return getSubOrders(algoId, null, format);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoOrder:   the algo order from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "page"} -> the page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> size of the results per page, min 1 and max 100
     *                                - [INT, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return sub algo orders list as {@link SubAlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public SubAlgoOrdersList getSubOrders(AlgoOrderStructure algoOrder, Params extraParams) throws Exception {
        return getSubOrders(algoOrder.getAlgoId(), extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoOrder:   the algo order from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "page"} -> the page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> size of the results per page, min 1 and max 100
     *                                - [INT, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public <T> T getSubOrders(AlgoOrderStructure algoOrder, Params extraParams, ReturnFormat format) throws Exception {
        return getSubOrders(algoOrder.getAlgoId(), extraParams, format);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoId:      the algo identifier from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "page"} -> the page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> size of the results per page, min 1 and max 100
     *                                - [INT, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return sub algo orders list as {@link SubAlgoOrdersList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public SubAlgoOrdersList getSubOrders(long algoId, Params extraParams) throws Exception {
        return getSubOrders(algoId, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoId:      the algo identifier from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "page"} -> the page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> size of the results per page, min 1 and max 100
     *                                - [INT, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/algo/spot/subOrders")
    public <T> T getSubOrders(long algoId, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_SPOT_SUB_ORDERS_ENDPOINT, extraParams), format);
    }

}
