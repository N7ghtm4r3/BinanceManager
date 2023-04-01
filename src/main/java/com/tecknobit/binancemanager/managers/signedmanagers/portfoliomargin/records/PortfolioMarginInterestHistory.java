package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records.PortfolioMarginInterestHistory.MarginInterest;

public class PortfolioMarginInterestHistory extends BinanceRowsList<MarginInterest> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total           : number of items
     * @param marginInterests :  list of the items
     **/
    public PortfolioMarginInterestHistory(int total, ArrayList<MarginInterest> marginInterests) {
        super(total, marginInterests);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jHistory : list details as {@link JSONObject}
     **/
    public PortfolioMarginInterestHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new MarginInterest((JSONObject) row));
    }

    public static class MarginInterest extends BinanceItem {

        private final String asset;
        private final double interest;
        private final long interestAccruedTime;
        private final double interestRate;
        private final double principal;
        private final String type;

        public MarginInterest(String asset, double interest, long interestAccruedTime, double interestRate,
                              double principal, String type) {
            super(null);
            this.asset = asset;
            this.interest = interest;
            this.interestAccruedTime = interestAccruedTime;
            this.interestRate = interestRate;
            this.principal = principal;
            this.type = type;
        }

        public MarginInterest(JSONObject jMarginInterest) {
            super(jMarginInterest);
            asset = hItem.getString("asset");
            interest = hItem.getDouble("interest", 0);
            interestAccruedTime = hItem.getLong("interestAccruedTime", 0);
            interestRate = hItem.getDouble("interestRate", 0);
            principal = hItem.getDouble("principal", 0);
            type = hItem.getString("type");
        }

        public String getAsset() {
            return asset;
        }

        public double getInterest() {
            return interest;
        }

        public long getInterestAccruedTime() {
            return interestAccruedTime;
        }

        public double getPrincipal() {
            return principal;
        }

        public String getType() {
            return type;
        }

    }

}
