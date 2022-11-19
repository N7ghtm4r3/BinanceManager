package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code IsolatedMarginTierData} class is useful to format Binance Isolated Margin Tier Data request response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
 **/

public class IsolatedMarginTierData {

    /**
     * {@code symbol} is instance that memorizes symbol of asset
     * **/
    private final String symbol;

    /**
     * {@code tier} is instance that memorizes tier value
     * **/
    private int tier;

    /**
     * {@code effectiveMultiple} is instance that memorizes effective multiple value
     * **/
    private double effectiveMultiple;

    /**
     * {@code initialRiskRatio} is instance that memorizes initial risk ratio value
     * **/
    private double initialRiskRatio;

    /**
     * {@code liquidationRiskRatio} is instance that memorizes liquidation risk ratio value
     * **/
    private double liquidationRiskRatio;

    /**
     * {@code baseAssetMaxBorrowable} is instance that memorizes max base asset borrowable
     * **/
    private double baseAssetMaxBorrowable;

    /**
     * {@code quoteAssetMaxBorrowable} is instance that memorizes max quote asset borrowable
     * **/
    private double quoteAssetMaxBorrowable;

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
     * @param isolatedMarginTierData: isolated margin tier data details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginTierData(JSONObject isolatedMarginTierData) {
        symbol = isolatedMarginTierData.getString("symbol");
        tier = isolatedMarginTierData.getInt("tier");
        if (tier < 0)
            throw new IllegalArgumentException("Tier value cannot be less than 0");
        effectiveMultiple = isolatedMarginTierData.getDouble("effectiveMultiple");
        if (effectiveMultiple < 0)
            throw new IllegalArgumentException("Effective multiple value cannot be less than 0");
        initialRiskRatio = isolatedMarginTierData.getDouble("initialRiskRatio");
        if (initialRiskRatio < 0)
            throw new IllegalArgumentException("Initial risk ratio value cannot be less than 0");
        liquidationRiskRatio = isolatedMarginTierData.getDouble("liquidationRiskRatio");
        if (liquidationRiskRatio < 0)
            throw new IllegalArgumentException("Liquidation risk ratio value cannot be less than 0");
        baseAssetMaxBorrowable = isolatedMarginTierData.getDouble("baseAssetMaxBorrowable");
        if (baseAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Base asset max borrowable value cannot be less than 0");
        quoteAssetMaxBorrowable = isolatedMarginTierData.getDouble("quoteAssetMaxBorrowable");
        if (quoteAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Quote asset max borrowable value cannot be less than 0");
    }

    public String getSymbol() {
        return symbol;
    }

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
     * Method to set {@link #tier}
     *
     * @param tier: tier value
     * @throws IllegalArgumentException when tier value is less than 0
     **/
    public void setTier(int tier) {
        if (tier < 0)
            throw new IllegalArgumentException("Tier value cannot be less than 0");
        this.tier = tier;
    }

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
     * Method to set {@link #effectiveMultiple}
     *
     * @param effectiveMultiple: effective multiple value
     * @throws IllegalArgumentException when effective multiple value is less than 0
     **/
    public void setEffectiveMultiple(double effectiveMultiple) {
        if (effectiveMultiple < 0)
            throw new IllegalArgumentException("Effective multiple value cannot be less than 0");
        this.effectiveMultiple = effectiveMultiple;
    }

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
     * Method to set {@link #initialRiskRatio}
     *
     * @param initialRiskRatio: initial risk ratio value
     * @throws IllegalArgumentException when effective multiple value is less than 0
     **/
    public void setInitialRiskRatio(double initialRiskRatio) {
        if (initialRiskRatio < 0)
            throw new IllegalArgumentException("Initial effective multiple value cannot be less than 0");
        this.initialRiskRatio = initialRiskRatio;
    }

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
     * Method to set {@link #liquidationRiskRatio}
     *
     * @param liquidationRiskRatio: liquidation risk ratio value
     * @throws IllegalArgumentException when liquidation risk ratio value is less than 0
     **/
    public void setLiquidationRiskRatio(double liquidationRiskRatio) {
        if (liquidationRiskRatio < 0)
            throw new IllegalArgumentException("Liquidation risk ratio value cannot be less than 0");
        this.liquidationRiskRatio = liquidationRiskRatio;
    }

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
     * Method to set {@link #baseAssetMaxBorrowable}
     *
     * @param baseAssetMaxBorrowable: max base asset borrowable
     * @throws IllegalArgumentException when max base asset borrowable value is less than 0
     **/
    public void setBaseAssetMaxBorrowable(double baseAssetMaxBorrowable) {
        if (baseAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Base asset max borrowable value cannot be less than 0");
        this.baseAssetMaxBorrowable = baseAssetMaxBorrowable;
    }

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
     * Method to set {@link #quoteAssetMaxBorrowable}
     *
     * @param quoteAssetMaxBorrowable: max quote asset borrowable
     * @throws IllegalArgumentException when max quote asset borrowable value is less than 0
     **/
    public void setQuoteAssetMaxBorrowable(double quoteAssetMaxBorrowable) {
        if (quoteAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Quote asset max borrowable value cannot be less than 0");
        this.quoteAssetMaxBorrowable = quoteAssetMaxBorrowable;
    }

    @Override
    public String toString() {
        return "IsolatedMarginTierData{" +
                "symbol='" + symbol + '\'' +
                ", tier=" + tier +
                ", effectiveMultiple=" + effectiveMultiple +
                ", initialRiskRatio=" + initialRiskRatio +
                ", liquidationRiskRatio=" + liquidationRiskRatio +
                ", baseAssetMaxBorrowable=" + baseAssetMaxBorrowable +
                ", quoteAssetMaxBorrowable=" + quoteAssetMaxBorrowable +
                '}';
    }

}
