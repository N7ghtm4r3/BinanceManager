package com.tecknobit.binancemanager.Managers.Market.Records.Filters;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Filter {

    public static final String PRICE_FILTER = "PRICE_FILTER";
    public static final String PERCENT_PRICE = "PERCENT_PRICE";
    public static final String LOT_SIZE = "LOT_SIZE";
    public static final String MIN_NOTIONAL = "MIN_NOTIONAL";
    public static final String ICEBERG_PARTS = "ICEBERG_PARTS";
    public static final String MARKET_LOT_SIZE = "MARKET_LOT_SIZE";
    public static final String MAX_NUM_ORDERS = "MAX_NUM_ORDERS";
    public static final String MAX_NUM_ALGO_ORDERS = "MAX_NUM_ALGO_ORDERS";
    private final ArrayList<String> keys;
    private final ArrayList<Object> values;
    private final String filterType;

    public Filter(ArrayList<String> keys, ArrayList<Object> values, String filterType) {
        this.keys = keys;
        this.values = values;
        this.filterType = filterType;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public ArrayList<Object> getValues() {
        return values;
    }

    public String getFilterType() {
        return filterType;
    }

    public JSONObject getFilterDetails() {
       HashMap<String,HashMap<String,Object>> filterDetails = new HashMap<>();
       HashMap<String,Object> filterValues = new HashMap<>();
       for (int j=0; j < keys.size(); j++)
           filterValues.put(keys.get(j),values.get(j));
       filterDetails.put(filterType, filterValues);
       return new JSONObject(filterDetails);
    }

    public static class FilterDetails {

        private final String key;
        private final Object value;

        public FilterDetails(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

    }

}