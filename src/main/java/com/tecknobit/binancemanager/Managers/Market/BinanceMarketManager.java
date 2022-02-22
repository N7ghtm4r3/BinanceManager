package com.tecknobit.binancemanager.Managers.Market;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.Market.Records.ExchangeInformation;
import com.tecknobit.binancemanager.Managers.Market.Records.OrderBook;
import com.tecknobit.binancemanager.Managers.Market.Records.Trade;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;

/**
 * The {@code BinanceMarketManager} class is useful to manage all Binance Market Endpoints
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#market-data-endpoints
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceMarketManager extends BinanceManager {

    /** Constructor with an endpoint give by parameter
     * @param #baseEndpoint base endpoint choose from BASE_ENDPOINTS array
     * **/
    public BinanceMarketManager(String baseEndpoint) throws SystemException, IOException {
        super(baseEndpoint);
    }

    /**
     * Constructor with an endpoint give by list auto research
     * **/
    public BinanceMarketManager() throws SystemException, IOException {
        super(null);
    }

    /** Request to get if service is avaible
     * any param required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#test-connectivity
     * return response as boolean
     * **/
    public boolean isMarketServiceWork() throws IOException {
        return getRequestResponse(TEST_CONNECTIVITY_ENDPOINT,"",GET_METHOD).equals("{}");
    }

    /** Request to get exchange information
     * any param required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as String
     * **/
    public String getExchangeInformation() throws IOException {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get exchange information
     * any param required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as JSONObject
     * **/
    public JSONObject getJSONExchangeInformation() throws IOException {
        return new JSONObject(getExchangeInformation());
    }

    /** Request to get exchange information
     * any param required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation() throws IOException {
        jsonObject = new JSONObject(getExchangeInformation());
        return new ExchangeInformation(jsonObject.getString("timezone"),
                jsonObject.getLong("serverTime"),
                jsonObject);
    }

    /** Request to get exchange information
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as String
     * **/
    public String getExchangeInformation(String symbol) throws Exception {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get exchange information
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as JSONObject
     * **/
    public JSONObject getJSONExchangeInformation(String symbol) throws Exception {
        return new JSONObject(getExchangeInformation(symbol));
    }

    /** Request to get exchange information
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation(String symbol) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbol)));
    }

    /** Request to get exchange information
     * @param #symbols: ArrayList of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as String
     * **/
    public String getExchangeInformation(ArrayList<String> symbols) throws Exception {
        StringBuilder params = new StringBuilder();
        for (String symbol : symbols)
            params.append("%22").append(symbol).append("%22,");
        params.replace(params.length()-1,params.length(),"");
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT,"?symbols=["+ params +"]",GET_METHOD);
    }

    /** Request to get exchange information
     * @param #symbols: ArrayList of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as JSONObject
     * **/
    public JSONObject getJSONExchangeInformation(ArrayList<String> symbols) throws Exception {
        return new JSONObject(getExchangeInformation(symbols));
    }

    /** Request to get exchange information
     * @param #symbols: ArrayList of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation(ArrayList<String> symbols) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbols)));
    }

    /** Request to get exchange information
     * @param #symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as String
     * **/
    public String getExchangeInformation(String[] symbols) throws Exception {
       return getExchangeInformation(new ArrayList<>(Arrays.asList(symbols)));
    }

    /** Request to get exchange information
     * @param #symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as JSONObject
     * **/
    public JSONObject getJSONExchangeInformation(String[] symbols) throws Exception {
        return new JSONObject(getExchangeInformation(symbols));
    }

    /** Request to get exchange information
     * @param #symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation(String[] symbols) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbols)));
    }

    /** Method to get ExchangeInformation object
     * @param #jsonObject: obtained from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * return exchange information as ExchangeInformation object
     * **/
    private ExchangeInformation getObjectExchangeInformation(JSONObject jsonObject){
        return new ExchangeInformation(jsonObject.getString("timezone"),
                jsonObject.getLong("serverTime"),
                jsonObject);
    }

    /** Request to get order book
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order book as String
     * **/
    public String getOrderBook(String symbol) throws IOException {
        return getRequestResponse(ORDER_BOOK_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get order book
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order book as JSONObject
     * **/
    public JSONObject getJSONOrderBook(String symbol) throws IOException {
        return new JSONObject(getOrderBook(symbol));
    }

    /** Request to get order book
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order book as OrderBook object
     * **/
    public OrderBook getObjectOrderBook(String symbol) throws IOException {
        jsonObject = new JSONObject(getOrderBook(symbol));
        return new OrderBook(jsonObject.getLong("lastUpdateId"),jsonObject,
                symbol);
    }

    /** Request to get order book
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order book as String
     * **/
    public String getOrderBook(String symbol, int limit) throws IOException {
        return getRequestResponse(ORDER_BOOK_ENDPOINT,"?symbol="+symbol+"&limit="+limit,GET_METHOD);
    }

    /** Request to get order book
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order book as JSONObject
     * **/
    public JSONObject getJSONOrderBook(String symbol, int limit) throws IOException {
        return new JSONObject(getOrderBook(symbol,limit));
    }

    /** Request to get order book
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order book as OrderBook object
     * **/
    public OrderBook getObjectOrderBook(String symbol, int limit) throws IOException {
        jsonObject = new JSONObject(getOrderBook(symbol, limit));
        return new OrderBook(jsonObject.getLong("lastUpdateId"),jsonObject,
                symbol);
    }

    /** Request to get recent trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trade as String
     * **/
    public String getRecentTrade(String symbol) throws IOException {
        return getRequestResponse(RECENT_TRADE_LIST_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get recent trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trade as JsonArray
     * **/
    public JSONArray getJSONRecentTrade(String symbol) throws IOException {
        return new JSONArray(getRecentTrade(symbol));
    }

    /** Request to get recent trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getRecentTradeList(String symbol) throws IOException {
        return getTradeList(new JSONArray(getRecentTrade(symbol)));
    }

    /** Request to get recent trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trade as String
     * **/
    public String getRecentTrade(String symbol, int limit) throws IOException {
        return getRequestResponse(RECENT_TRADE_LIST_ENDPOINT,"?symbol="+symbol+"&limit="+limit,GET_METHOD);
    }

    /** Request to get recent trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trade as JsonArray
     * **/
    public JSONArray getJSONRecentTrade(String symbol, int limit) throws IOException {
        return new JSONArray(getRecentTrade(symbol,limit));
    }

    /** Request to get recent trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getRecentTradeList(String symbol, int limit) throws IOException {
        return getTradeList(new JSONArray(getRecentTrade(symbol,limit)));
    }

    /** Method to get RecentTrade list object
     * @param #jsonArray: obtained from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return recent trades list as ArrayList<Trade> object
     * **/
    private ArrayList<Trade> getTradeList(JSONArray jsonArray) {
        ArrayList<Trade> trades = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject recentTrade = jsonArray.getJSONObject(j);
            trades.add(new Trade(recentTrade.getLong("id"),
                    recentTrade.getDouble("price"),
                    recentTrade.getDouble("qty"),
                    recentTrade.getDouble("quoteQty"),
                    recentTrade.getLong("time"),
                    recentTrade.getBoolean("isBuyerMaker"),
                    recentTrade.getBoolean("isBestMatch")
            ));
        }
        return trades;
    }

    /** Request to get old trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #apiKey: apiKey of your Binance account
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data
     * return old trade as String
     * **/
    public String getOldTrade(String symbol, String apiKey) throws IOException {
        return getRequestResponse(OLD_TRADE_LOOKUP_ENDPOINT,"?symbol="+symbol,GET_METHOD,apiKey);
    }

    /** Request to get old trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #apiKey: apiKey of your Binance account
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data
     * return old trade as JsonArray
     * **/
    public JSONArray getJSONOldTrade(String symbol, String apiKey) throws IOException {
        return new JSONArray(getOldTrade(symbol,apiKey));
    }

    /** Request to get old trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #apiKey: apiKey of your Binance account
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list
     * return old trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getOldTradeList(String symbol, String apiKey) throws IOException {
        return getTradeList(new JSONArray(getOldTrade(symbol,apiKey)));
    }

    /** Request to get old trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #apiKey: apiKey of your Binance account
     * @param #extraParams: extraParams of request
     * @implSpec (keys accepted are limit,fromId)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data
     * return old trade as String
     * **/
    public String getOldTrade(String symbol, String apiKey, HashMap<String,Object> extraParams) throws IOException {
        String params = "?symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return getRequestResponse(OLD_TRADE_LOOKUP_ENDPOINT,params,GET_METHOD,apiKey);
    }

    /** Request to get old trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #apiKey: apiKey of your Binance account
     * @param #extraParams: extraParams of request
     * @implSpec (keys accepted are limit,fromId)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data
     * return old trade as JsonArray
     * **/
    public JSONArray getJSONOldTrade(String symbol, String apiKey, HashMap<String,Object> extraParams) throws IOException {
        return new JSONArray(getOldTrade(symbol,apiKey,extraParams));
    }

    /** Request to get old trade
     * @param #symbol: symbol to fetch exchange information es. BTCBUSD
     * @param #apiKey: apiKey of your Binance account
     * @param #extraParams: extraParams of request
     * @implSpec (keys accepted are limit,fromId)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data
     * return old trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getOldTradeList(String symbol, String apiKey, HashMap<String,Object> extraParams) throws IOException {
        return getTradeList(new JSONArray(getOldTrade(symbol,apiKey,extraParams)));
    }

}
