package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHStakingHistory.ETHStaking;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHRedemptionHistory.ETHRedemption;

/**
 * The {@code ETHRedemptionHistory} class is useful to format a {@code Binance}'s
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-redemption-history-user_data">
 * Get ETH redemption history (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class ETHRedemptionHistory extends BinanceRowsList<ETHRedemption> {

    /**
     * Constructor to init {@link ETHRedemptionHistory} object
     *
     * @param rows : list of the items
     */
    public ETHRedemptionHistory(ArrayList<ETHRedemption> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link ETHRedemptionHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public ETHRedemptionHistory(int total, ArrayList<ETHRedemption> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link ETHRedemptionHistory}
     *
     * @param jETHRedemptionHistory : list details as {@link JSONObject}
     */
    public ETHRedemptionHistory(JSONObject jETHRedemptionHistory) {
        super(jETHRedemptionHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new ETHRedemption((JSONObject) row));
    }

    /**
     * The {@code ETHRedemption} class is useful to format a {@code Binance}'s ETH redemption
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see ETHStaking
     */
    public static class ETHRedemption extends ETHStaking {

        /**
         * {@code arrivalTime} arrival time of the redemption
         */
        private final long arrivalTime;

        /**
         * Constructor to init a {@link ETHRedemption} object
         *
         * @param time:        time of the redemption
         * @param asset:       asset of the redemption
         * @param amount:      amount of the redemption
         * @param status:      status of the redemption
         * @param arrivalTime: arrival time of the redemption
         */
        public ETHRedemption(long time, String asset, double amount, String status, long arrivalTime) {
            super(time, asset, amount, status);
            this.arrivalTime = arrivalTime;
        }

        /**
         * Constructor to init a {@link ETHRedemption} object
         *
         * @param jETHRedemption: ETH redemption details as {@link JSONObject}
         */
        public ETHRedemption(JSONObject jETHRedemption) {
            super(jETHRedemption);
            arrivalTime = hItem.getLong("arrivalTime", 0);
        }

        /**
         * Method to get {@link #arrivalTime} instance <br>
         * No-any params required
         *
         * @return {@link #arrivalTime} instance as long
         */
        public long getArrivalTime() {
            return arrivalTime;
        }

        /**
         * Method to get {@link #arrivalTime} instance <br>
         * No-any params required
         *
         * @return {@link #arrivalTime} instance as {@link Date}
         */
        public Date getArrivalDate() {
            return TimeFormatter.getDate(arrivalTime);
        }

    }

}
