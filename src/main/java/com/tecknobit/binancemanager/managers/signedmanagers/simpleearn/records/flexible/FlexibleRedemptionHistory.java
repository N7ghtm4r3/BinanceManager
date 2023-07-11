package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleRedemptionHistory.FlexibleRedemptionRecord;

public class FlexibleRedemptionHistory extends BinanceRowsList<FlexibleRedemptionRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public FlexibleRedemptionHistory(ArrayList<FlexibleRedemptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public FlexibleRedemptionHistory(int total, ArrayList<FlexibleRedemptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jFlexibleRedemptionHistory : list details as {@link JSONObject}
     */
    public FlexibleRedemptionHistory(JSONObject jFlexibleRedemptionHistory) {
        super(jFlexibleRedemptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new FlexibleRedemptionRecord((JSONObject) row));
    }

    public static class FlexibleRedemptionRecord extends SimpleEarnProductRecord {

        private final String projectId;

        private final long redeemId;

        public FlexibleRedemptionRecord(double amount, String asset, long time, SimpleEarnStatus status, String projectId,
                                        long redeemId) {
            super(amount, asset, time, status);
            this.projectId = projectId;
            this.redeemId = redeemId;
        }

        public FlexibleRedemptionRecord(JSONObject jFlexibleRedemptionRecord) {
            super(jFlexibleRedemptionRecord);
            projectId = hItem.getString("projectId");
            redeemId = hItem.getLong("redeemId", 0);
        }

        public String getProjectId() {
            return projectId;
        }

        public long getRedeemId() {
            return redeemId;
        }

    }

}
