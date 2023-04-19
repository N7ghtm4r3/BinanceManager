package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk;

import com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records.AlgoOrdersList.AlgoOrder.PositionSide;
import org.json.JSONObject;

public class CoinFuturesPositionRisk extends RiskStructure {

    private final boolean isolated;
    private final double isolatedWallet;
    private final double isolatedMargin;
    private final boolean isAutoAddMargin;
    private final PositionSide positionSide;

    public CoinFuturesPositionRisk(double entryPrice, double leverage, double markPrice, double positionAmount,
                                   String symbol, double unrealizedProfit, boolean isolated, double isolatedWallet,
                                   double isolatedMargin, boolean isAutoAddMargin, PositionSide positionSide) {
        super(entryPrice, leverage, markPrice, positionAmount, symbol, unrealizedProfit);
        this.isolated = isolated;
        this.isolatedWallet = isolatedWallet;
        this.isolatedMargin = isolatedMargin;
        this.isAutoAddMargin = isAutoAddMargin;
        this.positionSide = positionSide;
    }

    public CoinFuturesPositionRisk(JSONObject jCoinFuturesPositionRisk) {
        super(jCoinFuturesPositionRisk);
        isolated = hItem.getBoolean("isolated");
        isolatedWallet = hItem.getDouble("isolatedWallet", 0);
        isolatedMargin = hItem.getDouble("isolatedMargin", 0);
        isAutoAddMargin = hItem.getBoolean("isAutoAddMargin");
        positionSide = PositionSide.valueOf(hItem.getString("positionSide"));
    }

    public boolean isIsolated() {
        return isolated;
    }

    public double getIsolatedWallet() {
        return isolatedWallet;
    }

    public double getIsolatedMargin() {
        return isolatedMargin;
    }

    public boolean isAutoAddMargin() {
        return isAutoAddMargin;
    }

    public PositionSide getPositionSide() {
        return positionSide;
    }

}
