package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import java.util.ArrayList;

/**
 * The {@code OpenSpotOrders} class is useful to format a OpenSpotOrders object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenSpotOrders {

    private final ArrayList<DetailSpotOrder> detailSpotOrders;
    private final ArrayList<ComposedSpotOrderDetails> composedSpotOrderDetails;

    public OpenSpotOrders(ArrayList<DetailSpotOrder> singleOrders, ArrayList<ComposedSpotOrderDetails> cancelOrderComposeds) {
        this.detailSpotOrders = singleOrders;
        this.composedSpotOrderDetails = cancelOrderComposeds;
    }

    public ArrayList<DetailSpotOrder> getDetailSpotOrdersList() {
        return detailSpotOrders;
    }

    public DetailSpotOrder getDetailsSpotOrder(int index){
        return detailSpotOrders.get(index);
    }

    public ArrayList<ComposedSpotOrderDetails> getComposedSpotOrderDetailsList() {
        return composedSpotOrderDetails;
    }

    public ComposedSpotOrderDetails getComposedSpotOrderDetails(int index){
        return composedSpotOrderDetails.get(index);
    }

}
