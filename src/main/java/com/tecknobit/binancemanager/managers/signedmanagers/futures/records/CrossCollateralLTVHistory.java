package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralLTVHistory.CrossCollateralLTV;

public class CrossCollateralLTVHistory extends BinanceRowsList<CrossCollateralLTV> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total            : number of items
     * @param crossCollaterals :  list of the items
     **/
    public CrossCollateralLTVHistory(int total, ArrayList<CrossCollateralLTV> crossCollaterals) {
        super(total, crossCollaterals);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jHistory : list details as {@link JSONObject}
     **/
    public CrossCollateralLTVHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralLTV((JSONObject) row));
    }

    public static class CrossCollateralLTV extends CrossCollateralItem {

        private final double amount;
        private final double preCollateralRate;
        private final double afterCollateralRate;
        private final String direction;
        private final Status status;
        private final long adjustTime;

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

        public CrossCollateralLTV(JSONObject jCrossCollateralLTV) {
            super(jCrossCollateralLTV);
            amount = hItem.getDouble("amount", 0);
            preCollateralRate = hItem.getDouble("preCollateralRate", 0);
            afterCollateralRate = hItem.getDouble("afterCollateralRate", 0);
            direction = hItem.getString("direction");
            status = Status.valueOf(hItem.getString("status"));
            adjustTime = hItem.getLong("adjustTime", 0);
        }

        public double getAmount() {
            return amount;
        }

        public double getPreCollateralRate() {
            return preCollateralRate;
        }

        public double getAfterCollateralRate() {
            return afterCollateralRate;
        }

        public String getDirection() {
            return direction;
        }

        public Status getStatus() {
            return status;
        }

        public long getAdjustTime() {
            return adjustTime;
        }

    }

}
