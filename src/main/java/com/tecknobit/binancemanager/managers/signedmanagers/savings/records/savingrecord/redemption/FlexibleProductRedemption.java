package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption;

import org.json.JSONObject;

public class FlexibleProductRedemption extends RedemptionRecord {

    public enum RedemptionType {

        FAST,
        NORMAL

    }

    private final RedemptionType type;

    public FlexibleProductRedemption(String asset, double amount, long createTime, String status, double principal,
                                     String projectId, String projectName, RedemptionType type) {
        super(asset, amount, createTime, status, principal, projectId, projectName);
        this.type = type;
    }

    public FlexibleProductRedemption(JSONObject jFlexibleProductRedemption) {
        super(jFlexibleProductRedemption);
        this.type = RedemptionType.valueOf(hItem.getString("type"));
    }

    public RedemptionType getType() {
        return type;
    }

}
