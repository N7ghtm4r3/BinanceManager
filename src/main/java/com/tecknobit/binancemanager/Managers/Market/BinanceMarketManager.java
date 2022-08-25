package com.tecknobit.binancemanager.Managers.Market;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.Market.Records.CurrentAveragePrice;
import com.tecknobit.binancemanager.Managers.Market.Records.OrderBook;
import com.tecknobit.binancemanager.Managers.Market.Records.Stats.Candlestick;
import com.tecknobit.binancemanager.Managers.Market.Records.Stats.ExchangeInformation;
import com.tecknobit.binancemanager.Managers.Market.Records.Tickers.*;
import com.tecknobit.binancemanager.Managers.Market.Records.Trade.CompressedTrade;
import com.tecknobit.binancemanager.Managers.Market.Records.Trade.Trade;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.tecknobit.apimanager.Manager.APIRequest.GET_METHOD;
import static com.tecknobit.apimanager.Tools.Trading.TradingTools.computeTPTOPAsset;
import static com.tecknobit.binancemanager.Constants.EndpointsList.*;

/**
 * The {@code BinanceMarketManager} class is useful to manage all Binance Market Endpoints
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#market-data-endpoints">
 *     https://binance-docs.github.io/apidocs/spot/en/#market-data-endpoints</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceMarketManager extends BinanceManager {

    /** Constructor with an endpoint give by parameter
     * @param baseEndpoint base endpoint choose from BASE_ENDPOINTS array
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-connectivity">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-connectivity</a>
     * @return response as boolean
     * **/
    public boolean isMarketServiceWork() throws IOException {
        return getRequestResponse(TEST_CONNECTIVITY_ENDPOINT, "", GET_METHOD).equals("{}");
    }

    /** Request to get exchange information
     * any param required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link String}
     * **/
    public String getExchangeInformation() throws IOException {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT, "", GET_METHOD);
    }

    /** Request to get exchange information
     * any param required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link JSONObject}
     * **/
    public JSONObject getJSONExchangeInformation() throws IOException {
        return new JSONObject(getExchangeInformation());
    }

    /** Request to get exchange information
     * any param required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation() throws IOException {
        JSONObject information = new JSONObject(getExchangeInformation());
        return new ExchangeInformation(information.getString("timezone"),
                information.getLong("serverTime"),
                information);
    }

    /** Request to get exchange information
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link String}
     * **/
    public String getExchangeInformation(String symbol) throws Exception {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get exchange information
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link JSONObject}
     * **/
    public JSONObject getJSONExchangeInformation(String symbol) throws Exception {
        return new JSONObject(getExchangeInformation(symbol));
    }

    /** Request to get exchange information
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation(String symbol) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbol)));
    }

    /** Request to get exchange information
     * @param symbols: ArrayList of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link String}
     * **/
    public String getExchangeInformation(ArrayList<String> symbols) throws Exception {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols)
                + "]", GET_METHOD);
    }

    /** Request to get exchange information
     * @param symbols: ArrayList of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link JSONObject}
     * **/
    public JSONObject getJSONExchangeInformation(ArrayList<String> symbols) throws Exception {
        return new JSONObject(getExchangeInformation(symbols));
    }

    /** Request to get exchange information
     * @param symbols: ArrayList of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation(ArrayList<String> symbols) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbols)));
    }

    /** Request to get exchange information
     * @param symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link String}
     * **/
    public String getExchangeInformation(String[] symbols) throws Exception {
       return getExchangeInformation(new ArrayList<>(Arrays.asList(symbols)));
    }

    /** Request to get exchange information
     * @param symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as {@link JSONObject}
     * **/
    public JSONObject getJSONExchangeInformation(String[] symbols) throws Exception {
        return new JSONObject(getExchangeInformation(symbols));
    }

    /** Request to get exchange information
     * @param symbols: String[] of symbols to fetch exchange information es. BTCBUSD,ETHBUSD (auto assembled)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as ExchangeInformation object
     * **/
    public ExchangeInformation getObjectExchangeInformation(String[] symbols) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbols)));
    }

    /** Method to get ExchangeInformation object
     * @param jsonInformation: obtained from Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return exchange information as ExchangeInformation object
     * **/
    private ExchangeInformation getObjectExchangeInformation(JSONObject jsonInformation){
        return new ExchangeInformation(jsonInformation.getString("timezone"),
                jsonInformation.getLong("serverTime"),
                jsonInformation);
    }

    /** Request to get order book
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     *     https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order book as {@link String}
     * **/
    public String getOrderBook(String symbol) throws IOException {
        return getRequestResponse(ORDER_BOOK_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get order book
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     *     https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order book as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderBook(String symbol) throws IOException {
        return new JSONObject(getOrderBook(symbol));
    }

    /** Request to get order book
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     *     https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order book as OrderBook object
     * **/
    public OrderBook getObjectOrderBook(String symbol) throws IOException {
        JSONObject book = new JSONObject(getOrderBook(symbol));
        return new OrderBook(book.getLong("lastUpdateId"), book, symbol);
    }

    /** Request to get order book
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     *     https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order book as {@link String}
     * **/
    public String getOrderBook(String symbol, int limit) throws IOException {
        return getRequestResponse(ORDER_BOOK_ENDPOINT, "?symbol=" + symbol + "&limit=" + limit, GET_METHOD);
    }

    /** Request to get order book
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     *     https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order book as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderBook(String symbol, int limit) throws IOException {
        return new JSONObject(getOrderBook(symbol, limit));
    }

    /** Request to get order book
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit: limit of resutl to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     *     https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order book as OrderBook object
     * **/
    public OrderBook getObjectOrderBook(String symbol, int limit) throws IOException {
        JSONObject book = new JSONObject(getOrderBook(symbol, limit));
        return new OrderBook(book.getLong("lastUpdateId"), book, symbol);
    }

    /** Request to get recent trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trade as {@link String}
     * **/
    public String getRecentTrade(String symbol) throws IOException {
        return getRequestResponse(RECENT_TRADE_LIST_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get recent trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trade as {@link JSONArray}
     * **/
    public JSONArray getJSONRecentTrade(String symbol) throws IOException {
        return new JSONArray(getRecentTrade(symbol));
    }

    /** Request to get recent trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getRecentTradeList(String symbol) throws IOException {
        return getTradeList(new JSONArray(getRecentTrade(symbol)));
    }

    /** Request to get recent trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit: limit of result to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trade as {@link String}
     * **/
    public String getRecentTrade(String symbol, int limit) throws IOException {
        return getRequestResponse(RECENT_TRADE_LIST_ENDPOINT,"?symbol=" + symbol + "&limit=" + limit, GET_METHOD);
    }

    /** Request to get recent trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit: limit of result to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trade as {@link JSONArray}
     * **/
    public JSONArray getJSONRecentTrade(String symbol, int limit) throws IOException {
        return new JSONArray(getRecentTrade(symbol, limit));
    }

    /** Request to get recent trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param limit: limit of result to fetch
     * @implSpec Limit of default is 100 and max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000]
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getRecentTradeList(String symbol, int limit) throws IOException {
        return getTradeList(new JSONArray(getRecentTrade(symbol,limit)));
    }

    /** Method to get RecentTrade list object
     * @param jsonTrades: obtained from Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return recent trades list as ArrayList<Trade> object
     * **/
    private ArrayList<Trade> getTradeList(JSONArray jsonTrades) {
        ArrayList<Trade> trades = new ArrayList<>();
        for (int j = 0; j < jsonTrades.length(); j++){
            JSONObject recentTrade = jsonTrades.getJSONObject(j);
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
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param apiKey: apiKey of your Binance account
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data</a>
     * @return old trade as {@link String}
     * **/
    public String getOldTrade(String symbol, String apiKey) throws IOException {
        return getRequestResponse(OLD_TRADE_LOOKUP_ENDPOINT, "?symbol=" + symbol, GET_METHOD, apiKey);
    }

    /** Request to get old trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param apiKey: apiKey of your Binance account
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data</a>
     * @return old trade as {@link JSONArray}
     * **/
    public JSONArray getJSONOldTrade(String symbol, String apiKey) throws IOException {
        return new JSONArray(getOldTrade(symbol, apiKey));
    }

    /** Request to get old trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param apiKey: apiKey of your Binance account
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#recent-trades-list</a>
     * @return old trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getOldTradeList(String symbol, String apiKey) throws IOException {
        return getTradeList(new JSONArray(getOldTrade(symbol, apiKey)));
    }

    /** Request to get old trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param apiKey: apiKey of your Binance account
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are limit,fromId,recvWindow)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data</a>
     * @return old trade as {@link String}
     * **/
    public String getOldTrade(String symbol, String apiKey, Params extraParams) throws IOException {
        String params = "?symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return getRequestResponse(OLD_TRADE_LOOKUP_ENDPOINT, params, GET_METHOD, apiKey);
    }

    /** Request to get old trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param apiKey: apiKey of your Binance account
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are limit,fromId,recvWindow)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data</a>
     * @return old trade as {@link JSONArray}
     * **/
    public JSONArray getJSONOldTrade(String symbol, String apiKey, Params extraParams) throws IOException {
        return new JSONArray(getOldTrade(symbol, apiKey, extraParams));
    }

    /** Request to get old trade
     * @param symbol: symbol to fetch exchange information es. BTCBUSD
     * @param apiKey: apiKey of your Binance account
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are limit,fromId,recvWindow)
     * @implNote limit: valid limits are default 500 and max 1000
     * @implNote fromId: to insert it correctly ad L at the end of long number es 1499865549590 + L = 1499865549590L
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#old-trade-lookup-market_data</a>
     * @return old trade as ArrayList<Trade>
     * **/
    public ArrayList<Trade> getOldTradeList(String symbol, String apiKey, Params extraParams) throws IOException {
        return getTradeList(new JSONArray(getOldTrade(symbol, apiKey, extraParams)));
    }

    /** Request to get compressed trade list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return compressed trade list as {@link String}
     * **/
    public String getCompressedTradeList(String symbol) throws IOException {
        return getRequestResponse(COMPRESSED_TRADE_LIST_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get compressed trade list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return compressed trade list as {@link JSONArray}
     * **/
    public JSONArray getJSONCompressedTradeList(String symbol) throws IOException {
        return new JSONArray(getCompressedTradeList(symbol));
    }

    /** Request to get compressed trade list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     * https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return compressed trade list as ArrayList<CompressedTrade>
     * **/
    public ArrayList<CompressedTrade> getObjectCompressedTradeList(String symbol) throws IOException {
        return getObjectCompressedTradeList(new JSONArray(getCompressedTradeList(symbol)));
    }

    /** Request to get compressed trade list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return compressed trade list as {@link String}
     * **/
    public String getCompressedTradeList(String symbol, Params extraParams) throws IOException {
        String params = "?symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return getRequestResponse(COMPRESSED_TRADE_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get compressed trade list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return compressed trade list as {@link JSONArray}
     * **/
    public JSONArray getJSONCompressedTradeList(String symbol, Params extraParams) throws IOException {
        return new JSONArray(getCompressedTradeList(symbol, extraParams));
    }

    /** Request to get compressed trade list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return compressed trade list as ArrayList<CompressedTrade>
     * **/
    public ArrayList<CompressedTrade> getObjectCompressedTradeList(String symbol, Params extraParams) throws IOException {
        return getObjectCompressedTradeList(new JSONArray(getCompressedTradeList(symbol, extraParams)));
    }

    /** Method to assemble CompressedTrade list
     * @param jsonArray: obtain from Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
     * @return list of compressedTrade as ArrayList<CompressedTrade>
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
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return candlestick data as {@link String}
     * **/
    public String getCandlestickData(String symbol, String interval) throws IOException {
        return getRequestResponse(CANDLESTICK_DATA_ENDPOINT, "?symbol=" + symbol + "&interval=" + interval, GET_METHOD);
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return candlestick data as {@link JSONArray}
     * **/
    public JSONArray getJSONCandlestickData(String symbol, String interval) throws IOException {
        return new JSONArray(getCandlestickData(symbol, interval));
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return candlestick data as ArrayList<Candlestick>
     * **/
    public ArrayList<Candlestick> getCandlestickDataList(String symbol, String interval) throws IOException {
        return getCandlestickDataList(new JSONArray(getCandlestickData(symbol, interval)));
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return candlestick data as {@link String}
     * **/
    public String getCandlestickData(String symbol, String interval, Params extraParams) throws IOException {
        String params = "?symbol=" + symbol + "&interval=" + interval;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return getRequestResponse(CANDLESTICK_DATA_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return candlestick data as {@link JSONArray}
     * **/
    public JSONArray getJSONCandlestickData(String symbol, String interval, Params extraParams) throws IOException {
        return new JSONArray(getCandlestickData(symbol, interval, extraParams));
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return candlestick data as ArrayList<Candlestick>
     * **/
    public ArrayList<Candlestick> getCandlestickDataList(String symbol, String interval, Params extraParams) throws IOException {
        return getCandlestickDataList(new JSONArray(getCandlestickData(symbol, interval, extraParams)));
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *     https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
     * @return candlestick data as {@link String}
     * **/
    public String getUIKLines(String symbol, String interval) throws IOException {
        return getRequestResponse(UIKLINES_ENDPOINT, "?symbol=" + symbol + "&interval=" + interval, GET_METHOD);
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *     https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
     * @return candlestick data as {@link JSONArray}
     * **/
    public JSONArray getJSONUIKLines(String symbol, String interval) throws IOException {
        return new JSONArray(getUIKLines(symbol, interval));
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch candlestick data es. BTCBUSD
     * @param interval: time period to fetch
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *     https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
     * @return candlestick data as ArrayList<Candlestick>
     * **/
    public ArrayList<Candlestick> getUIKLinesList(String symbol, String interval) throws IOException {
        return getCandlestickDataList(new JSONArray(getJSONUIKLines(symbol, interval)));
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *     https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
     * @return candlestick data as {@link String}
     * **/
    public String getUIKLines(String symbol, String interval, Params extraParams) throws IOException {
        String params = "?symbol=" + symbol + "&interval=" + interval;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return getRequestResponse(UIKLINES_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get candlestick data
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *     https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
     * @return candlestick data as {@link JSONArray}
     * **/
    public JSONArray getJSONUIKLines(String symbol, String interval, Params extraParams) throws IOException {
        return new JSONArray(getUIKLines(symbol, interval, extraParams));
    }

    /** Request to get candlestick data list
     * @param symbol: symbol to fetch compressed trade es. BTCBUSD
     * @param interval: time period to fetch
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#uiklines">
     *     https://binance-docs.github.io/apidocs/spot/en/#uiklines</a>
     * @return candlestick data as ArrayList<Candlestick>
     * **/
    public ArrayList<Candlestick> getUIKLinesList(String symbol, String interval, Params extraParams) throws IOException {
        return getCandlestickDataList(new JSONArray(getUIKLines(symbol, interval, extraParams)));
    }

    /** Method to assemble Candlestick list
     * @param jsonArray: obtain from Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data</a>
     * @return list of candlestick as ArrayList<Candlestick>
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
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
     * @return current average price as {@link String}
     * **/
    public String getCurrentAveragePrice(String symbol) throws IOException {
        return getRequestResponse(CURRENT_AVERAGE_PRICE_ENDPOINT,"?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get current average price
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
     * @return current average price as {@link JSONObject}
     * **/
    public JSONObject getJSONCurrentAveragePrice(String symbol) throws IOException {
        return new JSONObject(getCurrentAveragePrice(symbol));
    }

    /** Request to get current average price
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
     * @return current average price value as double
     * **/
    public double getCurrentAveragePriceValue(String symbol) throws IOException {
        JSONObject avgPrice = new JSONObject(getCurrentAveragePrice(symbol));
        return avgPrice.getDouble("price");
    }

    /** Request to get current average price
     * @param symbol: symbol to fetch current average price es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-average-price">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-average-price</a>
     * @return current average price CurrentAveragePrice object
     * **/
    public CurrentAveragePrice getObjectCurrentAveragePrice(String symbol) throws IOException {
        JSONObject avgPrice = new JSONObject(getCurrentAveragePrice(symbol));
        return new CurrentAveragePrice(avgPrice.getInt("mins"), avgPrice.getDouble("price"));
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change as {@link String}
     * **/
    public String getTickerPriceChange(String symbol) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT,"?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change as {@link JSONObject}
     * **/
    public JSONObject getJSONTickerPriceChange(String symbol) throws IOException {
        return new JSONObject(getTickerPriceChange(symbol));
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change as {@link TickerPriceChange} custom object
     * **/
    public TickerPriceChange getObjectTickerPriceChange(String symbol) throws IOException {
        return new TickerPriceChange(new JSONObject(getTickerPriceChange(symbol)));
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change as {@link String}
     * **/
    public String getTickerPriceChange(String symbol, String type) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT,"?symbol=" + symbol + "&type=" + type, GET_METHOD);
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change as {@link JSONObject}
     * **/
    public JSONObject getJSONTickerPriceChange(String symbol, String type) throws IOException {
        return new JSONObject(getTickerPriceChange(symbol, type));
    }

    /** Request to get ticker price change
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change as {@link TickerPriceChange} custom object
     * **/
    public TickerPriceChange getObjectTickerPriceChange(String symbol, String type) throws IOException {
        return new TickerPriceChange(new JSONObject(getTickerPriceChange(symbol, type)));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link String}
     * **/
    public String getTickersPriceChange(String[] symbols) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONTickersPriceChange(String[] symbols) throws IOException {
        return new JSONArray(getTickersPriceChange(symbols));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<TickerPriceChange> getTickerPriceChangeList(String[] symbols) throws IOException {
        return assembleTickersChangeList(new JSONArray(getTickersPriceChange(symbols)));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link String}
     * **/
    public String getTickersPriceChange(String[] symbols, String type) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT, "?type="+ type
                +"&symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONTickersPriceChange(String[] symbols, String type) throws IOException {
        return new JSONArray(getTickersPriceChange(symbols, type));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<TickerPriceChange> getTickerPriceChangeList(String[] symbols, String type) throws IOException {
        return assembleTickersChangeList(new JSONArray(getTickersPriceChange(symbols, type)));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link String}
     * **/
    public String getTickersPriceChange(ArrayList<String> symbols) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols)  + "]", GET_METHOD);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONTickersPriceChange(ArrayList<String> symbols) throws IOException {
        return new JSONArray(getTickersPriceChange(symbols));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<TickerPriceChange> getTickerPriceChangeList(ArrayList<String> symbols) throws IOException {
        return assembleTickersChangeList(new JSONArray(getTickersPriceChange(symbols)));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link String}
     * **/
    public String getTickersPriceChange(ArrayList<String> symbols, String type) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT, "?type=" + type
                + "&symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONTickersPriceChange(ArrayList<String> symbols, String type) throws IOException {
        return new JSONArray(getTickersPriceChange(symbols, type));
    }

    /** Request to get all requested tickers change list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<TickerPriceChange> getTickerPriceChangeList(ArrayList<String> symbols, String type) throws IOException {
        return assembleTickersChangeList(new JSONArray(getTickersPriceChange(symbols, type)));
    }

    /** Request to get all tickers change list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link String}
     * **/
    public String getTickersPriceChange() throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT, "", GET_METHOD);
    }

    /** Request to get all tickers price change list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONTickersPriceChange() throws IOException {
        return new JSONArray(getTickersPriceChange());
    }

    /** Request to get all tickers price change list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<TickerPriceChange> getTickerPriceChangeList() throws IOException {
        return assembleTickersChangeList(new JSONArray(getTickersPriceChange()));
    }

    /** Request to get all tickers change list 
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link String}
     * **/
    public String getTickersPriceChange(String type) throws IOException {
        return getRequestResponse(TICKER_PRICE_CHANGE_ENDPOINT, "?type=" + type, GET_METHOD);
    }

    /** Request to get all tickers price change list
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONTickersPriceChange(String type) throws IOException {
        return new JSONArray(getTickersPriceChange(type));
    }

    /** Request to get all tickers price change list 
     * @param type: response formatter -> FULL or MINI (constants in {@link Ticker} class)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#24hr-ticker-price-change-statistics</a>
     * @return ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<TickerPriceChange> getTickersPriceChangeList(String type) throws IOException {
        return assembleTickersChangeList(new JSONArray(getTickersPriceChange(type)));
    }
    
    /** Method to assemble a tickers price change list
     * @param tickers: {@link JSONArray} list of tickers in JSON format
     * @return a tickers price change as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    private ArrayList<TickerPriceChange> assembleTickersChangeList(JSONArray tickers){
        ArrayList<TickerPriceChange> tickerPriceStatistics = new ArrayList<>();
        for(int j=0; j < tickers.length(); j++)
            tickerPriceStatistics.add(new TickerPriceChange(tickers.getJSONObject(j)));
        return tickerPriceStatistics;
    }

    /** Request to get price ticker
     * @param symbol: symbol to fetch price ticker es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker as {@link String}
     * **/
    public String getPriceTicker(String symbol) throws IOException {
        return getRequestResponse(PRICE_TICKER_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get price ticker
     * @param symbol: symbol to fetch price ticker es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker as {@link JSONObject}
     * **/
    public JSONObject getJSONPriceTicker(String symbol) throws IOException {
        return new JSONObject(getPriceTicker(symbol));
    }

    /** Request to get price ticker
     * @param symbol: symbol to fetch price ticker es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker as PriceTicker object
     * **/
    public PriceTicker getObjectPriceTicker(String symbol) throws IOException {
        return assemblePriceTicker(new JSONObject(getPriceTicker(symbol)));
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as {@link String}
     * **/
    public String getPriceTickers(String[] symbols) throws IOException {
        return getRequestResponse(PRICE_TICKER_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as {@link JSONArray}
     * **/
    public JSONArray getJSONPriceTickers(String[] symbols) throws IOException {
        return new JSONArray(getPriceTickers(symbols));
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as ArrayList<PriceTicker>
     * **/
    public ArrayList<PriceTicker> getPriceTickersList(String[] symbols) throws IOException {
        return assemblePriceTickersList(new JSONArray(getPriceTickers(symbols)));
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as {@link String}
     * **/
    public String getPriceTickers(ArrayList<String> symbols) throws IOException {
        return getRequestResponse(PRICE_TICKER_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as {@link JSONArray}
     * **/
    public JSONArray getJSONPriceTickers(ArrayList<String> symbols) throws IOException {
        return new JSONArray(getPriceTickers(symbols));
    }

    /** Request to get all requested price tickers list
     * @param symbols: symbols to fetch ticker price es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as ArrayList<PriceTicker>
     * **/
    public ArrayList<PriceTicker> getPriceTickersList(ArrayList<String> symbols) throws IOException {
        return assemblePriceTickersList(new JSONArray(getPriceTickers(symbols)));
    }

    /** Request to get all price tickers list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as {@link String}
     * **/
    public String getPriceTickers() throws IOException {
        return getRequestResponse(PRICE_TICKER_ENDPOINT, "", GET_METHOD);
    }

    /** Request to get all price tickers list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as {@link JSONArray}
     * **/
    public JSONArray getJSONPriceTickers() throws IOException {
        return new JSONArray(getPriceTickers());
    }

    /** Request to get all price tickers list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return price ticker list as ArrayList<PriceTicker>
     * **/
    public ArrayList<PriceTicker> getPriceTickersList() throws IOException {
        return assemblePriceTickersList(new JSONArray(getPriceTickers()));
    }

    /** Method to assemble a tickers price list
     * @param tickers: {@link JSONArray} list of tickers in JSON format
     * @return a tickers price as {@link ArrayList} of {@link PriceTicker} custom object
     * **/
    private ArrayList<PriceTicker> assemblePriceTickersList(JSONArray tickers){
        ArrayList<PriceTicker> tickerPrices = new ArrayList<>();
        for (int j = 0; j < tickers.length(); j++)
            tickerPrices.add(assemblePriceTicker(tickers.getJSONObject(j)));
        return tickerPrices;
    }

    /** Method to assemble PriceTicker list
     * @param jsonObject: obtain from Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-price-ticker</a>
     * @return PriceTicker object
     * **/
    private PriceTicker assemblePriceTicker(JSONObject jsonObject){
        return new PriceTicker(jsonObject.getString("symbol"),
                jsonObject.getDouble("price")
        );
    }

    /** Request to get an order book ticker
     * @param symbol: symbol to fetch book ticker es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker as {@link String}
     * **/
    public String getOrderBookTicker(String symbol) throws IOException {
        return getRequestResponse(BOOK_TICKER_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get an order book ticker
     * @param symbol: symbol to fetch book ticker es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderBookTicker(String symbol) throws IOException {
        return new JSONObject(getOrderBookTicker(symbol));
    }

    /** Request to get an order book ticker
     * @param symbol: symbol to fetch book ticker es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker as BookTicker object
     * **/
    public OrderBookTicker getObjectOrderBookTicker(String symbol) throws IOException {
        return assembleOrderBookTicker(new JSONObject(getOrderBookTicker(symbol)));
    }

    /** Request to get all requested order book tickers list 
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link String}
     * **/
    public String getOrderBookTickers(String[] symbols) throws IOException {
        return getRequestResponse(BOOK_TICKER_ENDPOINT,  "?symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested order book tickers list 
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link JSONArray}
     * **/
    public JSONArray getJSONOrderBookTickers(String[] symbols) throws IOException {
        return new JSONArray(getOrderBookTickers(symbols));
    }

    /** Request to get all requested order book tickers list 
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link ArrayList} of {@link OrderBookTicker} custom object
     * **/
    public ArrayList<OrderBookTicker> getOrderBookTickersList(String[] symbols) throws IOException {
        return assembleOrderBookTickersList(new JSONArray(getOrderBookTickers(symbols)));
    }

    /** Request to get all requested order book tickers list 
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link String}
     * **/
    public String getOrderBookTickers(ArrayList<String> symbols) throws IOException {
        return getRequestResponse(BOOK_TICKER_ENDPOINT,  "?symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get all requested order book tickers list 
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link JSONArray}
     * **/
    public JSONArray getJSONOrderBookTickers(ArrayList<String> symbols) throws IOException {
        return new JSONArray(getOrderBookTickers(symbols));
    }

    /** Request to get all requested order book tickers list 
     * @param symbols: symbols to fetch order book ticker es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link ArrayList} of {@link OrderBookTicker} custom object
     * **/
    public ArrayList<OrderBookTicker> getOrderBookTickersList(ArrayList<String> symbols) throws IOException {
        return assembleOrderBookTickersList(new JSONArray(getOrderBookTickers(symbols)));
    }
    
    /** Request to get all order book tickers list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link String}
     * **/
    public String getOrderBookTickers() throws IOException {
        return getRequestResponse(BOOK_TICKER_ENDPOINT,  "", GET_METHOD);
    }

    /** Request to get all order book tickers list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link JSONArray}
     * **/
    public JSONArray getJSONOrderBookTickers() throws IOException {
        return new JSONArray(getOrderBookTickers());
    }

    /** Request to get all order book tickers list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return order book ticker list as {@link ArrayList} of {@link OrderBookTicker} custom object
     * **/
    public ArrayList<OrderBookTicker> getOrderBookTickersList() throws IOException {
        return assembleOrderBookTickersList(new JSONArray(getOrderBookTickers()));
    }

    /** Method to assemble a tickers price list
     * @param tickers: {@link JSONArray} list of tickers in JSON format
     * @return a tickers price as {@link ArrayList} of {@link OrderBookTicker} custom object
     * **/
    private ArrayList<OrderBookTicker> assembleOrderBookTickersList(JSONArray tickers){
        ArrayList<OrderBookTicker> bookTickers = new ArrayList<>();
        for (int j = 0; j < tickers.length(); j++)
            bookTickers.add(assembleOrderBookTicker(tickers.getJSONObject(j)));
        return bookTickers;
    }

    /** Method to assemble an {@link OrderBookTicker} object
     * @param jsonOrderBook: obtain from Binance request
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker">
     *     https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker</a>
     * @return OrderBookTicker object
     * **/
    private OrderBookTicker assembleOrderBookTicker(JSONObject jsonOrderBook){
        return new OrderBookTicker(jsonOrderBook.getString("symbol"),
                jsonOrderBook.getDouble("bidPrice"),
                jsonOrderBook.getDouble("bidQty"),
                jsonOrderBook.getDouble("askPrice"),
                jsonOrderBook.getDouble("askQty")
        );
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link String}
     * **/
    public String getRollingTicker(String symbol) throws IOException {
        return getRequestResponse(ROLLING_TICKER_ENDPOINT,"?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link JSONObject}
     * **/
    public JSONObject getJSONRollingTicker(String symbol) throws IOException {
        return new JSONObject(getRollingTicker(symbol));
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link TickerPriceChange} custom object
     * **/
    public RollingTicker getObjectRollingTicker(String symbol) throws IOException {
        return new RollingTicker(new JSONObject(getRollingTicker(symbol)));
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link String}
     * **/
    public String getRollingTicker(String symbol, Params params) throws IOException {
        return getRequestResponse(ROLLING_TICKER_ENDPOINT,params.createQueryString() + "&symbol=" + symbol, GET_METHOD);
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link JSONObject}
     * **/
    public JSONObject getJSONRollingTicker(String symbol, Params params) throws IOException {
        return new JSONObject(getRollingTicker(symbol, params));
    }

    /** Request to get 24 hours rolling window price change statistics
     * @param symbol: symbol to fetch ticker price change es. BTCBUSD
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link TickerPriceChange} custom object
     * **/
    public RollingTicker getObjectRollingTicker(String symbol, Params params) throws IOException {
        return new RollingTicker(new JSONObject(getRollingTicker(symbol, params)));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link String}
     * **/
    public String getRollingTickers(String[] symbols) throws IOException {
        return getRequestResponse(ROLLING_TICKER_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONRollingTickers(String[] symbols) throws IOException {
        return new JSONArray(getRollingTickers(symbols));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<RollingTicker> getRollingTickersList(String[] symbols) throws IOException {
        return assembleRollingTickersList(new JSONArray(getRollingTickers(symbols)));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change as {@link String}
     * **/
    public String getRollingTickers(String[] symbols, Params params) throws IOException {
        return getRequestResponse(ROLLING_TICKER_ENDPOINT, params.createQueryString() + "&symbols=[" +
                assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONRollingTickers(String[] symbols, Params params) throws IOException {
        return new JSONArray(getRollingTickers(symbols, params));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link String} array format
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<RollingTicker> getRollingTickersList(String[] symbols, Params params) throws IOException {
        return assembleRollingTickersList(new JSONArray(getRollingTickers(symbols, params)));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link String}
     * **/
    public String getRollingTickers(ArrayList<String> symbols) throws IOException {
        return getRequestResponse(ROLLING_TICKER_ENDPOINT, "?symbols=[" + assembleSymbolsList(symbols)
                + "]", GET_METHOD);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONRollingTickers(ArrayList<String> symbols) throws IOException {
        return new JSONArray(getRollingTickers(symbols));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<RollingTicker> getRollingTickersList(ArrayList<String> symbols) throws IOException {
        return assembleRollingTickersList(new JSONArray(getRollingTickers(symbols)));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link String}
     * **/
    public String getRollingTickers(ArrayList<String> symbols, Params params) throws IOException {
        return getRequestResponse(ROLLING_TICKER_ENDPOINT, params.createQueryString() + "&symbols=[" +
                        assembleSymbolsList(symbols) + "]", GET_METHOD);
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link JSONArray}
     * **/
    public JSONArray getJSONRollingTickers(ArrayList<String> symbols, Params params) throws IOException {
        return new JSONArray(getRollingTickers(symbols, params));
    }

    /** Request to get 24 hours rolling requested window price change statistics list
     * @param symbols: symbols to fetch ticker price change es. BTCBUSD, ETHUSDT in {@link ArrayList} of {@link String} format
     * @param params: additional params of the request
     * @implSpec (keys accepted are type,windowSize)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics">
     *     https://binance-docs.github.io/apidocs/spot/en/#rolling-window-price-change-statistics</a>
     * @return rolling ticker price change list as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    public ArrayList<RollingTicker> getRollingTickersList(ArrayList<String> symbols, Params params) throws IOException {
        return assembleRollingTickersList(new JSONArray(getRollingTickers(symbols, params)));
    }

    /** Method to assemble a rolling tickers price change list
     * @param tickers: {@link JSONArray} list of rolling tickers in JSON format
     * @return a rolling ticker price change as {@link ArrayList} of {@link TickerPriceChange} custom object
     * **/
    private ArrayList<RollingTicker> assembleRollingTickersList(JSONArray tickers){
        ArrayList<RollingTicker> rollingTickers = new ArrayList<>();
        for(int j=0; j < tickers.length(); j++)
            rollingTickers.add(new RollingTicker(tickers.getJSONObject(j)));
        return rollingTickers;
    }

    /** Method to get forecast of a cryptocurrency in base of day's gap inserted
     * @param symbol: symbol to calculate forecast es. BTCBUSD
     * @param candlestickInterval: temporal interval of data for the forecast
     * @param intervalDays: days gap for the prevision range
     * @param toleranceValue: tolerance for select similar value compared to lastValue inserted
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     * **/
    public double getSymbolForecast(String symbol, String candlestickInterval, int intervalDays, double toleranceValue) throws IOException {
        ArrayList<Double> historicalValues = new ArrayList<>();
        for (Candlestick candlestick : getCandlestickDataList(symbol, candlestickInterval))
            historicalValues.add(candlestick.getHigh());
        return computeTPTOPAsset(historicalValues, getCurrentAveragePriceValue(symbol), intervalDays, toleranceValue);
    }

    /** Method to get forecast of a cryptocurrency in base of day's gap inserted
     * @param symbol: symbol to calculate forecast es. BTCBUSD
     * @param candlestickInterval: temporal interval of data for the forecast
     * @param intervalDays: days gap for the prevision range
     * @param toleranceValue: tolerance for select similar value compared to lastValue inserted
     * @param decimalDigits: number of digits to round final forecast value
     * @return forecast value as a double es. 8 or -8
     * @throws IllegalArgumentException if lastValue is negative or intervalDays are less or equal to 0
     * **/
    public double getSymbolForecast(String symbol, String candlestickInterval, int intervalDays, double toleranceValue,
                                    int decimalDigits) throws IOException {
        return roundValue(getSymbolForecast(symbol, candlestickInterval, intervalDays, toleranceValue), decimalDigits);
    }

}

