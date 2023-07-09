package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PlanStructure} class is useful to format a {@code Binance}'s plan structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
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
 * @see Plan
 */
@Structure
public abstract class PlanStructure extends Plan {

    /**
     * {@code PlanType} list of available plan types
     */
    public enum PlanType {

        /**
         * {@code SINGLE} plan type
         */
        SINGLE,

        /**
         * {@code PORTFOLIO} plan type
         */
        PORTFOLIO,

        /**
         * {@code INDEX} plan type
         */
        INDEX

    }

    /**
     * {@code planType} this is the plan type
     */
    protected final PlanType planType;

    /**
     * {@code editAllowed} whether this plan is allowed to be modified
     */
    protected final boolean editAllowed;

    /**
     * {@code flexibleAllowedToUse} whether this plan is flexible allowed to be used
     */
    protected final boolean flexibleAllowedToUse;

    /**
     * {@code creationDateTime} date time that this plan is created
     */
    protected final long creationDateTime;

    /**
     * {@code firstExecutionDateTime} first subscription date time
     */
    protected final long firstExecutionDateTime;

    /**
     * {@code lastUpdateDateTime} last update date time
     */
    protected final long lastUpdateDateTime;

    /**
     * {@code targetAsset} target asset of the plan created
     */
    protected final String targetAsset;

    /**
     * {@code sourceAsset} source asset of the plan created
     */
    protected final String sourceAsset;

    /**
     * {@code totalInvestedInUSD} total source asset invested in equivalent of USD
     */
    protected final double totalInvestedInUSD;

    /**
     * Constructor to init a {@link PlanStructure} object
     *
     * @param planId:                 for success creation, planId is associated. PlanId remains constant when plan is being updated
     * @param nextExecutionDateTime:  next execution date time
     * @param status:                 status of the plan
     * @param planType:               this is the plan type
     * @param editAllowed:            whether this plan is allowed to be modified
     * @param flexibleAllowedToUse:   whether this plan is flexible allowed to be used
     * @param creationDateTime:       date time that this plan is created
     * @param firstExecutionDateTime: first subscription date time
     * @param lastUpdateDateTime:     last update date time
     * @param targetAsset:            target asset of the plan created
     * @param sourceAsset:            source asset of the plan created
     * @param totalInvestedInUSD:     total source asset invested in equivalent of USD
     */
    public PlanStructure(long planId, long nextExecutionDateTime, PlanStatus status, PlanType planType, boolean editAllowed,
                         boolean flexibleAllowedToUse, long creationDateTime, long firstExecutionDateTime,
                         long lastUpdateDateTime, String targetAsset, String sourceAsset, double totalInvestedInUSD) {
        super(planId, nextExecutionDateTime, status);
        this.planType = planType;
        this.editAllowed = editAllowed;
        this.flexibleAllowedToUse = flexibleAllowedToUse;
        this.creationDateTime = creationDateTime;
        this.firstExecutionDateTime = firstExecutionDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.targetAsset = targetAsset;
        this.sourceAsset = sourceAsset;
        this.totalInvestedInUSD = totalInvestedInUSD;
    }

    /**
     * Constructor to init a {@link PlanStructure} object
     *
     * @param jPlanStructure: plan structure details as {@link JSONObject}
     */
    public PlanStructure(JSONObject jPlanStructure) {
        super(jPlanStructure);
        planType = PlanType.valueOf(hItem.getString("planType"));
        editAllowed = hItem.getBoolean("editAllowed");
        flexibleAllowedToUse = hItem.getBoolean("flexibleAllowedToUse");
        creationDateTime = hItem.getLong("creationDateTime", -1);
        firstExecutionDateTime = hItem.getLong("firstExecutionDateTime", -1);
        lastUpdateDateTime = hItem.getLong("lastUpdatedDateTime", -1);
        targetAsset = hItem.getString("targetAsset");
        sourceAsset = hItem.getString("sourceAsset");
        totalInvestedInUSD = hItem.getDouble("totalInvestedInUSD");
    }

    /**
     * Method to get {@link #planType} instance <br>
     * No-any params required
     *
     * @return {@link #planType} instance as {@link PlanType}
     */
    public PlanType getPlanType() {
        return planType;
    }

    /**
     * Method to get {@link #editAllowed} instance <br>
     * No-any params required
     *
     * @return {@link #editAllowed} instance as boolean
     */
    public boolean isEditAllowed() {
        return editAllowed;
    }

    /**
     * Method to get {@link #flexibleAllowedToUse} instance <br>
     * No-any params required
     *
     * @return {@link #flexibleAllowedToUse} instance as boolean
     */
    public boolean isFlexibleAllowedToUse() {
        return flexibleAllowedToUse;
    }

    /**
     * Method to get {@link #creationDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #creationDateTime} instance as long
     */
    public long getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Method to get {@link #creationDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #creationDateTime} instance as {@link Date}
     */
    public Date getCreationDate() {
        return TimeFormatter.getDate(creationDateTime);
    }

    /**
     * Method to get {@link #firstExecutionDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #firstExecutionDateTime} instance as long
     */
    public long getFirstExecutionDateTime() {
        return firstExecutionDateTime;
    }

    /**
     * Method to get {@link #firstExecutionDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #firstExecutionDateTime} instance as {@link Date}
     */
    public Date getFirstExecutionDate() {
        return TimeFormatter.getDate(firstExecutionDateTime);
    }

    /**
     * Method to get {@link #lastUpdateDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #lastUpdateDateTime} instance as long
     */
    public long getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    /**
     * Method to get {@link #lastUpdateDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #lastUpdateDateTime} instance as {@link Date}
     */
    public Date getLastUpdateDate() {
        return TimeFormatter.getDate(lastUpdateDateTime);
    }

    /**
     * Method to get {@link #targetAsset} instance <br>
     * No-any params required
     *
     * @return {@link #targetAsset} instance as {@link String}
     */
    public String getTargetAsset() {
        return targetAsset;
    }

    /**
     * Method to get {@link #sourceAsset} instance <br>
     * No-any params required
     *
     * @return {@link #sourceAsset} instance as {@link String}
     */
    public String getSourceAsset() {
        return sourceAsset;
    }

    /**
     * Method to get {@link #totalInvestedInUSD} instance <br>
     * No-any params required
     *
     * @return {@link #totalInvestedInUSD} instance as double
     */
    public double getTotalInvestedInUSD() {
        return totalInvestedInUSD;
    }

    /**
     * Method to get {@link #totalInvestedInUSD} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalInvestedInUSD} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalInvestedInUSD(int decimals) {
        return roundValue(totalInvestedInUSD, decimals);
    }

}
