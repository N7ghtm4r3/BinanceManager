package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit.DepositStatus.reachEnumConstant;

/**
 * The {@code Deposit} class is useful to format a {@code "Binance"}'s deposit
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data">
 * Deposit History (supporting network) (USER_DATA)</a>
 * @see BinanceItem
 */
public class Deposit extends BinanceItem {

    /**
     * {@code status} is instance that memorizes status value
     */
    protected final DepositStatus status;

    /**
     * {@code amount} is instance that memorizes amount value
     */
    protected final double amount;

    /**
     * {@code coin} is instance that memorizes coin value
     */
    protected final String coin;

    /**
     * {@code network} is instance that memorizes network value
     */
    protected final String network;

    /**
     * {@code unlockConfirm} is instance that memorizes unlock confirm value
     */
    protected final int unlockConfirm;

    /**
     * {@code address} is instance that memorizes address value
     */
    protected final String address;

    /**
     * {@code addressTag} is instance that memorizes address tag value
     */
    protected final String addressTag;

    /**
     * {@code txId} is instance that memorizes tx identifier value
     */
    protected final String txId;

    /**
     * {@code insertTime} is instance that memorizes insert time value
     */
    protected final long insertTime;

    /**
     * {@code transferType} is instance that memorizes transfer type value
     */
    protected final int transferType;

    /**
     * {@code confirmTimes} is instance that memorizes confirm times value
     */
    protected final String confirmTimes;

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
     */
    public Deposit(double amount, String coin, String network, DepositStatus status, String address, String addressTag,
                   String txId, long insertTime, int transferType, int unlockConfirm, String confirmTimes) {
        super(null);
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

    /**
     * Constructor to init {@link Deposit} object
     *
     * @param jDeposit: deposit details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     */
    public Deposit(JSONObject jDeposit) {
        super(jDeposit);
        amount = hItem.getDouble("amount", 0);
        coin = hItem.getString("coin");
        network = hItem.getString("network");
        status = reachEnumConstant(hItem.getInt("status"));
        address = hItem.getString("address");
        addressTag = hItem.getString("addressTag");
        txId = hItem.getString("txId");
        insertTime = hItem.getLong("insertTime", 0);
        transferType = hItem.getInt("transferType", 0);
        unlockConfirm = hItem.getInt("unlockConfirm", 0);
        confirmTimes = hItem.getString("confirmTimes");
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as int
     */
    public DepositStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    /**
     * Method to get {@link #coin} instance <br>
     * No-any params required
     *
     * @return {@link #coin} instance as {@link String}
     */
    public String getCoin() {
        return coin;
    }

    /**
     * Method to get {@link #network} instance <br>
     * No-any params required
     *
     * @return {@link #network} instance as {@link String}
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Method to get {@link #unlockConfirm} instance <br>
     * No-any params required
     *
     * @return {@link #unlockConfirm} instance as int
     */
    public int getUnlockConfirm() {
        return unlockConfirm;
    }

    /**
     * Method to get {@link #address} instance <br>
     * No-any params required
     *
     * @return {@link #address} instance as {@link String}
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method to get {@link #addressTag} instance <br>
     * No-any params required
     *
     * @return {@link #addressTag} instance as {@link String}
     */
    public String getAddressTag() {
        return addressTag;
    }

    /**
     * Method to get {@link #txId} instance <br>
     * No-any params required
     *
     * @return {@link #txId} instance as {@link String}
     */
    public String getTxId() {
        return txId;
    }

    /**
     * Method to get {@link #insertTime} instance <br>
     * No-any params required
     *
     * @return {@link #insertTime} instance as long
     */
    public long getInsertTime() {
        return insertTime;
    }

    /**
     * Method to get {@link #insertTime} instance <br>
     * No-any params required
     *
     * @return {@link #insertTime} instance as {@link Date}
     */
    public Date getInsertDate() {
        return TimeFormatter.getDate(insertTime);
    }

    /**
     * Method to get {@link #transferType} instance <br>
     * No-any params required
     *
     * @return {@link #transferType} instance as int
     */
    public int getTransferType() {
        return transferType;
    }

    /**
     * Method to get {@link #confirmTimes} instance <br>
     * No-any params required
     *
     * @return {@link #confirmTimes} instance as {@link String}
     */
    public String getConfirmTimes() {
        return confirmTimes;
    }

    /**
     * {@code DepositStatus} list of available deposit statutes
     */
    public enum DepositStatus {

        /**
         * {@code "pending"} status
         */
        pending(0),

        /**
         * {@code "credited_but_cannot_withdraw"} status
         */
        credited_but_cannot_withdraw(6),

        /**
         * {@code "wrong_deposit"} status
         */
        wrong_deposit(7),

        /**
         * {@code "waiting_for_user_confirm"} status
         */
        waiting_for_user_confirm(9),

        /**
         * {@code "success"} status
         */
        success(1);

        /**
         * {@code "status"} value
         */
        private final int status;

        /**
         * {@code VALUES} list of the statuses
         */
        private static final List<DepositStatus> VALUES = Arrays.stream(DepositStatus.values()).toList();

        /**
         * Constructor to init {@link DepositStatus}
         *
         * @param status: status value
         */
        DepositStatus(int status) {
            this.status = status;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constant as {@link DepositStatus}
         */
        public static DepositStatus reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         */
        @Override
        public String toString() {
            return String.valueOf(status);
        }

    }

}
