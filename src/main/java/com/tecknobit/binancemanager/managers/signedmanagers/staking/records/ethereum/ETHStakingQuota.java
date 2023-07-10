package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ETHStakingQuota extends BinanceItem {

    private final double leftStakingPersonalQuota;

    private final double leftRedemptionPersonalQuota;

    public ETHStakingQuota(double leftStakingPersonalQuota, double leftRedemptionPersonalQuota) {
        super(null);
        this.leftStakingPersonalQuota = leftStakingPersonalQuota;
        this.leftRedemptionPersonalQuota = leftRedemptionPersonalQuota;
    }

    public ETHStakingQuota(JSONObject jETHStakingQuota) {
        super(jETHStakingQuota);
        leftStakingPersonalQuota = hItem.getDouble("leftStakingPersonalQuota", 0);
        leftRedemptionPersonalQuota = hItem.getDouble("leftRedemptionPersonalQuota", 0);
    }

    public double getLeftStakingPersonalQuota() {
        return leftStakingPersonalQuota;
    }

    public double getLeftRedemptionPersonalQuota() {
        return leftRedemptionPersonalQuota;
    }

}
