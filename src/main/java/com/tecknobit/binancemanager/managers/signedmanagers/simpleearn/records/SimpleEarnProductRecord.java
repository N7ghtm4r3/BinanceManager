package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import org.json.JSONObject;

@Structure
public abstract class SimpleEarnProductRecord extends BinanceItem {

    protected final double amount;

    protected final String asset;

    protected final long time;

    protected final SimpleEarnStatus status;

    public SimpleEarnProductRecord(double amount, String asset, long time, SimpleEarnStatus status) {
        super(null);
        this.amount = amount;
        this.asset = asset;
        this.time = time;
        this.status = status;
    }

    public SimpleEarnProductRecord(JSONObject jSimpleEarnProductRecord) {
        super(jSimpleEarnProductRecord);
        amount = hItem.getDouble("amount", 0);
        asset = hItem.getString("asset");
        time = hItem.getLong("time", -1);
        status = SimpleEarnStatus.valueOf(hItem.getString("status"));
    }

    public double getAmount() {
        return amount;
    }

    public String getAsset() {
        return asset;
    }

    public long getTime() {
        return time;
    }

    public SimpleEarnStatus getStatus() {
        return status;
    }

}
