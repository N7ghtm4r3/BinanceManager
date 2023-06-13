package com.tecknobit.binancemanager.managers.market.records;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code OrderBook} class is useful to format {@code "Binance"} OrderBook request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-book">
 * Order Book</a>
 */
public class OrderBook {

    /**
     * {@code lastUpdateId} is instance that contains last update of order book
     */
    private final long lastUpdateId;

    /**
     * {@code orderDetailsBids} is instance that contains bids of the order book
     */
    private final ArrayList<BookOrderDetails> orderDetailsBids;

    /**
     * {@code orderDetailsBids} is instance that contains asks of the order book
     */
    private final ArrayList<BookOrderDetails> orderDetailsAsks;

    /**
     * {@code symbol} is instance that contains symbol of the order book
     */
    private final String symbol;

    /**
     * Constructor to init {@link OrderBook} object
     *
     * @param lastUpdateId:     last update of order book
     * @param orderDetailsBids: list of bids of the order book
     * @param orderDetailsAsks: list of asks of the order book
     * @param symbol:           symbol of the order book
     */
    public OrderBook(long lastUpdateId, ArrayList<BookOrderDetails> orderDetailsBids,
                     ArrayList<BookOrderDetails> orderDetailsAsks, String symbol) {
        this.lastUpdateId = lastUpdateId;
        this.orderDetailsBids = orderDetailsBids;
        this.orderDetailsAsks = orderDetailsAsks;
        this.symbol = symbol;
    }

    /**
     * Constructor to init {@link OrderBook} object
     *
     * @param orderBook: order book details as {@link JSONObject}
     */
    public OrderBook(JSONObject orderBook) {
        JsonHelper hBook = new JsonHelper(orderBook);
        symbol = hBook.getString("symbol");
        lastUpdateId = hBook.getLong("lastUpdateId", 0);
        orderDetailsAsks = returnOrdersList(hBook.getJSONArray("bids"));
        orderDetailsBids = returnOrdersList(hBook.getJSONArray("asks"));
    }

    /**
     * Method to get load list of order details
     *
     * @param jsonList: obtained from {@code "Binance"} request
     * @return order details list as {@link ArrayList} of {@link BookOrderDetails}
     */
    @Returner
    public static ArrayList<BookOrderDetails> returnOrdersList(JSONArray jsonList) {
        ArrayList<BookOrderDetails> orderDetails = new ArrayList<>();
        if (jsonList != null)
            for (int j = 0; j < jsonList.length(); j++)
                orderDetails.add(new BookOrderDetails(jsonList.getJSONArray(j)));
        return orderDetails;
    }

    /**
     * Method to get {@link #lastUpdateId} instance <br>
     * No-any params required
     *
     * @return {@link #lastUpdateId} instance as long
     */
    public long getLastUpdateId() {
        return lastUpdateId;
    }

    /**
     * Method to get {@link #orderDetailsBids} instance <br>
     * No-any params required
     *
     * @return {@link #orderDetailsBids} instance as {@link ArrayList} of {@link BookOrderDetails}
     */
    public ArrayList<BookOrderDetails> getOrderDetailsBids() {
        return orderDetailsBids;
    }

    /**
     * Method to get {@link #orderDetailsAsks} instance <br>
     * No-any params required
     *
     * @return {@link #orderDetailsAsks} instance as {@link ArrayList} of {@link BookOrderDetails}
     */
    public ArrayList<BookOrderDetails> getOrderDetailsAsks() {
        return orderDetailsAsks;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code BookOrderDetails} class is useful to format order book details
     */
    public static class BookOrderDetails {

        /**
         * {@code price} is instance that contains price in the order book
         */
        private final double price;

        /**
         * {@code quantity} is instance that contains quantity in the order book
         */
        private final double quantity;

        /**
         * Constructor to init {@link BookOrderDetails} object
         *
         * @param price:    price in the order book
         * @param quantity: quantity in the order book
         */
        public BookOrderDetails(double price, double quantity) {
            this.price = price;
            this.quantity = quantity;
        }

        /**
         * Constructor to init {@link BookOrderDetails} object
         *
         * @param jBookOrderDetails: book order details as {@link JSONArray}
         */
        public BookOrderDetails(JSONArray jBookOrderDetails) {
            price = jBookOrderDetails.getDouble(0);
            quantity = jBookOrderDetails.getDouble(1);
        }

        /**
         * Method to get {@link #price} instance <br>
         * No-any params required
         *
         * @return {@link #price} instance as double
         */
        public double getPrice() {
            return price;
        }

        /**
         * Method to get {@link #price} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #price} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPrice(int decimals) {
            return roundValue(price, decimals);
        }

        /**
         * Method to get {@link #quantity} instance <br>
         * No-any params required
         *
         * @return {@link #quantity} instance as double
         */
        public double getQuantity() {
            return quantity;
        }

        /**
         * Method to get {@link #quantity} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #quantity} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getQuantity(int decimals) {
            return roundValue(quantity, decimals);
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
