package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedRewardsHistory.LockedRewardsRecord;

/**
 * The {@code LockedRewardsHistory} class is useful to format a {@code Binance}'s locked rewards history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-rewards-history-user_data">
 * Get Locked Rewards History (USER_DATA)</a>
 * @see BinanceItem
 */
public class LockedRewardsHistory extends BinanceRowsList<LockedRewardsRecord> {

    /**
     * Constructor to init {@link LockedRewardsHistory} object
     *
     * @param rows : list of the items
     */
    public LockedRewardsHistory(ArrayList<LockedRewardsRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link LockedRewardsHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedRewardsHistory(int total, ArrayList<LockedRewardsRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link LockedRewardsHistory}
     *
     * @param jLockedRewardsHistory : list details as {@link JSONObject}
     */
    public LockedRewardsHistory(JSONObject jLockedRewardsHistory) {
        super(jLockedRewardsHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedRewardsRecord((JSONObject) row));
    }

    /**
     * The {@code LockedRewardsRecord} class is useful to format a {@code Binance}'s locked rewards record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class LockedRewardsRecord extends BinanceItem {

        /**
         * {@code positionId} position identifier
         */
        private final long positionId;

        /**
         * {@code time} of the rewards
         */
        private final long time;

        /**
         * {@code asset} of the rewards
         */
        private final String asset;

        /**
         * {@code lockPeriod} lock period of the rewards
         */
        private final int lockPeriod;

        /**
         * {@code amount} of the rewards
         */
        private final double amount;

        /**
         * Constructor to init a {@link LockedRewardsRecord} object
         *
         * @param positionId: position identifier
         * @param time:       time of the rewards
         * @param asset:      asset of the rewards
         * @param lockPeriod: lock period of the rewards
         * @param amount:     amount of the rewards
         */
        public LockedRewardsRecord(long positionId, long time, String asset, int lockPeriod, double amount) {
            super(null);
            this.positionId = positionId;
            this.time = time;
            this.asset = asset;
            this.lockPeriod = lockPeriod;
            this.amount = amount;
        }

        /**
         * Constructor to init a {@link LockedRewardsRecord} object
         *
         * @param jLockedRewardsRecord: locked rewards record details as {@link JSONObject}
         */
        public LockedRewardsRecord(JSONObject jLockedRewardsRecord) {
            super(jLockedRewardsRecord);
            positionId = hItem.getLong("positionId", 0);
            time = hItem.getLong("time", -1);
            asset = hItem.getString("asset");
            lockPeriod = hItem.getInt("lockPeriod", 0);
            amount = hItem.getDouble("amount", 0);
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
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as long
         */
        public long getTime() {
            return time;
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as {@link Date}
         */
        public Date getDate() {
            return TimeFormatter.getDate(time);
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
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
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

    }

}
