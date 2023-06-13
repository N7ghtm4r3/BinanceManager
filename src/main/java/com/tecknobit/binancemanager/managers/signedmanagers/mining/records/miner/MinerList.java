package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.MinerList.Miner;

/**
 * The {@code DetailMinerList} class is useful to create a detail miner list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
 * Request for Miner List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class MinerList extends MiningResponse<Miner> {

    /**
     * {@code SortSequence} list of available sort sequences
     */
    public enum SortSequence {

        /**
         * {@code miner_name} sort sequence
         */
        positive_sequence(0),

        /**
         * {@code real_time_computing_power} sort sequence
         */
        negative_sequence(1);

        /**
         * {@code sequence} column value
         */
        private final int sequence;

        /**
         * Constructor to init {@link SortSequence} object
         *
         * @param sequence: sequence column value
         */
        SortSequence(int sequence) {
            this.sequence = sequence;
        }

        /**
         * Method to get {@link #sequence} instance <br>
         * No-any params required
         *
         * @return {@link #sequence} instance as int
         */
        public int getSequence() {
            return sequence;
        }

        /**
         * Method to get {@link #sequence} instance <br>
         * No-any params required
         *
         * @return {@link #sequence} instance as {@link String}
         */
        @Override
        public String toString() {
            return sequence + "";
        }

    }

    /**
     * {@code SortColumn} list of available sequence column
     */
    public enum SortColumn {

        /**
         * {@code miner_name} sequence column
         */
        miner_name(1),

        /**
         * {@code real_time_computing_power} sequence column
         */
        real_time_computing_power(2),

        /**
         * {@code daily_average_computing_power} sequence column
         */
        daily_average_computing_power(3),

        /**
         * {@code real_time_rejection_rate} sequence column
         */
        real_time_rejection_rate(4),

        /**
         * {@code last_submission_time} sequence column
         */
        last_submission_time(5);

        /**
         * {@code sequence} column value
         */
        private final int sort;

        /**
         * Constructor to init {@link SortColumn} object
         *
         * @param sort: sequence column value
         */
        SortColumn(int sort) {
            this.sort = sort;
        }

        /**
         * Method to get {@link #sort} instance <br>
         * No-any params required
         *
         * @return {@link #sort} instance as int
         */
        public int getSort() {
            return sort;
        }

        /**
         * Method to get {@link #sort} instance <br>
         * No-any params required
         *
         * @return {@link #sort} instance as {@link String}
         */
        @Override
        public String toString() {
            return sort + "";
        }

    }

    /**
     * {@code WorkerStatus} list of available worker statuses
     */
    public enum WorkerStatus {

        /**
         * {@code all} worker status
         */
        all(0),

        /**
         * {@code valid} worker status
         */
        valid(1),

        /**
         * {@code invalid} worker status
         */
        invalid(2),

        /**
         * {@code failure} worker status
         */
        failure(3);

        /**
         * {@code status} worker status value
         */
        private final int status;

        /**
         * Constructor to init {@link WorkerStatus} object
         *
         * @param status: worker status value
         */
        WorkerStatus(int status) {
            this.status = status;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as int
         */
        public int getStatus() {
            return status;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         */
        @Override
        public String toString() {
            return status + "";
        }

    }

    /**
     * Constructor to init {@link MinerList} object
     *
     * @param data: miner list data
     */
    public MinerList(Miner data) {
        super(data);
    }

    /**
     * Constructor to init {@link MinerList} object
     *
     * @param jMinerList: miner list details as {@link JSONObject}
     */
    public MinerList(JSONObject jMinerList) {
        super(jMinerList);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new Miner(jData);
        else
            data = null;
    }

    /**
     * The {@code Miner} class is useful to create a miner
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see DataListItem
     */
    public static class Miner extends DataListItem {

        /**
         * {@code workerDatas} worker datas of the miner
         */
        private final ArrayList<WorkerData> workerDatas;

        /**
         * Constructor to init {@link Miner} object
         *
         * @param totalNum: total num of the miner
         * @param pageSize: page size of the miner
         * @param workerDatas: worker datas of the miner
         */
        public Miner(int totalNum, int pageSize, ArrayList<WorkerData> workerDatas) {
            super(totalNum, pageSize);
            this.workerDatas = workerDatas;
        }

        /**
         * Constructor to init {@link Miner} object
         *
         * @param jMiner: miner details as {@link JSONObject}
         */
        public Miner(JSONObject jMiner) {
            super(jMiner);
            workerDatas = new ArrayList<>();
            for (Object workerData : hItem.fetchList("workerDatas"))
                workerDatas.add(new WorkerData((JSONObject) workerData));
        }

        /**
         * Method to get {@link #workerDatas} instance <br>
         * No-any params required
         *
         * @return {@link #workerDatas} instance as {@link ArrayList} of {@link WorkerData}
         */
        public ArrayList<WorkerData> getWorkerDatas() {
            return workerDatas;
        }

        /**
         * The {@code WorkerData} class is useful to create a worker data
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         */
        public static class WorkerData extends BinanceItem {

            /**
             * {@code workerId} miner ID
             */
            private final long workerId;

            /**
             * {@code workerName} miner's name
             */
            private final String workerName;

            /**
             * {@code status} of the worker
             */
            private final int status;

            /**
             * {@code hashRate} real-time rate
             */
            private final long hashRate;

            /**
             * {@code dayHashRate} 24H Hashrate
             */
            private final long dayHashRate;

            /**
             * {@code rejectRate} real-time Rejection Rate
             */
            private final long rejectRate;

            /**
             * {@code lastShareTime} last submission time
             */
            private final long lastShareTime;

            /**
             * Constructor to init {@link WorkerData} object
             *
             * @param workerId: miner's ID
             * @param workerName: miner's name
             * @param status: status of the worker
             * @param hashRate: real-time rate
             * @param dayHashRate: 24H Hashrate
             * @param rejectRate: real-time Rejection Rate
             * @param lastShareTime: last submission time
             */
            public WorkerData(long workerId, String workerName, int status, long hashRate, long dayHashRate,
                              long rejectRate, long lastShareTime) {
                super(null);
                this.workerId = workerId;
                this.workerName = workerName;
                this.status = status;
                this.hashRate = hashRate;
                this.dayHashRate = dayHashRate;
                this.rejectRate = rejectRate;
                this.lastShareTime = lastShareTime;
            }

            /**
             * Constructor to init {@link WorkerData} object
             *
             * @param jWorkerData: worker data details as {@link JSONObject}
             */
            public WorkerData(JSONObject jWorkerData) {
                super(jWorkerData);
                workerId = hItem.getLong("workerId", 0);
                workerName = hItem.getString("workerName");
                status = hItem.getInt("status", 0);
                hashRate = hItem.getLong("hashRate", 0);
                dayHashRate = hItem.getLong("dayHashRate", 0);
                rejectRate = hItem.getLong("rejectRate", 0);
                lastShareTime = hItem.getLong("lastShareTime", 0);
            }

            /**
             * Method to get {@link #workerId} instance <br>
             * No-any params required
             *
             * @return {@link #workerId} instance as long
             */
            public long getWorkerId() {
                return workerId;
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
             * Method to get {@link #status} instance <br>
             * No-any params required
             *
             * @return {@link #status} instance as int
             */
            public int getStatus() {
                return status;
            }

            /**
             * Method to get {@link #hashRate} instance <br>
             * No-any params required
             *
             * @return {@link #hashRate} instance as long
             */
            public long getHashRate() {
                return hashRate;
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
             * Method to get {@link #rejectRate} instance <br>
             * No-any params required
             *
             * @return {@link #rejectRate} instance as long
             */
            public long getRejectRate() {
                return rejectRate;
            }

            /**
             * Method to get {@link #lastShareTime} instance <br>
             * No-any params required
             *
             * @return {@link #lastShareTime} instance as long
             */
            public long getLastShareTime() {
                return lastShareTime;
            }

            /**
             * Method to get {@link #lastShareTime} instance <br>
             * No-any params required
             *
             * @return {@link #lastShareTime} instance as {@link Date}
             */
            public Date getLastShareDate() {
                return TimeFormatter.getDate(lastShareTime);
            }

        }

    }

}
