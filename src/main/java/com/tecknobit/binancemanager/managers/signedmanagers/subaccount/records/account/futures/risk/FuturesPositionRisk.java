package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk;

import org.json.JSONObject;

public class FuturesPositionRisk extends RiskStructure {

    private final double maxNotional;
    private final double liquidationPrice;

    public FuturesPositionRisk(double entryPrice, double leverage, double maxNotional, double liquidationPrice,
                               double markPrice, double positionAmount, String symbol, double unrealizedProfit) {
        super(entryPrice, leverage, markPrice, positionAmount, symbol, unrealizedProfit);
        this.maxNotional = maxNotional;
        this.liquidationPrice = liquidationPrice;
    }

    public FuturesPositionRisk(JSONObject jFuturesPositionRisk) {
        super(jFuturesPositionRisk);
        maxNotional = hItem.getDouble("maxNotional", 0);
        liquidationPrice = hItem.getDouble("liquidationPrice", 0);
    }

    public double getMaxNotional() {
        return maxNotional;
    }

    public double getLiquidationPrice() {
        return liquidationPrice;
    }

}
