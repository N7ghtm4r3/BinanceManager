package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanAdjustLTV.LoanAdjustDirection;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanIncome.LoanType;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanRepay.LoanRepayType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceCryptoLoansManager} class is useful to manage crypto loans endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#crypto-loans-endpoints">
 * Crypto Loans Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceCryptoLoansManager extends BinanceSignedManager {

    /**
     * {@code LOAN_INCOME_ENDPOINT} is constant for LOAN_INCOME_ENDPOINT's endpoint
     **/
    public static final String LOAN_INCOME_ENDPOINT = "/sapi/v1/loan/income";

    /**
     * {@code LOAN_BORROW_ENDPOINT} is constant for LOAN_BORROW_ENDPOINT's endpoint
     **/
    public static final String LOAN_BORROW_ENDPOINT = "/sapi/v1/loan/borrow";

    /**
     * {@code LOAN_BORROW_HISTORY_ENDPOINT} is constant for LOAN_BORROW_HISTORY_ENDPOINT's endpoint
     **/
    public static final String LOAN_BORROW_HISTORY_ENDPOINT = "/sapi/v1/loan/borrow/history";

    /**
     * {@code LOAN_ONGOING_ORDERS_ENDPOINT} is constant for LOAN_ONGOING_ORDERS_ENDPOINT's endpoint
     **/
    public static final String LOAN_ONGOING_ORDERS_ENDPOINT = "/sapi/v1/loan/ongoing/orders";

    /**
     * {@code LOAN_REPAY_ENDPOINT} is constant for LOAN_REPAY_ENDPOINT's endpoint
     **/
    public static final String LOAN_REPAY_ENDPOINT = "/sapi/v1/loan/repay";

    /**
     * {@code LOAN_REPAY_HISTORY_ENDPOINT} is constant for LOAN_REPAY_HISTORY_ENDPOINT's endpoint
     **/
    public static final String LOAN_REPAY_HISTORY_ENDPOINT = "/sapi/v1/loan/repay/history";

    /**
     * {@code LOAN_ADJUST_LTV_ENDPOINT} is constant for LOAN_ADJUST_LTV_ENDPOINT's endpoint
     **/
    public static final String LOAN_ADJUST_LTV_ENDPOINT = "/sapi/v1/loan/adjust/ltv";

    /**
     * {@code LOAN_ADJUSTMENT_HISTORY_ENDPOINT} is constant for LOAN_ADJUSTMENT_HISTORY_ENDPOINT's endpoint
     **/
    public static final String LOAN_ADJUSTMENT_HISTORY_ENDPOINT = "/sapi/v1/loan/ltv/adjustment/history";

    /**
     * {@code LOAN_LOANABLE_DATA_ENDPOINT} is constant for LOAN_LOANABLE_DATA_ENDPOINT's endpoint
     **/
    public static final String LOAN_LOANABLE_DATA_ENDPOINT = "/sapi/v1/loan/loanable/data";

    /**
     * {@code LOAN_COLLATERAL_DATA_ENDPOINT} is constant for LOAN_COLLATERAL_DATA_ENDPOINT's endpoint
     **/
    public static final String LOAN_COLLATERAL_DATA_ENDPOINT = "/sapi/v1/loan/collateral/data";

    /**
     * {@code LOAN_REPAY_COLLATERAL_RATE_ENDPOINT} is constant for LOAN_REPAY_COLLATERAL_RATE_ENDPOINT's endpoint
     **/
    public static final String LOAN_REPAY_COLLATERAL_RATE_ENDPOINT = "/sapi/v1/loan/repay/collateral/rate";

    /**
     * {@code LOAN_CUSTOMIZE_MARGIN_CALL_ENDPOINT} is constant for LOAN_CUSTOMIZE_MARGIN_CALL_ENDPOINT's endpoint
     **/
    public static final String LOAN_CUSTOMIZE_MARGIN_CALL_ENDPOINT = "/sapi/v1/loan/customize/margin_call";

    /**
     * Constructor to init a {@link BinanceCryptoLoansManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceCryptoLoansManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceCryptoLoansManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceCryptoLoansManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceCryptoLoansManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceCryptoLoansManager(String baseEndpoint, int timeout, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceCryptoLoansManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceCryptoLoansManager(String baseEndpoint, String apiKey,
                                     String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceCryptoLoansManager} <br>
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
    public BinanceCryptoLoansManager() {
        super();
    }

    /**
     * Request to get crypto loans income history<br>
     * No-any params required
     *
     * @return crypto loans income history as {@link ArrayList} of {@link CryptoLoanIncome} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-crypto-loans-income-history-user_data">
     * Get Crypto Loans Income History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/income")
    public ArrayList<CryptoLoanIncome> getCryptoLoansIncomeHistory() throws Exception {
        return getCryptoLoansIncomeHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get crypto loans income history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return crypto loans income history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-crypto-loans-income-history-user_data">
     * Get Crypto Loans Income History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/income")
    public <T> T getCryptoLoansIncomeHistory(ReturnFormat format) throws Exception {
        return getCryptoLoansIncomeHistory(null, format);
    }

    /**
     * Request to get crypto loans income history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> type of the loan to fetch, constants available {@link LoanType}
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return crypto loans income history as {@link ArrayList} of {@link CryptoLoanIncome} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-crypto-loans-income-history-user_data">
     * Get Crypto Loans Income History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/income")
    public ArrayList<CryptoLoanIncome> getCryptoLoansIncomeHistory(Params extraParams) throws Exception {
        return getCryptoLoansIncomeHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get crypto loans income history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> type of the loan to fetch, constants available {@link LoanType}
     *                                - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 20]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return crypto loans income history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-crypto-loans-income-history-user_data">
     * Get Crypto Loans Income History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/income")
    public <T> T getCryptoLoansIncomeHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetSignedRequest(LOAN_INCOME_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(historyResponse);
            case LIBRARY_OBJECT:
                ArrayList<CryptoLoanIncome> incomes = new ArrayList<>();
                JSONArray jIncomes = new JSONArray(historyResponse);
                for (int j = 0; j < jIncomes.length(); j++)
                    incomes.add(new CryptoLoanIncome(jIncomes.getJSONObject(j)));
                return (T) incomes;
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to execute a crypto loan borrow
     *
     * @param loanCoin:       loan coin of the borrow
     * @param collateralCoin: collateral coin of the borrow
     * @param loanTerm:       loan term of the borrow
     * @return crypto loan borrow as {@link CryptoLoanBorrow} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-crypto-loan-borrow-trade">
     * Borrow - Crypto Loan Borrow (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/borrow")
    public CryptoLoanBorrow execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm) throws Exception {
        return execCryptoLoanBorrow(loanCoin, collateralCoin, loanTerm, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a crypto loan borrow
     *
     * @param loanCoin:       loan coin of the borrow
     * @param collateralCoin: collateral coin of the borrow
     * @param loanTerm:       loan term of the borrow
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return crypto loan borrow as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-crypto-loan-borrow-trade">
     * Borrow - Crypto Loan Borrow (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/loan/borrow")
    public <T> T execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm,
                                      ReturnFormat format) throws Exception {
        return execCryptoLoanBorrow(loanCoin, collateralCoin, loanTerm, null, format);
    }

    /**
     * Request to execute a crypto loan borrow
     *
     * @param loanCoin:       loan coin of the borrow
     * @param collateralCoin: collateral coin of the borrow
     * @param loanTerm:       loan term of the borrow
     * @param extraParams:    additional params of the request, keys accepted are:
     *                        <ul>
     *                              <li>
     *                                   {@code "loanAmount"} -> mandatory when collateralAmount is empty - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                   {@code "collateralAmount"} -> mandatory when loanAmount is empty - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                        </ul>
     * @return crypto loan borrow as {@link CryptoLoanBorrow} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-crypto-loan-borrow-trade">
     * Borrow - Crypto Loan Borrow (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/borrow")
    public CryptoLoanBorrow execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm,
                                                 Params extraParams) throws Exception {
        return execCryptoLoanBorrow(loanCoin, collateralCoin, loanTerm, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a crypto loan borrow
     *
     * @param loanCoin:       loan coin of the borrow
     * @param collateralCoin: collateral coin of the borrow
     * @param loanTerm:       loan term of the borrow
     * @param extraParams:    additional params of the request, keys accepted are:
     *                        <ul>
     *                              <li>
     *                                   {@code "loanAmount"} -> mandatory when collateralAmount is empty - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                   {@code "collateralAmount"} -> mandatory when loanAmount is empty - [DECIMAL]
     *                              </li>
     *                              <li>
     *                                   {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                        </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return crypto loan borrow as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-crypto-loan-borrow-trade">
     * Borrow - Crypto Loan Borrow (TRADE)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/loan/borrow")
    public <T> T execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm, Params extraParams,
                                      ReturnFormat format) throws Exception {
        Params payload = createCollateralPayload(loanCoin, collateralCoin);
        if (extraParams == null)
            extraParams = payload;
        else
            extraParams.mergeParams(payload);
        extraParams.addParam("loanTerm", loanTerm);
        String borrowResponse = sendPostRequest(LOAN_BORROW_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(borrowResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanBorrow(new JSONObject(borrowResponse));
            default:
                return (T) borrowResponse;
        }
    }

    /**
     * Request to get loan borrow history<br>
     * No-any params required
     *
     * @return loan borrow history as {@link CryptoLoanBorrowHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-borrow-history-user_data">
     * Borrow - Get Loan Borrow History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/borrow/history")
    public CryptoLoanBorrowHistory getLoanBorrowHistory() throws Exception {
        return getLoanBorrowHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get loan borrow history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return loan borrow history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-borrow-history-user_data">
     * Borrow - Get Loan Borrow History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/borrow/history")
    public <T> T getLoanBorrowHistory(ReturnFormat format) throws Exception {
        return getLoanBorrowHistory(null, format);
    }

    /**
     * Request to get loan borrow history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return loan borrow history as {@link CryptoLoanBorrowHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-borrow-history-user_data">
     * Borrow - Get Loan Borrow History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/borrow/history")
    public CryptoLoanBorrowHistory getLoanBorrowHistory(Params extraParams) throws Exception {
        return getLoanBorrowHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get loan borrow history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return loan borrow history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-borrow-history-user_data">
     * Borrow - Get Loan Borrow History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/borrow/history")
    public <T> T getLoanBorrowHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetRequest(LOAN_BORROW_HISTORY_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanBorrowHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get loan ongoing orders <br>
     * No-any params required
     *
     * @return loan ongoing orders as {@link CryptoLoanOngoingOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-ongoing-orders-user_data">
     * Borrow - Get Loan Ongoing Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/ongoing/orders")
    public CryptoLoanOngoingOrders getLoanOngoingOrders() throws Exception {
        return getLoanOngoingOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get loan ongoing orders
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return loan ongoing orders as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-ongoing-orders-user_data">
     * Borrow - Get Loan Ongoing Orders (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/ongoing/orders")
    public <T> T getLoanOngoingOrders(ReturnFormat format) throws Exception {
        return getLoanOngoingOrders(null, format);
    }

    /**
     * Request to get loan ongoing orders
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return loan ongoing orders as {@link CryptoLoanOngoingOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-ongoing-orders-user_data">
     * Borrow - Get Loan Ongoing Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/ongoing/orders")
    public CryptoLoanOngoingOrders getLoanOngoingOrders(Params extraParams) throws Exception {
        return getLoanOngoingOrders(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get loan ongoing orders
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return loan ongoing orders as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-get-loan-ongoing-orders-user_data">
     * Borrow - Get Loan Ongoing Orders (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/ongoing/orders")
    public <T> T getLoanOngoingOrders(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String listResponse = sendGetRequest(LOAN_ONGOING_ORDERS_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanOngoingOrders(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to execute a crypto loan repay
     *
     * @param orderId: order identifier to execute to repay
     * @param amount:  amount to repay
     * @return crypto loan repay as {@link CryptoLoanRepay} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-crypto-loan-repay-trade">
     * Repay - Crypto Loan Repay (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/repay")
    public CryptoLoanRepay execCryptoLoanRepay(long orderId, double amount) throws Exception {
        return execCryptoLoanRepay(orderId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a crypto loan repay
     *
     * @param orderId: order identifier to execute to repay
     * @param amount:  amount to repay
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return crypto loan repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-crypto-loan-repay-trade">
     * Repay - Crypto Loan Repay (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/loan/repay")
    public <T> T execCryptoLoanRepay(long orderId, double amount, ReturnFormat format) throws Exception {
        return execCryptoLoanRepay(orderId, amount, null, format);
    }

    /**
     * Request to execute a crypto loan repay
     *
     * @param orderId:     order identifier to execute to repay
     * @param amount:      amount to repay
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the repay, constants available {@link LoanRepayType}
     *                                - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "collateralReturn"} ->
     *                                <ul>
     *                                     <li>
     *                                          {@code "true"} -> return extra collateral to spot account
     *                                     </li>
     *                                     <li>
     *                                          {@code "false"} -> keep extra collateral in the order
     *                                     </li>
     *                                </ul> - [BOOLEAN, default true]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return crypto loan repay as {@link CryptoLoanRepay} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-crypto-loan-repay-trade">
     * Repay - Crypto Loan Repay (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/repay")
    public CryptoLoanRepay execCryptoLoanRepay(long orderId, double amount, Params extraParams) throws Exception {
        return execCryptoLoanRepay(orderId, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a crypto loan repay
     *
     * @param orderId:     order identifier to execute to repay
     * @param amount:      amount to repay
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the repay, constants available {@link LoanRepayType}
     *                                - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "collateralReturn"} ->
     *                                <ul>
     *                                     <li>
     *                                          {@code "true"} -> return extra collateral to spot account
     *                                     </li>
     *                                     <li>
     *                                          {@code "false"} -> keep extra collateral in the order
     *                                     </li>
     *                                </ul> - [BOOLEAN, default true]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return crypto loan repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-crypto-loan-repay-trade">
     * Repay - Crypto Loan Repay (TRADE)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/loan/repay")
    public <T> T execCryptoLoanRepay(long orderId, double amount, Params extraParams, ReturnFormat format) throws Exception {
        Params payload = createLoanPayload(orderId, amount);
        if (extraParams == null)
            extraParams = payload;
        else
            extraParams.mergeParams(payload);
        String repayResponse = sendPostRequest(LOAN_REPAY_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(repayResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanRepay(new JSONObject(repayResponse));
            default:
                return (T) repayResponse;
        }
    }

    /**
     * Request to get loan repayment history<br>
     * No-any params required
     *
     * @return loan repayment history as {@link CryptoLoanRepaymentHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-get-loan-repayment-history-user_data">
     * Repay - Get Loan Repayment History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/history")
    public CryptoLoanRepaymentHistory getLoanRepaymentHistory() throws Exception {
        return getLoanRepaymentHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get loan repayment history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return loan repayment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-get-loan-repayment-history-user_data">
     * Repay - Get Loan Repayment History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/history")
    public <T> T getLoanRepaymentHistory(ReturnFormat format) throws Exception {
        return getLoanRepaymentHistory(null, format);
    }

    /**
     * Request to get loan repayment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return loan repayment history as {@link CryptoLoanRepaymentHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-get-loan-repayment-history-user_data">
     * Repay - Get Loan Repayment History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/history")
    public CryptoLoanRepaymentHistory getLoanRepaymentHistory(Params extraParams) throws Exception {
        return getLoanRepaymentHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get loan repayment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return loan repayment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-get-loan-repayment-history-user_data">
     * Repay - Get Loan Repayment History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/history")
    public <T> T getLoanRepaymentHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetRequest(LOAN_REPAY_HISTORY_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanRepaymentHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to execute a crypto loan adjust LTV
     *
     * @param orderId:   order identifier to execute to adjust LTV
     * @param amount:    amount to adjust LTV
     * @param direction: direction of the crypto loan adjust LTV
     * @return crypto loan adjust LTV as {@link CryptoLoanAdjustLTV} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-crypto-loan-adjust-ltv-trade">
     * Adjust LTV - Crypto Loan Adjust LTV (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/adjust/ltv")
    public CryptoLoanAdjustLTV execCryptoLoanAdjustLTV(long orderId, double amount,
                                                       LoanAdjustDirection direction) throws Exception {
        return execCryptoLoanAdjustLTV(orderId, amount, direction, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a crypto loan adjust LTV
     *
     * @param orderId:   order identifier to execute to adjust LTV
     * @param amount:    amount to adjust LTV
     * @param direction: direction of the crypto loan adjust LTV
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return crypto loan adjust LTV as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-crypto-loan-adjust-ltv-trade">
     * Adjust LTV - Crypto Loan Adjust LTV (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/loan/adjust/ltv")
    public <T> T execCryptoLoanAdjustLTV(long orderId, double amount, LoanAdjustDirection direction,
                                         ReturnFormat format) throws Exception {
        return execCryptoLoanAdjustLTV(orderId, amount, direction, -1, format);
    }

    /**
     * Request to execute a crypto loan adjust LTV
     *
     * @param orderId:    order identifier to execute to adjust LTV
     * @param amount:     amount to adjust LTV
     * @param direction:  direction of the crypto loan adjust LTV
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return crypto loan adjust LTV as {@link CryptoLoanAdjustLTV} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-crypto-loan-adjust-ltv-trade">
     * Adjust LTV - Crypto Loan Adjust LTV (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/adjust/ltv")
    public CryptoLoanAdjustLTV execCryptoLoanAdjustLTV(long orderId, double amount, LoanAdjustDirection direction,
                                                       long recvWindow) throws Exception {
        return execCryptoLoanAdjustLTV(orderId, amount, direction, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to execute a crypto loan adjust LTV
     *
     * @param orderId:    order identifier to execute to adjust LTV
     * @param amount:     amount to adjust LTV
     * @param direction:  direction of the crypto loan adjust LTV
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return crypto loan adjust LTV as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-crypto-loan-adjust-ltv-trade">
     * Adjust LTV - Crypto Loan Adjust LTV (TRADE)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/loan/adjust/ltv")
    public <T> T execCryptoLoanAdjustLTV(long orderId, double amount, LoanAdjustDirection direction, long recvWindow,
                                         ReturnFormat format) throws Exception {
        Params payload = createLoanPayload(orderId, amount);
        payload.addParam("direction", direction);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String adjustResponse = sendPostRequest(LOAN_ADJUST_LTV_ENDPOINT, payload, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(adjustResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanAdjustLTV(new JSONObject(adjustResponse));
            default:
                return (T) adjustResponse;
        }
    }

    /**
     * Method to create a loan payload
     *
     * @param orderId: id of the order of the loan
     * @param amount:  amount of the loan
     * @return payload as {@link Params}
     **/
    private Params createLoanPayload(long orderId, double amount) {
        Params payload = new Params();
        payload.addParam("orderId", orderId);
        payload.addParam("amount", amount);
        payload.addParam("timestamp", getServerTime());
        return payload;
    }

    /**
     * Request to get loan LTV adjustment history<br>
     * No-any params required
     *
     * @return loan LTV adjustment history as {@link LoanLTVAdjustmentHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-get-loan-ltv-adjustment-history-user_data">
     * Adjust LTV - Get Loan LTV Adjustment History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/ltv/adjustment/history")
    public LoanLTVAdjustmentHistory getLoanLTVAdjustmentHistory() throws Exception {
        return getLoanLTVAdjustmentHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get loan LTV adjustment history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return loan LTV adjustment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-get-loan-ltv-adjustment-history-user_data">
     * Adjust LTV - Get Loan LTV Adjustment History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/ltv/adjustment/history")
    public <T> T getLoanLTVAdjustmentHistory(ReturnFormat format) throws Exception {
        return getLoanLTVAdjustmentHistory(null, format);
    }

    /**
     * Request to get loan LTV adjustment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return loan LTV adjustment history as {@link LoanLTVAdjustmentHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-get-loan-ltv-adjustment-history-user_data">
     * Adjust LTV - Get Loan LTV Adjustment History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/ltv/adjustment/history")
    public CryptoLoanRepaymentHistory getLoanLTVAdjustmentHistory(Params extraParams) throws Exception {
        return getLoanLTVAdjustmentHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get loan LTV adjustment history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier from fetch the list - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> collateral coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current querying page, max 1000 - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return loan LTV adjustment history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-get-loan-ltv-adjustment-history-user_data">
     * Adjust LTV - Get Loan LTV Adjustment History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/ltv/adjustment/history")
    public <T> T getLoanLTVAdjustmentHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetRequest(LOAN_ADJUSTMENT_HISTORY_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new LoanLTVAdjustmentHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value <br>
     * No-any params required
     *
     * @return interest rate and borrow limit of loanable assets as {@link LoanableAssetsData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
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
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/loanable/data")
    public LoanableAssetsData getLoanableAssetsData() throws Exception {
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
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/loanable/data")
    public <T> T getLoanableAssetsData(ReturnFormat format) throws Exception {
        return getLoanableAssetsData(null, format);
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "vipLevel"} -> vip level value - [INT, default user's vip level]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return interest rate and borrow limit of loanable assets as {@link LoanableAssetsData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
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
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/loanable/data")
    public LoanableAssetsData getLoanableAssetsData(Params extraParams) throws Exception {
        return getLoanableAssetsData(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get interest rate and borrow limit of loanable assets. The borrow limit is shown in USD value
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "loanCoin"} -> loan coin from fetch the list - [STRING]
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
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/loanable/data")
    public <T> T getLoanableAssetsData(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String listResponse = sendGetRequest(LOAN_LOANABLE_DATA_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new LoanableAssetsData(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get LTV information and collateral limit of collateral assets. The collateral limit is shown in USD value <br>
     * No-any params required
     *
     * @return LTV information and collateral limit of collateral assets as {@link CollateralAssetsData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-assets-data-user_data">
     * Get Collateral Assets Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/collateral/data")
    public CollateralAssetsData getCollateralAssetsData() throws Exception {
        return getCollateralAssetsData(LIBRARY_OBJECT);
    }

    /**
     * Request to get LTV information and collateral limit of collateral assets. The collateral limit is shown in USD value
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return LTV information and collateral limit of collateral assets as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-assets-data-user_data">
     * Get Collateral Assets Data (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/collateral/data")
    public <T> T getCollateralAssetsData(ReturnFormat format) throws Exception {
        return getCollateralAssetsData(null, format);
    }

    /**
     * Request to get LTV information and collateral limit of collateral assets. The collateral limit is shown in USD value
     *
     * @param extraParams: additional params of the request, keys accepted are:
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
     * @return LTV information and collateral limit of collateral assets as {@link CollateralAssetsData} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-assets-data-user_data">
     * Get Collateral Assets Data (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/collateral/data")
    public CollateralAssetsData getCollateralAssetsData(Params extraParams) throws Exception {
        return getCollateralAssetsData(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get LTV information and collateral limit of collateral assets. The collateral limit is shown in USD value
     *
     * @param extraParams: additional params of the request, keys accepted are:
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
     * @return LTV information and collateral limit of collateral assets as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-assets-data-user_data">
     * Get Collateral Assets Data (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/collateral/data")
    public <T> T getCollateralAssetsData(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String listResponse = sendGetRequest(LOAN_COLLATERAL_DATA_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new CollateralAssetsData(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the rate of collateral coin / loan coin when using collateral repay, the rate will be valid
     * within 8 second
     *
     * @param loanCoin:       loan coin of the repayment
     * @param collateralCoin: collateral coin of the repayment
     * @param repayAmount:    amount of the repayment
     * @return the rate of collateral coin / loan coin when using collateral repay as {@link CollateralRepayRate} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-collateral-repay-rate-user_data">
     * Check Collateral Repay Rate (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/collateral/rate")
    public CollateralRepayRate checkCollateralRepayRate(String loanCoin, String collateralCoin,
                                                        double repayAmount) throws Exception {
        return checkCollateralRepayRate(loanCoin, collateralCoin, repayAmount, LIBRARY_OBJECT);
    }

    /**
     * Request to get the rate of collateral coin / loan coin when using collateral repay, the rate will be valid
     * within 8 second
     *
     * @param loanCoin:       loan coin of the repayment
     * @param collateralCoin: collateral coin of the repayment
     * @param repayAmount:    amount of the repayment
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return the rate of collateral coin / loan coin when using collateral repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-collateral-repay-rate-user_data">
     * Check Collateral Repay Rate (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/collateral/rate")
    public <T> T checkCollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount,
                                          ReturnFormat format) throws Exception {
        return checkCollateralRepayRate(loanCoin, collateralCoin, repayAmount, -1, format);
    }

    /**
     * Request to get the rate of collateral coin / loan coin when using collateral repay, the rate will be valid
     * within 8 second
     *
     * @param loanCoin:       loan coin of the repayment
     * @param collateralCoin: collateral coin of the repayment
     * @param repayAmount:    amount of the repayment
     * @param recvWindow:     request is valid for in ms, must be less than 60000
     * @return the rate of collateral coin / loan coin when using collateral repay as {@link CollateralRepayRate} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-collateral-repay-rate-user_data">
     * Check Collateral Repay Rate (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/collateral/rate")
    public CollateralRepayRate checkCollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount,
                                                        long recvWindow) throws Exception {
        return checkCollateralRepayRate(loanCoin, collateralCoin, repayAmount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the rate of collateral coin / loan coin when using collateral repay, the rate will be valid
     * within 8 second
     *
     * @param loanCoin:       loan coin of the repayment
     * @param collateralCoin: collateral coin of the repayment
     * @param repayAmount:    amount of the repayment
     * @param recvWindow:     request is valid for in ms, must be less than 60000
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return the rate of collateral coin / loan coin when using collateral repay as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-collateral-repay-rate-user_data">
     * Check Collateral Repay Rate (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/loan/repay/collateral/rate")
    public <T> T checkCollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount, long recvWindow,
                                          ReturnFormat format) throws Exception {
        Params payload = createCollateralPayload(loanCoin, collateralCoin);
        payload.addParam("repayAmount", repayAmount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String repayRateResponse = sendGetRequest(LOAN_REPAY_COLLATERAL_RATE_ENDPOINT, payload, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(repayRateResponse);
            case LIBRARY_OBJECT:
                return (T) new CollateralRepayRate(new JSONObject(repayRateResponse));
            default:
                return (T) repayRateResponse;
        }
    }

    /**
     * Method to create a collateral payload
     *
     * @param loanCoin:       loan coin of the collateral
     * @param collateralCoin: coin of the collateral
     * @return payload as {@link Params}
     **/
    private Params createCollateralPayload(String loanCoin, String collateralCoin) {
        Params payload = new Params();
        payload.addParam("loanCoin", loanCoin);
        payload.addParam("collateralCoin", collateralCoin);
        payload.addParam("timestamp", getServerTime());
        return payload;
    }

    /**
     * Request to customize margin call for ongoing orders only
     *
     * @param marginCall: new margin call value
     * @return customized margin call as {@link CryptoLoanCustomizeMarginCall} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#crypto-loan-customize-margin-call-trade">
     * Crypto Loan Customize Margin Call (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/customize/margin_call")
    public CryptoLoanCustomizeMarginCall customizeMarginCall(double marginCall) throws Exception {
        return customizeMarginCall(marginCall, LIBRARY_OBJECT);
    }

    /**
     * Request to customize margin call for ongoing orders only
     *
     * @param marginCall: new margin call value
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return customized margin call as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#crypto-loan-customize-margin-call-trade">
     * Crypto Loan Customize Margin Call (TRADE)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/loan/customize/margin_call")
    public <T> T customizeMarginCall(double marginCall, ReturnFormat format) throws Exception {
        return customizeMarginCall(marginCall, null, format);
    }

    /**
     * Request to customize margin call for ongoing orders only
     *
     * @param marginCall:  new margin call value
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> mandatory when collateralCoin is empty - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> mandatory when orderID is empty - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return customized margin call as {@link CryptoLoanCustomizeMarginCall} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#crypto-loan-customize-margin-call-trade">
     * Crypto Loan Customize Margin Call (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/loan/customize/margin_call")
    public CryptoLoanCustomizeMarginCall customizeMarginCall(double marginCall, Params extraParams) throws Exception {
        return customizeMarginCall(marginCall, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to customize margin call for ongoing orders only
     *
     * @param marginCall:  new margin call value
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> mandatory when collateralCoin is empty - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "collateralCoin"} -> mandatory when orderID is empty - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return customized margin call as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#crypto-loan-customize-margin-call-trade">
     * Crypto Loan Customize Margin Call (TRADE)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/loan/customize/margin_call")
    public <T> T customizeMarginCall(double marginCall, Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("marginCall", marginCall);
        extraParams.addParam("timestamp", getServerTime());
        String marginCallResponse = sendPostRequest(LOAN_CUSTOMIZE_MARGIN_CALL_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(marginCallResponse);
            case LIBRARY_OBJECT:
                return (T) new CryptoLoanCustomizeMarginCall(new JSONObject(marginCallResponse));
            default:
                return (T) marginCallResponse;
        }
    }

}
