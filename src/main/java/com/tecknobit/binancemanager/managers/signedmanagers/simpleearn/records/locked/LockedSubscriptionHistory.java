package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleSubscriptionHistory.FlexibleSubscriptionRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedSubscriptionHistory.LockedSubscriptionRecord;

public class LockedSubscriptionHistory extends BinanceRowsList<LockedSubscriptionRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public LockedSubscriptionHistory(ArrayList<LockedSubscriptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedSubscriptionHistory(int total, ArrayList<LockedSubscriptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jLockedSubscriptionHistory : list details as {@link JSONObject}
     */
    public LockedSubscriptionHistory(JSONObject jLockedSubscriptionHistory) {
        super(jLockedSubscriptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedSubscriptionRecord((JSONObject) row));
    }

    public static class LockedSubscriptionRecord extends FlexibleSubscriptionRecord {

        private final long positionId;

        private final int lockPeriod;

        public LockedSubscriptionRecord(double amount, String asset, long time, SimpleEarnStatus status, long purchaseId,
                                        StakingPositionType type, long positionId, int lockPeriod) {
            super(amount, asset, time, status, purchaseId, type);
            this.positionId = positionId;
            this.lockPeriod = lockPeriod;
        }

        public LockedSubscriptionRecord(JSONObject jLockedSubscriptionRecord) {
            super(jLockedSubscriptionRecord);
            positionId = hItem.getLong("positionId", 0);
            lockPeriod = hItem.getInt("lockPeriod", 0);
        }

        public long getPositionId() {
            return positionId;
        }

        public int getLockPeriod() {
            return lockPeriod;
        }

    }

}
