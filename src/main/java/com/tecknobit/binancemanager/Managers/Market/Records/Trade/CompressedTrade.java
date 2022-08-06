package com.tecknobit.binancemanager.Managers.Market.Records.Trade;

/**
 * The {@code CompressedTrade} class is useful to format compressed trade in market endpoints type
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list">
 * https://binance-docs.github.io/apidocs/spot/en/#compressed-aggregate-trades-list</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class CompressedTrade {

    /**
     * {@code aggregateTradeId} is instance that contains aggregate trade id
     * **/
    private final long aggregateTradeId;

    /**
     * {@code price} is instance that contains price in compressed trade
     * **/
    private final double price;

    /**
     * {@code quantity} is instance that contains quantity in compressed trade
     * **/
    private final double quantity;

    /**
     * {@code firstTradeId} is instance that contains first trade id of compressed trade
     * **/
    private final long firstTradeId;

    /**
     * {@code lastTradeId} is instance that contains last trade id of compressed trade
     * **/
    private final long lastTradeId;

    /**
     * {@code timestamp} is instance that contains timestamp of compressed trade
     * **/
    private final long timestamp;

    /**
     * {@code isBuyerMaker} is instance that contains if compressed trade is buyer maker
     * **/
    private final boolean isBuyerMaker;

    /**
     * {@code isBestMatch} is instance that contains if is best match of compressed trade
     * **/
    private final boolean isBestMatch;

    /** Constructor to init {@link CompressedTrade} object
     * @param aggregateTradeId: aggregate trade id
     * @param price: price in compressed trade
     * @param quantity: quantity in compressed trade
     * @param firstTradeId: first trade id of compressed trade
     * @param lastTradeId: last trade id of compressed trade
     * @param timestamp: timestamp of compressed trade
     * @param isBuyerMaker: compressed trade is buyer maker
     * @param isBestMatch: is best match of compressed trade
     * **/
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

    @Override
    public String toString() {
        return "CompressedTrade{" +
                "aggregateTradeId=" + aggregateTradeId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", firstTradeId=" + firstTradeId +
                ", lastTradeId=" + lastTradeId +
                ", timestamp=" + timestamp +
                ", isBuyerMaker=" + isBuyerMaker +
                ", isBestMatch=" + isBestMatch +
                '}';
    }

}
