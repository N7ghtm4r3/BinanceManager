package com.tecknobit.binancemanager.managers.signedmanagers.rebate;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.rebate.records.SpotRebateHistory;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceRebateManager} class is useful to manage rebate endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rebate-endpoints">
 * Rebate endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceRebateManager extends BinanceSignedManager {

    /**
     * {@code REBATE_HISTORY_ENDPOINT} is constant for REBATE_HISTORY_ENDPOINT's endpoint
     **/
    public static final String REBATE_HISTORY_ENDPOINT = "/sapi/v1/rebate/taxQuery";

    /**
     * Constructor to init a {@link BinanceRebateManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceRebateManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceRebateManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceRebateManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceRebateManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceRebateManager(String baseEndpoint, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceRebateManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceRebateManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceRebateManager} <br>
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
    public BinanceRebateManager() {
        super();
    }

    /**
     * Request to get the spot rebate history records <br>
     * No-any params required
     *
     * @return spot rebate history records as {@link SpotRebateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-spot-rebate-history-records-user_data">
     * Get Spot Rebate History Records (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/rebate/taxQuery")
    public SpotRebateHistory getSpotRebateHistory() throws Exception {
        return getSpotRebateHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the spot rebate history records
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return spot rebate history {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-spot-rebate-history-records-user_data">
     * Get Spot Rebate History Records (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/rebate/taxQuery")
    public <T> T getSpotRebateHistory(ReturnFormat format) throws Exception {
        return returnSpotRebateHistory(sendGetSignedRequest(REBATE_HISTORY_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get the spot rebate history records
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
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return spot rebate history records as {@link SpotRebateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-spot-rebate-history-records-user_data">
     * Get Spot Rebate History Records (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/rebate/taxQuery")
    public SpotRebateHistory getSpotRebateHistory(Params extraParams) throws Exception {
        return getSpotRebateHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the spot rebate history records
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
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return spot rebate history {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-spot-rebate-history-records-user_data">
     * Get Spot Rebate History Records (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/rebate/taxQuery")
    public <T> T getSpotRebateHistory(Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("timestamp", getServerTime());
        return returnSpotRebateHistory(sendGetSignedRequest(REBATE_HISTORY_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a spot rebate history
     *
     * @param spotRebateHistoryResponse: obtained from Binance's response
     * @param format:                    return type formatter -> {@link ReturnFormat}
     * @return spot rebate history {@code "format"} defines
     **/
    @Returner
    private <T> T returnSpotRebateHistory(String spotRebateHistoryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(spotRebateHistoryResponse);
            case LIBRARY_OBJECT:
                return (T) new SpotRebateHistory(new JSONObject(spotRebateHistoryResponse));
            default:
                return (T) spotRebateHistoryResponse;
        }
    }

}
