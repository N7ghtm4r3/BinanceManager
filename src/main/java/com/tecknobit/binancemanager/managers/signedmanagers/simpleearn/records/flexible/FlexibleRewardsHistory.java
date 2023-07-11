package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleRewardsHistory.FlexibleRewardsRecord;

/**
 * The {@code FlexibleRewardsHistory} class is useful to format a {@code Binance}'s flexible rewards history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-rewards-history-user_data">
 * Get Flexible Rewards History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class FlexibleRewardsHistory extends BinanceRowsList<FlexibleRewardsRecord> {

    /**
     * Constructor to init {@link FlexibleRewardsHistory} object
     *
     * @param rows : list of the items
     */
    public FlexibleRewardsHistory(ArrayList<FlexibleRewardsRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link FlexibleRewardsHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public FlexibleRewardsHistory(int total, ArrayList<FlexibleRewardsRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link FlexibleRewardsHistory}
     *
     * @param jFlexibleRewardsHistory : list details as {@link JSONObject}
     */
    public FlexibleRewardsHistory(JSONObject jFlexibleRewardsHistory) {
        super(jFlexibleRewardsHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new FlexibleRewardsRecord((JSONObject) row));
    }

    /**
     * The {@code FlexibleRewardsRecord} class is useful to format a {@code Binance}'s flexible rewards record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class FlexibleRewardsRecord extends BinanceItem {

        /**
         * {@code FlexibleRewardsType} list of available rewards types
         */
        public enum FlexibleRewardsType {

            /**
             * {@code BONUS} rewards type
             */
            BONUS,

            /**
             * {@code REALTIME} rewards type
             */
            REALTIME,

            /**
             * {@code REWARDS} rewards type
             */
            REWARDS

        }

        /**
         * {@code asset} of the record
         */
        private final String asset;

        /**
         * {@code rewards} of the record
         */
        private final double rewards;

        /**
         * {@code projectId} project identifier
         */
        private final String projectId;

        /**
         * {@code type} of the rewards
         */
        private final FlexibleRewardsType type;

        /**
         * {@code time} when the record has been created
         */
        private final long time;

        /**
         * Constructor to init a {@link FlexibleRewardsRecord} object
         *
         * @param asset:     asset of the record
         * @param rewards:   rewards of the record
         * @param projectId: project identifier
         * @param type:      type of the rewards
         * @param time:      when the record has been created
         */
        public FlexibleRewardsRecord(String asset, double rewards, String projectId, FlexibleRewardsType type, long time) {
            super(null);
            this.asset = asset;
            this.rewards = rewards;
            this.projectId = projectId;
            this.type = type;
            this.time = time;
        }

        /**
         * Constructor to init a {@link FlexibleRewardsRecord} object
         *
         * @param jFlexibleRewardRecord: flexible rewards record details as {@link JSONObject}
         */
        public FlexibleRewardsRecord(JSONObject jFlexibleRewardRecord) {
            super(jFlexibleRewardRecord);
            asset = hItem.getString("asset");
            rewards = hItem.getDouble("rewards", 0);
            projectId = hItem.getString("projectId");
            type = FlexibleRewardsType.valueOf(hItem.getString("type"));
            time = hItem.getLong("time", -1);
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
         * Method to get {@link #rewards} instance <br>
         * No-any params required
         *
         * @return {@link #rewards} instance as double
         */
        public double getRewards() {
            return rewards;
        }

        /**
         * Method to get {@link #rewards} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #rewards} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getRewards(int decimals) {
            return roundValue(rewards, decimals);
        }

        /**
         * Method to get {@link #projectId} instance <br>
         * No-any params required
         *
         * @return {@link #projectId} instance as {@link String}
         */
        public String getProjectId() {
            return projectId;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link FlexibleRewardsType}
         */
        public FlexibleRewardsType getType() {
            return type;
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

    }

}
