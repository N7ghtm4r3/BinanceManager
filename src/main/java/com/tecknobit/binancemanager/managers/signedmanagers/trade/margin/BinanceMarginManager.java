package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
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

import static com.tecknobit.apimanager.apis.APIRequest.*;
import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.OrderType.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.SpotOrder.*;


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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public long executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type) throws Exception {
        return Long.parseLong(executeCrossMarginAccountTransfer(asset, amount, type, LIBRARY_OBJECT));
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public <T> T executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type,
                                                   ReturnFormat format) throws Exception {
        return returnExecuteCrossMarginAccountTransfer(sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,
                getTimestampParam() + "&asset=" + asset + "&amount=" + asset + "&type=" + type,
                POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public long executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type,
                                                  long recvWindow) throws Exception {
        return Long.parseLong(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow, LIBRARY_OBJECT));
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public <T> T executeCrossMarginAccountTransfer(String asset, double amount, MarginTransferType type,
                                                   long recvWindow, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&asset=" + asset + "&amount=" + amount + "&type=" + type + "&recvWindow="
                + recvWindow;
        return returnExecuteCrossMarginAccountTransfer(sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params,
                POST_METHOD), format);
    }

    /**
     * Method to create an execution
     *
     * @param executionResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return execution as {@code "format"} defines
     * @implSpec in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the transaction id as long
     **/
    @Returner
    private <T> T returnExecuteCrossMarginAccountTransfer(String executionResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(executionResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(executionResponse).getString("tranId");
            default:
                return (T) executionResponse;
        }
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
    @RequestPath(path = "/sapi/v1/margin/loan")
    public long applyMarginAccountBorrow(String asset, double amount) throws Exception {
        return Long.parseLong(applyMarginAccountBorrow(asset, amount, LIBRARY_OBJECT));
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
    @RequestPath(path = "/sapi/v1/margin/loan")
    public <T> T applyMarginAccountBorrow(String asset, double amount, ReturnFormat format) throws Exception {
        return returnApplyMarginAccountBorrow(sendSignedRequest(MARGIN_LOAN_ENDPOINT, getTimestampParam() +
                "&asset=" + asset + "&amount=" + amount, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/loan")
    public long applyMarginAccountBorrow(String asset, double amount, Params extraParams) throws Exception {
        return Long.parseLong(applyMarginAccountBorrow(asset, amount, extraParams, LIBRARY_OBJECT));
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
    @RequestPath(path = "/sapi/v1/margin/loan")
    public <T> T applyMarginAccountBorrow(String asset, double amount, Params extraParams,
                                          ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&asset=" + asset + "&amount=" + amount;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnApplyMarginAccountBorrow(sendSignedRequest(MARGIN_LOAN_ENDPOINT, params, POST_METHOD), format);
    }

    /**
     * Method to create an apply margin account borrow
     *
     * @param applyMarginResponse: obtained from Binance's response
     * @param format:              return type formatter -> {@link ReturnFormat}
     * @return apply margin account borrow as {@code "format"} defines
     * @implSpec in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the transaction id as long
     **/
    @Returner
    private <T> T returnApplyMarginAccountBorrow(String applyMarginResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(applyMarginResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(applyMarginResponse).getString("tranId");
            default:
                return (T) applyMarginResponse;
        }
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
    @RequestPath(path = "/sapi/v1/margin/repay")
    public long repayMarginAccount(String asset, double amount) throws Exception {
        return Long.parseLong(repayMarginAccount(asset, amount, LIBRARY_OBJECT));
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
    @RequestPath(path = "/sapi/v1/margin/repay")
    public <T> T repayMarginAccount(String asset, double amount, ReturnFormat format) throws Exception {
        return returnRepayMarginAccount(sendSignedRequest(MARGIN_REPAY_ENDPOINT, getTimestampParam() + "&asset="
                + asset + "&amount=" + amount, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/repay")
    public long repayMarginAccount(String asset, double amount, Params extraParams) throws Exception {
        return Long.parseLong(repayMarginAccount(asset, amount, extraParams, LIBRARY_OBJECT));
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
    @RequestPath(path = "/sapi/v1/margin/repay")
    public <T> T repayMarginAccount(String asset, double amount, Params extraParams, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&asset=" + asset + "&amount=" + amount;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnRepayMarginAccount(sendSignedRequest(MARGIN_REPAY_ENDPOINT, params, POST_METHOD), format);
    }

    /**
     * Method to create an apply margin account borrow
     *
     * @param repayMarginAccountResponse: obtained from Binance's response
     * @param format:                     return type formatter -> {@link ReturnFormat}
     * @return apply margin account borrow as {@code "format"} defines
     * @implSpec in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the transaction id as long
     **/
    @Returner
    private <T> T returnRepayMarginAccount(String repayMarginAccountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(repayMarginAccountResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(repayMarginAccountResponse).getString("tranId");
            default:
                return (T) repayMarginAccountResponse;
        }
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
    @RequestPath(path = "/sapi/v1/margin/asset")
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
    @RequestPath(path = "/sapi/v1/margin/asset")
    public <T> T queryMarginAsset(String asset, ReturnFormat format) throws Exception {
        String assetResponse = sendSignedRequest(QUERY_MARGIN_ASSET_ENDPOINT, getTimestampParam() + "&asset="
                + asset, GET_METHOD);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/allAssets")
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
    @RequestPath(path = "/sapi/v1/margin/allAssets")
    public <T> T queryAllMarginAssets(ReturnFormat format) throws Exception {
        String assetsResponse = sendSignedRequest(QUERY_ALL_MARGIN_ASSETS_ENDPOINT, "", GET_METHOD);
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
    @RequestPath(path = "/sapi/v1/margin/pair")
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
    @RequestPath(path = "/sapi/v1/margin/pair")
    public <T> T queryCrossMarginPair(String symbol, ReturnFormat format) throws Exception {
        String assetResponse = sendSignedRequest(QUERY_CROSS_MARGIN_PAIR_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol, GET_METHOD);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/allPairs")
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
    @RequestPath(path = "/sapi/v1/margin/allPairs")
    public <T> T queryAllMarginPairs(ReturnFormat format) throws Exception {
        String pairsResponse = sendSignedRequest(QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT, "", GET_METHOD);
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
    @RequestPath(path = "/sapi/v1/margin/priceIndex")
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
    @RequestPath(path = "/sapi/v1/margin/priceIndex")
    public <T> T getMarginPriceIndex(String symbol, ReturnFormat format) throws Exception {
        String priceResponse = sendSignedRequest(MARGIN_PRICE_INDEX_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
        String payload = apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                "&side=" + side + "&type=" + type + "&newOrderRespType=" + newOrderRespType, extraParams);
        String orderResponse = sendSignedRequest(MARGIN_ORDER_ENDPOINT, payload, POST_METHOD);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
    public <T> T cancelMarginOrder(String symbol, ReturnFormat format) throws Exception {
        return returnMarginOrderDetails(sendSignedRequest(MARGIN_ORDER_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol, DELETE_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/order")
    public MarginOrderDetails cancelMarginMarginOrder(MarginOrder order, Params extraParams) throws Exception {
        return cancelMarginMarginOrder(order.getSymbol(), extraParams, LIBRARY_OBJECT);
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
    @RequestPath(path = "/sapi/v1/margin/order")
    public <T> T cancelMarginMarginOrder(MarginOrder order, Params extraParams, ReturnFormat format) throws Exception {
        return cancelMarginMarginOrder(order.getSymbol(), extraParams, format);
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
    @RequestPath(path = "/sapi/v1/margin/order")
    public MarginOrderDetails cancelMarginMarginOrder(String symbol, Params extraParams) throws Exception {
        return cancelMarginMarginOrder(symbol, extraParams, LIBRARY_OBJECT);
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
    @RequestPath(path = "/sapi/v1/margin/order")
    public <T> T cancelMarginMarginOrder(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol,
                extraParams);
        return returnMarginOrderDetails(sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, DELETE_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price,
                                       double stopPrice, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity
                + "&price=" + price + "&stopPrice=" + stopPrice;
        return returnOCOMarginOrder(sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                       Params extraParams, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity +
                "&price=" + price + "&stopPrice=" + stopPrice;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnOCOMarginOrder(sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                       double stopLimitPrice, TimeInForce stopLimitTimeInForce,
                                       ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity +
                "&price=" + price + "&stopPrice=" + stopPrice + "&stopLimitPrice=" + stopLimitPrice +
                "&stopLimitTimeInForce=" + stopLimitTimeInForce;
        return returnOCOMarginOrder(sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
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
    @RequestPath(path = "/sapi/v1/margin/order/oco")
    public <T> T sendNewOCOMarginOrder(String symbol, Side side, double quantity, double price, double stopPrice,
                                       double stopLimitPrice, TimeInForce stopLimitTimeInForce, Params extraParams,
                                       ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity +
                "&price=" + price + "&stopPrice=" + stopPrice + "&stopLimitPrice=" + stopLimitPrice +
                "&stopLimitTimeInForce=" + stopLimitTimeInForce;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnOCOMarginOrder(sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
    public <T> T cancelAllMarginOrders(String symbol, ReturnFormat format) throws Exception {
        return returnOpenMarginOrders(sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol, DELETE_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
    public <T> T cancelAllMarginOrders(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnOpenMarginOrders(sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params, DELETE_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransfersHistory(ReturnFormat format) throws Exception {
        return returnTransfersHistoryList(sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, getTimestampParam(),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransfersHistory(Params extraParams, ReturnFormat format) throws Exception {
        return returnTransfersHistoryList(sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransfersHistory(String asset, ReturnFormat format) throws Exception {
        return returnTransfersHistoryList(sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, getTimestampParam()
                + "&asset=" + asset, GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/transfer")
    public <T> T getCrossMarginTransferHistory(String asset, Params extraParams, ReturnFormat format) throws Exception {
        String params = getTimestampParam() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return returnTransfersHistoryList(sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/loan")
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
    @RequestPath(path = "/sapi/v1/margin/loan")
    public <T> T getLoansList(String asset, ReturnFormat format) throws Exception {
        return returnLoansList(sendSignedRequest(MARGIN_LOAN_ENDPOINT, getTimestampParam() + "&asset=" + asset,
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/loan")
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
    @RequestPath(path = "/sapi/v1/margin/loan")
    public <T> T getLoansList(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnLoansList(sendSignedRequest(MARGIN_LOAN_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/repay")
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
    @RequestPath(path = "/sapi/v1/margin/repay")
    public <T> T getRepaysList(String asset, ReturnFormat format) throws Exception {
        return returnRepaysList(sendSignedRequest(MARGIN_REPAY_ENDPOINT, getTimestampParam() + "&asset=" + asset,
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/repay")
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
    @RequestPath(path = "/sapi/v1/margin/repay")
    public <T> T getRepaysList(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnRepaysList(sendSignedRequest(MARGIN_REPAY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams),
                GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
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
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, getTimestampParam(),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
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
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(Params extraParams, ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
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
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(String asset, ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, getTimestampParam()
                + "&asset=" + asset, GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
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
    @RequestPath(path = "/sapi/v1/margin/interestHistory")
    public <T> T getInterestHistory(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnInterestHistoryList(sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams),
                GET_METHOD), format);
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
     * Any params required
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
    public <T> T getForceLiquidation(ReturnFormat format) throws Exception {
        return returnForceLiquidationList(sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT, getTimestampParam(),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/forceLiquidationRec")
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
    @RequestPath(path = "/sapi/v1/margin/forceLiquidationRec")
    public <T> T getForceLiquidation(Params extraParams, ReturnFormat format) throws Exception {
        return returnForceLiquidationList(sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/account")
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
    @RequestPath(path = "/sapi/v1/margin/account")
    public <T> T getCrossMarginAccountDetails(ReturnFormat format) throws Exception {
        return returnCrossMarginAccountDetails(sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,
                getTimestampParam(), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/account")
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
    @RequestPath(path = "/sapi/v1/margin/account")
    public <T> T getCrossMarginAccountDetails(long recvWindow, ReturnFormat format) throws Exception {
        return returnCrossMarginAccountDetails(sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,
                getTimestampParam() + "&recvWindow=" + recvWindow, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
    public <T> T getMarginOrderStatus(String symbol, ReturnFormat format) throws Exception {
        return returnMarginOrderStatus(sendSignedRequest(MARGIN_ORDER_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/order")
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
    @RequestPath(path = "/sapi/v1/margin/order")
    public <T> T getMarginOrderStatus(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatus(sendSignedRequest(MARGIN_ORDER_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams),
                GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(ReturnFormat format) throws Exception {
        return returnAllOrders(sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam(), GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/openOrders")
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(String symbol, ReturnFormat format) throws Exception {
        return returnAllOrders(sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam() + "&symbol=" +
                symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(Params extraParams, ReturnFormat format) throws Exception {
        return returnAllOrders(sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/openOrders")
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
    @RequestPath(path = "/sapi/v1/margin/openOrders")
    public <T> T getAllOpenOrders(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnAllOrders(sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol + "&recvWindow=" + recvWindow, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrders")
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
    @RequestPath(path = "/sapi/v1/margin/allOrders")
    public <T> T getAllOrders(String symbol, ReturnFormat format) throws Exception {
        return returnAllOrders(sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrders")
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
    @RequestPath(path = "/sapi/v1/margin/allOrders")
    public <T> T getAllOrders(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnAllOrders(sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&orderListId=" + orderListId, DELETE_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, String listClientOrderId, ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId, DELETE_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, long orderListId, Params extraParams,
                                      ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&orderListId=" + orderListId, extraParams), DELETE_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T cancelOCOMarginOrder(String symbol, String listClientOrderId, Params extraParams,
                                      ReturnFormat format) throws Exception {
        return returnComposedMarginOrderDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&listClientOrderId=" + listClientOrderId, extraParams), DELETE_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(String symbol, String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam() +
                        "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId + "&isIsolated=" + true, GET_METHOD),
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam()
                + "&orderListId=" + orderListId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
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
    @RequestPath(path = "/sapi/v1/margin/orderList")
    public <T> T getOCOMarginOrderStatus(String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetails(sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, getTimestampParam() +
                "&origClientOrderId=" + origClientOrderId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true,
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId +
                        "&isIsolated=" + true, GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true + "&" + keyTime + "=" +
                valueTime + "&fromId=" + fromId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                 long valueTime, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId + "&isIsolated=" + true + "&" + keyTime
                + "=" + valueTime + "&fromId=" + fromId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&orderListId=" + orderListId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&origClientOrderId=" + origClientOrderId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, long fromId, String keyTime, long valueTime,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&orderListId=" + orderListId + "&" + keyTime + "=" + valueTime +
                        "&fromId=" + fromId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, long fromId, String keyTime, long valueTime,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                getTimestampParam() + "&origClientOrderId=" + origClientOrderId + "&" + keyTime + "=" + valueTime
                        + "&fromId=" + fromId, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, GET_METHOD,
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&origClientOrderId=" + origClientOrderId, extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                 Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                                "&orderListId=" + orderListId + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId,
                        extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String symbol, String origClientOrderId, long fromId, String keyTime, long valueTime,
                                 Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&origClientOrderId=" + origClientOrderId + "&" + keyTime + "=" + valueTime +
                        "&fromId=" + fromId, extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&orderListId=" + orderListId,
                        extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&origClientOrderId=" +
                        origClientOrderId, extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(long orderListId, long fromId, String keyTime, long valueTime, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&orderListId=" + orderListId +
                        "&" + keyTime + "=" + valueTime + "&fromId=" + fromId, extraParams),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/allOrderList")
    public <T> T getAllOCOOrders(String origClientOrderId, long fromId, String keyTime, long valueTime, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&origClientOrderId=" +
                        origClientOrderId + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId, extraParams),
                GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(String symbol, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                        getTimestampParam() + "&symbol=" + symbol + "&isIsolated=" + true, GET_METHOD),
                format);
    }

    /**
     * Request to get all open OCO margin orders <br>
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                getTimestampParam(), GET_METHOD), format);
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
    @WrappedRequest
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&isIsolated=" + true, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
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
    @RequestPath(path = "/sapi/v1/margin/openOrderList")
    public <T> T getAllOCOOpenOrders(Params extraParams, ReturnFormat format) throws Exception {
        return returnMarginOrderStatusDetailsList(sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/myTrades")
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
    @RequestPath(path = "/sapi/v1/margin/myTrades")
    public <T> T getTradesList(String symbol, ReturnFormat format) throws Exception {
        return returnTradesList(sendSignedRequest(MARGIN_TRADES_LIST_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/myTrades")
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
    @RequestPath(path = "/sapi/v1/margin/myTrades")
    public <T> T getTradesList(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnTradesList(sendSignedRequest(MARGIN_TRADES_LIST_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/maxBorrowable")
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
    @RequestPath(path = "/sapi/v1/margin/maxBorrowable")
    public <T> T getMaxBorrow(String asset, ReturnFormat format) throws Exception {
        return returnMaxBorrow(sendSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT, getTimestampParam() +
                "&asset=" + asset, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/maxBorrowable")
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
    @RequestPath(path = "/sapi/v1/margin/maxBorrowable")
    public <T> T getMaxBorrow(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnMaxBorrow(sendSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/maxTransferable")
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
    @RequestPath(path = "/sapi/v1/margin/maxTransferable")
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
    @RequestPath(path = "/sapi/v1/margin/maxTransferable")
    public <T> T getMaxTransferOutAmount(String asset, ReturnFormat format) throws Exception {
        return returnMaxTransferAmount(sendSignedRequest(GET_MAX_MARGIN_TRANSFER_ENDPOINT,
                getTimestampParam() + "&asset=" + asset, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/maxTransferable")
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
    @RequestPath(path = "/sapi/v1/margin/maxTransferable")
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
    @RequestPath(path = "/sapi/v1/margin/maxTransferable")
    public <T> T getMaxTransferOutAmount(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnMaxTransferAmount(sendSignedRequest(GET_MAX_MARGIN_TRANSFER_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams),
                GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/tradeCoeff")
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
    @RequestPath(path = "/sapi/v1/margin/tradeCoeff")
    public <T> T getMarginAccountSummary(ReturnFormat format) throws Exception {
        return returnSummary(sendSignedRequest(GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT, getTimestampParam(), GET_METHOD),
                format);
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
    @RequestPath(path = "/sapi/v1/margin/tradeCoeff")
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
    @RequestPath(path = "/sapi/v1/margin/tradeCoeff")
    public <T> T getMarginAccountSummary(long recvWindow, ReturnFormat format) throws Exception {
        return returnSummary(sendSignedRequest(GET_SUMMARY_MARGIN_ACCOUNT_ENDPOINT, getTimestampParam() +
                "&recvWindow=" + recvWindow, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
    public long execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                  double amount) throws Exception {
        return Long.parseLong(execIsolatedMarginAccountTransfer(asset, symbol, transFrom, transTo, amount,
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
    public <T> T execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                   double amount, ReturnFormat format) throws Exception {
        return returnIsolatedTransferId(sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,
                getTimestampParam() + "&asset=" + asset + "&symbol=" + symbol + "&transFrom=" + transFrom +
                        "&transTo=" + transTo + "&amount=" + amount, POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
    public <T> T execIsolatedMarginAccountTransfer(String asset, String symbol, String transFrom, String transTo,
                                                   double amount, long recvWindow, ReturnFormat format) throws Exception {
        return returnIsolatedTransferId(sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,
                getTimestampParam() + "&asset=" + asset + "&symbol=" + symbol + "&transFrom=" + transFrom +
                        "&transTo=" + transTo + "&amount=" + amount + "&recvWindow=" + recvWindow, POST_METHOD), format);
    }

    /**
     * Method to get an isolated transfer id
     *
     * @param isolatedTransferIdResponse: obtained from Binance's response
     * @param format:                     return type formatter -> {@link ReturnFormat}
     * @return isolated transfer id as {@code "format"} defines
     * @implSpec in this case {@link ReturnFormat#LIBRARY_OBJECT} will return the transaction id as long
     **/
    @Returner
    private <T> T returnIsolatedTransferId(String isolatedTransferIdResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(isolatedTransferIdResponse);
            case LIBRARY_OBJECT:
                return (T) new JSONObject(isolatedTransferIdResponse).getString("tranId");
            default:
                return (T) isolatedTransferIdResponse;
        }
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
    public <T> T getIsolatedTransferHistory(String symbol, ReturnFormat format) throws Exception {
        return returnIsolatedTransferHistoryList(sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/transfer")
    public <T> T getIsolatedTransferHistory(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnIsolatedTransferHistoryList(sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams),
                GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
    public <T> T getMarginIsolatedAccount(ReturnFormat format) throws Exception {
        return returnComposedAccountInfo(sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, getTimestampParam(),
                GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
    public <T> T getMarginIsolatedAccount(Params extraParams, ReturnFormat format) throws Exception {
        return returnComposedAccountInfo(sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/account")
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
        String method = DELETE_METHOD;
        if (enableIsolated)
            method = POST_METHOD;
        String params = getTimestampParam() + "&symbol=" + symbol;
        if (recvWindow != -1)
            params += "&recvWindow=" + recvWindow;
        String marginResponse = sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params, method);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/isolated/accountLimit")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/accountLimit")
    public <T> T getIsolateMarginAccountLimit(ReturnFormat format) throws Exception {
        return returnIsolatedMarginAccountLimit(sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT,
                getTimestampParam(), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/accountLimit")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/accountLimit")
    public <T> T getIsolateMarginAccountLimit(long recvWindow, ReturnFormat format) throws Exception {
        return returnIsolatedMarginAccountLimit(sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT,
                getTimestampParam() + "&recvWindow=" + recvWindow, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/pair")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/pair")
    public <T> T getIsolatedMarginSymbol(String symbol, ReturnFormat format) throws Exception {
        return returnIsolatedMarginSymbol(sendSignedRequest(QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/pair")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/pair")
    public <T> T getIsolatedMarginSymbol(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnIsolatedMarginSymbol(sendSignedRequest(QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol + "&recvWindow=" + recvWindow, GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/isolated/allPairs")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/allPairs")
    public <T> T getAllIsolatedSymbols(ReturnFormat format) throws Exception {
        return returnAllIMarginSymbols(sendSignedRequest(QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam(), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolated/allPairs")
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
    @RequestPath(path = "/sapi/v1/margin/isolated/allPairs")
    public <T> T getAllIsolatedSymbols(long recvWindow, ReturnFormat format) throws Exception {
        return returnAllIMarginSymbols(sendSignedRequest(QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT,
                getTimestampParam() + "&recvWindow=" + recvWindow, GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
    public <T> T toggleBNBOnTradeInterest(ReturnFormat format) throws Exception {
        return returnToggleBNB(sendSignedRequest(MARGIN_BNB_ENDPOINT, getTimestampParam(), POST_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
    public <T> T toggleBNBOnTradeInterest(Params extraParams, ReturnFormat format) throws Exception {
        return returnToggleBNB(sendSignedRequest(MARGIN_BNB_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), POST_METHOD), format);
    }

    /**
     * Request to get BNB burn status <br>
     * Any params required
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
    public <T> T getBNBBurnStatus(ReturnFormat format) throws Exception {
        return returnToggleBNB(sendSignedRequest(MARGIN_BNB_ENDPOINT, getTimestampParam(), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
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
    @RequestPath(path = "/sapi/v1/bnbBurn")
    public <T> T getBNBBurnStatus(long recvWindow, ReturnFormat format) throws Exception {
        return returnToggleBNB(sendSignedRequest(MARGIN_BNB_ENDPOINT, getTimestampParam() + "&recvWindow=" +
                recvWindow, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/interestRateHistory")
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
    @RequestPath(path = "/sapi/v1/margin/interestRateHistory")
    public <T> T getInterestRateHistory(String asset, ReturnFormat format) throws Exception {
        return returnRateHistoryList(sendSignedRequest(MARGIN_INTEREST_RATE_HISTORY_ENDPOINT, getTimestampParam()
                + "&asset=" + asset, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/interestRateHistory")
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
    @RequestPath(path = "/sapi/v1/margin/interestRateHistory")
    public <T> T getInterestRateHistory(String asset, Params extraParams, ReturnFormat format) throws Exception {
        return returnRateHistoryList(sendSignedRequest(MARGIN_INTEREST_RATE_HISTORY_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&asset=" + asset, extraParams),
                GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/crossMarginData")
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
    @RequestPath(path = "/sapi/v1/margin/crossMarginData")
    public <T> T getCrossMarginFeeData(ReturnFormat format) throws Exception {
        return returnCrossMarginFee(sendSignedRequest(CROSS_MARGIN_DATA_ENDPOINT, getTimestampParam(), GET_METHOD),
                format);
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
    @RequestPath(path = "/sapi/v1/margin/crossMarginData")
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
    @RequestPath(path = "/sapi/v1/margin/crossMarginData")
    public <T> T getCrossMarginFeeData(Params extraParams, ReturnFormat format) throws Exception {
        return returnCrossMarginFee(sendSignedRequest(CROSS_MARGIN_DATA_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
     * Any params required
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginData")
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginData")
    public <T> T getIsolatedMarginFeeData(ReturnFormat format) throws Exception {
        return returnIsolatedMarginFee(sendSignedRequest(ISOLATED_MARGIN_DATA_ENDPOINT, getTimestampParam(), GET_METHOD),
                format);
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginData")
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginData")
    public <T> T getIsolatedMarginFeeData(Params extraParams, ReturnFormat format) throws Exception {
        return returnIsolatedMarginFee(sendSignedRequest(ISOLATED_MARGIN_DATA_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams), GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginTier")
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginTier")
    public <T> T getIsolatedMarginTierData(String symbol, ReturnFormat format) throws Exception {
        return returnIsolatedMarginTierData(sendSignedRequest(ISOLATED_MARGIN_TIER_DATA_ENDPOINT,
                getTimestampParam() + "&symbol=" + symbol, GET_METHOD), format);
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginTier")
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
    @RequestPath(path = "/sapi/v1/margin/isolatedMarginTier")
    public <T> T getIsolatedMarginTierData(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnIsolatedMarginTierData(sendSignedRequest(ISOLATED_MARGIN_TIER_DATA_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol, extraParams),
                GET_METHOD), format);
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
         * Any params required
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
         * Any params required
         *
         * @return {@link #transferType} instance as {@link String}
         **/
        @Override
        public String toString() {
            return String.valueOf(transferType);
        }

    }

}