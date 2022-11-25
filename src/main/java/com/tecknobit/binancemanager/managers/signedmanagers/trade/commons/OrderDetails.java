package com.tecknobit.binancemanager.managers.signedmanagers.trade.commons;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code OrderDetails} class is useful to format the details of an order
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
 *             Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
 *             Cancel all Open Orders on a Symbol (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
 *             Margin Account Cancel Order (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
 *             Margin Account New OCO (TRADE)</a>
 *     </li>
 * </ul>
 **/
public class OrderDetails {

    /**
     * {@code orderListId} is instance that memorizes list order identifier
     **/
    protected final long orderListId;

    /**
     * {@code contingencyType} is instance that memorizes contingency type of the order
     **/
    protected final String contingencyType;

    /**
     * {@code listStatusType} is instance that memorizes list status type of the order
     **/
    protected final Status listStatusType;

    /**
     * {@code listOrderStatus} is instance that memorizes list order status
     **/
    protected final Status listOrderStatus;

    /**
     * {@code listClientOrderId} is instance that list client order id
     **/
    protected final String listClientOrderId;

    /**
     * {@code transactionTime} is instance that memorizes transaction time of the order
     **/
    protected final long transactionTime;

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     **/
    protected final String symbol;

    /**
     * {@code hOrder} {@code "JSON"} helper
     **/
    protected final JsonHelper hOrder;

    /**
     * {@code orders} is instance that memorizes order values
     **/
    protected ArrayList<Order> orders;

    /**
     * Constructor to init {@link OrderDetails} object
     *
     * @param orderListId:       list order identifier
     * @param contingencyType:   contingency type of the order
     * @param listStatusType:    list status type of the order
     * @param listOrderStatus:   list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime:   transaction time of the order
     * @param symbol:            symbol used in the order
     * @param orders:            list of {@link Order}
     **/
    public OrderDetails(long orderListId, String contingencyType, Status listStatusType, Status listOrderStatus,
                        String listClientOrderId, long transactionTime, String symbol,
                        ArrayList<Order> orders) {
        this.orderListId = orderListId;
        this.contingencyType = contingencyType;
        this.listStatusType = listStatusType;
        this.listOrderStatus = listOrderStatus;
        this.listClientOrderId = listClientOrderId;
        this.transactionTime = transactionTime;
        this.symbol = symbol;
        this.orders = orders;
        hOrder = null;
    }

    /**
     * Constructor to init {@link OrderDetails} object
     *
     * @param orderDetails: order details as {@link JSONObject}
     **/
    public OrderDetails(JSONObject orderDetails) {
        hOrder = new JsonHelper(orderDetails);
        orderListId = hOrder.getLong("orderListId", 0);
        contingencyType = hOrder.getString("contingencyType");
        listStatusType = Status.valueOf(hOrder.getString("listStatusType", Status.CONFIRMED.name()));
        listOrderStatus = Status.valueOf(hOrder.getString("listOrderStatus", Status.CONFIRMED.name()));
        listClientOrderId = hOrder.getString("listClientOrderId");
        transactionTime = hOrder.getLong("transactionTime", 0);
        symbol = hOrder.getString("symbol");
        orders = new ArrayList<>();
        JSONArray jOrders = hOrder.getJSONArray("orders", new JSONArray());
        for (int j = 0; j < jOrders.length(); j++)
            orders.add(new Order(jOrders.getJSONObject(j)));
    }

    /**
     * Method to get {@link #orderListId} instance <br>
     * Any params required
     *
     * @return {@link #orderListId} instance as long
     **/
    public long getOrderListId() {
        return orderListId;
    }

    /**
     * Method to get {@link #contingencyType} instance <br>
     * Any params required
     *
     * @return {@link #contingencyType} instance as {@link String}
     **/
    public String getContingencyType() {
        return contingencyType;
    }

    /**
     * Method to get {@link #listStatusType} instance <br>
     * Any params required
     *
     * @return {@link #listStatusType} instance as {@link Status}
     **/
    public Status getListStatusType() {
        return listStatusType;
    }

    /**
     * Method to get {@link #listOrderStatus} instance <br>
     * Any params required
     *
     * @return {@link #listOrderStatus} instance as {@link Status}
     **/
    public Status getListOrderStatus() {
        return listOrderStatus;
    }

    /**
     * Method to get {@link #listClientOrderId} instance <br>
     * Any params required
     *
     * @return {@link #listClientOrderId} instance as {@link String}
     **/
    public String getListClientOrderId() {
        return listClientOrderId;
    }

    /**
     * Method to get {@link #transactionTime} instance <br>
     * Any params required
     *
     * @return {@link #transactionTime} instance as long
     **/
    public long getTransactionTime() {
        return transactionTime;
    }

    /**
     * Method to get {@link #transactionTime} instance <br>
     * Any params required
     *
     * @return {@link #transactionTime} instance as {@link Date}
     **/
    public Date getTransactionDate() {
        return TimeFormatter.getDate(transactionTime);
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * Any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #orders} instance <br>
     * Any params required
     *
     * @return {@link #orders} instance as {@link ArrayList} of {@link Order}
     **/
    public ArrayList<Order> getOrdersList() {
        return orders;
    }

    /**
     * Method to add a {@link Order} to {@link #orders}
     *
     * @param order: order to add
     **/
    public void insertOrder(Order order) {
        if (!orders.contains(order))
            orders.add(order);
    }

    /**
     * Method to remove an {@link Order} from {@link #orders}
     *
     * @param order: order to remove
     * @return result of operation as boolean
     **/
    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    /**
     * Method to get an order from {@link #orders} list
     *
     * @param index: index to fetch the order
     * @return order as {@link Order}
     **/
    public Order getOrder(int index) {
        return orders.get(index);
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
