package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ComposedOrderDetails;

import java.util.ArrayList;

/**
 * The {@code OpenOrders} class is useful to format a OpenOrders object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenOrders {

    private final ArrayList<CancelSpotOrder> cancelSingleOrders;
    private final ArrayList<ComposedOrderDetails> cancelOrderComposeds;

    public OpenOrders(ArrayList<CancelSpotOrder> singleOrders, ArrayList<ComposedOrderDetails> cancelOrderComposeds) {
        this.cancelSingleOrders = singleOrders;
        this.cancelOrderComposeds = cancelOrderComposeds;
    }

    public ArrayList<CancelSpotOrder> getCancelSingleOrders() {
        return cancelSingleOrders;
    }

    public CancelSpotOrder getCancelSingleOrder(int index){
        return cancelSingleOrders.get(index);
    }

    public ArrayList<ComposedOrderDetails> getCancelOrderComposeds() {
        return cancelOrderComposeds;
    }

    public ComposedOrderDetails getComposedCancelOrder(int index){
        return cancelOrderComposeds.get(index);
    }

}
