package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring.AcquiringAlgorithm.AcquiringAlgorithmItem;

public class AcquiringAlgorithm extends MiningResponse<ArrayList<AcquiringAlgorithmItem>> {

    public AcquiringAlgorithm(ArrayList<AcquiringAlgorithmItem> data) {
        super(data);
    }

    public AcquiringAlgorithm(JSONObject jMiningResponse) {
        super(jMiningResponse);
        data = new ArrayList<>();
        for (Object rData : hItem.fetchList("data"))
            data.add(new AcquiringAlgorithmItem((JSONObject) rData));
    }

    public static class AcquiringAlgorithmItem extends AcquiringStructure {

        private final String unit;

        public AcquiringAlgorithmItem(String algoName, long algoId, int poolIndex, String unit) {
            super(algoName, algoId, poolIndex);
            this.unit = unit;
        }

        public AcquiringAlgorithmItem(JSONObject jAcquiringAlgorithm) {
            super(jAcquiringAlgorithm);
            unit = hItem.getString("unit");
        }

        public String getUnit() {
            return unit;
        }

    }

}
