package com.tecknobit.binancemanager.Managers.Wallet.Records.Deposit;

public record Deposit (double amount, String coin, String network, int status, String address, String addressTag,
                      String txId, long insertTime, int transferType, String unlockConfirm, String confirmTimes) {

    @Override
    public double amount() {
        return amount;
    }

    @Override
    public String coin() {
        return coin;
    }

    @Override
    public String network() {
        return network;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String addressTag() {
        return addressTag;
    }

    @Override
    public String txId() {
        return txId;
    }

    @Override
    public long insertTime() {
        return insertTime;
    }

    @Override
    public int transferType() {
        return transferType;
    }

    @Override
    public String unlockConfirm() {
        return unlockConfirm;
    }

    @Override
    public String confirmTimes() {
        return confirmTimes;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "amount=" + amount +
                ", coin='" + coin + '\'' +
                ", network='" + network + '\'' +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", addressTag='" + addressTag + '\'' +
                ", txId='" + txId + '\'' +
                ", insertTime=" + insertTime +
                ", transferType=" + transferType +
                ", unlockConfirm='" + unlockConfirm + '\'' +
                ", confirmTimes='" + confirmTimes + '\'' +
                '}';
    }

}
