package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.hashrateresale;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

public class HashRateResaleConfiguration extends MiningResponse<Boolean> {

    public HashRateResaleConfiguration(Boolean data) {
        super(data);
    }

    public HashRateResaleConfiguration(JSONObject jHashRateResaleConfiguration) {
        super(jHashRateResaleConfiguration);
        data = hItem.getBoolean("data");
    }

}
