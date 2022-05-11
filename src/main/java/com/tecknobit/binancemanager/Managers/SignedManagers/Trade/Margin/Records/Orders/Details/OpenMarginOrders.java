package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import java.util.ArrayList;

/**
 *  The {@code OpenMarginOrders} class is useful to asseble Binance Margin Account Cancel all Open Orders on a Symbol request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenMarginOrders {

    private ArrayList<DetailMarginOrder> detailMarginOrdersList;
    private ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetailsList;

    public OpenMarginOrders(ArrayList<DetailMarginOrder> detailMarginOrders,
                            ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails) {
        this.detailMarginOrdersList = detailMarginOrders;
        this.composedMarginOrderDetailsList = composedMarginOrderDetails;
    }

    public ArrayList<DetailMarginOrder> getCancelMarginOrdersList() {
        return detailMarginOrdersList;
    }

    public void setCancelMarginOrdersList(ArrayList<DetailMarginOrder> detailMarginOrdersList) {
        this.detailMarginOrdersList = detailMarginOrdersList;
    }

    public void insertCancelMarginOrder(DetailMarginOrder detailMarginOrder){
        if(!detailMarginOrdersList.contains(detailMarginOrder))
            detailMarginOrdersList.add(detailMarginOrder);
    }

    public boolean removeCancelMarginOrder(DetailMarginOrder detailMarginOrder){
        return detailMarginOrdersList.remove(detailMarginOrder);
    }

    public DetailMarginOrder getCancelMarginOrder(int index){
        try{
            return detailMarginOrdersList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

    public ArrayList<ComposedMarginOrderDetails> getComposedMarginOrderDetailsList() {
        return composedMarginOrderDetailsList;
    }

    public void setComposedMarginOrderDetailsList(ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetailsList) {
        this.composedMarginOrderDetailsList = composedMarginOrderDetailsList;
    }

    public void insertComposedMarginOrder(ComposedMarginOrderDetails composedMarginOrderDetail){
        if(!composedMarginOrderDetailsList.contains(composedMarginOrderDetail))
            composedMarginOrderDetailsList.add(composedMarginOrderDetail);
    }

    public boolean removeComposedMarginOrder(ComposedMarginOrderDetails composedMarginOrderDetail){
        return composedMarginOrderDetailsList.remove(composedMarginOrderDetail);
    }

    public ComposedMarginOrderDetails getComposedMarginOrderDetails(int index){
        try{
            return composedMarginOrderDetailsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

}
