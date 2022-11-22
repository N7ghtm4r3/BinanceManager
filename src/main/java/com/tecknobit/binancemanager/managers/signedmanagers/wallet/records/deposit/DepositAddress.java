package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit;

import org.json.JSONObject;

/**
 * The {@code DepositAddress} class is useful to format a {@code "Binance"}'s deposit address
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
 * Deposit Address (supporting network) (USER_DATA)</a>
 **/
public class DepositAddress {

    /**
     * {@code address} is instance that memorizes address value
     * **/
    private final String address;

    /**
     * {@code coin} is instance that memorizes coin value
     * **/
    private final String coin;

    /**
     * {@code tag} is instance that memorizes tag value
     * **/
    private final String tag;

    /**
     * {@code url} is instance that memorizes url value
     * **/
    private final String url;

    /** Constructor to init {@link DepositAddress} object
     * @param address: address value
     * @param coin: coin value
     * @param tag: address tag value
     * @param url: tx identifier value
     * **/
    public DepositAddress(String address, String coin, String tag, String url) {
        this.address = address;
        this.coin = coin;
        this.tag = tag;
        this.url = url;
    }

    /**
     * Constructor to init {@link DepositAddress} object
     *
     * @param jDeposit: deposit address details as {@link JSONObject}
     **/
    public DepositAddress(JSONObject jDeposit) {
        this(jDeposit.getString("address"), jDeposit.getString("coin"), jDeposit.getString("tag"),
                jDeposit.getString("url"));
    }

    /**
     * Method to get {@link #address} instance <br>
     * Any params required
     *
     * @return {@link #address} instance as {@link String}
     **/
    public String getAddress() {
        return address;
    }

    /**
     * Method to get {@link #coin} instance <br>
     * Any params required
     *
     * @return {@link #coin} instance as {@link String}
     **/
    public String getCoin() {
        return coin;
    }

    /**
     * Method to get {@link #tag} instance <br>
     * Any params required
     *
     * @return {@link #tag} instance as {@link String}
     **/
    public String getTag() {
        return tag;
    }

    /**
     * Method to get {@link #url} instance <br>
     * Any params required
     *
     * @return {@link #url} instance as {@link String}
     **/
    public String getUrl() {
        return url;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
