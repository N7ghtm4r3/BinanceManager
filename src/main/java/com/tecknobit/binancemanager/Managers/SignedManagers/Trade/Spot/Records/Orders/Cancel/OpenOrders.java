package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import java.util.ArrayList;

public class OpenOrders {

    private final ArrayList<CancelOrder> cancelSingleOrders;
    private final ArrayList<CancelOrderComposed> cancelOrderComposeds;

    public OpenOrders(ArrayList<CancelOrder> singleOrders, ArrayList<CancelOrderComposed> cancelOrderComposeds) {
        this.cancelSingleOrders = singleOrders;
        this.cancelOrderComposeds = cancelOrderComposeds;
    }

    public ArrayList<CancelOrder> getCancelSingleOrders() {
        return cancelSingleOrders;
    }

    public CancelOrder getCancelSingleOrder(int index){
        return cancelSingleOrders.get(index);
    }

    public ArrayList<CancelOrderComposed> getCancelOrderComposeds() {
        return cancelOrderComposeds;
    }

    public CancelOrderComposed getComposedCancelOrder(int index){
        return cancelOrderComposeds.get(index);
    }

}
