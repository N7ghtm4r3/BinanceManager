package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedRedemptionHistory.LockedRedemptionRecord;

/**
 * The {@code LockedRedemptionHistory} class is useful to format a {@code Binance}'s locked redemption history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-redemption-record-user_data">
 * Get Locked Redemption Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class LockedRedemptionHistory extends BinanceRowsList<LockedRedemptionRecord> {

    /**
     * Constructor to init {@link LockedRedemptionHistory} object
     *
     * @param rows : list of the items
     */
    public LockedRedemptionHistory(ArrayList<LockedRedemptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link LockedRedemptionHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedRedemptionHistory(int total, ArrayList<LockedRedemptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link LockedRedemptionHistory}
     *
     * @param jLockedRedemptionHistory : list details as {@link JSONObject}
     */
    public LockedRedemptionHistory(JSONObject jLockedRedemptionHistory) {
        super(jLockedRedemptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedRedemptionRecord((JSONObject) row));
    }

    /**
     * The {@code LockedRedemptionRecord} class is useful to format a {@code Binance}'s locked redemption record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SimpleEarnProductRecord
     */
    public static class LockedRedemptionRecord extends SimpleEarnProductRecord {

        /**
         * {@code positionId} position identifier
         */
        private final long positionId;

        /**
         * {@code redeemId} redemption identifier
         */
        private final long redeemId;

        /**
         * {@code lockPeriod} lock period
         */
        private final int lockPeriod;

        /**
         * {@code type} of the redemption
         */
        private final StakingPositionType type;

        /**
         * {@code deliverDate} when the delivery has been request
         */
        private final long deliverDate;

        /**
         * Constructor to init a {@link LockedRedemptionRecord} object
         *
         * @param amount:      amount of the redemption
         * @param asset:       asset of the redemption
         * @param time:        time of the redemption
         * @param status:      status of the redemption
         * @param positionId:  position identifier
         * @param redeemId:    redemption identifier
         * @param lockPeriod:  lock period
         * @param type:        type of the redemption
         * @param deliverDate: when the delivery has been request
         */
        public LockedRedemptionRecord(double amount, String asset, long time, SimpleEarnStatus status, long positionId,
                                      long redeemId, int lockPeriod, StakingPositionType type, long deliverDate) {
            super(amount, asset, time, status);
            this.positionId = positionId;
            this.redeemId = redeemId;
            this.lockPeriod = lockPeriod;
            this.type = type;
            this.deliverDate = deliverDate;
        }

        /**
         * Constructor to init a {@link LockedRedemptionRecord} object
         *
         * @param jLockedRedemptionRecord: locked redemption record details as {@link JSONObject}
         */
        public LockedRedemptionRecord(JSONObject jLockedRedemptionRecord) {
            super(jLockedRedemptionRecord);
            positionId = hItem.getLong("positionId", 0);
            redeemId = hItem.getLong("redeemId", 0);
            lockPeriod = hItem.getInt("lockPeriod", 0);
            type = StakingPositionType.valueOf(hItem.getString("type"));
            deliverDate = hItem.getLong("deliverDate", -1);
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
         * Method to get {@link #redeemId} instance <br>
         * No-any params required
         *
         * @return {@link #redeemId} instance as long
         */
        public long getRedeemId() {
            return redeemId;
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

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link StakingPositionType}
         */
        public StakingPositionType getType() {
            return type;
        }

        /**
         * Method to get {@link #deliverDate} instance <br>
         * No-any params required
         *
         * @return {@link #deliverDate} instance as long
         */
        public long getDeliverTime() {
            return deliverDate;
        }

        /**
         * Method to get {@link #deliverDate} instance <br>
         * No-any params required
         *
         * @return {@link #deliverDate} instance as {@link Date}
         */
        public Date getDeliverDate() {
            return TimeFormatter.getDate(deliverDate);
        }

    }

}
