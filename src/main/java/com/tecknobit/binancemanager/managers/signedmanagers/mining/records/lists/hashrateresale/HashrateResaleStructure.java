package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class HashrateResaleStructure extends BinanceItem {

    protected final String poolUsername;
    protected final String toPoolUsername;
    protected final String algoName;
    protected final long hashRate;

    public HashrateResaleStructure(String poolUsername, String toPoolUsername, String algoName, long hashRate) {
        super(null);
        this.poolUsername = poolUsername;
        this.toPoolUsername = toPoolUsername;
        this.algoName = algoName;
        this.hashRate = hashRate;
    }

    public HashrateResaleStructure(JSONObject jHashrateResaleStructure) {
        super(jHashrateResaleStructure);
        poolUsername = hItem.getString("poolUsername");
        toPoolUsername = hItem.getString("toPoolUsername");
        algoName = hItem.getString("algoName");
        hashRate = hItem.getLong("hashRate", 0);
    }

    public String getPoolUsername() {
        return poolUsername;
    }

    public String getToPoolUsername() {
        return toPoolUsername;
    }

    public String getAlgoName() {
        return algoName;
    }

    public long getHashRate() {
        return hashRate;
    }

}
