package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedRedemptionHistory.LockedRedemptionRecord;

public class LockedRedemptionHistory extends BinanceRowsList<LockedRedemptionRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public LockedRedemptionHistory(ArrayList<LockedRedemptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedRedemptionHistory(int total, ArrayList<LockedRedemptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jLockedRedemptionHistory : list details as {@link JSONObject}
     */
    public LockedRedemptionHistory(JSONObject jLockedRedemptionHistory) {
        super(jLockedRedemptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedRedemptionRecord((JSONObject) row));
    }

    public static class LockedRedemptionRecord extends SimpleEarnProductRecord {

        private final long positionId;

        private final long redeemId;

        private final int lockPeriod;

        private final StakingPositionType type;

        private final long deliverDate;

        public LockedRedemptionRecord(double amount, String asset, long time, SimpleEarnStatus status, long positionId,
                                      long redeemId, int lockPeriod, StakingPositionType type, long deliverDate) {
            super(amount, asset, time, status);
            this.positionId = positionId;
            this.redeemId = redeemId;
            this.lockPeriod = lockPeriod;
            this.type = type;
            this.deliverDate = deliverDate;
        }

        public LockedRedemptionRecord(JSONObject jLockedRedemptionRecord) {
            super(jLockedRedemptionRecord);
            positionId = hItem.getLong("positionId", 0);
            redeemId = hItem.getLong("redeemId", 0);
            lockPeriod = hItem.getInt("lockPeriod", 0);
            type = StakingPositionType.valueOf(hItem.getString("type"));
            deliverDate = hItem.getLong("deliverDate", -1);
        }

        public long getPositionId() {
            return positionId;
        }

        public long getRedeemId() {
            return redeemId;
        }

        public int getLockPeriod() {
            return lockPeriod;
        }

        public StakingPositionType getType() {
            return type;
        }

        public long getDeliverDate() {
            return deliverDate;
        }

    }

}
