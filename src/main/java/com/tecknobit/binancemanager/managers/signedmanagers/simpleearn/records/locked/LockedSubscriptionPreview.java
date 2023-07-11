package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LockedSubscriptionPreview} class is useful to format a {@code Binance}'s locked subscription preview
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-subscription-preview-user_data">
 * Get Locked Subscription Preview (USER_DATA)</a>
 * @see BinanceItem
 */
public class LockedSubscriptionPreview extends BinanceItem {

    /**
     * {@code rewardAsset} reward asset
     */
    private final String rewardAsset;

    /**
     * {@code totalRewardAmount} total reward amount
     */
    private final double totalRewardAmount;

    /**
     * {@code extraRewardAsset} extra reward asset
     */
    private final String extraRewardAsset;

    /**
     * {@code estTotalExtraRewardAmount} est total extra reward amount
     */
    private final double estTotalExtraRewardAmount;

    /**
     * {@code nextPay} next pay value
     */
    private final double nextPay;

    /**
     * {@code nextPayDate} next pay date
     */
    private final long nextPayDate;

    /**
     * {@code valueDate} value of the date
     */
    private final long valueDate;

    /**
     * {@code rewardsEndDate} date of the end of the rewards
     */
    private final long rewardsEndDate;

    /**
     * {@code deliverDate} delivery date
     */
    private final long deliverDate;

    /**
     * {@code nextSubscriptionDate} next subscription date
     */
    private final long nextSubscriptionDate;

    /**
     * Constructor to init a {@link LockedSubscriptionPreview} object
     *
     * @param rewardAsset:               reward asset
     * @param totalRewardAmount:         total reward amount
     * @param extraRewardAsset:          extra reward asset
     * @param estTotalExtraRewardAmount: est total extra reward amount
     * @param nextPay:                   next pay value
     * @param nextPayDate:               next pay date
     * @param valueDate:                 value of the date
     * @param rewardsEndDate:            date of the end of the rewards
     * @param deliverDate:               delivery date
     * @param nextSubscriptionDate:      next subscription date
     */
    public LockedSubscriptionPreview(String rewardAsset, double totalRewardAmount, String extraRewardAsset,
                                     double estTotalExtraRewardAmount, double nextPay, long nextPayDate, long valueDate,
                                     long rewardsEndDate, long deliverDate, long nextSubscriptionDate) {
        super(null);
        this.rewardAsset = rewardAsset;
        this.totalRewardAmount = totalRewardAmount;
        this.extraRewardAsset = extraRewardAsset;
        this.estTotalExtraRewardAmount = estTotalExtraRewardAmount;
        this.nextPay = nextPay;
        this.nextPayDate = nextPayDate;
        this.valueDate = valueDate;
        this.rewardsEndDate = rewardsEndDate;
        this.deliverDate = deliverDate;
        this.nextSubscriptionDate = nextSubscriptionDate;
    }

    /**
     * Constructor to init a {@link LockedSubscriptionPreview} object
     *
     * @param jLockedSubscriptionPreview: locked subscription preview details as {@link JSONObject}
     */
    public LockedSubscriptionPreview(JSONObject jLockedSubscriptionPreview) {
        super(jLockedSubscriptionPreview);
        rewardAsset = hItem.getString("rewardAsset");
        totalRewardAmount = hItem.getDouble("totalRewardAmt", 0);
        extraRewardAsset = hItem.getString("extraRewardAsset");
        estTotalExtraRewardAmount = hItem.getDouble("estTotalExtraRewardAmt", 0);
        nextPay = hItem.getDouble("nextPay", 0);
        nextPayDate = hItem.getLong("nextPayDate", -1);
        valueDate = hItem.getLong("valueDate", -1);
        rewardsEndDate = hItem.getLong("rewardsEndDate", -1);
        deliverDate = hItem.getLong("deliverDate", -1);
        nextSubscriptionDate = hItem.getLong("nextSubscriptionDate", -1);
    }

    /**
     * Method to get {@link #rewardAsset} instance <br>
     * No-any params required
     *
     * @return {@link #rewardAsset} instance as {@link String}
     */
    public String getRewardAsset() {
        return rewardAsset;
    }

    /**
     * Method to get {@link #totalRewardAmount} instance <br>
     * No-any params required
     *
     * @return {@link #totalRewardAmount} instance as double
     */
    public double getTotalRewardAmount() {
        return totalRewardAmount;
    }

    /**
     * Method to get {@link #totalRewardAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalRewardAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalRewardAmount(int decimals) {
        return roundValue(totalRewardAmount, decimals);
    }

    /**
     * Method to get {@link #extraRewardAsset} instance <br>
     * No-any params required
     *
     * @return {@link #extraRewardAsset} instance as {@link String}
     */
    public String getExtraRewardAsset() {
        return extraRewardAsset;
    }

    /**
     * Method to get {@link #estTotalExtraRewardAmount} instance <br>
     * No-any params required
     *
     * @return {@link #estTotalExtraRewardAmount} instance as double
     */
    public double getEstTotalExtraRewardAmount() {
        return estTotalExtraRewardAmount;
    }

    /**
     * Method to get {@link #estTotalExtraRewardAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #estTotalExtraRewardAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getEstTotalExtraRewardAmount(int decimals) {
        return roundValue(estTotalExtraRewardAmount, decimals);
    }

    /**
     * Method to get {@link #nextPay} instance <br>
     * No-any params required
     *
     * @return {@link #nextPay} instance as double
     */
    public double getNextPay() {
        return nextPay;
    }

    /**
     * Method to get {@link #nextPay} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #nextPay} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getNextPay(int decimals) {
        return roundValue(nextPay, decimals);
    }

    /**
     * Method to get {@link #nextPayDate} instance <br>
     * No-any params required
     *
     * @return {@link #nextPayDate} instance as long
     */
    public long getNextPayTime() {
        return nextPayDate;
    }

    /**
     * Method to get {@link #nextPayDate} instance <br>
     * No-any params required
     *
     * @return {@link #nextPayDate} instance as {@link Date}
     */
    public Date getNextPayDate() {
        return TimeFormatter.getDate(nextPayDate);
    }

    /**
     * Method to get {@link #valueDate} instance <br>
     * No-any params required
     *
     * @return {@link #valueDate} instance as long
     */
    public long getValueTime() {
        return valueDate;
    }

    /**
     * Method to get {@link #valueDate} instance <br>
     * No-any params required
     *
     * @return {@link #valueDate} instance as {@link Date}
     */
    public Date getValueDate() {
        return TimeFormatter.getDate(valueDate);
    }

    /**
     * Method to get {@link #rewardsEndDate} instance <br>
     * No-any params required
     *
     * @return {@link #rewardsEndDate} instance as long
     */
    public long getRewardsEndTime() {
        return rewardsEndDate;
    }

    /**
     * Method to get {@link #rewardsEndDate} instance <br>
     * No-any params required
     *
     * @return {@link #rewardsEndDate} instance as {@link Date}
     */
    public Date getRewardsEndDate() {
        return TimeFormatter.getDate(rewardsEndDate);
    }

    /**
     * Method to get {@link #deliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #deliverDate} instance as long
     */
    public long getDeliverTime() {
        return deliverDate;
    }

    /**
     * Method to get {@link #deliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #deliverDate} instance as {@link Date}
     */
    public Date getDeliverDate() {
        return TimeFormatter.getDate(deliverDate);
    }

    /**
     * Method to get {@link #nextSubscriptionDate} instance <br>
     * No-any params required
     *
     * @return {@link #nextSubscriptionDate} instance as long
     */
    public long getNextSubscriptionTime() {
        return nextSubscriptionDate;
    }

    /**
     * Method to get {@link #nextSubscriptionDate} instance <br>
     * No-any params required
     *
     * @return {@link #nextSubscriptionDate} instance as {@link Date}
     */
    public Date getNextSubscriptionDate() {
        return TimeFormatter.getDate(nextSubscriptionDate);
    }

}
