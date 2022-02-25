package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

import java.util.ArrayList;

public class FullOrder extends ResultOrder{

    private final ArrayList<Fill> fills;

    public FullOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime, double price,
                     double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                     String type, String side, ArrayList<Fill> fills) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.fills = fills;
    }

    public ArrayList<Fill> getFills() {
        return fills;
    }

    public static class Fill {
            
    }

}

