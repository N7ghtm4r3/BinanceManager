package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedProductPositions.LockedProductPosition;

public class LockedProductPositions extends BinanceRowsList<LockedProductPosition> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public LockedProductPositions(ArrayList<LockedProductPosition> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedProductPositions(int total, ArrayList<LockedProductPosition> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jLockedProductPositions : list details as {@link JSONObject}
     */
    public LockedProductPositions(JSONObject jLockedProductPositions) {
        super(jLockedProductPositions);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedProductPosition((JSONObject) row));
    }

    public static class LockedProductPosition extends BinanceItem {

        private final long positionId;

        private final String projectId;

        private final String asset;

        private final double amount;

        private final long purchaseTime;

        private final int duration;

        private final int accrualDays;

        private final String rewardAsset;

        private final double APY;

        private final boolean isRenewable;

        private final boolean isAutoRenew;

        private final long redeemDate;

        public LockedProductPosition(long positionId, String projectId, String asset, double amount, long purchaseTime,
                                     int duration, int accrualDays, String rewardAsset, double APY, boolean isRenewable,
                                     boolean isAutoRenew, long redeemDate) {
            super(null);
            this.positionId = positionId;
            this.projectId = projectId;
            this.asset = asset;
            this.amount = amount;
            this.purchaseTime = purchaseTime;
            this.duration = duration;
            this.accrualDays = accrualDays;
            this.rewardAsset = rewardAsset;
            this.APY = APY;
            this.isRenewable = isRenewable;
            this.isAutoRenew = isAutoRenew;
            this.redeemDate = redeemDate;
        }

        public LockedProductPosition(JSONObject jLockedProductPosition) {
            super(jLockedProductPosition);
            positionId = hItem.getLong("positionId", 0);
            projectId = hItem.getString("projectId");
            asset = hItem.getString("asset");
            amount = hItem.getDouble("amount", 0);
            purchaseTime = hItem.getLong("purchaseTime", -1);
            duration = hItem.getInt("duration", 0);
            accrualDays = hItem.getInt("accrualDays", 0);
            rewardAsset = hItem.getString("rewardAsset");
            APY = hItem.getDouble("APY", 0);
            isRenewable = hItem.getBoolean("isRenewable");
            isAutoRenew = hItem.getBoolean("isAutoRenew");
            redeemDate = hItem.getLong("redeemDate", -1);
        }

        public long getPositionId() {
            return positionId;
        }

        public String getProjectId() {
            return projectId;
        }

        public String getAsset() {
            return asset;
        }

        public double getAmount() {
            return amount;
        }

        public long getPurchaseTime() {
            return purchaseTime;
        }

        public int getDuration() {
            return duration;
        }

        public int getAccrualDays() {
            return accrualDays;
        }

        public String getRewardAsset() {
            return rewardAsset;
        }

        public double getAPY() {
            return APY;
        }

        public boolean isRenewable() {
            return isRenewable;
        }

        public boolean isAutoRenew() {
            return isAutoRenew;
        }

        public long getRedeemDate() {
            return redeemDate;
        }

    }

}
