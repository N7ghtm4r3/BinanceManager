package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHStakingHistory.ETHStaking;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHRewardsHistory.ETHReward;

/**
 * The {@code ETHRewardsHistory} class is useful to format a {@code Binance}'s ETH rewards history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-rewards-distribution-history-user_data">
 * Get ETH rewards distribution history (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class ETHRewardsHistory extends BinanceRowsList<ETHReward> {

    /**
     * Constructor to init {@link ETHRewardsHistory} object
     *
     * @param rows : list of the items
     */
    public ETHRewardsHistory(ArrayList<ETHReward> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link ETHRewardsHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public ETHRewardsHistory(int total, ArrayList<ETHReward> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link ETHRewardsHistory}
     *
     * @param jETHRewardsHistory : list details as {@link JSONObject}
     */
    public ETHRewardsHistory(JSONObject jETHRewardsHistory) {
        super(jETHRewardsHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new ETHReward((JSONObject) row));
    }

    /**
     * The {@code ETHReward} class is useful to format a {@code Binance}'s ETH reward
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see ETHStaking
     */
    public static class ETHReward extends ETHStaking {

        /**
         * {@code holding} BETH holding balance
         */
        private final double holding;

        /**
         * {@code annualPercentageRate} annual percentage rate (0.5 means 50% here)
         */
        private final double annualPercentageRate;

        /**
         * Constructor to init a {@link ETHReward} object
         *
         * @param time:                 time of the reward
         * @param asset:                asset of the reward
         * @param amount:               distributed rewards
         * @param status:               status of the reward
         * @param holding:              BETH holding balance
         * @param annualPercentageRate: annual percentage rate (0.5 means 50% here)
         */
        public ETHReward(long time, String asset, double amount, String status, double holding,
                         double annualPercentageRate) {
            super(time, asset, amount, status);
            this.holding = holding;
            this.annualPercentageRate = annualPercentageRate;
        }

        /**
         * Constructor to init a {@link ETHReward} object
         *
         * @param jETHReward: ETH reward details as {@link JSONObject}
         */
        public ETHReward(JSONObject jETHReward) {
            super(jETHReward);
            holding = hItem.getDouble("holding", 0);
            annualPercentageRate = hItem.getDouble("annualPercentageRate", 0);
        }

        /**
         * Method to get {@link #holding} instance <br>
         * No-any params required
         *
         * @return {@link #holding} instance as double
         */
        public double getHolding() {
            return holding;
        }

        /**
         * Method to get {@link #holding} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #holding} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getHolding(int decimals) {
            return roundValue(holding, decimals);
        }

        /**
         * Method to get {@link #annualPercentageRate} instance <br>
         * No-any params required
         *
         * @return {@link #annualPercentageRate} instance as double
         */
        public double getAnnualPercentageRate() {
            return annualPercentageRate;
        }

        /**
         * Method to get {@link #annualPercentageRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #annualPercentageRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAnnualPercentageRate(int decimals) {
            return roundValue(annualPercentageRate, decimals);
        }

    }

}
