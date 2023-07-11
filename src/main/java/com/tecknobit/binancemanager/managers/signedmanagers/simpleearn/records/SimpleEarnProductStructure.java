package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

@Structure
public abstract class SimpleEarnProductStructure extends BinanceItem {

    public enum SimpleEarnStatus {

        PURCHASING,

        CREATED,

        PAID,

        SUCCESS

    }

    protected final String asset;

    protected final boolean isSoldOut;

    protected final SimpleEarnStatus status;

    protected final long subscriptionStartTime;

    public SimpleEarnProductStructure(String asset, boolean isSoldOut, SimpleEarnStatus status, long subscriptionStartTime) {
        super(null);
        this.asset = asset;
        this.isSoldOut = isSoldOut;
        this.status = status;
        this.subscriptionStartTime = subscriptionStartTime;
    }

    public SimpleEarnProductStructure(JSONObject jSimpleEarnProductStructure) {
        super(jSimpleEarnProductStructure);
        asset = hItem.getString("asset");
        isSoldOut = hItem.getBoolean("isSoldOut");
        subscriptionStartTime = hItem.getLong("subscriptionStartTime", -1);
        status = SimpleEarnStatus.valueOf(hItem.getString("status"));
    }

    public String getAsset() {
        return asset;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public SimpleEarnStatus getStatus() {
        return status;
    }

    public long getSubscriptionStartTime() {
        return subscriptionStartTime;
    }

}
