package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code IsolatedMarginAccountInfo} class is useful to format Binance Isolated Margin Account Info request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

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
     * {@code isolatedCreated} is instance that if isolated has been created
     * **/
    private boolean isolatedCreated;

    /**
     * {@code enabled} is instance that if order has been enabled
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
     * {@code tradeEnabled} is instance that if trade has been enabled
     * **/
    private boolean tradeEnabled;

    /**
     * {@code baseAsset} is instance that base asset
     * **/
    private final IsolatedMarginAsset baseAsset;

    /**
     * {@code quoteAsset} is instance that quote asset
     * **/
    private final IsolatedMarginAsset quoteAsset;

    /** Constructor to init {@link IsolatedMarginAccountInfo} object
     * @param symbol: symbol used in the order
     * @param isolatedCreated: isolated has been created
     * @param enabled: order has been enabled
     * @param marginLevel: margin level
     * @param marginLevelStatus: margin status level
     * @param marginRatio: margin ratio
     * @param indexPrice: index price
     * @param liquidatePrice: liquidate price
     * @param liquidateRate: liquidate rate
     * @param tradeEnabled: trade enable
     * @param jsonAccount: time of order trade
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public IsolatedMarginAccountInfo(String symbol, boolean isolatedCreated, boolean enabled, double marginLevel,
                                     String marginLevelStatus, double marginRatio, double indexPrice, double liquidatePrice,
                                     double liquidateRate, boolean tradeEnabled, JSONObject jsonAccount) {
        this.symbol = symbol;
        this.isolatedCreated = isolatedCreated;
        this.enabled = enabled;
        if(marginLevel < 0)
            throw new IllegalArgumentException("Margin level value cannot be less than 0");
        else
            this.marginLevel = marginLevel;
        if(marginLevelStatusIsValid(marginLevelStatus))
            this.marginLevelStatus = marginLevelStatus;
        else {
            throw new IllegalArgumentException("Margin level status can only be EXCESSIVE, NORMAL, MARGIN_CALL, " +
                    "PRE_LIQUIDATION or FORCE_LIQUIDATION");
        }
        if(marginRatio < 0)
            throw new IllegalArgumentException("Margin ratio value cannot be less than 0");
        else
            this.marginRatio = marginRatio;
        if(indexPrice < 0)
            throw new IllegalArgumentException("Index price value cannot be less than 0");
        else
            this.indexPrice = indexPrice;
        if(liquidatePrice < 0)
            throw new IllegalArgumentException("Liquidate price value cannot be less than 0");
        else
            this.liquidatePrice = liquidatePrice;
        if(liquidateRate < 0)
            throw new IllegalArgumentException("Liquidate rate value cannot be less than 0");
        else
            this.liquidateRate = liquidateRate;
        this.tradeEnabled = tradeEnabled;
        baseAsset = loadIsolatedMarginAsset(jsonAccount.getJSONObject("baseAsset"));
        quoteAsset = loadIsolatedMarginAsset(jsonAccount.getJSONObject("quoteAsset"));
    }

    /** Method to assemble a IsolatedMarginAsset object
     * @param asset: obtained from Binance's request
     * @return {@link IsolatedMarginAsset} assembled
     * **/
    private IsolatedMarginAsset loadIsolatedMarginAsset(JSONObject asset) {
        return new IsolatedMarginAsset(asset.getString("asset"),
                asset.getBoolean("borrowEnabled"),
                asset.getDouble("borrowed"),
                asset.getDouble("free"),
                asset.getDouble("interest"),
                asset.getDouble("locked"),
                asset.getDouble("netAsset"),
                asset.getDouble("netAssetOfBtc"),
                asset.getBoolean("repayEnabled"),
                asset.getDouble("totalAsset")
        );
    }

    /** Method to assemble a IsolatedMarginAccountInfo list
     * @param jsonInfo: obtained from Binance's request
     * @return a list as ArrayList<IsolatedMarginAccountInfo>
     * **/
    public static ArrayList<IsolatedMarginAccountInfo> assembleIsolatedMarginAccountInfoList(JSONArray jsonInfo){
        ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfo = new ArrayList<>();
        for (int j = 0; j < jsonInfo.length(); j++){
            JSONObject isolatedInfo = jsonInfo.getJSONObject(j);
            isolatedMarginAccountInfo.add(new IsolatedMarginAccountInfo(isolatedInfo.getString("asset"),
                    isolatedInfo.getBoolean("isolatedCreated"),
                    isolatedInfo.getBoolean("enabled"),
                    isolatedInfo.getDouble("marginLevel"),
                    isolatedInfo.getString("marginLevelStatus"),
                    isolatedInfo.getDouble("marginRatio"),
                    isolatedInfo.getDouble("indexPrice"),
                    isolatedInfo.getDouble("liquidatePrice"),
                    isolatedInfo.getDouble("liquidateRate"),
                    isolatedInfo.getBoolean("tradeEnabled"),
                    isolatedInfo
            ));
        }
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

    /** Method to set {@link #marginLevel}
     * @param marginLevel: margin level
     * @throws IllegalArgumentException when margin level value is less than 0
     * **/
    public void setMarginLevel(double marginLevel) {
        if(marginLevel < 0)
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

    /** Method to check {@link #marginLevelStatus} validity
     * @param marginLevelStatus: margin status level
     * @throws IllegalArgumentException when margin level status is not valid
     * **/
    private boolean marginLevelStatusIsValid(String marginLevelStatus){
        return marginLevelStatus.equals(MARGIN_LEVEL_STATUS_EXCESSIVE) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_NORMAL) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_FORCE_LIQUIDATION) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_PRE_LIQUIDATION) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_MARGIN_CALL);
    }

    public double getMarginRatio() {
        return marginRatio;
    }

    /** Method to set {@link #marginRatio}
     * @param marginRatio: margin ratio
     * @throws IllegalArgumentException when margin ratio value is less than 0
     * **/
    public void setMarginRatio(double marginRatio) {
        if(marginRatio < 0)
            throw new IllegalArgumentException("Margin ratio value cannot be less than 0");
        this.marginRatio = marginRatio;
    }

    public double getIndexPrice() {
        return indexPrice;
    }

    /** Method to set {@link #indexPrice}
     * @param indexPrice: commission asset of margin account trade
     * @throws IllegalArgumentException when index price value is less than 0
     * **/
    public void setIndexPrice(double indexPrice) {
        if(indexPrice < 0)
            throw new IllegalArgumentException("Index price value cannot be less than 0");
        this.indexPrice = indexPrice;
    }

    public double getLiquidatePrice() {
        return liquidatePrice;
    }

    /** Method to set {@link #liquidatePrice}
     * @param liquidatePrice: commission asset of margin account trade
     * @throws IllegalArgumentException when liquidate price value is less than 0
     * **/
    public void setLiquidatePrice(double liquidatePrice) {
        if(liquidatePrice < 0)
            throw new IllegalArgumentException("Liquidate price value cannot be less than 0");
        this.liquidatePrice = liquidatePrice;
    }

    public double getLiquidateRate() {
        return liquidateRate;
    }

    /** Method to set {@link #liquidateRate}
     * @param liquidateRate: commission asset of margin account trade
     * @throws IllegalArgumentException when liquidate rate value is less than 0
     * **/
    public void setLiquidateRate(double liquidateRate) {
        if(liquidateRate < 0)
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

}
