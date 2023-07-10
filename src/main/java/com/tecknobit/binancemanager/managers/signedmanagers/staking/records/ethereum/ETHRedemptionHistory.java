package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHStakingHistory.ETHStaking;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHRedemptionHistory.ETHRedemption;

public class ETHRedemptionHistory extends BinanceRowsList<ETHRedemption> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public ETHRedemptionHistory(ArrayList<ETHRedemption> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public ETHRedemptionHistory(int total, ArrayList<ETHRedemption> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public ETHRedemptionHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new ETHRedemption((JSONObject) row));
    }

    public static class ETHRedemption extends ETHStaking {

        private final long arrivalTime;

        public ETHRedemption(long time, String asset, double amount, String status, long arrivalTime) {
            super(time, asset, amount, status);
            this.arrivalTime = arrivalTime;
        }

        public ETHRedemption(JSONObject jETHRedemption) {
            super(jETHRedemption);
            arrivalTime = hItem.getLong("arrivalTime", 0);
        }

        public long getArrivalTime() {
            return arrivalTime;
        }

    }

}
