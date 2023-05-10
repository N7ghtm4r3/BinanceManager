package com.tecknobit.binancemanager.managers.signedmanagers.fiat;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.RequestWeight;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatOperationsHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatPaymentsHistory;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceFiatManager} class is useful to manage all fiat endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fiat-endpoints">
 * Fiat endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceFiatManager extends BinanceSignedManager {

    /**
     * {@code FIAT_ORDERS_ENDPOINT} is constant for FIAT_ORDERS_ENDPOINT's endpoint
     **/
    public static final String FIAT_ORDERS_ENDPOINT = "/sapi/v1/fiat/orders";

    /**
     * {@code FIAT_PAYMENTS_ENDPOINT} is constant for FIAT_PAYMENTS_ENDPOINT's endpoint
     **/
    public static final String FIAT_PAYMENTS_ENDPOINT = "/sapi/v1/fiat/payments";

    /**
     * Constructor to init a {@link BinanceFiatManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFiatManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFiatManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceFiatManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFiatManager} <br>
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
    public BinanceFiatManager() {
        super();
    }

    /**
     * Request to get the deposit/withdraw history
     *
     * @param type: 0-deposit,1-withdraw
     * @return deposit/withdraw history as {@link FiatOperationsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data">
     * Get Fiat Deposit/Withdraw History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "90000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/orders")
    public FiatOperationsHistory getDepositWithdrawHistory(int type) throws Exception {
        return getDepositWithdrawHistory(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the deposit/withdraw history
     *
     * @param type:   0-deposit,1-withdraw
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return fiat operations list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data">
     * Get Fiat Deposit/Withdraw History (USER_DATA)</a>
     **/
    @RequestWeight(weight = "90000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/orders")
    public <T> T getDepositWithdrawHistory(int type, ReturnFormat format) throws Exception {
        return returnOperationsHistory(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, getTimestampParam()
                + "&transactionType=" + type), format);
    }

    /**
     * Request to get the deposit/withdraw history
     *
     * @param type:        0-deposit,1-withdraw
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "beginTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
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
     * @return deposit/withdraw history as {@link FiatOperationsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data">
     * Get Fiat Deposit/Withdraw History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "90000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/orders")
    public FiatOperationsHistory getDepositWithdrawHistory(int type, Params extraParams) throws Exception {
        return getDepositWithdrawHistory(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the deposit/withdraw history
     *
     * @param type:        0-deposit,1-withdraw
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "beginTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
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
     * @return fiat operations list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data">
     * Get Fiat Deposit/Withdraw History (USER_DATA)</a>
     **/
    @RequestWeight(weight = "90000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/orders")
    public <T> T getDepositWithdrawHistory(int type, Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("transactionType", type);
        extraParams.addParam("timestamp", getServerTime());
        return returnOperationsHistory(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a fiat operations list
     *
     * @param fiatOperationsResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return fiat operations list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOperationsHistory(String fiatOperationsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(fiatOperationsResponse);
            case LIBRARY_OBJECT:
                return (T) new FiatOperationsHistory(new JSONObject(fiatOperationsResponse));
            default:
                return (T) fiatOperationsResponse;
        }
    }

    /**
     * Request to get the payments history
     *
     * @param type: 0-buy,1-sell
     * @return payments history as {@link FiatPaymentsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data">
     * Get Fiat Payments History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/payments")
    public FiatPaymentsHistory getPaymentsHistory(int type) throws Exception {
        return getPaymentsHistory(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the payments history
     *
     * @param type:   0-buy,1-sell
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return fiat payments list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data">
     * Get Fiat Payments History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/payments")
    public <T> T getPaymentsHistory(int type, ReturnFormat format) throws Exception {
        return returnPaymentsHistory(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, getTimestampParam()
                + "&transactionType=" + type), format);
    }

    /**
     * Request to get the payments history
     *
     * @param type:        0-buy,1-sell
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "beginTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
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
     * @return payments history as {@link FiatPaymentsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data">
     * Get Fiat Payments History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/payments")
    public FiatPaymentsHistory getPaymentsHistory(int type, Params extraParams) throws Exception {
        return getPaymentsHistory(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the payments history
     *
     * @param type:        0-buy,1-sell
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "beginTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
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
     * @return fiat payments list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data">
     * Get Fiat Payments History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/fiat/payments")
    public <T> T getPaymentsHistory(int type, Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("transactionType", type);
        extraParams.addParam("timestamp", getServerTime());
        return returnPaymentsHistory(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a fiat payments list
     *
     * @param fiatPaymentsResponse: obtained from Binance's response
     * @param format:               return type formatter -> {@link ReturnFormat}
     * @return fiat payments list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnPaymentsHistory(String fiatPaymentsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(fiatPaymentsResponse);
            case LIBRARY_OBJECT:
                return (T) new FiatPaymentsHistory(new JSONObject(fiatPaymentsResponse));
            default:
                return (T) fiatPaymentsResponse;
        }
    }

}
