package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment;

import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;

import java.io.IOException;

/**
 * The {@code BinanceAutoInvestmentManager} class is useful to manage all {@code "Binance"}'s Auto-Investment Endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#auto-invest-endpoints">
 * Auto-Invest Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see Manager
 */
public class BinanceAutoInvestmentManager extends BinanceSignedManager {

    /**
     * {@code TARGET_ASSET_LIST_ENDPOINT} is constant for TARGET_ASSET_LIST_ENDPOINT's endpoint
     */
    public static final String TARGET_ASSET_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/target-asset/list";

    /**
     * {@code TARGET_ASSET_ROI_LIST_ENDPOINT} is constant for TARGET_ASSET_ROI_LIST_ENDPOINT's endpoint
     */
    public static final String TARGET_ASSET_ROI_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/target-asset/roi/list";

    /**
     * {@code ALL_ASSET_ENDPOINT} is constant for ALL_ASSET_ENDPOINT's endpoint
     */
    public static final String ALL_ASSET_ENDPOINT = "/sapi/v1/lending/auto-invest/all/asset";

    /**
     * {@code SOURCE_ASSET_LIST_ENDPOINT} is constant for SOURCE_ASSET_LIST_ENDPOINT's endpoint
     */
    public static final String SOURCE_ASSET_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/source-asset/list";

    /**
     * {@code PLAN_ADD_ENDPOINT} is constant for PLAN_ADD_ENDPOINT's endpoint
     */
    public static final String PLAN_ADD_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/add";

    /**
     * {@code PLAN_EDIT_ENDPOINT} is constant for PLAN_EDIT_ENDPOINT's endpoint
     */
    public static final String PLAN_EDIT_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/edit";

    /**
     * {@code PLAN_EDIT_STATUS_ENDPOINT} is constant for PLAN_EDIT_STATUS_ENDPOINT's endpoint
     */
    public static final String PLAN_EDIT_STATUS_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/edit-status";

    /**
     * {@code PLAN_LIST_ENDPOINT} is constant for PLAN_LIST_ENDPOINT's endpoint
     */
    public static final String PLAN_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/list";

    /**
     * {@code PLAN_ID_ENDPOINT} is constant for PLAN_ID_ENDPOINT's endpoint
     */
    public static final String PLAN_ID_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/id";

    /**
     * {@code AUTO_INVEST_HISTORY_LIST_ENDPOINT} is constant for AUTO_INVEST_HISTORY_LIST_ENDPOINT's endpoint
     */
    public static final String AUTO_INVEST_HISTORY_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/history/list";

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, int timeout, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceAutoInvestmentManager}'s manager without re-insert
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
    public BinanceAutoInvestmentManager() {
        super();
    }

}
