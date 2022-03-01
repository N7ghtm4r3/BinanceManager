package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import java.util.ArrayList;

/**
 * The {@code OpenSpotOrders} class is useful to format a OpenSpotOrders object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenSpotOrders {

    private final ArrayList<CancelSpotOrder> cancelSingleOrders;
    private final ArrayList<ComposedSpotOrderDetails> cancelOrderComposeds;

    public OpenSpotOrders(ArrayList<CancelSpotOrder> singleOrders, ArrayList<ComposedSpotOrderDetails> cancelOrderComposeds) {
        this.cancelSingleOrders = singleOrders;
        this.cancelOrderComposeds = cancelOrderComposeds;
    }

    public ArrayList<CancelSpotOrder> getCancelSingleOrders() {
        return cancelSingleOrders;
    }

    public CancelSpotOrder getCancelSingleOrder(int index){
        return cancelSingleOrders.get(index);
    }

    public ArrayList<ComposedSpotOrderDetails> getCancelOrderComposeds() {
        return cancelOrderComposeds;
    }

    public ComposedSpotOrderDetails getComposedCancelOrder(int index){
        return cancelOrderComposeds.get(index);
    }

}
