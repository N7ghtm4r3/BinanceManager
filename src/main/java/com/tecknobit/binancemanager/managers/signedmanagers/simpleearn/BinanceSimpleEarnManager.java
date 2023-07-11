package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn;

import com.tecknobit.apimanager.annotations.*;
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
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleRewardsHistory.FlexibleRewardsRecord.FlexibleRewardsType;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.*;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedProductList.LockedProduct;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
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

    /**
     * Request to get available simple earn flexible product list <br>
     * No-any params required
     *
     * @return available simple earn flexible product list as {@link SimpleEarnFlexibleProductList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-flexible-product-list-user_data">
     * Get Simple Earn Flexible Product List (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/list")
    public SimpleEarnFlexibleProductList getSimpleEarnFlexibleProductList() throws Exception {
        return getSimpleEarnFlexibleProductList(LIBRARY_OBJECT);
    }

    /**
     * Request to get available simple earn flexible product list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return available simple earn flexible product list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-flexible-product-list-user_data">
     * Get Simple Earn Flexible Product List (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/list")
    public <T> T getSimpleEarnFlexibleProductList(ReturnFormat format) throws Exception {
        return getSimpleEarnFlexibleProductList(null, format);
    }

    /**
     * Request to get available simple earn flexible product list
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return available simple earn flexible product list as {@link SimpleEarnFlexibleProductList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-flexible-product-list-user_data">
     * Get Simple Earn Flexible Product List (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/list")
    public SimpleEarnFlexibleProductList getSimpleEarnFlexibleProductList(Params queryParams) throws Exception {
        return getSimpleEarnFlexibleProductList(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get available simple earn flexible product list
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return available simple earn flexible product list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-flexible-product-list-user_data">
     * Get Simple Earn Flexible Product List (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/list")
    public <T> T getSimpleEarnFlexibleProductList(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_LIST_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SimpleEarnFlexibleProductList(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get available locked product list <br>
     * No-any params required
     *
     * @return available locked product list as {@link LockedProductList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-locked-product-list-user_data">
     * Get Simple Earn Locked Product List (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/list")
    public LockedProductList getSimpleEarnLockedProductList() throws Exception {
        return getSimpleEarnLockedProductList(LIBRARY_OBJECT);
    }

    /**
     * Request to get available locked product list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return available locked product list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-locked-product-list-user_data">
     * Get Simple Earn Locked Product List (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/list")
    public <T> T getSimpleEarnLockedProductList(ReturnFormat format) throws Exception {
        return getSimpleEarnLockedProductList(null, format);
    }

    /**
     * Request to get available locked product list
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return available locked product list as {@link LockedProductList} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-locked-product-list-user_data">
     * Get Simple Earn Locked Product List (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/list")
    public LockedProductList getSimpleEarnLockedProductList(Params queryParams) throws Exception {
        return getSimpleEarnLockedProductList(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get available locked product list
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return available locked product list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-simple-earn-locked-product-list-user_data">
     * Get Simple Earn Locked Product List (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/list")
    public <T> T getSimpleEarnLockedProductList(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_LIST_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedProductList(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param product: product to subscribe
     * @param amount:  amount of the subscription
     * @return flexible product subscription as {@link FlexibleProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public FlexibleProductSubscription subscribeFlexibleProduct(SimpleEarnFlexibleProduct product,
                                                                double amount) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param product: product to subscribe
     * @param amount:  amount of the subscription
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return flexible product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public <T> T subscribeFlexibleProduct(SimpleEarnFlexibleProduct product, double amount,
                                          ReturnFormat format) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, format);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param productId: product identifier of the product to subscribe
     * @param amount:    amount of the subscription
     * @return flexible product subscription as {@link FlexibleProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public FlexibleProductSubscription subscribeFlexibleProduct(String productId, double amount) throws Exception {
        return subscribeFlexibleProduct(productId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param productId: product identifier of the product to subscribe
     * @param amount:    amount of the subscription
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return flexible product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public <T> T subscribeFlexibleProduct(String productId, double amount, ReturnFormat format) throws Exception {
        return subscribeFlexibleProduct(productId, amount, null, format);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param product:     product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible product subscription as {@link FlexibleProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public FlexibleProductSubscription subscribeFlexibleProduct(SimpleEarnFlexibleProduct product, double amount,
                                                                Params extraParams) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param product:     product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public <T> T subscribeFlexibleProduct(SimpleEarnFlexibleProduct product, double amount, Params extraParams,
                                          ReturnFormat format) throws Exception {
        return subscribeFlexibleProduct(product.getProductId(), amount, extraParams, format);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param productId:   product identifier of the product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible product subscription as {@link FlexibleProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
    public FlexibleProductSubscription subscribeFlexibleProduct(String productId, double amount,
                                                                Params extraParams) throws Exception {
        return subscribeFlexibleProduct(productId, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a flexible product <br>
     *
     * @param productId:   product identifier of the product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
     * Subscribe Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/subscribe")
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

    /**
     * Request to subscribe a locked product <br>
     *
     * @param product: product to subscribe
     * @param amount:  amount of the subscription
     * @return locked product subscription as {@link LockedProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public LockedProductSubscription subscribeLockedProduct(LockedProduct product, double amount) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param product: product to subscribe
     * @param amount:  amount of the subscription
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return locked product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public <T> T subscribeLockedProduct(LockedProduct product, double amount, ReturnFormat format) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, format);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param projectId: project identifier of the product to subscribe
     * @param amount:    amount of the subscription
     * @return locked product subscription as {@link LockedProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public LockedProductSubscription subscribeLockedProduct(String projectId, double amount) throws Exception {
        return subscribeLockedProduct(projectId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param projectId: project identifier of the product to subscribe
     * @param amount:    amount of the subscription
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return locked product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public <T> T subscribeLockedProduct(String projectId, double amount, ReturnFormat format) throws Exception {
        return subscribeLockedProduct(projectId, amount, null, format);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param product:     product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked product subscription as {@link LockedProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public LockedProductSubscription subscribeLockedProduct(LockedProduct product, double amount,
                                                            Params extraParams) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param product:     product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public <T> T subscribeLockedProduct(LockedProduct product, double amount, Params extraParams,
                                        ReturnFormat format) throws Exception {
        return subscribeLockedProduct(product.getProjectId(), amount, extraParams, format);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param projectId:   project identifier of the product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked product subscription as {@link LockedProductSubscription} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
    public LockedProductSubscription subscribeLockedProduct(String projectId, double amount,
                                                            Params extraParams) throws Exception {
        return subscribeLockedProduct(projectId, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to subscribe a locked product <br>
     *
     * @param projectId:   project identifier of the product to subscribe
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked product subscription as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
     * Subscribe Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/subscribe")
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

    /**
     * Request to redeem a flexible product <br>
     *
     * @param product: product to redeem
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public ProductRedemption redeemFlexibleProduct(SimpleEarnFlexibleProduct product) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a flexible product <br>
     *
     * @param product: product to redeem
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public <T> T redeemFlexibleProduct(SimpleEarnFlexibleProduct product, ReturnFormat format) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), format);
    }

    /**
     * Request to redeem a flexible product
     *
     * @param productId: product identifier of the product to redeem
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public ProductRedemption redeemFlexibleProduct(String productId) throws Exception {
        return redeemFlexibleProduct(productId, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a flexible product
     *
     * @param productId: product identifier of the product to redeem
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public <T> T redeemFlexibleProduct(String productId, ReturnFormat format) throws Exception {
        return redeemFlexibleProduct(productId, -1, format);
    }

    /**
     * Request to redeem a flexible product <br>
     *
     * @param product: product to redeem
     * @param amount:  amount to redeem
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public ProductRedemption redeemFlexibleProduct(SimpleEarnFlexibleProduct product, double amount) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a flexible product <br>
     *
     * @param product: product to redeem
     * @param amount:  amount to redeem
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public <T> T redeemFlexibleProduct(SimpleEarnFlexibleProduct product, double amount, ReturnFormat format) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), amount, format);
    }

    /**
     * Request to redeem a flexible product
     *
     * @param productId: product identifier of the product to redeem
     * @param amount:    amount to redeem
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public ProductRedemption redeemFlexibleProduct(String productId, double amount) throws Exception {
        return redeemFlexibleProduct(productId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a flexible product
     *
     * @param productId: product identifier of the product to redeem
     * @param amount:    amount to redeem
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
     * Redeem Flexible Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/redeem")
    public <T> T redeemFlexibleProduct(String productId, double amount, ReturnFormat format) throws Exception {
        Params payload = createTimestampPayload(null);
        payload.addParam("productId", productId);
        if (!(amount == -1))
            payload.addParam("amount", amount);
        else
            payload.addParam("redeemAll", true);
        return returnProductRedemption(sendPostSignedRequest(FLEXIBLE_REDEEM_ENDPOINT, payload), format);
    }

    /**
     * Request to redeem a locked product
     *
     * @param product: product to redeem
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public ProductRedemption redeemLockedProduct(LockedProductSubscription product) throws Exception {
        return redeemLockedProduct(product.getPositionId(), LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a locked product
     *
     * @param product: product to redeem
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public <T> T redeemLockedProduct(LockedProductSubscription product, ReturnFormat format) throws Exception {
        return redeemLockedProduct(product.getPositionId(), format);
    }

    /**
     * Request to redeem a locked product
     *
     * @param positionId: position of the product to redeem
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public ProductRedemption redeemLockedProduct(long positionId) throws Exception {
        return redeemLockedProduct(positionId, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a locked product
     *
     * @param positionId: position of the product to redeem
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public <T> T redeemLockedProduct(long positionId, ReturnFormat format) throws Exception {
        return redeemLockedProduct(positionId, -1, format);
    }

    /**
     * Request to redeem a locked product
     *
     * @param product:    product to redeem
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public ProductRedemption redeemLockedProduct(LockedProductSubscription product, long recvWindow) throws Exception {
        return redeemLockedProduct(product.getPositionId(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a locked product
     *
     * @param product:    product to redeem
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public <T> T redeemLockedProduct(LockedProductSubscription product, long recvWindow,
                                     ReturnFormat format) throws Exception {
        return redeemLockedProduct(product.getPositionId(), recvWindow, format);
    }

    /**
     * Request to redeem a locked product
     *
     * @param positionId: position of the product to redeem
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return product redemption as {@link ProductRedemption} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
    public ProductRedemption redeemLockedProduct(long positionId, long recvWindow) throws Exception {
        return redeemLockedProduct(positionId, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a locked product
     *
     * @param positionId: position of the product to redeem
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return product redemption as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
     * Redeem Locked Product (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/locked/redeem")
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

    /**
     * Request to get flexible product position <br>
     * No-any params required
     *
     * @return flexible product position as {@link SimpleEarnFlexibleProductPositions} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
     * Get Flexible Product Position (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/position")
    public SimpleEarnFlexibleProductPositions getFlexibleProductPosition() throws Exception {
        return getFlexibleProductPosition(LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible product position
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return flexible product position as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
     * Get Flexible Product Position (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/position")
    public <T> T getFlexibleProductPosition(ReturnFormat format) throws Exception {
        return getFlexibleProductPosition(null, format);
    }

    /**
     * Request to get flexible product position
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible product position as {@link SimpleEarnFlexibleProductPositions} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
     * Get Flexible Product Position (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/position")
    public SimpleEarnFlexibleProductPositions getFlexibleProductPosition(Params queryParams) throws Exception {
        return getFlexibleProductPosition(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible product position
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible product position as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
     * Get Flexible Product Position (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/position")
    public <T> T getFlexibleProductPosition(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_POSITION_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SimpleEarnFlexibleProductPositions(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get locked product position <br>
     * No-any params required
     *
     * @return locked product position as {@link LockedProductPositions} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-product-position-user_data">
     * Get Locked Product Position (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/position")
    public LockedProductPositions getLockedProductPosition() throws Exception {
        return getLockedProductPosition(LIBRARY_OBJECT);
    }

    /**
     * Request to get locked product position
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return locked product position as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-product-position-user_data">
     * Get Locked Product Position (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/position")
    public <T> T getLockedProductPosition(ReturnFormat format) throws Exception {
        return getLockedProductPosition(null, format);
    }

    /**
     * Request to get locked product position
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked product position as {@link LockedProductPositions} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-product-position-user_data">
     * Get Locked Product Position (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/position")
    public LockedProductPositions getLockedProductPosition(Params queryParams) throws Exception {
        return getLockedProductPosition(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked product position
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked product position as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-product-position-user_data">
     * Get Locked Product Position (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/position")
    public <T> T getLockedProductPosition(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_POSITION_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedProductPositions(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get simple earn account <br>
     * No-any params required
     *
     * @return simple earn account as {@link SimpleEarnAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#simple-account-user_data">
     * Simple Account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/account")
    public SimpleEarnAccount getSimpleAccount() throws Exception {
        return getSimpleAccount(LIBRARY_OBJECT);
    }

    /**
     * Request to get simple earn account
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return simple earn account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#simple-account-user_data">
     * Simple Account (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/account")
    public <T> T getSimpleAccount(ReturnFormat format) throws Exception {
        return getSimpleAccount(-1, format);
    }

    /**
     * Request to get simple earn account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return simple earn account as {@link SimpleEarnAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#simple-account-user_data">
     * Simple Account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/account")
    public SimpleEarnAccount getSimpleAccount(long recvWindow) throws Exception {
        return getSimpleAccount(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get simple earn account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return simple earn account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#simple-account-user_data">
     * Simple Account (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/account")
    public <T> T getSimpleAccount(long recvWindow, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(SIMPLE_EARN_ACCOUNT_ENDPOINT,
                createTimestampPayload(null, recvWindow)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SimpleEarnAccount(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get flexible subscription history <br>
     * No-any params required
     *
     * @return flexible subscription history as {@link FlexibleSubscriptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-record-user_data">
     * Get Flexible Subscription Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/subscriptionRecord")
    public FlexibleSubscriptionHistory getFlexibleSubscriptionHistory() throws Exception {
        return getFlexibleSubscriptionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible subscription history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return flexible subscription history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-record-user_data">
     * Get Flexible Subscription Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/subscriptionRecord")
    public <T> T getFlexibleSubscriptionHistory(ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionHistory(null, format);
    }

    /**
     * Request to get flexible subscription history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "purchaseId"} -> purchase identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible subscription history as {@link FlexibleSubscriptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-record-user_data">
     * Get Flexible Subscription Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/subscriptionRecord")
    public FlexibleSubscriptionHistory getFlexibleSubscriptionHistory(Params queryParams) throws Exception {
        return getFlexibleSubscriptionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible subscription history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "purchaseId"} -> purchase identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible subscription history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-record-user_data">
     * Get Flexible Subscription Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/subscriptionRecord")
    public <T> T getFlexibleSubscriptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_SUBSCRIPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleSubscriptionHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get locked subscription history <br>
     * No-any params required
     *
     * @return locked subscription history as {@link LockedSubscriptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-record-user_data">
     * Get Locked Subscription Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/subscriptionRecord")
    public LockedSubscriptionHistory getLockedSubscriptionHistory() throws Exception {
        return getLockedSubscriptionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get locked subscription history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return locked subscription history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-record-user_data">
     * Get Locked Subscription Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/subscriptionRecord")
    public <T> T getLockedSubscriptionHistory(ReturnFormat format) throws Exception {
        return getLockedSubscriptionHistory(null, format);
    }

    /**
     * Request to get locked subscription history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked subscription history as {@link LockedSubscriptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-record-user_data">
     * Get Locked Subscription Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/subscriptionRecord")
    public LockedSubscriptionHistory getLockedSubscriptionHistory(Params queryParams) throws Exception {
        return getLockedSubscriptionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked subscription history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked subscription history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-record-user_data">
     * Get Locked Subscription Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/subscriptionRecord")
    public <T> T getLockedSubscriptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_SUBSCRIPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedSubscriptionHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get flexible redemption history <br>
     * No-any params required
     *
     * @return flexible redemption history as {@link FlexibleRedemptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-redemption-record-user_data">
     * Get Flexible Redemption Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/redemptionRecord")
    public FlexibleRedemptionHistory getFlexibleRedemptionHistory() throws Exception {
        return getFlexibleRedemptionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible redemption history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return flexible redemption history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-redemption-record-user_data">
     * Get Flexible Redemption Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/redemptionRecord")
    public <T> T getFlexibleRedemptionHistory(ReturnFormat format) throws Exception {
        return getFlexibleRedemptionHistory(null, format);
    }

    /**
     * Request to get flexible redemption history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "redeemId"} -> redeem identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible redemption history as {@link FlexibleRedemptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-redemption-record-user_data">
     * Get Flexible Redemption Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/redemptionRecord")
    public FlexibleRedemptionHistory getFlexibleRedemptionHistory(Params queryParams) throws Exception {
        return getFlexibleRedemptionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible redemption history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "redeemId"} -> redeem identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible redemption history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-redemption-record-user_data">
     * Get Flexible Redemption Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/redemptionRecord")
    public <T> T getFlexibleRedemptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_REDEMPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleRedemptionHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get locked redemption history <br>
     * No-any params required
     *
     * @return locked redemption history as {@link LockedRedemptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-redemption-record-user_data">
     * Get Locked Redemption Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/redemptionRecord")
    public LockedRedemptionHistory getLockedRedemptionHistory() throws Exception {
        return getLockedRedemptionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get locked redemption history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return locked redemption history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-redemption-record-user_data">
     * Get Locked Redemption Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/redemptionRecord")
    public <T> T getLockedRedemptionHistory(ReturnFormat format) throws Exception {
        return getLockedRedemptionHistory(null, format);
    }

    /**
     * Request to get locked redemption history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> position identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "redeemId"} -> redeem identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked redemption history as {@link LockedRedemptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-redemption-record-user_data">
     * Get Locked Redemption Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/redemptionRecord")
    public LockedRedemptionHistory getLockedRedemptionHistory(Params queryParams) throws Exception {
        return getLockedRedemptionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked redemption history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> position identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "redeemId"} -> redeem identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked redemption history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-redemption-record-user_data">
     * Get Locked Redemption Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/redemptionRecord")
    public <T> T getLockedRedemptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_REDEMPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedRedemptionHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get flexible rewards history
     *
     * @param type: type of the rewards to fetch
     * @return flexible rewards history as {@link FlexibleRewardsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-rewards-history-user_data">
     * Get Flexible Rewards History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rewardsRecord")
    public FlexibleRewardsHistory getFlexibleRewardsHistory(FlexibleRewardsType type) throws Exception {
        return getFlexibleRewardsHistory(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible rewards history
     *
     * @param type:   type of the rewards to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return flexible rewards history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-rewards-history-user_data">
     * Get Flexible Rewards History (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rewardsRecord")
    public <T> T getFlexibleRewardsHistory(FlexibleRewardsType type, ReturnFormat format) throws Exception {
        return getFlexibleRewardsHistory(type, null, format);
    }

    /**
     * Request to get flexible rewards history
     *
     * @param type:        type of the rewards to fetch
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible rewards history as {@link FlexibleRewardsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-rewards-history-user_data">
     * Get Flexible Rewards History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rewardsRecord")
    public FlexibleRewardsHistory getFlexibleRewardsHistory(FlexibleRewardsType type, Params queryParams) throws Exception {
        return getFlexibleRewardsHistory(type, queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible rewards history
     *
     * @param type:        type of the rewards to fetch
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible rewards history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-rewards-history-user_data">
     * Get Flexible Rewards History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rewardsRecord")
    public <T> T getFlexibleRewardsHistory(FlexibleRewardsType type, Params queryParams, ReturnFormat format) throws Exception {
        queryParams = createTimestampPayload(queryParams);
        queryParams.addParam("type", type);
        JSONObject response = new JSONObject(sendGetSignedRequest(FLEXIBLE_REWARDS_HISTORY_ENDPOINT, queryParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new FlexibleRewardsHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get locked rewards history <br>
     * No-any params required
     *
     * @return locked rewards history as {@link LockedRewardsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-rewards-history-user_data">
     * Get Locked Rewards History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/rewardsRecord")
    public LockedRewardsHistory getLockedRewardsHistory() throws Exception {
        return getLockedRewardsHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get locked rewards history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return locked rewards history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-rewards-history-user_data">
     * Get Locked Rewards History (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/rewardsRecord")
    public <T> T getLockedRewardsHistory(ReturnFormat format) throws Exception {
        return getLockedRewardsHistory(null, format);
    }

    /**
     * Request to get locked rewards history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> position identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "redeemId"} -> redeem identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked rewards history as {@link LockedRewardsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-rewards-history-user_data">
     * Get Locked Rewards History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/rewardsRecord")
    public LockedRewardsHistory getLockedRewardsHistory(Params queryParams) throws Exception {
        return getLockedRewardsHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked rewards history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> position identifier from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "redeemId"} -> redeem identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked rewards history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-rewards-history-user_data">
     * Get Locked Rewards History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/history/rewardsRecord")
    public <T> T getLockedRewardsHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(LOCKED_REWARDS_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new LockedRewardsHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to set auto-subscribe a flexible product
     *
     * @param product:       the product to set auto-subscribe a flexible product
     * @param autoSubscribe: whether set auto-subscribe for a flexible product
     * @return whether the auto-subscribe a flexible product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-flexible-auto-subscribe-user_data">
     * Set Flexible Auto Subscribe (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setFlexibleAutoSubscribe(SimpleEarnFlexibleProduct product, boolean autoSubscribe) throws Exception {
        return setFlexibleAutoSubscribe(product.getProductId(), autoSubscribe);
    }

    /**
     * Request to set auto-subscribe a flexible product
     *
     * @param productId:     the product identifier of the product to set auto-subscribe a flexible product
     * @param autoSubscribe: whether set auto-subscribe for a flexible product
     * @return whether the auto-subscribe a flexible product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-flexible-auto-subscribe-user_data">
     * Set Flexible Auto Subscribe (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setFlexibleAutoSubscribe(String productId, boolean autoSubscribe) throws Exception {
        return setFlexibleAutoSubscribe(productId, autoSubscribe, -1);
    }

    /**
     * Request to set auto-subscribe a flexible product
     *
     * @param product:       the product to set auto-subscribe a flexible product
     * @param autoSubscribe: whether set auto-subscribe for a flexible product
     * @param recvWindow:    request is valid for in ms, must be less than 60000
     * @return whether the auto-subscribe a flexible product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-flexible-auto-subscribe-user_data">
     * Set Flexible Auto Subscribe (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setFlexibleAutoSubscribe(SimpleEarnFlexibleProduct product, boolean autoSubscribe,
                                            long recvWindow) throws Exception {
        return setFlexibleAutoSubscribe(product.getProductId(), autoSubscribe, recvWindow);
    }

    /**
     * Request to set auto-subscribe a flexible product
     *
     * @param productId:     the product identifier of the product to set auto-subscribe a flexible product
     * @param autoSubscribe: whether set auto-subscribe for a flexible product
     * @param recvWindow:    request is valid for in ms, must be less than 60000
     * @return whether the auto-subscribe a flexible product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-flexible-auto-subscribe-user_data">
     * Set Flexible Auto Subscribe (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setFlexibleAutoSubscribe(String productId, boolean autoSubscribe, long recvWindow) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("productId", productId);
        payload.addParam("autoSubscribe", autoSubscribe);
        return isSuccessResponse(FLEXIBLE_SET_AUTO_SUBSCRIBE_ENDPOINT, payload);
    }

    /**
     * Request to set auto-subscribe a locked product
     *
     * @param product:       the product to set auto-subscribe a locked product
     * @param autoSubscribe: whether set auto-subscribe for a locked product
     * @return whether the auto-subscribe a locked product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-locked-auto-subscribe-user_data">
     * Set Locked Auto Subscribe (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setLockedAutoSubscribe(LockedProductSubscription product, boolean autoSubscribe) throws Exception {
        return setLockedAutoSubscribe(product.getPositionId(), autoSubscribe);
    }

    /**
     * Request to set auto-subscribe a locked product
     *
     * @param positionId:    the position identifier of the product to set auto-subscribe a locked product
     * @param autoSubscribe: whether set auto-subscribe for a locked product
     * @return whether the auto-subscribe a locked product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-locked-auto-subscribe-user_data">
     * Set Locked Auto Subscribe (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setLockedAutoSubscribe(long positionId, boolean autoSubscribe) throws Exception {
        return setLockedAutoSubscribe(positionId, autoSubscribe, -1);
    }

    /**
     * Request to set auto-subscribe a locked product
     *
     * @param product:       the product to set auto-subscribe a locked product
     * @param autoSubscribe: whether set auto-subscribe for a locked product
     * @param recvWindow:    request is valid for in ms, must be less than 60000
     * @return whether the auto-subscribe a locked product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-locked-auto-subscribe-user_data">
     * Set Locked Auto Subscribe (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setLockedAutoSubscribe(LockedProductSubscription product, boolean autoSubscribe,
                                          long recvWindow) throws Exception {
        return setLockedAutoSubscribe(product.getPositionId(), autoSubscribe, recvWindow);
    }

    /**
     * Request to set auto-subscribe a locked product
     *
     * @param positionId:    the position identifier of the product to set auto-subscribe a locked product
     * @param autoSubscribe: whether set auto-subscribe for a locked product
     * @param recvWindow:    request is valid for in ms, must be less than 60000
     * @return whether the auto-subscribe a locked product has been successful as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-locked-auto-subscribe-user_data">
     * Set Locked Auto Subscribe (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/simple-earn/flexible/setAutoSubscribe")
    public boolean setLockedAutoSubscribe(long positionId, boolean autoSubscribe, long recvWindow) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("positionId", positionId);
        payload.addParam("autoSubscribe", autoSubscribe);
        return isSuccessResponse(LOCKED_SET_AUTO_SUBSCRIBE_ENDPOINT, payload);
    }

    /**
     * Request to get the personal left quota for a flexible product
     *
     * @param product: the product from fetch the personal left quota
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-personal-left-quota-user_data">
     * Get Flexible Personal Left Quota (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/personalLeftQuota")
    public double getFlexiblePersonalLeftQuota(SimpleEarnFlexibleProduct product) throws Exception {
        return getFlexiblePersonalLeftQuota(product.getProductId());
    }

    /**
     * Request to get the personal left quota for a flexible product
     *
     * @param productId: the product identifier of the product from fetch the personal left quota
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-personal-left-quota-user_data">
     * Get Flexible Personal Left Quota (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/personalLeftQuota")
    public double getFlexiblePersonalLeftQuota(String productId) throws Exception {
        return getFlexiblePersonalLeftQuota(productId, -1);
    }

    /**
     * Request to get the personal left quota for a flexible product
     *
     * @param product:    the product from fetch the personal left quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-personal-left-quota-user_data">
     * Get Flexible Personal Left Quota (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/personalLeftQuota")
    public double getFlexiblePersonalLeftQuota(SimpleEarnFlexibleProduct product, long recvWindow) throws Exception {
        return getFlexiblePersonalLeftQuota(product.getProductId(), recvWindow);
    }

    /**
     * Request to get the personal left quota for a flexible product
     *
     * @param productId:  the product identifier of the product from fetch the personal left quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-personal-left-quota-user_data">
     * Get Flexible Personal Left Quota (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/personalLeftQuota")
    public double getFlexiblePersonalLeftQuota(String productId, long recvWindow) throws Exception {
        Params query = createTimestampPayload(null, recvWindow);
        query.addParam("productId", productId);
        return getLeftPersonalQuota(FLEXIBLE_PERSONAL_LEFT_QUOTA_ENDPOINT, query);
    }

    /**
     * Request to get the personal left quota for a locked product
     *
     * @param product: the product from fetch the personal left quota
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-personal-left-quota-user_data">
     * Get Locked Personal Left Quota (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/personalLeftQuota")
    public double getLockedPersonalLeftQuota(LockedProduct product) throws Exception {
        return getLockedPersonalLeftQuota(product.getProjectId());
    }

    /**
     * Request to get the personal left quota for a locked product
     *
     * @param projectId: the project identifier of the product from fetch the personal left quota
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-personal-left-quota-user_data">
     * Get Locked Personal Left Quota (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/personalLeftQuota")
    public double getLockedPersonalLeftQuota(String projectId) throws Exception {
        return getLockedPersonalLeftQuota(projectId, -1);
    }

    /**
     * Request to get the personal left quota for a locked product
     *
     * @param product:    the product from fetch the personal left quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-personal-left-quota-user_data">
     * Get Locked Personal Left Quota (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/personalLeftQuota")
    public double getLockedPersonalLeftQuota(LockedProduct product, long recvWindow) throws Exception {
        return getLockedPersonalLeftQuota(product.getProjectId(), recvWindow);
    }

    /**
     * Request to get the personal left quota for a locked product
     *
     * @param projectId:  the project identifier of the product from fetch the personal left quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return the personal left quota as double
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-personal-left-quota-user_data">
     * Get Locked Personal Left Quota (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/personalLeftQuota")
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

    /**
     * Request to get flexible subscription preview
     *
     * @param product: product to get flexible subscription preview
     * @param amount:  amount of the subscription
     * @return flexible subscription preview as {@link FlexibleSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product,
                                                                      double amount) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param product: product to get flexible subscription preview
     * @param amount:  amount of the subscription
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return flexible subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public <T> T getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product, double amount,
                                                ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, format);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param productId: product identifier of the product to get flexible subscription preview
     * @param amount:    amount of the subscription
     * @return flexible subscription preview as {@link FlexibleSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(String productId, double amount) throws Exception {
        return getFlexibleSubscriptionPreview(productId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param productId: product identifier of the product to get flexible subscription preview
     * @param amount:    amount of the subscription
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return flexible subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public <T> T getFlexibleSubscriptionPreview(String productId, double amount, ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionPreview(productId, amount, -1, format);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param product:    product to get flexible subscription preview
     * @param amount:     amount of the subscription
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return flexible subscription preview as {@link FlexibleSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product, double amount,
                                                                      long recvWindow) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param product:    product to get flexible subscription preview
     * @param amount:     amount of the subscription
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return flexible subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public <T> T getFlexibleSubscriptionPreview(SimpleEarnFlexibleProduct product, double amount, long recvWindow,
                                                ReturnFormat format) throws Exception {
        return getFlexibleSubscriptionPreview(product.getProductId(), amount, recvWindow, format);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param productId:  product identifier of the product to get flexible subscription preview
     * @param amount:     amount of the subscription
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return flexible subscription preview as {@link FlexibleSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
    public FlexibleSubscriptionPreview getFlexibleSubscriptionPreview(String productId, double amount,
                                                                      long recvWindow) throws Exception {
        return getFlexibleSubscriptionPreview(productId, amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get flexible subscription preview
     *
     * @param productId:  product identifier of the product to get flexible subscription preview
     * @param amount:     amount of the subscription
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return flexible subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
     * Subscribe Flexible Product (TRADE)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/subscriptionPreview")
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

    /**
     * Request to get locked subscription preview
     *
     * @param product: product to get locked subscription preview
     * @param amount:  amount of the subscription
     * @return locked subscription preview as {@link ArrayList} of {@link LockedSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(LockedProduct product,
                                                                             double amount) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param product: product to get locked subscription preview
     * @param amount:  amount of the subscription
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return locked subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public <T> T getLockedSubscriptionPreview(LockedProduct product, double amount, ReturnFormat format) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, format);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param projectId: project identifier of the product to get locked subscription preview
     * @param amount:    amount of the subscription
     * @return locked subscription preview as {@link ArrayList} of {@link LockedSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(String projectId, double amount) throws Exception {
        return getLockedSubscriptionPreview(projectId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param projectId: project identifier of the product to get locked subscription preview
     * @param amount:    amount of the subscription
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return locked subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public <T> T getLockedSubscriptionPreview(String projectId, double amount, ReturnFormat format) throws Exception {
        return getLockedSubscriptionPreview(projectId, amount, null, format);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param product:     product to get locked subscription preview
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked subscription preview as {@link ArrayList} of {@link LockedSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(LockedProduct product, double amount,
                                                                             Params extraParams) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param product:     product to get locked subscription preview
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public <T> T getLockedSubscriptionPreview(LockedProduct product, double amount, Params extraParams,
                                              ReturnFormat format) throws Exception {
        return getLockedSubscriptionPreview(product.getProjectId(), amount, extraParams, format);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param projectId:   project identifier of the product to get locked subscription preview
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return locked subscription preview as {@link ArrayList} of {@link LockedSubscriptionPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
    public ArrayList<LockedSubscriptionPreview> getLockedSubscriptionPreview(String projectId, double amount,
                                                                             Params extraParams) throws Exception {
        return getLockedSubscriptionPreview(projectId, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get locked subscription preview
     *
     * @param projectId:   project identifier of the product to get locked subscription preview
     * @param amount:      amount of the subscription
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "autoSubscribe"} -> whether auto-subscribe the product - [BOOLEAN, default true]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return locked subscription preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
     * Get Locked Subscription Preview (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/locked/subscriptionPreview")
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

    /**
     * Method to create a locked payload
     *
     * @param projectId:   project identifier to use
     * @param amount:      amount of the request
     * @param extraParams: extra params of the request
     * @return payload as {@link Params}
     */
    private Params createLockedPayload(String projectId, double amount, Params extraParams) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("projectId", projectId);
        extraParams.addParam("amount", amount);
        return extraParams;
    }

    /**
     * Request to get rate history
     *
     * @param product: product from get rate history
     * @return rate history as {@link RateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public RateHistory getRateHistory(SimpleEarnFlexibleProduct product) throws Exception {
        return getRateHistory(product.getProductId(), LIBRARY_OBJECT);
    }

    /**
     * Request to get rate history
     *
     * @param product: product from get rate history
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return rate history preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public <T> T getRateHistory(SimpleEarnFlexibleProduct product, ReturnFormat format) throws Exception {
        return getRateHistory(product.getProductId(), format);
    }

    /**
     * Request to get rate history
     *
     * @param productId: product identifier of the product from get rate history
     * @return rate history as {@link RateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public RateHistory getRateHistory(String productId) throws Exception {
        return getRateHistory(productId, LIBRARY_OBJECT);
    }

    /**
     * Request to get rate history
     *
     * @param productId: product identifier of the product from get rate history
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return rate history preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public <T> T getRateHistory(String productId, ReturnFormat format) throws Exception {
        return getRateHistory(productId, null, format);
    }

    /**
     * Request to get rate history
     *
     * @param product:     product from get rate history
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     * @return rate history as {@link RateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public RateHistory getRateHistory(SimpleEarnFlexibleProduct product, Params queryParams) throws Exception {
        return getRateHistory(product.getProductId(), queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get rate history
     *
     * @param product:     product from get rate history
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return rate history preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public <T> T getRateHistory(SimpleEarnFlexibleProduct product, Params queryParams, ReturnFormat format) throws Exception {
        return getRateHistory(product.getProductId(), queryParams, format);
    }

    /**
     * Request to get rate history
     *
     * @param productId:   product identifier of the product from get rate history
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     * @return rate history as {@link RateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
    public RateHistory getRateHistory(String productId, Params queryParams) throws Exception {
        return getRateHistory(productId, queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get rate history
     *
     * @param productId:   product identifier of the product from get rate history
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return rate history preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-rate-history-user_data">
     * Get Rate History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/rateHistory")
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

    /**
     * Request to get collateral history <br>
     * No-any params required
     *
     * @return collateral history as {@link CollateralHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-record-user_data">
     * Get Collateral Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/collateralRecord")
    public CollateralHistory getCollateralHistory() throws Exception {
        return getCollateralHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get collateral history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return collateral history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-record-user_data">
     * Get Collateral Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/collateralRecord")
    public <T> T getCollateralHistory(ReturnFormat format) throws Exception {
        return getCollateralHistory(null, format);
    }

    /**
     * Request to get collateral history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return collateral history as {@link CollateralHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-record-user_data">
     * Get Collateral Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/collateralRecord")
    public CollateralHistory getCollateralHistory(Params queryParams) throws Exception {
        return getCollateralHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get collateral history
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier from fetch the list - [STRING]
     *                           </li>
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return collateral history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-record-user_data">
     * Get Collateral Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/simple-earn/flexible/history/collateralRecord")
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
