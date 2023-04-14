package com.tecknobit.binancemanager.managers.signedmanagers.mining;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring.AcquiringAlgorithm;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring.AcquiringCoinName;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.hashrateresale.HashRateResaleConfiguration;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.hashrateresale.HashrateResaleRequest;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale.HashRateResaleDetail;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale.HashrateResaleList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.EarningsList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.ExtraBonusList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.AccountList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.MiningAccountEarning;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.StatisticList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.DetailMinerList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.MinerList;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.MinerList.SortColumn;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.MinerList.SortSequence;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.MinerList.WorkerStatus;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceMiningManager} class is useful to manage mining endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#mining-endpoints">
 * Mining Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceMiningManager extends BinanceSignedManager {

    /**
     * {@code MINING_PUB_ALGOLIST_ENDPOINT} is constant for MINING_PUB_ALGOLIST_ENDPOINT's endpoint
     **/
    public static final String MINING_PUB_ALGOLIST_ENDPOINT = "/sapi/v1/mining/pub/algoList";

    /**
     * {@code MINING_PUB_COINLIST_ENDPOINT} is constant for MINING_PUB_COINLIST_ENDPOINT's endpoint
     **/
    public static final String MINING_PUB_COINLIST_ENDPOINT = "/sapi/v1/mining/pub/coinList";

    /**
     * {@code MINING_WORKER_DETAIL_ENDPOINT} is constant for MINING_WORKER_DETAIL_ENDPOINT's endpoint
     **/
    public static final String MINING_WORKER_DETAIL_ENDPOINT = "/sapi/v1/mining/worker/detail";

    /**
     * {@code MINING_WORKER_LIST_ENDPOINT} is constant for MINING_WORKER_LIST_ENDPOINT's endpoint
     **/
    public static final String MINING_WORKER_LIST_ENDPOINT = "/sapi/v1/mining/worker/list";

    /**
     * {@code MINING_PAYMENT_LIST_ENDPOINT} is constant for MINING_PAYMENT_LIST_ENDPOINT's endpoint
     **/
    public static final String MINING_PAYMENT_LIST_ENDPOINT = "/sapi/v1/mining/payment/list";

    /**
     * {@code MINING_PAYMENT_OTHER_ENDPOINT} is constant for MINING_PAYMENT_OTHER_ENDPOINT's endpoint
     **/
    public static final String MINING_PAYMENT_OTHER_ENDPOINT = "/sapi/v1/mining/payment/other";

    /**
     * {@code MINING_HASH_TRANSFER_CONFIG_DETAILS_LIST_ENDPOINT} is constant for MINING_HASH_TRANSFER_CONFIG_DETAILS_LIST_ENDPOINT's endpoint
     **/
    public static final String MINING_HASH_TRANSFER_CONFIG_DETAILS_LIST_ENDPOINT = "/sapi/v1/mining/hash-transfer/config/details/list";

    /**
     * {@code MINING_HASH_TRANSFER_PROFIT_DETAILS_ENDPOINT} is constant for MINING_HASH_TRANSFER_PROFIT_DETAILS_ENDPOINT's endpoint
     **/
    public static final String MINING_HASH_TRANSFER_PROFIT_DETAILS_ENDPOINT = "/sapi/v1/mining/hash-transfer/profit/details";

    /**
     * {@code MINING_HASH_TRANSFER_CONFIG_ENDPOINT} is constant for MINING_HASH_TRANSFER_CONFIG_ENDPOINT's endpoint
     **/
    public static final String MINING_HASH_TRANSFER_CONFIG_ENDPOINT = "/sapi/v1/mining/hash-transfer/config";

    /**
     * {@code MINING_HASH_TRANSFER_CONFIG_CANCEL_ENDPOINT} is constant for MINING_HASH_TRANSFER_CONFIG_CANCEL_ENDPOINT's endpoint
     **/
    public static final String MINING_HASH_TRANSFER_CONFIG_CANCEL_ENDPOINT = "/sapi/v1/mining/hash-transfer/config/cancel";

    /**
     * {@code MINING_STATISTICS_USER_STATUS_ENDPOINT} is constant for MINING_STATISTICS_USER_STATUS_ENDPOINT's endpoint
     **/
    public static final String MINING_STATISTICS_USER_STATUS_ENDPOINT = "/sapi/v1/mining/statistics/user/status";

    /**
     * {@code MINING_STATISTICS_USER_LIST_ENDPOINT} is constant for MINING_STATISTICS_USER_LIST_ENDPOINT's endpoint
     **/
    public static final String MINING_STATISTICS_USER_LIST_ENDPOINT = "/sapi/v1/mining/statistics/user/list";

    /**
     * {@code MINING_PAYMENT_UID_ENDPOINT} is constant for MINING_PAYMENT_UID_ENDPOINT's endpoint
     **/
    public static final String MINING_PAYMENT_UID_ENDPOINT = "/sapi/v1/mining/payment/uid";

    /**
     * Constructor to init a {@link BinanceMiningManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceMiningManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMiningManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceMiningManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMiningManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceMiningManager(String baseEndpoint, int timeout, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMiningManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceMiningManager(String baseEndpoint, String apiKey,
                                String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceMiningManager} <br>
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
    public BinanceMiningManager() {
        super();
    }

    /**
     * Request to acquiring algorithm <br>
     * No-any params required
     *
     * @return acquiring algorithm as {@link AcquiringAlgorithm} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-algorithm-market_data">
     * Acquiring Algorithm (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/pub/algoList")
    public AcquiringAlgorithm acquiringAlgorithm() throws Exception {
        return acquiringAlgorithm(LIBRARY_OBJECT);
    }

    /**
     * Request to acquiring algorithm
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return acquiring Algorithm as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-algorithm-market_data">
     * Acquiring Algorithm (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/pub/algoList")
    public <T> T acquiringAlgorithm(ReturnFormat format) throws Exception {
        String algorithmResponse = sendGetSignedRequest(MINING_PUB_ALGOLIST_ENDPOINT);
        switch (format) {
            case JSON:
                return (T) new JSONObject(algorithmResponse);
            case LIBRARY_OBJECT:
                return (T) new AcquiringAlgorithm(new JSONObject(algorithmResponse));
            default:
                return (T) algorithmResponse;
        }
    }

    /**
     * Request to acquiring acquiring coin name <br>
     * No-any params required
     *
     * @return acquiring coin name as {@link AcquiringCoinName} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-coinname-market_data">
     * Acquiring CoinName (MARKET_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/pub/coinList")
    public AcquiringCoinName acquiringCoinName() throws Exception {
        return acquiringCoinName(LIBRARY_OBJECT);
    }

    /**
     * Request to acquiring acquiring coin name
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return acquiring coin name as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-coinname-market_data">
     * Acquiring CoinName (MARKET_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/pub/coinList")
    public <T> T acquiringCoinName(ReturnFormat format) throws Exception {
        String coinNameResponse = sendGetSignedRequest(MINING_PUB_COINLIST_ENDPOINT);
        switch (format) {
            case JSON:
                return (T) new JSONObject(coinNameResponse);
            case LIBRARY_OBJECT:
                return (T) new AcquiringCoinName(new JSONObject(coinNameResponse));
            default:
                return (T) coinNameResponse;
        }
    }

    /**
     * Request to request for detail miner list
     *
     * @param algo:       algorithm
     * @param userName:   mining account
     * @param workerName: miner’s name
     * @return detail miner list as {@link DetailMinerList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-detail-miner-list-user_data">
     * Request for Detail Miner List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/detail")
    public DetailMinerList getDetailMinerList(String algo, String userName, String workerName) throws Exception {
        return getDetailMinerList(algo, userName, workerName, LIBRARY_OBJECT);
    }

    /**
     * Request to request for detail miner list
     *
     * @param algo:       algorithm
     * @param userName:   mining account
     * @param workerName: miner’s name
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return detail miner list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-detail-miner-list-user_data">
     * Request for Detail Miner List (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/detail")
    public <T> T getDetailMinerList(String algo, String userName, String workerName, ReturnFormat format) throws Exception {
        return getDetailMinerList(algo, userName, workerName, -1, format);
    }

    /**
     * Request to request for detail miner list
     *
     * @param algo:       algorithm
     * @param userName:   mining account
     * @param workerName: miner’s name
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return detail miner list as {@link DetailMinerList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-detail-miner-list-user_data">
     * Request for Detail Miner List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/detail")
    public DetailMinerList getDetailMinerList(String algo, String userName, String workerName,
                                              long recvWindow) throws Exception {
        return getDetailMinerList(algo, userName, workerName, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to request for detail miner list
     *
     * @param algo:       algorithm
     * @param userName:   mining account
     * @param workerName: miner’s name
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return detail miner list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-detail-miner-list-user_data">
     * Request for Detail Miner List (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/detail")
    public <T> T getDetailMinerList(String algo, String userName, String workerName, long recvWindow,
                                    ReturnFormat format) throws Exception {
        Params query = createAlgoQuery(algo, userName);
        query.addParam("workerName", workerName);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        String listResponse = sendGetSignedRequest(MINING_WORKER_DETAIL_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new DetailMinerList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to request for miner list
     *
     * @param algo:     algorithm
     * @param userName: mining account
     * @return miner list as {@link MinerList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
     * Request for Miner List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/list")
    public MinerList getMinerList(String algo, String userName) throws Exception {
        return getMinerList(algo, userName, LIBRARY_OBJECT);
    }

    /**
     * Request to request for miner list
     *
     * @param algo:     algorithm
     * @param userName: mining account
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return miner list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
     * Request for Miner List (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/list")
    public <T> T getMinerList(String algo, String userName, ReturnFormat format) throws Exception {
        return getMinerList(algo, userName, null, format);
    }

    /**
     * Request to request for miner list
     *
     * @param algo:        algorithm
     * @param userName:    mining account
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "sort"} -> sort sequence, constants available {@link SortSequence}
     *                                - [INTEGER, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "sortColumn"} -> sort by, constants available {@link SortColumn}
     *                                - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "workerStatus"} -> worker status, constants available {@link WorkerStatus}
     *                                - [INTEGER, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return miner list as {@link MinerList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
     * Request for Miner List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/list")
    public MinerList getMinerList(String algo, String userName, Params extraParams) throws Exception {
        return getMinerList(algo, userName, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to request for miner list
     *
     * @param algo:        algorithm
     * @param userName:    mining account
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "sort"} -> sort sequence, constants available {@link SortSequence}
     *                                - [INTEGER, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "sortColumn"} -> sort by, constants available {@link SortColumn}
     *                                - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "workerStatus"} -> worker status, constants available {@link WorkerStatus}
     *                                - [INTEGER, default 0]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return miner list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
     * Request for Miner List (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/worker/list")
    public <T> T getMinerList(String algo, String userName, Params extraParams, ReturnFormat format) throws Exception {
        Params query = createAlgoQuery(algo, userName);
        query.mergeParams(extraParams);
        String listResponse = sendGetSignedRequest(MINING_WORKER_LIST_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new MinerList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the earnings list
     *
     * @param algo:     algorithm
     * @param userName: mining account
     * @return earnings list as {@link EarningsList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
     * Earnings List(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/list")
    public EarningsList getEarningsList(String algo, String userName) throws Exception {
        return getEarningsList(algo, userName, LIBRARY_OBJECT);
    }

    /**
     * Request to get the earnings list
     *
     * @param algo:     algorithm
     * @param userName: mining account
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return earnings list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
     * Earnings List(USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/list")
    public <T> T getEarningsList(String algo, String userName, ReturnFormat format) throws Exception {
        return getEarningsList(algo, userName, null, format);
    }

    /**
     * Request to get the earnings list
     *
     * @param algo:        algorithm
     * @param userName:    mining account
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin name - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> number of pages, maximum 200 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return earnings list as {@link EarningsList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
     * Earnings List(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/list")
    public EarningsList getEarningsList(String algo, String userName, Params extraParams) throws Exception {
        return getEarningsList(algo, userName, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the earnings list
     *
     * @param algo:        algorithm
     * @param userName:    mining account
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin name - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> number of pages, maximum 200 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return earnings list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
     * Earnings List(USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/list")
    public <T> T getEarningsList(String algo, String userName, Params extraParams, ReturnFormat format) throws Exception {
        Params query = createAlgoQuery(algo, userName);
        query.mergeParams(extraParams);
        String listResponse = sendGetSignedRequest(MINING_PAYMENT_LIST_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new EarningsList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the extra bonus list
     *
     * @param algo:     algorithm
     * @param userName: mining account
     * @return extra bonus list as {@link ExtraBonusList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
     * Extra Bonus List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/other")
    public ExtraBonusList getExtraBonusList(String algo, String userName) throws Exception {
        return getExtraBonusList(algo, userName, LIBRARY_OBJECT);
    }

    /**
     * Request to get the extra bonus list
     *
     * @param algo:     algorithm
     * @param userName: mining account
     * @param format:   return type formatter -> {@link ReturnFormat}
     * @return extra bonus list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
     * Extra Bonus List (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/other")
    public <T> T getExtraBonusList(String algo, String userName, ReturnFormat format) throws Exception {
        return getExtraBonusList(algo, userName, null, format);
    }

    /**
     * Request to get the extra bonus list
     *
     * @param algo:        algorithm
     * @param userName:    mining account
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin name - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> number of pages, maximum 200 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return extra bonus list as {@link ExtraBonusList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
     * Extra Bonus List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/other")
    public ExtraBonusList getExtraBonusList(String algo, String userName, Params extraParams) throws Exception {
        return getExtraBonusList(algo, userName, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the extra bonus list
     *
     * @param algo:        algorithm
     * @param userName:    mining account
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "coin"} -> coin name - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "startDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endDate"} -> search date, millisecond timestamp, while empty query all
     *                                - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> number of pages, maximum 200 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return extra bonus list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
     * Extra Bonus List (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/payment/other")
    public <T> T getExtraBonusList(String algo, String userName, Params extraParams, ReturnFormat format) throws Exception {
        Params query = createAlgoQuery(algo, userName);
        query.mergeParams(extraParams);
        String listResponse = sendGetSignedRequest(MINING_PAYMENT_OTHER_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new ExtraBonusList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    /**
     * Request to get the hashrate resale list <br>
     * No-any params required
     *
     * @return hashrate resale list as {@link HashrateResaleList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
     * Hashrate Resale List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/hash-transfer/config/details/list")
    public HashrateResaleList getHashrateResaleList() throws Exception {
        return getHashrateResaleList(LIBRARY_OBJECT);
    }

    /**
     * Request to get the hashrate resale list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return hashrate resale list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
     * Hashrate Resale List (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/mining/hash-transfer/config/details/list")
    public <T> T getHashrateResaleList(ReturnFormat format) throws Exception {
        return getHashrateResaleList(null, format);
    }

    /**
     * Request to get the hashrate resale list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> number of pages, maximum 200 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return hashrate resale list as {@link HashrateResaleList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
     * Hashrate Resale List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/mining/hash-transfer/config/details/list")
    public HashrateResaleList getHashrateResaleList(Params extraParams) throws Exception {
        return getHashrateResaleList(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the hashrate resale list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "pageIndex"} -> page number - [INTEGER, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "pageSize"} -> number of pages, maximum 200 - [INTEGER, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return hashrate resale list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
     * Hashrate Resale List (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/mining/hash-transfer/config/details/list")
    public <T> T getHashrateResaleList(Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        String listResponse = sendGetSignedRequest(MINING_HASH_TRANSFER_CONFIG_DETAILS_LIST_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new HashrateResaleList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    public HashRateResaleDetail getHashrateResaleDetail(long configId, String userName) throws Exception {
        return getHashrateResaleDetail(configId, userName, LIBRARY_OBJECT);
    }

    public <T> T getHashrateResaleDetail(long configId, String userName, ReturnFormat format) throws Exception {
        return getHashrateResaleDetail(configId, userName, null, format);
    }

    public HashRateResaleDetail getHashrateResaleDetail(long configId, String userName, Params extraParams) throws Exception {
        return getHashrateResaleDetail(configId, userName, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getHashrateResaleDetail(long configId, String userName, Params extraParams,
                                         ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.mergeParams(createConfigPayload(configId, userName));
        String listResponse = sendGetSignedRequest(MINING_HASH_TRANSFER_PROFIT_DETAILS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new HashRateResaleDetail(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    public HashrateResaleRequest execHashrateResaleRequest(String userName, String algo, long endDate, long startDate,
                                                           String toPoolUser, long hashRate) throws Exception {
        return execHashrateResaleRequest(userName, algo, endDate, startDate, toPoolUser, hashRate, LIBRARY_OBJECT);
    }

    public <T> T execHashrateResaleRequest(String userName, String algo, long endDate, long startDate, String toPoolUser,
                                           long hashRate, ReturnFormat format) throws Exception {
        return execHashrateResaleRequest(userName, algo, endDate, startDate, toPoolUser, hashRate, -1, format);
    }

    public HashrateResaleRequest execHashrateResaleRequest(String userName, String algo, long endDate, long startDate,
                                                           String toPoolUser, long hashRate,
                                                           long recvWindow) throws Exception {
        return execHashrateResaleRequest(userName, algo, endDate, startDate, toPoolUser, hashRate, recvWindow,
                LIBRARY_OBJECT);
    }

    public <T> T execHashrateResaleRequest(String userName, String algo, long endDate, long startDate, String toPoolUser,
                                           long hashRate, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("userName", userName);
        payload.addParam("algo", algo);
        payload.addParam("endDate", endDate);
        payload.addParam("startDate", startDate);
        payload.addParam("toPoolUser", toPoolUser);
        payload.addParam("hashRate", hashRate);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String resaleResponse = sendPostSignedRequest(MINING_HASH_TRANSFER_CONFIG_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(resaleResponse);
            case LIBRARY_OBJECT:
                return (T) new HashrateResaleRequest(new JSONObject(resaleResponse));
            default:
                return (T) resaleResponse;
        }
    }

    public HashRateResaleConfiguration cancelHashrateResaleConfiguration(long configId, String userName) throws Exception {
        return cancelHashrateResaleConfiguration(configId, userName, LIBRARY_OBJECT);
    }

    public <T> T cancelHashrateResaleConfiguration(long configId, String userName, ReturnFormat format) throws Exception {
        return cancelHashrateResaleConfiguration(configId, userName, -1, format);
    }

    public HashRateResaleConfiguration cancelHashrateResaleConfiguration(long configId, String userName,
                                                                         long recvWindow) throws Exception {
        return cancelHashrateResaleConfiguration(configId, userName, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T cancelHashrateResaleConfiguration(long configId, String userName, long recvWindow,
                                                   ReturnFormat format) throws Exception {
        Params payload = createConfigPayload(configId, userName);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        String configResponse = sendPostSignedRequest(MINING_HASH_TRANSFER_CONFIG_CANCEL_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(configResponse);
            case LIBRARY_OBJECT:
                return (T) new HashRateResaleConfiguration(new JSONObject(configResponse));
            default:
                return (T) configResponse;
        }
    }

    private Params createConfigPayload(long configId, String userName) {
        Params payload = createTimestampPayload(null);
        payload.addParam("configId", configId);
        payload.addParam("userName", userName);
        return payload;
    }

    public StatisticList getStatisticList(String algo, String userName) throws Exception {
        return getStatisticList(algo, userName, LIBRARY_OBJECT);
    }

    public <T> T getStatisticList(String algo, String userName, ReturnFormat format) throws Exception {
        return getStatisticList(algo, userName, -1, format);
    }

    public StatisticList getStatisticList(String algo, String userName, long recvWindow) throws Exception {
        return getStatisticList(algo, userName, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getStatisticList(String algo, String userName, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createAlgoQuery(algo, userName);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        String listResponse = sendGetSignedRequest(MINING_STATISTICS_USER_STATUS_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new StatisticList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    public AccountList getAccountList(String algo, String userName) throws Exception {
        return getAccountList(algo, userName, LIBRARY_OBJECT);
    }

    public <T> T getAccountList(String algo, String userName, ReturnFormat format) throws Exception {
        return getAccountList(algo, userName, -1, format);
    }

    public AccountList getAccountList(String algo, String userName, long recvWindow) throws Exception {
        return getAccountList(algo, userName, recvWindow, LIBRARY_OBJECT);
    }

    public <T> T getAccountList(String algo, String userName, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createAlgoQuery(algo, userName);
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        String listResponse = sendGetSignedRequest(MINING_STATISTICS_USER_LIST_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new AccountList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

    private Params createAlgoQuery(String algo, String userName) {
        Params query = createTimestampPayload(null);
        query.addParam("algo", algo);
        query.addParam("userName", userName);
        return query;
    }

    public MiningAccountEarning getMiningAccountEarning(String algo) throws Exception {
        return getMiningAccountEarning(algo, LIBRARY_OBJECT);
    }

    public <T> T getMiningAccountEarning(String algo, ReturnFormat format) throws Exception {
        return getMiningAccountEarning(algo, null, format);
    }

    public MiningAccountEarning getMiningAccountEarning(String algo, Params extraParams) throws Exception {
        return getMiningAccountEarning(algo, extraParams, LIBRARY_OBJECT);
    }

    public <T> T getMiningAccountEarning(String algo, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("algo", algo);
        String listResponse = sendGetSignedRequest(MINING_PAYMENT_UID_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new MiningAccountEarning(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

}
