package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation;

import org.json.JSONObject;

public class BLVTRedemption extends BLVTOperation {

    private final double netProceed;

    public BLVTRedemption(long id, String tokenName, double amount, long timestamp, double nav, double fee,
                          double netProceed) {
        super(id, tokenName, amount, timestamp, nav, fee);
        this.netProceed = netProceed;
    }

    public BLVTRedemption(JSONObject jBLVTRedemption) {
        super(jBLVTRedemption);
        netProceed = hItem.getDouble("netProceed", 0);
    }

    public double getNetProceed() {
        return netProceed;
    }

}
