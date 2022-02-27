package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

/**
 *  The {@code FullOrder} class is useful to format all Order Binance request in ResultOrder format
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class ResultOrder extends ACKOrder {

    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;

    public ResultOrder(String symbol, long orderId, long orderListId, String clientOrderId,
                       long transactTime, double price, double origQty, double executedQty, double cummulativeQuoteQty,
                       String status, String timeInForce, String type, String side) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime);
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
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

}
