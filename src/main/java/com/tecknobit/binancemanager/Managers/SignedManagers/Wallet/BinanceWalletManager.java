package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.API.APIPermission;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.API.APIStatus;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshot;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset.AssetDetail;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset.AssetDividend;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset.CoinInformation;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset.ConvertibleBNBAssets;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Deposit.Deposit;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Deposit.DepositAddress;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.DustLog;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.DustTransfer;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.UniversalTransfer;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.FundingWallet;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.TradeFee;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Withdraw;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.RequestManager.GET_METHOD;
import static com.tecknobit.binancemanager.Helpers.RequestManager.POST_METHOD;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset.CoinInformation.NetworkItem.getNetworkList;

/**
 *  The {@code BinanceWalletManager} class is useful to manage all Binance Wallet Endpoints
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#general-api-information">https://binance-docs.github.io/apidocs/spot/en/#general-api-information</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceWalletManager extends BinanceSignedManager {

    /** Constructor with an endpoint give by parameter
     * @param apiKey your api key
     * @param secretKey your secret key
     * @param baseEndpoint base endpoint choose from BASE_ENDPOINTS array
     * **/
    public BinanceWalletManager(String apiKey, String secretKey, String baseEndpoint) throws IOException, SystemException {
        super(baseEndpoint, apiKey, secretKey);
        if(!isSystemAvailable(baseEndpoint))
            throw new SystemException();
    }

    /** Constructor with an endpoint give by list auto research
     * @param apiKey your api key
     * @param secretKey your secret key
     * **/
    public BinanceWalletManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(null, apiKey, secretKey);
    }

    /** Request to get information of your coins available for deposit and withdraw
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return all coin information as String
     * **/
    public String getAllCoins() throws Exception {
        return sendSignedRequest(ALL_COINS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get information of your coins available for deposit and withdraw
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return all coin information as JsonArray
     * **/
    public JSONArray getJSONAllCoins() throws Exception {
        return new JSONArray(getAllCoins());
    }

    /** Request to get information of your coins available for deposit and withdraw
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return all coin information as ArrayList<CoinInformation>
     * **/
    public ArrayList<CoinInformation> getAllCoinsList() throws Exception {
        jsonArray = new JSONArray(getAllCoins());
        ArrayList<CoinInformation> coinInformationList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            coinInformationList.add(assembleCoinInformationObject(jsonArray.getJSONObject(j)));
        return coinInformationList;
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link String}
     * **/
    public String getSingleCoin(String coin) throws Exception {
        return getSingleCoin(coin, "coin").toString();
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link JSONObject}
     * **/
    public JSONObject getSingleCoinJSON(String coin) throws Exception {
        return getSingleCoin(coin, "coin");
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link CoinInformation} object
     * **/
    public CoinInformation getSingleCoinObject(String coin) throws Exception {
        return assembleCoinInformationObject(getSingleCoin(coin, "coin"));
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link String}
     * **/
    public String getSingleCoinByName(String name) throws Exception {
        return getSingleCoin(name, "name").toString();
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link JSONObject}
     * **/
    public JSONObject getSingleCoinByNameJSON(String name) throws Exception {
        return getSingleCoin(name, "name");
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link CoinInformation} object
     * **/
    public CoinInformation getSingleCoinByNameObject(String name) throws Exception {
        return assembleCoinInformationObject(getSingleCoin(name, "name"));
    }

    /** Method to get information of your coin available
     * @param keyValue: name or coin es Bitcoin or BTC
     * @param keySearch: name or coin param
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return coin corresponding to params as {@link JSONObject}
     * **/
    private JSONObject getSingleCoin(String keyValue, String keySearch) throws Exception {
        jsonArray = getJSONAllCoins();
        keyValue = keyValue.toUpperCase();
        for (int j=0; j < jsonArray.length(); j++) {
            JSONObject coin = jsonArray.getJSONObject(j);
            if(coin.getString(keySearch).toUpperCase().equals(keyValue))
                return coin;
        }
        throw new IllegalArgumentException("No coin found with this property");
    }

    /** Method to assemble an CoinInformation object
     * @param coin: obtained from Binance's request
     * @return a CoinInformation object with response data as {@link CoinInformation} object
     * **/
    private CoinInformation assembleCoinInformationObject(JSONObject coin){
        return new CoinInformation(coin.getString("coin"),
                coin.getBoolean("depositAllEnable"),
                coin.getDouble("free"),
                coin.getDouble("freeze"),
                coin.getDouble("ipoable"),
                coin.getDouble("ipoing"),
                coin.getBoolean("isLegalMoney"),
                coin.getDouble("locked"),
                coin.getString("name"),
                getNetworkList(new JsonHelper(coin).getJSONArray("networkList")),
                coin.getDouble("storage"),
                coin.getBoolean("trading"),
                coin.getBoolean("withdrawAllEnable"),
                coin.getDouble("withdrawing")
        );
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as String
     * **/
    public String getAccountSnapshot(String type) throws Exception {
        String params = getParamTimestamp()+"&type="+type;
        return sendSignedRequest(DAILY_ACCOUNT_SNAPSHOT_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as JsonObject
     * **/
    public JSONObject getJSONAccountSnapshot(String type) throws Exception {
        return new JSONObject(getAccountSnapshot(type));
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as AccountSnapshot object.
     * @implNote you need to cast return object in: AccountSnapshotSpot or AccountSnapshotMargin or AccountSnapshotFutures
     * **/
    public AccountSnapshot getObjectAccountSnapshot(String type) throws Exception {
        return getObjectAccountSnapshot(type,new JSONObject(getAccountSnapshot(type)));
    }

    /** Request to get your daily account snapshot
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as String
     * **/
    public String getAccountSnapshot(String type, HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleAdditionalParams(getParamTimestamp()+"&type="+type,extraParams);
        return sendSignedRequest(DAILY_ACCOUNT_SNAPSHOT_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get your daily account snapshot
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as JsonObject
     * **/
    public JSONObject getJSONAccountSnapshot(String type, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getAccountSnapshot(type, extraParams));
    }

    /** Request to get your daily account snapshot
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as AccountSnapshot object.
     * @implNote you need to cast return object in: AccountSnapshotSpot or AccountSnapshotMargin or AccountSnapshotFutures
     * in base of the type used
     * **/
    public AccountSnapshot getObjectAccountSnapshot(String type, HashMap<String,Object> extraParams) throws Exception {
        return getObjectAccountSnapshot(type, new JSONObject(getAccountSnapshot(type, extraParams)));
    }

    /** Method to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @param jsonObject: obtain by request to binance
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as AccountSnapshot object.
     * @implNote you need to cast return object in: AccountSnapshotSpot or AccountSnapshotMargin or AccountSnapshotFutures
     * in base of the type used
     * **/
    private AccountSnapshot getObjectAccountSnapshot(String type, JSONObject jsonObject){
        try {
            jsonArray = jsonObject.getJSONArray("snapshotVos");
        }catch (JSONException jsonException){
            jsonArray = null;
        }
        return new AccountSnapshot(
                jsonObject.getInt("code"),
                jsonObject.getString("msg"),
                type,
                jsonArray).getAccountSnapshot();
    }

    /** Request to get enable or disable fast withdraw
     * @param enableFastWithdraw: true,false
     * true: endpoint will be ENABLE_FAST_WITHDRAW_ENDPOINT
     * false: endpoint will be DISABLE_FAST_WITHDRAW_ENDPOINT
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-fast-withdraw-switch-user_data">https://binance-docs.github.io/apidocs/spot/en/#disable-fast-withdraw-switch-user_data</a>
     * @return successful or not of operation (true or false)
     * **/
    public boolean switchFastWithdraw(boolean enableFastWithdraw) throws Exception {
        String switchOperationEndpoint = DISABLE_FAST_WITHDRAW_ENDPOINT;
        if(enableFastWithdraw)
            switchOperationEndpoint = ENABLE_FAST_WITHDRAW_ENDPOINT;
        return sendSignedRequest(switchOperationEndpoint,getParamTimestamp(),POST_METHOD).equals("{}");
    }

    /** Request to submit withdraw
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as String
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount) throws Exception {
        String params = getParamTimestamp()+"&coin="+coinSymbol+"&address="+address+"&amount="+amount;
        return submitWithdraw(params);
    }

    /** Request to submit withdraw
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as JsonObject
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol,address,amount));
    }

    /** Request to submit withdraw with extraParams
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as String
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount,
                                 HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&coin="+coinSymbol+"&address="+address+"&amount="+amount;
        params = requestManager.assembleAdditionalParams(params,extraParams);
        return submitWithdraw(params);
    }

    /** Request to submit withdraw with extraParams
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as JsonObject
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount,
                                         HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol,address,amount,extraParams));
    }

    /** Method to submit withdraw request
     * @param params: params of request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as String
     * **/
    private String submitWithdraw(String params) throws Exception {
        return sendSignedRequest(SUBMIT_WITHDRAW_ENDPOINT, params, POST_METHOD);
    }

    /** Request to get deposit history
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
     * @return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory() throws Exception {
        return getDepositHistory(getParamTimestamp());
    }

    /** Request to get deposit history
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are coin,status,startTime,endTime,offset,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
     * @return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleAdditionalParams(getParamTimestamp(),extraParams);
        return getDepositHistory(params);
    }

    /** Method to submit get deposit history request
     * @param params: params of request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
     * @return list of deposits as ArrayList<Deposit>
     * **/
    private ArrayList<Deposit> getDepositHistory(String params) throws Exception {
        ArrayList<Deposit> depositHistory = new ArrayList<>();
        jsonArray = new JSONArray(sendSignedRequest(DEPOSIT_HISTORY_ENDPOINT,params,GET_METHOD));
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
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory() throws Exception {
        return getWithdrawHistory(getParamTimestamp());
    }

    /** Request to get withdraw history
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are coin,withdrawOrderId,status,offset,limit,startTime,endTime,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleAdditionalParams(getParamTimestamp(),extraParams);
        return getWithdrawHistory(params);
    }

    /** Method to submit get withdraw history request
     * @param params: params of request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    private ArrayList<Withdraw> getWithdrawHistory(String params) throws Exception {
        ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
        jsonArray = new JSONArray(sendSignedRequest(WITHDRAW_HISTORY_ENDPOINT,params,GET_METHOD));
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
     * @param coin: symbol of coin used to fetch address es. BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as DepositAddress object
     * **/
    public DepositAddress getDepositAddress(String coin) throws Exception {
        return getDepositAddressSender(getParamTimestamp()+"&coin="+coin);
    }

    /** Request to get deposit address
     * @param coin: symbol of coin used to fetch address es. BTC
     * @param network: network used to fetch address es. BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as DepositAddress object
     * **/
    public DepositAddress getDepositAddress(String coin,String network) throws Exception {
        return getDepositAddressSender(getParamTimestamp()+"&coin="+coin+"&network="+network);
    }

    /** Method to submit get deposit address request
     * @param params: params of request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as DepositAddress object
     * **/
    private DepositAddress getDepositAddressSender(String params) throws Exception {
        jsonObject = new JSONObject(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, params, GET_METHOD));
        return new DepositAddress(jsonObject.getString("address"),
                jsonObject.getString("coin"),
                jsonObject.getString("tag"),
                jsonObject.getString("url")
        );
    }

    /** Request to get account status
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data</a>
     * @return account status as String
     * **/
    public String getAccountStatus() throws Exception {
        return sendSignedRequest(ACCOUNT_STATUS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get account status
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data</a>
     * @return account status as JsonObject
     * **/
    public JSONObject getJSONAccountStatus() throws Exception {
        return new JSONObject(getAccountStatus());
    }

    /** Request to get API trading status
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
     * @return API trading status as String
     * **/
    public String getAPITradingStatus() throws Exception {
        return sendSignedRequest(API_TRADING_STATUS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get API trading status
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
     * @return API trading status as JsonObject
     * **/
    public JSONObject getJSONAPITradingStatus() throws Exception {
        return new JSONObject(getAPITradingStatus());
    }

    /** Request to get API trading status
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
     * @return API trading status as APIStatus object
     * **/
    public APIStatus getObjectAPITradingStatus() throws Exception {
        jsonObject = new JSONObject(getAPITradingStatus()).getJSONObject("data");
        HashMap<String,Integer> triggerCondition = new HashMap<>();
        JSONObject jsonObjectTrigger = jsonObject.getJSONObject("triggerCondition");
        ArrayList<String> keys = new ArrayList<>(jsonObjectTrigger.keySet());
        for (int j=0; j < jsonObjectTrigger.length(); j++){
            String key = keys.get(j);
            triggerCondition.put(key,jsonObjectTrigger.getInt(key));
        }
        return new APIStatus(jsonObject.getBoolean("isLocked"),
                jsonObject.getInt("plannedRecoverTime"),
                triggerCondition,
                jsonObject.getLong("updateTime")
        );
    }

    /** Request to get dust log information
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as String
     * **/
    public String getDustLog() throws Exception {
        return sendSignedRequest(DUST_LOG_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get dust log information
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as JsonObject
     * **/
    public JSONObject getJSONDustLog() throws Exception {
        return new JSONObject(getDustLog());
    }

    /** Request to get dust log information
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as DustLog object
     * **/
    public DustLog getObjectDustLog() throws Exception {
        return getObjectDustLog(new JSONObject(getDustLog()));
    }

    /** Request to get dust log information
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as String
     * **/
    public String getDustLog(HashMap<String,Object> extraParams) throws Exception {
        String params = requestManager.assembleAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(DUST_LOG_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get dust log information
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as JsonObject
     * **/
    public JSONObject getJSONDustLog(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getDustLog(extraParams));
    }

    /** Request to get dust log information
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as DustLog object
     * **/
    public DustLog getObjectDustLog(HashMap<String,Object> extraParams) throws Exception {
        return getObjectDustLog(new JSONObject(getDustLog(extraParams)));
    }

    /** Method to submit get dust log object
     * @param jsonObject: jsonObject assembled from request to Binance
     * @return dust log object
     * **/
    private DustLog getObjectDustLog(JSONObject jsonObject){
        int total = 0;
        ArrayList<DustLog.AssetDribblets> assetDribblets = new ArrayList<>();
        try {
            total = jsonObject.getInt("total");
            assetDribblets = new ArrayList<>();
            JSONArray jsonArrayDribblets = jsonObject.getJSONArray("assetDribblets");
            for (int j=0; j < jsonArrayDribblets.length(); j++){
                JSONObject jsonObjectDribblets = jsonArrayDribblets.getJSONObject(j);
                assetDribblets.add(new DustLog.AssetDribblets(jsonObjectDribblets.getLong("operateTime"),
                        jsonObjectDribblets.getDouble("jsonArrayDribblets"),
                        jsonObjectDribblets.getDouble("totalServiceChargeAmount"),
                        jsonObjectDribblets.getLong("transId"),
                        DustLog.AssetDribbletsDetails.getListDribbletsDetails(jsonObjectDribblets
                                .getJSONArray("userAssetDribbletDetails"))
                ));
            }
        }catch (JSONException ignored){}
        return new DustLog(total,assetDribblets);
    }

    /** Request to get convertible assets into BNB
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * @return convertible assets into BNB as String
     * **/
    public String getConvertibleBNBAssets() throws Exception {
        return sendSignedRequest(ASSET_CONVERTIBLE_BNB_ENDPOINT,getParamTimestamp(),POST_METHOD);
    }

    /** Request to get convertible assets into BNB
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * @return convertible assets into BNB as JsonObject
     * **/
    public JSONObject getJSONConvertibleBNBAssets() throws Exception {
        return new JSONObject(getConvertibleBNBAssets());
    }

    /** Request to get convertible assets into BNB
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * @return convertible assets into BNB as ConvertibleBNBAssets object
     * **/
    public ConvertibleBNBAssets getObjectConvertibleBNBAssets() throws Exception {
        jsonObject = new JSONObject(getConvertibleBNBAssets());
        ArrayList<ConvertibleBNBAssets.AssetDetails> assetsDetails = new ArrayList<>();
        double totalTransferBtc = jsonObject.getDouble("totalTransferBtc");
        double totalTransferBNB = jsonObject.getDouble("totalTransferBNB");
        double dribbletPercentage = jsonObject.getDouble("dribbletPercentage");
        jsonArray = jsonObject.getJSONArray("details");
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject assetDetails = jsonArray.getJSONObject(j);
            assetsDetails.add(new ConvertibleBNBAssets.AssetDetails(assetDetails.getString("asset"),
                    assetDetails.getString("assetFullName"),
                    assetDetails.getDouble("amountFree"),
                    assetDetails.getDouble("toBTC"),
                    assetDetails.getDouble("toBNB"),
                    assetDetails.getDouble("toBNBOffExchange"),
                    assetDetails.getDouble("exchange")
            ));
        }
        return new ConvertibleBNBAssets(assetsDetails,totalTransferBtc,totalTransferBNB,dribbletPercentage);
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data</a>
     * @return dust transfer as String
     * **/
    public String getDustTransfer(ArrayList<String> assets) throws Exception {
        StringBuilder params = new StringBuilder(getParamTimestamp());
        for (String asset : assets)
            params.append("&asset=").append(asset);
        return sendSignedRequest(DUST_TRANSFER_ENDPOINT,params.toString(),POST_METHOD);
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data</a>
     * @return dust transfer as JsonObject
     * **/
    public JSONObject getJSONDustTransfer(ArrayList<String> assets) throws Exception {
        return new JSONObject(getDustTransfer(assets));
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data</a>
     * @return dust transfer as DustTransfer object
     * **/
    public DustTransfer getObjectDustTransfer(ArrayList<String> assets) throws Exception {
        jsonObject = new JSONObject(getDustTransfer(assets));
        ArrayList<DustTransfer.TransferResult> transferResults = new ArrayList<>();
        double totalServiceCharge = 0,totalTransfered = 0;
        try {
            totalServiceCharge = jsonObject.getDouble("totalServiceCharge");
            totalTransfered = jsonObject.getDouble("totalTransfered");
            jsonArray = jsonObject.getJSONArray("transferResult");
            for(int j=0; j < jsonArray.length(); j++){
                JSONObject transferResult = jsonArray.getJSONObject(j);
                transferResults.add(new DustTransfer.TransferResult(transferResult.getDouble("amount"),
                        transferResult.getString("fromAsset"),
                        transferResult.getLong("operateTime"),
                        transferResult.getDouble("serviceChargeAmount"),
                        transferResult.getLong("tranId"),
                        transferResult.getDouble("transferedAmount")
                ));
            }
        }catch (JSONException ignored){}
        return new DustTransfer(totalServiceCharge,totalTransfered,transferResults);
    }

    /** Request to get asset dividend
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return asset dividend as String
     * **/
    public String getAssetDividend() throws Exception {
        return sendSignedRequest(ASSET_DIVIDEND_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get asset dividend
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return asset dividend as JsonObject
     * **/
    public JSONObject getJSONAssetDividend() throws Exception {
        return new JSONObject(getAssetDividend());
    }

    /** Request to get asset dividend
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return asset dividend as AssetDividend object
     * **/
    public AssetDividend getObjectAssetDividend() throws Exception {
        return getObjectAssetDividend(new JSONObject(getAssetDividend()));
    }

    /** Request to get asset dividend
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return  get asset dividend as String
     * **/
    public String getAssetDividend(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(ASSET_DIVIDEND_ENDPOINT,requestManager.assembleAdditionalParams(getParamTimestamp(),
                extraParams),GET_METHOD);
    }

    /** Request to get asset dividend
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return  get asset dividend as JsonObject
     * **/
    public JSONObject getJSONAssetDividend(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getAssetDividend(extraParams));
    }

    /** Request to get asset dividend
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return  get asset dividend as AssetDividend object
     * **/
    public AssetDividend getObjectAssetDividend(HashMap<String,Object> extraParams) throws Exception {
        return getObjectAssetDividend(new JSONObject(getAssetDividend(extraParams)));
    }

    /** Method to submit get asset dividend
     * @param jsonObject: jsonObject assembled from request to Binance
     * @return asset dividend object
     * **/
    private AssetDividend getObjectAssetDividend(JSONObject jsonObject){
        ArrayList<AssetDividend.AssetDividendDetails> assetDividendDetails = new ArrayList<>();
        int total = 0;
        try {
            total = jsonObject.getInt("total");
            jsonArray = jsonObject.getJSONArray("rows");
            for (int j=0; j < jsonArray.length(); j++){
                JSONObject jsonObjectAsset = jsonArray.getJSONObject(j);
                assetDividendDetails.add(new AssetDividend.AssetDividendDetails(jsonObjectAsset.getLong("id"),
                        jsonObjectAsset.getDouble("amount"),
                        jsonObjectAsset.getString("asset"),
                        jsonObjectAsset.getLong("divTime"),
                        jsonObjectAsset.getString("enInfo"),
                        jsonObjectAsset.getLong("tranId")
                ));
            }
        }catch (JSONException ignored){}
        return new AssetDividend(total,assetDividendDetails);
    }

    /** Request to get asset detail
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as String
     * **/
    public String getAssetDetail() throws Exception {
        return sendSignedRequest(ASSET_DETAIL_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get asset detail
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as JsonObject
     * **/
    public JSONObject getJSONAssetDetail() throws Exception {
        return new JSONObject(getAssetDetail());
    }

    /** Request to get asset detail
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail list as ArrayList<AssetDetail>
     * **/
    public ArrayList<AssetDetail> getAssetDetailList() throws Exception {
        jsonObject = new JSONObject(getAssetDetail());
        ArrayList<AssetDetail> assetDetailList = new ArrayList<>();
        for (String key : new ArrayList<>(jsonObject.keySet())){
            JSONObject asset = jsonObject.getJSONObject(key);
            assetDetailList.add(new AssetDetail(key,
                    asset.getDouble("minWithdrawAmount"),
                    asset.getBoolean("depositStatus"),
                    asset.getDouble("withdrawFee"),
                    asset.getBoolean("withdrawStatus"),
                    getDepositTip(asset)
            ));
        }
        return assetDetailList;
    }

    /** Request to get asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as String
     * **/
    public String getAssetDetail(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(ASSET_DETAIL_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as JsonObject
     * **/
    public JSONObject getJSONAssetDetail(String asset) throws Exception {
        return new JSONObject(getAssetDetail(asset));
    }

    /** Request to get asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as AssetDetail object
     * **/
    public AssetDetail getObjectAssetDetail(String asset) throws Exception {
        jsonObject = new JSONObject(getAssetDetail(asset)).getJSONObject(asset);
        return new AssetDetail(asset,
                jsonObject.getDouble("minWithdrawAmount"),
                jsonObject.getBoolean("depositStatus"),
                jsonObject.getDouble("withdrawFee"),
                jsonObject.getBoolean("withdrawStatus"),
                getDepositTip(jsonObject)
        );
    }

    /** Method to get depositTip
     * @param jsonObject: jsonObject assembled from request to Binance
     * @return depositTip as String
     * **/
    private String getDepositTip(JSONObject jsonObject){
        try {
            return jsonObject.getString("depositTip");
        }catch (JSONException jsonException){
            return "No tip";
        }
    }

    /** Request to get asset trade fee
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
     * @return asset trade fee as String
     * **/
    public String getTradeFee() throws Exception {
        return sendSignedRequest(TRADE_FEE_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get asset trade fee
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
     * @return asset trade fee as JsonArray
     * **/
    public JSONArray getJSONTradeFee() throws Exception {
        return new JSONArray(getTradeFee());
    }

    /** Request to get asset trade fee
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee list as ArrayList<TradeFee>
     * **/
    public ArrayList<TradeFee> getTradeFeeList() throws Exception {
        ArrayList<TradeFee> tradeFeesList = new ArrayList<>();
        jsonArray = new JSONArray(getTradeFee());
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject fee = jsonArray.getJSONObject(j);
            tradeFeesList.add(new TradeFee(fee.getString("symbol"),
                    fee.getDouble("makerCommission"),
                    fee.getDouble("takerCommission")
            ));
        }
        return tradeFeesList;
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee as String
     * **/
    public String getTradeFee(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(TRADE_FEE_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee as JsonArray
     * **/
    public JSONArray getJSONTradeFee(String symbol) throws Exception {
        return new JSONArray(getTradeFee(symbol));
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee as TradeFee object
     * **/
    public TradeFee getObjectTradeFee(String symbol) throws Exception {
        jsonObject = new JSONArray(getTradeFee(symbol)).getJSONObject(0);
        return new TradeFee(jsonObject.getString("symbol"),
                jsonObject.getDouble("makerCommission"),
                jsonObject.getDouble("takerCommission")
        );
    }

    /** Request to get universal transfer
     * @param type: type for the request es. MAIN_UMFUTURE
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as String
     * **/
    public String getUniversalTransfer(String type,String asset,double amount) throws Exception {
        String params = getParamTimestamp()+"&type="+type+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to get universal transfer
     * @param type: type for the request es. MAIN_UMFUTURE
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as JsonObject
     * **/
    public JSONObject getJSONUniversalTransfer(String type,String asset,double amount) throws Exception {
        return new JSONObject(getUniversalTransfer(type,asset,amount));
    }

    /** Request to get universal transfer
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromSymbol,toSymbol,recvWindow)
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as String
     * **/
    public String getUniversalTransfer(String type,String asset,double amount, HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT,requestManager.assembleAdditionalParams(getParamTimestamp(),extraParams),
                POST_METHOD);
    }

    /** Request to get universal transfer
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromSymbol,toSymbol,recvWindow)
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as JsonObject
     * **/
    public JSONObject getJSONUniversalTransfer(String type, String asset, double amount,
                                               HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getUniversalTransfer(type, asset, amount, extraParams));
    }

    /** Request to get universal transfer history
     * @param type: type to fetch history
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    public ArrayList<UniversalTransfer> getUniversalTransferHistory(String type) throws Exception {
        String params = getParamTimestamp()+"&type="+type;
        return getUniversalTransferHistorySender(params);
    }

    /** Request to get universal transfer history
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,current,size,fromSymbol,toSymbol,recvWindow)
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    public ArrayList<UniversalTransfer> getUniversalTransferHistory(String type, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&type="+type;
        params = requestManager.assembleAdditionalParams(params,extraParams);
        return getUniversalTransferHistorySender(params);
    }

    /** Method to submit get universal transfer history
     * @param params: params of request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    private ArrayList<UniversalTransfer> getUniversalTransferHistorySender(String params) throws Exception{
        ArrayList<UniversalTransfer> universalTransfersHistory = new ArrayList<>();
        try {
            jsonArray = new JSONObject(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT,params,GET_METHOD)).getJSONArray("rows");
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
        return universalTransfersHistory;
    }

    /** Request to get funding wallet
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet() throws Exception {
        return getFundingWallet(getParamTimestamp());
    }

    /** Request to get universal transfer history
     * @param extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are asset,needBtcValuation,recvWindow)
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet(HashMap<String,Object> extraParams) throws Exception {
        return getFundingWallet(requestManager.assembleAdditionalParams(getParamTimestamp(),extraParams));
    }

    /** Method to submit get funding wallet request
     * @param params: params of request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    private ArrayList<FundingWallet> getFundingWallet(String params) throws Exception {
        ArrayList<FundingWallet> fundingWalletsList = new ArrayList<>();
        jsonArray = new JSONArray(sendSignedRequest(FUNDING_WALLET_ENDPOINT, params, POST_METHOD));
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
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
     * @return API key permission as String
     * **/
    public String getAPIKeyPermission() throws Exception {
        return sendSignedRequest(API_KEY_PERMISSION_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get API key permission
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
     * @return API key permission as JsonObject
     * **/
    public JSONObject getJSONAPIKeyPermission() throws Exception {
        return new JSONObject(getAPIKeyPermission());
    }

    /** Request to get API key permission
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
     * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
     * @return API key permission as APIPermission object
     * **/
    public APIPermission getObjectAPIKeyPermission() throws Exception {
        jsonObject = new JSONObject(getAPIKeyPermission());
        long tradingAuthorityExpirationTime;
        try {
            tradingAuthorityExpirationTime = jsonObject.getLong("tradingAuthorityExpirationTime");
        }catch (JSONException e){
            tradingAuthorityExpirationTime = -1;
        }
        return new APIPermission(jsonObject.getBoolean("ipRestrict"),
                jsonObject.getLong("createTime"),
                jsonObject.getBoolean("enableWithdrawals"),
                jsonObject.getBoolean("enableInternalTransfer"),
                jsonObject.getBoolean("permitsUniversalTransfer"),
                jsonObject.getBoolean("enableVanillaOptions"),
                jsonObject.getBoolean("enableReading"),
                jsonObject.getBoolean("enableFutures"),
                jsonObject.getBoolean("enableMargin"),
                jsonObject.getBoolean("enableSpotAndMarginTrading"),
                tradingAuthorityExpirationTime
        );
    }

}
