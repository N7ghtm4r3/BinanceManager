package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit.DepositStatus.valueOf;

/**
 * The {@code Deposit} class is useful to format a {@code "Binance"}'s deposit
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
 * Deposit History (supporting network) (USER_DATA)</a>
 **/
public class Deposit {

    /**
     * {@code status} is instance that memorizes status value
     **/
    private DepositStatus status;

    /**
     * {@code amount} is instance that memorizes amount value
     **/
    private double amount;

    /**
     * {@code coin} is instance that memorizes coin value
     **/
    private final String coin;

    /**
     * {@code network} is instance that memorizes network value
     * **/
    private final String network;
    /**
     * {@code unlockConfirm} is instance that memorizes unlock confirm value
     **/
    private int unlockConfirm;

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
     **/
    private final String txId;

    /**
     * {@code insertTime} is instance that memorizes insert time value
     * **/
    private long insertTime;

    /**
     * {@code transferType} is instance that memorizes transfer type value
     * **/
    private int transferType;

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
    public Deposit(double amount, String coin, String network, DepositStatus status, String address, String addressTag,
                   String txId, long insertTime, int transferType, int unlockConfirm, String confirmTimes) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        else
            this.amount = amount;
        this.coin = coin;
        this.network = network;
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
     * {@code confirmTimes} is instance that memorizes confirm times value
     **/
    private String confirmTimes;

    /**
     * Constructor to init {@link Deposit} object
     *
     * @param deposit: deposit details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public Deposit(JSONObject deposit) {
        this(deposit.getDouble("amount"), deposit.getString("coin"), deposit.getString("network"),
                valueOf(deposit.getInt("status")), deposit.getString("address"), deposit.getString("addressTag"),
                deposit.getString("txId"), deposit.getLong("insertTime"), deposit.getInt("transferType"),
                deposit.getInt("unlockConfirm"), deposit.getString("confirmTimes"));
    }

    /**
     * Method to get {@link #status} instance <br>
     * Any params required
     *
     * @return {@link #status} instance as int
     **/
    public DepositStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #amount} instance <br>
     * Any params required
     *
     * @return {@link #amount} instance as double
     **/
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

    /**
     * Method to get {@link #coin} instance <br>
     * Any params required
     *
     * @return {@link #coin} instance as {@link String}
     **/
    public String getCoin() {
        return coin;
    }

    /**
     * Method to get {@link #network} instance <br>
     * Any params required
     *
     * @return {@link #network} instance as {@link String}
     **/
    public String getNetwork() {
        return network;
    }

    /**
     * Method to set {@link #status}
     *
     * @param status: status value
     **/
    public void setStatus(DepositStatus status) {
        this.status = status;
    }

    /**
     * Method to get {@link #unlockConfirm} instance <br>
     * Any params required
     *
     * @return {@link #unlockConfirm} instance as int
     **/
    public int getUnlockConfirm() {
        return unlockConfirm;
    }

    /**
     * Method to get {@link #address} instance <br>
     * Any params required
     *
     * @return {@link #address} instance as {@link String}
     **/
    public String getAddress() {
        return address;
    }

    /**
     * Method to set {@link #address}
     *
     * @param address: address value
     **/
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method to get {@link #addressTag} instance <br>
     * Any params required
     *
     * @return {@link #addressTag} instance as {@link String}
     **/
    public String getAddressTag() {
        return addressTag;
    }

    /**
     * Method to set {@link #addressTag}
     *
     * @param addressTag: address tag value
     **/
    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }

    /**
     * Method to get {@link #txId} instance <br>
     * Any params required
     *
     * @return {@link #txId} instance as {@link String}
     **/
    public String getTxId() {
        return txId;
    }

    /**
     * Method to get {@link #insertTime} instance <br>
     * Any params required
     *
     * @return {@link #insertTime} instance as long
     **/
    public long getInsertTime() {
        return insertTime;
    }

    /**
     * Method to set {@link #insertTime}
     *
     * @param insertTime: insert time value
     * @throws IllegalArgumentException when insert time value is less than 0
     **/
    public void setInsertTime(long insertTime) {
        if (insertTime < 0)
            throw new IllegalArgumentException("Insert time value cannot be less than 0");
        this.insertTime = insertTime;
    }

    /**
     * Method to get {@link #insertTime} instance <br>
     * Any params required
     *
     * @return {@link #insertTime} instance as {@link Date}
     **/
    public Date getInsertDate() {
        return TimeFormatter.getDate(insertTime);
    }

    /**
     * Method to get {@link #transferType} instance <br>
     * Any params required
     *
     * @return {@link #transferType} instance as int
     **/
    public int getTransferType() {
        return transferType;
    }

    /**
     * Method to set {@link #transferType}
     *
     * @param transferType: transfer type value
     * @throws IllegalArgumentException when transfer type value is less than 0
     **/
    public void setTransferType(int transferType) {
        if (transferType < 0 || transferType > 1)
            throw new IllegalArgumentException("Status value can only be from 0 to 1");
        this.transferType = transferType;
    }

    /**
     * Method to set {@link #unlockConfirm}
     *
     * @param unlockConfirm: unlock confirm value
     **/
    public void setUnlockConfirm(int unlockConfirm) {
        this.unlockConfirm = unlockConfirm;
    }

    /**
     * {@code DepositStatus} list of available deposit statutes
     **/
    public enum DepositStatus {

        /**
         * {@code "pending"} status
         **/
        pending(0),

        /**
         * {@code "credited_but_cannot_withdraw"} status
         **/
        credited_but_cannot_withdraw(6),

        /**
         * {@code "success"} status
         **/
        success(1);

        /**
         * {@code "status"} value
         **/
        private final int status;

        /**
         * Constructor to init {@link DepositStatus}
         *
         * @param status: status value
         **/
        DepositStatus(int status) {
            this.status = status;
        }

        /**
         * Method to get the {@link DepositStatus} by an int <br>
         * Any params required
         *
         * @return {@link DepositStatus} corresponding value
         **/
        public static DepositStatus valueOf(int status) {
            switch (status) {
                case 0:
                    return pending;
                case 6:
                    return credited_but_cannot_withdraw;
                default:
                    return success;
            }
        }

        /**
         * Method to get {@link #status} instance <br>
         * Any params required
         *
         * @return {@link #status} instance as {@link String}
         **/
        @Override
        public String toString() {
            return String.valueOf(status);
        }

    }

    /**
     * Method to get {@link #confirmTimes} instance <br>
     * Any params required
     *
     * @return {@link #confirmTimes} instance as {@link String}
     **/
    public String getConfirmTimes() {
        return confirmTimes;
    }

    /**
     * Method to set {@link #confirmTimes}
     *
     * @param confirmTimes: confirm times value
     **/
    public void setConfirmTimes(String confirmTimes) {
        this.confirmTimes = confirmTimes;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
