package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.ETHStakingHistory.ETHStaking;

/**
 * The {@code ETHStakingHistory} class is useful to format a {@code Binance}'s ETH staking history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-eth-staking-history-user_data">
 * Get ETH staking history (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class ETHStakingHistory extends BinanceRowsList<ETHStaking> {

    /**
     * Constructor to init {@link ETHStakingHistory} object
     *
     * @param rows : list of the items
     */
    public ETHStakingHistory(ArrayList<ETHStaking> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link ETHStakingHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public ETHStakingHistory(int total, ArrayList<ETHStaking> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link ETHStakingHistory}
     *
     * @param jETHStakingHistory : list details as {@link JSONObject}
     */
    public ETHStakingHistory(JSONObject jETHStakingHistory) {
        super(jETHStakingHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new ETHStaking((JSONObject) row));
    }

    /**
     * The {@code ETHStaking} class is useful to format a {@code Binance}'s ETH staking
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class ETHStaking extends BinanceItem {

        /**
         * {@code time} of the staking
         */
        protected final long time;

        /**
         * {@code asset} of the staking
         */
        protected final String asset;

        /**
         * {@code amount} of the staking
         */
        protected final double amount;

        /**
         * {@code status} of the staking
         */
        protected final String status;

        /**
         * Constructor to init a {@link ETHStaking} object
         *
         * @param time:   time of the staking
         * @param asset:  asset of the staking
         * @param amount: amount of the staking
         * @param status: status of the staking
         */
        public ETHStaking(long time, String asset, double amount, String status) {
            super(null);
            this.time = time;
            this.asset = asset;
            this.amount = amount;
            this.status = status;
        }

        /**
         * Constructor to init a {@link ETHStaking} object
         *
         * @param jETHStaking: ETH staking details as {@link JSONObject}
         */
        public ETHStaking(JSONObject jETHStaking) {
            super(jETHStaking);
            time = hItem.getLong("time", 0);
            asset = hItem.getString("asset");
            amount = hItem.getDouble("amount", 0);
            status = hItem.getString("status");
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as long
         */
        public long getTime() {
            return time;
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as {@link Date}
         */
        public Date getDate() {
            return TimeFormatter.getDate(time);
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         */
        public String getStatus() {
            return status;
        }

    }

}
