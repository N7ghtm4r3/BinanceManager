package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Details;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response.FullSpotOrder;
import org.json.JSONObject;


// TODO: 26/08/2022 WORK ON

public class SpotOrderCAS {

    private final String cancelResult;
    private final String newOrderResult;
    private final DetailSpotOrder orderCanceled;
    private final FullSpotOrder newOrder;

    public SpotOrderCAS(String cancelResult, String newOrderResult, DetailSpotOrder orderCanceled, FullSpotOrder newOrder) {
        this.cancelResult = cancelResult;
        this.newOrderResult = newOrderResult;
        this.orderCanceled = orderCanceled;
        this.newOrder = newOrder;
    }

    public SpotOrderCAS(JSONObject casOrder) {
        JsonHelper hSpotOrder = new JsonHelper(casOrder);
        cancelResult = hSpotOrder.getString("cancelResult");
        newOrderResult = hSpotOrder.getString("newOrderResult");
        orderCanceled = new DetailSpotOrder(hSpotOrder.getJSONObject("cancelResponse", new JSONObject()));
        newOrder = new FullSpotOrder(hSpotOrder.getJSONObject("newOrderResponse", new JSONObject()));
    }

    public String getCancelResult() {
        return cancelResult;
    }

    public String getNewOrderResult() {
        return newOrderResult;
    }

    public DetailSpotOrder getOrderCanceled() {
        return orderCanceled;
    }

    public FullSpotOrder getNewOrder() {
        return newOrder;
    }

    @Override
    public String toString() {
        return "SpotOrderCAS{" +
                "cancelResult='" + cancelResult + '\'' +
                ", newOrderResult='" + newOrderResult + '\'' +
                ", orderCanceled=" + orderCanceled +
                ", newOrder=" + newOrder +
                '}';
    }

}
