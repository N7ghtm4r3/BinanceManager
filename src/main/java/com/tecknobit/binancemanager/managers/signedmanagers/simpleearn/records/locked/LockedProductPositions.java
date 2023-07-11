package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked.LockedProductPositions.LockedProductPosition;

/**
 * The {@code LockedProductPositions} class is useful to format a {@code Binance}'s locked product positions
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-locked-product-position-user_data">
 * Get Locked Product Position (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class LockedProductPositions extends BinanceRowsList<LockedProductPosition> {

    /**
     * Constructor to init {@link LockedProductPositions} object
     *
     * @param rows : list of the items
     */
    public LockedProductPositions(ArrayList<LockedProductPosition> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link LockedProductPositions} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public LockedProductPositions(int total, ArrayList<LockedProductPosition> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link LockedProductPositions}
     *
     * @param jLockedProductPositions : list details as {@link JSONObject}
     */
    public LockedProductPositions(JSONObject jLockedProductPositions) {
        super(jLockedProductPositions);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedProductPosition((JSONObject) row));
    }

    /**
     * The {@code LockedProductPosition} class is useful to format a {@code Binance}'s locked product position
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class LockedProductPosition extends BinanceItem {

        /**
         * {@code positionId} position identifier
         */
        private final long positionId;

        /**
         * {@code projectId} project identifier
         */
        private final String projectId;

        /**
         * {@code asset} of the locked product position
         */
        private final String asset;

        /**
         * {@code amount} of the locked product position
         */
        private final double amount;

        /**
         * {@code purchaseTime} when the locked product position has been purchased
         */
        private final long purchaseTime;

        /**
         * {@code duration} of the locked product position
         */
        private final int duration;

        /**
         * {@code accrualDays} accrual days of the locked product position
         */
        private final int accrualDays;

        /**
         * {@code rewardAsset} reward asset of the locked product position
         */
        private final String rewardAsset;

        /**
         * {@code APY} value of the locked product position
         */
        private final double APY;

        /**
         * {@code isRenewable} whether the locked product position is renewable
         */
        private final boolean isRenewable;

        /**
         * {@code isAutoRenew} whether the locked product position is auto-renewable
         */
        private final boolean isAutoRenew;

        /**
         * {@code redeemDate} when the locked product position has been redeemed
         */
        private final long redeemDate;

        /**
         * Constructor to init a {@link LockedProductPosition} object
         *
         * @param positionId:   position identifier
         * @param projectId:    project identifier
         * @param asset:        asset of the locked product position
         * @param amount:       amount of the locked product position
         * @param purchaseTime: when the locked product position has been purchased
         * @param duration:     duration of the locked product position
         * @param accrualDays:  accrual days of the locked product position
         * @param rewardAsset:  reward asset of the locked product position
         * @param APY:          value of the locked product position
         * @param isRenewable:  whether the locked product position is renewable
         * @param isAutoRenew:  whether the locked product position is auto-renewable
         * @param redeemDate:   when the locked product position has been redeemed
         */
        public LockedProductPosition(long positionId, String projectId, String asset, double amount, long purchaseTime,
                                     int duration, int accrualDays, String rewardAsset, double APY, boolean isRenewable,
                                     boolean isAutoRenew, long redeemDate) {
            super(null);
            this.positionId = positionId;
            this.projectId = projectId;
            this.asset = asset;
            this.amount = amount;
            this.purchaseTime = purchaseTime;
            this.duration = duration;
            this.accrualDays = accrualDays;
            this.rewardAsset = rewardAsset;
            this.APY = APY;
            this.isRenewable = isRenewable;
            this.isAutoRenew = isAutoRenew;
            this.redeemDate = redeemDate;
        }

        /**
         * Constructor to init a {@link LockedProductPosition} object
         *
         * @param jLockedProductPosition: locked product position details as {@link JSONObject}
         */
        public LockedProductPosition(JSONObject jLockedProductPosition) {
            super(jLockedProductPosition);
            positionId = hItem.getLong("positionId", 0);
            projectId = hItem.getString("projectId");
            asset = hItem.getString("asset");
            amount = hItem.getDouble("amount", 0);
            purchaseTime = hItem.getLong("purchaseTime", -1);
            duration = hItem.getInt("duration", 0);
            accrualDays = hItem.getInt("accrualDays", 0);
            rewardAsset = hItem.getString("rewardAsset");
            APY = hItem.getDouble("APY", 0);
            isRenewable = hItem.getBoolean("isRenewable");
            isAutoRenew = hItem.getBoolean("isAutoRenew");
            redeemDate = hItem.getLong("redeemDate", -1);
        }

        /**
         * Method to get {@link #positionId} instance <br>
         * No-any params required
         *
         * @return {@link #positionId} instance as long
         */
        public long getPositionId() {
            return positionId;
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
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #purchaseTime} instance <br>
         * No-any params required
         *
         * @return {@link #purchaseTime} instance as long
         */
        public long getPurchaseTime() {
            return purchaseTime;
        }

        /**
         * Method to get {@link #purchaseTime} instance <br>
         * No-any params required
         *
         * @return {@link #purchaseTime} instance as {@link Date}
         */
        public Date getPurchaseDate() {
            return TimeFormatter.getDate(purchaseTime);
        }

        /**
         * Method to get {@link #duration} instance <br>
         * No-any params required
         *
         * @return {@link #duration} instance as int
         */
        public int getDuration() {
            return duration;
        }

        /**
         * Method to get {@link #accrualDays} instance <br>
         * No-any params required
         *
         * @return {@link #accrualDays} instance as int
         */
        public int getAccrualDays() {
            return accrualDays;
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
         * Method to get {@link #APY} instance <br>
         * No-any params required
         *
         * @return {@link #APY} instance as double
         */
        public double getAPY() {
            return APY;
        }

        /**
         * Method to get {@link #APY} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #APY} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAPY(int decimals) {
            return roundValue(APY, decimals);
        }

        /**
         * Method to get {@link #isRenewable} instance <br>
         * No-any params required
         *
         * @return {@link #isRenewable} instance as boolean
         */
        public boolean isRenewable() {
            return isRenewable;
        }

        /**
         * Method to get {@link #isAutoRenew} instance <br>
         * No-any params required
         *
         * @return {@link #isAutoRenew} instance as boolean
         */
        public boolean isAutoRenew() {
            return isAutoRenew;
        }

        /**
         * Method to get {@link #redeemDate} instance <br>
         * No-any params required
         *
         * @return {@link #redeemDate} instance as long
         */
        public long getRedeemTime() {
            return redeemDate;
        }

        /**
         * Method to get {@link #redeemDate} instance <br>
         * No-any params required
         *
         * @return {@link #redeemDate} instance as {@link Date}
         */
        public Date getRedeemDate() {
            return TimeFormatter.getDate(redeemDate);
        }

    }

}
