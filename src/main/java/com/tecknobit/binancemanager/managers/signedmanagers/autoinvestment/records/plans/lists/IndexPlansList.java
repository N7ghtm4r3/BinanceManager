package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.Plan;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.PlanStructure;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists.IndexPlansList.IndexPlan;

/**
 * The {@code IndexPlansList} class is useful to format a {@code Binance}'s index plans list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
 * Get list of plans (USER_DATA)</a>
 * @see BinanceItem
 * @see PlansListStructure
 */
public class IndexPlansList extends PlansListStructure<IndexPlan> {

    /**
     * Constructor to init a {@link IndexPlansList} object
     *
     * @param planValueInUSD: the value of the plan in USD
     * @param planValueInBTC: the value of the plan in BTC
     * @param plans:          list of the plans
     */
    public IndexPlansList(double planValueInUSD, double planValueInBTC, ArrayList<IndexPlan> plans) {
        super(planValueInUSD, planValueInBTC, plans);
    }

    /**
     * Constructor to init a {@link IndexPlansList} object
     *
     * @param jIndexPlansList: index plans list details as {@link JSONObject}
     */
    public IndexPlansList(JSONObject jIndexPlansList) {
        super(jIndexPlansList);
        for (JSONObject item : jPlans)
            plans.add(new IndexPlan(item));
    }

    /**
     * The {@code IndexPlan} class is useful to format a {@code Binance}'s index plan
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see Plan
     * @see PlanStructure
     */
    public static class IndexPlan extends PlanStructure {

        /**
         * {@code subscriptionAmount} subscription amount value
         */
        protected final double subscriptionAmount;

        /**
         * {@code subscriptionCycle} subscription cycle value
         */
        protected final SubscriptionCycle subscriptionCycle;

        /**
         * {@code subscriptionStartDay} subscription start day
         */
        protected final int subscriptionStartDay;

        /**
         * {@code subscriptionStartWeekday} subscription start weekday
         */
        protected final SubscriptionStartWeekday subscriptionStartWeekday;

        /**
         * {@code subscriptionStartTime} subscription start time
         */
        protected final int subscriptionStartTime;

        /**
         * {@code sourceWallet} source wallet
         */
        protected final SourceWallet sourceWallet;

        /**
         * Constructor to init a {@link IndexPlan} object
         *
         * @param planId:                   for success creation, planId is associated. PlanId remains constant when plan is being updated
         * @param nextExecutionDateTime:    next execution date time
         * @param status:                   status of the plan
         * @param planType:                 this is the plan type
         * @param editAllowed:              whether this plan is allowed to be modified
         * @param flexibleAllowedToUse:     whether this plan is flexible allowed to be used
         * @param creationDateTime:         date time that this plan is created
         * @param firstExecutionDateTime:   first subscription date time
         * @param lastUpdateDateTime:       last update date time
         * @param targetAsset:              target asset of the plan created
         * @param sourceAsset:              source asset of the plan created
         * @param totalInvestedInUSD:       total source asset invested in equivalent of USD
         * @param subscriptionAmount:       subscription amount value
         * @param subscriptionCycle:        subscription cycle value
         * @param subscriptionStartDay:     subscription start day
         * @param subscriptionStartWeekday: subscription start weekday
         * @param subscriptionStartTime:    subscription start time
         * @param sourceWallet:             source wallet
         */
        public IndexPlan(long planId, long nextExecutionDateTime, PlanStatus status, PlanType planType, boolean editAllowed,
                         boolean flexibleAllowedToUse, long creationDateTime, long firstExecutionDateTime,
                         long lastUpdateDateTime, String targetAsset, String sourceAsset, double totalInvestedInUSD,
                         double subscriptionAmount, SubscriptionCycle subscriptionCycle, int subscriptionStartDay,
                         SubscriptionStartWeekday subscriptionStartWeekday, int subscriptionStartTime,
                         SourceWallet sourceWallet) {
            super(planId, nextExecutionDateTime, status, planType, editAllowed, flexibleAllowedToUse, creationDateTime,
                    firstExecutionDateTime, lastUpdateDateTime, targetAsset, sourceAsset, totalInvestedInUSD);
            this.subscriptionAmount = subscriptionAmount;
            this.subscriptionCycle = subscriptionCycle;
            this.subscriptionStartDay = subscriptionStartDay;
            this.subscriptionStartWeekday = subscriptionStartWeekday;
            this.subscriptionStartTime = subscriptionStartTime;
            this.sourceWallet = sourceWallet;
        }

        /**
         * Constructor to init a {@link IndexPlan} object
         *
         * @param jPlanIndex: index plan details as {@link JSONObject}
         */
        public IndexPlan(JSONObject jPlanIndex) {
            super(jPlanIndex);
            subscriptionAmount = hItem.getDouble("subscriptionAmount", 0);
            subscriptionCycle = SubscriptionCycle.valueOf(hItem.getString("subscriptionCycle"));
            subscriptionStartDay = hItem.getInt("subscriptionStartDay", 1);
            String sWeekDay = hItem.getString("subscriptionStartWeekday");
            if (sWeekDay != null)
                subscriptionStartWeekday = SubscriptionStartWeekday.valueOf(sWeekDay);
            else
                subscriptionStartWeekday = null;
            subscriptionStartTime = hItem.getInt("subscriptionStartTime", 0);
            sourceWallet = SourceWallet.valueOf(hItem.getString("sourceWallet"));
        }

        /**
         * Method to get {@link #subscriptionAmount} instance <br>
         * No-any params required
         *
         * @return {@link #subscriptionAmount} instance as double
         */
        public double getSubscriptionAmount() {
            return subscriptionAmount;
        }

        /**
         * Method to get {@link #subscriptionAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #subscriptionAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getSubscriptionAmount(int decimals) {
            return roundValue(subscriptionAmount, decimals);
        }

        /**
         * Method to get {@link #subscriptionCycle} instance <br>
         * No-any params required
         *
         * @return {@link #subscriptionCycle} instance as {@link SubscriptionCycle}
         */
        public SubscriptionCycle getSubscriptionCycle() {
            return subscriptionCycle;
        }

        /**
         * Method to get {@link #subscriptionStartDay} instance <br>
         * No-any params required
         *
         * @return {@link #subscriptionStartDay} instance as int
         */
        public int getSubscriptionStartDay() {
            return subscriptionStartDay;
        }

        /**
         * Method to get {@link #subscriptionStartWeekday} instance <br>
         * No-any params required
         *
         * @return {@link #subscriptionStartWeekday} instance as {@link SubscriptionStartWeekday}
         */
        public SubscriptionStartWeekday getSubscriptionStartWeekday() {
            return subscriptionStartWeekday;
        }

        /**
         * Method to get {@link #subscriptionStartTime} instance <br>
         * No-any params required
         *
         * @return {@link #subscriptionStartTime} instance as int
         */
        public int getSubscriptionStartTime() {
            return subscriptionStartTime;
        }

        /**
         * Method to get {@link #sourceWallet} instance <br>
         * No-any params required
         *
         * @return {@link #sourceWallet} instance as {@link SourceWallet}
         */
        public SourceWallet getSourceWallet() {
            return sourceWallet;
        }

    }

}
