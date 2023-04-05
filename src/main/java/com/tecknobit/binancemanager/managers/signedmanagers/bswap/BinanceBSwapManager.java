package com.tecknobit.binancemanager.managers.signedmanagers.bswap;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.LiquidityOperation;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolLiquidityInformation;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolStructure.PoolType;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.SwapPool;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap.SwapQuote;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceBSwapManager} class is useful to manage BSwap endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#bswap-endpoints">
 * BSwap Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceBSwapManager extends BinanceSignedManager {

    /**
     * {@code BSWAP_POOLS_ENDPOINT} is constant for BSWAP_POOLS_ENDPOINT's endpoint
     **/
    public static final String BSWAP_POOLS_ENDPOINT = "/sapi/v1/bswap/pools";

    /**
     * {@code BSWAP_LIQUIDITY_ENDPOINT} is constant for BSWAP_LIQUIDITY_ENDPOINT's endpoint
     **/
    public static final String BSWAP_LIQUIDITY_ENDPOINT = "/sapi/v1/bswap/liquidity";

    /**
     * {@code BSWAP_LIQUIDITY_ADD_ENDPOINT} is constant for BSWAP_LIQUIDITY_ADD_ENDPOINT's endpoint
     **/
    public static final String BSWAP_LIQUIDITY_ADD_ENDPOINT = "/sapi/v1/bswap/liquidityAdd";

    /**
     * {@code BSWAP_LIQUIDITY_REMOVE_ENDPOINT} is constant for BSWAP_LIQUIDITY_REMOVE_ENDPOINT's endpoint
     **/
    public static final String BSWAP_LIQUIDITY_REMOVE_ENDPOINT = "/sapi/v1/bswap/liquidityRemove";

    /**
     * {@code BSWAP_LIQUIDITY_OPS_ENDPOINT} is constant for BSWAP_LIQUIDITY_OPS_ENDPOINT's endpoint
     **/
    public static final String BSWAP_LIQUIDITY_OPS_ENDPOINT = "/sapi/v1/bswap/liquidityOps";

    /**
     * {@code BSWAP_QUOTE_ENDPOINT} is constant for BSWAP_QUOTE_ENDPOINT's endpoint
     **/
    public static final String BSWAP_QUOTE_ENDPOINT = "/sapi/v1/bswap/quote";

    /**
     * {@code BSWAP_SWAP_ENDPOINT} is constant for BSWAP_SWAP_ENDPOINT's endpoint
     **/
    public static final String BSWAP_SWAP_ENDPOINT = "/sapi/v1/bswap/swap";

    /**
     * {@code BSWAP_POOL_CONFIGURE_ENDPOINT} is constant for BSWAP_POOL_CONFIGURE_ENDPOINT's endpoint
     **/
    public static final String BSWAP_POOL_CONFIGURE_ENDPOINT = "/sapi/v1/bswap/poolConfigure";

    /**
     * {@code BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT} is constant for BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT's endpoint
     **/
    public static final String BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT = "/sapi/v1/bswap/addLiquidityPreview";

    /**
     * {@code BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT} is constant for BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT's endpoint
     **/
    public static final String BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT = "/sapi/v1/bswap/removeLiquidityPreview";

    /**
     * {@code BSWAP_UNCLAIMED_REWARDS_ENDPOINT} is constant for BSWAP_UNCLAIMED_REWARDS_ENDPOINT's endpoint
     **/
    public static final String BSWAP_UNCLAIMED_REWARDS_ENDPOINT = "/sapi/v1/bswap/unclaimedRewards";

    /**
     * {@code BSWAP_CLAIM_REWARDS_ENDPOINT} is constant for BSWAP_CLAIM_REWARDS_ENDPOINT's endpoint
     **/
    public static final String BSWAP_CLAIM_REWARDS_ENDPOINT = "/sapi/v1/bswap/claimRewards";

    /**
     * {@code BSWAP_CLAIMED_HISTORY_ENDPOINT} is constant for BSWAP_CLAIMED_HISTORY_ENDPOINT's endpoint
     **/
    public static final String BSWAP_CLAIMED_HISTORY_ENDPOINT = "/sapi/v1/bswap/claimedHistory";

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceBSwapManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                               String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceBSwapManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                               String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceBSwapManager(String baseEndpoint, int timeout, String apiKey,
                               String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceBSwapManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager} <br>
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
    public BinanceBSwapManager() {
        super();
    }

    public ArrayList<SwapPool> getAllSwapPools() throws IOException {
        return getAllSwapPools(LIBRARY_OBJECT);
    }

    public <T> T getAllSwapPools(ReturnFormat format) throws IOException {
        String poolsResponse = sendGetRequest(BSWAP_POOLS_ENDPOINT, (Params) null, apiKey);
        switch (format) {
            case JSON:
                return (T) new JSONArray(poolsResponse);
            case LIBRARY_OBJECT:
                ArrayList<SwapPool> swapPools = new ArrayList<>();
                JSONArray jPools = new JSONArray(poolsResponse);
                for (int j = 0; j < jPools.length(); j++)
                    swapPools.add(new SwapPool(jPools.getJSONObject(j)));
                return (T) swapPools;
            default:
                return (T) poolsResponse;
        }
    }

    public ArrayList<PoolLiquidityInformation> getPoolLiquidityInformation() throws Exception {
        return getPoolLiquidityInformation(LIBRARY_OBJECT);
    }

    public <T> T getPoolLiquidityInformation(ReturnFormat format) throws Exception {
        return getPoolLiquidityInformation(null, format);
    }

    public ArrayList<PoolLiquidityInformation> getPoolLiquidityInformation(Params extraParams) throws Exception {
        return getPoolLiquidityInformation(extraParams, LIBRARY_OBJECT);
    }

    public <T> T getPoolLiquidityInformation(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String poolLiquidityInformationResponse = sendGetSignedRequest(BSWAP_LIQUIDITY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(poolLiquidityInformationResponse);
            case LIBRARY_OBJECT:
                ArrayList<PoolLiquidityInformation> information = new ArrayList<>();
                JSONArray jInformation = new JSONArray(poolLiquidityInformationResponse);
                for (int j = 0; j < jInformation.length(); j++)
                    information.add(new PoolLiquidityInformation(jInformation.getJSONObject(j)));
                return (T) information;
            default:
                return (T) poolLiquidityInformationResponse;
        }
    }

    public long addLiquidity(PoolStructure pool, String asset, double quantity) throws Exception {
        return Long.parseLong(addLiquidity(pool.getPoolId(), asset, quantity, LIBRARY_OBJECT));
    }

    public <T> T addLiquidity(PoolStructure pool, String asset, double quantity, ReturnFormat format) throws Exception {
        return addLiquidity(pool.getPoolId(), asset, quantity, format);
    }

    public long addLiquidity(long poolId, String asset, double quantity) throws Exception {
        return Long.parseLong(addLiquidity(poolId, asset, quantity, LIBRARY_OBJECT));
    }

    public <T> T addLiquidity(long poolId, String asset, double quantity, ReturnFormat format) throws Exception {
        return addLiquidity(poolId, asset, quantity, null, format);
    }

    public long addLiquidity(PoolStructure pool, String asset, double quantity, Params extraParams) throws Exception {
        return Long.parseLong(addLiquidity(pool.getPoolId(), asset, quantity, extraParams, LIBRARY_OBJECT));
    }

    public <T> T addLiquidity(PoolStructure pool, String asset, double quantity, Params extraParams,
                              ReturnFormat format) throws Exception {
        return addLiquidity(pool.getPoolId(), asset, quantity, extraParams, format);
    }

    public long addLiquidity(long poolId, String asset, double quantity, Params extraParams) throws Exception {
        return Long.parseLong(addLiquidity(poolId, asset, quantity, extraParams, LIBRARY_OBJECT));
    }

    public <T> T addLiquidity(long poolId, String asset, double quantity, Params extraParams,
                              ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("poolId", poolId);
        extraParams.addParam("asset", asset);
        extraParams.addParam("quantity", quantity);
        return returnOperationId(sendPostSignedRequest(BSWAP_LIQUIDITY_ADD_ENDPOINT, extraParams), format);
    }

    public long removeLiquidity(PoolStructure pool, PoolType type, double shareAmount) throws Exception {
        return Long.parseLong(removeLiquidity(pool.getPoolId(), type, shareAmount, LIBRARY_OBJECT));
    }

    public <T> T removeLiquidity(PoolStructure pool, PoolType type, double shareAmount,
                                 ReturnFormat format) throws Exception {
        return removeLiquidity(pool.getPoolId(), type, shareAmount, format);
    }

    public long removeLiquidity(long poolId, PoolType type, double shareAmount) throws Exception {
        return Long.parseLong(removeLiquidity(poolId, type, shareAmount, LIBRARY_OBJECT));
    }

    public <T> T removeLiquidity(long poolId, PoolType type, double shareAmount, ReturnFormat format) throws Exception {
        return removeLiquidity(poolId, type, shareAmount, null, format);
    }

    public long removeLiquidity(PoolStructure pool, PoolType type, double shareAmount, Params extraParams) throws Exception {
        return Long.parseLong(removeLiquidity(pool.getPoolId(), type, shareAmount, extraParams, LIBRARY_OBJECT));
    }

    public <T> T removeLiquidity(PoolStructure pool, PoolType type, double shareAmount, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return removeLiquidity(pool.getPoolId(), type, shareAmount, extraParams, format);
    }

    public long removeLiquidity(long poolId, PoolType type, double shareAmount, Params extraParams) throws Exception {
        return Long.parseLong(removeLiquidity(poolId, type, shareAmount, extraParams, LIBRARY_OBJECT));
    }

    public <T> T removeLiquidity(long poolId, PoolType type, double shareAmount, Params extraParams,
                                 ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("poolId", poolId);
        extraParams.addParam("type", type);
        extraParams.addParam("shareAmount", shareAmount);
        return returnOperationId(sendPostSignedRequest(BSWAP_LIQUIDITY_REMOVE_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create an operation id
     *
     * @param operationIdResponse: obtained from Binance's response
     * @param format:              return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOperationId(String operationIdResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(operationIdResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(new JSONObject(operationIdResponse).getLong("operationId"));
            default:
                return (T) operationIdResponse;
        }
    }

    public ArrayList<LiquidityOperation> getLiquidityOperation() throws Exception {
        return getLiquidityOperation(LIBRARY_OBJECT);
    }

    public <T> T getLiquidityOperation(ReturnFormat format) throws Exception {
        return getLiquidityOperation(null, format);
    }

    public ArrayList<LiquidityOperation> getLiquidityOperation(Params extraParams) throws Exception {
        return getLiquidityOperation(extraParams, LIBRARY_OBJECT);
    }

    public <T> T getLiquidityOperation(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String liquidityOperationResponse = sendGetSignedRequest(BSWAP_LIQUIDITY_OPS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(liquidityOperationResponse);
            case LIBRARY_OBJECT:
                ArrayList<LiquidityOperation> operations = new ArrayList<>();
                JSONArray jOperations = new JSONArray(liquidityOperationResponse);
                for (int j = 0; j < jOperations.length(); j++)
                    operations.add(new LiquidityOperation(jOperations.getJSONObject(j)));
                return (T) operations;
            default:
                return (T) liquidityOperationResponse;
        }
    }

    public SwapQuote requestQuote(String quoteAsset, String baseAsset, double quoteQty) throws Exception {
        return requestQuote(quoteAsset, baseAsset, quoteQty, LIBRARY_OBJECT);
    }

    public <T> T requestQuote(String quoteAsset, String baseAsset, double quoteQty, ReturnFormat format) throws Exception {
        return requestQuote(quoteAsset, baseAsset, quoteQty, -1, format);
    }

    public SwapQuote requestQuote(String quoteAsset, String baseAsset, double quoteQty, long recvWindow) throws Exception {
        return requestQuote(quoteAsset, baseAsset, quoteQty, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T requestQuote(String quoteAsset, String baseAsset, double quoteQty, long recvWindow,
                              ReturnFormat format) throws Exception {
        String quoteResponse = sendGetSignedRequest(BSWAP_QUOTE_ENDPOINT, createSwapQuery(quoteAsset, baseAsset, quoteQty,
                recvWindow));
        switch (format) {
            case JSON:
                return (T) new JSONObject(quoteResponse);
            case LIBRARY_OBJECT:
                return (T) new SwapQuote(new JSONObject(quoteResponse));
            default:
                return (T) quoteResponse;
        }
    }

    public long swap(String quoteAsset, String baseAsset, double quoteQty) throws Exception {
        return Long.parseLong(swap(quoteAsset, baseAsset, quoteQty, LIBRARY_OBJECT));
    }

    public <T> T swap(String quoteAsset, String baseAsset, double quoteQty, ReturnFormat format) throws Exception {
        return swap(quoteAsset, baseAsset, quoteQty, -1, format);
    }

    public long swap(String quoteAsset, String baseAsset, double quoteQty, long recvWindow) throws Exception {
        return Long.parseLong(swap(quoteAsset, baseAsset, quoteQty, recvWindow, LIBRARY_OBJECT));
    }

    public <T> T swap(String quoteAsset, String baseAsset, double quoteQty, long recvWindow,
                      ReturnFormat format) throws Exception {
        String quoteResponse = sendPostSignedRequest(BSWAP_QUOTE_ENDPOINT, createSwapQuery(quoteAsset, baseAsset,
                quoteQty, recvWindow));
        switch (format) {
            case JSON:
                return (T) new JSONObject(quoteResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(new JSONObject(quoteResponse).getLong("swapId"));
            default:
                return (T) quoteResponse;
        }
    }

    private Params createSwapQuery(String quoteAsset, String baseAsset, double quoteQty, long recvWindow) {
        Params query = new Params();
        query.addParam("quoteAsset", quoteAsset);
        query.addParam("baseAsset", baseAsset);
        query.addParam("quoteQty", quoteQty);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        query.addParam("timestamp", getServerTime());
        return query;
    }

}
