package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleRewardsHistory.FlexibleRewardsRecord;

public class FlexibleRewardsHistory extends BinanceRowsList<FlexibleRewardsRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public FlexibleRewardsHistory(ArrayList<FlexibleRewardsRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public FlexibleRewardsHistory(int total, ArrayList<FlexibleRewardsRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jFlexibleRewardsHistory : list details as {@link JSONObject}
     */
    public FlexibleRewardsHistory(JSONObject jFlexibleRewardsHistory) {
        super(jFlexibleRewardsHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new FlexibleRewardsRecord((JSONObject) row));
    }

    public static class FlexibleRewardsRecord extends BinanceItem {

        public enum FlexibleRewardType {

            BONUS,

            REALTIME,

            REWARDS

        }

        private final String asset;

        private final double rewards;

        private final String projectId;

        private final FlexibleRewardType type;

        private final long time;

        public FlexibleRewardsRecord(String asset, double rewards, String projectId, FlexibleRewardType type, long time) {
            super(null);
            this.asset = asset;
            this.rewards = rewards;
            this.projectId = projectId;
            this.type = type;
            this.time = time;
        }

        public FlexibleRewardsRecord(JSONObject jFlexibleRewardRecord) {
            super(jFlexibleRewardRecord);
            asset = hItem.getString("asset");
            rewards = hItem.getDouble("rewards", 0);
            projectId = hItem.getString("projectId");
            type = FlexibleRewardType.valueOf(hItem.getString("type"));
            time = hItem.getLong("time", -1);
        }

        public String getAsset() {
            return asset;
        }

        public double getRewards() {
            return rewards;
        }

        public String getProjectId() {
            return projectId;
        }

        public FlexibleRewardType getType() {
            return type;
        }

        public long getTime() {
            return time;
        }

    }

}
