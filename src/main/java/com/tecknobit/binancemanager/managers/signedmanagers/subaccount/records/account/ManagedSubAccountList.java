package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManagedSubAccountList extends BinanceItem {

    private final int total;
    private final ArrayList<SubUserInfo> subUsersInfo;

    public ManagedSubAccountList(int total, ArrayList<SubUserInfo> subUsersInfo) {
        super(null);
        this.total = total;
        this.subUsersInfo = subUsersInfo;
    }

    public ManagedSubAccountList(JSONObject jManagedSubAccountList) {
        super(jManagedSubAccountList);
        total = hItem.getInt("total", 0);
        subUsersInfo = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("managerSubUserInfoVoList");
        if (jList != null)
            for (JSONObject item : jList)
                subUsersInfo.add(new SubUserInfo(item));
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<SubUserInfo> getSubUsersInfo() {
        return subUsersInfo;
    }

    public static class SubUserInfo extends BinanceItem {

        private final long rootUserId;
        private final long managersubUserId;
        private final long bindParentUserId;
        private final String email;
        private final long insertTimeStamp;
        private final String bindParentEmail;
        private final boolean isSubUserEnabled;
        private final boolean isUserActive;
        private final boolean isMarginEnabled;
        private final boolean isFutureEnabled;
        private final boolean isSignedLVTRiskAgreement;

        public SubUserInfo(long rootUserId, long managersubUserId, long bindParentUserId, String email,
                           long insertTimeStamp, String bindParentEmail, boolean isSubUserEnabled, boolean isUserActive,
                           boolean isMarginEnabled, boolean isFutureEnabled, boolean isSignedLVTRiskAgreement) {
            super(null);
            this.rootUserId = rootUserId;
            this.managersubUserId = managersubUserId;
            this.bindParentUserId = bindParentUserId;
            this.email = email;
            this.insertTimeStamp = insertTimeStamp;
            this.bindParentEmail = bindParentEmail;
            this.isSubUserEnabled = isSubUserEnabled;
            this.isUserActive = isUserActive;
            this.isMarginEnabled = isMarginEnabled;
            this.isFutureEnabled = isFutureEnabled;
            this.isSignedLVTRiskAgreement = isSignedLVTRiskAgreement;
        }

        public SubUserInfo(JSONObject jSubUserInfo) {
            super(jSubUserInfo);
            rootUserId = hItem.getLong("rootUserId", 0);
            managersubUserId = hItem.getLong("managersubUserId", 0);
            bindParentUserId = hItem.getLong("bindParentUserId", 0);
            email = hItem.getString("email");
            insertTimeStamp = hItem.getLong("insertTimeStamp", 0);
            bindParentEmail = hItem.getString("bindParentEmail");
            isSubUserEnabled = hItem.getBoolean("isSubUserEnabled");
            isUserActive = hItem.getBoolean("isUserActive");
            isMarginEnabled = hItem.getBoolean("isMarginEnabled");
            isFutureEnabled = hItem.getBoolean("isFutureEnabled");
            isSignedLVTRiskAgreement = hItem.getBoolean("isSignedLVTRiskAgreement");
        }

        public long getRootUserId() {
            return rootUserId;
        }

        public long getManagersubUserId() {
            return managersubUserId;
        }

        public long getBindParentUserId() {
            return bindParentUserId;
        }

        public String getEmail() {
            return email;
        }

        public long getInsertTimeStamp() {
            return insertTimeStamp;
        }

        public String getBindParentEmail() {
            return bindParentEmail;
        }

        public boolean isSubUserEnabled() {
            return isSubUserEnabled;
        }

        public boolean isUserActive() {
            return isUserActive;
        }

        public boolean isMarginEnabled() {
            return isMarginEnabled;
        }

        public boolean isFutureEnabled() {
            return isFutureEnabled;
        }

        public boolean isSignedLVTRiskAgreement() {
            return isSignedLVTRiskAgreement;
        }

    }

}
