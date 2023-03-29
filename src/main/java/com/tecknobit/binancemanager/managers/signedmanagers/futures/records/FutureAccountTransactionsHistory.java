package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futures.records.FutureAccountTransactionsHistory.FutureAccountTransaction;

public class FutureAccountTransactionsHistory extends BinanceRowsList<FutureAccountTransaction> {

    public enum FutureTransactionType {

        SPOT_ACCOUNT_TO_USDT_FUTURES_ACCOUNT(1),
        USDT_FUTURES_ACCOUNT_TO_SPOT_ACCOUNT(2),
        SPOT_ACCOUNT_TO_COIN_FUTURES_ACCOUNT(3),
        COIN_FUTURES_ACCOUNT_TO_SPOT_ACCOUNT(4);

        private final int type;

        FutureTransactionType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total        : number of items
     * @param transactions :  list of the items
     **/
    public FutureAccountTransactionsHistory(int total, ArrayList<FutureAccountTransaction> transactions) {
        super(total, transactions);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jTransactions : list details as {@link JSONObject}
     **/
    public FutureAccountTransactionsHistory(JSONObject jTransactions) {
        super(jTransactions);
        JSONArray jFTransactions = hItem.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jFTransactions.length(); j++)
            rows.add(new FutureAccountTransaction(jFTransactions.getJSONObject(j)));
    }

    public static class FutureAccountTransaction extends BinanceItem {

        private final String asset;
        private final long tranId;
        private final double amount;
        private final FutureTransactionType type;
        private final long timestamp;
        private final Status status;

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

        public FutureAccountTransaction(JSONObject jFutureAccountTransaction) {
            super(jFutureAccountTransaction);
            asset = hItem.getString("asset");
            tranId = hItem.getLong("tranId", 0);
            amount = hItem.getDouble("amount", 0);
            type = FutureTransactionType.valueOf(hItem.getString("type"));
            timestamp = hItem.getLong("timestamp", 0);
            status = Status.valueOf(hItem.getString("status"));
        }

        public String getAsset() {
            return asset;
        }

        public long getTranId() {
            return tranId;
        }

        public double getAmount() {
            return amount;
        }

        public FutureTransactionType getType() {
            return type;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public Status getStatus() {
            return status;
        }

    }

}
