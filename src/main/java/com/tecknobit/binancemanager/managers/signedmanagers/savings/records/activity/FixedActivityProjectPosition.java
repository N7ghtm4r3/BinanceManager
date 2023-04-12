package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity;

import org.json.JSONObject;

public class FixedActivityProjectPosition extends SavingActivityStructure {

    private final boolean canTransfer;
    private final long createTimestamp;
    private final long endTime;
    private final double interest;
    private final int lot;
    private final long positionId;
    private final double principal;
    private final long purchaseTime;
    private final String redeemDate;
    private final long startTime;

    public FixedActivityProjectPosition(String asset, int duration, double interestRate, String projectId,
                                        String projectName, SavingStatus status, SavingActivityType type,
                                        boolean canTransfer, long createTimestamp, long endTime, double interest, int lot,
                                        long positionId, double principal, long purchaseTime, String redeemDate,
                                        long startTime) {
        super(asset, duration, interestRate, projectId, projectName, status, type);
        this.canTransfer = canTransfer;
        this.createTimestamp = createTimestamp;
        this.endTime = endTime;
        this.interest = interest;
        this.lot = lot;
        this.positionId = positionId;
        this.principal = principal;
        this.purchaseTime = purchaseTime;
        this.redeemDate = redeemDate;
        this.startTime = startTime;
    }

    public FixedActivityProjectPosition(JSONObject jFixedActivityProjectPosition) {
        super(jFixedActivityProjectPosition);
        canTransfer = hItem.getBoolean("canTransfer");
        createTimestamp = hItem.getLong("createTimestamp", 0);
        endTime = hItem.getLong("endTime", 0);
        interest = hItem.getDouble("interest", 0);
        lot = hItem.getInt("lot", 0);
        positionId = hItem.getLong("positionId", 0);
        principal = hItem.getDouble("principal", 0);
        purchaseTime = hItem.getLong("purchaseTime", 0);
        redeemDate = hItem.getString("redeemDate");
        startTime = hItem.getLong("startTime", 0);
    }

    public boolean canTransfer() {
        return canTransfer;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public long getEndTime() {
        return endTime;
    }

    public double getInterest() {
        return interest;
    }

    public int getLot() {
        return lot;
    }

    public long getPositionId() {
        return positionId;
    }

    public double getPrincipal() {
        return principal;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public String getRedeemDate() {
        return redeemDate;
    }

    public long getStartTime() {
        return startTime;
    }

}
