package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ETHStakingQuota} class is useful to format a {@code Binance}'s ETH staking quota
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-current-eth-staking-quota-user_data">
 * Get current ETH staking quota (USER_DATA)</a>
 * @see BinanceItem
 */
public class ETHStakingQuota extends BinanceItem {

    /**
     * {@code leftStakingPersonalQuota} show min(Daily available limit, total personal staking quota)
     */
    private final double leftStakingPersonalQuota;

    /**
     * {@code leftRedemptionPersonalQuota} show min(Daily personal redeem quota, total redemption limit)
     */
    private final double leftRedemptionPersonalQuota;

    /**
     * Constructor to init a {@link ETHStakingQuota} object
     *
     * @param leftStakingPersonalQuota:    show min(Daily available limit, total personal staking quota)
     * @param leftRedemptionPersonalQuota: show min(Daily personal redeem quota, total redemption limit)
     */
    public ETHStakingQuota(double leftStakingPersonalQuota, double leftRedemptionPersonalQuota) {
        super(null);
        this.leftStakingPersonalQuota = leftStakingPersonalQuota;
        this.leftRedemptionPersonalQuota = leftRedemptionPersonalQuota;
    }

    /**
     * Constructor to init a {@link ETHStakingQuota} object
     *
     * @param jETHStakingQuota: ETH staking quota details as {@link JSONObject}
     */
    public ETHStakingQuota(JSONObject jETHStakingQuota) {
        super(jETHStakingQuota);
        leftStakingPersonalQuota = hItem.getDouble("leftStakingPersonalQuota", 0);
        leftRedemptionPersonalQuota = hItem.getDouble("leftRedemptionPersonalQuota", 0);
    }

    /**
     * Method to get {@link #leftStakingPersonalQuota} instance <br>
     * No-any params required
     *
     * @return {@link #leftStakingPersonalQuota} instance as double
     */
    public double getLeftStakingPersonalQuota() {
        return leftStakingPersonalQuota;
    }

    /**
     * Method to get {@link #leftStakingPersonalQuota} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #leftStakingPersonalQuota} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLeftStakingPersonalQuota(int decimals) {
        return roundValue(leftStakingPersonalQuota, decimals);
    }

    /**
     * Method to get {@link #leftRedemptionPersonalQuota} instance <br>
     * No-any params required
     *
     * @return {@link #leftRedemptionPersonalQuota} instance as double
     */
    public double getLeftRedemptionPersonalQuota() {
        return leftRedemptionPersonalQuota;
    }

    /**
     * Method to get {@link #leftRedemptionPersonalQuota} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #leftRedemptionPersonalQuota} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLeftRedemptionPersonalQuota(int decimals) {
        return roundValue(leftRedemptionPersonalQuota, decimals);
    }

}
