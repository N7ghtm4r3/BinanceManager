package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details.DetailMarginOrder.*;

/**
 *  The {@code ComposedMarginOrderDetails} class is useful to format Binance Margin Account Cancel all Open Orders on a Symbol request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedMarginOrderDetails extends OrderDetails {

    /**
     * {@code detailMarginOrdersList} is instance that memorizes details margin orders list
     * **/
    private ArrayList<DetailMarginOrder> detailMarginOrdersList;

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /** Constructor to init {@link ComposedMarginOrderDetails} object
     * @param orderListId: list order identifier
     * @param contingencyType: contingency type of the order
     * @param listStatusType: list status type of the order
     * @param listOrderStatus: list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime: transaction time of the order
     * @param symbol: symbol used in the order
     * @param isIsolated: is isolated
     * @param jsonDetails: order details in JSON format
     * **/
    public ComposedMarginOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                      String listClientOrderId, long transactionTime, String symbol, boolean isIsolated,
                                      JSONObject jsonDetails) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                jsonDetails);
        this.isIsolated = isIsolated;
        loadOrderReport(jsonDetails.getJSONArray("orderReports"));
    }

    /** Method to load OrderReport list
     * @param orderReports: obtained from Binance's request
     * **/
    private void loadOrderReport(JSONArray orderReports) {
        detailMarginOrdersList = new ArrayList<>();
        for (int j = 0; j < orderReports.length(); j++)
            detailMarginOrdersList.add(assembleDetailMarginOrderObject(orderReports.getJSONObject(j)));
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

    /** Method to assemble a {@link ComposedMarginOrderDetails} object
     * @param order: obtained from Binance's request
     * @return composed margin order details as {@link ComposedMarginOrderDetails}
     * **/
    public static ComposedMarginOrderDetails assembleComposedMarginOrderDetails(JSONObject order){
        return new ComposedMarginOrderDetails(order.getLong("orderListId"),
                order.getString("contingencyType"),
                order.getString("listStatusType"),
                order.getString("listOrderStatus"),
                order.getString("listClientOrderId"),
                order.getLong("transactionTime"),
                order.getString("symbol"),
                order.getBoolean("isIsolated"),
                order
        );
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
