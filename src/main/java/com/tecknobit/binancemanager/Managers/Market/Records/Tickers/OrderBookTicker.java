package com.tecknobit.binancemanager.Managers.Market.Records.Tickers;

/**
 * The {@code OrderBookTicker} class is useful to manage OrderBookTicker requests
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#symbol-order-book-ticker
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OrderBookTicker extends Ticker{

    private final double bidPrice;
    private final double bidQty;
    private final double askPrice;
    private final double askQty;

    public OrderBookTicker(String symbol, double bidPrice, double bidQty, double askPrice, double askQty) {
        super(symbol);
        this.bidPrice = bidPrice;
        this.bidQty = bidQty;
        this.askPrice = askPrice;
        this.askQty = askQty;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getBidQty() {
        return bidQty;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public double getAskQty() {
        return askQty;
    }

}
