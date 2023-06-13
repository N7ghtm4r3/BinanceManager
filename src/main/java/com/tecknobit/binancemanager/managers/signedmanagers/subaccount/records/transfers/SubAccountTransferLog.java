package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubAccountTransferLog} class is useful to format a sub transfer log
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
 *             Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
 *             Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
public class SubAccountTransferLog extends BinanceItem {

    /**
     * {@code TransferDirection} list of available transfer directions
     */
    public enum TransferDirection {

        /**
         * {@code FROM} transfer direction
         */
        FROM,

        /**
         * {@code TO} transfer direction
         */
        TO

    }

    /**
     * {@code count} of the transfer log
     */
    private final int count;

    /**
     * {@code transferLogs} list of transfer logs
     */
    private final ArrayList<TransferLog> transferLogs;

    /**
     * Constructor to init {@link SubAccountTransferLog} object
     *
     * @param count        : count of the transfer log
     * @param transferLogs : list of transfer logs
     */
    public SubAccountTransferLog(int count, ArrayList<TransferLog> transferLogs) {
        super(null);
        this.count = count;
        this.transferLogs = transferLogs;
    }

    /**
     * Constructor to init {@link SubAccountTransferLog} object
     *
     * @param jSubAccountTransferLog: subaccount transfer log details as {@link JSONObject}
     */
    public SubAccountTransferLog(JSONObject jSubAccountTransferLog) {
        super(jSubAccountTransferLog);
        count = hItem.getInt("count");
        transferLogs = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("managerSubTransferHistoryVos");
        if (jList != null)
            for (JSONObject item : jList)
                transferLogs.add(new TransferLog(item));
    }

    /**
     * Method to get {@link #count} instance <br>
     * No-any params required
     *
     * @return {@link #count} instance as int
     */
    public int getCount() {
        return count;
    }

    /**
     * Method to get {@link #transferLogs} instance <br>
     * No-any params required
     *
     * @return {@link #transferLogs} instance as {@link ArrayList} of {@link TransferLog}
     */
    public ArrayList<TransferLog> getTransferLogs() {
        return transferLogs;
    }

    /**
     * The {@code TransferLog} class is useful to format a transfer log
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SubTransferStructure
     */
    public static class TransferLog extends SubTransferStructure {

        /**
         * {@code fromEmail} from email of the transfer log
         */
        private final String fromEmail;

        /**
         * {@code toEmail} to email of the transfer log
         */
        private final String toEmail;

        /**
         * {@code amount} of the transfer log
         */
        private final double amount;

        /**
         * {@code scheduledData} scheduled data of the transfer log
         */
        private final long scheduledData;

        /**
         * {@code createTime} create time of the transfer log
         */
        private final long createTime;

        /**
         * Constructor to init {@link TransferLog} object
         *
         * @param tranId          : transaction id of the transfer log
         * @param asset           : asset of the transfer log
         * @param fromAccountType : from account type of the transfer log
         * @param toAccountType   : to account type of the transfer log
         * @param status          : status of the transfer log
         * @param fromEmail       : from email of the transfer log
         * @param toEmail         : to email of the transfer log
         * @param amount          : amount of the transfer log
         * @param scheduledData   : scheduled data of the transfer log
         * @param createTime      : create time of the transfer log
         */
        public TransferLog(long tranId, String asset, PrincipalAccountType fromAccountType,
                           PrincipalAccountType toAccountType, TransferStatus status, String fromEmail,
                           String toEmail, double amount, long scheduledData, long createTime) {
            super(tranId, asset, fromAccountType, toAccountType, status);
            this.fromEmail = fromEmail;
            this.toEmail = toEmail;
            this.amount = amount;
            this.scheduledData = scheduledData;
            this.createTime = createTime;
        }

        /**
         * Constructor to init {@link TransferLog} object
         *
         * @param jTransferLog: transfer log details as {@link JSONObject}
         */
        public TransferLog(JSONObject jTransferLog) {
            super(jTransferLog);
            fromEmail = hItem.getString("fromEmail");
            toEmail = hItem.getString("toEmail");
            amount = hItem.getDouble("amount", 0);
            scheduledData = hItem.getLong("scheduledData", 0);
            createTime = hItem.getLong("createTime", 0);
        }

        /**
         * Method to get {@link #fromEmail} instance <br>
         * No-any params required
         *
         * @return {@link #fromEmail} instance as {@link String}
         */
        public String getFromEmail() {
            return fromEmail;
        }

        /**
         * Method to get {@link #toEmail} instance <br>
         * No-any params required
         *
         * @return {@link #toEmail} instance as {@link String}
         */
        public String getToEmail() {
            return toEmail;
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
         * Method to get {@link #scheduledData} instance <br>
         * No-any params required
         *
         * @return {@link #scheduledData} instance as long
         */
        public long getScheduledData() {
            return scheduledData;
        }

        /**
         * Method to get {@link #scheduledData} instance <br>
         * No-any params required
         *
         * @return {@link #scheduledData} instance as {@link Date}
         */
        public Date getScheduledDate() {
            return TimeFormatter.getDate(scheduledData);
        }

        /**
         * Method to get {@link #createTime} instance <br>
         * No-any params required
         *
         * @return {@link #createTime} instance as long
         */
        public long getCreateTime() {
            return createTime;
        }

        /**
         * Method to get {@link #createTime} instance <br>
         * No-any params required
         *
         * @return {@link #createTime} instance as {@link Date}
         */
        public Date getCreateDate() {
            return TimeFormatter.getDate(createTime);
        }

    }

}
