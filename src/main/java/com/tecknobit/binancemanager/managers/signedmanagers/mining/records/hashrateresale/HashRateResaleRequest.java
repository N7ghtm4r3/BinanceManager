package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.hashrateresale;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

public class HashRateResaleRequest extends MiningResponse<Integer> {

    public HashRateResaleRequest(Integer data) {
        super(data);
    }

    public HashRateResaleRequest(JSONObject jHashRateResaleRequest) {
        super(jHashRateResaleRequest);
        data = hItem.getInt("data", 0);
    }

}
