package com.tecknobit.binancemanager.managers.signedmanagers.staking;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.PurchaseStakingProductResult;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingHistoryRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct.ProductType;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.*;
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
 * @see Manager
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
     * {@code ETH_STAKE_ENDPOINT} is constant for ETH_STAKE_ENDPOINT's endpoint
     */
    public static final String ETH_STAKE_ENDPOINT = "/sapi/v1/eth-staking/eth/stake";

    /**
     * {@code ETH_REDEEM_ENDPOINT} is constant for ETH_REDEEM_ENDPOINT's endpoint
     */
    public static final String ETH_REDEEM_ENDPOINT = "/sapi/v1/eth-staking/eth/redeem";

    /**
     * {@code ETH_STAKING_HISTORY_ENDPOINT} is constant for ETH_STAKING_HISTORY_ENDPOINT's endpoint
     */
    public static final String ETH_STAKING_HISTORY_ENDPOINT = "/sapi/v1/eth-staking/eth/history/stakingHistory";

    /**
     * {@code ETH_REDEMPTION_HISTORY_ENDPOINT} is constant for ETH_REDEMPTION_HISTORY_ENDPOINT's endpoint
     */
    public static final String ETH_REDEMPTION_HISTORY_ENDPOINT = "/sapi/v1/eth-staking/eth/history/redemptionHistory";

    /**
     * {@code ETH_REWARDS_HISTORY_ENDPOINT} is constant for ETH_REWARDS_HISTORY_ENDPOINT's endpoint
     */
    public static final String ETH_REWARDS_HISTORY_ENDPOINT = "/sapi/v1/eth-staking/eth/history/rewardsHistory";

    /**
     * {@code ETH_QUOTA_ENDPOINT} is constant for ETH_QUOTA_ENDPOINT's endpoint
     */
    public static final String ETH_QUOTA_ENDPOINT = "/sapi/v1/eth-staking/eth/quota";

    /**
     * {@code ETH_RATE_HISTORY_ENDPOINT} is constant for ETH_RATE_HISTORY_ENDPOINT's endpoint
     */
    public static final String ETH_RATE_HISTORY_ENDPOINT = "/sapi/v1/eth-staking/eth/history/rateHistory";

    /**
     * {@code ETH_STAKING_ACCOUNT_ENDPOINT} is constant for ETH_STAKING_ACCOUNT_ENDPOINT's endpoint
     */
    public static final String ETH_STAKING_ACCOUNT_ENDPOINT = "/sapi/v1/eth-staking/account";

    /**
     * {@code WBETH_WRAP_ENDPOINT} is constant for WBETH_WRAP_ENDPOINT's endpoint
     */
    public static final String WBETH_WRAP_ENDPOINT = "/sapi/v1/eth-staking/wbeth/wrap";

    /**
     * {@code WBETH_UNWRAP_ENDPOINT} is constant for WBETH_UNWRAP_ENDPOINT's endpoint
     */
    public static final String WBETH_UNWRAP_ENDPOINT = "/sapi/v1/eth-staking/wbeth/unwrap";

    /**
     * {@code WBETH_WRAP_HISTORY_ENDPOINT} is constant for WBETH_WRAP_HISTORY_ENDPOINT's endpoint
     */
    public static final String WBETH_WRAP_HISTORY_ENDPOINT = "/sapi/v1/eth-staking/wbeth/history/wrapHistory";

    /**
     * {@code WBETH_UNWRAP_HISTORY_ENDPOINT} is constant for WBETH_UNWRAP_HISTORY_ENDPOINT's endpoint
     */
    public static final String WBETH_UNWRAP_HISTORY_ENDPOINT = "/sapi/v1/eth-staking/wbeth/history/unwrapHistory";

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
        return isSuccessResponse(SET_AUTO_STAKING_ENDPOINT, payload);
    }

    /**
     * Method to create a staking product payload
     *
     * @param product:     product type
     * @param extraParams: extra params of the request
     * @return payload as {@link Params}
     */
    private Params createStakingProductPayload(ProductType product, Params extraParams) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("product", product);
        return extraParams;
    }

    /**
     * Request to get personal left quota of Staking Product
     *
     * @param product:   product type
     * @param productId: product identifier
     * @return personal left quota as {@link ArrayList} of {@link Double}
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
     * @return personal left quota as {@link ArrayList} of {@link Double}
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

    /**
     * Request to subscribe ETH Staking
     *
     * @param amount: amount to subscribe
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-eth-staking-trade">
     * Subscribe ETH Staking(TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/eth/stake")
    public boolean subscribeETHStaking(double amount) throws Exception {
        return subscribeETHStaking(amount, -1);
    }

    /**
     * Request to subscribe ETH Staking
     *
     * @param amount:     amount to subscribe
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-eth-staking-trade">
     * Subscribe ETH Staking(TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/eth/stake")
    public boolean subscribeETHStaking(double amount, long recvWindow) throws Exception {
        return isSuccessResponse(ETH_STAKE_ENDPOINT, createETHOperationPayload(amount, recvWindow));
    }

    /**
     * Request to redeem ETH
     *
     * @param amount: amount to redeem
     * @return ETH redeemed as {@link ETHRedeem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-eth-trade">
     * Redeem ETH (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/eth/redeem")
    public ETHRedeem redeemETHStaking(double amount) throws Exception {
        return redeemETHStaking(amount, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem ETH
     *
     * @param amount: amount to redeem
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ETH redeemed as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-eth-trade">
     * Redeem ETH (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/eth/redeem")
    public <T> T redeemETHStaking(double amount, ReturnFormat format) throws Exception {
        return redeemETHStaking(amount, -1, format);
    }

    /**
     * Request to redeem ETH
     *
     * @param amount:     amount to redeem
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return ETH redeemed as {@link ETHRedeem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-eth-trade">
     * Redeem ETH (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/eth/redeem")
    public ETHRedeem redeemETHStaking(double amount, long recvWindow) throws Exception {
        return redeemETHStaking(amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem ETH
     *
     * @param amount:     amount to redeem
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return ETH redeemed as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-eth-trade">
     * Redeem ETH (TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/eth/redeem")
    public <T> T redeemETHStaking(double amount, long recvWindow, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendPostSignedRequest(ETH_REDEEM_ENDPOINT, createETHOperationPayload(amount,
                recvWindow)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ETHRedeem(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get ETH staking history<br>
     * No-any params required
     *
     * @return ETH staking history as {@link ETHStakingHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-staking-history-user_data">
     * Get ETH staking history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/stakingHistory")
    public ETHStakingHistory getETHStakingHistory() throws Exception {
        return getETHStakingHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH staking history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ETH staking history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-staking-history-user_data">
     * Get ETH staking history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/stakingHistory")
    public <T> T getETHStakingHistory(ReturnFormat format) throws Exception {
        return getETHStakingHistory(null, format);
    }

    /**
     * Request to get ETH staking history
     *
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
     *                     </ul>
     * @return ETH staking history as {@link ETHStakingHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-staking-history-user_data">
     * Get ETH staking history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/stakingHistory")
    public ETHStakingHistory getETHStakingHistory(Params queryParams) throws Exception {
        return getETHStakingHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH staking history
     *
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
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return ETH staking history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-staking-history-user_data">
     * Get ETH staking history (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/stakingHistory")
    public <T> T getETHStakingHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ETH_STAKING_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ETHStakingHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get ETH redemption history<br>
     * No-any params required
     *
     * @return ETH redemption history as {@link ETHRedemptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-redemption-history-user_data">
     * Get ETH redemption history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/redemptionHistory")
    public ETHRedemptionHistory getETHRedemptionHistory() throws Exception {
        return getETHRedemptionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH redemption history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ETH redemption history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-redemption-history-user_data">
     * Get ETH redemption history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/redemptionHistory")
    public <T> T getETHRedemptionHistory(ReturnFormat format) throws Exception {
        return getETHRedemptionHistory(null, format);
    }

    /**
     * Request to get ETH redemption history
     *
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
     *                     </ul>
     * @return ETH redemption history as {@link ETHRedemptionHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-redemption-history-user_data">
     * Get ETH redemption history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/redemptionHistory")
    public ETHRedemptionHistory getETHRedemptionHistory(Params queryParams) throws Exception {
        return getETHRedemptionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH redemption history
     *
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
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return ETH redemption history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-redemption-history-user_data">
     * Get ETH redemption history (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/redemptionHistory")
    public <T> T getETHRedemptionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ETH_REDEMPTION_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ETHRedemptionHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get ETH rewards history<br>
     * No-any params required
     *
     * @return ETH rewards history as {@link ETHRewardsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-rewards-distribution-history-user_data">
     * Get ETH rewards distribution history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rewardsHistory")
    public ETHRewardsHistory getETHRewardsDistributionHistory() throws Exception {
        return getETHRewardsDistributionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH rewards history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ETH rewards history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-rewards-distribution-history-user_data">
     * Get ETH rewards distribution history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rewardsHistory")
    public <T> T getETHRewardsDistributionHistory(ReturnFormat format) throws Exception {
        return getETHRewardsDistributionHistory(null, format);
    }

    /**
     * Request to get ETH rewards history
     *
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
     *                     </ul>
     * @return ETH rewards history as {@link ETHRewardsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-rewards-distribution-history-user_data">
     * Get ETH rewards distribution history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rewardsHistory")
    public ETHRewardsHistory getETHRewardsDistributionHistory(Params queryParams) throws Exception {
        return getETHRewardsDistributionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH rewards history
     *
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
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return ETH rewards history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-rewards-distribution-history-user_data">
     * Get ETH rewards distribution history (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rewardsHistory")
    public <T> T getETHRewardsDistributionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ETH_REWARDS_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ETHRewardsHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get ETH staking quota<br>
     * No-any params required
     *
     * @return ETH staking quota as {@link ETHStakingQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-current-eth-staking-quota-user_data">
     * Get current ETH staking quota (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/quota")
    public ETHStakingQuota getCurrentETHStakingQuota() throws Exception {
        return getCurrentETHStakingQuota(LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH staking quota
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ETH staking quota as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-current-eth-staking-quota-user_data">
     * Get current ETH staking quota (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/quota")
    public <T> T getCurrentETHStakingQuota(ReturnFormat format) throws Exception {
        return getCurrentETHStakingQuota(-1, format);
    }

    /**
     * Request to get ETH staking quota
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return ETH staking quota as {@link ETHStakingQuota} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-current-eth-staking-quota-user_data">
     * Get current ETH staking quota (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/quota")
    public ETHStakingQuota getCurrentETHStakingQuota(long recvWindow) throws Exception {
        return getCurrentETHStakingQuota(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH staking quota
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return ETH staking quota as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-current-eth-staking-quota-user_data">
     * Get current ETH staking quota (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/quota")
    public <T> T getCurrentETHStakingQuota(long recvWindow, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ETH_QUOTA_ENDPOINT, createTimestampPayload(null,
                recvWindow)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ETHStakingQuota(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get BETH rate history<br>
     * No-any params required
     *
     * @return BETH rate history as {@link BETHRateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-beth-rate-history-user_data">
     * Get BETH Rate History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rateHistory")
    public BETHRateHistory getBETHRateHistory() throws Exception {
        return getBETHRateHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get BETH rate history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return BETH rate history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-beth-rate-history-user_data">
     * Get BETH Rate History (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rateHistory")
    public <T> T getBETHRateHistory(ReturnFormat format) throws Exception {
        return getBETHRateHistory(null, format);
    }

    /**
     * Request to get BETH rate history
     *
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
     *                     </ul>
     * @return BETH rate history as {@link BETHRateHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-beth-rate-history-user_data">
     * Get BETH Rate History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rateHistory")
    public BETHRateHistory getBETHRateHistory(Params queryParams) throws Exception {
        return getBETHRateHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get BETH rate history
     *
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
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return BETH rate history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-beth-rate-history-user_data">
     * Get BETH Rate History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/eth/history/rateHistory")
    public <T> T getBETHRateHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ETH_RATE_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new BETHRateHistory(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get ETH staking account<br>
     * No-any params required
     *
     * @return ETH staking account as {@link ETHStakingAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#eth-staking-account-user_data">
     * ETH Staking account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/account")
    public ETHStakingAccount getETHStakingAccount() throws Exception {
        return getETHStakingAccount(LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH staking account
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return ETH staking account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#eth-staking-account-user_data">
     * ETH Staking account (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/account")
    public <T> T getETHStakingAccount(ReturnFormat format) throws Exception {
        return getETHStakingAccount(-1, format);
    }

    /**
     * Request to get ETH staking account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return ETH staking account as {@link ETHStakingAccount} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#eth-staking-account-user_data">
     * ETH Staking account (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/account")
    public ETHStakingAccount getETHStakingAccount(long recvWindow) throws Exception {
        return getETHStakingAccount(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get ETH staking account
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return ETH staking account as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#eth-staking-account-user_data">
     * ETH Staking account (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/account")
    public <T> T getETHStakingAccount(long recvWindow, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ETH_STAKING_ACCOUNT_ENDPOINT, createTimestampPayload(null,
                recvWindow)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new ETHStakingAccount(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to wrap BETH
     *
     * @param amount: amount to wrap
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#wrap-beth-trade">
     * Wrap BETH(TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/wbeth/wrap")
    public boolean wrapBETH(double amount) throws Exception {
        return wrapBETH(amount, -1);
    }

    /**
     * Request to wrap BETH
     *
     * @param amount:     amount to wrap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#wrap-beth-trade">
     * Wrap BETH(TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/wbeth/wrap")
    public boolean wrapBETH(double amount, long recvWindow) throws Exception {
        return isSuccessResponse(WBETH_WRAP_ENDPOINT, createETHOperationPayload(amount, recvWindow));
    }

    /**
     * Request to unwrap BETH
     *
     * @param amount: amount to unwrap
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#unwrap-wbeth-trade">
     * Unwrap WBETH(TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/wbeth/unwrap")
    public boolean unwrapBETH(double amount) throws Exception {
        return wrapBETH(amount, -1);
    }

    /**
     * Request to unwrap BETH
     *
     * @param amount:     amount to unwrap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return result of the operation -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws IOException when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#unwrap-wbeth-trade">
     * Unwrap WBETH(TRADE)</a>
     * @implNote rate Limit: 1/3s per account
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/eth-staking/wbeth/unwrap")
    public boolean unwrapBETH(double amount, long recvWindow) throws Exception {
        return isSuccessResponse(WBETH_UNWRAP_ENDPOINT, createETHOperationPayload(amount, recvWindow));
    }

    /**
     * Method to create a ETH operation payload
     *
     * @param amount:     amount of the operation
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return payload as {@link Params}
     */
    private Params createETHOperationPayload(double amount, long recvWindow) {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("amount", amount);
        return payload;
    }

    /**
     * Request to get WBETH wraps history<br>
     * No-any params required
     *
     * @return WBETH wraps history as {@link WBETHActionsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-wrap-history-user_data">
     * Get WBETH wrap history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/wrapHistory")
    public WBETHActionsHistory getWBETHWrapHistory() throws Exception {
        return getWBETHWrapHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get WBETH wraps history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return WBETH actions history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-wrap-history-user_data">
     * Get WBETH wrap history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/wrapHistory")
    public <T> T getWBETHWrapHistory(ReturnFormat format) throws Exception {
        return getWBETHWrapHistory(null, format);
    }

    /**
     * Request to get WBETH wraps history
     *
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
     *                     </ul>
     * @return WBETH wraps history as {@link WBETHActionsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-wrap-history-user_data">
     * Get WBETH wrap history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/wrapHistory")
    public WBETHActionsHistory getWBETHWrapHistory(Params queryParams) throws Exception {
        return getWBETHWrapHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get WBETH wraps history
     *
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
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return WBETH actions history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-wrap-history-user_data">
     * Get WBETH wrap history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/wrapHistory")
    public <T> T getWBETHWrapHistory(Params queryParams, ReturnFormat format) throws Exception {
        return returnWBETHActionsHistory(sendGetSignedRequest(WBETH_WRAP_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)), format);
    }

    /**
     * Request to get WBETH unwraps history<br>
     * No-any params required
     *
     * @return WBETH unwraps history as {@link WBETHActionsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-unwrap-history-user_data">
     * Get WBETH unwrap history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/unwrapHistory")
    public WBETHActionsHistory getWBETHUnwrapHistory() throws Exception {
        return getWBETHUnwrapHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get WBETH unwraps history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return WBETH actions history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-unwrap-history-user_data">
     * Get WBETH unwrap history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/unwrapHistory")
    public <T> T getWBETHUnwrapHistory(ReturnFormat format) throws Exception {
        return getWBETHUnwrapHistory(null, format);
    }

    /**
     * Request to get WBETH unwraps history
     *
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
     *                     </ul>
     * @return WBETH unwraps history as {@link WBETHActionsHistory} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-unwrap-history-user_data">
     * Get WBETH unwrap history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/unwrapHistory")
    public WBETHActionsHistory getWBETHUnwrapHistory(Params queryParams) throws Exception {
        return getWBETHUnwrapHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get WBETH unwraps history
     *
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
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return WBETH actions history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-unwrap-history-user_data">
     * Get WBETH unwrap history (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/eth-staking/wbeth/history/unwrapHistory")
    public <T> T getWBETHUnwrapHistory(Params queryParams, ReturnFormat format) throws Exception {
        return returnWBETHActionsHistory(sendGetSignedRequest(WBETH_UNWRAP_HISTORY_ENDPOINT,
                createTimestampPayload(queryParams)), format);
    }

    /**
     * Method to create a WBETH actions history
     *
     * @param WBETHActionsHistoryResponse: obtained from Binance's response
     * @param format:                      return type formatter -> {@link ReturnFormat}
     * @return WBETH actions history as {@code "format"} defines
     */
    @Returner
    private <T> T returnWBETHActionsHistory(String WBETHActionsHistoryResponse, ReturnFormat format) {
        JSONObject response = new JSONObject(WBETHActionsHistoryResponse);
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new WBETHActionsHistory(response);
            default -> (T) WBETHActionsHistoryResponse;
        };
    }

}
