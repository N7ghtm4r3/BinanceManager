package com.tecknobit.binancemanager.managers.signedmanagers.blvt.records.operation;

import com.tecknobit.binancemanager.managers.signedmanagers.blvt.records.BLVTStructure;
import org.json.JSONObject;

public abstract class BLVTOperation extends BLVTStructure {

    protected final double nav;
    protected final double fee;

    public BLVTOperation(long id, String tokenName, double amount, long timestamp, double nav, double fee) {
        super(id, tokenName, amount, timestamp);
        this.nav = nav;
        this.fee = fee;
    }

    public BLVTOperation(JSONObject jBLVTSubscription) {
        super(jBLVTSubscription);
        nav = hItem.getDouble("nav", 0);
        fee = hItem.getDouble("fee", 0);
    }

    public double getNav() {
        return nav;
    }

    public double getFee() {
        return fee;
    }

}
