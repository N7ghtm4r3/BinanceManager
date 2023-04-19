package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction;

import org.json.JSONObject;

import java.util.ArrayList;

public class IPRestriction extends RestrictionStructure {

    private final boolean ipRestrict;

    public IPRestriction(ArrayList<String> ipList, long updateTime, String apiKey, boolean ipRestrict) {
        super(ipList, updateTime, apiKey);
        this.ipRestrict = ipRestrict;
    }

    public IPRestriction(JSONObject jIPRestriction) {
        super(jIPRestriction);
        ipRestrict = hItem.getBoolean("ipRestrict");
    }

    public boolean isIpRestrict() {
        return ipRestrict;
    }

}
