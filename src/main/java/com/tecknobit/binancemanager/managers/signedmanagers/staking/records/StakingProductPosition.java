package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import org.json.JSONObject;

public class StakingProductPosition extends StakingProduct.StakingDetail {

    public enum StakingPositionType {

        AUTO,
        NORMAL

    }

    private final long positionId;
    private final double amount;
    private final long purchaseTime;
    private final int accrualDays;
    private final double rewardAmt;
    private final String extraRewardAsset;
    private final double extraRewardAPY;
    private final double estExtraRewardAmt;
    private final double nextInterestPay;
    private final long nextInterestPayDate;
    private final int payInterestPeriod;
    private final double redeemAmountEarly;
    private final long interestEndDate;
    private final long deliverDate;
    private final int redeemPeriod;
    private final double redeemingAmt;
    private final long partialAmtDeliverDate;
    private final boolean canRedeemEarly;
    private final StakingPositionType type;
    private final String status;

    public StakingProductPosition(String asset, String rewardAsset, int duration, boolean renewable, double apy,
                                  long positionId, double amount, long purchaseTime, int accrualDays, double rewardAmt,
                                  String extraRewardAsset, double extraRewardAPY, double estExtraRewardAmt,
                                  double nextInterestPay, long nextInterestPayDate, int payInterestPeriod,
                                  double redeemAmountEarly, long interestEndDate, long deliverDate, int redeemPeriod,
                                  double redeemingAmt, long partialAmtDeliverDate, boolean canRedeemEarly,
                                  StakingPositionType type, String status) {
        super(asset, rewardAsset, duration, renewable, apy);
        this.positionId = positionId;
        this.amount = amount;
        this.purchaseTime = purchaseTime;
        this.accrualDays = accrualDays;
        this.rewardAmt = rewardAmt;
        this.extraRewardAsset = extraRewardAsset;
        this.extraRewardAPY = extraRewardAPY;
        this.estExtraRewardAmt = estExtraRewardAmt;
        this.nextInterestPay = nextInterestPay;
        this.nextInterestPayDate = nextInterestPayDate;
        this.payInterestPeriod = payInterestPeriod;
        this.redeemAmountEarly = redeemAmountEarly;
        this.interestEndDate = interestEndDate;
        this.deliverDate = deliverDate;
        this.redeemPeriod = redeemPeriod;
        this.redeemingAmt = redeemingAmt;
        this.partialAmtDeliverDate = partialAmtDeliverDate;
        this.canRedeemEarly = canRedeemEarly;
        this.type = type;
        this.status = status;
    }

    public StakingProductPosition(JSONObject jStakingDetail) {
        super(jStakingDetail);
        positionId = hItem.getLong("positionId", 0);
        amount = hItem.getDouble("amount", 0);
        purchaseTime = hItem.getLong("purchaseTime", 0);
        accrualDays = hItem.getInt("accrualDays", 0);
        rewardAmt = hItem.getDouble("rewardAmt", 0);
        extraRewardAsset = hItem.getString("extraRewardAsset");
        extraRewardAPY = hItem.getDouble("extraRewardAPY", 0);
        estExtraRewardAmt = hItem.getDouble("estExtraRewardAmt", 0);
        nextInterestPay = hItem.getDouble("nextInterestPay", 0);
        nextInterestPayDate = hItem.getLong("nextInterestPayDate", 0);
        payInterestPeriod = hItem.getInt("payInterestPeriod", 0);
        redeemAmountEarly = hItem.getDouble("redeemAmountEarly", 0);
        interestEndDate = hItem.getLong("interestEndDate", 0);
        deliverDate = hItem.getLong("deliverDate", 0);
        redeemPeriod = hItem.getInt("redeemPeriod", 0);
        redeemingAmt = hItem.getDouble("redeemingAmt", 0);
        partialAmtDeliverDate = hItem.getLong("partialAmtDeliverDate", 0);
        canRedeemEarly = hItem.getBoolean("canRedeemEarly");
        type = StakingPositionType.valueOf(hItem.getString("type"));
        status = hItem.getString("status");
    }

    public long getPositionId() {
        return positionId;
    }

    public double getAmount() {
        return amount;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public int getAccrualDays() {
        return accrualDays;
    }

    public double getRewardAmt() {
        return rewardAmt;
    }

    public String getExtraRewardAsset() {
        return extraRewardAsset;
    }

    public double getExtraRewardAPY() {
        return extraRewardAPY;
    }

    public double getEstExtraRewardAmt() {
        return estExtraRewardAmt;
    }

    public double getNextInterestPay() {
        return nextInterestPay;
    }

    public long getNextInterestPayDate() {
        return nextInterestPayDate;
    }

    public int getPayInterestPeriod() {
        return payInterestPeriod;
    }

    public double getRedeemAmountEarly() {
        return redeemAmountEarly;
    }

    public long getInterestEndDate() {
        return interestEndDate;
    }

    public long getDeliverDate() {
        return deliverDate;
    }

    public int getRedeemPeriod() {
        return redeemPeriod;
    }

    public double getRedeemingAmt() {
        return redeemingAmt;
    }

    public long getPartialAmtDeliverDate() {
        return partialAmtDeliverDate;
    }

    public boolean canRedeemEarly() {
        return canRedeemEarly;
    }

    public StakingPositionType getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

}
