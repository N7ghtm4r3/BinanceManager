package com.tecknobit.binancemanager.Managers.Wallet.Records;

public record FundingWallet (String asset, double free, double locked, double freeze, int withdrawing, double btcValuation) {

    @Override
    public String asset() {
        return asset;
    }

    @Override
    public double free() {
        return free;
    }

    @Override
    public double locked() {
        return locked;
    }

    @Override
    public double freeze() {
        return freeze;
    }

    @Override
    public int withdrawing() {
        return withdrawing;
    }

    @Override
    public double btcValuation() {
        return btcValuation;
    }

    @Override
    public String toString() {
        return "FundingWallet{" +
                "asset='" + asset + '\'' +
                ", free=" + free +
                ", locked=" + locked +
                ", freeze=" + freeze +
                ", withdrawing=" + withdrawing +
                ", btcValuation=" + btcValuation +
                '}';
    }

}
