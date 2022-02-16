package com.tecknobit.binancemanager.Managers.Wallet;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.Wallet.Records.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.POST_METHOD;

public class BinanceWalletManager extends BinanceManager {

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
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data
     * return all coin information as String
     * **/
    public String getAllCoins() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(ALL_COINS_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get information of your coins available for deposit and withdraw
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data
     * return all coin information as JsonArray
     * **/
    public JSONArray getJSONAllCoins() throws Exception {
        return new JSONArray(getAllCoins());
    }

    /** Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
     * return account snapshot as String
     * **/
    public String getAccountSnapshot(String type) throws Exception {
        String params = getParamTimestamp()+"&type="+type;
        return getRequestResponse(DAILY_ACCOUNT_SNAP_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get your daily account snapshot
     * @param #type: SPOT,MARGIN OR FUTURES
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
     * return account snapshot as JsonObject
     * **/
    public JSONObject getJSONAccountSnapshot(String type) throws Exception {
        return new JSONObject(getAccountSnapshot(type));
    }

    /** Request to get enable or disable fast withdraw
     * @param #enableFastWithdraw: true,false
     * true: endpoint will be ENABLE_FAST_WITHDRAW_ENDPOINT
     * false: endpoint will be DISABLE_FAST_WITHDRAW_ENDPOINT
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#disable-fast-withdraw-switch-user_data
     * return successful or not of operation (true or false)
     * **/
    public boolean switchFastWithdraw(boolean enableFastWithdraw) throws Exception {
        String switchOperationEndpoint = DISABLE_FAST_WITHDRAW_ENDPOINT;
        String params = getParamTimestamp();
        if(enableFastWithdraw)
            switchOperationEndpoint = ENABLE_FAST_WITHDRAW_ENDPOINT;
        return getRequestResponse(switchOperationEndpoint,params+getSignature(params),POST_METHOD,apiKey).equals("{}");
    }

    /** Request to submit withdraw
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data
     * return id of transaction if operation is successful as String
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount) throws Exception {
        String params = getParamTimestamp()+"&coin="+coinSymbol+"&address="+address+"&amount="+amount;
        return submitWithdraw(params);
    }

    /** Request to submit withdraw
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data
     * return id of transaction if operation is successful as JsonObject
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
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data
     * return id of transaction if operation is successful as String
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount,
                                 HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&coin="+coinSymbol+"&address="+address+"&amount="+amount;
        params = requestManager.assembleExtraParams(params,extraParams);
        return submitWithdraw(params);
    }

    /** Request to submit withdraw with extraParams
     * @param #coinSymbol: symbol of coin used es. BTC
     * @param #address: address used
     * @param #amount: amount to withdraw
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data
     * return id of transaction if operation is successful as JsonObject
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount,
                                         HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol,address,amount,extraParams));
    }

    /** Method to submit withdraw request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data
     * return id of transaction if operation is successful as String
     * **/
    private String submitWithdraw(String params) throws Exception {
        return getRequestResponse(SUBMIT_WITHDRAW_ENDPOINT,params+getSignature(params),POST_METHOD,apiKey);
    }

    /** Request to get deposit history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
     * return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory() throws Exception {
        return getDepositHistory(getParamTimestamp());
    }

    /** Request to get deposit history
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are coin,status,startTime,endTime,offset,limit)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
     * return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return getDepositHistory(params);
    }

    /** Method to submit get deposit history request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
     * return list of deposits as ArrayList<Deposit>
     * **/
    private ArrayList<Deposit> getDepositHistory(String params) throws Exception {
        ArrayList<Deposit> depositHistory = new ArrayList<>();
        jsonArray = new JSONArray(getRequestResponse(DEPOSIT_HISTORY_ENDPOINT,
                params+getSignature(params),GET_METHOD,apiKey));
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
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
     * return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory() throws Exception {
        return getWithdrawHistory(getParamTimestamp());
    }

    /** Request to get withdraw history
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are coin,withdrawOrderId,status,offset,limit,startTime,endTime)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
     * return list of deposits as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return getWithdrawHistory(params);
    }

    /** Method to submit get withdraw history request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
     * return list of deposits as ArrayList<Withdraw>
     * **/
    private ArrayList<Withdraw> getWithdrawHistory(String params) throws Exception {
        ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
        jsonArray = new JSONArray(getRequestResponse(WITHDRAW_HISTORY_ENDPOINT,params+getSignature(params),
                GET_METHOD,apiKey));
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

    /** Request to get deposit address
     * @param #coin: symbol of coin used to fetch address es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data
     * return deposit address information as DepositAddress object
     * **/
    public DepositAddress getDepositAddress(String coin) throws Exception {
        return getDepositAddressSender(getParamTimestamp()+"&coin="+coin);
    }

    /** Request to get deposit address
     * @param #coin: symbol of coin used to fetch address es. BTC
     * @param #network: network used to fetch address es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data
     * return deposit address information as DepositAddress object
     * **/
    public DepositAddress getDepositAddress(String coin,String network) throws Exception {
        return getDepositAddressSender(getParamTimestamp()+"&coin="+coin+"&network="+network);
    }

    /** Method to submit get deposit address request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data
     * return deposit address information as DepositAddress object
     * **/
    private DepositAddress getDepositAddressSender(String params) throws Exception {
        jsonObject = new JSONObject(getRequestResponse(DEPOSIT_ADDRESS_ENDPOINT,params+getSignature(params),
                GET_METHOD,apiKey));
        return new DepositAddress(jsonObject.getString("address"),
                jsonObject.getString("coin"),
                jsonObject.getString("tag"),
                jsonObject.getString("url")
        );
    }

    /** Request to get account status
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data
     * return account status as String
     * **/
    public String getAccountStatus() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(ACCOUNT_STATUS_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get account status
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data
     * return account status as JsonObject
     * **/
    public JSONObject getJSONAccountStatus() throws Exception {
        return new JSONObject(getAccountStatus());
    }

    /** Request to get API trading status
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data
     * return API trading status as String
     * **/
    public String getAPITradingStatus() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(API_TRADING_STATUS_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get API trading status
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data
     * return API trading status as JsonObject
     * **/
    public JSONObject getJSONAPITradingStatus() throws Exception {
        return new JSONObject(getAPITradingStatus());
    }

    /** Request to get dust log information
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data
     * return dust log information as String
     * **/
    public String getDustLog() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(DUST_LOG_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get dust log information
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data
     * return dust log information as String
     * **/
    public String getDustLog(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return getRequestResponse(DUST_LOG_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get dust log information
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data
     * return dust log information as JsonObject
     * **/
    public JSONObject getJSONDustLog() throws Exception {
        return new JSONObject(getDustLog());
    }

    /** Request to get dust log information
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data
     * return dust log information as JsonObject
     * **/
    public JSONObject getJSONDustLog(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getDustLog(extraParams));
    }

    /** Request to get convertible assets into BNB
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data
     * return convertible assets into BNB as String
     * **/
    public String getConvertibleBNBAssets() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(ASSET_CONVERTIBLE_BNB_ENDPOINT,params+getSignature(params),POST_METHOD,apiKey);
    }

    /** Request to get convertible assets into BNB
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data
     * return convertible assets into BNB as JsonObject
     * **/
    public JSONObject getJSONConvertibleBNBAssets() throws Exception {
        return new JSONObject(getConvertibleBNBAssets());
    }

    /** Request to get dust transfer
     * @param #assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data
     * return dust transfer as String
     * **/
    public String getDustTransfer(ArrayList<String> assets) throws Exception {
        StringBuilder params = new StringBuilder(getParamTimestamp());
        for (String asset : assets)
            params.append("&asset=").append(asset);
        return getRequestResponse(DUST_TRANSFER_ENDPOINT,params+getSignature(params.toString()),POST_METHOD,apiKey);
    }

    /** Request to get dust transfer
     * @param #assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data
     * return dust transfer as JsonObject
     * **/
    public JSONObject getJSONDustTransfer(ArrayList<String> assets) throws Exception {
        return new JSONObject(getDustTransfer(assets));
    }

    /** Request to get dividend asset
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data
     * return dividend asset as String
     * **/
    public String getAssetDividend() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(ASSET_DIVIDEND_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get dividend asset
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data
     * return dividend asset as JsonObject
     * **/
    public JSONObject getJSONAssetDividend() throws Exception {
        return new JSONObject(getAssetDividend());
    }

    /** Request to get dividend asset
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,startTime,endTime,limit)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data
     * return  get dividend asset as String
     * **/
    public String getAssetDividend(HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(ASSET_DIVIDEND_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get dividend asset
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,startTime,endTime,limit)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data
     * return  get dividend asset as JsonObject
     * **/
    public JSONObject getJSONAssetDividend(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getAssetDividend(extraParams));
    }

    /** Request to get universal transfer
     * @param #type: type for the request es. MAIN_UMFUTURE
     * @param #asset: asset for the request es. BTC
     * @param #amount: amount for the request
     * @implNote in case #type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case #type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer as String
     * **/
    public String getUniversalTransfer(String type,String asset,double amount) throws Exception {
        String params = getParamTimestamp()+"&type="+type+"&asset="+asset+"&amount="+amount;
        return getRequestResponse(UNIVERSAL_TRANSFER_ENDPOINT,params+getSignature(params),POST_METHOD,apiKey);
    }

    /** Request to get universal transfer
     * @param #type: type for the request es. MAIN_UMFUTURE
     * @param #asset: asset for the request es. BTC
     * @param #amount: amount for the request
     * @implNote in case #type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case #type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer as JsonObject
     * **/
    public JSONObject getJSONUniversalTransfer(String type,String asset,double amount) throws Exception {
        return new JSONObject(getUniversalTransfer(type,asset,amount));
    }

    /** Request to get universal transfer
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromSymbol,toSymbol)
     * @implNote in case #type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case #type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer as String
     * **/
    public String getUniversalTransfer(String type,String asset,double amount, HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return getRequestResponse(UNIVERSAL_TRANSFER_ENDPOINT,params+getSignature(params),POST_METHOD,apiKey);
    }

    /** Request to get universal transfer
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromSymbol,toSymbol)
     * @implNote in case #type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case #type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer as JsonObject
     * **/
    public JSONObject getJSONUniversalTransfer(String type,String asset,double amount, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getUniversalTransfer(type, asset, amount, extraParams));
    }

    /** Request to get universal transfer history
     * @param #type: type to fetch history
     * @implNote in case #type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case #type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    public ArrayList<UniversalTransfer> getUniversalTransferHistory(String type){
        String params = getParamTimestamp()+"&type="+type;
        return getUniversalTransferHistorySender(params);
    }

    /** Request to get universal transfer history
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,current,size,fromSymbol,toSymbol)
     * @implNote in case #type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case #type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    public ArrayList<UniversalTransfer> getUniversalTransferHistory(String type, HashMap<String,Object> extraParams){
        String params = getParamTimestamp()+"&type="+type;
        params = requestManager.assembleExtraParams(params,extraParams);
        return getUniversalTransferHistorySender(params);
    }

    /** Method to submit get universal transfer history
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
     * return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    private ArrayList<UniversalTransfer> getUniversalTransferHistorySender(String params){
        ArrayList<UniversalTransfer> universalTransfersHistory = new ArrayList<>();
        try {
            jsonArray = new JSONObject(getRequestResponse(UNIVERSAL_TRANSFER_ENDPOINT,params+getSignature(params),
                    GET_METHOD,apiKey)).getJSONArray("rows");
            for (int j=0; j < jsonArray.length(); j++){
                JSONObject universalTranHistory = jsonArray.getJSONObject(j);
                universalTransfersHistory.add(new UniversalTransfer(universalTranHistory.getString("asset"),
                        universalTranHistory.getDouble("amount"),
                        universalTranHistory.getString("type"),
                        universalTranHistory.getString("status"),
                        universalTranHistory.getLong("tranId"),
                        universalTranHistory.getLong("timestamp")
                ));
            }
        }catch (JSONException ignored){}
        finally {
            return universalTransfersHistory;
        }
    }

    /** Request to get funding wallet
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data
     * return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet() throws Exception {
        return getFundingWallet(getParamTimestamp());
    }

    /** Request to get universal transfer history
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,needBtcValuation)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data
     * return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet(HashMap<String,Object> extraParams) throws Exception {
        return getFundingWallet(requestManager.assembleExtraParams(getParamTimestamp(),extraParams));
    }

    /** Method to submit get funding wallet request
     * @param #params: params of request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data
     * return funding wallet as ArrayList<FundingWallet>
     * **/
    private ArrayList<FundingWallet> getFundingWallet(String params) throws Exception {
        ArrayList<FundingWallet> fundingWalletsList = new ArrayList<>();
        jsonArray = new JSONArray(getRequestResponse(FUNDING_WALLET_ENDPOINT,params+getSignature(params),
                POST_METHOD,apiKey));
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject fundingWallet = jsonArray.getJSONObject(j);
            fundingWalletsList.add(new FundingWallet(fundingWallet.getString("asset"),
                    fundingWallet.getDouble("free"),
                    fundingWallet.getDouble("locked"),
                    fundingWallet.getDouble("freeze"),
                    fundingWallet.getInt("withdrawing"),
                    fundingWallet.getDouble("btcValuation")
            ));
        }
        return fundingWalletsList;
    }

    /** Request to get API key permission
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data
     * return API key permission as String
     * **/
    public String getAPIKeyPermission() throws Exception {
        String params = getParamTimestamp();
        return getRequestResponse(API_KEY_PERMISSION_ENDPOINT,params+getSignature(params),GET_METHOD,apiKey);
    }

    /** Request to get API key permission
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data
     * return API key permission as JsonObject
     * **/
    public JSONObject getJSONAPIKeyPermission() throws Exception {
        return new JSONObject(getAPIKeyPermission());
    }

    /** Method to get signature of request
     * @param #params: params of request to get signature
     * return es."&signature=c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    private String getSignature(String params) throws Exception {
        return "&signature="+ requestManager.getSignature(secretKey,params);
    }

    //Account API Trading Status (USER_DATA)
    //DustLog(USER_DATA)
    //Get Assets That Can Be Converted Into BNB
    //Dust Transfer (USER_DATA)
    //Asset Dividend Record
    //Get API Key Permission
    
}
