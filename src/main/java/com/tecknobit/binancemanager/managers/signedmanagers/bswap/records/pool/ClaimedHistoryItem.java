package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import org.json.JSONObject;

public class ClaimedHistoryItem extends PoolStructure {

    private final String assetRewards;
    private final long claimTime;
    private final double claimAmount;
    private final BSwapStatus status;

    public ClaimedHistoryItem(long poolId, String poolName, String assetRewards, long claimTime, double claimAmount,
                              BSwapStatus status) {
        super(poolId, poolName);
        this.assetRewards = assetRewards;
        this.claimTime = claimTime;
        this.claimAmount = claimAmount;
        this.status = status;
    }

    public ClaimedHistoryItem(JSONObject jClaimedHistoryItem) {
        super(jClaimedHistoryItem);
        assetRewards = hItem.getString("assetRewards");
        claimTime = hItem.getLong("claimTime", 0);
        claimAmount = hItem.getDouble("claimAmount", 0);
        status = BSwapStatus.reachEnumConstant(hItem.getInt("status", 0));
    }

    public String getAssetRewards() {
        return assetRewards;
    }

    public long getClaimTime() {
        return claimTime;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public BSwapStatus getStatus() {
        return status;
    }

}
