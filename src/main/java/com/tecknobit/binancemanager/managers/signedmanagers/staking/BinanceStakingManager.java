package com.tecknobit.binancemanager.managers.signedmanagers.staking;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.PurchaseStakingProductResult;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct.ProductType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceStakingManager} class is useful to manage staking endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#staking-endpoints">
 * Staking Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceStakingManager extends BinanceSignedManager {

    /**
     * Constructor to init a {@link BinanceStakingManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceStakingManager(String baseEndpoint, String defaultErrorMessage, int timeout,
                                 String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceStakingManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceStakingManager(String baseEndpoint, String defaultErrorMessage,
                                 String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceStakingManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceStakingManager(String baseEndpoint, int timeout, String apiKey,
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceStakingManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
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
     **/
    public BinanceStakingManager() {
        super();
    }

    public ArrayList<StakingProduct> getStakingProductList(ProductType product) throws Exception {
        return getStakingProductList(product, LIBRARY_OBJECT);
    }

    public <T> T getStakingProductList(ProductType product, ReturnFormat format) throws Exception {
        return getStakingProductList(product, null, format);
    }

    public ArrayList<StakingProduct> getStakingProductList(ProductType product, Params extraParams) throws Exception {
        return getStakingProductList(product, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getStakingProductList(ProductType product, Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        extraParams.addParam("product", product);
        String listResponse = sendGetSignedRequest(STAKING_PRODUCT_LIST_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(listResponse);
            case LIBRARY_OBJECT:
                ArrayList<StakingProduct> stakingProducts = new ArrayList<>();
                JSONArray jStaking = new JSONArray();
                for (int j = 0; j < jStaking.length(); j++)
                    stakingProducts.add(new StakingProduct(jStaking.getJSONObject(j)));
                return (T) stakingProducts;
            default:
                return (T) listResponse;
        }
    }

    public PurchaseStakingProductResult purchaseStakingProduct(ProductType product, long productId,
                                                               double amount) throws Exception {
        return purchaseStakingProduct(product, productId, amount, LIBRARY_OBJECT);
    }

    public <T> T purchaseStakingProduct(ProductType product, long productId, double amount,
                                        ReturnFormat format) throws Exception {
        return purchaseStakingProduct(product, productId, amount, null, format);
    }

    public PurchaseStakingProductResult purchaseStakingProduct(ProductType product, long productId, double amount,
                                                               Params extraParams) throws Exception {
        return purchaseStakingProduct(product, productId, amount, extraParams, LIBRARY_OBJECT);
    }

    public <T> T purchaseStakingProduct(ProductType product, long productId, double amount, Params extraParams,
                                        ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("product", product);
        extraParams.addParam("productId", productId);
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

    public boolean redeemStakingProduct(ProductType product, long productId) throws Exception {
        return redeemStakingProduct(product, productId, null);
    }

    public boolean redeemStakingProduct(ProductType product, long productId, Params extraParams) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("product", product);
        extraParams.addParam("productId", productId);
        sendPostSignedRequest(STAKING_REDEEM_ENDPOINT, extraParams);
        return JsonHelper.getBoolean(apiRequest.getJSONResponse(), "success");
    }

}
