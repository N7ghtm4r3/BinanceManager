package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class SubFuturesAccountStructure extends BinanceItem {

    protected final double totalInitialMargin;
    protected final double totalMaintenanceMargin;
    protected final double totalMarginBalance;
    protected final double totalOpenOrderInitialMargin;
    protected final double totalPositionInitialMargin;
    protected final double totalUnrealizedProfit;
    protected final double totalWalletBalance;

    public SubFuturesAccountStructure(double totalInitialMargin, double totalMaintenanceMargin, double totalMarginBalance,
                                      double totalOpenOrderInitialMargin, double totalPositionInitialMargin,
                                      double totalUnrealizedProfit, double totalWalletBalance) {
        super(null);
        this.totalInitialMargin = totalInitialMargin;
        this.totalMaintenanceMargin = totalMaintenanceMargin;
        this.totalMarginBalance = totalMarginBalance;
        this.totalOpenOrderInitialMargin = totalOpenOrderInitialMargin;
        this.totalPositionInitialMargin = totalPositionInitialMargin;
        this.totalUnrealizedProfit = totalUnrealizedProfit;
        this.totalWalletBalance = totalWalletBalance;
    }

    public SubFuturesAccountStructure(JSONObject jSubFuturesAccount) {
        super(jSubFuturesAccount);
        totalInitialMargin = hItem.getDouble("totalInitialMargin", 0);
        totalMaintenanceMargin = hItem.getDouble("totalMaintenanceMargin", 0);
        totalMarginBalance = hItem.getDouble("totalMarginBalance", 0);
        totalOpenOrderInitialMargin = hItem.getDouble("totalOpenOrderInitialMargin", 0);
        totalPositionInitialMargin = hItem.getDouble("totalPositionInitialMargin", 0);
        totalUnrealizedProfit = hItem.getDouble("totalUnrealizedProfit", 0);
        totalWalletBalance = hItem.getDouble("totalWalletBalance", 0);
    }

    public double getTotalInitialMargin() {
        return totalInitialMargin;
    }

    public double getTotalMaintenanceMargin() {
        return totalMaintenanceMargin;
    }

    public double getTotalMarginBalance() {
        return totalMarginBalance;
    }

    public double getTotalOpenOrderInitialMargin() {
        return totalOpenOrderInitialMargin;
    }

    public double getTotalPositionInitialMargin() {
        return totalPositionInitialMargin;
    }

    public double getTotalUnrealizedProfit() {
        return totalUnrealizedProfit;
    }

    public double getTotalWalletBalance() {
        return totalWalletBalance;
    }

}
