package com.tecknobit.binancemanager.Managers;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.binancemanager.Constants.EndpointsList.DAILY_ACCOUNT_SNAP_URL;
import static com.tecknobit.binancemanager.Helpers.ConnectionManager.GET_METHOD;

public class BinanceWalletManager extends BinanceManager{

    //https://binance-docs.github.io/apidocs/spot/en/#general-api-information
    public static final String SPOT = "SPOT";
    public static final String MARGIN = "MARGIN";
    public static final String FUTURES = "FUTURES";
    private final String apiKey;
    private final String secretKey;

    /** Constructor with an endpoint give by parameter
     * @param #apiKey your api key
     * @param #secretKey your secret key
     * @param #baseEndpoint base endpoint choose from BASE_ENDPOINTS array
     * **/
    public BinanceWalletManager(String apiKey, String secretKey, String baseEndpoint) throws IOException, SystemException {
        super(baseEndpoint);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        if(!isSystemAvailable(baseEndpoint))
            throw new SystemException();
    }

    /** Constructor with an endpoint give by list research
     * @param #apiKey your api key
     * @param #secretKey your secret key
     * **/
    public BinanceWalletManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(null);
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    /** Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES
     * return string response
     * **/
    public String getAccountSnapshot(String type) throws Exception {
        String params = "?timestamp="+getTimestamp()+"&type="+type;
        connectionManager.startConnection(baseEndpoint+DAILY_ACCOUNT_SNAP_URL+params+"&signature="
                +connectionManager.getSignature(secretKey,params),GET_METHOD,apiKey);
        return connectionManager.getResponse();
    }

    /** Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES
     * return jsonobject response
     * **/
    public JSONObject getJSONAccountSnapshot(String type) throws Exception {
        return new JSONObject(getAccountSnapshot(type));
    }
    
}
