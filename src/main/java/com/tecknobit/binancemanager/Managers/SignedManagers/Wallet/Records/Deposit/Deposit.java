package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Deposit;

/**
 *  The {@code Deposit} class is useful to manage Deposit Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class Deposit {

    private final double amount;
    private final String coin;
    private final String network;
    private final int status;
    private final String address;
    private final String addressTag;
    private final String txId;
    private final long insertTime;
    private final int transferType;
    private final String unlockConfirm;
    private final String confirmTimes;

    public Deposit(double amount, String coin, String network, int status, String address, String addressTag,
                   String txId, long insertTime, int transferType, String unlockConfirm, String confirmTimes) {
        this.amount = amount;
        this.coin = coin;
        this.network = network;
        this.status = status;
        this.address = address;
        this.addressTag = addressTag;
        this.txId = txId;
        this.insertTime = insertTime;
        this.transferType = transferType;
        this.unlockConfirm = unlockConfirm;
        this.confirmTimes = confirmTimes;
    }

    public double amount() {
        return amount;
    }

    public String coin() {
        return coin;
    }

    public String network() {
        return network;
    }

    public int status() {
        return status;
    }

    public String address() {
        return address;
    }

    public String addressTag() {
        return addressTag;
    }

    public String txId() {
        return txId;
    }

    public long insertTime() {
        return insertTime;
    }

    public int transferType() {
        return transferType;
    }

    public String unlockConfirm() {
        return unlockConfirm;
    }

    public String confirmTimes() {
        return confirmTimes;
    }

}
