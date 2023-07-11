package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.CollateralHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.ProductRedemption;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.RateHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.*;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleRewardsHistory.FlexibleRewardsRecord.FlexibleRewardType;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.*;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedProductList.LockedProduct;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.SimpleEarnFlexibleProductList.SimpleEarnFlexibleProduct;

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

    public SimpleEarnFlexibleProductList getSimpleEarnFlexibleProductList() throws Exception {
        return getSimpleEarnFlexibleProductList(LIBRARY_OBJECT);
    }

    public <T> T getSimpleEarnFlexibleProductList(ReturnFormat format) throws Exception {
        return getSimpleEarnFlexibleProductList(null, format);
    }

    public SimpleEarnFlexibleProductList getSimpleEarnFlexibleProductList(Params queryParams) throws Exception {
        return getSimpleEarnFlexibleProductList(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getSimpleEarnFlexibleProductList(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_LIST_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SimpleEarnFlexibleProductList(response);
            default -> (T) response.toString();
        };
    }

    public LockedProductList getSimpleEarnLockedProductList() throws Exception {
        return getSimpleEarnLockedProductList(LIBRARY_OBJECT);
    }

    public <T> T getSimpleEarnLockedProductList(ReturnFormat format) throws Exception {
        return getSimpleEarnLockedProductList(null, format);
    }

    public LockedProductList getSimpleEarnLockedProductList(Params queryParams) throws Exception {
        return getSimpleEarnLockedProductList(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getSimpleEarnLockedProductList(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_LIST_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedProductList(response);
            default -> (T) response.toString();
        };
    }

    public FlexibleProductSubscription subscribeFlexibleProduct(SimpleEarnFlexibleProduct product,
                                                                double amount) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, LIBRARY_OBJECT);
    }

    public <T> T subscribeFlexibleProduct(SimpleEarnFlexibleProduct product, double amount,
                                          ReturnFormat format) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, format);
    }

    public FlexibleProductSubscription subscribeFlexibleProduct(String productId, double amount) throws Exception {
        return subscribeFlexibleProduct(productId, amount, LIBRARY_OBJECT);
    }

    public <T> T subscribeFlexibleProduct(String productId, double amount, ReturnFormat format) throws Exception {
        return subscribeFlexibleProduct(productId, amount, null, format);
    }

    public FlexibleProductSubscription subscribeFlexibleProduct(SimpleEarnFlexibleProduct product, double amount,
                                                                Params extraParams) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T subscribeFlexibleProduct(SimpleEarnFlexibleProduct product, double amount, Params extraParams,
                                          ReturnFormat format) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, extraParams, format);
    }

    public FlexibleProductSubscription subscribeFlexibleProduct(String productId, double amount,
                                                                Params extraParams) throws Exception {
        return subscribeFlexibleProduct(productId, amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T subscribeFlexibleProduct(String productId, double amount, Params extraParams,
                                          ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("productId", productId);
        extraParams.addParam("amount", amount);
        JSONObject response = new JSONObject(sendPostSignedRequest(FLEXIBLE_SUBSCRIBE_ENDPOINT, extraParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleProductSubscription(response);
            default -> (T) response.toString();
        };
    }

    public LockedProductSubscription subscribeLockedProduct(LockedProduct product, double amount) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, LIBRARY_OBJECT);
    }

    public <T> T subscribeLockedProduct(LockedProduct product, double amount, ReturnFormat format) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, format);
    }

    public LockedProductSubscription subscribeLockedProduct(String projectId, double amount) throws Exception {
        return subscribeLockedProduct(projectId, amount, LIBRARY_OBJECT);
    }

    public <T> T subscribeLockedProduct(String projectId, double amount, ReturnFormat format) throws Exception {
        return subscribeLockedProduct(projectId, amount, null, format);
    }

    public LockedProductSubscription subscribeLockedProduct(LockedProduct product, double amount,
                                                            Params extraParams) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T subscribeLockedProduct(LockedProduct product, double amount, Params extraParams,
                                        ReturnFormat format) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, extraParams, format);
    }

    public LockedProductSubscription subscribeLockedProduct(String projectId, double amount,
                                                            Params extraParams) throws Exception {
        return subscribeLockedProduct(projectId, amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T subscribeLockedProduct(String projectId, double amount, Params extraParams,
                                        ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendPostSignedRequest(LOCKED_SUBSCRIBE_ENDPOINT,
                createLockedPayload(projectId, amount, extraParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedProductSubscription(response);
            default -> (T) response.toString();
        };
    }

    public ProductRedemption redeemFlexibleProduct(SimpleEarnFlexibleProduct product) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), LIBRARY_OBJECT);
    }

    public <T> T redeemFlexibleProduct(SimpleEarnFlexibleProduct product, ReturnFormat format) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), format);
    }

    public ProductRedemption redeemFlexibleProduct(String productId) throws Exception {
        return redeemFlexibleProduct(productId, LIBRARY_OBJECT);
    }

    public <T> T redeemFlexibleProduct(String productId, ReturnFormat format) throws Exception {
        return redeemFlexibleProduct(productId, null, format);
    }

    public ProductRedemption redeemFlexibleProduct(SimpleEarnFlexibleProduct product, Params extraParams) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), extraParams, LIBRARY_OBJECT);
    }

    public <T> T redeemFlexibleProduct(SimpleEarnFlexibleProduct product, Params extraParams,
                                       ReturnFormat format) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), extraParams, format);
    }

    public ProductRedemption redeemFlexibleProduct(String productId, Params extraParams) throws Exception {
        return redeemFlexibleProduct(productId, extraParams, LIBRARY_OBJECT);
    }

    public <T> T redeemFlexibleProduct(String productId, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("productId", productId);
        return returnProductRedemption(sendPostSignedRequest(FLEXIBLE_REDEEM_ENDPOINT, extraParams), format);
    }

    public ProductRedemption redeemLockedProduct(LockedProductSubscription product) throws Exception {
        return redeemLockedProduct(product.getPositionId(), LIBRARY_OBJECT);
    }

    public <T> T redeemLockedProduct(LockedProductSubscription product, ReturnFormat format) throws Exception {
        return redeemLockedProduct(product.getPositionId(), format);
    }

    public ProductRedemption redeemLockedProduct(long positionId) throws Exception {
        return redeemLockedProduct(positionId, LIBRARY_OBJECT);
    }

    public <T> T redeemLockedProduct(long positionId, ReturnFormat format) throws Exception {
        return redeemLockedProduct(positionId, -1, format);
    }

    public ProductRedemption redeemLockedProduct(LockedProductSubscription product, long recvWindow) throws Exception {
        return redeemLockedProduct(product.getPositionId(), recvWindow, LIBRARY_OBJECT);
    }

    public <T> T redeemLockedProduct(LockedProductSubscription product, long recvWindow,
                                     ReturnFormat format) throws Exception {
        return redeemLockedProduct(product.getPositionId(), recvWindow, format);
    }

    public ProductRedemption redeemLockedProduct(long positionId, long recvWindow) throws Exception {
        return redeemLockedProduct(positionId, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T redeemLockedProduct(long positionId, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("positionId", positionId);
        return returnProductRedemption(sendPostSignedRequest(LOCKED_REDEEM_ENDPOINT, payload), format);
    }

    /**
     * Method to create a product redemption
     *
     * @param redemptionResponse: obtained from Binance's response
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
     */
    @Returner
    private <T> T returnProductRedemption(String redemptionResponse, ReturnFormat format) {
        JSONObject response = new JSONObject(redemptionResponse);
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ProductRedemption(response);
            default -> (T) redemptionResponse;
        };
    }

    public SimpleEarnFlexibleProductPositions getFlexibleProductPosition() throws Exception {
        return getFlexibleProductPosition(LIBRARY_OBJECT);
    }

    public <T> T getFlexibleProductPosition(ReturnFormat format) throws Exception {
        return getFlexibleProductPosition(null, format);
    }

    public SimpleEarnFlexibleProductPositions getFlexibleProductPosition(Params queryParams) throws Exception {
        return getFlexibleProductPosition(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleProductPosition(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_POSITION_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SimpleEarnFlexibleProductPositions(response);
            default -> (T) response.toString();
        };
    }

    public LockedProductPositions getLockedProductPosition() throws Exception {
        return getLockedProductPosition(LIBRARY_OBJECT);
    }

    public <T> T getLockedProductPosition(ReturnFormat format) throws Exception {
        return getLockedProductPosition(null, format);
    }

    public LockedProductPositions getLockedProductPosition(Params queryParams) throws Exception {
        return getLockedProductPosition(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getLockedProductPosition(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_POSITION_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedProductPositions(response);
            default -> (T) response.toString();
        };
    }

    public SimpleEarnAccount getSimpleAccount() throws Exception {
        return getSimpleAccount(LIBRARY_OBJECT);
    }

    public <T> T getSimpleAccount(ReturnFormat format) throws Exception {
        return getSimpleAccount(-1, format);
    }

    public SimpleEarnAccount getSimpleAccount(long recvWindow) throws Exception {
        return getSimpleAccount(recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getSimpleAccount(long recvWindow, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(SIMPLE_EARN_ACCOUNT_ENDPOINT,
                createTimestampPayload(null, recvWindow)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SimpleEarnAccount(response);
            default -> (T) response.toString();
        };
    }

    public FlexibleSubscriptionHistory getFlexibleSubscriptionHistory() throws Exception {
        return getFlexibleSubscriptionHistory(LIBRARY_OBJECT);
    }

    public <T> T getFlexibleSubscriptionHistory(ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionHistory(null, format);
    }

    public FlexibleSubscriptionHistory getFlexibleSubscriptionHistory(Params queryParams) throws Exception {
        return getFlexibleSubscriptionHistory(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleSubscriptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_SUBSCRIPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleSubscriptionHistory(response);
            default -> (T) response.toString();
        };
    }

    public LockedSubscriptionHistory getLockedSubscriptionHistory() throws Exception {
        return getLockedSubscriptionHistory(LIBRARY_OBJECT);
    }

    public <T> T getLockedSubscriptionHistory(ReturnFormat format) throws Exception {
        return getLockedSubscriptionHistory(null, format);
    }

    public LockedSubscriptionHistory getLockedSubscriptionHistory(Params queryParams) throws Exception {
        return getLockedSubscriptionHistory(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getLockedSubscriptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_SUBSCRIPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedSubscriptionHistory(response);
            default -> (T) response.toString();
        };
    }

    public FlexibleRedemptionHistory getFlexibleRedemptionHistory() throws Exception {
        return getFlexibleRedemptionHistory(LIBRARY_OBJECT);
    }

    public <T> T getFlexibleRedemptionHistory(ReturnFormat format) throws Exception {
        return getFlexibleRedemptionHistory(null, format);
    }

    public FlexibleRedemptionHistory getFlexibleRedemptionHistory(Params queryParams) throws Exception {
        return getFlexibleRedemptionHistory(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleRedemptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_REDEMPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleRedemptionHistory(response);
            default -> (T) response.toString();
        };
    }

    public LockedRedemptionHistory getLockedRedemptionHistory() throws Exception {
        return getLockedRedemptionHistory(LIBRARY_OBJECT);
    }

    public <T> T getLockedRedemptionHistory(ReturnFormat format) throws Exception {
        return getLockedRedemptionHistory(null, format);
    }

    public LockedRedemptionHistory getLockedRedemptionHistory(Params queryParams) throws Exception {
        return getLockedRedemptionHistory(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getLockedRedemptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_REDEMPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedRedemptionHistory(response);
            default -> (T) response.toString();
        };
    }

    public FlexibleRewardsHistory getFlexibleRewardsHistory(FlexibleRewardType type) throws Exception {
        return getFlexibleRewardsHistory(type, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleRewardsHistory(FlexibleRewardType type, ReturnFormat format) throws Exception {
        return getFlexibleRewardsHistory(type, null, format);
    }

    public FlexibleRewardsHistory getFlexibleRewardsHistory(FlexibleRewardType type, Params queryParams) throws Exception {
        return getFlexibleRewardsHistory(type, queryParams, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleRewardsHistory(FlexibleRewardType type, Params queryParams, ReturnFormat format) throws Exception {
        queryParams = createTimestampPayload(queryParams);
        queryParams.addParam("type", type);
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_REWARDS_HISTORY_ENDPOINT, queryParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleRewardsHistory(response);
            default -> (T) response.toString();
        };
    }

    public LockedRewardsHistory getLockedRewardsHistory() throws Exception {
        return getLockedRewardsHistory(LIBRARY_OBJECT);
    }

    public <T> T getLockedRewardsHistory(ReturnFormat format) throws Exception {
        return getLockedRewardsHistory(null, format);
    }

    public LockedRewardsHistory getLockedRewardsHistory(Params queryParams) throws Exception {
        return getLockedRewardsHistory(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getLockedRewardsHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_REWARDS_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedRewardsHistory(response);
            default -> (T) response.toString();
        };
    }

    public boolean setFlexibleAutoSubscribe(SimpleEarnFlexibleProduct product, boolean autoSubscribe) throws Exception {
        return setFlexibleAutoSubscribe(product.getProductId(), autoSubscribe);
    }

    public boolean setFlexibleAutoSubscribe(String productId, boolean autoSubscribe) throws Exception {
        return setFlexibleAutoSubscribe(productId, autoSubscribe, -1);
    }

    public boolean setFlexibleAutoSubscribe(SimpleEarnFlexibleProduct product, boolean autoSubscribe,
                                            long recvWindow) throws Exception {
        return setFlexibleAutoSubscribe(product.getProductId(), autoSubscribe, recvWindow);
    }

    public boolean setFlexibleAutoSubscribe(String productId, boolean autoSubscribe, long recvWindow) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("productId", productId);
        payload.addParam("autoSubscribe", autoSubscribe);
        return isSuccessResponse(FLEXIBLE_SET_AUTO_SUBSCRIBE_ENDPOINT, payload);
    }

    public boolean setLockedAutoSubscribe(LockedProductSubscription product, boolean autoSubscribe) throws Exception {
        return setLockedAutoSubscribe(product.getPositionId(), autoSubscribe);
    }

    public boolean setLockedAutoSubscribe(long positionId, boolean autoSubscribe) throws Exception {
        return setLockedAutoSubscribe(positionId, autoSubscribe, -1);
    }

    public boolean setLockedAutoSubscribe(LockedProductSubscription product, boolean autoSubscribe,
                                          long recvWindow) throws Exception {
        return setLockedAutoSubscribe(product.getPositionId(), autoSubscribe, recvWindow);
    }

    public boolean setLockedAutoSubscribe(long positionId, boolean autoSubscribe, long recvWindow) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("positionId", positionId);
        payload.addParam("autoSubscribe", autoSubscribe);
        return isSuccessResponse(LOCKED_SET_AUTO_SUBSCRIBE_ENDPOINT, payload);
    }

    public double getFlexiblePersonalLeftQuota(SimpleEarnFlexibleProduct product) throws Exception {
        return getFlexiblePersonalLeftQuota(product.getProductId());
    }

    public double getFlexiblePersonalLeftQuota(String productId) throws Exception {
        return getFlexiblePersonalLeftQuota(productId, -1);
    }

    public double getFlexiblePersonalLeftQuota(SimpleEarnFlexibleProduct product, long recvWindow) throws Exception {
        return getFlexiblePersonalLeftQuota(product.getProductId(), recvWindow);
    }

    public double getFlexiblePersonalLeftQuota(String productId, long recvWindow) throws Exception {
        Params query = createTimestampPayload(null, recvWindow);
        query.addParam("productId", productId);
        return getLeftPersonalQuota(FLEXIBLE_PERSONAL_LEFT_QUOTA_ENDPOINT, query);
    }

    public double getLockedPersonalLeftQuota(LockedProduct product) throws Exception {
        return getLockedPersonalLeftQuota(product.getProjectId());
    }

    public double getLockedPersonalLeftQuota(String projectId) throws Exception {
        return getLockedPersonalLeftQuota(projectId, -1);
    }

    public double getLockedPersonalLeftQuota(LockedProduct product, long recvWindow) throws Exception {
        return getLockedPersonalLeftQuota(product.getProjectId(), recvWindow);
    }

    public double getLockedPersonalLeftQuota(String projectId, long recvWindow) throws Exception {
        Params query = createTimestampPayload(null, recvWindow);
        query.addParam("projectId", projectId);
        return getLeftPersonalQuota(LOCKED_PERSONAL_LEFT_QUOTA_ENDPOINT, query);
    }

    /**
     * Method to get left personal quota
     *
     * @param endpoint: endpoint to request
     * @param query:    query of the request
     * @return left personal quota as double
     */
    private double getLeftPersonalQuota(String endpoint, Params query) throws Exception {
        sendGetSignedRequest(endpoint, query);
        return JsonHelper.getDouble(apiRequest.getJSONResponse(), "leftPersonalQuota");
    }

    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product,
                                                                      double amount) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product, double amount,
                                                ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, format);
    }

    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(String productId, double amount) throws Exception {
        return getFlexibleSubscriptionPreview(productId, amount, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleSubscriptionPreview(String productId, double amount, ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionPreview(productId, amount, -1, format);
    }

    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product, double amount,
                                                                      long recvWindow) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product, double amount, long recvWindow,
                                                ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, recvWindow, format);
    }

    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(String productId, double amount,
                                                                      long recvWindow) throws Exception {
        return getFlexibleSubscriptionPreview(productId, amount, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getFlexibleSubscriptionPreview(String productId, double amount, long recvWindow,
                                                ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null, recvWindow);
        query.addParam("productId", productId);
        query.addParam("amount", amount);
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_SUBSCRIPTION_PREVIEW_ENDPOINT, query));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleSubscriptionPreview(response);
            default -> (T) response.toString();
        };
    }

    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(LockedProduct product,
                                                                             double amount) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, LIBRARY_OBJECT);
    }

    public <T> T getLockedSubscriptionPreview(LockedProduct product, double amount, ReturnFormat format) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, format);
    }

    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(String projectId, double amount) throws Exception {
        return getLockedSubscriptionPreview(projectId, amount, LIBRARY_OBJECT);
    }

    public <T> T getLockedSubscriptionPreview(String projectId, double amount, ReturnFormat format) throws Exception {
        return getLockedSubscriptionPreview(projectId, amount, null, format);
    }

    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(LockedProduct product, double amount,
                                                                             Params extraParams) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getLockedSubscriptionPreview(LockedProduct product, double amount, Params extraParams,
                                              ReturnFormat format) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, extraParams, format);
    }

    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(String projectId, double amount,
                                                                             Params extraParams) throws Exception {
        return getLockedSubscriptionPreview(projectId, amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getLockedSubscriptionPreview(String projectId, double amount, Params extraParams,
                                              ReturnFormat format) throws Exception {
        JSONArray response = new JSONArray(sendGetSignedRequest(LOCKED_SUBSCRIPTION_PREVIEW_ENDPOINT,
                createLockedPayload(projectId, amount, extraParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> {
                ArrayList<LockedSubscriptionPreview> previews = new ArrayList<>();
                for (int j = 0; j < response.length(); j++)
                    previews.add(new LockedSubscriptionPreview(response.getJSONObject(j)));
                yield (T) previews;
            }
            default -> (T) response.toString();
        };
    }

    private Params createLockedPayload(String projectId, double amount, Params extraParams) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("projectId", projectId);
        extraParams.addParam("amount", amount);
        return extraParams;
    }

    public RateHistory getRateHistory(SimpleEarnFlexibleProduct productId) throws Exception {
        return getRateHistory(productId.getProductId(), LIBRARY_OBJECT);
    }

    public <T> T getRateHistory(SimpleEarnFlexibleProduct productId, ReturnFormat format) throws Exception {
        return getRateHistory(productId.getProductId(), format);
    }

    public RateHistory getRateHistory(String productId) throws Exception {
        return getRateHistory(productId, LIBRARY_OBJECT);
    }

    public <T> T getRateHistory(String productId, ReturnFormat format) throws Exception {
        return getRateHistory(productId, null, format);
    }

    public RateHistory getRateHistory(SimpleEarnFlexibleProduct product, Params queryParams) throws Exception {
        return getRateHistory(product.getProductId(), queryParams, LIBRARY_OBJECT);
    }

    public <T> T getRateHistory(SimpleEarnFlexibleProduct product, Params queryParams, ReturnFormat format) throws Exception {
        return getRateHistory(product.getProductId(), queryParams, format);
    }

    public RateHistory getRateHistory(String productId, Params queryParams) throws Exception {
        return getRateHistory(productId, queryParams, LIBRARY_OBJECT);
    }

    public <T> T getRateHistory(String productId, Params queryParams, ReturnFormat format) throws Exception {
        queryParams = createTimestampPayload(queryParams);
        queryParams.addParam("productId", productId);
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_RATE_HISTORY_ENDPOINT, queryParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new RateHistory(response);
            default -> (T) response.toString();
        };
    }

    public CollateralHistory getCollateralHistory() throws Exception {
        return getCollateralHistory(LIBRARY_OBJECT);
    }

    public <T> T getCollateralHistory(ReturnFormat format) throws Exception {
        return getCollateralHistory(null, format);
    }

    public CollateralHistory getCollateralHistory(Params queryParams) throws Exception {
        return getCollateralHistory(queryParams, LIBRARY_OBJECT);
    }

    public <T> T getCollateralHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_COLLATERAL_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new CollateralHistory(response);
            default -> (T) response.toString();
        };
    }

}
