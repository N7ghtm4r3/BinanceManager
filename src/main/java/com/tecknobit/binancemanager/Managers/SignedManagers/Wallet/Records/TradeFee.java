package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code TradeFee} class is useful to manage TradeFee Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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


