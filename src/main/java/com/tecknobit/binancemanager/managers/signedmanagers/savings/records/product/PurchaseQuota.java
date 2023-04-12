package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class PurchaseQuota extends BinanceItem {

    protected final String asset;
    protected final double leftQuota;

    public PurchaseQuota(String asset, double leftQuota) {
        super(null);
        this.asset = asset;
        this.leftQuota = leftQuota;
    }

    public PurchaseQuota(JSONObject jPurchaseQuota) {
        super(jPurchaseQuota);
        asset = hItem.getString("asset");
        leftQuota = hItem.getDouble("leftQuota", 0);
    }

    public String getAsset() {
        return asset;
    }

    public double getLeftQuota() {
        return leftQuota;
    }

}
