package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleRedemptionHistory.FlexibleRedemptionRecord;

/**
 * The {@code FlexibleRedemptionHistory} class is useful to format a {@code Binance}'s flexible redemption history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-redemption-record-user_data">
 * Get Flexible Redemption Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class FlexibleRedemptionHistory extends BinanceRowsList<FlexibleRedemptionRecord> {

    /**
     * Constructor to init {@link FlexibleRedemptionHistory} object
     *
     * @param rows : list of the items
     */
    public FlexibleRedemptionHistory(ArrayList<FlexibleRedemptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link FlexibleRedemptionHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public FlexibleRedemptionHistory(int total, ArrayList<FlexibleRedemptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link FlexibleRedemptionHistory}
     *
     * @param jFlexibleRedemptionHistory : list details as {@link JSONObject}
     */
    public FlexibleRedemptionHistory(JSONObject jFlexibleRedemptionHistory) {
        super(jFlexibleRedemptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new FlexibleRedemptionRecord((JSONObject) row));
    }

    /**
     * The {@code FlexibleRedemptionRecord} class is useful to format a {@code Binance}'s flexible redemption record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SimpleEarnProductRecord
     */
    public static class FlexibleRedemptionRecord extends SimpleEarnProductRecord {

        /**
         * {@code projectId} project identifier
         */
        private final String projectId;

        /**
         * {@code redeemId} redemption identifier
         */
        private final long redeemId;

        /**
         * Constructor to init a {@link FlexibleRedemptionRecord} object
         *
         * @param amount:    amount of the redemption
         * @param asset:     asset of the redemption
         * @param time:      time of the redemption
         * @param status:    status of the redemption
         * @param projectId: project identifier
         * @param redeemId:  redemption identifier
         */
        public FlexibleRedemptionRecord(double amount, String asset, long time, SimpleEarnStatus status, String projectId,
                                        long redeemId) {
            super(amount, asset, time, status);
            this.projectId = projectId;
            this.redeemId = redeemId;
        }

        /**
         * Constructor to init a {@link FlexibleRedemptionRecord} object
         *
         * @param jFlexibleRedemptionRecord: flexible redemption record details as {@link JSONObject}
         */
        public FlexibleRedemptionRecord(JSONObject jFlexibleRedemptionRecord) {
            super(jFlexibleRedemptionRecord);
            projectId = hItem.getString("projectId");
            redeemId = hItem.getLong("redeemId", 0);
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
         * Method to get {@link #redeemId} instance <br>
         * No-any params required
         *
         * @return {@link #redeemId} instance as long
         */
        public long getRedeemId() {
            return redeemId;
        }

    }

}
