package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity;

import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

public abstract class SavingActivityStructure extends SavingStructure {

    public enum SavingActivityType {

        DAILY,
        ACTIVITY,
        CUSTOMIZED_FIXED

    }

    protected final int duration;
    protected final double interestRate;
    protected final String projectId;
    protected final String projectName;

    protected final SavingStatus status;
    protected final SavingActivityType type;

    public SavingActivityStructure(String asset, int duration, double interestRate, String projectId, String projectName,
                                   SavingStatus status, SavingActivityType type) {
        super(asset);
        this.duration = duration;
        this.interestRate = interestRate;
        this.projectId = projectId;
        this.projectName = projectName;
        this.status = status;
        this.type = type;
    }

    public SavingActivityStructure(JSONObject jSavingProductStructure) {
        super(jSavingProductStructure);
        duration = hItem.getInt("duration", 0);
        interestRate = hItem.getDouble("interestRate", 0);
        projectId = hItem.getString("projectId");
        projectName = hItem.getString("projectName");
        status = SavingStatus.valueOf(hItem.getString("status"));
        type = SavingActivityType.valueOf(hItem.getString("type"));
    }

    public int getDuration() {
        return duration;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public SavingStatus getStatus() {
        return status;
    }

    public SavingActivityType getType() {
        return type;
    }

}
