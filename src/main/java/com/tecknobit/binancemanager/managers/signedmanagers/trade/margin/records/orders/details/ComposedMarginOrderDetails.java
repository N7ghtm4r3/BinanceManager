package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * The {@code ComposedMarginOrderDetails} class is useful to format {@code "Binance"} Margin Account Cancel all Open Orders on a Symbol request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
 **/

public class ComposedMarginOrderDetails extends OrderDetails {

    /**
     * {@code detailMarginOrdersList} is instance that memorizes details margin orders list
     * **/
    private ArrayList<DetailMarginOrder> detailMarginOrdersList;

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /**
     * Constructor to init {@link ComposedMarginOrderDetails} object
     *
     * @param orderListId:            list order identifier
     * @param contingencyType:        contingency type of the order
     * @param listStatusType:         list status type of the order
     * @param listOrderStatus:        list order status
     * @param listClientOrderId:      list client order id
     * @param transactionTime:        transaction time of the order
     * @param symbol:                 symbol used in the order
     * @param detailMarginOrdersList: list of {@link DetailMarginOrder}
     * @param isIsolated:             is isolated
     **/
    public ComposedMarginOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol, ArrayList<OrderValues> orderValues,
                                      ArrayList<DetailMarginOrder> detailMarginOrdersList, boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, orderValues);
        this.detailMarginOrdersList = detailMarginOrdersList;
        this.isIsolated = isIsolated;
    }

    /**
     * Constructor to init {@link ComposedMarginOrderDetails} object
     *
     * @param marginOrderDetails: margin order details as {@link JSONObject}
     **/
    public ComposedMarginOrderDetails(JSONObject marginOrderDetails) {
        super(marginOrderDetails);
        loadOrderReport(marginOrderDetails.getJSONArray("orderReports"));
        isIsolated = marginOrderDetails.getBoolean("isIsolated");
    }

    /**
     * Method to load OrderReport list
     *
     * @param orderReports: obtained from {@code "Binance"}'s request
     **/
    private void loadOrderReport(JSONArray orderReports) {
        detailMarginOrdersList = new ArrayList<>();
        for (int j = 0; j < orderReports.length(); j++)
            detailMarginOrdersList.add(new DetailMarginOrder(orderReports.getJSONObject(j)));
    }

    public ArrayList<DetailMarginOrder> getCancelMarginOrdersList() {
        return detailMarginOrdersList;
    }

    public void setDetailMarginOrdersList(ArrayList<DetailMarginOrder> detailMarginOrdersList) {
        this.detailMarginOrdersList = detailMarginOrdersList;
    }

    public void insertDetailMarginOrder(DetailMarginOrder detailMarginOrder){
        if(!detailMarginOrdersList.contains(detailMarginOrder))
            detailMarginOrdersList.add(detailMarginOrder);
    }

    public boolean removeDetailMarginOrder(DetailMarginOrder detailMarginOrder){
        return detailMarginOrdersList.remove(detailMarginOrder);
    }

    public DetailMarginOrder getCancelMarginOrder(int index){
        return detailMarginOrdersList.get(index);
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    @Override
    public String toString() {
        return "ComposedMarginOrderDetails{" +
                "detailMarginOrdersList=" + detailMarginOrdersList +
                ", isIsolated=" + isIsolated +
                ", orderListId=" + orderListId +
                ", contingencyType='" + contingencyType + '\'' +
                ", listStatusType='" + listStatusType + '\'' +
                ", listOrderStatus='" + listOrderStatus + '\'' +
                ", listClientOrderId='" + listClientOrderId + '\'' +
                ", transactionTime=" + transactionTime +
                ", symbol='" + symbol + '\'' +
                ", orderValues=" + orderValues +
                '}';
    }

}
