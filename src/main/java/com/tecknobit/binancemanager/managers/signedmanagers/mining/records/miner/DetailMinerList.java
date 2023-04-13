package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.DetailMinerList.DetailMiner;

public class DetailMinerList extends MiningResponse<ArrayList<DetailMiner>> {

    public DetailMinerList(ArrayList<DetailMiner> data) {
        super(data);
    }

    public DetailMinerList(JSONObject jMiningResponse) {
        super(jMiningResponse);
        data = new ArrayList<>();
        for (Object miner : hItem.fetchList("data"))
            data.add(new DetailMiner((JSONObject) miner));
    }

    public static class DetailMiner extends BinanceItem {

        private final String workerName;
        private final HashRateType type;
        private final ArrayList<HashrateData> hashrateDatas;

        public DetailMiner(String workerName, HashRateType type, ArrayList<HashrateData> hashrateDatas) {
            super(null);
            this.workerName = workerName;
            this.type = type;
            this.hashrateDatas = hashrateDatas;
        }

        public DetailMiner(JSONObject jDetailMiner) {
            super(jDetailMiner);
            workerName = hItem.getString("workerName");
            type = HashRateType.valueOf(hItem.getString("type"));
            hashrateDatas = new ArrayList<>();
            for (Object hashrate : hItem.fetchList("hashrateDatas"))
                hashrateDatas.add(new HashrateData((JSONObject) hashrate));
        }

        public String getWorkerName() {
            return workerName;
        }

        public HashRateType getType() {
            return type;
        }

        public ArrayList<HashrateData> getHashrateDatas() {
            return hashrateDatas;
        }

        public static class HashrateData extends BinanceItem {

            private final long time;
            private final String hashrate;
            private final double reject;

            public HashrateData(long time, String hashrate, double reject) {
                super(null);
                this.time = time;
                this.hashrate = hashrate;
                this.reject = reject;
            }

            public HashrateData(JSONObject jHashrateData) {
                super(jHashrateData);
                time = hItem.getLong("time", 0);
                hashrate = hItem.getString("hashrate");
                reject = hItem.getDouble("reject", 0);
            }

            public long getTime() {
                return time;
            }

            public String getHashrate() {
                return hashrate;
            }

            public double getReject() {
                return reject;
            }

        }

    }

}
