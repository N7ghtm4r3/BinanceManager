package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Details;

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
