package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.Withdraw.WithdrawStatus.Processing;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.Withdraw.WithdrawStatus.valueOf;

/**
 * The {@code Withdraw} class is useful to format a {@code "Binance"}'s withdraw
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data">
 * Withdraw History (supporting network) (USER_DATA)</a>
 **/
public class Withdraw {

    /**
     * {@code status} is instance that memorizes transfer status value
     **/
    private final WithdrawStatus status;

    /**
     * {@code address} is instance that memorizes address value
     **/
    private final String address;

    /**
     * {@code amount} is instance that memorizes amount value
     **/
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
                    String network, int transferType, WithdrawStatus status, double transactionFee, int confirmNo,
                    String info, String txId) {
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
     * {@code transactionFee} is instance that memorizes transaction fee value
     **/
    private final double transactionFee;

    /**
     * {@code confirmNo} is instance that memorizes confirms number value
     **/
    private final int confirmNo;

    /**
     * {@code info} is instance that memorizes info value
     **/
    private final String info;

    /**
     * {@code txId} is instance that memorizes transaction identifier value
     **/
    private final String txId;

    /**
     * Constructor to init {@link Withdraw} object
     *
     * @param address: address value
     * @param amount:  amount value
     * @param coin:    coin value
     * @apiNote this constructor is useful to create a  {@link Withdraw} only with the mandatory params
     **/
    public Withdraw(String address, double amount, String coin) {
        this(address, amount, null, coin, null, null, null, -1, Processing, 0, 0, null, null);
    }

    /**
     * Constructor to init {@link Withdraw} object
     *
     * @param withdraw: withdraw details as {@link JSONObject}
     **/
    public Withdraw(JSONObject withdraw) {
        JsonHelper hWithdraw = new JsonHelper(withdraw);
        address = hWithdraw.getString("address");
        amount = hWithdraw.getDouble("amount", 0);
        applyTime = hWithdraw.getString("applyTime");
        coin = hWithdraw.getString("coin");
        id = hWithdraw.getString("id");
        withdrawOrderId = hWithdraw.getString("withdrawOrderId");
        network = hWithdraw.getString("network");
        transferType = hWithdraw.getInt("transferType", 0);
        status = valueOf(hWithdraw.getInt("status", 0));
        transactionFee = hWithdraw.getDouble("transactionFee", 0);
        confirmNo = hWithdraw.getInt("confirmNo", 0);
        info = hWithdraw.getString("info");
        txId = hWithdraw.getString("txId");
    }

    /**
     * Method to get {@link #status} instance <br>
     * Any params required
     *
     * @return {@link #status} instance as int
     **/
    public WithdrawStatus getStatus() {
        return status;
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
     * Method to get {@link #applyTime} instance <br>
     * Any params required
     *
     * @return {@link #applyTime} instance as {@link String}
     **/
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * Method to get {@link #applyTime} instance <br>
     * Any params required
     *
     * @return {@link #applyTime} instance as long
     **/
    public long getApplyTimestamp() {
        TimeFormatter.changeDefaultPattern("yyyy-mm-dd HH:mm:ss");
        return TimeFormatter.getDateTimestamp(applyTime);
    }

    /**
     * Method to get {@link #applyTime} instance <br>
     * Any params required
     *
     * @return {@link #applyTime} instance as {@link Date}
     **/
    public Date getApplyDate() {
        return TimeFormatter.getDate(getApplyTimestamp());
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
     * Method to get {@link #id} instance <br>
     * Any params required
     *
     * @return {@link #id} instance as {@link String}
     **/
    public String getId() {
        return id;
    }

    /**
     * Method to get {@link #withdrawOrderId} instance <br>
     * Any params required
     *
     * @return {@link #withdrawOrderId} instance as {@link String}
     **/
    public String getWithdrawOrderId() {
        return withdrawOrderId;
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
     * Method to get {@link #transferType} instance <br>
     * Any params required
     *
     * @return {@link #transferType} instance as int
     **/
    public int getTransferType() {
        return transferType;
    }

    /**
     * {@code WithdrawStatus} list of available withdraw statutes
     **/
    public enum WithdrawStatus {

        /**
         * {@code "Email_Sent"} status
         * **/
        Email_Sent(0),

        /**
         * {@code "Cancelled"} status
         * **/
        Cancelled(1),

        /**
         * {@code "Awaiting_Approval"} status
         * **/
        Awaiting_Approval(2),

        /**
         * {@code "Rejected"} status
         * **/
        Rejected(3),

        /**
         * {@code "Processing"} status
         * **/
        Processing(4),

        /**
         * {@code "Failure"} status
         * **/
        Failure(5),

        /**
         * {@code "Completed"} status
         * **/
        Completed(6);

        /**
         * {@code "status"} value
         * **/
        private final int status;

        /** Constructor to init {@link Deposit.DepositStatus}
         * @param status: status value
         * **/
        WithdrawStatus(int status) {
            this.status = status;
        }

        /**
         * Method to get the {@link WithdrawStatus} by an int <br>
         * Any params required
         *
         * @return {@link WithdrawStatus} corresponding value
         **/
        public static WithdrawStatus valueOf(int status) {
            switch (status) {
                case 0:
                    return Email_Sent;
                case 1:
                    return Cancelled;
                case 2:
                    return Awaiting_Approval;
                case 3:
                    return Rejected;
                case 4:
                    return Processing;
                case 5:
                    return Failure;
                default:
                    return Completed;
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
     * Method to get {@link #transactionFee} instance <br>
     * Any params required
     *
     * @return {@link #transactionFee} instance as double
     **/
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

    /**
     * Method to get {@link #confirmNo} instance <br>
     * Any params required
     *
     * @return {@link #confirmNo} instance as int
     **/
    public int getConfirmNo() {
        return confirmNo;
    }

    /**
     * Method to get {@link #info} instance <br>
     * Any params required
     *
     * @return {@link #info} instance as {@link String}
     **/
    public String getInfo() {
        return info;
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
