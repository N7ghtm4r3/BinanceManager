package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin;


import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records.BankruptcyLoanAmount;
import com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records.CollateralRate;
import com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records.PortfolioMarginAccountInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
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
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
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

    public PortfolioMarginAccountInfo getPortfolioMarginAccountInfo() throws Exception {
        return getPortfolioMarginAccountInfo(LIBRARY_OBJECT);
    }

    public <T> T getPortfolioMarginAccountInfo(ReturnFormat format) throws Exception {
        return getPortfolioMarginAccountInfo(-1, LIBRARY_OBJECT);
    }

    public PortfolioMarginAccountInfo getPortfolioMarginAccountInfo(long recvWindow) throws Exception {
        return getPortfolioMarginAccountInfo(recvWindow, LIBRARY_OBJECT);
    }

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

    public ArrayList<CollateralRate> getPortfolioMarginCollateralRate() throws Exception {
        return getPortfolioMarginCollateralRate(LIBRARY_OBJECT);
    }

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

    public BankruptcyLoanAmount getPortfolioMarginBankruptcyLoanAmount() throws Exception {
        return getPortfolioMarginBankruptcyLoanAmount(LIBRARY_OBJECT);
    }

    public <T> T getPortfolioMarginBankruptcyLoanAmount(ReturnFormat format) throws Exception {
        return getPortfolioMarginBankruptcyLoanAmount(-1, format);
    }

    public BankruptcyLoanAmount getPortfolioMarginBankruptcyLoanAmount(long recvWindow) throws Exception {
        return getPortfolioMarginBankruptcyLoanAmount(recvWindow, LIBRARY_OBJECT);
    }

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

    public long marginBankruptcyLoanRepay() throws Exception {
        return Long.parseLong(marginBankruptcyLoanRepay(LIBRARY_OBJECT));
    }

    public <T> T marginBankruptcyLoanRepay(ReturnFormat format) throws Exception {
        return marginBankruptcyLoanRepay(-1, format);
    }

    public long marginBankruptcyLoanRepay(long recvWindow) throws Exception {
        return Long.parseLong(marginBankruptcyLoanRepay(recvWindow, LIBRARY_OBJECT));
    }

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

    public String createQuery(long recvWindow) {
        String query = getTimestampParam();
        if (recvWindow != -1)
            query += "&recvWindow=" + recvWindow;
        return query;
    }

}
