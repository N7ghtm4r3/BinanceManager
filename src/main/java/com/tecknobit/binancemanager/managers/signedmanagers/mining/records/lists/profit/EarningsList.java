package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.EarningsList.Earning;

/**
 * The {@code EarningsList} class is useful to create an earnings list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
 * Earnings List(USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class EarningsList extends MiningResponse<Earning> {

    /**
     * Constructor to init {@link EarningsList} object
     *
     * @param data: earnings list
     */
    public EarningsList(Earning data) {
        super(data);
    }

    /**
     * Constructor to init {@link EarningsList} object
     *
     * @param jEarningsList: earnings list details as {@link JSONObject}
     */
    public EarningsList(JSONObject jEarningsList) {
        super(jEarningsList);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new Earning(jData);
        else
            data = null;
    }

    /**
     * The {@code Earning} class is useful to create an earning
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see DataListItem
     */
    public static class Earning extends DataListItem {

        /**
         * {@code accountProfits} account profits list
         */
        private final ArrayList<AccountProfit> accountProfits;

        /**
         * Constructor to init {@link Earning} object
         *
         * @param totalNum: total num of the account profits list
         * @param pageSize: page size of the account profits list
         * @param accountProfits: page size of the account profits list
         */
        public Earning(int totalNum, int pageSize, ArrayList<AccountProfit> accountProfits) {
            super(totalNum, pageSize);
            this.accountProfits = accountProfits;
        }

        /**
         * Constructor to init {@link Earning} object
         *
         * @param jEarning: earning details as {@link JSONObject}
         */
        public Earning(JSONObject jEarning) {
            super(jEarning);
            accountProfits = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("accountProfits");
            if (jList != null)
                for (JSONObject profit : jList)
                    accountProfits.add(new AccountProfit(profit));
        }

        /**
         * Method to get {@link #accountProfits} instance <br>
         * No-any params required
         *
         * @return {@link #accountProfits} instance as {@link ArrayList} of {@link AccountProfit}
         */
        public ArrayList<AccountProfit> getAccountProfits() {
            return accountProfits;
        }

        /**
         * The {@code EarningAccountProfit class is useful to create an account profit
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         * @see Profit
         */
        public static class AccountProfit extends Profit {

            /**
             * {@code dayHashRate} daily hashrate
             */
            private final long dayHashRate;

            /**
             * {@code hashTransfer} transferred hashrate
             */
            private final long hashTransfer;

            /**
             * {@code transferAmount} transferred income
             */
            private final double transferAmount;

            /**
             * Constructor to init {@link AccountProfit} object
             *
             * @param time: mining date
             * @param type: type of the profit
             * @param profitAmount: earnings amount
             * @param coinName: coin type
             * @param status: profit type value
             * @param dayHashRate: daily hashrate
             * @param hashTransfer: transferred hashrate
             * @param transferAmount: transferred income
             */
            public AccountProfit(long time, ProfitType type, double profitAmount, String coinName, ProfitStatus status,
                                 long dayHashRate, long hashTransfer, double transferAmount) {
                super(time, type, profitAmount, coinName, status);
                this.dayHashRate = dayHashRate;
                this.hashTransfer = hashTransfer;
                this.transferAmount = transferAmount;
            }

            /**
             * Constructor to init {@link AccountProfit} object
             *
             * @param jAccountProfit: account profit details as {@link JSONObject}
             */
            public AccountProfit(JSONObject jAccountProfit) {
                super(jAccountProfit);
                dayHashRate = hItem.getLong("dayHashRate", 0);
                hashTransfer = hItem.getLong("hashTransfer", 0);
                transferAmount = hItem.getDouble("transferAmount", 0);
            }

            /**
             * Method to get {@link #dayHashRate} instance <br>
             * No-any params required
             *
             * @return {@link #dayHashRate} instance as long
             */
            public long getDayHashRate() {
                return dayHashRate;
            }

            /**
             * Method to get {@link #hashTransfer} instance <br>
             * No-any params required
             *
             * @return {@link #hashTransfer} instance as long
             */
            public long getHashTransfer() {
                return hashTransfer;
            }

            /**
             * Method to get {@link #transferAmount} instance <br>
             * No-any params required
             *
             * @return {@link #transferAmount} instance as double
             */
            public double getTransferAmount() {
                return transferAmount;
            }

            /**
             * Method to get {@link #transferAmount} instance
             *
             * @param decimals: number of digits to round final value
             * @return {@link #transferAmount} instance rounded with decimal digits inserted
             * @throws IllegalArgumentException if decimalDigits is negative
             */
            public double getAmount(int decimals) {
                return roundValue(transferAmount, decimals);
            }

        }

    }

}
