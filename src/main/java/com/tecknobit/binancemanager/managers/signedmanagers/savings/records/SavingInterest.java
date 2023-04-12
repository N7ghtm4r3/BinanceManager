package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.SavingActivityStructure.SavingActivityType;
import org.json.JSONObject;

public class SavingInterest extends SavingStructure {

    private final double interest;
    private final SavingActivityType lendingType;
    private final String productName;
    private final long time;

    public SavingInterest(String asset, double interest, SavingActivityType lendingType, String productName, long time) {
        super(asset);
        this.interest = interest;
        this.lendingType = lendingType;
        this.productName = productName;
        this.time = time;
    }

    public SavingInterest(JSONObject jSavingInterest) {
        super(jSavingInterest);
        interest = hItem.getDouble("interest", 0);
        lendingType = SavingActivityType.valueOf(hItem.getString("lendingType"));
        productName = hItem.getString("productName");
        time = hItem.getLong("time", 0);
    }

    public double getInterest() {
        return interest;
    }

    public SavingActivityType getLendingType() {
        return lendingType;
    }

    public String getProductName() {
        return productName;
    }

    public long getTime() {
        return time;
    }

}
