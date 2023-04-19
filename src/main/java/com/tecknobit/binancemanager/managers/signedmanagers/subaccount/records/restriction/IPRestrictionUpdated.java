package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IPRestrictionUpdated extends IPRestriction {

    public enum IPStatus {

        unrestricted(1),
        restricted_to_trusted_ips(2);

        /**
         * {@code "status"} value
         **/
        private final int status;

        /**
         * {@code VALUES} list of the statuses
         **/
        private static final List<IPStatus> VALUES = Arrays.stream(IPStatus.values()).toList();

        /**
         * Constructor to init {@link IPStatus}
         *
         * @param status: status value
         **/
        IPStatus(int status) {
            this.status = status;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constant as {@link IPStatus}
         **/
        public static IPStatus reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        public int getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return status + "";
        }

    }

    private final IPStatus status;

    public IPRestrictionUpdated(ArrayList<String> ipList, long updateTime, String apiKey, boolean ipRestrict,
                                IPStatus status) {
        super(ipList, updateTime, apiKey, ipRestrict);
        this.status = status;
    }

    public IPRestrictionUpdated(JSONObject jIPRestrictionUpdated) {
        super(jIPRestrictionUpdated);
        status = IPStatus.reachEnumConstant(hItem.getInt("status"));
    }

    public IPStatus getStatus() {
        return status;
    }

}
