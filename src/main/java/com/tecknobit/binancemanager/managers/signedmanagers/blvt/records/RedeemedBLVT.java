package com.tecknobit.binancemanager.managers.signedmanagers.blvt.records;

import org.json.JSONObject;

public class RedeemedBLVT extends BLVTStructure {

    private final BLVTStatus status;
    private final double redeemAmount;

    public RedeemedBLVT(long id, String tokenName, double amount, long timestamp, BLVTStatus status, double redeemAmount) {
        super(id, tokenName, amount, timestamp);
        this.status = status;
        this.redeemAmount = redeemAmount;
    }

    public RedeemedBLVT(JSONObject jRedeemedBLVT) {
        super(jRedeemedBLVT);
        status = BLVTStatus.valueOf(hItem.getString("status"));
        redeemAmount = hItem.getDouble("redeemAmount", 0);
    }

    public BLVTStatus getStatus() {
        return status;
    }

    public double getRedeemAmount() {
        return redeemAmount;
    }

}
