package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.market.records.stats.Candlestick.Interval;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.BinanceWebsocketManager;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.BLVT;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.BLVTStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.RedeemedBLVT;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.info.BLVTInfo;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.info.BLVTLimitInfo;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation.BLVTRedemption;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation.BLVTSubscription;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.websocket.WbsBLVTCandlestick;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.websocket.WbsBLVTInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType.kline;
import static com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType.nav;

/**
 * The {@code BinanceBLVTManager} class is useful to manage BLVT endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#blvt-endpoints">
 * Get BLVT Info (MARKET_DATA)</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see BinanceWebsocketManager
 **/
public class BinanceBLVTManager extends BinanceWebsocketManager {

    /**
     * {@code BLVT_TOKEN_INFO_ENDPOINT} is constant for BLVT_TOKEN_INFO_ENDPOINT's endpoint
     **/
    public static final String BLVT_TOKEN_INFO_ENDPOINT = "/sapi/v1/blvt/tokenInfo";

    /**
     * {@code BLVT_SUBSCRIBE_ENDPOINT} is constant for BLVT_SUBSCRIBE_ENDPOINT's endpoint
     **/
    public static final String BLVT_SUBSCRIBE_ENDPOINT = "/sapi/v1/blvt/subscribe";

    /**
     * {@code BLVT_SUBSCRIBE_RECORD_ENDPOINT} is constant for BLVT_SUBSCRIBE_RECORD_ENDPOINT's endpoint
     **/
    public static final String BLVT_SUBSCRIBE_RECORD_ENDPOINT = BLVT_SUBSCRIBE_ENDPOINT + "/record";

    /**
     * {@code BLVT_REDEEM_ENDPOINT} is constant for BLVT_REDEEM_ENDPOINT's endpoint
     **/
    public static final String BLVT_REDEEM_ENDPOINT = "/sapi/v1/blvt/redeem";

    /**
     * {@code BLVT_REDEEM_RECORD_ENDPOINT} is constant for BLVT_REDEEM_RECORD_ENDPOINT's endpoint
     **/
    public static final String BLVT_REDEEM_RECORD_ENDPOINT = BLVT_REDEEM_ENDPOINT + "/record";

    /**
     * {@code BLVT_USER_LIMIT_ENDPOINT} is constant for BLVT_USER_LIMIT_ENDPOINT's endpoint
     **/
    public static final String BLVT_USER_LIMIT_ENDPOINT = "/sapi/v1/blvt/userLimit";

    /**
     * {@code BLVT_WEBSOCKET_STREAM_ENDPOINT} is constant for BLVT_WEBSOCKET_STREAM_ENDPOINT's endpoint
     **/
    public static final String BLVT_WEBSOCKET_STREAM_ENDPOINT = "wss://nbstream.binance.com/lvt-p";

    /**
     * Constructor to init a {@link BinanceBLVTManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceBLVTManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBLVTManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceBLVTManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBLVTManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceBLVTManager(String baseEndpoint, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBLVTManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceBLVTManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBLVTManager} <br>
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
    public BinanceBLVTManager() {
        super();
    }

    /**
     * Request to get the BLVT info <br>
     * No-any params required
     *
     * @return the BLVT info as {@link ArrayList} of {@link BLVTInfo} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-info-market_data">
     * Get BLVT Info (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/tokenInfo")
    public ArrayList<BLVTInfo> getBLVTInfo() throws Exception {
        return getBLVTInfo(LIBRARY_OBJECT);
    }

    /**
     * Request to get the BLVT info
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT info as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-info-market_data">
     * Get BLVT Info (MARKET_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/blvt/tokenInfo")
    public <T> T getBLVTInfo(ReturnFormat format) throws Exception {
        return getBLVTInfo(null, format);
    }

    /**
     * Request to get the BLVT info
     *
     * @param tokenName: the token from fetch the info
     * @return the BLVT info as {@link ArrayList} of {@link BLVTInfo} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-info-market_data">
     * Get BLVT Info (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/tokenInfo")
    public ArrayList<BLVTInfo> getBLVTInfo(String tokenName) throws Exception {
        return getBLVTInfo(tokenName, LIBRARY_OBJECT);
    }

    /**
     * Request to get the BLVT info
     *
     * @param tokenName: the token from fetch the info
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return the BLVT info as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-info-market_data">
     * Get BLVT Info (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/blvt/tokenInfo")
    public <T> T getBLVTInfo(String tokenName, ReturnFormat format) throws Exception {
        Params query = null;
        if (tokenName != null) {
            query = new Params();
            query.addParam("tokenName", tokenName);
        }
        String infoResponse = sendGetRequest(BLVT_TOKEN_INFO_ENDPOINT, query, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONArray(infoResponse);
            case LIBRARY_OBJECT:
                ArrayList<BLVTInfo> blvtInfo = new ArrayList<>();
                JSONArray jInfo = new JSONArray(infoResponse);
                for (int j = 0; j < jInfo.length(); j++)
                    blvtInfo.add(new BLVTInfo(jInfo.getJSONObject(j)));
                return (T) blvtInfo;
            default:
                return (T) infoResponse;
        }
    }

    /**
     * Request to subscribe BLVT
     *
     * @param token: token to subscribe
     * @param cost:  spot balance
     * @return the BLVT as {@link BLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public BLVT subscribeBLVT(BLVTStructure token, double cost) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param token:  token to subscribe
     * @param cost:   spot balance
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public <T> T subscribeBLVT(BLVTStructure token, double cost, ReturnFormat format) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, format);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param tokenName: name of the token to subscribe
     * @param cost:      spot balance
     * @return the BLVT as {@link BLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public BLVT subscribeBLVT(String tokenName, double cost) throws Exception {
        return subscribeBLVT(tokenName, cost, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param tokenName: name of the token to subscribe
     * @param cost:      spot balance
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public <T> T subscribeBLVT(String tokenName, double cost, ReturnFormat format) throws Exception {
        return subscribeBLVT(tokenName, cost, -1, format);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param token:      token to subscribe
     * @param cost:       spot balance
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the BLVT as {@link BLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public BLVT subscribeBLVT(BLVTStructure token, double cost, long recvWindow) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param token:      token to subscribe
     * @param cost:       spot balance
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public <T> T subscribeBLVT(BLVTStructure token, double cost, long recvWindow, ReturnFormat format) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, recvWindow, format);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param tokenName:  name of the token to subscribe
     * @param cost:       spot balance
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the BLVT as {@link BLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public BLVT subscribeBLVT(String tokenName, double cost, long recvWindow) throws Exception {
        return subscribeBLVT(tokenName, cost, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe BLVT
     *
     * @param tokenName:  name of the token to subscribe
     * @param cost:       spot balance
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
     * Subscribe BLVT (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/blvt/subscribe")
    public <T> T subscribeBLVT(String tokenName, double cost, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("tokenName", tokenName);
        payload.addParam("cost", cost);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String subscriptionResponse = sendPostSignedRequest(BLVT_SUBSCRIBE_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(subscriptionResponse);
            case LIBRARY_OBJECT:
                return (T) new BLVT(new JSONObject(subscriptionResponse));
            default:
                return (T) subscriptionResponse;
        }
    }

    /**
     * Request to get the subscription record<br>
     * No-any params required
     *
     * @return the BLVT subscriptions as {@link ArrayList} of {@link BLVTSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
     * Query Subscription Record (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/subscribe/record")
    public ArrayList<BLVTSubscription> getBLVTSubscriptions() throws Exception {
        return getBLVTSubscriptions(LIBRARY_OBJECT);
    }

    /**
     * Request to get the subscription record
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT subscriptions as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
     * Query Subscription Record (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/blvt/subscribe/record")
    public <T> T getBLVTSubscriptions(ReturnFormat format) throws Exception {
        return getBLVTSubscriptions(null, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subscription record
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tokenName"} -> the token name from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "id"} -> the id from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return the BLVT subscriptions as {@link ArrayList} of {@link BLVTSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
     * Query Subscription Record (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/subscribe/record")
    public ArrayList<BLVTSubscription> getBLVTSubscriptions(Params extraParams) throws Exception {
        return getBLVTSubscriptions(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subscription record
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tokenName"} -> the token name from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "id"} -> the id from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return the BLVT subscriptions as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
     * Query Subscription Record (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/blvt/subscribe/record")
    public <T> T getBLVTSubscriptions(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String subscriptionsResponse = sendGetSignedRequest(BLVT_SUBSCRIBE_RECORD_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(subscriptionsResponse);
            case LIBRARY_OBJECT:
                ArrayList<BLVTSubscription> subscriptions = new ArrayList<>();
                JSONArray jSubscriptions = new JSONArray(subscriptionsResponse);
                for (int j = 0; j < jSubscriptions.length(); j++)
                    subscriptions.add(new BLVTSubscription(jSubscriptions.getJSONObject(j)));
                return (T) subscriptions;
            default:
                return (T) subscriptionsResponse;
        }
    }

    /**
     * Request to redeem BLVT
     *
     * @param token:  token to redeem
     * @param amount: the amount of the redemption
     * @return the BLVT as {@link RedeemedBLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public RedeemedBLVT redeemBLVT(BLVTStructure token, double amount) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem BLVT
     *
     * @param token:  token to redeem
     * @param amount: the amount of the redemption
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public <T> T redeemBLVT(BLVTStructure token, double amount, ReturnFormat format) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, format);
    }

    /**
     * Request to redeem BLVT
     *
     * @param tokenName: the name of the token to redeem
     * @param amount:    the amount of the redemption
     * @return the BLVT as {@link RedeemedBLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public RedeemedBLVT redeemBLVT(String tokenName, double amount) throws Exception {
        return redeemBLVT(tokenName, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem BLVT
     *
     * @param tokenName: the name of the token to redeem
     * @param amount:    the amount of the redemption
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public <T> T redeemBLVT(String tokenName, double amount, ReturnFormat format) throws Exception {
        return redeemBLVT(tokenName, amount, -1, format);
    }

    /**
     * Request to redeem BLVT
     *
     * @param token:      token to redeem
     * @param amount:     the amount of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the BLVT as {@link RedeemedBLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public RedeemedBLVT redeemBLVT(BLVTStructure token, double amount, long recvWindow) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem BLVT
     *
     * @param token:      token to redeem
     * @param amount:     the amount of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public <T> T redeemBLVT(BLVTStructure token, double amount, long recvWindow, ReturnFormat format) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, recvWindow, format);
    }

    /**
     * Request to redeem BLVT
     *
     * @param tokenName:  the name of the token to redeem
     * @param amount:     the amount of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the BLVT as {@link RedeemedBLVT} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public RedeemedBLVT redeemBLVT(String tokenName, double amount, long recvWindow) throws Exception {
        return redeemBLVT(tokenName, amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem BLVT
     *
     * @param tokenName:  the name of the token to redeem
     * @param amount:     the amount of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return the BLVT as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
     * Redeem BLVT (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/blvt/redeem")
    public <T> T redeemBLVT(String tokenName, double amount, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("tokenName", tokenName);
        payload.addParam("amount", amount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String redeemResponse = sendPostSignedRequest(BLVT_REDEEM_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(redeemResponse);
            case LIBRARY_OBJECT:
                return (T) new RedeemedBLVT(new JSONObject(redeemResponse));
            default:
                return (T) redeemResponse;
        }
    }

    /**
     * Request to get the redemption record <br>
     * No-any params required
     *
     * @return the BLVT redemptions as {@link ArrayList} of {@link BLVTRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
     * Query Redemption Record (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/redeem/record")
    public ArrayList<BLVTRedemption> getBLVTRedemptions() throws Exception {
        return getBLVTRedemptions(LIBRARY_OBJECT);
    }

    /**
     * Request to get the redemption record
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT redemptions as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
     * Query Redemption Record (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/blvt/redeem/record")
    public <T> T getBLVTRedemptions(ReturnFormat format) throws Exception {
        return getBLVTRedemptions(null, LIBRARY_OBJECT);
    }

    /**
     * Request to get the redemption record
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tokenName"} -> the token name from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "id"} -> the id from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return the BLVT redemptions as {@link ArrayList} of {@link BLVTRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
     * Query Redemption Record (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/redeem/record")
    public ArrayList<BLVTRedemption> getBLVTRedemptions(Params extraParams) throws Exception {
        return getBLVTRedemptions(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the redemption record
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tokenName"} -> the token name from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "id"} -> the id from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return the BLVT redemptions as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
     * Query Redemption Record (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/blvt/redeem/record")
    public <T> T getBLVTRedemptions(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String redemptionsResponse = sendGetSignedRequest(BLVT_REDEEM_RECORD_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(redemptionsResponse);
            case LIBRARY_OBJECT:
                ArrayList<BLVTRedemption> redemptions = new ArrayList<>();
                JSONArray jRedemptions = new JSONArray(redemptionsResponse);
                for (int j = 0; j < jRedemptions.length(); j++)
                    redemptions.add(new BLVTRedemption(jRedemptions.getJSONObject(j)));
                return (T) redemptions;
            default:
                return (T) redemptionsResponse;
        }
    }

    /**
     * Request to get the BLVT user limit info <br>
     * No-any params required
     *
     * @return the BLVT user limit info as {@link ArrayList} of {@link BLVTLimitInfo} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-user-limit-info-user_data">
     * Get BLVT User Limit Info (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/userLimit")
    public ArrayList<BLVTLimitInfo> getBLVTUserLimitInfo() throws Exception {
        return getBLVTUserLimitInfo(LIBRARY_OBJECT);
    }

    /**
     * Request to get the BLVT user limit info
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT user limit info as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-user-limit-info-user_data">
     * Get BLVT User Limit Info (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/blvt/userLimit")
    public <T> T getBLVTUserLimitInfo(ReturnFormat format) throws Exception {
        return getBLVTUserLimitInfo(null, LIBRARY_OBJECT);
    }

    /**
     * Request to get the BLVT user limit info
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tokenName"} -> the token name from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return the BLVT user limit info as {@link ArrayList} of {@link BLVTLimitInfo} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-user-limit-info-user_data">
     * Get BLVT User Limit Info (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/blvt/userLimit")
    public ArrayList<BLVTLimitInfo> getBLVTUserLimitInfo(Params extraParams) throws Exception {
        return getBLVTUserLimitInfo(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the BLVT user limit info
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tokenName"} -> the token name from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return the BLVT user limit info as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-user-limit-info-user_data">
     * Get BLVT User Limit Info (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/blvt/userLimit")
    public <T> T getBLVTUserLimitInfo(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String limitsResponse = sendGetSignedRequest(BLVT_USER_LIMIT_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(limitsResponse);
            case LIBRARY_OBJECT:
                ArrayList<BLVTLimitInfo> limits = new ArrayList<>();
                JSONArray jLimits = new JSONArray(limitsResponse);
                for (int j = 0; j < jLimits.length(); j++)
                    limits.add(new BLVTLimitInfo(jLimits.getJSONObject(j)));
                return (T) limits;
            default:
                return (T) limitsResponse;
        }
    }

    /**
     * Method to start the stream to fetch the BLVT info by the stream
     *
     * @param token: token for the stream
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-info-streams">
     * Websocket BLVT Info Streams</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "wss://nbstream.binance.com/lvt-p<tokenName>@tokenNav")
    public void startWbsBLVTInfoStream(BLVTStructure token) throws Exception {
        startWbsBLVTInfoStream(token.getTokenName());
    }

    /**
     * Method to start the stream to fetch the BLVT info by the stream
     *
     * @param tokenName: name of the token for the stream
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-info-streams">
     * Websocket BLVT Info Streams</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "wss://nbstream.binance.com/lvt-p<tokenName>@tokenNav")
    public void startWbsBLVTInfoStream(String tokenName) throws Exception {
        startWebsocket(BLVT_WEBSOCKET_STREAM_ENDPOINT + tokenName.toUpperCase() + "@tokenNav");
    }

    /**
     * Method to format the stream response of the fetch of the BLVT info<br>
     * No any params required
     *
     * @return BLVT info as {@link WbsBLVTInfo} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-info-streams">
     * Websocket BLVT Info Streams</a>
     * @implNote <b>You need to call first these methods to open and fetch the stream by websocket: </b>
     * <ul>
     *     <li>
     *         {@link #startWbsBLVTInfoStream(BLVTStructure)}
     *     </li>
     *     <li>
     *         {@link #startWbsBLVTInfoStream(String)}
     *     </li>
     * </ul>
     **/
    @Wrapper
    public WbsBLVTInfo getWbsBLVTInfo() {
        return getWbsBLVTInfo(LIBRARY_OBJECT);
    }

    /**
     * Method to format the stream response of the fetch of the BLVT info
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT info as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-info-streams">
     * Websocket BLVT Info Streams</a>
     * @implNote <b>You need to call first these methods to open and fetch the stream by websocket: </b>
     * <ul>
     *     <li>
     *         {@link #startWbsBLVTInfoStream(BLVTStructure)}
     *     </li>
     *     <li>
     *         {@link #startWbsBLVTInfoStream(String)}
     *     </li>
     * </ul>
     **/
    @Returner
    public <T> T getWbsBLVTInfo(ReturnFormat format) {
        waitCorrectResponse(nav);
        switch (format) {
            case JSON:
                return (T) new JSONObject(webSocketResponse);
            case LIBRARY_OBJECT:
                return (T) new WbsBLVTInfo(new JSONObject(webSocketResponse));
            default:
                return (T) webSocketResponse;
        }
    }

    /**
     * Method to start the stream to fetch the BLVT NAV Kline/Candlestick streams
     *
     * @param token:    token for the stream
     * @param interval: the interval for the candlestick
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-nav-kline-candlestick-streams">
     * Websocket BLVT NAV Kline/Candlestick Streams</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "wss://nbstream.binance.com/lvt-p<tokenName>@nav_kline_<interval>")
    public void startWbsBLVTCandlestickStream(BLVTStructure token, Interval interval) throws Exception {
        startWbsBLVTCandlestickStream(token.getTokenName(), interval);
    }

    /**
     * Method to start the stream to fetch the BLVT NAV Kline/Candlestick streams
     *
     * @param tokenName: the name of the token for the stream
     * @param interval:  the interval for the candlestick
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-nav-kline-candlestick-streams">
     * Websocket BLVT NAV Kline/Candlestick Streams</a>
     **/
    @RequestPath(method = GET, path = "wss://nbstream.binance.com/lvt-p<tokenName>@nav_kline_<interval>")
    public void startWbsBLVTCandlestickStream(String tokenName, Interval interval) throws Exception {
        startWebsocket(BLVT_WEBSOCKET_STREAM_ENDPOINT + tokenName.toUpperCase() + "@nav_kline_" + interval);
    }

    /**
     * Method to format the stream response of the fetch of the BLVT NAV Kline/Candlestick stream<br>
     * No any params required
     *
     * @return BLVT candlestick as {@link WbsBLVTCandlestick} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-nav-kline-candlestick-streams">
     * Websocket BLVT NAV Kline/Candlestick Streams</a>
     * @implNote <b>You need to call first these methods to open and fetch the stream by websocket: </b>
     * <ul>
     *     <li>
     *         {@link #startWbsBLVTCandlestickStream(BLVTStructure, Interval)} (BLVTStructure)}
     *     </li>
     *     <li>
     *         {@link #startWbsBLVTCandlestickStream(String, Interval)} (String)}
     *     </li>
     * </ul>
     **/
    @Wrapper
    public WbsBLVTCandlestick getWbsBLVTCandlestick() {
        return getWbsBLVTCandlestick(LIBRARY_OBJECT);
    }

    /**
     * Method to format the stream response of the fetch of the BLVT NAV Kline/Candlestick stream
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the BLVT candlestick as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-nav-kline-candlestick-streams">
     * Websocket BLVT NAV Kline/Candlestick Streams</a>
     * @implNote <b>You need to call first these methods to open and fetch the stream by websocket: </b>
     * <ul>
     *     <li>
     *         {@link #startWbsBLVTCandlestickStream(BLVTStructure, Interval)} (BLVTStructure)}
     *     </li>
     *     <li>
     *         {@link #startWbsBLVTCandlestickStream(String, Interval)} (String)}
     *     </li>
     * </ul>
     **/
    @Returner
    public <T> T getWbsBLVTCandlestick(ReturnFormat format) {
        waitCorrectResponse(kline);
        switch (format) {
            case JSON:
                return (T) new JSONObject(webSocketResponse);
            case LIBRARY_OBJECT:
                return (T) new WbsBLVTCandlestick(new JSONObject(webSocketResponse));
            default:
                return (T) webSocketResponse;
        }
    }

}
