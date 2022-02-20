package com.tecknobit.binancemanager.Managers.Market.Records.Filters;

import java.util.ArrayList;

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

    public Filter(ArrayList<String> keys, ArrayList<Object> values) {
        this.keys = keys;
        this.values = values;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public ArrayList<Object> getValues() {
        return values;
    }

}
