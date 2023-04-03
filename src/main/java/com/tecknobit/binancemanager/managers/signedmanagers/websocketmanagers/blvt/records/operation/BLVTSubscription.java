package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation;

import org.json.JSONObject;

public class BLVTSubscription extends BLVTOperation {

    private final double totalCharge;

    public BLVTSubscription(long id, String tokenName, double amount, long timestamp, double nav, double fee,
                            double totalCharge) {
        super(id, tokenName, amount, timestamp, nav, fee);
        this.totalCharge = totalCharge;
    }

    public BLVTSubscription(JSONObject jBLVTSubscription) {
        super(jBLVTSubscription);
        totalCharge = hItem.getDouble("totalCharge", 0);
    }

    public double getTotalCharge() {
        return totalCharge;
    }

}
