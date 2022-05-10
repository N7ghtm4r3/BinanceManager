package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code IsolatedMarginTierData} class is useful to format Binance Isolated Margin Tier Data request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginTierData {

    private final String symbol;
    private int tier;
    private double effectiveMultiple;
    private double initialRiskRatio;
    private double liquidationRiskRatio;
    private double baseAssetMaxBorrowable;
    private double quoteAssetMaxBorrowable;

    public IsolatedMarginTierData(String symbol, int tier, double effectiveMultiple, double initialRiskRatio,
                                  double liquidationRiskRatio, double baseAssetMaxBorrowable, double quoteAssetMaxBorrowable) {
        this.symbol = symbol;
        this.tier = tier;
        this.effectiveMultiple = effectiveMultiple;
        this.initialRiskRatio = initialRiskRatio;
        this.liquidationRiskRatio = liquidationRiskRatio;
        this.baseAssetMaxBorrowable = baseAssetMaxBorrowable;
        this.quoteAssetMaxBorrowable = quoteAssetMaxBorrowable;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        if(tier < 0)
            throw new IllegalArgumentException("Tier value cannot be less than 0");
        this.tier = tier;
    }

    public double getEffectiveMultiple() {
        return effectiveMultiple;
    }

    public void setEffectiveMultiple(double effectiveMultiple) {
        if(effectiveMultiple < 0)
            throw new IllegalArgumentException("Effective multiple value cannot be less than 0");
        this.effectiveMultiple = effectiveMultiple;
    }

    public double getInitialRiskRatio() {
        return initialRiskRatio;
    }

    public void setInitialRiskRatio(double initialRiskRatio) {
        if(initialRiskRatio < 0)
            throw new IllegalArgumentException("Initial risk ratio value cannot be less than 0");
        this.initialRiskRatio = initialRiskRatio;
    }

    public double getLiquidationRiskRatio() {
        return liquidationRiskRatio;
    }

    public void setLiquidationRiskRatio(double liquidationRiskRatio) {
        if(liquidationRiskRatio < 0)
            throw new IllegalArgumentException("Liquidation risk ratio value cannot be less than 0");
        this.liquidationRiskRatio = liquidationRiskRatio;
    }

    public double getBaseAssetMaxBorrowable() {
        return baseAssetMaxBorrowable;
    }

    public void setBaseAssetMaxBorrowable(double baseAssetMaxBorrowable) {
        if(baseAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Base asset max borrowable value cannot be less than 0");
        this.baseAssetMaxBorrowable = baseAssetMaxBorrowable;
    }

    public double getQuoteAssetMaxBorrowable() {
        return quoteAssetMaxBorrowable;
    }

    public void setQuoteAssetMaxBorrowable(double quoteAssetMaxBorrowable) {
        if(quoteAssetMaxBorrowable < 0)
            throw new IllegalArgumentException("Quote asset max borrowable value cannot be less than 0");
        this.quoteAssetMaxBorrowable = quoteAssetMaxBorrowable;
    }

}
