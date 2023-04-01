package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class CollateralRate extends BinanceItem {

    private final String asset;
    private final double collateralRate;

    public CollateralRate(String asset, double collateralRate) {
        super(null);
        this.asset = asset;
        this.collateralRate = collateralRate;
    }

    public CollateralRate(JSONObject jCollateralRate) {
        super(jCollateralRate);
        asset = hItem.getString("asset");
        collateralRate = hItem.getDouble("collateralRate", 0);
    }

    public String getAsset() {
        return asset;
    }

    public double getCollateralRate() {
        return collateralRate;
    }

}
