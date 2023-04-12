package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption;

import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.SavingRecordStructure;
import org.json.JSONObject;

public abstract class RedemptionRecord extends SavingRecordStructure {

    protected final double principal;
    protected final String projectId;
    protected final String projectName;

    public RedemptionRecord(String asset, double amount, long createTime, String status, double principal,
                            String projectId, String projectName) {
        super(asset, amount, createTime, status);
        this.principal = principal;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public RedemptionRecord(JSONObject jRedemptionRecord) {
        super(jRedemptionRecord);
        principal = hItem.getDouble("principal", 0);
        projectId = hItem.getString("projectId");
        projectName = hItem.getString("projectName");
    }

    public double getPrincipal() {
        return principal;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

}
