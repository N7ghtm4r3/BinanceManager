package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleSubscriptionHistory.FlexibleSubscriptionRecord;

public class FlexibleSubscriptionHistory extends BinanceRowsList<FlexibleSubscriptionRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public FlexibleSubscriptionHistory(ArrayList<FlexibleSubscriptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public FlexibleSubscriptionHistory(int total, ArrayList<FlexibleSubscriptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jFlexibleSubscriptionHistory : list details as {@link JSONObject}
     */
    public FlexibleSubscriptionHistory(JSONObject jFlexibleSubscriptionHistory) {
        super(jFlexibleSubscriptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new FlexibleSubscriptionRecord((JSONObject) row));
    }

    public static class FlexibleSubscriptionRecord extends SimpleEarnProductRecord {

        protected final long purchaseId;

        protected final StakingPositionType type;

        public FlexibleSubscriptionRecord(double amount, String asset, long time, SimpleEarnStatus status, long purchaseId,
                                          StakingPositionType type) {
            super(amount, asset, time, status);
            this.purchaseId = purchaseId;
            this.type = type;
        }

        public FlexibleSubscriptionRecord(JSONObject jFlexibleSubscriptionRecord) {
            super(jFlexibleSubscriptionRecord);
            purchaseId = hItem.getLong("purchaseId");
            type = StakingPositionType.valueOf(hItem.getString("type"));
        }

        public long getPurchaseId() {
            return purchaseId;
        }

        public StakingPositionType getType() {
            return type;
        }

    }

}


