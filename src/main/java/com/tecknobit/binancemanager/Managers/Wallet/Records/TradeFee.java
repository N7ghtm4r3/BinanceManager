package com.tecknobit.binancemanager.Managers.Wallet.Records;

public class TradeFee {

    private final String symbol;
    private final double makerCommission;
    private final double takerCommission;

    public TradeFee(String symbol, double makerCommission, double takerCommission) {
        this.symbol = symbol;
        this.makerCommission = makerCommission;
        this.takerCommission = takerCommission;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getMakerCommission() {
        return makerCommission;
    }

    public double getTakerCommission() {
        return takerCommission;
    }

}


