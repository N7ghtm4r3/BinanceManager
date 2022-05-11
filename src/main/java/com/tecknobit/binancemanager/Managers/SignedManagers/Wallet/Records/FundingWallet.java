package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code FundingWallet} class is useful to manage FundingWallet Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data">https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class FundingWallet {

    private final String asset;
    private double free;
    private double locked;
    private double freeze;
    private int withdrawing;
    private double btcValuation;

    public FundingWallet(String asset, double free, double locked, double freeze, int withdrawing, double btcValuation) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
        this.freeze = freeze;
        this.withdrawing = withdrawing;
        this.btcValuation = btcValuation;
    }

    public String getAsset() {
        return asset;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getLocked() {
        return locked;
    }

    public void setLocked(double locked) {
        this.locked = locked;
    }

    public double getFreeze() {
        return freeze;
    }

    public void setFreeze(double freeze) {
        this.freeze = freeze;
    }

    public int getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(int withdrawing) {
        this.withdrawing = withdrawing;
    }

    public double getBtcValuation() {
        return btcValuation;
    }

    public void setBtcValuation(double btcValuation) {
        this.btcValuation = btcValuation;
    }

}
