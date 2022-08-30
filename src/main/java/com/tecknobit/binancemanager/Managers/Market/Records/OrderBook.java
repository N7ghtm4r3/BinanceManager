package com.tecknobit.binancemanager.Managers.Market.Records;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code OrderBook} class is useful to format Binance OrderBook request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
 * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
 **/

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

    /**
     * Constructor to init {@link OrderBook} object
     *
     * @param lastUpdateId:     last update of order book
     * @param orderDetailsBids: list of bids of the order book
     * @param orderDetailsAsks: list of asks of the order book
     * @param symbol:           symbol of the order book
     **/
    public OrderBook(long lastUpdateId, ArrayList<BookOrderDetails> orderDetailsBids, ArrayList<BookOrderDetails> orderDetailsAsks,
                     String symbol) {
        this.lastUpdateId = lastUpdateId;
        this.orderDetailsBids = orderDetailsBids;
        this.orderDetailsAsks = orderDetailsAsks;
        this.symbol = symbol;
    }

    /**
     * Constructor to init {@link OrderBook} object
     *
     * @param orderBook: order book details as {@link JSONObject}
     **/
    public OrderBook(JSONObject orderBook, String symbol) {
        this.symbol = symbol;
        lastUpdateId = orderBook.getLong("lastUpdateId");
        JsonHelper hBook = new JsonHelper(orderBook);
        orderDetailsAsks = loadOrderList(hBook.getJSONArray("bids", new JSONArray()));
        orderDetailsBids = loadOrderList(hBook.getJSONArray("asks", new JSONArray()));
    }

    /**
     * Method to get load list of order details
     *
     * @param jsonList: obtained from Binance request
     * @return order details list as ArrayList<BookOrderDetails> object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
     * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
     **/
    private ArrayList<BookOrderDetails> loadOrderList(JSONArray jsonList) {
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

        /**
         * Method to get {@link #price} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #price} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPrice(int decimals) {
            return roundValue(price, decimals);
        }

        public double getQuantity() {
            return quantity;
        }

        /**
         * Method to get {@link #quantity} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #quantity} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getQuantity(int decimals) {
            return roundValue(quantity, decimals);
        }


        /**
         * Method to get order details value formatted in JSON
         * Any params required
         *
         * @return JSONObject of {@link #price} and {@link #quantity}
         * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
         * https://binance-docs.github.io/apidocs/spot/en/#order-book</a>
         **/
        public JSONObject getOrderDetails() {
            HashMap<String, Double> values = new HashMap<>();
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
