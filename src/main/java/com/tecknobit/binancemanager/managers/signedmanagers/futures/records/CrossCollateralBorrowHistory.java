package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralBorrowHistory.CrossCollateralBorrow;

public class CrossCollateralBorrowHistory extends BinanceRowsList<CrossCollateralBorrow> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total   : number of items
     * @param borrows :  list of the items
     **/
    public CrossCollateralBorrowHistory(int total, ArrayList<CrossCollateralBorrow> borrows) {
        super(total, borrows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jHistory : list details as {@link JSONObject}
     **/
    public CrossCollateralBorrowHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralBorrow((JSONObject) row));
    }

    public static class CrossCollateralBorrow extends CrossCollateralItem {

        private final long confirmedTime;
        private final double collateralRate;
        private final double leftTotal;
        private final double leftPrincipal;
        private final long deadline;
        private final double collateralAmount;
        private final Status orderStatus;
        private final long borrowId;

        public CrossCollateralBorrow(String coin, String collateralCoin, long confirmedTime, double collateralRate,
                                     double leftTotal, double leftPrincipal, long deadline, double collateralAmount,
                                     Status orderStatus, long borrowId) {
            super(coin, collateralCoin);
            this.confirmedTime = confirmedTime;
            this.collateralRate = collateralRate;
            this.leftTotal = leftTotal;
            this.leftPrincipal = leftPrincipal;
            this.deadline = deadline;
            this.collateralAmount = collateralAmount;
            this.orderStatus = orderStatus;
            this.borrowId = borrowId;
        }

        public CrossCollateralBorrow(JSONObject jCrossCollateralBorrow) {
            super(jCrossCollateralBorrow);
            confirmedTime = hItem.getLong("confirmedTime", 0);
            collateralRate = hItem.getDouble("collateralRate", 0);
            leftTotal = hItem.getDouble("leftTotal", 0);
            leftPrincipal = hItem.getDouble("leftPrincipal", 0);
            deadline = hItem.getLong("deadline", 0);
            collateralAmount = hItem.getDouble("collateralAmount", 0);
            orderStatus = Status.valueOf(hItem.getString("orderStatus"));
            borrowId = hItem.getLong("borrowId", 0);
        }

        public long getConfirmedTime() {
            return confirmedTime;
        }

        public double getCollateralRate() {
            return collateralRate;
        }

        public double getLeftTotal() {
            return leftTotal;
        }

        public double getLeftPrincipal() {
            return leftPrincipal;
        }

        public long getDeadline() {
            return deadline;
        }

        public double getCollateralAmount() {
            return collateralAmount;
        }

        public Status getOrderStatus() {
            return orderStatus;
        }

        public long getBorrowId() {
            return borrowId;
        }

    }

}


