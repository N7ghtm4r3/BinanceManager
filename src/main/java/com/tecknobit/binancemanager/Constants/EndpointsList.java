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

    /**Wallet manager endpoint urls**/
    public static final String ALL_COINS_ENDPOINT = "/sapi/v1/capital/config/getall";
    public static final String DAILY_ACCOUNT_SNAPSHOT_ENDPOINT = "/sapi/v1/accountSnapshot";
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

    /**Market manager endpoint urls**/
    public static final String TEST_CONNECTIVITY_ENDPOINT = "/api/v3/ping";
    public static final String EXCHANGE_INFORMATION_ENDPOINT = "/api/v3/exchangeInfo";
    public static final String ORDER_BOOK_ENDPOINT = "/api/v3/depth";
    public static final String RECENT_TRADE_LIST_ENDPOINT = "/api/v3/trades";
    public static final String OLD_TRADE_LOOKUP_ENDPOINT = "/api/v3/historicalTrades";
    public static final String COMPRESSED_TRADE_LIST_ENDPOINT = "/api/v3/aggTrades";
    public static final String CANDLESTICK_DATA_ENDPOINT = "/api/v3/klines";
    public static final String CURRENT_AVERAGE_PRICE_ENDPOINT = "/api/v3/avgPrice";
    public static final String TICKER_PRICE_CHANGE_ENDPOINT = "/api/v3/ticker/24hr";
    public static final String PRICE_TICKER_ENDPOINT = "/api/v3/ticker/price";
    public static final String BOOK_TICKER_ENDPOINT = "/api/v3/ticker/bookTicker";

    /**Spot manager endpoint urls**/
    public static final String SPOT_TEST_NEW_ORDER_ENDPOINT = "/api/v3/order/test";
    public static final String SPOT_ORDER_ENDPOINT = "/api/v3/order";
    public static final String SPOT_OPEN_ORDERS_ENDPOINT = "/api/v3/openOrders";
    public static final String SPOT_ALL_ORDERS_LIST_ENDPOINT = "/api/v3/allOrders";
    public static final String SPOT_OCO_ORDER_ENDPOINT = "/api/v3/order/oco";
    public static final String SPOT_OCO_ORDER_LIST_ENDPOINT = "/api/v3/orderList";
    public static final String SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT = "/api/v3/allOrderList";
    public static final String SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT = "/api/v3/openOrderList";
    public static final String SPOT_ACCOUNT_INFORMATION_ENDPOINT = "/api/v3/account";
    public static final String SPOT_ACCOUNT_TRADE_LIST_ENDPOINT = "/api/v3/myTrades";
    public static final String SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE = "/api/v3/rateLimit/order";

    /**Margin manager endpoint urls**/
    public static final String CROSS_MARGIN_TRANSFERS_ENDPOINT = "/sapi/v1/margin/transfer";
    public static final String MARGIN_LOAN_ENDPOINT = "/sapi/v1/margin/loan";
    public static final String MARGIN_REPAY_ENDPOINT = "/sapi/v1/margin/repay";
    public static final String QUERY_MARGIN_ASSET_ENDPOINT = "/sapi/v1/margin/asset";
    public static final String QUERY_ALL_MARGIN_ASSETS_ENDPOINT = "/sapi/v1/margin/allAssets";
    public static final String QUERY_CROSS_MARGIN_PAIR_ENDPOINT = "/sapi/v1/margin/pair";
    public static final String QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT = "/sapi/v1/margin/allPairs";
    public static final String MARGIN_PRICE_INDEX_ENDPOINT = "/sapi/v1/margin/priceIndex";
    public static final String MARGIN_ORDER_ENDPOINT = "/sapi/v1/margin/order";
    public static final String MARGIN_OPEN_ORDERS_ENDPOINT = "/sapi/v1/margin/openOrders";
    public static final String MARGIN_INTERST_HISTORY_ENDPOINT = "/sapi/v1/margin/interestHistory";
    public static final String MARGIN_FORCE_LIQUIDATION_ENDPOINT = "/sapi/v1/margin/forceLiquidationRec";
    public static final String CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT = "/sapi/v1/margin/account";
    public static final String MARGIN_ALL_ORDERS_ENDPOINT = "/sapi/v1/margin/allOrders";
    public static final String MARGIN_OCO_ORDER_ENDPOINT = "/sapi/v1/margin/order/oco";
    public static final String MARGIN_OCO_ORDERS_ENDPOINT = "/sapi/v1/margin/orderList";
    public static final String MARGIN_OCO_ALL_ORDERS_ENDPOINT = "/sapi/v1/margin/allOrderList";
    public static final String MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT = "/sapi/v1/margin/openOrderList";
    public static final String MARGIN_TRADES_LIST_ENDPOINT = "/sapi/v1/margin/myTrades";
    public static final String GET_MAX_MARGIN_BORROW_ENDPOINT = "/sapi/v1/margin/maxBorrowable";
    public static final String ISOLATED_MARGIN_TRANSFER_ENDPOINT = "/sapi/v1/margin/isolated/transfer";
    public static final String ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT = "/sapi/v1/margin/isolated/account";

}
