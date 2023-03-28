package com.tecknobit.binancemanager.managers.signedmanagers.staking;

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

import static com.tecknobit.binancemanager.constants.EndpointsList.*;
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

    public boolean redeemStakingProduct(ProductType product, long productId) throws Exception {
        return redeemStakingProduct(product, productId, null);
    }

    public boolean redeemStakingProduct(ProductType product, long productId, Params extraParams) throws Exception {
        sendPostSignedRequest(STAKING_REDEEM_ENDPOINT, createStakingOpePayload(product, productId, extraParams));
        return JsonHelper.getBoolean(apiRequest.getJSONResponse(), "success");
    }

    public ArrayList<StakingProductPosition> getStakingProductPosition(ProductType product) throws Exception {
        return getStakingProductPosition(product, LIBRARY_OBJECT);
    }

    public <T> T getStakingProductPosition(ProductType product, ReturnFormat format) throws Exception {
        return getStakingProductPosition(product, null, format);
    }

    public ArrayList<StakingProductPosition> getStakingProductPosition(ProductType product,
                                                                       Params extraParams) throws Exception {
        return getStakingProductPosition(product, extraParams, LIBRARY_OBJECT);
    }

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

    public ArrayList<StakingHistoryRecord> getStakingHistory(ProductType product, TxnType txnType) throws Exception {
        return getStakingHistory(product, txnType, LIBRARY_OBJECT);
    }

    public <T> T getStakingHistory(ProductType product, TxnType txnType, ReturnFormat format) throws Exception {
        return getStakingHistory(product, txnType, null, format);
    }

    public ArrayList<StakingHistoryRecord> getStakingHistory(ProductType product, TxnType txnType,
                                                             Params extraParams) throws Exception {
        return getStakingHistory(product, txnType, extraParams, LIBRARY_OBJECT);
    }

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

    public boolean setAutoStaking(ProductType product, long positionId, boolean renewable) throws Exception {
        return setAutoStaking(product, positionId, renewable, -1);
    }

    public boolean setAutoStaking(ProductType product, long positionId, boolean renewable, long recvWindow) throws Exception {
        Params payload = createStakingProductPayload(product, null);
        payload.addParam("positionId", positionId);
        payload.addParam("renewable", renewable);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        sendPostSignedRequest(SET_AUTO_STAKING_ENDPOINT, payload);
        return JsonHelper.getBoolean(apiRequest.getJSONResponse(), "success");
    }

    private Params createStakingProductPayload(ProductType product, Params extraParams) {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        extraParams.addParam("product", product);
        return extraParams;
    }

    public ArrayList<Double> getPersonalLeftQuota(ProductType product, long productId) throws Exception {
        return getPersonalLeftQuota(product, productId, LIBRARY_OBJECT);
    }

    public <T> T getPersonalLeftQuota(ProductType product, long productId, ReturnFormat format) throws Exception {
        return getPersonalLeftQuota(product, productId, -1, format);
    }

    public ArrayList<Double> getPersonalLeftQuota(ProductType product, long productId, long recvWindow) throws Exception {
        return getPersonalLeftQuota(product, productId, recvWindow, LIBRARY_OBJECT);
    }

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

    private Params createStakingOpePayload(ProductType product, long productId, Params extraParams) {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("product", product);
        extraParams.addParam("productId", productId);
        return extraParams;
    }

}
