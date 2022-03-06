package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

public class MarginOrderStatus extends ResultMarginOrder {

    private final double icebergQty;
    private final boolean isWorking;
    private final double stopPrice;
    private final long time;

    public MarginOrderStatus(String symbol, double orderId, String clientOrderId, long updateTime, boolean isIsolated,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                             String timeInForce, String type, String side, double icebergQty, boolean isWorking, double stopPrice,
                             long time) {
        super(symbol, orderId, clientOrderId, updateTime, isIsolated, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.icebergQty = icebergQty;
        this.isWorking = isWorking;
        this.stopPrice = stopPrice;
        this.time = time;
    }

    public double getIcebergQty() {
        return icebergQty;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public long getTime() {
        return time;
    }

    public long getUpdateTime(){
        return super.getTransactTime();
    }

}
