package com.tecknobit.binancemanager.Managers.Market.Records.Book;

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
    private final ArrayList<OrderDetails> orderDetailsBids;
    private final ArrayList<OrderDetails> orderDetailsAsks;
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
     * return order details list as ArrayList<OrderDetails> object
     * **/
    private ArrayList<OrderDetails> loadOrderDetails(JSONArray jsonArray){
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONArray details = jsonArray.getJSONArray(j);
            orderDetails.add(new OrderDetails(details.getDouble(0),
                    details.getDouble(1)));
        }
        return orderDetails;
    }

    public long getLastUpdateId() {
        return lastUpdateId;
    }

    public ArrayList<OrderDetails> getOrderDetailsBids() {
        return orderDetailsBids;
    }

    public ArrayList<OrderDetails> getOrderDetailsAsks() {
        return orderDetailsAsks;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * The {@code OrderDetails} class is useful to obtain and format OrderDetails object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#order-book
     * **/

    public static class OrderDetails {

        private final double price;
        private final double quantity;

        public OrderDetails(double price, double quantity) {
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