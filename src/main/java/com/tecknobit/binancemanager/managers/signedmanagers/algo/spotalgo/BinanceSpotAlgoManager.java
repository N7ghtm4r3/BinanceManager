package com.tecknobit.binancemanager.managers.signedmanagers.algo.spotalgo;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.algo.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;

import java.io.IOException;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.AlgoNewOrderOperation.returnAlgoOrderNewOperation;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.AlgoOrdersList.returnAlgoOrdersList;
import static com.tecknobit.binancemanager.managers.signedmanagers.algo.records.CancelAlgoOrderResult.returnCancelAlgoOrder;

/**
 * The {@code BinanceSpotAlgoManager} class is useful to manage spotalgo endpoints
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
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
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

    public AlgoNewOrderOperation createTwapOrder(String symbol, Side side, double quantity, long duration) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, LIBRARY_OBJECT);
    }

    public <T> T createTwapOrder(String symbol, Side side, double quantity, long duration,
                                 ReturnFormat format) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, null, format);
    }

    public AlgoNewOrderOperation createTwapOrder(String symbol, Side side, double quantity, long duration,
                                                 Params extraParams) throws Exception {
        return createTwapOrder(symbol, side, quantity, duration, extraParams, LIBRARY_OBJECT);
    }

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

    public CancelAlgoOrderResult cancelAlgoOrder(AlgoOrderStructure algoOrder) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), LIBRARY_OBJECT);
    }

    public <T> T cancelAlgoOrder(AlgoOrderStructure algoOrder, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), format);
    }

    public CancelAlgoOrderResult cancelAlgoOrder(long algoId) throws Exception {
        return cancelAlgoOrder(algoId, LIBRARY_OBJECT);
    }

    public <T> T cancelAlgoOrder(long algoId, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoId, -1, format);
    }

    public CancelAlgoOrderResult cancelAlgoOrder(AlgoOrderStructure algoOrder, long recvWindow) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), LIBRARY_OBJECT);
    }

    public <T> T cancelAlgoOrder(AlgoOrderStructure algoOrder, long recvWindow, ReturnFormat format) throws Exception {
        return cancelAlgoOrder(algoOrder.getAlgoId(), recvWindow, format);
    }

    public CancelAlgoOrderResult cancelAlgoOrder(long algoId, long recvWindow) throws Exception {
        return cancelAlgoOrder(algoId, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T cancelAlgoOrder(long algoId, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("algoId", algoId);
        payload.addParam("timestamp", getServerTime());
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return returnCancelAlgoOrder(sendDeleteSignedRequest(ALGO_SPOT_ORDER_ENDPOINT, payload), format);
    }

    public AlgoOrdersList getCurrentAlgoOpenOrders() throws Exception {
        return getCurrentAlgoOpenOrders(LIBRARY_OBJECT);
    }

    public <T> T getCurrentAlgoOpenOrders(ReturnFormat format) throws Exception {
        return getCurrentAlgoOpenOrders(-1, format);
    }

    public AlgoOrdersList getCurrentAlgoOpenOrders(long recvWindow) throws Exception {
        return getCurrentAlgoOpenOrders(recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getCurrentAlgoOpenOrders(long recvWindow, ReturnFormat format) throws Exception {
        String query = getTimestampParam();
        if (recvWindow != -1)
            query += "&recvWindow=" + recvWindow;
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_SPOT_OPEN_ORDERS_ENDPOINT, query), format);
    }

    public AlgoOrdersList getHistoricalAlgoOrders() throws Exception {
        return getHistoricalAlgoOrders(LIBRARY_OBJECT);
    }

    public <T> T getHistoricalAlgoOrders(ReturnFormat format) throws Exception {
        return getHistoricalAlgoOrders(null, format);
    }

    public AlgoOrdersList getHistoricalAlgoOrders(Params extraParams) throws Exception {
        return getHistoricalAlgoOrders(extraParams, LIBRARY_OBJECT);
    }

    public <T> T getHistoricalAlgoOrders(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_SPOT_HISTORICAL_ORDERS_ENDPOINT, extraParams), format);
    }

    public SubAlgoOrdersList getSubOrders() throws Exception {
        return getSubOrders(LIBRARY_OBJECT);
    }

    public <T> T getSubOrders(ReturnFormat format) throws Exception {
        return getSubOrders(null, format);
    }

    public SubAlgoOrdersList getSubOrders(Params extraParams) throws Exception {
        return getSubOrders(extraParams, LIBRARY_OBJECT);
    }

    public <T> T getSubOrders(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        return returnAlgoOrdersList(sendGetSignedRequest(ALGO_SPOT_SUB_ORDERS_ENDPOINT, extraParams), format);
    }

}
