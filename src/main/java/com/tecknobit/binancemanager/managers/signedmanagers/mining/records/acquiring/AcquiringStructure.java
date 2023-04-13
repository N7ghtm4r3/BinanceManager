package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class AcquiringStructure extends BinanceItem {

    protected final String algoName;
    protected final long algoId;
    protected final int poolIndex;

    public AcquiringStructure(String algoName, long algoId, int poolIndex) {
        super(null);
        this.algoName = algoName;
        this.algoId = algoId;
        this.poolIndex = poolIndex;
    }

    public AcquiringStructure(JSONObject jAcquiringStructure) {
        super(jAcquiringStructure);
        algoName = hItem.getString("algoName");
        algoId = hItem.getLong("algoId", 0);
        poolIndex = hItem.getInt("poolIndex", 0);
    }

    public String getAlgoName() {
        return algoName;
    }

    public long getAlgoId() {
        return algoId;
    }

    public int getPoolIndex() {
        return poolIndex;
    }

}
