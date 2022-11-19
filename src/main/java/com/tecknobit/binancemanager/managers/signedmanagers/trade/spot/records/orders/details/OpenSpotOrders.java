package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details.OpenMarginOrders;

import java.util.ArrayList;

/**
 * The {@code OpenSpotOrders} class is useful to format a OpenSpotOrders object
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenSpotOrders {

    /**
     * {@code detailSpotOrdersList} is instance that memorizes spot orders data list
     * **/
    private ArrayList<DetailSpotOrder> detailSpotOrdersList;

    /**
     * {@code composedSpotOrderDetailsList} is instance that memorizes composed spot orders data list
     * **/
    private ArrayList<ComposedSpotOrderDetails> composedSpotOrderDetailsList;

    /** Constructor to init {@link OpenMarginOrders} object
     * @param singleOrders: spot orders data list
     * @param cancelOrderComposeds: composed spot orders data list
     * **/
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
        return detailSpotOrdersList.get(index);
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
        return composedSpotOrderDetailsList.get(index);
    }

    @Override
    public String toString() {
        return "OpenSpotOrders{" +
                "detailSpotOrdersList=" + detailSpotOrdersList +
                ", composedSpotOrderDetailsList=" + composedSpotOrderDetailsList +
                '}';
    }

}
