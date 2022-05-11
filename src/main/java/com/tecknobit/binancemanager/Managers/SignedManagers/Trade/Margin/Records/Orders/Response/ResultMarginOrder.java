package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response;

/**
 * The {@code ResultMarginOrder} class is useful to format ResultMarginOrder object of Binance's request Margin Account New Order
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * **/

public class ResultMarginOrder extends ACKMarginOrder{

    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;

    public ResultMarginOrder(String symbol, double orderId, String clientOrderId, long transactTime, boolean isIsolated,
                             double price, double origQty, double executedQty, double cummulativeQuoteQty, String status,
                             String timeInForce, String type, String side) {
        super(symbol, orderId, clientOrderId, transactTime, isIsolated);
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
