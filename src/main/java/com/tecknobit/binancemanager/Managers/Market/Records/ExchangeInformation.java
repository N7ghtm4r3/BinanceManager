package com.tecknobit.binancemanager.Managers.Market.Records;

import com.tecknobit.binancemanager.Managers.Market.Records.Filters.Filter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExchangeInformation {

    private final String timezone;
    private final long serverTime;
    private final JSONObject jsonObject;
    private ArrayList<RateLimit> rateLimits;
    private ArrayList<ExchangeFilter> exchangeFilters;
    private ArrayList<Symbol> symbols;

    public ExchangeInformation(String timezone, long serverTime, JSONObject jsonObject) {
        this.timezone = timezone;
        this.serverTime = serverTime;
        this.jsonObject = jsonObject;
        assembleRateLimits();
        assembleExchangeFilters();
        assembleSymbols();
    }

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

    private void assembleExchangeFilters() {
        exchangeFilters = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("exchangeFilters");
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject filter = jsonArray.getJSONObject(j);
            ArrayList<String> filterKeys = new ArrayList<>(filter.keySet());
            ArrayList<Object> filterValues = new ArrayList<>();
            for (int i=0; j < filterKeys.size(); i++)
                filterValues.add(filter.get(filterKeys.get(j)));
            exchangeFilters.add(new ExchangeFilter(filterKeys,filterValues));
        }
    }

    private void assembleSymbols() {
        symbols = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("symbols");
        for (int j=0; j < jsonArray.length(); j++){
            
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

    public ArrayList<ExchangeFilter> getExchangeFilters() {
        return exchangeFilters;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

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

    public static class ExchangeFilter extends Filter {

        public ExchangeFilter(ArrayList<String> keys, ArrayList<Object> values) {
            super(keys, values);
        }

        @Override
        public ArrayList<String> getKeys() {
            return super.getKeys();
        }

        @Override
        public ArrayList<Object> getValues() {
            return super.getValues();
        }

    }

    public static class Symbol {

        private final String symbol;
        private final boolean quoteOrderQtyMarketAllowed;
        private final String status;
        private final String baseAsset;
        private final int baseAssetPrecision;
        private final String quoteAsset;
        private final int quotePrecision;
        private final int quoteAssetPrecision;
        private final ArrayList<EnumValues> orderTypes;
        private final boolean icebergAllowed;
        private final boolean ocoAllowed;
        private final boolean isSpotTradingAllowed;
        private final boolean isMarginTradingAllowed;
        private final HashMap<String,Filter> filters;
        private final ArrayList<EnumValues> permissions;

        public Symbol(String symbol, boolean quoteOrderQtyMarketAllowed, String status, String baseAsset, int baseAssetPrecision, String quoteAsset,
                      int quotePrecision, int quoteAssetPrecision, ArrayList<EnumValues> orderTypes, boolean icebergAllowed,
                      boolean ocoAllowed, boolean isSpotTradingAllowed, boolean isMarginTradingAllowed, HashMap<String, Filter> filters,
                      ArrayList<EnumValues> permissions) {
            this.symbol = symbol;
            this.quoteOrderQtyMarketAllowed = quoteOrderQtyMarketAllowed;
            this.status = status;
            this.baseAsset = baseAsset;
            this.baseAssetPrecision = baseAssetPrecision;
            this.quoteAsset = quoteAsset;
            this.quotePrecision = quotePrecision;
            this.quoteAssetPrecision = quoteAssetPrecision;
            this.orderTypes = orderTypes;
            this.icebergAllowed = icebergAllowed;
            this.ocoAllowed = ocoAllowed;
            this.isSpotTradingAllowed = isSpotTradingAllowed;
            this.isMarginTradingAllowed = isMarginTradingAllowed;
            this.filters = filters;
            this.permissions = permissions;
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

        public ArrayList<EnumValues> getOrderTypes() {
            return orderTypes;
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

        public HashMap<String,Filter> getFilters() {
            return filters;
        }

        public ArrayList<EnumValues> getPermissions() {
            return permissions;
        }

        private static class EnumValues {

            private final String enumValue;

            public EnumValues(String enumValue) {
                this.enumValue = enumValue;
            }

            public String getEnumValue() {
                return enumValue;
            }

        }

    }

}
