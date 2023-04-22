package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.restriction;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code RestrictionStructure} class is useful to format a restriction structure
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
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#update-ip-restriction-for-sub-account-api-key-for-master-account">
 *             Update IP Restriction for Sub-Account API key (For Master Account)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public abstract class RestrictionStructure extends BinanceItem {

    /**
     * {@code ipList} ip list of the restriction structure
     **/
    protected final ArrayList<String> ipList;

    /**
     * {@code updateTime} update time of the restriction structure
     **/
    protected final long updateTime;

    /**
     * {@code apiKey} api key of the restriction structure
     **/
    protected final String apiKey;

    /**
     * Constructor to init {@link RestrictionStructure} object
     *
     * @param ipList     : ip list of the restriction structure
     * @param updateTime : update time of the restriction structure
     * @param apiKey     : api key of the restriction structure
     **/
    public RestrictionStructure(ArrayList<String> ipList, long updateTime, String apiKey) {
        super(null);
        this.ipList = ipList;
        this.updateTime = updateTime;
        this.apiKey = apiKey;
    }

    /**
     * Constructor to init {@link RestrictionStructure} object
     *
     * @param jRestrictionStructure : restriction structure details as {@link JSONObject}
     **/
    public RestrictionStructure(JSONObject jRestrictionStructure) {
        super(jRestrictionStructure);
        ipList = hItem.fetchList("ipList");
        updateTime = hItem.getLong("updateTime", 0);
        apiKey = hItem.getString("apiKey");
    }

    /**
     * Method to get {@link #ipList} instance <br>
     * No-any params required
     *
     * @return {@link #ipList} instance as {@link ArrayList} of {@link String}
     **/
    public ArrayList<String> getIpList() {
        return ipList;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     **/
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     **/
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * Method to get {@link #apiKey} instance <br>
     * No-any params required
     *
     * @return {@link #apiKey} instance as {@link String}
     **/
    public String getApiKey() {
        return apiKey;
    }

}
