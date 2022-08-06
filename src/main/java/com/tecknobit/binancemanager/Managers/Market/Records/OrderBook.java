package com.tecknobit.binancemanager.Managers.Market.Records;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code OrderBook} class is useful to format Binance OrderBook request
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
 * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OrderBook {

    /**
     * {@code lastUpdateId} is instance that contains last update of order book
     * **/
    private final long lastUpdateId;

    /**
     * {@code orderDetailsBids} is instance that contains bids of the order book
     * **/
    private final ArrayList<BookOrderDetails> orderDetailsBids;

    /**
     * {@code orderDetailsBids} is instance that contains asks of the order book
     * **/
    private final ArrayList<BookOrderDetails> orderDetailsAsks;

    /**
     * {@code symbol} is instance that contains symbol of the order book
     * **/
    private final String symbol;

    /** Constructor to init {@link OrderBook} object
     * @param lastUpdateId: last update of order book
     * @param jsonOrderBook: values of bids and asks in JSON format
     * @param symbol: symbol of the order book
     * **/
    public OrderBook(long lastUpdateId, JSONObject jsonOrderBook, String symbol) {
        this.lastUpdateId = lastUpdateId;
        orderDetailsAsks = loadOrderList(jsonOrderBook.getJSONArray("bids"));
        orderDetailsBids = loadOrderList(jsonOrderBook.getJSONArray("asks"));
        this.symbol = symbol;
    }

    /** Method to get load list of order details
     * @param jsonList: obtained from Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * @return order details list as ArrayList<BookOrderDetails> object
     * **/
    private ArrayList<BookOrderDetails> loadOrderList(JSONArray jsonList){
        ArrayList<BookOrderDetails> orderDetails = new ArrayList<>();
        for (int j = 0; j < jsonList.length(); j++){
            JSONArray details = jsonList.getJSONArray(j);
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

    @Override
    public String toString() {
        return "OrderBook{" +
                "lastUpdateId=" + lastUpdateId +
                ", orderDetailsBids=" + orderDetailsBids +
                ", orderDetailsAsks=" + orderDetailsAsks +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    /**
     * The {@code BookOrderDetails} class is useful to format order book details
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     * **/

    public static class BookOrderDetails {

        /**
         * {@code price} is instance that contains price in the order book
         * **/
        private final double price;

        /**
         * {@code quantity} is instance that contains quantity in the order book
         * **/
        private final double quantity;

        /** Constructor to init {@link BookOrderDetails} object
         * @param price: price in the order book
         * @param quantity: quantity in the order book
         * **/
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
         * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
         * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
         * @return JSONObject of {@link #price} and {@link #quantity}
         * **/
        public JSONObject getOrderDetails(){
            HashMap<String,Double> values = new HashMap<>();
            values.put("quantity", quantity);
            values.put("price", price);
            return new JSONObject(values);
        }

        @Override
        public String toString() {
            return "BookOrderDetails{" +
                    "price=" + price +
                    ", quantity=" + quantity +
                    '}';
        }

    }

}
