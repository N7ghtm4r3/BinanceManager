package com.tecknobit.binancemanager.managers.signedmanagers.futures;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.futures.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.futures.records.FutureAccountTransactionsHistory.FutureTransactionType;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceFuturesManager} class is useful to manage futures endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#futures">
 * Futures</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceFuturesManager extends BinanceSignedManager {

    /**
     * {@code FUTURES_TRANSFER_ENDPOINT} is constant for FUTURES_TRANSFER_ENDPOINT's endpoint
     **/
    public static final String FUTURES_TRANSFER_ENDPOINT = "/sapi/v1/futures/transfer";

    /**
     * {@code FUTURES_LOAN_BORROW_HISTORY_ENDPOINT} is constant for FUTURES_LOAN_BORROW_HISTORY_ENDPOINT's endpoint
     **/
    public static final String FUTURES_LOAN_BORROW_HISTORY_ENDPOINT = "/sapi/v1/futures/loan/borrow/history";

    /**
     * {@code FUTURES_LOAN_REPAY_HISTORY_ENDPOINT} is constant for FUTURES_LOAN_REPAY_HISTORY_ENDPOINT's endpoint
     **/
    public static final String FUTURES_LOAN_REPAY_HISTORY_ENDPOINT = "/sapi/v1/futures/loan/repay/history";

    /**
     * {@code FUTURES_LOAN_WALLET_ENDPOINT} is constant for FUTURES_LOAN_WALLET_ENDPOINT's endpoint
     **/
    public static final String FUTURES_LOAN_WALLET_ENDPOINT = "/sapi/v2/futures/loan/wallet";

    /**
     * {@code FUTURES_LOAN_ADJUST_COLLATERAL_HISTORY_ENDPOINT} is constant for FUTURES_LOAN_ADJUST_COLLATERAL_HISTORY_ENDPOINT's endpoint
     **/
    public static final String FUTURES_LOAN_ADJUST_COLLATERAL_HISTORY_ENDPOINT = "/sapi/v1/futures/loan/adjustCollateral/history";

    /**
     * {@code FUTURES_LOAN_LIQUIDATION_HISTORY_ENDPOINT} is constant for FUTURES_LOAN_LIQUIDATION_HISTORY_ENDPOINT's endpoint
     **/
    public static final String FUTURES_LOAN_LIQUIDATION_HISTORY_ENDPOINT = "/sapi/v1/futures/loan/liquidationHistory";

    /**
     * {@code FUTURES_LOAN_INTEREST_HISTORY_ENDPOINT} is constant for FUTURES_LOAN_INTEREST_HISTORY_ENDPOINT's endpoint
     **/
    public static final String FUTURES_LOAN_INTEREST_HISTORY_ENDPOINT = "/sapi/v1/futures/loan/interestHistory";

    /**
     * Constructor to init a {@link BinanceFuturesManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFuturesManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFuturesManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFuturesManager(String baseEndpoint, int timeout, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFuturesManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFuturesManager} <br>
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
    public BinanceFuturesManager() {
        super();
    }

    /**
     * Request to execute transfer between spot account and futures account
     *
     * @param asset:  the asset being transferred
     * @param amount: the amount to be transferred
     * @param type:   the type of the transfer
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-future-account-transfer-user_data">
     * New Future Account Transfer (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/futures/transfer")
    public long execNewFutureAccountTransfer(String asset, double amount, FutureTransactionType type) throws Exception {
        return Long.parseLong(execNewFutureAccountTransfer(asset, amount, type, LIBRARY_OBJECT));
    }

    /**
     * Request to execute transfer between spot account and futures account
     *
     * @param asset:  the asset being transferred
     * @param amount: the amount to be transferred
     * @param type:   the type of the transfer
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return transfer id list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-future-account-transfer-user_data">
     * New Future Account Transfer (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/futures/transfer")
    public <T> T execNewFutureAccountTransfer(String asset, double amount, FutureTransactionType type,
                                              ReturnFormat format) throws Exception {
        return execNewFutureAccountTransfer(asset, amount, type, -1, format);
    }

    /**
     * Request to execute transfer between spot account and futures account
     *
     * @param asset:      the asset being transferred
     * @param amount:     the amount to be transferred
     * @param type:       the type of the transfer
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-future-account-transfer-user_data">
     * New Future Account Transfer (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/futures/transfer")
    public long execNewFutureAccountTransfer(String asset, double amount, FutureTransactionType type,
                                             long recvWindow) throws Exception {
        return Long.parseLong(execNewFutureAccountTransfer(asset, amount, type, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to execute transfer between spot account and futures account
     *
     * @param asset:      the asset being transferred
     * @param amount:     the amount to be transferred
     * @param type:       the type of the transfer
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return transfer id list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-future-account-transfer-user_data">
     * New Future Account Transfer (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/futures/transfer")
    public <T> T execNewFutureAccountTransfer(String asset, double amount, FutureTransactionType type, long recvWindow,
                                              ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("asset", asset);
        payload.addParam("amount", amount);
        payload.addParam("type", type.getType());
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String transferResponse = sendPostSignedRequest(FUTURES_TRANSFER_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(transferResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(JsonHelper.getLong(new JSONObject(transferResponse), "tranId"));
            default:
                return (T) transferResponse;
        }
    }

    /**
     * Request to get the future account transaction history list
     *
     * @param asset:     the asset being transferred
     * @param startTime: the start time from fetch the list
     * @return future account transaction history list as {@link FutureAccountTransactionsHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-future-account-transaction-history-list-user_data">
     * Get Future Account Transaction History List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/transfer")
    public FutureAccountTransactionsHistory getFutureAccountTransactionsHistory(String asset, long startTime) throws Exception {
        return getFutureAccountTransactionsHistory(asset, startTime, LIBRARY_OBJECT);
    }

    /**
     * Request to get the future account transaction history list
     *
     * @param asset:     the asset being transferred
     * @param startTime: the start time from fetch the list
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return future account transaction history list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-future-account-transaction-history-list-user_data">
     * Get Future Account Transaction History List (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/futures/transfer")
    public <T> T getFutureAccountTransactionsHistory(String asset, long startTime, ReturnFormat format) throws Exception {
        return getFutureAccountTransactionsHistory(asset, startTime, null, format);
    }

    /**
     * Request to get the future account transaction history list
     *
     * @param asset:       the asset being transferred
     * @param startTime:   the start time from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return future account transaction history list as {@link FutureAccountTransactionsHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-future-account-transaction-history-list-user_data">
     * Get Future Account Transaction History List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/transfer")
    public FutureAccountTransactionsHistory getFutureAccountTransactionsHistory(String asset, long startTime,
                                                                                Params extraParams) throws Exception {
        return getFutureAccountTransactionsHistory(asset, startTime, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the future account transaction history list
     *
     * @param asset:       the asset being transferred
     * @param startTime:   the start time from fetch the list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size value, max 100 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return future account transaction history list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-future-account-transaction-history-list-user_data">
     * Get Future Account Transaction History List (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/futures/transfer")
    public <T> T getFutureAccountTransactionsHistory(String asset, long startTime, Params extraParams,
                                                     ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("asset", asset);
        extraParams.addParam("startTime", startTime);
        extraParams.addParam("timestamp", getServerTime());
        String transactionsHistory = sendGetSignedRequest(FUTURES_TRANSFER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(transactionsHistory);
            case LIBRARY_OBJECT:
                return (T) new FutureAccountTransactionsHistory(new JSONObject(transactionsHistory));
            default:
                return (T) transactionsHistory;
        }
    }

    /**
     * Request to get the cross collateral borrow history <br>
     * No-any params required
     *
     * @return cross collateral borrow history as {@link CrossCollateralBorrowHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-borrow-history-user_data">
     * Cross-Collateral Borrow History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/borrow/history")
    public CrossCollateralBorrowHistory getCrossCollateralBorrowHistory() throws Exception {
        return getCrossCollateralBorrowHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral borrow history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross collateral borrow history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-borrow-history-user_data">
     * Cross-Collateral Borrow History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/borrow/history")
    public <T> T getCrossCollateralBorrowHistory(ReturnFormat format) throws Exception {
        return getCrossCollateralBorrowHistory(null, format);
    }

    /**
     * Request to get the cross collateral borrow history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross collateral borrow history as {@link CrossCollateralBorrowHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-borrow-history-user_data">
     * Cross-Collateral Borrow History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/borrow/history")
    public CrossCollateralBorrowHistory getCrossCollateralBorrowHistory(Params extraParams) throws Exception {
        return getCrossCollateralBorrowHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral borrow history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cross collateral borrow history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-borrow-history-user_data">
     * Cross-Collateral Borrow History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/borrow/history")
    public <T> T getCrossCollateralBorrowHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String borrowHistory = sendGetSignedRequest(FUTURES_LOAN_BORROW_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(borrowHistory);
            case LIBRARY_OBJECT:
                return (T) new CrossCollateralBorrowHistory(new JSONObject(borrowHistory));
            default:
                return (T) borrowHistory;
        }
    }

    /**
     * Request to get the cross collateral repayment history <br>
     * No-any params required
     *
     * @return cross collateral repayment history as {@link CrossCollateralRepaymentsHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-repayment-history-user_data">
     * Cross-Collateral Repayment History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/repay/history")
    public CrossCollateralRepaymentsHistory getCrossCollateralRepaymentHistory() throws Exception {
        return getCrossCollateralRepaymentHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral repayment history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross collateral repayment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-repayment-history-user_data">
     * Cross-Collateral Repayment History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/repay/history")
    public <T> T getCrossCollateralRepaymentHistory(ReturnFormat format) throws Exception {
        return getCrossCollateralRepaymentHistory(null, format);
    }

    /**
     * Request to get the cross collateral repayment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross collateral repayment history as {@link CrossCollateralRepaymentsHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-repayment-history-user_data">
     * Cross-Collateral Repayment History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/repay/history")
    public CrossCollateralRepaymentsHistory getCrossCollateralRepaymentHistory(Params extraParams) throws Exception {
        return getCrossCollateralRepaymentHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral repayment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cross collateral repayment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-repayment-history-user_data">
     * Cross-Collateral Repayment History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/repay/history")
    public <T> T getCrossCollateralRepaymentHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String repaymentHistory = sendGetSignedRequest(FUTURES_LOAN_REPAY_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(repaymentHistory);
            case LIBRARY_OBJECT:
                return (T) new CrossCollateralRepaymentsHistory(new JSONObject(repaymentHistory));
            default:
                return (T) repaymentHistory;
        }
    }

    /**
     * Request to get the cross collateral wallet <br>
     * No-any params required
     *
     * @return cross collateral wallet as {@link CrossCollateralWallet} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-wallet-v2-user_data">
     * Cross-Collateral Wallet V2 (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v2/futures/loan/wallet")
    public CrossCollateralWallet getCrossCollateralWallet() throws Exception {
        return getCrossCollateralWallet(LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral wallet
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross collateral wallet as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-wallet-v2-user_data">
     * Cross-Collateral Wallet V2 (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v2/futures/loan/wallet")
    public <T> T getCrossCollateralWallet(ReturnFormat format) throws Exception {
        return getCrossCollateralWallet(-1, format);
    }

    /**
     * Request to get the cross collateral wallet
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return cross collateral wallet as {@link CrossCollateralWallet} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-wallet-v2-user_data">
     * Cross-Collateral Wallet V2 (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v2/futures/loan/wallet")
    public CrossCollateralWallet getCrossCollateralWallet(long recvWindow) throws Exception {
        return getCrossCollateralWallet(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral wallet
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return cross collateral wallet as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-wallet-v2-user_data">
     * Cross-Collateral Wallet V2 (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v2/futures/loan/wallet")
    public <T> T getCrossCollateralWallet(long recvWindow, ReturnFormat format) throws Exception {
        Params query = new Params();
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        query.addParam("timestamp", getServerTime());
        String wallerResponse = sendGetSignedRequest(FUTURES_LOAN_WALLET_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(wallerResponse);
            case LIBRARY_OBJECT:
                return (T) new CrossCollateralWallet(new JSONObject(wallerResponse));
            default:
                return (T) wallerResponse;
        }
    }

    /**
     * Request to get the cross collateral LTV history <br>
     * No-any params required
     *
     * @return cross collateral LTV history as {@link CrossCollateralLTVHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-cross-collateral-ltv-history-user_data">
     * Adjust Cross-Collateral LTV History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/adjustCollateral/history")
    public CrossCollateralLTVHistory getAdjustCrossCollateralLTVHistory() throws Exception {
        return getAdjustCrossCollateralLTVHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral LTV history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross collateral LTV history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-cross-collateral-ltv-history-user_data">
     * Adjust Cross-Collateral LTV History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/adjustCollateral/history")
    public <T> T getAdjustCrossCollateralLTVHistory(ReturnFormat format) throws Exception {
        return getAdjustCrossCollateralLTVHistory(null, format);
    }

    /**
     * Request to get the cross collateral LTV history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross collateral LTV history as {@link CrossCollateralLTVHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-cross-collateral-ltv-history-user_data">
     * Adjust Cross-Collateral LTV History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/adjustCollateral/history")
    public CrossCollateralLTVHistory getAdjustCrossCollateralLTVHistory(Params extraParams) throws Exception {
        return getAdjustCrossCollateralLTVHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral LTV history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cross collateral LTV history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-cross-collateral-ltv-history-user_data">
     * Adjust Cross-Collateral LTV History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/adjustCollateral/history")
    public <T> T getAdjustCrossCollateralLTVHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String LTVHistory = sendGetSignedRequest(FUTURES_LOAN_ADJUST_COLLATERAL_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(LTVHistory);
            case LIBRARY_OBJECT:
                return (T) new CrossCollateralLTVHistory(new JSONObject(LTVHistory));
            default:
                return (T) LTVHistory;
        }
    }

    /**
     * Request to get the cross collateral liquidation history <br>
     * No-any params required
     *
     * @return cross collateral liquidation history as {@link CrossCollateralLiquidationHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-liquidation-history-user_data">
     * Cross-Collateral Liquidation History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/liquidationHistory")
    public CrossCollateralLiquidationHistory getCrossCollateralLiquidationHistory() throws Exception {
        return getCrossCollateralLiquidationHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral liquidation history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross collateral liquidation history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-liquidation-history-user_data">
     * Cross-Collateral Liquidation History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/liquidationHistory")
    public <T> T getCrossCollateralLiquidationHistory(ReturnFormat format) throws Exception {
        return getCrossCollateralLiquidationHistory(null, format);
    }

    /**
     * Request to get the cross collateral liquidation history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross collateral liquidation history as {@link CrossCollateralLiquidationHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-liquidation-history-user_data">
     * Cross-Collateral Liquidation History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/liquidationHistory")
    public CrossCollateralLiquidationHistory getCrossCollateralLiquidationHistory(Params extraParams) throws Exception {
        return getCrossCollateralLiquidationHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral liquidation history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cross collateral liquidation history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-liquidation-history-user_data">
     * Cross-Collateral Liquidation History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/liquidationHistory")
    public <T> T getCrossCollateralLiquidationHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String liquidationHistory = sendGetSignedRequest(FUTURES_LOAN_LIQUIDATION_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(liquidationHistory);
            case LIBRARY_OBJECT:
                return (T) new CrossCollateralLiquidationHistory(new JSONObject(liquidationHistory));
            default:
                return (T) liquidationHistory;
        }
    }

    /**
     * Request to get the cross collateral interest history <br>
     * No-any params required
     *
     * @return cross collateral interest history as {@link CrossCollateralInterestsHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-interest-history-user_data">
     * Cross-Collateral Interest History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/interestHistory")
    public CrossCollateralInterestsHistory getCrossCollateralInterestHistory() throws Exception {
        return getCrossCollateralInterestHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral interest history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return cross collateral interest history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-interest-history-user_data">
     * Cross-Collateral Interest History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/interestHistory")
    public <T> T getCrossCollateralInterestHistory(ReturnFormat format) throws Exception {
        return getCrossCollateralInterestHistory(null, format);
    }

    /**
     * Request to get the cross collateral interest history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return cross collateral interest history as {@link CrossCollateralInterestsHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-interest-history-user_data">
     * Cross-Collateral Interest History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/interestHistory")
    public CrossCollateralInterestsHistory getCrossCollateralInterestHistory(Params extraParams) throws Exception {
        return getCrossCollateralInterestHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the cross collateral interest history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page. Start from 1 - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return cross collateral interest history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-interest-history-user_data">
     * Cross-Collateral Interest History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/futures/loan/interestHistory")
    public <T> T getCrossCollateralInterestHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String liquidationHistory = sendGetSignedRequest(FUTURES_LOAN_INTEREST_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(liquidationHistory);
            case LIBRARY_OBJECT:
                return (T) new CrossCollateralInterestsHistory(new JSONObject(liquidationHistory));
            default:
                return (T) liquidationHistory;
        }
    }

}
