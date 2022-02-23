package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Deposit;

/**
 *  The {@code DepositAddress} class is useful to manage DepositAddress Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-address-supporting-network-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class DepositAddress {

    private final String address;
    private final String coin;
    private final String tag;
    private final String url;

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

}
