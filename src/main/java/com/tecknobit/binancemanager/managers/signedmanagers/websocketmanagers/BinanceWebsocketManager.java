package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

import static com.tecknobit.apimanager.formatters.JsonHelper.getString;
import static com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType.no_content;
import static com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse.EventType.valueOf;

/**
 * The {@code BinanceWebsocketManager} class is useful to manage all websocket binance requests
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 * Introduction</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 */
public class BinanceWebsocketManager extends BinanceSignedManager {

    /**
     * {@code webSocketResponse} response obtained from the websocket connection
     */
    protected volatile String webSocketResponse;

    /**
     * Constructor to init a {@link BinanceWebsocketManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceWebsocketManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                   String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWebsocketManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceWebsocketManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                   String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWebsocketManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceWebsocketManager(String baseEndpoint, int timeout, String apiKey,
                                   String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWebsocketManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceWebsocketManager(String baseEndpoint, String apiKey,
                                   String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWebsocketManager} <br>
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
     */
    public BinanceWebsocketManager() {
        super();
    }

    /**
     * Method to start the websocket connection
     *
     * @param endpoint: the endpoint of the stream
     */
    protected void startWebsocket(String endpoint) throws Exception {
        new WebSocketClient(new URI(endpoint)) {

            /**
             * Called after an opening handshake has been performed and the given websocket is ready to be
             * written on.
             *
             * @param handshakeData The handshake of the websocket instance
             */
            @Override
            public void onOpen(ServerHandshake handshakeData) {
            }

            /**
             * Callback for string messages received from the remote host
             *
             * @param message The UTF-8 decoded message that was received.
             */
            @Override
            public void onMessage(String message) {
                webSocketResponse = message;
            }

            /**
             * Called after the websocket connection has been closed.
             *
             * @param code   The codes can be looked up here
             * @param reason Additional information string
             * @param remote Returns whether the closing of the connection was initiated by the remote
             *               host.
             */
            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            /**
             * Called when errors occurs. If an error causes the websocket connection to fail {@link
             * #onClose(int, String, boolean)} will be called additionally.<br> This method will be called
             * primarily because of IO or protocol errors.<br> If the given exception is an RuntimeException
             * that probably means that you encountered a bug.<br>
             *
             * @param ex The exception causing this error
             */
            @Override
            public void onError(Exception ex) {
                try {
                    throw new Exception(ex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.connect();
    }

    /**
     * Method to wait the correct response to format the correct object or return the correct response
     *
     * @param type: type of the event to wait
     */
    protected void waitCorrectResponse(EventType type) {
        while (webSocketResponse == null || valueOf(getString(new JSONObject(webSocketResponse), "e",
                no_content.name())) != type) {
            Thread.onSpinWait();
        }
    }

}
