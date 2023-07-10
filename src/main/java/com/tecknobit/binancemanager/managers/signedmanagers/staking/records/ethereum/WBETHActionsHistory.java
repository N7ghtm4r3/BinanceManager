package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.WBETHActionsHistory.WBETHAction;

public class WBETHActionsHistory extends BinanceRowsList<WBETHAction> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public WBETHActionsHistory(ArrayList<WBETHAction> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public WBETHActionsHistory(int total, ArrayList<WBETHAction> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public WBETHActionsHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new WBETHAction((JSONObject) row));
    }

    public static class WBETHAction extends BinanceItem {

        private final long time;

        private final String fromAsset;

        private final double fromAmount;

        private final String toAsset;

        private final double toAmount;

        private final double exchangeRate;

        private final String status;

        public WBETHAction(long time, String fromAsset, double fromAmount, String toAsset, double toAmount,
                           double exchangeRate, String status) {
            super(null);
            this.time = time;
            this.fromAsset = fromAsset;
            this.fromAmount = fromAmount;
            this.toAsset = toAsset;
            this.toAmount = toAmount;
            this.exchangeRate = exchangeRate;
            this.status = status;
        }

        public WBETHAction(JSONObject jWBETHAction) {
            super(jWBETHAction);
            time = hItem.getLong("time", 0);
            fromAsset = hItem.getString("fromAsset");
            fromAmount = hItem.getDouble("fromAmount", 0);
            toAsset = hItem.getString("toAsset");
            toAmount = hItem.getDouble("toAmount", 0);
            exchangeRate = hItem.getDouble("exchangeRate", 0);
            status = hItem.getString("status");
        }

        public long getTime() {
            return time;
        }

        public String getFromAsset() {
            return fromAsset;
        }

        public double getFromAmount() {
            return fromAmount;
        }

        public String getToAsset() {
            return toAsset;
        }

        public double getToAmount() {
            return toAmount;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public String getStatus() {
            return status;
        }

    }

}
