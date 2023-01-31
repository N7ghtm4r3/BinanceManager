package com.tecknobit.binancemanager.managers.signedmanagers.fiat;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatOperations;
import com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatPayments;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.binancemanager.constants.EndpointsList.FIAT_ORDERS_ENDPOINT;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceFiatManager} class is useful to manage all fiat endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fiat-endpoints">
 * Fiat endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceFiatManager extends BinanceSignedManager {

    /**
     * Constructor to init a {@link BinanceFiatManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFiatManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFiatManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceFiatManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceFiatManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceFiatManager} <br>
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
    public BinanceFiatManager() {
        super();
    }

    public FiatOperations getDepositWithdrawHistory(int type) throws Exception {
        return getDepositWithdrawHistory(type, LIBRARY_OBJECT);
    }

    public <T> T getDepositWithdrawHistory(int type, ReturnFormat format) throws Exception {
        return returnFiatOperations(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, getTimestampParam()
                + "&transactionType=" + type), format);
    }

    public FiatOperations getDepositWithdrawHistory(int type, Params extraParams) throws Exception {
        return getDepositWithdrawHistory(type, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getDepositWithdrawHistory(int type, Params extraParams,
                                           ReturnFormat format) throws Exception {
        extraParams.addParam("transactionType", type);
        extraParams.addParam("timestamp", getServerTime());
        return returnFiatOperations(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a fiat operations list
     *
     * @param fiatOperationsResponse: obtained from Binance's response
     * @param format:                 return type formatter -> {@link ReturnFormat}
     * @return fiat operations list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnFiatOperations(String fiatOperationsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(fiatOperationsResponse);
            case LIBRARY_OBJECT:
                return (T) new FiatOperations(new JSONObject(fiatOperationsResponse));
            default:
                return (T) fiatOperationsResponse;
        }
    }

    public FiatPayments getPaymentsHistory(int type) throws Exception {
        return getPaymentsHistory(type, LIBRARY_OBJECT);
    }

    public <T> T getPaymentsHistory(int type, ReturnFormat format) throws Exception {
        return returnFiatPayments(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, getTimestampParam()
                + "&transactionType=" + type), format);
    }

    public FiatPayments getPaymentsHistory(int type, Params extraParams) throws Exception {
        return getPaymentsHistory(type, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getPaymentsHistory(int type, Params extraParams,
                                    ReturnFormat format) throws Exception {
        extraParams.addParam("transactionType", type);
        extraParams.addParam("timestamp", getServerTime());
        return returnFiatPayments(sendGetSignedRequest(FIAT_ORDERS_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a fiat payments list
     *
     * @param fiatPaymentsResponse: obtained from Binance's response
     * @param format:               return type formatter -> {@link ReturnFormat}
     * @return fiat payments list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnFiatPayments(String fiatPaymentsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(fiatPaymentsResponse);
            case LIBRARY_OBJECT:
                return (T) new FiatPayments(new JSONObject(fiatPaymentsResponse));
            default:
                return (T) fiatPaymentsResponse;
        }
    }

}
