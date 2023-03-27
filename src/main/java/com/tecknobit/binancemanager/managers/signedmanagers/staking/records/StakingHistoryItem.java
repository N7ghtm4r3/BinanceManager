package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

public class StakingHistoryItem extends BinanceItem {

    private final long positionId;
    private final long time;
    private final String asset;
    private final String project;
    private final double amount;
    private final int lockPeriod;
    private final long deliverDate;
    private final StakingPositionType type;
    private final String status;

    public StakingHistoryItem(long positionId, long time, String asset, String project, double amount, int lockPeriod,
                              long deliverDate, StakingPositionType type, String status) {
        super(null);
        this.positionId = positionId;
        this.time = time;
        this.asset = asset;
        this.project = project;
        this.amount = amount;
        this.lockPeriod = lockPeriod;
        this.deliverDate = deliverDate;
        this.type = type;
        this.status = status;
    }

    public StakingHistoryItem(JSONObject jStakingHistoryItem) {
        super(jStakingHistoryItem);
        positionId = hItem.getLong("positionId", 0);
        time = hItem.getLong("time", 0);
        asset = hItem.getString("asset");
        project = hItem.getString("project");
        amount = hItem.getDouble("amount", 0);
        lockPeriod = hItem.getInt("lockPeriod", 0);
        deliverDate = hItem.getLong("deliverDate", 0);
        String sType = hItem.getString("type");
        if (sType != null)
            type = StakingPositionType.valueOf(sType);
        else
            type = null;
        status = hItem.getString("status");
    }

    public long getPositionId() {
        return positionId;
    }

    public long getTime() {
        return time;
    }

    public String getAsset() {
        return asset;
    }

    public String getProject() {
        return project;
    }

    public double getAmount() {
        return amount;
    }

    public int getLockPeriod() {
        return lockPeriod;
    }

    public long getDeliverDate() {
        return deliverDate;
    }

    public StakingPositionType getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

}
