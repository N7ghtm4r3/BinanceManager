package com.tecknobit.binancemanager.Managers.Market.Records;

import java.util.ArrayList;

public class ExchangeInformation {

    private final String timezone;
    private final long serverTime;
    private final ArrayList<RateLimit> rateLimits;
    private final ArrayList<ExchangeFilter> exchangeFilters;
    private final ArrayList<Symbol> symbols;

    public ExchangeInformation(String timezone, long serverTime, ArrayList<RateLimit> rateLimits,
                               ArrayList<ExchangeFilter> exchangeFilters, ArrayList<Symbol> symbols) {
        this.timezone = timezone;
        this.serverTime = serverTime;
        this.rateLimits = rateLimits;
        this.exchangeFilters = exchangeFilters;
        this.symbols = symbols;
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

    }

    public static class ExchangeFilter {
    }

    public static class Symbol {

        private final String symbol;
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
        private final ArrayList<EnumValues> filters;
        private final ArrayList<EnumValues> permissions;

        public Symbol(String symbol, String status, String baseAsset, int baseAssetPrecision, String quoteAsset,
                      int quotePrecision, int quoteAssetPrecision, ArrayList<EnumValues> orderTypes, boolean icebergAllowed,
                      boolean ocoAllowed, boolean isSpotTradingAllowed, boolean isMarginTradingAllowed, ArrayList<EnumValues> filters,
                      ArrayList<EnumValues> permissions) {
            this.symbol = symbol;
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

        public ArrayList<EnumValues> getFilters() {
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
