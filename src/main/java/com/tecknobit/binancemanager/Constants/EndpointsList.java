package com.tecknobit.binancemanager.Constants;

/**
 *  The {@code EndpointsList} class is a container class for all Binance's endpoints of the service API
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#introduction
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class EndpointsList {

    /**Generals endpoint urls**/
    public static final String SYSTEM_STATUS_ENDPOINT = "/sapi/v1/system/status";
    public static final String TIMESTAMP_ENDPOINT = "/api/v3/time";

    /**Wallet managers endpoint urls**/
    public static final String ALL_COINS_ENDPOINT = "/sapi/v1/capital/config/getall";
    public static final String DAILY_ACCOUNT_SNAP_ENDPOINT = "/sapi/v1/accountSnapshot";
    public static final String DISABLE_FAST_WITHDRAW_ENDPOINT = "/sapi/v1/account/disableFastWithdrawSwitch";
    public static final String ENABLE_FAST_WITHDRAW_ENDPOINT = "/sapi/v1/account/enableFastWithdrawSwitch";
    public static final String DEPOSIT_HISTORY_ENDPOINT = "/sapi/v1/capital/deposit/hisrec";
    public static final String WITHDRAW_HISTORY_ENDPOINT = "/sapi/v1/capital/withdraw/history";
    public static final String SUBMIT_WITHDRAW_ENDPOINT = "/sapi/v1/capital/withdraw/apply";
    public static final String DEPOSIT_ADDRESS_ENDPOINT = "/sapi/v1/capital/deposit/address";
    public static final String ACCOUNT_STATUS_ENDPOINT = "/sapi/v1/account/status";
    public static final String API_TRADING_STATUS_ENDPOINT = "/sapi/v1/account/apiTradingStatus";
    public static final String DUST_LOG_ENDPOINT = "/sapi/v1/asset/dribblet";
    public static final String ASSET_CONVERTIBLE_BNB_ENDPOINT = "/sapi/v1/asset/dust-btc";
    public static final String DUST_TRANSFER_ENDPOINT = "/sapi/v1/asset/dust";
    public static final String ASSET_DIVIDEND_ENDPOINT = "/sapi/v1/asset/assetDividend";
    public static final String ASSET_DETAIL_ENPOINT = "/sapi/v1/asset/assetDetail";
    public static final String TRADE_FEE_ENPOINT = "/sapi/v1/asset/tradeFee";
    public static final String UNIVERSAL_TRANSFER_ENDPOINT = "/sapi/v1/asset/transfer";
    public static final String FUNDING_WALLET_ENDPOINT = "/sapi/v1/asset/get-funding-asset";
    public static final String API_KEY_PERMISSION_ENDPOINT = "/sapi/v1/account/apiRestrictions";

    /**Market managers endpoint urls**/



}
