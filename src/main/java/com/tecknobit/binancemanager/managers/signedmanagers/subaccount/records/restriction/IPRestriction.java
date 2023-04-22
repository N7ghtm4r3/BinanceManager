package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code IPRestriction} class is useful to format an ip restriction
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-ip-restriction-for-a-sub-account-api-key-for-master-account">
 *             Get IP Restriction for a Sub-account API Key (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#delete-ip-list-for-a-sub-account-api-key-for-master-account">
 *             Delete IP List For a Sub-account API Key (For Master Account)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see RestrictionStructure
 **/
public class IPRestriction extends RestrictionStructure {

    /**
     * {@code ipRestrict} whether the ip is restricted
     **/
    private final boolean ipRestrict;

    /**
     * Constructor to init {@link IPRestriction} object
     *
     * @param ipList      : ip list of the ip restriction
     * @param updateTime  : update time of the ip restriction
     * @param apiKey      : api key of the ip restriction
     * @param ipRestrict: whether the ip is restricted
     **/
    public IPRestriction(ArrayList<String> ipList, long updateTime, String apiKey, boolean ipRestrict) {
        super(ipList, updateTime, apiKey);
        this.ipRestrict = ipRestrict;
    }

    /**
     * Constructor to init {@link IPRestriction} object
     *
     * @param jIPRestriction : ip restriction details as {@link JSONObject}
     **/
    public IPRestriction(JSONObject jIPRestriction) {
        super(jIPRestriction);
        ipRestrict = hItem.getBoolean("ipRestrict");
    }

    /**
     * Method to get {@link #ipRestrict} instance <br>
     * No-any params required
     *
     * @return {@link #ipRestrict} instance as boolean
     **/
    public boolean isIpRestrict() {
        return ipRestrict;
    }

}
