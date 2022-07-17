package com.tecknobit.binancemanager.Managers;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import com.tecknobit.apimanager.Tools.Trading.TradingTools;
import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Helpers.RequestManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.binancemanager.Constants.EndpointsList.SYSTEM_STATUS_ENDPOINT;
import static com.tecknobit.binancemanager.Constants.EndpointsList.TIMESTAMP_ENDPOINT;
import static com.tecknobit.binancemanager.Helpers.RequestManager.GET_METHOD;
import static java.lang.System.currentTimeMillis;

/**
 *  The {@code BinanceManager} class is useful to manage all Binance Endpoints
 *  giving basics methods for others Binance managers and basics endpoints for API request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
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
     * {@code requestManager} is instance that contains list of Binance's main endpoints
     * **/
    protected RequestManager requestManager;

    /**
     * {@code jsonObject} is instance that help to format api response when they are {@link JSONObject}
     * **/
    protected JSONObject jsonObject;

    /**
     * {@code accountDetails} is instance that help to format api response when they are {@link JSONArray}
     * **/
    protected JSONArray jsonArray;

    /**
     * {@code jsonHelper} is instance that help to format api response when they are JSON format type
     * **/
    protected JsonHelper jsonHelper;

    /**
     * {@code baseEndpoint} is instance that  memorizes main endpoint where {@link BinanceManager}'s managers work on
     * **/
    protected final String baseEndpoint;

    /**
     * {@code tradingTools} is instance that  memorizes {@link TradingTools} object
     * **/
    protected final TradingTools tradingTools;

    /** Constructor to init a Binance manager
     * @param baseEndpoint base endpoint to work on
     * **/
    public BinanceManager(String baseEndpoint) throws SystemException, IOException {
        requestManager = new RequestManager();
        tradingTools = new TradingTools();
        if(baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
    }

    /** Method to set automatically a working endpoint
     * any params required
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
        requestManager.sendAPIRequest(baseEndpoint + SYSTEM_STATUS_ENDPOINT, GET_METHOD);
        jsonObject = requestManager.getJSONResponse();
        return jsonObject.getInt("status") == 0;
    }

    /** Request to get server timestamp or your current timestamp
     * any param required
     * @return es. 1566247363776
     * **/
    public long getTimestamp(){
        try {
            requestManager.sendAPIRequest(baseEndpoint + TIMESTAMP_ENDPOINT, GET_METHOD);
            jsonObject = requestManager.getJSONResponse();
            return jsonObject.getLong("serverTime");
        } catch (Exception e) {
            return currentTimeMillis();
        }
    }

    /** Method to get timestamp for request
     * any params required
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
        requestManager.sendAPIRequest(baseEndpoint + endpoint + params, method, "X-MBX-APIKEY", apiKey);
        return requestManager.getResponse();
    }

    /** Method to execute and get response of a request
     * @param endpoint: endpoint to request
     * @param params: params HTTP for the request
     * @param method: method HTTP for the request
     * @return response of request formatted in Json
     * **/
    public String getRequestResponse(String endpoint, String params, String method) throws IOException {
        requestManager.sendAPIRequest(baseEndpoint + endpoint + params, method);
        return requestManager.getResponse();
    }

    /** Method to get status code of request response
     * any params required
     * @return status code of request response
     * **/
    public int getStatusResponse(){
        return requestManager.getResponseStatusCode();
    }

    /** Method to get error response of an HTTP request
     * any params required
     * @return requestManager.getErrorResponse();
     * **/
    public String getErrorResponse() {
        return requestManager.getErrorResponse();
    }

    /** Method to round a value
     * @param value: value to round
     * @param decimalDigits: number of digits to round final value
     * @return value rounded with decimalDigits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     * **/
    public double roundValue(double value, int decimalDigits){
        return tradingTools.roundValue(value, decimalDigits);
    }

    /** Method to get percent between two values
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     * **/
    public double getTrendPercent(double startValue, double finalValue){
        return tradingTools.computeAssetPercent(startValue, finalValue);
    }

    /** Method to get percent between two values and round it
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value as double es. 8 or -8
     * @throws IllegalArgumentException if startValue or lastValue are negative
     * **/
    public double getTrendPercent(double startValue, double finalValue, int decimalDigits){
        return tradingTools.computeAssetPercent(startValue, finalValue, decimalDigits);
    }

    /** Method to format percent between two values and textualize it
     * @param percent: value to format
     * @return percent value formatted es. +8% or -8% as {@link String}
     * **/
    public String getTextTrendPercent(double percent){
        return tradingTools.textualizeAssetPercent(percent);
    }

    /** Method to get percent between two values and textualize it
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @return percent value es. +8% or -8% as {@link String}
     * **/
    public String getTextTrendPercent(double startValue, double finalValue){
        return tradingTools.textualizeAssetPercent(startValue, finalValue);
    }

    /** Method to get percent between two values and textualize it
     * @param startValue: first value to make compare
     * @param finalValue: last value to compare and get percent by first value
     * @param decimalDigits: number of digits to round final percent value
     * @return percent value es. +8% or -8% as {@link String}
     * **/
    public String getTextTrendPercent(double startValue, double finalValue, int decimalDigits){
        return tradingTools.textualizeAssetPercent(startValue, finalValue, decimalDigits);
    }

    /** Method get tradingTools object
     * any params required
     * @return {@link TradingTools} object
     * **/
    public TradingTools getTradingTools() {
        return tradingTools;
    }

    /** Method get jsonHelper object
     * any params required
     * @return {@link JsonHelper} object
     * **/
    public JsonHelper getJsonHelper() {
        return jsonHelper;
    }

}
