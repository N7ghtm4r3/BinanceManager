package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.HashMap;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.StatisticList.MiningStatistic;

/**
 * The {@code StatisticList} class is useful to create a statistic list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#statistic-list-user_data">
 * Statistic List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class StatisticList extends MiningResponse<MiningStatistic> {

    /**
     * Constructor to init {@link StatisticList} object
     *
     * @param data: statistic list
     */
    public StatisticList(MiningStatistic data) {
        super(data);
    }

    /**
     * Constructor to init {@link StatisticList} object
     *
     * @param jStatisticList: statistic list details as {@link JSONObject}
     */
    public StatisticList(JSONObject jStatisticList) {
        super(jStatisticList);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new MiningStatistic(jData);
        else
            data = null;
    }

    /**
     * The {@code MiningStatistic} class is useful to create a mining statistic
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class MiningStatistic extends BinanceItem {

        /**
         * {@code fifteenMinHashRate} 15 mins hashrate
         */
        private final String fifteenMinHashRate;

        /**
         * {@code dayHashRate} 24H Hashrate
         */
        private final String dayHashRate;

        /**
         * {@code validNum} effective quantity
         */
        private final int validNum;

        /**
         * {@code invalidNum} invalid quantity
         */
        private final int invalidNum;

        /**
         * {@code profitToday} today's estimate
         */
        private final HashMap<String, Double> profitToday;

        /**
         * {@code profitYesterday} yesterday's earnings
         */
        private final HashMap<String, Double> profitYesterday;

        /**
         * {@code userName} mining account
         */
        private final String userName;

        /**
         * {@code unit} hashrate unit
         */
        private final String unit;

        /**
         * {@code algo} algorithm
         */
        private final String algo;

        /**
         * Constructor to init {@link MiningStatistic} object
         *
         * @param fifteenMinHashRate: 15 mins hashrate
         * @param dayHashRate: 24H Hashrate
         * @param validNum: effective quantity
         * @param invalidNum: invalid quantity
         * @param profitToday: today's estimate
         * @param profitYesterday: yesterday's earnings
         * @param userName: mining account
         * @param unit: hashrate unit
         * @param algo: algorithm
         */
        public MiningStatistic(String fifteenMinHashRate, String dayHashRate, int validNum, int invalidNum,
                               HashMap<String, Double> profitToday, HashMap<String, Double> profitYesterday,
                               String userName, String unit, String algo) {
            super(null);
            this.fifteenMinHashRate = fifteenMinHashRate;
            this.dayHashRate = dayHashRate;
            this.validNum = validNum;
            this.invalidNum = invalidNum;
            this.profitToday = profitToday;
            this.profitYesterday = profitYesterday;
            this.userName = userName;
            this.unit = unit;
            this.algo = algo;
        }

        /**
         * Constructor to init {@link MiningStatistic} object
         *
         * @param jMiningStatistic: mining statistic details as {@link JSONObject}
         */
        public MiningStatistic(JSONObject jMiningStatistic) {
            super(jMiningStatistic);
            fifteenMinHashRate = hItem.getString("fifteenMinHashRate");
            dayHashRate = hItem.getString("dayHashRate");
            validNum = hItem.getInt("validNum", 0);
            invalidNum = hItem.getInt("invalidNum", 0);
            profitToday = loadProfitMap(hItem.getJSONObject("profitToday"));
            profitYesterday = loadProfitMap(hItem.getJSONObject("profitYesterday"));
            userName = hItem.getString("userName");
            unit = hItem.getString("unit");
            algo = hItem.getString("algo");
        }

        /**
         * Method to load the profit map
         *
         * @param jMap: map from fetch the details
         * @return profit map as {@link HashMap} of {@link String} and {@link Double}
         */
        private HashMap<String, Double> loadProfitMap(JSONObject jMap) {
            HashMap<String, Double> profitMap = new HashMap<>();
            for (String key : jMap.keySet())
                profitMap.put(key, jMap.getDouble(key));
            return profitMap;
        }

        /**
         * Method to get {@link #fifteenMinHashRate} instance <br>
         * No-any params required
         *
         * @return {@link #fifteenMinHashRate} instance as {@link String}
         */
        public String getFifteenMinHashRate() {
            return fifteenMinHashRate;
        }

        /**
         * Method to get {@link #dayHashRate} instance <br>
         * No-any params required
         *
         * @return {@link #dayHashRate} instance as {@link String}
         */
        public String getDayHashRate() {
            return dayHashRate;
        }

        /**
         * Method to get {@link #validNum} instance <br>
         * No-any params required
         *
         * @return {@link #validNum} instance as long
         */
        public int getValidNum() {
            return validNum;
        }

        /**
         * Method to get {@link #invalidNum} instance <br>
         * No-any params required
         *
         * @return {@link #invalidNum} instance as long
         */
        public int getInvalidNum() {
            return invalidNum;
        }

        /**
         * Method to get {@link #profitToday} instance <br>
         * No-any params required
         *
         * @return {@link #profitToday} instance as {@link HashMap} of {@link String} and {@link Double}
         */
        public HashMap<String, Double> getProfitToday() {
            return profitToday;
        }

        /**
         * Method to get {@link #profitYesterday} instance <br>
         * No-any params required
         *
         * @return {@link #profitYesterday} instance as {@link HashMap} of {@link String} and {@link Double}
         */
        public HashMap<String, Double> getProfitYesterday() {
            return profitYesterday;
        }

        /**
         * Method to get {@link #userName} instance <br>
         * No-any params required
         *
         * @return {@link #userName} instance as {@link String}
         */
        public String getUserName() {
            return userName;
        }

        /**
         * Method to get {@link #unit} instance <br>
         * No-any params required
         *
         * @return {@link #unit} instance as {@link String}
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Method to get {@link #algo} instance <br>
         * No-any params required
         *
         * @return {@link #algo} instance as {@link String}
         */
        public String getAlgo() {
            return algo;
        }

    }

}
