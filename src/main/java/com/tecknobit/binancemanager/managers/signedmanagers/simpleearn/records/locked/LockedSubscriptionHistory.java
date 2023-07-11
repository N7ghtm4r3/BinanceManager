package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleSubscriptionHistory.FlexibleSubscriptionRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedSubscriptionHistory.LockedSubscriptionRecord;

/**
 * The {@code LockedSubscriptionHistory} class is useful to format a {@code Binance}'s locked subscription history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-record-user_data">
 * Get Locked Subscription Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class LockedSubscriptionHistory extends BinanceRowsList<LockedSubscriptionRecord> {

    /**
     * Constructor to init {@link LockedSubscriptionHistory} object
     *
     * @param rows : list of the items
     */
    public LockedSubscriptionHistory(ArrayList<LockedSubscriptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link LockedSubscriptionHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedSubscriptionHistory(int total, ArrayList<LockedSubscriptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link LockedSubscriptionHistory}
     *
     * @param jLockedSubscriptionHistory : list details as {@link JSONObject}
     */
    public LockedSubscriptionHistory(JSONObject jLockedSubscriptionHistory) {
        super(jLockedSubscriptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedSubscriptionRecord((JSONObject) row));
    }

    /**
     * The {@code LockedSubscriptionRecord} class is useful to format a {@code Binance}'s locked subscription record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class LockedSubscriptionRecord extends FlexibleSubscriptionRecord {

        /**
         * {@code positionId} position identifier
         */
        private final long positionId;

        /**
         * {@code lockPeriod} lock period of the subscription
         */
        private final int lockPeriod;

        /**
         * Constructor to init a {@link LockedSubscriptionRecord} object
         *
         * @param amount:     amount of the locked subscription record
         * @param asset:      asset of the locked subscription record
         * @param time:       time of the locked subscription record
         * @param status:     status of the locked subscription record
         * @param purchaseId: purchase identifier
         * @param type:       type of the subscription
         * @param positionId: position identifier
         * @param lockPeriod: lock period of the subscription
         */
        public LockedSubscriptionRecord(double amount, String asset, long time, SimpleEarnStatus status, long purchaseId,
                                        StakingPositionType type, long positionId, int lockPeriod) {
            super(amount, asset, time, status, purchaseId, type);
            this.positionId = positionId;
            this.lockPeriod = lockPeriod;
        }

        /**
         * Constructor to init a {@link LockedSubscriptionRecord} object
         *
         * @param jLockedSubscriptionRecord: locked subscription record details as {@link JSONObject}
         */
        public LockedSubscriptionRecord(JSONObject jLockedSubscriptionRecord) {
            super(jLockedSubscriptionRecord);
            positionId = hItem.getLong("positionId", 0);
            lockPeriod = hItem.getInt("lockPeriod", 0);
        }

        /**
         * Method to get {@link #positionId} instance <br>
         * No-any params required
         *
         * @return {@link #positionId} instance as long
         */
        public long getPositionId() {
            return positionId;
        }

        /**
         * Method to get {@link #lockPeriod} instance <br>
         * No-any params required
         *
         * @return {@link #lockPeriod} instance as int
         */
        public int getLockPeriod() {
            return lockPeriod;
        }

    }

}
