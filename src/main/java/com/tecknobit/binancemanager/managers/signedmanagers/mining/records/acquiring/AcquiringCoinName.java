package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring.AcquiringCoinName.AcquiringCoinNameItem;

public class AcquiringCoinName extends MiningResponse<ArrayList<AcquiringCoinNameItem>> {

    public AcquiringCoinName(ArrayList<AcquiringCoinNameItem> data) {
        super(data);
    }

    public AcquiringCoinName(JSONObject jMiningResponse) {
        super(jMiningResponse);
        data = new ArrayList<>();
        for (Object rData : hItem.fetchList("data"))
            data.add(new AcquiringCoinNameItem((JSONObject) rData));
    }

    public static class AcquiringCoinNameItem extends AcquiringStructure {

        private final String coinName;
        private final long coinId;

        public AcquiringCoinNameItem(String algoName, long algoId, int poolIndex, String coinName, long coinId) {
            super(algoName, algoId, poolIndex);
            this.coinName = coinName;
            this.coinId = coinId;
        }

        public AcquiringCoinNameItem(JSONObject jAcquiringCoinNameItem) {
            super(jAcquiringCoinNameItem);
            coinName = hItem.getString("coinName");
            coinId = hItem.getLong("coinId", 0);
        }

        public String getCoinName() {
            return coinName;
        }

        public long getCoinId() {
            return coinId;
        }

    }

}
