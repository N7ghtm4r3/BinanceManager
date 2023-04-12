package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class SavingStructure extends BinanceItem {

    public enum SavingStatus {

        PREHEATING,
        PURCHASING,
        RUNNING,
        HOLDING,
        REDEEMED,
        END

    }

    protected final String asset;

    public SavingStructure(String asset) {
        super(null);
        this.asset = asset;
    }

    public SavingStructure(JSONObject jSavingProductStructure) {
        super(jSavingProductStructure);
        asset = hItem.getString("asset");
    }

    public String getAsset() {
        return asset;
    }

}
