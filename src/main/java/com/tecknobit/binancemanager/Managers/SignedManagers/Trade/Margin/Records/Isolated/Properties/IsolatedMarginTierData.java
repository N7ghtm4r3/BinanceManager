package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code IsolatedMarginTierData} class is useful to format Binance Isolated Margin Tier Data request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginTierData {

    private final String symbol;
    private final int tier;
    private final double effectiveMultiple;
    private final double initialRiskRatio;
    private final double liquidationRiskRatio;
    private final double baseAssetMaxBorrowable;
    private final double quoteAssetMaxBorrowable;

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

    public double getEffectiveMultiple() {
        return effectiveMultiple;
    }

    public double getInitialRiskRatio() {
        return initialRiskRatio;
    }

    public double getLiquidationRiskRatio() {
        return liquidationRiskRatio;
    }

    public double getBaseAssetMaxBorrowable() {
        return baseAssetMaxBorrowable;
    }

    public double getQuoteAssetMaxBorrowable() {
        return quoteAssetMaxBorrowable;
    }

}
