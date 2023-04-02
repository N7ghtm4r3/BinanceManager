package com.tecknobit.binancemanager.managers.signedmanagers.giftcard;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.*;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceGiftCardManager} class is useful to manage gift card endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#binance-gift-card-endpoints">
 * Binance Gift Card Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceGiftCardManager extends BinanceSignedManager {

    /**
     * {@code GIFTCARD_CREATE_CODE_ENDPOINT} is constant for GIFTCARD_CREATE_CODE_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_CREATE_CODE_ENDPOINT = "/sapi/v1/giftcard/createCode";

    /**
     * {@code GIFTCARD_BUY_CODE_ENDPOINT} is constant for GIFTCARD_BUY_CODE_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_BUY_CODE_ENDPOINT = "/sapi/v1/giftcard/buyCode";

    /**
     * {@code GIFTCARD_REDEEM_CODE_ENDPOINT} is constant for GIFTCARD_REDEEM_CODE_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_REDEEM_CODE_ENDPOINT = "/sapi/v1/giftcard/redeemCode";

    /**
     * {@code GIFTCARD_VERIFY_ENDPOINT} is constant for GIFTCARD_VERIFY_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_VERIFY_ENDPOINT = "/sapi/v1/giftcard/verify";

    /**
     * {@code GIFTCARD_RSA_PUBLIC_KEY_ENDPOINT} is constant for GIFTCARD_RSA_PUBLIC_KEY_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_RSA_PUBLIC_KEY_ENDPOINT = "/sapi/v1/giftcard/cryptography/rsa-public-key";

    /**
     * {@code GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT} is constant for GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT = "/sapi/v1/giftcard/buyCode/token-limit";

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, String defaultErrorMessage, int timeout,
                                  String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, int timeout, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager} <br>
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
    public BinanceGiftCardManager() {
        super();
    }

    public TokenGiftCard createSingleTokenGiftCard(String token, double amount) throws Exception {
        return createSingleTokenGiftCard(token, amount, LIBRARY_OBJECT);
    }

    public <T> T createSingleTokenGiftCard(String token, double amount, ReturnFormat format) throws Exception {
        return createSingleTokenGiftCard(token, amount, -1, format);
    }

    public TokenGiftCard createSingleTokenGiftCard(String token, double amount, long recvWindow) throws Exception {
        return createSingleTokenGiftCard(token, amount, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T createSingleTokenGiftCard(String token, double amount, long recvWindow,
                                           ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("token", token);
        payload.addParam("amount", amount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return returnTokenGiftCard(sendPostSignedRequest(GIFTCARD_CREATE_CODE_ENDPOINT, payload), format);
    }

    public TokenGiftCard createDualTokenGiftCard(String baseToken, String faceToken,
                                                 double baseTokenAmount) throws Exception {
        return createDualTokenGiftCard(baseToken, faceToken, baseTokenAmount, LIBRARY_OBJECT);
    }

    public <T> T createDualTokenGiftCard(String baseToken, String faceToken, double baseTokenAmount,
                                         ReturnFormat format) throws Exception {
        return createDualTokenGiftCard(baseToken, faceToken, baseTokenAmount, null, format);
    }

    public TokenGiftCard createDualTokenGiftCard(String baseToken, String faceToken, double baseTokenAmount,
                                                 Params extraParams) throws Exception {
        return createDualTokenGiftCard(baseToken, faceToken, baseTokenAmount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T createDualTokenGiftCard(String baseToken, String faceToken, double baseTokenAmount, Params extraParams,
                                         ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("baseToken", baseToken);
        extraParams.addParam("faceToken", faceToken);
        extraParams.addParam("baseTokenAmount", baseTokenAmount);
        return returnTokenGiftCard(sendPostSignedRequest(GIFTCARD_BUY_CODE_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a token giftcard
     *
     * @param tokenGiftCardResponse: obtained from Binance's response
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return token giftcard as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTokenGiftCard(String tokenGiftCardResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(tokenGiftCardResponse);
            case LIBRARY_OBJECT:
                return (T) new TokenGiftCard(new JSONObject(tokenGiftCardResponse));
            default:
                return (T) tokenGiftCardResponse;
        }
    }

    public GiftCard redeemBinanceGiftCard(String code) throws Exception {
        return redeemBinanceGiftCard(code, LIBRARY_OBJECT);
    }

    public <T> T redeemBinanceGiftCard(String code, ReturnFormat format) throws Exception {
        return redeemBinanceGiftCard(code, null, format);
    }

    public GiftCard redeemBinanceGiftCard(String code, Params extraParams) throws Exception {
        return redeemBinanceGiftCard(code, extraParams, LIBRARY_OBJECT);
    }

    public <T> T redeemBinanceGiftCard(String code, Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("code", code);
        String giftCardResponse = sendPostSignedRequest(GIFTCARD_REDEEM_CODE_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(giftCardResponse);
            case LIBRARY_OBJECT:
                return (T) new GiftCard(new JSONObject(giftCardResponse));
            default:
                return (T) giftCardResponse;
        }
    }

    public GiftCardVerification verifyBinanceGiftCard(String referenceNo) throws Exception {
        return verifyBinanceGiftCard(referenceNo, LIBRARY_OBJECT);
    }

    public <T> T verifyBinanceGiftCard(String referenceNo, ReturnFormat format) throws Exception {
        return verifyBinanceGiftCard(referenceNo, -1, LIBRARY_OBJECT);
    }

    public GiftCardVerification verifyBinanceGiftCard(String referenceNo, long recvWindow) throws Exception {
        return verifyBinanceGiftCard(referenceNo, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T verifyBinanceGiftCard(String referenceNo, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampQuery(recvWindow);
        query.addParam("referenceNo", referenceNo);
        String verifyResponse = sendGetSignedRequest(GIFTCARD_VERIFY_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(verifyResponse);
            case LIBRARY_OBJECT:
                return (T) new GiftCardVerification(new JSONObject(verifyResponse));
            default:
                return (T) verifyResponse;
        }
    }

    public BinanceRSAPublicKey fetchRSAPublicKey() throws Exception {
        return fetchRSAPublicKey(LIBRARY_OBJECT);
    }

    public <T> T fetchRSAPublicKey(ReturnFormat format) throws Exception {
        return fetchRSAPublicKey(-1, LIBRARY_OBJECT);
    }

    public BinanceRSAPublicKey fetchRSAPublicKey(long recvWindow) throws Exception {
        return fetchRSAPublicKey(recvWindow, LIBRARY_OBJECT);
    }

    public <T> T fetchRSAPublicKey(long recvWindow, ReturnFormat format) throws Exception {
        String keyResponse = sendGetSignedRequest(GIFTCARD_VERIFY_ENDPOINT, createTimestampQuery(recvWindow));
        switch (format) {
            case JSON:
                return (T) new JSONObject(keyResponse);
            case LIBRARY_OBJECT:
                return (T) new BinanceRSAPublicKey(new JSONObject(keyResponse));
            default:
                return (T) keyResponse;
        }
    }

    public TokenLimits fetchTokenLimit(String baseToken) throws Exception {
        return fetchTokenLimit(baseToken, LIBRARY_OBJECT);
    }

    public <T> T fetchTokenLimit(String baseToken, ReturnFormat format) throws Exception {
        return fetchTokenLimit(baseToken, -1, LIBRARY_OBJECT);
    }

    public TokenLimits fetchTokenLimit(String baseToken, long recvWindow) throws Exception {
        return fetchTokenLimit(baseToken, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T fetchTokenLimit(String baseToken, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampQuery(recvWindow);
        query.addParam("baseToken", baseToken);
        String limitsResponse = sendGetSignedRequest(GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(limitsResponse);
            case LIBRARY_OBJECT:
                return (T) new TokenLimits(new JSONObject(limitsResponse));
            default:
                return (T) limitsResponse;
        }
    }

    private Params createTimestampQuery(long recvWindow) {
        Params query = new Params();
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        query.addParam("timestamp", getServerTime());
        return query;
    }

}
