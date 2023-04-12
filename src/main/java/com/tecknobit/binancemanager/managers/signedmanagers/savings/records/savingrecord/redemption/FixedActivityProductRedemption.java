package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption;

import org.json.JSONObject;

public class FixedActivityProductRedemption extends RedemptionRecord {

    private final double interest;
    private final long startTime;

    public FixedActivityProductRedemption(String asset, double amount, long createTime, String status, double principal,
                                          String projectId, String projectName, double interest, long startTime) {
        super(asset, amount, createTime, status, principal, projectId, projectName);
        this.interest = interest;
        this.startTime = startTime;
    }

    public FixedActivityProductRedemption(JSONObject jFixedActivityProductRedemption) {
        super(jFixedActivityProductRedemption);
        interest = hItem.getDouble("interest", 0);
        startTime = hItem.getLong("startTime", 0);
    }

    public double getInterest() {
        return interest;
    }

    public long getStartTime() {
        return startTime;
    }

}
