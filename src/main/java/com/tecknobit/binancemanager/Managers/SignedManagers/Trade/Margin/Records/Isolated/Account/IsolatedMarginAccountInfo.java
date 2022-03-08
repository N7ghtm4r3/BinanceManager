package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class IsolatedMarginAccountInfo {

    public static final String MARGIN_LEVEL_STATUS_EXCESSIVE = "EXCESSIVE";
    public static final String MARGIN_LEVEL_STATUS_NORMAL = "NORMAL";
    public static final String MARGIN_LEVEL_STATUS_MARGIN_CALL = "MARGIN_CALL";
    public static final String MARGIN_LEVEL_STATUS_PRE_LIQUIDATION = "PRE_LIQUIDATION";
    public static final String MARGIN_LEVEL_STATUS_FORCE_LIQUIDATION = "FORCE_LIQUIDATION";
    private final String symbol;
    private final boolean isolatedCreated;
    private final boolean enabled;
    private final double marginLevel;
    private final String marginLevelStatus;
    private final double marginRatio;
    private final double indexPrice;
    private final double liquidatePrice;
    private final double liquidateRate;
    private final boolean tradeEnabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public double getMarginLevel() {
        return marginLevel;
    }

    public String getMarginLevelStatus() {
        return marginLevelStatus;
    }

    public double getMarginRatio() {
        return marginRatio;
    }

    public double getIndexPrice() {
        return indexPrice;
    }

    public double getLiquidatePrice() {
        return liquidatePrice;
    }

    public double getLiquidateRate() {
        return liquidateRate;
    }

    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    public IsolatedMarginAsset getBaseAsset() {
        return baseAsset;
    }

    public IsolatedMarginAsset getQuoteAsset() {
        return quoteAsset;
    }

}
