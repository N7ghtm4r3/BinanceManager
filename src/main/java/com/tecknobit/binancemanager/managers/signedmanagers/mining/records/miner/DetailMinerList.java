package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.DetailMinerList.DetailMiner;

/**
 * The {@code DetailMinerList} class is useful to create a detail miner list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-algorithm-market_data">
 * Acquiring Algorithm (MARKET_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class DetailMinerList extends MiningResponse<ArrayList<DetailMiner>> {

    /**
     * Constructor to init {@link DetailMinerList} object
     *
     * @param data: detail miner list 
     */
    public DetailMinerList(ArrayList<DetailMiner> data) {
        super(data);
    }

    /**
     * Constructor to init {@link DetailMinerList} object
     *
     * @param jDetailMinerList: detail miner list details as {@link JSONObject}
     */
    public DetailMinerList(JSONObject jDetailMinerList) {
        super(jDetailMinerList);
        data = new ArrayList<>();
        ArrayList<JSONObject> list = hItem.fetchList("data");
        if (list != null)
            for (JSONObject miner : list)
                data.add(new DetailMiner(miner));
    }

    /**
     * The {@code DetailMiner} class is useful to create a detail miner
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class DetailMiner extends BinanceItem {

        /**
         * {@code workerName} worker name of the detail miner
         */
        private final String workerName;

        /**
         * {@code type} of the detail miner
         */
        private final HashRateType type;

        /**
         * {@code hashrateDatas} hashrate datas of the detail miner
         */
        private final ArrayList<HashrateData> hashrateDatas;

        /**
         * Constructor to init {@link DetailMiner} object
         *
         * @param workerName: worker name of the detail miner
         * @param type: type of the detail miner
         * @param hashrateDatas: hashrate datas of the detail miner
         */
        public DetailMiner(String workerName, HashRateType type, ArrayList<HashrateData> hashrateDatas) {
            super(null);
            this.workerName = workerName;
            this.type = type;
            this.hashrateDatas = hashrateDatas;
        }

        /**
         * Constructor to init {@link DetailMiner} object
         *
         * @param jDetailMiner: detail miner as {@link JSONObject}
         */
        public DetailMiner(JSONObject jDetailMiner) {
            super(jDetailMiner);
            workerName = hItem.getString("workerName");
            type = HashRateType.valueOf(hItem.getString("type"));
            hashrateDatas = new ArrayList<>();
            for (Object hashrate : hItem.fetchList("hashrateDatas"))
                hashrateDatas.add(new HashrateData((JSONObject) hashrate));
        }

        /**
         * Method to get {@link #workerName} instance <br>
         * No-any params required
         *
         * @return {@link #workerName} instance as {@link String}
         */
        public String getWorkerName() {
            return workerName;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link HashRateType}
         */
        public HashRateType getType() {
            return type;
        }

        /**
         * Method to get {@link #hashrateDatas} instance <br>
         * No-any params required
         *
         * @return {@link #hashrateDatas} instance as {@link ArrayList} of {@link HashrateData}
         */
        public ArrayList<HashrateData> getHashrateDatas() {
            return hashrateDatas;
        }

        /**
         * The {@code HashrateData} class is useful to create a hashrate data
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         */
        public static class HashrateData extends BinanceItem {

            /**
             * {@code time} of the hashrate data
             */
            private final long time;

            /**
             * {@code hashrate} hashrate of the data
             */
            private final String hashrate;

            /**
             * {@code reject} of the hashrate data
             */
            private final double reject;

            /**
             * Constructor to init {@link HashrateData} object
             *
             * @param time: time of the hashrate data
             * @param hashrate: hashrate of the data
             * @param reject: reject of the hashrate data
             */
            public HashrateData(long time, String hashrate, double reject) {
                super(null);
                this.time = time;
                this.hashrate = hashrate;
                this.reject = reject;
            }

            /**
             * Constructor to init {@link HashrateData} object
             *
             * @param jHashrateData: hashrate data details as {@link JSONObject}
             */
            public HashrateData(JSONObject jHashrateData) {
                super(jHashrateData);
                time = hItem.getLong("time", 0);
                hashrate = hItem.getString("hashrate");
                reject = hItem.getDouble("reject", 0);
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
             * Method to get {@link #hashrate} instance <br>
             * No-any params required
             *
             * @return {@link #hashrate} instance as {@link String}
             */
            public String getHashrate() {
                return hashrate;
            }

            /**
             * Method to get {@link #reject} instance <br>
             * No-any params required
             *
             * @return {@link #reject} instance as double
             */
            public double getReject() {
                return reject;
            }

            /**
             * Method to get {@link #reject} instance
             *
             * @param decimals: number of digits to round final value
             * @return {@link #reject} instance rounded with decimal digits inserted
             * @throws IllegalArgumentException if decimalDigits is negative
             */
            public double getReject(int decimals) {
                return roundValue(reject, decimals);
            }

        }

    }

}
