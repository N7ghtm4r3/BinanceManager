package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralLTVHistory.CrossCollateralLTV;

/**
 * The {@code CrossCollateralLTVHistory} class is useful to create a cross collateral LTV history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-cross-collateral-ltv-history-user_data">
 * Adjust Cross-Collateral LTV History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CrossCollateralLTVHistory extends BinanceRowsList<CrossCollateralLTV> {

    /**
     * Constructor to init {@link CrossCollateralLTVHistory} object
     *
     * @param total            : number of LTVs
     * @param crossCollaterals :  list of the LTVs
     */
    public CrossCollateralLTVHistory(int total, ArrayList<CrossCollateralLTV> crossCollaterals) {
        super(total, crossCollaterals);
    }

    /**
     * Constructor to init {@link CrossCollateralLTVHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public CrossCollateralLTVHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralLTV((JSONObject) row));
    }

    /**
     * The {@code CrossCollateralLTV} class is useful to create a cross collateral LTV
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see CrossCollateralItem
     */
    public static class CrossCollateralLTV extends CrossCollateralItem {

        /**
         * {@code amount} of the cross collateral ltv
         */
        private final double amount;

        /**
         * {@code preCollateralRate} pre collateral rate of the cross collateral ltv
         */
        private final double preCollateralRate;

        /**
         * {@code afterCollateralRate} after collateral rate of the cross collateral ltv
         */
        private final double afterCollateralRate;

        /**
         * {@code direction} of the cross collateral ltv
         */
        private final String direction;

        /**
         * {@code status} of the cross collateral ltv
         */
        private final Status status;

        /**
         * {@code adjustTime} adjust time of the cross collateral ltv
         */
        private final long adjustTime;

        /**
         * Constructor to init {@link CrossCollateralLTV} object
         *
         * @param coin:                coin of the cross collateral
         * @param collateralCoin:      collateral coin of the cross collateral
         * @param amount:              amount of the cross collateral ltv
         * @param preCollateralRate:   pre collateral rate of the cross collateral ltv
         * @param afterCollateralRate: after collateral rate of the cross collateral ltv
         * @param direction:           direction of the cross collateral ltv
         * @param status:              status of the cross collateral ltv
         * @param adjustTime:          adjust time of the cross collateral ltv
         */
        public CrossCollateralLTV(String coin, String collateralCoin, double amount, double preCollateralRate,
                                  double afterCollateralRate, String direction, Status status, long adjustTime) {
            super(coin, collateralCoin);
            this.amount = amount;
            this.preCollateralRate = preCollateralRate;
            this.afterCollateralRate = afterCollateralRate;
            this.direction = direction;
            this.status = status;
            this.adjustTime = adjustTime;
        }

        /**
         * Constructor to init {@link CrossCollateralLTV} object
         *
         * @param jCrossCollateralLTV: cross collateral LTV details as {@link JSONObject}
         */
        public CrossCollateralLTV(JSONObject jCrossCollateralLTV) {
            super(jCrossCollateralLTV);
            amount = hItem.getDouble("amount", 0);
            preCollateralRate = hItem.getDouble("preCollateralRate", 0);
            afterCollateralRate = hItem.getDouble("afterCollateralRate", 0);
            direction = hItem.getString("direction");
            status = Status.valueOf(hItem.getString("status"));
            adjustTime = hItem.getLong("adjustTime", 0);
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
         * Method to get {@link #preCollateralRate} instance <br>
         * No-any params required
         *
         * @return {@link #preCollateralRate} instance as double
         */
        public double getPreCollateralRate() {
            return preCollateralRate;
        }

        /**
         * Method to get {@link #preCollateralRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #preCollateralRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPreCollateralRate(int decimals) {
            return roundValue(preCollateralRate, decimals);
        }

        /**
         * Method to get {@link #afterCollateralRate} instance <br>
         * No-any params required
         *
         * @return {@link #afterCollateralRate} instance as double
         */
        public double getAfterCollateralRate() {
            return afterCollateralRate;
        }

        /**
         * Method to get {@link #afterCollateralRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #afterCollateralRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAfterCollateralRate(int decimals) {
            return roundValue(afterCollateralRate, decimals);
        }

        /**
         * Method to get {@link #direction} instance <br>
         * No-any params required
         *
         * @return {@link #direction} instance as {@link String}
         */
        public String getDirection() {
            return direction;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link Status}
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Method to get {@link #adjustTime} instance <br>
         * No-any params required
         *
         * @return {@link #adjustTime} instance as long
         */
        public long getAdjustTime() {
            return adjustTime;
        }

        /**
         * Method to get {@link #adjustTime} instance <br>
         * No-any params required
         *
         * @return {@link #adjustTime} instance as {@link Date}
         */
        public Date getAdjustDate() {
            return TimeFormatter.getDate(adjustTime);
        }

    }

}
