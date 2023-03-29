package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralLiquidationHistory.CrossCollateralLiquidation;

public class CrossCollateralLiquidationHistory extends BinanceRowsList<CrossCollateralLiquidation> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total        : number of items
     * @param liquidations :  list of the items
     **/
    public CrossCollateralLiquidationHistory(int total, ArrayList<CrossCollateralLiquidation> liquidations) {

        super(total, liquidations);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public CrossCollateralLiquidationHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralLiquidation((JSONObject) row));
    }

    public static class CrossCollateralLiquidation extends CrossCollateralItem {

        private final double collateralAmountForLiquidation;
        private final long forceLiquidationStartTime;
        private final double restCollateralAmountAfterLiquidation;
        private final double restLoanAmount;
        private final Status status;

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

        public CrossCollateralLiquidation(JSONObject jCrossCollateralLiquidation) {
            super(jCrossCollateralLiquidation);
            collateralAmountForLiquidation = hItem.getDouble("collateralAmountForLiquidation", 0);
            forceLiquidationStartTime = hItem.getLong("forceLiquidationStartTime", 0);
            restCollateralAmountAfterLiquidation = hItem.getDouble("restCollateralAmountAfterLiquidation", 0);
            restLoanAmount = hItem.getDouble("restLoanAmount", 0);
            status = Status.valueOf(hItem.getString("status"));
        }

        public double getCollateralAmountForLiquidation() {
            return collateralAmountForLiquidation;
        }

        public long getForceLiquidationStartTime() {
            return forceLiquidationStartTime;
        }

        public double getRestCollateralAmountAfterLiquidation() {
            return restCollateralAmountAfterLiquidation;
        }

        public double getRestLoanAmount() {
            return restLoanAmount;
        }

        public Status getStatus() {
            return status;
        }

    }

}
