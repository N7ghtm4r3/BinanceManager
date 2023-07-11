package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn;

import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;

import java.io.IOException;

/**
 * The {@code BinanceSimpleEarnManager} class is useful to manage simple earn endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#simple-earn-endpoints">
 * Simple Earn Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see Manager
 */
public class BinanceSimpleEarnManager extends BinanceSignedManager {

    /**
     * {@code FLEXIBLE_LIST_ENDPOINT} is constant for FLEXIBLE_LIST_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_LIST_ENDPOINT = "/sapi/v1/simple-earn/flexible/list";

    /**
     * {@code LOCKED_LIST_ENDPOINT} is constant for LOCKED_LIST_ENDPOINT's endpoint
     */
    public static final String LOCKED_LIST_ENDPOINT = "/sapi/v1/simple-earn/locked/list";

    /**
     * {@code FLEXIBLE_SUBSCRIBE_ENDPOINT} is constant for FLEXIBLE_SUBSCRIBE_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_SUBSCRIBE_ENDPOINT = "/sapi/v1/simple-earn/flexible/subscribe";

    /**
     * {@code LOCKED_SUBSCRIBE_ENDPOINT} is constant for LOCKED_SUBSCRIBE_ENDPOINT's endpoint
     */
    public static final String LOCKED_SUBSCRIBE_ENDPOINT = "/sapi/v1/simple-earn/locked/subscribe";

    /**
     * {@code FLEXIBLE_REDEEM_ENDPOINT} is constant for FLEXIBLE_REDEEM_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_REDEEM_ENDPOINT = "/sapi/v1/simple-earn/flexible/redeem";

    /**
     * {@code LOCKED_REDEEM_ENDPOINT} is constant for LOCKED_REDEEM_ENDPOINT's endpoint
     */
    public static final String LOCKED_REDEEM_ENDPOINT = "/sapi/v1/simple-earn/locked/redeem";

    /**
     * {@code FLEXIBLE_POSITION_ENDPOINT} is constant for FLEXIBLE_POSITION_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_POSITION_ENDPOINT = "/sapi/v1/simple-earn/flexible/position";

    /**
     * {@code LOCKED_POSITION_ENDPOINT} is constant for LOCKED_POSITION_ENDPOINT's endpoint
     */
    public static final String LOCKED_POSITION_ENDPOINT = "/sapi/v1/simple-earn/locked/position";

    /**
     * {@code SIMPLE_EARN_ACCOUNT_ENDPOINT} is constant for SIMPLE_EARN_ACCOUNT_ENDPOINT's endpoint
     */
    public static final String SIMPLE_EARN_ACCOUNT_ENDPOINT = "/sapi/v1/simple-earn/account";

    /**
     * {@code FLEXIBLE_SUBSCRIPTION_HISTORY_ENDPOINT} is constant for FLEXIBLE_SUBSCRIPTION_HISTORY_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_SUBSCRIPTION_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/flexible/history/subscriptionRecord";

    /**
     * {@code LOCKED_SUBSCRIPTION_HISTORY_ENDPOINT} is constant for LOCKED_SUBSCRIPTION_HISTORY_ENDPOINT's endpoint
     */
    public static final String LOCKED_SUBSCRIPTION_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/locked/history/subscriptionRecord";

    /**
     * {@code FLEXIBLE_REDEMPTION_HISTORY_ENDPOINT} is constant for FLEXIBLE_REDEMPTION_HISTORY_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_REDEMPTION_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/flexible/history/redemptionRecord";

    /**
     * {@code LOCKED_REDEMPTION_HISTORY_ENDPOINT} is constant for LOCKED_REDEMPTION_HISTORY_ENDPOINT's endpoint
     */
    public static final String LOCKED_REDEMPTION_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/locked/history/redemptionRecord";

    /**
     * {@code FLEXIBLE_REWARDS_HISTORY_ENDPOINT} is constant for FLEXIBLE_REWARDS_HISTORY_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_REWARDS_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/flexible/history/rewardsRecord";

    /**
     * {@code LOCKED_REWARDS_HISTORY_ENDPOINT} is constant for LOCKED_REWARDS_HISTORY_ENDPOINT's endpoint
     */
    public static final String LOCKED_REWARDS_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/locked/history/rewardsRecord";

    /**
     * {@code FLEXIBLE_SET_AUTO_SUBSCRIBE_ENDPOINT} is constant for FLEXIBLE_SET_AUTO_SUBSCRIBE_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_SET_AUTO_SUBSCRIBE_ENDPOINT = "/sapi/v1/simple-earn/flexible/setAutoSubscribe";

    /**
     * {@code LOCKED_SET_AUTO_SUBSCRIBE_ENDPOINT} is constant for LOCKED_SET_AUTO_SUBSCRIBE_ENDPOINT's endpoint
     */
    public static final String LOCKED_SET_AUTO_SUBSCRIBE_ENDPOINT = "/sapi/v1/simple-earn/locked/setAutoSubscribe";

    /**
     * {@code FLEXIBLE_PERSONAL_LEFT_QUOTA_ENDPOINT} is constant for FLEXIBLE_PERSONAL_LEFT_QUOTA_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_PERSONAL_LEFT_QUOTA_ENDPOINT = "/sapi/v1/simple-earn/flexible/personalLeftQuota";

    /**
     * {@code LOCKED_PERSONAL_LEFT_QUOTA_ENDPOINT} is constant for LOCKED_PERSONAL_LEFT_QUOTA_ENDPOINT's endpoint
     */
    public static final String LOCKED_PERSONAL_LEFT_QUOTA_ENDPOINT = "/sapi/v1/simple-earn/locked/personalLeftQuota";

    /**
     * {@code FLEXIBLE_SUBSCRIPTION_PREVIEW_ENDPOINT} is constant for FLEXIBLE_SUBSCRIPTION_PREVIEW_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_SUBSCRIPTION_PREVIEW_ENDPOINT = "/sapi/v1/simple-earn/flexible/subscriptionPreview";

    /**
     * {@code LOCKED_SUBSCRIPTION_PREVIEW_ENDPOINT} is constant for LOCKED_SUBSCRIPTION_PREVIEW_ENDPOINT's endpoint
     */
    public static final String LOCKED_SUBSCRIPTION_PREVIEW_ENDPOINT = "/sapi/v1/simple-earn/locked/subscriptionPreview";

    /**
     * {@code FLEXIBLE_RATE_HISTORY_ENDPOINT} is constant for FLEXIBLE_RATE_HISTORY_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_RATE_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/flexible/history/rateHistory";

    /**
     * {@code FLEXIBLE_COLLATERAL_HISTORY_ENDPOINT} is constant for FLEXIBLE_COLLATERAL_HISTORY_ENDPOINT's endpoint
     */
    public static final String FLEXIBLE_COLLATERAL_HISTORY_ENDPOINT = "/sapi/v1/simple-earn/flexible/history/collateralRecord";

    /**
     * Constructor to init a {@link BinanceSimpleEarnManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSimpleEarnManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSimpleEarnManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSimpleEarnManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSimpleEarnManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSimpleEarnManager(String baseEndpoint, int timeout, String apiKey,
                                    String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSimpleEarnManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSimpleEarnManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSimpleEarnManager} <br>
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
    public BinanceSimpleEarnManager() {
        super();
    }

}
