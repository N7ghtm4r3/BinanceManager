package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.SpotOrder;

/**
 *  The {@code OrderStatus} class is useful to format an OrderStatus object
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class OrderSpotStatus extends SpotOrder {

    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;
    private final double stopPrice;
    private final double icebergQty;
    private final long time;
    private final long updateTime;
    private final boolean isWorking;
    private final double origQuoteOrderQty;

    public OrderSpotStatus(String symbol, long orderId, long orderListId, String clientOrderId, double price, double origQty,
                           double executedQty, double cummulativeQuoteQty, String status, String timeInForce, String type,
                           String side, double stopPrice, double icebergQty, long time, long updateTime, boolean isWorking,
                           double origQuoteOrderQty) {
        super(symbol, orderId, orderListId, clientOrderId);
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
        this.stopPrice = stopPrice;
        this.icebergQty = icebergQty;
        this.time = time;
        this.updateTime = updateTime;
        this.isWorking = isWorking;
        this.origQuoteOrderQty = origQuoteOrderQty;
    }

    public double getPrice() {
        return price;
    }

    public double getOrigQty() {
        return origQty;
    }

    public double getExecutedQty() {
        return executedQty;
    }

    public double getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public String getType() {
        return type;
    }

    public String getSide() {
        return side;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public double getIcebergQty() {
        return icebergQty;
    }

    public long getTime() {
        return time;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double getOrigQuoteOrderQty() {
        return origQuoteOrderQty;
    }

}
