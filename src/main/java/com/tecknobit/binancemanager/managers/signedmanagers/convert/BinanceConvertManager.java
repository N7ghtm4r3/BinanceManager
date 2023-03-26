package com.tecknobit.binancemanager.managers.signedmanagers.convert;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.WrappedRequest;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.convert.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.convert.records.QuoteRequest.ValidTime;
import com.tecknobit.binancemanager.managers.signedmanagers.convert.records.QuoteRequest.WalletType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceConvertManager} class is useful to manage convert endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#convert-endpoints">
 * Convert Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceConvertManager extends BinanceSignedManager {

    /**
     * Constructor to init a {@link BinanceConvertManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceConvertManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                 String secretKey) throws Exception {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceConvertManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceConvertManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                 String secretKey) throws Exception {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceConvertManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceConvertManager(String baseEndpoint, int timeout, String apiKey,
                                 String secretKey) throws Exception {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceConvertManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceConvertManager(String baseEndpoint, String apiKey, String secretKey) throws Exception {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceConvertManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceConvertManager}'s manager without re-insert
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
    public BinanceConvertManager() {
        super();
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param fromAsset: user spends coin
     * @return convert pairs list as {@link ArrayList} of {@link ConvertPair} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * List All Convert Pairs</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/exchangeInfo")
    public ArrayList<ConvertPair> getAllFromConvertPairs(String fromAsset) throws Exception {
        return getAllFromConvertPairs(fromAsset, LIBRARY_OBJECT);
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param fromAsset: user spends coin
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return convert pairs list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * List All Convert Pairs</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/convert/exchangeInfo")
    public <T> T getAllFromConvertPairs(String fromAsset, ReturnFormat format) throws Exception {
        return getAllConvertPairs(fromAsset, null, format);
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param toAsset: user receives coin
     * @return convert pairs list as {@link ArrayList} of {@link ConvertPair} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * List All Convert Pairs</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/exchangeInfo")
    public ArrayList<ConvertPair> getAllToConvertPairs(String toAsset) throws Exception {
        return getAllToConvertPairs(toAsset, LIBRARY_OBJECT);
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param toAsset: user receives coin
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return convert pairs list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * List All Convert Pairs</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/convert/exchangeInfo")
    public <T> T getAllToConvertPairs(String toAsset, ReturnFormat format) throws Exception {
        return getAllConvertPairs(null, toAsset, format);
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param fromAsset: user spends coin
     * @param toAsset:   user receives coin
     * @return convert pairs list as {@link ArrayList} of {@link ConvertPair} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * List All Convert Pairs</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/exchangeInfo")
    public ArrayList<ConvertPair> getAllConvertPairs(String fromAsset, String toAsset) throws Exception {
        return getAllConvertPairs(fromAsset, toAsset, LIBRARY_OBJECT);
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param fromAsset: user spends coin
     * @param toAsset:   user receives coin
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return convert pairs list {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
     * List All Convert Pairs</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/convert/exchangeInfo")
    public <T> T getAllConvertPairs(String fromAsset, String toAsset, ReturnFormat format) throws Exception {
        Params query = new Params();
        if (fromAsset != null)
            query.addParam("fromAsset", fromAsset);
        if (toAsset != null)
            query.addParam("toAsset", toAsset);
        String convertPairsResponse = sendGetRequest(CONVERT_EXCHANGE_INFO_ENDPOINT, query.createQueryString());
        switch (format) {
            case JSON:
                return (T) new JSONArray(convertPairsResponse);
            case LIBRARY_OBJECT:
                ArrayList<ConvertPair> pairs = new ArrayList<>();
                JSONArray jPairs = new JSONArray(convertPairsResponse);
                for (int j = 0; j < jPairs.length(); j++)
                    pairs.add(new ConvertPair(jPairs.getJSONObject(j)));
                return (T) pairs;
            default:
                return (T) convertPairsResponse;
        }
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits <br>
     * No-any params required
     *
     * @return order quantity precision per asset as {@link ArrayList} of {@link OrderQuantityPrecision} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-quantity-precision-per-asset-user_data">
     * Query order quantity precision per asset (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/assetInfo")
    public ArrayList<OrderQuantityPrecision> getOrderQuantityPrecisionPerAsset() throws Exception {
        return getOrderQuantityPrecisionPerAsset(LIBRARY_OBJECT);
    }

    /**
     * Request to query for all convertible token pairs and the tokens’ respective upper/lower limits
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order quantity precision per asset {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-quantity-precision-per-asset-user_data">
     * Query order quantity precision per asset (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/convert/assetInfo")
    public <T> T getOrderQuantityPrecisionPerAsset(ReturnFormat format) throws Exception {
        String orderQuantityPrecisionResponse = sendGetSignedRequest(ASSET_INFO_ENDPOINT, getTimestampParam());
        switch (format) {
            case JSON:
                return (T) new JSONArray(orderQuantityPrecisionResponse);
            case LIBRARY_OBJECT:
                ArrayList<OrderQuantityPrecision> orderQuantityPrecisions = new ArrayList<>();
                JSONArray jOrderQuantityPrecisions = new JSONArray(orderQuantityPrecisionResponse);
                for (int j = 0; j < jOrderQuantityPrecisions.length(); j++)
                    orderQuantityPrecisions.add(new OrderQuantityPrecision(jOrderQuantityPrecisions.getJSONObject(j)));
                return (T) orderQuantityPrecisions;
            default:
                return (T) orderQuantityPrecisionResponse;
        }
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:  user spends coin
     * @param toAsset:    user receives coin
     * @param fromAmount: the amount you will be debited after the conversion
     * @return request quote as {@link QuoteRequest} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public QuoteRequest sendFromQuoteRequest(String fromAsset, String toAsset, double fromAmount) throws Exception {
        return sendFromQuoteRequest(fromAsset, toAsset, fromAmount, LIBRARY_OBJECT);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:  user spends coin
     * @param toAsset:    user receives coin
     * @param fromAmount: the amount you will be debited after the conversion
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return request quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public <T> T sendFromQuoteRequest(String fromAsset, String toAsset, double fromAmount,
                                      ReturnFormat format) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, fromAmount, -1, format);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:   user spends coin
     * @param toAsset:     user receives coin
     * @param fromAmount:  the amount you will be debited after the conversion
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "walletType"} -> wallet type, constants available {@link WalletType}
     *                                - [STRING, default SPOT]
     *                           </li>
     *                           <li>
     *                                {@code "validTime"} -> valid time, constants available {@link ValidTime}
     *                                - [STRING, default 10s]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                            - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return request quote as {@link QuoteRequest} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public QuoteRequest sendFromQuoteRequest(String fromAsset, String toAsset, double fromAmount,
                                             Params extraParams) throws Exception {
        return sendFromQuoteRequest(fromAsset, toAsset, fromAmount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:   user spends coin
     * @param toAsset:     user receives coin
     * @param fromAmount:  the amount you will be debited after the conversion
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "walletType"} -> wallet type, constants available {@link WalletType}
     *                                - [STRING, default SPOT]
     *                           </li>
     *                           <li>
     *                                {@code "validTime"} -> valid time, constants available {@link ValidTime}
     *                                - [STRING, default 10s]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                            - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return request quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public <T> T sendFromQuoteRequest(String fromAsset, String toAsset, double fromAmount, Params extraParams,
                                      ReturnFormat format) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, fromAmount, -1, extraParams, format);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset: user spends coin
     * @param toAsset:   user receives coin
     * @param toAmount:  the amount you will be debited after the conversion
     * @return request quote as {@link QuoteRequest} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public QuoteRequest sendToQuoteRequest(String fromAsset, String toAsset, double toAmount) throws Exception {
        return sendToQuoteRequest(fromAsset, toAsset, toAmount, LIBRARY_OBJECT);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset: user spends coin
     * @param toAsset:   user receives coin
     * @param toAmount:  the amount you will be debited after the conversion
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return request quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public <T> T sendToQuoteRequest(String fromAsset, String toAsset, double toAmount,
                                    ReturnFormat format) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, -1, toAmount, format);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:   user spends coin
     * @param toAsset:     user receives coin
     * @param toAmount:    the amount you will be debited after the conversion
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "walletType"} -> wallet type, constants available {@link WalletType}
     *                                - [STRING, default SPOT]
     *                           </li>
     *                           <li>
     *                                {@code "validTime"} -> valid time, constants available {@link ValidTime}
     *                                - [STRING, default 10s]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                            - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return request quote as {@link QuoteRequest} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public QuoteRequest sendToQuoteRequest(String fromAsset, String toAsset, double toAmount,
                                           Params extraParams) throws Exception {
        return sendToQuoteRequest(fromAsset, toAsset, toAmount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:   user spends coin
     * @param toAsset:     user receives coin
     * @param toAmount:    the amount you will be debited after the conversion
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "walletType"} -> wallet type, constants available {@link WalletType}
     *                                - [STRING, default SPOT]
     *                           </li>
     *                           <li>
     *                                {@code "validTime"} -> valid time, constants available {@link ValidTime}
     *                                - [STRING, default 10s]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                            - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return request quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public <T> T sendToQuoteRequest(String fromAsset, String toAsset, double toAmount, Params extraParams,
                                    ReturnFormat format) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, -1, toAmount, extraParams, format);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:  user spends coin
     * @param toAsset:    user receives coin
     * @param fromAmount: the amount you will be debited after the conversion
     * @param toAmount:   the amount you will be debited after the conversion
     * @return request quote as {@link QuoteRequest} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public QuoteRequest sendQuoteRequest(String fromAsset, String toAsset, double fromAmount,
                                         double toAmount) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, fromAmount, toAmount, LIBRARY_OBJECT);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:  user spends coin
     * @param toAsset:    user receives coin
     * @param fromAmount: the amount you will be debited after the conversion
     * @param toAmount:   the amount you will be debited after the conversion
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return request quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public <T> T sendQuoteRequest(String fromAsset, String toAsset, double fromAmount, double toAmount,
                                  ReturnFormat format) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, fromAmount, toAmount, null, format);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:   user spends coin
     * @param toAsset:     user receives coin
     * @param fromAmount:  the amount you will be debited after the conversion
     * @param toAmount:    the amount you will be debited after the conversion
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "walletType"} -> wallet type, constants available {@link WalletType}
     *                                - [STRING, default SPOT]
     *                           </li>
     *                           <li>
     *                                {@code "validTime"} -> valid time, constants available {@link ValidTime}
     *                                - [STRING, default 10s]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                            - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return request quote as {@link QuoteRequest} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public QuoteRequest sendQuoteRequest(String fromAsset, String toAsset, double fromAmount, double toAmount,
                                         Params extraParams) throws Exception {
        return sendQuoteRequest(fromAsset, toAsset, fromAmount, toAmount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request a quote for the requested token pairs
     *
     * @param fromAsset:   user spends coin
     * @param toAsset:     user receives coin
     * @param fromAmount:  the amount you will be debited after the conversion
     * @param toAmount:    the amount you will be debited after the conversion
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "walletType"} -> wallet type, constants available {@link WalletType}
     *                                - [STRING, default SPOT]
     *                           </li>
     *                           <li>
     *                                {@code "validTime"} -> valid time, constants available {@link ValidTime}
     *                                - [STRING, default 10s]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                            - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return request quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
     * Send quote request (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/convert/getQuote")
    public <T> T sendQuoteRequest(String fromAsset, String toAsset, double fromAmount, double toAmount,
                                  Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        if (fromAmount != -1)
            extraParams.addParam("fromAmount", fromAmount);
        if (toAmount != -1)
            extraParams.addParam("toAmount", toAmount);
        String quoteResponse = sendPostSignedRequest(GET_QUOTE_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(quoteResponse);
            case LIBRARY_OBJECT:
                return (T) new QuoteRequest(new JSONObject(quoteResponse));
            default:
                return (T) quoteResponse;
        }
    }

    /**
     * Request to accept the offered quote by quote ID
     *
     * @param quote: the quote to accept
     * @return accept quote as {@link AcceptQuote} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#accept-quote-trade">
     * Accept Quote (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/convert/acceptQuote")
    public AcceptQuote acceptQuote(QuoteRequest quote) throws Exception {
        return acceptQuote(quote.getQuoteId(), LIBRARY_OBJECT);
    }

    /**
     * Request to accept the offered quote by quote ID
     *
     * @param quote:  the quote to accept
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return accept quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#accept-quote-trade">
     * Accept Quote (TRADE)</a>
     **/
    @WrappedRequest
    @RequestPath(method = POST, path = "/sapi/v1/convert/acceptQuote")
    public <T> T acceptQuote(QuoteRequest quote, ReturnFormat format) throws Exception {
        return acceptQuote(quote.getQuoteId(), format);
    }

    /**
     * Request to accept the offered quote by quote ID
     *
     * @param quoteId: the identifier of the quote to accept
     * @return accept quote as {@link AcceptQuote} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#accept-quote-trade">
     * Accept Quote (TRADE)</a>
     **/
    @Wrapper
    @RequestPath(method = POST, path = "/sapi/v1/convert/acceptQuote")
    public AcceptQuote acceptQuote(long quoteId) throws Exception {
        return acceptQuote(quoteId, LIBRARY_OBJECT);
    }

    /**
     * Request to accept the offered quote by quote ID
     *
     * @param quoteId: the identifier of the quote to accept
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return accept quote {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#accept-quote-trade">
     * Accept Quote (TRADE)</a>
     **/
    @Returner
    @RequestPath(method = POST, path = "/sapi/v1/convert/acceptQuote")
    public <T> T acceptQuote(long quoteId, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("timestamp", System.currentTimeMillis());
        payload.addParam("quoteId", quoteId);
        String quoteResponse = sendPostSignedRequest(GET_QUOTE_ENDPOINT, payload);
        switch (format) {
            case JSON:
                return (T) new JSONObject(quoteResponse);
            case LIBRARY_OBJECT:
                return (T) new AcceptQuote(new JSONObject(quoteResponse));
            default:
                return (T) quoteResponse;
        }
    }

    /**
     * Request to query order status by order ID
     *
     * @param order: the order from fetch the status
     * @return order status as {@link Convert} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public Convert getOrderStatusByOrderId(AcceptQuote order) throws Exception {
        return getOrderStatusByOrderId(order.getOrderId(), LIBRARY_OBJECT);
    }

    /**
     * Request to query order status by order ID
     *
     * @param order:  the order from fetch the status
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public <T> T getOrderStatusByOrderId(AcceptQuote order, ReturnFormat format) throws Exception {
        return getOrderStatusByOrderId(order.getOrderId(), format);
    }

    /**
     * Request to query order status by order ID
     *
     * @param orderId: the identifier of the order from fetch the status
     * @return order status as {@link Convert} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public Convert getOrderStatusByOrderId(long orderId) throws Exception {
        return getOrderStatusByOrderId(orderId, LIBRARY_OBJECT);
    }

    /**
     * Request to query order status by order ID
     *
     * @param orderId: the identifier of the order from fetch the status
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return order status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public <T> T getOrderStatusByOrderId(long orderId, ReturnFormat format) throws Exception {
        return getOrderStatus(orderId, -1, format);
    }

    /**
     * Request to query order status by order ID
     *
     * @param quote: the quote from fetch the status
     * @return order status as {@link Convert} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public Convert getOrderStatusByQuoteId(QuoteRequest quote) throws Exception {
        return getOrderStatusByQuoteId(quote.getQuoteId(), LIBRARY_OBJECT);
    }

    /**
     * Request to query order status by order ID
     *
     * @param quote:  the quote from fetch the status
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public <T> T getOrderStatusByQuoteId(QuoteRequest quote, ReturnFormat format) throws Exception {
        return getOrderStatusByQuoteId(quote.getQuoteId(), format);
    }

    /**
     * Request to query order status by order ID
     *
     * @param quoteId: the identifier of the quote from fetch the status
     * @return order status as {@link Convert} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public Convert getOrderStatusByQuoteId(long quoteId) throws Exception {
        return getOrderStatusByQuoteId(quoteId, LIBRARY_OBJECT);
    }

    /**
     * Request to query order status by order ID
     *
     * @param quoteId: the identifier of the quote from fetch the status
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return order status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public <T> T getOrderStatusByQuoteId(long quoteId, ReturnFormat format) throws Exception {
        return getOrderStatus(-1, quoteId, format);
    }

    /**
     * Request to query order status by order ID
     *
     * @param order: the order from fetch the status
     * @param quote: the quote from fetch the status
     * @return order status as {@link Convert} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public Convert getOrderStatus(AcceptQuote order, QuoteRequest quote) throws Exception {
        return getOrderStatus(order.getOrderId(), quote.getQuoteId(), LIBRARY_OBJECT);
    }

    /**
     * Request to query order status by order ID
     *
     * @param order:  the order from fetch the status
     * @param quote:  the quote from fetch the status
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return order status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @WrappedRequest
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public <T> T getOrderStatus(AcceptQuote order, QuoteRequest quote, ReturnFormat format) throws Exception {
        return getOrderStatus(order.getOrderId(), quote.getQuoteId(), format);
    }

    /**
     * Request to query order status by order ID
     *
     * @param orderId: the identifier of the order from fetch the status
     * @param quoteId: the identifier of the quote from fetch the status
     * @return order status as {@link Convert} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public Convert getOrderStatus(long orderId, long quoteId) throws Exception {
        return getOrderStatus(orderId, quoteId, LIBRARY_OBJECT);
    }

    /**
     * Request to query order status by order ID
     *
     * @param orderId: the identifier of the order from fetch the status
     * @param quoteId: the identifier of the quote from fetch the status
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return order status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
     * Order status (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/convert/orderStatus")
    public <T> T getOrderStatus(long orderId, long quoteId, ReturnFormat format) throws Exception {
        Params query = new Params();
        if (orderId != -1)
            query.addParam("orderId", orderId);
        if (quoteId != -1)
            query.addParam("quoteId", quoteId);
        query.addParam("timestamp", System.currentTimeMillis());
        String orderStatusResponse = sendGetSignedRequest(CONVERT_ORDER_STATUS_ENDPOINT, query.createQueryString());
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderStatusResponse);
            case LIBRARY_OBJECT:
                return (T) new Convert(new JSONObject(orderStatusResponse));
            default:
                return (T) orderStatusResponse;
        }
    }

    /**
     * Request to get convert Trade History
     *
     * @param startTime: start time value
     * @param endTime:   end time value
     * @return convert trade history as {@link ConvertTradeHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data">
     * Get Convert Trade History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/tradeFlow")
    public ConvertTradeHistory getConvertTradeHistory(long startTime, long endTime) throws Exception {
        return getConvertTradeHistory(startTime, endTime, LIBRARY_OBJECT);
    }

    /**
     * Request to get convert Trade History
     *
     * @param startTime: start time value
     * @param endTime:   end time value
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return convert trade history status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data">
     * Get Convert Trade History (USER_DATA)</a>
     **/
    @RequestPath(method = GET, path = "/sapi/v1/convert/tradeFlow")
    public <T> T getConvertTradeHistory(long startTime, long endTime, ReturnFormat format) throws Exception {
        return getConvertTradeHistory(startTime, endTime, null, format);
    }

    /**
     * Request to get convert Trade History
     *
     * @param startTime:   start time value
     * @param endTime:     end time value
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INTEGER, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return convert trade history as {@link ConvertTradeHistory} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data">
     * Get Convert Trade History (USER_DATA)</a>
     **/
    @Wrapper
    @RequestPath(method = GET, path = "/sapi/v1/convert/tradeFlow")
    public ConvertTradeHistory getConvertTradeHistory(long startTime, long endTime, Params extraParams) throws Exception {
        return getConvertTradeHistory(startTime, endTime, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get convert Trade History
     *
     * @param startTime:   start time value
     * @param endTime:     end time value
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INTEGER, default 100]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return convert trade history status {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data">
     * Get Convert Trade History (USER_DATA)</a>
     **/
    @Returner
    @RequestPath(method = GET, path = "/sapi/v1/convert/tradeFlow")
    public <T> T getConvertTradeHistory(long startTime, long endTime, Params extraParams,
                                        ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("startTime", startTime);
        extraParams.addParam("endTime", endTime);
        extraParams.addParam("timestamp", getServerTime());
        String tradeHistoryResponse = sendGetSignedRequest(TRADE_FLOW_ENDPOINT, extraParams.createQueryString());
        switch (format) {
            case JSON:
                return (T) new JSONObject(tradeHistoryResponse);
            case LIBRARY_OBJECT:
                return (T) new ConvertTradeHistory(new JSONObject(tradeHistoryResponse));
            default:
                return (T) tradeHistoryResponse;
        }
    }

}

