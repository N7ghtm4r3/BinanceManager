package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.Plan;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.PlanStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists.IndexPlansList.IndexPlan;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists.SinglePortfolioPlansList.SinglePortfolioPlan;

/**
 * The {@code SinglePortfolioPlansList} class is useful to format a {@code Binance}'s single & portfolio plans list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
 * Get list of plans (USER_DATA)</a>
 * @see BinanceItem
 * @see PlansListStructure
 */
public class SinglePortfolioPlansList extends PlansListStructure<SinglePortfolioPlan> {

    /**
     * {@code pnlInUSD} pnl value in USD
     */
    private final double pnlInUSD;

    /**
     * {@code roi} roi value
     */
    private final double roi;

    /**
     * Constructor to init a {@link SinglePortfolioPlansList} object
     *
     * @param planValueInUSD: the value of the plan in USD
     * @param planValueInBTC: the value of the plan in BTC
     * @param plans:          list of the plans
     * @param pnlInUSD:       pnl value in USD
     * @param roi:            roi value
     */
    public SinglePortfolioPlansList(double planValueInUSD, double planValueInBTC, ArrayList<SinglePortfolioPlan> plans,
                                    double pnlInUSD, double roi) {
        super(planValueInUSD, planValueInBTC, plans);
        this.pnlInUSD = pnlInUSD;
        this.roi = roi;
    }

    /**
     * Constructor to init a {@link SinglePortfolioPlansList} object
     *
     * @param jSinglePortfolioPlansList: single & portfolio plans list details as {@link JSONObject}
     */
    public SinglePortfolioPlansList(JSONObject jSinglePortfolioPlansList) {
        super(jSinglePortfolioPlansList);
        pnlInUSD = hItem.getDouble("pnlInUSD", 0);
        roi = hItem.getDouble("roi", 0);
        for (JSONObject item : jPlans)
            plans.add(new SinglePortfolioPlan(item));
    }

    /**
     * Method to get {@link #pnlInUSD} instance <br>
     * No-any params required
     *
     * @return {@link #pnlInUSD} instance as double
     */
    public double getPnlInUSD() {
        return pnlInUSD;
    }

    /**
     * Method to get {@link #pnlInUSD} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #pnlInUSD} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPnlInUSD(int decimals) {
        return roundValue(pnlInUSD, decimals);
    }

    /**
     * Method to get {@link #roi} instance <br>
     * No-any params required
     *
     * @return {@link #roi} instance as double
     */
    public double getRoi() {
        return roi;
    }

    /**
     * Method to get {@link #roi} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #roi} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRoi(int decimals) {
        return roundValue(roi, decimals);
    }

    /**
     * The {@code SinglePortfolioPlan} class is useful to format a {@code Binance}'s single & portfolio plan
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see Plan
     * @see PlanStructure
     * @see IndexPlan
     */
    public static class SinglePortfolioPlan extends IndexPlan {

        /**
         * {@code totalTargetAmount} total target amount
         */
        private final double totalTargetAmount;

        /**
         * {@code planValueInUSD} plan value in USD
         */
        private final double planValueInUSD;

        /**
         * {@code pnlInUSD} pnl in USD
         */
        private final double pnlInUSD;

        /**
         * {@code roi} roi value
         */
        private final double roi;

        /**
         * Constructor to init a {@link SinglePortfolioPlan} object
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
         * @param totalTargetAmount:        total target amount
         * @param planValueInUSD:           plan value in USD
         * @param pnlInUSD:                 pnl in USD
         * @param roi:                      roi value
         */
        public SinglePortfolioPlan(long planId, long nextExecutionDateTime, PlanStatus status, PlanType planType,
                                   boolean editAllowed, boolean flexibleAllowedToUse, long creationDateTime,
                                   long firstExecutionDateTime, long lastUpdateDateTime, String targetAsset,
                                   String sourceAsset, double totalInvestedInUSD, double subscriptionAmount,
                                   SubscriptionCycle subscriptionCycle, int subscriptionStartDay,
                                   SubscriptionStartWeekday subscriptionStartWeekday, int subscriptionStartTime,
                                   SourceWallet sourceWallet, double totalTargetAmount, double planValueInUSD,
                                   double pnlInUSD, double roi) {
            super(planId, nextExecutionDateTime, status, planType, editAllowed, flexibleAllowedToUse, creationDateTime,
                    firstExecutionDateTime, lastUpdateDateTime, targetAsset, sourceAsset, totalInvestedInUSD,
                    subscriptionAmount, subscriptionCycle, subscriptionStartDay, subscriptionStartWeekday,
                    subscriptionStartTime, sourceWallet);
            this.totalTargetAmount = totalTargetAmount;
            this.planValueInUSD = planValueInUSD;
            this.pnlInUSD = pnlInUSD;
            this.roi = roi;
        }

        /**
         * Constructor to init a {@link SinglePortfolioPlan} object
         *
         * @param jSinglePortfolioPlan: single & portfolio plan details as {@link JSONObject}
         */
        public SinglePortfolioPlan(JSONObject jSinglePortfolioPlan) {
            super(jSinglePortfolioPlan);
            totalTargetAmount = hItem.getDouble("totalTargetAmount", 0);
            planValueInUSD = hItem.getDouble("planValueInUSD", 0);
            pnlInUSD = hItem.getDouble("pnlInUSD", 0);
            roi = hItem.getDouble("roi", 0);
        }

        /**
         * Method to get {@link #totalTargetAmount} instance <br>
         * No-any params required
         *
         * @return {@link #totalTargetAmount} instance as double
         */
        public double getTotalTargetAmount() {
            return totalTargetAmount;
        }

        /**
         * Method to get {@link #totalTargetAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalTargetAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalTargetAmount(int decimals) {
            return roundValue(totalTargetAmount, decimals);
        }

        /**
         * Method to get {@link #planValueInUSD} instance <br>
         * No-any params required
         *
         * @return {@link #planValueInUSD} instance as double
         */
        public double getPlanValueInUSD() {
            return planValueInUSD;
        }

        /**
         * Method to get {@link #planValueInUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #planValueInUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPlanValueInUSD(int decimals) {
            return roundValue(planValueInUSD, decimals);
        }

        /**
         * Method to get {@link #pnlInUSD} instance <br>
         * No-any params required
         *
         * @return {@link #pnlInUSD} instance as double
         */
        public double getPnlInUSD() {
            return pnlInUSD;
        }

        /**
         * Method to get {@link #pnlInUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #pnlInUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPnlInUSD(int decimals) {
            return roundValue(pnlInUSD, decimals);
        }

        /**
         * Method to get {@link #roi} instance <br>
         * No-any params required
         *
         * @return {@link #roi} instance as double
         */
        public double getRoi() {
            return roi;
        }

        /**
         * Method to get {@link #roi} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #roi} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getRoi(int decimals) {
            return roundValue(roi, decimals);
        }

    }

}
