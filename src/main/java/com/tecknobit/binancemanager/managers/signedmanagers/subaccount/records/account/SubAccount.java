package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class SubAccount extends BinanceItem {

    private final String email;
    private final boolean isFreeze;
    private final long createTime;
    private final boolean isManagedSubAccount;
    private final boolean isAssetManagementSubAccount;

    public SubAccount(String email, boolean isFreeze, long createTime, boolean isManagedSubAccount,
                      boolean isAssetManagementSubAccount) {
        super(null);
        this.email = email;
        this.isFreeze = isFreeze;
        this.createTime = createTime;
        this.isManagedSubAccount = isManagedSubAccount;
        this.isAssetManagementSubAccount = isAssetManagementSubAccount;
    }

    public SubAccount(JSONObject jSubAccount) {
        super(jSubAccount);
        email = hItem.getString("email");
        isFreeze = hItem.getBoolean("isFreeze");
        createTime = hItem.getLong("createTime", 0);
        isManagedSubAccount = hItem.getBoolean("isManagedSubAccount");
        isAssetManagementSubAccount = hItem.getBoolean("isAssetManagementSubAccount");
    }

    public String getEmail() {
        return email;
    }

    public boolean isFreeze() {
        return isFreeze;
    }

    public long getCreateTime() {
        return createTime;
    }

    public boolean isManagedSubAccount() {
        return isManagedSubAccount;
    }

    public boolean isAssetManagementSubAccount() {
        return isAssetManagementSubAccount;
    }

}
