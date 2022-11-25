package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ComposedSpotOrderDetails} class is useful to format a {@code "Binance"}'s composed spot order details object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
 * Cancel all Open Orders on a Symbol (TRADE)</a>
 * @see OrderDetails
 **/
public class ComposedSpotOrderDetails extends OrderDetails {

    /**
     * {@code orderReportsList} is instance that memorizes order reports list
     **/
    private ArrayList<SpotOrderDetails> orderReportsList;

    /**
     * Constructor to init {@link ComposedSpotOrderDetails} object
     *
     * @param orderListId:       list order identifier
     * @param contingencyType:   contingency type of the order
     * @param listStatusType:    list status type of the order
     * @param listOrderStatus:   list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime:   transaction time of the order
     * @param symbol:            symbol used in the order
     * @param orders:            list of {@link Order}
     * @param orderReportsList:  list of {@link SpotOrderDetails}
     **/
    public ComposedSpotOrderDetails(long orderListId, String contingencyType, Status listStatusType, Status listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, ArrayList<Order> orders,
                                    ArrayList<SpotOrderDetails> orderReportsList) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                orders);
        this.orderReportsList = orderReportsList;
    }

    /**
     * Constructor to init {@link ComposedSpotOrderDetails} object
     *
     * @param composedSpotOrder: composed spot order details as {@link JSONObject}
     **/
    public ComposedSpotOrderDetails(JSONObject composedSpotOrder) {
        super(composedSpotOrder);
        orderReportsList = new ArrayList<>();
        JSONArray jOrders = hOrder.getJSONArray("orderReportsList", new JSONArray());
        for (int j = 0; j < jOrders.length(); j++)
            orderReportsList.add(new SpotOrderDetails(jOrders.getJSONObject(j)));
    }

    /**
     * Method to get {@link #orderReportsList} instance <br>
     * Any params required
     *
     * @return {@link #orderReportsList} instance as {@link ArrayList} of {@link SpotOrderDetails}
     **/
    public ArrayList<SpotOrderDetails> getOrderReportsList() {
        return orderReportsList;
    }

    /**
     * Method to set {@link #orderReportsList} instance <br>
     *
     * @param orderReportsList: list of {@link SpotOrderDetails} to set
     **/
    public void setOrderReportsList(ArrayList<SpotOrderDetails> orderReportsList) {
        this.orderReportsList = orderReportsList;
    }

    /**
     * Method to add an order report  to {@link #orderReportsList}
     *
     * @param orderReport: order report to add
     **/
    public void insertOrderReport(SpotOrderDetails orderReport) {
        if (!orderReportsList.contains(orderReport))
            orderReportsList.add(orderReport);
    }

    /**
     * Method to remove an order report  from {@link #orderReportsList}
     *
     * @param orderReport: order report  to remove
     * @return result of operation as boolean
     **/
    public boolean removeOrderReport(SpotOrderDetails orderReport) {
        return orderReportsList.remove(orderReport);
    }

    /**
     * Method to get a details of the spot order from {@link #orderReportsList} list
     *
     * @param index: index to fetch the details of the spot order
     * @return permission as {@link SpotOrderDetails}
     **/
    public SpotOrderDetails getOrderReport(int index) {
        return orderReportsList.get(index);
    }

}
