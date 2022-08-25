package com.tecknobit.binancemanager.Managers.Market.Records;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code Filter} class is useful to format filter of ExchangeInformation Binance request
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
 *  https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class Filter {

    /**
     * {@code PRICE_FILTER} is constant for price filter
     * **/
    public static final String PRICE_FILTER = "PRICE_FILTER";

    /**
     * {@code PERCENT_PRICE} is constant for percent filter
     * **/
    public static final String PERCENT_PRICE = "PERCENT_PRICE";

    /**
     * {@code LOT_SIZE} is constant for lot size filter
     * **/
    public static final String LOT_SIZE = "LOT_SIZE";

    /**
     * {@code MIN_NOTIONAL} is constant for min notional filter
     * **/
    public static final String MIN_NOTIONAL = "MIN_NOTIONAL";

    /**
     * {@code ICEBERG_PARTS} is constant for iceberg parts filter
     * **/
    public static final String ICEBERG_PARTS = "ICEBERG_PARTS";

    /**
     * {@code MARKET_LOT_SIZE} is constant for market lot size
     * **/
    public static final String MARKET_LOT_SIZE = "MARKET_LOT_SIZE";

    /**
     * {@code MAX_NUM_ORDERS} is constant for max orders number
     * **/
    public static final String MAX_NUM_ORDERS = "MAX_NUM_ORDERS";

    /**
     * {@code MAX_NUM_ALGO_ORDERS} is constant for max algo orders number
     * **/
    public static final String MAX_NUM_ALGO_ORDERS = "MAX_NUM_ALGO_ORDERS";

    /**
     * {@code keys} is instance that contains keys of the filters
     * **/
    private final ArrayList<String> keys;

    /**
     * {@code values} is instance that contains values of the filters
     * **/
    private final ArrayList<Object> values;

    /**
     * {@code filterType} is instance that contains type of the filter
     * **/
    private final String filterType;

    /** Constructor to init {@link Filter} object
     * @param keys: keys of the filters
     * @param values: values of the filters
     * @param filterType: type of the filter
     * **/
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

    /** Method to get order details value formatted in JSON <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return JsonObject of key and values of a filter
     * **/
    public JSONObject getFilterDetails() {
       HashMap<String, HashMap<String,Object>> filterDetails = new HashMap<>();
       HashMap<String, Object> filterValues = new HashMap<>();
       for (int j=0; j < keys.size(); j++)
           filterValues.put(keys.get(j),values.get(j));
       filterDetails.put(filterType, filterValues);
       return new JSONObject(filterDetails);
    }

    @Override
    public String toString() {
        return "Filter{" +
                "keys=" + keys +
                ", values=" + values +
                ", filterType='" + filterType + '\'' +
                '}';
    }

    /**
     * The {@code FilterDetails} class is useful to contain and filter details
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * https://binance-docs.github.io/apidocs/spot/en/#exchange-information</a>
     * **/

    public static class FilterDetails {

        /**
         * {@code key} is instance that contains key of the filter
         * **/
        private final String key;

        /**
         * {@code value} is instance that contains value of the filter
         * **/
        private final Object value;

        /** Constructor to init {@link FilterDetails} object
         * @param key: key of the filter
         * @param value: value of the filter
         * **/
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

        @Override
        public String toString() {
            return "FilterDetails{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }

    }

}
