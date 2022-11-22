package com.tecknobit.binancemanager.managers.signedmanagers;

import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.HMAC_SHA256_ALGORITHM;

/**
 * The {@code BinanceSignedManager} class is useful to manage all signed binance requests
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 * Introduction</a>
 * @see BinanceManager
 **/
public class BinanceSignedManager extends BinanceManager {

    /**
     * {@code apiKey} is instance that contains api key of {@code "Binance"}'s account
     **/
    protected final String apiKey;

    /**
     * {@code secretKey} is instance that contains secret key of {@code "Binance"}'s account
     **/
    protected final String secretKey;

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceSignedManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        storeProperties(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceSignedManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                String secretKey) throws SystemException, IOException {
        this(baseEndpoint, defaultErrorMessage, -1, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceSignedManager(String baseEndpoint, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        this(baseEndpoint, null, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceSignedManager"}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceSignedManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        this(baseEndpoint, null, -1, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSignedManager} <br>
     * Any params required
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
    public BinanceSignedManager() {
        super();
        apiKey = properties.getProperty("apiKey");
        secretKey = properties.getProperty("secretKey");
    }

    /**
     * Method to store some properties
     *
     * @param baseEndpoint         base endpoint to work on
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     * @param apiKey               your api key
     * @param secretKey            your secret key
     **/
    protected void storeProperties(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                   String secretKey) {
        storeProperties(baseEndpoint, defaultErrorMessage, timeout);
        properties.setProperty("apiKey", apiKey);
        properties.setProperty("secretKey", secretKey);
    }

    /**
     * Method to execute an SpotOrder request and get response of that
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param method:   method HTTP for the request
     * @return response of the request
     **/
    protected String sendSignedRequest(String endpoint, String params, String method) throws Exception {
        return getRequestResponse(endpoint, params + getSignature(params), method, apiKey);
    }

    /** Method to get signature of request
     * @param params: params of request to get signature
     * @return es."&signature=c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    protected String getSignature(String params) throws Exception {
        return "&signature=" + APIRequest.getSignature(secretKey, params, HMAC_SHA256_ALGORITHM);
    }

    /** Method to get apiKey used <br>
     * Any params required
     * @return apiKey
     * **/
    public String getApiKey() {
        return apiKey;
    }

    /** Method to get secretKey used <br>
     * Any params required
     * @return secretKey
     * **/
    public String getSecretKey() {
        return secretKey;
    }

}
