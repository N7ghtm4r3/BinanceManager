package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code BNBBurn} class is useful to format all Binance BNBBurn request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class BNBBurn {

    private final boolean spotBNBBurn;
    private final boolean interestBNBBurn;

    public BNBBurn(boolean spotBNBBurn, boolean interestBNBBurn) {
        this.spotBNBBurn = spotBNBBurn;
        this.interestBNBBurn = interestBNBBurn;
    }

    public boolean isSpotBNBBurn() {
        return spotBNBBurn;
    }

    public boolean isInterestBNBBurn() {
        return interestBNBBurn;
    }

}
