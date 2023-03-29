package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class CrossCollateralItem extends BinanceItem {


    protected final String coin;
    protected final String collateralCoin;

    public CrossCollateralItem(String coin, String collateralCoin) {
        super(null);
        this.coin = coin;
        this.collateralCoin = collateralCoin;
    }

    public CrossCollateralItem(JSONObject jCrossCollateralItem) {
        super(jCrossCollateralItem);
        coin = hItem.getString("coin");
        collateralCoin = hItem.getString("collateralCoin");
    }

    public String getCoin() {
        return coin;
    }

    public String getCollateralCoin() {
        return collateralCoin;
    }

}
