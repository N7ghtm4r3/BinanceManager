package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code Withdraw} class is useful to manage Withdraw Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class Withdraw {

    private final String address;
    private final double amount;
    private final String applyTime;
    private final String coin;
    private final String id;
    private final String withdrawOrderId;
    private final String network;
    private final int transferType;
    private final int status;
    private final double transactionFee;
    private final int confirmNo;
    private final String info;
    private final String txId;

    public Withdraw(String address, double amount, String applyTime, String coin, String id, String withdrawOrderId,
                    String network, int transferType, int status, double transactionFee, int confirmNo, String info,
                    String txId) {
        this.address = address;
        this.amount = amount;
        this.applyTime = applyTime;
        this.coin = coin;
        this.id = id;
        this.withdrawOrderId = withdrawOrderId;
        this.network = network;
        this.transferType = transferType;
        this.status = status;
        this.transactionFee = transactionFee;
        this.confirmNo = confirmNo;
        this.info = info;
        this.txId = txId;
    }

    public String address() {
        return address;
    }

    public double amount() {
        return amount;
    }

    public String applyTime() {
        return applyTime;
    }

    public String coin() {
        return coin;
    }

    public String id() {
        return id;
    }

    public String withdrawOrderId() {
        return withdrawOrderId;
    }

    public String network() {
        return network;
    }

    public int transferType() {
        return transferType;
    }

    public int status() {
        return status;
    }

    public double transactionFee() {
        return transactionFee;
    }

    public int confirmNo() {
        return confirmNo;
    }

    public String info() {
        return info;
    }

    public String txId() {
        return txId;
    }

}
