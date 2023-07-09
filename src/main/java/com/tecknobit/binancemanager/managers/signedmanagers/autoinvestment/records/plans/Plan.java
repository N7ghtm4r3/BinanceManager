package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code Plan} class is useful to format a {@code Binance}'s plan
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-creation-user_data">
 *             Investment plan creation(USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-adjustment-trade">
 *             Investment plan adjustment (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
 *             Change Plan Status (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
 *             Get list of plans (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
 *             Query holding details of the plan (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
public class Plan extends BinanceItem {

    /**
     * {@code SourceType} list of available source types
     */
    public enum SourceType {

        /**
         * {@code MAIN_SITE} source type
         */
        MAIN_SITE,

        /**
         * {@code TR} source type
         */
        TR

    }

    /**
     * {@code PlanStatus} list of available plan statuses
     */
    public enum PlanStatus {

        /**
         * {@code ONGOING} plan status
         */
        ONGOING,

        /**
         * {@code PAUSED} plan status
         */
        PAUSED,

        /**
         * {@code REMOVED} plan status
         */
        REMOVED

    }

    /**
     * {@code SubscriptionCycle} list of available subscription cycles
     */
    public enum SubscriptionCycle {

        /**
         * {@code H1} subscription cycle
         */
        H1,

        /**
         * {@code H4} subscription cycle
         */
        H4,

        /**
         * {@code H8} subscription cycle
         */
        H8,

        /**
         * {@code H12} subscription cycle
         */
        H12,

        /**
         * {@code WEEKLY} subscription cycle
         */
        WEEKLY,

        /**
         * {@code DAILY} subscription cycle
         */
        DAILY,

        /**
         * {@code MONTHLY} subscription cycle
         */
        MONTHLY,

        /**
         * {@code BI_WEEKLY} subscription cycle
         */
        BI_WEEKLY

    }

    /**
     * {@code SubscriptionStartWeekday} list of available subscription start weekdays
     */
    public enum SubscriptionStartWeekday {

        /**
         * {@code MON} subscription start weekday
         */
        MON,

        /**
         * {@code TUE} subscription start weekday
         */
        TUE,

        /**
         * {@code WED} subscription start weekday
         */
        WED,

        /**
         * {@code THU} subscription start weekday
         */
        THU,

        /**
         * {@code FRI} subscription start weekday
         */
        FRI,

        /**
         * {@code SAT} subscription start weekday
         */
        SAT,

        /**
         * {@code SUN} subscription start weekday
         */
        SUN

    }

    /**
     * {@code SourceWallet} list of available wallet sources
     */
    public enum SourceWallet {

        /**
         * {@code SPOT} wallet source
         */
        SPOT,

        /**
         * {@code SPOT_WALLET} wallet source
         */
        SPOT_WALLET,

        /**
         * {@code FLEXIBLE_SAVINGS} wallet source
         */
        FLEXIBLE_SAVINGS,

        /**
         * {@code SPOT_WALLET_FLEXIBLE_SAVINGS} wallet source
         */
        SPOT_WALLET_FLEXIBLE_SAVINGS,

        /**
         * {@code REWARDS} wallet source
         */
        REWARDS

    }

    /**
     * {@code planId} for success creation, planId is associated. PlanId remains constant when plan is being updated
     */
    protected final long planId;

    /**
     * {@code nextExecutionDateTime} next execution date time
     */
    protected final long nextExecutionDateTime;

    /**
     * {@code status} of the plan
     */
    protected final PlanStatus status;

    /**
     * Constructor to init a {@link Plan} object
     *
     * @param planId:                for success creation, planId is associated. PlanId remains constant when plan is being updated
     * @param nextExecutionDateTime: next execution date time
     * @param status:                status of the plan
     */
    public Plan(long planId, long nextExecutionDateTime, PlanStatus status) {
        super(null);
        this.planId = planId;
        this.nextExecutionDateTime = nextExecutionDateTime;
        this.status = status;
    }

    /**
     * Constructor to init a {@link Plan} object
     *
     * @param jPlan: plan details as {@link JSONObject}
     */
    public Plan(JSONObject jPlan) {
        super(jPlan);
        planId = hItem.getLong("planId");
        nextExecutionDateTime = hItem.getLong("nextExecutionDateTime");
        String sStatus = hItem.getString("status");
        if (sStatus != null)
            status = PlanStatus.valueOf(sStatus);
        else
            status = null;
    }

    /**
     * Method to get {@link #planId} instance <br>
     * No-any params required
     *
     * @return {@link #planId} instance as long
     */
    public long getPlanId() {
        return planId;
    }

    /**
     * Method to get {@link #nextExecutionDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #nextExecutionDateTime} instance as long
     */
    public long getNextExecutionDateTime() {
        return nextExecutionDateTime;
    }

    /**
     * Method to get {@link #nextExecutionDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #nextExecutionDateTime} instance as {@link Date}
     */
    public Date getNextExecutionDate() {
        return TimeFormatter.getDate(nextExecutionDateTime);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link PlanStatus}
     */
    public PlanStatus getStatus() {
        return status;
    }

}
