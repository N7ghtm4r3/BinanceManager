package com.tecknobit.binancemanager.managers.signedmanagers;

import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.HMAC_SHA256_ALGORITHM;

/**
 * The {@code BinanceSignedManager} class is useful to manage all signed binance requests
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 *     https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceSignedManager extends BinanceManager {

    /**
     * {@code apiKey} is instance that contains api key of Binance's account
     * **/
    protected final String apiKey;

    /**
     * {@code secretKey} is instance that contains secret key of Binance's account
     * **/
    protected final String secretKey;

    /** Constructor to init BinanceSignedManager
     * @param baseEndpoint base endpoint to work on
     * @param apiKey your api key
     * @param secretKey your secret key
     * **/
    public BinanceSignedManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    /** Method to execute an SpotOrder request and get response of that
     * @param endpoint: endpoint to request
     * @param params: params HTTP for the request
     * @param method: method HTTP for the request
     * @return response of the request
     * **/
    protected String sendSignedRequest(String endpoint, String params, String method) throws Exception {
        return getRequestResponse(endpoint,params + getSignature(params), method, apiKey);
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
