package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code ManagedSubAccountList} class is useful to format a managed subaccount list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-list-for-investor-user_data">
 * Query Managed Sub-account List (For Investor)(USER_DATA)</a>
 * @see BinanceItem
 **/
public class ManagedSubAccountList extends BinanceItem {

    /**
     * {@code total} of the managed subaccount list
     **/
    private final int total;

    /**
     * {@code subUsersInfo} sub users info list
     **/
    private final ArrayList<SubUserInfo> subUsersInfo;

    /**
     * Constructor to init {@link ManagedSubAccountList} object
     *
     * @param total : total of the managed subaccount list
     * @param subUsersInfo : sub users info list
     **/
    public ManagedSubAccountList(int total, ArrayList<SubUserInfo> subUsersInfo) {
        super(null);
        this.total = total;
        this.subUsersInfo = subUsersInfo;
    }

    /**
     * Constructor to init {@link ManagedSubAccountList} object
     *
     * @param jManagedSubAccountList : managed subaccount list details as {@link JSONObject}
     **/
    public ManagedSubAccountList(JSONObject jManagedSubAccountList) {
        super(jManagedSubAccountList);
        total = hItem.getInt("total", 0);
        subUsersInfo = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("managerSubUserInfoVoList");
        if (jList != null)
            for (JSONObject item : jList)
                subUsersInfo.add(new SubUserInfo(item));
    }

    /**
     * Method to get {@link #total} instance <br>
     * No-any params required
     *
     * @return {@link #total} instance as int
     **/
    public int getTotal() {
        return total;
    }

    /**
     * Method to get {@link #subUsersInfo} instance <br>
     * No-any params required
     *
     * @return {@link #subUsersInfo} instance as {@link ArrayList} of {@link SubUserInfo}
     **/
    public ArrayList<SubUserInfo> getSubUsersInfo() {
        return subUsersInfo;
    }

    /**
     * The {@code SubUserInfo} class is useful to format a sub user info
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class SubUserInfo extends BinanceItem {

        /**
         * {@code rootUserId} root user id of the sub user info
         **/
        private final long rootUserId;

        /**
         * {@code managersubUserId} manager sub user id of the sub user info
         **/
        private final long managersubUserId;

        /**
         * {@code bindParentUserId} bind parent user id of the sub user info
         **/
        private final long bindParentUserId;

        /**
         * {@code email} of the sub user info
         **/
        private final String email;

        /**
         * {@code insertTimestamp} insert timestamp of the sub user info
         **/
        private final long insertTimestamp;

        /**
         * {@code bindParentEmail} bind parent email of the sub user info
         **/
        private final String bindParentEmail;

        /**
         * {@code isSubUserEnabled} whether the sub user is enabled
         **/
        private final boolean isSubUserEnabled;

        /**
         * {@code isUserActive} whether the sub user is active
         **/
        private final boolean isUserActive;

        /**
         * {@code isMarginEnabled} whether the sub user has margin enabled
         **/
        private final boolean isMarginEnabled;

        /**
         * {@code isFutureEnabled} whether the sub user has future enabled
         **/
        private final boolean isFutureEnabled;

        /**
         * {@code isSignedLVTRiskAgreement} whether the sub user has signed LVT risk agreement
         **/
        private final boolean isSignedLVTRiskAgreement;

        /**
         * Constructor to init {@link SubUserInfo} object
         *
         * @param rootUserId : root user id of the sub user info
         * @param managersubUserId : manager sub user id of the sub user info
         * @param bindParentUserId : bind parent user id of the sub user info
         * @param email : email of the sub user info
         * @param insertTimestamp : insert timestamp of the sub user info
         * @param bindParentEmail : bind parent email of the sub user info
         * @param isSubUserEnabled : whether the sub user is enabled
         * @param isUserActive : whether the sub user is active
         * @param isMarginEnabled : whether the sub user has margin enabled
         * @param isFutureEnabled : whether the sub user has future enabled
         * @param isSignedLVTRiskAgreement : whether the sub user has signed LVT risk agreement
         **/
        public SubUserInfo(long rootUserId, long managersubUserId, long bindParentUserId, String email,
                           long insertTimestamp, String bindParentEmail, boolean isSubUserEnabled, boolean isUserActive,
                           boolean isMarginEnabled, boolean isFutureEnabled, boolean isSignedLVTRiskAgreement) {
            super(null);
            this.rootUserId = rootUserId;
            this.managersubUserId = managersubUserId;
            this.bindParentUserId = bindParentUserId;
            this.email = email;
            this.insertTimestamp = insertTimestamp;
            this.bindParentEmail = bindParentEmail;
            this.isSubUserEnabled = isSubUserEnabled;
            this.isUserActive = isUserActive;
            this.isMarginEnabled = isMarginEnabled;
            this.isFutureEnabled = isFutureEnabled;
            this.isSignedLVTRiskAgreement = isSignedLVTRiskAgreement;
        }

        /**
         * Constructor to init {@link SubUserInfo} object
         *
         * @param jSubUserInfo: sub user info details as {@link JSONObject}
         **/
        public SubUserInfo(JSONObject jSubUserInfo) {
            super(jSubUserInfo);
            rootUserId = hItem.getLong("rootUserId", 0);
            managersubUserId = hItem.getLong("managersubUserId", 0);
            bindParentUserId = hItem.getLong("bindParentUserId", 0);
            email = hItem.getString("email");
            insertTimestamp = hItem.getLong("insertTimeStamp", 0);
            bindParentEmail = hItem.getString("bindParentEmail");
            isSubUserEnabled = hItem.getBoolean("isSubUserEnabled");
            isUserActive = hItem.getBoolean("isUserActive");
            isMarginEnabled = hItem.getBoolean("isMarginEnabled");
            isFutureEnabled = hItem.getBoolean("isFutureEnabled");
            isSignedLVTRiskAgreement = hItem.getBoolean("isSignedLVTRiskAgreement");
        }

        /**
         * Method to get {@link #rootUserId} instance <br>
         * No-any params required
         *
         * @return {@link #rootUserId} instance as long
         **/
        public long getRootUserId() {
            return rootUserId;
        }

        /**
         * Method to get {@link #managersubUserId} instance <br>
         * No-any params required
         *
         * @return {@link #managersubUserId} instance as long
         **/
        public long getManagersubUserId() {
            return managersubUserId;
        }

        /**
         * Method to get {@link #bindParentUserId} instance <br>
         * No-any params required
         *
         * @return {@link #bindParentUserId} instance as long
         **/
        public long getBindParentUserId() {
            return bindParentUserId;
        }

        /**
         * Method to get {@link #email} instance <br>
         * No-any params required
         *
         * @return {@link #email} instance as {@link String}
         **/
        public String getEmail() {
            return email;
        }

        /**
         * Method to get {@link #insertTimestamp} instance <br>
         * No-any params required
         *
         * @return {@link #insertTimestamp} instance as long
         **/
        public long getInsertTimestamp() {
            return insertTimestamp;
        }

        /**
         * Method to get {@link #insertTimestamp} instance <br>
         * No-any params required
         *
         * @return {@link #insertTimestamp} instance as {@link Date}
         **/
        public Date getInsertDate() {
            return TimeFormatter.getDate(insertTimestamp);
        }

        /**
         * Method to get {@link #bindParentEmail} instance <br>
         * No-any params required
         *
         * @return {@link #bindParentEmail} instance as {@link String}
         **/
        public String getBindParentEmail() {
            return bindParentEmail;
        }

        /**
         * Method to get {@link #isSubUserEnabled} instance <br>
         * No-any params required
         *
         * @return {@link #isSubUserEnabled} instance as boolean
         **/
        public boolean isSubUserEnabled() {
            return isSubUserEnabled;
        }

        /**
         * Method to get {@link #isUserActive} instance <br>
         * No-any params required
         *
         * @return {@link #isUserActive} instance as boolean
         **/
        public boolean isUserActive() {
            return isUserActive;
        }

        /**
         * Method to get {@link #isMarginEnabled} instance <br>
         * No-any params required
         *
         * @return {@link #isMarginEnabled} instance as boolean
         **/
        public boolean isMarginEnabled() {
            return isMarginEnabled;
        }

        /**
         * Method to get {@link #isFutureEnabled} instance <br>
         * No-any params required
         *
         * @return {@link #isFutureEnabled} instance as boolean
         **/
        public boolean isFutureEnabled() {
            return isFutureEnabled;
        }

        /**
         * Method to get {@link #isSignedLVTRiskAgreement} instance <br>
         * No-any params required
         *
         * @return {@link #isSignedLVTRiskAgreement} instance as boolean
         **/
        public boolean isSignedLVTRiskAgreement() {
            return isSignedLVTRiskAgreement;
        }

    }

}
