package com.tecknobit.binancemanager.managers.signedmanagers.savings;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.ChangePositionResult;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.LendingAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingInterest;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure.SavingStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.FixedActivityProject;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.FixedActivityProject.SortBy;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.FixedActivityProjectPosition;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.SavingActivityStructure.SavingActivityType;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product.FlexibleProduct;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product.FlexibleProduct.Featured;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product.FlexibleProduct.FlexibleProductStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product.FlexibleProductPosition;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product.PurchaseQuota;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product.RedemptionQuota;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.PurchaseRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption.FixedActivityProductRedemption;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption.FlexibleProductRedemption;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption.FlexibleProductRedemption.RedemptionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.SavingActivityStructure.SavingActivityType.DAILY;
import static java.lang.Long.parseLong;

/**
 * The {@code BinanceSavingsManager} class is useful to manage savings endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#savings-endpoints">
 * Savings endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 */
public class BinanceSavingsManager extends BinanceSignedManager {

    /**
     * {@code LENDING_DAILY_PRODUCT_LIST_ENDPOINT} is constant for LENDING_DAILY_PRODUCT_LIST_ENDPOINT's endpoint
     */
    public static final String LENDING_DAILY_PRODUCT_LIST_ENDPOINT = "/sapi/v1/lending/daily/product/list";

    /**
     * {@code LENDING_DAILY_USER_LEFT_QUOTA_ENDPOINT} is constant for LENDING_DAILY_USER_LEFT_QUOTA_ENDPOINT's endpoint
     */
    public static final String LENDING_DAILY_USER_LEFT_QUOTA_ENDPOINT = "/sapi/v1/lending/daily/userLeftQuota";

    /**
     * {@code LENDING_DAILY_PURCHASE_ENDPOINT} is constant for LENDING_DAILY_PURCHASE_ENDPOINT's endpoint
     */
    public static final String LENDING_DAILY_PURCHASE_ENDPOINT = "/sapi/v1/lending/daily/purchase";

    /**
     * {@code LENDING_DAILY_USER_REDEMPTION_QUOTA_ENDPOINT} is constant for LENDING_DAILY_USER_REDEMPTION_QUOTA_ENDPOINT's endpoint
     */
    public static final String LENDING_DAILY_USER_REDEMPTION_QUOTA_ENDPOINT = "/sapi/v1/lending/daily/userRedemptionQuota";

    /**
     * {@code LENDING_DAILY_REDEEM_ENDPOINT} is constant for LENDING_DAILY_REDEEM_ENDPOINT's endpoint
     */
    public static final String LENDING_DAILY_REDEEM_ENDPOINT = "/sapi/v1/lending/daily/redeem";

    /**
     * {@code LENDING_DAILY_TOKEN_POSITION_ENDPOINT} is constant for LENDING_DAILY_TOKEN_POSITION_ENDPOINT's endpoint
     */
    public static final String LENDING_DAILY_TOKEN_POSITION_ENDPOINT = "/sapi/v1/lending/daily/token/position";

    /**
     * {@code LENDING_PROJECT_LIST_ENDPOINT} is constant for LENDING_PROJECT_LIST_ENDPOINT's endpoint
     */
    public static final String LENDING_PROJECT_LIST_ENDPOINT = "/sapi/v1/lending/project/list";

    /**
     * {@code LENDING_CUSTOMIZED_FIXED_PURCHASE_ENDPOINT} is constant for LENDING_CUSTOMIZED_FIXED_PURCHASE_ENDPOINT's endpoint
     */
    public static final String LENDING_CUSTOMIZED_FIXED_PURCHASE_ENDPOINT = "/sapi/v1/lending/customizedFixed/purchase";

    /**
     * {@code LENDING_PROJECT_POSITION_LIST_ENDPOINT} is constant for LENDING_PROJECT_POSITION_LIST_ENDPOINT's endpoint
     */
    public static final String LENDING_PROJECT_POSITION_LIST_ENDPOINT = "/sapi/v1/lending/project/position/list";

    /**
     * {@code LENDING_UNION_ACCOUNT_ENDPOINT} is constant for LENDING_UNION_ACCOUNT_ENDPOINT's endpoint
     */
    public static final String LENDING_UNION_ACCOUNT_ENDPOINT = "/sapi/v1/lending/union/account";

    /**
     * {@code LENDING_UNION_PURCHASE_RECORD_ENDPOINT} is constant for LENDING_UNION_PURCHASE_RECORD_ENDPOINT's endpoint
     */
    public static final String LENDING_UNION_PURCHASE_RECORD_ENDPOINT = "/sapi/v1/lending/union/purchaseRecord";

    /**
     * {@code LENDING_UNION_REDEMPTION_RECORD_ENDPOINT} is constant for LENDING_UNION_REDEMPTION_RECORD_ENDPOINT's endpoint
     */
    public static final String LENDING_UNION_REDEMPTION_RECORD_ENDPOINT = "/sapi/v1/lending/union/redemptionRecord";

    /**
     * {@code LENDING_UNION_INTEREST_HISTORY_ENDPOINT} is constant for LENDING_UNION_INTEREST_HISTORY_ENDPOINT's endpoint
     */
    public static final String LENDING_UNION_INTEREST_HISTORY_ENDPOINT = "/sapi/v1/lending/union/interestHistory";

    /**
     * {@code LENDING_POSITION_CHANGED_ENDPOINT} is constant for LENDING_POSITION_CHANGED_ENDPOINT's endpoint
     */
    public static final String LENDING_POSITION_CHANGED_ENDPOINT = "/sapi/v1/lending/positionChanged";

    /**
     * Constructor to init a {@link BinanceSavingsManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSavingsManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSavingsManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSavingsManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSavingsManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSavingsManager(String baseEndpoint, int timeout, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSavingsManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSavingsManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSavingsManager} <br>
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
    public BinanceSavingsManager() {
        super();
    }

    /**
     * Request to get the flexible product list <br>
     * No-any params required
     *
     * @return flexible product list as {@link ArrayList} of {@link FlexibleProduct} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-list-user_data">
     * Get Flexible Product List (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/product/list")
    public ArrayList<FlexibleProduct> getFlexibleProductList() throws Exception {
        return getFlexibleProductList(LIBRARY_OBJECT);
    }

    /**
     * Request to get the flexible product list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return flexible product list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-list-user_data">
     * Get Flexible Product List (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/product/list")
    public <T> T getFlexibleProductList(ReturnFormat format) throws Exception {
        return getFlexibleProductList(null, format);
    }

    /**
     * Request to get the flexible product list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "status"} -> constants available {@link FlexibleProductStatus} - [ENUM, default ALL]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "featured"} -> constants available {@link Featured} - [STRING, default ALL]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 50]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible product list as {@link ArrayList} of {@link FlexibleProduct} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-list-user_data">
     * Get Flexible Product List (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/product/list")
    public ArrayList<FlexibleProduct> getFlexibleProductList(Params extraParams) throws Exception {
        return getFlexibleProductList(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the flexible product list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "status"} -> constants available {@link FlexibleProductStatus} - [ENUM, default ALL]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "featured"} -> constants available {@link Featured} - [STRING, default ALL]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 50]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return flexible product list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-list-user_data">
     * Get Flexible Product List (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/product/list")
    public <T> T getFlexibleProductList(Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetSignedRequest(LENDING_DAILY_PRODUCT_LIST_ENDPOINT,
                createTimestampPayload(extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<FlexibleProduct> products = new ArrayList<>();
                JSONArray jProducts = new JSONArray(listResponse);
                for (int j = 0; j < jProducts.length(); j++)
                    products.add(new FlexibleProduct(jProducts.getJSONObject(j)));
                return (T) products;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the left daily purchase quota of flexible product
     *
     * @param product: the product from fetch the purchase quota
     * @return left daily purchase quota of flexible product as {@link PurchaseQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public PurchaseQuota getLeftDailyPurchaseQuota(FlexibleProduct product) throws Exception {
        return getLeftDailyPurchaseQuota(product.getProductId(), LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily purchase quota of flexible product
     *
     * @param product: the product from fetch the purchase quota
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return left daily purchase quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public <T> T getLeftDailyPurchaseQuota(FlexibleProduct product, ReturnFormat format) throws Exception {
        return getLeftDailyPurchaseQuota(product.getProductId(), format);
    }

    /**
     * Request to get the left daily purchase quota of flexible product
     *
     * @param productId: the product id from fetch the purchase quota
     * @return left daily purchase quota of flexible product as {@link PurchaseQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public PurchaseQuota getLeftDailyPurchaseQuota(String productId) throws Exception {
        return getLeftDailyPurchaseQuota(productId, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily purchase quota of flexible product
     *
     * @param productId: the product id from fetch the purchase quota
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return left daily purchase quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public <T> T getLeftDailyPurchaseQuota(String productId, ReturnFormat format) throws Exception {
        return getLeftDailyPurchaseQuota(productId, -1, format);
    }

    /**
     * Request to get the left daily purchase quota of flexible product <br>
     *
     * @param product:    the product from fetch the purchase quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return left daily purchase quota of flexible product as {@link PurchaseQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public PurchaseQuota getLeftDailyPurchaseQuota(FlexibleProduct product, long recvWindow) throws Exception {
        return getLeftDailyPurchaseQuota(product.getProductId(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily purchase quota of flexible product <br>
     *
     * @param product:    the product from fetch the purchase quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return left daily purchase quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public <T> T getLeftDailyPurchaseQuota(FlexibleProduct product, long recvWindow, ReturnFormat format) throws Exception {
        return getLeftDailyPurchaseQuota(product.getProductId(), recvWindow, format);
    }

    /**
     * Request to get the left daily purchase quota of flexible product <br>
     *
     * @param productId:  the product id from fetch the purchase quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return left daily purchase quota of flexible product as {@link PurchaseQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public PurchaseQuota getLeftDailyPurchaseQuota(String productId, long recvWindow) throws Exception {
        return getLeftDailyPurchaseQuota(productId, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily purchase quota of flexible product
     *
     * @param productId:  the product id from fetch the purchase quota
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return left daily purchase quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
     * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userLeftQuota")
    public <T> T getLeftDailyPurchaseQuota(String productId, long recvWindow, ReturnFormat format) throws Exception {
        String purchaseResponse = sendGetSignedRequest(LENDING_DAILY_USER_LEFT_QUOTA_ENDPOINT,
                getLeftQuotaQuery(productId, recvWindow));
        switch (format) {
            case JSON:
                return (T) new JSONObject(purchaseResponse);
            case LIBRARY_OBJECT:
                return (T) new PurchaseQuota(new JSONObject(purchaseResponse));
            default:
                return (T) purchaseResponse;
        }
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param product: the product to purchase
     * @param amount:  the amount of the purchase
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public long purchaseFlexibleProduct(FlexibleProduct product, double amount) throws Exception {
        return parseLong(purchaseFlexibleProduct(product.getProductId(), amount, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param product: the product to purchase
     * @param amount:  the amount of the purchase
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public <T> T purchaseFlexibleProduct(FlexibleProduct product, double amount, ReturnFormat format) throws Exception {
        return purchaseFlexibleProduct(product.getProductId(), amount, format);
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param productId: the product id from fetch the purchase quota
     * @param amount:    the amount of the purchase
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public long purchaseFlexibleProduct(String productId, double amount) throws Exception {
        return parseLong(purchaseFlexibleProduct(productId, amount, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param productId: the product id from fetch the purchase quota
     * @param amount:    the amount of the purchase
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public <T> T purchaseFlexibleProduct(String productId, double amount, ReturnFormat format) throws Exception {
        return purchaseFlexibleProduct(productId, amount, -1, format);
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param product:    the product to purchase
     * @param amount:     the amount of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public long purchaseFlexibleProduct(FlexibleProduct product, double amount, long recvWindow) throws Exception {
        return parseLong(purchaseFlexibleProduct(product.getProductId(), amount, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param product:    the product to purchase
     * @param amount:     the amount of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public <T> T purchaseFlexibleProduct(FlexibleProduct product, double amount, long recvWindow,
                                         ReturnFormat format) throws Exception {
        return purchaseFlexibleProduct(product.getProductId(), amount, recvWindow, format);
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param productId:  the product id from fetch the purchase quota
     * @param amount:     the amount of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public long purchaseFlexibleProduct(String productId, double amount, long recvWindow) throws Exception {
        return parseLong(purchaseFlexibleProduct(productId, amount, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase quota of flexible product
     *
     * @param productId:  the product id from fetch the purchase quota
     * @param amount:     the amount of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-flexible-product-user_data">
     * Purchase Flexible Product (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/purchase")
    public <T> T purchaseFlexibleProduct(String productId, double amount, long recvWindow,
                                         ReturnFormat format) throws Exception {
        return returnPurchaseId(sendPostSignedRequest(LENDING_DAILY_PURCHASE_ENDPOINT,
                createFlexibleOpePayload(productId, amount, recvWindow)), format);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param product: the product from fetch the redemption quota
     * @param type:    the type of the redemption
     * @return left daily redemption quota of flexible product as {@link RedemptionQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public RedemptionQuota getLeftDailyRedemptionQuota(FlexibleProduct product, RedemptionType type) throws Exception {
        return getLeftDailyRedemptionQuota(product.getProductId(), type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param product: the product from fetch the redemption quota
     * @param type:    the type of the redemption
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return left daily redemption quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public <T> T getLeftDailyRedemptionQuota(FlexibleProduct product, RedemptionType type,
                                             ReturnFormat format) throws Exception {
        return getLeftDailyRedemptionQuota(product.getProductId(), type, format);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param productId: the product id from fetch the redemption quota
     * @param type:      the type of the redemption
     * @return left daily redemption quota of flexible product as {@link RedemptionQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public RedemptionQuota getLeftDailyRedemptionQuota(String productId, RedemptionType type) throws Exception {
        return getLeftDailyRedemptionQuota(productId, type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param productId: the product id from fetch the redemption quota
     * @param type:      the type of the redemption
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return left daily redemption quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public <T> T getLeftDailyRedemptionQuota(String productId, RedemptionType type, ReturnFormat format) throws Exception {
        return getLeftDailyRedemptionQuota(productId, type, -1, format);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param product:    the product from fetch the redemption quota
     * @param type:       the type of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return left daily redemption quota of flexible product as {@link RedemptionQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public RedemptionQuota getLeftDailyRedemptionQuota(FlexibleProduct product, RedemptionType type,
                                                       long recvWindow) throws Exception {
        return getLeftDailyRedemptionQuota(product.getProductId(), type, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param product:    the product from fetch the redemption quota
     * @param type:       the type of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return left daily redemption quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public <T> T getLeftDailyRedemptionQuota(FlexibleProduct product, RedemptionType type, long recvWindow,
                                             ReturnFormat format) throws Exception {
        return getLeftDailyRedemptionQuota(product.getProductId(), type, recvWindow, format);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param productId:  the product id from fetch the redemption quota
     * @param type:       the type of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return left daily redemption quota of flexible product as {@link RedemptionQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public RedemptionQuota getLeftDailyRedemptionQuota(String productId, RedemptionType type,
                                                       long recvWindow) throws Exception {
        return getLeftDailyRedemptionQuota(productId, type, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the left daily redemption quota of flexible product
     *
     * @param productId:  the product id from fetch the redemption quota
     * @param type:       the type of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return left daily redemption quota of flexible product as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-redemption-quota-of-flexible-product-user_data">
     * Get Left Daily Redemption Quota of Flexible Product (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/userRedemptionQuota")
    public <T> T getLeftDailyRedemptionQuota(String productId, RedemptionType type, long recvWindow,
                                             ReturnFormat format) throws Exception {
        Params query = getLeftQuotaQuery(productId, recvWindow);
        query.addParam("type", type);
        String redemptionQuotaResponse = sendGetSignedRequest(LENDING_DAILY_USER_REDEMPTION_QUOTA_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(redemptionQuotaResponse);
            case LIBRARY_OBJECT:
                return (T) new RedemptionQuota(new JSONObject(redemptionQuotaResponse));
            default:
                return (T) redemptionQuotaResponse;
        }
    }

    /**
     * Method to create a left quota query
     *
     * @param productId:  the product id to insert
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return query as {@link Params}
     */
    private Params getLeftQuotaQuery(String productId, long recvWindow) {
        Params query = createTimestampPayload(null);
        query.addParam("productId", productId);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        return query;
    }

    /**
     * Request to redeem quota of flexible product
     *
     * @param product: the product to redeem
     * @param amount:  the amount of the redemption
     * @param type:    the type of the redemption
     * @return whether the redemption has been success as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-user_data">
     * Redeem Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/redeem")
    public boolean redeemFlexibleProduct(FlexibleProduct product, double amount, RedemptionType type) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), amount, type);
    }

    /**
     * Request to redeem quota of flexible product
     *
     * @param productId: the product id to redeem
     * @param amount:    the amount of the redemption
     * @param type:      the type of the redemption
     * @return whether the redemption has been success as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-user_data">
     * Redeem Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/redeem")
    public boolean redeemFlexibleProduct(String productId, double amount, RedemptionType type) throws Exception {
        return redeemFlexibleProduct(productId, amount, type, -1);
    }

    /**
     * Request to redeem quota of flexible product
     *
     * @param product:    the product to redeem
     * @param amount:     the amount of the redemption
     * @param type:       the type of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return whether the redemption has been success as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-user_data">
     * Redeem Flexible Product (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/redeem")
    public boolean redeemFlexibleProduct(FlexibleProduct product, double amount, RedemptionType type,
                                         long recvWindow) throws Exception {
        return redeemFlexibleProduct(product.getProductId(), amount, type, recvWindow);
    }

    /**
     * Request to redeem quota of flexible product
     *
     * @param productId:  the product id to redeem
     * @param amount:     the amount of the redemption
     * @param type:       the type of the redemption
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return whether the redemption has been success as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-user_data">
     * Redeem Flexible Product (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/daily/redeem")
    public boolean redeemFlexibleProduct(String productId, double amount, RedemptionType type,
                                         long recvWindow) throws Exception {
        Params payload = createFlexibleOpePayload(productId, amount, recvWindow);
        payload.addParam("type", type);
        sendPostSignedRequest(LENDING_DAILY_REDEEM_ENDPOINT, payload);
        return apiRequest.getResponseStatusCode() == 200;
    }

    /**
     * Method to create a payload
     *
     * @param productId:  the product id to insert
     * @param amount:     the amount of the operation
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return payload as {@link Params}
     */
    private Params createFlexibleOpePayload(String productId, double amount, long recvWindow) {
        Params payload = new Params();
        payload.addParam("productId", productId);
        payload.addParam("amount", amount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return payload;
    }

    /**
     * Request to get the flexible product position <br>
     * No-any params required
     *
     * @return flexible product position as {@link ArrayList} of {@link FlexibleProductPosition} custom object
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
     * Get Flexible Product Position (USER_DATA) </a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/token/position")
    public ArrayList<FlexibleProductPosition> getFlexibleProductPosition() throws Exception {
        return getFlexibleProductPosition(LIBRARY_OBJECT);
    }

    /**
     * Request to get the flexible product position
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
     * Get Flexible Product Position (USER_DATA) </a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/token/position")
    public <T> T getFlexibleProductPosition(ReturnFormat format) throws Exception {
        return getFlexibleProductPosition(null, format);
    }

    /**
     * Request to get the flexible product position
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return flexible product position as {@link ArrayList} of {@link FlexibleProductPosition} custom object
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
     * Get Flexible Product Position (USER_DATA) </a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/token/position")
    public ArrayList<FlexibleProductPosition> getFlexibleProductPosition(Params extraParams) throws Exception {
        return getFlexibleProductPosition(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the flexible product position
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
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
     * Get Flexible Product Position (USER_DATA) </a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/daily/token/position")
    public <T> T getFlexibleProductPosition(Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetSignedRequest(LENDING_DAILY_TOKEN_POSITION_ENDPOINT,
                createTimestampPayload(extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<FlexibleProductPosition> positions = new ArrayList<>();
                JSONArray jPositions = new JSONArray(listResponse);
                for (int j = 0; j < jPositions.length(); j++)
                    positions.add(new FlexibleProductPosition(jPositions.getJSONObject(j)));
                return (T) positions;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the fixed and activity project list
     *
     * @param type: the activity type to fetch
     * @return fixed and activity project list as {@link ArrayList} of {@link FixedActivityProject} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-and-activity-project-list-user_data">
     * Get Fixed and Activity Project List(USER_DATA) </a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/list")
    public ArrayList<FixedActivityProject> getFixedActivityProjectList(SavingActivityType type) throws Exception {
        return getFixedActivityProjectList(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the fixed and activity project list
     *
     * @param type:   the activity type to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return fixed and activity project list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-and-activity-project-list-user_data">
     * Get Fixed and Activity Project List(USER_DATA) </a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/list")
    public <T> T getFixedActivityProjectList(SavingActivityType type, ReturnFormat format) throws Exception {
        return getFixedActivityProjectList(type, null, format);
    }

    /**
     * Request to get the fixed and activity project list
     *
     * @param type:        the activity type to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> constants available {@link FlexibleProductStatus} - [ENUM, default ALL]
     *                           </li>
     *                           <li>
     *                                {@code "isSortAsc"} -> whether is sort asc - [BOOLEAN, default true]
     *                           </li>
     *                           <li>
     *                                {@code "sortBy"} -> constants available {@link SortBy} - [ENUM, default START_TIME]
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
     * @return fixed and activity project list as {@link ArrayList} of {@link FixedActivityProject} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-and-activity-project-list-user_data">
     * Get Fixed and Activity Project List(USER_DATA) </a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/list")
    public ArrayList<FixedActivityProject> getFixedActivityProjectList(SavingActivityType type,
                                                                       Params extraParams) throws Exception {
        return getFixedActivityProjectList(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the fixed and activity project list
     *
     * @param type:        the activity type to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> constants available {@link FlexibleProductStatus} - [ENUM, default ALL]
     *                           </li>
     *                           <li>
     *                                {@code "isSortAsc"} -> whether is sort asc - [BOOLEAN, default true]
     *                           </li>
     *                           <li>
     *                                {@code "sortBy"} -> constants available {@link SortBy} - [ENUM, default START_TIME]
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
     * @return fixed and activity project list as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-and-activity-project-list-user_data">
     * Get Fixed and Activity Project List(USER_DATA) </a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/list")
    public <T> T getFixedActivityProjectList(SavingActivityType type, Params extraParams,
                                             ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("type", type);
        String listResponse = sendGetSignedRequest(LENDING_PROJECT_LIST_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<FixedActivityProject> projects = new ArrayList<>();
                JSONArray jProjects = new JSONArray(listResponse);
                for (int j = 0; j < jProjects.length(); j++)
                    projects.add(new FixedActivityProject(jProjects.getJSONObject(j)));
                return (T) projects;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param project: the project to purchase
     * @param lot:     the lot of the purchase
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public long purchaseFixedActivityProject(FixedActivityProject project, int lot) throws Exception {
        return parseLong(purchaseFixedActivityProject(project.getProjectId(), lot, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param project: the project to purchase
     * @param lot:     the lot of the purchase
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public <T> T purchaseFixedActivityProject(FixedActivityProject project, int lot, ReturnFormat format) throws Exception {
        return purchaseFixedActivityProject(project.getProjectId(), lot, format);
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param projectId: the project id to purchase
     * @param lot:       the lot of the purchase
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public long purchaseFixedActivityProject(String projectId, int lot) throws Exception {
        return parseLong(purchaseFixedActivityProject(projectId, lot, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param projectId: the project id to purchase
     * @param lot:       the lot of the purchase
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public <T> T purchaseFixedActivityProject(String projectId, int lot, ReturnFormat format) throws Exception {
        return purchaseFixedActivityProject(projectId, lot, -1, format);
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param project:    the project to purchase
     * @param lot:        the lot of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public long purchaseFixedActivityProject(FixedActivityProject project, int lot, long recvWindow) throws Exception {
        return parseLong(purchaseFixedActivityProject(project.getProjectId(), lot, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param project:    the project to purchase
     * @param lot:        the lot of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public <T> T purchaseFixedActivityProject(FixedActivityProject project, int lot, long recvWindow,
                                              ReturnFormat format) throws Exception {
        return purchaseFixedActivityProject(project.getProjectId(), lot, recvWindow, format);
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param projectId:  the project id to purchase
     * @param lot:        the lot of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return purchase id as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public long purchaseFixedActivityProject(String projectId, int lot, long recvWindow) throws Exception {
        return parseLong(purchaseFixedActivityProject(projectId, lot, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to purchase fixed/activity project
     *
     * @param projectId:  the project id to purchase
     * @param lot:        the lot of the purchase
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-fixed-activity-project-user_data">
     * Purchase Fixed/Activity Project (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/customizedFixed/purchase")
    public <T> T purchaseFixedActivityProject(String projectId, int lot, long recvWindow,
                                              ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("projectId", projectId);
        payload.addParam("lot", lot);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return returnPurchaseId(sendPostSignedRequest(LENDING_CUSTOMIZED_FIXED_PURCHASE_ENDPOINT, payload), format);
    }

    /**
     * Method to create a purchase id
     *
     * @param purchaseResponse: obtained from Binance's response
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return purchase id as {@code "format"} defines
     */
    @Returner
    private <T> T returnPurchaseId(String purchaseResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(purchaseResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(new JSONObject(purchaseResponse).getLong("purchaseId"));
            default:
                return (T) purchaseResponse;
        }
    }

    /**
     * Request to get the fixed/activity project position <br>
     * No-any params required
     *
     * @return fixed/activity project position as {@link ArrayList} of {@link FixedActivityProjectPosition} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-activity-project-position-user_data">
     * Get Fixed/Activity Project Position (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/position/list")
    public ArrayList<FixedActivityProjectPosition> getFixedActivityProjectPosition() throws Exception {
        return getFixedActivityProjectPosition(LIBRARY_OBJECT);
    }

    /**
     * Request to get the fixed/activity project position
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return fixed/activity project position as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-activity-project-position-user_data">
     * Get Fixed/Activity Project Position (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/position/list")
    public <T> T getFixedActivityProjectPosition(ReturnFormat format) throws Exception {
        return getFixedActivityProjectPosition(null, format);
    }

    /**
     * Request to get the fixed/activity project position
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "projectId"} -> project id from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> constants available {@link SavingStatus} - [ENUM, default ALL]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return fixed/activity project position as {@link ArrayList} of {@link FixedActivityProjectPosition} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-activity-project-position-user_data">
     * Get Fixed/Activity Project Position (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/position/list")
    public ArrayList<FixedActivityProjectPosition> getFixedActivityProjectPosition(Params extraParams) throws Exception {
        return getFixedActivityProjectPosition(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the fixed/activity project position
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "projectId"} -> project id from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> constants available {@link SavingStatus} - [ENUM, default ALL]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return fixed/activity project position as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-activity-project-position-user_data">
     * Get Fixed/Activity Project Position (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/project/position/list")
    public <T> T getFixedActivityProjectPosition(Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetSignedRequest(LENDING_PROJECT_POSITION_LIST_ENDPOINT,
                createTimestampPayload(extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<FixedActivityProjectPosition> positions = new ArrayList<>();
                JSONArray jPositions = new JSONArray(listResponse);
                for (int j = 0; j < jPositions.length(); j++)
                    positions.add(new FixedActivityProjectPosition(jPositions.getJSONObject(j)));
                return (T) positions;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the lending account <br>
     * No-any params required
     *
     * @return lending account as {@link LendingAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#lending-account-user_data">
     * Lending Account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/account")
    public LendingAccount getLendingAccount() throws Exception {
        return getLendingAccount(LIBRARY_OBJECT);
    }

    /**
     * Request to get the lending account
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return lending account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#lending-account-user_data">
     * Lending Account (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/account")
    public <T> T getLendingAccount(ReturnFormat format) throws Exception {
        return getLendingAccount(-1, format);
    }

    /**
     * Request to get the lending account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return lending account as {@link LendingAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#lending-account-user_data">
     * Lending Account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/account")
    public LendingAccount getLendingAccount(long recvWindow) throws Exception {
        return getLendingAccount(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get the lending account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return lending account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#lending-account-user_data">
     * Lending Account (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/account")
    public <T> T getLendingAccount(long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        String accountResponse = sendGetSignedRequest(LENDING_UNION_ACCOUNT_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountResponse);
            case LIBRARY_OBJECT:
                return (T) new LendingAccount(new JSONObject(accountResponse));
            default:
                return (T) accountResponse;
        }
    }

    /**
     * Request to get the purchase record
     *
     * @param type: type of the purchase records to fetch
     * @return the purchase records as {@link ArrayList} of {@link PurchaseRecord} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-purchase-record-user_data">
     * Get Purchase Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/purchaseRecord")
    public ArrayList<PurchaseRecord> getPurchaseRecord(SavingActivityType type) throws Exception {
        return getPurchaseRecord(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the purchase record
     *
     * @param type:   type of the purchase records to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return the purchase records as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-purchase-record-user_data">
     * Get Purchase Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/purchaseRecord")
    public <T> T getPurchaseRecord(SavingActivityType type, ReturnFormat format) throws Exception {
        return getPurchaseRecord(type, null, format);
    }

    /**
     * Request to get the purchase record
     *
     * @param type:        type of the purchase records to fetch
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return the purchase records as {@link ArrayList} of {@link PurchaseRecord} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-purchase-record-user_data">
     * Get Purchase Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/purchaseRecord")
    public ArrayList<PurchaseRecord> getPurchaseRecord(SavingActivityType type, Params extraParams) throws Exception {
        return getPurchaseRecord(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the purchase record
     *
     * @param type:        type of the purchase records to fetch
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
     * @return the purchase records as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-purchase-record-user_data">
     * Get Purchase Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/purchaseRecord")
    public <T> T getPurchaseRecord(SavingActivityType type, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("lendingType", type);
        String purchaseResponse = sendGetSignedRequest(LENDING_UNION_PURCHASE_RECORD_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(purchaseResponse);
            case LIBRARY_OBJECT:
                ArrayList<PurchaseRecord> records = new ArrayList<>();
                JSONArray jRecords = new JSONArray(purchaseResponse);
                for (int j = 0; j < jRecords.length(); j++)
                    records.add(new PurchaseRecord(jRecords.getJSONObject(j)));
                return (T) records;
            default:
                return (T) purchaseResponse;
        }
    }

    /**
     * Request to get the redemption record
     *
     * @param type: type of the redemption records to fetch
     * @return the redemption records as {@link ArrayList} of:
     * <ul>
     *     <li>
     *         {@link SavingActivityType#DAILY} -> {@link FlexibleProductRedemption} custom object
     *     </li>
     *     <li>
     *         Others {@link SavingActivityType} -> {@link FixedActivityProductRedemption} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
     * Get Redemption Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/redemptionRecord")
    public <T> ArrayList<T> getRedemptionRecord(SavingActivityType type) throws Exception {
        return getRedemptionRecord(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the redemption record
     *
     * @param type:   type of the redemption records to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return redemption records as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
     * Get Redemption Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/redemptionRecord")
    public <T> T getRedemptionRecord(SavingActivityType type, ReturnFormat format) throws Exception {
        return getRedemptionRecord(type, null, format);
    }

    /**
     * Request to get the redemption record
     *
     * @param type:        type of the redemption records to fetch
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return the redemption records as {@link ArrayList} of:
     * <ul>
     *     <li>
     *         {@link SavingActivityType#DAILY} -> {@link FlexibleProductRedemption} custom object
     *     </li>
     *     <li>
     *         Others {@link SavingActivityType} -> {@link FixedActivityProductRedemption} custom object
     *     </li>
     * </ul>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
     * Get Redemption Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/redemptionRecord")
    public <T> ArrayList<T> getRedemptionRecord(SavingActivityType type, Params extraParams) throws Exception {
        return getRedemptionRecord(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the redemption record
     *
     * @param type:        type of the redemption records to fetch
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
     * @return redemption records as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
     * Get Redemption Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/redemptionRecord")
    public <T> T getRedemptionRecord(SavingActivityType type, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("lendingType", type);
        String redemptionResponse = sendGetSignedRequest(LENDING_UNION_REDEMPTION_RECORD_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(redemptionResponse);
            case LIBRARY_OBJECT:
                ArrayList<T> records = new ArrayList<>();
                JSONArray jRecords = new JSONArray(redemptionResponse);
                if (type == DAILY) {
                    for (int j = 0; j < jRecords.length(); j++)
                        records.add((T) new FlexibleProductRedemption(jRecords.getJSONObject(j)));
                } else {
                    for (int j = 0; j < jRecords.length(); j++)
                        records.add((T) new FixedActivityProductRedemption(jRecords.getJSONObject(j)));
                }
                return (T) records;
            default:
                return (T) redemptionResponse;
        }
    }

    /**
     * Request to get the interest history
     *
     * @param type: type of the activities to fetch
     * @return interest history as {@link ArrayList} of {@link SavingInterest} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2">
     * Get Interest History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/interestHistory")
    public ArrayList<SavingInterest> getInterestHistory(SavingActivityType type) throws Exception {
        return getInterestHistory(type, LIBRARY_OBJECT);
    }

    /**
     * Request to get the interest history
     *
     * @param type:   type of the activities to fetch
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return interest history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2">
     * Get Interest History (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/interestHistory")
    public <T> T getInterestHistory(SavingActivityType type, ReturnFormat format) throws Exception {
        return getInterestHistory(type, null, format);
    }

    /**
     * Request to get the interest history
     *
     * @param type:        type of the activities to fetch
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
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return interest history as {@link ArrayList} of {@link SavingInterest} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2">
     * Get Interest History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/interestHistory")
    public ArrayList<SavingInterest> getInterestHistory(SavingActivityType type, Params extraParams) throws Exception {
        return getInterestHistory(type, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the interest history
     *
     * @param type:        type of the activities to fetch
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
     * @return interest history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2">
     * Get Interest History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/union/interestHistory")
    public <T> T getInterestHistory(SavingActivityType type, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("lendingType", type);
        String historyResponse = sendGetSignedRequest(LENDING_UNION_INTEREST_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(historyResponse);
            case LIBRARY_OBJECT:
                ArrayList<SavingInterest> interests = new ArrayList<>();
                JSONArray jInterest = new JSONArray(historyResponse);
                for (int j = 0; j < jInterest.length(); j++)
                    interests.add(new SavingInterest(jInterest.getJSONObject(j)));
                return (T) interests;
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param project: the project to change the position
     * @param lot:     the lot to change the position
     * @return change result as {@link ChangePositionResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public ChangePositionResult changeFixedActivityPosition(FixedActivityProject project, int lot) throws Exception {
        return changeFixedActivityPosition(project.getProjectId(), lot, LIBRARY_OBJECT);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param project: the project to change the position
     * @param lot:     the lot to change the position
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return change result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public <T> T changeFixedActivityPosition(FixedActivityProject project, int lot, ReturnFormat format) throws Exception {
        return changeFixedActivityPosition(project.getProjectId(), lot, format);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param projectId: the project id to change the position
     * @param lot:       the lot to change the position
     * @return change result as {@link ChangePositionResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public ChangePositionResult changeFixedActivityPosition(String projectId, int lot) throws Exception {
        return changeFixedActivityPosition(projectId, lot, LIBRARY_OBJECT);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param projectId: the project id to change the position
     * @param lot:       the lot to change the position
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return change result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public <T> T changeFixedActivityPosition(String projectId, int lot, ReturnFormat format) throws Exception {
        return changeFixedActivityPosition(projectId, lot, null, format);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param project:     the project to change the position
     * @param lot:         the lot to change the position
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> for fixed position - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return change result as {@link ChangePositionResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public ChangePositionResult changeFixedActivityPosition(FixedActivityProject project, int lot,
                                                            Params extraParams) throws Exception {
        return changeFixedActivityPosition(project.getProjectId(), lot, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param project:     the project to change the position
     * @param lot:         the lot to change the position
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> for fixed position - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return change result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public <T> T changeFixedActivityPosition(FixedActivityProject project, int lot, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return changeFixedActivityPosition(project.getProjectId(), lot, extraParams, format);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param projectId:   the project id to change the position
     * @param lot:         the lot to change the position
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> for fixed position - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return change result as {@link ChangePositionResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public ChangePositionResult changeFixedActivityPosition(String projectId, int lot, Params extraParams) throws Exception {
        return changeFixedActivityPosition(projectId, lot, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to change the fixed/activity position to daily position
     *
     * @param projectId:   the project id to change the position
     * @param lot:         the lot to change the position
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> for fixed position - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return change result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-fixed-activity-position-to-daily-position-user_data">
     * Change Fixed/Activity Position to Daily Position(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/positionChanged")
    public <T> T changeFixedActivityPosition(String projectId, int lot, Params extraParams,
                                             ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("projectId", projectId);
        extraParams.addParam("lot", lot);
        String resultResponse = sendPostSignedRequest(LENDING_POSITION_CHANGED_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(resultResponse);
            case LIBRARY_OBJECT:
                return (T) new ChangePositionResult(new JSONObject(resultResponse));
            default:
                return (T) resultResponse;
        }
    }

}
