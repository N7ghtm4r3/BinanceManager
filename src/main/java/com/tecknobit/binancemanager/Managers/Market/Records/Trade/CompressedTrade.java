package com.tecknobit.binancemanager.Managers.Market.Records.Trade;

public class CompressedTrade {

    private final long aggregateTradeId;
    private final double price;
    private final double quantity;
    private final long firstTradeId;
    private final long lastTradeId;
    private final long timestamp;
    private final boolean isBuyerMaker;
    private final boolean isBestMatch;

    public CompressedTrade(long aggregateTradeId, double price, double quantity, long firstTradeId, long lastTradeId,
                           long timestamp, boolean isBuyerMaker, boolean isBestMatch) {
        this.aggregateTradeId = aggregateTradeId;
        this.price = price;
        this.quantity = quantity;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
        this.timestamp = timestamp;
        this.isBuyerMaker = isBuyerMaker;
        this.isBestMatch = isBestMatch;
    }

    public long getAggregateTradeId() {
        return aggregateTradeId;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isBuyerMaker() {
        return isBuyerMaker;
    }

    public boolean isBestMatch() {
        return isBestMatch;
    }

}
