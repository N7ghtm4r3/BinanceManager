package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord;

import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

public abstract class SavingRecordStructure extends SavingStructure {

    protected final double amount;
    protected final long createTime;
    protected final String status;

    public SavingRecordStructure(String asset, double amount, long createTime, String status) {
        super(asset);
        this.amount = amount;
        this.createTime = createTime;
        this.status = status;
    }

    public SavingRecordStructure(JSONObject jSavingRecordStructure) {
        super(jSavingRecordStructure);
        amount = hItem.getDouble("amount", 0);
        createTime = hItem.getLong("createTime", 0);
        status = hItem.getString("status");
    }

    public double getAmount() {
        return amount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getStatus() {
        return status;
    }

}
