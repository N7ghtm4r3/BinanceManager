package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ComposedSpotOrderDetails} class is useful to format a ComposedSpotOrderDetails object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedSpotOrderDetails extends OrderDetails {

    private ArrayList<CancelSpotOrder> cancelSpotOrders;

    public ComposedSpotOrderDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        loadCancelOrders(jsonObject.getJSONArray("orderReports"));
    }

    /** Method to load CancelSpotOrder list
     * @param #list: obtained from Binance's request
     * return an ArrayList<CancelSpotOrder> with response data
     * **/
    private void loadCancelOrders(JSONArray list){
        cancelSpotOrders = new ArrayList<>();
        for (int j=0; j < list.length(); j++)
            cancelSpotOrders.add(CancelSpotOrder.assembleCancelOrderObject(list.getJSONObject(j)));
    }

    public ArrayList<CancelSpotOrder> getCancelSpotOrders() {
        return cancelSpotOrders;
    }

    public CancelSpotOrder getCancelOrder(int index){
        return cancelSpotOrders.get(index);
    }

}
