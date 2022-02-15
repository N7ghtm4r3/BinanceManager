package com.tecknobit.binancemanager.Managers;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Helpers.Records.Deposit;
import com.tecknobit.binancemanager.Helpers.Records.Withdraw;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.POST_METHOD;

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

    /** Request to get enable or disable fast withdraw
     * @param #enableFastWithdraw: true,false
     * true: endpoint will be ENABLE_FAST_WITHDRAW_ENDPOINT
     * false: endpoint will be DISABLE_FAST_WITHDRAW_ENDPOINT
     * return successful or not of operation
     * **/
    public boolean switchFastWithdraw(boolean enableFastWithdraw) throws Exception {
        String switchOperationEndpoint = DISABLE_FAST_WITHDRAW_ENDPOINT;
        String params = getParamsTimestamp();
        if(enableFastWithdraw)
            switchOperationEndpoint = ENABLE_FAST_WITHDRAW_ENDPOINT;
        requestManager.startConnection(baseEndpoint+switchOperationEndpoint+params+getSignature(params),
                POST_METHOD,apiKey);
        return requestManager.getResponse().equals("{}");
    }

    /** Request to submit withdraw
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * return id of transaction if operation is successful as string
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount) throws Exception {
        String params = getParamsTimestamp()+"&coin="+coinSymbol+"&address="+address+"&amount="+amount;
        return submitWithdraw(params);
    }

    /** Request to submit withdraw
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * return id of transaction if operation is successful as jsonObject
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol,address,amount));
    }

    /** Request to submit withdraw with extraParams
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType)
     * return id of transaction if operation is successful as string
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount,
                                 HashMap<String,Object> extraParams) throws Exception {
        String params = getParamsTimestamp()+"&coin="+coinSymbol+"&address="+address+"&amount="+amount;
        params = requestManager.assembleExtraParams(params,extraParams);
        return submitWithdraw(params);
    }

    /** Request to submit withdraw with extraParams
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType)
     * return id of transaction if operation is successful as jsonObject
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount,
                                         HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol,address,amount,extraParams));
    }

    /** Method to submit withdraw request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data
     * return id of transaction if operation is successful as string
     * **/
    private String submitWithdraw(String params) throws Exception {
        requestManager.startConnection(baseEndpoint+SUBMIT_WITHDRAW_ENDPOINT+params+getSignature(params)
                ,POST_METHOD,apiKey);
        return requestManager.getResponse();
    }

    /** Request to get deposit history
     * any params required
     * return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory() throws Exception {
        return getDepositHistory(getParamsTimestamp());
    }

    /** Request to get deposit history
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are coin,status,startTime,endTime,offset,limit)
     * return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamsTimestamp(),extraParams);
        return getDepositHistory(params);
    }

    /** Method to submit get deposit history request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
     * return list of deposits as ArrayList<Deposit>
     * **/
    private ArrayList<Deposit> getDepositHistory(String params) throws Exception {
        ArrayList<Deposit> depositHistory = new ArrayList<>();
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

    /** Request to get withdraw history
     * any params required
     * return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory() throws Exception {
        return getWithdrawHistory(getParamsTimestamp());
    }

    /** Request to get withdraw history
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are coin,withdrawOrderId,status,offset,limit,startTime,endTime)
     * return list of deposits as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamsTimestamp(),extraParams);
        return getWithdrawHistory(params);
    }

    /** Method to submit get withdraw history request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
     * return list of deposits as ArrayList<Withdraw>
     * **/
    private ArrayList<Withdraw> getWithdrawHistory(String params) throws Exception {
        ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
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
