package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records;

/**
 *  The {@code Withdraw} class is useful to manage Withdraw Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class Withdraw {

    /**
     * {@code address} is instance that memorizes address value
     * **/
    private final String address;

    /**
     * {@code amount} is instance that memorizes amount value
     * **/
    private final double amount;

    /**
     * {@code applyTime} is instance that memorizes apply time value
     * **/
    private final String applyTime;

    /**
     * {@code coin} is instance that memorizes coin value
     * **/
    private final String coin;

    /**
     * {@code id} is instance that memorizes identifier value
     * **/
    private final String id;

    /**
     * {@code withdrawOrderId} is instance that memorizes identifier of withdraw value
     * **/
    private final String withdrawOrderId;

    /**
     * {@code network} is instance that memorizes network value
     * **/
    private final String network;

    /**
     * {@code transferType} is instance that memorizes transfer type value
     * **/
    private final int transferType;

    /**
     * {@code status} is instance that memorizes transfer status value
     * **/
    private final int status;

    /**
     * {@code transactionFee} is instance that memorizes transaction fee value
     * **/
    private final double transactionFee;

    /**
     * {@code confirmNo} is instance that memorizes confirms number value
     * **/
    private final int confirmNo;

    /**
     * {@code info} is instance that memorizes info value
     * **/
    private final String info;

    /**
     * {@code txId} is instance that memorizes transaction identifier value
     * **/
    private final String txId;

    /** Constructor to init {@link Withdraw} object
     * @param address: address value
     * @param amount: amount value
     * @param applyTime: apply time value
     * @param coin: coin value
     * @param id: identifier value
     * @param withdrawOrderId: identifier of withdraw value
     * @param network: network value
     * @param transferType: transfer type value
     * @param status: status value
     * @param transactionFee: transaction fee value
     * @param confirmNo: confirms number value
     * @param info: info value
     * @param txId: transaction identifier value
     * **/
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
