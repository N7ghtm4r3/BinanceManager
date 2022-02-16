package com.tecknobit.binancemanager.Managers.Wallet.Records.Deposit;

public record DepositAddress(String address, String coin, String tag, String url) {

    @Override
    public String address() {
        return address;
    }

    @Override
    public String coin() {
        return coin;
    }

    @Override
    public String tag() {
        return tag;
    }

    @Override
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
