package com.tecknobit.binancemanager.managers.signedmanagers;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.HMAC_SHA256_ALGORITHM;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.DELETE;
import static com.tecknobit.apimanager.apis.APIRequest.getSignature;

/**
 * The {@code BinanceSignedManager} class is useful to manage all signed binance requests
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 * Introduction</a>
 * @see BinanceManager
 */
public class BinanceSignedManager extends BinanceManager {

    /**
     * {@code apiKey} is instance that contains api key of {@code "Binance"}'s account
     */
    protected final String apiKey;

    /**
     * {@code secretKey} is instance that contains secret key of {@code "Binance"}'s account
     */
    protected final String secretKey;

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSignedManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        storeKeys(apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSignedManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        storeKeys(apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSignedManager(String baseEndpoint, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        storeKeys(apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSignedManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        storeKeys(apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager} <br>
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
    public BinanceSignedManager() {
        super();
        apiKey = properties.getProperty("apiKey");
        secretKey = properties.getProperty("secretKey");
    }

    /**
     * Method to store some properties
     *
     * @param apiKey    your api key
     * @param secretKey your secret key
     */
    protected void storeKeys(String apiKey, String secretKey) {
        properties.setProperty("apiKey", apiKey);
        properties.setProperty("secretKey", secretKey);
    }

    /**
     * Method to create a payload with the timestamp value
     *
     * @param extraParams: extra params of the request
     * @return payload as {@link Params}
     */
    protected Params createTimestampPayload(Params extraParams) {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        return extraParams;
    }

    /**
     * Method to execute a get request and get response of that
     *
     * @param endpoint: endpoint to request
     * @return response of the request
     */
    @Wrapper
    protected String sendGetSignedRequest(String endpoint) throws Exception {
        return sendGetSignedRequest(endpoint, (String) null);
    }

    /**
     * Method to execute a get request and get response of that
     *
     * @param endpoint : endpoint to request
     * @param params   :   params HTTP for the request
     * @return response of the request
     */
    @Wrapper
    protected String sendGetSignedRequest(String endpoint, Params params) throws Exception {
        String query = null;
        if (params != null)
            query = params.createQueryString();
        return sendGetSignedRequest(endpoint, query);
    }

    /**
     * Method to execute a get request and get response of that
     *
     * @param endpoint : endpoint to request
     * @param params   :   params HTTP for the request
     * @return response of the request
     */
    protected String sendGetSignedRequest(String endpoint, String params) throws Exception {
        APIRequest.Params mParams = new APIRequest.Params();
        if (params == null)
            params = "";
        mParams.addParam("signature", getSignature(secretKey, params, HMAC_SHA256_ALGORITHM));
        return sendGetRequest(endpoint, apiRequest.encodeAdditionalParams(params, mParams), apiKey);
    }

    /**
     * Method to execute a delete request and get response of that
     *
     * @param endpoint: endpoint to request
     * @return response of the request
     */
    @Wrapper
    protected String sendDeleteSignedRequest(String endpoint) throws Exception {
        return sendDeleteSignedRequest(endpoint, (String) null);
    }

    /**
     * Method to execute a delete request and get response of that
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @return response of the request
     */
    @Wrapper
    protected String sendDeleteSignedRequest(String endpoint, Params params) throws Exception {
        if (params == null)
            params = new Params();
        return sendDeleteSignedRequest(endpoint, params.createQueryString());
    }

    /**
     * Method to execute a delete request and get response of that
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @return response of the request
     */
    protected String sendDeleteSignedRequest(String endpoint, String params) throws Exception {
        APIRequest.Params mParams = new APIRequest.Params();
        if (params == null)
            params = "";
        apiRequest.sendAPIRequest(baseEndpoint + endpoint + params, DELETE, "X-MBX-APIKEY", apiKey);
        return apiRequest.getResponse();
    }

    /**
     * Method to execute a signed post request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @return response of the request
     */
    protected String sendPostSignedRequest(String endpoint, Params params) throws Exception {
        if (params == null)
            params = new Params();
        params.addParam("timestamp", getServerTime());
        params.addParam("signature", getSignature(secretKey, params.createQueryString(), HMAC_SHA256_ALGORITHM));
        return sendPostRequest(endpoint, params, apiKey);
    }

    /**
     * Method to create a transaction identifier
     *
     * @param trainIdResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return transaction identifier as {@code "format"} defines
     */
    @Returner
    protected <T> T returnTranId(String trainIdResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(trainIdResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(new JSONObject(trainIdResponse).getLong("tranId"));
            default:
                return (T) trainIdResponse;
        }
    }

    /**
     * Method to get apiKey used <br>
     * No-any params required
     *
     * @return apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Method to get secretKey used <br>
     * No-any params required
     *
     * @return secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

}
