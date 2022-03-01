package com.tecknobit.binancemanager.Managers.Market.Records;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code OrderBook} class is useful to format Binance OrderBook request
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OrderBook {

    private final long lastUpdateId;
    private final ArrayList<BookOrderDetails> orderDetailsBids;
    private final ArrayList<BookOrderDetails> orderDetailsAsks;
    private final String symbol;

    public OrderBook(long lastUpdateId, JSONObject jsonObject, String symbol) {
        this.lastUpdateId = lastUpdateId;
        orderDetailsAsks = loadOrderDetails(jsonObject.getJSONArray("bids"));
        orderDetailsBids = loadOrderDetails(jsonObject.getJSONArray("asks"));
        this.symbol = symbol;
    }

    /** Method to get load list of order details
     * @param #jsonArray: obtained from Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * return order details list as ArrayList<BookOrderDetails> object
     * **/
    private ArrayList<BookOrderDetails> loadOrderDetails(JSONArray jsonArray){
        ArrayList<BookOrderDetails> orderDetails = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONArray details = jsonArray.getJSONArray(j);
            orderDetails.add(new BookOrderDetails(details.getDouble(0),
                    details.getDouble(1)));
        }
        return orderDetails;
    }

    public long getLastUpdateId() {
        return lastUpdateId;
    }

    public ArrayList<BookOrderDetails> getOrderDetailsBids() {
        return orderDetailsBids;
    }

    public ArrayList<BookOrderDetails> getOrderDetailsAsks() {
        return orderDetailsAsks;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * The {@code BookOrderDetails} class is useful to obtain and format BookOrderDetails object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * **/

    public static class BookOrderDetails {

        private final double price;
        private final double quantity;

        public BookOrderDetails(double price, double quantity) {
            this.price = price;
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public double getQuantity() {
            return quantity;
        }

        /** Method to get order details value formatted in JSON
         * any params required
         * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
         * return JsonObject of {@link #price} and {@link #quantity}
         * **/
        public JSONObject getOrderDetails(){
            HashMap<String,Double> values = new HashMap<>();
            values.put("quantity",quantity);
            values.put("price",price);
            return new JSONObject(values);
        }

    }

}
