package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists.TransfersHistoryList.Transfer;

/**
 * The {@code TransfersHistoryList} class is useful to format a {@code "Binance"}'s margin transfer history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
 * Get Cross Margin Transfer History (USER_DATA)</a>
 * @see MarginList
 **/
public class TransfersHistoryList extends MarginList<Transfer> {

    /**
     * Constructor to init {@link TransfersHistoryList} object
     *
     * @param total:                    total size of transfers
     * @param marginTransferAssetsList: list of {@link Transfer}
     **/
    public TransfersHistoryList(int total, ArrayList<Transfer> marginTransferAssetsList) {
        super(total, marginTransferAssetsList);
    }

    /**
     * Constructor to init {@link TransfersHistoryList} object
     *
     * @param jTransfersHistoryList: transfer history details as {@link JSONObject}
     **/
    public TransfersHistoryList(JSONObject jTransfersHistoryList) {
        super(jTransfersHistoryList);
        JSONArray jTransfers = hMarginList.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jTransfers.length(); j++)
            rows.add(new Transfer(jTransfers.getJSONObject(j)));
    }

    /**
     * {@code TransferType} list of available types for a transfer
     **/
    public enum TransferType {

        /**
         * {@code "ROLL_IN"} transfer type
         **/
        ROLL_IN,

        /**
         * {@code "ROLL_OUT"} transfer type
         **/
        ROLL_OUT

    }

    /**
     * The {@code Transfer} class is useful to format a transfer
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see MarginListItem
     **/
    public static class Transfer extends MarginListItem {

        /**
         * {@code amount} is instance that memorizes amount to transfer
         **/
        private double amount;

        /**
         * {@code type} is instance that memorizes type value
         **/
        private TransferType type;

        /**
         * Constructor to init {@link Transfer} object
         *
         * @param asset:     asset
         * @param txId:      identifier of transaction
         * @param amount:    amount to transfer
         * @param timestamp: timestamp of transaction
         * @param status:    status of transaction
         * @param type:      type value
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Transfer(String asset, long txId, double amount, Status status, long timestamp, TransferType type) {
            super(asset, txId, timestamp, status);
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            else
                this.amount = amount;
            this.type = type;
        }

        /**
         * Constructor to init {@link Transfer} object
         *
         * @param transferAsset: transfer asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Transfer(JSONObject transferAsset) {
            super(transferAsset);
            amount = transferAsset.getDouble("amount");
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            type = TransferType.valueOf(transferAsset.getString("type"));
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
         * Method to set {@link #amount}
         *
         * @param amount: amount to transfer
         * @throws IllegalArgumentException when amount to transfer value is less than 0
         **/
        public void setAmount(double amount) {
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
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
         * Any params required
         *
         * @return {@link #type} instance as {@link TransferType}
         **/
        public TransferType getType() {
            return type;
        }

        /**
         * Method to set {@link #type}
         *
         * @param type: transfer type to set
         **/
        public void setType(TransferType type) {
            this.type = type;
        }

    }

}
