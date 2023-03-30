package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralLiquidationHistory.CrossCollateralLiquidation;

/**
 * The {@code CrossCollateralLiquidationHistory} class is useful to create a cross collateral liquidation history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-liquidation-history-user_data">
 * Cross-Collateral Liquidation History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class CrossCollateralLiquidationHistory extends BinanceRowsList<CrossCollateralLiquidation> {

    /**
     * Constructor to init {@link CrossCollateralLiquidationHistory} object
     *
     * @param total        : number of liquidations
     * @param liquidations :  list of the liquidations
     **/
    public CrossCollateralLiquidationHistory(int total, ArrayList<CrossCollateralLiquidation> liquidations) {
        super(total, liquidations);
    }

    /**
     * Constructor to init {@link CrossCollateralLiquidationHistory}
     *
     * @param jHistory : liquidation history details as {@link JSONObject}
     **/
    public CrossCollateralLiquidationHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralLiquidation((JSONObject) row));
    }

    /**
     * The {@code CrossCollateralLiquidation} class is useful to create a cross collateral liquidation
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see CrossCollateralItem
     **/
    public static class CrossCollateralLiquidation extends CrossCollateralItem {

        /**
         * {@code collateralAmountForLiquidation} collateral amount for liquidation of the cross collateral liquidation
         **/
        private final double collateralAmountForLiquidation;

        /**
         * {@code forceLiquidationStartTime} force liquidation start time of the cross collateral liquidation
         **/
        private final long forceLiquidationStartTime;

        /**
         * {@code restCollateralAmountAfterLiquidation} rest collateral amount after liquidation of the cross collateral
         * liquidation
         **/
        private final double restCollateralAmountAfterLiquidation;

        /**
         * {@code restLoanAmount} rest loan amount of the cross collateral liquidation
         **/
        private final double restLoanAmount;

        /**
         * {@code status} of the cross collateral liquidation
         **/
        private final Status status;

        /**
         * Constructor to init {@link CrossCollateralLiquidation} object
         *
         * @param coin:                                 coin of the cross collateral
         * @param collateralCoin:                       collateral coin of the cross collateral
         * @param collateralAmountForLiquidation:       collateral amount for liquidation of the cross collateral liquidation
         * @param forceLiquidationStartTime:            force liquidation start time of the cross collateral liquidation
         * @param restCollateralAmountAfterLiquidation: rest collateral amount after liquidation of the cross collateral
         *                                              liquidation
         * @param restLoanAmount:                       rest loan amount of the cross collateral liquidation
         * @param status:                               status of the cross collateral liquidation
         **/
        public CrossCollateralLiquidation(String coin, String collateralCoin, double collateralAmountForLiquidation,
                                          long forceLiquidationStartTime, double restCollateralAmountAfterLiquidation,
                                          double restLoanAmount, Status status) {
            super(coin, collateralCoin);
            this.collateralAmountForLiquidation = collateralAmountForLiquidation;
            this.forceLiquidationStartTime = forceLiquidationStartTime;
            this.restCollateralAmountAfterLiquidation = restCollateralAmountAfterLiquidation;
            this.restLoanAmount = restLoanAmount;
            this.status = status;
        }

        /**
         * Constructor to init {@link CrossCollateralLiquidation} object
         *
         * @param jCrossCollateralLiquidation: cross collateral liquidation details as {@link JSONObject}
         **/
        public CrossCollateralLiquidation(JSONObject jCrossCollateralLiquidation) {
            super(jCrossCollateralLiquidation);
            collateralAmountForLiquidation = hItem.getDouble("collateralAmountForLiquidation", 0);
            forceLiquidationStartTime = hItem.getLong("forceLiquidationStartTime", 0);
            restCollateralAmountAfterLiquidation = hItem.getDouble("restCollateralAmountAfterLiquidation", 0);
            restLoanAmount = hItem.getDouble("restLoanAmount", 0);
            status = Status.valueOf(hItem.getString("status"));
        }

        /**
         * Method to get {@link #collateralAmountForLiquidation} instance <br>
         * No-any params required
         *
         * @return {@link #collateralAmountForLiquidation} instance as double
         **/
        public double getCollateralAmountForLiquidation() {
            return collateralAmountForLiquidation;
        }

        /**
         * Method to get {@link #collateralAmountForLiquidation} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #collateralAmountForLiquidation} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getCollateralAmountForLiquidation(int decimals) {
            return roundValue(collateralAmountForLiquidation, decimals);
        }

        /**
         * Method to get {@link #forceLiquidationStartTime} instance <br>
         * No-any params required
         *
         * @return {@link #forceLiquidationStartTime} instance as long
         **/
        public long getForceLiquidationStartTime() {
            return forceLiquidationStartTime;
        }

        /**
         * Method to get {@link #forceLiquidationStartTime} instance <br>
         * No-any params required
         *
         * @return {@link #forceLiquidationStartTime} instance as {@link Date}
         **/
        public Date getForceLiquidationStartDate() {
            return TimeFormatter.getDate(forceLiquidationStartTime);
        }

        /**
         * Method to get {@link #restCollateralAmountAfterLiquidation} instance <br>
         * No-any params required
         *
         * @return {@link #restCollateralAmountAfterLiquidation} instance as double
         **/
        public double getRestCollateralAmountAfterLiquidation() {
            return restCollateralAmountAfterLiquidation;
        }

        /**
         * Method to get {@link #restCollateralAmountAfterLiquidation} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #restCollateralAmountAfterLiquidation} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getRestCollateralAmountAfterLiquidation(int decimals) {
            return roundValue(restCollateralAmountAfterLiquidation, decimals);
        }

        /**
         * Method to get {@link #restLoanAmount} instance <br>
         * No-any params required
         *
         * @return {@link #restLoanAmount} instance as double
         **/
        public double getRestLoanAmount() {
            return restLoanAmount;
        }

        /**
         * Method to get {@link #restLoanAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #restLoanAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getRestLoanAmount(int decimals) {
            return roundValue(restLoanAmount, decimals);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link Status}
         **/
        public Status getStatus() {
            return status;
        }

    }

}
