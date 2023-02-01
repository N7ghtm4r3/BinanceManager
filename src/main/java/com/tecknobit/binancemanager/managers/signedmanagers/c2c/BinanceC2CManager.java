package com.tecknobit.binancemanager.managers.signedmanagers.c2c;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.c2c.records.C2CTradeHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.binancemanager.constants.EndpointsList.C2C_TRADES_HISTORY_ENDPOINT;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceC2CManager} class is useful to manage C2C endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#c2c-endpoints">
 * C2C endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceC2CManager extends BinanceSignedManager {

    /**
     * Constructor to init a {@link BinanceC2CManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceC2CManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceC2CManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceC2CManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceC2CManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceC2CManager(String baseEndpoint, int timeout, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceC2CManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceC2CManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceC2CManager} <br>
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
    public BinanceC2CManager() {
        super();
    }

    /**
     * Request to get the C2C Trade History
     *
     * @param tradeType: BUY, SELL
     * @return C2C Trade History as {@link C2CTradeHistory} custom object
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
     * Get C2C Trade History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/c2c/orderMatch/listUserOrderHistory")
    public C2CTradeHistory getC2CTradeHistory(Side tradeType) throws Exception {
        return getC2CTradeHistory(tradeType, LIBRARY_OBJECT);
    }

    /**
     * Request to get the C2C Trade History
     *
     * @param tradeType: BUY, SELL
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return C2C trades list {@code "format"} defines
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
     * Get C2C Trade History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/c2c/orderMatch/listUserOrderHistory")
    public <T> T getC2CTradeHistory(Side tradeType, ReturnFormat format) throws Exception {
        return returnC2CTradeHistory(sendGetSignedRequest(C2C_TRADES_HISTORY_ENDPOINT, getTimestampParam() + "&tradeType="
                + tradeType), format);
    }

    /**
     * Request to get the C2C Trade History
     *
     * @param tradeType:   BUY, SELL
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTimestamp"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTimestamp"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "rows"} -> size of the list to fetch, max 500 - [INT, default 100]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return C2C Trade History as {@link C2CTradeHistory} custom object
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
     * Get C2C Trade History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/c2c/orderMatch/listUserOrderHistory")
    public C2CTradeHistory getC2CTradeHistory(Side tradeType, Params extraParams) throws Exception {
        return getC2CTradeHistory(tradeType, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the C2C Trade History
     *
     * @param tradeType:   BUY, SELL
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTimestamp"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTimestamp"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "rows"} -> size of the list to fetch, max 500 - [INT, default 100]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return C2C trades list {@code "format"} defines
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
     * Get C2C Trade History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/c2c/orderMatch/listUserOrderHistory")
    public <T> T getC2CTradeHistory(Side tradeType, Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("timestamp", getServerTime());
        extraParams.addParam("traderType", tradeType);
        return returnC2CTradeHistory(sendGetSignedRequest(C2C_TRADES_HISTORY_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a C2C trades list
     *
     * @param C2CTradesResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return C2C trades list {@code "format"} defines
     **/
    @Returner
    private <T> T returnC2CTradeHistory(String C2CTradesResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(C2CTradesResponse);
            case LIBRARY_OBJECT:
                return (T) new C2CTradeHistory(new JSONObject(C2CTradesResponse));
            default:
                return (T) C2CTradesResponse;
        }
    }

}
