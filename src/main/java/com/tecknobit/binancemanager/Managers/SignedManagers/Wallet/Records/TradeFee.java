package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code TradeFee} class is useful to manage TradeFee Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class TradeFee {

    /**
     * {@code symbol} is instance that memorizes symbol value
     * **/
    private final String symbol;

    /**
     * {@code makerCommission} is instance that memorizes maker commission value
     * **/
    private final double makerCommission;

    /**
     * {@code takerCommission} is instance that memorizes taker commission value
     * **/
    private final double takerCommission;

    /** Constructor to init {@link TradeFee} object
     * @param symbol: symbol value
     * @param makerCommission: maker commission value
     * @param takerCommission: taker commission value
     * **/
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


