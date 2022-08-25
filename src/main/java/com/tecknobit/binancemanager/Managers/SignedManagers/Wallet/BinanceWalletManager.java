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
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.DustItem;
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

import static com.tecknobit.apimanager.Manager.APIRequest.GET_METHOD;
import static com.tecknobit.apimanager.Manager.APIRequest.POST_METHOD;
import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset.CoinInformation.NetworkItem.getNetworkList;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.DustItem.getListDribbletsDetails;

/**
 *  The {@code BinanceWalletManager} class is useful to manage all Binance Wallet Endpoints
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#general-api-information">
 *      https://binance-docs.github.io/apidocs/spot/en/#general-api-information</a>
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

    /** Request to get information of your coins available for deposit and withdraw <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return all coin information as {@link String}
     * **/
    public String getAllCoins() throws Exception {
        return sendSignedRequest(ALL_COINS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get information of your coins available for deposit and withdraw <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return all coin information as {@link JSONArray}
     * **/
    public JSONArray getJSONAllCoins() throws Exception {
        return new JSONArray(getAllCoins());
    }

    /** Request to get information of your coins available for deposit and withdraw <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return all coin information as ArrayList<CoinInformation>
     * **/
    public ArrayList<CoinInformation> getAllCoinsList() throws Exception {
        JSONArray coinsInformation = new JSONArray(getAllCoins());
        ArrayList<CoinInformation> coinInformationList = new ArrayList<>();
        for (int j = 0; j < coinsInformation.length(); j++)
            coinInformationList.add(assembleCoinInformationObject(coinsInformation.getJSONObject(j)));
        return coinInformationList;
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link String}
     * **/
    public String getSingleCoin(String coin) throws Exception {
        return getSingleCoin(coin, "coin").toString();
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link JSONObject}
     * **/
    public JSONObject getSingleCoinJSON(String coin) throws Exception {
        return getSingleCoin(coin, "coin");
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link CoinInformation} object
     * **/
    public CoinInformation getSingleCoinObject(String coin) throws Exception {
        return assembleCoinInformationObject(getSingleCoin(coin, "coin"));
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link String}
     * **/
    public String getSingleCoinByName(String name) throws Exception {
        return getSingleCoin(name, "name").toString();
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link JSONObject}
     * **/
    public JSONObject getSingleCoinByNameJSON(String name) throws Exception {
        return getSingleCoin(name, "name");
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return single coin information as {@link CoinInformation} object
     * **/
    public CoinInformation getSingleCoinByNameObject(String name) throws Exception {
        return assembleCoinInformationObject(getSingleCoin(name, "name"));
    }

    /** Method to get information of your coin available
     * @param keyValue: name or coin es Bitcoin or BTC
     * @param keySearch: name or coin param
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * @return coin corresponding to params as {@link JSONObject}
     * **/
    private JSONObject getSingleCoin(String keyValue, String keySearch) throws Exception {
        JSONArray coins = getJSONAllCoins();
        keyValue = keyValue.toUpperCase();
        for (int j = 0; j < coins.length(); j++) {
            JSONObject coin = coins.getJSONObject(j);
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as {@link String}
     * **/
    public String getAccountSnapshot(String type) throws Exception {
        String params = getParamTimestamp() + "&type=" + type;
        return sendSignedRequest(DAILY_ACCOUNT_SNAPSHOT_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as {@link JSONObject}
     * **/
    public JSONObject getJSONAccountSnapshot(String type) throws Exception {
        return new JSONObject(getAccountSnapshot(type));
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as AccountSnapshot - {type} object.
     * **/
    public <T extends AccountSnapshot> T getObjectAccountSnapshot(String type) throws Exception {
        return getObjectAccountSnapshot(type,new JSONObject(getAccountSnapshot(type)));
    }

    /** Request to get your daily account snapshot
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as {@link String}
     * **/
    public String getAccountSnapshot(String type, Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp() + "&type=" + type,
                extraParams);
        return sendSignedRequest(DAILY_ACCOUNT_SNAPSHOT_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get your daily account snapshot
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as {@link JSONObject}
     * **/
    public JSONObject getJSONAccountSnapshot(String type, Params extraParams) throws Exception {
        return new JSONObject(getAccountSnapshot(type, extraParams));
    }

    /** Request to get your daily account snapshot
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as AccountSnapshot - {type} object.
     * **/
    public <T extends AccountSnapshot> T getObjectAccountSnapshot(String type, Params extraParams) throws Exception {
        return getObjectAccountSnapshot(type, new JSONObject(getAccountSnapshot(type, extraParams)));
    }

    /** Method to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @param jsonAccount: obtain by request to binance
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return account snapshot as AccountSnapshot - {type} object.
     * **/
    private <T extends AccountSnapshot> T getObjectAccountSnapshot(String type, JSONObject jsonAccount){
        JSONArray account;
        try {
            account = jsonAccount.getJSONArray("snapshotVos");
        }catch (JSONException jsonException){
            account = null;
        }
        return new AccountSnapshot(jsonAccount.getInt("code"), jsonAccount.getString("msg"), type,
                account).getAccountSnapshot();
    }

    /** Request to get enable or disable fast withdraw
     * @param enableFastWithdraw: true,false
     * true: endpoint will be ENABLE_FAST_WITHDRAW_ENDPOINT
     * false: endpoint will be DISABLE_FAST_WITHDRAW_ENDPOINT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-fast-withdraw-switch-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-fast-withdraw-switch-user_data</a>
     * @return successful or not of operation (true or false)
     * **/
    public boolean switchFastWithdraw(boolean enableFastWithdraw) throws Exception {
        String switchOperationEndpoint = DISABLE_FAST_WITHDRAW_ENDPOINT;
        if(enableFastWithdraw)
            switchOperationEndpoint = ENABLE_FAST_WITHDRAW_ENDPOINT;
        return sendSignedRequest(switchOperationEndpoint, getParamTimestamp(), POST_METHOD).equals("{}");
    }

    /** Request to submit withdraw
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as {@link String}
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount) throws Exception {
        String params = getParamTimestamp() + "&coin=" + coinSymbol + "&address=" + address + "&amount=" + amount;
        return submitWithdraw(params);
    }

    /** Request to submit withdraw
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as {@link JSONObject}
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol, address, amount));
    }

    /** Request to submit withdraw with extraParams
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as {@link String}
     * **/
    public String submitWithdraw(String coinSymbol, String address, double amount, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&coin=" + coinSymbol + "&address=" + address + "&amount=" + amount;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return submitWithdraw(params);
    }

    /** Request to submit withdraw with extraParams
     * @param coinSymbol: symbol of coin used es. BTC
     * @param address: address used
     * @param amount: amount to withdraw
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are withdrawOrderId,network,addressTag,transactionFeeFlag,name,walletType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as {@link JSONObject}
     * **/
    public JSONObject submitJSONWithdraw(String coinSymbol, String address, double amount, Params extraParams) throws Exception {
        return new JSONObject(submitWithdraw(coinSymbol,address,amount,extraParams));
    }

    /** Method to submit withdraw request
     * @param params: params of request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data</a>
     * @return id of transaction if operation is successful as {@link String}
     * **/
    private String submitWithdraw(String params) throws Exception {
        return sendSignedRequest(SUBMIT_WITHDRAW_ENDPOINT, params, POST_METHOD);
    }

    /** Request to get deposit history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
     * @return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory() throws Exception {
        return getDepositHistory(getParamTimestamp());
    }

    /** Request to get deposit history
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are coin,status,startTime,endTime,offset,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
     * @return list of deposits as ArrayList<Deposit>
     * **/
    public ArrayList<Deposit> getDepositHistory(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return getDepositHistory(params);
    }

    /** Method to submit get deposit history request
     * @param params: params of request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
     * @return list of deposits as ArrayList<Deposit>
     * **/
    private ArrayList<Deposit> getDepositHistory(String params) throws Exception {
        ArrayList<Deposit> depositHistory = new ArrayList<>();
        JSONArray deposits = new JSONArray(sendSignedRequest(DEPOSIT_HISTORY_ENDPOINT, params, GET_METHOD));
        for(int j = 0; j < deposits.length(); j++){
            JSONObject deposit = deposits.getJSONObject(j);
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

    /** Request to get withdraw history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory() throws Exception {
        return getWithdrawHistory(getParamTimestamp());
    }

    /** Request to get withdraw history
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are coin,withdrawOrderId,status,offset,limit,startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return getWithdrawHistory(params);
    }

    /** Method to submit get withdraw history request
     * @param params: params of request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    private ArrayList<Withdraw> getWithdrawHistory(String params) throws Exception {
        ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
        JSONArray withdraws = new JSONArray(sendSignedRequest(WITHDRAW_HISTORY_ENDPOINT, params, GET_METHOD));
        for(int j=0; j < withdraws.length(); j++){
            JSONObject withdraw = withdraws.getJSONObject(j);
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as DepositAddress object
     * **/
    public DepositAddress getDepositAddress(String coin) throws Exception {
        return getDepositAddressSender(getParamTimestamp() + "&coin=" + coin);
    }

    /** Request to get deposit address
     * @param coin: symbol of coin used to fetch address es. BTC
     * @param network: network used to fetch address es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as DepositAddress object
     * **/
    public DepositAddress getDepositAddress(String coin, String network) throws Exception {
        return getDepositAddressSender(getParamTimestamp() + "&coin=" + coin + "&network=" + network);
    }

    /** Method to submit get deposit address request
     * @param params: params of request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as DepositAddress object
     * **/
    private DepositAddress getDepositAddressSender(String params) throws Exception {
        JSONObject depositAddress = new JSONObject(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, params, GET_METHOD));
        return new DepositAddress(depositAddress.getString("address"),
                depositAddress.getString("coin"),
                depositAddress.getString("tag"),
                depositAddress.getString("url")
        );
    }

    /** Request to get account status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data</a>
     * @return account status as {@link String}
     * **/
    public String getAccountStatus() throws Exception {
        return sendSignedRequest(ACCOUNT_STATUS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get account status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data</a>
     * @return account status as {@link JSONObject}
     * **/
    public JSONObject getJSONAccountStatus() throws Exception {
        return new JSONObject(getAccountStatus());
    }

    /** Request to get API trading status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
     * @return API trading status as {@link String}
     * **/
    public String getAPITradingStatus() throws Exception {
        return sendSignedRequest(API_TRADING_STATUS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get API trading status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
     * @return API trading status as {@link JSONObject}
     * **/
    public JSONObject getJSONAPITradingStatus() throws Exception {
        return new JSONObject(getAPITradingStatus());
    }

    /** Request to get API trading status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
     * @return API trading status as APIStatus object
     * **/
    public APIStatus getObjectAPITradingStatus() throws Exception {
        JSONObject apiStatus = new JSONObject(getAPITradingStatus()).getJSONObject("data");
        HashMap<String,Integer> triggerCondition = new HashMap<>();
        JSONObject jsonObjectTrigger = apiStatus.getJSONObject("triggerCondition");
        ArrayList<String> keys = new ArrayList<>(jsonObjectTrigger.keySet());
        for (int j = 0; j < jsonObjectTrigger.length(); j++){
            String key = keys.get(j);
            triggerCondition.put(key,jsonObjectTrigger.getInt(key));
        }
        return new APIStatus(apiStatus.getBoolean("isLocked"),
                apiStatus.getInt("plannedRecoverTime"),
                triggerCondition,
                apiStatus.getLong("updateTime")
        );
    }

    /** Request to get dust log information <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as {@link String}
     * **/
    public String getDustLog() throws Exception {
        return sendSignedRequest(DUST_LOG_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get dust log information <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as {@link JSONObject}
     * **/
    public JSONObject getJSONDustLog() throws Exception {
        return new JSONObject(getDustLog());
    }

    /** Request to get dust log information <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as DustLog object
     * **/
    public DustLog getObjectDustLog() throws Exception {
        return getObjectDustLog(new JSONObject(getDustLog()));
    }

    /** Request to get dust log information
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as {@link String}
     * **/
    public String getDustLog(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(DUST_LOG_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get dust log information
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as {@link JSONObject}
     * **/
    public JSONObject getJSONDustLog(Params extraParams) throws Exception {
        return new JSONObject(getDustLog(extraParams));
    }

    /** Request to get dust log information
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as DustLog object
     * **/
    public DustLog getObjectDustLog(Params extraParams) throws Exception {
        return getObjectDustLog(new JSONObject(getDustLog(extraParams)));
    }

    /** Method to submit get dust log object
     * @param jsonDustLog: jsonObject assembled from request to Binance
     * @return dust log object
     * **/
    private DustLog getObjectDustLog(JSONObject jsonDustLog){
        int total = 0;
        ArrayList<DustLog.AssetDribblets> assetDribblets = new ArrayList<>();
        try {
            total = jsonDustLog.getInt("total");
            assetDribblets = new ArrayList<>();
            JSONArray jsonArrayDribblets = jsonDustLog.getJSONArray("assetDribblets");
            for (int j = 0; j < jsonArrayDribblets.length(); j++){
                JSONObject jsonObjectDribblets = jsonArrayDribblets.getJSONObject(j);
                assetDribblets.add(new DustLog.AssetDribblets(jsonObjectDribblets.getLong("operateTime"),
                        jsonObjectDribblets.getDouble("totalTransferedAmount"),
                        jsonObjectDribblets.getDouble("totalServiceChargeAmount"),
                        jsonObjectDribblets.getLong("transId"),
                        getListDribbletsDetails(jsonObjectDribblets
                                .getJSONArray("userAssetDribbletDetails"))
                ));
            }
        }catch (JSONException ignored){}
        return new DustLog(total,assetDribblets);
    }

    /** Request to get convertible assets into BNB <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * @return convertible assets into BNB as {@link String}
     * **/
    public String getConvertibleBNBAssets() throws Exception {
        return sendSignedRequest(ASSET_CONVERTIBLE_BNB_ENDPOINT, getParamTimestamp(), POST_METHOD);
    }

    /** Request to get convertible assets into BNB <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * @return convertible assets into BNB as {@link JSONObject}
     * **/
    public JSONObject getJSONConvertibleBNBAssets() throws Exception {
        return new JSONObject(getConvertibleBNBAssets());
    }

    /** Request to get convertible assets into BNB <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * @return convertible assets into BNB as ConvertibleBNBAssets object
     * **/
    public ConvertibleBNBAssets getObjectConvertibleBNBAssets() throws Exception {
        JSONObject BNBAsset = new JSONObject(getConvertibleBNBAssets());
        ArrayList<ConvertibleBNBAssets.AssetDetails> assetsDetails = new ArrayList<>();
        double totalTransferBtc = BNBAsset.getDouble("totalTransferBtc");
        double totalTransferBNB = BNBAsset.getDouble("totalTransferBNB");
        double dribbletPercentage = BNBAsset.getDouble("dribbletPercentage");
        JSONArray BNBAssets = BNBAsset.getJSONArray("details");
        for (int j = 0; j < BNBAssets.length(); j++){
            JSONObject assetDetails = BNBAssets.getJSONObject(j);
            assetsDetails.add(new ConvertibleBNBAssets.AssetDetails(assetDetails.getString("asset"),
                    assetDetails.getString("assetFullName"),
                    assetDetails.getDouble("amountFree"),
                    assetDetails.getDouble("toBTC"),
                    assetDetails.getDouble("toBNB"),
                    assetDetails.getDouble("toBNBOffExchange"),
                    assetDetails.getDouble("exchange")
            ));
        }
        return new ConvertibleBNBAssets(assetsDetails, totalTransferBtc, totalTransferBNB, dribbletPercentage);
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data</a>
     * @return dust transfer as {@link String}
     * **/
    public String getDustTransfer(ArrayList<String> assets) throws Exception {
        StringBuilder params = new StringBuilder(getParamTimestamp());
        for (String asset : assets)
            params.append("&asset=").append(asset);
        return sendSignedRequest(DUST_TRANSFER_ENDPOINT, params.toString(), POST_METHOD);
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data</a>
     * @return dust transfer as {@link JSONObject}
     * **/
    public JSONObject getJSONDustTransfer(ArrayList<String> assets) throws Exception {
        return new JSONObject(getDustTransfer(assets));
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data</a>
     * @return dust transfer as DustTransfer object
     * **/
    public DustTransfer getObjectDustTransfer(ArrayList<String> assets) throws Exception {
        JSONObject dustTransfer = new JSONObject(getDustTransfer(assets));
        double totalServiceCharge = 0,totalTransfered = 0;
        ArrayList<DustItem> transferResults = new ArrayList<>();
        try {
            totalServiceCharge = dustTransfer.getDouble("totalServiceCharge");
            totalTransfered = dustTransfer.getDouble("totalTransfered");
            transferResults = getListDribbletsDetails(dustTransfer.getJSONArray("transferResult"));
        }catch (JSONException ignored){}
        return new DustTransfer(totalServiceCharge, totalTransfered, transferResults);
    }

    /** Request to get asset dividend <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return asset dividend as {@link String}
     * **/
    public String getAssetDividend() throws Exception {
        return sendSignedRequest(ASSET_DIVIDEND_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get asset dividend <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return asset dividend as {@link JSONObject}
     * **/
    public JSONObject getJSONAssetDividend() throws Exception {
        return new JSONObject(getAssetDividend());
    }

    /** Request to get asset dividend <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return asset dividend as AssetDividend object
     * **/
    public AssetDividend getObjectAssetDividend() throws Exception {
        return getObjectAssetDividend(new JSONObject(getAssetDividend()));
    }

    /** Request to get asset dividend
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return  get asset dividend as {@link String}
     * **/
    public String getAssetDividend(Params extraParams) throws Exception {
        return sendSignedRequest(ASSET_DIVIDEND_ENDPOINT, apiRequest.encodeAdditionalParams(getParamTimestamp(),
                extraParams), GET_METHOD);
    }

    /** Request to get asset dividend
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return  get asset dividend as {@link JSONObject}
     * **/
    public JSONObject getJSONAssetDividend(Params extraParams) throws Exception {
        return new JSONObject(getAssetDividend(extraParams));
    }

    /** Request to get asset dividend
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return  get asset dividend as AssetDividend object
     * **/
    public AssetDividend getObjectAssetDividend(Params extraParams) throws Exception {
        return getObjectAssetDividend(new JSONObject(getAssetDividend(extraParams)));
    }

    /** Method to submit get asset dividend
     * @param jsonAssets: json object assembled from request to Binance
     * @return asset dividend object
     * **/
    private AssetDividend getObjectAssetDividend(JSONObject jsonAssets){
        ArrayList<AssetDividend.AssetDividendDetails> assetDividendDetails = new ArrayList<>();
        int total = 0;
        try {
            total = jsonAssets.getInt("total");
            JSONArray assetsDividend = jsonAssets.getJSONArray("rows");
            for (int j = 0; j < assetsDividend.length(); j++){
                JSONObject jsonObjectAsset = assetsDividend.getJSONObject(j);
                assetDividendDetails.add(new AssetDividend.AssetDividendDetails(jsonObjectAsset.getLong("id"),
                        jsonObjectAsset.getDouble("amount"),
                        jsonObjectAsset.getString("asset"),
                        jsonObjectAsset.getLong("divTime"),
                        jsonObjectAsset.getString("enInfo"),
                        jsonObjectAsset.getLong("tranId")
                ));
            }
        }catch (JSONException ignored){}
        return new AssetDividend(total, assetDividendDetails);
    }

    /** Request to get asset detail <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as {@link String}
     * **/
    public String getAssetDetail() throws Exception {
        return sendSignedRequest(ASSET_DETAIL_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get asset detail <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as {@link JSONObject}
     * **/
    public JSONObject getJSONAssetDetail() throws Exception {
        return new JSONObject(getAssetDetail());
    }

    /** Request to get asset detail <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail list as ArrayList<AssetDetail>
     * **/
    public ArrayList<AssetDetail> getAssetDetailList() throws Exception {
        JSONObject assetDetail = new JSONObject(getAssetDetail());
        ArrayList<AssetDetail> assetDetailList = new ArrayList<>();
        for (String key : new ArrayList<>(assetDetail.keySet())){
            JSONObject asset = assetDetail.getJSONObject(key);
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as {@link String}
     * **/
    public String getAssetDetail(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(ASSET_DETAIL_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as {@link JSONObject}
     * **/
    public JSONObject getJSONAssetDetail(String asset) throws Exception {
        return new JSONObject(getAssetDetail(asset));
    }

    /** Request to get asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as AssetDetail object
     * **/
    public AssetDetail getObjectAssetDetail(String asset) throws Exception {
        JSONObject assetDetail = new JSONObject(getAssetDetail(asset)).getJSONObject(asset);
        return new AssetDetail(asset,
                assetDetail.getDouble("minWithdrawAmount"),
                assetDetail.getBoolean("depositStatus"),
                assetDetail.getDouble("withdrawFee"),
                assetDetail.getBoolean("withdrawStatus"),
                getDepositTip(assetDetail)
        );
    }

    /** Method to get depositTip
     * @param jsonObject: jsonObject assembled from request to Binance
     * @return depositTip as {@link String}
     * **/
    private String getDepositTip(JSONObject jsonObject){
        try {
            return jsonObject.getString("depositTip");
        }catch (JSONException jsonException){
            return "No tip";
        }
    }

    /** Request to get asset trade fee <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
     * @return asset trade fee as {@link String}
     * **/
    public String getTradeFee() throws Exception {
        return sendSignedRequest(TRADE_FEE_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get asset trade fee <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
     * @return asset trade fee as {@link JSONArray}
     * **/
    public JSONArray getJSONTradeFee() throws Exception {
        return new JSONArray(getTradeFee());
    }

    /** Request to get asset trade fee <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee list as ArrayList<TradeFee>
     * **/
    public ArrayList<TradeFee> getTradeFeeList() throws Exception {
        ArrayList<TradeFee> tradeFeesList = new ArrayList<>();
        JSONArray tradeFees = new JSONArray(getTradeFee());
        for (int j = 0; j < tradeFees.length(); j++){
            JSONObject fee = tradeFees.getJSONObject(j);
            tradeFeesList.add(new TradeFee(fee.getString("symbol"),
                    fee.getDouble("makerCommission"),
                    fee.getDouble("takerCommission")
            ));
        }
        return tradeFeesList;
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee as {@link String}
     * **/
    public String getTradeFee(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(TRADE_FEE_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee as {@link JSONArray}
     * **/
    public JSONArray getJSONTradeFee(String symbol) throws Exception {
        return new JSONArray(getTradeFee(symbol));
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset trade fee as TradeFee object
     * **/
    public TradeFee getObjectTradeFee(String symbol) throws Exception {
        JSONObject tradeFee = new JSONArray(getTradeFee(symbol)).getJSONObject(0);
        return new TradeFee(tradeFee.getString("symbol"),
                tradeFee.getDouble("makerCommission"),
                tradeFee.getDouble("takerCommission")
        );
    }

    /** Request to get universal transfer
     * @param type: type for the request es. MAIN_UMFUTURE
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as {@link String}
     * **/
    public String getUniversalTransfer(String type, String asset, double amount) throws Exception {
        String params = getParamTimestamp() + "&type=" + type + "&asset=" + asset + "&amount=" + amount;
        return sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to get universal transfer
     * @param type: type for the request es. MAIN_UMFUTURE
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as {@link JSONObject}
     * **/
    public JSONObject getJSONUniversalTransfer(String type, String asset, double amount) throws Exception {
        return new JSONObject(getUniversalTransfer(type, asset, amount));
    }

    /** Request to get universal transfer
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are fromSymbol,toSymbol,recvWindow)
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as {@link String}
     * **/
    public String getUniversalTransfer(String type, String asset, double amount, Params extraParams) throws Exception {
        return sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, apiRequest.encodeAdditionalParams(getParamTimestamp(),
                        extraParams), POST_METHOD);
    }

    /** Request to get universal transfer
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are fromSymbol,toSymbol,recvWindow)
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer as {@link JSONObject}
     * **/
    public JSONObject getJSONUniversalTransfer(String type, String asset, double amount,
                                               Params extraParams) throws Exception {
        return new JSONObject(getUniversalTransfer(type, asset, amount, extraParams));
    }

    /** Request to get universal transfer history
     * @param type: type to fetch history
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    public ArrayList<UniversalTransfer> getUniversalTransferHistory(String type) throws Exception {
        String params = getParamTimestamp() + "&type=" + type;
        return getUniversalTransferHistorySender(params);
    }

    /** Request to get universal transfer history
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,current,size,fromSymbol,toSymbol,recvWindow)
     * @implNote in case type is ISOLATEDMARGIN_MARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass fromSymbol
     * @implNote in case type is MARGIN_ISOLATEDMARGIN or ISOLATEDMARGIN_ISOLATEDMARGIN you MUST pass toSymbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    public ArrayList<UniversalTransfer> getUniversalTransferHistory(String type, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&type=" + type;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return getUniversalTransferHistorySender(params);
    }

    /** Method to submit get universal transfer history
     * @param params: params of request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * **/
    private ArrayList<UniversalTransfer> getUniversalTransferHistorySender(String params) throws Exception{
        ArrayList<UniversalTransfer> universalTransfersHistory = new ArrayList<>();
        try {
            JSONArray transfers = new JSONObject(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, params, GET_METHOD)).getJSONArray("rows");
            for (int j = 0; j < transfers.length(); j++){
                JSONObject universalTranHistory = transfers.getJSONObject(j);
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

    /** Request to get funding wallet <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet() throws Exception {
        return getFundingWallet(getParamTimestamp());
    }

    /** Request to get universal transfer history
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,needBtcValuation,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet(Params extraParams) throws Exception {
        return getFundingWallet(apiRequest.encodeAdditionalParams(getParamTimestamp(),extraParams));
    }

    /** Method to submit get funding wallet request
     * @param params: params of request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    private ArrayList<FundingWallet> getFundingWallet(String params) throws Exception {
        ArrayList<FundingWallet> fundingWalletsList = new ArrayList<>();
        JSONArray fundingList = new JSONArray(sendSignedRequest(FUNDING_WALLET_ENDPOINT, params, POST_METHOD));
        for (int j = 0; j < fundingList.length(); j++){
            JSONObject fundingWallet = fundingList.getJSONObject(j);
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

    /** Request to get API key permission <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
     * @return API key permission as {@link String}
     * **/
    public String getAPIKeyPermission() throws Exception {
        return sendSignedRequest(API_KEY_PERMISSION_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get API key permission <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
     * @return API key permission as {@link JSONObject}
     * **/
    public JSONObject getJSONAPIKeyPermission() throws Exception {
        return new JSONObject(getAPIKeyPermission());
    }

    /** Request to get API key permission <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
     * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
     * @return API key permission as APIPermission object
     * **/
    public APIPermission getObjectAPIKeyPermission() throws Exception {
        JSONObject apiPermission = new JSONObject(getAPIKeyPermission());
        long tradingAuthorityExpirationTime;
        try {
            tradingAuthorityExpirationTime = apiPermission.getLong("tradingAuthorityExpirationTime");
        }catch (JSONException e){
            tradingAuthorityExpirationTime = -1;
        }
        return new APIPermission(apiPermission.getBoolean("ipRestrict"),
                apiPermission.getLong("createTime"),
                apiPermission.getBoolean("enableWithdrawals"),
                apiPermission.getBoolean("enableInternalTransfer"),
                apiPermission.getBoolean("permitsUniversalTransfer"),
                apiPermission.getBoolean("enableVanillaOptions"),
                apiPermission.getBoolean("enableReading"),
                apiPermission.getBoolean("enableFutures"),
                apiPermission.getBoolean("enableMargin"),
                apiPermission.getBoolean("enableSpotAndMarginTrading"),
                tradingAuthorityExpirationTime
        );
    }

}
