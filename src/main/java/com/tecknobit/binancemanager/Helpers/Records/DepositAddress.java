package com.tecknobit.binancemanager.Helpers.Records;

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

}
