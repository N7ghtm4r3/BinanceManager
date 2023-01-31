package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties;

import org.json.JSONObject;

/**
 * The {@code BNBBurn} class is useful to format a {@code "Binance"}'s BNBBurn
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
 * Toggle BNB Burn On Spot Trade And Margin Interest (USER_DATA)</a>
 **/
public class BNBBurn {

    /**
     * {@code spotBNBBurn} is instance that memorizes if is BNB spot burn
     **/
    private final boolean spotBNBBurn;

    /**
     * {@code interestBNBBurn} is instance that memorizes is BNB interest burn
     **/
    private final boolean interestBNBBurn;

    /**
     * Constructor to init {@link BNBBurn} object
     *
     * @param spotBNBBurn:     is BNB spot burn
     * @param interestBNBBurn: is BNB interest burn
     **/
    public BNBBurn(boolean spotBNBBurn, boolean interestBNBBurn) {
        this.spotBNBBurn = spotBNBBurn;
        this.interestBNBBurn = interestBNBBurn;
    }

    /**
     * Constructor to init {@link BNBBurn} object
     *
     * @param jBNBBurn: BNB burn details as {@link JSONObject}
     **/
    public BNBBurn(JSONObject jBNBBurn) {
        this(jBNBBurn.getBoolean("spotBNBBurn"), jBNBBurn.getBoolean("interestBNBBurn"));
    }

    /**
     * Method to get {@link #spotBNBBurn} instance <br>
     * No-any params required
     *
     * @return {@link #spotBNBBurn} instance as boolean
     **/
    public boolean isSpotBNBBurn() {
        return spotBNBBurn;
    }

    /**
     * Method to get {@link #interestBNBBurn} instance <br>
     * No-any params required
     *
     * @return {@link #interestBNBBurn} instance as boolean
     **/
    public boolean isInterestBNBBurn() {
        return interestBNBBurn;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
