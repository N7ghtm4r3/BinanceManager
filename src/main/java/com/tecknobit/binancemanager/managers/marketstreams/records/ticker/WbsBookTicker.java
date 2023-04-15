package com.tecknobit.binancemanager.managers.marketstreams.records.ticker;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class WbsBookTicker extends BinanceItem {

    private final long orderBookUpdateId;
    private final String symbol;
    private final double bestBidPrice;
    private final double bestBidQuantity;
    private final double bestAskPrice;
    private final double bestAskQuantity;

    public WbsBookTicker(long orderBookUpdateId, String symbol, double bestBidPrice, double bestBidQuantity,
                         double bestAskPrice, double bestAskQuantity) {
        super(null);
        this.orderBookUpdateId = orderBookUpdateId;
        this.symbol = symbol;
        this.bestBidPrice = bestBidPrice;
        this.bestBidQuantity = bestBidQuantity;
        this.bestAskPrice = bestAskPrice;
        this.bestAskQuantity = bestAskQuantity;
    }

    public WbsBookTicker(JSONObject jWbsBookTicker) {
        super(jWbsBookTicker);
        orderBookUpdateId = hItem.getLong("u", 0);
        symbol = hItem.getString("s");
        bestBidPrice = hItem.getDouble("b", 0);
        bestBidQuantity = hItem.getDouble("B", 0);
        bestAskPrice = hItem.getDouble("a", 0);
        bestAskQuantity = hItem.getDouble("A", 0);
    }

    public long getOrderBookUpdateId() {
        return orderBookUpdateId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getBestBidPrice() {
        return bestBidPrice;
    }

    public double getBestBidQuantity() {
        return bestBidQuantity;
    }

    public double getBestAskPrice() {
        return bestAskPrice;
    }

    public double getBestAskQuantity() {
        return bestAskQuantity;
    }

}
