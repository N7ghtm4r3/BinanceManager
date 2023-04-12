package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity;

import org.json.JSONObject;

public class FixedActivityProject extends SavingActivityStructure {

    private final int displayPriority;
    private final double interestPerLot;
    private final double lotSize;
    private final int lotsLowLimit;
    private final int lotsPurchased;
    private final int lotsUpLimit;
    private final int maxLotsPerUser;
    private final boolean needKyc;
    private final boolean withAreaLimitation;

    public FixedActivityProject(String asset, int duration, double interestRate, String projectId, String projectName,
                                SavingStatus status, SavingActivityType type, int displayPriority, double interestPerLot,
                                double lotSize, int lotsLowLimit, int lotsPurchased, int lotsUpLimit, int maxLotsPerUser,
                                boolean needKyc, boolean withAreaLimitation) {
        super(asset, duration, interestRate, projectId, projectName, status, type);
        this.displayPriority = displayPriority;
        this.interestPerLot = interestPerLot;
        this.lotSize = lotSize;
        this.lotsLowLimit = lotsLowLimit;
        this.lotsPurchased = lotsPurchased;
        this.lotsUpLimit = lotsUpLimit;
        this.maxLotsPerUser = maxLotsPerUser;
        this.needKyc = needKyc;
        this.withAreaLimitation = withAreaLimitation;
    }

    public FixedActivityProject(JSONObject jFixedActivityProject) {
        super(jFixedActivityProject);
        displayPriority = hItem.getInt("displayPriority", 0);
        interestPerLot = hItem.getDouble("interestPerLot", 0);
        lotSize = hItem.getDouble("lotSize", 0);
        lotsLowLimit = hItem.getInt("lotsLowLimit", 0);
        lotsPurchased = hItem.getInt("lotsPurchased", 0);
        lotsUpLimit = hItem.getInt("lotsUpLimit", 0);
        maxLotsPerUser = hItem.getInt("maxLotsPerUser", 0);
        needKyc = hItem.getBoolean("needKyc");
        withAreaLimitation = hItem.getBoolean("withAreaLimitation");
    }

    public int getDisplayPriority() {
        return displayPriority;
    }

    public double getInterestPerLot() {
        return interestPerLot;
    }

    public double getLotSize() {
        return lotSize;
    }

    public int getLotsLowLimit() {
        return lotsLowLimit;
    }

    public int getLotsPurchased() {
        return lotsPurchased;
    }

    public int getLotsUpLimit() {
        return lotsUpLimit;
    }

    public int getMaxLotsPerUser() {
        return maxLotsPerUser;
    }

    public boolean isNeededKyc() {
        return needKyc;
    }

    public boolean isWithAreaLimitation() {
        return withAreaLimitation;
    }

}
