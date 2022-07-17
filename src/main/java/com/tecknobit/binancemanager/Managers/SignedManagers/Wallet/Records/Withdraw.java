package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code Withdraw} class is useful to manage Withdraw Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
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

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public String getCoin() {
        return coin;
    }

    public String getId() {
        return id;
    }

    public String getWithdrawOrderId() {
        return withdrawOrderId;
    }

    public String getNetwork() {
        return network;
    }

    public int getTransferType() {
        return transferType;
    }

    public int getStatus() {
        return status;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public int getConfirmNo() {
        return confirmNo;
    }

    public String getInfo() {
        return info;
    }

    public String getTxId() {
        return txId;
    }

}
