package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanAdjustLTV.LoanAdjustDirection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    public ArrayList<CryptoLoanIncome> getCryptoLoansIncomeHistory() throws Exception {
        return getCryptoLoansIncomeHistory(LIBRARY_OBJECT);
    }

    public <T> T getCryptoLoansIncomeHistory(ReturnFormat format) throws Exception {
        return getCryptoLoansIncomeHistory(null, format);
    }

    public ArrayList<CryptoLoanIncome> getCryptoLoansIncomeHistory(Params extraParams) throws Exception {
        return getCryptoLoansIncomeHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public CryptoLoanBorrow execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm) throws Exception {
        return execCryptoLoanBorrow(loanCoin, collateralCoin, loanTerm, LIBRARY_OBJECT);
    }

    public <T> T execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm,
                                      ReturnFormat format) throws Exception {
        return execCryptoLoanBorrow(loanCoin, collateralCoin, loanTerm, null, format);
    }

    public CryptoLoanBorrow execCryptoLoanBorrow(String loanCoin, String collateralCoin, int loanTerm,
                                                 Params extraParams) throws Exception {
        return execCryptoLoanBorrow(loanCoin, collateralCoin, loanTerm, extraParams, LIBRARY_OBJECT);
    }

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

    public CryptoLoanBorrowHistory getLoanBorrowHistory() throws Exception {
        return getLoanBorrowHistory(LIBRARY_OBJECT);
    }

    public <T> T getLoanBorrowHistory(ReturnFormat format) throws Exception {
        return getLoanBorrowHistory(null, format);
    }

    public CryptoLoanBorrowHistory getLoanBorrowHistory(Params extraParams) throws Exception {
        return getLoanBorrowHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public CryptoLoanOngoingOrders getLoanOngoingOrders() throws Exception {
        return getLoanOngoingOrders(LIBRARY_OBJECT);
    }

    public <T> T getLoanOngoingOrders(ReturnFormat format) throws Exception {
        return getLoanOngoingOrders(null, format);
    }

    public CryptoLoanOngoingOrders getLoanOngoingOrders(Params extraParams) throws Exception {
        return getLoanOngoingOrders(extraParams, LIBRARY_OBJECT);
    }

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

    public CryptoLoanRepay execCryptoLoanRepay(long orderId, double amount) throws Exception {
        return execCryptoLoanRepay(orderId, amount, LIBRARY_OBJECT);
    }

    public <T> T execCryptoLoanRepay(long orderId, double amount, ReturnFormat format) throws Exception {
        return execCryptoLoanRepay(orderId, amount, null, format);
    }

    public CryptoLoanRepay execCryptoLoanRepay(long orderId, double amount, Params extraParams) throws Exception {
        return execCryptoLoanRepay(orderId, amount, extraParams, LIBRARY_OBJECT);
    }

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

    public CryptoLoanRepaymentHistory getLoanRepaymentHistory() throws Exception {
        return getLoanRepaymentHistory(LIBRARY_OBJECT);
    }

    public <T> T getLoanRepaymentHistory(ReturnFormat format) throws Exception {
        return getLoanRepaymentHistory(null, format);
    }

    public CryptoLoanRepaymentHistory getLoanRepaymentHistory(Params extraParams) throws Exception {
        return getLoanRepaymentHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public CryptoLoanAdjustLTV execCryptoLoanAdjustLTV(long orderId, double amount,
                                                       LoanAdjustDirection direction) throws Exception {
        return execCryptoLoanAdjustLTV(orderId, amount, direction, LIBRARY_OBJECT);
    }

    public <T> T execCryptoLoanAdjustLTV(long orderId, double amount, LoanAdjustDirection direction,
                                         ReturnFormat format) throws Exception {
        return execCryptoLoanAdjustLTV(orderId, amount, direction, -1, format);
    }

    public CryptoLoanAdjustLTV execCryptoLoanAdjustLTV(long orderId, double amount, LoanAdjustDirection direction,
                                                       long recvWindow) throws Exception {
        return execCryptoLoanAdjustLTV(orderId, amount, direction, recvWindow, LIBRARY_OBJECT);
    }

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

    private Params createLoanPayload(long orderId, double amount) {
        Params payload = new Params();
        payload.addParam("orderId", orderId);
        payload.addParam("amount", amount);
        payload.addParam("timestamp", getServerTime());
        return payload;
    }

    public LoanLTVAdjustmentHistory getLoanLTVAdjustmentHistory() throws Exception {
        return getLoanLTVAdjustmentHistory(LIBRARY_OBJECT);
    }

    public <T> T getLoanLTVAdjustmentHistory(ReturnFormat format) throws Exception {
        return getLoanLTVAdjustmentHistory(null, format);
    }

    public CryptoLoanRepaymentHistory getLoanLTVAdjustmentHistory(Params extraParams) throws Exception {
        return getLoanLTVAdjustmentHistory(extraParams, LIBRARY_OBJECT);
    }

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

    public LoanableAssetsData getLoanableAssetsData() throws Exception {
        return getLoanableAssetsData(LIBRARY_OBJECT);
    }

    public <T> T getLoanableAssetsData(ReturnFormat format) throws Exception {
        return getLoanableAssetsData(null, format);
    }

    public LoanableAssetsData getLoanableAssetsData(Params extraParams) throws Exception {
        return getLoanableAssetsData(extraParams, LIBRARY_OBJECT);
    }

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

    public CollateralAssetsData getCollateralAssetsData() throws Exception {
        return getCollateralAssetsData(LIBRARY_OBJECT);
    }

    public <T> T getCollateralAssetsData(ReturnFormat format) throws Exception {
        return getCollateralAssetsData(null, format);
    }

    public CollateralAssetsData getCollateralAssetsData(Params extraParams) throws Exception {
        return getCollateralAssetsData(extraParams, LIBRARY_OBJECT);
    }

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

    public CollateralRepayRate checkCollateralRepayRate(String loanCoin, String collateralCoin,
                                                        double repayAmount) throws Exception {
        return checkCollateralRepayRate(loanCoin, collateralCoin, repayAmount, LIBRARY_OBJECT);
    }

    public <T> T checkCollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount,
                                          ReturnFormat format) throws Exception {
        return checkCollateralRepayRate(loanCoin, collateralCoin, repayAmount, -1, format);
    }

    public CollateralRepayRate checkCollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount,
                                                        long recvWindow) throws Exception {
        return checkCollateralRepayRate(loanCoin, collateralCoin, repayAmount, recvWindow, LIBRARY_OBJECT);
    }

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

    private Params createCollateralPayload(String loanCoin, String collateralCoin) {
        Params payload = new Params();
        payload.addParam("loanCoin", loanCoin);
        payload.addParam("collateralCoin", collateralCoin);
        payload.addParam("timestamp", getServerTime());
        return payload;
    }

    public CryptoLoanCustomizeMarginCall customizeMarginCall(double marginCall) throws Exception {
        return customizeMarginCall(marginCall, LIBRARY_OBJECT);
    }

    public <T> T customizeMarginCall(double marginCall, ReturnFormat format) throws Exception {
        return customizeMarginCall(marginCall, null, format);
    }

    public CryptoLoanCustomizeMarginCall customizeMarginCall(double marginCall, Params extraParams) throws Exception {
        return customizeMarginCall(marginCall, extraParams, LIBRARY_OBJECT);
    }

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
