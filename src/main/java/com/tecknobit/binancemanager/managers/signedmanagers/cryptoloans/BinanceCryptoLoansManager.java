package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;

import java.io.IOException;

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

    
}
