package com.tecknobit.binancemanager.managers.signedmanagers.convert;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.convert.records.ConvertPair;
import com.tecknobit.binancemanager.managers.signedmanagers.convert.records.OrderQuantityPrecision;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.binancemanager.constants.EndpointsList.ASSET_INFO_ENDPOINT;
import static com.tecknobit.binancemanager.constants.EndpointsList.CONVERT_EXCHANGE_INFO_ENDPOINT;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceConvertManager} class is useful to manage convert endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#list-all-convert-pairs">
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
                                 String secretKey) throws SystemException, IOException {
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
                                 String secretKey) throws SystemException, IOException {
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
                                 String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceConvertManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search the is working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceConvertManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
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

    public ArrayList<ConvertPair> getAllFromConvertPairs(String fromAsset) throws IOException {
        return getAllFromConvertPairs(fromAsset, LIBRARY_OBJECT);
    }

    public <T> T getAllFromConvertPairs(String fromAsset, ReturnFormat format) throws IOException {
        return getAllConvertPairs(fromAsset, null, format);
    }

    public ArrayList<ConvertPair> getAllToConvertPairs(String toAsset) throws IOException {
        return getAllToConvertPairs(toAsset, LIBRARY_OBJECT);
    }

    public <T> T getAllToConvertPairs(String toAsset, ReturnFormat format) throws IOException {
        return getAllConvertPairs(null, toAsset, format);
    }

    public ArrayList<ConvertPair> getAllConvertPairs(String fromAsset, String toAsset) throws IOException {
        return getAllConvertPairs(fromAsset, toAsset, LIBRARY_OBJECT);
    }

    public <T> T getAllConvertPairs(String fromAsset, String toAsset, ReturnFormat format) throws IOException {
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

    public ArrayList<OrderQuantityPrecision> getOrderQuantityPrecisionPerAsset() throws Exception {
        return getOrderQuantityPrecisionPerAsset(LIBRARY_OBJECT);
    }

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

}
