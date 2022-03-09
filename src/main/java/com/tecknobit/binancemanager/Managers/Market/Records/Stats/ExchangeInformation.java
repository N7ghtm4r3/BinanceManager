package com.tecknobit.binancemanager.Managers.Market.Records.Stats;

import com.tecknobit.binancemanager.Managers.Market.Records.Filter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code ExchangeInformation} class is useful to manage ExchangeInformation Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ExchangeInformation {

    private final String timezone;
    private final long serverTime;
    private final JSONObject jsonObject;
    private ArrayList<RateLimit> rateLimits;
    private final ArrayList<Filter> exchangeFilters;
    private ArrayList<Symbol> symbols;

    public ExchangeInformation(String timezone, long serverTime, JSONObject jsonObject) {
        this.timezone = timezone;
        this.serverTime = serverTime;
        this.jsonObject = jsonObject;
        assembleRateLimits();
        exchangeFilters = assembleFilters(jsonObject.getJSONArray("exchangeFilters"));
        assembleSymbols();
    }

    /** Method to assemble an RateLimits list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * any return
     * **/
    private void assembleRateLimits() {
        rateLimits = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("rateLimits");
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject rateLimit = jsonArray.getJSONObject(j);
            rateLimits.add(new RateLimit(rateLimit.getInt("intervalNum"),
                    rateLimit.getInt("limit"),
                    rateLimit.getString("interval"),
                    rateLimit.getString("rateLimitType")
            ));
        }
    }

    /** Method to assemble a Filters list
     * @param #jsonArray: obtained from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * @return filters list as ArrayList<Filter> object
     * **/
    private static ArrayList<Filter> assembleFilters(JSONArray jsonArray) {
        ArrayList<Filter> filters = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject filter = jsonArray.getJSONObject(j);
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
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * any return
     * **/
    private void assembleSymbols() {
        symbols = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("symbols");
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject symbol = jsonArray.getJSONObject(j);
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
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * **/

    public static class RateLimit {

        public static final String RATE_TYPE_REQUEST_WEIGHT = "REQUEST_WEIGHT";
        public static final String RATE_TYPE_ORDERS = "ORDERS";
        public static final String RATE_TYPE_RAW_REQUESTS = "RAW_REQUESTS";
        public static final String INTERVAL_DAY = "DAY";
        public static final String INTERVAL_HOUR = "HOUR";
        public static final String INTERVAL_MINUTE = "MINUTE";
        public static final String INTERVAL_SECOND = "SECOND";
        private final int intervalNum;
        private final int limit;
        private final String interval;
        private final String rateLimitType;

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
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     * **/

    public static class Symbol {

        private final String symbol;
        private final boolean quoteOrderQtyMarketAllowed;
        private final String status;
        private final String baseAsset;
        private final int baseAssetPrecision;
        private final String quoteAsset;
        private final int quotePrecision;
        private final int quoteAssetPrecision;
        private final ArrayList<String> orderTypes;
        private final boolean icebergAllowed;
        private final boolean ocoAllowed;
        private final boolean isSpotTradingAllowed;
        private final boolean isMarginTradingAllowed;
        private final ArrayList<Filter> filters;
        private final ArrayList<String> permissions;
        private final int baseCommissionPrecision;

        public Symbol(String symbol, boolean quoteOrderQtyMarketAllowed, String status, String baseAsset, int baseAssetPrecision, String quoteAsset,
                      int quotePrecision, int quoteAssetPrecision, boolean icebergAllowed, boolean ocoAllowed,
                      boolean isSpotTradingAllowed, boolean isMarginTradingAllowed, JSONObject jsonObject, int baseCommissionPrecision) {
            this.symbol = symbol;
            this.quoteOrderQtyMarketAllowed = quoteOrderQtyMarketAllowed;
            this.status = status;
            this.baseAsset = baseAsset;
            this.baseAssetPrecision = baseAssetPrecision;
            this.quoteAsset = quoteAsset;
            this.quotePrecision = quotePrecision;
            this.quoteAssetPrecision = quoteAssetPrecision;
            this.orderTypes = loadEnumsList(jsonObject.getJSONArray("orderTypes"));
            this.icebergAllowed = icebergAllowed;
            this.ocoAllowed = ocoAllowed;
            this.isSpotTradingAllowed = isSpotTradingAllowed;
            this.isMarginTradingAllowed = isMarginTradingAllowed;
            filters = ExchangeInformation.assembleFilters(jsonObject.getJSONArray("filters"));
            this.permissions = loadEnumsList(jsonObject.getJSONArray("permissions"));
            this.baseCommissionPrecision = baseCommissionPrecision;
        }

        /** Method to assemble a EnumValues list
         * @param #jsonArray: obtained from Binance request
         * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
         * @return exchangeEnumValues list as ArrayList<EnumValues> object
         * **/
        private ArrayList<String> loadEnumsList(JSONArray jsonArray){
            ArrayList<String> enumValues = new ArrayList<>();
            for(int j=0; j < jsonArray.length(); j++)
                enumValues.add(jsonArray.getString(j));
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
