package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedRewardsHistory.LockedRewardsRecord;

public class LockedRewardsHistory extends BinanceRowsList<LockedRewardsRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public LockedRewardsHistory(ArrayList<LockedRewardsRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedRewardsHistory(int total, ArrayList<LockedRewardsRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jLockedRewardsHistory : list details as {@link JSONObject}
     */
    public LockedRewardsHistory(JSONObject jLockedRewardsHistory) {
        super(jLockedRewardsHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedRewardsRecord((JSONObject) row));
    }

    public static class LockedRewardsRecord extends BinanceItem {

        private final long positionId;

        private final long time;

        private final String asset;

        private final int lockPeriod;

        private final double amount;

        public LockedRewardsRecord(long positionId, long time, String asset, int lockPeriod, double amount) {
            super(null);
            this.positionId = positionId;
            this.time = time;
            this.asset = asset;
            this.lockPeriod = lockPeriod;
            this.amount = amount;
        }

        public LockedRewardsRecord(JSONObject jLockedRewardsRecord) {
            super(jLockedRewardsRecord);
            positionId = hItem.getLong("positionId", 0);
            time = hItem.getLong("time", -1);
            asset = hItem.getString("asset");
            lockPeriod = hItem.getInt("lockPeriod", 0);
            amount = hItem.getDouble("amount", 0);
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

        public int getLockPeriod() {
            return lockPeriod;
        }

        public double getAmount() {
            return amount;
        }

    }

}
