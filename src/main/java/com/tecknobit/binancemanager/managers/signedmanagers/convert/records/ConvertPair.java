package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ConvertPair extends BinanceItem {

    private final String fromAsset;
    private final String toAsset;
    private final double fromAssetMinAmount;
    private final double fromAssetMaxAmount;
    private final double toAssetMinAmount;
    private final double toAssetMaxAmount;

    public ConvertPair(String fromAsset, String toAsset, double fromAssetMinAmount, double fromAssetMaxAmount,
                       double toAssetMinAmount, double toAssetMaxAmount) {
        super(null);
        this.fromAsset = fromAsset;
        this.toAsset = toAsset;
        this.fromAssetMinAmount = fromAssetMinAmount;
        this.fromAssetMaxAmount = fromAssetMaxAmount;
        this.toAssetMinAmount = toAssetMinAmount;
        this.toAssetMaxAmount = toAssetMaxAmount;
    }

    public ConvertPair(JSONObject jConvertPair) {
        super(jConvertPair);
        fromAsset = hItem.getString("fromAsset");
        toAsset = hItem.getString("toAsset");
        fromAssetMinAmount = hItem.getDouble("fromAssetMinAmount", 0);
        fromAssetMaxAmount = hItem.getDouble("fromAssetMaxAmount", 0);
        toAssetMinAmount = hItem.getDouble("toAssetMinAmount", 0);
        toAssetMaxAmount = hItem.getDouble("toAssetMaxAmount", 0);
    }

    public String getFromAsset() {
        return fromAsset;
    }

    public String getToAsset() {
        return toAsset;
    }

    public double getFromAssetMinAmount() {
        return fromAssetMinAmount;
    }

    public double getFromAssetMaxAmount() {
        return fromAssetMaxAmount;
    }

    public double getToAssetMinAmount() {
        return toAssetMinAmount;
    }

    public double getToAssetMaxAmount() {
        return toAssetMaxAmount;
    }

}
