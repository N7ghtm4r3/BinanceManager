package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class SubAccountEnabledResult extends BinanceItem {

    private final String email;
    private final boolean isAccountEnabled;

    public SubAccountEnabledResult(String email, boolean isAccountEnabled) {
        super(null);
        this.email = email;
        this.isAccountEnabled = isAccountEnabled;
    }

    public SubAccountEnabledResult(JSONObject jSubAccountEnabledResult) {
        super(jSubAccountEnabledResult);
        email = hItem.getString("email");
        boolean enabled = hItem.getBoolean("isMarginEnabled");
        if (!enabled)
            isAccountEnabled = hItem.getBoolean("isFuturesEnabled");
        else
            isAccountEnabled = true;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAccountEnabled() {
        return isAccountEnabled;
    }

}
