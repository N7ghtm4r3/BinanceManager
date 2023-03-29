package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralRepaymentsHistory.CrossCollateralRepayment;

public class CrossCollateralRepaymentsHistory extends BinanceRowsList<CrossCollateralRepayment> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total      : number of items
     * @param repayments :  list of the items
     **/
    public CrossCollateralRepaymentsHistory(int total, ArrayList<CrossCollateralRepayment> repayments) {
        super(total, repayments);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jHistory : list details as {@link JSONObject}
     **/
    public CrossCollateralRepaymentsHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralRepayment((JSONObject) row));
    }

    public static class CrossCollateralRepayment extends CrossCollateralItem {

        public enum RepayType {

            NORMAL,
            COLLATERAL

        }

        private final double amount;
        private final RepayType repayType;
        private final double releasedCollateral;
        private final double price;
        private final double repayCollateral;
        private final long confirmedTime;
        private final long updateTime;
        private final Status status;
        private final long repayId;

        public CrossCollateralRepayment(String coin, String collateralCoin, double amount, RepayType repayType,
                                        double releasedCollateral, double price, double repayCollateral,
                                        long confirmedTime, long updateTime, Status status, long repayId) {
            super(coin, collateralCoin);
            this.amount = amount;
            this.repayType = repayType;
            this.releasedCollateral = releasedCollateral;
            this.price = price;
            this.repayCollateral = repayCollateral;
            this.confirmedTime = confirmedTime;
            this.updateTime = updateTime;
            this.status = status;
            this.repayId = repayId;
        }

        public CrossCollateralRepayment(JSONObject jCrossCollateralRepayment) {
            super(jCrossCollateralRepayment);
            amount = hItem.getDouble("amount", 0);
            repayType = RepayType.valueOf(hItem.getString("repayType"));
            releasedCollateral = hItem.getDouble("releasedCollateral", 0);
            price = hItem.getDouble("price", 0);
            repayCollateral = hItem.getDouble("repayCollateral", 0);
            confirmedTime = hItem.getLong("confirmedTime", 0);
            updateTime = hItem.getLong("updateTime", 0);
            status = Status.valueOf(hItem.getString("status"));
            repayId = hItem.getLong("repayId", 0);
        }

        public double getAmount() {
            return amount;
        }

        public RepayType getRepayType() {
            return repayType;
        }

        public double getReleasedCollateral() {
            return releasedCollateral;
        }

        public double getPrice() {
            return price;
        }

        public double getRepayCollateral() {
            return repayCollateral;
        }

        public long getConfirmedTime() {
            return confirmedTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public Status getStatus() {
            return status;
        }

        public long getRepayId() {
            return repayId;
        }

    }

}
