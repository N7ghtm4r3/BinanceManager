package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel;

import java.util.ArrayList;

public class OpenMarginOrders {

    private final ArrayList<CancelMarginOrder> cancelMarginOrders;
    private final ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails;

    public OpenMarginOrders(ArrayList<CancelMarginOrder> cancelMarginOrders,
                            ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails) {
        this.cancelMarginOrders = cancelMarginOrders;
        this.composedMarginOrderDetails = composedMarginOrderDetails;
    }

    public ArrayList<CancelMarginOrder> getCancelMarginOrdersList() {
        return cancelMarginOrders;
    }

    public CancelMarginOrder getCancelMarginOrder(int index){
        return cancelMarginOrders.get(index);
    }

    public ArrayList<ComposedMarginOrderDetails> getComposedMarginOrderDetails() {
        return composedMarginOrderDetails;
    }

    public ComposedMarginOrderDetails getComposedMarginOrderDetails(int index){
        return composedMarginOrderDetails.get(index);
    }

}
