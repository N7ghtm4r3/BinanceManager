package com.tecknobit.binancemanager.managers.signedmanagers.wallet;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.FundingWallet;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.TradeFee;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.Withdraw;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.FuturesAccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.api.APIPermission;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.api.APIStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.AssetDetail;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.AssetsDividendList;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.CoinInformation;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.ConvertibleBNBAssets;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.DepositAddress;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustLogList;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustTransfer;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.UniversalTransfer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.GET_METHOD;
import static com.tecknobit.apimanager.apis.APIRequest.POST_METHOD;
import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.MARGIN;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.SPOT;

/**
 * The {@code BinanceWalletManager} class is useful to manage all {@code "Binance"} Wallet Endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#wallet-endpoints">
 * Wallet Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceWalletManager extends BinanceSignedManager {

    /**
     * Constructor to init a {@link BinanceWalletManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceWalletManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWalletManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceWalletManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWalletManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceWalletManager(String baseEndpoint, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceSignedManager"}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceWalletManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceWalletManager} <br>
     * Any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceWalletManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceSignedManager firstManager = new BinanceSignedManager("apiKey", "apiSecret");
     *        //You don't need to insert all credentials to make manager work
     *        BinanceSignedManager secondManager = new BinanceSignedManager(); //same credentials used
     *     }
     * </pre>
     **/
    public BinanceWalletManager() {
        super();
    }

    /**
     * Request to get information of your coins available for deposit and withdraw <br>
     * Any params required
     *
     * @return all coin information as {@link String}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     **/
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
            coinInformationList.add(new CoinInformation(coinsInformation.getJSONObject(j)));
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
        return new CoinInformation(getSingleCoin(coin, "coin"));
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
        return new CoinInformation(getSingleCoin(name, "name"));
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
        return getObjectAccountSnapshot(type, new JSONObject(getAccountSnapshot(type)));
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

    /**
     * Method to get your daily account snapshot
     *
     * @param type:        SPOT,MARGIN OR FUTURES
     * @param jsonAccount: obtain by request to binance
     * @return account snapshot as AccountSnapshot - {type} object.
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     **/
    private <T extends AccountSnapshot> T getObjectAccountSnapshot(String type, JSONObject jsonAccount) {
        switch (type) {
            case SPOT:
                return (T) new SpotAccountSnapshot(jsonAccount);
            case MARGIN:
                return (T) new MarginAccountSnapshot(jsonAccount);
            default:
                return (T) new FuturesAccountSnapshot(jsonAccount);
        }
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
        for (int j = 0; j < deposits.length(); j++)
            depositHistory.add(new Deposit(deposits.getJSONObject(j)));
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
     * @implSpec (keys accepted are coin, withdrawOrderId, status, offset, limit, startTime, endTime, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     * @return list of withdraws as ArrayList<Withdraw>
     * **/
    public ArrayList<Withdraw> getWithdrawHistory(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return getWithdrawHistory(params);
    }

    /**
     * Method to submit get withdraw history request
     *
     * @param params: params of request
     * @return list of withdraws as {@link ArrayList} of {@link Withdraw}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
     **/
    private ArrayList<Withdraw> getWithdrawHistory(String params) throws Exception {
        ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
        JSONArray withdraws = new JSONArray(sendSignedRequest(WITHDRAW_HISTORY_ENDPOINT, params, GET_METHOD));
        for (int j = 0; j < withdraws.length(); j++)
            withdrawsHistory.add(new Withdraw(withdraws.getJSONObject(j)));
        return withdrawsHistory;
    }

    /** Request to get deposit address
     * @param coin: symbol of coin used to fetch address es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as {@link DepositAddress} custom object
     * **/
    public DepositAddress getDepositAddress(String coin) throws Exception {
        String params = getParamTimestamp() + "&coin=" + coin;
        return new DepositAddress(new JSONObject(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, params, GET_METHOD)));
    }

    /** Request to get deposit address
     * @param coin: symbol of coin used to fetch address es. BTC
     * @param network: network used to fetch address es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as {@link DepositAddress} custom object
     * **/
    public DepositAddress getDepositAddress(String coin, String network) throws Exception {
        String params = getParamTimestamp() + "&coin=" + coin + "&network=" + network;
        return new DepositAddress(new JSONObject(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, params, GET_METHOD)));
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
        return new APIStatus(new JSONObject(getAPITradingStatus()).getJSONObject("data"));
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

    /**
     * Request to get dust log information <br>
     * Any params required
     *
     * @return dust log information as {@link JSONObject}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     **/
    public JSONObject getJSONDustLog() throws Exception {
        return new JSONObject(getDustLog());
    }

    /**
     * Request to get dust log information <br>
     * Any params required
     *
     * @return dust log information as DustLogList object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     **/
    public DustLogList getObjectDustLog() throws Exception {
        return new DustLogList(new JSONObject(getDustLog()));
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
     * @implSpec (keys accepted are startTime, endTime, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return dust log information as {@link JSONObject}
     * **/
    public JSONObject getJSONDustLog(Params extraParams) throws Exception {
        return new JSONObject(getDustLog(extraParams));
    }

    /**
     * Request to get dust log information
     *
     * @param extraParams: additional params of the request
     * @return dust log information as DustLogList object
     * @implSpec (keys accepted are startTime, endTime, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     **/
    public DustLogList getObjectDustLog(Params extraParams) throws Exception {
        return new DustLogList(new JSONObject(getDustLog(extraParams)));
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
        return new ConvertibleBNBAssets(new JSONObject(getConvertibleBNBAssets()));
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
        return new DustTransfer(new JSONObject(getDustTransfer(assets)));
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

    /**
     * Request to get asset dividend <br>
     * Any params required
     *
     * @return asset dividend as {@link JSONObject}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     **/
    public JSONObject getJSONAssetDividend() throws Exception {
        return new JSONObject(getAssetDividend());
    }

    /**
     * Request to get asset dividend <br>
     * Any params required
     *
     * @return asset dividend as AssetsDividendList object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     **/
    public AssetsDividendList getObjectAssetDividend() throws Exception {
        return new AssetsDividendList(new JSONObject(getAssetDividend()));
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
     * @implSpec (keys accepted are asset, startTime, endTime, limit, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     * @return get asset dividend as {@link JSONObject}
     * **/
    public JSONObject getJSONAssetDividend(Params extraParams) throws Exception {
        return new JSONObject(getAssetDividend(extraParams));
    }

    /**
     * Request to get asset dividend
     *
     * @param extraParams: additional params of the request
     * @return get asset dividend as AssetsDividendList object
     * @implSpec (keys accepted are asset, startTime, endTime, limit, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data</a>
     **/
    public AssetsDividendList getObjectAssetDividend(Params extraParams) throws Exception {
        return new AssetsDividendList(new JSONObject(getAssetDividend(extraParams)));
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
        for (String key : new ArrayList<>(assetDetail.keySet()))
            assetDetailList.add(new AssetDetail(key, assetDetail.getJSONObject(key)));
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

    /** Custom request to get single asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
     * @return asset detail as AssetDetail object
     * **/
    public AssetDetail getObjectAssetDetail(String asset) throws Exception {
        return new AssetDetail(asset, new JSONObject(getAssetDetail(asset)).getJSONObject(asset));
    }

    /** Method to get depositTip
     * @param jsonObject: jsonObject assembled from request to {@code "Binance"}
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
        for (int j = 0; j < tradeFees.length(); j++)
            tradeFeesList.add(new TradeFee(tradeFees.getJSONObject(j)));
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
     * @return asset trade fee as {@link TradeFee} custom object
     * **/
    public TradeFee getObjectTradeFee(String symbol) throws Exception {
        return new TradeFee(new JSONArray(getTradeFee(symbol)).getJSONObject(0));
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

    /**
     * Method to submit get universal transfer history
     *
     * @param params: params of request
     * @return universal transfer history as ArrayList<UniversalTransfer>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     **/
    private ArrayList<UniversalTransfer> getUniversalTransferHistorySender(String params) throws Exception {
        ArrayList<UniversalTransfer> universalTransfersHistory = new ArrayList<>();
        JSONArray transfers = JsonHelper.getJSONArray(new JSONObject(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, params, GET_METHOD)),
                "rows", new JSONArray());
        for (int j = 0; j < transfers.length(); j++)
            universalTransfersHistory.add(new UniversalTransfer(transfers.getJSONObject(j)));
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
        for (int j = 0; j < fundingList.length(); j++)
            fundingWalletsList.add(new FundingWallet(fundingList.getJSONObject(j)));
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
        return new APIPermission(new JSONObject(getAPIKeyPermission()));
    }

}
