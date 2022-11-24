package com.tecknobit.binancemanager.managers.signedmanagers.wallet;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.FundingWallet;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.TradeFee;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.Withdraw;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.Withdraw.WithdrawStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.AccountType;
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
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit.DepositStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.DepositAddress;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustLogList;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustTransfer;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.UniversalTransfer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.GET_METHOD;
import static com.tecknobit.apimanager.apis.APIRequest.POST_METHOD;
import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.JSON;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

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
                                String secretKey) throws SystemException, Exception {
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
                                String secretKey) throws SystemException, Exception {
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
                                String secretKey) throws SystemException, Exception {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceSignedManager"}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceWalletManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, Exception {
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
     * @return all coin information as {@link ArrayList} of {@link CoinInformation}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     * All Coins' Information (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/config/getall")
    public ArrayList<CoinInformation> getAllCoins() throws Exception {
        return getAllCoins(LIBRARY_OBJECT);
    }

    /**
     * Request to get information of your coins available for deposit and withdraw
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return all coin information as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     * All Coins' Information (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(path = "/sapi/v1/capital/config/getall")
    public <T> T getAllCoins(ReturnFormat format) throws Exception {
        String coinsResponse = sendSignedRequest(ALL_COINS_ENDPOINT, getTimestampParam(), GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONArray(coinsResponse);
            case LIBRARY_OBJECT:
                ArrayList<CoinInformation> coins = new ArrayList<>();
                JSONArray jList = new JSONArray(coinsResponse);
                for (int j = 0; j < jList.length(); j++)
                    coins.add(new CoinInformation(jList.getJSONObject(j)));
                return (T) coins;
            default:
                return (T) coinsResponse;
        }
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     All Coins' Information (USER_DATA)</a>
     * @return single coin information as {@link CoinInformation} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/config/getall")
    public CoinInformation getSingleCoin(String coin) throws Exception {
        return getSingleCoin(coin, LIBRARY_OBJECT);
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param coin: identifier of coin to fetch es. BTC
     * @param format: return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     All Coins' Information (USER_DATA)</a>
     * @return single coin information as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/config/getall")
    public <T> T getSingleCoin(String coin, ReturnFormat format) throws Exception {
        return returnSingleCoin("coin", coin, format);
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     All Coins' Information (USER_DATA)</a>
     * @return single coin information as {@link CoinInformation} object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/config/getall")
    public CoinInformation getSingleCoinByName(String name) throws Exception {
        return getSingleCoinByName(name, LIBRARY_OBJECT);
    }

    /** Custom request to get information of your coin available for deposit and withdraw
     * @param name: identifier of coin to fetch by name es. Bitcoin
     * @param format: return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
     *     All Coins' Information (USER_DATA)</a>
     * @return single coin information as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/config/getall")
    public <T> T getSingleCoinByName(String name, ReturnFormat format) throws Exception {
        return returnSingleCoin("name", name, format);
    }

    /** Method to get information of your coin available
     * @param key: name or coin es Bitcoin or BTC
     * @param value: name or coin param
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return coin corresponding to params as {@code "format"} defines
     * **/
    @Returner
    private <T> T returnSingleCoin(String key, String value, ReturnFormat format) throws Exception {
        value = value.toUpperCase();
        JSONArray coins = getAllCoins(JSON);
        boolean found = false;
        JSONObject jCoin = null;
        for (int j = 0; j < coins.length() && !found; j++) {
            jCoin = coins.getJSONObject(j);
            if (jCoin.getString(key).toUpperCase().equals(value))
                found = true;
        }
        if (found) {
            switch (format) {
                case JSON:
                    return (T) jCoin;
                case LIBRARY_OBJECT:
                    return (T) new CoinInformation(jCoin);
                default:
                    return (T) jCoin.toString();
            }
        } else
            throw new IllegalArgumentException("No coin found with this property");
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT,MARGIN OR FUTURES
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     Daily Account Snapshot (USER_DATA)</a>
     * @return account snapshot as {@link AccountSnapshot} cast custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/accountSnapshot")
    public <T> T getAccountSnapshot(AccountType type) throws Exception {
        return getAccountSnapshot(type, LIBRARY_OBJECT);
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT, MARGIN OR FUTURES
     * @param format: return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     Daily Account Snapshot (USER_DATA)</a>
     * @return account snapshot as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/accountSnapshot")
    public <T> T getAccountSnapshot(AccountType type, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&type=" + type.toString().toUpperCase();
        return returnAccountSnapshot(type, sendSignedRequest(DAILY_ACCOUNT_SNAPSHOT_ENDPOINT, params, GET_METHOD), format);
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT, MARGIN OR FUTURES
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> limit results: min 7, max 30, default 7 - [INT, default 7]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate snapshots - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate snapshots - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     Daily Account Snapshot (USER_DATA)</a>
     * @return account snapshot as {@link AccountSnapshot} cast custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/accountSnapshot")
    public <T> T getAccountSnapshot(AccountType type, Params extraParams) throws Exception {
        return getAccountSnapshot(type, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get your daily account snapshot
     * @param type: SPOT, MARGIN OR FUTURES
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> limit results: min 7, max 30, default 7 - [INT, default 7]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate snapshots - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate snapshots - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
     *     Daily Account Snapshot (USER_DATA)</a>
     * @return account snapshot as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/accountSnapshot")
    public <T> T getAccountSnapshot(AccountType type, Params extraParams, ReturnFormat format) throws Exception {
        return returnAccountSnapshot(type, sendSignedRequest(DAILY_ACCOUNT_SNAPSHOT_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&type=" + type.toString().toUpperCase(),
                        extraParams), GET_METHOD), format);
    }

    /**
     * Method to create an account object
     *
     * @param type: SPOT, MARGIN OR FUTURES
     * @param accountResponse: obtained from Binance's response
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAccountSnapshot(AccountType type, String accountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountResponse);
            case LIBRARY_OBJECT:
                switch (type) {
                    case spot:
                        return (T) new SpotAccountSnapshot(new JSONObject(accountResponse));
                    case margin:
                        return (T) new MarginAccountSnapshot(new JSONObject(accountResponse));
                    default:
                        return (T) new FuturesAccountSnapshot(new JSONObject(accountResponse));
                }
            default:
                return (T) accountResponse;
        }
    }

    /**
     * Request to disable fast withdraw <br>
     * Any params required
     *
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-fast-withdraw-switch-user_data">
     * Disable Fast Withdraw Switch (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/account/disableFastWithdrawSwitch")
    public boolean disableFastWithdraw() throws Exception {
        return switchFastWithdraw(false);
    }

    /**
     * Request to enable fast withdraw <br>
     * Any params required
     *
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-fast-withdraw-switch-user_data">
     * Enable Fast Withdraw Switch (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/account/enableFastWithdrawSwitch")
    public boolean enableFastWithdraw() throws Exception {
        return switchFastWithdraw(true);
    }

    /**
     * Request to enable or disable fast withdraw
     *
     * @param enableFastWithdraw: true, false
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     **/
    private boolean switchFastWithdraw(boolean enableFastWithdraw) throws Exception {
        String switchOperationEndpoint = DISABLE_FAST_WITHDRAW_ENDPOINT;
        if (enableFastWithdraw)
            switchOperationEndpoint = ENABLE_FAST_WITHDRAW_ENDPOINT;
        return sendSignedRequest(switchOperationEndpoint, getTimestampParam(), POST_METHOD).equals("{}");
    }

    /**
     * Request to submit withdraw
     *
     * @param withdraw: withdraw to submit
     * @return id of withdraw if operation is successful as {@link String}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public String submitWithdraw(Withdraw withdraw) throws Exception {
        return submitWithdraw(withdraw.getCoin(), withdraw.getAddress(), withdraw.getAmount(), LIBRARY_OBJECT);
    }

    /**
     * Request to submit withdraw
     *
     * @param withdraw: withdraw to submit
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return id of withdraw if operation is successful as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public <T> T submitWithdraw(Withdraw withdraw, ReturnFormat format) throws Exception {
        return submitWithdraw(withdraw.getCoin(), withdraw.getAddress(), withdraw.getAmount(), format);
    }

    /**
     * Request to submit withdraw
     *
     * @param coin:    symbol of coin used es. BTC
     * @param address: address used
     * @param amount:  amount to withdraw
     * @return id of withdraw if operation is successful as {@link String}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public String submitWithdraw(String coin, String address, double amount) throws Exception {
        return submitWithdraw(coin, address, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to submit withdraw
     *
     * @param coin:    symbol of coin used es. BTC
     * @param address: address used
     * @param amount:  amount to withdraw
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return id of withdraw if operation is successful as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public <T> T submitWithdraw(String coin, String address, double amount, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&coin=" + coin + "&address=" + address + "&amount=" + amount;
        return returnWithdrawId(sendSignedRequest(SUBMIT_WITHDRAW_ENDPOINT, params, POST_METHOD), format);
    }

    /**
     * Request to submit withdraw with extra params
     *
     * @param withdraw:    withdraw to submit
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "withdrawOrderId"} -> client id for withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "network"} -> network - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "addressTag"} -> secondary address identifier for coins like XRP,XMR etc - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transactionFeeFlag"} -> hen making internal transfer, true for returning
     *                                the fee to the destination account; false for returning the fee back to the departure
     *                                account - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "name"} -> description of the address. Space in name should be encoded into
     *                                %20 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "walletType"} -> the wallet type for withdraw，0-spot wallet ，1-funding wallet.
     *                                Default walletType is the current "selected wallet"
     *                                under wallet->Fiat and Spot/Funding->Deposit - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return id of withdraw if operation is successful as {@link String}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implSpec (keys accepted are withdrawOrderId, network, addressTag, transactionFeeFlag, name, walletType, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public String submitWithdraw(Withdraw withdraw, Params extraParams) throws Exception {
        return submitWithdraw(withdraw.getCoin(), withdraw.getAddress(), withdraw.getAmount(), extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to submit withdraw with extra params
     *
     * @param withdraw:    withdraw to submit
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "withdrawOrderId"} -> client id for withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "network"} -> network - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "addressTag"} -> secondary address identifier for coins like XRP,XMR etc - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transactionFeeFlag"} -> hen making internal transfer, true for returning
     *                                the fee to the destination account; false for returning the fee back to the departure
     *                                account - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "name"} -> description of the address. Space in name should be encoded into
     *                                %20 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "walletType"} -> the wallet type for withdraw，0-spot wallet ，1-funding wallet.
     *                                Default walletType is the current "selected wallet"
     *                                under wallet->Fiat and Spot/Funding->Deposit - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return id of withdraw if operation is successful as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public <T> T submitWithdraw(Withdraw withdraw, Params extraParams, ReturnFormat format) throws Exception {
        return submitWithdraw(withdraw.getCoin(), withdraw.getAddress(), withdraw.getAmount(), extraParams, format);
    }

    /**
     * Request to submit withdraw with extra params
     *
     * @param coin:        symbol of coin used es. BTC
     * @param address:     address used
     * @param amount:      amount to withdraw
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "withdrawOrderId"} -> client id for withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "network"} -> network - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "addressTag"} -> secondary address identifier for coins like XRP,XMR etc - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transactionFeeFlag"} -> hen making internal transfer, true for returning
     *                                the fee to the destination account; false for returning the fee back to the departure
     *                                account - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "name"} -> description of the address. Space in name should be encoded into
     *                                %20 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "walletType"} -> the wallet type for withdraw，0-spot wallet ，1-funding wallet.
     *                                Default walletType is the current "selected wallet"
     *                                under wallet->Fiat and Spot/Funding->Deposit - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return id of withdraw if operation is successful as {@link String}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public String submitWithdraw(String coin, String address, double amount, Params extraParams) throws Exception {
        return submitWithdraw(coin, address, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to submit withdraw with extra params
     *
     * @param coin:        symbol of coin used es. BTC
     * @param address:     address used
     * @param amount:      amount to withdraw
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "withdrawOrderId"} -> client id for withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "network"} -> network - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "addressTag"} -> secondary address identifier for coins like XRP,XMR etc - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transactionFeeFlag"} -> hen making internal transfer, true for returning
     *                                the fee to the destination account; false for returning the fee back to the departure
     *                                account - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "name"} -> description of the address. Space in name should be encoded into
     *                                %20 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "walletType"} -> the wallet type for withdraw，0-spot wallet ，1-funding wallet.
     *                                Default walletType is the current "selected wallet"
     *                                under wallet->Fiat and Spot/Funding->Deposit - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return id of withdraw if operation is successful as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-user_data">
     * Withdraw(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/apply")
    public <T> T submitWithdraw(String coin, String address, double amount, Params extraParams,
                                ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&coin=" + coin + "&address=" + address + "&amount=" + amount;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnWithdrawId(sendSignedRequest(SUBMIT_WITHDRAW_ENDPOINT, params, POST_METHOD), format);
    }

    /**
     * Method to create a withdrawal id
     *
     * @param withdrawResponse: obtained from Binance's response
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return withdrawal id as {@code "format"} defines
     * @apiNote in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the id value as {@link String}
     **/
    @Returner
    private <T> T returnWithdrawId(String withdrawResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(withdrawResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(withdrawResponse).getString("id");
            default:
                return (T) withdrawResponse;
        }
    }

    /**
     * Request to get deposit history <br>
     * Any params required
     *
     * @return list of deposits as {@link ArrayList} of {@link Deposit}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     * Deposit History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/hisrec")
    public ArrayList<Deposit> getDepositHistory() throws Exception {
        return getDepositHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get deposit history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return list of deposits as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     * Deposit History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/hisrec")
    public <T> T getDepositHistory(ReturnFormat format) throws Exception {
        return returnDepositHistory(sendSignedRequest(DEPOSIT_HISTORY_ENDPOINT, getTimestampParam(), GET_METHOD), format);
    }

    /**
     * Request to get deposit history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin of the deposit - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> 0(0:pending,6: credited but cannot withdraw, 1:success),
     *                                constants available {@link DepositStatus} - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate history - [LONG, default 90 days from current timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate history - [LONG, default present timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset - [INTEGER, default, 0]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INTEGER, default 1000]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return list of deposits as {@link ArrayList} of {@link Deposit}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     * Deposit History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/hisrec")
    public ArrayList<Deposit> getDepositHistory(Params extraParams) throws Exception {
        return getDepositHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get deposit history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin of the deposit - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> 0(0:pending,6: credited but cannot withdraw, 1:success),
     *                                constants available {@link DepositStatus} - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate history - [LONG, default 90 days from current timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate history - [LONG, default present timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset - [INTEGER, default, 0]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INTEGER, default 1000]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return list of deposits as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
     * Deposit History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/hisrec")
    public <T> T getDepositHistory(Params extraParams, ReturnFormat format) throws Exception {
        return returnDepositHistory(sendSignedRequest(DEPOSIT_HISTORY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
    }

    /**
     * Method to create a deposit history
     *
     * @param depositHistoryResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return deposits history as {@code "format"} defines
     **/
    @Returner
    private <T> T returnDepositHistory(String depositHistoryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(depositHistoryResponse);
            case LIBRARY_OBJECT:
                ArrayList<Deposit> depositHistory = new ArrayList<>();
                JSONArray jDeposits = new JSONArray(depositHistoryResponse);
                for (int j = 0; j < jDeposits.length(); j++)
                    depositHistory.add(new Deposit(jDeposits.getJSONObject(j)));
                return (T) depositHistory;
            default:
                return (T) depositHistoryResponse;
        }
    }

    /**
     * Request to get withdraw history <br>
     * Any params required
     *
     * @return list of withdraws as {@link ArrayList} of {@link Withdraw}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * Deposit Address (supporting network)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public ArrayList<Withdraw> getWithdrawHistory() throws Exception {
        return getWithdrawHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get withdraw history <br>
     * Any params required
     *
     * @return list of withdraws as {@link ArrayList} of {@link Withdraw}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * Deposit Address (supporting network)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public <T> T getWithdrawHistory(ReturnFormat format) throws Exception {
        return returnWithdrawHistory(sendSignedRequest(WITHDRAW_HISTORY_ENDPOINT, getTimestampParam(), GET_METHOD), format);
    }

    /**
     * Request to get withdraw history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin of the withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "withdrawOrderId"} -> client id for withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> 0(0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected
     *                                4:Processing 5:Failure 6:Completed),
     *                                constants available {@link WithdrawStatus} - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate history - [LONG, default 90 days from current timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate history - [LONG, default present timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset - [INTEGER, default, 0]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INTEGER, default 1000]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return list of withdraws as {@link ArrayList} of {@link Withdraw}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implSpec (keys accepted are coin, withdrawOrderId, status, offset, limit, startTime, endTime, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * Deposit Address (supporting network)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public ArrayList<Withdraw> getWithdrawHistory(Params extraParams) throws Exception {
        return getWithdrawHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get withdraw history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin of the withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "withdrawOrderId"} -> client id for withdraw - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> 0(0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected
     *                                4:Processing 5:Failure 6:Completed),
     *                                constants available {@link WithdrawStatus} - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate history - [LONG, default 90 days from current timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate history - [LONG, default present timestamp]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset - [INTEGER, default, 0]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INTEGER, default 1000]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return list of withdraws as {@link ArrayList} of {@link Withdraw}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implSpec (keys accepted are coin, withdrawOrderId, status, offset, limit, startTime, endTime, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * Deposit Address (supporting network)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public <T> T getWithdrawHistory(Params extraParams, ReturnFormat format) throws Exception {
        return returnWithdrawHistory(sendSignedRequest(WITHDRAW_HISTORY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
    }

    /**
     * Method to create a withdrawal history
     *
     * @param withdrawHistoryResponse: obtained from Binance's response
     * @param format:                  return type formatter -> {@link ReturnFormat}
     * @return withdrawal history as {@code "format"} defines
     **/
    @Returner
    private <T> T returnWithdrawHistory(String withdrawHistoryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(withdrawHistoryResponse);
            case LIBRARY_OBJECT:
                ArrayList<Withdraw> withdrawsHistory = new ArrayList<>();
                JSONArray jWithdraws = new JSONArray(withdrawHistoryResponse);
                for (int j = 0; j < jWithdraws.length(); j++)
                    withdrawsHistory.add(new Withdraw(jWithdraws.getJSONObject(j)));
                return (T) withdrawsHistory;
            default:
                return (T) withdrawHistoryResponse;
        }
    }

    /** Request to get deposit address
     * @param coin: symbol of coin used to fetch address es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
     * @return deposit address information as {@link DepositAddress} custom object
     * **/
    public DepositAddress getDepositAddress(String coin) throws Exception {
        String params = getTimestampParam() + "&coin=" + coin;
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
        String params = getTimestampParam() + "&coin=" + coin + "&network=" + network;
        return new DepositAddress(new JSONObject(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, params, GET_METHOD)));
    }

    /** Request to get account status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data</a>
     * @return account status as {@link String}
     * **/
    public String getAccountStatus() throws Exception {
        return sendSignedRequest(ACCOUNT_STATUS_ENDPOINT, getTimestampParam(), GET_METHOD);
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
        return sendSignedRequest(API_TRADING_STATUS_ENDPOINT, getTimestampParam(), GET_METHOD);
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
        return sendSignedRequest(DUST_LOG_ENDPOINT, getTimestampParam(), GET_METHOD);
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
        String params = apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams);
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
        return sendSignedRequest(ASSET_CONVERTIBLE_BNB_ENDPOINT, getTimestampParam(), POST_METHOD);
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
        StringBuilder params = new StringBuilder(getTimestampParam());
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
        return sendSignedRequest(ASSET_DIVIDEND_ENDPOINT, getTimestampParam(), GET_METHOD);
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
        return sendSignedRequest(ASSET_DIVIDEND_ENDPOINT, apiRequest.encodeAdditionalParams(getTimestampParam(),
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
        return sendSignedRequest(ASSET_DETAIL_ENDPOINT, getTimestampParam(), GET_METHOD);
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
        String params = getTimestampParam() + "&asset=" + asset;
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
        return sendSignedRequest(TRADE_FEE_ENDPOINT, getTimestampParam(), GET_METHOD);
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
        String params = getTimestampParam() + "&symbol=" + symbol;
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
        String params = getTimestampParam() + "&type=" + type + "&asset=" + asset + "&amount=" + amount;
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
        return sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, apiRequest.encodeAdditionalParams(getTimestampParam(),
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
        String params = getTimestampParam() + "&type=" + type;
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
        String params = getTimestampParam() + "&type=" + type;
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
        return getFundingWallet(getTimestampParam());
    }

    /** Request to get universal transfer history
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,needBtcValuation,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
     * @return funding wallet as ArrayList<FundingWallet>
     * **/
    public ArrayList<FundingWallet> getFundingWallet(Params extraParams) throws Exception {
        return getFundingWallet(apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams));
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
        return sendSignedRequest(API_KEY_PERMISSION_ENDPOINT, getTimestampParam(), GET_METHOD);
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
