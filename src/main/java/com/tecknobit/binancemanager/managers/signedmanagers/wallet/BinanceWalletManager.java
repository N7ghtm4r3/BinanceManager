package com.tecknobit.binancemanager.managers.signedmanagers.wallet;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
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
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.CoinInformation.NetworkItem;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.ConvertibleBNBAssets;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit.DepositStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.DepositAddress;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustLogList;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustTransfer;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.UniversalTransferHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.UniversalTransferHistory.TransferType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.GET_METHOD;
import static com.tecknobit.apimanager.apis.APIRequest.POST_METHOD;
import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.JSON;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceWalletManager} class is useful to manage all {@code "Binance"}'s Wallet Endpoints
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
                                String secretKey) throws Exception {
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
                                String secretKey) throws Exception {
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
    public BinanceWalletManager(String baseEndpoint, int timeout, String apiKey, String secretKey) throws Exception {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceWalletManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceWalletManager(String baseEndpoint, String apiKey, String secretKey) throws Exception {
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
     *                                {@code "transactionFeeFlag"} -> when making internal transfer, true for returning
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
     *                                {@code "transactionFeeFlag"} -> when making internal transfer, true for returning
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
     *                                {@code "transactionFeeFlag"} -> when making internal transfer, true for returning
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
     *                                {@code "transactionFeeFlag"} -> when making internal transfer, true for returning
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
     * Withdraw History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/history")
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
     * Withdraw History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/history")
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * Withdraw History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/history")
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
     * Withdraw History (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/withdraw/history")
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

    /**
     * Request to get deposit address
     *
     * @param coin: coin used to fetch address
     * @return deposit address information as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public DepositAddress getDepositAddress(CoinInformation coin) throws Exception {
        return getDepositAddress(coin.getCoin(), LIBRARY_OBJECT);
    }

    /**
     * Request to get deposit address
     *
     * @param coin:   coin used to fetch address
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return deposit address information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public <T> T getDepositAddress(CoinInformation coin, ReturnFormat format) throws Exception {
        return getDepositAddress(coin.getCoin(), format);
    }

    /**
     * Request to get deposit address
     *
     * @param coin: symbol of coin used to fetch address es. BTC
     * @return deposit address information as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public DepositAddress getDepositAddress(String coin) throws Exception {
        return getDepositAddress(coin, LIBRARY_OBJECT);
    }

    /**
     * Request to get deposit address
     *
     * @param coin:   symbol of coin used to fetch address es. BTC
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return deposit address information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public <T> T getDepositAddress(String coin, ReturnFormat format) throws Exception {
        return returnDepositAddress(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, getTimestampParam() + "&coin=" +
                coin, GET_METHOD), format);
    }

    /**
     * Request to get deposit address
     *
     * @param coin:    coin used to fetch address
     * @param network: network used to fetch address
     * @return deposit address information as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public DepositAddress getDepositAddress(CoinInformation coin, NetworkItem network) throws Exception {
        return getDepositAddress(coin.getCoin(), network.getNetwork(), LIBRARY_OBJECT);
    }

    /**
     * Request to get deposit address
     *
     * @param coin:    coin used to fetch address
     * @param network: network used to fetch address
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return deposit address information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public <T> T getDepositAddress(CoinInformation coin, NetworkItem network, ReturnFormat format) throws Exception {
        return getDepositAddress(coin.getCoin(), network.getNetwork(), format);
    }

    /**
     * Request to get deposit address
     *
     * @param coin:    symbol of coin used to fetch address es. BTC
     * @param network: network used to fetch address es. BTC
     * @return deposit address information as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public DepositAddress getDepositAddress(String coin, String network) throws Exception {
        return getDepositAddress(coin, network, LIBRARY_OBJECT);
    }

    /**
     * Request to get deposit address
     *
     * @param coin:    symbol of coin used to fetch address es. BTC
     * @param network: network used to fetch address es. BTC
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return deposit address information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
     * Deposit Address (supporting network) (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/capital/deposit/address")
    public <T> T getDepositAddress(String coin, String network, ReturnFormat format) throws Exception {
        return returnDepositAddress(sendSignedRequest(DEPOSIT_ADDRESS_ENDPOINT, getTimestampParam() + "&coin="
                + coin + "&network=" + network, GET_METHOD), format);
    }

    /**
     * Method to create a deposit address object
     *
     * @param depositAddressResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
     **/
    @Returner
    private <T> T returnDepositAddress(String depositAddressResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(depositAddressResponse);
            case LIBRARY_OBJECT:
                return (T) new DepositAddress(new JSONObject(depositAddressResponse));
            default:
                return (T) depositAddressResponse;
        }
    }

    /**
     * Request to get account status <br>
     * Any params required
     *
     * @return account status as {@link String}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">
     * Account Status (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/account/status")
    public String getAccountStatus() throws Exception {
        return getAccountStatus(LIBRARY_OBJECT);
    }

    /**
     * Request to get account status
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return account status  as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-status-user_data">
     * Account Status (USER_DATA)</a>
     * @implSpec in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the status value as {@link String}
     **/
    @Returner
    @RequestPath(path = "/sapi/v1/account/status")
    public <T> T getAccountStatus(ReturnFormat format) throws Exception {
        String accountStatusResponse = sendSignedRequest(ACCOUNT_STATUS_ENDPOINT, getTimestampParam(), GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountStatusResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(accountStatusResponse).getString("data");
            default:
                return (T) accountStatusResponse;
        }
    }

    /**
     * Request to get API trading status <br>
     * Any params required
     *
     * @return API trading status as {@link APIStatus} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
     * Account API Trading Status (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/account/apiTradingStatus")
    public APIStatus getAPITradingStatus() throws Exception {
        return getAPITradingStatus(LIBRARY_OBJECT);
    }

    /**
     * Request to get API trading status
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return API trading status as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
     * Account API Trading Status (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(path = "/sapi/v1/account/apiTradingStatus")
    public <T> T getAPITradingStatus(ReturnFormat format) throws Exception {
        String tradingStatusResponse = sendSignedRequest(API_TRADING_STATUS_ENDPOINT, getTimestampParam(), GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(tradingStatusResponse);
            case LIBRARY_OBJECT:
                return (T) new APIStatus(new JSONObject(tradingStatusResponse).getJSONObject("data"));
            default:
                return (T) tradingStatusResponse;
        }
    }

    /**
     * Request to get dust log information <br>
     * Any params required
     *
     * @return dust log information as {@link DustLogList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * DustLog(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/dribblet")
    public DustLogList getDustLog() throws Exception {
        return getDustLog(LIBRARY_OBJECT);
    }

    /**
     * Request to get dust log information
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return dust log information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * DustLog(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/dribblet")
    public <T> T getDustLog(ReturnFormat format) throws Exception {
        return returnDustLog(sendSignedRequest(DUST_LOG_ENDPOINT, getTimestampParam(), GET_METHOD), format);
    }

    /**
     * Request to get dust log information
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate dust log - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate dust log - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return dust log information as {@link DustLogList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * DustLog(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/dribblet")
    public DustLogList getDustLog(Params extraParams) throws Exception {
        return getDustLog(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get dust log information
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate dust log - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate dust log - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return dust log information as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     * DustLog(USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/dribblet")
    public <T> T getDustLog(Params extraParams, ReturnFormat format) throws Exception {
        return returnDustLog(sendSignedRequest(DUST_LOG_ENDPOINT, apiRequest.encodeAdditionalParams(getTimestampParam(),
                extraParams), GET_METHOD), format);
    }

    /**
     * Method to create a dust log object
     *
     * @param dustLogResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return dust log as {@code "format"} defines
     **/
    @Returner
    private <T> T returnDustLog(String dustLogResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(dustLogResponse);
            case LIBRARY_OBJECT:
                return (T) new DustLogList(new JSONObject(dustLogResponse));
            default:
                return (T) dustLogResponse;
        }
    }

    /** Request to get convertible assets into BNB <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
     *     Get Assets That Can Be Converted Into BNB (USER_DATA)</a>
     * @return convertible assets into BNB as {@link ConvertibleBNBAssets} custom object
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/dust-btc")
    public ConvertibleBNBAssets getConvertibleBNBAssets() throws Exception {
        return getConvertibleBNBAssets(LIBRARY_OBJECT);
    }

    /**
     * Request to get convertible assets into BNB
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return convertible assets into BNB as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
     * Get Assets That Can Be Converted Into BNB (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/dust-btc")
    public <T> T getConvertibleBNBAssets(ReturnFormat format) throws Exception {
        String assetsResponse = sendSignedRequest(ASSET_CONVERTIBLE_BNB_ENDPOINT, getTimestampParam(), POST_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetsResponse);
            case LIBRARY_OBJECT:
                return (T) new ConvertibleBNBAssets(new JSONObject(assetsResponse));
            default:
                return (T) assetsResponse;
        }
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">
     *     Dust Transfer (USER_DATA)</a>
     * @return dust transfer as {@link DustTransfer} custom object
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/dust")
    public DustTransfer getDustTransfer(ArrayList<String> assets) throws Exception {
        return getDustTransfer(assets, LIBRARY_OBJECT);
    }

    /** Request to get dust transfer
     * @param assets: list of assets to request dust transfer es. BTC,ETH,SOL
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dust-transfer-user_data">
     *     Dust Transfer (USER_DATA)</a>
     * @return dust transfer as {@code "format"} defines
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/dust")
    public <T> T getDustTransfer(ArrayList<String> assets, ReturnFormat format) throws Exception {
        String dustResponse = sendSignedRequest(DUST_TRANSFER_ENDPOINT, getTimestampParam() + "&" +
                apiRequest.concatenateParamsList("", "asset", assets), POST_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(dustResponse);
            case LIBRARY_OBJECT:
                return (T) new DustTransfer(new JSONObject(dustResponse));
            default:
                return (T) dustResponse;
        }
    }

    /**
     * Request to get asset dividend <br>
     * Any params required
     *
     * @return asset dividend as {@link AssetsDividendList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * Asset Dividend Record (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/assetDividend")
    public AssetsDividendList getAssetsDividend() throws Exception {
        return getAssetsDividend(LIBRARY_OBJECT);
    }

    /**
     * Request to get asset dividend
     * @param format:                      return type formatter -> {@link ReturnFormat}
     *
     * @return asset dividend as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * Asset Dividend Record (USER_DATA)</a>
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/assetDividend")
    public <T> T getAssetsDividend(ReturnFormat format) throws Exception {
        return returnAssetsDividend(sendSignedRequest(ASSET_DIVIDEND_ENDPOINT, getTimestampParam(), GET_METHOD), format);
    }

    /**
     * Request to get asset dividend
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset of the dividend - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate asset dividend - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate asset dividend - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 500 - [INTEGER, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return asset dividend as {@link AssetsDividendList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * Asset Dividend Record (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/assetDividend")
    public AssetsDividendList getAssetsDividend(Params extraParams) throws Exception {
        return getAssetsDividend(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get asset dividend
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset of the dividend - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate asset dividend - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate asset dividend - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 500 - [INTEGER, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return asset dividend as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
     * Asset Dividend Record (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/assetDividend")
    public <T> T getAssetsDividend(Params extraParams, ReturnFormat format) throws Exception {
        return returnAssetsDividend(sendSignedRequest(ASSET_DIVIDEND_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
    }

    /**
     * Method to create an assets dividend list
     *
     * @param assetsDividendResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return assets dividend list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAssetsDividend(String assetsDividendResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetsDividendResponse);
            case LIBRARY_OBJECT:
                return (T) new AssetsDividendList(new JSONObject(assetsDividendResponse));
            default:
                return (T) assetsDividendResponse;
        }
    }

    /** Request to get asset detail <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     Asset Detail (USER_DATA)</a>
     * @return asset detail list as {@link ArrayList} of {@link AssetDetail}
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/assetDetail")
    public ArrayList<AssetDetail> getAssetsDetail() throws Exception {
        return getAssetsDetail(LIBRARY_OBJECT);
    }

    /** Request to get asset detail
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     Asset Detail (USER_DATA)</a>
     * @return asset detail list as {@code "format"} defines
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/assetDetail")
    public <T> T getAssetsDetail(ReturnFormat format) throws Exception {
        String assetsResponse = sendSignedRequest(ASSET_DETAIL_ENDPOINT, getTimestampParam(), GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetsResponse);
            case LIBRARY_OBJECT:
                ArrayList<AssetDetail> assetsDetail = new ArrayList<>();
                JSONObject jAssetDetail = new JSONObject(assetsResponse);
                for (String key : new ArrayList<>(jAssetDetail.keySet()))
                    assetsDetail.add(new AssetDetail(jAssetDetail.getJSONObject(key).put("assetName", key)));
                return (T) assetsDetail;
            default:
                return (T) assetsResponse;

        }
    }

    /** Custom request to get single asset detail
     * @param asset: asset to get details es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     Asset Detail (USER_DATA)</a>
     * @return asset detail as {@link AssetDetail} custom object
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/assetDetail")
    public AssetDetail getAssetDetail(String asset) throws Exception {
        return getAssetDetail(asset, LIBRARY_OBJECT);
    }

    /** Custom request to get single asset detail
     * @param asset: asset to get details es BTC
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
     *     Asset Detail (USER_DATA)</a>
     * @return asset detail as {@code "format"} defines
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/assetDetail")
    public <T> T getAssetDetail(String asset, ReturnFormat format) throws Exception {
        String assetResponse = sendSignedRequest(ASSET_DETAIL_ENDPOINT, getTimestampParam() + "&asset=" + asset,
                GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetResponse);
            case LIBRARY_OBJECT:
                return (T) new AssetDetail(new JSONObject(assetResponse).put("assetName", asset));
            default:
                return (T) assetResponse;
        }
    }

    /** Request to get asset trade fee <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
     *     Trade Fee (USER_DATA)</a>
     * @return asset trade fee list as {@link ArrayList} of {@link TradeFee}
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/tradeFee")
    public ArrayList<TradeFee> getTradeFees() throws Exception {
        return getTradeFees(LIBRARY_OBJECT);
    }

    /** Request to get asset trade fee
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
     *     Trade Fee (USER_DATA)</a>
     * @return asset trade fee list as {@code "format"} defines
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/tradeFee")
    public <T> T getTradeFees(ReturnFormat format) throws Exception {
        String feeResponse = sendSignedRequest(TRADE_FEE_ENDPOINT, getTimestampParam(), GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONArray(feeResponse);
            case LIBRARY_OBJECT:
                ArrayList<TradeFee> tradeFees = new ArrayList<>();
                JSONArray jTradeFees = new JSONArray(feeResponse);
                for (int j = 0; j < jTradeFees.length(); j++)
                    tradeFees.add(new TradeFee(jTradeFees.getJSONObject(j)));
                return (T) tradeFees;
            default:
                return (T) feeResponse;
        }
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
     *     Trade Fee (USER_DATA)</a>
     * @return asset trade fee as {@link TradeFee} custom object
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/tradeFee")
    public TradeFee getTradeFee(String symbol) throws Exception {
        return getTradeFee(symbol, LIBRARY_OBJECT);
    }

    /** Request to get asset trade fee
     * @param symbol: asset to get details es BTCUSD
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
     *     Trade Fee (USER_DATA)</a>
     * @return asset trade fee as {@code "format"} defines
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
     * **/
    @Returner
    @RequestPath(path = "/sapi/v1/asset/tradeFee")
    public <T> T getTradeFee(String symbol, ReturnFormat format) throws Exception {
        String feeResponse = sendSignedRequest(TRADE_FEE_ENDPOINT, getTimestampParam() + "&symbol=" + symbol,
                GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(feeResponse);
            case LIBRARY_OBJECT:
                return (T) new TradeFee(new JSONArray(feeResponse).getJSONObject(0));
            default:
                return (T) feeResponse;
        }
    }

    /** Request to get universal transfer
     * @param type: type for the request
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *    User Universal Transfer (USER_DATA)</a>
     * @return universal transfer as {@link String} custom object
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public String getUniversalTransfer(TransferType type, String asset, double amount) throws Exception {
        return getUniversalTransfer(type, asset, amount, LIBRARY_OBJECT);
    }

    /** Request to get universal transfer
     * @param type: type for the request
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *    User Universal Transfer (USER_DATA)</a>
     * @return universal transfer as {@code "format"} defines
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public <T> T getUniversalTransfer(TransferType type, String asset, double amount, ReturnFormat format) throws Exception {
        return returnUniversalTransfer(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, getTimestampParam() +
                "&type=" + type + "&asset=" + asset + "&amount=" + amount, POST_METHOD), format);
    }

    /** Request to get universal transfer
     * @param type: type for the request
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromSymbol"} -> from symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toSymbol"} -> to symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *    User Universal Transfer (USER_DATA)</a>
     * @return universal transfer as {@link String} custom object
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public String getUniversalTransfer(TransferType type, String asset, double amount,
                                       Params extraParams) throws Exception {
        return getUniversalTransfer(type, asset, amount, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get universal transfer
     * @param type: type for the request
     * @param asset: asset for the request es. BTC
     * @param amount: amount for the request
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromSymbol"} -> from symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toSymbol"} -> to symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     *    User Universal Transfer (USER_DATA)</a>
     * @return universal transfer as {@code "format"} defines
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public <T> T getUniversalTransfer(TransferType type, String asset, double amount, Params extraParams,
                                      ReturnFormat format) throws Exception {
        return returnUniversalTransfer(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), POST_METHOD), format);
    }

    /**
     * Method to create a universal transfer
     *
     * @param universalTransferResponse: obtained from Binance's response
     * @param format:                    return type formatter -> {@link ReturnFormat}
     * @return universal transfer as {@code "format"} defines
     * @apiNote in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the id value as {@link String}
     **/
    @Returner
    private <T> T returnUniversalTransfer(String universalTransferResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(universalTransferResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(universalTransferResponse).getString("tranId");
            default:
                return (T) universalTransferResponse;
        }
    }

    /**
     * Request to get universal transfer history
     *
     * @param type: type of the history to fetch
     * @return universal transfer history as {@link UniversalTransferHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     * User Universal Transfer (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public UniversalTransferHistory getUniversalTransferHistory(TransferType type) throws Exception {
        return getUniversalTransferHistory(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get universal transfer history
     *
     * @param type:   type of the history to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return universal transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     * User Universal Transfer (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public <T> T getUniversalTransferHistory(TransferType type, ReturnFormat format) throws Exception {
        return returnUniversalTransferHistory(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT, getTimestampParam()
                + "&type=" + type, GET_METHOD), format);
    }

    /**
     * Request to get universal transfer history
     *
     * @param type:        type of the history to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current value - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "fromSymbol"} -> from symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toSymbol"} -> to symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return universal transfer history as {@link UniversalTransferHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     * User Universal Transfer (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public UniversalTransferHistory getUniversalTransferHistory(TransferType type, Params extraParams) throws Exception {
        return getUniversalTransferHistory(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get universal transfer history
     *
     * @param type:        type of the history to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current value - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "fromSymbol"} -> from symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toSymbol"} -> to symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return universal transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
     * User Universal Transfer (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/transfer")
    public <T> T getUniversalTransferHistory(TransferType type, Params extraParams, ReturnFormat format) throws Exception {
        return returnUniversalTransferHistory(sendSignedRequest(UNIVERSAL_TRANSFER_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&type=" + type, extraParams),
                GET_METHOD), format);
    }

    /**
     * Method to create a universal transfer history
     *
     * @param universalTransferResponse: obtained from Binance's response
     * @param format:                    return type formatter -> {@link ReturnFormat}
     * @return universal transfer history as {@code "format"} defines
     **/
    @Returner
    private <T> T returnUniversalTransferHistory(String universalTransferResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(universalTransferResponse);
            case LIBRARY_OBJECT:
                return (T) new UniversalTransferHistory(new JSONObject(universalTransferResponse));
            default:
                return (T) universalTransferResponse;
        }
    }

    /** Request to get funding wallet <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     *     Funding Wallet (USER_DATA)</a>
     * @return funding wallet as {@link ArrayList} of {@link FundingWallet}
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
     * **/
    @RequestPath(path = "/sapi/v1/asset/get-funding-asset")
    public ArrayList<FundingWallet> getFundingWallet() throws Exception {
        return getFundingWallet(LIBRARY_OBJECT);
    }

    /**
     * Request to get funding wallet
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return funding wallet as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     * Funding Wallet (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/get-funding-asset")
    public <T> T getFundingWallet(ReturnFormat format) throws Exception {
        return returnFundingWallets(sendSignedRequest(FUNDING_WALLET_ENDPOINT, getTimestampParam(), POST_METHOD), format);
    }

    /**
     * Request to get universal transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset of the funding wallet - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "needBtcValuation"} -> true or false whether the valuation is needed in BTC - [BOOLEAN]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return funding wallet as {@link ArrayList} of {@link FundingWallet}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     * Funding Wallet (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/get-funding-asset")
    public ArrayList<FundingWallet> getFundingWallet(Params extraParams) throws Exception {
        return getFundingWallet(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get universal transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset of the funding wallet - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "needBtcValuation"} -> true or false whether the valuation is needed in BTC - [BOOLEAN]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return funding wallet as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">
     * Funding Wallet (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/asset/get-funding-asset")
    public <T> T getFundingWallet(Params extraParams, ReturnFormat format) throws Exception {
        return returnFundingWallets(sendSignedRequest(FUNDING_WALLET_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), POST_METHOD), format);
    }

    /**
     * Method to create a funding wallets list
     *
     * @param fundingWalletsResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return funding wallets history as {@code "format"} defines
     **/
    @Returner
    private <T> T returnFundingWallets(String fundingWalletsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(fundingWalletsResponse);
            case LIBRARY_OBJECT:
                ArrayList<FundingWallet> wallets = new ArrayList<>();
                JSONArray jWallets = new JSONArray(fundingWalletsResponse);
                for (int j = 0; j < jWallets.length(); j++)
                    wallets.add(new FundingWallet(jWallets.getJSONObject(j)));
                return (T) wallets;
            default:
                return (T) fundingWalletsResponse;
        }
    }

    /**
     * Request to get API key permission <br>
     * Any params required
     *
     * @return API key permission as {@link APIPermission} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
     * Get API Key Permission (USER_DATA)</a>
     **/
    @RequestPath(path = "/sapi/v1/account/apiRestrictions")
    public APIPermission getAPIKeyPermission() throws Exception {
        return getAPIKeyPermission(LIBRARY_OBJECT);
    }

    /**
     * Request to get API key permission
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return API key permission as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
     * Get API Key Permission (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(path = "/sapi/v1/account/apiRestrictions")
    public <T> T getAPIKeyPermission(ReturnFormat format) throws Exception {
        String permissionResponse = sendSignedRequest(API_KEY_PERMISSION_ENDPOINT, getTimestampParam(), GET_METHOD);
        switch (format) {
            case JSON:
                return (T) new JSONObject(permissionResponse);
            case LIBRARY_OBJECT:
                return (T) new APIPermission(new JSONObject(permissionResponse));
            default:
                return (T) permissionResponse;
        }
    }

}