package com.tecknobit.binancemanager.managers.signedmanagers.viploans;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanOngoingOrders.VIPLoanOrder;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceVipLoansManager} class is useful to manage vip loans endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loans-endpoints">
 * Vip Loans Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see Manager
 */
public class BinanceVipLoansManager extends BinanceSignedManager {

    /**
     * {@code VIP_ONGOING_ORDERS_ENDPOINT} is constant for VIP_ONGOING_ORDERS_ENDPOINT's endpoint
     */
    public static final String VIP_ONGOING_ORDERS_ENDPOINT = "/sapi/v1/loan/vip/ongoing/orders";

    /**
     * {@code VIP_REPAY_ENDPOINT} is constant for VIP_REPAY_ENDPOINT's endpoint
     */
    public static final String VIP_REPAY_ENDPOINT = "/sapi/v1/loan/vip/repay";

    /**
     * {@code VIP_REPAY_HISTORY_ENDPOINT} is constant for VIP_REPAY_HISTORY_ENDPOINT's endpoint
     */
    public static final String VIP_REPAY_HISTORY_ENDPOINT = VIP_REPAY_ENDPOINT + "/history";

    /**
     * {@code VIP_COLLATERAL_ACCOUNT_ENDPOINT} is constant for VIP_COLLATERAL_ACCOUNT_ENDPOINT's endpoint
     */
    public static final String VIP_COLLATERAL_ACCOUNT_ENDPOINT = "/sapi/v1/loan/vip/collateral/account";

    /**
     * {@code VIP_LOAN_BORROW_ENDPOINT} is constant for VIP_LOAN_BORROW_ENDPOINT's endpoint
     */
    public static final String VIP_LOAN_BORROW_ENDPOINT = "/sapi/v1/loan/vip/borrow";

    /**
     * {@code VIP_LOANABLE_DATA_ENDPOINT} is constant for VIP_LOANABLE_DATA_ENDPOINT's endpoint
     */
    public static final String VIP_LOANABLE_DATA_ENDPOINT = "/sapi/v1/loan/vip/loanable/data";

    /**
     * {@code VIP_COLLATERAL_DATA_ENDPOINT} is constant for VIP_COLLATERAL_DATA_ENDPOINT's endpoint
     */
    public static final String VIP_COLLATERAL_DATA_ENDPOINT = "/sapi/v1/loan/vip/collateral/data";

    /**
     * {@code VIP_REQUEST_DATA_ENDPOINT} is constant for VIP_REQUEST_DATA_ENDPOINT's endpoint
     */
    public static final String VIP_REQUEST_DATA_ENDPOINT = "/sapi/v1/loan/vip/request/data";

    /**
     * Constructor to init a {@link BinanceVipLoansManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceVipLoansManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceVipLoansManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceVipLoansManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceVipLoansManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceVipLoansManager(String baseEndpoint, int timeout, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceVipLoansManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceVipLoansManager(String baseEndpoint, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceVipLoansManager} <br>
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
     */
    public BinanceVipLoansManager() {
        super();
    }

    /**
     * Request to get the VIP loan ongoing orders <br>
     * No-any params required
     *
     * @return VIP loan ongoing orders as {@link VIPLoanOngoingOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-ongoing-orders-user_data">
     * Get VIP Loan Ongoing Orders (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/ongoing/orders")
    public VIPLoanOngoingOrders getVIPLoanOngoingOrders() throws Exception {
        return getVIPLoanOngoingOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get the VIP loan ongoing orders
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return VIP loan ongoing orders as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-ongoing-orders-user_data">
     * Get VIP Loan Ongoing Orders (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/ongoing/orders")
    public <T> T getVIPLoanOngoingOrders(ReturnFormat format) throws Exception {
        return getVIPLoanOngoingOrders(null, format);
    }

    /**
     * Request to get the VIP loan ongoing orders
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "collateralAccountId"} -> collateral account identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return VIP loan ongoing orders as {@link VIPLoanOngoingOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-ongoing-orders-user_data">
     * Get VIP Loan Ongoing Orders (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/ongoing/orders")
    public VIPLoanOngoingOrders getVIPLoanOngoingOrders(Params extraParams) throws Exception {
        return getVIPLoanOngoingOrders(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the VIP loan ongoing orders
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "collateralAccountId"} -> collateral account identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return VIP loan ongoing orders as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-ongoing-orders-user_data">
     * Get VIP Loan Ongoing Orders (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/ongoing/orders")
    public <T> T getVIPLoanOngoingOrders(Params extraParams, ReturnFormat format) throws Exception {
        String ordersResponse = sendGetRequest(VIP_ONGOING_ORDERS_ENDPOINT, createTimestampPayload(extraParams), apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(ordersResponse);
            case LIBRARY_OBJECT:
                return (T) new VIPLoanOngoingOrders(new JSONObject(ordersResponse));
            default:
                return (T) ordersResponse;
        }
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param order:  order to execute the VIP loan repay
     * @param amount: amount of the VIP loan repay
     * @return VIP loan repay as {@link VIPLoanRepay} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public VIPLoanRepay execVIPLoanRepay(VIPLoanOrder order, double amount) throws Exception {
        return execVIPLoanRepay(order.getOrderId(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param order:  order to execute the VIP loan repay
     * @param amount: amount of the VIP loan repay
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return VIP loan repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public <T> T execVIPLoanRepay(VIPLoanOrder order, double amount, ReturnFormat format) throws Exception {
        return execVIPLoanRepay(order.getOrderId(), amount, format);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param orderId: order identifier to execute the VIP loan repay
     * @param amount:  amount of the VIP loan repay
     * @return VIP loan repay as {@link VIPLoanRepay} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public VIPLoanRepay execVIPLoanRepay(long orderId, double amount) throws Exception {
        return execVIPLoanRepay(orderId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param orderId: order identifier to execute the VIP loan repay
     * @param amount:  amount of the VIP loan repay
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return VIP loan repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public <T> T execVIPLoanRepay(long orderId, double amount, ReturnFormat format) throws Exception {
        return execVIPLoanRepay(orderId, amount, -1, format);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param order:      order to execute the VIP loan repay
     * @param amount:     amount of the VIP loan repay
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return VIP loan repay as {@link VIPLoanRepay} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public VIPLoanRepay execVIPLoanRepay(VIPLoanOrder order, double amount, long recvWindow) throws Exception {
        return execVIPLoanRepay(order.getOrderId(), amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param order:      order to execute the VIP loan repay
     * @param amount:     amount of the VIP loan repay
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return VIP loan repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public <T> T execVIPLoanRepay(VIPLoanOrder order, double amount, long recvWindow, ReturnFormat format) throws Exception {
        return execVIPLoanRepay(order.getOrderId(), amount, recvWindow, format);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param orderId:    order identifier to execute the VIP loan repay
     * @param amount:     amount of the VIP loan repay
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return VIP loan repay as {@link VIPLoanRepay} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public VIPLoanRepay execVIPLoanRepay(long orderId, double amount, long recvWindow) throws Exception {
        return execVIPLoanRepay(orderId, amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a VIP loan repay
     *
     * @param orderId:    order identifier to execute the VIP loan repay
     * @param amount:     amount of the VIP loan repay
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return VIP loan repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
     * VIP Loan Repay (TRADE)</a>
     */
    @Returner
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/repay")
    public <T> T execVIPLoanRepay(long orderId, double amount, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("orderId", orderId);
        payload.addParam("amount", amount);
        payload.addParam("timestamp", getServerTime());
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String repayResponse = sendPostRequest(VIP_REPAY_ENDPOINT, payload, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(repayResponse);
            case LIBRARY_OBJECT:
                return (T) new VIPLoanRepay(new JSONObject(repayResponse));
            default:
                return (T) repayResponse;
        }
    }

    /**
     * Request to get the VIP loan repayment history <br>
     * No-any params required
     *
     * @return VIP loan repayment history as {@link VIPLoanRepaymentHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
     * Get VIP Loan Repayment History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/repay/history")
    public VIPLoanRepaymentHistory getVIPLoanRepaymentHistory() throws Exception {
        return getVIPLoanRepaymentHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the VIP loan repayment history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return VIP loan repayment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
     * Get VIP Loan Repayment History (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/repay/history")
    public <T> T getVIPLoanRepaymentHistory(ReturnFormat format) throws Exception {
        return getVIPLoanRepaymentHistory(null, format);
    }

    /**
     * Request to get the VIP loan repayment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return VIP loan repayment history as {@link VIPLoanRepaymentHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
     * Get VIP Loan Repayment History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/repay/history")
    public VIPLoanRepaymentHistory getVIPLoanRepaymentHistory(Params extraParams) throws Exception {
        return getVIPLoanRepaymentHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the VIP loan repayment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return VIP loan repayment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
     * Get VIP Loan Repayment History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/repay/history")
    public <T> T getVIPLoanRepaymentHistory(Params extraParams, ReturnFormat format) throws Exception {
        String historyResponse = sendGetRequest(VIP_REPAY_HISTORY_ENDPOINT, createTimestampPayload(extraParams), apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new VIPLoanRepaymentHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to check locked value of VIP collateral account<br>
     * No-any params required
     *
     * @return value of VIP collateral account as {@link LockedValuesList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-locked-value-of-vip-collateral-account-user_data">
     * Check Locked Value of VIP Collateral Account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "6000(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/account")
    public LockedValuesList checkCollateralAccountLockedValue() throws Exception {
        return checkCollateralAccountLockedValue(LIBRARY_OBJECT);
    }

    /**
     * Request to check locked value of VIP collateral account
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return value of VIP collateral account as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-locked-value-of-vip-collateral-account-user_data">
     * Check Locked Value of VIP Collateral Account (USER_DATA)</a>
     */
    @RequestWeight(weight = "6000(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/account")
    public <T> T checkCollateralAccountLockedValue(ReturnFormat format) throws Exception {
        return checkCollateralAccountLockedValue(null, format);
    }

    /**
     * Request to check locked value of VIP collateral account
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "collateralAccountId"} -> collateral account identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return value of VIP collateral account as {@link LockedValuesList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-locked-value-of-vip-collateral-account-user_data">
     * Check Locked Value of VIP Collateral Account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "6000(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/account")
    public LockedValuesList checkCollateralAccountLockedValue(Params extraParams) throws Exception {
        return checkCollateralAccountLockedValue(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to check locked value of VIP collateral account
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "collateralAccountId"} -> collateral account identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return value of VIP collateral account as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-locked-value-of-vip-collateral-account-user_data">
     * Check Locked Value of VIP Collateral Account (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "6000(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/account")
    public <T> T checkCollateralAccountLockedValue(Params extraParams, ReturnFormat format) throws Exception {
        String lockedValuesResponse = sendGetRequest(VIP_COLLATERAL_ACCOUNT_ENDPOINT, createTimestampPayload(extraParams),
                apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(lockedValuesResponse);
            case LIBRARY_OBJECT:
                return (T) new LockedValuesList(new JSONObject(lockedValuesResponse));
            default:
                return (T) lockedValuesResponse;
        }
    }

    /**
     * Request to execute a VIP loan borrow
     *
     * @param accountId:           loan account identifier
     * @param loanCoin:            loan coin value
     * @param loanAmount:          loan amount value
     * @param collateralAccountId: collateral account identifier
     * @param collateralCoin:      collateral coin value
     * @param loanTerm:            30 or 60
     * @return VIP loan borrow as {@link VIPLoanBorrow} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-borrow-trade">
     * VIP Loan Borrow (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/borrow")
    public VIPLoanBorrow execVIPLoanBorrow(long accountId, String loanCoin, double loanAmount, String collateralAccountId,
                                           String collateralCoin, int loanTerm) throws Exception {
        return execVIPLoanBorrow(accountId, loanCoin, loanAmount, collateralAccountId, collateralCoin, loanTerm,
                LIBRARY_OBJECT);
    }

    /**
     * Request to execute a VIP loan borrow
     *
     * @param accountId:           loan account identifier
     * @param loanCoin:            loan coin value
     * @param loanAmount:          loan amount value
     * @param collateralAccountId: collateral account identifier
     * @param collateralCoin:      collateral coin value
     * @param loanTerm:            30 or 60
     * @param format:              return type formatter -> {@link ReturnFormat}
     * @return VIP loan borrow as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-borrow-trade">
     * VIP Loan Borrow (TRADE)</a>
     */
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/borrow")
    public <T> T execVIPLoanBorrow(long accountId, String loanCoin, double loanAmount, String collateralAccountId,
                                   String collateralCoin, int loanTerm, ReturnFormat format) throws Exception {
        return execVIPLoanBorrow(accountId, loanCoin, loanAmount, collateralAccountId, collateralCoin, loanTerm, -1,
                format);
    }

    /**
     * Request to execute a VIP loan borrow
     *
     * @param accountId:           loan account identifier
     * @param loanCoin:            loan coin value
     * @param loanAmount:          loan amount value
     * @param collateralAccountId: collateral account identifier
     * @param collateralCoin:      collateral coin value
     * @param loanTerm:            30 or 60
     * @param recvWindow:          request is valid for in ms, must be less than 60000
     * @return VIP loan borrow as {@link VIPLoanBorrow} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-borrow-trade">
     * VIP Loan Borrow (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/borrow")
    public VIPLoanBorrow execVIPLoanBorrow(long accountId, String loanCoin, double loanAmount, String collateralAccountId,
                                           String collateralCoin, int loanTerm, long recvWindow) throws Exception {
        return execVIPLoanBorrow(accountId, loanCoin, loanAmount, collateralAccountId, collateralCoin, loanTerm,
                recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a VIP loan borrow
     *
     * @param accountId:           loan account identifier
     * @param loanCoin:            loan coin value
     * @param loanAmount:          loan amount value
     * @param collateralAccountId: collateral account identifier
     * @param collateralCoin:      collateral coin value
     * @param loanTerm:            30 or 60
     * @param recvWindow:          request is valid for in ms, must be less than 60000
     * @param format:              return type formatter -> {@link ReturnFormat}
     * @return VIP loan borrow as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-borrow-trade">
     * VIP Loan Borrow (TRADE)</a>
     */
    @Returner
    @RequestWeight(weight = "6000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/loan/vip/borrow")
    public <T> T execVIPLoanBorrow(long accountId, String loanCoin, double loanAmount, String collateralAccountId,
                                   String collateralCoin, int loanTerm, long recvWindow,
                                   ReturnFormat format) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("accountId", accountId);
        payload.addParam("loanCoin", loanCoin);
        payload.addParam("loanAmount", loanAmount);
        payload.addParam("collateralAccountId", collateralAccountId);
        payload.addParam("collateralCoin", collateralCoin);
        payload.addParam("loanTerm", loanAmount);
        JSONObject response = new JSONObject(sendPostSignedRequest(VIP_LOAN_BORROW_ENDPOINT, payload));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new VIPLoanBorrow(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value<br>
     * No-any params required
     *
     * @return interest rate and borrow limit of loanable assets as {@link VIPLoanableAssetsData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-loanable-assets-data-user_data">
     * Get Loanable Assets Data (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/loanable/data")
    public VIPLoanableAssetsData getLoanableAssetsData() throws Exception {
        return getLoanableAssetsData(LIBRARY_OBJECT);
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return interest rate and borrow limit of loanable assets as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-loanable-assets-data-user_data">
     * Get Loanable Assets Data (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/loanable/data")
    public <T> T getLoanableAssetsData(ReturnFormat format) throws Exception {
        return getLoanableAssetsData(null, format);
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "vipLevel"} -> vip level value - [INT, default user's vip level]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return interest rate and borrow limit of loanable assets as {@link VIPLoanableAssetsData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-loanable-assets-data-user_data">
     * Get Loanable Assets Data (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/loanable/data")
    public VIPLoanableAssetsData getLoanableAssetsData(Params queryParams) throws Exception {
        return getLoanableAssetsData(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "vipLevel"} -> vip level value - [INT, default user's vip level]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return interest rate and borrow limit of loanable assets as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-loanable-assets-data-user_data">
     * Get Loanable Assets Data (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/loanable/data")
    public <T> T getLoanableAssetsData(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetRequest(VIP_LOANABLE_DATA_ENDPOINT,
                createTimestampPayload(queryParams), apiKey));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new VIPLoanableAssetsData(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get collateral asset data <br>
     * No-any params required
     *
     * @return collateral asset data as {@link VIPLoanData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-asset-data-user_data">
     * Get Collateral Asset Data (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/data")
    public VIPLoanData getCollateralAssetData() throws Exception {
        return getCollateralAssetData(LIBRARY_OBJECT);
    }

    /**
     * Request to get collateral asset data
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return loan data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-asset-data-user_data">
     * Get Collateral Asset Data (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/data")
    public <T> T getCollateralAssetData(ReturnFormat format) throws Exception {
        return getCollateralAssetData(null, format);
    }

    /**
     * Request to get collateral asset data
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return collateral asset data as {@link VIPLoanData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-asset-data-user_data">
     * Get Collateral Asset Data (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/data")
    public VIPLoanData getCollateralAssetData(Params queryParams) throws Exception {
        return getCollateralAssetData(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get collateral asset data
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return loan data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-asset-data-user_data">
     * Get Collateral Asset Data (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/collateral/data")
    public <T> T getCollateralAssetData(Params queryParams, ReturnFormat format) throws Exception {
        return returnLoanData(sendGetRequest(VIP_COLLATERAL_DATA_ENDPOINT, createTimestampPayload(queryParams), apiKey),
                format);
    }

    /**
     * Request to get application status <br>
     * No-any params required
     *
     * @return collateral asset data as {@link VIPLoanData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-application-status-user_data">
     * Query Application Status (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/request/data")
    public VIPLoanData getApplicationStatus() throws Exception {
        return getApplicationStatus(LIBRARY_OBJECT);
    }

    /**
     * Request to get application status
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return loan data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-application-status-user_data">
     * Query Application Status (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/request/data")
    public <T> T getApplicationStatus(ReturnFormat format) throws Exception {
        return getApplicationStatus(null, format);
    }

    /**
     * Request to get application status
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "current"} -> currently querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return collateral asset data as {@link VIPLoanData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-application-status-user_data">
     * Query Application Status (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "400(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/request/data")
    public VIPLoanData getApplicationStatus(Params queryParams) throws Exception {
        return getApplicationStatus(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get application status
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "current"} -> currently querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return loan data as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-application-status-user_data">
     * Query Application Status (USER_DATA)</a>
     */
    @RequestWeight(weight = "400(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/loan/vip/request/data")
    public <T> T getApplicationStatus(Params queryParams, ReturnFormat format) throws Exception {
        return returnLoanData(sendGetRequest(VIP_REQUEST_DATA_ENDPOINT, createTimestampPayload(queryParams), apiKey),
                format);
    }

    /**
     * Method to create a loan data
     *
     * @param loanResponse: obtained from Binance's response
     * @param format:       return type formatter -> {@link ReturnFormat}
     * @return loan data as {@code "format"} defines
     */
    @Returner
    private <T> T returnLoanData(String loanResponse, ReturnFormat format) {
        JSONObject response = new JSONObject(loanResponse);
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new VIPLoanData(response);
            default -> (T) loanResponse;
        };
    }

}
