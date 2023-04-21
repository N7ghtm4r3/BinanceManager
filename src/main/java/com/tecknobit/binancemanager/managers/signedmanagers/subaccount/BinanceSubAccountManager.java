package com.tecknobit.binancemanager.managers.signedmanagers.subaccount;

import com.tecknobit.apimanager.annotations.Returner;
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
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.AccountType;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.FuturesAccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.DepositAddress;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
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

    public String createVirtualSubAccount(String subAccountString) throws Exception {
        return createVirtualSubAccount(subAccountString, LIBRARY_OBJECT);
    }

    public <T> T createVirtualSubAccount(String subAccountString, ReturnFormat format) throws Exception {
        return createVirtualSubAccount(subAccountString, -1, format);
    }

    public String createVirtualSubAccount(String subAccountString, long recvWindow) throws Exception {
        return createVirtualSubAccount(subAccountString, LIBRARY_OBJECT);
    }

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

    public ArrayList<SubAccount> getSubAccountList() throws Exception {
        return getSubAccountList(LIBRARY_OBJECT);
    }

    public <T> T getSubAccountList(ReturnFormat format) throws Exception {
        return getSubAccountList(null, format);
    }

    public ArrayList<SubAccount> getSubAccountList(Params extraParams) throws Exception {
        return getSubAccountList(extraParams, LIBRARY_OBJECT);
    }

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

    public ArrayList<SpotAssetTransfer> getSubAccountSpotTransferHistory() throws Exception {
        return getSubAccountSpotTransferHistory(LIBRARY_OBJECT);
    }

    public <T> T getSubAccountSpotTransferHistory(ReturnFormat format) throws Exception {
        return getSubAccountSpotTransferHistory(null, format);
    }

    public ArrayList<SpotAssetTransfer> getSubAccountSpotTransferHistory(Params extraParams) throws Exception {
        return getSubAccountSpotTransferHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public FutureAssetTransferHistory getSubAccountFutureTransferHistory(String email, FuturesType type) throws Exception {
        return getSubAccountFutureTransferHistory(email, type, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountFutureTransferHistory(String email, FuturesType type, ReturnFormat format) throws Exception {
        return getSubAccountFutureTransferHistory(email, type, null, format);
    }

    public FutureAssetTransferHistory getSubAccountFutureTransferHistory(String email, FuturesType type,
                                                                         Params extraParams) throws Exception {
        return getSubAccountFutureTransferHistory(email, type, extraParams, LIBRARY_OBJECT);
    }

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

    public FutureAssetTransferResult execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type,
                                                             String asset, double amount) throws Exception {
        return execFutureAssetTransfer(fromEmail, toEmail, type, asset, amount, LIBRARY_OBJECT);
    }

    public <T> T execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type, String asset,
                                         double amount, ReturnFormat format) throws Exception {
        return execFutureAssetTransfer(fromEmail, toEmail, type, asset, amount, -1, format);
    }

    public FutureAssetTransferResult execFutureAssetTransfer(String fromEmail, String toEmail, FuturesType type,
                                                             String asset, double amount, long recvWindow) throws Exception {
        return execFutureAssetTransfer(fromEmail, toEmail, type, asset, amount, recvWindow, LIBRARY_OBJECT);
    }

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

    public ArrayList<SpotBalance> getSubAccountAssets(SubAccount subAccount) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAssets(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), format);
    }

    public ArrayList<SpotBalance> getSubAccountAssets(String email) throws Exception {
        return getSubAccountAssets(email, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAssets(String email, ReturnFormat format) throws Exception {
        return getSubAccountAssets(email, -1, format);
    }

    public ArrayList<SpotBalance> getSubAccountAssets(SubAccount subAccount, long recvWindow) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAssets(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return getSubAccountAssets(subAccount.getEmail(), recvWindow, format);
    }

    public ArrayList<SpotBalance> getSubAccountAssets(String email, long recvWindow) throws Exception {
        return getSubAccountAssets(email, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAssets(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnBalances(sendGetSignedRequest(SUB_ACCOUNT_ASSETS_ENDPOINT, createEmailPayload(email, recvWindow,
                true)), format);
    }

    public SpotAssetsSummary getSubAccountSpotAssetsSummary() throws Exception {
        return getSubAccountSpotAssetsSummary(LIBRARY_OBJECT);
    }

    public <T> T getSubAccountSpotAssetsSummary(ReturnFormat format) throws Exception {
        return getSubAccountSpotAssetsSummary(null, format);
    }

    public SpotAssetsSummary getSubAccountSpotAssetsSummary(Params extraParams) throws Exception {
        return getSubAccountSpotAssetsSummary(extraParams, LIBRARY_OBJECT);
    }

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

    public DepositAddress getSubAccountDepositAddress(SubAccount subAccount, String coin) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositAddress(SubAccount subAccount, String coin, ReturnFormat format) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, format);
    }

    public DepositAddress getSubAccountDepositAddress(String email, String coin) throws Exception {
        return getSubAccountDepositAddress(email, coin, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositAddress(String email, String coin, ReturnFormat format) throws Exception {
        return getSubAccountDepositAddress(email, coin, null, format);
    }

    public DepositAddress getSubAccountDepositAddress(SubAccount subAccount, String coin,
                                                      Params extraParams) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositAddress(SubAccount subAccount, String coin, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return getSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, format);
    }

    public DepositAddress getSubAccountDepositAddress(String email, String coin, Params extraParams) throws Exception {
        return getSubAccountDepositAddress(email, coin, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositAddress(String email, String coin, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return returnDepositAddress(sendGetSignedRequest(SUB_ACCOUNT_CAPITAL_DEPOSIT_SUB_ADDRESS_ENDPOINT,
                createDepositQuery(email, coin, extraParams)), format);
    }

    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(SubAccount subAccount) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositHistory(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), format);
    }

    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(String email) throws Exception {
        return getSubAccountDepositHistory(email, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositHistory(String email, ReturnFormat format) throws Exception {
        return getSubAccountDepositHistory(email, null, format);
    }

    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(SubAccount subAccount,
                                                                     Params extraParams) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), extraParams, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountDepositHistory(SubAccount subAccount, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return getSubAccountDepositHistory(subAccount.getEmail(), extraParams, format);
    }

    public ArrayList<DepositHistoryItem> getSubAccountDepositHistory(String email, Params extraParams) throws Exception {
        return getSubAccountDepositHistory(email, extraParams, LIBRARY_OBJECT);
    }

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

    public ArrayList<SubAccountStatus> getSubAccountStatus() throws Exception {
        return getSubAccountStatus(LIBRARY_OBJECT);
    }

    public <T> T getSubAccountStatus(ReturnFormat format) throws Exception {
        return getSubAccountStatus(null, format);
    }

    public ArrayList<SubAccountStatus> getSubAccountStatus(Params extraParams) throws Exception {
        return getSubAccountStatus(extraParams, LIBRARY_OBJECT);
    }

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

    public SubAccountEnabledResult enableMarginSubAccount(SubAccount subAccount) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T enableMarginSubAccount(SubAccount subAccount, ReturnFormat format) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), format);
    }

    public SubAccountEnabledResult enableMarginSubAccount(String email) throws Exception {
        return enableMarginSubAccount(email, LIBRARY_OBJECT);
    }

    public <T> T enableMarginSubAccount(String email, ReturnFormat format) throws Exception {
        return enableMarginSubAccount(email, -1, format);
    }

    public SubAccountEnabledResult enableMarginSubAccount(SubAccount subAccount, long recvWindow) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T enableMarginSubAccount(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return enableMarginSubAccount(subAccount.getEmail(), recvWindow, format);
    }

    public SubAccountEnabledResult enableMarginSubAccount(String email, long recvWindow) throws Exception {
        return enableMarginSubAccount(email, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T enableMarginSubAccount(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnEnabledResult(sendPostSignedRequest(SUB_ACCOUNT_MARGIN_ENABLE_ENDPOINT, createEmailPayload(email,
                recvWindow, false)), format);
    }

    public SubMarginAccount getSubMarginAccountDetail(String email) throws Exception {
        return getSubMarginAccountDetail(email, LIBRARY_OBJECT);
    }

    public <T> T getSubMarginAccountDetail(String email, ReturnFormat format) throws Exception {
        return getSubMarginAccountDetail(email, -1, format);
    }

    public SubMarginAccount getSubMarginAccountDetail(String email, long recvWindow) throws Exception {
        return getSubMarginAccountDetail(email, recvWindow, LIBRARY_OBJECT);
    }

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

    public SummarySubMarginAccount getSummarySubMarginAccount() throws Exception {
        return getSummarySubMarginAccount(LIBRARY_OBJECT);
    }

    public <T> T getSummarySubMarginAccount(ReturnFormat format) throws Exception {
        return getSummarySubMarginAccount(-1, format);
    }

    public SummarySubMarginAccount getSummarySubMarginAccount(long recvWindow) throws Exception {
        return getSummarySubMarginAccount(recvWindow, LIBRARY_OBJECT);
    }

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

    public SubAccountEnabledResult enableFuturesSubAccount(SubAccount subAccount) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T enableFuturesSubAccount(SubAccount subAccount, ReturnFormat format) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), format);
    }

    public SubAccountEnabledResult enableFuturesSubAccount(String email) throws Exception {
        return enableFuturesSubAccount(email, LIBRARY_OBJECT);
    }

    public <T> T enableFuturesSubAccount(String email, ReturnFormat format) throws Exception {
        return enableFuturesSubAccount(email, -1, format);
    }

    public SubAccountEnabledResult enableFuturesSubAccount(SubAccount subAccount, long recvWindow) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T enableFuturesSubAccount(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return enableFuturesSubAccount(subAccount.getEmail(), recvWindow, format);
    }

    public SubAccountEnabledResult enableFuturesSubAccount(String email, long recvWindow) throws Exception {
        return enableFuturesSubAccount(email, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T enableFuturesSubAccount(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnEnabledResult(sendPostSignedRequest(SUB_ACCOUNT_FUTURES_ENABLE_ENDPOINT, createEmailPayload(email,
                recvWindow, false)), format);
    }

    public SubFuturesAccount getSubFuturesAccountDetail(String email) throws Exception {
        return getSubFuturesAccountDetail(email, LIBRARY_OBJECT);
    }

    public <T> T getSubFuturesAccountDetail(String email, ReturnFormat format) throws Exception {
        return getSubFuturesAccountDetail(email, -1, format);
    }

    public SubFuturesAccount getSubFuturesAccountDetail(String email, long recvWindow) throws Exception {
        return getSubFuturesAccountDetail(email, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSubFuturesAccountDetail(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnSubFuturesAccount(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_ACCOUNT_ENDPOINT,
                createEmailPayload(email, recvWindow, true)), format);
    }

    public SummarySubFuturesAccount getSummarySubFuturesAccount() throws Exception {
        return getSummarySubFuturesAccount(LIBRARY_OBJECT);
    }

    public <T> T getSummarySubFuturesAccount(ReturnFormat format) throws Exception {
        return getSummarySubFuturesAccount(-1, format);
    }

    public SummarySubFuturesAccount getSummarySubFuturesAccount(long recvWindow) throws Exception {
        return getSummarySubFuturesAccount(recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSummarySubFuturesAccount(long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        return returnSummarySubFuturesAccount(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_ACCOUNT_SUMMARY_ENDPOINT, query),
                format);
    }

    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), LIBRARY_OBJECT);
    }

    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(String email) throws Exception {
        return getFuturesPositionRisk(email, LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(String email, ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(email, -1, format);
    }

    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount,
                                                                 long recvWindow) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, long recvWindow,
                                        ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), recvWindow, format);
    }

    public ArrayList<FuturesPositionRisk> getFuturesPositionRisk(String email, long recvWindow) throws Exception {
        return getFuturesPositionRisk(email, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(String email, long recvWindow, ReturnFormat format) throws Exception {
        return returnFuturesPositionRisk(sendGetSignedRequest(SUB_ACCOUNT_FUTURES_POSITION_RISK_ENDPOINT,
                createEmailPayload(email, recvWindow, true)), format);
    }

    public long execFuturesTransfer(SubAccount subAccount, String asset, double amount,
                                    FutureTransactionType type) throws Exception {
        return parseLong(execFuturesTransfer(subAccount.getEmail(), asset, amount, type, LIBRARY_OBJECT));
    }

    public <T> T execFuturesTransfer(SubAccount subAccount, String asset, double amount, FutureTransactionType type,
                                     ReturnFormat format) throws Exception {
        return execFuturesTransfer(subAccount.getEmail(), asset, amount, type, format);
    }

    public long execFuturesTransfer(String email, String asset, double amount,
                                    FutureTransactionType type) throws Exception {
        return parseLong(execFuturesTransfer(email, asset, amount, type, LIBRARY_OBJECT));
    }

    public <T> T execFuturesTransfer(String email, String asset, double amount, FutureTransactionType type,
                                     ReturnFormat format) throws Exception {
        return execFuturesTransfer(email, asset, amount, type, -1, format);
    }

    public long execFuturesTransfer(SubAccount subAccount, String asset, double amount, FutureTransactionType type,
                                    long recvWindow) throws Exception {
        return parseLong(execFuturesTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T execFuturesTransfer(SubAccount subAccount, String asset, double amount, FutureTransactionType type,
                                     long recvWindow, ReturnFormat format) throws Exception {
        return execFuturesTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, format);
    }

    public long execFuturesTransfer(String email, String asset, double amount, FutureTransactionType type,
                                    long recvWindow) throws Exception {
        return parseLong(execFuturesTransfer(email, asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T execFuturesTransfer(String email, String asset, double amount, FutureTransactionType type,
                                     long recvWindow, ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_FUTURES_TRANSFER_ENDPOINT, createTransferPayload(email,
                asset, amount, type, recvWindow)), format);
    }

    public long execMarginTransfer(SubAccount subAccount, String asset, double amount,
                                   SubMarginTransfer type) throws Exception {
        return parseLong(execMarginTransfer(subAccount.getEmail(), asset, amount, type, LIBRARY_OBJECT));
    }

    public <T> T execMarginTransfer(SubAccount subAccount, String asset, double amount, SubMarginTransfer type,
                                    ReturnFormat format) throws Exception {
        return execMarginTransfer(subAccount.getEmail(), asset, amount, type, format);
    }

    public long execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type) throws Exception {
        return parseLong(execMarginTransfer(email, asset, amount, type, LIBRARY_OBJECT));
    }

    public <T> T execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type,
                                    ReturnFormat format) throws Exception {
        return execMarginTransfer(email, asset, amount, type, -1, format);
    }

    public long execMarginTransfer(SubAccount subAccount, String asset, double amount, SubMarginTransfer type,
                                   long recvWindow) throws Exception {
        return parseLong(execMarginTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T execMarginTransfer(SubAccount subAccount, String asset, double amount, SubMarginTransfer type,
                                    long recvWindow, ReturnFormat format) throws Exception {
        return execMarginTransfer(subAccount.getEmail(), asset, amount, type, recvWindow, format);
    }

    public long execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type,
                                   long recvWindow) throws Exception {
        return parseLong(execMarginTransfer(email, asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T execMarginTransfer(String email, String asset, double amount, SubMarginTransfer type,
                                    long recvWindow, ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_MARGIN_TRANSFER_ENDPOINT, createTransferPayload(email,
                asset, amount, type, recvWindow)), format);
    }

    public long execSubToSubTransfer(SubAccount subAccount, String asset, double amount) throws Exception {
        return parseLong(execSubToSubTransfer(subAccount.getEmail(), asset, amount, LIBRARY_OBJECT));
    }

    public <T> T execSubToSubTransfer(SubAccount subAccount, String asset, double amount,
                                      ReturnFormat format) throws Exception {
        return execSubToSubTransfer(subAccount.getEmail(), asset, amount, format);
    }

    public long execSubToSubTransfer(String email, String asset, double amount) throws Exception {
        return parseLong(execSubToSubTransfer(email, asset, amount, LIBRARY_OBJECT));
    }

    public <T> T execSubToSubTransfer(String email, String asset, double amount, ReturnFormat format) throws Exception {
        return execSubToSubTransfer(email, asset, amount, -1, format);
    }

    public long execSubToSubTransfer(SubAccount subAccount, String asset, double amount, long recvWindow) throws Exception {
        return parseLong(execSubToSubTransfer(subAccount.getEmail(), asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T execSubToSubTransfer(SubAccount subAccount, String asset, double amount, long recvWindow,
                                      ReturnFormat format) throws Exception {
        return execSubToSubTransfer(subAccount.getEmail(), asset, amount, recvWindow, format);
    }

    public long execSubToSubTransfer(String email, String asset, double amount, long recvWindow) throws Exception {
        return parseLong(execSubToSubTransfer(email, asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T execSubToSubTransfer(String email, String asset, double amount, long recvWindow,
                                      ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_TRANSFER_SUB_TO_SUB_ENDPOINT, createTransferPayload(email,
                asset, amount, null, recvWindow)), format);
    }

    public long transferToMaster(String asset, double amount) throws Exception {
        return parseLong(transferToMaster(asset, amount, LIBRARY_OBJECT));
    }

    public <T> T transferToMaster(String asset, double amount, ReturnFormat format) throws Exception {
        return transferToMaster(asset, amount, -1, format);
    }

    public long transferToMaster(String asset, double amount, long recvWindow) throws Exception {
        return parseLong(transferToMaster(asset, amount, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T transferToMaster(String asset, double amount, long recvWindow, ReturnFormat format) throws Exception {
        return returnTransferId(sendPostSignedRequest(SUB_ACCOUNT_TRANSFER_SUB_TO_MASTER_ENDPOINT,
                createTransferPayload(null, asset, amount, null, recvWindow)), format);
    }

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

    public ArrayList<SubAccountTransfer> getSubAccountTransferHistory() throws Exception {
        return getSubAccountTransferHistory(LIBRARY_OBJECT);
    }

    public <T> T getSubAccountTransferHistory(ReturnFormat format) throws Exception {
        return getSubAccountTransferHistory(null, format);
    }

    public ArrayList<SubAccountTransfer> getSubAccountTransferHistory(Params extraParams) throws Exception {
        return getSubAccountTransferHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public SubUniversalTransfer execSubUniversalTransfer(PrincipalAccountType fromAccountType,
                                                         PrincipalAccountType toAccountType, String asset,
                                                         double amount) throws Exception {
        return execSubUniversalTransfer(fromAccountType, toAccountType, asset, amount, LIBRARY_OBJECT);
    }

    public <T> T execSubUniversalTransfer(PrincipalAccountType fromAccountType, PrincipalAccountType toAccountType,
                                          String asset, double amount, ReturnFormat format) throws Exception {
        return execSubUniversalTransfer(fromAccountType, toAccountType, asset, amount, null, format);
    }

    public SubUniversalTransfer execSubUniversalTransfer(PrincipalAccountType fromAccountType,
                                                         PrincipalAccountType toAccountType, String asset, double amount,
                                                         Params extraParams) throws Exception {
        return execSubUniversalTransfer(fromAccountType, toAccountType, asset, amount, extraParams, LIBRARY_OBJECT);
    }

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

    public SubUniversalTransferHistory getUniversalTransferHistory() throws Exception {
        return getUniversalTransferHistory(LIBRARY_OBJECT);
    }

    public <T> T getUniversalTransferHistory(ReturnFormat format) throws Exception {
        return getUniversalTransferHistory(null, format);
    }

    public SubUniversalTransferHistory getUniversalTransferHistory(Params extraParams) throws Exception {
        return getUniversalTransferHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public <T> T getSubFuturesAccountDetail(String email, FuturesType type) throws Exception {
        return getSubFuturesAccountDetail(email, type, LIBRARY_OBJECT);
    }

    public <T> T getSubFuturesAccountDetail(String email, FuturesType type, ReturnFormat format) throws Exception {
        return getSubFuturesAccountDetail(email, type, -1, format);
    }

    public <T> T getSubFuturesAccountDetail(String email, FuturesType type, long recvWindow) throws Exception {
        return getSubFuturesAccountDetail(email, type, recvWindow, LIBRARY_OBJECT);
    }

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

    public <T> T getSummarySubFuturesAccount(FuturesType type) throws Exception {
        return getSummarySubFuturesAccount(type, LIBRARY_OBJECT);
    }

    public <T> T getSummarySubFuturesAccount(FuturesType type, ReturnFormat format) throws Exception {
        return getSummarySubFuturesAccount(type, null, format);
    }

    public <T> T getSummarySubFuturesAccount(FuturesType type, Params extraParams) throws Exception {
        return getSummarySubFuturesAccount(type, extraParams, LIBRARY_OBJECT);
    }

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

    public <T> ArrayList<T> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type,
                                        ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, LIBRARY_OBJECT);
    }

    public <T> ArrayList<T> getFuturesPositionRisk(String email, FuturesType type) throws Exception {
        return getFuturesPositionRisk(email, type, LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(String email, FuturesType type, ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(email, type, -1, format);
    }

    public <T> ArrayList<T> getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type,
                                                   long recvWindow) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getFuturesPositionRisk(SubFuturesAccount subFuturesAccount, FuturesType type, long recvWindow,
                                        ReturnFormat format) throws Exception {
        return getFuturesPositionRisk(subFuturesAccount.getEmail(), type, recvWindow, format);
    }

    public <T> ArrayList<T> getFuturesPositionRisk(String email, FuturesType type, long recvWindow) throws Exception {
        return getFuturesPositionRisk(email, type, recvWindow, LIBRARY_OBJECT);
    }

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

    public SubAccountEnabledResult enableLeverageTokenSubAccount(SubAccount subAccount) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T enableLeverageTokenSubAccount(SubAccount subAccount, ReturnFormat format) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), format);
    }

    public SubAccountEnabledResult enableLeverageTokenSubAccount(String email) throws Exception {
        return enableLeverageTokenSubAccount(email, LIBRARY_OBJECT);
    }

    public <T> T enableLeverageTokenSubAccount(String email, ReturnFormat format) throws Exception {
        return enableLeverageTokenSubAccount(email, -1, format);
    }

    public SubAccountEnabledResult enableLeverageTokenSubAccount(SubAccount subAccount, long recvWindow) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T enableLeverageTokenSubAccount(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return enableLeverageTokenSubAccount(subAccount.getEmail(), recvWindow, format);
    }

    public SubAccountEnabledResult enableLeverageTokenSubAccount(String email, long recvWindow) throws Exception {
        return enableLeverageTokenSubAccount(email, recvWindow, LIBRARY_OBJECT);
    }

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

    public IPRestriction getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                  ReturnFormat format) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, format);
    }

    public IPRestriction getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey) throws Exception {
        return getSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey,
                                                  ReturnFormat format) throws Exception {
        return getSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, -1, format);
    }

    public IPRestriction getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                          long recvWindow) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey, long recvWindow,
                                                  ReturnFormat format) throws Exception {
        return getSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, recvWindow, format);
    }

    public IPRestriction getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey,
                                                          long recvWindow) throws Exception {
        return getSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, long recvWindow,
                                                  ReturnFormat format) throws Exception {
        Params query = createEmailPayload(email, recvWindow, true);
        query.addParam("subAccountApiKey", subAccountAPIKey);
        return returnIpRestriction(sendGetSignedRequest(SUB_ACCOUNT_API_IP_RESTRICTION_ENDPOINT, query), format);
    }

    public IPRestriction deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, LIBRARY_OBJECT);
    }

    public <T> T deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey,
                                              ReturnFormat format) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, format);
    }

    public IPRestriction deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey) throws Exception {
        return deleteSubAccountAPIKeyIpList(email, subAccountAPIKey, LIBRARY_OBJECT);
    }

    public <T> T deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey,
                                              ReturnFormat format) throws Exception {
        return deleteSubAccountAPIKeyIpList(email, subAccountAPIKey, null, format);
    }

    public IPRestriction deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey,
                                                      Params extraParams) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, extraParams, LIBRARY_OBJECT);
    }

    public <T> T deleteSubAccountAPIKeyIpList(SubAccount subAccount, String subAccountAPIKey, Params extraParams,
                                              ReturnFormat format) throws Exception {
        return deleteSubAccountAPIKeyIpList(subAccount.getEmail(), subAccountAPIKey, extraParams, format);
    }

    public IPRestriction deleteSubAccountAPIKeyIpList(String email, String subAccountAPIKey,
                                                      Params extraParams) throws Exception {
        return deleteSubAccountAPIKeyIpList(email, subAccountAPIKey, extraParams, LIBRARY_OBJECT);
    }

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

    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                                    IPStatus status) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, LIBRARY_OBJECT);
    }

    public <T> T updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey, IPStatus status,
                                                     ReturnFormat format) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, format);
    }

    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey,
                                                                    IPStatus status) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, status, LIBRARY_OBJECT);
    }

    public <T> T updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, IPStatus status,
                                                     ReturnFormat format) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, status, null, format);
    }

    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey,
                                                                    IPStatus status, Params extraParams) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, extraParams,
                LIBRARY_OBJECT);
    }

    public <T> T updateSubAccountAPIKeyIpRestriction(SubAccount subAccount, String subAccountAPIKey, IPStatus status,
                                                     Params extraParams, ReturnFormat format) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(subAccount.getEmail(), subAccountAPIKey, status, extraParams, format);
    }

    public IPRestrictionUpdated updateSubAccountAPIKeyIpRestriction(String email, String subAccountAPIKey, IPStatus status,
                                                                    Params extraParams) throws Exception {
        return updateSubAccountAPIKeyIpRestriction(email, subAccountAPIKey, status, extraParams, LIBRARY_OBJECT);
    }

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

    public long depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount) throws Exception {
        return parseLong(depositAssetsIntoManagedSubAccount(toEmail, asset, amount, LIBRARY_OBJECT));
    }

    public <T> T depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount,
                                                    ReturnFormat format) throws Exception {
        return depositAssetsIntoManagedSubAccount(toEmail, asset, amount, -1, format);
    }

    public long depositAssetsIntoManagedSubAccount(String toEmail, String asset, double amount,
                                                   long recvWindow) throws Exception {
        return parseLong(depositAssetsIntoManagedSubAccount(toEmail, asset, amount, recvWindow, LIBRARY_OBJECT));
    }

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

    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(SubAccount subAccount) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountAssetDetails(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), format);
    }

    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(String email) throws Exception {
        return getManagedSubAccountAssetDetails(email, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountAssetDetails(String email, ReturnFormat format) throws Exception {
        return getManagedSubAccountAssetDetails(email, -1, format);
    }

    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(SubAccount subAccount,
                                                                                     long recvWindow) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountAssetDetails(SubAccount subAccount, long recvWindow,
                                                  ReturnFormat format) throws Exception {
        return getManagedSubAccountAssetDetails(subAccount.getEmail(), recvWindow, format);
    }

    public ArrayList<ManagedSubAccountAssetDetails> getManagedSubAccountAssetDetails(String email,
                                                                                     long recvWindow) throws Exception {
        return getManagedSubAccountAssetDetails(email, recvWindow, LIBRARY_OBJECT);
    }

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

    public long withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount) throws Exception {
        return parseLong(withdrawAssetsFromManagedSubAccount(fromEmail, asset, amount, LIBRARY_OBJECT));
    }

    public <T> T withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount,
                                                     ReturnFormat format) throws Exception {
        return withdrawAssetsFromManagedSubAccount(fromEmail, asset, amount, null, format);
    }

    public long withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount,
                                                    Params extraParams) throws Exception {
        return parseLong(withdrawAssetsFromManagedSubAccount(fromEmail, asset, amount, extraParams, LIBRARY_OBJECT));
    }

    public <T> T withdrawAssetsFromManagedSubAccount(String fromEmail, String asset, double amount,
                                                     Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("fromEmail", fromEmail);
        extraParams.addParam("asset", asset);
        extraParams.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(MANAGED_SUB_ACCOUNT_WITHDRAW_ENDPOINT, extraParams), format);
    }

    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type, ReturnFormat format) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, format);
    }

    public <T> T getManagedSubAccountSnapshot(String email, AccountType type) throws Exception {
        return getManagedSubAccountSnapshot(email, type, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountSnapshot(String email, AccountType type, ReturnFormat format) throws Exception {
        return getManagedSubAccountSnapshot(email, type, null, format);
    }

    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type, Params extraParams) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountSnapshot(SubAccount subAccount, AccountType type, Params extraParams,
                                              ReturnFormat format) throws Exception {
        return getManagedSubAccountSnapshot(subAccount.getEmail(), type, extraParams, format);
    }

    public <T> T getManagedSubAccountSnapshot(String email, AccountType type, Params extraParams) throws Exception {
        return getManagedSubAccountSnapshot(email, type, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountSnapshot(String email, AccountType type, Params extraParams,
                                              ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("type", type.toString().toUpperCase());
        return returnAccountSnapshot(type, sendGetSignedRequest(MANAGED_SUB_ACCOUNT_SNAPSHOT_ENDPOINT, extraParams),
                format);
    }

    public SubAccountTransferLog getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page,
                                                        int limit) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    public <T> T getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                        ReturnFormat format) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, format);
    }

    public SubAccountTransferLog getInvestorTransferLog(String email, long startTime, long endTime, int page,
                                                        int limit) throws Exception {
        return getInvestorTransferLog(email, startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    public <T> T getInvestorTransferLog(String email, long startTime, long endTime, int page, int limit,
                                        ReturnFormat format) throws Exception {
        return getInvestorTransferLog(email, startTime, endTime, page, limit, null, format);
    }

    public SubAccountTransferLog getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page,
                                                        int limit, Params extraParams) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams,
                LIBRARY_OBJECT);
    }

    public <T> T getInvestorTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return getInvestorTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams, format);
    }

    public SubAccountTransferLog getInvestorTransferLog(String email, long startTime, long endTime, int page,
                                                        int limit, Params extraParams) throws Exception {
        return getInvestorTransferLog(email, startTime, endTime, page, limit, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getInvestorTransferLog(String email, long startTime, long endTime, int page, int limit,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return returnTransferLog(sendGetSignedRequest(MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_INVESTOR_ENDPOINT,
                createTransferLogQuery(email, startTime, endTime, page, limit, extraParams)), format);
    }

    public SubAccountTransferLog getTradingTeamTransferLog(SubAccount account, long startTime, long endTime,
                                                           int page, int limit) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    public <T> T getTradingTeamTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                           ReturnFormat format) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, format);
    }

    public SubAccountTransferLog getTradingTeamTransferLog(String email, long startTime, long endTime, int page,
                                                           int limit) throws Exception {
        return getTradingTeamTransferLog(email, startTime, endTime, page, limit, LIBRARY_OBJECT);
    }

    public <T> T getTradingTeamTransferLog(String email, long startTime, long endTime, int page, int limit,
                                           ReturnFormat format) throws Exception {
        return getTradingTeamTransferLog(email, startTime, endTime, page, limit, null, format);
    }

    public SubAccountTransferLog getTradingTeamTransferLog(SubAccount account, long startTime, long endTime,
                                                           int page, int limit, Params extraParams) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams,
                LIBRARY_OBJECT);
    }

    public <T> T getTradingTeamTransferLog(SubAccount account, long startTime, long endTime, int page, int limit,
                                           Params extraParams, ReturnFormat format) throws Exception {
        return getTradingTeamTransferLog(account.getEmail(), startTime, endTime, page, limit, extraParams, format);
    }

    public SubAccountTransferLog getTradingTeamTransferLog(String email, long startTime, long endTime, int page,
                                                           int limit, Params extraParams) throws Exception {
        return getTradingTeamTransferLog(email, startTime, endTime, page, limit, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getTradingTeamTransferLog(String email, long startTime, long endTime, int page, int limit,
                                           Params extraParams, ReturnFormat format) throws Exception {
        return returnTransferLog(sendGetSignedRequest(MANAGED_SUB_ACCOUNT_TRANSLOG_FOR_TRADE_PARENT_ENDPOINT,
                createTransferLogQuery(email, startTime, endTime, page, limit, extraParams)), format);
    }

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

    public FuturesAccountSnapshot getManagedSubAccountFuturesAssetDetails(SubFuturesAccount subAccount) throws Exception {
        return getManagedSubAccountFuturesAssetDetails(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountFuturesAssetDetails(SubFuturesAccount subAccount, ReturnFormat format) throws Exception {
        return getManagedSubAccountFuturesAssetDetails(subAccount.getEmail(), format);
    }

    public FuturesAccountSnapshot getManagedSubAccountFuturesAssetDetails(String email) throws Exception {
        return getManagedSubAccountFuturesAssetDetails(email, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountFuturesAssetDetails(String email, ReturnFormat format) throws Exception {
        return returnAccountSnapshot(futures, sendGetSignedRequest(MANAGED_SUB_ACCOUNT_FETCH_FUTURE_ASSET_ENDPOINT
                + "?email=" + email), format);
    }

    public SubMarginAccountAssetDetails getManagedSubAccountMarginAssetDetails(SubMarginAccount subAccount) throws Exception {
        return getManagedSubAccountMarginAssetDetails(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountMarginAssetDetails(SubMarginAccount subAccount, ReturnFormat format) throws Exception {
        return getManagedSubAccountMarginAssetDetails(subAccount.getEmail(), format);
    }

    public SubMarginAccountAssetDetails getManagedSubAccountMarginAssetDetails(String email) throws Exception {
        return getManagedSubAccountMarginAssetDetails(email, LIBRARY_OBJECT);
    }

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

    public ArrayList<SpotBalance> getMasterAccountAssets(SubAccount subAccount) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getMasterAccountAssets(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), format);
    }

    public ArrayList<SpotBalance> getMasterAccountAssets(String email) throws Exception {
        return getMasterAccountAssets(email, LIBRARY_OBJECT);
    }

    public <T> T getMasterAccountAssets(String email, ReturnFormat format) throws Exception {
        return getMasterAccountAssets(email, -1, format);
    }

    public ArrayList<SpotBalance> getMasterAccountAssets(SubAccount subAccount, long recvWindow) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getMasterAccountAssets(SubAccount subAccount, long recvWindow, ReturnFormat format) throws Exception {
        return getMasterAccountAssets(subAccount.getEmail(), recvWindow, format);
    }

    public ArrayList<SpotBalance> getMasterAccountAssets(String email, long recvWindow) throws Exception {
        return getMasterAccountAssets(email, recvWindow, LIBRARY_OBJECT);
    }

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

    public ManagedSubAccountList getManagedSubAccountList() throws Exception {
        return getManagedSubAccountList(LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountList(ReturnFormat format) throws Exception {
        return getManagedSubAccountList(null, format);
    }

    public ManagedSubAccountList getManagedSubAccountList(Params extraParams) throws Exception {
        return getManagedSubAccountList(extraParams, LIBRARY_OBJECT);
    }

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

    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(SubAccount subAccount) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), LIBRARY_OBJECT);
    }

    public <T> T getSubAccountTransactionStatistic(SubAccount subAccount, ReturnFormat format) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), format);
    }

    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(String email) throws Exception {
        return getSubAccountTransactionStatistic(email, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountTransactionStatistic(String email, ReturnFormat format) throws Exception {
        return getSubAccountTransactionStatistic(email, -1, format);
    }

    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(SubAccount subAccount,
                                                                              long recvWindow) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSubAccountTransactionStatistic(SubAccount subAccount, long recvWindow,
                                                   ReturnFormat format) throws Exception {
        return getSubAccountTransactionStatistic(subAccount.getEmail(), recvWindow, format);
    }

    public SubAccountTransactionsStatistics getSubAccountTransactionStatistic(String email,
                                                                              long recvWindow) throws Exception {
        return getSubAccountTransactionStatistic(email, recvWindow, LIBRARY_OBJECT);
    }

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

    public DepositAddress getManagedSubAccountDepositAddress(SubAccount subAccount, String coin) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountDepositAddress(SubAccount subAccount, String coin, ReturnFormat format) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, format);
    }

    public DepositAddress getManagedSubAccountDepositAddress(String email, String coin) throws Exception {
        return getManagedSubAccountDepositAddress(email, coin, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountDepositAddress(String email, String coin, ReturnFormat format) throws Exception {
        return getManagedSubAccountDepositAddress(email, coin, null, format);
    }

    public DepositAddress getManagedSubAccountDepositAddress(SubAccount subAccount, String coin,
                                                             Params extraParams) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountDepositAddress(SubAccount subAccount, String coin, Params extraParams,
                                                    ReturnFormat format) throws Exception {
        return getManagedSubAccountDepositAddress(subAccount.getEmail(), coin, extraParams, format);
    }

    public DepositAddress getManagedSubAccountDepositAddress(String email, String coin, Params extraParams) throws Exception {
        return getManagedSubAccountDepositAddress(email, coin, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getManagedSubAccountDepositAddress(String email, String coin, Params extraParams,
                                                    ReturnFormat format) throws Exception {
        return returnDepositAddress(sendGetSignedRequest(MANAGED_SUB_ACCOUNT_DEPOSIT_ADDRESS_ENDPOINT,
                createDepositQuery(email, coin, extraParams)), format);
    }

    private Params createDepositQuery(String email, String coin, Params extraParams) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("email", email);
        extraParams.addParam("coin", coin);
        return extraParams;
    }

}
