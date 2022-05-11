package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Deposit;

/**
 *  The {@code Deposit} class is useful to manage Deposit Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class Deposit {

    private double amount;
    private final String coin;
    private final String network;
    private int status;
    private String address;
    private String addressTag;
    private String txId;
    private long insertTime;
    private int transferType;
    private String unlockConfirm;
    private String confirmTimes;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        this.amount = amount;
    }

    public String getCoin() {
        return coin;
    }

    public String getNetwork() {
        return network;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if(status < 0 || status > 6)
            throw new IllegalArgumentException("Status value can only be from 0 to 6");
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressTag() {
        return addressTag;
    }

    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(long insertTime) {
        if(insertTime < 0)
            throw new IllegalArgumentException("Insert time value cannot be less than 0");
        this.insertTime = insertTime;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        if(transferType < 0 || transferType > 1)
            throw new IllegalArgumentException("Status value can only be from 0 to 1");
        this.transferType = transferType;
    }

    public String getUnlockConfirm() {
        return unlockConfirm;
    }

    public void setUnlockConfirm(String unlockConfirm) {
        this.unlockConfirm = unlockConfirm;
    }

    public String getConfirmTimes() {
        return confirmTimes;
    }

    public void setConfirmTimes(String confirmTimes) {
        this.confirmTimes = confirmTimes;
    }

}
