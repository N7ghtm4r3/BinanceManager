package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import java.util.ArrayList;

/**
 *  The {@code OpenMarginOrders} class is useful to asseble Binance Margin Account Cancel all Open Orders on a Symbol request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenMarginOrders {

    private final ArrayList<DetailMarginOrder> detailMarginOrders;
    private final ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails;

    public OpenMarginOrders(ArrayList<DetailMarginOrder> detailMarginOrders,
                            ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails) {
        this.detailMarginOrders = detailMarginOrders;
        this.composedMarginOrderDetails = composedMarginOrderDetails;
    }

    public ArrayList<DetailMarginOrder> getCancelMarginOrdersList() {
        return detailMarginOrders;
    }

    public DetailMarginOrder getCancelMarginOrder(int index){
        return detailMarginOrders.get(index);
    }

    public ArrayList<ComposedMarginOrderDetails> getComposedMarginOrderDetails() {
        return composedMarginOrderDetails;
    }

    public ComposedMarginOrderDetails getComposedMarginOrderDetails(int index){
        return composedMarginOrderDetails.get(index);
    }

}
