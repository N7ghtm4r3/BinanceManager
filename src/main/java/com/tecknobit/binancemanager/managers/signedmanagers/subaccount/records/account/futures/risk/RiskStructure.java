package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class RiskStructure extends BinanceItem {

    protected final double entryPrice;
    protected final double leverage;
    protected final double markPrice;
    protected final double positionAmount;
    protected final String symbol;
    protected final double unrealizedProfit;

    public RiskStructure(double entryPrice, double leverage, double markPrice, double positionAmount, String symbol,
                         double unrealizedProfit) {
        super(null);
        this.entryPrice = entryPrice;
        this.leverage = leverage;
        this.markPrice = markPrice;
        this.positionAmount = positionAmount;
        this.symbol = symbol;
        this.unrealizedProfit = unrealizedProfit;
    }

    public RiskStructure(JSONObject jFuturesPositionRisk) {
        super(jFuturesPositionRisk);
        entryPrice = hItem.getDouble("entryPrice", 0);
        leverage = hItem.getDouble("leverage", 0);
        markPrice = hItem.getDouble("markPrice", 0);
        positionAmount = hItem.getDouble("positionAmount", 0);
        symbol = hItem.getString("symbol");
        unrealizedProfit = hItem.getDouble("unrealizedProfit", 0);
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public double getLeverage() {
        return leverage;
    }

    public double getMarkPrice() {
        return markPrice;
    }

    public double getPositionAmount() {
        return positionAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getUnrealizedProfit() {
        return unrealizedProfit;
    }

}
