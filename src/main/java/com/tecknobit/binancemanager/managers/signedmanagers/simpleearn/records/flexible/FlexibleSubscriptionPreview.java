package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class FlexibleSubscriptionPreview extends BinanceItem {

    private final double totalAmount;

    private final String rewardAsset;

    private final String airDropAsset;

    private final double estDailyBonusRewards;

    private final double estDailyRealTimeRewards;

    private final double estDailyAirdropRewards;

    public FlexibleSubscriptionPreview(double totalAmount, String rewardAsset, String airDropAsset,
                                       double estDailyBonusRewards, double estDailyRealTimeRewards,
                                       double estDailyAirdropRewards) {
        super(null);
        this.totalAmount = totalAmount;
        this.rewardAsset = rewardAsset;
        this.airDropAsset = airDropAsset;
        this.estDailyBonusRewards = estDailyBonusRewards;
        this.estDailyRealTimeRewards = estDailyRealTimeRewards;
        this.estDailyAirdropRewards = estDailyAirdropRewards;
    }

    public FlexibleSubscriptionPreview(JSONObject jFlexibleSubscriptionPreview) {
        super(jFlexibleSubscriptionPreview);
        totalAmount = hItem.getDouble("totalAmount", 0);
        rewardAsset = hItem.getString("rewardAsset");
        airDropAsset = hItem.getString("airDropAsset");
        estDailyBonusRewards = hItem.getDouble("estDailyBonusRewards", 0);
        estDailyRealTimeRewards = hItem.getDouble("estDailyRealTimeRewards", 0);
        estDailyAirdropRewards = hItem.getDouble("estDailyAirdropRewards", 0);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getRewardAsset() {
        return rewardAsset;
    }

    public String getAirDropAsset() {
        return airDropAsset;
    }

    public double getEstDailyBonusRewards() {
        return estDailyBonusRewards;
    }

    public double getEstDailyRealTimeRewards() {
        return estDailyRealTimeRewards;
    }

    public double getEstDailyAirdropRewards() {
        return estDailyAirdropRewards;
    }

}
