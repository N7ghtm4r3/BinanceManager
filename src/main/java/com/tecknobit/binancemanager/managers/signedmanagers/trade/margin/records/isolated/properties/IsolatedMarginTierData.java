package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code IsolatedMarginTierData} class is useful to format a {@code "Binance"}'s isolated margin tier data
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
 * Query Isolated Margin Tier Data (USER_DATA)</a>
 **/
public class IsolatedMarginTierData {

    /**
     * {@code symbol} is instance that memorizes symbol of asset
     * **/
    private final String symbol;

    /**
     * {@code tier} is instance that memorizes tier value
     **/
    private final int tier;

    /**
     * {@code effectiveMultiple} is instance that memorizes effective multiple value
     **/
    private final double effectiveMultiple;

    /**
     * {@code initialRiskRatio} is instance that memorizes initial risk ratio value
     **/
    private final double initialRiskRatio;

    /**
     * {@code liquidationRiskRatio} is instance that memorizes liquidation risk ratio value
     **/
    private final double liquidationRiskRatio;

    /**
     * {@code baseAssetMaxBorrowable} is instance that memorizes max base asset borrowable
     **/
    private final double baseAssetMaxBorrowable;

    /**
     * {@code quoteAssetMaxBorrowable} is instance that memorizes max quote asset borrowable
     * **/
    private final double quoteAssetMaxBorrowable;

    /** Constructor to init {@link IsolatedMarginTierData} object
     * @param symbol: symbol of asset
     * @param tier: tier value
     * @param effectiveMultiple: effective multiple value
     * @param initialRiskRatio: initial risk ratio value
     * @param liquidationRiskRatio: liquidation risk ratio value
     * @param baseAssetMaxBorrowable: max base asset borrowable
     * @param quoteAssetMaxBorrowable: max quote asset borrowable
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public IsolatedMarginTierData(String symbol, int tier, double effectiveMultiple, double initialRiskRatio,
                                  double liquidationRiskRatio, double baseAssetMaxBorrowable, double quoteAssetMaxBorrowable) {
        this.symbol = symbol;
        if(tier < 0)
            throw new IllegalArgumentException("Tier value cannot be less than 0");
        else
            this.tier = tier;
        if(effectiveMultiple < 0)
            throw new IllegalArgumentException("Effective multiple value cannot be less than 0");
        else
            this.effectiveMultiple = effectiveMultiple;
        if(initialRiskRatio < 0)
            throw new IllegalArgumentException("Initial risk ratio value cannot be less than 0");
        else
            this.initialRiskRatio = initialRiskRatio;
        if (liquidationRiskRatio < 0)
            throw new IllegalArgumentException("Liquidation risk ratio value cannot be less than 0");
        else
            this.liquidationRiskRatio = liquidationRiskRatio;
        if (baseAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Base asset max borrowable value cannot be less than 0");
        else
            this.baseAssetMaxBorrowable = baseAssetMaxBorrowable;
        if (quoteAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Quote asset max borrowable value cannot be less than 0");
        else
            this.quoteAssetMaxBorrowable = quoteAssetMaxBorrowable;
    }

    /**
     * Constructor to init {@link IsolatedMarginTierData} object
     *
     * @param jTierData: isolated margin tier data details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginTierData(JSONObject jTierData) {
        this(jTierData.getString("symbol"), jTierData.getInt("tier"), jTierData.getDouble("effectiveMultiple"),
                jTierData.getDouble("initialRiskRatio"), jTierData.getDouble("liquidationRiskRatio"),
                jTierData.getDouble("baseAssetMaxBorrowable"), jTierData.getDouble("quoteAssetMaxBorrowable"));
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #tier} instance <br>
     * No-any params required
     *
     * @return {@link #tier} instance as int
     **/
    public int getTier() {
        return tier;
    }

    /**
     * Method to get {@link #tier} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #tier} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTier(int decimals) {
        return roundValue(tier, decimals);
    }

    /**
     * Method to get {@link #effectiveMultiple} instance <br>
     * No-any params required
     *
     * @return {@link #effectiveMultiple} instance as double
     **/
    public double getEffectiveMultiple() {
        return effectiveMultiple;
    }

    /**
     * Method to get {@link #effectiveMultiple} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #effectiveMultiple} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getEffectiveMultiple(int decimals) {
        return roundValue(effectiveMultiple, decimals);
    }

    /**
     * Method to get {@link #initialRiskRatio} instance <br>
     * No-any params required
     *
     * @return {@link #initialRiskRatio} instance as double
     **/
    public double getInitialRiskRatio() {
        return initialRiskRatio;
    }

    /**
     * Method to get {@link #initialRiskRatio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #initialRiskRatio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInitialRiskRatio(int decimals) {
        return roundValue(initialRiskRatio, decimals);
    }

    /**
     * Method to get {@link #liquidationRiskRatio} instance <br>
     * No-any params required
     *
     * @return {@link #liquidationRiskRatio} instance as double
     **/
    public double getLiquidationRiskRatio() {
        return liquidationRiskRatio;
    }

    /**
     * Method to get {@link #liquidationRiskRatio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #liquidationRiskRatio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLiquidationRiskRatio(int decimals) {
        return roundValue(liquidationRiskRatio, decimals);
    }

    /**
     * Method to get {@link #baseAssetMaxBorrowable} instance <br>
     * No-any params required
     *
     * @return {@link #baseAssetMaxBorrowable} instance as double
     **/
    public double getBaseAssetMaxBorrowable() {
        return baseAssetMaxBorrowable;
    }

    /**
     * Method to get {@link #baseAssetMaxBorrowable} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #baseAssetMaxBorrowable} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBaseAssetMaxBorrowable(int decimals) {
        return roundValue(baseAssetMaxBorrowable, decimals);
    }

    /**
     * Method to get {@link #quoteAssetMaxBorrowable} instance <br>
     * No-any params required
     *
     * @return {@link #quoteAssetMaxBorrowable} instance as double
     **/
    public double getQuoteAssetMaxBorrowable() {
        return quoteAssetMaxBorrowable;
    }

    /**
     * Method to get {@link #quoteAssetMaxBorrowable} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteAssetMaxBorrowable} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteAssetMaxBorrowable(int decimals) {
        return roundValue(quoteAssetMaxBorrowable, decimals);
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
