package com.tecknobit.binancemanager.Managers;

import com.tecknobit.apimanager.Manager.APIRequest;
import com.tecknobit.apimanager.Tools.Trading.TradingTools;
import com.tecknobit.binancemanager.Exceptions.SystemException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.Manager.APIRequest.GET_METHOD;
import static com.tecknobit.apimanager.Tools.Trading.TradingTools.computeAssetPercent;
import static com.tecknobit.apimanager.Tools.Trading.TradingTools.textualizeAssetPercent;
import static com.tecknobit.binancemanager.Constants.EndpointsList.SYSTEM_STATUS_ENDPOINT;
import static com.tecknobit.binancemanager.Constants.EndpointsList.TIMESTAMP_ENDPOINT;
import static java.lang.System.currentTimeMillis;

/**
 *  The {@code BinanceManager} class is useful to manage all Binance Endpoints
 *  giving basics methods for others Binance managers and basics endpoints for API request
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 *      https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceManager {

    /**
     * {@code BASE_ENDPOINTS} is a list constant that contains list of Binance's main endpoints
     * **/
    public static final String[] BASE_ENDPOINTS = new String[]{"https://api.binance.com", "https://api1.binance.com",
            "https://api2.binance.com", "https://api3.binance.com"};

    /**
     * {@code apiRequest} is instance that contains list of Binance's main endpoints
     * **/
    protected APIRequest apiRequest;

    /**
     * {@code baseEndpoint} is instance that  memorizes main endpoint where {@link BinanceManager}'s managers work on
     * **/
    protected final String baseEndpoint;

    /** Constructor to init a Binance manager
     * @param baseEndpoint base endpoint to work on
     * **/
    public BinanceManager(String baseEndpoint) throws SystemException, IOException {
        apiRequest = new APIRequest();
        if(baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
    }

    /** Method to set automatically a working endpoint <br>
     * Any params required
     * **/
    protected String getDefaultBaseEndpoint() throws SystemException, IOException {
        for (String string : BASE_ENDPOINTS)
            if(isSystemAvailable(string))
                return string;
        throw new SystemException();
    }

    /** Request to get system status
     * @param baseEndpoint endpoint to request status
     * **/
    public boolean isSystemAvailable(String baseEndpoint) throws IOException {
        apiRequest.sendAPIRequest(baseEndpoint + SYSTEM_STATUS_ENDPOINT, GET_METHOD);
        return ((JSONObject) apiRequest.getJSONResponse()).getInt("status") == 0;
    }

    /** Request to get server timestamp or your current timestamp
     * any param required
     * @return es. 1566247363776
     * **/
    public long getTimestamp(){
        try {
            apiRequest.sendAPIRequest(baseEndpoint + TIMESTAMP_ENDPOINT, GET_METHOD);
            return ((JSONObject) apiRequest.getJSONResponse()).getLong("serverTime");
        } catch (Exception e) {
            return currentTimeMillis();
        }
    }

    /** Method to get timestamp for request <br>
     * Any params required
     * @return "?timestamp=" + getTimestamp() return value
     * **/
    public String getParamTimestamp(){
        return "?timestamp=" + getTimestamp();
    }

    /** Method to execute and get response of a request
     * @param endpoint: endpoint to request
     * @param params: params HTTP for the request
     * @param method: method HTTP for the request
     * @param apiKey: apiKey of the account to perform request
     * @return response of request formatted in Json
     * **/
    public String getRequestResponse(String endpoint, String params, String method, String apiKey) throws IOException {
        apiRequest.sendAPIRequest(baseEndpoint + endpoint + params, method, "X-MBX-APIKEY", apiKey);
        return apiRequest.getResponse();
    }

    /** Method to execute and get response of a request
     * @param endpoint: endpoint to request
     * @param params: params HTTP for the request
     * @param method: method HTTP for the request
     * @return response of request formatted in Json
     * **/
    public String getRequestResponse(String endpoint, String params, String method) throws IOException {
        apiRequest.sendAPIRequest(baseEndpoint + endpoint + params, method);
        return apiRequest.getResponse();
    }

    /** Method to get status code of request response <br>
     * Any params required
     * @return status code of request response
     * **/
    public int getStatusResponse(){
        return apiRequest.getResponseStatusCode();
    }

    /** Method to get error response of an HTTP request <br>
     * Any params required
     * @return apiRequest.getErrorResponse();
     * **/
    public String getErrorResponse() {
        return apiRequest.getErrorResponse();
    }

    /** Method to print error response of an HTTP request  <br>
     * Any params required
     * **/
    public void printErrorResponse() {
        apiRequest.printErrorResponse();
    }

    /** Method to round a value
     * @param value: value to round
     * @param decimalDigits: number of digits to round final value
     * @return value rounded with decimalDigits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * **/
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

    /** Method to get percent between two values and textualize it
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @return percent value es. +8% or -8% as {@link String}
     * **/
    public String getTextTrendPercent(double startValue, double finalValue){
        return textualizeAssetPercent(startValue, finalValue);
    }

    /** Method to get percent between two values and textualize it
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value es. +8% or -8% as {@link String}
     * **/
    public String getTextTrendPercent(double startValue, double finalValue, int decimalDigits){
        return textualizeAssetPercent(startValue, finalValue, decimalDigits);
    }

    /** Method to concatenate a list of symbols
     * @param symbols: symbols to concatenate in {@link String} array format
     * @return list of symbols as {@link String} es. "%22symbol1%22,%22symbol2%22" HTTP encoded -> "symbol1","symbol2"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String assembleSymbolsList(String[] symbols) {
        return apiRequest.assembleParamsList("%22", "%22,", symbols);
    }

    /** Method to concatenate a list of symbols
     * @param symbols: symbols to concatenate in {@link ArrayList} of {@link String} format
     * @return list of symbols as {@link String} es. "%22symbol1%22,%22symbol2%22" HTTP encoded -> "symbol1","symbol2"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String assembleSymbolsList(ArrayList<String> symbols) {
        return apiRequest.assembleParamsList("%22", "%22,", symbols);
    }

    /**
     * The {@code Params} class is useful to assemble params values for the request
     * @implNote this class can be used to assemble body payload or query request params
     * @implSpec look this library <a href="https://github.com/N7ghtm4r3/APIManager">here</a>
     * @see com.tecknobit.apimanager.Manager.APIRequest.Params
     * **/

    public static class Params extends APIRequest.Params {}

}
