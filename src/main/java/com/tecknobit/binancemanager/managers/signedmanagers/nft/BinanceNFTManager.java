package com.tecknobit.binancemanager.managers.signedmanagers.nft;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTAsset.NFTOrderType;
import com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTAssetsList;
import com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTDepositHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTTransactionHistory;
import com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTWithdrawHistory;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceNFTManager} class is useful to manage NFT endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#nft-endpoints">
 * NFT Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceNFTManager extends BinanceSignedManager {

    /**
     * {@code NFT_HISTORY_TRANSACTIONS_ENDPOINT} is constant for NFT_HISTORY_TRANSACTIONS_ENDPOINT's endpoint
     **/
    public static final String NFT_HISTORY_TRANSACTIONS_ENDPOINT = "/sapi/v1/nft/history/transactions";

    /**
     * {@code NFT_HISTORY_DEPOSIT_ENDPOINT} is constant for NFT_HISTORY_DEPOSIT_ENDPOINT's endpoint
     **/
    public static final String NFT_HISTORY_DEPOSIT_ENDPOINT = "/sapi/v1/nft/history/deposit";

    /**
     * {@code NFT_HISTORY_WITHDRAW_ENDPOINT} is constant for NFT_HISTORY_WITHDRAW_ENDPOINT's endpoint
     **/
    public static final String NFT_HISTORY_WITHDRAW_ENDPOINT = "/sapi/v1/nft/history/withdraw";

    /**
     * {@code NFT_USER_GET_ASSET_ENDPOINT} is constant for NFT_USER_GET_ASSET_ENDPOINT's endpoint
     **/
    public static final String NFT_USER_GET_ASSET_ENDPOINT = "/sapi/v1/nft/user/getAsset";

    /**
     * Constructor to init a {@link BinanceNFTManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceNFTManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceNFTManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceNFTManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceNFTManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceNFTManager(String baseEndpoint, int timeout, String apiKey,
                             String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceNFTManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceNFTManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceNFTManager} <br>
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
    public BinanceNFTManager() {
        super();
    }

    /**
     * Request to get the NFT transaction history
     *
     * @param orderType: type of orders to fetch
     * @return NFT transaction history as {@link NFTTransactionHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-transaction-history-user_data">
     * Get NFT Transaction History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/transactions")
    public NFTTransactionHistory getNFTTransactionHistory(NFTOrderType orderType) throws Exception {
        return getNFTTransactionHistory(orderType, LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT transaction history
     *
     * @param orderType: type of orders to fetch
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return NFT transaction history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-transaction-history-user_data">
     * Get NFT Transaction History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/transactions")
    public <T> T getNFTTransactionHistory(NFTOrderType orderType, ReturnFormat format) throws Exception {
        return getNFTTransactionHistory(orderType, null, format);
    }

    /**
     * Request to get the NFT transaction history
     *
     * @param orderType:   type of orders to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return NFT transaction history as {@link NFTTransactionHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-transaction-history-user_data">
     * Get NFT Transaction History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/transactions")
    public NFTTransactionHistory getNFTTransactionHistory(NFTOrderType orderType, Params extraParams) throws Exception {
        return getNFTTransactionHistory(orderType, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT transaction history
     *
     * @param orderType:   type of orders to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return NFT transaction history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-transaction-history-user_data">
     * Get NFT Transaction History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/transactions")
    public <T> T getNFTTransactionHistory(NFTOrderType orderType, Params extraParams, ReturnFormat format) throws Exception {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("orderType", orderType.getType());
        String historyResponse = sendGetSignedRequest(NFT_HISTORY_TRANSACTIONS_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new NFTTransactionHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get the NFT deposit history <br>
     * No-any params required
     *
     * @return NFT deposit history as {@link NFTDepositHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-deposit-history-user_data">
     * Get NFT Deposit History(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/deposit")
    public NFTDepositHistory getNFTDepositHistory() throws Exception {
        return getNFTDepositHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT deposit history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return NFT deposit history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-deposit-history-user_data">
     * Get NFT Deposit History(USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/deposit")
    public <T> T getNFTDepositHistory(ReturnFormat format) throws Exception {
        return getNFTDepositHistory(null, format);
    }

    /**
     * Request to get the NFT deposit history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return NFT deposit history as {@link NFTDepositHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-deposit-history-user_data">
     * Get NFT Deposit History(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/deposit")
    public NFTDepositHistory getNFTDepositHistory(Params extraParams) throws Exception {
        return getNFTDepositHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT deposit history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return NFT deposit history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-deposit-history-user_data">
     * Get NFT Deposit History(USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/deposit")
    public <T> T getNFTDepositHistory(Params extraParams, ReturnFormat format) throws Exception {
        String historyResponse = sendGetSignedRequest(NFT_HISTORY_DEPOSIT_ENDPOINT, createTimestampPayload(extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new NFTDepositHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get the NFT withdraw history <br>
     * No-any params required
     *
     * @return NFT withdraw history as {@link NFTWithdrawHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-withdraw-history-user_data">
     * Get NFT Withdraw History(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/withdraw")
    public NFTWithdrawHistory getNFTWithdrawHistory() throws Exception {
        return getNFTWithdrawHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT withdraw history
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return NFT withdraw history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-withdraw-history-user_data">
     * Get NFT Withdraw History(USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/withdraw")
    public <T> T getNFTWithdrawHistory(ReturnFormat format) throws Exception {
        return getNFTWithdrawHistory(null, format);
    }

    /**
     * Request to get the NFT withdraw history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return NFT withdraw history as {@link NFTWithdrawHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-withdraw-history-user_data">
     * Get NFT Withdraw History(USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/withdraw")
    public NFTWithdrawHistory getNFTWithdrawHistory(Params extraParams) throws Exception {
        return getNFTWithdrawHistory(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT withdraw history
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "startTime"} -> results that matching after this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> results that matching before this time will be fetched - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return NFT withdraw history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-withdraw-history-user_data">
     * Get NFT Withdraw History(USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/nft/history/withdraw")
    public <T> T getNFTWithdrawHistory(Params extraParams, ReturnFormat format) throws Exception {
        String historyResponse = sendGetSignedRequest(NFT_HISTORY_WITHDRAW_ENDPOINT, createTimestampPayload(extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONObject(historyResponse);
            case LIBRARY_OBJECT:
                return (T) new NFTWithdrawHistory(new JSONObject(historyResponse));
            default:
                return (T) historyResponse;
        }
    }

    /**
     * Request to get the NFT Asset <br>
     * No-any params required
     *
     * @return NFT assets list as {@link NFTAssetsList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-asset-user_data">
     * Get NFT Asset (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/user/getAsset")
    public NFTAssetsList getNFTAsset() throws Exception {
        return getNFTAsset(LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT Asset
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return NFT assets list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-asset-user_data">
     * Get NFT Asset (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/nft/user/getAsset")
    public <T> T getNFTAsset(ReturnFormat format) throws Exception {
        return getNFTAsset(null, format);
    }

    /**
     * Request to get the NFT Asset
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return NFT assets list as {@link NFTAssetsList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-asset-user_data">
     * Get NFT Asset (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/nft/user/getAsset")
    public NFTAssetsList getNFTAsset(Params extraParams) throws Exception {
        return getNFTAsset(extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get the NFT Asset
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> size of the results per page, max 50 - [INT, default 50]
     *                           </li>
     *                           <li>
     *                                {@code "page"} -> page from fetch the results - [INT, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return NFT assets list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-asset-user_data">
     * Get NFT Asset (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/nft/user/getAsset")
    public <T> T getNFTAsset(Params extraParams, ReturnFormat format) throws Exception {
        String listResponse = sendGetSignedRequest(NFT_USER_GET_ASSET_ENDPOINT, createTimestampPayload(extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONObject(listResponse);
            case LIBRARY_OBJECT:
                return (T) new NFTAssetsList(new JSONObject(listResponse));
            default:
                return (T) listResponse;
        }
    }

}
