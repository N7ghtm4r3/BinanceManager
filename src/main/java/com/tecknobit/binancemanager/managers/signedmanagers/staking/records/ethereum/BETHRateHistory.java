package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.BETHRateHistory.BETHRate;

public class BETHRateHistory extends BinanceRowsList<BETHRate> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public BETHRateHistory(ArrayList<BETHRate> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public BETHRateHistory(int total, ArrayList<BETHRate> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public BETHRateHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new BETHRate((JSONObject) row));
    }

    public static class BETHRate extends BinanceItem {

        private final double annualPercentageRate;

        private final double exchangeRate;

        private final long time;

        public BETHRate(double annualPercentageRate, double exchangeRate, long time) {
            super(null);
            this.annualPercentageRate = annualPercentageRate;
            this.exchangeRate = exchangeRate;
            this.time = time;
        }

        public BETHRate(JSONObject jBETHRate) {
            super(null);
            annualPercentageRate = hItem.getDouble("annualPercentageRate", 0);
            exchangeRate = hItem.getDouble("exchangeRate", 0);
            time = hItem.getLong("time", 0);
        }

        public double getAnnualPercentageRate() {
            return annualPercentageRate;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public long getTime() {
            return time;
        }

    }

}
