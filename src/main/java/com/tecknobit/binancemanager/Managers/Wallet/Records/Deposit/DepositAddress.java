package com.tecknobit.binancemanager.Managers.Wallet.Records.Deposit;

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
