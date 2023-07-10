package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHStakingHistory.ETHStaking;

public class ETHStakingHistory extends BinanceRowsList<ETHStaking> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public ETHStakingHistory(ArrayList<ETHStaking> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public ETHStakingHistory(int total, ArrayList<ETHStaking> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public ETHStakingHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new ETHStaking((JSONObject) row));
    }

    public static class ETHStaking extends BinanceItem {

        protected final long time;

        protected final String asset;

        protected final double amount;

        protected final String status;

        public ETHStaking(long time, String asset, double amount, String status) {
            super(null);
            this.time = time;
            this.asset = asset;
            this.amount = amount;
            this.status = status;
        }

        public ETHStaking(JSONObject jETHStaking) {
            super(jETHStaking);
            time = hItem.getLong("time", 0);
            asset = hItem.getString("asset");
            amount = hItem.getDouble("amount", 0);
            status = hItem.getString("status");
        }

        public long getTime() {
            return time;
        }

        public String getAsset() {
            return asset;
        }

        public double getAmount() {
            return amount;
        }

        public String getStatus() {
            return status;
        }

    }

}
