package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Deposit} class is useful to manage Deposit Binance request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data</a>
 **/

public class Deposit {

    /**
     * {@code amount} is instance that memorizes amount value
     * **/
    private double amount;

    /**
     * {@code coin} is instance that memorizes coin value
     * **/
    private final String coin;

    /**
     * {@code network} is instance that memorizes network value
     * **/
    private final String network;

    /**
     * {@code status} is instance that memorizes status value
     * **/
    private int status;

    /**
     * {@code address} is instance that memorizes address value
     * **/
    private String address;

    /**
     * {@code addressTag} is instance that memorizes address tag value
     * **/
    private String addressTag;

    /**
     * {@code txId} is instance that memorizes tx identifier value
     * **/
    private String txId;

    /**
     * {@code insertTime} is instance that memorizes insert time value
     * **/
    private long insertTime;

    /**
     * {@code transferType} is instance that memorizes transfer type value
     * **/
    private int transferType;

    /**
     * {@code unlockConfirm} is instance that memorizes unlock confirm value
     * **/
    private String unlockConfirm;

    /**
     * {@code confirmTimes} is instance that memorizes confirm times value
     * **/
    private String confirmTimes;

    /** Constructor to init {@link Deposit} object
     * @param amount: amount value
     * @param coin: coin value
     * @param network: network value
     * @param status: status value
     * @param address: address value
     * @param addressTag: address tag value
     * @param txId: tx identifier value
     * @param insertTime: insert time value
     * @param transferType: transfer type value
     * @param unlockConfirm: unlock confirm value
     * @param confirmTimes: confirm times value
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public Deposit(double amount, String coin, String network, int status, String address, String addressTag,
                   String txId, long insertTime, int transferType, String unlockConfirm, String confirmTimes) {
        if(amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        else
            this.amount = amount;
        this.coin = coin;
        this.network = network;
        if(status < 0 || status > 6)
            throw new IllegalArgumentException("Status value can only be from 0 to 6");
        else
            this.status = status;
        this.address = address;
        this.addressTag = addressTag;
        this.txId = txId;
        if (insertTime < 0)
            throw new IllegalArgumentException("Insert time value cannot be less than 0");
        else
            this.insertTime = insertTime;
        if (transferType < 0 || transferType > 1)
            throw new IllegalArgumentException("Status value can only be from 0 to 1");
        else
            this.transferType = transferType;
        this.unlockConfirm = unlockConfirm;
        this.confirmTimes = confirmTimes;
    }

    /**
     * Constructor to init {@link Deposit} object
     *
     * @param deposit: deposit details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public Deposit(JSONObject deposit) {
        amount = deposit.getDouble("amount");
        if (amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        coin = deposit.getString("coin");
        network = deposit.getString("network");
        status = deposit.getInt("status");
        if (status < 0 || status > 6)
            throw new IllegalArgumentException("Status value can only be from 0 to 6");
        address = deposit.getString("address");
        addressTag = deposit.getString("addressTag");
        txId = deposit.getString("txId");
        insertTime = deposit.getLong("insertTime");
        if (insertTime < 0)
            throw new IllegalArgumentException("Insert time value cannot be less than 0");
        transferType = deposit.getInt("transferType");
        if (transferType < 0 || transferType > 1)
            throw new IllegalArgumentException("Status value can only be from 0 to 1");
        unlockConfirm = deposit.getString("unlockConfirm");
        confirmTimes = deposit.getString("confirmTimes");
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

    /**
     * Method to set {@link #amount}
     *
     * @param amount: amount value
     * @throws IllegalArgumentException when amount value is less than 0
     **/
    public void setAmount(double amount) {
        if (amount < 0)
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

    /** Method to set {@link #status}
     * @param status: status value
     * @throws IllegalArgumentException when status value is less than 0
     * **/
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

    /** Method to set {@link #insertTime}
     * @param insertTime: insert time value
     * @throws IllegalArgumentException when insert time value is less than 0
     * **/
    public void setInsertTime(long insertTime) {
        if(insertTime < 0)
            throw new IllegalArgumentException("Insert time value cannot be less than 0");
        this.insertTime = insertTime;
    }

    public int getTransferType() {
        return transferType;
    }

    /** Method to set {@link #transferType}
     * @param transferType: transfer type value
     * @throws IllegalArgumentException when transfer type value is less than 0
     * **/
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
