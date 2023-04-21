package com.tecknobit.binancemanager.managers.signedmanagers.algo.futuresalgo;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.algo.records.*;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;

/**
 * The {@code BinanceFuturesAlgoManager} class is useful to manage futures algo endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-algo-endpoints">
 * Futures Algo Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceFuturesAlgoManager extends BinanceSignedManager {

    /**
     * {@code ALGO_FUTURES_NEW_ORDER_VP_ENDPOINT} is constant for ALGO_FUTURES_NEW_ORDER_VP_ENDPOINT's endpoint
     **/
    public static final String ALGO_FUTURES_NEW_ORDER_VP_ENDPOINT = "/sapi/v1/algo/futures/newOrderVp";

    /**
     * {@code ALGO_FUTURES_NEW_ORDER_TWAP_ENDPOINT} is constant for ALGO_FUTURES_NEW_ORDER_TWAP_ENDPOINT's endpoint
     **/
    public static final String ALGO_FUTURES_NEW_ORDER_TWAP_ENDPOINT = "/sapi/v1/algo/futures/newOrderTwap";

    /**
     * {@code ALGO_FUTURES_ORDER_ENDPOINT} is constant for ALGO_FUTURES_ORDER_ENDPOINT's endpoint
     **/
    public static final String ALGO_FUTURES_ORDER_ENDPOINT = "/sapi/v1/algo/futures/order";

    /**
     * {@code ALGO_FUTURES_OPEN_ORDERS_ENDPOINT} is constant for ALGO_FUTURES_OPEN_ORDERS_ENDPOINT's endpoint
     **/
    public static final String ALGO_FUTURES_OPEN_ORDERS_ENDPOINT = "/sapi/v1/algo/futures/openOrders";

    /**
     * {@code ALGO_FUTURES_HISTORICAL_ORDERS_ENDPOINT} is constant for ALGO_FUTURES_HISTORICAL_ORDERS_ENDPOINT's endpoint
     **/
    public static final String ALGO_FUTURES_HISTORICAL_ORDERS_ENDPOINT = "/sapi/v1/algo/futures/historicalOrders";

    /**
     * {@code ALGO_FUTURES_SUBORDERS_ENDPOINT} is constant for ALGO_FUTURES_SUBORDERS_ENDPOINT's endpoint
     **/
    public static final String ALGO_FUTURES_SUBORDERS_ENDPOINT = "/sapi/v1/algo/futures/subOrders";

    /**
     * Constructor to init a {@link BinanceFuturesAlgoManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFuturesAlgoManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesAlgoManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFuturesAlgoManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesAlgoManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFuturesAlgoManager(String baseEndpoint, int timeout, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesAlgoManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFuturesAlgoManager(String baseEndpoint, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesAlgoManager} <br>
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
    public BinanceFuturesAlgoManager() {
        super();
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:   trading symbol
     * @param side:     trading side
     * @param quantity: quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                  equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param urgency:  represent the relative speed of the current execution
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#volume-participation-vp-new-order-trade">
     * Volume Participation(VP) New Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderVp")
    public AlgoNewOrderOperation createVPOrder(String symbol, Side side, double quantity, AlgoOrdersList.AlgoOrder.Urgency urgency) throws Exception {
        return createVPOrder(symbol, side, quantity, urgency, LIBRARY_OBJECT);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:   trading symbol
     * @param side:     trading side
     * @param quantity: quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                  equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param urgency:  represent the relative speed of the current execution
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return algo orders operation as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#volume-participation-vp-new-order-trade">
     * Volume Participation(VP) New Order (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderVp")
    public <T> T createVPOrder(String symbol, Side side, double quantity, AlgoOrdersList.AlgoOrder.Urgency urgency,
                               ReturnFormat format) throws Exception {
        return createVPOrder(symbol, side, quantity, urgency, null, format);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                     equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param urgency:     represent the relative speed of the current execution
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionSide"} -> position side for the order, constants available
     *                                {@link AlgoOrdersList.AlgoOrder.PositionSide} - [STRING, default BOTH]
     *                           </li>
     *                           <li>
     *                                {@code "clientAlgoId"} -> a unique id among Algo orders (length should be 32
     *                                characters)， If it is not sent, we will give default value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "reduceOnly"} -> cannot be sent when you open a position
     *                                - [BOOLEAN, default false]
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#volume-participation-vp-new-order-trade">
     * Volume Participation(VP) New Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderVp")
    public AlgoNewOrderOperation createVPOrder(String symbol, Side side, double quantity, AlgoOrdersList.AlgoOrder.Urgency urgency,
                                               Params extraParams) throws Exception {
        return createVPOrder(symbol, side, quantity, urgency, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                     equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param urgency:     represent the relative speed of the current execution
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionSide"} -> position side for the order, constants available
     *                                {@link AlgoOrdersList.AlgoOrder.PositionSide} - [STRING, default BOTH]
     *                           </li>
     *                           <li>
     *                                {@code "clientAlgoId"} -> a unique id among Algo orders (length should be 32
     *                                characters)， If it is not sent, we will give default value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "reduceOnly"} -> cannot be sent when you open a position
     *                                - [BOOLEAN, default false]
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
     * @return algo orders operation as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#volume-participation-vp-new-order-trade">
     * Volume Participation(VP) New Order (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderVp")
    public <T> T createVPOrder(String symbol, Side side, double quantity, AlgoOrdersList.AlgoOrder.Urgency urgency, Params extraParams,
                               ReturnFormat format) throws Exception {
        extraParams = createOrderPayload(symbol, side, quantity, extraParams);
        extraParams.addParam("urgency", urgency);
        return returnAlgoOrderNewOperation(sendPostSignedRequest(ALGO_FUTURES_NEW_ORDER_VP_ENDPOINT, extraParams),
                format);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:   trading symbol
     * @param side:     trading side
     * @param quantity: quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                  equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param duration: duration for TWAP orders in seconds. [300, 86400];Less than 5min => defaults to 5 min;
     *                  Greater than 24h => defaults to 24h
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade">
     * Time-Weighted Average Price(Twap) New Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderTwap")
    public AlgoNewOrderOperation createTwapOrder(String symbol, Side side, double quantity, long duration) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, LIBRARY_OBJECT);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:   trading symbol
     * @param side:     trading side
     * @param quantity: quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                  equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param duration: duration for TWAP orders in seconds. [300, 86400];Less than 5min => defaults to 5 min;
     *                  Greater than 24h => defaults to 24h
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return algo orders operation as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade">
     * Time-Weighted Average Price(Twap) New Order (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderTwap")
    public <T> T createTwapOrder(String symbol, Side side, double quantity, long duration,
                                 ReturnFormat format) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, null, format);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                     equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param duration:    duration for TWAP orders in seconds. [300, 86400];Less than 5min => defaults to 5 min;
     *                     Greater than 24h => defaults to 24h
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionSide"} -> position side for the order, constants available
     *                                {@link AlgoOrdersList.AlgoOrder.PositionSide} - [STRING, default BOTH]
     *                           </li>
     *                           <li>
     *                                {@code "clientAlgoId"} -> a unique id among Algo orders (length should be 32
     *                                characters)， If it is not sent, we will give default value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "reduceOnly"} -> cannot be sent when you open a position
     *                                - [BOOLEAN, default false]
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade">
     * Time-Weighted Average Price(Twap) New Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderTwap")
    public AlgoNewOrderOperation createTwapOrder(String symbol, Side side, double quantity, long duration,
                                                 Params extraParams) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send in a VP new order. Only support on USDⓈ-M Contracts
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset; The notional (quantity * mark price(base asset)) must be more than the
     *                     equivalent of 10,000 USDT and less than the equivalent of 1,000,000 USDT
     * @param duration:    duration for TWAP orders in seconds. [300, 86400];Less than 5min => defaults to 5 min;
     *                     Greater than 24h => defaults to 24h
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionSide"} -> position side for the order, constants available
     *                                {@link AlgoOrdersList.AlgoOrder.PositionSide} - [STRING, default BOTH]
     *                           </li>
     *                           <li>
     *                                {@code "clientAlgoId"} -> a unique id among Algo orders (length should be 32
     *                                characters)， If it is not sent, we will give default value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "reduceOnly"} -> cannot be sent when you open a position
     *                                - [BOOLEAN, default false]
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
     * @return algo orders operation as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade">
     * Time-Weighted Average Price(Twap) New Order (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/algo/futures/newOrderTwap")
    public <T> T createTwapOrder(String symbol, Side side, double quantity, long duration, Params extraParams,
                                 ReturnFormat format) throws Exception {
        extraParams = createOrderPayload(symbol, side, quantity, extraParams);
        extraParams.addParam("duration", duration);
        return returnAlgoOrderNewOperation(sendPostSignedRequest(ALGO_FUTURES_NEW_ORDER_TWAP_ENDPOINT, extraParams),
                format);
    }

    /**
     * Request to create an order payload
     *
     * @param symbol:      trading symbol
     * @param side:        trading side
     * @param quantity:    quantity of base asset
     * @param extraParams: extra params for the request
     * @return order payload as {@link Params}
     **/
    private Params createOrderPayload(String symbol, Side side, double quantity, Params extraParams) {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("quantity", quantity);
        return extraParams;
    }

    /**
     * Method to create an algo order operation
     *
     * @param algoOrderResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return algo orders operation as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAlgoOrderNewOperation(String algoOrderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(algoOrderResponse);
            case LIBRARY_OBJECT:
                return (T) new AlgoNewOrderOperation(new JSONObject(algoOrderResponse));
            default:
                return (T) algoOrderResponse;
        }
    }

    /**
     * Request to cancel an active order
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public CancelAlgoOrderResult cancelAlgoOrder(AlgoOrderStructure algoOrder) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an active order
     *
     * @param algoOrder: the algo order to cancel
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return cancel algo order result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public <T> T cancelAlgoOrder(AlgoOrderStructure algoOrder, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), format);
    }

    /**
     * Request to cancel an active order
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public CancelAlgoOrderResult cancelAlgoOrder(long algoId) throws Exception {
        return cancelAlgoOrder(algoId, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an active order
     *
     * @param algoId: the algo identifier of the order to cancel
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cancel algo order result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public <T> T cancelAlgoOrder(long algoId, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoId, -1, format);
    }

    /**
     * Request to cancel an active order
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public CancelAlgoOrderResult cancelAlgoOrder(AlgoOrderStructure algoOrder, long recvWindow) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an active order
     *
     * @param algoOrder:  the algo order to cancel
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return cancel algo order result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public <T> T cancelAlgoOrder(AlgoOrderStructure algoOrder, long recvWindow, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), recvWindow, format);
    }

    /**
     * Request to cancel an active order
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public CancelAlgoOrderResult cancelAlgoOrder(long algoId, long recvWindow) throws Exception {
        return cancelAlgoOrder(algoId, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an active order
     *
     * @param algoId:     the algo identifier of the order to cancel
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return cancel algo order result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
     * Cancel Algo Order (TRADE)</a>
     **/
    @Returner
    @RequestPath(method = DELETE, path = "/sapi/v1/algo/futures/order")
    public <T> T cancelAlgoOrder(long algoId, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("algoId", algoId);
        payload.addParam("timestamp", getServerTime());
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String cancelResponse = sendDeleteSignedRequest(ALGO_FUTURES_ORDER_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(cancelResponse);
            case LIBRARY_OBJECT:
                return (T) new CancelAlgoOrderResult(new JSONObject(cancelResponse));
            default:
                return (T) cancelResponse;
        }
    }

    /**
     * Request to get the current algo open orders <br>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/openOrders")
    public AlgoOrdersList getCurrentAlgoOpenOrders() throws Exception {
        return getCurrentAlgoOpenOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get the current algo open orders
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/openOrders")
    public <T> T getCurrentAlgoOpenOrders(ReturnFormat format) throws Exception {
        return getCurrentAlgoOpenOrders(-1, format);
    }

    /**
     * Request to get the current algo open orders
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/openOrders")
    public AlgoOrdersList getCurrentAlgoOpenOrders(long recvWindow) throws Exception {
        return getCurrentAlgoOpenOrders(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the current algo open orders
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data">
     * Query Current Algo Open Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/openOrders")
    public <T> T getCurrentAlgoOpenOrders(long recvWindow, ReturnFormat format) throws Exception {
        String query = getTimestampParam();
        if (recvWindow != -1)
            query += "&recvWindow=" + recvWindow;
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_FUTURES_OPEN_ORDERS_ENDPOINT, query), format);
    }

    /**
     * Request to get the historical algo orders <br>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data">
     * Query Historical Algo Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/historicalOrders")
    public AlgoOrdersList getHistoricalAlgoOrders() throws Exception {
        return getHistoricalAlgoOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get the historical algo orders
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data">
     * Query Historical Algo Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/historicalOrders")
    public <T> T getHistoricalAlgoOrders(ReturnFormat format) throws Exception {
        return getHistoricalAlgoOrders(null, format);
    }

    /**
     * Request to get the historical algo orders
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
     *                     </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data">
     * Query Historical Algo Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/historicalOrders")
    public AlgoOrdersList getHistoricalAlgoOrders(Params extraParams) throws Exception {
        return getHistoricalAlgoOrders(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the historical algo orders
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data">
     * Query Historical Algo Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/historicalOrders")
    public <T> T getHistoricalAlgoOrders(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_FUTURES_HISTORICAL_ORDERS_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create an algo orders list
     *
     * @param algoOrdersResponse: obtained from Binance's response
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return algo orders list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAlgoOrdersList(String algoOrdersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(algoOrdersResponse);
            case LIBRARY_OBJECT:
                return (T) new AlgoOrdersList(new JSONObject(algoOrdersResponse));
            default:
                return (T) algoOrdersResponse;
        }
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algo: the algo order from fetch the list
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public SubAlgoOrdersList getSubOrders(AlgoOrderStructure algo) throws Exception {
        return getSubOrders(algo.getAlgoId(), LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algo:   the algo order from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return sub algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public <T> T getSubOrders(AlgoOrderStructure algo, ReturnFormat format) throws Exception {
        return getSubOrders(algo.getAlgoId(), format);
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public SubAlgoOrdersList getSubOrders(long algoId) throws Exception {
        return getSubOrders(algoId, LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algoId: the algo identifier from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return sub algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public <T> T getSubOrders(long algoId, ReturnFormat format) throws Exception {
        return getSubOrders(algoId, null, format);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algo:        the algo order from fetch the list
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public SubAlgoOrdersList getSubOrders(AlgoOrderStructure algo, Params extraParams) throws Exception {
        return getSubOrders(algo.getAlgoId(), extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get respective sub orders for a specified {@code "algoId"}
     *
     * @param algo:        the algo order from fetch the list
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
     * @return sub algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public <T> T getSubOrders(AlgoOrderStructure algo, Params extraParams, ReturnFormat format) throws Exception {
        return getSubOrders(algo.getAlgoId(), extraParams, LIBRARY_OBJECT);
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
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
     * @return sub algo orders list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
     * Query Sub Orders (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/algo/futures/subOrder")
    public <T> T getSubOrders(long algoId, Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("algoId", algoId);
        extraParams.addParam("timestamp", getServerTime());
        String algoOrdersResponse = sendGetSignedRequest(ALGO_FUTURES_SUBORDERS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(algoOrdersResponse);
            case LIBRARY_OBJECT:
                return (T) new SubAlgoOrdersList(new JSONObject(algoOrdersResponse));
            default:
                return (T) algoOrdersResponse;
        }
    }

}
