package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account;

import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code IsolatedMarginAccountInfo} class is useful to format a {@code "Binance"}'s isolated margin account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
 * Query Isolated Margin Account Info (USER_DATA)</a>
 **/
public class IsolatedMarginAccountInfo {

    /**
     * {@code baseAsset} is instance that memorizes base asset
     **/
    private final IsolatedMarginAsset baseAsset;

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     **/
    private final String symbol;
    /**
     * {@code quoteAsset} is instance that memorizes quote asset
     **/
    private final IsolatedMarginAsset quoteAsset;
    /**
     * {@code isolatedCreated} is instance that memorizes if isolated has been created
     **/
    private boolean isolatedCreated;
    /**
     * {@code marginLevel} is instance that memorizes margin level
     **/
    private double marginLevel;
    /**
     * {@code enabled} is instance that memorizes if order has been enabled
     **/
    private boolean enabled;
    /**
     * {@code marginLevelStatus} is instance that memorizes margin status level
     **/
    private MarginLevelStatus marginLevelStatus;
    /**
     * {@code marginRatio} is instance that memorizes margin ratio
     **/
    private double marginRatio;
    /**
     * {@code indexPrice} is instance that memorizes index price
     **/
    private double indexPrice;
    /**
     * {@code liquidatePrice} is instance that memorizes liquidate price
     **/
    private double liquidatePrice;
    /**
     * {@code liquidateRate} is instance that memorizes liquidate rate
     **/
    private double liquidateRate;
    /**
     * {@code tradeEnabled} is flag that checks if trade has been enabled
     **/
    private boolean tradeEnabled;

    /**
     * Constructor to init {@link IsolatedMarginAccountInfo} object
     *
     * @param symbol:            symbol used in the order
     * @param isolatedCreated:   isolated has been created
     * @param enabled:           if order has been enabled
     * @param marginLevel:       margin level
     * @param marginLevelStatus: margin status level
     * @param marginRatio:       margin ratio
     * @param indexPrice:        index price
     * @param liquidatePrice:    liquidate price
     * @param liquidateRate:     liquidate rate
     * @param tradeEnabled:      trade enable
     * @param baseAsset:         time of order trade
     * @param quoteAsset:        time of order trade
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginAccountInfo(String symbol, boolean isolatedCreated, boolean enabled, double marginLevel,
                                     MarginLevelStatus marginLevelStatus, double marginRatio, double indexPrice,
                                     double liquidatePrice, double liquidateRate, boolean tradeEnabled,
                                     IsolatedMarginAsset baseAsset, IsolatedMarginAsset quoteAsset) {
        this.symbol = symbol;
        this.isolatedCreated = isolatedCreated;
        this.enabled = enabled;
        this.marginLevel = marginLevel;
        this.marginLevelStatus = marginLevelStatus;
        this.marginRatio = marginRatio;
        this.indexPrice = indexPrice;
        this.liquidatePrice = liquidatePrice;
        this.liquidateRate = liquidateRate;
        this.tradeEnabled = tradeEnabled;
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    /**
     * Constructor to init {@link IsolatedMarginAccountInfo} object
     *
     * @param isolatedMarginAccountInfo: isolated margin account info as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginAccountInfo(JSONObject isolatedMarginAccountInfo) {
        symbol = isolatedMarginAccountInfo.getString("asset");
        isolatedCreated = isolatedMarginAccountInfo.getBoolean("isolatedCreated");
        enabled = isolatedMarginAccountInfo.getBoolean("enabled");
        marginLevel = isolatedMarginAccountInfo.getDouble("marginLevel");
        if (marginLevel < 0)
            throw new IllegalArgumentException("Margin level value cannot be less than 0");
        marginLevelStatus = MarginLevelStatus.valueOf(isolatedMarginAccountInfo.getString("marginLevelStatus"));
        marginRatio = isolatedMarginAccountInfo.getDouble("marginRatio");
        if (marginRatio < 0)
            throw new IllegalArgumentException("Margin ratio value cannot be less than 0");
        indexPrice = isolatedMarginAccountInfo.getDouble("indexPrice");
        if (indexPrice < 0)
            throw new IllegalArgumentException("Index price value cannot be less than 0");
        liquidatePrice = isolatedMarginAccountInfo.getDouble("liquidatePrice");
        if (liquidatePrice < 0)
            throw new IllegalArgumentException("Liquidate price value cannot be less than 0");
        liquidateRate = isolatedMarginAccountInfo.getDouble("liquidateRate");
        if (liquidateRate < 0)
            throw new IllegalArgumentException("Liquidate rate value cannot be less than 0");
        tradeEnabled = isolatedMarginAccountInfo.getBoolean("tradeEnabled");
        baseAsset = new IsolatedMarginAsset(isolatedMarginAccountInfo.getJSONObject("baseAsset"));
        quoteAsset = new IsolatedMarginAsset(isolatedMarginAccountInfo.getJSONObject("quoteAsset"));
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * Any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #isolatedCreated} instance <br>
     * Any params required
     *
     * @return {@link #isolatedCreated} instance as boolean
     **/
    public boolean isIsolatedCreated() {
        return isolatedCreated;
    }

    /**
     * Method to set {@link #isolatedCreated}
     *
     * @param isolatedCreated: if isolated has been created
     **/
    public void setIsolatedCreated(boolean isolatedCreated) {
        this.isolatedCreated = isolatedCreated;
    }

    /**
     * Method to get {@link #enabled} instance <br>
     * Any params required
     *
     * @return {@link #enabled} instance as boolean
     **/
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Method to set {@link #enabled}
     *
     * @param enabled: if order has been enabled
     **/
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Method to get {@link #marginLevel} instance <br>
     * Any params required
     *
     * @return {@link #marginLevel} instance as double
     **/
    public double getMarginLevel() {
        return marginLevel;
    }

    /**
     * Method to get {@link #marginLevelStatus} instance <br>
     * Any params required
     *
     * @return {@link #marginLevelStatus} instance as {@link MarginLevelStatus}
     **/
    public MarginLevelStatus getMarginLevelStatus() {
        return marginLevelStatus;
    }

    /**
     * Method to get {@link #marginLevel} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginLevel} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMarginLevel(int decimals) {
        return roundValue(marginLevel, decimals);
    }

    /**
     * Method to set {@link #marginLevel}
     *
     * @param marginLevel: margin level
     * @throws IllegalArgumentException when margin level value is less than 0
     **/
    public void setMarginLevel(double marginLevel) {
        if (marginLevel < 0)
            throw new IllegalArgumentException("Margin level value cannot be less than 0");
        this.marginLevel = marginLevel;
    }

    /**
     * Method to set {@link #marginLevelStatus}
     *
     * @param marginLevelStatus: margin status level
     * @throws IllegalArgumentException when margin level status is not valid
     **/
    public void setMarginLevelStatus(MarginLevelStatus marginLevelStatus) {
        this.marginLevelStatus = marginLevelStatus;
    }

    /**
     * Method to get {@link #marginRatio} instance <br>
     * Any params required
     *
     * @return {@link #marginRatio} instance as double
     **/
    public double getMarginRatio() {
        return marginRatio;
    }

    /**
     * Method to get {@link #indexPrice} instance <br>
     * Any params required
     *
     * @return {@link #indexPrice} instance as double
     **/
    public double getIndexPrice() {
        return indexPrice;
    }

    /**
     * Method to get {@link #marginRatio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginRatio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMarginRatio(int decimals) {
        return roundValue(marginRatio, decimals);
    }

    /**
     * Method to set {@link #marginRatio}
     *
     * @param marginRatio: margin ratio
     * @throws IllegalArgumentException when margin ratio value is less than 0
     **/
    public void setMarginRatio(double marginRatio) {
        if (marginRatio < 0)
            throw new IllegalArgumentException("Margin ratio value cannot be less than 0");
        this.marginRatio = marginRatio;
    }

    /**
     * Method to get {@link #liquidatePrice} instance <br>
     * Any params required
     *
     * @return {@link #liquidatePrice} instance as double
     **/
    public double getLiquidatePrice() {
        return liquidatePrice;
    }

    /**
     * Method to get {@link #indexPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #indexPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIndexPrice(int decimals) {
        return roundValue(indexPrice, decimals);
    }

    /**
     * Method to set {@link #indexPrice}
     *
     * @param indexPrice: commission asset of margin account trade
     * @throws IllegalArgumentException when index price value is less than 0
     **/
    public void setIndexPrice(double indexPrice) {
        if (indexPrice < 0)
            throw new IllegalArgumentException("Index price value cannot be less than 0");
        this.indexPrice = indexPrice;
    }

    /**
     * Method to get {@link #liquidateRate} instance <br>
     * Any params required
     *
     * @return {@link #liquidateRate} instance as double
     **/
    public double getLiquidateRate() {
        return liquidateRate;
    }

    /**
     * Method to get {@link #liquidatePrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #liquidatePrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLiquidatePrice(int decimals) {
        return roundValue(liquidatePrice, decimals);
    }

    /**
     * Method to set {@link #liquidatePrice}
     *
     * @param liquidatePrice: commission asset of margin account trade
     * @throws IllegalArgumentException when liquidate price value is less than 0
     **/
    public void setLiquidatePrice(double liquidatePrice) {
        if (liquidatePrice < 0)
            throw new IllegalArgumentException("Liquidate price value cannot be less than 0");
        this.liquidatePrice = liquidatePrice;
    }

    /**
     * Method to get {@link #tradeEnabled} instance <br>
     * Any params required
     *
     * @return {@link #tradeEnabled} instance as boolean
     **/
    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    /**
     * Method to get {@link #liquidateRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #liquidateRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLiquidateRate(int decimals) {
        return roundValue(liquidateRate, decimals);
    }

    /**
     * Method to set {@link #liquidateRate}
     *
     * @param liquidateRate: commission asset of margin account trade
     * @throws IllegalArgumentException when liquidate rate value is less than 0
     **/
    public void setLiquidateRate(double liquidateRate) {
        if (liquidateRate < 0)
            throw new IllegalArgumentException("Liquidate rate value cannot be less than 0");
        this.liquidateRate = liquidateRate;
    }

    /**
     * Method to set {@link #tradeEnabled}
     *
     * @param tradeEnabled: flag that checks if trade has been enabled
     **/
    public void setTradeEnabled(boolean tradeEnabled) {
        this.tradeEnabled = tradeEnabled;
    }

    /**
     * Method to get {@link #baseAsset} instance <br>
     * Any params required
     *
     * @return {@link #baseAsset} instance as {@link IsolatedMarginAsset}
     **/
    public IsolatedMarginAsset getBaseAsset() {
        return baseAsset;
    }

    /**
     * Method to get {@link #quoteAsset} instance <br>
     * Any params required
     *
     * @return {@link #quoteAsset} instance as {@link IsolatedMarginAsset}
     **/
    public IsolatedMarginAsset getQuoteAsset() {
        return quoteAsset;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * {@code MarginLevelStatus} list of available margin level status
     **/
    public enum MarginLevelStatus {

        /**
         * {@code "EXCESSIVE"} margin level status
         **/
        EXCESSIVE,

        /**
         * {@code "NORMAL"} margin level status
         **/
        NORMAL,

        /**
         * {@code "MARGIN_CALL"} margin level status
         **/
        MARGIN_CALL,

        /**
         * {@code "PRE_LIQUIDATION"} margin level status
         **/
        PRE_LIQUIDATION,

        /**
         * {@code "FORCE_LIQUIDATION"} margin level status
         **/
        FORCE_LIQUIDATION

    }

    /**
     * The {@code IsolatedMarginAsset} class is useful to create an isolated margin asset object
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see UserMarginAsset
     **/
    public static class IsolatedMarginAsset extends UserMarginAsset {

        /**
         * {@code borrowEnabled} is instance if borrow is enabled for asset
         **/
        private boolean borrowEnabled;

        /**
         * {@code netAssetOfBtc} is instance that memorizes net asset of Bitcoin
         **/
        private double netAssetOfBtc;

        /**
         * {@code repayEnabled} is instance if repay is enabled for asset
         **/
        private boolean repayEnabled;

        /**
         * {@code totalAsset} is instance that memorizes total asset amount
         **/
        private double totalAsset;

        /**
         * Constructor to init {@link IsolatedMarginAsset} object
         *
         * @param asset:         asset
         * @param borrowEnabled: borrow is enabled for asset
         * @param borrowed:      amount of borrow from asset
         * @param free:          free amount of asset
         * @param interest:      amount of interest in asset
         * @param locked:        amount locked for asset
         * @param netAsset:      net asset
         * @param netAssetOfBtc: net asset of Bitcoin
         * @param repayEnabled:  repay is enabled for asset
         * @param totalAsset:    total asset amount
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public IsolatedMarginAsset(String asset, boolean borrowEnabled, double borrowed, double free, double interest,
                                   double locked, double netAsset, double netAssetOfBtc, boolean repayEnabled, double totalAsset) {
            super(asset, borrowed, free, interest, locked, netAsset);
            this.borrowEnabled = borrowEnabled;
            if (netAssetOfBtc < 0)
                throw new IllegalArgumentException("Net asset of BTC value cannot be less than 0");
            else
                this.netAssetOfBtc = netAssetOfBtc;
            this.repayEnabled = repayEnabled;
            if (totalAsset < 0)
                throw new IllegalArgumentException("Total asset value cannot be less than 0");
            else
                this.totalAsset = totalAsset;
        }

        /**
         * Constructor to init {@link IsolatedMarginAsset} object
         *
         * @param isolateMarginAsset: isolated margin asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public IsolatedMarginAsset(JSONObject isolateMarginAsset) {
            super(isolateMarginAsset);
            borrowEnabled = isolateMarginAsset.getBoolean("borrowEnabled");
            netAssetOfBtc = isolateMarginAsset.getDouble("netAssetOfBtc");
            if (netAssetOfBtc < 0)
                throw new IllegalArgumentException("Net asset of BTC value cannot be less than 0");
            repayEnabled = isolateMarginAsset.getBoolean("repayEnabled");
            totalAsset = isolateMarginAsset.getDouble("totalAsset");
            if (totalAsset < 0)
                throw new IllegalArgumentException("Total asset value cannot be less than 0");
        }

        /**
         * Method to get {@link #borrowEnabled} instance <br>
         * Any params required
         *
         * @return {@link #borrowEnabled} instance as boolean
         **/
        public boolean isBorrowEnabled() {
            return borrowEnabled;
        }

        /**
         * Method to set {@link #borrowEnabled}
         *
         * @param borrowEnabled: if borrow is enabled for asset
         **/
        public void setBorrowEnabled(boolean borrowEnabled) {
            this.borrowEnabled = borrowEnabled;
        }

        /**
         * Method to get {@link #netAssetOfBtc} instance <br>
         * Any params required
         *
         * @return {@link #netAssetOfBtc} instance as double
         **/
        public double getNetAssetOfBtc() {
            return netAssetOfBtc;
        }

        /**
         * Method to set {@link #netAssetOfBtc}
         *
         * @param netAssetOfBtc: net asset of Bitcoin
         * @throws IllegalArgumentException when net asset of Bitcoin value is less than 0
         **/
        public void setNetAssetOfBtc(double netAssetOfBtc) {
            if (netAssetOfBtc < 0)
                throw new IllegalArgumentException("Net asset of BTC value cannot be less than 0");
            this.netAssetOfBtc = netAssetOfBtc;
        }

        /**
         * Method to get {@link #netAssetOfBtc} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #netAssetOfBtc} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getNetAssetOfBtc(int decimals) {
            return roundValue(netAssetOfBtc, decimals);
        }

        /**
         * Method to get {@link #repayEnabled} instance <br>
         * Any params required
         *
         * @return {@link #repayEnabled} instance as boolean
         **/
        public boolean isRepayEnabled() {
            return repayEnabled;
        }

        /**
         * Method to set {@link #repayEnabled}
         *
         * @param repayEnabled: if repay is enabled for asset
         **/
        public void setRepayEnabled(boolean repayEnabled) {
            this.repayEnabled = repayEnabled;
        }

        /**
         * Method to get {@link #totalAsset} instance <br>
         * Any params required
         *
         * @return {@link #totalAsset} instance as double
         **/
        public double getTotalAsset() {
            return totalAsset;
        }

        /**
         * Method to set {@link #totalAsset}
         *
         * @param totalAsset: total asset amount
         * @throws IllegalArgumentException when total asset amount value is less than 0
         **/
        public void setTotalAsset(double totalAsset) {
            if (totalAsset < 0)
                throw new IllegalArgumentException("Total asset value cannot be less than 0");
            this.totalAsset = totalAsset;
        }

        /**
         * Method to get {@link #totalAsset} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalAsset} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalAsset(int decimals) {
            return roundValue(totalAsset, decimals);
        }

    }
    
}
