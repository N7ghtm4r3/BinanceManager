package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.FutureAccountTransactionsHistory.FutureAccountTransaction;

/**
 * The {@code FutureAccountTransactionsHistory} class is useful to format a future account transactions history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-future-account-transaction-history-list-user_data">
 * Get Future Account Transaction History List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class FutureAccountTransactionsHistory extends BinanceRowsList<FutureAccountTransaction> {

    /**
     * {@code FutureTransactionType} list of available future transaction types
     **/
    public enum FutureTransactionType {

        /**
         * {@code SPOT_ACCOUNT_TO_USDT_FUTURES_ACCOUNT} transaction type
         **/
        SPOT_ACCOUNT_TO_USDT_FUTURES_ACCOUNT(1),

        /**
         * {@code USDT_FUTURES_ACCOUNT_TO_SPOT_ACCOUNT} transaction type
         **/
        USDT_FUTURES_ACCOUNT_TO_SPOT_ACCOUNT(2),

        /**
         * {@code SPOT_ACCOUNT_TO_COIN_FUTURES_ACCOUNT} transaction type
         **/
        SPOT_ACCOUNT_TO_COIN_FUTURES_ACCOUNT(3),

        /**
         * {@code COIN_FUTURES_ACCOUNT_TO_SPOT_ACCOUNT} transaction type
         **/
        COIN_FUTURES_ACCOUNT_TO_SPOT_ACCOUNT(4);

        /**
         * {@code VALUES} list of the types
         **/
        private static final List<FutureTransactionType> VALUES = Arrays.stream(FutureTransactionType.values()).toList();

        /**
         * {@code type} type value
         **/
        private final int type;

        /**
         * Constructor to init {@link FutureTransactionType} object
         *
         * @param type: type value
         **/
        FutureTransactionType(int type) {
            this.type = type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param value: value to reach
         * @return enum constant as {@link FutureTransactionType}
         **/
        public static FutureTransactionType reachEnumConstant(int value) {
            return VALUES.get(value);
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         **/
        public int getType() {
            return type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         **/
        @Override
        public String toString() {
            return type + "";
        }

    }

    /**
     * Constructor to init {@link FutureAccountTransactionsHistory} object
     *
     * @param total        : number of transactions
     * @param transactions :  list of the transactions
     **/
    public FutureAccountTransactionsHistory(int total, ArrayList<FutureAccountTransaction> transactions) {
        super(total, transactions);
    }

    /**
     * Constructor to init {@link FutureAccountTransactionsHistory}
     *
     * @param jTransactions : list details as {@link JSONObject}
     **/
    public FutureAccountTransactionsHistory(JSONObject jTransactions) {
        super(jTransactions);
        JSONArray jFTransactions = hItem.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jFTransactions.length(); j++)
            rows.add(new FutureAccountTransaction(jFTransactions.getJSONObject(j)));
    }

    /**
     * The {@code FutureAccountTransaction} class is useful to format a future account transaction
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class FutureAccountTransaction extends BinanceItem {

        /**
         * {@code asset} of the transaction
         **/
        private final String asset;

        /**
         * {@code tranId} id of the transaction
         **/
        private final long tranId;

        /**
         * {@code amount} of the transaction
         **/
        private final double amount;

        /**
         * {@code type} of the transaction
         **/
        private final FutureTransactionType type;

        /**
         * {@code timestamp} of the transaction
         **/
        private final long timestamp;

        /**
         * {@code status} of the transaction
         **/
        private final Status status;

        /**
         * Constructor to init {@link FutureAccountTransaction}
         *
         * @param asset     : asset of the transaction
         * @param tranId    : id of the transaction
         * @param amount    : amount of the transaction
         * @param type      : type of the transaction
         * @param timestamp : timestamp of the transaction
         * @param status    : status
         **/
        public FutureAccountTransaction(String asset, long tranId, double amount, FutureTransactionType type,
                                        long timestamp, Status status) {
            super(null);
            this.asset = asset;
            this.tranId = tranId;
            this.amount = amount;
            this.type = type;
            this.timestamp = timestamp;
            this.status = status;
        }

        /**
         * Constructor to init {@link FutureAccountTransaction}
         *
         * @param jFutureAccountTransaction : transaction details as {@link JSONObject}
         **/
        public FutureAccountTransaction(JSONObject jFutureAccountTransaction) {
            super(jFutureAccountTransaction);
            asset = hItem.getString("asset");
            tranId = hItem.getLong("tranId", 0);
            amount = hItem.getDouble("amount", 0);
            type = FutureTransactionType.reachEnumConstant(hItem.getInt("type"));
            timestamp = hItem.getLong("timestamp", 0);
            status = Status.valueOf(hItem.getString("status"));
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         **/
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #tranId} instance <br>
         * No-any params required
         *
         * @return {@link #tranId} instance as long
         **/
        public long getTranId() {
            return tranId;
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
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
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link FutureTransactionType}
         **/
        public FutureTransactionType getType() {
            return type;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as long
         **/
        public long getTimestamp() {
            return timestamp;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as {@link Date}
         **/
        public Date getDate() {
            return TimeFormatter.getDate(timestamp);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link Status}
         **/
        public Status getStatus() {
            return status;
        }

    }

}
