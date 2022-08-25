package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Deposit;

/**
 *  The {@code DepositAddress} class is useful to manage DepositAddress Binance request
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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

    public String address() {
        return address;
    }

    public String coin() {
        return coin;
    }

    public String tag() {
        return tag;
    }

    public String url() {
        return url;
    }

    @Override
    public String toString() {
        return "DepositAddress{" +
                "address='" + address + '\'' +
                ", coin='" + coin + '\'' +
                ", tag='" + tag + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
