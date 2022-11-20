package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Withdraw} class is useful to manage {@code "Binance"}'s withdraw request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data</a>
 **/

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

    /**
     * Constructor to init {@link Withdraw} object
     *
     * @param withdraw: withdraw details as {@link JSONObject}
     **/
    public Withdraw(JSONObject withdraw) {
        address = withdraw.getString("address");
        amount = withdraw.getDouble("amount");
        applyTime = withdraw.getString("applyTime");
        coin = withdraw.getString("coin");
        id = withdraw.getString("id");
        withdrawOrderId = withdraw.getString("withdrawOrderId");
        network = withdraw.getString("network");
        transferType = withdraw.getInt("transferType");
        status = withdraw.getInt("status");
        transactionFee = withdraw.getDouble("transactionFee");
        confirmNo = withdraw.getInt("confirmNo");
        info = withdraw.getString("info");
        txId = withdraw.getString("txId");
    }

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
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

    /**
     * Method to get {@link #transactionFee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #transactionFee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTransactionFee(int decimals) {
        return roundValue(transactionFee, decimals);
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
