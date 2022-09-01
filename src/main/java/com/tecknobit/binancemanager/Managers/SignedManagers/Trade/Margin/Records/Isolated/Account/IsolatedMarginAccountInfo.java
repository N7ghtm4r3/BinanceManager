package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.MarginAccountSnapshot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code IsolatedMarginAccountInfo} class is useful to format Binance Isolated Margin Account Info request response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 **/

public class IsolatedMarginAccountInfo {

    /**
     * {@code MARGIN_LEVEL_STATUS_EXCESSIVE} is constant for excessive level status
     * **/
    public static final String MARGIN_LEVEL_STATUS_EXCESSIVE = "EXCESSIVE";

    /**
     * {@code NORMAL} is constant for normal level status
     * **/
    public static final String MARGIN_LEVEL_STATUS_NORMAL = "NORMAL";

    /**
     * {@code MARGIN_CALL} is constant for margin call level status
     * **/
    public static final String MARGIN_LEVEL_STATUS_MARGIN_CALL = "MARGIN_CALL";

    /**
     * {@code PRE_LIQUIDATION} is constant for pre liquidation level status
     * **/
    public static final String MARGIN_LEVEL_STATUS_PRE_LIQUIDATION = "PRE_LIQUIDATION";

    /**
     * {@code FORCE_LIQUIDATION} is constant for force liquidation level status
     * **/
    public static final String MARGIN_LEVEL_STATUS_FORCE_LIQUIDATION = "FORCE_LIQUIDATION";

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     * **/
    private final String symbol;

    /**
     * {@code isolatedCreated} is instance that memorizes if isolated has been created
     * **/
    private boolean isolatedCreated;

    /**
     * {@code enabled} is instance that memorizes f order has been enabled
     * **/
    private boolean enabled;

    /**
     * {@code marginLevel} is instance that memorizes margin level
     * **/
    private double marginLevel;

    /**
     * {@code marginLevelStatus} is instance that memorizes margin status level
     * **/
    private String marginLevelStatus;

    /**
     * {@code marginRatio} is instance that memorizes margin ratio
     * **/
    private double marginRatio;

    /**
     * {@code indexPrice} is instance that memorizes index price
     * **/
    private double indexPrice;

    /**
     * {@code liquidatePrice} is instance that memorizes liquidate price
     * **/
    private double liquidatePrice;

    /**
     * {@code liquidateRate} is instance that memorizes liquidate rate
     * **/
    private double liquidateRate;

    /**
     * {@code baseAsset} is instance that memorizes base asset
     **/
    private final IsolatedMarginAsset baseAsset;
    /**
     * {@code quoteAsset} is instance that memorizes quote asset
     **/
    private final IsolatedMarginAsset quoteAsset;
    /**
     * {@code tradeEnabled} is flag that checks if trade has been enabled
     **/
    private boolean tradeEnabled;

    /**
     * Constructor to init {@link IsolatedMarginAccountInfo} object
     *
     * @param symbol:            symbol used in the order
     * @param isolatedCreated:   isolated has been created
     * @param enabled:           order has been enabled
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
                                     String marginLevelStatus, double marginRatio, double indexPrice, double liquidatePrice,
                                     double liquidateRate, boolean tradeEnabled, IsolatedMarginAsset baseAsset,
                                     IsolatedMarginAsset quoteAsset) {
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
        marginLevelStatus = isolatedMarginAccountInfo.getString("marginLevelStatus");
        if (!marginLevelStatusIsValid(marginLevelStatus)) {
            throw new IllegalArgumentException("Margin level status can only be EXCESSIVE, NORMAL, MARGIN_CALL, " +
                    "PRE_LIQUIDATION or FORCE_LIQUIDATION");
        }
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
     * Method to assemble an IsolatedMarginAccountInfo list
     *
     * @param jsonInfo: obtained from Binance's request
     * @return a list as ArrayList<IsolatedMarginAccountInfo>
     **/
    public static ArrayList<IsolatedMarginAccountInfo> createIsolatedMarginAccountInfoList(JSONArray jsonInfo) {
        ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfo = new ArrayList<>();
        for (int j = 0; j < jsonInfo.length(); j++)
            isolatedMarginAccountInfo.add(new IsolatedMarginAccountInfo(jsonInfo.getJSONObject(j)));
        return isolatedMarginAccountInfo;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isIsolatedCreated() {
        return isolatedCreated;
    }

    public void setIsolatedCreated(boolean isolatedCreated) {
        this.isolatedCreated = isolatedCreated;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getMarginLevel() {
        return marginLevel;
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

    public String getMarginLevelStatus() {
        return marginLevelStatus;
    }

    /** Method to set {@link #marginLevelStatus}
     * @param marginLevelStatus: margin status level
     * @throws IllegalArgumentException when margin level status is not valid
     * **/
    public void setMarginLevelStatus(String marginLevelStatus) {
        if(marginLevelStatusIsValid(marginLevelStatus))
            this.marginLevelStatus = marginLevelStatus;
        else {
            throw new IllegalArgumentException("Margin level status can only be EXCESSIVE, NORMAL, MARGIN_CALL, " +
                    "PRE_LIQUIDATION or FORCE_LIQUIDATION");
        }
    }

    /**
     * Method to check {@link #marginLevelStatus} validity
     *
     * @param marginLevelStatus: margin status level
     * @return validity of margin level status as boolean
     **/
    private boolean marginLevelStatusIsValid(String marginLevelStatus) {
        return marginLevelStatus.equals(MARGIN_LEVEL_STATUS_EXCESSIVE) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_NORMAL) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_FORCE_LIQUIDATION) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_PRE_LIQUIDATION) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_MARGIN_CALL);
    }

    public double getMarginRatio() {
        return marginRatio;
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

    public double getIndexPrice() {
        return indexPrice;
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

    public double getLiquidatePrice() {
        return liquidatePrice;
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

    public double getLiquidateRate() {
        return liquidateRate;
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

    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    public void setTradeEnabled(boolean tradeEnabled) {
        this.tradeEnabled = tradeEnabled;
    }

    public IsolatedMarginAsset getBaseAsset() {
        return baseAsset;
    }

    public IsolatedMarginAsset getQuoteAsset() {
        return quoteAsset;
    }

    @Override
    public String toString() {
        return "IsolatedMarginAccountInfo{" +
                "symbol='" + symbol + '\'' +
                ", isolatedCreated=" + isolatedCreated +
                ", enabled=" + enabled +
                ", marginLevel=" + marginLevel +
                ", marginLevelStatus='" + marginLevelStatus + '\'' +
                ", marginRatio=" + marginRatio +
                ", indexPrice=" + indexPrice +
                ", liquidatePrice=" + liquidatePrice +
                ", liquidateRate=" + liquidateRate +
                ", tradeEnabled=" + tradeEnabled +
                ", baseAsset=" + baseAsset +
                ", quoteAsset=" + quoteAsset +
                '}';
    }

    /**
     * The {@code IsolatedMarginAsset} class is useful to create an isolated margin asset object
     **/

    public static class IsolatedMarginAsset extends MarginAccountSnapshot.UserMarginAsset {

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

        public boolean isBorrowEnabled() {
            return borrowEnabled;
        }

        public void setBorrowEnabled(boolean borrowEnabled) {
            this.borrowEnabled = borrowEnabled;
        }

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

        public boolean isRepayEnabled() {
            return repayEnabled;
        }

        public void setRepayEnabled(boolean repayEnabled) {
            this.repayEnabled = repayEnabled;
        }

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

        @Override
        public String toString() {
            return "IsolatedMarginAsset{" +
                    "borrowEnabled=" + borrowEnabled +
                    ", netAssetOfBtc=" + netAssetOfBtc +
                    ", repayEnabled=" + repayEnabled +
                    ", totalAsset=" + totalAsset +
                    ", borrowed=" + borrowed +
                    ", interest=" + interest +
                    ", netAsset=" + netAsset +
                    ", asset='" + asset + '\'' +
                    ", free=" + free +
                    ", locked=" + locked +
                    '}';
        }

    }
    
}
