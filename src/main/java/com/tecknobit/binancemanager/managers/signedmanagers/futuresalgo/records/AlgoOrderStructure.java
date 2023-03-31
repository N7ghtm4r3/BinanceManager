package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import org.json.JSONObject;

public abstract class AlgoOrderStructure extends BinanceItem {

    protected final long algoId;
    protected final String symbol;
    protected final Side side;
    protected final double executedQty;
    protected final double executedAmt;
    protected final double avgPrice;
    protected final long bookTime;

    public AlgoOrderStructure(long algoId, String symbol, Side side, double executedQty, double executedAmt,
                              double avgPrice, long bookTime) {
        super(null);
        this.algoId = algoId;
        this.symbol = symbol;
        this.side = side;
        this.executedQty = executedQty;
        this.executedAmt = executedAmt;
        this.avgPrice = avgPrice;
        this.bookTime = bookTime;
    }

    public AlgoOrderStructure(JSONObject jAlgoOrderStructure) {
        super(jAlgoOrderStructure);
        algoId = hItem.getLong("algoId", 0);
        symbol = hItem.getString("symbol");
        side = Side.valueOf(hItem.getString("side"));
        executedQty = hItem.getDouble("executedQty", 0);
        executedAmt = hItem.getDouble("executedAmt", 0);
        avgPrice = hItem.getDouble("avgPrice", 0);
        bookTime = hItem.getLong("bookTime", 0);
    }

    public long getAlgoId() {
        return algoId;
    }

    public String getSymbol() {
        return symbol;
    }

    public Side getSide() {
        return side;
    }

    public double getExecutedQty() {
        return executedQty;
    }

    public double getExecutedAmt() {
        return executedAmt;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public long getBookTime() {
        return bookTime;
    }

}
