package com.tecknobit.binancemanager.Constants;

/**
 *  The {@code EndpointsList} class is a container class for all Binance's endpoints of the service API
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 *      https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public abstract class EndpointsList {

    /**
     * {@code SYSTEM_STATUS_ENDPOINT} is constant for SYSTEM_STATUS_ENDPOINT's endpoint
     * **/
    public static final String SYSTEM_STATUS_ENDPOINT = "/sapi/v1/system/status";

    /**
     * {@code TIMESTAMP_ENDPOINT} is constant for TIMESTAMP_ENDPOINT's endpoint
     * **/
    public static final String TIMESTAMP_ENDPOINT = "/api/v3/time";

    /**
     * {@code ALL_COINS_ENDPOINT} is constant for ALL_COINS_ENDPOINT's endpoint
     * **/
    public static final String ALL_COINS_ENDPOINT = "/sapi/v1/capital/config/getall";

    /**
     * {@code DAILY_ACCOUNT_SNAPSHOT_ENDPOINT} is constant for DAILY_ACCOUNT_SNAPSHOT_ENDPOINT's endpoint
     * **/
    public static final String DAILY_ACCOUNT_SNAPSHOT_ENDPOINT = "/sapi/v1/accountSnapshot";

    /**
     * {@code DISABLE_FAST_WITHDRAW_ENDPOINT} is constant for DISABLE_FAST_WITHDRAW_ENDPOINT's endpoint
     * **/
    public static final String DISABLE_FAST_WITHDRAW_ENDPOINT = "/sapi/v1/account/disableFastWithdrawSwitch";

    /**
     * {@code ENABLE_FAST_WITHDRAW_ENDPOINT} is constant for ENABLE_FAST_WITHDRAW_ENDPOINT's endpoint
     * **/
    public static final String ENABLE_FAST_WITHDRAW_ENDPOINT = "/sapi/v1/account/enableFastWithdrawSwitch";

    /**
     * {@code DEPOSIT_HISTORY_ENDPOINT} is constant for DEPOSIT_HISTORY_ENDPOINT's endpoint
     * **/
    public static final String DEPOSIT_HISTORY_ENDPOINT = "/sapi/v1/capital/deposit/hisrec";

    /**
     * {@code WITHDRAW_HISTORY_ENDPOINT} is constant for WITHDRAW_HISTORY_ENDPOINT's endpoint
     * **/
    public static final String WITHDRAW_HISTORY_ENDPOINT = "/sapi/v1/capital/withdraw/history";

    /**
     * {@code SUBMIT_WITHDRAW_ENDPOINT} is constant for SUBMIT_WITHDRAW_ENDPOINT's endpoint
     * **/
    public static final String SUBMIT_WITHDRAW_ENDPOINT = "/sapi/v1/capital/withdraw/apply";

    /**
     * {@code DEPOSIT_ADDRESS_ENDPOINT} is constant for DEPOSIT_ADDRESS_ENDPOINT's endpoint
     * **/
    public static final String DEPOSIT_ADDRESS_ENDPOINT = "/sapi/v1/capital/deposit/address";

    /**
     * {@code ACCOUNT_STATUS_ENDPOINT} is constant for ACCOUNT_STATUS_ENDPOINT's endpoint
     * **/
    public static final String ACCOUNT_STATUS_ENDPOINT = "/sapi/v1/account/status";

    /**
     * {@code API_TRADING_STATUS_ENDPOINT} is constant for API_TRADING_STATUS_ENDPOINT's endpoint
     * **/
    public static final String API_TRADING_STATUS_ENDPOINT = "/sapi/v1/account/apiTradingStatus";

    /**
     * {@code DUST_LOG_ENDPOINT} is constant for DUST_LOG_ENDPOINT's endpoint
     * **/
    public static final String DUST_LOG_ENDPOINT = "/sapi/v1/asset/dribblet";

    /**
     * {@code ASSET_CONVERTIBLE_BNB_ENDPOINT} is constant for ASSET_CONVERTIBLE_BNB_ENDPOINT's endpoint
     * **/
    public static final String ASSET_CONVERTIBLE_BNB_ENDPOINT = "/sapi/v1/asset/dust-btc";

    /**
     * {@code DUST_TRANSFER_ENDPOINT} is constant for DUST_TRANSFER_ENDPOINT's endpoint
     * **/
    public static final String DUST_TRANSFER_ENDPOINT = "/sapi/v1/asset/dust";

    /**
     * {@code ASSET_DIVIDEND_ENDPOINT} is constant for ASSET_DIVIDEND_ENDPOINT's endpoint
     * **/
    public static final String ASSET_DIVIDEND_ENDPOINT = "/sapi/v1/asset/assetDividend";

    /**
     * {@code ASSET_DETAIL_ENDPOINT} is constant for ASSET_DETAIL_ENDPOINT's endpoint
     * **/
    public static final String ASSET_DETAIL_ENDPOINT = "/sapi/v1/asset/assetDetail";

    /**
     * {@code TRADE_FEE_ENDPOINT} is constant for TRADE_FEE_ENDPOINT's endpoint
     * **/
    public static final String TRADE_FEE_ENDPOINT = "/sapi/v1/asset/tradeFee";

    /**
     * {@code UNIVERSAL_TRANSFER_ENDPOINT} is constant for UNIVERSAL_TRANSFER_ENDPOINT's endpoint
     * **/
    public static final String UNIVERSAL_TRANSFER_ENDPOINT = "/sapi/v1/asset/transfer";

    /**
     * {@code FUNDING_WALLET_ENDPOINT} is constant for FUNDING_WALLET_ENDPOINT's endpoint
     * **/
    public static final String FUNDING_WALLET_ENDPOINT = "/sapi/v1/asset/get-funding-asset";

    /**
     * {@code API_KEY_PERMISSION_ENDPOINT} is constant for API_KEY_PERMISSION_ENDPOINT's endpoint
     * **/
    public static final String API_KEY_PERMISSION_ENDPOINT = "/sapi/v1/account/apiRestrictions";

    /**
     * {@code TEST_CONNECTIVITY_ENDPOINT} is constant for TEST_CONNECTIVITY_ENDPOINT's endpoint
     * **/
    public static final String TEST_CONNECTIVITY_ENDPOINT = "/api/v3/ping";

    /**
     * {@code EXCHANGE_INFORMATION_ENDPOINT} is constant for EXCHANGE_INFORMATION_ENDPOINT's endpoint
     * **/
    public static final String EXCHANGE_INFORMATION_ENDPOINT = "/api/v3/exchangeInfo";

    /**
     * {@code ORDER_BOOK_ENDPOINT} is constant for ORDER_BOOK_ENDPOINT's endpoint
     * **/
    public static final String ORDER_BOOK_ENDPOINT = "/api/v3/depth";

    /**
     * {@code RECENT_TRADE_LIST_ENDPOINT} is constant for RECENT_TRADE_LIST_ENDPOINT's endpoint
     * **/
    public static final String RECENT_TRADE_LIST_ENDPOINT = "/api/v3/trades";

    /**
     * {@code OLD_TRADE_LOOKUP_ENDPOINT} is constant for OLD_TRADE_LOOKUP_ENDPOINT's endpoint
     * **/
    public static final String OLD_TRADE_LOOKUP_ENDPOINT = "/api/v3/historicalTrades";

    /**
     * {@code COMPRESSED_TRADE_LIST_ENDPOINT} is constant for COMPRESSED_TRADE_LIST_ENDPOINT's endpoint
     * **/
    public static final String COMPRESSED_TRADE_LIST_ENDPOINT = "/api/v3/aggTrades";

    /**
     * {@code CANDLESTICK_DATA_ENDPOINT} is constant for CANDLESTICK_DATA_ENDPOINT's endpoint
     * **/
    public static final String CANDLESTICK_DATA_ENDPOINT = "/api/v3/klines";

    /**
     * {@code CURRENT_AVERAGE_PRICE_ENDPOINT} is constant for CURRENT_AVERAGE_PRICE_ENDPOINT's endpoint
     * **/
    public static final String CURRENT_AVERAGE_PRICE_ENDPOINT = "/api/v3/avgPrice";

    /**
     * {@code TICKER_PRICE_CHANGE_ENDPOINT} is constant for TICKER_PRICE_CHANGE_ENDPOINT's endpoint
     * **/
    public static final String TICKER_PRICE_CHANGE_ENDPOINT = "/api/v3/ticker/24hr";

    /**
     * {@code PRICE_TICKER_ENDPOINT} is constant for PRICE_TICKER_ENDPOINT's endpoint
     * **/
    public static final String PRICE_TICKER_ENDPOINT = "/api/v3/ticker/price";

    /**
     * {@code BOOK_TICKER_ENDPOINT} is constant for BOOK_TICKER_ENDPOINT's endpoint
     * **/
    public static final String BOOK_TICKER_ENDPOINT = "/api/v3/ticker/bookTicker";

    /**
     * {@code SPOT_TEST_NEW_ORDER_ENDPOINT} is constant for SPOT_TEST_NEW_ORDER_ENDPOINT's endpoint
     * **/
    public static final String SPOT_TEST_NEW_ORDER_ENDPOINT = "/api/v3/order/test";

    /**
     * {@code SPOT_ORDER_ENDPOINT} is constant for SPOT_ORDER_ENDPOINT's endpoint
     * **/
    public static final String SPOT_ORDER_ENDPOINT = "/api/v3/order";

    /**
     * {@code SPOT_OPEN_ORDERS_ENDPOINT} is constant for SPOT_OPEN_ORDERS_ENDPOINT's endpoint
     * **/
    public static final String SPOT_OPEN_ORDERS_ENDPOINT = "/api/v3/openOrders";

    /**
     * {@code SPOT_ALL_ORDERS_LIST_ENDPOINT} is constant for SPOT_ALL_ORDERS_LIST_ENDPOINT's endpoint
     * **/
    public static final String SPOT_ALL_ORDERS_LIST_ENDPOINT = "/api/v3/allOrders";

    /**
     * {@code SPOT_OCO_ORDER_ENDPOINT} is constant for SPOT_OCO_ORDER_ENDPOINT's endpoint
     * **/
    public static final String SPOT_OCO_ORDER_ENDPOINT = "/api/v3/order/oco";

    /**
     * {@code SPOT_OCO_ORDER_LIST_ENDPOINT} is constant for SPOT_OCO_ORDER_LIST_ENDPOINT's endpoint
     * **/
    public static final String SPOT_OCO_ORDER_LIST_ENDPOINT = "/api/v3/orderList";

    /**
     * {@code SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT} is constant for SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT's endpoint
     * **/
    public static final String SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT = "/api/v3/allOrderList";

    /**
     * {@code SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT} is constant for SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT's endpoint
     * **/
    public static final String SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT = "/api/v3/openOrderList";

    /**
     * {@code SPOT_ACCOUNT_INFORMATION_ENDPOINT} is constant for SPOT_ACCOUNT_INFORMATION_ENDPOINT's endpoint
     * **/
    public static final String SPOT_ACCOUNT_INFORMATION_ENDPOINT = "/api/v3/account";

    /**
     * {@code SPOT_ACCOUNT_TRADE_LIST_ENDPOINT} is constant for SPOT_ACCOUNT_TRADE_LIST_ENDPOINT's endpoint
     * **/
    public static final String SPOT_ACCOUNT_TRADE_LIST_ENDPOINT = "/api/v3/myTrades";

    /**
     * {@code SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE} is constant for SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE's endpoint
     * **/
    public static final String SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE = "/api/v3/rateLimit/order";

    /**
     * {@code CROSS_MARGIN_TRANSFERS_ENDPOINT} is constant for CROSS_MARGIN_TRANSFERS_ENDPOINT's endpoint
     * **/
    public static final String CROSS_MARGIN_TRANSFERS_ENDPOINT = "/sapi/v1/margin/transfer";

    /**
     * {@code MARGIN_LOAN_ENDPOINT} is constant for MARGIN_LOAN_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_LOAN_ENDPOINT = "/sapi/v1/margin/loan";

    /**
     * {@code MARGIN_REPAY_ENDPOINT} is constant for MARGIN_REPAY_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_REPAY_ENDPOINT = "/sapi/v1/margin/repay";

    /**
     * {@code QUERY_MARGIN_ASSET_ENDPOINT} is constant for QUERY_MARGIN_ASSET_ENDPOINT's endpoint
     * **/
    public static final String QUERY_MARGIN_ASSET_ENDPOINT = "/sapi/v1/margin/asset";

    /**
     * {@code QUERY_ALL_MARGIN_ASSETS_ENDPOINT} is constant for QUERY_ALL_MARGIN_ASSETS_ENDPOINT's endpoint
     * **/
    public static final String QUERY_ALL_MARGIN_ASSETS_ENDPOINT = "/sapi/v1/margin/allAssets";

    /**
     * {@code QUERY_CROSS_MARGIN_PAIR_ENDPOINT} is constant for QUERY_CROSS_MARGIN_PAIR_ENDPOINT's endpoint
     * **/
    public static final String QUERY_CROSS_MARGIN_PAIR_ENDPOINT = "/sapi/v1/margin/pair";

    /**
     * {@code QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT} is constant for QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT's endpoint
     * **/
    public static final String QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT = "/sapi/v1/margin/allPairs";

    /**
     * {@code MARGIN_PRICE_INDEX_ENDPOINT} is constant for MARGIN_PRICE_INDEX_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_PRICE_INDEX_ENDPOINT = "/sapi/v1/margin/priceIndex";

    /**
     * {@code MARGIN_ORDER_ENDPOINT} is constant for MARGIN_ORDER_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_ORDER_ENDPOINT = "/sapi/v1/margin/order";

    /**
     * {@code MARGIN_OPEN_ORDERS_ENDPOINT} is constant for MARGIN_OPEN_ORDERS_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_OPEN_ORDERS_ENDPOINT = "/sapi/v1/margin/openOrders";

    /**
     * {@code MARGIN_INTEREST_HISTORY_ENDPOINT} is constant for MARGIN_INTEREST_HISTORY_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_INTEREST_HISTORY_ENDPOINT = "/sapi/v1/margin/interestHistory";

    /**
     * {@code MARGIN_FORCE_LIQUIDATION_ENDPOINT} is constant for MARGIN_FORCE_LIQUIDATION_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_FORCE_LIQUIDATION_ENDPOINT = "/sapi/v1/margin/forceLiquidationRec";

    /**
     * {@code CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT} is constant for CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT's endpoint
     * **/
    public static final String CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT = "/sapi/v1/margin/account";

    /**
     * {@code MARGIN_ALL_ORDERS_ENDPOINT} is constant for MARGIN_ALL_ORDERS_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_ALL_ORDERS_ENDPOINT = "/sapi/v1/margin/allOrders";

    /**
     * {@code MARGIN_OCO_ORDER_ENDPOINT} is constant for MARGIN_OCO_ORDER_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_OCO_ORDER_ENDPOINT = "/sapi/v1/margin/order/oco";

    /**
     * {@code MARGIN_OCO_ORDERS_ENDPOINT} is constant for MARGIN_OCO_ORDERS_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_OCO_ORDERS_ENDPOINT = "/sapi/v1/margin/orderList";

    /**
     * {@code MARGIN_OCO_ALL_ORDERS_ENDPOINT} is constant for MARGIN_OCO_ALL_ORDERS_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_OCO_ALL_ORDERS_ENDPOINT = "/sapi/v1/margin/allOrderList";

    /**
     * {@code MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT} is constant for MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT = "/sapi/v1/margin/openOrderList";

    /**
     * {@code MARGIN_TRADES_LIST_ENDPOINT} is constant for MARGIN_TRADES_LIST_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_TRADES_LIST_ENDPOINT = "/sapi/v1/margin/myTrades";

    /**
     * {@code GET_MAX_MARGIN_BORROW_ENDPOINT} is constant for GET_MAX_MARGIN_BORROW_ENDPOINT's endpoint
     * **/
    public static final String GET_MAX_MARGIN_BORROW_ENDPOINT = "/sapi/v1/margin/maxBorrowable";

    /**
     * {@code GET_MAX_MARGIN_TRANSFER_ENDPOINT} is constant for GET_MAX_MARGIN_TRANSFER_ENDPOINT's endpoint
     * **/
    public static final String GET_MAX_MARGIN_TRANSFER_ENDPOINT = "/sapi/v1/margin/maxTransferable";

    /**
     * {@code ISOLATED_MARGIN_TRANSFER_ENDPOINT} is constant for ISOLATED_MARGIN_TRANSFER_ENDPOINT's endpoint
     * **/
    public static final String ISOLATED_MARGIN_TRANSFER_ENDPOINT = "/sapi/v1/margin/isolated/transfer";

    /**
     * {@code ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT} is constant for ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT's endpoint
     * **/
    public static final String ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT = "/sapi/v1/margin/isolated/account";

    /**
     * {@code ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT} is constant for ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT's endpoint
     * **/
    public static final String ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT = "/sapi/v1/margin/isolated/accountLimit";

    /**
     * {@code QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT} is constant for QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT's endpoint
     * **/
    public static final String QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT = "/sapi/v1/margin/isolated/pair";

    /**
     * {@code QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT} is constant for QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT's endpoint
     * **/
    public static final String QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT = "/sapi/v1/margin/isolated/allPairs";

    /**
     * {@code MARGIN_BNB_ENDPOINT} is constant for MARGIN_BNB_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_BNB_ENDPOINT = "/sapi/v1/bnbBurn";

    /**
     * {@code MARGIN_INTEREST_RATE_HISTORY_ENDPOINT} is constant for MARGIN_INTEREST_RATE_HISTORY_ENDPOINT's endpoint
     * **/
    public static final String MARGIN_INTEREST_RATE_HISTORY_ENDPOINT = "/sapi/v1/margin/interestRateHistory";

    /**
     * {@code CROSS_MARGIN_DATA_ENDPOINT} is constant for CROSS_MARGIN_DATA_ENDPOINT's endpoint
     * **/
    public static final String CROSS_MARGIN_DATA_ENDPOINT = "/sapi/v1/margin/crossMarginData";

    /**
     * {@code ISOLATED_MARGIN_DATA_ENDPOINT} is constant for ISOLATED_MARGIN_DATA_ENDPOINT's endpoint
     * **/
    public static final String ISOLATED_MARGIN_DATA_ENDPOINT = "/sapi/v1/margin/isolatedMarginData";

    /**
     * {@code ISOLATED_MARGIN_TIER_DATA_ENDPOINT} is constant for ISOLATED_MARGIN_TIER_DATA_ENDPOINT's endpoint
     * **/
    public static final String ISOLATED_MARGIN_TIER_DATA_ENDPOINT = "/sapi/v1/margin/isolatedMarginTier";

}
