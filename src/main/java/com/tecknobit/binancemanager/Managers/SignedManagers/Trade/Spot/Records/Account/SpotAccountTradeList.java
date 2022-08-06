package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account;

import com.tecknobit.binancemanager.Managers.Market.Records.Trade.Trade;

/**
 *  The {@code SpotAccountTradeList} class is useful to format SpotAccountTradeList object
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotAccountTradeList extends Trade {

    /**
     * {@code symbol} is instance that memorizes symbol
     * **/
    private final String symbol;

    /**
     * {@code orderId} is instance that memorizes order identifier
     * **/
    private final double orderId;

    /**
     * {@code orderListId} is instance that memorizes order list identifier
     * **/
    private final double orderListId;

    /**
     * {@code commission} is instance that memorizes commission value
     * **/
    private final double commission;

    /**
     * {@code commissionAsset} is instance that memorizes commission asset value
     * **/
    private final String commissionAsset;

    /**
     * {@code isMaker} is instance that memorizes if is maker
     * **/
    private final boolean isMaker;

    /** Constructor to init {@link SpotAccountTradeList} object
     * @param id: trade id
     * @param price: price in trade
     * @param qty: quantity in trade
     * @param quoteQty: quote quantity in trade
     * @param time: time of the trade
     * @param isBuyerMaker: trade is buyer maker
     * @param isBestMatch: is best match of trade
     * @param symbol: symbol
     * @param orderId: order identifier
     * @param orderListId: order list identifier
     * @param commission: commission value
     * @param commissionAsset: commission asset value
     * @param isMaker: is maker
     * **/
    public SpotAccountTradeList(String symbol, long id, double orderId, double orderListId, double price, double qty,
                                double quoteQty, double commission, String commissionAsset, long time,
                                boolean isBuyerMaker, boolean isMaker, boolean isBestMatch) {
        super(id, price, qty, quoteQty, time, isBuyerMaker, isBestMatch);
        this.symbol = symbol;
        this.orderId = orderId;
        this.orderListId = orderListId;
        this.commission = commission;
        this.commissionAsset = commissionAsset;
        this.isMaker = isMaker;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getOrderId() {
        return orderId;
    }

    public double getOrderListId() {
        return orderListId;
    }

    public double getCommission() {
        return commission;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

    public boolean isMaker() {
        return isMaker;
    }

    @Override
    public String toString() {
        return "SpotAccountTradeList{" +
                "symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", orderListId=" + orderListId +
                ", commission=" + commission +
                ", commissionAsset='" + commissionAsset + '\'' +
                ", isMaker=" + isMaker +
                '}';
    }

}
