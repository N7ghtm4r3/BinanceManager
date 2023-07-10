package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHStakingHistory.ETHStaking;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHRewardsHistory.ETHReward;

public class ETHRewardsHistory extends BinanceRowsList<ETHReward> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public ETHRewardsHistory(ArrayList<ETHReward> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public ETHRewardsHistory(int total, ArrayList<ETHReward> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public ETHRewardsHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new ETHReward((JSONObject) row));
    }

    public static class ETHReward extends ETHStaking {

        private final double holding;

        private final double annualPercentageRate;

        public ETHReward(long time, String asset, double amount, String status, double holding,
                         double annualPercentageRate) {
            super(time, asset, amount, status);
            this.holding = holding;
            this.annualPercentageRate = annualPercentageRate;
        }

        public ETHReward(JSONObject jETHReward) {
            super(jETHReward);
            holding = hItem.getDouble("holding", 0);
            annualPercentageRate = hItem.getDouble("annualPercentageRate", 0);
        }

        public double getHolding() {
            return holding;
        }

        public double getAnnualPercentageRate() {
            return annualPercentageRate;
        }

    }

}
