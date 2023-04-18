package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class FuturesPositionRisk extends BinanceItem {

    private final double entryPrice;
    private final double leverage;
    private final double maxNotional;
    private final double liquidationPrice;
    private final double markPrice;
    private final double positionAmount;
    private final String symbol;
    private final double unrealizedProfit;

    public FuturesPositionRisk(double entryPrice, double leverage, double maxNotional, double liquidationPrice,
                               double markPrice, double positionAmount, String symbol, double unrealizedProfit) {
        super(null);
        this.entryPrice = entryPrice;
        this.leverage = leverage;
        this.maxNotional = maxNotional;
        this.liquidationPrice = liquidationPrice;
        this.markPrice = markPrice;
        this.positionAmount = positionAmount;
        this.symbol = symbol;
        this.unrealizedProfit = unrealizedProfit;
    }

    public FuturesPositionRisk(JSONObject jFuturesPositionRisk) {
        super(jFuturesPositionRisk);
        entryPrice = hItem.getDouble("entryPrice", 0);
        leverage = hItem.getDouble("leverage", 0);
        maxNotional = hItem.getDouble("maxNotional", 0);
        liquidationPrice = hItem.getDouble("liquidationPrice", 0);
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

    public double getMaxNotional() {
        return maxNotional;
    }

    public double getLiquidationPrice() {
        return liquidationPrice;
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
