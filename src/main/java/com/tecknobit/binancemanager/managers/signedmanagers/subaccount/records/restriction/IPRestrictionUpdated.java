package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code IPRestrictionUpdated} class is useful to format an ip restriction updated
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
 * Update IP Restriction for Sub-Account API key (For Master Account)</a>
 * @see BinanceItem
 * @see RestrictionStructure
 **/
public class IPRestrictionUpdated extends RestrictionStructure {

    /**
     * {@code IPStatus} list of available ip statuses
     **/
    public enum IPStatus {

        /**
         * {@code unrestricted} ip status
         **/
        unrestricted(1),

        /**
         * {@code restricted_to_trusted_ips} ip status
         **/
        restricted_to_trusted_ips(2);

        /**
         * {@code status} value
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
            return VALUES.get(status - 1);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as int
         **/
        public int getStatus() {
            return status;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         **/
        @Override
        public String toString() {
            return status + "";
        }

    }

    /**
     * {@code status} value
     **/
    private final IPStatus status;

    /**
     * Constructor to init {@link IPRestrictionUpdated} object
     *
     * @param ipList     : ip list of the ip restriction updated
     * @param updateTime : update time of the ip restriction updated
     * @param apiKey     : api key of the ip restriction updated
     * @param status     : status value
     **/
    public IPRestrictionUpdated(ArrayList<String> ipList, long updateTime, String apiKey, IPStatus status) {
        super(ipList, updateTime, apiKey);
        this.status = status;
    }

    /**
     * Constructor to init {@link IPRestrictionUpdated} object
     *
     * @param jIPRestrictionUpdated : ip restriction updated details as {@link JSONObject}
     **/
    public IPRestrictionUpdated(JSONObject jIPRestrictionUpdated) {
        super(jIPRestrictionUpdated);
        status = IPStatus.reachEnumConstant(hItem.getInt("status"));
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link IPStatus}
     **/
    public IPStatus getStatus() {
        return status;
    }

}
