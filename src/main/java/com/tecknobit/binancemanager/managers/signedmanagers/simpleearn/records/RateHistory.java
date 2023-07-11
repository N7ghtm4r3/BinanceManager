package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.RateHistory.RateRecord;

public class RateHistory extends BinanceRowsList<RateRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public RateHistory(ArrayList<RateRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public RateHistory(int total, ArrayList<RateRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jRateHistory : list details as {@link JSONObject}
     */
    public RateHistory(JSONObject jRateHistory) {
        super(jRateHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new RateRecord((JSONObject) row));
    }

    public static class RateRecord extends BinanceItem {

        private final String productId;

        private final String asset;

        private final double annualPercentageRate;

        private final long time;

        public RateRecord(String productId, String asset, double annualPercentageRate, long time) {
            super(null);
            this.productId = productId;
            this.asset = asset;
            this.annualPercentageRate = annualPercentageRate;
            this.time = time;
        }

        public RateRecord(JSONObject jRateRecord) {
            super(jRateRecord);
            productId = hItem.getString("productId");
            asset = hItem.getString("asset");
            annualPercentageRate = hItem.getDouble("annualPercentageRate", 0);
            time = hItem.getLong("time", -1);
        }

        public String getProductId() {
            return productId;
        }

        public String getAsset() {
            return asset;
        }

        public double getAnnualPercentageRate() {
            return annualPercentageRate;
        }

        public long getTime() {
            return time;
        }

    }

}
