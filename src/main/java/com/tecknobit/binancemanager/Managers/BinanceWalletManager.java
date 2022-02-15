package com.tecknobit.binancemanager.Managers;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Helpers.Records.Deposit;
import com.tecknobit.binancemanager.Helpers.Records.Withdraw;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;

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

    /** Request to get information of your coins available for deposit and withdraw
     * any params required
     * return string response
     * **/
    public String getAllCoins() throws Exception {
        String params = getParamsTimestamp();
        requestManager.startConnection(baseEndpoint+ ALL_COINS_ENDPOINT +params+getSignature(params),GET_METHOD,apiKey);
        return requestManager.getResponse();
    }

    /** Request to get information of your coins available for deposit and withdraw
     * any params required
     * return jsonArray response
     * **/
    public JSONArray getJSONAllCoins() throws Exception {
        return new JSONArray(getAllCoins());
    }

    /** Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES
     * return string response
     * **/
    public String getAccountSnapshot(String type) throws Exception {
        String params = getParamsTimestamp()+"&type="+type;
        requestManager.startConnection(baseEndpoint+ DAILY_ACCOUNT_SNAP_ENDPOINT +params+getSignature(params),GET_METHOD,apiKey);
        return requestManager.getResponse();
    }

    /** Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES
     * return jsonObject response
     * **/
    public JSONObject getJSONAccountSnapshot(String type) throws Exception {
        return new JSONObject(getAccountSnapshot(type));
    }

    public ArrayList<Deposit> getDepositHistory() throws Exception {
        ArrayList<Deposit> depositHistory = new ArrayList<>();
        String params = getParamsTimestamp();
        requestManager.startConnection(baseEndpoint+DEPOSIT_HISTORY_ENDPOINT+params+getSignature(params),
                GET_METHOD,apiKey);
        jsonArray = new JSONArray(requestManager.getResponse());
        for(int j=0; j < jsonArray.length(); j++){
            JSONObject deposit = jsonArray.getJSONObject(j);
            depositHistory.add(new Deposit(deposit.getDouble("amount"),
                   deposit.getString("coin"),
                   deposit.getString("network"),
                   deposit.getInt("status"),
                   deposit.getString("address"),
                   deposit.getString("addressTag"),
                   deposit.getString("txId"),
                   deposit.getLong("insertTime"),
                   deposit.getInt("transferType"),
                   deposit.getString("unlockConfirm"),
                   deposit.getString("confirmTimes")
            ));
        }
        return depositHistory;
    }

    public ArrayList<Withdraw> getWithdrawHistory() throws Exception {
        ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
        String params = getParamsTimestamp();
        requestManager.startConnection(baseEndpoint+WITHDRAW_HISTORY_ENDPOINT+params+getSignature(params),
                GET_METHOD,apiKey);
        jsonArray = new JSONArray(requestManager.getResponse());
        for(int j=0; j < jsonArray.length(); j++){
            JSONObject withdraw = jsonArray.getJSONObject(j);
            withdrawsHistory.add(new Withdraw(withdraw.getString("address"),
                    withdraw.getDouble("amount"),
                    withdraw.getString("applyTime"),
                    withdraw.getString("coin"),
                    withdraw.getString("id"),
                    withdraw.getString("withdrawOrderId"),
                    withdraw.getString("network"),
                    withdraw.getInt("transferType"),
                    withdraw.getInt("status"),
                    withdraw.getDouble("transactionFee"),
                    withdraw.getInt("confirmNo"),
                    withdraw.getString("info"),
                    withdraw.getString("txId")
            ));
        }
        return withdrawsHistory;
    }

    /** Method to get signature of request
     * @param #params: params of request to get signature
     * return es."&signature=c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    private String getSignature(String params) throws Exception {
        return "&signature="+ requestManager.getSignature(secretKey,params);
    }
    
}
