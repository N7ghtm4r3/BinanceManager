package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class OrderQuantityPrecision extends BinanceItem {

    private final String asset;
    private final double fraction;

    public OrderQuantityPrecision(String asset, double fraction) {
        super(null);
        this.asset = asset;
        this.fraction = fraction;
    }

    public OrderQuantityPrecision(JSONObject jOrderQuantityPrecision) {
        super(jOrderQuantityPrecision);
        asset = hItem.getString("asset");
        fraction = hItem.getDouble("fraction", 0);
    }

    public String getAsset() {
        return asset;
    }

    public double getFraction() {
        return fraction;
    }

}
