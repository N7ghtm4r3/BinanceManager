package com.tecknobit.binancemanager.managers.signedmanagers.pay;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.RequestWeight;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.pay.records.PayTradeHistory;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinancePayManager} class is useful to manage pay endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#pay-endpoints">
 * Pay endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinancePayManager extends BinanceSignedManager {

    /**
     * {@code PAY_TRADE_HISTORY_ENDPOINT} is constant for PAY_TRADE_HISTORY_ENDPOINT's endpoint
     **/
    public static final String PAY_TRADE_HISTORY_ENDPOINT = "/sapi/v1/pay/transactions";

    /**
     * Constructor to init a {@link BinancePayManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinancePayManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePayManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinancePayManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePayManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinancePayManager(String baseEndpoint, int timeout, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePayManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinancePayManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePayManager} <br>
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
    public BinancePayManager() {
        super();
    }

    /**
     * Request to get the pay trade history <br>
     * No-any params required
     *
     * @return pay trade history as {@link PayTradeHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * Get Pay Trade History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/pay/transactions")
    public PayTradeHistory getPayTradeHistory() throws Exception {
        return getPayTradeHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the pay trade history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return pay trades list {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * Get Pay Trade History (USER_DATA)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/pay/transactions")
    public <T> T getPayTradeHistory(ReturnFormat format) throws Exception {
        return returnPayTradeHistory(sendGetSignedRequest(PAY_TRADE_HISTORY_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get the pay trade history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 100]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return pay trade history as {@link PayTradeHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * Get Pay Trade History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/pay/transactions")
    public PayTradeHistory getPayTradeHistory(Params extraParams) throws Exception {
        return getPayTradeHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the pay trade history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 100]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return pay trades list {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * Get Pay Trade History (USER_DATA)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/pay/transactions")
    public <T> T getPayTradeHistory(Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("timestamp", getServerTime());
        return returnPayTradeHistory(sendGetSignedRequest(PAY_TRADE_HISTORY_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a pay trades list
     *
     * @param payTradesResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return pay trades list {@code "format"} defines
     **/
    @Returner
    private <T> T returnPayTradeHistory(String payTradesResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(payTradesResponse);
            case LIBRARY_OBJECT:
                return (T) new PayTradeHistory(new JSONObject(payTradesResponse));
            default:
                return (T) payTradesResponse;
        }
    }

}
