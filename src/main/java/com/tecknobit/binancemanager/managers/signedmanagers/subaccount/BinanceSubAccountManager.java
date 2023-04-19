package com.tecknobit.binancemanager.managers.signedmanagers.subaccount;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;

import java.io.IOException;

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
     * {@code SUB_ACCOUNT_FUTURES_TRANSFERS_ENDPOINT} is constant for SUB_ACCOUNT_FUTURES_TRANSFERS_ENDPOINT's endpoint
     **/
    public static final String SUB_ACCOUNT_FUTURES_TRANSFERS_ENDPOINT = "/sapi/v1/sub-account/futures/transfers";

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

    
}
