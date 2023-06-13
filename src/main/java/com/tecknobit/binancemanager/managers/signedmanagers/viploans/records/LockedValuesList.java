package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.LockedValuesList.LockedValue;

/**
 * The {@code LockedValuesList} class is useful to create a locked values list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-locked-value-of-vip-collateral-account-user_data">
 * Check Locked Value of VIP Collateral Account (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class LockedValuesList extends BinanceRowsList<LockedValue> {

    /**
     * Constructor to init {@link LockedValuesList} object
     *
     * @param total        : number of locked values
     * @param lockedValues :  list of the locked values
     */
    public LockedValuesList(int total, ArrayList<LockedValue> lockedValues) {
        super(total, lockedValues);
    }

    /**
     * Constructor to init {@link LockedValuesList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public LockedValuesList(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LockedValue((JSONObject) row));
    }

    /**
     * The {@code LockedValue} class is useful to create a locked value
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class LockedValue extends BinanceItem {

        /**
         * {@code collateralAccountId} collateral account id of the locked value
         */
        private final long collateralAccountId;

        /**
         * {@code collateralCoin} collateral coin of the locked value
         */
        private final String collateralCoin;

        /**
         * {@code collateralValue} collateral value of the locked value
         */
        private final double collateralValue;

        /**
         * Constructor to init {@link LockedValue} object
         *
         * @param collateralAccountId: collateral account id of the locked value
         * @param collateralCoin:      collateral coin of the locked value
         * @param collateralValue:     collateral value of the locked value
         */
        public LockedValue(long collateralAccountId, String collateralCoin, double collateralValue) {
            super(null);
            this.collateralAccountId = collateralAccountId;
            this.collateralCoin = collateralCoin;
            this.collateralValue = collateralValue;
        }

        /**
         * Constructor to init {@link LockedValue} object
         *
         * @param jLockedValue: locked value details as {@link JSONObject}
         */
        public LockedValue(JSONObject jLockedValue) {
            super(jLockedValue);
            collateralAccountId = hItem.getLong("collateralAccountId", 0);
            collateralCoin = hItem.getString("collateralCoin");
            collateralValue = hItem.getDouble("collateralValue", 0);
        }

        /**
         * Method to get {@link #collateralAccountId} instance <br>
         * No-any params required
         *
         * @return {@link #collateralAccountId} instance as long
         */
        public long getCollateralAccountId() {
            return collateralAccountId;
        }

        /**
         * Method to get {@link #collateralCoin} instance <br>
         * No-any params required
         *
         * @return {@link #collateralCoin} instance as {@link String}
         */
        public String getCollateralCoin() {
            return collateralCoin;
        }

        /**
         * Method to get {@link #collateralValue} instance <br>
         * No-any params required
         *
         * @return {@link #collateralValue} instance as double
         */
        public double getCollateralValue() {
            return collateralValue;
        }

        /**
         * Method to get {@link #collateralValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCollateralValue(int decimals) {
            return roundValue(collateralValue, decimals);
        }

    }

}
