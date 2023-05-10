package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin;


import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.RequestWeight;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinancePortfolioMarginManager} class is useful to portfolio margin endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-endpoints">
 * Portfolio Margin Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinancePortfolioMarginManager extends BinanceSignedManager {

    /**
     * {@code PORTFOLIO_ACCOUNT_ENDPOINT} is constant for PORTFOLIO_ACCOUNT_ENDPOINT's endpoint
     **/
    public static final String PORTFOLIO_ACCOUNT_ENDPOINT = "/sapi/v1/portfolio/account";

    /**
     * {@code PORTFOLIO_COLLATERAL_RATE_ENDPOINT} is constant for PORTFOLIO_COLLATERAL_RATE_ENDPOINT's endpoint
     **/
    public static final String PORTFOLIO_COLLATERAL_RATE_ENDPOINT = "/sapi/v1/portfolio/collateralRate";

    /**
     * {@code PORTFOLIO_PM_LOAN_ENDPOINT} is constant for PORTFOLIO_PM_LOAN_ENDPOINT's endpoint
     **/
    public static final String PORTFOLIO_PM_LOAN_ENDPOINT = "/sapi/v1/portfolio/pmLoan";

    /**
     * {@code PORTFOLIO_REPAY_ENDPOINT} is constant for PORTFOLIO_REPAY_ENDPOINT's endpoint
     **/
    public static final String PORTFOLIO_REPAY_ENDPOINT = "/sapi/v1/portfolio/repay";

    /**
     * {@code PORTFOLIO_INTEREST_HISTORY_ENDPOINT} is constant for PORTFOLIO_INTEREST_HISTORY_ENDPOINT's endpoint
     **/
    public static final String PORTFOLIO_INTEREST_HISTORY_ENDPOINT = "/sapi/v1/portfolio/interest-history";

    /**
     * {@code PORTFOLIO_INTEREST_RATE_ENDPOINT} is constant for PORTFOLIO_INTEREST_RATE_ENDPOINT's endpoint
     **/
    public static final String PORTFOLIO_INTEREST_RATE_ENDPOINT = "/sapi/v1/portfolio/interest-rate";

    /**
     * Constructor to init a {@link BinancePortfolioMarginManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinancePortfolioMarginManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                         String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePortfolioMarginManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinancePortfolioMarginManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                         String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePortfolioMarginManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinancePortfolioMarginManager(String baseEndpoint, int timeout, String apiKey,
                                         String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePortfolioMarginManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinancePortfolioMarginManager(String baseEndpoint, String apiKey,
                                         String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinancePortfolioMarginManager} <br>
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
    public BinancePortfolioMarginManager() {
        super();
    }

    /**
     * Request to get the portfolio margin account info <br>
     * No-any params required
     *
     * @return portfolio margin account info as {@link PortfolioMarginAccountInfo} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     * Get Portfolio Margin Account Info (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/account")
    public PortfolioMarginAccountInfo getPortfolioMarginAccountInfo() throws Exception {
        return getPortfolioMarginAccountInfo(LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin account info
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return portfolio margin account info as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     * Get Portfolio Margin Account Info (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/account")
    public <T> T getPortfolioMarginAccountInfo(ReturnFormat format) throws Exception {
        return getPortfolioMarginAccountInfo(-1, LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin account info
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return portfolio margin account info as {@link PortfolioMarginAccountInfo} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     * Get Portfolio Margin Account Info (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/account")
    public PortfolioMarginAccountInfo getPortfolioMarginAccountInfo(long recvWindow) throws Exception {
        return getPortfolioMarginAccountInfo(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin account info
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return portfolio margin account info as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     * Get Portfolio Margin Account Info (USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/account")
    public <T> T getPortfolioMarginAccountInfo(long recvWindow, ReturnFormat format) throws Exception {
        String accountInfo = sendGetSignedRequest(PORTFOLIO_ACCOUNT_ENDPOINT, createQuery(recvWindow));
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountInfo);
            case LIBRARY_OBJECT:
                return (T) new PortfolioMarginAccountInfo(new JSONObject(accountInfo));
            default:
                return (T) accountInfo;
        }
    }

    /**
     * Request to get the portfolio margin collateral rate <br>
     * No-any params required
     *
     * @return portfolio margin collateral rate as {@link ArrayList} of {@link CollateralRate} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     * Portfolio Margin Collateral Rate (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/collateralRate")
    public ArrayList<CollateralRate> getPortfolioMarginCollateralRate() throws Exception {
        return getPortfolioMarginCollateralRate(LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin collateral rate
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return portfolio margin collateral rate as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     * Portfolio Margin Collateral Rate (MARKET_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/collateralRate")
    public <T> T getPortfolioMarginCollateralRate(ReturnFormat format) throws Exception {
        String collateralRateResponse = sendGetRequest(PORTFOLIO_COLLATERAL_RATE_ENDPOINT, (String) null, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONArray(collateralRateResponse);
            case LIBRARY_OBJECT:
                ArrayList<CollateralRate> rates = new ArrayList<>();
                JSONArray jRates = new JSONArray(collateralRateResponse);
                for (int j = 0; j < jRates.length(); j++)
                    rates.add(new CollateralRate(jRates.getJSONObject(j)));
                return (T) rates;
            default:
                return (T) collateralRateResponse;
        }
    }

    /**
     * Request to get the portfolio margin bankruptcy loan amount <br>
     * No-any params required
     *
     * @return portfolio margin bankruptcy loan amount as {@link BankruptcyLoanAmount} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-amount-user_data">
     * Query Portfolio Margin Bankruptcy Loan Amount (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "500(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/pmLoan")
    public BankruptcyLoanAmount getPortfolioMarginBankruptcyLoanAmount() throws Exception {
        return getPortfolioMarginBankruptcyLoanAmount(LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin bankruptcy loan amount
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return portfolio margin bankruptcy loan amount as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-amount-user_data">
     * Query Portfolio Margin Bankruptcy Loan Amount (USER_DATA)</a>
     **/
    @RequestWeight(weight = "500(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/pmLoan")
    public <T> T getPortfolioMarginBankruptcyLoanAmount(ReturnFormat format) throws Exception {
        return getPortfolioMarginBankruptcyLoanAmount(-1, format);
    }

    /**
     * Request to get the portfolio margin bankruptcy loan amount
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return portfolio margin bankruptcy loan amount as {@link BankruptcyLoanAmount} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-amount-user_data">
     * Query Portfolio Margin Bankruptcy Loan Amount (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "500(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/pmLoan")
    public BankruptcyLoanAmount getPortfolioMarginBankruptcyLoanAmount(long recvWindow) throws Exception {
        return getPortfolioMarginBankruptcyLoanAmount(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin bankruptcy loan amount
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return portfolio margin bankruptcy loan amount as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-amount-user_data">
     * Query Portfolio Margin Bankruptcy Loan Amount (USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "500(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/pmLoan")
    public <T> T getPortfolioMarginBankruptcyLoanAmount(long recvWindow, ReturnFormat format) throws Exception {
        String loanAmountResponse = sendGetRequest(PORTFOLIO_PM_LOAN_ENDPOINT, createQuery(recvWindow), apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(loanAmountResponse);
            case LIBRARY_OBJECT:
                return (T) new BankruptcyLoanAmount(new JSONObject(loanAmountResponse));
            default:
                return (T) loanAmountResponse;
        }
    }

    /**
     * Request to repay portfolio margin bankruptcy loan <br>
     * No-any params required
     *
     * @return id of the operation as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-bankruptcy-loan-repay">
     * Portfolio Margin Bankruptcy Loan Repay</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/portfolio/repay")
    public long marginBankruptcyLoanRepay() throws Exception {
        return Long.parseLong(marginBankruptcyLoanRepay(LIBRARY_OBJECT));
    }

    /**
     * Request to repay portfolio margin bankruptcy loan
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return id of the operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-bankruptcy-loan-repay">
     * Portfolio Margin Bankruptcy Loan Repay</a>
     **/
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/portfolio/repay")
    public <T> T marginBankruptcyLoanRepay(ReturnFormat format) throws Exception {
        return marginBankruptcyLoanRepay(-1, format);
    }

    /**
     * Request to repay portfolio margin bankruptcy loan
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return id of the operation as long
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-bankruptcy-loan-repay">
     * Portfolio Margin Bankruptcy Loan Repay</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/portfolio/repay")
    public long marginBankruptcyLoanRepay(long recvWindow) throws Exception {
        return Long.parseLong(marginBankruptcyLoanRepay(recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to repay portfolio margin bankruptcy loan
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return id of the operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-bankruptcy-loan-repay">
     * Portfolio Margin Bankruptcy Loan Repay</a>
     **/
    @Returner
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/portfolio/repay")
    public <T> T marginBankruptcyLoanRepay(long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("timestamp", getServerTime());
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String loanRepayResponse = sendPostRequest(PORTFOLIO_REPAY_ENDPOINT, payload, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(loanRepayResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(JsonHelper.getLong(new JSONObject(loanRepayResponse), "tranId"));
            default:
                return (T) loanRepayResponse;
        }
    }

    /**
     * Method to create a query request string
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return query as {@link String}
     **/
    public String createQuery(long recvWindow) {
        String query = getTimestampParam();
        if (recvWindow != -1)
            query += "&recvWindow=" + recvWindow;
        return query;
    }

    /**
     * Request to get the user's portfolio margin interest history<br>
     * No-any params required
     *
     * @return portfolio margin interest history as {@link PortfolioMarginInterestHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-history-user_data">
     * Query Portfolio Margin Interest History(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-history")
    public PortfolioMarginInterestHistory getPortfolioMarginInterestHistory() throws Exception {
        return getPortfolioMarginInterestHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the user's portfolio margin interest history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return portfolio margin interest history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-history-user_data">
     * Query Portfolio Margin Interest History(USER_DATA)</a>
     **/
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-history")
    public <T> T getPortfolioMarginInterestHistory(ReturnFormat format) throws Exception {
        return getPortfolioMarginInterestHistory(null, format);
    }

    /**
     * Request to get the user's portfolio margin interest history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results per page, max 100
     *                                - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return portfolio margin interest history as {@link PortfolioMarginInterestHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-history-user_data">
     * Query Portfolio Margin Interest History(USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-history")
    public PortfolioMarginInterestHistory getPortfolioMarginInterestHistory(Params extraParams) throws Exception {
        return getPortfolioMarginInterestHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the user's portfolio margin interest history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results per page, max 100
     *                                - [INT, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return portfolio margin interest history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-history-user_data">
     * Query Portfolio Margin Interest History(USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-history")
    public <T> T getPortfolioMarginInterestHistory(Params extraParams, ReturnFormat format) throws Exception {
        String historyResponse = sendGetRequest(PORTFOLIO_INTEREST_HISTORY_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new PortfolioMarginInterestHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get the portfolio margin interest rate<br>
     * No-any params required
     *
     * @return portfolio margin interest rate as {@link ArrayList} of {@link PortfolioMarginInterestRate} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-rate-market_data">
     * Query Portfolio Margin Interest Rate (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-rate")
    public ArrayList<PortfolioMarginInterestRate> getPortfolioMarginInterestRate() throws Exception {
        return getPortfolioMarginInterestRate(LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin interest rate
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return portfolio margin interest rate as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-rate-market_data">
     * Query Portfolio Margin Interest Rate (MARKET_DATA)</a>
     **/
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-rate")
    public <T> T getPortfolioMarginInterestRate(ReturnFormat format) throws Exception {
        return getPortfolioMarginInterestRate(null, format);
    }

    /**
     * Request to get the portfolio margin interest rate
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return portfolio margin interest rate as {@link ArrayList} of {@link PortfolioMarginInterestRate} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-rate-market_data">
     * Query Portfolio Margin Interest Rate (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-rate")
    public ArrayList<PortfolioMarginInterestRate> getPortfolioMarginInterestRate(Params extraParams) throws Exception {
        return getPortfolioMarginInterestRate(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the portfolio margin interest rate
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return portfolio margin interest rate as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-interest-rate-market_data">
     * Query Portfolio Margin Interest Rate (MARKET_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "50(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/portfolio/interest-rate")
    public <T> T getPortfolioMarginInterestRate(Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetRequest(PORTFOLIO_COLLATERAL_RATE_ENDPOINT, extraParams, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<PortfolioMarginInterestRate> interestRates = new ArrayList<>();
                JSONArray jInterestRates = new JSONArray(listResponse);
                for (int j = 0; j < jInterestRates.length(); j++)
                    interestRates.add(new PortfolioMarginInterestRate(jInterestRates.getJSONObject(j)));
                return (T) interestRates;
            default:
                return (T) listResponse;
        }
    }

}
