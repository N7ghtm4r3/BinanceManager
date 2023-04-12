package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord;

import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.SavingActivityStructure.SavingActivityType;

public class PurchaseRecord extends SavingRecordStructure {

    private final SavingActivityType lendingType;
    private final int lot;
    private final String productName;
    private final long purchaseId;

    public PurchaseRecord(String asset, double amount, long createTime, String status, SavingActivityType lendingType,
                          int lot, String productName, long purchaseId) {
        super(asset, amount, createTime, status);
        this.lendingType = lendingType;
        this.lot = lot;
        this.productName = productName;
        this.purchaseId = purchaseId;
    }

    public PurchaseRecord(JSONObject jPurchaseRecord) {
        super(jPurchaseRecord);
        lendingType = SavingActivityType.valueOf(hItem.getString("lendingType"));
        lot = hItem.getInt("lot", 0);
        productName = hItem.getString("productName");
        purchaseId = hItem.getLong("purchaseId", 0);
    }

    public SavingActivityType getLendingType() {
        return lendingType;
    }

    public int getLot() {
        return lot;
    }

    public String getProductName() {
        return productName;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

}
