package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.CrossCollateralInterestsHistory.CrossCollateralInterest;

public class CrossCollateralInterestsHistory extends BinanceRowsList<CrossCollateralInterest> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total     : number of items
     * @param interests :  list of the items
     **/
    public CrossCollateralInterestsHistory(int total, ArrayList<CrossCollateralInterest> interests) {
        super(total, interests);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jHistory : list details as {@link JSONObject}
     **/
    public CrossCollateralInterestsHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CrossCollateralInterest((JSONObject) row));
    }

    public static class CrossCollateralInterest extends BinanceItem {

        private final String collateralCoin;
        private final String interestCoin;
        private final double interest;
        private final double interestFreeLimitUsed;
        private final double principalForInterest;
        private final double interestRate;
        private final long time;

        public CrossCollateralInterest(String collateralCoin, String interestCoin, double interest,
                                       double interestFreeLimitUsed, double principalForInterest, double interestRate,
                                       long time) {
            super(null);
            this.collateralCoin = collateralCoin;
            this.interestCoin = interestCoin;
            this.interest = interest;
            this.interestFreeLimitUsed = interestFreeLimitUsed;
            this.principalForInterest = principalForInterest;
            this.interestRate = interestRate;
            this.time = time;
        }

        public CrossCollateralInterest(JSONObject jCrossCollateralInterest) {
            super(jCrossCollateralInterest);
            collateralCoin = hItem.getString("collateralCoin");
            interestCoin = hItem.getString("interestCoin");
            interest = hItem.getDouble("interest", 0);
            interestFreeLimitUsed = hItem.getDouble("interestFreeLimitUsed", 0);
            principalForInterest = hItem.getDouble("principalForInterest", 0);
            interestRate = hItem.getDouble("interestRate", 0);
            time = hItem.getLong("time", 0);
        }

        public String getCollateralCoin() {
            return collateralCoin;
        }

        public String getInterestCoin() {
            return interestCoin;
        }

        public double getInterest() {
            return interest;
        }

        public double getInterestFreeLimitUsed() {
            return interestFreeLimitUsed;
        }

        public double getPrincipalForInterest() {
            return principalForInterest;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public long getTime() {
            return time;
        }

    }

}
