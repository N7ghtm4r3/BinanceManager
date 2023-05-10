package com.tecknobit.binancemanager.managers.signedmanagers.subaccount;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.futures.records.FutureAccountTransactionsHistory.FutureTransactionType;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.ManagedSubAccountList;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.SubAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.SubAccountEnabledResult;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.SubAccountStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.SubFuturesAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.SummarySubFuturesAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin.CoinSubFuturesAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin.CoinSummarySubFuturesAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk.CoinFuturesPositionRisk;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk.FuturesPositionRisk;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin.SubMarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin.SubMarginAccountAssetDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin.SummarySubMarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.*;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.FutureAssetTransferHistory.FuturesType;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction.IPRestriction;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction.IPRestrictionUpdated;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction.IPRestrictionUpdated.IPStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers.*;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers.SubAccountTransfer.SubMarginTransfer;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers.SubAccountTransferLog.TransferDirection;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.AccountType;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.FuturesAccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit.DepositStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.DepositAddress;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.FutureAssetTransferHistory.FuturesType.USDT_MARGINED;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.AccountType.futures;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.returnAccountSnapshot;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.SpotBalance;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.DepositAddress.returnDepositAddress;
import static java.lang.Long.parseLong;

/**
 * The {@code BinanceSubAccountManager} class is useful to manage subaccount endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-endpoints">
 * Sub-Account Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceSubAccountManager extends BinanceSignedManager {

    /**
     * {@code SUB_ACCOUNT_VIRTUAL_SUB_ACCOUNT_ENDPOINT} is constant for SUB_ACCOUNT_VIRTUAL_SUB_ACCOUNT_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_VIRTUAL_SUB_ACCOUNT_ENDPOINT = "/sapi/v1/sub-account/virtualSubAccount";

    /**
     * {@code SUB_ACCOUNT_LIST_ENDPOINT} is constant for SUB_ACCOUNT_LIST_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_LIST_ENDPOINT = "/sapi/v1/sub-account/list";

    /**
     * {@code SUB_ACCOUNT_SUB_TRANSFER_HISTORY_ENDPOINT} is constant for SUB_ACCOUNT_SUB_TRANSFER_HISTORY_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_SUB_TRANSFER_HISTORY_ENDPOINT = "/sapi/v1/sub-account/sub/transfer/history";

    /**
     * {@code SUB_ACCOUNT_FUTURES_INTERNAL_TRANSFER_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_INTERNAL_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_INTERNAL_TRANSFER_ENDPOINT = "/sapi/v1/sub-account/futures/internalTransfer";

    /**
     * {@code SUB_ACCOUNT_ASSETS_ENDPOINT} is constant for SUB_ACCOUNT_ASSETS_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_ASSETS_ENDPOINT = "/sapi/v3/sub-account/assets";

    /**
     * {@code SUB_ACCOUNT_SPOT_SUMMARY_ENDPOINT} is constant for SUB_ACCOUNT_SPOT_SUMMARY_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_SPOT_SUMMARY_ENDPOINT = "/sapi/v1/sub-account/spotSummary";

    /**
     * {@code SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_ADDRESS_ENDPOINT} is constant for SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_ADDRESS_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_ADDRESS_ENDPOINT = "/sapi/v1/capital/deposit/subAddress";

    /**
     * {@code SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_HISREC_ENDPOINT} is constant for SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_HISREC_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_HISREC_ENDPOINT = "/sapi/v1/capital/deposit/subHisrec";

    /**
     * {@code SUB_ACCOUNT_STATUS_ENDPOINT} is constant for SUB_ACCOUNT_STATUS_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_STATUS_ENDPOINT = "/sapi/v1/sub-account/status";

    /**
     * {@code SUB_ACCOUNT_MARGIN_ENABLE_ENDPOINT} is constant for SUB_ACCOUNT_MARGIN_ENABLE_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_MARGIN_ENABLE_ENDPOINT = "/sapi/v1/sub-account/margin/enable";

    /**
     * {@code SUB_ACCOUNT_MARGIN_ACCOUNT_ENDPOINT} is constant for SUB_ACCOUNT_MARGIN_ACCOUNT_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_MARGIN_ACCOUNT_ENDPOINT = "/sapi/v1/sub-account/margin/account";

    /**
     * {@code SUB_ACCOUNT_MARGIN_ACCOUNT_SUMMARY_ENDPOINT} is constant for SUB_ACCOUNT_MARGIN_ACCOUNT_SUMMARY_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_MARGIN_ACCOUNT_SUMMARY_ENDPOINT = "/sapi/v1/sub-account/margin/accountSummary";

    /**
     * {@code SUB_ACCOUNT_FUTURES_ENABLE_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_ENABLE_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_ENABLE_ENDPOINT = "/sapi/v1/sub-account/futures/enable";

    /**
     * {@code SUB_ACCOUNT_FUTURES_ACCOUNT_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_ACCOUNT_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_ACCOUNT_ENDPOINT = "/sapi/v1/sub-account/futures/account";

    /**
     * {@code SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_ENDPOINT = "/sapi/v1/sub-account/futures/accountSummary";

    /**
     * {@code SUB_ACCOUNT_FUTURES_POSITION_RISK_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_POSITION_RISK_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_POSITION_RISK_ENDPOINT = "/sapi/v1/sub-account/futures/positionRisk";

    /**
     * {@code SUB_ACCOUNT_FUTURES_TRANSFER_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_TRANSFER_ENDPOINT = "/sapi/v1/sub-account/futures/transfer";

    /**
     * {@code SUB_ACCOUNT_MARGIN_TRANSFER_ENDPOINT} is constant for SUB_ACCOUNT_MARGIN_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_MARGIN_TRANSFER_ENDPOINT = "/sapi/v1/sub-account/margin/transfer ";

    /**
     * {@code SUB_ACCOUNT_TRANSFER_SUB_TO_SUB_ENDPOINT} is constant for SUB_ACCOUNT_TRANSFER_SUB_TO_SUB_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_TRANSFER_SUB_TO_SUB_ENDPOINT = "/sapi/v1/sub-account/transfer/subToSub";

    /**
     * {@code SUB_ACCOUNT_TRANSFER_SUB_TO_MASTER_ENDPOINT} is constant for SUB_ACCOUNT_TRANSFER_SUB_TO_MASTER_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_TRANSFER_SUB_TO_MASTER_ENDPOINT = "/sapi/v1/sub-account/transfer/subToMaster";

    /**
     * {@code SUB_ACCOUNT_TRANSFER_SUB_USER_HISTORY_ENDPOINT} is constant for SUB_ACCOUNT_TRANSFER_SUB_USER_HISTORY_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_TRANSFER_SUB_USER_HISTORY_ENDPOINT = "/sapi/v1/sub-account/transfer/subUserHistory";

    /**
     * {@code SUB_ACCOUNT_UNIVERSAL_TRANSFER_ENDPOINT} is constant for SUB_ACCOUNT_UNIVERSAL_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_UNIVERSAL_TRANSFER_ENDPOINT = "/sapi/v1/sub-account/universalTransfer";

    /**
     * {@code SUB_ACCOUNT_FUTURES_ACCOUNT_V2_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_ACCOUNT_V2_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_ACCOUNT_V2_ENDPOINT = "/sapi/v2/sub-account/futures/account";

    /**
     * {@code SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_V2_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_V2_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_V2_ENDPOINT = "/sapi/v2/sub-account/futures/accountSummary";

    /**
     * {@code SUB_ACCOUNT_FUTURES_POSITION_RISK_V2_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_POSITION_RISK_V2_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_POSITION_RISK_V2_ENDPOINT = "/sapi/v2/sub-account/futures/positionRisk";

    /**
     * {@code SUB_ACCOUNT_BLVT_ENABLE_ENDPOINT} is constant for SUB_ACCOUNT_BLVT_ENABLE_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_BLVT_ENABLE_ENDPOINT = "/sapi/v1/sub-account/blvt/enable";

    /**
     * {@code SUB_ACCOUNT_API_IP_RESTRICTION_ENDPOINT} is constant for SUB_ACCOUNT_API_IP_RESTRICTION_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_API_IP_RESTRICTION_ENDPOINT = "/sapi/v1/sub-account/subAccountApi/ipRestriction";

    /**
     * {@code SUB_ACCOUNT_API_IP_LIST_ENDPOINT} is constant for SUB_ACCOUNT_API_IP_LIST_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_API_IP_LIST_ENDPOINT = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList";

    /**
     * {@code SUB_ACCOUNT_API_IP_RESTRICTION_V2_ENDPOINT} is constant for SUB_ACCOUNT_API_IP_RESTRICTION_V2_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_API_IP_RESTRICTION_V2_ENDPOINT = "/sapi/v2/sub-account/subAccountApi/ipRestriction";

    /**
     * {@code MANAGED_SUB_ACCOUNT_DEPOSIT_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_DEPOSIT_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_DEPOSIT_ENDPOINT = "/sapi/v1/managed-subaccount/deposit";

    /**
     * {@code MANAGED_SUB_ACCOUNT_ASSET_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_ASSET_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_ASSET_ENDPOINT = "/sapi/v1/managed-subaccount/asset";

    /**
     * {@code MANAGED_SUB_ACCOUNT_WITHDRAW_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_WITHDRAW_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_WITHDRAW_ENDPOINT = "/sapi/v1/managed-subaccount/withdraw";

    /**
     * {@code MANAGED_SUB_ACCOUNT_SNAPSHOT_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_SNAPSHOT_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_SNAPSHOT_ENDPOINT = "/sapi/v1/managed-subaccount/accountSnapshot";

    /**
     * {@code MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_INVESTOR_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_INVESTOR_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_INVESTOR_ENDPOINT = "/sapi/v1/managed-subaccount/queryTransLogForInvestor";

    /**
     * {@code MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_TRADE_PARENT_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_TRADE_PARENT_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_TRADE_PARENT_ENDPOINT = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent";

    /**
     * {@code MANAGED_SUB_ACCOUNT_FETCH_FUTURE_ASSET_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_FETCH_FUTURE_ASSET_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_FETCH_FUTURE_ASSET_ENDPOINT = "/sapi/v1/managed-subaccount/fetch-future-asset";

    /**
     * {@code MANAGED_SUB_ACCOUNT_MARGIN_ASSET_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_MARGIN_ASSET_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_MARGIN_ASSET_ENDPOINT = "/sapi/v1/managed-subaccount/marginAsset";

    /**
     * {@code SUB_ACCOUNT_ASSETS_V4_ENDPOINT} is constant for SUB_ACCOUNT_ASSETS_V4_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_ASSETS_V4_ENDPOINT = "/sapi/v4/sub-account/assets";

    /**
     * {@code MANAGED_SUB_ACCOUNT_INFO_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_INFO_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_INFO_ENDPOINT = "/sapi/v1/managed-subaccount/info";

    /**
     * {@code SUB_ACCOUNT_TRANSACTION_STATISTICS_ENDPOINT} is constant for SUB_ACCOUNT_TRANSACTION_STATISTICS_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_TRANSACTION_STATISTICS_ENDPOINT = "/sapi/v1/sub-account/transaction-tatistics";

    /**
     * {@code MANAGED_SUB_ACCOUNT_DEPOSIT_ADDRESS_ENDPOINT} is constant for MANAGED_SUB_ACCOUNT_DEPOSIT_ADDRESS_ENDPOINT's endpoint
     **/
    public static final String MANAGED_SUB_ACCOUNT_DEPOSIT_ADDRESS_ENDPOINT = "/sapi/v1/managed-subaccount/deposit/address";

    /**
     * Constructor to init a {@link BinanceSubAccountManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceSubAccountManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSubAccountManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceSubAccountManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSubAccountManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceSubAccountManager(String baseEndpoint, int timeout, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSubAccountManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceSubAccountManager(String baseEndpoint, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSubAccountManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceSubAccountManager}'s manager without re-insert
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
    public BinanceSubAccountManager() {
        super();
    }

    /**
     * Request to create a virtual subaccount
     *
     * @param subAccountString: will be created a virtual email using that string for you to register
     * @return virtual subaccount as {@link String}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-endpoints">
     * Create a Virtual Sub-account(For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/virtualSubAccount")
    public String createVirtualSubAccount(String subAccountString) throws Exception {
        return createVirtualSubAccount(subAccountString, LIBRARY_OBJECT);
    }

    /**
     * Request to create a virtual subaccount
     *
     * @param subAccountString: will be created a virtual email using that string for you to register
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return virtual subaccount as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-endpoints">
     * Create a Virtual Sub-account(For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/virtualSubAccount")
    public <T> T createVirtualSubAccount(String subAccountString, ReturnFormat format) throws Exception {
        return createVirtualSubAccount(subAccountString, -1, format);
    }

    /**
     * Request to create a virtual subaccount
     *
     * @param subAccountString: will be created a virtual email using that string for you to register
     * @param recvWindow:       request is valid for in ms, must be less than 60000
     * @return virtual subaccount as {@link String}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-endpoints">
     * Create a Virtual Sub-account(For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/virtualSubAccount")
    public String createVirtualSubAccount(String subAccountString, long recvWindow) throws Exception {
        return createVirtualSubAccount(subAccountString, LIBRARY_OBJECT);
    }

    /**
     * Request to create a virtual subaccount
     *
     * @param subAccountString: will be created a virtual email using that string for you to register
     * @param recvWindow:       request is valid for in ms, must be less than 60000
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return virtual subaccount as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-endpoints">
     * Create a Virtual Sub-account(For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/virtualSubAccount")
    public <T> T createVirtualSubAccount(String subAccountString, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("subAccountString", subAccountString);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String subAccount = sendPostSignedRequest(SUB_ACCOUNT_VIRTUAL_SUB_ACCOUNT_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(subAccount);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(subAccount).getString("email");
            default:
                return (T) subAccount;
        }
    }

    /**
     * Request to get subaccount list <br>
     * No-any params required
     *
     * @return subaccount list as {@link ArrayList} of {@link SubAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-list-for-master-account">
     * Query Sub-account List (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/list")
    public ArrayList<SubAccount> getSubAccountList() throws Exception {
        return getSubAccountList(LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-list-for-master-account">
     * Query Sub-account List (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/list")
    public <T> T getSubAccountList(ReturnFormat format) throws Exception {
        return getSubAccountList(null, format);
    }

    /**
     * Request to get subaccount list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "isFreeze"} -> whether the subaccount is frozen - [BOOLEAN]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 200 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount list as {@link ArrayList} of {@link SubAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-list-for-master-account">
     * Query Sub-account List (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/list")
    public ArrayList<SubAccount> getSubAccountList(Params extraParams) throws Exception {
        return getSubAccountList(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "isFreeze"} -> whether the subaccount is frozen - [BOOLEAN]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 200 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-list-for-master-account">
     * Query Sub-account List (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/list")
    public <T> T getSubAccountList(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String subAccounts = sendGetSignedRequest(SUB_ACCOUNT_LIST_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(subAccounts);
            case LIBRARY_OBJECT:
                ArrayList<SubAccount> accounts = new ArrayList<>();
                JSONArray jAccounts = JsonHelper.getJSONArray(new JSONObject(subAccounts), subAccounts, new JSONArray());
                for (int j = 0; j < jAccounts.length(); j++)
                    accounts.add(new SubAccount(jAccounts.getJSONObject(j)));
                return (T) accounts;
            default:
                return (T) subAccounts;
        }
    }

    /**
     * Request to get subaccount spot asset transfer history <br>
     * No-any params required
     *
     * @return subaccount spot asset transfer history as {@link ArrayList} of {@link SpotAssetTransfer} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-asset-transfer-history-for-master-account">
     * Query Sub-account Spot Asset Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/sub/transfer/history")
    public ArrayList<SpotAssetTransfer> getSubAccountSpotTransferHistory() throws Exception {
        return getSubAccountSpotTransferHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount spot asset transfer history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount spot asset transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-asset-transfer-history-for-master-account">
     * Query Sub-account Spot Asset Transfer History (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/sub/transfer/history")
    public <T> T getSubAccountSpotTransferHistory(ReturnFormat format) throws Exception {
        return getSubAccountSpotTransferHistory(null, format);
    }

    /**
     * Request to get subaccount spot asset transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromEmail"} -> from email of the records to fetch - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toEmail"} -> to email of the records to fetch - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount spot asset transfer history as {@link ArrayList} of {@link SpotAssetTransfer} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-asset-transfer-history-for-master-account">
     * Query Sub-account Spot Asset Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/sub/transfer/history")
    public ArrayList<SpotAssetTransfer> getSubAccountSpotTransferHistory(Params extraParams) throws Exception {
        return getSubAccountSpotTransferHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount spot asset transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromEmail"} -> from email of the records to fetch - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toEmail"} -> to email of the records to fetch - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount spot asset transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-asset-transfer-history-for-master-account">
     * Query Sub-account Spot Asset Transfer History (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/sub/transfer/history")
    public <T> T getSubAccountSpotTransferHistory(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String subAccounts = sendGetSignedRequest(SUB_ACCOUNT_SUB_TRANSFER_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(subAccounts);
            case LIBRARY_OBJECT:
                ArrayList<SpotAssetTransfer> transfers = new ArrayList<>();
                JSONArray jTransfers = new JSONArray(subAccounts);
                for (int j = 0; j < jTransfers.length(); j++)
                    transfers.add(new SpotAssetTransfer(jTransfers.getJSONObject(j)));
                return (T) transfers;
            default:
                return (T) subAccounts;
        }
    }

    /**
     * Request to get subaccount futures asset transfer history
     *
     * @param email: subaccount email
     * @param type:  futures type to fetch
     * @return futures asset transfer history as {@link FutureAssetTransferHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-futures-asset-transfer-history-for-master-account">
     * Query Sub-account Futures Asset Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public FutureAssetTransferHistory getSubAccountFutureTransferHistory(String email, FuturesType type) throws Exception {
        return getSubAccountFutureTransferHistory(email, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount futures asset transfer history
     *
     * @param email:  subaccount email
     * @param type:   futures type to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return futures asset transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-futures-asset-transfer-history-for-master-account">
     * Query Sub-account Futures Asset Transfer History (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public <T> T getSubAccountFutureTransferHistory(String email, FuturesType type, ReturnFormat format) throws Exception {
        return getSubAccountFutureTransferHistory(email, type, null, format);
    }

    /**
     * Request to get subaccount futures asset transfer history
     *
     * @param email:       subaccount email
     * @param type:        futures type to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return futures asset transfer history as {@link FutureAssetTransferHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-futures-asset-transfer-history-for-master-account">
     * Query Sub-account Futures Asset Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public FutureAssetTransferHistory getSubAccountFutureTransferHistory(String email, FuturesType type,
                                                                         Params extraParams) throws Exception {
        return getSubAccountFutureTransferHistory(email, type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount futures asset transfer history
     *
     * @param email:       subaccount email
     * @param type:        futures type to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return futures asset transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-futures-asset-transfer-history-for-master-account">
     * Query Sub-account Futures Asset Transfer History (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public <T> T getSubAccountFutureTransferHistory(String email, FuturesType type, Params extraParams,
                                                    ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("futuresType", type);
        String historyResponse = sendGetSignedRequest(SUB_ACCOUNT_FUTURES_INTERNAL_TRANSFER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new FutureAssetTransferHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to subaccount futures asset transfer
     *
     * @param fromEmail: from email value
     * @param toEmail:   to email value
     * @param type:      futures type to execute
     * @param asset:     asset to transfer
     * @param amount:    amount to transfer
     * @return subaccount futures asset transfer as {@link FutureAssetTransferResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-futures-asset-transfer-for-master-account">
     * Sub-account Futures Asset Transfer (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public FutureAssetTransferResult execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type,
                                                             String asset, double amount) throws Exception {
        return execFutureAssetTransfer(fromEmail, toEmail, type, asset, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to subaccount futures asset transfer
     *
     * @param fromEmail: from email value
     * @param toEmail:   to email value
     * @param type:      futures type to execute
     * @param asset:     asset to transfer
     * @param amount:    amount to transfer
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return subaccount futures asset transfer as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-futures-asset-transfer-for-master-account">
     * Sub-account Futures Asset Transfer (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public <T> T execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type, String asset,
                                         double amount, ReturnFormat format) throws Exception {
        return execFutureAssetTransfer(fromEmail, toEmail, type, asset, amount, -1, format);
    }

    /**
     * Request to subaccount futures asset transfer
     *
     * @param fromEmail:  from email value
     * @param toEmail:    to email value
     * @param type:       futures type to execute
     * @param asset:      asset to transfer
     * @param amount:     amount to transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount futures asset transfer as {@link FutureAssetTransferResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-futures-asset-transfer-for-master-account">
     * Sub-account Futures Asset Transfer (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public FutureAssetTransferResult execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type,
                                                             String asset, double amount, long recvWindow) throws Exception {
        return execFutureAssetTransfer(fromEmail, toEmail, type, asset, amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to subaccount futures asset transfer
     *
     * @param fromEmail:  from email value
     * @param toEmail:    to email value
     * @param type:       futures type to execute
     * @param asset:      asset to transfer
     * @param amount:     amount to transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount futures asset transfer as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-futures-asset-transfer-for-master-account">
     * Sub-account Futures Asset Transfer (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/internalTransfer")
    public <T> T execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type, String asset,
                                         double amount, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("fromEmail", fromEmail);
        payload.addParam("toEmail", toEmail);
        payload.addParam("futuresType", type);
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String transferResponse = sendPostSignedRequest(SUB_ACCOUNT_FUTURES_INTERNAL_TRANSFER_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(transferResponse);
            case LIBRARY_OBJECT:
                return (T) new FutureAssetTransferResult(new JSONObject(transferResponse));
            default:
                return (T) transferResponse;
        }
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param subAccount: subaccount from fetch the list
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public ArrayList<SpotBalance> getSubAccountAssets(SubAccount subAccount) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param subAccount: subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public <T> T getSubAccountAssets(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), format);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param email: subaccount email from fetch the list
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public ArrayList<SpotBalance> getSubAccountAssets(String email) throws Exception {
        return getSubAccountAssets(email, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param email:  subaccount email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public <T> T getSubAccountAssets(String email, ReturnFormat format) throws Exception {
        return getSubAccountAssets(email, -1, format);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param subAccount: subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public ArrayList<SpotBalance> getSubAccountAssets(SubAccount subAccount, long recvWindow) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param subAccount: subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public <T> T getSubAccountAssets(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param email:      subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public ArrayList<SpotBalance> getSubAccountAssets(String email, long recvWindow) throws Exception {
        return getSubAccountAssets(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount assets
     *
     * @param email:      subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
     * Query Sub-account Assets (For Master Account)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v3/sub-account/assets")
    public <T> T getSubAccountAssets(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnBalances(sendGetSignedRequest(SUB_ACCOUNT_ASSETS_ENDPOINT, createEmailPayload(email, recvWindow,
                true)), format);
    }

    /**
     * Request to get BTC valued asset summary of subaccounts <br>
     * No-any params required
     *
     * @return BTC valued asset summary of subaccounts as {@link SpotAssetsSummary} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-assets-summary-for-master-account">
     * Query Sub-account Spot Assets Summary (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/spotSummary")
    public SpotAssetsSummary getSubAccountSpotAssetsSummary() throws Exception {
        return getSubAccountSpotAssetsSummary(LIBRARY_OBJECT);
    }

    /**
     * Request to get BTC valued asset summary of subaccounts
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return BTC valued asset summary of subaccounts as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-assets-summary-for-master-account">
     * Query Sub-account Spot Assets Summary (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/spotSummary")
    public <T> T getSubAccountSpotAssetsSummary(ReturnFormat format) throws Exception {
        return getSubAccountSpotAssetsSummary(null, format);
    }

    /**
     * Request to get BTC valued asset summary of subaccounts
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results per page, max 20 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return BTC valued asset summary of subaccounts as {@link SpotAssetsSummary} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-assets-summary-for-master-account">
     * Query Sub-account Spot Assets Summary (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/spotSummary")
    public SpotAssetsSummary getSubAccountSpotAssetsSummary(Params extraParams) throws Exception {
        return getSubAccountSpotAssetsSummary(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get BTC valued asset summary of subaccounts
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results per page, max 20 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return BTC valued asset summary of subaccounts as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-assets-summary-for-master-account">
     * Query Sub-account Spot Assets Summary (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/spotSummary")
    public <T> T getSubAccountSpotAssetsSummary(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String assetsResponse = sendGetSignedRequest(SUB_ACCOUNT_SPOT_SUMMARY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetsResponse);
            case LIBRARY_OBJECT:
                return (T) new SpotAssetsSummary(new JSONObject(assetsResponse));
            default:
                return (T) assetsResponse;
        }
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param subAccount: subaccount from fetch the deposit address
     * @param coin:       coin of the deposit address
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public DepositAddress getSubAccountDepositAddress(SubAccount subAccount, String coin) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param subAccount: subaccount from fetch the deposit address
     * @param coin:       coin of the deposit address
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public <T> T getSubAccountDepositAddress(SubAccount subAccount, String coin, ReturnFormat format) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, format);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param email: subaccount email from fetch the deposit address
     * @param coin:  coin of the deposit address
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public DepositAddress getSubAccountDepositAddress(String email, String coin) throws Exception {
        return getSubAccountDepositAddress(email, coin, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param email:  subaccount email from fetch the deposit address
     * @param coin:   coin of the deposit address
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public <T> T getSubAccountDepositAddress(String email, String coin, ReturnFormat format) throws Exception {
        return getSubAccountDepositAddress(email, coin, null, format);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param subAccount:  subaccount from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public DepositAddress getSubAccountDepositAddress(SubAccount subAccount, String coin,
                                                      Params extraParams) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param subAccount:  subaccount from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public <T> T getSubAccountDepositAddress(SubAccount subAccount, String coin, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, format);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param email:       subaccount email from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public DepositAddress getSubAccountDepositAddress(String email, String coin, Params extraParams) throws Exception {
        return getSubAccountDepositAddress(email, coin, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit address
     *
     * @param email:       subaccount email from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-address-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subAddress")
    public <T> T getSubAccountDepositAddress(String email, String coin, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return returnDepositAddress(sendGetSignedRequest(SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_ADDRESS_ENDPOINT,
                createDepositQuery(email, coin, extraParams)), format);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param subAccount: subaccount from fetch the list
     * @return subaccount deposit history as {@link ArrayList} of {@link DepositHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(SubAccount subAccount) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param subAccount: subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount deposit history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public <T> T getSubAccountDepositHistory(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), format);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param email: subaccount email from fetch the list
     * @return subaccount deposit history as {@link ArrayList} of {@link DepositHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(String email) throws Exception {
        return getSubAccountDepositHistory(email, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param email:  subaccount email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount deposit history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public <T> T getSubAccountDepositHistory(String email, ReturnFormat format) throws Exception {
        return getSubAccountDepositHistory(email, null, format);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param subAccount:  subaccount from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> status value, constants available {@link DepositStatus} - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset from fetch the results - [INT, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> transaction id from fetch the results - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount deposit history as {@link ArrayList} of {@link DepositHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(SubAccount subAccount,
                                                                     Params extraParams) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param subAccount:  subaccount from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> status value, constants available {@link DepositStatus} - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset from fetch the results - [INT, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> transaction id from fetch the results - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount deposit history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public <T> T getSubAccountDepositHistory(SubAccount subAccount, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), extraParams, format);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param email:       subaccount email from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> status value, constants available {@link DepositStatus} - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset from fetch the results - [INT, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> transaction id from fetch the results - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount deposit history as {@link ArrayList} of {@link DepositHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(String email, Params extraParams) throws Exception {
        return getSubAccountDepositHistory(email, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch subaccount deposit history
     *
     * @param email:       subaccount email from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> status value, constants available {@link DepositStatus} - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "offset"} -> offset from fetch the results - [INT, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> transaction id from fetch the results - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount deposit history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
     * Get Sub-account Deposit Address (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/capital/deposit/subHisrec")
    public <T> T getSubAccountDepositHistory(String email, Params extraParams, ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(extraParams);
        query.addParam("email", email);
        String historyResponse = sendGetSignedRequest(SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_HISREC_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONArray(historyResponse);
            case LIBRARY_OBJECT:
                ArrayList<DepositHistoryItem> history = new ArrayList<>();
                JSONArray jHistory = new JSONArray(historyResponse);
                for (int j = 0; j < jHistory.length(); j++)
                    history.add(new DepositHistoryItem(jHistory.getJSONObject(j)));
                return (T) history;
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get subaccount status <br>
     * No-any params required
     *
     * @return subaccount status as {@link ArrayList} of {@link SubAccountStatus} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-39-s-status-on-margin-futures-for-master-account">
     * Get Sub-account's Status on Margin/Futures (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/status")
    public ArrayList<SubAccountStatus> getSubAccountStatus() throws Exception {
        return getSubAccountStatus(LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount status
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount status as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-39-s-status-on-margin-futures-for-master-account">
     * Get Sub-account's Status on Margin/Futures (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/status")
    public <T> T getSubAccountStatus(ReturnFormat format) throws Exception {
        return getSubAccountStatus(null, format);
    }

    /**
     * Request to get subaccount status
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount status as {@link ArrayList} of {@link SubAccountStatus} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-39-s-status-on-margin-futures-for-master-account">
     * Get Sub-account's Status on Margin/Futures (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/status")
    public ArrayList<SubAccountStatus> getSubAccountStatus(Params extraParams) throws Exception {
        return getSubAccountStatus(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount status
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount status as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-39-s-status-on-margin-futures-for-master-account">
     * Get Sub-account's Status on Margin/Futures (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/status")
    public <T> T getSubAccountStatus(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String statusResponse = sendGetSignedRequest(SUB_ACCOUNT_STATUS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(statusResponse);
            case LIBRARY_OBJECT:
                ArrayList<SubAccountStatus> statuses = new ArrayList<>();
                JSONArray jStatuses = new JSONArray(statusResponse);
                for (int j = 0; j < jStatuses.length(); j++)
                    statuses.add(new SubAccountStatus(jStatuses.getJSONObject(j)));
                return (T) statuses;
            default:
                return (T) statusResponse;
        }
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public SubAccountEnabledResult enableMarginSubAccount(SubAccount subAccount) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public <T> T enableMarginSubAccount(SubAccount subAccount, ReturnFormat format) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), format);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param email: the subaccount email to enable
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public SubAccountEnabledResult enableMarginSubAccount(String email) throws Exception {
        return enableMarginSubAccount(email, LIBRARY_OBJECT);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param email:  the subaccount email to enable
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public <T> T enableMarginSubAccount(String email, ReturnFormat format) throws Exception {
        return enableMarginSubAccount(email, -1, format);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public SubAccountEnabledResult enableMarginSubAccount(SubAccount subAccount, long recvWindow) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public <T> T enableMarginSubAccount(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param email:      the subaccount email to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public SubAccountEnabledResult enableMarginSubAccount(String email, long recvWindow) throws Exception {
        return enableMarginSubAccount(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable margin for subaccount
     *
     * @param email:      the subaccount email to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
     * Enable Margin for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/enable")
    public <T> T enableMarginSubAccount(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnEnabledResult(sendPostSignedRequest(SUB_ACCOUNT_MARGIN_ENABLE_ENDPOINT, createEmailPayload(email,
                recvWindow, false)), format);
    }

    /**
     * Request to get detail on sub-account's margin account
     *
     * @param email: the subaccount email to fetch details
     * @return subaccount's margin account as {@link SubMarginAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-margin-account-for-master-account">
     * Get Detail on Sub-account's Margin Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/account")
    public SubMarginAccount getSubMarginAccountDetail(String email) throws Exception {
        return getSubMarginAccountDetail(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get detail on sub-account's margin account
     *
     * @param email:  the subaccount email to fetch details
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount's margin account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-margin-account-for-master-account">
     * Get Detail on Sub-account's Margin Account (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/account")
    public <T> T getSubMarginAccountDetail(String email, ReturnFormat format) throws Exception {
        return getSubMarginAccountDetail(email, -1, format);
    }

    /**
     * Request to get detail on sub-account's margin account
     *
     * @param email:      the subaccount email to fetch details
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount's margin account as {@link SubMarginAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-margin-account-for-master-account">
     * Get Detail on Sub-account's Margin Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/account")
    public SubMarginAccount getSubMarginAccountDetail(String email, long recvWindow) throws Exception {
        return getSubMarginAccountDetail(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get detail on sub-account's margin account
     *
     * @param email:      the subaccount email to fetch details
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount's margin account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-margin-account-for-master-account">
     * Get Detail on Sub-account's Margin Account (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/account")
    public <T> T getSubMarginAccountDetail(String email, long recvWindow, ReturnFormat format) throws Exception {
        String accountResponse = sendGetSignedRequest(SUB_ACCOUNT_MARGIN_ACCOUNT_ENDPOINT, createEmailPayload(email,
                recvWindow, true));
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountResponse);
            case LIBRARY_OBJECT:
                return (T) new SubMarginAccount(new JSONObject(accountResponse));
            default:
                return (T) accountResponse;
        }
    }

    /**
     * Request to get summary of subaccount margin account <br>
     * No-any params required
     *
     * @return summary subaccount margin account as {@link SummarySubMarginAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-margin-account-for-master-account">
     * Get Summary of Sub-account's Margin Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/accountSummary")
    public SummarySubMarginAccount getSummarySubMarginAccount() throws Exception {
        return getSummarySubMarginAccount(LIBRARY_OBJECT);
    }

    /**
     * Request to get summary of subaccount margin account
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return summary subaccount margin account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-margin-account-for-master-account">
     * Get Summary of Sub-account's Margin Account (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/accountSummary")
    public <T> T getSummarySubMarginAccount(ReturnFormat format) throws Exception {
        return getSummarySubMarginAccount(-1, format);
    }

    /**
     * Request to get summary of subaccount margin account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return summary subaccount margin account as {@link SummarySubMarginAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-margin-account-for-master-account">
     * Get Summary of Sub-account's Margin Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/accountSummary")
    public SummarySubMarginAccount getSummarySubMarginAccount(long recvWindow) throws Exception {
        return getSummarySubMarginAccount(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary of subaccount margin account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return summary subaccount margin account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-margin-account-for-master-account">
     * Get Summary of Sub-account's Margin Account (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/margin/accountSummary")
    public <T> T getSummarySubMarginAccount(long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        String summaryResponse = sendGetSignedRequest(SUB_ACCOUNT_MARGIN_ACCOUNT_SUMMARY_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(summaryResponse);
            case LIBRARY_OBJECT:
                return (T) new SummarySubMarginAccount(new JSONObject(summaryResponse));
            default:
                return (T) summaryResponse;
        }
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public SubAccountEnabledResult enableFuturesSubAccount(SubAccount subAccount) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public <T> T enableFuturesSubAccount(SubAccount subAccount, ReturnFormat format) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), format);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param email: the subaccount email to enable
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public SubAccountEnabledResult enableFuturesSubAccount(String email) throws Exception {
        return enableFuturesSubAccount(email, LIBRARY_OBJECT);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param email:  the subaccount email to enable
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public <T> T enableFuturesSubAccount(String email, ReturnFormat format) throws Exception {
        return enableFuturesSubAccount(email, -1, format);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public SubAccountEnabledResult enableFuturesSubAccount(SubAccount subAccount, long recvWindow) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public <T> T enableFuturesSubAccount(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param email:      the subaccount email to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public SubAccountEnabledResult enableFuturesSubAccount(String email, long recvWindow) throws Exception {
        return enableFuturesSubAccount(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable futures for subaccount
     *
     * @param email:      the subaccount email to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
     * Enable Futures for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/enable")
    public <T> T enableFuturesSubAccount(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnEnabledResult(sendPostSignedRequest(SUB_ACCOUNT_FUTURES_ENABLE_ENDPOINT, createEmailPayload(email,
                recvWindow, false)), format);
    }

    /**
     * Request to get the sub futures account details
     *
     * @param email: the subaccount email to fetch
     * @return sub futures account details as {@link SubFuturesAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
     * Get Detail on Sub-account's Futures Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/account")
    public SubFuturesAccount getSubFuturesAccountDetail(String email) throws Exception {
        return getSubFuturesAccountDetail(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get the sub futures account details
     *
     * @param email:  the subaccount email to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
     * Get Detail on Sub-account's Futures Account (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/account")
    public <T> T getSubFuturesAccountDetail(String email, ReturnFormat format) throws Exception {
        return getSubFuturesAccountDetail(email, -1, format);
    }

    /**
     * Request to get the sub futures account details
     *
     * @param email:      the subaccount email to fetch
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return sub futures account details as {@link SubFuturesAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
     * Get Detail on Sub-account's Futures Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/account")
    public SubFuturesAccount getSubFuturesAccountDetail(String email, long recvWindow) throws Exception {
        return getSubFuturesAccountDetail(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the sub futures account details
     *
     * @param email:      the subaccount email to fetch
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
     * Get Detail on Sub-account's Futures Account (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/account")
    public <T> T getSubFuturesAccountDetail(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnSubFuturesAccount(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_ACCOUNT_ENDPOINT,
                createEmailPayload(email, recvWindow, true)), format);
    }

    /**
     * Request to get the summary sub futures account  <br>
     * No-any params required
     *
     * @return summary sub futures account as {@link SummarySubFuturesAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
     * Get Summary of Sub-account's Futures Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/accountSummary")
    public SummarySubFuturesAccount getSummarySubFuturesAccount() throws Exception {
        return getSummarySubFuturesAccount(LIBRARY_OBJECT);
    }

    /**
     * Request to get the summary sub futures account
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return summary futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
     * Get Summary of Sub-account's Futures Account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/accountSummary")
    public <T> T getSummarySubFuturesAccount(ReturnFormat format) throws Exception {
        return getSummarySubFuturesAccount(-1, format);
    }

    /**
     * Request to get the summary sub futures account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return summary sub futures account as {@link SummarySubFuturesAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
     * Get Summary of Sub-account's Futures Account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/accountSummary")
    public SummarySubFuturesAccount getSummarySubFuturesAccount(long recvWindow) throws Exception {
        return getSummarySubFuturesAccount(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the summary sub futures account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return summary futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
     * Get Summary of Sub-account's Futures Account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/accountSummary")
    public <T> T getSummarySubFuturesAccount(long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        return returnSummarySubFuturesAccount(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_ENDPOINT, query),
                format);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @return futures position risk list as {@link ArrayList} of {@link FuturesPositionRisk} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param email: sub futures account email from fetch the list
     * @return futures position risk list as {@link ArrayList} of {@link FuturesPositionRisk} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(String email) throws Exception {
        return getFuturesPositionRisk(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param email:  sub futures account email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(String email, ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(email, -1, format);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param recvWindow:        request is valid for in ms, must be less than 60000
     * @return futures position risk list as {@link ArrayList} of {@link FuturesPositionRisk} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount,
                                                                 long recvWindow) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param recvWindow:        request is valid for in ms, must be less than 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, long recvWindow,
                                        ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param email:      sub futures account email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return futures position risk list as {@link ArrayList} of {@link FuturesPositionRisk} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(String email, long recvWindow) throws Exception {
        return getFuturesPositionRisk(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the futures position risk list
     *
     * @param email:      sub futures account email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-for-master-account">
     * Get Futures Position-Risk of Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnFuturesPositionRisk(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_POSITION_RISK_ENDPOINT,
                createEmailPayload(email, recvWindow, true)), format);
    }

    /**
     * Request to execute a futures transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public long execFuturesTransfer(SubAccount subAccount, String asset, double amount,
                                    FutureTransactionType type) throws Exception {
        return parseLong(execFuturesTransfer(subAccount.getEmail(), asset, amount, type, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a futures transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public <T> T execFuturesTransfer(SubAccount subAccount, String asset, double amount, FutureTransactionType type,
                                     ReturnFormat format) throws Exception {
        return execFuturesTransfer(subAccount.getEmail(), asset, amount, type, format);
    }

    /**
     * Request to execute a futures transfer
     *
     * @param email:  subaccount email from execute the transfer
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @param type:   type of the transfer to execute
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public long execFuturesTransfer(String email, String asset, double amount,
                                    FutureTransactionType type) throws Exception {
        return parseLong(execFuturesTransfer(email, asset, amount, type, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a futures transfer
     *
     * @param email:  subaccount email from execute the transfer
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @param type:   type of the transfer to execute
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public <T> T execFuturesTransfer(String email, String asset, double amount, FutureTransactionType type,
                                     ReturnFormat format) throws Exception {
        return execFuturesTransfer(email, asset, amount, type, -1, format);
    }

    /**
     * Request to execute a futures transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public long execFuturesTransfer(SubAccount subAccount, String asset, double amount, FutureTransactionType type,
                                    long recvWindow) throws Exception {
        return parseLong(execFuturesTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a futures transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public <T> T execFuturesTransfer(SubAccount subAccount, String asset, double amount, FutureTransactionType type,
                                     long recvWindow, ReturnFormat format) throws Exception {
        return execFuturesTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, format);
    }

    /**
     * Request to execute a futures transfer
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public long execFuturesTransfer(String email, String asset, double amount, FutureTransactionType type,
                                    long recvWindow) throws Exception {
        return parseLong(execFuturesTransfer(email, asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a futures transfer
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures-transfer-for-sub-account-for-master-account">
     * Futures Transfer for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/futures/transfer")
    public <T> T execFuturesTransfer(String email, String asset, double amount, FutureTransactionType type,
                                     long recvWindow, ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_FUTURES_TRANSFER_ENDPOINT, createTransferPayload(email,
                asset, amount, type, recvWindow)), format);
    }

    /**
     * Request to execute a margin transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public long execMarginTransfer(SubAccount subAccount, String asset, double amount,
                                   SubMarginTransfer type) throws Exception {
        return parseLong(execMarginTransfer(subAccount.getEmail(), asset, amount, type, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a margin transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public <T> T execMarginTransfer(SubAccount subAccount, String asset, double amount, SubMarginTransfer type,
                                    ReturnFormat format) throws Exception {
        return execMarginTransfer(subAccount.getEmail(), asset, amount, type, format);
    }

    /**
     * Request to execute a margin transfer
     *
     * @param email:  subaccount email from execute the transfer
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @param type:   type of the transfer to execute
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public long execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type) throws Exception {
        return parseLong(execMarginTransfer(email, asset, amount, type, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a margin transfer
     *
     * @param email:  subaccount email from execute the transfer
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @param type:   type of the transfer to execute
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public <T> T execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type,
                                    ReturnFormat format) throws Exception {
        return execMarginTransfer(email, asset, amount, type, -1, format);
    }

    /**
     * Request to execute a margin transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public long execMarginTransfer(SubAccount subAccount, String asset, double amount, SubMarginTransfer type,
                                   long recvWindow) throws Exception {
        return parseLong(execMarginTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a margin transfer
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public <T> T execMarginTransfer(SubAccount subAccount, String asset, double amount, SubMarginTransfer type,
                                    long recvWindow, ReturnFormat format) throws Exception {
        return execMarginTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, format);
    }

    /**
     * Request to execute a margin transfer
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public long execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type,
                                   long recvWindow) throws Exception {
        return parseLong(execMarginTransfer(email, asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a margin transfer
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-transfer-for-sub-account-for-master-account">
     * Margin Transfer for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/margin/transfer")
    public <T> T execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type,
                                    long recvWindow, ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_MARGIN_TRANSFER_ENDPOINT, createTransferPayload(email,
                asset, amount, type, recvWindow)), format);
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public long execSubToSubTransfer(SubAccount subAccount, String asset, double amount) throws Exception {
        return parseLong(execSubToSubTransfer(subAccount.getEmail(), asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public <T> T execSubToSubTransfer(SubAccount subAccount, String asset, double amount,
                                      ReturnFormat format) throws Exception {
        return execSubToSubTransfer(subAccount.getEmail(), asset, amount, format);
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param email:  subaccount email from execute the transfer
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public long execSubToSubTransfer(String email, String asset, double amount) throws Exception {
        return parseLong(execSubToSubTransfer(email, asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param email:  subaccount email from execute the transfer
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public <T> T execSubToSubTransfer(String email, String asset, double amount, ReturnFormat format) throws Exception {
        return execSubToSubTransfer(email, asset, amount, -1, format);
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public long execSubToSubTransfer(SubAccount subAccount, String asset, double amount, long recvWindow) throws Exception {
        return parseLong(execSubToSubTransfer(subAccount.getEmail(), asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param subAccount: subaccount from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public <T> T execSubToSubTransfer(SubAccount subAccount, String asset, double amount, long recvWindow,
                                      ReturnFormat format) throws Exception {
        return execSubToSubTransfer(subAccount.getEmail(), asset, amount, recvWindow, format);
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public long execSubToSubTransfer(String email, String asset, double amount, long recvWindow) throws Exception {
        return parseLong(execSubToSubTransfer(email, asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer to subaccount
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-sub-account-of-same-master-for-sub-account">
     * Transfer to Sub-account of Same Master (For Sub-account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToSub")
    public <T> T execSubToSubTransfer(String email, String asset, double amount, long recvWindow,
                                      ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_TRANSFER_SUB_TO_SUB_ENDPOINT, createTransferPayload(email,
                asset, amount, null, recvWindow)), format);
    }

    /**
     * Request to execute a transfer to master
     *
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-master-for-sub-account">
     * Transfer to Master (For Sub-account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToMaster")
    public long transferToMaster(String asset, double amount) throws Exception {
        return parseLong(transferToMaster(asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer to master
     *
     * @param asset:  asset of the transfer
     * @param amount: amount of the transfer
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-master-for-sub-account">
     * Transfer to Master (For Sub-account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToMaster")
    public <T> T transferToMaster(String asset, double amount, ReturnFormat format) throws Exception {
        return transferToMaster(asset, amount, -1, format);
    }

    /**
     * Request to execute a transfer to master
     *
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transfer id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-master-for-sub-account">
     * Transfer to Master (For Sub-account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToMaster")
    public long transferToMaster(String asset, double amount, long recvWindow) throws Exception {
        return parseLong(transferToMaster(asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer to master
     *
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#transfer-to-master-for-sub-account">
     * Transfer to Master (For Sub-account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/transfer/subToMaster")
    public <T> T transferToMaster(String asset, double amount, long recvWindow, ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_TRANSFER_SUB_TO_MASTER_ENDPOINT,
                createTransferPayload(null, asset, amount, null, recvWindow)), format);
    }

    /**
     * Method to create a transfer payload
     *
     * @param email:      subaccount email from execute the transfer
     * @param asset:      asset of the transfer
     * @param amount:     amount of the transfer
     * @param type:       type of the transfer to execute
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return payload as {@link Params}
     **/
    private <T> Params createTransferPayload(String email, String asset, double amount, T type, long recvWindow) {
        Params payload = new Params();
        if (email != null)
            payload.addParam("email", email);
        payload.addParam("asset", amount);
        payload.addParam("amount", amount);
        if (type != null)
            payload.addParam("type", type);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return payload;
    }

    /**
     * Method to create a transfer id
     *
     * @param transferResponse: obtained from Binance's response
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return transfer id as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTransferId(String transferResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(transferResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(new JSONObject(transferResponse).getLong("txnId"));
            default:
                return (T) transferResponse;
        }
    }

    /**
     * Request to get subaccount transfer history <br>
     * No-any params required
     *
     * @return subaccount transfer history as {@link ArrayList} of {@link SubAccountTransfer} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-transfer-history-for-sub-account">
     * Sub-account Transfer History (For Sub-account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transfer/subUserHistory")
    public ArrayList<SubAccountTransfer> getSubAccountTransferHistory() throws Exception {
        return getSubAccountTransferHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount transfer history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-transfer-history-for-sub-account">
     * Sub-account Transfer History (For Sub-account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transfer/subUserHistory")
    public <T> T getSubAccountTransferHistory(ReturnFormat format) throws Exception {
        return getSubAccountTransferHistory(null, format);
    }

    /**
     * Request to get subaccount transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> if not sent, result of all assets will be returned - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> type value, constants available {@link SubMarginTransfer} - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount transfer history as {@link ArrayList} of {@link SubAccountTransfer} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-transfer-history-for-sub-account">
     * Sub-account Transfer History (For Sub-account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transfer/subUserHistory")
    public ArrayList<SubAccountTransfer> getSubAccountTransferHistory(Params extraParams) throws Exception {
        return getSubAccountTransferHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> if not sent, result of all assets will be returned - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> type value, constants available {@link SubMarginTransfer} - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-transfer-history-for-sub-account">
     * Sub-account Transfer History (For Sub-account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transfer/subUserHistory")
    public <T> T getSubAccountTransferHistory(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String historyResponse = sendGetSignedRequest(SUB_ACCOUNT_TRANSFER_SUB_USER_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(historyResponse);
            case LIBRARY_OBJECT:
                ArrayList<SubAccountTransfer> transfers = new ArrayList<>();
                JSONArray jTransfers = new JSONArray(historyResponse);
                for (int j = 0; j < jTransfers.length(); j++)
                    transfers.add(new SubAccountTransfer(jTransfers.getJSONObject(j)));
                return (T) transfers;
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to execute a subaccount universal transfer
     *
     * @param fromAccountType: from account type value
     * @param toAccountType:   to account type value
     * @param asset:           asset of the transfer
     * @param amount:          amount of the transfer
     * @return subaccount universal transfer as {@link SubUniversalTransfer} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#universal-transfer-for-master-account">
     * Universal Transfer (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/universalTransfer")
    public SubUniversalTransfer execSubUniversalTransfer(PrincipalAccountType fromAccountType,
                                                         PrincipalAccountType toAccountType, String asset,
                                                         double amount) throws Exception {
        return execSubUniversalTransfer(fromAccountType, toAccountType, asset, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a subaccount universal transfer
     *
     * @param fromAccountType: from account type value
     * @param toAccountType:   to account type value
     * @param asset:           asset of the transfer
     * @param amount:          amount of the transfer
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return subaccount universal transfer as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#universal-transfer-for-master-account">
     * Universal Transfer (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/universalTransfer")
    public <T> T execSubUniversalTransfer(PrincipalAccountType fromAccountType, PrincipalAccountType toAccountType,
                                          String asset, double amount, ReturnFormat format) throws Exception {
        return execSubUniversalTransfer(fromAccountType, toAccountType, asset, amount, null, format);
    }

    /**
     * Request to execute a subaccount universal transfer
     *
     * @param fromAccountType: from account type value
     * @param toAccountType:   to account type value
     * @param asset:           asset of the transfer
     * @param amount:          amount of the transfer
     * @param extraParams:     additional params of the request, keys accepted are:
     *                         <ul>
     *                               <li>
     *                                    {@code "fromEmail"} -> from email of the transfer - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "toEmail"} -> to email of the transfer - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "clientTranId"} -> must be unique - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "symbol"} -> only supported under ISOLATED_MARGIN type - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                    - [LONG, default 5000]
     *                               </li>
     *                         </ul>
     * @return subaccount universal transfer as {@link SubUniversalTransfer} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#universal-transfer-for-master-account">
     * Universal Transfer (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/universalTransfer")
    public SubUniversalTransfer execSubUniversalTransfer(PrincipalAccountType fromAccountType,
                                                         PrincipalAccountType toAccountType, String asset, double amount,
                                                         Params extraParams) throws Exception {
        return execSubUniversalTransfer(fromAccountType, toAccountType, asset, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a subaccount universal transfer
     *
     * @param fromAccountType: from account type value
     * @param toAccountType:   to account type value
     * @param asset:           asset of the transfer
     * @param amount:          amount of the transfer
     * @param extraParams:     additional params of the request, keys accepted are:
     *                         <ul>
     *                               <li>
     *                                    {@code "fromEmail"} -> from email of the transfer - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "toEmail"} -> to email of the transfer - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "clientTranId"} -> must be unique - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "symbol"} -> only supported under ISOLATED_MARGIN type - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                    - [LONG, default 5000]
     *                               </li>
     *                         </ul>
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return subaccount universal transfer as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#universal-transfer-for-master-account">
     * Universal Transfer (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/universalTransfer")
    public <T> T execSubUniversalTransfer(PrincipalAccountType fromAccountType, PrincipalAccountType toAccountType,
                                          String asset, double amount, Params extraParams,
                                          ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("fromAccountType", fromAccountType);
        extraParams.addParam("toAccountType", toAccountType);
        extraParams.addParam("asset", asset);
        extraParams.addParam("amount", amount);
        String transferResponse = sendPostSignedRequest(SUB_ACCOUNT_UNIVERSAL_TRANSFER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(transferResponse);
            case LIBRARY_OBJECT:
                return (T) new SubUniversalTransfer(new JSONObject(transferResponse));
            default:
                return (T) transferResponse;
        }
    }

    /**
     * Request to get subaccount universal transfer history <br>
     * No-any params required
     *
     * @return subaccount universal transfer history as {@link SubUniversalTransferHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-universal-transfer-history-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/universalTransfer")
    public SubUniversalTransferHistory getUniversalTransferHistory() throws Exception {
        return getUniversalTransferHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount universal transfer history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount universal transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-universal-transfer-history-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/universalTransfer")
    public <T> T getUniversalTransferHistory(ReturnFormat format) throws Exception {
        return getUniversalTransferHistory(null, format);
    }

    /**
     * Request to get subaccount universal transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromEmail"} -> from email of the transfer value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toEmail"} -> to email of the transfer value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "clientTranId"} -> client tran id value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount universal transfer history as {@link SubUniversalTransferHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-universal-transfer-history-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/universalTransfer")
    public SubUniversalTransferHistory getUniversalTransferHistory(Params extraParams) throws Exception {
        return getUniversalTransferHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount universal transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "fromEmail"} -> from email of the transfer value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "toEmail"} -> to email of the transfer value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "clientTranId"} -> client tran id value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records
     *                                - [LONG, default 100 days]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 500 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount universal transfer history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-universal-transfer-history-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/universalTransfer")
    public <T> T getUniversalTransferHistory(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String historyResponse = sendGetSignedRequest(SUB_ACCOUNT_UNIVERSAL_TRANSFER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new SubUniversalTransferHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get sub futures account details
     *
     * @param email: email from fetch the account details
     * @param type:  type of the account to fetch
     * @return sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link SubFuturesAccount} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link SubFuturesAccount} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-v2-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/account")
    public <T> T getSubFuturesAccountDetail(String email, FuturesType type) throws Exception {
        return getSubFuturesAccountDetail(email, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get sub futures account details
     *
     * @param email:  email from fetch the account details
     * @param type:   type of the account to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-v2-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/account")
    public <T> T getSubFuturesAccountDetail(String email, FuturesType type, ReturnFormat format) throws Exception {
        return getSubFuturesAccountDetail(email, type, -1, format);
    }

    /**
     * Request to get sub futures account details
     *
     * @param email:      email from fetch the account details
     * @param type:       type of the account to fetch
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link SubFuturesAccount} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link SubFuturesAccount} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-v2-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/account")
    public <T> T getSubFuturesAccountDetail(String email, FuturesType type, long recvWindow) throws Exception {
        return getSubFuturesAccountDetail(email, type, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get sub futures account details
     *
     * @param email:      email from fetch the account details
     * @param type:       type of the account to fetch
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-v2-for-master-account">
     * Query Universal Transfer History (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/account")
    public <T> T getSubFuturesAccountDetail(String email, FuturesType type, long recvWindow,
                                            ReturnFormat format) throws Exception {
        Params payload = createEmailPayload(email, recvWindow, true);
        payload.addParam("futuresType", type);
        JSONObject accountResponse = new JSONObject(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_ACCOUNT_V2_ENDPOINT,
                payload));
        if (type == USDT_MARGINED)
            return returnSubFuturesAccount(accountResponse.getJSONObject("futureAccountResp").toString(), format);
        else {
            switch (format) {
                case JSON:
                    return (T) accountResponse;
                case LIBRARY_OBJECT:
                    return (T) new CoinSubFuturesAccount(accountResponse.getJSONObject("deliveryAccountResp"));
                default:
                    return (T) accountResponse.toString();
            }
        }
    }

    /**
     * Method to create a futures account
     *
     * @param accountResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return futures account as {@code "format"} defines
     **/
    @Returner
    private <T> T returnSubFuturesAccount(String accountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountResponse);
            case LIBRARY_OBJECT:
                return (T) new SubFuturesAccount(new JSONObject(accountResponse));
            default:
                return (T) accountResponse;
        }
    }

    /**
     * Request to get summary sub futures account details
     *
     * @param type: type of the account to fetch
     * @return summary sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link SummarySubFuturesAccount}
     *     </li>
     *     <li>
     *         {@link FuturesType#COIN_MARGINED} -> {@link CoinSummarySubFuturesAccount} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
     * Get Summary of Sub-account's Futures Account V2 (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/accountSummary")
    public <T> T getSummarySubFuturesAccount(FuturesType type) throws Exception {
        return getSummarySubFuturesAccount(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary sub futures account details
     *
     * @param type:   type of the account to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return summary futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
     * Get Summary of Sub-account's Futures Account V2 (For Master Account)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/accountSummary")
    public <T> T getSummarySubFuturesAccount(FuturesType type, ReturnFormat format) throws Exception {
        return getSummarySubFuturesAccount(type, null, format);
    }

    /**
     * Request to get summary sub futures account details
     *
     * @param type:        type of the account to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 10 - [INT, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return summary sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link SummarySubFuturesAccount} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#COIN_MARGINED} -> {@link CoinSummarySubFuturesAccount} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
     * Get Summary of Sub-account's Futures Account V2 (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/accountSummary")
    public <T> T getSummarySubFuturesAccount(FuturesType type, Params extraParams) throws Exception {
        return getSummarySubFuturesAccount(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary sub futures account details
     *
     * @param type:        type of the account to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 10 - [INT, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return summary futures account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
     * Get Summary of Sub-account's Futures Account V2 (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/accountSummary")
    public <T> T getSummarySubFuturesAccount(FuturesType type, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("futuresType", type);
        JSONObject accountResponse = new JSONObject(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_V2_ENDPOINT,
                extraParams));
        if (type == USDT_MARGINED) {
            return returnSummarySubFuturesAccount(accountResponse.getJSONObject("futureAccountSummaryResp").toString(),
                    format);
        } else {
            switch (format) {
                case JSON:
                    return (T) accountResponse;
                case LIBRARY_OBJECT:
                    return (T) new CoinSummarySubFuturesAccount(accountResponse.getJSONObject("deliveryAccountSummaryResp"));
                default:
                    return (T) accountResponse.toString();
            }
        }
    }

    /**
     * Method to create a summary futures account
     *
     * @param summaryResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return summary futures account as {@code "format"} defines
     **/
    @Returner
    private <T> T returnSummarySubFuturesAccount(String summaryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(summaryResponse);
            case LIBRARY_OBJECT:
                return (T) new SummarySubFuturesAccount(new JSONObject(summaryResponse));
            default:
                return (T) summaryResponse;
        }
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param type:              type of the account to fetch
     * @return summary sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link ArrayList} of {@link FuturesPositionRisk} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#COIN_MARGINED} -> {@link ArrayList} of {@link CoinFuturesPositionRisk} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> ArrayList<T> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param type:              type of the account to fetch
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type,
                                        ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param email: sub futures account email from fetch the list
     * @param type:  type of the account to fetch
     * @return summary sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link ArrayList} of {@link FuturesPositionRisk} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#COIN_MARGINED} -> {@link ArrayList} of {@link CoinFuturesPositionRisk} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> ArrayList<T> getFuturesPositionRisk(String email, FuturesType type) throws Exception {
        return getFuturesPositionRisk(email, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param email:  sub futures account email from fetch the list
     * @param type:   type of the account to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(String email, FuturesType type, ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(email, type, -1, format);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param type:              type of the account to fetch
     * @param recvWindow:        request is valid for in ms, must be less than 60000
     * @return summary sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link ArrayList} of {@link FuturesPositionRisk} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#COIN_MARGINED} -> {@link ArrayList} of {@link CoinFuturesPositionRisk} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> ArrayList<T> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type,
                                                   long recvWindow) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param subFuturesAccount: sub futures account from fetch the list
     * @param type:              type of the account to fetch
     * @param recvWindow:        request is valid for in ms, must be less than 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type, long recvWindow,
                                        ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, recvWindow, format);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param email:      sub futures account email from fetch the list
     * @param type:       type of the account to fetch
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return summary sub futures account details as:
     * <ul>
     *     <li>
     *         {@link FuturesType#USDT_MARGINED} -> {@link ArrayList} of {@link FuturesPositionRisk} custom object
     *     </li>
     *     <li>
     *         {@link FuturesType#COIN_MARGINED} -> {@link ArrayList} of {@link CoinFuturesPositionRisk} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> ArrayList<T> getFuturesPositionRisk(String email, FuturesType type, long recvWindow) throws Exception {
        return getFuturesPositionRisk(email, type, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get summary futures position risk list
     *
     * @param email:      sub futures account email from fetch the list
     * @param type:       type of the account to fetch
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-futures-position-risk-of-sub-account-v2-for-master-account">
     * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v2/sub-account/futures/positionRisk")
    public <T> T getFuturesPositionRisk(String email, FuturesType type, long recvWindow,
                                        ReturnFormat format) throws Exception {
        Params query = createEmailPayload(email, recvWindow, true);
        query.addParam("futuresType", type);
        JSONObject listResponse = new JSONObject(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_POSITION_RISK_V2_ENDPOINT,
                query));
        if (type == USDT_MARGINED)
            return returnFuturesPositionRisk(listResponse.getJSONArray("futurePositionRiskVos").toString(), format);
        else {
            JSONArray results = new JSONArray(listResponse.getJSONArray("deliveryPositionRiskVos"));
            switch (format) {
                case JSON:
                    return (T) results;
                case LIBRARY_OBJECT:
                    ArrayList<CoinFuturesPositionRisk> risks = new ArrayList<>();
                    for (int j = 0; j < results.length(); j++)
                        risks.add(new CoinFuturesPositionRisk(results.getJSONObject(j)));
                    return (T) risks;
                default:
                    return (T) results.toString();
            }
        }
    }

    /**
     * Method to create a futures position risk list
     *
     * @param listResponse: obtained from Binance's response
     * @param format:       return type formatter -> {@link ReturnFormat}
     * @return futures position risk list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnFuturesPositionRisk(String listResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<FuturesPositionRisk> risks = new ArrayList<>();
                JSONArray jRisks = new JSONArray(listResponse);
                for (int j = 0; j < jRisks.length(); j++)
                    risks.add(new FuturesPositionRisk(jRisks.getJSONObject(j)));
                return (T) risks;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public SubAccountEnabledResult enableLeverageTokenSubAccount(SubAccount subAccount) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public <T> T enableLeverageTokenSubAccount(SubAccount subAccount, ReturnFormat format) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), format);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param email: the subaccount email to enable
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public SubAccountEnabledResult enableLeverageTokenSubAccount(String email) throws Exception {
        return enableLeverageTokenSubAccount(email, LIBRARY_OBJECT);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param email:  the subaccount email to enable
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public <T> T enableLeverageTokenSubAccount(String email, ReturnFormat format) throws Exception {
        return enableLeverageTokenSubAccount(email, -1, format);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public SubAccountEnabledResult enableLeverageTokenSubAccount(SubAccount subAccount, long recvWindow) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param subAccount: the subaccount to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public <T> T enableLeverageTokenSubAccount(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param email:      the subaccount email to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return enabled result as {@link SubAccountEnabledResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public SubAccountEnabledResult enableLeverageTokenSubAccount(String email, long recvWindow) throws Exception {
        return enableLeverageTokenSubAccount(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable leverage token for subaccount
     *
     * @param email:      the subaccount email to enable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
     * Enable Leverage Token for Sub-account (For Master Account)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/sub-account/blvt/enable")
    public <T> T enableLeverageTokenSubAccount(String email, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = createEmailPayload(email, recvWindow, false);
        payload.addParam("enableBlvt", true);
        return returnEnabledResult(sendPostSignedRequest(SUB_ACCOUNT_BLVT_ENABLE_ENDPOINT, payload), format);
    }

    /**
     * Method to create an enabled result
     *
     * @param resultResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return enabled result as {@code "format"} defines
     **/
    @Returner
    private <T> T returnEnabledResult(String resultResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(resultResponse);
            case LIBRARY_OBJECT:
                return (T) new SubAccountEnabledResult(new JSONObject(resultResponse));
            default:
                return (T) resultResponse;
        }
    }

    /**
     * Request to get IP restriction
     *
     * @param subAccount:       the subaccount from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public IPRestriction getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, LIBRARY_OBJECT);
    }

    /**
     * Request to get IP restriction
     *
     * @param subAccount:       the subaccount from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public <T> T getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                  ReturnFormat format) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, format);
    }

    /**
     * Request to get IP restriction
     *
     * @param email:            the subaccount email from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public IPRestriction getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey) throws Exception {
        return getSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, LIBRARY_OBJECT);
    }

    /**
     * Request to get IP restriction
     *
     * @param email:            the subaccount email from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public <T> T getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey,
                                                  ReturnFormat format) throws Exception {
        return getSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, -1, format);
    }

    /**
     * Request to get IP restriction
     *
     * @param subAccount:       the subaccount from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @param recvWindow:       request is valid for in ms, must be less than 60000
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public IPRestriction getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                          long recvWindow) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get IP restriction
     *
     * @param subAccount:       the subaccount from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @param recvWindow:       request is valid for in ms, must be less than 60000
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public <T> T getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey, long recvWindow,
                                                  ReturnFormat format) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, recvWindow, format);
    }

    /**
     * Request to get IP restriction
     *
     * @param email:            the subaccount email from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @param recvWindow:       request is valid for in ms, must be less than 60000
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public IPRestriction getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey,
                                                          long recvWindow) throws Exception {
        return getSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get IP restriction
     *
     * @param email:            the subaccount email from fetch the restriction
     * @param subAccountAPIKey: the subaccount API key from fetch the restriction
     * @param recvWindow:       request is valid for in ms, must be less than 60000
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction")
    public <T> T getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, long recvWindow,
                                                  ReturnFormat format) throws Exception {
        Params query = createEmailPayload(email, recvWindow, true);
        query.addParam("subAccountApiKey", subAccountAPIKey);
        return returnIpRestriction(sendGetSignedRequest(SUB_ACCOUNT_API_IP_RESTRICTION_ENDPOINT, query), format);
    }

    /**
     * Request to delete IP restriction
     *
     * @param subAccount:       the subaccount from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public IPRestriction deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, LIBRARY_OBJECT);
    }

    /**
     * Request to delete IP restriction
     *
     * @param subAccount:       the subaccount from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public <T> T deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey,
                                              ReturnFormat format) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, format);
    }

    /**
     * Request to delete IP restriction
     *
     * @param email:            the subaccount email from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public IPRestriction deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey) throws Exception {
        return deleteSubAccountAPIKeyIpList(email, subAccountAPIKey, LIBRARY_OBJECT);
    }

    /**
     * Request to delete IP restriction
     *
     * @param email:            the subaccount email from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public <T> T deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey,
                                              ReturnFormat format) throws Exception {
        return deleteSubAccountAPIKeyIpList(email, subAccountAPIKey, null, format);
    }

    /**
     * Request to delete IP restriction
     *
     * @param subAccount:       the subaccount from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public IPRestriction deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey,
                                                      Params extraParams) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to delete IP restriction
     *
     * @param subAccount:       the subaccount from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public <T> T deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey, Params extraParams,
                                              ReturnFormat format) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, extraParams, format);
    }

    /**
     * Request to delete IP restriction
     *
     * @param email:            the subaccount email from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @return IP restriction as {@link IPRestriction} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public IPRestriction deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey,
                                                      Params extraParams) throws Exception {
        return deleteSubAccountAPIKeyIpList(email, subAccountAPIKey, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to delete IP restriction
     *
     * @param email:            the subaccount email from delete the restriction
     * @param subAccountAPIKey: the subaccount API key from delete the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
     * Get IP Restriction for a Sub-account API Key (For Master Account)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = DELETE, path = "/sapi/v1/sub-account/subAccountApi/ipRestriction/ipList")
    public <T> T deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey, Params extraParams,
                                              ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("subAccountApiKey", subAccountAPIKey);
        return returnIpRestriction(sendDeleteSignedRequest(SUB_ACCOUNT_API_IP_LIST_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create an IP restriction
     *
     * @param ipRestrictionResponse: obtained from Binance's response
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return IP restriction as {@code "format"} defines
     **/
    @Returner
    private <T> T returnIpRestriction(String ipRestrictionResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(ipRestrictionResponse);
            case LIBRARY_OBJECT:
                return (T) new IPRestriction(new JSONObject(ipRestrictionResponse));
            default:
                return (T) ipRestrictionResponse;
        }
    }

    /**
     * Request to update IP restriction
     *
     * @param subAccount:       the subaccount where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @return IP restriction updated as {@link IPRestrictionUpdated} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                                    IPStatus status) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, LIBRARY_OBJECT);
    }

    /**
     * Request to update IP restriction
     *
     * @param subAccount:       the subaccount where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction updated as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public <T> T updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey, IPStatus status,
                                                     ReturnFormat format) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, format);
    }

    /**
     * Request to update IP restriction
     *
     * @param email:            the subaccount email where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @return IP restriction updated as {@link IPRestrictionUpdated} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey,
                                                                    IPStatus status) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, status, LIBRARY_OBJECT);
    }

    /**
     * Request to update IP restriction
     *
     * @param email:            the subaccount email where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction updated as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public <T> T updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, IPStatus status,
                                                     ReturnFormat format) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, status, null, format);
    }

    /**
     * Request to update IP restriction
     *
     * @param subAccount:       the subaccount where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @return IP restriction updated as {@link IPRestrictionUpdated} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                                    IPStatus status, Params extraParams) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, extraParams,
                LIBRARY_OBJECT);
    }

    /**
     * Request to update IP restriction
     *
     * @param subAccount:       the subaccount where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction updated as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public <T> T updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey, IPStatus status,
                                                     Params extraParams, ReturnFormat format) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, extraParams, format);
    }

    /**
     * Request to update IP restriction
     *
     * @param email:            the subaccount email where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @return IP restriction updated as {@link IPRestrictionUpdated} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, IPStatus status,
                                                                    Params extraParams) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, status, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to update IP restriction
     *
     * @param email:            the subaccount email where update the restriction
     * @param subAccountAPIKey: the subaccount API key where update the restriction
     * @param status:           status of the restriction
     * @param extraParams:      additional params of the request, keys accepted are:
     *                          <ul>
     *                                <li>
     *                                     {@code "ipAddress"} -> can be added in batches, separated by commas - [STRING]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                     - [LONG, default 5000]
     *                                </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return IP restriction updated as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
     * Update IP Restriction for Sub-Account API key (For Master Account)</a>
     **/
    @Returner
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v2/sub-account/subAccountApi/ipRestriction")
    public <T> T updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, IPStatus status,
                                                     Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("email", email);
        extraParams.addParam("subAccountApiKey", subAccountAPIKey);
        extraParams.addParam("status", status.getStatus());
        String updateResponse = sendPostSignedRequest(SUB_ACCOUNT_API_IP_RESTRICTION_V2_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(updateResponse);
            case LIBRARY_OBJECT:
                return (T) new IPRestrictionUpdated(new JSONObject(updateResponse));
            default:
                return (T) updateResponse;
        }
    }

    /**
     * Request to deposit assets into the managed subaccount
     *
     * @param toEmail: to email of the deposit
     * @param asset:   asset of the deposit
     * @param amount:  amount of the deposit
     * @return transaction identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-assets-into-the-managed-sub-account-for-investor-master-account">
     * Deposit Assets Into The Managed Sub-account（For Investor Master Account）</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/deposit")
    public long depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount) throws Exception {
        return parseLong(depositAssetsIntoManagedSubAccount(toEmail, asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to deposit assets into the managed subaccount
     *
     * @param toEmail: to email of the deposit
     * @param asset:   asset of the deposit
     * @param amount:  amount of the deposit
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return transaction identifier as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-assets-into-the-managed-sub-account-for-investor-master-account">
     * Deposit Assets Into The Managed Sub-account（For Investor Master Account）</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/deposit")
    public <T> T depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount,
                                                    ReturnFormat format) throws Exception {
        return depositAssetsIntoManagedSubAccount(toEmail, asset, amount, -1, format);
    }

    /**
     * Request to deposit assets into the managed subaccount
     *
     * @param toEmail:    to email of the deposit
     * @param asset:      asset of the deposit
     * @param amount:     amount of the deposit
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return transaction identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-assets-into-the-managed-sub-account-for-investor-master-account">
     * Deposit Assets Into The Managed Sub-account（For Investor Master Account）</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/deposit")
    public long depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount,
                                                   long recvWindow) throws Exception {
        return parseLong(depositAssetsIntoManagedSubAccount(toEmail, asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to deposit assets into the managed subaccount
     *
     * @param toEmail:    to email of the deposit
     * @param asset:      asset of the deposit
     * @param amount:     amount of the deposit
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transaction identifier as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-assets-into-the-managed-sub-account-for-investor-master-account">
     * Deposit Assets Into The Managed Sub-account（For Investor Master Account）</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/deposit")
    public <T> T depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount,
                                                    long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("toEmail", toEmail);
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return returnTranId(sendPostSignedRequest(MANAGED_SUB_ACCOUNT_DEPOSIT_ENDPOINT, payload), format);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param subAccount: subaccount from fetch the list
     * @return subaccount asset details as {@link ArrayList} of {@link ManagedSubAccountAssetDetails} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(SubAccount subAccount) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param subAccount: subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount asset details as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public <T> T getManagedSubAccountAssetDetails(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), format);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param email: subaccount email from fetch the list
     * @return subaccount asset details as {@link ArrayList} of {@link ManagedSubAccountAssetDetails} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(String email) throws Exception {
        return getManagedSubAccountAssetDetails(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param email:  subaccount email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount asset details as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public <T> T getManagedSubAccountAssetDetails(String email, ReturnFormat format) throws Exception {
        return getManagedSubAccountAssetDetails(email, -1, format);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param subAccount: subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount asset details as {@link ArrayList} of {@link ManagedSubAccountAssetDetails} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(SubAccount subAccount,
                                                                                     long recvWindow) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param subAccount: subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount asset details as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public <T> T getManagedSubAccountAssetDetails(SubAccount subAccount, long recvWindow,
                                                  ReturnFormat format) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param email:      subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount asset details as {@link ArrayList} of {@link ManagedSubAccountAssetDetails} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(String email,
                                                                                     long recvWindow) throws Exception {
        return getManagedSubAccountAssetDetails(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount asset details
     *
     * @param email:      subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount asset details as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
     * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/asset")
    public <T> T getManagedSubAccountAssetDetails(String email, long recvWindow, ReturnFormat format) throws Exception {
        String assetResponse = sendGetSignedRequest(MANAGED_SUB_ACCOUNT_ASSET_ENDPOINT, createEmailPayload(email,
                recvWindow, true));
        switch (format) {
            case JSON:
                return (T) new JSONArray(assetResponse);
            case LIBRARY_OBJECT:
                ArrayList<ManagedSubAccountAssetDetails> assetDetails = new ArrayList<>();
                JSONArray jAssetDetails = new JSONArray(assetResponse);
                for (int j = 0; j < jAssetDetails.length(); j++)
                    assetDetails.add(new ManagedSubAccountAssetDetails(jAssetDetails.getJSONObject(j)));
                return (T) assetDetails;
            default:
                return (T) assetResponse;
        }
    }

    /**
     * Request to withdraw assets from the managed subaccount
     *
     * @param fromEmail: from email of the withdrawal
     * @param asset:     asset of the withdrawal
     * @param amount:    amount of the withdrawal
     * @return transaction identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdrawl-assets-from-the-managed-sub-account-for-investor-master-account">
     * Withdraw Assets From The Managed Sub-account（For Investor Master Account）</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/withdraw")
    public long withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount) throws Exception {
        return parseLong(withdrawAssetsFromManagedSubAccount(fromEmail, asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to withdraw assets from the managed subaccount
     *
     * @param fromEmail: from email of the withdrawal
     * @param asset:     asset of the withdrawal
     * @param amount:    amount of the withdrawal
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return transaction identifier as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdrawl-assets-from-the-managed-sub-account-for-investor-master-account">
     * Withdraw Assets From The Managed Sub-account（For Investor Master Account）</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/withdraw")
    public <T> T withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount,
                                                     ReturnFormat format) throws Exception {
        return withdrawAssetsFromManagedSubAccount(fromEmail, asset, amount, null, format);
    }

    /**
     * Request to withdraw assets from the managed subaccount
     *
     * @param fromEmail:   from email of the withdrawal
     * @param asset:       asset of the withdrawal
     * @param amount:      amount of the withdrawal
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transferDate"} -> withdrawals is automatically occur on the transfer
     *                                date(UTC0). If a date is not selected, the withdrawal occurs right now
     *                                - [LONG, default now]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return transaction identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdrawl-assets-from-the-managed-sub-account-for-investor-master-account">
     * Withdraw Assets From The Managed Sub-account（For Investor Master Account）</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/withdraw")
    public long withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount,
                                                    Params extraParams) throws Exception {
        return parseLong(withdrawAssetsFromManagedSubAccount(fromEmail, asset, amount, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to withdraw assets from the managed subaccount
     *
     * @param fromEmail:   from email of the withdrawal
     * @param asset:       asset of the withdrawal
     * @param amount:      amount of the withdrawal
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transferDate"} -> withdrawals is automatically occur on the transfer
     *                                date(UTC0). If a date is not selected, the withdrawal occurs right now
     *                                - [LONG, default now]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return transaction identifier as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdrawl-assets-from-the-managed-sub-account-for-investor-master-account">
     * Withdraw Assets From The Managed Sub-account（For Investor Master Account）</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/managed-subaccount/withdraw")
    public <T> T withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount,
                                                     Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("fromEmail", fromEmail);
        extraParams.addParam("asset", asset);
        extraParams.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(MANAGED_SUB_ACCOUNT_WITHDRAW_ENDPOINT, extraParams), format);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param subAccount: the subaccount from fetch the snapshot
     * @param type:       the type of the subaccount from fetch the snapshot
     * @return subaccount snapshot as {@link AccountSnapshot} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param subAccount: the subaccount from fetch the snapshot
     * @param type:       the type of the subaccount from fetch the snapshot
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type, ReturnFormat format) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, format);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param email: the subaccount email from fetch the snapshot
     * @param type:  the type of the subaccount from fetch the snapshot
     * @return subaccount snapshot as {@link AccountSnapshot} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @Wrapper
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(String email, AccountType type) throws Exception {
        return getManagedSubAccountSnapshot(email, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param email:  the subaccount email from fetch the snapshot
     * @param type:   the type of the subaccount from fetch the snapshot
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(String email, AccountType type, ReturnFormat format) throws Exception {
        return getManagedSubAccountSnapshot(email, type, null, format);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param subAccount:  the subaccount from fetch the snapshot
     * @param type:        the type of the subaccount from fetch the snapshot
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results, max 30 - [INT, default 7]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount snapshot as {@link AccountSnapshot} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type, Params extraParams) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param subAccount:  the subaccount from fetch the snapshot
     * @param type:        the type of the subaccount from fetch the snapshot
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results, max 30 - [INT, default 7]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type, Params extraParams,
                                              ReturnFormat format) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, extraParams, format);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param email:       the subaccount email from fetch the snapshot
     * @param type:        the type of the subaccount from fetch the snapshot
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results, max 30 - [INT, default 7]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount snapshot as {@link AccountSnapshot} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @Wrapper
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(String email, AccountType type, Params extraParams) throws Exception {
        return getManagedSubAccountSnapshot(email, type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount snapshot
     *
     * @param email:       the subaccount email from fetch the snapshot
     * @param type:        the type of the subaccount from fetch the snapshot
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results, max 30 - [INT, default 7]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-snapshot-for-investor-master-account">
     * Query Managed Sub-account Snapshot&#xFF08;For Investor Master Account&#xFF09;</a>
     **/
    @RequestWeight(weight = "2400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/accountSnapshot")
    public <T> T getManagedSubAccountSnapshot(String email, AccountType type, Params extraParams,
                                              ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("type", type.toString().toUpperCase());
        return returnAccountSnapshot(type, sendGetSignedRequest(MANAGED_SUB_ACCOUNT_SNAPSHOT_ENDPOINT, extraParams),
                format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:   the subaccount from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public SubAccountTransferLog getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page,
                                                        int limit) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:   the subaccount from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public <T> T getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                        ReturnFormat format) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:     the subaccount email from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public SubAccountTransferLog getInvestorTransferLog(String email, long startTime, long endTime, int page,
                                                        int limit) throws Exception {
        return getInvestorTransferLog(email, startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:     the subaccount email from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public <T> T getInvestorTransferLog(String email, long startTime, long endTime, int page, int limit,
                                        ReturnFormat format) throws Exception {
        return getInvestorTransferLog(email, startTime, endTime, page, limit, null, format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:     the subaccount from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public SubAccountTransferLog getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page,
                                                        int limit, Params extraParams) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams,
                LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:     the subaccount from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public <T> T getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams, format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:       the subaccount email from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public SubAccountTransferLog getInvestorTransferLog(String email, long startTime, long endTime, int page,
                                                        int limit, Params extraParams) throws Exception {
        return getInvestorTransferLog(email, startTime, endTime, page, limit, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:       the subaccount email from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForInvestor")
    public <T> T getInvestorTransferLog(String email, long startTime, long endTime, int page, int limit,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return returnTransferLog(sendGetSignedRequest(MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_INVESTOR_ENDPOINT,
                createTransferLogQuery(email, startTime, endTime, page, limit, extraParams)), format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:   the subaccount from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public SubAccountTransferLog getTradingTeamTransferLog(SubAccount account, long startTime, long endTime,
                                                           int page, int limit) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:   the subaccount from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public <T> T getTradingTeamTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                           ReturnFormat format) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:     the subaccount email from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public SubAccountTransferLog getTradingTeamTransferLog(String email, long startTime, long endTime, int page,
                                                           int limit) throws Exception {
        return getTradingTeamTransferLog(email, startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:     the subaccount email from fetch transfer log
     * @param startTime: the start time
     * @param endTime:   end time (The start time and end time interval cannot exceed half a year)
     * @param page:      page from fetch the result
     * @param limit:     results limit (Max: 500)
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public <T> T getTradingTeamTransferLog(String email, long startTime, long endTime, int page, int limit,
                                           ReturnFormat format) throws Exception {
        return getTradingTeamTransferLog(email, startTime, endTime, page, limit, null, format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:     the subaccount from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public SubAccountTransferLog getTradingTeamTransferLog(SubAccount account, long startTime, long endTime,
                                                           int page, int limit, Params extraParams) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams,
                LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param account:     the subaccount from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public <T> T getTradingTeamTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                           Params extraParams, ReturnFormat format) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams, format);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:       the subaccount email from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @return subaccount transfer log as {@link SubAccountTransferLog} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public SubAccountTransferLog getTradingTeamTransferLog(String email, long startTime, long endTime, int page,
                                                           int limit, Params extraParams) throws Exception {
        return getTradingTeamTransferLog(email, startTime, endTime, page, limit, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount transfer log
     *
     * @param email:       the subaccount email from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
     * Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/queryTransLogForTradeParent")
    public <T> T getTradingTeamTransferLog(String email, long startTime, long endTime, int page, int limit,
                                           Params extraParams, ReturnFormat format) throws Exception {
        return returnTransferLog(sendGetSignedRequest(MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_TRADE_PARENT_ENDPOINT,
                createTransferLogQuery(email, startTime, endTime, page, limit, extraParams)), format);
    }

    /**
     * Method to create a transfer log payload
     *
     * @param email:       the subaccount email from fetch transfer log
     * @param startTime:   the start time
     * @param endTime:     end time (The start time and end time interval cannot exceed half a year)
     * @param page:        page from fetch the result
     * @param limit:       results limit (Max: 500)
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "transfers"} -> transfer direction, constants available
     *                                {@link TransferDirection} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transferFunctionAccountType"} -> transfer function account type,
     *                                constants available {@link PrincipalAccountType} - [STRING]
     *                           </li>
     *                     </ul>
     * @return payload as {@link Params}
     **/
    private Params createTransferLogQuery(String email, long startTime, long endTime, int page, int limit,
                                          Params extraParams) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("startTime", startTime);
        extraParams.addParam("endTime", endTime);
        extraParams.addParam("page", page);
        extraParams.addParam("limit", limit);
        return extraParams;
    }

    /**
     * Method to create a subaccount transfer log
     *
     * @param transferResponse: obtained from Binance's response
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return subaccount transfer log as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTransferLog(String transferResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(transferResponse);
            case LIBRARY_OBJECT:
                return (T) new SubAccountTransferLog(new JSONObject(transferResponse));
            default:
                return (T) transferResponse;
        }
    }

    /**
     * Request to get the subaccount futures asset details
     *
     * @param subAccount: the subaccount from fetch the list
     * @return subaccount futures asset details as {@link FuturesAccountSnapshot} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-futures-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Futures Asset Details（For Investor Master Account）(USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/fetch-future-asset")
    public FuturesAccountSnapshot getManagedSubAccountFuturesAssetDetails(SubFuturesAccount subAccount) throws Exception {
        return getManagedSubAccountFuturesAssetDetails(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount futures asset details
     *
     * @param subAccount: the subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-futures-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Futures Asset Details（For Investor Master Account）(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/fetch-future-asset")
    public <T> T getManagedSubAccountFuturesAssetDetails(SubFuturesAccount subAccount, ReturnFormat format) throws Exception {
        return getManagedSubAccountFuturesAssetDetails(subAccount.getEmail(), format);
    }

    /**
     * Request to get the subaccount futures asset details
     *
     * @param email: the subaccount email from fetch the list
     * @return subaccount futures asset details as {@link FuturesAccountSnapshot} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-futures-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Futures Asset Details（For Investor Master Account）(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/fetch-future-asset")
    public FuturesAccountSnapshot getManagedSubAccountFuturesAssetDetails(String email) throws Exception {
        return getManagedSubAccountFuturesAssetDetails(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount futures asset details
     *
     * @param email:  the subaccount email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-futures-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Futures Asset Details（For Investor Master Account）(USER_DATA)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/fetch-future-asset")
    public <T> T getManagedSubAccountFuturesAssetDetails(String email, ReturnFormat format) throws Exception {
        return returnAccountSnapshot(futures, sendGetSignedRequest(MANAGED_SUB_ACCOUNT_FETCH_FUTURE_ASSET_ENDPOINT
                + "?email=" + email), format);
    }

    /**
     * Request to get the subaccount margin asset details
     *
     * @param subAccount: the subaccount from fetch the list
     * @return subaccount margin asset details as {@link SubMarginAccountAssetDetails} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-margin-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Margin Asset Details (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/marginAsset")
    public SubMarginAccountAssetDetails getManagedSubAccountMarginAssetDetails(SubMarginAccount subAccount) throws Exception {
        return getManagedSubAccountMarginAssetDetails(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount margin asset details
     *
     * @param subAccount: the subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount margin asset details as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-margin-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Margin Asset Details (For Investor Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/marginAsset")
    public <T> T getManagedSubAccountMarginAssetDetails(SubMarginAccount subAccount, ReturnFormat format) throws Exception {
        return getManagedSubAccountMarginAssetDetails(subAccount.getEmail(), format);
    }

    /**
     * Request to get the subaccount margin asset details
     *
     * @param email: the subaccount email from fetch the list
     * @return subaccount margin asset details as {@link SubMarginAccountAssetDetails} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-margin-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Margin Asset Details (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/marginAsset")
    public SubMarginAccountAssetDetails getManagedSubAccountMarginAssetDetails(String email) throws Exception {
        return getManagedSubAccountMarginAssetDetails(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount margin asset details
     *
     * @param email:  the subaccount email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount margin asset details as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-margin-asset-details-for-investor-master-account-user_data">
     * Query Managed Sub-account Margin Asset Details (For Investor Master Account) (USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/marginAsset")
    public <T> T getManagedSubAccountMarginAssetDetails(String email, ReturnFormat format) throws Exception {
        String detailsResponse = sendGetSignedRequest(MANAGED_SUB_ACCOUNT_MARGIN_ASSET_ENDPOINT + "?email="
                + email);
        switch (format) {
            case JSON:
                return (T) new JSONObject(detailsResponse);
            case LIBRARY_OBJECT:
                return (T) new SubMarginAccountAssetDetails(new JSONObject(detailsResponse));
            default:
                return (T) detailsResponse;
        }
    }

    /**
     * Request to get the subaccount assets
     *
     * @param subAccount: the subaccount from fetch the list
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public ArrayList<SpotBalance> getMasterAccountAssets(SubAccount subAccount) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param subAccount: the subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public <T> T getMasterAccountAssets(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), format);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param email: the subaccount email from fetch the list
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public ArrayList<SpotBalance> getMasterAccountAssets(String email) throws Exception {
        return getMasterAccountAssets(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param email:  the subaccount email from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public <T> T getMasterAccountAssets(String email, ReturnFormat format) throws Exception {
        return getMasterAccountAssets(email, -1, format);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param subAccount: the subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public ArrayList<SpotBalance> getMasterAccountAssets(SubAccount subAccount, long recvWindow) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param subAccount: the subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public <T> T getMasterAccountAssets(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param email:      the subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount assets as {@link ArrayList} of {@link SpotBalance} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public ArrayList<SpotBalance> getMasterAccountAssets(String email, long recvWindow) throws Exception {
        return getMasterAccountAssets(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the subaccount assets
     *
     * @param email:      the subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account-user_data">
     * Query Sub-account Assets (For Master Account)(USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v4/sub-account/assets")
    public <T> T getMasterAccountAssets(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnBalances(sendGetSignedRequest(SUB_ACCOUNT_ASSETS_V4_ENDPOINT, createEmailPayload(email, recvWindow,
                true)), format);
    }

    /**
     * Method to create a balances list
     *
     * @param balancesResponse: obtained from Binance's response
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return balances list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnBalances(String balancesResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(balancesResponse);
            case LIBRARY_OBJECT:
                ArrayList<SpotBalance> balances = new ArrayList<>();
                JSONArray jBalances = JsonHelper.getJSONArray(new JSONObject(balancesResponse), "balances",
                        new JSONArray());
                for (int j = 0; j < jBalances.length(); j++)
                    balances.add(new SpotBalance(jBalances.getJSONObject(j)));
                return (T) balances;
            default:
                return (T) balancesResponse;
        }
    }

    /**
     * Request to get investor's managed subaccount list <br>
     * No-any params required
     *
     * @return managed subaccount list as {@link ManagedSubAccountList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-list-for-investor-user_data">
     * Query Managed Sub-account List (For Investor)(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/info")
    public ManagedSubAccountList getManagedSubAccountList() throws Exception {
        return getManagedSubAccountList(LIBRARY_OBJECT);
    }

    /**
     * Request to get investor's managed subaccount list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return managed subaccount list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-list-for-investor-user_data">
     * Query Managed Sub-account List (For Investor)(USER_DATA)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/info")
    public <T> T getManagedSubAccountList(ReturnFormat format) throws Exception {
        return getManagedSubAccountList(null, format);
    }

    /**
     * Request to get investor's managed subaccount list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results per page, max 20 - [INT, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return managed subaccount list as {@link ManagedSubAccountList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-list-for-investor-user_data">
     * Query Managed Sub-account List (For Investor)(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/info")
    public ManagedSubAccountList getManagedSubAccountList(Params extraParams) throws Exception {
        return getManagedSubAccountList(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get investor's managed subaccount list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "email"} -> subaccount email - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results per page, max 20 - [INT, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return managed subaccount list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-list-for-investor-user_data">
     * Query Managed Sub-account List (For Investor)(USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/info")
    public <T> T getManagedSubAccountList(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String subAccounts = sendGetSignedRequest(MANAGED_SUB_ACCOUNT_INFO_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(subAccounts);
            case LIBRARY_OBJECT:
                return (T) new ManagedSubAccountList(new JSONObject(subAccounts));
            default:
                return (T) subAccounts;
        }
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param subAccount: the subaccount from fetch the list
     * @return subaccount transaction statistics as {@link SubAccountTransactionsStatistics} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(SubAccount subAccount) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param subAccount: the subaccount from fetch the list
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount transaction statistics as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public <T> T getSubAccountTransactionStatistic(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), format);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param email: the subaccount from fetch the list
     * @return subaccount transaction statistics as {@link SubAccountTransactionsStatistics} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(String email) throws Exception {
        return getSubAccountTransactionStatistic(email, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param email:  the subaccount from fetch the list
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return subaccount transaction statistics as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public <T> T getSubAccountTransactionStatistic(String email, ReturnFormat format) throws Exception {
        return getSubAccountTransactionStatistic(email, -1, format);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param subAccount: the subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount transaction statistics as {@link SubAccountTransactionsStatistics} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(SubAccount subAccount,
                                                                              long recvWindow) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param subAccount: the subaccount from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount transaction statistics as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public <T> T getSubAccountTransactionStatistic(SubAccount subAccount, long recvWindow,
                                                   ReturnFormat format) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), recvWindow, format);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param email:      the subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return subaccount transaction statistics as {@link SubAccountTransactionsStatistics} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(String email,
                                                                              long recvWindow) throws Exception {
        return getSubAccountTransactionStatistic(email, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get subaccount transaction statistics
     *
     * @param email:      the subaccount email from fetch the list
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return subaccount transaction statistics as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
     * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "60(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/sub-account/transaction-tatistics")
    public <T> T getSubAccountTransactionStatistic(String email, long recvWindow, ReturnFormat format) throws Exception {
        String transactionsResponse = sendGetSignedRequest(SUB_ACCOUNT_TRANSACTION_STATISTICS_ENDPOINT,
                createEmailPayload(email, recvWindow, true));
        switch (format) {
            case JSON:
                return (T) new JSONObject(transactionsResponse);
            case LIBRARY_OBJECT:
                return (T) new SubAccountTransactionsStatistics(new JSONObject(transactionsResponse));
            default:
                return (T) transactionsResponse;
        }
    }

    /**
     * Method to create an email payload
     *
     * @param email:      email to insert
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param insertTime: whether insert the timestamp in the payload
     * @return payload as {@link Params}
     **/
    private Params createEmailPayload(String email, long recvWindow, boolean insertTime) {
        Params payload;
        if (insertTime)
            payload = createTimestampPayload(null);
        else
            payload = new Params();
        payload.addParam("email", email);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return payload;
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param subAccount: subaccount from fetch the deposit address
     * @param coin:       coin of the deposit address
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public DepositAddress getManagedSubAccountDepositAddress(SubAccount subAccount, String coin) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, LIBRARY_OBJECT);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param subAccount: subaccount from fetch the deposit address
     * @param coin:       coin of the deposit address
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public <T> T getManagedSubAccountDepositAddress(SubAccount subAccount, String coin, ReturnFormat format) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, format);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param email: subaccount email from fetch the deposit address
     * @param coin:  coin of the deposit address
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public DepositAddress getManagedSubAccountDepositAddress(String email, String coin) throws Exception {
        return getManagedSubAccountDepositAddress(email, coin, LIBRARY_OBJECT);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param email:  subaccount email from fetch the deposit address
     * @param coin:   coin of the deposit address
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public <T> T getManagedSubAccountDepositAddress(String email, String coin, ReturnFormat format) throws Exception {
        return getManagedSubAccountDepositAddress(email, coin, null, format);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param subAccount:  subaccount from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public DepositAddress getManagedSubAccountDepositAddress(SubAccount subAccount, String coin,
                                                             Params extraParams) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param subAccount:  subaccount from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public <T> T getManagedSubAccountDepositAddress(SubAccount subAccount, String coin, Params extraParams,
                                                    ReturnFormat format) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, format);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param email:       subaccount email from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subaccount deposit address as {@link DepositAddress} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public DepositAddress getManagedSubAccountDepositAddress(String email, String coin, Params extraParams) throws Exception {
        return getManagedSubAccountDepositAddress(email, coin, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get investor's managed sub-account deposit address
     *
     * @param email:       subaccount email from fetch the deposit address
     * @param coin:        coin of the deposit address
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "network"} -> network value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return deposit address as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
     * Get Managed Sub-account Deposit Address (For Investor Master Account) (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/managed-subaccount/deposit/address")
    public <T> T getManagedSubAccountDepositAddress(String email, String coin, Params extraParams,
                                                    ReturnFormat format) throws Exception {
        return returnDepositAddress(sendGetSignedRequest(MANAGED_SUB_ACCOUNT_DEPOSIT_ADDRESS_ENDPOINT,
                createDepositQuery(email, coin, extraParams)), format);
    }

    /**
     * Method to create a deposit payload
     *
     * @param email:       email to insert
     * @param coin:        coin to insert
     * @param extraParams: extra params of the request
     * @return payload as {@link Params}
     **/
    private Params createDepositQuery(String email, String coin, Params extraParams) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("coin", coin);
        return extraParams;
    }

}
