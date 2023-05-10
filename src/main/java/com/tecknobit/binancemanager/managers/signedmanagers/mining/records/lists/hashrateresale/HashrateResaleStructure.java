package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code HashrateResaleStructure} class is useful to create a hash rate resale structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
 *             Hashrate Resale List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-detail-user_data">
 *             Hashrate Resale Detail (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
@Structure
public abstract class HashrateResaleStructure extends BinanceItem {

    /**
     * {@code poolUsername} transfer out of subaccount
     **/
    protected final String poolUsername;

    /**
     * {@code toPoolUsername} transfer into subaccount
     **/
    protected final String toPoolUsername;

    /**
     * {@code algoName} transfer algorithm
     **/
    protected final String algoName;

    /**
     * {@code hashRate} transferred hashrate quantity
     **/
    protected final long hashRate;

    /**
     * Constructor to init {@link HashrateResaleStructure} object
     *
     * @param poolUsername:   transfer out of subaccount
     * @param toPoolUsername: transfer into subaccount
     * @param algoName:       transfer algorithm
     * @param hashRate:       transferred hashrate quantity
     **/
    public HashrateResaleStructure(String poolUsername, String toPoolUsername, String algoName, long hashRate) {
        super(null);
        this.poolUsername = poolUsername;
        this.toPoolUsername = toPoolUsername;
        this.algoName = algoName;
        this.hashRate = hashRate;
    }

    /**
     * Constructor to init {@link HashrateResaleStructure} object
     *
     * @param jHashrateResaleStructure: hashrate resale structure details as {@link JSONObject}
     **/
    public HashrateResaleStructure(JSONObject jHashrateResaleStructure) {
        super(jHashrateResaleStructure);
        poolUsername = hItem.getString("poolUsername");
        toPoolUsername = hItem.getString("toPoolUsername");
        algoName = hItem.getString("algoName");
        hashRate = hItem.getLong("hashRate", 0);
    }

    /**
     * Method to get {@link #poolUsername} instance <br>
     * No-any params required
     *
     * @return {@link #poolUsername} instance as {@link String}
     **/
    public String getPoolUsername() {
        return poolUsername;
    }

    /**
     * Method to get {@link #toPoolUsername} instance <br>
     * No-any params required
     *
     * @return {@link #toPoolUsername} instance as {@link String}
     **/
    public String getToPoolUsername() {
        return toPoolUsername;
    }

    /**
     * Method to get {@link #algoName} instance <br>
     * No-any params required
     *
     * @return {@link #algoName} instance as {@link String}
     **/
    public String getAlgoName() {
        return algoName;
    }

    /**
     * Method to get {@link #hashRate} instance <br>
     * No-any params required
     *
     * @return {@link #hashRate} instance as long
     **/
    public long getHashRate() {
        return hashRate;
    }

}
