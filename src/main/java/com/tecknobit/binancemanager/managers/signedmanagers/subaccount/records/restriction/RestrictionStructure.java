package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class RestrictionStructure extends BinanceItem {
    protected final ArrayList<String> ipList;
    protected final long updateTime;
    protected final String apiKey;

    public RestrictionStructure(ArrayList<String> ipList, long updateTime, String apiKey) {
        super(null);
        this.ipList = ipList;
        this.updateTime = updateTime;
        this.apiKey = apiKey;
    }

    public RestrictionStructure(JSONObject jRestrictionStructure) {
        super(jRestrictionStructure);
        ipList = hItem.fetchList("ipList");
        updateTime = hItem.getLong("updateTime", 0);
        apiKey = hItem.getString("apiKey");
    }

    public ArrayList<String> getIpList() {
        return ipList;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getApiKey() {
        return apiKey;
    }

}
