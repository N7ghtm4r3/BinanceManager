package com.tecknobit.binancemanager.Managers.Market;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.Market.Records.Stats.Candlestick;
import com.tecknobit.binancemanager.Managers.Market.Records.CurrentAveragePrice;
import com.tecknobit.binancemanager.Managers.Market.Records.Stats.ExchangeInformation;
import com.tecknobit.binancemanager.Managers.Market.Records.OrderBook;
import com.tecknobit.binancemanager.Managers.Market.Records.Tickers.OrderBookTicker;
import com.tecknobit.binancemanager.Managers.Market.Records.Tickers.PriceTicker;
import com.tecknobit.binancemanager.Managers.Market.Records.Tickers.TickerPriceChange;
import com.tecknobit.binancemanager.Managers.Market.Records.Trade.CompressedTrade;
import com.tecknobit.binancemanager.Managers.Market.Records.Trade.Trade;
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
     * @implSpec (keys accepted are limit,fromId,recvWindow)
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
     * @implSpec (keys accepted are limit,fromId,recvWindow)
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
     * @implSpec (keys accepted are limit,fromId,recvWindow)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data
     * return old trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getOldTradeList(String symbol, String apiKey, HashMap<String,Object> extraParams) throws IOException {
        return getTradeList(new JSONArray(getOldTrade(symbol,apiKey,extraParams)));
    }

    /** Request to get compressed trade list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return compressed trade list as String
     * **/
    public String getCompressedTradeList(String symbol) throws IOException {
        return getRequestResponse(COMPRESSED_TRADE_LIST_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get compressed trade list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return compressed trade list as JsonArray
     * **/
    public JSONArray getJSONCompressedTradeList(String symbol) throws IOException {
        return new JSONArray(getCompressedTradeList(symbol));
    }

    /** Request to get compressed trade list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return compressed trade list as ArrayList<CompressedTrade>
     * **/
    public ArrayList<CompressedTrade> getObjectCompressedTradeList(String symbol) throws IOException {
        return getObjectCompressedTradeList(new JSONArray(getCompressedTradeList(symbol)));
    }

    /** Request to get compressed trade list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return compressed trade list as String
     * **/
    public String getCompressedTradeList(String symbol, HashMap<String,Object> extraParams) throws IOException {
        String params = "?symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return getRequestResponse(COMPRESSED_TRADE_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get compressed trade list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return compressed trade list as JsonArray
     * **/
    public JSONArray getJSONCompressedTradeList(String symbol, HashMap<String,Object> extraParams) throws IOException {
        return new JSONArray(getCompressedTradeList(symbol,extraParams));
    }

    /** Request to get compressed trade list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return compressed trade list as ArrayList<CompressedTrade>
     * **/
    public ArrayList<CompressedTrade> getObjectCompressedTradeList(String symbol, HashMap<String,Object> extraParams) throws IOException {
        return getObjectCompressedTradeList(new JSONArray(getCompressedTradeList(symbol,extraParams)));
    }

    /** Method to assemble CompressedTrade list
     * @param #jsonArray: obtain from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list
     * return list of compressedTrade as ArrayList<CompressedTrade>
     * **/
    private ArrayList<CompressedTrade> getObjectCompressedTradeList(JSONArray jsonArray){
        ArrayList<CompressedTrade> compressedTrades = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject compressedTrade = jsonArray.getJSONObject(j);
            compressedTrades.add(new CompressedTrade(compressedTrade.getLong("a"),
                    compressedTrade.getDouble("p"),
                    compressedTrade.getDouble("q"),
                    compressedTrade.getLong("f"),
                    compressedTrade.getLong("l"),
                    compressedTrade.getLong("T"),
                    compressedTrade.getBoolean("m"),
                    compressedTrade.getBoolean("M")
            ));
        }
        return compressedTrades;
    }

    /** Request to get candlestick data
     * @param #symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param #interval: time period to fetch
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return candlestick data as String
     * **/
    public String getCandlestickData(String symbol, String interval) throws IOException {
        return getRequestResponse(CANDLESTICK_DATA_ENDPOINT,"?symbol="+symbol+"&interval="+interval,GET_METHOD);
    }

    /** Request to get candlestick data
     * @param #symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param #interval: time period to fetch
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return candlestick data as JsonArray
     * **/
    public JSONArray getJSONCandlestickData(String symbol, String interval) throws IOException {
        return new JSONArray(getCandlestickData(symbol,interval));
    }

    /** Request to get candlestick data list
     * @param #symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param #interval: time period to fetch
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return candlestick data as ArrayList<Candlestick>
     * **/
    public ArrayList<Candlestick> getCandlestickDataList(String symbol, String interval) throws IOException {
        return getCandlestickDataList(new JSONArray(getCandlestickData(symbol,interval)));
    }

    /** Request to get candlestick data
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param #interval: time period to fetch
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return candlestick data as String
     * **/
    public String getCandlestickData(String symbol, String interval, HashMap<String,Object> extraParams) throws IOException {
        String params = "?symbol="+symbol+"&interval="+interval;
        params = requestManager.assembleExtraParams(params,extraParams);
        return getRequestResponse(CANDLESTICK_DATA_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get candlestick data
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param #interval: time period to fetch
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return candlestick data as JsonArray
     * **/
    public JSONArray getJSONCandlestickData(String symbol, String interval, HashMap<String,Object> extraParams) throws IOException {
        return new JSONArray(getCandlestickData(symbol,interval,extraParams));
    }

    /** Request to get candlestick data list
     * @param #symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param #interval: time period to fetch
     * @param #extraParams: hashmap composed by extraParams
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return candlestick data as ArrayList<Candlestick>
     * **/
    public ArrayList<Candlestick> getCandlestickDataList(String symbol, String interval, HashMap<String,Object> extraParams) throws IOException {
        return getCandlestickDataList(new JSONArray(getCandlestickData(symbol,interval,extraParams)));
    }

    /** Method to assemble Candlestick list
     * @param #jsonArray: obtain from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data
     * return list of candlestick as ArrayList<Candlestick>
     * **/
    private ArrayList<Candlestick> getCandlestickDataList(JSONArray jsonArray){
        ArrayList<Candlestick> candlesticksList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONArray candlestick = jsonArray.getJSONArray(j);
            candlesticksList.add(new Candlestick(candlestick.getLong(0),
                    candlestick.getDouble(1),
                    candlestick.getDouble(2),
                    candlestick.getDouble(3),
                    candlestick.getDouble(4),
                    candlestick.getDouble(5),
                    candlestick.getLong(6),
                    candlestick.getDouble(7),
                    candlestick.getInt(8),
                    candlestick.getDouble(9),
                    candlestick.getDouble(10),
                    candlestick.getDouble(11)
            ));
        }
        return candlesticksList;
    }

    /** Request to get current average price
     * @param #symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-average-price
     * return current average price as String
     * **/
    public String getCurrentAveragePrice(String symbol) throws IOException {
        return getRequestResponse(CURRENT_AVERAGE_PRICE_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get current average price
     * @param #symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-average-price
     * return current average price as JsonObject
     * **/
    public JSONObject getJSONCurrentAveragePrice(String symbol) throws IOException {
        return new JSONObject(getCurrentAveragePrice(symbol));
    }

    /** Request to get current average price
     * @param #symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-average-price
     * return current average price value as double
     * **/
    public double getCurrentAveragePriceValue(String symbol) throws IOException {
        jsonObject = new JSONObject(getCurrentAveragePrice(symbol));
        return jsonObject.getDouble("price");
    }

    /** Request to get current average price
     * @param #symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-average-price
     * return current average price CurrentAveragePrice object
     * **/
    public CurrentAveragePrice getObjectCurrentAveragePrice(String symbol) throws IOException {
        jsonObject = new JSONObject(getCurrentAveragePrice(symbol));
        return new CurrentAveragePrice(jsonObject.getInt("mins"),
                jsonObject.getDouble("price")
        );
    }

    /** Request to get ticker price change
     * @param #symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change as String
     * **/
    public String getTickerPriceChange(String symbol) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get ticker price change
     * @param #symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change as JsonObject
     * **/
    public JSONObject getJSONTickerPriceChange(String symbol) throws IOException {
        return new JSONObject(getTickerPriceChange(symbol));
    }

    /** Request to get ticker price change
     * @param #symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change as TickerPriceChange object
     * **/
    public TickerPriceChange getObjectTickerPriceChange(String symbol) throws IOException {
        return assembleTickerPriceChange(new JSONObject(getTickerPriceChange(symbol)));
    }

    /** Request to get ticker price change list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change list as String
     * **/
    public String getTickerPriceChange() throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get ticker price change list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change list as JsonArray
     * **/
    public JSONArray getJSONTickerPriceChange() throws IOException {
        return new JSONArray(getTickerPriceChange());
    }

    /** Request to get ticker price change list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change list as ArrayList<TickerPriceChange>
     * **/
    public ArrayList<TickerPriceChange> getTickerPriceChangeList() throws IOException {
        ArrayList<TickerPriceChange> tickerPriceStatistics = new ArrayList<>();
        jsonArray = new JSONArray(getTickerPriceChange());
        for(int j=0; j < jsonArray.length(); j++)
            tickerPriceStatistics.add(assembleTickerPriceChange(jsonArray.getJSONObject(j)));
        return tickerPriceStatistics;
    }

    /** Method to assemble TickerPriceChange list
     * @param #jsonObject: obtain from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics
     * return ticker price change list as ArrayList<TickerPriceChange>
     * **/
    private TickerPriceChange assembleTickerPriceChange(JSONObject jsonObject){
        return new TickerPriceChange(jsonObject.getString("symbol"),
                jsonObject.getDouble("priceChange"),
                jsonObject.getDouble("priceChangePercent"),
                jsonObject.getDouble("weightedAvgPrice"),
                jsonObject.getDouble("prevClosePrice"),
                jsonObject.getDouble("lastPrice"),
                jsonObject.getDouble("lastQty"),
                jsonObject.getDouble("bidPrice"),
                jsonObject.getDouble("bidQty"),
                jsonObject.getDouble("askPrice"),
                jsonObject.getDouble("askQty"),
                jsonObject.getDouble("openPrice"),
                jsonObject.getDouble("highPrice"),
                jsonObject.getDouble("lowPrice"),
                jsonObject.getDouble("volume"),
                jsonObject.getDouble("quoteVolume"),
                jsonObject.getLong("openTime"),
                jsonObject.getLong("closeTime"),
                jsonObject.getLong("firstId"),
                jsonObject.getLong("lastId"),
                jsonObject.getInt("count")
        );
    }

    /** Request to get price ticker
     * @param #symbol: symbol to fetch price ticker es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return price ticker as String
     * **/
    public String getPriceTicker(String symbol) throws IOException {
        return getRequestResponse(PRICE_TICKER_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get price ticker
     * @param #symbol: symbol to fetch price ticker es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return price ticker as JsonObject
     * **/
    public JSONObject getJSONPriceTicker(String symbol) throws IOException {
        return new JSONObject(getPriceTicker(symbol));
    }

    /** Request to get price ticker
     * @param #symbol: symbol to fetch price ticker es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return price ticker as PriceTicker object
     * **/
    public PriceTicker getObjectPriceTicker(String symbol) throws IOException {
        return assemblePriceTicker(new JSONObject(getPriceTicker(symbol)));
    }

    /** Request to get price ticker list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return price ticker list as String
     * **/
    public String getPriceTicker() throws IOException {
        return getRequestResponse(PRICE_TICKER_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get price ticker list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return price ticker list as JsonArray
     * **/
    public JSONArray getJSONPriceTicker() throws IOException {
        return new JSONArray(getPriceTicker());
    }

    /** Request to get price ticker list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return price ticker list as ArrayList<PriceTicker>
     * **/
    public ArrayList<PriceTicker> getTickerPriceList() throws IOException {
        ArrayList<PriceTicker> tickerPrices = new ArrayList<>();
        jsonArray = new JSONArray(getPriceTicker());
        for (int j=0; j < jsonArray.length(); j++)
            tickerPrices.add(assemblePriceTicker(jsonArray.getJSONObject(j)));
        return tickerPrices;
    }

    /** Method to assemble PriceTicker list
     * @param #jsonObject: obtain from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker
     * return PriceTicker object
     * **/
    private PriceTicker assemblePriceTicker(JSONObject jsonObject){
        return new PriceTicker(jsonObject.getString("symbol"),
                jsonObject.getDouble("price")
        );
    }

    /** Request to get order book ticker
     * @param #symbol: symbol to fetch book ticker es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return order book ticker as String
     * **/
    public String getOrderBookTicker(String symbol) throws IOException {
        return getRequestResponse(BOOK_TICKER_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get order book ticker
     * @param #symbol: symbol to fetch book ticker es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return order book ticker as JsonObject
     * **/
    public JSONObject getJSONOrderBookTicker(String symbol) throws IOException {
        return new JSONObject(getOrderBookTicker(symbol));
    }

    /** Request to get order book ticker
     * @param #symbol: symbol to fetch book ticker es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return order book ticker as BookTicker object
     * **/
    public OrderBookTicker getObjectOrderBookTicker(String symbol) throws IOException {
        return assembleOrderBookTicker(new JSONObject(getOrderBookTicker(symbol)));
    }

    /** Request to get order book ticker list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return order book ticker list as String
     * **/
    public String getOrderBookTicker() throws IOException {
        return getRequestResponse(BOOK_TICKER_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get order book ticker list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return order book ticker list as JsonArray
     * **/
    public JSONArray getJSONOrderBookTicker() throws IOException {
        return new JSONArray(getOrderBookTicker());
    }

    /** Request to get book ticker list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return order book ticker list as ArrayList<OrderBookTicker>
     * **/
    public ArrayList<OrderBookTicker> getOrderBookTickerList(String symbol) throws IOException {
        ArrayList<OrderBookTicker> bookTickers = new ArrayList<>();
        jsonArray = new JSONArray(getOrderBookTicker());
        for (int j=0; j < jsonArray.length(); j++)
            bookTickers.add(assembleOrderBookTicker(jsonArray.getJSONObject(j)));
        return bookTickers;
    }

    /** Method to assemble OrderBookTicker list
     * @param #jsonObject: obtain from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
     * return OrderBookTicker object
     * **/
    private OrderBookTicker assembleOrderBookTicker(JSONObject jsonObject){
        return new OrderBookTicker(jsonObject.getString("symbol"),
                jsonObject.getDouble("bidPrice"),
                jsonObject.getDouble("bidQty"),
                jsonObject.getDouble("askPrice"),
                jsonObject.getDouble("askQty")
        );
    }

}

