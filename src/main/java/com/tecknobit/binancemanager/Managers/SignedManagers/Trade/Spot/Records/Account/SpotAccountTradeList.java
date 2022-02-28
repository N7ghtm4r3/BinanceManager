package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account;

import com.tecknobit.binancemanager.Managers.Market.Records.Trade.Trade;

public class SpotAccountTradeList extends Trade {

    private final String symbol;
    private final double orderId;
    private final double orderListId;
    private final double commission;
    private final String commissionAsset;
    private final boolean isMaker;

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

}
