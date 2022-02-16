package com.tecknobit.binancemanager.Managers.Wallet.Records;

public record Withdraw (String address, double amount, String applyTime, String coin, String id, String withdrawOrderId,
                        String network, int transferType, int status, double transactionFee, int confirmNo, String info,
                        String txId) {

    @Override
    public String address() {
        return address;
    }

    @Override
    public double amount() {
        return amount;
    }

    @Override
    public String applyTime() {
        return applyTime;
    }

    @Override
    public String coin() {
        return coin;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String withdrawOrderId() {
        return withdrawOrderId;
    }

    @Override
    public String network() {
        return network;
    }

    @Override
    public int transferType() {
        return transferType;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public double transactionFee() {
        return transactionFee;
    }

    @Override
    public int confirmNo() {
        return confirmNo;
    }

    @Override
    public String info() {
        return info;
    }

    @Override
    public String txId() {
        return txId;
    }

    @Override
    public String toString() {
        return "Withdraw{" +
                "address='" + address + '\'' +
                ", amount=" + amount +
                ", applyTime='" + applyTime + '\'' +
                ", coin='" + coin + '\'' +
                ", id='" + id + '\'' +
                ", withdrawOrderId='" + withdrawOrderId + '\'' +
                ", network='" + network + '\'' +
                ", transferType=" + transferType +
                ", status=" + status +
                ", transactionFee=" + transactionFee +
                ", confirmNo=" + confirmNo +
                ", info='" + info + '\'' +
                ", txId='" + txId + '\'' +
                '}';
    }

}
