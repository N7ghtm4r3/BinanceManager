package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt;

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

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

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

    public ArrayList<BLVTInfo> getBLVTInfo() throws Exception {
        return getBLVTInfo(LIBRARY_OBJECT);
    }

    public <T> T getBLVTInfo(ReturnFormat format) throws Exception {
        return getBLVTInfo(null, format);
    }

    public ArrayList<BLVTInfo> getBLVTInfo(String tokenName) throws Exception {
        return getBLVTInfo(tokenName, LIBRARY_OBJECT);
    }

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

    public BLVT subscribeBLVT(BLVTStructure token, double cost) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, LIBRARY_OBJECT);
    }

    public <T> T subscribeBLVT(BLVTStructure token, double cost, ReturnFormat format) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, format);
    }

    public BLVT subscribeBLVT(String tokenName, double cost) throws Exception {
        return subscribeBLVT(tokenName, cost, LIBRARY_OBJECT);
    }

    public <T> T subscribeBLVT(String tokenName, double cost, ReturnFormat format) throws Exception {
        return subscribeBLVT(tokenName, cost, -1, format);
    }

    public BLVT subscribeBLVT(BLVTStructure token, double cost, long recvWindow) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T subscribeBLVT(BLVTStructure token, double cost, long recvWindow, ReturnFormat format) throws Exception {
        return subscribeBLVT(token.getTokenName(), cost, recvWindow, format);
    }

    public BLVT subscribeBLVT(String tokenName, double cost, long recvWindow) throws Exception {
        return subscribeBLVT(tokenName, cost, recvWindow, LIBRARY_OBJECT);
    }

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

    public ArrayList<BLVTSubscription> getBLVTSubscriptions() throws Exception {
        return getBLVTSubscriptions(LIBRARY_OBJECT);
    }

    public <T> T getBLVTSubscriptions(ReturnFormat format) throws Exception {
        return getBLVTSubscriptions(null, LIBRARY_OBJECT);
    }

    public ArrayList<BLVTSubscription> getBLVTSubscriptions(Params extraParams) throws Exception {
        return getBLVTSubscriptions(extraParams, LIBRARY_OBJECT);
    }

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

    public RedeemedBLVT redeemBLVT(BLVTStructure token, double amount) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, LIBRARY_OBJECT);
    }

    public <T> T redeemBLVT(BLVTStructure token, double amount, ReturnFormat format) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, format);
    }

    public RedeemedBLVT redeemBLVT(String tokenName, double amount) throws Exception {
        return redeemBLVT(tokenName, amount, LIBRARY_OBJECT);
    }

    public <T> T redeemBLVT(String tokenName, double amount, ReturnFormat format) throws Exception {
        return redeemBLVT(tokenName, amount, -1, format);
    }

    public RedeemedBLVT redeemBLVT(BLVTStructure token, double amount, long recvWindow) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T redeemBLVT(BLVTStructure token, double amount, long recvWindow, ReturnFormat format) throws Exception {
        return redeemBLVT(token.getTokenName(), amount, recvWindow, format);
    }

    public RedeemedBLVT redeemBLVT(String tokenName, double amount, long recvWindow) throws Exception {
        return redeemBLVT(tokenName, amount, recvWindow, LIBRARY_OBJECT);
    }

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

    public ArrayList<BLVTRedemption> getBLVTRedemptions() throws Exception {
        return getBLVTRedemptions(LIBRARY_OBJECT);
    }

    public <T> T getBLVTRedemptions(ReturnFormat format) throws Exception {
        return getBLVTRedemptions(null, LIBRARY_OBJECT);
    }

    public ArrayList<BLVTRedemption> getBLVTRedemptions(Params extraParams) throws Exception {
        return getBLVTRedemptions(extraParams, LIBRARY_OBJECT);
    }

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

    public ArrayList<BLVTLimitInfo> getBLVTUserLimitInfo() throws Exception {
        return getBLVTUserLimitInfo(LIBRARY_OBJECT);
    }

    public <T> T getBLVTUserLimitInfo(ReturnFormat format) throws Exception {
        return getBLVTUserLimitInfo(null, LIBRARY_OBJECT);
    }

    public ArrayList<BLVTLimitInfo> getBLVTUserLimitInfo(Params extraParams) throws Exception {
        return getBLVTUserLimitInfo(extraParams, LIBRARY_OBJECT);
    }

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

    public void startWbsBLVTInfoStream(BLVTStructure token) throws Exception {
        startWbsBLVTInfoStream(token.getTokenName());
    }

    public void startWbsBLVTInfoStream(String tokenName) throws Exception {
        startWebsocket(BLVT_WEBSOCKET_STREAM_ENDPOINT + tokenName.toUpperCase() + "@tokenNav");
    }

    public WbsBLVTInfo getWbsBLVTInfo() {
        return getWbsBLVTInfo(LIBRARY_OBJECT);
    }

    public <T> T getWbsBLVTInfo(ReturnFormat format) {
        while (webSocketResponse == null)
            Thread.onSpinWait();
        switch (format) {
            case JSON:
                return (T) new JSONObject(webSocketResponse);
            case LIBRARY_OBJECT:
                return (T) new WbsBLVTInfo(new JSONObject(webSocketResponse));
            default:
                return (T) webSocketResponse;
        }
    }

    public void startWbsBLVTCandlestickStream(BLVTStructure token, Interval interval) throws Exception {
        startWbsBLVTCandlestickStream(token.getTokenName(), interval);
    }

    public void startWbsBLVTCandlestickStream(String tokenName, Interval interval) throws Exception {
        startWebsocket(BLVT_WEBSOCKET_STREAM_ENDPOINT + tokenName.toUpperCase() + "@nav_kline_" + interval);
    }

    public WbsBLVTCandlestick getWbsBLVTCandlestick() {
        return getWbsBLVTCandlestick(LIBRARY_OBJECT);
    }

    public <T> T getWbsBLVTCandlestick(ReturnFormat format) {
        while (webSocketResponse == null)
            Thread.onSpinWait();
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
