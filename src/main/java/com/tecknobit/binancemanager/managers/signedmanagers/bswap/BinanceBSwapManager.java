package com.tecknobit.binancemanager.managers.signedmanagers.bswap;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.UnclaimedRewards;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.UnclaimedRewards.RewardsType;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.*;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolStructure.BSwapStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolStructure.PoolType;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap.SwapHistoryItem;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap.SwapPreview;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap.SwapQuote;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceBSwapManager} class is useful to manage BSwap endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#bswap-endpoints">
 * BSwap Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 */
public class BinanceBSwapManager extends BinanceSignedManager {

    /**
     * {@code BSWAP_POOLS_ENDPOINT} is constant for BSWAP_POOLS_ENDPOINT's endpoint
     */
    public static final String BSWAP_POOLS_ENDPOINT = "/sapi/v1/bswap/pools";

    /**
     * {@code BSWAP_LIQUIDITY_ENDPOINT} is constant for BSWAP_LIQUIDITY_ENDPOINT's endpoint
     */
    public static final String BSWAP_LIQUIDITY_ENDPOINT = "/sapi/v1/bswap/liquidity";

    /**
     * {@code BSWAP_LIQUIDITY_ADD_ENDPOINT} is constant for BSWAP_LIQUIDITY_ADD_ENDPOINT's endpoint
     */
    public static final String BSWAP_LIQUIDITY_ADD_ENDPOINT = "/sapi/v1/bswap/liquidityAdd";

    /**
     * {@code BSWAP_LIQUIDITY_REMOVE_ENDPOINT} is constant for BSWAP_LIQUIDITY_REMOVE_ENDPOINT's endpoint
     */
    public static final String BSWAP_LIQUIDITY_REMOVE_ENDPOINT = "/sapi/v1/bswap/liquidityRemove";

    /**
     * {@code BSWAP_LIQUIDITY_OPS_ENDPOINT} is constant for BSWAP_LIQUIDITY_OPS_ENDPOINT's endpoint
     */
    public static final String BSWAP_LIQUIDITY_OPS_ENDPOINT = "/sapi/v1/bswap/liquidityOps";

    /**
     * {@code BSWAP_QUOTE_ENDPOINT} is constant for BSWAP_QUOTE_ENDPOINT's endpoint
     */
    public static final String BSWAP_QUOTE_ENDPOINT = "/sapi/v1/bswap/quote";

    /**
     * {@code BSWAP_SWAP_ENDPOINT} is constant for BSWAP_SWAP_ENDPOINT's endpoint
     */
    public static final String BSWAP_SWAP_ENDPOINT = "/sapi/v1/bswap/swap";

    /**
     * {@code BSWAP_POOL_CONFIGURE_ENDPOINT} is constant for BSWAP_POOL_CONFIGURE_ENDPOINT's endpoint
     */
    public static final String BSWAP_POOL_CONFIGURE_ENDPOINT = "/sapi/v1/bswap/poolConfigure";

    /**
     * {@code BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT} is constant for BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT's endpoint
     */
    public static final String BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT = "/sapi/v1/bswap/addLiquidityPreview";

    /**
     * {@code BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT} is constant for BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT's endpoint
     */
    public static final String BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT = "/sapi/v1/bswap/removeLiquidityPreview";

    /**
     * {@code BSWAP_UNCLAIMED_REWARDS_ENDPOINT} is constant for BSWAP_UNCLAIMED_REWARDS_ENDPOINT's endpoint
     */
    public static final String BSWAP_UNCLAIMED_REWARDS_ENDPOINT = "/sapi/v1/bswap/unclaimedRewards";

    /**
     * {@code BSWAP_CLAIM_REWARDS_ENDPOINT} is constant for BSWAP_CLAIM_REWARDS_ENDPOINT's endpoint
     */
    public static final String BSWAP_CLAIM_REWARDS_ENDPOINT = "/sapi/v1/bswap/claimRewards";

    /**
     * {@code BSWAP_CLAIMED_HISTORY_ENDPOINT} is constant for BSWAP_CLAIMED_HISTORY_ENDPOINT's endpoint
     */
    public static final String BSWAP_CLAIMED_HISTORY_ENDPOINT = "/sapi/v1/bswap/claimedHistory";

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceBSwapManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                               String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceBSwapManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                               String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceBSwapManager(String baseEndpoint, int timeout, String apiKey,
                               String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceBSwapManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
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
     */
    public BinanceBSwapManager() {
        super();
    }

    /**
     * Request to get the metadata about all swap pools <br>
     * No-any params required
     *
     * @return swap pools as {@link ArrayList} of {@link SwapPool} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#list-all-swap-pools-market_data">
     * List All Swap Pools (MARKET_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/pools")
    public ArrayList<SwapPool> getAllSwapPools() throws IOException {
        return getAllSwapPools(LIBRARY_OBJECT);
    }

    /**
     * Request to get the metadata about all swap pools
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return swap pools as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#list-all-swap-pools-market_data">
     * List All Swap Pools (MARKET_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/pools")
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

    /**
     * Request to get the liquidity information and user share of a pool <br>
     * No-any params required
     *
     * @return liquidity information and user share of a pool as {@link ArrayList} of {@link PoolLiquidityInformation} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-information-of-a-pool-user_data">
     * Get liquidity information of a pool (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1 / 10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidity")
    public ArrayList<PoolLiquidityInformation> getPoolLiquidityInformation() throws Exception {
        return getPoolLiquidityInformation(LIBRARY_OBJECT);
    }

    /**
     * Request to get the liquidity information and user share of a pool
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return liquidity information and user share of a pool as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-information-of-a-pool-user_data">
     * Get liquidity information of a pool (USER_DATA)</a>
     */
    @RequestWeight(weight = "1 / 10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidity")
    public <T> T getPoolLiquidityInformation(ReturnFormat format) throws Exception {
        return getPoolLiquidityInformation(null, format);
    }

    /**
     * Request to get the liquidity information and user share of a pool
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return liquidity information and user share of a pool as {@link ArrayList} of {@link PoolLiquidityInformation} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-information-of-a-pool-user_data">
     * Get liquidity information of a pool (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1 / 10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidity")
    public ArrayList<PoolLiquidityInformation> getPoolLiquidityInformation(Params extraParams) throws Exception {
        return getPoolLiquidityInformation(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the liquidity information and user share of a pool
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return liquidity information and user share of a pool as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-information-of-a-pool-user_data">
     * Get liquidity information of a pool (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1 / 10(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidity")
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

    /**
     * Request to add liquidity to a pool
     *
     * @param pool:     pool where add the liquidity
     * @param asset:    asset where add the liquidity
     * @param quantity: quantity of the asset to add
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public long addLiquidity(PoolStructure pool, String asset, double quantity) throws Exception {
        return Long.parseLong(addLiquidity(pool.getPoolId(), asset, quantity, LIBRARY_OBJECT));
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param pool:     pool where add the liquidity
     * @param asset:    asset where add the liquidity
     * @param quantity: quantity of the asset to add
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public <T> T addLiquidity(PoolStructure pool, String asset, double quantity, ReturnFormat format) throws Exception {
        return addLiquidity(pool.getPoolId(), asset, quantity, format);
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param poolId:   pool identifier where add the liquidity
     * @param asset:    asset where add the liquidity
     * @param quantity: quantity of the asset to add
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public long addLiquidity(long poolId, String asset, double quantity) throws Exception {
        return Long.parseLong(addLiquidity(poolId, asset, quantity, LIBRARY_OBJECT));
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param poolId:   pool identifier where add the liquidity
     * @param asset:    asset where add the liquidity
     * @param quantity: quantity of the asset to add
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public <T> T addLiquidity(long poolId, String asset, double quantity, ReturnFormat format) throws Exception {
        return addLiquidity(poolId, asset, quantity, null, format);
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param pool:        pool where add the liquidity
     * @param asset:       asset where add the liquidity
     * @param quantity:    quantity of the asset to add
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the pool, constants available {@link PoolType}
     *                                - [STRING, default SINGLE]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public long addLiquidity(PoolStructure pool, String asset, double quantity, Params extraParams) throws Exception {
        return Long.parseLong(addLiquidity(pool.getPoolId(), asset, quantity, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param pool:        pool where add the liquidity
     * @param asset:       asset where add the liquidity
     * @param quantity:    quantity of the asset to add
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the pool, constants available {@link PoolType}
     *                                - [STRING, default SINGLE]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public <T> T addLiquidity(PoolStructure pool, String asset, double quantity, Params extraParams,
                              ReturnFormat format) throws Exception {
        return addLiquidity(pool.getPoolId(), asset, quantity, extraParams, format);
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param poolId:      pool identifier where add the liquidity
     * @param asset:       asset where add the liquidity
     * @param quantity:    quantity of the asset to add
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the pool, constants available {@link PoolType}
     *                                - [STRING, default SINGLE]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public long addLiquidity(long poolId, String asset, double quantity, Params extraParams) throws Exception {
        return Long.parseLong(addLiquidity(poolId, asset, quantity, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to add liquidity to a pool
     *
     * @param poolId:      pool identifier where add the liquidity
     * @param asset:       asset where add the liquidity
     * @param quantity:    quantity of the asset to add
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the pool, constants available {@link PoolType}
     *                                - [STRING, default SINGLE]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-trade">
     * Add Liquidity (TRADE)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityAdd")
    public <T> T addLiquidity(long poolId, String asset, double quantity, Params extraParams,
                              ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("poolId", poolId);
        extraParams.addParam("asset", asset);
        extraParams.addParam("quantity", quantity);
        return returnOperationId(sendPostSignedRequest(BSWAP_LIQUIDITY_ADD_ENDPOINT, extraParams), format);
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param pool:        pool from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public long removeLiquidity(PoolStructure pool, PoolType type, double shareAmount) throws Exception {
        return Long.parseLong(removeLiquidity(pool.getPoolId(), type, shareAmount, LIBRARY_OBJECT));
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param pool:        pool from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public <T> T removeLiquidity(PoolStructure pool, PoolType type, double shareAmount,
                                 ReturnFormat format) throws Exception {
        return removeLiquidity(pool.getPoolId(), type, shareAmount, format);
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param poolId:      pool identifier from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public long removeLiquidity(long poolId, PoolType type, double shareAmount) throws Exception {
        return Long.parseLong(removeLiquidity(poolId, type, shareAmount, LIBRARY_OBJECT));
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param poolId:      pool identifier from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public <T> T removeLiquidity(long poolId, PoolType type, double shareAmount, ReturnFormat format) throws Exception {
        return removeLiquidity(poolId, type, shareAmount, null, format);
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param pool:        pool from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> mandatory for single asset removal - [LIST]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public long removeLiquidity(PoolStructure pool, PoolType type, double shareAmount, Params extraParams) throws Exception {
        return Long.parseLong(removeLiquidity(pool.getPoolId(), type, shareAmount, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param pool:        pool from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> mandatory for single asset removal - [LIST]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public <T> T removeLiquidity(PoolStructure pool, PoolType type, double shareAmount, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return removeLiquidity(pool.getPoolId(), type, shareAmount, extraParams, format);
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param poolId:      pool identifier from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> mandatory for single asset removal - [LIST]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return operation identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
    public long removeLiquidity(long poolId, PoolType type, double shareAmount, Params extraParams) throws Exception {
        return Long.parseLong(removeLiquidity(poolId, type, shareAmount, extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset
     * removal
     *
     * @param poolId:      pool identifier from remove the liquidity
     * @param type:        type of the pool
     * @param shareAmount: share amount to remove
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "asset"} -> mandatory for single asset removal - [LIST]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return an operation id as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-trade">
     * Remove Liquidity (TRADE)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/liquidityRemove")
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
     */
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

    /**
     * Request to get the liquidity operation (add/remove) records <br>
     * No-any params required
     *
     * @return liquidity operation records as {@link ArrayList} of {@link LiquidityOperation} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-operation-record-user_data">
     * Get Liquidity Operation Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidityOps")
    public ArrayList<LiquidityOperation> getLiquidityOperation() throws Exception {
        return getLiquidityOperation(LIBRARY_OBJECT);
    }

    /**
     * Request to get the liquidity operation (add/remove) records
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return liquidity operation records as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-operation-record-user_data">
     * Get Liquidity Operation Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidityOps")
    public <T> T getLiquidityOperation(ReturnFormat format) throws Exception {
        return getLiquidityOperation(null, format);
    }

    /**
     * Request to get the liquidity operation (add/remove) records
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "operationId"} -> operation identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "operation"} -> operation to fetch, constants available
     *                                {@link LiquidityOperation} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 3]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return liquidity operation records as {@link ArrayList} of {@link LiquidityOperation} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-operation-record-user_data">
     * Get Liquidity Operation Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidityOps")
    public ArrayList<LiquidityOperation> getLiquidityOperation(Params extraParams) throws Exception {
        return getLiquidityOperation(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the liquidity operation (add/remove) records
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "operationId"} -> operation identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "operation"} -> operation to fetch, constants available
     *                                {@link LiquidityOperation} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 3]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return liquidity operation records as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-operation-record-user_data">
     * Get Liquidity Operation Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/liquidityOps")
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

    /**
     * Request to get a quote for swap quote asset (selling asset) for base asset (buying asset), essentially price/exchange rates
     *
     * @param quoteAsset: quote asset of the quote
     * @param baseAsset:  base asset of the quote
     * @param quoteQty:   quantity of quote asset (to sell)
     * @return quote for swap as {@link SwapQuote} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
     * Request Quote (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/quote")
    public SwapQuote requestQuote(String quoteAsset, String baseAsset, double quoteQty) throws Exception {
        return requestQuote(quoteAsset, baseAsset, quoteQty, LIBRARY_OBJECT);
    }

    /**
     * Request to get a quote for swap quote asset (selling asset) for base asset (buying asset), essentially price/exchange rates
     *
     * @param quoteAsset: quote asset of the quote
     * @param baseAsset:  base asset of the quote
     * @param quoteQty:   quantity of quote asset (to sell)
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return quote for swap as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
     * Request Quote (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/quote")
    public <T> T requestQuote(String quoteAsset, String baseAsset, double quoteQty, ReturnFormat format) throws Exception {
        return requestQuote(quoteAsset, baseAsset, quoteQty, -1, format);
    }

    /**
     * Request to get a quote for swap quote asset (selling asset) for base asset (buying asset), essentially price/exchange rates
     *
     * @param quoteAsset: quote asset of the quote
     * @param baseAsset:  base asset of the quote
     * @param quoteQty:   quantity of quote asset (to sell)
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return quote for swap as {@link SwapQuote} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
     * Request Quote (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/quote")
    public SwapQuote requestQuote(String quoteAsset, String baseAsset, double quoteQty, long recvWindow) throws Exception {
        return requestQuote(quoteAsset, baseAsset, quoteQty, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get a quote for swap quote asset (selling asset) for base asset (buying asset), essentially price/exchange rates
     *
     * @param quoteAsset: quote asset of the quote
     * @param baseAsset:  base asset of the quote
     * @param quoteQty:   quantity of quote asset (to sell)
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return quote for swap as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
     * Request Quote (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/quote")
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

    /**
     * Request to swap quoteAsset for baseAsset
     *
     * @param quoteAsset: quote asset of the swap
     * @param baseAsset:  base asset of the swap
     * @param quoteQty:   quote quantity of the swap
     * @return swap identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#swap-trade">
     * Swap (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/swap")
    public long swap(String quoteAsset, String baseAsset, double quoteQty) throws Exception {
        return Long.parseLong(swap(quoteAsset, baseAsset, quoteQty, LIBRARY_OBJECT));
    }

    /**
     * Request to swap quoteAsset for baseAsset
     *
     * @param quoteAsset: quote asset of the swap
     * @param baseAsset:  base asset of the swap
     * @param quoteQty:   quote quantity of the swap
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return swap identifier as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#swap-trade">
     * Swap (TRADE)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/swap")
    public <T> T swap(String quoteAsset, String baseAsset, double quoteQty, ReturnFormat format) throws Exception {
        return swap(quoteAsset, baseAsset, quoteQty, -2, format);
    }

    /**
     * Request to swap quoteAsset for baseAsset
     *
     * @param quoteAsset: quote asset of the swap
     * @param baseAsset:  base asset of the swap
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return swap identifier as long
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#swap-trade">
     * Swap (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/swap")
    public long swap(String quoteAsset, String baseAsset, double quoteQty, long recvWindow) throws Exception {
        return Long.parseLong(swap(quoteAsset, baseAsset, quoteQty, recvWindow, LIBRARY_OBJECT));
    }

    /**
     * Request to swap quoteAsset for baseAsset
     *
     * @param quoteAsset: quote asset of the swap
     * @param baseAsset:  base asset of the swap
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return swap identifier as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#swap-trade">
     * Swap (TRADE)</a>
     */
    @Returner
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/swap")
    public <T> T swap(String quoteAsset, String baseAsset, double quoteQty, long recvWindow,
                      ReturnFormat format) throws Exception {
        String quoteResponse = sendPostSignedRequest(BSWAP_SWAP_ENDPOINT, createSwapQuery(quoteAsset, baseAsset,
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

    /**
     * Method to create a swap query
     *
     * @param quoteAsset: quote asset of the swap
     * @param baseAsset:  base asset of the swap
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return query as {@link Params}
     */
    private Params createSwapQuery(String quoteAsset, String baseAsset, double quoteQty, long recvWindow) {
        Params query = new Params();
        query.addParam("quoteAsset", quoteAsset);
        query.addParam("baseAsset", baseAsset);
        query.addParam("quoteQty", quoteQty);
        if (recvWindow > -1)
            query.addParam("recvWindow", recvWindow);
        if (recvWindow != -2)
            query.addParam("timestamp", getServerTime());
        return query;
    }

    /**
     * Request to get swap history <br>
     * No-any params required
     *
     * @return swap history as {@link ArrayList} of {@link SwapHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
     * Get Swap History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/swap")
    public ArrayList<SwapHistoryItem> getSwapHistory() throws Exception {
        return getSwapHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get swap history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return swap history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
     * Get Swap History (USER_DATA)</a>
     */
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/swap")
    public <T> T getSwapHistory(ReturnFormat format) throws Exception {
        return getSwapHistory(null, format);
    }

    /**
     * Request to get swap history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "swapId"} -> swap identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> status of the swap, constants available {@link BSwapStatus} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "quoteAsset"} -> quote asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "baseAsset"} -> base asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 3]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return swap history as {@link ArrayList} of {@link SwapHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
     * Get Swap History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/swap")
    public ArrayList<SwapHistoryItem> getSwapHistory(Params extraParams) throws Exception {
        return getSwapHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get swap history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "swapId"} -> swap identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "status"} -> status of the swap, constants available {@link BSwapStatus} - [ENUM]
     *                           </li>
     *                           <li>
     *                                {@code "quoteAsset"} -> quote asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "baseAsset"} -> base asset from fetch the list - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 3]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return swap history as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
     * Get Swap History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "3000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/swap")
    public <T> T getSwapHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetSignedRequest(BSWAP_SWAP_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(historyResponse);
            case LIBRARY_OBJECT:
                ArrayList<SwapHistoryItem> historyItems = new ArrayList<>();
                JSONArray jHistory = new JSONArray(historyResponse);
                for (int j = 0; j < jHistory.length(); j++)
                    historyItems.add(new SwapHistoryItem(jHistory.getJSONObject(j)));
                return (T) historyItems;
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get pool configure <br>
     * No-any params required
     *
     * @return pool configure as {@link ArrayList} of {@link PoolConfigure} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pool-configure-user_data">
     * Get Pool Configure (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/poolConfigure")
    public ArrayList<PoolConfigure> getPoolConfigure() throws Exception {
        return getPoolConfigure(LIBRARY_OBJECT);
    }

    /**
     * Request to get pool configure
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return pool configure as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pool-configure-user_data">
     * Get Pool Configure (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/poolConfigure")
    public <T> T getPoolConfigure(ReturnFormat format) throws Exception {
        return getPoolConfigure(null, format);
    }

    /**
     * Request to get pool configure
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return pool configure as {@link ArrayList} of {@link PoolConfigure} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pool-configure-user_data">
     * Get Pool Configure (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/poolConfigure")
    public ArrayList<PoolConfigure> getPoolConfigure(Params extraParams) throws Exception {
        return getPoolConfigure(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get pool configure
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return pool configure as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pool-configure-user_data">
     * Get Pool Configure (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/poolConfigure")
    public <T> T getPoolConfigure(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String configureResponse = sendGetSignedRequest(BSWAP_POOL_CONFIGURE_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(configureResponse);
            case LIBRARY_OBJECT:
                ArrayList<PoolConfigure> configures = new ArrayList<>();
                JSONArray jConfigures = new JSONArray(configureResponse);
                for (int j = 0; j < jConfigures.length(); j++)
                    configures.add(new PoolConfigure(jConfigures.getJSONObject(j)));
                return (T) configures;
            default:
                return (T) configureResponse;
        }
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param pool:       pool from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public SwapPreview addLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset,
                                           double quoteQty) throws Exception {
        return addLiquidityPreview(pool.getPoolId(), type, quoteAsset, quoteQty, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param pool:       pool from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public <T> T addLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset, double quoteQty,
                                     ReturnFormat format) throws Exception {
        return addLiquidityPreview(pool.getPoolId(), type, quoteAsset, quoteQty, format);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param poolId:     pool identifier from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public SwapPreview addLiquidityPreview(long poolId, PoolType type, String quoteAsset, double quoteQty) throws Exception {
        return addLiquidityPreview(poolId, type, quoteAsset, quoteQty, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param poolId:     pool identifier from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public <T> T addLiquidityPreview(long poolId, PoolType type, String quoteAsset, double quoteQty,
                                     ReturnFormat format) throws Exception {
        return addLiquidityPreview(poolId, type, quoteAsset, quoteQty, -1, format);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param pool:       pool from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public SwapPreview addLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset, double quoteQty,
                                           long recvWindow) throws Exception {
        return addLiquidityPreview(pool.getPoolId(), type, quoteAsset, quoteQty, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param pool:       pool from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public <T> T addLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset, double quoteQty,
                                     long recvWindow, ReturnFormat format) throws Exception {
        return addLiquidityPreview(pool.getPoolId(), type, quoteAsset, quoteQty, recvWindow, format);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param poolId:     pool identifier from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public SwapPreview addLiquidityPreview(long poolId, PoolType type, String quoteAsset, double quoteQty,
                                           long recvWindow) throws Exception {
        return addLiquidityPreview(poolId, type, quoteAsset, quoteQty, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate expected share amount for adding liquidity in single or dual token
     *
     * @param poolId:     pool identifier from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param quoteQty:   quote quantity of the swap
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
     * Add Liquidity Preview (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/addLiquidityPreview")
    public <T> T addLiquidityPreview(long poolId, PoolType type, String quoteAsset, double quoteQty, long recvWindow,
                                     ReturnFormat format) throws Exception {
        Params query = createPreviewPayload(poolId, type, quoteAsset, recvWindow);
        query.addParam("quoteQty", quoteQty);
        return returnSwapPreview(sendGetSignedRequest(BSWAP_ADD_LIQUIDITY_PREVIEW_ENDPOINT, query), format);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param pool:        pool from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public SwapPreview removeLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset,
                                              double shareAmount) throws Exception {
        return removeLiquidityPreview(pool.getPoolId(), type, quoteAsset, shareAmount, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param pool:        pool from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public <T> T removeLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset, double shareAmount,
                                        ReturnFormat format) throws Exception {
        return removeLiquidityPreview(pool.getPoolId(), type, quoteAsset, shareAmount, format);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param poolId:      pool identifier from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public SwapPreview removeLiquidityPreview(long poolId, PoolType type, String quoteAsset,
                                              double shareAmount) throws Exception {
        return removeLiquidityPreview(poolId, type, quoteAsset, shareAmount, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param poolId:      pool identifier from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public <T> T removeLiquidityPreview(long poolId, PoolType type, String quoteAsset, double shareAmount,
                                        ReturnFormat format) throws Exception {
        return removeLiquidityPreview(poolId, type, quoteAsset, shareAmount, -1, format);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param pool:        pool from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public SwapPreview removeLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset, double shareAmount,
                                              long recvWindow) throws Exception {
        return removeLiquidityPreview(pool.getPoolId(), type, quoteAsset, shareAmount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param pool:        pool from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public <T> T removeLiquidityPreview(PoolStructure pool, PoolType type, String quoteAsset, double shareAmount,
                                        long recvWindow, ReturnFormat format) throws Exception {
        return removeLiquidityPreview(pool.getPoolId(), type, quoteAsset, shareAmount, recvWindow, format);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param poolId:      pool identifier from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @return swap preview as {@link SwapPreview} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public SwapPreview removeLiquidityPreview(long poolId, PoolType type, String quoteAsset, double shareAmount,
                                              long recvWindow) throws Exception {
        return removeLiquidityPreview(poolId, type, quoteAsset, shareAmount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to calculate the expected asset amount of single token redemption or dual token redemption
     *
     * @param poolId:      pool identifier from fetch the preview
     * @param type:        type of the preview
     * @param quoteAsset:  quote asset of the preview
     * @param shareAmount: share amount of the preview
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
     * Remove Liquidity Preview (USER_DATA)</a>
     */
    @RequestWeight(weight = "150(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/removeLiquidityPreview")
    public <T> T removeLiquidityPreview(long poolId, PoolType type, String quoteAsset, double shareAmount, long recvWindow,
                                        ReturnFormat format) throws Exception {
        Params query = createPreviewPayload(poolId, type, quoteAsset, recvWindow);
        query.addParam("shareAmount", shareAmount);
        return returnSwapPreview(sendGetSignedRequest(BSWAP_REMOVE_LIQUIDITY_PREVIEW_ENDPOINT, query), format);
    }

    /**
     * Method to create a preview payload
     *
     * @param poolId:     pool identifier from fetch the preview
     * @param type:       type of the preview
     * @param quoteAsset: quote asset of the preview
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return preview payload as {@link Params}
     */
    private Params createPreviewPayload(long poolId, PoolType type, String quoteAsset, long recvWindow) {
        Params query = new Params();
        query.addParam("poolId", poolId);
        query.addParam("type", type);
        query.addParam("quoteAsset", quoteAsset);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        query.addParam("timestamp", getServerTime());
        return query;
    }

    /**
     * Method to create a swap preview
     *
     * @param swapPreviewResponse: obtained from Binance's response
     * @param format:              return type formatter -> {@link ReturnFormat}
     * @return a swap preview as {@code "format"} defines
     */
    @Returner
    private <T> T returnSwapPreview(String swapPreviewResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(swapPreviewResponse);
            case LIBRARY_OBJECT:
                return (T) new SwapPreview(new JSONObject(swapPreviewResponse));
            default:
                return (T) swapPreviewResponse;
        }
    }

    /**
     * Request to get unclaimed rewards record <br>
     * No-any params required
     *
     * @return unclaimed rewards record as {@link UnclaimedRewards} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-unclaimed-rewards-record-user_data">
     * Get Unclaimed Rewards Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/unclaimedRewards")
    public UnclaimedRewards getUnclaimedRewards() throws Exception {
        return getUnclaimedRewards(LIBRARY_OBJECT);
    }

    /**
     * Request to get unclaimed rewards record
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return unclaimed rewards record as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-unclaimed-rewards-record-user_data">
     * Get Unclaimed Rewards Record (USER_DATA)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/unclaimedRewards")
    public <T> T getUnclaimedRewards(ReturnFormat format) throws Exception {
        return getUnclaimedRewards(null, format);
    }

    /**
     * Request to get unclaimed rewards record
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the rewards, constants available {@link RewardsType}
     *                                - [STRING, default {@link RewardsType#SWAP_REWARDS}]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return unclaimed rewards record as {@link UnclaimedRewards} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-unclaimed-rewards-record-user_data">
     * Get Unclaimed Rewards Record (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/unclaimedRewards")
    public UnclaimedRewards getUnclaimedRewards(Params extraParams) throws Exception {
        return getUnclaimedRewards(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get unclaimed rewards record
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the rewards, constants available {@link RewardsType}
     *                                - [STRING, default {@link RewardsType#SWAP_REWARDS}]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return unclaimed rewards record as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-unclaimed-rewards-record-user_data">
     * Get Unclaimed Rewards Record (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/unclaimedRewards")
    public <T> T getUnclaimedRewards(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String rewardsResponse = sendGetSignedRequest(BSWAP_UNCLAIMED_REWARDS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(rewardsResponse);
            case LIBRARY_OBJECT:
                return (T) new UnclaimedRewards(new JSONObject(rewardsResponse));
            default:
                return (T) rewardsResponse;
        }
    }

    /**
     * Request to claim swap rewards or liquidity rewards <br>
     * No-any params required
     *
     * @return claim swap rewards or liquidity rewards result as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#claim-rewards-trade">
     * Claim Rewards (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/claimRewards")
    public boolean claimRewards() throws Exception {
        return Boolean.parseBoolean(claimRewards(LIBRARY_OBJECT));
    }

    /**
     * Request to claim swap rewards or liquidity rewards
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return claim swap rewards or liquidity rewards result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#claim-rewards-trade">
     * Claim Rewards (TRADE)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/claimRewards")
    public <T> T claimRewards(ReturnFormat format) throws Exception {
        return claimRewards(null, format);
    }

    /**
     * Request to claim swap rewards or liquidity rewards
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the rewards, constants available {@link RewardsType}
     *                                - [STRING, default {@link RewardsType#SWAP_REWARDS}]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return claim swap rewards or liquidity rewards result as boolean
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#claim-rewards-trade">
     * Claim Rewards (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/claimRewards")
    public boolean claimRewards(Params extraParams) throws Exception {
        return Boolean.parseBoolean(claimRewards(extraParams, LIBRARY_OBJECT));
    }

    /**
     * Request to claim swap rewards or liquidity rewards
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "type"} -> type of the rewards, constants available {@link RewardsType}
     *                                - [STRING, default {@link RewardsType#SWAP_REWARDS}]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return claim swap rewards or liquidity rewards result as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#claim-rewards-trade">
     * Claim Rewards (TRADE)</a>
     */
    @Returner
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = POST, path = "/sapi/v1/bswap/claimRewards")
    public <T> T claimRewards(Params extraParams, ReturnFormat format) throws Exception {
        String rewardsResponse = sendPostSignedRequest(BSWAP_CLAIM_REWARDS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(rewardsResponse);
            case LIBRARY_OBJECT:
                return (T) String.valueOf(new JSONObject(rewardsResponse).getBoolean("success"));
            default:
                return (T) rewardsResponse;
        }
    }

    /**
     * Request to get history of claimed rewards <br>
     * No-any params required
     *
     * @return history of claimed rewards as {@link  ArrayList} of {@link ClaimedHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-claimed-history-user_data">
     * Get Claimed History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/claimedHistory")
    public ArrayList<ClaimedHistoryItem> getClaimedHistory() throws Exception {
        return getClaimedHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get history of claimed rewards
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return history of claimed rewards as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-claimed-history-user_data">
     * Get Claimed History (USER_DATA)</a>
     */
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/claimedHistory")
    public <T> T getClaimedHistory(ReturnFormat format) throws Exception {
        return getClaimedHistory(null, format);
    }

    /**
     * Request to get history of claimed rewards
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "assetRewards"} -> asset rewards to fetch - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> type of the rewards, constants available {@link RewardsType}
     *                                - [STRING, default {@link RewardsType#SWAP_REWARDS}]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 3]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return history of claimed rewards as {@link  ArrayList} of {@link ClaimedHistoryItem} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-claimed-history-user_data">
     * Get Claimed History (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/claimedHistory")
    public ArrayList<ClaimedHistoryItem> getClaimedHistory(Params extraParams) throws Exception {
        return getClaimedHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get history of claimed rewards
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "poolId"} -> pool identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "assetRewards"} -> asset rewards to fetch - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "type"} -> type of the rewards, constants available {@link RewardsType}
     *                                - [STRING, default {@link RewardsType#SWAP_REWARDS}]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to after get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to before get the records - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 100 - [INT, default 3]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return history of claimed rewards as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-claimed-history-user_data">
     * Get Claimed History (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1000(UID)")
    @RequestPath(method = GET, path = "/sapi/v1/bswap/claimedHistory")
    public <T> T getClaimedHistory(Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("timestamp", getServerTime());
        String historyResponse = sendGetSignedRequest(BSWAP_CLAIMED_HISTORY_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONArray(historyResponse);
            case LIBRARY_OBJECT:
                ArrayList<ClaimedHistoryItem> historyItems = new ArrayList<>();
                JSONArray jHistory = new JSONArray(historyResponse);
                for (int j = 0; j < jHistory.length(); j++)
                    historyItems.add(new ClaimedHistoryItem(jHistory.getJSONObject(j)));
                return (T) historyItems;
            default:
                return (T) historyResponse;
        }
    }

}
