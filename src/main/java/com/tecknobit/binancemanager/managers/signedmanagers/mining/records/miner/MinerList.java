package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.MinerList.Miner;

public class MinerList extends MiningResponse<Miner> {

    public MinerList(Miner data) {
        super(data);
    }

    public MinerList(JSONObject jMiningResponse) {
        super(jMiningResponse);
        data = new Miner(hItem.getJSONObject("data"));
    }

    public static class Miner extends DataListItem {

        private final ArrayList<WorkerData> workerDatas;

        public Miner(int totalNum, int pageSize, ArrayList<WorkerData> workerDatas) {
            super(totalNum, pageSize);
            this.workerDatas = workerDatas;
        }

        public Miner(JSONObject jDataListItem) {
            super(jDataListItem);
            workerDatas = new ArrayList<>();
            for (Object workerData : hItem.fetchList("workerDatas"))
                workerDatas.add(new WorkerData((JSONObject) workerData));
        }

        public ArrayList<WorkerData> getWorkerDatas() {
            return workerDatas;
        }

        public static class WorkerData extends BinanceItem {

            private final long workerId;
            private final String workerName;
            private final int status;
            private final long hashRate;
            private final long dayHashRate;
            private final long rejectRate;
            private final long lastShareTime;

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

            public long getWorkerId() {
                return workerId;
            }

            public String getWorkerName() {
                return workerName;
            }

            public int getStatus() {
                return status;
            }

            public long getHashRate() {
                return hashRate;
            }

            public long getDayHashRate() {
                return dayHashRate;
            }

            public long getRejectRate() {
                return rejectRate;
            }

            public long getLastShareTime() {
                return lastShareTime;
            }

        }

    }

}
