package com.tecknobit.binancemanager.Constants;

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

}
