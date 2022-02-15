package com.tecknobit.binancemanager;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Helpers.ConnectionManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.ConnectionManager.GET_METHOD;
import static java.lang.System.*;
import static java.util.Arrays.asList;

public class BinanceManager{

    //https://binance-docs.github.io/apidocs/spot/en/#general-api-information
    public static final ArrayList<String> BASE_ENDPOINTS = new ArrayList<>(asList("https://api.binance.com",
            "https://api1.binance.com","https://api2.binance.com","https://api3.binance.com"));
    public static final String SPOT = "SPOT";
    public static final String MARGIN = "MARGIN";
    public static final String FUTURES = "FUTURES";
    private final ConnectionManager connectionManager;
    private final String apiKey;
    private final String secretKey;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String baseEndpoint;

    public BinanceManager(String apiKey, String secretKey, String baseEndpoint) throws IOException, SystemException {
        connectionManager = new ConnectionManager();
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.baseEndpoint = baseEndpoint;
        if(!isSystemAvailable(baseEndpoint))
            throw new SystemException();
    }

    public BinanceManager(String apiKey, String secretKey) throws SystemException, IOException {
        connectionManager = new ConnectionManager();
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.baseEndpoint = getDefaultBaseEndpoint();
    }

    private String getDefaultBaseEndpoint() throws SystemException, IOException {
        for (String string : BASE_ENDPOINTS)
            if(isSystemAvailable(string))
                return string;
        throw new SystemException();
    }

    private boolean isSystemAvailable(String baseEndpoint) throws IOException {
        connectionManager.startConnection(baseEndpoint+SYSTEM_STATUS_URL,GET_METHOD);
        jsonObject = new JSONObject(connectionManager.getResponse());
        return jsonObject.getInt("status") == 0;
    }
    
    /**Request to get server timestamp or your current timestamp
     * any param required **/
    public long getTimestamp(){
        try {
            connectionManager.startConnection(baseEndpoint+TIMESTAMP_URL,GET_METHOD);
            jsonObject = new JSONObject(connectionManager.getResponse());
            return jsonObject.getLong("serverTime");
        } catch (IOException e) {
            return currentTimeMillis();
        }
    }

    /**Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES **/
    public String getAccountSnapshot(String type) throws Exception {
        String params = "?timestamp="+getTimestamp()+"&type="+type;
        connectionManager.startConnection(baseEndpoint+DAILY_ACCOUNT_SNAP_URL+params+"&signature="
                +connectionManager.getSignature(secretKey,params),GET_METHOD,apiKey);
        return connectionManager.getResponse();
    }

}
