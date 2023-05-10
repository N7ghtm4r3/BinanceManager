package com.tecknobit.binancemanager.managers;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.apis.APIRequest;
import com.tecknobit.apimanager.apis.APIRequest.RequestMethod;
import com.tecknobit.apimanager.trading.TradingTools;
import com.tecknobit.binancemanager.exceptions.SystemException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.apimanager.trading.TradingTools.computeAssetPercent;
import static com.tecknobit.apimanager.trading.TradingTools.textualizeAssetPercent;
import static com.tecknobit.binancemanager.managers.market.BinanceMarketManager.TIMESTAMP_ENDPOINT;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.BinanceWalletManager.SYSTEM_STATUS_ENDPOINT;
import static java.lang.System.currentTimeMillis;

/**
 * The {@code BinanceManager} class is useful to manage all {@code "Binance"}'s Endpoints
 * giving basics methods for others {@code "Binance"} managers and basics endpoints for API request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 * Introduction</a>
 **/
public class BinanceManager {

    /**
     * {@code WEB_SOCKET_STREAM_ENDPOINT} is constant for WEB_SOCKET_STREAM_ENDPOINT's endpoint
     **/
    public static final String WEB_SOCKET_STREAM_ENDPOINT = "wss://stream.binance.com:9443/ws/";

    /**
     * {@code properties} is a local instance used to instantiate a new {@link BinanceManager}'s manager without
     * re-insert credentials
     **/
    protected static final Properties properties = new Properties();

    /**
     * {@code baseEndpoint} is instance that  memorizes main endpoint where {@link BinanceManager}'s managers work on
     **/
    protected final String baseEndpoint;

    /**
     * {@code apiRequest} is instance that contains list of {@code "Binance"}'s main endpoints
     **/
    protected APIRequest apiRequest;

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint         base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     **/
    public BinanceManager(String baseEndpoint, String defaultErrorMessage, int timeout) throws SystemException, IOException {
        apiRequest = new APIRequest(defaultErrorMessage, timeout);
        if (baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
        storeProperties(this.baseEndpoint, defaultErrorMessage, timeout);
    }

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint         base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage: custom error to show when is not a request error
     **/
    public BinanceManager(String baseEndpoint, String defaultErrorMessage) throws SystemException, IOException {
        apiRequest = new APIRequest(defaultErrorMessage);
        if (baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
        storeProperties(this.baseEndpoint, defaultErrorMessage, -1);
    }

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout:     custom timeout for request
     **/
    public BinanceManager(String baseEndpoint, int timeout) throws SystemException, IOException {
        apiRequest = new APIRequest(timeout);
        if (baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
        storeProperties(this.baseEndpoint, null, timeout);
    }

    /**
     * Constructor to init a {@link BinanceManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     **/
    public BinanceManager(String baseEndpoint) throws SystemException, IOException {
        apiRequest = new APIRequest();
        if (baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
        storeProperties(this.baseEndpoint, null, -1);
    }

    /**
     * Constructor to init a {@link BinanceManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceManager firstManager = new BinanceManager("defaultErrorMessage", timeout);
     *        //You don't need to insert all credentials to make manager work
     *        BinanceManager secondManager = new BinanceManager(); //same credentials used
     *     }
     * </pre>
     **/
    public BinanceManager() {
        baseEndpoint = properties.getProperty("baseEndpoint");
        if (baseEndpoint == null)
            throw new IllegalArgumentException("You need to call a parameterized constructor first");
        String defaultErrorMessage = properties.getProperty("defaultErrorMessage");
        int timeout;
        try {
            timeout = Integer.parseInt(properties.getProperty("timeout"));
        } catch (NumberFormatException e) {
            timeout = -1;
        }
        if (defaultErrorMessage != null && timeout != -1)
            apiRequest = new APIRequest(defaultErrorMessage, timeout);
        else if (defaultErrorMessage != null)
            apiRequest = new APIRequest(defaultErrorMessage);
        else if (timeout != -1)
            apiRequest = new APIRequest(timeout);
        else
            apiRequest = new APIRequest();
    }

    /**
     * Method to store some properties
     *
     * @param baseEndpoint         base endpoint to work on
     * @param defaultErrorMessage: custom error to show when is not a request error
     * @param timeout:             custom timeout for request
     **/
    protected void storeProperties(String baseEndpoint, String defaultErrorMessage, int timeout) {
        properties.clear();
        properties.setProperty("baseEndpoint", baseEndpoint);
        if (defaultErrorMessage != null)
            properties.setProperty("defaultErrorMessage", defaultErrorMessage);
        if (timeout != -1)
            properties.setProperty("timeout", String.valueOf(timeout));
    }

    /**
     * Method to set automatically a working endpoint <br>
     * No-any params required
     **/
    protected String getDefaultBaseEndpoint() throws SystemException, IOException {
        for (BinanceEndpoint endpoint : BinanceEndpoint.values())
            if (isSystemAvailable(endpoint.endpoint))
                return endpoint.endpoint;
        throw new SystemException();
    }

    /**
     * Request to get system status
     *
     * @param baseEndpoint endpoint to request status
     **/
    public boolean isSystemAvailable(String baseEndpoint) throws IOException {
        apiRequest.sendAPIRequest(baseEndpoint + SYSTEM_STATUS_ENDPOINT, GET);
        return new JSONObject(apiRequest.getResponse()).getInt("status") == 0;
    }

    /**
     * Request to get server timestamp or your current timestamp
     * No-any params required
     *
     * @return es. 1566247363776
     **/
    @RequestPath(method = GET, path = "/api/v3/time")
    public long getServerTime() {
        try {
            apiRequest.sendAPIRequest(baseEndpoint + TIMESTAMP_ENDPOINT, GET);
            return ((JSONObject) apiRequest.getJSONResponse()).getLong("serverTime");
        } catch (Exception e) {
            return currentTimeMillis();
        }
    }

    /**
     * Method to get timestamp for request <br>
     * No-any params required
     *
     * @return "?timestamp=" + getServerTime() return value
     **/
    public String getTimestampParam() {
        return "?timestamp=" + getServerTime();
    }

    /**
     * Method to execute and get response of {@link RequestMethod#GET} request
     *
     * @param endpoint : endpoint to request
     * @param params   :   params HTTP for the request
     * @return response of request formatted in Json
     **/
    @Wrapper
    public String sendGetRequest(String endpoint, Params params) throws IOException {
        return sendGetRequest(endpoint, params, null);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#GET} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    public String sendGetRequest(String endpoint, Params params, String apiKey) throws IOException {
        if (params == null)
            params = new Params();
        return sendRequest(endpoint, params.createQueryString(), apiKey, GET);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#GET} request
     *
     * @param endpoint : endpoint to request
     * @param params   :   params HTTP for the request
     * @return response of request formatted in Json
     **/
    @Wrapper
    public String sendGetRequest(String endpoint, String params) throws IOException {
        return sendGetRequest(endpoint, params, null);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#GET} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    public String sendGetRequest(String endpoint, String params, String apiKey) throws IOException {
        return sendRequest(endpoint, params, apiKey, GET);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#DELETE} request
     *
     * @param endpoint : endpoint to request
     * @param params   :   params HTTP for the request
     * @return response of request formatted in Json
     **/
    @Wrapper
    public String sendDeleteRequest(String endpoint, Params params) throws IOException {
        return sendDeleteRequest(endpoint, params, null);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#DELETE} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    public String sendDeleteRequest(String endpoint, Params params, String apiKey) throws IOException {
        return sendRequest(endpoint, params.createQueryString(), apiKey, DELETE);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#DELETE} request
     *
     * @param endpoint : endpoint to request
     * @param params   :   params HTTP for the request
     * @return response of request formatted in Json
     **/
    @Wrapper
    public String sendDeleteRequest(String endpoint, String params) throws IOException {
        return sendDeleteRequest(endpoint, params, null);
    }

    /**
     * Method to execute and get response of {@link RequestMethod#DELETE} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    public String sendDeleteRequest(String endpoint, String params, String apiKey) throws IOException {
        return sendRequest(endpoint, params, apiKey, DELETE);
    }

    /**
     * Method to execute and get response of a request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    private String sendRequest(String endpoint, String params, String apiKey, RequestMethod method) throws IOException {
        if (params == null)
            params = "";
        String reqUrl = baseEndpoint + endpoint + params;
        if (apiKey != null)
            apiRequest.sendAPIRequest(reqUrl, method, "X-MBX-APIKEY", apiKey);
        else
            apiRequest.sendAPIRequest(reqUrl, method);
        return apiRequest.getResponse();
    }

    /**
     * Method to execute and get response of a {@link RequestMethod#POST} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @return response of request formatted in Json
     **/
    @Wrapper
    public String sendPostRequest(String endpoint, Params params) throws IOException {
        return sendPostRequest(endpoint, params, null);
    }

    /**
     * Method to execute and get response of a {@link RequestMethod#POST} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    public String sendPostRequest(String endpoint, Params params, String apiKey) throws IOException {
        return sendPayloadedRequest(endpoint, params, apiKey, POST);
    }

    /**
     * Method to execute and get response of a {@link RequestMethod#PUT} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @return response of request formatted in Json
     **/
    @Wrapper
    public String sendPutRequest(String endpoint, Params params) throws IOException {
        return sendPutRequest(endpoint, params, null);
    }

    /**
     * Method to execute and get response of a {@link RequestMethod#PUT} request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    public String sendPutRequest(String endpoint, Params params, String apiKey) throws IOException {
        return sendPayloadedRequest(endpoint, params, apiKey, PUT);
    }

    /**
     * Method to execute and get response of a request
     *
     * @param endpoint: endpoint to request
     * @param params:   params HTTP for the request
     * @param apiKey:   apiKey of the account to perform request
     * @return response of request formatted in Json
     **/
    private String sendPayloadedRequest(String endpoint, Params params, String apiKey,
                                        RequestMethod method) throws IOException {
        if (params == null)
            params = new Params();
        String reqUrl = baseEndpoint + endpoint;
        if (apiKey != null)
            apiRequest.sendPayloadedAPIRequest(reqUrl, method, "X-MBX-APIKEY", apiKey, params);
        else
            apiRequest.sendPayloadedAPIRequest(reqUrl, method, params);
        return apiRequest.getResponse();
    }

    /**
     * Method to get status code of request response <br>
     * No-any params required
     *
     * @return status code of request response
     **/
    public int getStatusResponse() {
        return apiRequest.getResponseStatusCode();
    }

    /**
     * Method to get error response of an HTTP request <br>
     * No-any params required
     *
     * @return apiRequest.getErrorResponse();
     **/
    public String getErrorResponse() {
        return apiRequest.getErrorResponse();
    }

    /**
     * Method to get error response of an HTTP request <br>
     * No-any params required
     *
     * @return apiRequest.getErrorResponse() as
     **/
    public <T> T getJSONErrorResponse() {
        return apiRequest.getJSONErrorResponse();
    }

    /**
     * Method to print error response of an HTTP request  <br>
     * No-any params required
     **/
    public void printErrorResponse() {
        apiRequest.printErrorResponse();
    }

    /**
     * Method to round a value
     *
     * @param value:         value to round
     * @param decimalDigits: number of digits to round final value
     * @return value rounded with decimalDigits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double roundValue(double value, int decimalDigits){
        return TradingTools.roundValue(value, decimalDigits);
    }

    /** Method to get percent between two values
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     * **/
    public double getTrendPercent(double startValue, double finalValue){
        return computeAssetPercent(startValue, finalValue);
    }

    /** Method to get percent between two values and round it
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     * **/
    public double getTrendPercent(double startValue, double finalValue, int decimalDigits){
        return computeAssetPercent(startValue, finalValue, decimalDigits);
    }

    /** Method to format percent between two values and textualize it
     * @param percent: value to format
     * @return percent value formatted es. +8% or -8% as {@link String}
     * **/
    public String getTextTrendPercent(double percent){
        return textualizeAssetPercent(percent);
    }

    /**
     * Method to get percent between two values and textualize it
     *
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @return percent value es. +8% or -8% as {@link String}
     **/
    public String getTextTrendPercent(double startValue, double finalValue) {
        return textualizeAssetPercent(startValue, finalValue);
    }

    /**
     * Method to get percent between two values and textualize it
     *
     * @param startValue:    first value to make compare
     * @param finalValue:    last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value es. +8% or -8% as {@link String}
     **/
    public String getTextTrendPercent(double startValue, double finalValue, int decimalDigits) {
        return textualizeAssetPercent(startValue, finalValue, decimalDigits);
    }

    /**
     * Method to concatenate a list of symbols
     *
     * @param symbols: symbols to concatenate in {@link String} array format
     * @return list of symbols as {@link String} es. "%22symbol1%22,%22symbol2%22" HTTP encoded -> "symbol1","symbol2"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
    public <T> String assembleSymbolsList(T[] symbols) {
        return apiRequest.assembleParamsList("%22", "%22,", symbols);
    }

    /**
     * Method to concatenate a list of symbols
     *
     * @param symbols: symbols to concatenate in {@link ArrayList} of {@link String} format
     * @return list of symbols as {@link String} es. "%22symbol1%22,%22symbol2%22" HTTP encoded -> "symbol1","symbol2"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
    public <T> String assembleSymbolsList(ArrayList<T> symbols) {
        return apiRequest.assembleParamsList("%22", "%22,", symbols);
    }

    /**
     * Method to manage a params list
     *
     * @param params: params to manage
     * @param key:    key of the list to manage
     **/
    protected <T> void manageList(Params params, String key) {
        T list = params.getParam(key);
        if (list != null) {
            if (list instanceof Collection<?>)
                list = (T) ((Collection<?>) list).toArray(new Object[0]);
            if (list instanceof Object[] && !Arrays.toString((Object[]) list).contains("%22"))
                params.addParam(key, "[" + assembleSymbolsList((T[]) list) + "]");
        }
    }

    /**
     * The {@code Params} class is useful to assemble params values for the request
     *
     * @implNote this class can be used to assemble body payload or query request params
     * @implSpec look this library <a href="https://github.com/N7ghtm4r3/APIManager">here</a>
     * @see APIRequest.Params
     **/
    public static class Params extends APIRequest.Params {
    }

    /**
     * {@code BinanceEndpoint} list of available {@code "Binance"}'s endpoints
     **/
    public enum BinanceEndpoint {

        /**
         * {@code "MAIN_ENDPOINT"} principal API cluster
         **/
        MAIN_ENDPOINT("https://api.binance.com"),

        /**
         * {@code "SECOND_ENDPOINT"} second API cluster
         **/
        SECOND_ENDPOINT("https://api1.binance.com"),

        /**
         * {@code "THIRD_ENDPOINT"} third API cluster
         **/
        THIRD_ENDPOINT("https://api3.binance.com"),

        /**
         * {@code "FOURTH_ENDPOINT"} forth API cluster
         **/
        FOURTH_ENDPOINT("https://api3.binance.com"),

        /**
         * {@code "FIFTH_ENDPOINT"} fifth API cluster
         **/
        FIFTH_ENDPOINT("https://api4.binance.com");

        /**
         * {@code "endpoint"} value
         **/
        private final String endpoint;

        /**
         * Constructor to init a {@link BinanceEndpoint}
         *
         * @param endpoint endpoint value
         **/
        BinanceEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        /**
         * Method to get {@link #endpoint} instance <br>
         * No-any params required
         *
         * @return {@link #endpoint} instance as {@link String}
         **/
        @Override
        public String toString() {
            return endpoint;
        }

    }

    /**
     * {@code ReturnFormat} is the instance to pass in {@link Returner} methods to format as you want the response by
     * {@code "Binance"}
     *
     * @apiNote you can choose between:
     * <ul>
     * <li>
     * {@link Returner.ReturnFormat#STRING} -> returns the response formatted as {@link String}
     * </li>
     * <li>
     * {@link Returner.ReturnFormat#JSON} -> returns the response formatted as {@code "JSON"}
     * </li>
     * <li>
     * {@link Returner.ReturnFormat#LIBRARY_OBJECT} -> returns the response formatted as custom object offered by library that uses this list
     * </li>
     * </ul>
     **/
    public enum ReturnFormat {

        STRING,
        JSON,
        LIBRARY_OBJECT

    }

    /**
     * The {@code BinanceResponse} interface is useful to format base {@code "Binance"}'s errors responses
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public interface BinanceResponse {

        /**
         * Method to get error code <br>
         * No-any params required
         *
         * @return code of error as int
         * *
         * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
         **/
        int getCode();

        /**
         * Method to get error message <br>
         * No-any params required
         *
         * @return message of error as {@link String}
         * *
         * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
         **/
        String getMessage();

    }

}
