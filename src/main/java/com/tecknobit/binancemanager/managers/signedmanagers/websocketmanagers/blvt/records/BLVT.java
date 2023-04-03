package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records;

import org.json.JSONObject;

public class BLVT extends BLVTStructure {

    private final BLVTStatus status;
    private final double cost;

    public BLVT(long id, String tokenName, double amount, long timestamp, BLVTStatus status, double cost) {
        super(id, tokenName, amount, timestamp);
        this.status = status;
        this.cost = cost;
    }

    public BLVT(JSONObject jBLVT) {
        super(jBLVT);
        status = BLVTStatus.valueOf(hItem.getString("status"));
        cost = hItem.getDouble("cost", 0);
    }

    public BLVTStatus getStatus() {
        return status;
    }

    public double getCost() {
        return cost;
    }

}
