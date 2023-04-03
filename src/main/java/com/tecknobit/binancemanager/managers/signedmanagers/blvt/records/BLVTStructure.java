package com.tecknobit.binancemanager.managers.signedmanagers.blvt.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class BLVTStructure extends BinanceItem {

    public enum BLVTStatus {

        S,
        P,
        F

    }

    protected final long id;
    protected final String tokenName;
    protected final double amount;
    protected final long timestamp;

    public BLVTStructure(long id, String tokenName, double amount, long timestamp) {
        super(null);
        this.id = id;
        this.tokenName = tokenName;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BLVTStructure(JSONObject jBLVTStructure) {
        super(null);
        id = hItem.getLong("id", 0);
        tokenName = hItem.getString("tokenName");
        amount = hItem.getDouble("amount", 0);
        timestamp = hItem.getLong("timestamp", 0);
    }

    public long getId() {
        return id;
    }

    public String getTokenName() {
        return tokenName;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
