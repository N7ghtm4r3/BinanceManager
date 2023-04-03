package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.BinanceWebsocketManager;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams.records.AccountUpdate;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams.records.BalanceUpdate;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams.records.OrderUpdate;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.BinanceEndpoint.MAIN_ENDPOINT;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceUserDataStreamsManager} class is useful to manage user data streams endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-data-streams">
 * User Data Streams</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see BinanceWebsocketManager
 **/
public class BinanceUserDataStreamsManager extends BinanceWebsocketManager {

    /**
     * {@code SPOT_USER_DATA_STREAM_ENDPOINT} is constant for SPOT_USER_DATA_STREAM_ENDPOINT's endpoint
     **/
    public static final String SPOT_USER_DATA_STREAM_ENDPOINT = "/api/v3/userDataStream";

    /**
     * {@code MARGIN_USER_DATA_STREAM_ENDPOINT} is constant for MARGIN_USER_DATA_STREAM_ENDPOINT's endpoint
     **/
    public static final String MARGIN_USER_DATA_STREAM_ENDPOINT = "/sapi/v1/userDataStream";

    /**
     * {@code ISOLATED_MARGIN_USER_DATA_STREAM_ENDPOINT} is constant for ISOLATED_MARGIN_USER_DATA_STREAM_ENDPOINT's endpoint
     **/
    public static final String ISOLATED_MARGIN_USER_DATA_STREAM_ENDPOINT = MARGIN_USER_DATA_STREAM_ENDPOINT + "/isolated";

    /**
     * {@code USER_DATA_STREAM_ENDPOINT} is constant for USER_DATA_STREAM_ENDPOINT's endpoint
     **/
    public static final String USER_DATA_STREAM_ENDPOINT = "wss://stream.binance.com:9443/ws/";

    /**
     * {@code previousListenKey} previous listen key used in the data stream
     **/
    private String previousListenKey;

    /**
     * {@code currentListenKey} current listen key used in the data stream
     **/
    private String currentListenKey;

    /**
     * Constructor to init a {@link BinanceUserDataStreamsManager}
     *
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceUserDataStreamsManager(String defaultErrorMessage, int timeout,
                                         String apiKey, String secretKey) throws SystemException, IOException {
        super(MAIN_ENDPOINT.toString(), defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceUserDataStreamsManager}
     *
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceUserDataStreamsManager(String defaultErrorMessage, String apiKey,
                                         String secretKey) throws SystemException, IOException {
        super(MAIN_ENDPOINT.toString(), defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceUserDataStreamsManager}
     *
     * @param timeout   :             custom timeout for request
     * @param apiKey    your api key
     * @param secretKey your secret key
     **/
    public BinanceUserDataStreamsManager(int timeout, String apiKey, String secretKey) throws SystemException, IOException {
        super(MAIN_ENDPOINT.toString(), timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceUserDataStreamsManager}
     *
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceUserDataStreamsManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(MAIN_ENDPOINT.toString(), apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceUserDataStreamsManager} <br>
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
    public BinanceUserDataStreamsManager() {
        super();
    }

    /**
     * Request to start a new user data stream. The stream will close after 60 minutes unless a keep-alive is sent.
     * If the account has an active {@code "listenKey"}, that {@code "listenKey"} will be returned and its validity
     * will be extended for 60 minutes <br>
     * No-any params required
     *
     * @return listen key as {@link String}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-spot">
     * LISTEN KEY (SPOT)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/api/v3/userDataStream")
    public String createSpotListenKey() throws Exception {
        return createSpotListenKey(LIBRARY_OBJECT);
    }

    /**
     * Request to start a new user data stream. The stream will close after 60 minutes unless a keep-alive is sent.
     * If the account has an active {@code "listenKey"}, that {@code "listenKey"} will be returned and its validity
     * will be extended for 60 minutes <br>
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return listen key {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-spot">
     * LISTEN KEY (SPOT)</a>
     **/
    @RequestPath(method = POST, path = "/api/v3/userDataStream")
    public <T> T createSpotListenKey(ReturnFormat format) throws Exception {
        return returnListenKey(sendPostRequest(SPOT_USER_DATA_STREAM_ENDPOINT, null, apiKey), format);
    }

    /**
     * Request to ping or keep-alive a listen key
     *
     * @param listenKey: the listen key to ping or keep-alive
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-spot">
     * LISTEN KEY (SPOT)</a>
     **/
    @RequestPath(method = PUT, path = "/api/v3/userDataStream")
    public boolean pingKeepAliveSpotListenKey(String listenKey) throws IOException {
        return sendPutRequest(SPOT_USER_DATA_STREAM_ENDPOINT, createListenKeyPayload(listenKey), apiKey).equals("{}");
    }

    /**
     * Request to close a listen key
     *
     * @param listenKey: the listen key to close
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-spot">
     * LISTEN KEY (SPOT)</a>
     **/
    @RequestPath(method = DELETE, path = "/api/v3/userDataStream")
    public boolean closeSpotListenKey(String listenKey) throws IOException {
        return sendDeleteRequest(SPOT_USER_DATA_STREAM_ENDPOINT, createListenKeyPayload(listenKey), apiKey).equals("{}");
    }

    /**
     * Request to start a new user data stream. The stream will close after 60 minutes unless a keep-alive is sent.
     * If the account has an active {@code "listenKey"}, that {@code "listenKey"} will be returned and its validity
     * will be extended for 60 minutes <br>
     * No-any params required
     *
     * @return listen key as {@link String}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin">
     * LISTEN KEY (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/userDataStream")
    public String createMarginListenKey() throws Exception {
        return createMarginListenKey(LIBRARY_OBJECT);
    }

    /**
     * Request to start a new user data stream. The stream will close after 60 minutes unless a keep-alive is sent.
     * If the account has an active {@code "listenKey"}, that {@code "listenKey"} will be returned and its validity
     * will be extended for 60 minutes
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return listen key {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin">
     * LISTEN KEY (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/userDataStream")
    public <T> T createMarginListenKey(ReturnFormat format) throws Exception {
        return returnListenKey(sendPostRequest(MARGIN_USER_DATA_STREAM_ENDPOINT, null, apiKey), format);
    }

    /**
     * Request to ping or keep-alive a listen key
     *
     * @param listenKey: the listen key to ping or keep-alive
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin">
     * LISTEN KEY (MARGIN)</a>
     **/
    @RequestPath(method = PUT, path = "/sapi/v1/userDataStream")
    public boolean pingKeepAliveMarginListenKey(String listenKey) throws IOException {
        return sendPutRequest(MARGIN_USER_DATA_STREAM_ENDPOINT, createListenKeyPayload(listenKey), apiKey).equals("{}");
    }

    /**
     * Request to close a listen key
     *
     * @param listenKey: the listen key to close
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin">
     * LISTEN KEY (MARGIN)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/userDataStream")
    public boolean closeMarginListenKey(String listenKey) throws IOException {
        return sendDeleteRequest(MARGIN_USER_DATA_STREAM_ENDPOINT, createListenKeyPayload(listenKey), apiKey).equals("{}");
    }

    /**
     * Request to start a new user data stream. The stream will close after 60 minutes unless a keep-alive is sent.
     * If the account has an active {@code "listenKey"}, that {@code "listenKey"} will be returned and its validity
     * will be extended for 60 minutes
     *
     * @param symbol: the symbol used in for the listen key
     * @return listen key as {@link String}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin>
     * LISTEN KEY (ISOLATED_MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/userDataStream/isolated")
    public String createIsolatedMarginListenKey(String symbol) throws Exception {
        return createIsolatedMarginListenKey(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to start a new user data stream. The stream will close after 60 minutes unless a keep-alive is sent.
     * If the account has an active {@code "listenKey"}, that {@code "listenKey"} will be returned and its validity
     * will be extended for 60 minutes
     *
     * @param symbol: the symbol used in for the listen key
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return listen key {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin>
     * LISTEN KEY (ISOLATED_MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/userDataStream/isolated")
    public <T> T createIsolatedMarginListenKey(String symbol, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("symbol", symbol);
        return returnListenKey(sendPostRequest(ISOLATED_MARGIN_USER_DATA_STREAM_ENDPOINT, payload, apiKey), format);
    }

    /**
     * Request to ping or keep-alive a listen key
     *
     * @param symbol:    the symbol used in for the listen key
     * @param listenKey: the listen key to ping or keep-alive
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin>
     * LISTEN KEY (ISOLATED_MARGIN)</a>
     **/
    @RequestPath(method = PUT, path = "/sapi/v1/userDataStream/isolated")
    public boolean pingKeepAliveIsolatedMarginListenKey(String symbol, String listenKey) throws IOException {
        Params payload = createListenKeyPayload(listenKey);
        payload.addParam("symbol", symbol);
        return sendPutRequest(ISOLATED_MARGIN_USER_DATA_STREAM_ENDPOINT, payload, apiKey).equals("{}");
    }

    /**
     * Request to ping or keep-alive a listen key
     *
     * @param symbol:    the symbol used in for the listen key
     * @param listenKey: the listen key to close
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#listen-key-isolated-margin>
     * LISTEN KEY (ISOLATED_MARGIN)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/userDataStream/isolated")
    public boolean closeIsolatedMarginListenKey(String symbol, String listenKey) throws IOException {
        Params payload = createListenKeyPayload(listenKey);
        payload.addParam("symbol", symbol);
        return sendDeleteRequest(ISOLATED_MARGIN_USER_DATA_STREAM_ENDPOINT, payload, apiKey).equals("{}");
    }

    /**
     * Method to create a listen key payload for the requests
     *
     * @param listenKey: the listen key to insert in the payload
     * @return payload as {@link Params}
     **/
    private Params createListenKeyPayload(String listenKey) {
        Params payload = new Params();
        payload.addParam("listenKey", listenKey);
        return payload;
    }

    /**
     * Method to create a listen key
     *
     * @param listenKeyResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return listen key {@code "format"} defines
     **/
    @Returner
    private <T> T returnListenKey(String listenKeyResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(listenKeyResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(listenKeyResponse).getString("listenKey");
            default:
                return (T) listenKeyResponse;
        }
    }

    /**
     * Method to get the account update from the user data streams
     *
     * @param listenKey: the listen key to use in the connection
     * @return account update as {@link AccountUpdate} custom object
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
     * @implNote this method could not work properly because need different scenarios attempts to be developed in the correct
     * way, so if you get an error when use it please create a GitHub's ticket <a href="https://github.com/N7ghtm4r3/BinanceManager/issues/new">here</a>
     * with Binance's websocket API response, hide personal data, and write about error that has been thrown.
     * Thank you for help!
     **/
    @Wrapper
    @RequestPath(method = GET, path = "wss://stream.binance.com:9443/ws/{listenKey}")
    public AccountUpdate getAccountUpdate(String listenKey) throws Exception {
        return getAccountUpdate(listenKey, LIBRARY_OBJECT);
    }

    /**
     * Method to get the account update from the user data streams
     *
     * @param listenKey: the listen key to use in the connection
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return account update as {@code "format"} defines
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
     * @implNote this method could not work properly because need different scenarios attempts to be developed in the correct
     * way, so if you get an error when use it please create a GitHub's ticket <a href="https://github.com/N7ghtm4r3/BinanceManager/issues/new">here</a>
     * with Binance's websocket API response, hide personal data, and write about error that has been thrown.
     * Thank you for help!
     **/
    @RequestPath(method = GET, path = "wss://stream.binance.com:9443/ws/{listenKey}")
    public <T> T getAccountUpdate(String listenKey, ReturnFormat format) throws Exception {
        return (T) getWebSocketContent(listenKey, AccountUpdate.class, format);
    }

    /**
     * Method to get the balance update from the user data streams
     *
     * @param listenKey: the listen key to use in the connection
     * @return balance update as {@link BalanceUpdate} custom object
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
     * @implNote this method could not work properly because need different scenarios attempts to be developed in the correct
     * way, so if you get an error when use it please create a GitHub's ticket <a href="https://github.com/N7ghtm4r3/BinanceManager/issues/new">here</a>
     * with Binance's websocket API response, hide personal data, and write about error that has been thrown.
     * Thank you for help!
     **/
    @Wrapper
    @RequestPath(method = GET, path = "wss://stream.binance.com:9443/ws/{listenKey}")
    public BalanceUpdate getBalanceUpdate(String listenKey) throws Exception {
        return getBalanceUpdate(listenKey, LIBRARY_OBJECT);
    }

    /**
     * Method to get the balance update from the user data streams
     *
     * @param listenKey: the listen key to use in the connection
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return balance update as {@code "format"} defines
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
     * @implNote this method could not work properly because need different scenarios attempts to be developed in the correct
     * way, so if you get an error when use it please create a GitHub's ticket <a href="https://github.com/N7ghtm4r3/BinanceManager/issues/new">here</a>
     * with Binance's websocket API response, hide personal data, and write about error that has been thrown.
     * Thank you for help!
     **/
    @RequestPath(method = GET, path = "wss://stream.binance.com:9443/ws/{listenKey}")
    public <T> T getBalanceUpdate(String listenKey, ReturnFormat format) throws Exception {
        return (T) getWebSocketContent(listenKey, BalanceUpdate.class, format);
    }

    /**
     * Method to get the order update from the user data streams
     *
     * @param listenKey: the listen key to use in the connection
     * @return order update as {@link OrderUpdate} custom object
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
     * @implNote this method could not work properly because need different scenarios attempts to be developed in the correct
     * way, so if you get an error when use it please create a GitHub's ticket <a href="https://github.com/N7ghtm4r3/BinanceManager/issues/new">here</a>
     * with Binance's websocket API response, hide personal data, and write about error that has been thrown.
     * Thank you for help!
     **/
    @Wrapper
    @RequestPath(method = GET, path = "wss://stream.binance.com:9443/ws/{listenKey}")
    public OrderUpdate getOrderUpdate(String listenKey) throws Exception {
        return getOrderUpdate(listenKey, LIBRARY_OBJECT);
    }

    /**
     * Method to get the order update from the user data streams
     *
     * @param listenKey: the listen key to use in the connection
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return order update as {@code "format"} defines
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
     * @implNote this method could not work properly because need different scenarios attempts to be developed in the correct
     * way, so if you get an error when use it please create a GitHub's ticket <a href="https://github.com/N7ghtm4r3/BinanceManager/issues/new">here</a>
     * with Binance's websocket API response, hide personal data, and write about error that has been thrown.
     * Thank you for help!
     **/
    @RequestPath(method = GET, path = "wss://stream.binance.com:9443/ws/{listenKey}")
    public <T> T getOrderUpdate(String listenKey, ReturnFormat format) throws Exception {
        return (T) getWebSocketContent(listenKey, OrderUpdate.class, format);
    }

    /**
     * Method to create an update object from websocket stream
     *
     * @param listenKey: the listen key to use in the connection
     * @param type:      which update object have to be created
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return content as {@code "format"} defines
     **/
    @Returner
    private <T> T getWebSocketContent(String listenKey, Class<T> type, ReturnFormat format) throws Exception {
        currentListenKey = listenKey;
        startWebsocket(USER_DATA_STREAM_ENDPOINT);
        while (webSocketResponse == null)
            Thread.onSpinWait();
        JSONObject response = new JSONObject(webSocketResponse);
        switch (format) {
            case JSON:
                return (T) response;
            case LIBRARY_OBJECT:
                if (type.equals(AccountUpdate.class))
                    return (T) new AccountUpdate(response);
                else if (type.equals(BalanceUpdate.class))
                    return (T) new BalanceUpdate(response);
                else
                    return (T) new OrderUpdate(response);
            default:
                return (T) webSocketResponse;
        }
    }

    /**
     * Method to start the websocket connection
     *
     * @param endpoint: the endpoint of the stream
     * @apiNote when you change {@code "listenKey"} will be created a new websocket connection with the new one key
     **/
    @Override
    protected void startWebsocket(String endpoint) throws Exception {
        if (!previousListenKey.equals(currentListenKey)) {
            previousListenKey = currentListenKey;
            super.startWebsocket(endpoint + currentListenKey);
        }
    }

}
