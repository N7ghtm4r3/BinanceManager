package com.tecknobit.binancemanager.Managers.Market.Records.Stats;

import com.tecknobit.binancemanager.Managers.Market.Records.Filter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code ExchangeInformation} class is useful to manage ExchangeInformation Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
 *      https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ExchangeInformation {

    /**
     * {@code timezone} is instance that contains timezone information
     * **/
    private final String timezone;

    /**
     * {@code serverTime} is instance that contains server time information
     * **/
    private final long serverTime;

    /**
     * {@code jsonInformation} is instance that contains exchange information in JSON format
     * **/
    private final JSONObject jsonInformation;

    /**
     * {@code rateLimits} is instance that contains rate limits list
     * **/
    private ArrayList<RateLimit> rateLimits;

    /**
     * {@code exchangeFilters} is instance that contains filters list
     * **/
    private final ArrayList<Filter> exchangeFilters;

    /**
     * {@code symbols} is instance that contains symbols list
     * **/
    private ArrayList<Symbol> symbols;

    /** Constructor to init {@link ExchangeInformation} object
     * @param timezone: timezone information
     * @param serverTime: server time information
     * @param jsonInformation: exchange information in JSON format
     * **/
    public ExchangeInformation(String timezone, long serverTime, JSONObject jsonInformation) {
        this.timezone = timezone;
        this.serverTime = serverTime;
        this.jsonInformation = jsonInformation;
        assembleRateLimits();
        exchangeFilters = assembleFilters(jsonInformation.getJSONArray("exchangeFilters"));
        assembleSymbols();
    }

    /** Method to assemble an RateLimits list
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * **/
    private void assembleRateLimits() {
        rateLimits = new ArrayList<>();
        JSONArray jsonRateLimits = jsonInformation.getJSONArray("rateLimits");
        for (int j = 0; j < jsonRateLimits.length(); j++){
            JSONObject rateLimit = jsonRateLimits.getJSONObject(j);
            rateLimits.add(new RateLimit(rateLimit.getInt("intervalNum"),
                    rateLimit.getInt("limit"),
                    rateLimit.getString("interval"),
                    rateLimit.getString("rateLimitType")
            ));
        }
    }

    /** Method to assemble a Filters list
     * @param jsonFilters: obtained from Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * @return filters list as ArrayList<Filter> object
     * **/
    private static ArrayList<Filter> assembleFilters(JSONArray jsonFilters) {
        ArrayList<Filter> filters = new ArrayList<>();
        for (int j = 0; j < jsonFilters.length(); j++){
            JSONObject filter = jsonFilters.getJSONObject(j);
            ArrayList<String> filterKeys = new ArrayList<>(filter.keySet());
            ArrayList<Object> filterValues = new ArrayList<>();
            for (String filterKey : filterKeys)
                filterValues.add(filter.get(filterKey));
            filters.add(new Filter(filterKeys,filterValues,filter.getString("filterType")));
        }
        return filters;
    }

    /** Method to assemble a Symbols list
     * any params required
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * **/
    private void assembleSymbols() {
        symbols = new ArrayList<>();
        JSONArray jsonSymbols = jsonInformation.getJSONArray("symbols");
        for (int j = 0; j < jsonSymbols.length(); j++){
            JSONObject symbol = jsonSymbols.getJSONObject(j);
            symbols.add(new Symbol(symbol.getString("symbol"),
                    symbol.getBoolean("quoteOrderQtyMarketAllowed"),
                    symbol.getString("status"),
                    symbol.getString("baseAsset"),
                    symbol.getInt("baseAssetPrecision"),
                    symbol.getString("quoteAsset"),
                    symbol.getInt("quotePrecision"),
                    symbol.getInt("quoteAssetPrecision"),
                    symbol.getBoolean("icebergAllowed"),
                    symbol.getBoolean("ocoAllowed"),
                    symbol.getBoolean("isSpotTradingAllowed"),
                    symbol.getBoolean("isMarginTradingAllowed"),
                    symbol,
                    symbol.getInt("baseCommissionPrecision")
            ));
        }
    }

    public String getTimezone() {
        return timezone;
    }

    public long getServerTime() {
        return serverTime;
    }

    public ArrayList<RateLimit> getRateLimits() {
        return rateLimits;
    }

    public ArrayList<Filter> getExchangeFilters() {
        return exchangeFilters;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * The {@code RateLimit} class is useful to obtain and format RateLimit object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * **/

    public static class RateLimit {

        /**
         * {@code RATE_TYPE_REQUEST_WEIGHT} is constant for request weight rate limit
         * **/
        public static final String RATE_TYPE_REQUEST_WEIGHT = "REQUEST_WEIGHT";

        /**
         * {@code RATE_TYPE_ORDERS} is constant for request orders rate limit
         * **/
        public static final String RATE_TYPE_ORDERS = "ORDERS";

        /**
         * {@code RAW_REQUESTS} is constant for raw requests rate limit
         * **/
        public static final String RATE_TYPE_RAW_REQUESTS = "RAW_REQUESTS";

        /**
         * {@code INTERVAL_DAY} is constant for interval day rate limit
         * **/
        public static final String INTERVAL_DAY = "DAY";

        /**
         * {@code INTERVAL_HOUR} is constant for interval hour rate limit
         * **/
        public static final String INTERVAL_HOUR = "HOUR";

        /**
         * {@code INTERVAL_MINUTE} is constant for interval minute rate limit
         * **/
        public static final String INTERVAL_MINUTE = "MINUTE";

        /**
         * {@code INTERVAL_SECOND} is constant for interval second rate limit
         * **/
        public static final String INTERVAL_SECOND = "SECOND";

        /**
         * {@code intervalNum} is instance that contains interval number for rate limit
         * **/
        private final int intervalNum;

        /**
         * {@code limit} is instance that contains limit number for rate
         * **/
        private final int limit;

        /**
         * {@code interval} is instance that contains interval for rate limit
         * **/
        private final String interval;

        /**
         * {@code rateLimitType} is instance that contains rate limit type
         * **/
        private final String rateLimitType;

        /** Constructor to init {@link RateLimit} object
         * @param intervalNum: interval number for rate limit
         * @param limit: limit number for rate
         * @param interval: interval for rate limit
         * @param rateLimitType: rate limit type
         * **/
        public RateLimit(int intervalNum, int limit, String interval, String rateLimitType) {
            this.intervalNum = intervalNum;
            this.limit = limit;
            this.interval = interval;
            this.rateLimitType = rateLimitType;
        }

        public int getIntervalNum() {
            return intervalNum;
        }

        public int getLimit() {
            return limit;
        }

        public String getInterval() {
            return interval;
        }

        public String getRateLimitType() {
            return rateLimitType;
        }

    }

    /**
     * The {@code Symbol} class is useful to obtain and format Symbol object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * **/

    public static class Symbol {

        /**
         * {@code symbol} is instance that contains symbol
         * **/
        private final String symbol;

        /**
         * {@code quoteOrderQtyMarketAllowed} is instance that contains quote order quantity market allowed
         * **/
        private final boolean quoteOrderQtyMarketAllowed;

        /**
         * {@code status} is instance that contains status of symbol
         * **/
        private final String status;

        /**
         * {@code baseAsset} is instance that contains base asset of symbol
         * **/
        private final String baseAsset;

        /**
         * {@code baseAssetPrecision} is instance that contains base asset precision of symbol
         * **/
        private final int baseAssetPrecision;

        /**
         * {@code quoteAsset} is instance that contains quote asset of symbol
         * **/
        private final String quoteAsset;

        /**
         * {@code quotePrecision} is instance that contains quote precision of symbol
         * **/
        private final int quotePrecision;

        /**
         * {@code quoteAssetPrecision} is instance that contains quote asset precision of symbol
         * **/
        private final int quoteAssetPrecision;

        /**
         * {@code orderTypes} is instance that contains order types of symbol
         * **/
        private final ArrayList<String> orderTypes;

        /**
         * {@code icebergAllowed} is instance that contains if iceberg is allowed for the symbol
         * **/
        private final boolean icebergAllowed;

        /**
         * {@code ocoAllowed} is instance that contains if oco is allowed for the symbol
         * **/
        private final boolean ocoAllowed;

        /**
         * {@code isSpotTradingAllowed} is instance that contains if spot trading is allowed for the symbol
         * **/
        private final boolean isSpotTradingAllowed;

        /**
         * {@code isMarginTradingAllowed} is instance that contains if margin trading is allowed for the symbol
         * **/
        private final boolean isMarginTradingAllowed;

        /**
         * {@code filters} is instance that contains filters list of symbol
         * **/
        private final ArrayList<Filter> filters;

        /**
         * {@code permissions} is instance that contains permissions list of symbol
         * **/
        private final ArrayList<String> permissions;

        /**
         * {@code baseCommissionPrecision} is instance that contains base commission precision of symbol
         * **/
        private final int baseCommissionPrecision;

        /** Constructor to init {@link Symbol} object
         * @param symbol: symbol
         * @param quoteOrderQtyMarketAllowed: quote order quantity market allowed
         * @param status: status of symbol
         * @param baseAsset: base asset of symbol
         * @param baseAssetPrecision: base asset precision of symbol
         * @param quoteAsset: quote asset of symbol
         * @param quotePrecision: quote precision of symbol
         * @param quoteAssetPrecision: quote asset precision of symbol
         * @param icebergAllowed: iceberg is allowed for the symbol
         * @param ocoAllowed: oco is allowed for the symbol
         * @param isSpotTradingAllowed: spot trading is allowed for the symbol
         * @param isMarginTradingAllowed: margin trading is allowed for the symbol
         * @param jsonSymbol: information list about symbol in JSON format
         * @param baseCommissionPrecision: base commission precision of symbol
         * **/
        public Symbol(String symbol, boolean quoteOrderQtyMarketAllowed, String status, String baseAsset,
                      int baseAssetPrecision, String quoteAsset, int quotePrecision, int quoteAssetPrecision,
                      boolean icebergAllowed, boolean ocoAllowed, boolean isSpotTradingAllowed, boolean isMarginTradingAllowed,
                      JSONObject jsonSymbol, int baseCommissionPrecision) {
            this.symbol = symbol;
            this.quoteOrderQtyMarketAllowed = quoteOrderQtyMarketAllowed;
            this.status = status;
            this.baseAsset = baseAsset;
            this.baseAssetPrecision = baseAssetPrecision;
            this.quoteAsset = quoteAsset;
            this.quotePrecision = quotePrecision;
            this.quoteAssetPrecision = quoteAssetPrecision;
            this.orderTypes = loadEnumsList(jsonSymbol.getJSONArray("orderTypes"));
            this.icebergAllowed = icebergAllowed;
            this.ocoAllowed = ocoAllowed;
            this.isSpotTradingAllowed = isSpotTradingAllowed;
            this.isMarginTradingAllowed = isMarginTradingAllowed;
            filters = ExchangeInformation.assembleFilters(jsonSymbol.getJSONArray("filters"));
            this.permissions = loadEnumsList(jsonSymbol.getJSONArray("permissions"));
            this.baseCommissionPrecision = baseCommissionPrecision;
        }

        /** Method to assemble a EnumValues list
         * @param jsonList: obtained from Binance request
         * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
         *     https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
         * @return exchangeEnumValues list as ArrayList<EnumValues> object
         * **/
        private ArrayList<String> loadEnumsList(JSONArray jsonList){
            ArrayList<String> enumValues = new ArrayList<>();
            for(int j = 0; j < jsonList.length(); j++)
                enumValues.add(jsonList.getString(j));
            return enumValues;
        }

        public String getSymbol() {
            return symbol;
        }

        public boolean isQuoteOrderQtyMarketAllowed() {
            return quoteOrderQtyMarketAllowed;
        }

        public String getStatus() {
            return status;
        }

        public String getBaseAsset() {
            return baseAsset;
        }

        public int getBaseAssetPrecision() {
            return baseAssetPrecision;
        }

        public String getQuoteAsset() {
            return quoteAsset;
        }

        public int getQuotePrecision() {
            return quotePrecision;
        }

        public int getQuoteAssetPrecision() {
            return quoteAssetPrecision;
        }

        public ArrayList<String> getOrderTypesList() {
            return orderTypes;
        }

        public String getOrderType(int index) {
            return orderTypes.get(index);
        }

        public boolean isIcebergAllowed() {
            return icebergAllowed;
        }

        public boolean isOcoAllowed() {
            return ocoAllowed;
        }

        public boolean isSpotTradingAllowed() {
            return isSpotTradingAllowed;
        }

        public boolean isMarginTradingAllowed() {
            return isMarginTradingAllowed;
        }

        public ArrayList<Filter> getFiltersList() {
            return filters;
        }

        public Filter getFilter(int index) {
            return filters.get(index);
        }

        public ArrayList<String> getPermissionsList() {
            return permissions;
        }

        public String getPermission(int index) {
            return permissions.get(index);
        }

        public int getBaseCommissionPrecision() {
            return baseCommissionPrecision;
        }

    }

}
