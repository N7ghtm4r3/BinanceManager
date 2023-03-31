package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records.AlgoOrdersList;
import org.json.JSONObject;

import java.io.IOException;

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

}
