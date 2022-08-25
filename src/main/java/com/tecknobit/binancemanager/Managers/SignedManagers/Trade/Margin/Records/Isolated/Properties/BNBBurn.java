package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code BNBBurn} class is useful to format all Binance BNBBurn request response
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class BNBBurn {

    /**
     * {@code spotBNBBurn} is instance that memorizes if is BNB spot burn
     * **/
    private boolean spotBNBBurn;

    /**
     * {@code interestBNBBurn} is instance that memorizes is BNB interest burn
     * **/
    private boolean interestBNBBurn;

    /** Constructor to init {@link BNBBurn} object
     * @param spotBNBBurn: is BNB spot burn
     * @param interestBNBBurn: is BNB interest burn
     * **/
    public BNBBurn(boolean spotBNBBurn, boolean interestBNBBurn) {
        this.spotBNBBurn = spotBNBBurn;
        this.interestBNBBurn = interestBNBBurn;
    }

    public boolean isSpotBNBBurn() {
        return spotBNBBurn;
    }

    public void setSpotBNBBurn(boolean spotBNBBurn) {
        this.spotBNBBurn = spotBNBBurn;
    }

    public boolean isInterestBNBBurn() {
        return interestBNBBurn;
    }

    public void setInterestBNBBurn(boolean interestBNBBurn) {
        this.interestBNBBurn = interestBNBBurn;
    }

    @Override
    public String toString() {
        return "BNBBurn{" +
                "spotBNBBurn=" + spotBNBBurn +
                ", interestBNBBurn=" + interestBNBBurn +
                '}';
    }

}
