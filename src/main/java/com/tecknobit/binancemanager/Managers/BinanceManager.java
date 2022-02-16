package com.tecknobit.binancemanager.Managers;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Helpers.Request.RequestManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;

public class BinanceManager {

    public static final ArrayList<String> BASE_ENDPOINTS = new ArrayList<>(asList("https://api.binance.com",
            "https://api1.binance.com","https://api2.binance.com","https://api3.binance.com"));
    protected RequestManager requestManager;
    protected JSONObject jsonObject;
    protected JSONArray jsonArray;
    protected final String baseEndpoint;

    /** Constructor to init a Binance manager
     * @param #baseEndpoint base endpoint to work on
     * **/
    public BinanceManager(String baseEndpoint) throws SystemException, IOException {
        this.requestManager = new RequestManager();
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
     * @param #baseEndpoint endpoint to request status
     * **/
    public boolean isSystemAvailable(String baseEndpoint) throws IOException {
        requestManager.startConnection(baseEndpoint+ SYSTEM_STATUS_ENDPOINT,GET_METHOD);
        jsonObject = new JSONObject(requestManager.getResponse());
        return jsonObject.getInt("status") == 0;
    }

    /** Request to get server timestamp or your current timestamp
     * any param required
     * return es. 1566247363776
     * **/
    public long getTimestamp(){
        try {
            requestManager.startConnection(baseEndpoint+ TIMESTAMP_ENDPOINT,GET_METHOD);
            jsonObject = new JSONObject(requestManager.getResponse());
            return jsonObject.getLong("serverTime");
        } catch (IOException e) {
            return currentTimeMillis();
        }
    }

    /** Method to get timestamp for request
     * any params required
     * return "?timestamp=" + getTimestamp() return value
     * **/
    protected String getParamTimestamp(){
        return "?timestamp="+getTimestamp();
    }

    /** Method to execute and get response of a request
     * @param #endpoint: endpoint to request
     * @param #params: params HTTP for the request
     * @param #method: method HTTP for the request
     * @param #apiKey: apiKey of the account to perform request
     * return response of request formatted in Json
     * **/
    protected String getRequestResponse(String endpoint, String params, String method, String apiKey) throws IOException {
        requestManager.startConnection(baseEndpoint+endpoint+params,method,apiKey);
        return requestManager.getResponse();
    }

}
