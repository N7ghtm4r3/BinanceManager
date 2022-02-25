package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders;

public class OpenOrder extends CancelOrder{

    public OpenOrder(String symbol, String origClientOrderId, long orderId, long orderListId, String clientOrderId,
                     double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                     String timeInForce, String type, String side) {
        super(symbol, origClientOrderId, orderId, orderListId, clientOrderId, price, origQty, executedQty,
                cummulativeQuoteQty, status, timeInForce, type, side);
    }

}
