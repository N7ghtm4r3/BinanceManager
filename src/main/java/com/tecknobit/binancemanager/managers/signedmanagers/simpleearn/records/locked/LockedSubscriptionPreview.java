package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class LockedSubscriptionPreview extends BinanceItem {

    private final String rewardAsset;

    private final double totalRewardAmount;

    private final String extraRewardAsset;

    private final double estTotalExtraRewardAmount;

    private final double nextPay;

    private final long nextPayDate;

    private final long valueDate;

    private final long rewardsEndDate;

    private final long deliverDate;

    private final long nextSubscriptionDate;

    public LockedSubscriptionPreview(String rewardAsset, double totalRewardAmount, String extraRewardAsset,
                                     double estTotalExtraRewardAmount, double nextPay, long nextPayDate, long valueDate,
                                     long rewardsEndDate, long deliverDate, long nextSubscriptionDate) {
        super(null);
        this.rewardAsset = rewardAsset;
        this.totalRewardAmount = totalRewardAmount;
        this.extraRewardAsset = extraRewardAsset;
        this.estTotalExtraRewardAmount = estTotalExtraRewardAmount;
        this.nextPay = nextPay;
        this.nextPayDate = nextPayDate;
        this.valueDate = valueDate;
        this.rewardsEndDate = rewardsEndDate;
        this.deliverDate = deliverDate;
        this.nextSubscriptionDate = nextSubscriptionDate;
    }

    public LockedSubscriptionPreview(JSONObject jLockedSubscriptionPreview) {
        super(jLockedSubscriptionPreview);
        rewardAsset = hItem.getString("rewardAsset");
        totalRewardAmount = hItem.getDouble("totalRewardAmt", 0);
        extraRewardAsset = hItem.getString("extraRewardAsset");
        estTotalExtraRewardAmount = hItem.getDouble("estTotalExtraRewardAmt", 0);
        nextPay = hItem.getDouble("nextPay", 0);
        nextPayDate = hItem.getLong("nextPayDate", -1);
        valueDate = hItem.getLong("valueDate", -1);
        rewardsEndDate = hItem.getLong("rewardsEndDate", -1);
        deliverDate = hItem.getLong("deliverDate", -1);
        nextSubscriptionDate = hItem.getLong("nextSubscriptionDate", -1);
    }

    public String getRewardAsset() {
        return rewardAsset;
    }

    public double getTotalRewardAmount() {
        return totalRewardAmount;
    }

    public String getExtraRewardAsset() {
        return extraRewardAsset;
    }

    public double getEstTotalExtraRewardAmount() {
        return estTotalExtraRewardAmount;
    }

    public double getNextPay() {
        return nextPay;
    }

    public long getNextPayDate() {
        return nextPayDate;
    }

    public long getValueDate() {
        return valueDate;
    }

    public long getRewardsEndDate() {
        return rewardsEndDate;
    }

    public long getDeliverDate() {
        return deliverDate;
    }

    public long getNextSubscriptionDate() {
        return nextSubscriptionDate;
    }

}
