package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel;

import java.util.ArrayList;

/**
 *  The {@code OpenMarginOrders} class is useful to asseble Binance Margin Account Cancel all Open Orders on a Symbol request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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
