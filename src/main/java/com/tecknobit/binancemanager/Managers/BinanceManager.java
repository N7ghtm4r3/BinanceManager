package com.tecknobit.binancemanager.Managers;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Helpers.ConnectionManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.Constants.EndpointsList.SYSTEM_STATUS_URL;
import static com.tecknobit.binancemanager.Constants.EndpointsList.TIMESTAMP_URL;
import static com.tecknobit.binancemanager.Helpers.ConnectionManager.GET_METHOD;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;

public class BinanceManager {

    public static final ArrayList<String> BASE_ENDPOINTS = new ArrayList<>(asList("https://api.binance.com",
            "https://api1.binance.com","https://api2.binance.com","https://api3.binance.com"));
    protected ConnectionManager connectionManager;
    protected JSONObject jsonObject;
    protected JSONArray jsonArray;
    protected final String baseEndpoint;

    /** Constructor to init a Binance manager
     * @param #baseEndpoint base endpoint to work on
     * **/
    public BinanceManager(String baseEndpoint) throws SystemException, IOException {
        if(baseEndpoint != null)
            this.baseEndpoint = baseEndpoint;
        else
            this.baseEndpoint = getDefaultBaseEndpoint();
        this.connectionManager = new ConnectionManager();
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
        connectionManager.startConnection(baseEndpoint+SYSTEM_STATUS_URL,GET_METHOD);
        jsonObject = new JSONObject(connectionManager.getResponse());
        return jsonObject.getInt("status") == 0;
    }

    /** Request to get server timestamp or your current timestamp
     * any param required
     * **/
    public long getTimestamp(){
        try {
            connectionManager.startConnection(baseEndpoint+TIMESTAMP_URL,GET_METHOD);
            jsonObject = new JSONObject(connectionManager.getResponse());
            return jsonObject.getLong("serverTime");
        } catch (IOException e) {
            return currentTimeMillis();
        }
    }

}
