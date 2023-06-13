package com.tecknobit.binancemanager.managers.signedmanagers.staking;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.PurchaseStakingProductResult;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingHistoryRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct.ProductType;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingHistoryRecord.TxnType;

/**
 * The {@code BinanceStakingManager} class is useful to manage staking endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#staking-endpoints">
 * Staking Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 */
public class BinanceStakingManager extends BinanceSignedManager {

    /**
     * {@code STAKING_PRODUCT_LIST_ENDPOINT} is constant for STAKING_PRODUCT_LIST_ENDPOINT's endpoint
     */
    public static final String STAKING_PRODUCT_LIST_ENDPOINT = "/sapi/v1/staking/productList";

    /**
     * {@code STAKING_PURCHASE_ENDPOINT} is constant for STAKING_PURCHASE_ENDPOINT's endpoint
     */
    public static final String STAKING_PURCHASE_ENDPOINT = "/sapi/v1/staking/purchase";

    /**
     * {@code STAKING_REDEEM_ENDPOINT} is constant for STAKING_REDEEM_ENDPOINT's endpoint
     */
    public static final String STAKING_REDEEM_ENDPOINT = "/sapi/v1/staking/redeem";

    /**
     * {@code STAKING_POSITION_ENDPOINT} is constant for STAKING_POSITION_ENDPOINT's endpoint
     */
    public static final String STAKING_POSITION_ENDPOINT = "/sapi/v1/staking/position";

    /**
     * {@code STAKING_RECORD_ENDPOINT} is constant for STAKING_RECORD_ENDPOINT's endpoint
     */
    public static final String STAKING_RECORD_ENDPOINT = "/sapi/v1/staking/stakingRecord";

    /**
     * {@code SET_AUTO_STAKING_ENDPOINT} is constant for SET_AUTO_STAKING_ENDPOINT's endpoint
     */
    public static final String SET_AUTO_STAKING_ENDPOINT = "/sapi/v1/staking/setAutoStaking";

    /**
     * {@code PERSONAL_LEFT_QUOTA_ENDPOINT} is constant for PERSONAL_LEFT_QUOTA_ENDPOINT's endpoint
     */
    public static final String PERSONAL_LEFT_QUOTA_ENDPOINT = "/sapi/v1/staking/personalLeftQuota";

    /**
     * Constructor to init a {@link BinanceStakingManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceStakingManager(String baseEndpoint, String defaultErrorMessage, int timeout,
                                 String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceStakingManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceStakingManager(String baseEndpoint, String defaultErrorMessage,
                                 String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceStakingManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceStakingManager(String baseEndpoint, int timeout, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceStakingManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceStakingManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceStakingManager} <br>
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
    public BinanceStakingManager() {
        super();
    }

    /**
     * Request to get available staking product list
     *
     * @param product: product type
     * @return staking product list as {@link ArrayList} of {@link StakingProduct} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-list-user_data">
     * Get Staking Product List(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/productList")
    public ArrayList<StakingProduct> getStakingProductList(ProductType product) throws Exception {
        return getStakingProductList(product, LIBRARY_OBJECT);
    }

    /**
     * Request to get available staking product list
     *
     * @param product: product type
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return staking product list {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-list-user_data">
     * Get Staking Product List(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/productList")
    public <T> T getStakingProductList(ProductType product, ReturnFormat format) throws Exception {
        return getStakingProductList(product, null, format);
    }

    /**
     * Request to get available staking product list
     *
     * @param product:     product type
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value from fetch the list- [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @return staking product list as {@link ArrayList} of {@link StakingProduct} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-list-user_data">
     * Get Staking Product List(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/productList")
    public ArrayList<StakingProduct> getStakingProductList(ProductType product, Params extraParams) throws Exception {
        return getStakingProductList(product, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get available staking product list
     *
     * @param product:     product type
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value from fetch the list- [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return staking product list {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-list-user_data">
     * Get Staking Product List(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/productList")
    public <T> T getStakingProductList(ProductType product, Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetSignedRequest(STAKING_PRODUCT_LIST_ENDPOINT, createStakingProductPayload(product,
                extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<StakingProduct> stakingProducts = new ArrayList<>();
                JSONArray jStaking = new JSONArray(listResponse);
                for (int j = 0; j < jStaking.length(); j++)
                    stakingProducts.add(new StakingProduct(jStaking.getJSONObject(j)));
                return (T) stakingProducts;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to purchase staking product
     *
     * @param product:   product type
     * @param productId: product id of the purchase
     * @param amount:    amount of the purchase
     * @return staking purchase result as {@link PurchaseStakingProductResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-staking-product-user_data">
     * Purchase Staking Product(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/purchase")
    public PurchaseStakingProductResult purchaseStakingProduct(ProductType product, long productId,
                                                               double amount) throws Exception {
        return purchaseStakingProduct(product, productId, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to purchase staking product
     *
     * @param product:   product type
     * @param productId: product id of the purchase
     * @param amount:    amount of the purchase
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return staking purchase result {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-staking-product-user_data">
     * Purchase Staking Product(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/purchase")
    public <T> T purchaseStakingProduct(ProductType product, long productId, double amount,
                                        ReturnFormat format) throws Exception {
        return purchaseStakingProduct(product, productId, amount, null, format);
    }

    /**
     * Request to purchase staking product
     *
     * @param product:     product type
     * @param productId:   product id of the purchase
     * @param amount:      amount of the purchase
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "renewable"} -> true or false - [BOOLEAN, default false]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @return staking purchase result as {@link PurchaseStakingProductResult} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-staking-product-user_data">
     * Purchase Staking Product(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/purchase")
    public PurchaseStakingProductResult purchaseStakingProduct(ProductType product, long productId, double amount,
                                                               Params extraParams) throws Exception {
        return purchaseStakingProduct(product, productId, amount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to purchase staking product
     *
     * @param product:     product type
     * @param productId:   product id of the purchase
     * @param amount:      amount of the purchase
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "renewable"} -> true or false - [BOOLEAN, default false]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return staking purchase result {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-staking-product-user_data">
     * Purchase Staking Product(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/purchase")
    public <T> T purchaseStakingProduct(ProductType product, long productId, double amount, Params extraParams,
                                        ReturnFormat format) throws Exception {
        extraParams = createStakingOpePayload(product, productId, extraParams);
        extraParams.addParam("amount", amount);
        String purchaseResponse = sendPostSignedRequest(STAKING_PURCHASE_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(purchaseResponse);
            case LIBRARY_OBJECT:
                return (T) new PurchaseStakingProductResult(new JSONObject(purchaseResponse));
            default:
                return (T) purchaseResponse;
        }
    }

    /**
     * Request to redeem staking product. Locked staking and Locked DeFI staking belong to early redemption, redeeming
     * in advance will result in loss of interest that you have earned
     *
     * @param product:   product type
     * @param productId: product identifier
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-staking-product-user_data">
     * Redeem Staking Product(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/redeem")
    public boolean redeemStakingProduct(ProductType product, long productId) throws Exception {
        return redeemStakingProduct(product, productId, null);
    }

    /**
     * Request to redeem staking product. Locked staking and Locked DeFI staking belong to early redemption, redeeming
     * in advance will result in loss of interest that you have earned
     *
     * @param product:     product type
     * @param productId:   product identifier
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "positionId"} -> mandatory if product is "STAKING" or "L_DEFI" - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "amount"} -> mandatory if product is "F_DEFI" - [DECIMALS]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-staking-product-user_data">
     * Redeem Staking Product(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/redeem")
    public boolean redeemStakingProduct(ProductType product, long productId, Params extraParams) throws Exception {
        sendPostSignedRequest(STAKING_REDEEM_ENDPOINT, createStakingOpePayload(product, productId, extraParams));
        return JsonHelper.getBoolean(apiRequest.getJSONResponse(), "success");
    }

    /**
     * Request to get staking product position
     *
     * @param product: product type
     * @return staking product positions list as {@link ArrayList} of {@link StakingProductPosition} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-position-user_data">
     * Get Staking Product Position(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/position")
    public ArrayList<StakingProductPosition> getStakingProductPosition(ProductType product) throws Exception {
        return getStakingProductPosition(product, LIBRARY_OBJECT);
    }

    /**
     * Request to get staking product position
     *
     * @param product: product type
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return staking product positions list {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-position-user_data">
     * Get Staking Product Position(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/position")
    public <T> T getStakingProductPosition(ProductType product, ReturnFormat format) throws Exception {
        return getStakingProductPosition(product, null, format);
    }

    /**
     * Request to get staking product position
     *
     * @param product:     product type
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset value from fetch the list- [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @return staking product positions list as {@link ArrayList} of {@link StakingProductPosition} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-position-user_data">
     * Get Staking Product Position(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/position")
    public ArrayList<StakingProductPosition> getStakingProductPosition(ProductType product,
                                                                       Params extraParams) throws Exception {
        return getStakingProductPosition(product, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get staking product position
     *
     * @param product:     product type
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "productId"} -> product identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "asset"} -> asset value from fetch the list- [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> currently querying page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return staking product positions list {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-position-user_data">
     * Get Staking Product Position(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/position")
    public <T> T getStakingProductPosition(ProductType product, Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetSignedRequest(STAKING_POSITION_ENDPOINT, createStakingProductPayload(product,
                extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<StakingProductPosition> stakingProductPositions = new ArrayList<>();
                JSONArray jStaking = new JSONArray(listResponse);
                for (int j = 0; j < jStaking.length(); j++)
                    stakingProductPositions.add(new StakingProductPosition(jStaking.getJSONObject(j)));
                return (T) stakingProductPositions;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get staking history
     *
     * @param product: product type
     * @param txnType: txn type
     * @return staking history as {@link ArrayList} of {@link StakingHistoryRecord} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-history-user_data">
     * Get Staking History(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/stakingRecord")
    public ArrayList<StakingHistoryRecord> getStakingHistory(ProductType product, TxnType txnType) throws Exception {
        return getStakingHistory(product, txnType, LIBRARY_OBJECT);
    }

    /**
     * Request to get staking history
     *
     * @param product: product type
     * @param txnType: txn type
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return staking history {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-history-user_data">
     * Get Staking History(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/stakingRecord")
    public <T> T getStakingHistory(ProductType product, TxnType txnType, ReturnFormat format) throws Exception {
        return getStakingHistory(product, txnType, null, format);
    }

    /**
     * Request to get staking history
     *
     * @param product:     product type
     * @param txnType:     txn type
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value from fetch the list- [LONG]
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
     *                                {@code "size"} -> size of the results - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @return staking history as {@link ArrayList} of {@link StakingHistoryRecord} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-history-user_data">
     * Get Staking History(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/stakingRecord")
    public ArrayList<StakingHistoryRecord> getStakingHistory(ProductType product, TxnType txnType,
                                                             Params extraParams) throws Exception {
        return getStakingHistory(product, txnType, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get staking history
     *
     * @param product:     product type
     * @param txnType:     txn type
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> asset value from fetch the list- [LONG]
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
     *                                {@code "size"} -> size of the results - [LONG, default 10]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return staking history {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-history-user_data">
     * Get Staking History(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/stakingRecord")
    public <T> T getStakingHistory(ProductType product, TxnType txnType, Params extraParams,
                                   ReturnFormat format) throws Exception {
        extraParams = createStakingProductPayload(product, extraParams);
        extraParams.addParam("txnType", txnType);
        String listResponse = sendGetSignedRequest(STAKING_RECORD_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<StakingHistoryRecord> stakingHistoryRecords = new ArrayList<>();
                JSONArray jStaking = new JSONArray(listResponse);
                for (int j = 0; j < jStaking.length(); j++)
                    stakingHistoryRecords.add(new StakingHistoryRecord(jStaking.getJSONObject(j)));
                return (T) stakingHistoryRecords;
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to set auto staking on locked staking or locked DeFi staking
     *
     * @param product:   product type
     * @param position:  position where set the auto-staking
     * @param renewable: whether the staking is renewable
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-auto-staking-user_data">
     * Set Auto Staking(USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/setAutoStaking")
    public boolean setAutoStaking(ProductType product, StakingProductPosition position, boolean renewable) throws Exception {
        return setAutoStaking(product, position.getPositionId(), renewable, -1);
    }

    /**
     * Request to set auto staking on locked staking or locked DeFi staking
     *
     * @param product:    product type
     * @param positionId: position identifier
     * @param renewable:  whether the staking is renewable
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-auto-staking-user_data">
     * Set Auto Staking(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/setAutoStaking")
    public boolean setAutoStaking(ProductType product, long positionId, boolean renewable) throws Exception {
        return setAutoStaking(product, positionId, renewable, -1);
    }

    /**
     * Request to set auto staking on locked staking or locked DeFi staking
     *
     * @param product:    product type
     * @param position:   position where set the auto-staking
     * @param renewable:  whether the staking is renewable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-auto-staking-user_data">
     * Set Auto Staking(USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/setAutoStaking")
    public boolean setAutoStaking(ProductType product, StakingProductPosition position, boolean renewable,
                                  long recvWindow) throws Exception {
        return setAutoStaking(product, position.getPositionId(), renewable, recvWindow);
    }

    /**
     * Request to set auto staking on locked staking or locked DeFi staking
     *
     * @param product:    product type
     * @param positionId: position identifier
     * @param renewable:  whether the staking is renewable
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#set-auto-staking-user_data">
     * Set Auto Staking(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/staking/setAutoStaking")
    public boolean setAutoStaking(ProductType product, long positionId, boolean renewable, long recvWindow) throws Exception {
        Params payload = createStakingProductPayload(product, null);
        payload.addParam("positionId", positionId);
        payload.addParam("renewable", renewable);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        sendPostSignedRequest(SET_AUTO_STAKING_ENDPOINT, payload);
        return JsonHelper.getBoolean(apiRequest.getJSONResponse(), "success");
    }

    /**
     * Method to create a staking product payload
     *
     * @param product:     product type
     * @param extraParams: extra params of the request
     * @return payload as {@link Params}
     */
    private Params createStakingProductPayload(ProductType product, Params extraParams) {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        extraParams.addParam("product", product);
        return extraParams;
    }

    /**
     * Request to get personal left quota of Staking Product
     *
     * @param product:   product type
     * @param productId: product identifier
     * @return personal Left Quota as {@link ArrayList} of {@link Double}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-personal-left-quota-of-staking-product-user_data">
     * Get Personal Left Quota of Staking Product(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/personalLeftQuota")
    public ArrayList<Double> getPersonalLeftQuota(ProductType product, long productId) throws Exception {
        return getPersonalLeftQuota(product, productId, LIBRARY_OBJECT);
    }

    /**
     * Request to get personal left quota of Staking Product
     *
     * @param product:   product type
     * @param productId: product identifier
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return personal left quota as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-personal-left-quota-of-staking-product-user_data">
     * Get Personal Left Quota of Staking Product(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/personalLeftQuota")
    public <T> T getPersonalLeftQuota(ProductType product, long productId, ReturnFormat format) throws Exception {
        return getPersonalLeftQuota(product, productId, -1, format);
    }

    /**
     * Request to get personal left quota of Staking Product
     *
     * @param product:    product type
     * @param productId:  product identifier
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return personal Left Quota as {@link ArrayList} of {@link Double}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-personal-left-quota-of-staking-product-user_data">
     * Get Personal Left Quota of Staking Product(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/personalLeftQuota")
    public ArrayList<Double> getPersonalLeftQuota(ProductType product, long productId, long recvWindow) throws Exception {
        return getPersonalLeftQuota(product, productId, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get personal left quota of Staking Product
     *
     * @param product:    product type
     * @param productId:  product identifier
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return personal left quota as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-personal-left-quota-of-staking-product-user_data">
     * Get Personal Left Quota of Staking Product(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/staking/personalLeftQuota")
    public <T> T getPersonalLeftQuota(ProductType product, long productId, long recvWindow,
                                      ReturnFormat format) throws Exception {
        Params query = createStakingOpePayload(product, productId, null);
        query.addParam("timestamp", getServerTime());
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        String quotesList = sendGetSignedRequest(PERSONAL_LEFT_QUOTA_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONArray(quotesList);
            case LIBRARY_OBJECT:
                ArrayList<Double> quotes = new ArrayList<>();
                JSONArray jQuotesList = new JSONArray(quotesList);
                for (int j = 0; j < jQuotesList.length(); j++)
                    quotes.add(jQuotesList.getJSONObject(j).getDouble("leftPersonalQuota"));
                return (T) quotes;
            default:
                return (T) quotesList;
        }
    }

    /**
     * Method to create a staking operation product payload
     *
     * @param product:     product type
     * @param productId:   product identifier
     * @param extraParams: extra params of the request
     * @return payload as {@link Params}
     */
    private Params createStakingOpePayload(ProductType product, long productId, Params extraParams) {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("product", product);
        extraParams.addParam("productId", productId);
        return extraParams;
    }

}
