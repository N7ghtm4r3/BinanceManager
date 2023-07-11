package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductRecord;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure.SimpleEarnStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleSubscriptionHistory.FlexibleSubscriptionRecord;

/**
 * The {@code FlexibleSubscriptionHistory} class is useful to format a {@code Binance}'s flexible subscription history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-record-user_data">
 * Get Flexible Subscription Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class FlexibleSubscriptionHistory extends BinanceRowsList<FlexibleSubscriptionRecord> {

    /**
     * Constructor to init {@link FlexibleSubscriptionHistory} object
     *
     * @param rows : list of the items
     */
    public FlexibleSubscriptionHistory(ArrayList<FlexibleSubscriptionRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link FlexibleSubscriptionHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public FlexibleSubscriptionHistory(int total, ArrayList<FlexibleSubscriptionRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link FlexibleSubscriptionHistory}
     *
     * @param jFlexibleSubscriptionHistory : list details as {@link JSONObject}
     */
    public FlexibleSubscriptionHistory(JSONObject jFlexibleSubscriptionHistory) {
        super(jFlexibleSubscriptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new FlexibleSubscriptionRecord((JSONObject) row));
    }

    /**
     * The {@code FlexibleSubscriptionRecord} class is useful to format a {@code Binance}'s flexible subscription record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SimpleEarnProductRecord
     */
    public static class FlexibleSubscriptionRecord extends SimpleEarnProductRecord {

        /**
         * {@code purchaseId} purchase identifier
         */
        protected final long purchaseId;

        /**
         * {@code type} of the subscription
         */
        protected final StakingPositionType type;

        /**
         * Constructor to init a {@link FlexibleSubscriptionRecord} object
         *
         * @param amount:     amount of the flexible subscription record
         * @param asset:      asset of the flexible subscription record
         * @param time:       time of the flexible subscription record
         * @param status:     status of the flexible subscription record
         * @param purchaseId: purchase identifier
         * @param type:       type of the subscription
         */
        public FlexibleSubscriptionRecord(double amount, String asset, long time, SimpleEarnStatus status, long purchaseId,
                                          StakingPositionType type) {
            super(amount, asset, time, status);
            this.purchaseId = purchaseId;
            this.type = type;
        }

        /**
         * Constructor to init a {@link FlexibleSubscriptionRecord} object
         *
         * @param jFlexibleSubscriptionRecord: flexible subscription record details as {@link JSONObject}
         */
        public FlexibleSubscriptionRecord(JSONObject jFlexibleSubscriptionRecord) {
            super(jFlexibleSubscriptionRecord);
            purchaseId = hItem.getLong("purchaseId");
            type = StakingPositionType.valueOf(hItem.getString("type"));
        }

        /**
         * Method to get {@link #purchaseId} instance <br>
         * No-any params required
         *
         * @return {@link #purchaseId} instance as long
         */
        public long getPurchaseId() {
            return purchaseId;
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

    }

}


