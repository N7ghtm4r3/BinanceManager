package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import java.util.ArrayList;

/**
 * The {@code OpenSpotOrders} class is useful to format a OpenSpotOrders object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenSpotOrders {

    private ArrayList<DetailSpotOrder> detailSpotOrdersList;
    private ArrayList<ComposedSpotOrderDetails> composedSpotOrderDetailsList;

    public OpenSpotOrders(ArrayList<DetailSpotOrder> singleOrders, ArrayList<ComposedSpotOrderDetails> cancelOrderComposeds) {
        this.detailSpotOrdersList = singleOrders;
        this.composedSpotOrderDetailsList = cancelOrderComposeds;
    }

    public ArrayList<DetailSpotOrder> getDetailSpotOrdersList() {
        return detailSpotOrdersList;
    }

    public void setDetailSpotOrdersList(ArrayList<DetailSpotOrder> detailSpotOrdersList) {
        this.detailSpotOrdersList = detailSpotOrdersList;
    }

    public void insertDetailSpotOrder(DetailSpotOrder detailSpotOrder){
        if(!detailSpotOrdersList.contains(detailSpotOrder))
            detailSpotOrdersList.add(detailSpotOrder);
    }

    public boolean removeDetailSpotOrder(DetailSpotOrder detailSpotOrder){
        return detailSpotOrdersList.remove(detailSpotOrder);
    }

    public DetailSpotOrder getDetailsSpotOrder(int index){
        try{
            return detailSpotOrdersList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

    public ArrayList<ComposedSpotOrderDetails> getComposedSpotOrderDetailsList() {
        return composedSpotOrderDetailsList;
    }

    public void setComposedSpotOrderDetailsList(ArrayList<ComposedSpotOrderDetails> composedSpotOrderDetailsList) {
        this.composedSpotOrderDetailsList = composedSpotOrderDetailsList;
    }

    public void insertComposedSpotOrderDetail(ComposedSpotOrderDetails composedSpotOrderDetails){
        if(!composedSpotOrderDetailsList.contains(composedSpotOrderDetails))
            composedSpotOrderDetailsList.add(composedSpotOrderDetails);
    }

    public boolean removeComposedSpotOrderDetail(ComposedSpotOrderDetails composedSpotOrderDetails){
        return composedSpotOrderDetailsList.remove(composedSpotOrderDetails);
    }

    public ComposedSpotOrderDetails getComposedSpotOrderDetails(int index){
        try{
            return composedSpotOrderDetailsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

}
