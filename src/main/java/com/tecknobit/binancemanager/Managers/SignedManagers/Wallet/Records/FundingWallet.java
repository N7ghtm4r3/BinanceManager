package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code FundingWallet} class is useful to manage FundingWallet Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#funding-wallet-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class FundingWallet {

    private final String asset;
    private final double free;
    private final double locked;
    private final double freeze;
    private final int withdrawing;
    private final double btcValuation;

    public FundingWallet(String asset, double free, double locked, double freeze, int withdrawing, double btcValuation) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
        this.freeze = freeze;
        this.withdrawing = withdrawing;
        this.btcValuation = btcValuation;
    }

    public String asset() {
        return asset;
    }

    public double free() {
        return free;
    }

    public double locked() {
        return locked;
    }

    public double freeze() {
        return freeze;
    }

    public int withdrawing() {
        return withdrawing;
    }

    public double btcValuation() {
        return btcValuation;
    }

}
