package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import org.json.JSONObject;

import java.util.ArrayList;

public class SwapPool extends PoolStructure {

    private final ArrayList<String> assets;

    public SwapPool(long poolId, String poolName, ArrayList<String> assets) {
        super(poolId, poolName);
        this.assets = assets;
    }

    public SwapPool(JSONObject jSwapPool) {
        super(jSwapPool);
        assets = new ArrayList<>();
        for (Object asset : hItem.fetchList("assets"))
            assets.add((String) asset);
    }

    public ArrayList<String> getAssets() {
        return assets;
    }

}
