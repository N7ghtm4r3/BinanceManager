package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.OrderType;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.CrossMarginAccountDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccountTrade;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginMaxBorrow;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginSummary;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account.ComposedIMarginAccountInfo;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account.IsolatedMarginAccountLimit;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account.IsolatedMarginAccountStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties.BNBBurn;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties.IsolatedMarginFee;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties.IsolatedMarginSymbol;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties.IsolatedMarginTierData;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.liability.SmallLiabilityExchangeCoin;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.liability.SmallLiabilityExchangeHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response.MarginOrder.SideEffectType;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.UniversalTransferHistory.TransferType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.OrderType.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.SpotOrder.*;
import static java.lang.Long.parseLong;


/**
 * The {@code BinanceMarginManager} class is useful to manage all {@code "Binance"}'s Margin Endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">
 * Margin Account/Trade</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceMarginManager extends BinanceSignedManager {

    /**
     * {@code CROSS_MARGIN_TRANSFERS_ENDPOINT} is constant for CROSS_MARGIN_TRANSFERS_ENDPOINT's endpoint
     **/
    public static final String CROSS_MARGIN_TRANSFERS_ENDPOINT = "/sapi/v1/margin/transfer";

    /**
     * {@code MARGIN_LOAN_ENDPOINT} is constant for MARGIN_LOAN_ENDPOINT's endpoint
     **/
    public static final String MARGIN_LOAN_ENDPOINT = "/sapi/v1/margin/loan";

    /**
     * {@code MARGIN_REPAY_ENDPOINT} is constant for MARGIN_REPAY_ENDPOINT's endpoint
     **/
    public static final String MARGIN_REPAY_ENDPOINT = "/sapi/v1/margin/repay";

    /**
     * {@code QUERY_MARGIN_ASSET_ENDPOINT} is constant for QUERY_MARGIN_ASSET_ENDPOINT's endpoint
     **/
    public static final String QUERY_MARGIN_ASSET_ENDPOINT = "/sapi/v1/margin/asset";

    /**
     * {@code QUERY_ALL_MARGIN_ASSETS_ENDPOINT} is constant for QUERY_ALL_MARGIN_ASSETS_ENDPOINT's endpoint
     **/
    public static final String QUERY_ALL_MARGIN_ASSETS_ENDPOINT = "/sapi/v1/margin/allAssets";

    /**
     * {@code QUERY_CROSS_MARGIN_PAIR_ENDPOINT} is constant for QUERY_CROSS_MARGIN_PAIR_ENDPOINT's endpoint
     **/
    public static final String QUERY_CROSS_MARGIN_PAIR_ENDPOINT = "/sapi/v1/margin/pair";

    /**
     * {@code QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT} is constant for QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT's endpoint
     **/
    public static final String QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT = "/sapi/v1/margin/allPairs";

    /**
     * {@code MARGIN_PRICE_INDEX_ENDPOINT} is constant for MARGIN_PRICE_INDEX_ENDPOINT's endpoint
     **/
    public static final String MARGIN_PRICE_INDEX_ENDPOINT = "/sapi/v1/margin/priceIndex";

    /**
     * {@code MARGIN_ORDER_ENDPOINT} is constant for MARGIN_ORDER_ENDPOINT's endpoint
     **/
    public static final String MARGIN_ORDER_ENDPOINT = "/sapi/v1/margin/order";

    /**
     * {@code MARGIN_OPEN_ORDERS_ENDPOINT} is constant for MARGIN_OPEN_ORDERS_ENDPOINT's endpoint
     **/
    public static final String MARGIN_OPEN_ORDERS_ENDPOINT = "/sapi/v1/margin/openOrders";

    /**
     * {@code MARGIN_INTEREST_HISTORY_ENDPOINT} is constant for MARGIN_INTEREST_HISTORY_ENDPOINT's endpoint
     **/
    public static final String MARGIN_INTEREST_HISTORY_ENDPOINT = "/sapi/v1/margin/interestHistory";

    /**
     * {@code MARGIN_FORCE_LIQUIDATION_ENDPOINT} is constant for MARGIN_FORCE_LIQUIDATION_ENDPOINT's endpoint
     **/
    public static final String MARGIN_FORCE_LIQUIDATION_ENDPOINT = "/sapi/v1/margin/forceLiquidationRec";

    /**
     * {@code CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT} is constant for CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT's endpoint
     **/
    public static final String CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT = "/sapi/v1/margin/account";

    /**
     * {@code MARGIN_ALL_ORDERS_ENDPOINT} is constant for MARGIN_ALL_ORDERS_ENDPOINT's endpoint
     **/
    public static final String MARGIN_ALL_ORDERS_ENDPOINT = "/sapi/v1/margin/allOrders";

    /**
     * {@code MARGIN_OCO_ORDER_ENDPOINT} is constant for MARGIN_OCO_ORDER_ENDPOINT's endpoint
     **/
    public static final String MARGIN_OCO_ORDER_ENDPOINT = "/sapi/v1/margin/order/oco";

    /**
     * {@code MARGIN_OCO_ORDERS_ENDPOINT} is constant for MARGIN_OCO_ORDERS_ENDPOINT's endpoint
     **/
    public static final String MARGIN_OCO_ORDERS_ENDPOINT = "/sapi/v1/margin/orderList";

    /**
     * {@code MARGIN_OCO_ALL_ORDERS_ENDPOINT} is constant for MARGIN_OCO_ALL_ORDERS_ENDPOINT's endpoint
     **/
    public static final String MARGIN_OCO_ALL_ORDERS_ENDPOINT = "/sapi/v1/margin/allOrderList";

    /**
     * {@code MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT} is constant for MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT's endpoint
     **/
    public static final String MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT = "/sapi/v1/margin/openOrderList";

    /**
     * {@code MARGIN_TRADES_LIST_ENDPOINT} is constant for MARGIN_TRADES_LIST_ENDPOINT's endpoint
     **/
    public static final String MARGIN_TRADES_LIST_ENDPOINT = "/sapi/v1/margin/myTrades";

    /**
     * {@code GET_MAX_MARGIN_BORROW_ENDPOINT} is constant for GET_MAX_MARGIN_BORROW_ENDPOINT's endpoint
     **/
    public static final String GET_MAX_MARGIN_BORROW_ENDPOINT = "/sapi/v1/margin/maxBorrowable";

    /**
     * {@code GET_MAX_MARGIN_TRANSFER_ENDPOINT} is constant for GET_MAX_MARGIN_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String GET_MAX_MARGIN_TRANSFER_ENDPOINT = "/sapi/v1/margin/maxTransferable";

    /**
     * {@code GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT} is constant for GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT's endpoint
     **/
    public static final String GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT = "/sapi/v1/margin/tradeCoeff";

    /**
     * {@code ISOLATED_MARGIN_TRANSFER_ENDPOINT} is constant for ISOLATED_MARGIN_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String ISOLATED_MARGIN_TRANSFER_ENDPOINT = "/sapi/v1/margin/isolated/transfer";

    /**
     * {@code ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT} is constant for ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT's endpoint
     **/
    public static final String ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT = "/sapi/v1/margin/isolated/account";

    /**
     * {@code ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT} is constant for ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT's endpoint
     **/
    public static final String ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT = "/sapi/v1/margin/isolated/accountLimit";

    /**
     * {@code QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT} is constant for QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT's endpoint
     **/
    public static final String QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT = "/sapi/v1/margin/isolated/pair";

    /**
     * {@code QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT} is constant for QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT's endpoint
     **/
    public static final String QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT = "/sapi/v1/margin/isolated/allPairs";

    /**
     * {@code NEXT_HOURLY_INTEREST_RATE_ENDPOINT} is constant for NEXT_HOURLY_INTEREST_RATE_ENDPOINT's endpoint
     **/
    public static final String NEXT_HOURLY_INTEREST_RATE_ENDPOINT = "/sapi/v1/margin/next-hourly-interest-rate";

    /**
     * {@code MARGIN_BNB_ENDPOINT} is constant for MARGIN_BNB_ENDPOINT's endpoint
     **/
    public static final String MARGIN_BNB_ENDPOINT = "/sapi/v1/bnbBurn";

    /**
     * {@code MARGIN_INTEREST_RATE_HISTORY_ENDPOINT} is constant for MARGIN_INTEREST_RATE_HISTORY_ENDPOINT's endpoint
     **/
    public static final String MARGIN_INTEREST_RATE_HISTORY_ENDPOINT = "/sapi/v1/margin/interestRateHistory";

    /**
     * {@code CROSS_MARGIN_DATA_ENDPOINT} is constant for CROSS_MARGIN_DATA_ENDPOINT's endpoint
     **/
    public static final String CROSS_MARGIN_DATA_ENDPOINT = "/sapi/v1/margin/crossMarginData";

    /**
     * {@code ISOLATED_MARGIN_DATA_ENDPOINT} is constant for ISOLATED_MARGIN_DATA_ENDPOINT's endpoint
     **/
    public static final String ISOLATED_MARGIN_DATA_ENDPOINT = "/sapi/v1/margin/isolatedMarginData";

    /**
     * {@code ISOLATED_MARGIN_TIER_DATA_ENDPOINT} is constant for ISOLATED_MARGIN_TIER_DATA_ENDPOINT's endpoint
     **/
    public static final String ISOLATED_MARGIN_TIER_DATA_ENDPOINT = "/sapi/v1/margin/isolatedMarginTier";

    /**
     * {@code COLLATERAL_RATIO_ENDPOINT} is constant for COLLATERAL_RATIO_ENDPOINT's endpoint
     **/
    public static final String COLLATERAL_RATIO_ENDPOINT = "/sapi/v1/margin/crossMarginCollateralRatio";

    /**
     * {@code EXCHANGE_SMALL_LIABILITY_ENDPOINT} is constant for EXCHANGE_SMALL_LIABILITY_ENDPOINT's endpoint
     **/
    public static final String EXCHANGE_SMALL_LIABILITY_ENDPOINT = "/sapi/v1/margin/exchange-small-liability";

    /**
     * {@code EXCHANGE_SMALL_LIABILITY_HISTORY_ENDPOINT} is constant for EXCHANGE_SMALL_LIABILITY_HISTORY_ENDPOINT's endpoint
     **/
    public static final String EXCHANGE_SMALL_LIABILITY_HISTORY_ENDPOINT = "/sapi/v1/margin/exchange-small-liability-history";

    /**
     * Constructor to init a {@link BinanceMarginManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceMarginManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMarginManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceMarginManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMarginManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceMarginManager(String baseEndpoint, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceMarginManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceMarginManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMarginManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceSignedManager}'s manager without re-insert
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
    public BinanceMarginManager() {
        super();
    }

    /**
     * Request to execute a transfer
     *
     * @param asset:  asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type:   {@link MarginTransferType#MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link MarginTransferType#CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     *                it defines type of transfer
     * @return result transfer's tranId as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     * Cross Margin Account Transfer (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/transfer")
    public long executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type) throws Exception {
        return parseLong(executeCrossMarginAccountTransfer(asset, amount, type, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer
     *
     * @param asset:  asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type:   {@link MarginTransferType#MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link MarginTransferType#CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     *                it defines type of transfer
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result transfer's tranId as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     * Cross Margin Account Transfer (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/transfer")
    public <T> T executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type,
                                                   ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        payload.addParam("type", type);
        return returnTranId(sendPostSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, payload), format);
    }

    /**
     * Request to execute a transfer
     *
     * @param asset:      asset used in the request es. BTC
     * @param amount:     the amount to be transferred
     * @param type:       {@link MarginTransferType#MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link MarginTransferType#CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     *                    it defines type of transfer
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return result transfer's tranId as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     * Cross Margin Account Transfer (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/transfer")
    public long executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type,
                                                  long recvWindow) throws Exception {
        return parseLong(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a transfer
     *
     * @param asset:      asset used in the request es. BTC
     * @param amount:     the amount to be transferred
     * @param type:       {@link MarginTransferType#MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link MarginTransferType#CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     *                    it defines type of transfer
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return result transfer's tranId as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     * Cross Margin Account Transfer (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/transfer")
    public <T> T executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type,
                                                   long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        payload.addParam("type", type);
        payload.addParam("recvWindow", recvWindow);
        return returnTranId(sendPostSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, payload), format);
    }

    /**
     * Request to execute a margin account borrow
     *
     * @param asset:  asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @return result of margin account borrow request as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     * Margin Account Borrow (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/loan")
    public long applyMarginAccountBorrow(String asset, double amount) throws Exception {
        return parseLong(applyMarginAccountBorrow(asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a margin account borrow
     *
     * @param asset:  asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of margin account borrow request as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     * Margin Account Borrow (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/loan")
    public <T> T applyMarginAccountBorrow(String asset, double amount, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(MARGIN_LOAN_ENDPOINT, payload), format);
    }

    /**
     * Request to execute a margin account borrow
     *
     * @param asset:       asset used in the request es. BTC
     * @param amount:      the amount borrowed
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return result of margin account borrow request as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     * Margin Account Borrow (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/loan")
    public long applyMarginAccountBorrow(String asset, double amount, Params extraParams) throws Exception {
        return parseLong(applyMarginAccountBorrow(asset, amount, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to execute a margin account borrow
     *
     * @param asset:       asset used in the request es. BTC
     * @param amount:      the amount borrowed
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of margin account borrow request as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     * Margin Account Borrow (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/loan")
    public <T> T applyMarginAccountBorrow(String asset, double amount, Params extraParams,
                                          ReturnFormat format) throws Exception {
        extraParams.addParam("asset", asset);
        extraParams.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(MARGIN_LOAN_ENDPOINT, extraParams), format);
    }

    /**
     * Request to execute an repay margin account request
     *
     * @param asset:  asset used in the request es. BTC
     * @param amount: the amount repaid
     * @return result of repay margin account request as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     * Margin Account Repay (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/repay")
    public long repayMarginAccount(String asset, double amount) throws Exception {
        return parseLong(repayMarginAccount(asset, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to execute an repay margin account request
     *
     * @param asset:  asset used in the request es. BTC
     * @param amount: the amount repaid
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of repay margin account request as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     * Margin Account Repay (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/repay")
    public <T> T repayMarginAccount(String asset, double amount, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(MARGIN_REPAY_ENDPOINT, payload), format);
    }

    /**
     * Request to execute an repay margin account request
     *
     * @param asset:       asset used in the request es. BTC
     * @param amount:      the amount repaid
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return result of repay margin account request as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     * Margin Account Repay (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/repay")
    public long repayMarginAccount(String asset, double amount, Params extraParams) throws Exception {
        return parseLong(repayMarginAccount(asset, amount, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to execute an repay margin account request
     *
     * @param asset:       asset used in the request es. BTC
     * @param amount:      the amount repaid
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of repay margin account request as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     * Margin Account Repay (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/repay")
    public <T> T repayMarginAccount(String asset, double amount, Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("asset", asset);
        extraParams.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(MARGIN_REPAY_ENDPOINT, extraParams), format);
    }

    /**
     * Request to get margin asset details
     *
     * @param asset: asset used in the request es. BTC
     * @return detail's asset as {@link String}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data">
     * Query Margin Asset (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/asset")
    public MarginAsset queryMarginAsset(String asset) throws Exception {
        return queryMarginAsset(asset, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin asset details
     *
     * @param asset:  asset used in the request es. BTC
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return detail's asset as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data">
     * Query Margin Asset (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/asset")
    public <T> T queryMarginAsset(String asset, ReturnFormat format) throws Exception {
        String assetResponse = sendGetSignedRequest(QUERY_MARGIN_ASSET_ENDPOINT, getTimestampParam() + "&asset="
                + asset);
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginAsset(new JSONObject(assetResponse));
            default:
                return (T) assetResponse;
        }
    }

    /**
     * Request to get all margin asset details <br>
     * No-any params required
     *
     * @return all asset details as {@link ArrayList} of {@link MarginAsset}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
     * Get All Margin Assets (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allAssets")
    public ArrayList<MarginAsset> queryAllMarginAssets() throws Exception {
        return queryAllMarginAssets(LIBRARY_OBJECT);
    }

    /**
     * Request to get all margin asset details
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return all asset details as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
     * Get All Margin Assets (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/allAssets")
    public <T> T queryAllMarginAssets(ReturnFormat format) throws Exception {
        String assetsResponse = sendGetSignedRequest(QUERY_ALL_MARGIN_ASSETS_ENDPOINT);
        switch (format) {
            case JSON:
                return (T) new JSONArray(assetsResponse);
            case LIBRARY_OBJECT:
                ArrayList<MarginAsset> marginAssets = new ArrayList<>();
                JSONArray jAssets = new JSONArray(assetsResponse);
                for (int j = 0; j < jAssets.length(); j++)
                    marginAssets.add(new MarginAsset(jAssets.getJSONObject(j)));
                return (T) marginAssets;
            default:
                return (T) assetsResponse;
        }
    }

    /**
     * Request to get cross margin pair details
     *
     * @param asset: asset used in the request es. BTC
     * @return cross margin pair as {@link MarginPair} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data">
     * Query Cross Margin Pair (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/pair")
    public MarginPair queryCrossMarginPair(String asset) throws Exception {
        return queryCrossMarginPair(asset, LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin pair details
     *
     * @param symbol: symbol used in the request es. BTCUSDT
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross margin pair as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data">
     * Query Cross Margin Pair (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/pair")
    public <T> T queryCrossMarginPair(String symbol, ReturnFormat format) throws Exception {
        String assetResponse = sendGetSignedRequest(QUERY_CROSS_MARGIN_PAIR_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol);
        switch (format) {
            case JSON:
                return (T) new JSONObject(assetResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginPair(new JSONObject(assetResponse));
            default:
                return (T) assetResponse;
        }
    }

    /**
     * Request to get all cross margin pair details <br>
     * No-any params required
     *
     * @return all cross margin pair as {@link ArrayList} of {@link MarginPair}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
     * Get All Cross Margin Pairs (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allPairs")
    public ArrayList<MarginPair> queryAllMarginPairs() throws Exception {
        return queryAllMarginPairs(LIBRARY_OBJECT);
    }

    /**
     * Request to get all cross margin pair details
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return all cross margin pair as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
     * Get All Cross Margin Pairs (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/allPairs")
    public <T> T queryAllMarginPairs(ReturnFormat format) throws Exception {
        String pairsResponse = sendGetSignedRequest(QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT);
        switch (format) {
            case JSON:
                return (T) new JSONArray(pairsResponse);
            case LIBRARY_OBJECT:
                ArrayList<MarginPair> pairs = new ArrayList<>();
                JSONArray jPairs = new JSONArray(pairsResponse);
                for (int j = 0; j < jPairs.length(); j++)
                    pairs.add(new MarginPair(jPairs.getJSONObject(j)));
                return (T) pairs;
            default:
                return (T) pairsResponse;
        }
    }

    /**
     * Request to get price index of a symbol
     *
     * @param symbol: symbol used to get price index
     * @return priceIndex of a symbol as {@link MarginPriceIndex} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data">
     * Query Margin PriceIndex (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/priceIndex")
    public MarginPriceIndex getMarginPriceIndex(String symbol) throws Exception {
        return getMarginPriceIndex(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get price index of a symbol
     *
     * @param symbol: symbol used to get price index
     * @return priceIndex of a symbol as {@link MarginPriceIndex} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data">
     * Query Margin PriceIndex (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/priceIndex")
    public <T> T getMarginPriceIndex(String symbol, ReturnFormat format) throws Exception {
        String priceResponse = sendGetSignedRequest(MARGIN_PRICE_INDEX_ENDPOINT, "?symbol=" + symbol);
        switch (format) {
            case JSON:
                return (T) new JSONObject(priceResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginPriceIndex(new JSONObject(priceResponse));
            default:
                return (T) priceResponse;
        }
    }

    /**
     * Request to send a limit margin order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default FULL]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return result of the order as {@link FullMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendLimitOrder(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                                    double price, Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, LIMIT, getLimitPayload(timeInForce, quantity,
                price, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a limit margin order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default FULL]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendLimitOrder(String symbol, Side side, TimeInForce timeInForce, double quantity, double price,
                                Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, LIMIT, getLimitPayload(timeInForce, quantity,
                price, extraParams), format);
    }

    /**
     * Request to send a market margin order with quantity
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default FULL]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link FullMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendMarketOrderQty(String symbol, Side side, double quantity,
                                                        Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, MARKET, getMarketPayload("quantity",
                quantity, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a market margin order with quantity
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default FULL]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendMarketOrderQty(String symbol, Side side, double quantity, Params extraParams,
                                    ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, MARKET, getMarketPayload("quantity",
                quantity, extraParams), format);
    }

    /**
     * Request to send a market margin order with quote quantity
     *
     * @param symbol:        symbol used in the request es. BTCBUSD
     * @param side:          side of the order
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams:   additional params of the request (insert {@code "null"} if there are no extra params),
     *                       keys accepted are:
     *                       <ul>
     *                           <li>
     *                               {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                           </li>
     *                           <li>
     *                               {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                               if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                               MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default FULL]
     *                           </li>
     *                           <li>
     *                                {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                - [ENUM, default NO_SIDE_EFFECT]
     *                            </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                       </ul>
     * @return result of the order as {@link FullMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendMarketOrderQuoteQty(String symbol, Side side, double quoteQuantity,
                                                             Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, MARKET, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a market margin order with quote quantity
     *
     * @param symbol:        symbol used in the request es. BTCBUSD
     * @param side:          side of the order
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams:   additional params of the request (insert {@code "null"} if there are no extra params),
     *                       keys accepted are:
     *                       <ul>
     *                           <li>
     *                               {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                           </li>
     *                           <li>
     *                               {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                               if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                               MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default FULL]
     *                           </li>
     *                           <li>
     *                                {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                - [ENUM, default NO_SIDE_EFFECT]
     *                            </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                       </ul>
     * @param format:        return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendMarketOrderQuoteQty(String symbol, Side side, double quoteQuantity, Params extraParams,
                                         ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, MARKET, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), format);
    }

    /**
     * Request to send a stop loss margin order with stopPrice
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param stopPrice:   stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendStopLossOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                        Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, STOP_LOSS, getLevelPayload(quantity,
                "stopPrice", stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a stop loss margin order with stopPrice
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param stopPrice:   stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendStopLossOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, STOP_LOSS, getLevelPayload(quantity,
                "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to send a stop loss limit margin order with stop price
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param stopPrice:   stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendStopLossLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce,
                                                                 double quantity, double price, double stopPrice,
                                                                 Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a stop loss limit margin order with stop price
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param stopPrice:   stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendStopLossLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                             double price, double stopPrice, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to send a take profit margin order with stop price
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param stopPrice:   stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendTakeProfitOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                                              Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, TAKE_PROFIT, getLevelPayload(quantity,
                "stopPrice", stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a take profit margin order with stop price
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param stopPrice:   stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendTakeProfitOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                          Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, TAKE_PROFIT, getLevelPayload(quantity,
                "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to send a take profit limit margin order with stop price value
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param stopPrice:   stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendTakeProfitLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce,
                                                                   double quantity, double price, double stopPrice,
                                                                   Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a take profit limit margin order with stop price value
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param stopPrice:   stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                     keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                             MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendTakeProfitLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                               double price, double stopPrice, Params extraParams,
                                               ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to send a limit maker margin order with trailing delta
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                           {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                           MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendLimitMakerOrder(String symbol, Side side, double quantity, double price,
                                                         Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, null, LIMIT_MAKER, getLimitMakerPayload(quantity, price,
                extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a limit maker margin order with trailing delta
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                           {@code "newOrderRespType"} -> set the response JSON. ACK, RESULT, or FULL;
     *                           MARKET and LIMIT order types default to FULL, all other orders default to ACK - [STRING, default ACK]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendLimitMakerOrder(String symbol, Side side, double quantity, double price, Params extraParams,
                                     ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, null, LIMIT_MAKER, getLimitMakerPayload(quantity, price,
                extraParams), format);
    }

    /**
     * Request to send a limit margin order
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price:            price value in the order
     * @param extraParams:      additional params of the request (insert {@code "null"} if there are no extra params),
     *                          keys accepted are:
     *                          <ul>
     *                              <li>
     *                                  {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                  {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                  if not sent - [STRING]
     *                              </li>
     *                              <li>
     *                                  {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                              </li>
     *                              <li>
     *                                   {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                   - [ENUM, default NO_SIDE_EFFECT]
     *                               </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                          </ul>
     * @return result of the order as {@link FullMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendLimitOrder(String symbol, Side side, OrderResponseType newOrderRespType,
                                                    TimeInForce timeInForce, double quantity, double price,
                                                    Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, LIMIT, getLimitPayload(timeInForce, quantity, price,
                extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a limit margin order
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price:            price value in the order
     * @param extraParams:      additional params of the request (insert {@code "null"} if there are no extra params),
     *                          keys accepted are:
     *                          <ul>
     *                              <li>
     *                                  {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                  {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                  if not sent - [STRING]
     *                              </li>
     *                              <li>
     *                                  {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                              </li>
     *                              <li>
     *                                   {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                   - [ENUM, default NO_SIDE_EFFECT]
     *                               </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendLimitOrder(String symbol, Side side, OrderResponseType newOrderRespType, TimeInForce timeInForce,
                                double quantity, double price, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, LIMIT, getLimitPayload(timeInForce, quantity, price,
                extraParams), format);
    }

    /**
     * Request to send a market margin order with quantity
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return result of the order as {@link FullMarginOrder}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendMarketOrderQty(String symbol, Side side, OrderResponseType newOrderRespType,
                                                        double quantity, Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, MARKET, getMarketPayload("quantity", quantity,
                extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a market margin order with quantity
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendMarketOrderQty(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                    Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, MARKET, getMarketPayload("quantity", quantity,
                extraParams), format);
    }

    /**
     * Request to send a market margin order with quote quantity
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity:    quote quantity value in the order
     * @param extraParams:      additional params of the request (insert {@code "null"} if there are no extra params),
     *                          keys accepted are:
     *                          <ul>
     *                              <li>
     *                                  {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                              </li>
     *                              <li>
     *                                  {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                  if not sent - [STRING]
     *                              </li>
     *                              <li>
     *                                  {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                              </li>
     *                              <li>
     *                                   {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                   - [ENUM, default NO_SIDE_EFFECT]
     *                               </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                          </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendMarketOrderQuoteQty(String symbol, Side side, OrderResponseType newOrderRespType,
                                                             double quoteQuantity, Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, MARKET, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a market margin order with quote quantity
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity:    quote quantity value in the order
     * @param extraParams:      additional params of the request (insert {@code "null"} if there are no extra params),
     *                          keys accepted are:
     *                          <ul>
     *                              <li>
     *                                  {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                              </li>
     *                              <li>
     *                                  {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                  if not sent - [STRING]
     *                              </li>
     *                              <li>
     *                                  {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                              </li>
     *                              <li>
     *                                   {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                   - [ENUM, default NO_SIDE_EFFECT]
     *                               </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendMarketOrderQuoteQty(String symbol, Side side, OrderResponseType newOrderRespType,
                                         double quoteQuantity, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, MARKET, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), format);
    }

    /**
     * Request to send a stop loss margin order with stopPrice
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param stopPrice:        stop price value for the order
     * @param extraParams:      additional params of the request (insert {@code "null"} if there are no extra params),
     *                          keys accepted are:
     *                          <ul>
     *                              <li>
     *                                  {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                              </li>
     *                              <li>
     *                                  {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                  {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                  if not sent - [STRING]
     *                              </li>
     *                              <li>
     *                                  {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                              </li>
     *                              <li>
     *                                   {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                   - [ENUM, default NO_SIDE_EFFECT]
     *                               </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                          </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendStopLossOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                            double quantity, double stopPrice,
                                                            Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, STOP_LOSS, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a stop loss margin order with stopPrice
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param stopPrice:        stop price value for the order
     * @param extraParams:      additional params of the request (insert {@code "null"} if there are no extra params),
     *                          keys accepted are:
     *                          <ul>
     *                              <li>
     *                                  {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                              </li>
     *                              <li>
     *                                  {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                  {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                  if not sent - [STRING]
     *                              </li>
     *                              <li>
     *                                  {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                              </li>
     *                              <li>
     *                                   {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                   - [ENUM, default NO_SIDE_EFFECT]
     *                               </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                          </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendStopLossOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                        double stopPrice, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, STOP_LOSS, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /**
     * Request to send a stop loss limit margin order with stop price
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce:      time in force for the order
     * @param quantity:         quantity value in the order
     * @param price:            price value in the order
     * @param stopPrice:        stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendStopLossLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                                 TimeInForce timeInForce, double quantity, double price,
                                                                 double stopPrice, Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a stop loss limit margin order with stop price
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce:      time in force for the order
     * @param quantity:         quantity value in the order
     * @param price:            price value in the order
     * @param stopPrice:        stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendStopLossLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                             TimeInForce timeInForce, double quantity, double price, double stopPrice,
                                             Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to send a take profit margin order with stop price
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param stopPrice:        stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendTakeProfitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                              double quantity, double stopPrice,
                                                              Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, TAKE_PROFIT, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a take profit margin order with stop price
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param stopPrice:        stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendTakeProfitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                          double stopPrice, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, TAKE_PROFIT, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /**
     * Request to send a take profit limit margin order with stop price value
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce:      time in force for the order
     * @param quantity:         quantity value in the order
     * @param price:            price value in the order
     * @param stopPrice:        stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendTakeProfitLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                                   TimeInForce timeInForce, double quantity, double price,
                                                                   double stopPrice, Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a take profit limit margin order with stop price value
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce:      time in force for the order
     * @param quantity:         quantity value in the order
     * @param price:            price value in the order
     * @param stopPrice:        stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendTakeProfitLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                               TimeInForce timeInForce, double quantity, double price, double stopPrice,
                                               Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to send a limit maker margin order with trailing delta
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param price:            price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return result of the order as {@link ACKMarginOrder}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T extends MarginOrder> T sendLimitMakerOrder(String symbol, Side side, OrderResponseType newOrderRespType,
                                                         double quantity, double price, Params extraParams) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, LIMIT_MAKER, getLimitMakerPayload(quantity, price,
                extraParams), LIBRARY_OBJECT);
    }

    /**
     * Request to send a limit maker margin order with trailing delta
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity:         quantity value in the order
     * @param price:            price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params),
     *                   keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                            - [ENUM, default NO_SIDE_EFFECT]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     * Margin Account New Order (TRADE)</a>
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/order")
    public <T> T sendLimitMakerOrder(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                     double price, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewMarginOrder(symbol, side, newOrderRespType, LIMIT_MAKER, getLimitMakerPayload(quantity, price,
                extraParams), format);
    }

    /**
     * Request to send a new margin order
     *
     * @param symbol:           symbol used to the order es. BTCBUSD
     * @param side:             BUY or SELL order
     * @param type:             LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams:      additional params of the request
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     **/
    @Returner
    private <T> T returnNewMarginOrder(String symbol, Side side, OrderResponseType newOrderRespType, OrderType type,
                                       Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("newOrderRespType", newOrderRespType);
        extraParams.addParam("type", type);
        String orderResponse = sendPostSignedRequest(MARGIN_ORDER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderResponse);
            case LIBRARY_OBJECT:
                JSONObject orderResult = new JSONObject(orderResponse);
                if (newOrderRespType != null) {
                    switch (newOrderRespType) {
                        case RESULT:
                            return (T) new ResultMarginOrder(orderResult);
                        case FULL:
                            return (T) new FullMarginOrder(orderResult);
                        default:
                            return (T) new ACKMarginOrder(orderResult);
                    }
                } else {
                    if (type.equals(LIMIT) || type.equals(MARKET))
                        return (T) new FullMarginOrder(orderResult);
                    else
                        return (T) new ACKMarginOrder(orderResult);
                }
            default:
                return (T) orderResponse;
        }
    }

    /**
     * Request to get cancel a margin order
     *
     * @param order: order to delete
     * @return cancel a margin order response as {@link MarginOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public MarginOrderDetails cancelMarginOrder(MarginOrder order) throws Exception {
        return cancelMarginOrder(order.getSymbol(), LIBRARY_OBJECT);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param order:  order to delete
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cancel a margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public <T> T cancelMarginOrder(MarginOrder order, ReturnFormat format) throws Exception {
        return cancelMarginOrder(order.getSymbol(), format);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param symbol: symbol used in the order es. BTCBUSD
     * @return cancel a margin order response as {@link MarginOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public MarginOrderDetails cancelMarginOrder(String symbol) throws Exception {
        return cancelMarginOrder(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param symbol: symbol used in the order es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cancel a margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public <T> T cancelMarginOrder(String symbol, ReturnFormat format) throws Exception {
        return returnMarginOrderDetails(sendDeleteSignedRequest(MARGIN_ORDER_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol), format);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param order:       order to delete
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> order identifier - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return cancel a margin order response as {@link MarginOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public MarginOrderDetails cancelMarginOrder(MarginOrder order, Params extraParams) throws Exception {
        return cancelMarginOrder(order.getSymbol(), extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param order:       order to delete
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> order identifier - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cancel a margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public <T> T cancelMarginOrder(MarginOrder order, Params extraParams, ReturnFormat format) throws Exception {
        return cancelMarginOrder(order.getSymbol(), extraParams, format);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param symbol:      symbol used in the order es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                           {@code "orderId"} -> order identifier - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @return cancel a margin order response as {@link MarginOrderDetails} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public MarginOrderDetails cancelMarginOrder(String symbol, Params extraParams) throws Exception {
        return cancelMarginOrder(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get cancel a margin order
     *
     * @param symbol:      symbol used in the order es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                           {@code "orderId"} -> order identifier - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cancel a margin order response as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     * Margin Account Cancel Order (TRADE)</a>
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
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/order")
    public <T> T cancelMarginOrder(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol,
                extraParams);
        return returnMarginOrderDetails(sendDeleteSignedRequest(MARGIN_ORDER_ENDPOINT, params), format);
    }

    /**
     * Method to create a margin order details
     *
     * @param marginDetailsResponse: obtained from Binance's response
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return margin order details as {@code "format"} defines
     **/
    @Returner
    private <T> T returnMarginOrderDetails(String marginDetailsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(marginDetailsResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginOrderDetails(new JSONObject(marginDetailsResponse));
            default:
                return (T) marginDetailsResponse;
        }
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:    used in the order es. BTCBUSD
     * @param #side:      BUY or SELL
     * @param #quantity:  used in the order es. 0.00542
     * @param #price:     used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @return new OCO margin order response as {@link OCOMarginOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     * Margin Account New OCO (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public OCOMarginOrder sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price,
                                                double stopPrice) throws Exception {
        return sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, LIBRARY_OBJECT);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:    used in the order es. BTCBUSD
     * @param #side:      BUY or SELL
     * @param #quantity:  used in the order es. 0.00542
     * @param #price:     used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return new OCO margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     * Margin Account New OCO (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price,
                                       double stopPrice, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("symbol", symbol);
        payload.addParam("side", side);
        payload.addParam("quantity", quantity);
        payload.addParam("price", price);
        payload.addParam("stopPrice", stopPrice);
        return returnOCOMarginOrder(sendPostRequest(MARGIN_OCO_ORDER_ENDPOINT, payload), format);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:     used in the order es. BTCBUSD
     * @param #side:       BUY or SELL
     * @param #quantity:   used in the order es. 0.00542
     * @param #price:      used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice:  used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> stop limit time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> a unique Id for the limit order - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> a unique Id for the stop loss/stop loss limit leg - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "stopIcebergQty"} -> stop iceberg quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response {@code "JSON"} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return new OCO margin order response as {@link OCOMarginOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     * Margin Account New OCO (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public OCOMarginOrder sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price,
                                                double stopPrice, Params extraParams) throws Exception {
        return sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:     used in the order es. BTCBUSD
     * @param #side:       BUY or SELL
     * @param #quantity:   used in the order es. 0.00542
     * @param #price:      used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice:  used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> stop limit time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> a unique Id for the limit order - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> a unique Id for the stop loss/stop loss limit leg - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "stopIcebergQty"} -> stop iceberg quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> set the response {@code "JSON"} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                              {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                              - [ENUM, default NO_SIDE_EFFECT]
     *                          </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return new OCO margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     * Margin Account New OCO (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                       Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("quantity", quantity);
        extraParams.addParam("price", price);
        extraParams.addParam("stopPrice", stopPrice);
        return returnOCOMarginOrder(sendPostSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, extraParams), format);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:               used in the order es. BTCBUSD
     * @param #side:                 BUY or SELL
     * @param #quantity:             used in the order es. 0.00542
     * @param #price:                used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice:            used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice:       used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @return new OCO margin order response as {@link OCOMarginOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public OCOMarginOrder sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price,
                                                double stopPrice, double stopLimitPrice,
                                                TimeInForce stopLimitTimeInForce) throws Exception {
        return sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, stopLimitPrice, stopLimitTimeInForce,
                LIBRARY_OBJECT);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:               used in the order es. BTCBUSD
     * @param #side:                 BUY or SELL
     * @param #quantity:             used in the order es. 0.00542
     * @param #price:                used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice:            used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice:       used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return new OCO margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                       double stopLimitPrice, TimeInForce stopLimitTimeInForce,
                                       ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("symbol", symbol);
        payload.addParam("side", side);
        payload.addParam("quantity", quantity);
        payload.addParam("price", price);
        payload.addParam("stopPrice", stopPrice);
        payload.addParam("stopLimitPrice", stopLimitPrice);
        payload.addParam("stopLimitTimeInForce", stopLimitTimeInForce);
        return returnOCOMarginOrder(sendPostSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, payload), format);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:               used in the order es. BTCBUSD
     * @param #side:                 BUY or SELL
     * @param #quantity:             used in the order es. 0.00542
     * @param #price:                used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice:            used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice:       used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                   <li>
     *                                       {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "orderId"} -> a unique Id for the limit order - [LONG]
     *                                   </li>
     *                                   <li>
     *                                       {@code "stopClientOrderId"} -> a unique Id for the stop loss/stop loss limit leg - [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "stopIcebergQty"} -> stop iceberg quantity - [DECIMAL]
     *                                   </li>
     *                                   <li>
     *                                       {@code "newOrderRespType"} -> set the response {@code "JSON"} - [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                       if not sent - [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                                   </li>
     *                                   <li>
     *                                        {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                        - [ENUM, default NO_SIDE_EFFECT]
     *                                    </li>
     *                                   <li>
     *                                        {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                   </li>
     *                               </ul>
     * @return new OCO margin order response as {@link OCOMarginOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     * Margin Account New OCO (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public OCOMarginOrder sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                                double stopLimitPrice, TimeInForce stopLimitTimeInForce,
                                                Params extraParams) throws Exception {
        return sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, stopLimitPrice, stopLimitTimeInForce,
                extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send new OCO margin order
     *
     * @param #symbol:               used in the order es. BTCBUSD
     * @param #side:                 BUY or SELL
     * @param #quantity:             used in the order es. 0.00542
     * @param #price:                used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice:            used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice:       used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                   <li>
     *                                       {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "orderId"} -> a unique Id for the limit order - [LONG]
     *                                   </li>
     *                                   <li>
     *                                       {@code "stopClientOrderId"} -> a unique Id for the stop loss/stop loss limit leg - [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "stopIcebergQty"} -> stop iceberg quantity - [DECIMAL]
     *                                   </li>
     *                                   <li>
     *                                       {@code "newOrderRespType"} -> set the response {@code "JSON"} - [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                       if not sent - [STRING]
     *                                   </li>
     *                                   <li>
     *                                       {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                                   </li>
     *                                   <li>
     *                                        {@code "sideEffectType"} -> side effect type, constants available {@link SideEffectType}
     *                                        - [ENUM, default NO_SIDE_EFFECT]
     *                                    </li>
     *                                   <li>
     *                                        {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                   </li>
     *                               </ul>
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return new OCO margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     * Margin Account New OCO (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                       double stopLimitPrice, TimeInForce stopLimitTimeInForce, Params extraParams,
                                       ReturnFormat format) throws Exception {
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("quantity", quantity);
        extraParams.addParam("price", price);
        extraParams.addParam("stopPrice", stopPrice);
        extraParams.addParam("stopLimitPrice", stopLimitPrice);
        extraParams.addParam("stopLimitTimeInForce", stopLimitTimeInForce);
        return returnOCOMarginOrder(sendPostSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create an OCO margin order
     *
     * @param OCOMarginOrderResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return OCO margin order as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOCOMarginOrder(String OCOMarginOrderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(OCOMarginOrderResponse);
            case LIBRARY_OBJECT:
                return (T) new OCOMarginOrder(new JSONObject(OCOMarginOrderResponse));
            default:
                return (T) OCOMarginOrderResponse;
        }
    }

    /**
     * Request to get cancel all a margin orders
     *
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @return cancel all margin order response as {@link OpenMarginOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     * Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/openOrders")
    public OpenMarginOrders cancelAllMarginOrders(String symbol) throws Exception {
        return cancelAllMarginOrders(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get cancel all a margin orders
     *
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cancel all margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     * Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/openOrders")
    public <T> T cancelAllMarginOrders(String symbol, ReturnFormat format) throws Exception {
        return returnOpenMarginOrders(sendDeleteSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol), format);
    }

    /**
     * Request to get cancel all a margin orders
     *
     * @param symbol:      symbol used in the orders es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return cancel all margin order response as {@link OpenMarginOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     * Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/openOrders")
    public OpenMarginOrders cancelAllMarginOrders(String symbol, Params extraParams) throws Exception {
        return cancelAllMarginOrders(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get cancel all a margin orders
     *
     * @param symbol:      symbol used in the orders es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return cancel all margin order response as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     * Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
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
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/openOrders")
    public <T> T cancelAllMarginOrders(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnOpenMarginOrders(sendDeleteSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params), format);
    }

    /**
     * Method to create an open margin orders list
     *
     * @param ordersResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return open margin orders as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOpenMarginOrders(String ordersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(ordersResponse);
            case LIBRARY_OBJECT:
                return (T) new OpenMarginOrders(new JSONArray(ordersResponse));
            default:
                return (T) ordersResponse;
        }
    }

    /**
     * Request to get cross margin transfer history <br>
     * No-any params required
     *
     * @return cross margin transfer history response as {@link TransfersHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public TransfersHistoryList getCrossMarginTransfersHistory() throws Exception {
        return getCrossMarginTransfersHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross margin transfer history response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransfersHistory(ReturnFormat format) throws Exception {
        return returnTransfersHistoryList(sendGetSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, getTimestampParam()),
                format);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> transfer type, constants available {@link TransferType} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross margin transfer history response as {@link TransfersHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public TransfersHistoryList getCrossMarginTransfersHistory(Params extraParams) throws Exception {
        return getCrossMarginTransfersHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> transfer type, constants available {@link TransferType} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return cross margin transfer history response as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransfersHistory(Params extraParams, ReturnFormat format) throws Exception {
        return returnTransfersHistoryList(sendGetSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param asset: asset used to request es BTC
     * @return cross margin transfer history response as {@link TransfersHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public TransfersHistoryList getCrossMarginTransfersHistory(String asset) throws Exception {
        return getCrossMarginTransfersHistory(asset, LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param asset:  asset used to request es BTC
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross margin transfer history response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransfersHistory(String asset, ReturnFormat format) throws Exception {
        return returnTransfersHistoryList(sendGetSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, getTimestampParam()
                + "&asset=" + asset), format);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> transfer type, constants available {@link TransferType} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross margin transfer history response as {@link TransfersHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public TransfersHistoryList getCrossMarginTransferHistory(String asset, Params extraParams) throws Exception {
        return getCrossMarginTransferHistory(asset, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin transfer history
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> transfer type, constants available {@link TransferType} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfers records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cross margin transfer history response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     * Get Cross Margin Transfer History (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransferHistory(String asset, Params extraParams, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnTransfersHistoryList(sendGetSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params), format);
    }

    /**
     * Method to create a transfers history list
     *
     * @param transfersHistoryResponse: obtained from Binance's response
     * @param format:                   return type formatter -> {@link ReturnFormat}
     * @return transfers history list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTransfersHistoryList(String transfersHistoryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(transfersHistoryResponse);
            case LIBRARY_OBJECT:
                return (T) new TransfersHistoryList(new JSONObject(transfersHistoryResponse));
            default:
                return (T) transfersHistoryResponse;
        }
    }

    /**
     * Request to get query loan record
     *
     * @param asset: asset used to request es BTC
     * @return query loan record as {@link LoansList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     * Query Loan Record (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/loan")
    public LoansList getLoansList(String asset) throws Exception {
        return getLoansList(asset, LIBRARY_OBJECT);
    }

    /**
     * Request to get query loan record
     *
     * @param asset:  asset used to request es BTC
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return query loan record as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     * Query Loan Record (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/loan")
    public <T> T getLoansList(String asset, ReturnFormat format) throws Exception {
        return returnLoansList(sendGetSignedRequest(MARGIN_LOAN_ENDPOINT, getTimestampParam() + "&asset=" + asset),
                format);
    }

    /**
     * Request to get query loan record
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate loans records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate loans records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return query loan record as {@link LoansList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     * Query Loan Record (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/loan")
    public LoansList getLoansList(String asset, Params extraParams) throws Exception {
        return getLoansList(asset, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get query loan record
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate loans records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate loans records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return query loan record as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     * Query Loan Record (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/loan")
    public <T> T getLoansList(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnLoansList(sendGetSignedRequest(MARGIN_LOAN_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&asset=" + asset, extraParams)), format);
    }

    /**
     * Method to create a loans list
     *
     * @param loansResponse: obtained from Binance's response
     * @param format:        return type formatter -> {@link ReturnFormat}
     * @return loans list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnLoansList(String loansResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(loansResponse);
            case LIBRARY_OBJECT:
                return (T) new LoansList(new JSONObject(loansResponse));
            default:
                return (T) loansResponse;
        }
    }

    /**
     * Request to get query repay record
     *
     * @param asset: asset used to request es BTC
     * @return query repay record as {@link RepaysList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     * Query Repay Record (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/repay")
    public RepaysList getRepaysList(String asset) throws Exception {
        return getRepaysList(asset, LIBRARY_OBJECT);
    }

    /**
     * Request to get query repay record
     *
     * @param asset: asset used to request es BTC
     * @return query repay record as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     * Query Repay Record (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/repay")
    public <T> T getRepaysList(String asset, ReturnFormat format) throws Exception {
        return returnRepaysList(sendGetSignedRequest(MARGIN_REPAY_ENDPOINT, getTimestampParam() + "&asset=" + asset),
                format);
    }

    /**
     * Request to get query repay record
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate repays records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate repays records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return query repay record as {@link RepaysList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     * Query Repay Record (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/repay")
    public RepaysList getRepaysList(String asset, Params extraParams) throws Exception {
        return getRepaysList(asset, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get query repay record
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate repays records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate repays records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return query repay record as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     * Query Repay Record (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/repay")
    public <T> T getRepaysList(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnRepaysList(sendGetSignedRequest(MARGIN_REPAY_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&asset=" + asset, extraParams)), format);
    }

    /**
     * Method to create a repays list
     *
     * @param repaysResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return repays list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnRepaysList(String repaysResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(repaysResponse);
            case LIBRARY_OBJECT:
                return (T) new RepaysList(new JSONObject(repaysResponse));
            default:
                return (T) repaysResponse;
        }
    }

    /**
     * Request to get interest history <br>
     * No-any params required
     *
     * @return interest history response as {@link InterestHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public InterestHistoryList getInterestHistory() throws Exception {
        return getInterestHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get interest history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return interest history response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendGetSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, getTimestampParam()),
                format);
    }

    /**
     * Request to get interest history
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return interest history response as {@link InterestHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public InterestHistoryList getInterestHistory(Params extraParams) throws Exception {
        return getInterestHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get interest history
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return interest history response as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(Params extraParams, ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendGetSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Request to get interest history
     *
     * @param asset: asset used to request es BTC
     * @return interest history response as {@link InterestHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public InterestHistoryList getInterestHistory(String asset) throws Exception {
        return getInterestHistory(asset, LIBRARY_OBJECT);
    }

    /**
     * Request to get interest history
     *
     * @param asset:  asset used to request es BTC
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return interest history response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(String asset, ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendGetSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, getTimestampParam()
                + "&asset=" + asset), format);
    }

    /**
     * Request to get interest history
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return interest history response as {@link InterestHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public InterestHistoryList getInterestHistory(String asset, Params extraParams) throws Exception {
        return getInterestHistory(asset, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get interest history
     *
     * @param asset:       asset used to request es BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "txId"} -> the tranId in POST /sapi/v1/margin/loan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return interest history response as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     * Get Interest History (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendGetSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams)),
                format);
    }

    /**
     * Method to create an interests list
     *
     * @param interestHistoryListResponse: obtained from Binance's response
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @return interests list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnInterestHistoryList(String interestHistoryListResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(interestHistoryListResponse);
            case LIBRARY_OBJECT:
                return (T) new InterestHistoryList(new JSONObject(interestHistoryListResponse));
            default:
                return (T) interestHistoryListResponse;
        }
    }

    /**
     * Request to get margin force liquidation <br>
     * No-any params required
     *
     * @return margin force liquidation response as {@link ForceLiquidationList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     * Get Force Liquidation Record (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/forceLiquidationRec")
    public ForceLiquidationList getForceLiquidation() throws Exception {
        return getForceLiquidation(LIBRARY_OBJECT);
    }

    /**
     * Request to get margin force liquidation
     * * @param format:            return type formatter -> {@link ReturnFormat}
     *
     * @return margin force liquidation response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     * Get Force Liquidation Record (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/forceLiquidationRec")
    public <T> T getForceLiquidation(ReturnFormat format) throws Exception {
        return returnForceLiquidationList(sendGetSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT, getTimestampParam()),
                format);
    }

    /**
     * Request to get margin force liquidation
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return margin force liquidation response as {@link ForceLiquidationList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     * Get Force Liquidation Record (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/forceLiquidationRec")
    public ForceLiquidationList getForceLiquidation(Params extraParams) throws Exception {
        return getForceLiquidation(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin force liquidation
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "isolatedSymbol"} -> isolated symbol - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return margin force liquidation response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     * Get Force Liquidation Record (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/forceLiquidationRec")
    public <T> T getForceLiquidation(Params extraParams, ReturnFormat format) throws Exception {
        return returnForceLiquidationList(sendGetSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Method to create a force liquidation list
     *
     * @param forceLiquidationListResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return force liquidation list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnForceLiquidationList(String forceLiquidationListResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(forceLiquidationListResponse);
            case LIBRARY_OBJECT:
                return (T) new ForceLiquidationList(new JSONObject(forceLiquidationListResponse));
            default:
                return (T) forceLiquidationListResponse;
        }
    }

    /** Request to get cross margin account details <br>
     * No-any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     Query Cross Margin Account Details (USER_DATA)</a>
     * @return cross margin account details response as {@link CrossMarginAccountDetails} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/account")
    public CrossMarginAccountDetails getCrossMarginAccountDetails() throws Exception {
        return getCrossMarginAccountDetails(LIBRARY_OBJECT);
    }

    /** Request to get cross margin account details
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     Query Cross Margin Account Details (USER_DATA)</a>
     * @return cross margin account details response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/account")
    public <T> T getCrossMarginAccountDetails(ReturnFormat format) throws Exception {
        return returnCrossMarginAccountDetails(sendGetSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,
                getTimestampParam()), format);
    }

    /**
     * Request to get cross margin account details
     *
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return cross margin account details response as {@link CrossMarginAccountDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     * Query Cross Margin Account Details (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/account")
    public CrossMarginAccountDetails getCrossMarginAccountDetails(long recvWindow) throws Exception {
        return getCrossMarginAccountDetails(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin account details
     *
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return cross margin account details response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     * Query Cross Margin Account Details (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/account")
    public <T> T getCrossMarginAccountDetails(long recvWindow, ReturnFormat format) throws Exception {
        return returnCrossMarginAccountDetails(sendGetSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,
                getTimestampParam() + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create a cross margin account object
     *
     * @param crossAccountResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return cross margin account object as {@code "format"} defines
     **/
    @Returner
    private <T> T returnCrossMarginAccountDetails(String crossAccountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(crossAccountResponse);
            case LIBRARY_OBJECT:
                return (T) new CrossMarginAccountDetails(new JSONObject(crossAccountResponse));
            default:
                return (T) crossAccountResponse;
        }
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *    Query Margin Account's Order (USER_DATA)</a>
     * @return margin status order response as {@link MarginOrderStatus} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/order")
    public MarginOrderStatus getMarginOrderStatus(String symbol) throws Exception {
        return getMarginOrderStatus(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin status order
     *
     * @param symbol: used in the order es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return margin status order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     * Query Margin Account's Order (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/order")
    public <T> T getMarginOrderStatus(String symbol, ReturnFormat format) throws Exception {
        return returnMarginOrderStatus(sendGetSignedRequest(MARGIN_ORDER_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol), format);
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                           {@code "orderId"} -> order identifier - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *    Query Margin Account's Order (USER_DATA)</a>
     * @return margin status order response as {@link MarginOrderStatus} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/order")
    public MarginOrderStatus getMarginOrderStatus(String symbol, Params extraParams) throws Exception {
        return getMarginOrderStatus(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin status order
     *
     * @param symbol:      used in the order es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> order identifier - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return margin status order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     * Query Margin Account's Order (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/order")
    public <T> T getMarginOrderStatus(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatus(sendGetSignedRequest(MARGIN_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol, extraParams)), format);
    }

    /**
     * Method to create a margin order status object
     *
     * @param marginOrderStatusResponse: obtained from Binance's response
     * @param format:                    return type formatter -> {@link ReturnFormat}
     * @return margin order status object as {@code "format"} defines
     **/
    @Returner
    private <T> T returnMarginOrderStatus(String marginOrderStatusResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(marginOrderStatusResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginOrderStatus(new JSONObject(marginOrderStatusResponse));
            default:
                return (T) marginOrderStatusResponse;
        }
    }

    /** Request to get all margin open orders <br>
     * No-any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@link ArrayList} of {@link MarginOrderStatus}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public ArrayList<MarginOrderStatus> getAllOpenOrders() throws Exception {
        return getAllOpenOrders(LIBRARY_OBJECT);
    }

    /** Request to get all margin open orders 
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(ReturnFormat format) throws Exception {
        return returnAllOrders(sendGetSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam()), format);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@link ArrayList} of {@link MarginOrderStatus}
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public ArrayList<MarginOrderStatus> getAllOpenOrders(String symbol) throws Exception {
        return getAllOpenOrders(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get all margin open orders on a symbol
     *
     * @param #symbol: used in the order es. BTCBUSD
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return all margin open orders as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     * Query Margin Account's Open Orders (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(String symbol, ReturnFormat format) throws Exception {
        return returnAllOrders(sendGetSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol), format);
    }

    /** Request to get all margin open orders
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "symbol"} -> symbol value - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at:
     * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@link ArrayList} of {@link MarginOrderStatus}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public ArrayList<MarginOrderStatus> getAllOpenOrders(Params extraParams) throws Exception {
        return getAllOpenOrders(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all margin open orders
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "symbol"} -> symbol value - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at:
     * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(Params extraParams, ReturnFormat format) throws Exception {
        return returnAllOrders(sendGetSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam(), extraParams)), format);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@link ArrayList} of {@link MarginOrderStatus}
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public ArrayList<MarginOrderStatus> getAllOpenOrders(String symbol, long recvWindow) throws Exception {
        return getAllOpenOrders(symbol, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     Query Margin Account's Open Orders (USER_DATA)</a>
     * @return all margin open orders as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnAllOrders(sendGetSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol + "&recvWindow=" + recvWindow), format);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     Query Margin Account's All Orders (USER_DATA)</a>
     * @return all margin orders as {@link ArrayList} of {@link MarginOrderStatus}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrders")
    public ArrayList<MarginOrderStatus> getAllOrders(String symbol) throws Exception {
        return getAllOrders(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get all margin orders on a symbol
     *
     * @param #symbol: used in the order es. BTCBUSD
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return all margin orders as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     * Query Margin Account's All Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrders")
    public <T> T getAllOrders(String symbol, ReturnFormat format) throws Exception {
        return returnAllOrders(sendGetSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol), format);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul> 
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                 {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     Query Margin Account's All Orders (USER_DATA)</a>
     * @return all margin orders as {@link ArrayList} of {@link MarginOrderStatus}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrders")
    public ArrayList<MarginOrderStatus> getAllOrders(String symbol, Params extraParams) throws Exception {
        return getAllOrders(symbol, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul> 
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                 {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     Query Margin Account's All Orders (USER_DATA)</a>
     * @return all margin orders as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrders")
    public <T> T getAllOrders(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnAllOrders(sendGetSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol, extraParams)), format);
    }

    /**
     * Method to create an all open margin orders list
     *
     * @param ordersResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return all open margin orders as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAllOrders(String ordersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(ordersResponse);
            case LIBRARY_OBJECT:
                ArrayList<MarginOrderStatus> marginOrderStatus = new ArrayList<>();
                JSONArray jOrders = new JSONArray(ordersResponse);
                for (int j = 0; j < jOrders.length(); j++)
                    marginOrderStatus.add(new MarginOrderStatus(jOrders.getJSONObject(j)));
                return (T) marginOrderStatus;
            default:
                return (T) ordersResponse;
        }
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     Margin Account Cancel OCO (TRADE)</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} custom object
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public ComposedMarginOrderDetails cancelOCOMarginOrder(String symbol, long orderListId) throws Exception {
        return cancelOCOMarginOrder(symbol, orderListId, LIBRARY_OBJECT);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     Margin Account Cancel OCO (TRADE)</a>
     * @return cancel OCO margin order response as {@code "format"} defines
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
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendDeleteSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&orderListId=" + orderListId), format);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     Margin Account Cancel OCO (TRADE)</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} custom object
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public ComposedMarginOrderDetails cancelOCOMarginOrder(String symbol, String listClientOrderId) throws Exception {
        return cancelOCOMarginOrder(symbol, listClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel OCO margin order
     *
     * @param #symbol:            used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return cancel OCO margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     * Margin Account Cancel OCO (TRADE)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, String listClientOrderId, ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendDeleteSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId), format);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> used to uniquely identify this cancel.
     *                           Automatically generated by default - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     Margin Account Cancel OCO (TRADE)</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} custom object
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public ComposedMarginOrderDetails cancelOCOMarginOrder(String symbol, long orderListId,
                                                           Params extraParams) throws Exception {
        return cancelOCOMarginOrder(symbol, orderListId, extraParams, LIBRARY_OBJECT);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> used to uniquely identify this cancel.
     *                           Automatically generated by default - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     Margin Account Cancel OCO (TRADE)</a>
     * @return cancel OCO margin order response as {@code "format"} defines
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
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, long orderListId, Params extraParams,
                                      ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendDeleteSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&orderListId=" + orderListId, extraParams)), format);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> used to uniquely identify this cancel.
     *                           Automatically generated by default - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     Margin Account Cancel OCO (TRADE)</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} custom object
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public ComposedMarginOrderDetails cancelOCOMarginOrder(String symbol, String listClientOrderId,
                                                           Params extraParams) throws Exception {
        return cancelOCOMarginOrder(symbol, listClientOrderId, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel OCO margin order
     *
     * @param #symbol:            used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams:        additional params of the request, keys accepted are:
     *                            <ul>
     *                                <li>
     *                                    {@code "listClientOrderId"} -> a unique Id for the entire orderList- [STRING]
     *                                </li>
     *                                <li>
     *                                    {@code "newClientOrderId"} -> used to uniquely identify this cancel.
     *                                    Automatically generated by default - [STRING]
     *                                </li>
     *                                <li>
     *                                    {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                                </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                </li>
     *                            </ul>
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return cancel OCO margin order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     * Margin Account Cancel OCO (TRADE)</a>
     **/
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, String listClientOrderId, Params extraParams,
                                      ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendDeleteSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&listClientOrderId=" + listClientOrderId, extraParams)), format);
    }

    /**
     * Method to create a composed margin order details object
     *
     * @param composedOrderResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return composed margin order details object as {@code "format"} defines
     **/
    @Returner
    private <T> T returnComposedMarginOrderDetails(String composedOrderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(composedOrderResponse);
            case LIBRARY_OBJECT:
                return (T) new ComposedMarginOrderDetails(new JSONObject(composedOrderResponse));
            default:
                return (T) composedOrderResponse;
        }
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     Query Margin Account's OCO (USER_DATA)</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public MarginOrderStatusDetails getOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
        return getOCOMarginOrderStatus(symbol, orderListId, LIBRARY_OBJECT);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     Query Margin Account's OCO (USER_DATA)</a>
     * @return OCO margin order status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendGetSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true), format);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     Query Margin Account's OCO (USER_DATA)</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public MarginOrderStatusDetails getOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return getOCOMarginOrderStatus(symbol, origClientOrderId, LIBRARY_OBJECT);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     Query Margin Account's OCO (USER_DATA)</a>
     * @return OCO margin order status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(String symbol, String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendGetSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                        + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId + "&isIsolated=" + true),
                format);
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     Query Margin Account's OCO (USER_DATA)</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} custom object
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/orderList")
    public MarginOrderStatusDetails getOCOMarginOrderStatus(long orderListId) throws Exception {
        return getOCOMarginOrderStatus(orderListId, LIBRARY_OBJECT);
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     Query Margin Account's OCO (USER_DATA)</a>
     * @return OCO margin order status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendGetSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                + "&orderListId=" + orderListId), format);
    }

    /**
     * Request to get OCO margin order status
     *
     * @param #origClientOrderId: identifier od order list es. 1
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     * Query Margin Account's OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public MarginOrderStatusDetails getOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        return getOCOMarginOrderStatus(origClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to get OCO margin order status
     *
     * @param #origClientOrderId: identifier od order list es. 1
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return OCO margin order status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     * Query Margin Account's OCO (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendGetSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                + "&origClientOrderId=" + origClientOrderId), format);
    }

    /**
     * Method to create an order status details object
     *
     * @param orderStatusResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return an order status details object as {@code "format"} defines
     **/
    @Returner
    private <T> T returnMarginOrderStatusDetails(String orderStatusResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderStatusResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginOrderStatusDetails(new JSONObject(orderStatusResponse));
            default:
                return (T) orderStatusResponse;
        }
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, long orderListId) throws Exception {
        return getAllOCOOrders(symbol, orderListId, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                        getTimestampParam() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true),
                format);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, String origClientOrderId) throws Exception {
        return getAllOCOOrders(symbol, origClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #symbol:            used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return all OCO margin orders status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId +
                        "&isIsolated=" + true), format);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #symbol:      used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId:      order from start request es. 124564
     * @param #keyTime:     {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:   value of keyTime es. 152221
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, long orderListId, long fromId,
                                                               String keyTime, long valueTime) throws Exception {
        return getAllOCOOrders(symbol, orderListId, fromId, keyTime, valueTime, LIBRARY_OBJECT);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #symbol:      used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId:      order from start request es. 124564
     * @param #keyTime:     {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:   value of keyTime es. 152221
     * @param format:       return type formatter -> {@link ReturnFormat}
     * @return all OCO margin orders status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true + "&" + keyTime + "=" +
                valueTime + "&fromId=" + fromId), format);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #symbol:            used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId:            order from start request es. 124564
     * @param #keyTime:           {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:         value of keyTime es. 152221
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, String origClientOrderId, long fromId,
                                                               String keyTime, long valueTime) throws Exception {
        return getAllOCOOrders(symbol, origClientOrderId, fromId, keyTime, valueTime, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"}
     * @param #valueTime: value of keyTime es. 152221
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                 long valueTime, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId + "&isIsolated=" + true + "&" + keyTime
                + "=" + valueTime + "&fromId=" + fromId), format);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(long orderListId) throws Exception {
        return getAllOCOOrders(orderListId, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&orderListId=" + orderListId), format);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String origClientOrderId) throws Exception {
        return getAllOCOOrders(origClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #origClientOrderId: identifier od order list es. 1
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return all OCO margin orders status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&origClientOrderId=" + origClientOrderId), format);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId:      order from start request es. 124564
     * @param #keyTime:     {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:   value of keyTime es. 152221
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(long orderListId, long fromId, String keyTime,
                                                               long valueTime) throws Exception {
        return getAllOCOOrders(orderListId, fromId, keyTime, valueTime, LIBRARY_OBJECT);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId:      order from start request es. 124564
     * @param #keyTime:     {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:   value of keyTime es. 152221
     * @param format:       return type formatter -> {@link ReturnFormat}
     * @return all OCO margin orders status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, long fromId, String keyTime, long valueTime,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&orderListId=" + orderListId + "&" + keyTime + "=" + valueTime +
                        "&fromId=" + fromId), format);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId:            order from start request es. 124564
     * @param #keyTime:           {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:         value of keyTime es. 152221
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String origClientOrderId, long fromId, String keyTime,
                                                               long valueTime) throws Exception {
        return getAllOCOOrders(origClientOrderId, fromId, keyTime, valueTime, LIBRARY_OBJECT);
    }

    /**
     * Request to get all OCO margin orders status
     *
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId:            order from start request es. 124564
     * @param #keyTime:           {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime:         value of keyTime es. 152221
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return all OCO margin orders status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     * Query Margin Account's all OCO (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, long fromId, String keyTime, long valueTime,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&origClientOrderId=" + origClientOrderId + "&" + keyTime + "=" + valueTime
                        + "&fromId=" + fromId), format);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, long orderListId,
                                                               Params extraParams) throws Exception {
        return getAllOCOOrders(symbol, orderListId, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&orderListId=" + orderListId, extraParams)), LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, String origClientOrderId,
                                                               Params extraParams) throws Exception {
        return getAllOCOOrders(symbol, origClientOrderId, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&origClientOrderId=" + origClientOrderId, extraParams)), format);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, long orderListId, long fromId,
                                                               String keyTime, long valueTime,
                                                               Params extraParams) throws Exception {
        return getAllOCOOrders(symbol, orderListId, fromId, keyTime, valueTime, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                 Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol
                                + "&orderListId=" + orderListId + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId,
                        extraParams)), format);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String symbol, String origClientOrderId,
                                                               long fromId, String keyTime, long valueTime,
                                                               Params extraParams) throws Exception {
        return getAllOCOOrders(symbol, origClientOrderId, fromId, keyTime, valueTime, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, long fromId, String keyTime, long valueTime,
                                 Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&origClientOrderId=" + origClientOrderId + "&" + keyTime + "=" + valueTime +
                        "&fromId=" + fromId, extraParams)), format);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(long orderListId, Params extraParams) throws Exception {
        return getAllOCOOrders(orderListId, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&orderListId=" + orderListId,
                        extraParams)), format);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String origClientOrderId, Params extraParams) throws Exception {
        return getAllOCOOrders(origClientOrderId, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&origClientOrderId=" +
                        origClientOrderId, extraParams)), format);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(long orderListId, long fromId, String keyTime,
                                                               long valueTime, Params extraParams) throws Exception {
        return getAllOCOOrders(orderListId, fromId, keyTime, valueTime, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, long fromId, String keyTime, long valueTime, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&orderListId=" + orderListId +
                        "&" + keyTime + "=" + valueTime + "&fromId=" + fromId, extraParams)), format);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOrders(String origClientOrderId, long fromId, String keyTime,
                                                               long valueTime, Params extraParams) throws Exception {
        return getAllOCOOrders(origClientOrderId, fromId, keyTime, valueTime, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: {@code "startTime"} or {@code "endTime"} key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *    Query Margin Account's all OCO (USER_DATA)</a>
     * @return all OCO margin orders status response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, long fromId, String keyTime, long valueTime, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&origClientOrderId=" +
                        origClientOrderId + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId, extraParams)), format);
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     Query Margin Account's Open OCO (USER_DATA)</a>
     * @return all open OCO margin orders response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOpenOrders(String symbol) throws Exception {
        return getAllOCOOpenOrders(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get all open OCO margin orders
     *
     * @param #symbol: used in the order es. BTCBUSD
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return all open OCO margin orders response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     * Query Margin Account's Open OCO (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(String symbol, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&isIsolated=" + true), format);
    }

    /**
     * Request to get all open OCO margin orders <br>
     * No-any params required
     *
     * @return all open OCO margin orders response as {@link ArrayList} of {@link MarginOrderStatusDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     * Query Margin Account's Open OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOpenOrders() throws Exception {
        return getAllOCOOpenOrders(LIBRARY_OBJECT);
    }

    /** Request to get all open OCO margin orders
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     Query Margin Account's Open OCO (USER_DATA)</a>
     * @return all open OCO margin orders response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                getTimestampParam()), format);
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     Query Margin Account's Open OCO (USER_DATA)</a>
     * @return all open OCO margin orders response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOpenOrders(String symbol, long recvWindow) throws Exception {
        return getAllOCOOpenOrders(symbol, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get all open OCO margin orders
     *
     * @param #symbol:     used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return all open OCO margin orders response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     * Query Margin Account's Open OCO (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&isIsolated=" + true), format);
    }

    /** Request to get all open OCO margin orders
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     Query Margin Account's all OCO (USER_DATA)</a>
     * @return all open OCO margin orders response as {@link ArrayList} of {@link MarginOrderStatusDetails}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public ArrayList<MarginOrderStatusDetails> getAllOCOOpenOrders(Params extraParams) throws Exception {
        return getAllOCOOpenOrders(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all open OCO margin orders
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                           {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate orders records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> if supplied, neither startTime or endTime can be provided - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size value, max 1000 - [INTEGER, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     Query Margin Account's all OCO (USER_DATA)</a>
     * @return all open OCO margin orders response as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendGetSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Method to create an order status details list
     *
     * @param orderStatusResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return an order status details list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnMarginOrderStatusDetailsList(String orderStatusResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(orderStatusResponse);
            case LIBRARY_OBJECT:
                ArrayList<MarginOrderStatusDetails> marginOrderStatusDetails = new ArrayList<>();
                JSONArray jList = new JSONArray(orderStatusResponse);
                for (int j = 0; j < jList.length(); j++)
                    marginOrderStatusDetails.add(new MarginOrderStatusDetails(jList.getJSONObject(j)));
                return (T) marginOrderStatusDetails;
            default:
                return (T) orderStatusResponse;
        }
    }

    /**
     * Request to get margin trade list
     *
     * @param #symbol: used in the request es. BTCBUSD
     * @return margin trade list as {@link ArrayList} of {@link MarginAccountTrade}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     * Query Margin Account's Trade List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/myTrades")
    public ArrayList<MarginAccountTrade> getTradesList(String symbol) throws Exception {
        return getTradesList(symbol, LIBRARY_OBJECT);
    }

    /** Request to get margin trade list
     * @param #symbol: used in the request es. BTCBUSD
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     *    Query Margin Account's Trade List (USER_DATA)</a>
     * @return margin trade list as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/myTrades")
    public <T> T getTradesList(String symbol, ReturnFormat format) throws Exception {
        return returnTradesList(sendGetSignedRequest(MARGIN_TRADES_LIST_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol), format);
    }

    /**
     * Request to get margin trade list
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                 {@code "fromId"} -> tradeId to fetch from - [STRING, default gets most recent trades]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return margin trade list as {@link ArrayList} of {@link MarginAccountTrade}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     * Query Margin Account's Trade List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/myTrades")
    public ArrayList<MarginAccountTrade> getTradesList(String symbol, Params extraParams) throws Exception {
        return getTradesList(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin trade list
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                 {@code "fromId"} -> tradeId to fetch from - [STRING, default gets most recent trades]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return margin trade list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     * Query Margin Account's Trade List (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/myTrades")
    public <T> T getTradesList(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnTradesList(sendGetSignedRequest(MARGIN_TRADES_LIST_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams)),
                format);
    }

    /**
     * Method to create a trades list
     *
     * @param tradesListResponse: obtained from Binance's response
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return a trades list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTradesList(String tradesListResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(tradesListResponse);
            case LIBRARY_OBJECT:
                ArrayList<MarginAccountTrade> marginAccountTrades = new ArrayList<>();
                JSONArray jTrades = new JSONArray(tradesListResponse);
                for (int j = 0; j < jTrades.length(); j++)
                    marginAccountTrades.add(new MarginAccountTrade(jTrades.getJSONObject(j)));
                return (T) marginAccountTrades;
            default:
                return (T) tradesListResponse;
        }
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     Query Max Borrow (USER_DATA)</a>
     * @return max borrow as {@link MarginMaxBorrow} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxBorrowable")
    public MarginMaxBorrow getMaxBorrow(String asset) throws Exception {
        return getMaxBorrow(asset, LIBRARY_OBJECT);
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     Query Max Borrow (USER_DATA)</a>
     * @return max borrow as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxBorrowable")
    public <T> T getMaxBorrow(String asset, ReturnFormat format) throws Exception {
        return returnMaxBorrow(sendGetSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT, getTimestampParam() +
                "&asset=" + asset), format);
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     Query Max Borrow (USER_DATA)</a>
     * @return max borrow as {@link MarginMaxBorrow} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxBorrowable")
    public MarginMaxBorrow getMaxBorrow(String asset, Params extraParams) throws Exception {
        return getMaxBorrow(asset, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     Query Max Borrow (USER_DATA)</a>
     * @return max borrow as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxBorrowable")
    public <T> T getMaxBorrow(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnMaxBorrow(sendGetSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams)),
                format);
    }

    /**
     * Method to create a max borrow object
     *
     * @param maxBorrowResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return a max borrow as {@code "format"} defines
     **/
    @Returner
    private <T> T returnMaxBorrow(String maxBorrowResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(maxBorrowResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginMaxBorrow(new JSONObject(maxBorrowResponse));
            default:
                return (T) maxBorrowResponse;
        }
    }

    /**
     * Request to get max transfer out amount
     *
     * @param #asset:   used in the request es. BTC
     * @param decimals: number of digits to round final value
     * @return max transfer out amount as double
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     * Query Max Transfer-Out Amount (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxTransferable")
    public double getMaxTransferOutAmount(String asset, int decimals) throws Exception {
        return roundValue(Double.parseDouble(getMaxTransferOutAmount(asset, LIBRARY_OBJECT)), decimals);
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *    Query Max Transfer-Out Amount (USER_DATA)</a>
     * @return max transfer out amount as double
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxTransferable")
    public double getMaxTransferOutAmount(String asset) throws Exception {
        return Double.parseDouble(getMaxTransferOutAmount(asset, LIBRARY_OBJECT));
    }

    /**
     * Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *    Query Max Transfer-Out Amount (USER_DATA)</a>
     * @return max transfer out amount as double
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxTransferable")
    public <T> T getMaxTransferOutAmount(String asset, ReturnFormat format) throws Exception {
        return returnMaxTransferAmount(sendGetSignedRequest(GET_MAX_MARGIN_TRANSFER_ENDPOINT, getTimestampParam()
                + "&asset=" + asset), format);
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param decimals: number of digits to round final value
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *    Query Max Transfer-Out Amount (USER_DATA)</a>
     * @return max transfer out amount as double
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxTransferable")
    public double getMaxTransferOutAmount(String asset, Params extraParams, int decimals) throws Exception {
        return roundValue(Double.parseDouble(getMaxTransferOutAmount(asset, extraParams, LIBRARY_OBJECT)), decimals);
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *    Query Max Transfer-Out Amount (USER_DATA)</a>
     * @return max transfer out amount as double
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxTransferable")
    public double getMaxTransferOutAmount(String asset, Params extraParams) throws Exception {
        return Double.parseDouble(getMaxTransferOutAmount(asset, extraParams, LIBRARY_OBJECT));
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                                {@code "isIsolated"} -> for isolated margin or not, "TRUE", "FALSE" - [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *    Query Max Transfer-Out Amount (USER_DATA)</a>
     * @return max transfer out amount as double
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/maxTransferable")
    public <T> T getMaxTransferOutAmount(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnMaxTransferAmount(sendGetSignedRequest(GET_MAX_MARGIN_TRANSFER_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams)),
                format);
    }

    /**
     * Method to get a max transfer amount
     *
     * @param maxTransferAmountResponse: obtained from Binance's response
     * @param format:                    return type formatter -> {@link ReturnFormat}
     * @return max transfer amount as {@code "format"} defines
     * @implSpec in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the amount value as double
     **/
    @Returner
    private <T> T returnMaxTransferAmount(String maxTransferAmountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(maxTransferAmountResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(maxTransferAmountResponse).getString("amount");
            default:
                return (T) maxTransferAmountResponse;
        }
    }

    /**
     * Request to get personal margin level information <br>
     * No-any params required
     *
     * @return margin level information as {@link MarginSummary} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-margin-account-user_data">
     * Get Summary of Margin account (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/tradeCoeff")
    public MarginSummary getMarginAccountSummary() throws Exception {
        return getMarginAccountSummary(LIBRARY_OBJECT);
    }

    /**
     * Request to get personal margin level information
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return margin level information as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-margin-account-user_data">
     * Get Summary of Margin account (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/tradeCoeff")
    public <T> T getMarginAccountSummary(ReturnFormat format) throws Exception {
        return returnSummary(sendGetSignedRequest(GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get personal margin level information <br>
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return margin level information as {@link MarginSummary} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-margin-account-user_data">
     * Get Summary of Margin account (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/tradeCoeff")
    public MarginSummary getMarginAccountSummary(long recvWindow) throws Exception {
        return getMarginAccountSummary(LIBRARY_OBJECT);
    }

    /**
     * Request to get personal margin level information <br>
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return margin level information as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-margin-account-user_data">
     * Get Summary of Margin account (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/tradeCoeff")
    public <T> T getMarginAccountSummary(long recvWindow, ReturnFormat format) throws Exception {
        return returnSummary(sendGetSignedRequest(GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT, getTimestampParam()
                + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to get a summary object
     *
     * @param summaryResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return summary as {@code "format"} defines
     **/
    @Returner
    private <T> T returnSummary(String summaryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(summaryResponse);
            case LIBRARY_OBJECT:
                return (T) new MarginSummary(new JSONObject(summaryResponse));
            default:
                return (T) summaryResponse;
        }
    }

    /**
     * Request to get margin isolated transfer
     *
     * @param #asset:     used in the request es. BTC
     * @param #symbol:    used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo:   SPOT or ISOLATED_MARGIN
     * @param #amount:    used in the request to transfer es. 1
     * @return margin isolated transferId as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     * Isolated Margin Account Transfer (MARGIN)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/transfer")
    public long execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                  double amount) throws Exception {
        return parseLong(execIsolatedMarginAccountTransfer(asset, symbol, transFrom, transTo, amount,
                LIBRARY_OBJECT));
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     Isolated Margin Account Transfer (MARGIN)</a>
     * @return margin isolated transferId as {@code "format"} defines
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/transfer")
    public <T> T execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                   double amount, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("symbol", symbol);
        payload.addParam("transFrom", transFrom);
        payload.addParam("transTo", transTo);
        payload.addParam("amount", amount);
        return returnTranId(sendPostSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT, payload), format);
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     Isolated Margin Account Transfer (MARGIN)</a>
     * @return margin isolated transfer as long
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
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/transfer")
    public long execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                  double amount, long recvWindow) throws Exception {
        return execIsolatedMarginAccountTransfer(asset, symbol, transFrom, transTo, amount, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     Isolated Margin Account Transfer (MARGIN)</a>
     * @return margin isolated transfer as {@code "format"} defines
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
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/transfer")
    public <T> T execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                   double amount, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("symbol", symbol);
        payload.addParam("transFrom", transFrom);
        payload.addParam("transTo", transTo);
        payload.addParam("amount", amount);
        payload.addParam("recvWindow", recvWindow);
        return returnTranId(sendPostSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT, payload), format);
    }

    /**
     * Request to get margin isolated transfer history
     *
     * @param #symbol: used in the request es. BTCBUSD
     * @return margin isolated transfer history as {@link IsolatedTransferHistoryList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     * Get Isolated Margin Transfer History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/transfer")
    public IsolatedTransferHistoryList getIsolatedTransferHistory(String symbol) throws Exception {
        return getIsolatedTransferHistory(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin isolated transfer history
     *
     * @param #symbol: used in the request es. BTCBUSD
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return margin isolated transfer history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     * Get Isolated Margin Transfer History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/transfer")
    public <T> T getIsolatedTransferHistory(String symbol, ReturnFormat format) throws Exception {
        return returnIsolatedTransferHistoryList(sendGetSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol), format);
    }

    /**
     * Request to get margin isolated transfer history
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transFrom"} -> "SPOT", "ISOLATED_MARGIN" constants available {@link TransferType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transFrom"} -> "SPOT", "ISOLATED_MARGIN" constants available {@link TransferType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfer records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfer records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago- [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return margin isolated transfer history as {@link IsolatedTransferHistoryList} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     * Get Isolated Margin Transfer History (USER_DATA)</a>
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/transfer")
    public IsolatedTransferHistoryList getIsolatedTransferHistory(String symbol, Params extraParams) throws Exception {
        return getIsolatedTransferHistory(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get margin isolated transfer history
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transFrom"} -> "SPOT", "ISOLATED_MARGIN" constants available {@link TransferType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "transFrom"} -> "SPOT", "ISOLATED_MARGIN" constants available {@link TransferType} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate transfer records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate transfer records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "archived"} -> set to true for archived data from 6 months ago- [BOOLEAN, default false]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return margin isolated transfer history as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     * Get Isolated Margin Transfer History (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/transfer")
    public <T> T getIsolatedTransferHistory(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnIsolatedTransferHistoryList(sendGetSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams)),
                format);
    }

    /**
     * Method to create an isolated transfer history id
     *
     * @param isolatedTransferHistoryResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return isolated transfer history as {@code "format"} defines
     **/
    @Returner
    private <T> T returnIsolatedTransferHistoryList(String isolatedTransferHistoryResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(isolatedTransferHistoryResponse);
            case LIBRARY_OBJECT:
                return (T) new IsolatedTransferHistoryList(new JSONObject(isolatedTransferHistoryResponse));
            default:
                return (T) isolatedTransferHistoryResponse;
        }
    }

    /** Request to get margin isolated account info <br>
     * No-any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     Query Isolated Margin Account Info (USER_DATA)</a>
     * @return margin account info as {@link ComposedIMarginAccountInfo} object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/account")
    public ComposedIMarginAccountInfo getMarginIsolatedAccount() throws Exception {
        return getMarginIsolatedAccount(LIBRARY_OBJECT);
    }

    /**
     * Request to get margin isolated account info
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return margin account info as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     * Query Isolated Margin Account Info (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/account")
    public <T> T getMarginIsolatedAccount(ReturnFormat format) throws Exception {
        return returnComposedAccountInfo(sendGetSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, getTimestampParam()),
                format);
    }

    /** Request to get margin isolated account info
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "symbols"} -> max 5 symbols can be sent; separated by ",". e.g. "BTCUSDT,BNBUSDT,ADAUSDT"
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     Query Isolated Margin Account Info (USER_DATA)</a>
     * @return margin account info as {@link ComposedIMarginAccountInfo} object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/account")
    public ComposedIMarginAccountInfo getMarginIsolatedAccount(Params extraParams) throws Exception {
        return getMarginIsolatedAccount(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get margin isolated account info
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "symbols"} -> max 5 symbols can be sent; separated by ",". e.g. "BTCUSDT,BNBUSDT,ADAUSDT"
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     Query Isolated Margin Account Info (USER_DATA)</a>
     * @return margin account info as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/account")
    public <T> T getMarginIsolatedAccount(Params extraParams, ReturnFormat format) throws Exception {
        return returnComposedAccountInfo(sendGetSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Method to create a composed account object
     *
     * @param composedAccountResponse: obtained from Binance's response
     * @param format:                  return type formatter -> {@link ReturnFormat}
     * @return composed account object as {@code "format"} defines
     **/
    @Returner
    private <T> T returnComposedAccountInfo(String composedAccountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(composedAccountResponse);
            case LIBRARY_OBJECT:
                return (T) new ComposedIMarginAccountInfo(new JSONObject(composedAccountResponse));
            default:
                return (T) composedAccountResponse;
        }
    }

    /**
     * Request to disable isolated margin account on a symbol
     *
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @return margin account status as {@link IsolatedMarginAccountStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     * Disable Isolated Margin Account (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/isolated/account")
    public IsolatedMarginAccountStatus disableIsolatedMarginAccount(String symbol) throws Exception {
        return changeMarginAccountStatus(symbol, false, -1, LIBRARY_OBJECT);
    }

    /** Request to disable isolated margin account on a symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     Disable Isolated Margin Account (TRADE)</a>
     * @return margin account status as {@code "format"} defines
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
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/isolated/account")
    public <T> T disableIsolatedMarginAccount(String symbol, ReturnFormat format) throws Exception {
        return changeMarginAccountStatus(symbol, false, -1, format);
    }

    /** Request to disable isolated margin account on a symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     Disable Isolated Margin Account (TRADE)</a>
     * @return margin account status as {@link IsolatedMarginAccountStatus} custom object
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
    @Wrapper
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/isolated/account")
    public IsolatedMarginAccountStatus disableIsolatedMarginAccount(String symbol, long recvWindow) throws Exception {
        return changeMarginAccountStatus(symbol, false, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to disable isolated margin account on a symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     Disable Isolated Margin Account (TRADE)</a>
     * @return margin account status as {@code "format"} defines
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
    @RequestPath(method = DELETE, path = "/sapi/v1/margin/isolated/account")
    public <T> T disableIsolatedMarginAccount(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return changeMarginAccountStatus(symbol, false, recvWindow, format);
    }

    /**
     * Request to enable isolated margin account on a symbol
     *
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @return margin account status as {@link IsolatedMarginAccountStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">
     * Enable Isolated Margin Account (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/account")
    public IsolatedMarginAccountStatus enableIsolatedMarginAccount(String symbol) throws Exception {
        return changeMarginAccountStatus(symbol, true, -1, LIBRARY_OBJECT);
    }

    /**
     * Request to enable isolated margin account on a symbol
     *
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return margin account status as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">
     * Enable Isolated Margin Account (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/account")
    public <T> T enableIsolatedMarginAccount(String symbol, ReturnFormat format) throws Exception {
        return changeMarginAccountStatus(symbol, true, -1, format);
    }

    /**
     * Request to enable isolated margin account on a symbol
     *
     * @param #symbol:     symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return margin account status as {@link IsolatedMarginAccountStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">
     * Enable Isolated Margin Account (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/account")
    public IsolatedMarginAccountStatus enableIsolatedMarginAccount(String symbol, long recvWindow) throws Exception {
        return changeMarginAccountStatus(symbol, true, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to enable isolated margin account on a symbol
     *
     * @param #symbol:     symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return margin account status as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-isolated-margin-account-trade">
     * Enable Isolated Margin Account (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/isolated/account")
    public <T> T enableIsolatedMarginAccount(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return changeMarginAccountStatus(symbol, true, recvWindow, format);
    }

    /**
     * Method to change margin account status
     *
     * @param #symbol:         symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @param #recvWindow:     time to keep alive request, then rejected. Max value is 60000
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return change margin account status as {@code "format"} defines
     **/
    @Returner
    private <T> T changeMarginAccountStatus(String symbol, boolean enableIsolated, long recvWindow,
                                            ReturnFormat format) throws Exception {
        RequestMethod method = DELETE;
        Params params = new Params();
        params.addParam("symbol", symbol);
        if (enableIsolated)
            method = POST;
        if (recvWindow != -1)
            params.addParam("recvWindow", recvWindow);
        String marginResponse;
        if (method.equals(POST))
            marginResponse = sendPostSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params);
        else
            marginResponse = sendDeleteSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params);
        switch (format) {
            case JSON:
                return (T) new JSONObject(marginResponse);
            case LIBRARY_OBJECT:
                return (T) new IsolatedMarginAccountStatus(new JSONObject(marginResponse));
            default:
                return (T) marginResponse;
        }
    }

    /** Request to get isolate margin account limit <br>
     * No-any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     Query Enabled Isolated Margin Account Limit (USER_DATA)</a>
     * @return isolate margin account limit as {@link IsolatedMarginAccountLimit} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/accountLimit")
    public IsolatedMarginAccountLimit getIsolateMarginAccountLimit() throws Exception {
        return getIsolateMarginAccountLimit(LIBRARY_OBJECT);
    }

    /**
     * Request to get isolate margin account limit
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return isolate margin account limit as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     * Query Enabled Isolated Margin Account Limit (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/accountLimit")
    public <T> T getIsolateMarginAccountLimit(ReturnFormat format) throws Exception {
        return returnIsolatedMarginAccountLimit(sendGetSignedRequest(ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT,
                getTimestampParam()), format);
    }

    /** Request to get isolate margin account limit
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     Query Enabled Isolated Margin Account Limit (USER_DATA)</a>
     * @return isolate margin account limit as {@link IsolatedMarginAccountLimit} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/accountLimit")
    public IsolatedMarginAccountLimit getIsolateMarginAccountLimit(long recvWindow) throws Exception {
        return getIsolateMarginAccountLimit(recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get isolate margin account limit
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     Query Enabled Isolated Margin Account Limit (USER_DATA)</a>
     * @return isolate margin account limit as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/accountLimit")
    public <T> T getIsolateMarginAccountLimit(long recvWindow, ReturnFormat format) throws Exception {
        return returnIsolatedMarginAccountLimit(sendGetSignedRequest(ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT,
                getTimestampParam() + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create an isolated margin account limit
     *
     * @param isolatedMarginAccountResponse: obtained from Binance's response
     * @param format:                        return type formatter -> {@link ReturnFormat}
     * @return isolated margin account limit as {@code "format"} defines
     **/
    @Returner
    private <T> T returnIsolatedMarginAccountLimit(String isolatedMarginAccountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(isolatedMarginAccountResponse);
            case LIBRARY_OBJECT:
                return (T) new IsolatedMarginAccountLimit(new JSONObject(isolatedMarginAccountResponse));
            default:
                return (T) isolatedMarginAccountResponse;
        }
    }

    /**
     * Request to get isolate margin symbol
     *
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @return isolate margin symbol as {@link IsolatedMarginSymbol} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     * Query Isolated Margin Symbol (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/pair")
    public IsolatedMarginSymbol getIsolatedMarginSymbol(String symbol) throws Exception {
        return getIsolatedMarginSymbol(symbol, LIBRARY_OBJECT);
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *    Query Isolated Margin Symbol (USER_DATA)</a>
     * @return isolate margin symbol as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/pair")
    public <T> T getIsolatedMarginSymbol(String symbol, ReturnFormat format) throws Exception {
        return returnIsolatedMarginSymbol(sendGetSignedRequest(QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol), format);
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *    Query Isolated Margin Symbol (USER_DATA)</a>
     * @return isolate margin symbol as {@link IsolatedMarginSymbol} custom object
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/pair")
    public IsolatedMarginSymbol getIsolatedMarginSymbol(String symbol, long recvWindow) throws Exception {
        return getIsolatedMarginSymbol(symbol, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *    Query Isolated Margin Symbol (USER_DATA)</a>
     * @return isolate margin symbol as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/pair")
    public <T> T getIsolatedMarginSymbol(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnIsolatedMarginSymbol(sendGetSignedRequest(QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create an isolated margin symbol
     *
     * @param isolatedMarginSymbolResponse: obtained from Binance's response
     * @param format:                       return type formatter -> {@link ReturnFormat}
     * @return isolated margin symbol as {@code "format"} defines
     **/
    @Returner
    private <T> T returnIsolatedMarginSymbol(String isolatedMarginSymbolResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(isolatedMarginSymbolResponse);
            case LIBRARY_OBJECT:
                return (T) new IsolatedMarginSymbol(new JSONObject(isolatedMarginSymbolResponse));
            default:
                return (T) isolatedMarginSymbolResponse;
        }
    }

    /**
     * Request to get all isolate margin symbols <br>
     * No-any params required
     *
     * @return all isolate margin symbols as {@link ArrayList} of {@link IsolatedMarginSymbol}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/allPairs")
    public ArrayList<IsolatedMarginSymbol> getAllIsolatedSymbols() throws Exception {
        return getAllIsolatedSymbols(LIBRARY_OBJECT);
    }

    /**
     * Request to get all isolate margin symbols
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return all isolate margin symbols as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/allPairs")
    public <T> T getAllIsolatedSymbols(ReturnFormat format) throws Exception {
        return returnAllIMarginSymbols(sendGetSignedRequest(QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam()), format);
    }

    /**
     * Request to get all isolate margin symbols
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return all isolate margin symbols as {@link ArrayList} of {@link IsolatedMarginSymbol}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     * Get All Isolated Margin Symbol(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/allPairs")
    public ArrayList<IsolatedMarginSymbol> getAllIsolatedSymbols(long recvWindow) throws Exception {
        return getAllIsolatedSymbols(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get all isolate margin symbols
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return all isolate margin symbols as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     * Get All Isolated Margin Symbol(USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolated/allPairs")
    public <T> T getAllIsolatedSymbols(long recvWindow, ReturnFormat format) throws Exception {
        return returnAllIMarginSymbols(sendGetSignedRequest(QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam() + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create an isolated margin symbols list
     *
     * @param symbolsResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return isolated margin symbols list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAllIMarginSymbols(String symbolsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(symbolsResponse);
            case LIBRARY_OBJECT:
                ArrayList<IsolatedMarginSymbol> isolatedMarginSymbols = new ArrayList<>();
                JSONArray jSymbols = new JSONArray(symbolsResponse);
                for (int j = 0; j < jSymbols.length(); j++)
                    isolatedMarginSymbols.add(new IsolatedMarginSymbol(jSymbols.getJSONObject(j)));
                return (T) isolatedMarginSymbols;
            default:
                return (T) symbolsResponse;
        }
    }

    /**
     * Request to get toggle BNB on trade interest <br>
     * No-any params required
     *
     * @return toggle BNB on trade interest as {@link BNBBurn} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     * Toggle BNB Burn On Spot Trade And Margin Interest (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public BNBBurn toggleBNBOnTradeInterest() throws Exception {
        return toggleBNBOnTradeInterest(LIBRARY_OBJECT);
    }

    /**
     * Request to get toggle BNB on trade interest
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return toggle BNB on trade interest as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     * Toggle BNB Burn On Spot Trade And Margin Interest (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public <T> T toggleBNBOnTradeInterest(ReturnFormat format) throws Exception {
        return returnToggleBNB(sendPostSignedRequest(MARGIN_BNB_ENDPOINT, null), format);
    }

    /**
     * Request to get toggle BNB on trade interest
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "spotBNBBurn"} -> "true" or "false"; Determines whether to use BNB to pay for trading fees on SPOT
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "interestBNBBurn"} -> "true" or "false"; Determines whether to use BNB to pay for margin loan's interest
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return toggle BNB on trade interest as {@link BNBBurn} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     * Toggle BNB Burn On Spot Trade And Margin Interest (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public BNBBurn toggleBNBOnTradeInterest(Params extraParams) throws Exception {
        return toggleBNBOnTradeInterest(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get toggle BNB on trade interest
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "spotBNBBurn"} -> "true" or "false"; Determines whether to use BNB to pay for trading fees on SPOT
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "interestBNBBurn"} -> "true" or "false"; Determines whether to use BNB to pay for margin loan's interest
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     Toggle BNB Burn On Spot Trade And Margin Interest (USER_DATA)</a>
     * @return toggle BNB on trade interest as {@code "format"} defines
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
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public <T> T toggleBNBOnTradeInterest(Params extraParams, ReturnFormat format) throws Exception {
        return returnToggleBNB(sendPostSignedRequest(MARGIN_BNB_ENDPOINT, extraParams), format);
    }

    /**
     * Request to get BNB burn status <br>
     * No-any params required
     *
     * @return BNB burn status as {@link BNBBurn} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     * Get BNB Burn Status (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public BNBBurn getBNBBurnStatus() throws Exception {
        return getBNBBurnStatus(LIBRARY_OBJECT);
    }

    /**
     * Request to get BNB burn status
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return BNB burn status as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     * Get BNB Burn Status (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public <T> T getBNBBurnStatus(ReturnFormat format) throws Exception {
        return returnToggleBNB(sendPostSignedRequest(MARGIN_BNB_ENDPOINT, null), format);
    }

    /** Request to get BNB burn status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     Get BNB Burn Status (USER_DATA)</a>
     * @return BNB burn status as {@link BNBBurn} custom object
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
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public BNBBurn getBNBBurnStatus(long recvWindow) throws Exception {
        return getBNBBurnStatus(recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get BNB burn status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     Get BNB Burn Status (USER_DATA)</a>
     * @return BNB burn status as {@code "format"} defines
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
    @RequestPath(method = POST, path = "/sapi/v1/bnbBurn")
    public <T> T getBNBBurnStatus(long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("recvWindow", recvWindow);
        return returnToggleBNB(sendPostSignedRequest(MARGIN_BNB_ENDPOINT, payload), format);
    }

    /**
     * Method to create a toggle BNB object
     *
     * @param toggleBNBResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return toggle BNB object as {@code "format"} defines
     **/
    @Returner
    private <T> T returnToggleBNB(String toggleBNBResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(toggleBNBResponse);
            case LIBRARY_OBJECT:
                new BNBBurn(new JSONObject(toggleBNBResponse));
            default:
                return (T) toggleBNBResponse;
        }
    }

    /**
     * Request to get margin interest rate history list
     *
     * @param #asset: used in the request es. BTC
     * @return margin interest rate history as {@code ArrayList} of {@link MarginInterestRate}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     * Query Margin Interest Rate History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestRateHistory")
    public ArrayList<MarginInterestRate> getInterestRateHistory(String asset) throws Exception {
        return getInterestRateHistory(asset, LIBRARY_OBJECT);
    }

    /** Request to get margin interest rate history list
     * @param #asset: used in the request es. BTC
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     Query Margin Interest Rate History (USER_DATA)</a>
     * @return margin interest rate history as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestRateHistory")
    public <T> T getInterestRateHistory(String asset, ReturnFormat format) throws Exception {
        return returnRateHistoryList(sendGetSignedRequest(MARGIN_INTEREST_RATE_HISTORY_ENDPOINT, getTimestampParam()
                + "&asset=" + asset), format);
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "vipLevel"} -> vip level value - [INT, default user's vip level]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG, default 7 days ago]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records, maximum range: 1 months - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     Query Margin Interest Rate History (USER_DATA)</a>
     * @return margin interest rate history as {@code ArrayList} of {@link MarginInterestRate}
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
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestRateHistory")
    public ArrayList<MarginInterestRate> getInterestRateHistory(String asset, Params extraParams) throws Exception {
        return getInterestRateHistory(asset, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "vipLevel"} -> vip level value - [INT, default user's vip level]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate interest records - [LONG, default 7 days ago]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate interest records, maximum range: 1 months - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     Query Margin Interest Rate History (USER_DATA)</a>
     * @return margin interest rate history as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/interestRateHistory")
    public <T> T getInterestRateHistory(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnRateHistoryList(sendGetSignedRequest(MARGIN_INTEREST_RATE_HISTORY_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams)),
                format);
    }

    /**
     * Method to create a rate history list
     *
     * @param rateHistoryListResponse: obtained from Binance's response
     * @param format:                  return type formatter -> {@link ReturnFormat}
     * @return rate history list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnRateHistoryList(String rateHistoryListResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(rateHistoryListResponse);
            case LIBRARY_OBJECT:
                ArrayList<MarginInterestRate> marginInterestRates = new ArrayList<>();
                JSONArray jRates = new JSONArray(rateHistoryListResponse);
                for (int j = 0; j < jRates.length(); j++)
                    marginInterestRates.add(new MarginInterestRate(jRates.getJSONObject(j)));
                return (T) marginInterestRates;
            default:
                return (T) rateHistoryListResponse;
        }
    }

    /**
     * Request to get cross margin fee data list <br>
     * No-any params required
     *
     * @return cross margin fee data as {@link ArrayList} of {@link CrossMarginFee}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     * Query Cross Margin Fee Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/crossMarginData")
    public ArrayList<CrossMarginFee> getCrossMarginFeeData() throws Exception {
        return getCrossMarginFeeData(LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin fee data list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross margin fee data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     * Query Cross Margin Fee Data (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/crossMarginData")
    public <T> T getCrossMarginFeeData(ReturnFormat format) throws Exception {
        return returnCrossMarginFee(sendGetSignedRequest(CROSS_MARGIN_DATA_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get cross margin fee data list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "vipLevel"} -> user's current specific margin data will be returned if vipLevel is omitted
     *                                - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "coin"} -> coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross margin fee data as {@link ArrayList} of {@link CrossMarginFee}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     * Query Cross Margin Fee Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/crossMarginData")
    public ArrayList<CrossMarginFee> getCrossMarginFeeData(Params extraParams) throws Exception {
        return getCrossMarginFeeData(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get cross margin fee data list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "vipLevel"} -> user's current specific margin data will be returned if vipLevel is omitted
     *                                - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "coin"} -> coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     Query Cross Margin Fee Data (USER_DATA)</a>
     * @return cross margin fee data as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/crossMarginData")
    public <T> T getCrossMarginFeeData(Params extraParams, ReturnFormat format) throws Exception {
        return returnCrossMarginFee(sendGetSignedRequest(CROSS_MARGIN_DATA_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Method to create a cross margin fees list
     *
     * @param feesResponse: obtained from Binance's response
     * @param format:       return type formatter -> {@link ReturnFormat}
     * @return cross margin fees list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnCrossMarginFee(String feesResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(feesResponse);
            case LIBRARY_OBJECT:
                ArrayList<CrossMarginFee> crossMarginFees = new ArrayList<>();
                JSONArray jFees = new JSONArray(feesResponse);
                for (int j = 0; j < jFees.length(); j++)
                    crossMarginFees.add(new CrossMarginFee(jFees.getJSONObject(j)));
                return (T) crossMarginFees;
            default:
                return (T) feesResponse;
        }
    }

    /**
     * Request to get isolated margin fee data list <br>
     * No-any params required
     *
     * @return isolated margin fee data as {@link ArrayList} of {@link IsolatedMarginFee}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     * Query Isolated Margin Fee Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginData")
    public ArrayList<IsolatedMarginFee> getIsolatedMarginFeeData() throws Exception {
        return getIsolatedMarginFeeData(LIBRARY_OBJECT);
    }

    /**
     * Request to get isolated margin fee data list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return isolated margin fee data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     * Query Isolated Margin Fee Data (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginData")
    public <T> T getIsolatedMarginFeeData(ReturnFormat format) throws Exception {
        return returnIsolatedMarginFee(sendGetSignedRequest(ISOLATED_MARGIN_DATA_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get isolated margin fee data list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "vipLevel"} -> user's current specific margin data will be returned if vipLevel is omitted
     *                                - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> symbol value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return isolated margin fee data as {@link ArrayList} of {@link IsolatedMarginFee}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     * Query Isolated Margin Fee Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginData")
    public ArrayList<IsolatedMarginFee> getIsolatedMarginFeeData(Params extraParams) throws Exception {
        return getIsolatedMarginFeeData(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get isolated margin fee data list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "vipLevel"} -> user's current specific margin data will be returned if vipLevel is omitted
     *                                - [INT]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> symbol value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *    Query Isolated Margin Fee Data (USER_DATA)</a>
     * @return isolated margin fee data as {@code "format"} defines
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginData")
    public <T> T getIsolatedMarginFeeData(Params extraParams, ReturnFormat format) throws Exception {
        return returnIsolatedMarginFee(sendGetSignedRequest(ISOLATED_MARGIN_DATA_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Method to create an isolated margin fees list
     *
     * @param feesResponse: obtained from Binance's response
     * @param format:       return type formatter -> {@link ReturnFormat}
     * @return isolated margin fees list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnIsolatedMarginFee(String feesResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(feesResponse);
            case LIBRARY_OBJECT:
                ArrayList<IsolatedMarginFee> isolatedMarginFees = new ArrayList<>();
                JSONArray jFees = new JSONArray(feesResponse);
                for (int j = 0; j < jFees.length(); j++)
                    isolatedMarginFees.add(new IsolatedMarginFee(jFees.getJSONObject(j)));
                return (T) isolatedMarginFees;
            default:
                return (T) feesResponse;
        }
    }

    /**
     * Request to get isolated margin tier data list
     *
     * @param #symbol: used in the request es. BTCBUSD
     * @return isolated margin tier data as {@link ArrayList} of {@link IsolatedMarginTierData}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     * Query Isolated Margin Tier Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginTier")
    public ArrayList<IsolatedMarginTierData> getIsolatedMarginTierData(String symbol) throws Exception {
        return getIsolatedMarginTierData(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get isolated margin tier data list
     *
     * @param #symbol: used in the request es. BTCBUSD
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return isolated margin tier data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     * Query Isolated Margin Tier Data (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginTier")
    public <T> T getIsolatedMarginTierData(String symbol, ReturnFormat format) throws Exception {
        return returnIsolatedMarginTierData(sendGetSignedRequest(ISOLATED_MARGIN_TIER_DATA_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol), format);
    }

    /**
     * Request to get isolated margin tier data list
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tier"} -> all margin tier data will be returned if tier is omitted - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> symbol value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return isolated margin tier data as {@link ArrayList} of {@link IsolatedMarginTierData}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     * Query Isolated Margin Tier Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginTier")
    public ArrayList<IsolatedMarginTierData> getIsolatedMarginTierData(String symbol, Params extraParams) throws Exception {
        return getIsolatedMarginTierData(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get isolated margin tier data list
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "tier"} -> all margin tier data will be returned if tier is omitted - [INTEGER]
     *                           </li>
     *                           <li>
     *                                {@code "symbol"} -> symbol value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return isolated margin tier data as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     * Query Isolated Margin Tier Data (USER_DATA)</a>
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
    @RequestPath(method = GET, path = "/sapi/v1/margin/isolatedMarginTier")
    public <T> T getIsolatedMarginTierData(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnIsolatedMarginTierData(sendGetSignedRequest(ISOLATED_MARGIN_TIER_DATA_ENDPOINT,
                        apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams)),
                format);
    }

    /**
     * Method to create an isolated margin tier data list
     *
     * @param tierDataResponse: obtained from Binance's response
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return isolated margin tier data list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnIsolatedMarginTierData(String tierDataResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(tierDataResponse);
            case LIBRARY_OBJECT:
                ArrayList<IsolatedMarginTierData> isolatedMarginTierData = new ArrayList<>();
                JSONArray jTiers = new JSONArray(tierDataResponse);
                for (int j = 0; j < jTiers.length(); j++)
                    isolatedMarginTierData.add(new IsolatedMarginTierData(jTiers.getJSONObject(j)));
                return (T) isolatedMarginTierData;
            default:
                return (T) tierDataResponse;
        }
    }

    /**
     * Request to get cross margin collateral ratio <br>
     * No-any params required
     *
     * @return collateral ratio as {@link ArrayList} of {@link CollateralRatio} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-collateral-ratio-market_data">
     * Cross margin collateral ratio (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/crossMarginCollateralRatio")
    public ArrayList<CollateralRatio> getCrossMarginCollateralRatio() throws Exception {
        return getCrossMarginCollateralRatio(LIBRARY_OBJECT);
    }

    /**
     * Request to get cross margin collateral ratio
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return collateral ratio as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-collateral-ratio-market_data">
     * Cross margin collateral ratio (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/crossMarginCollateralRatio")
    public <T> T getCrossMarginCollateralRatio(ReturnFormat format) throws Exception {
        String collateralRatioResponse = sendGetSignedRequest(COLLATERAL_RATIO_ENDPOINT);
        switch (format) {
            case JSON:
                return (T) new JSONArray(collateralRatioResponse);
            case LIBRARY_OBJECT:
                ArrayList<CollateralRatio> collateralRatio = new ArrayList<>();
                JSONArray jRatio = new JSONArray(collateralRatioResponse);
                for (int j = 0; j < jRatio.length(); j++)
                    collateralRatio.add(new CollateralRatio(jRatio.getJSONObject(j)));
                return (T) collateralRatio;
            default:
                return (T) collateralRatioResponse;
        }
    }

    /**
     * Request to get the coins which can be small liability exchange <br>
     * No-any params required
     *
     * @return small liability exchange coins list as {@link ArrayList} of {@link SmallLiabilityExchangeCoin} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-coin-list-user_data">
     * Get Small Liability Exchange Coin List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/exchange-small-liability")
    public ArrayList<SmallLiabilityExchangeCoin> getSmallLiabilityExchangeCoinList() throws Exception {
        return getSmallLiabilityExchangeCoinList(LIBRARY_OBJECT);
    }

    /**
     * Request to get the coins which can be small liability exchange
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return small liability exchange coins list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-coin-list-user_data">
     * Get Small Liability Exchange Coin List (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/exchange-small-liability")
    public <T> T getSmallLiabilityExchangeCoinList(ReturnFormat format) throws Exception {
        String coinListResponse = sendGetSignedRequest(EXCHANGE_SMALL_LIABILITY_ENDPOINT, getTimestampParam());
        switch (format) {
            case JSON:
                return (T) new JSONArray(coinListResponse);
            case LIBRARY_OBJECT:
                ArrayList<SmallLiabilityExchangeCoin> smallLiabilityExchangeCoins = new ArrayList<>();
                JSONArray jCoins = new JSONArray(coinListResponse);
                for (int j = 0; j < jCoins.length(); j++)
                    smallLiabilityExchangeCoins.add(new SmallLiabilityExchangeCoin(jCoins.getJSONObject(j)));
                return (T) smallLiabilityExchangeCoins;
            default:
                return (T) coinListResponse;
        }
    }

    /**
     * Request to cross Margin Small Liability Exchange
     *
     * @param assetNames: the assets list of small liability exchange
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#small-liability-exchange-margin">
     * Small Liability Exchange (MARGIN)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/margin/exchange-small-liability")
    public boolean smallLiabilityExchange(String... assetNames) throws Exception {
        Params payload = new Params();
        payload.addParam("assetNames", assetNames);
        sendPostSignedRequest(EXCHANGE_SMALL_LIABILITY_ENDPOINT, payload);
        return apiRequest.getResponseStatusCode() == 200;
    }

    /**
     * Request to get Small liability Exchange History
     *
     * @param current: currently querying page. Start from 1. Default:1
     * @param size:    default:10, Max:100
     * @return small liability exchange history as {@link SmallLiabilityExchangeHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-history-user_data">
     * Get Small Liability Exchange History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/exchange-small-liability-history")
    public SmallLiabilityExchangeHistory getSmallLiabilityExchangeHistory(int current, int size) throws Exception {
        return getSmallLiabilityExchangeHistory(current, size, LIBRARY_OBJECT);
    }

    /**
     * Request to get Small liability Exchange History
     *
     * @param current: currently querying page. Start from 1. Default:1
     * @param size:    default:10, Max:100
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return small liability exchange history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-history-user_data">
     * Get Small Liability Exchange History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/margin/exchange-small-liability-history")
    public <T> T getSmallLiabilityExchangeHistory(int current, int size, ReturnFormat format) throws Exception {
        return getSmallLiabilityExchangeHistory(current, size, null, format);
    }

    /**
     * Request to get Small liability Exchange History
     *
     * @param current:     currently querying page. Start from 1. Default:1
     * @param size:        default:10, Max:100
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> days from current timestamp - [LONG, default 30]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched
     *                                - [LONG, default present timestamp]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return small liability exchange history as {@link SmallLiabilityExchangeHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-history-user_data">
     * Get Small Liability Exchange History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/exchange-small-liability-history")
    public SmallLiabilityExchangeHistory getSmallLiabilityExchangeHistory(int current, int size,
                                                                          Params extraParams) throws Exception {
        return getSmallLiabilityExchangeHistory(current, size, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get Small liability Exchange History
     *
     * @param current:     currently querying page. Start from 1. Default:1
     * @param size:        default:10, Max:100
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> days from current timestamp - [LONG, default 30]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched
     *                                - [LONG, default present timestamp]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return small liability exchange history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-history-user_data">
     * Get Small Liability Exchange History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/exchange-small-liability-history")
    public <T> T getSmallLiabilityExchangeHistory(int current, int size, Params extraParams,
                                                  ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("current", current);
        extraParams.addParam("size", size);
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetSignedRequest(EXCHANGE_SMALL_LIABILITY_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new SmallLiabilityExchangeHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get user the next hourly estimate interest
     *
     * @param isIsolated: for isolated margin or not, {@code "TRUE"}, {@code "FALSE"}
     * @param assets:     list of assets up to 20
     * @return next hourly estimate interest as {@link ArrayList} of {@link FutureHourlyInterestRate} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-a-future-hourly-interest-rate-user_data">
     * Get a future hourly interest rate (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/margin/next-hourly-interest-rate")
    public ArrayList<FutureHourlyInterestRate> getFutureHourlyInterestRates(boolean isIsolated,
                                                                            String... assets) throws Exception {
        return getFutureHourlyInterestRates(isIsolated, LIBRARY_OBJECT, assets);
    }

    /**
     * Request to get user the next hourly estimate interest
     *
     * @param isIsolated: for isolated margin or not, {@code "TRUE"}, {@code "FALSE"}
     * @param assets:     list of assets up to 20
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return next hourly estimate interest as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-a-future-hourly-interest-rate-user_data">
     * Get a future hourly interest rate (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/margin/next-hourly-interest-rate")
    public <T> T getFutureHourlyInterestRates(boolean isIsolated, ReturnFormat format, String... assets) throws Exception {
        Params query = new Params();
        query.addParam("isIsolated", isIsolated);
        query.addParam("timestamp", System.currentTimeMillis());
        query.addParam("assets", Arrays.stream(assets).toList());
        String interestRateResponse = sendGetSignedRequest(NEXT_HOURLY_INTEREST_RATE_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONArray(interestRateResponse);
            case LIBRARY_OBJECT:
                ArrayList<FutureHourlyInterestRate> futureHourlyInterestRates = new ArrayList<>();
                JSONArray jRates = new JSONArray(interestRateResponse);
                for (int j = 0; j < jRates.length(); j++)
                    futureHourlyInterestRates.add(new FutureHourlyInterestRate(jRates.getJSONObject(j)));
                return (T) futureHourlyInterestRates;
            default:
                return (T) interestRateResponse;
        }
    }

    /**
     * {@code MarginTransferType} list of available transfer type
     **/
    public enum MarginTransferType {

        /**
         * {@code "pending"} status
         **/
        MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT(1),

        /**
         * {@code "credited_but_cannot_withdraw"} status
         **/
        CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT(2);

        /**
         * {@code "status"} value
         **/
        private final int transferType;

        /**
         * Constructor to init {@link Deposit.DepositStatus}
         *
         * @param transferType: status value
         **/
        MarginTransferType(int transferType) {
            this.transferType = transferType;
        }

        /**
         * Method to get the {@link MarginTransferType} by an int <br>
         * No-any params required
         *
         * @return {@link MarginTransferType} corresponding value
         **/
        public static MarginTransferType valueOf(int status) {
            if (status == 1)
                return MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT;
            return CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT;
        }

        /**
         * Method to get {@link #transferType} instance <br>
         * No-any params required
         *
         * @return {@link #transferType} instance as {@link String}
         **/
        @Override
        public String toString() {
            return String.valueOf(transferType);
        }

    }

}