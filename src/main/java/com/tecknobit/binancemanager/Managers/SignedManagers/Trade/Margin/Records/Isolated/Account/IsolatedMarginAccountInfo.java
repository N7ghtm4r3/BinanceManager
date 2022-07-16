package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code IsolatedMarginAccountInfo} class is useful to format Binance Isolated Margin Account Info request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginAccountInfo {

    public static final String MARGIN_LEVEL_STATUS_EXCESSIVE = "EXCESSIVE";
    public static final String MARGIN_LEVEL_STATUS_NORMAL = "NORMAL";
    public static final String MARGIN_LEVEL_STATUS_MARGIN_CALL = "MARGIN_CALL";
    public static final String MARGIN_LEVEL_STATUS_PRE_LIQUIDATION = "PRE_LIQUIDATION";
    public static final String MARGIN_LEVEL_STATUS_FORCE_LIQUIDATION = "FORCE_LIQUIDATION";
    private final String symbol;
    private boolean isolatedCreated;
    private boolean enabled;
    private double marginLevel;
    private String marginLevelStatus;
    private double marginRatio;
    private double indexPrice;
    private double liquidatePrice;
    private double liquidateRate;
    private boolean tradeEnabled;
    private final IsolatedMarginAsset baseAsset;
    private final IsolatedMarginAsset quoteAsset;

    public IsolatedMarginAccountInfo(String symbol, boolean isolatedCreated, boolean enabled, double marginLevel,
                                     String marginLevelStatus, double marginRatio, double indexPrice, double liquidatePrice,
                                     double liquidateRate, boolean tradeEnabled, JSONObject jsonObject) {
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
        baseAsset = loadIsolatedMarginAsset(jsonObject.getJSONObject("baseAsset"));
        quoteAsset = loadIsolatedMarginAsset(jsonObject.getJSONObject("quoteAsset"));
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
     * @param jsonArray: obtained from Binance's request
     * @return a list as ArrayList<IsolatedMarginAccountInfo>
     * **/
    public static ArrayList<IsolatedMarginAccountInfo> assembleIsolatedMarginAccountInfoList(JSONArray jsonArray){
        ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfos = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject isolatedInfo = jsonArray.getJSONObject(j);
            isolatedMarginAccountInfos.add(new IsolatedMarginAccountInfo(isolatedInfo.getString("asset"),
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
        return isolatedMarginAccountInfos;
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

    public void setMarginLevel(double marginLevel) {
        this.marginLevel = marginLevel;
    }

    public String getMarginLevelStatus() {
        return marginLevelStatus;
    }

    public void setMarginLevelStatus(String marginLevelStatus) {
        if(marginLevelStatus.equals(MARGIN_LEVEL_STATUS_EXCESSIVE) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_NORMAL) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_FORCE_LIQUIDATION) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_PRE_LIQUIDATION) ||
                marginLevelStatus.equals(MARGIN_LEVEL_STATUS_MARGIN_CALL)) {
            this.marginLevelStatus = marginLevelStatus;
        }else {
            throw new IllegalArgumentException("Margin level status can only be EXCESSIVE, NORMAL, MARGIN_CALL, " +
                    "PRE_LIQUIDATION or FORCE_LIQUIDATION");
        }
    }

    public double getMarginRatio() {
        return marginRatio;
    }

    public void setMarginRatio(double marginRatio) {
        if(marginRatio < 0)
            throw new IllegalArgumentException("Margin ratio value cannot be less than 0");
        this.marginRatio = marginRatio;
    }

    public double getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(double indexPrice) {
        if(indexPrice < 0)
            throw new IllegalArgumentException("Index price value cannot be less than 0");
        this.indexPrice = indexPrice;
    }

    public double getLiquidatePrice() {
        return liquidatePrice;
    }

    public void setLiquidatePrice(double liquidatePrice) {
        if(liquidatePrice < 0)
            throw new IllegalArgumentException("Liquidate price value cannot be less than 0");
        this.liquidatePrice = liquidatePrice;
    }

    public double getLiquidateRate() {
        return liquidateRate;
    }

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
