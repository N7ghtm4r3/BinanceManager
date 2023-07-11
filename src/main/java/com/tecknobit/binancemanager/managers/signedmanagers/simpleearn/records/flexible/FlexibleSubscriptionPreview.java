package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FlexibleSubscriptionPreview} class is useful to format a {@code Binance}'s flexible subscription preview
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-subscription-preview-user_data">
 * Get Flexible Subscription Preview (USER_DATA)</a>
 * @see BinanceItem
 */
public class FlexibleSubscriptionPreview extends BinanceItem {

    /**
     * {@code totalAmount} total amount of the subscription preview
     */
    private final double totalAmount;

    /**
     * {@code rewardAsset} reward asset of the subscription preview
     */
    private final String rewardAsset;

    /**
     * {@code airDropAsset} air drop asset of the subscription preview
     */
    private final String airDropAsset;

    /**
     * {@code estDailyBonusRewards} est daily bonus rewards of the subscription preview
     */
    private final double estDailyBonusRewards;

    /**
     * {@code estDailyRealTimeRewards} est daily realtime rewards of the subscription preview
     */
    private final double estDailyRealTimeRewards;

    /**
     * {@code estDailyAirdropRewards} est daily airdrop rewards of the subscription preview
     */
    private final double estDailyAirdropRewards;

    /**
     * Constructor to init a {@link FlexibleSubscriptionPreview} object
     *
     * @param totalAmount:             total amount of the subscription preview
     * @param rewardAsset:             reward asset of the subscription preview
     * @param airDropAsset:            air drop asset of the subscription preview
     * @param estDailyBonusRewards:    est daily bonus rewards of the subscription preview
     * @param estDailyRealTimeRewards: est daily realtime rewards of the subscription preview
     * @param estDailyAirdropRewards:  est daily airdrop rewards of the subscription preview
     */
    public FlexibleSubscriptionPreview(double totalAmount, String rewardAsset, String airDropAsset,
                                       double estDailyBonusRewards, double estDailyRealTimeRewards,
                                       double estDailyAirdropRewards) {
        super(null);
        this.totalAmount = totalAmount;
        this.rewardAsset = rewardAsset;
        this.airDropAsset = airDropAsset;
        this.estDailyBonusRewards = estDailyBonusRewards;
        this.estDailyRealTimeRewards = estDailyRealTimeRewards;
        this.estDailyAirdropRewards = estDailyAirdropRewards;
    }

    /**
     * Constructor to init a {@link FlexibleSubscriptionPreview} object
     *
     * @param jFlexibleSubscriptionPreview: flexible subscription preview details as {@link JSONObject}
     */
    public FlexibleSubscriptionPreview(JSONObject jFlexibleSubscriptionPreview) {
        super(jFlexibleSubscriptionPreview);
        totalAmount = hItem.getDouble("totalAmount", 0);
        rewardAsset = hItem.getString("rewardAsset");
        airDropAsset = hItem.getString("airDropAsset");
        estDailyBonusRewards = hItem.getDouble("estDailyBonusRewards", 0);
        estDailyRealTimeRewards = hItem.getDouble("estDailyRealTimeRewards", 0);
        estDailyAirdropRewards = hItem.getDouble("estDailyAirdropRewards", 0);
    }

    /**
     * Method to get {@link #totalAmount} instance <br>
     * No-any params required
     *
     * @return {@link #totalAmount} instance as double
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Method to get {@link #totalAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalAmount(int decimals) {
        return roundValue(totalAmount, decimals);
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
     * Method to get {@link #airDropAsset} instance <br>
     * No-any params required
     *
     * @return {@link #airDropAsset} instance as {@link String}
     */
    public String getAirDropAsset() {
        return airDropAsset;
    }

    /**
     * Method to get {@link #estDailyBonusRewards} instance <br>
     * No-any params required
     *
     * @return {@link #estDailyBonusRewards} instance as double
     */
    public double getEstDailyBonusRewards() {
        return estDailyBonusRewards;
    }

    /**
     * Method to get {@link #estDailyBonusRewards} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #estDailyBonusRewards} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getEstDailyBonusRewards(int decimals) {
        return roundValue(estDailyBonusRewards, decimals);
    }

    /**
     * Method to get {@link #estDailyRealTimeRewards} instance <br>
     * No-any params required
     *
     * @return {@link #estDailyRealTimeRewards} instance as double
     */
    public double getEstDailyRealTimeRewards() {
        return estDailyRealTimeRewards;
    }

    /**
     * Method to get {@link #estDailyRealTimeRewards} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #estDailyRealTimeRewards} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getEstDailyRealTimeRewards(int decimals) {
        return roundValue(estDailyRealTimeRewards, decimals);
    }

    /**
     * Method to get {@link #estDailyAirdropRewards} instance <br>
     * No-any params required
     *
     * @return {@link #estDailyAirdropRewards} instance as double
     */
    public double getEstDailyAirdropRewards() {
        return estDailyAirdropRewards;
    }

    /**
     * Method to get {@link #estDailyAirdropRewards} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #estDailyAirdropRewards} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getEstDailyAirdropRewards(int decimals) {
        return roundValue(estDailyAirdropRewards, decimals);
    }

}
