package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code IsolatedTransferHistoryList} class is useful to format a {@code "Binance"}'s isolated transfer history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
 * Get Isolated Margin Transfer History (USER_DATA)</a>
 * @see MarginList
 **/
public class IsolatedTransferHistoryList extends MarginList<IsolatedTransferHistoryList.IsolatedTransfer> {

    /**
     * Constructor to init {@link IsolatedTransferHistoryList} object
     *
     * @param total:                 total size of transfers history
     * @param isolatedTransfersList: list of {@link IsolatedTransfer}
     **/
    public IsolatedTransferHistoryList(int total, ArrayList<IsolatedTransfer> isolatedTransfersList) {
        super(total, isolatedTransfersList);
    }

    /**
     * Constructor to init {@link IsolatedTransferHistoryList} object
     *
     * @param jsonTransfer: transfers details as {@link JSONObject}
     **/
    public IsolatedTransferHistoryList(JSONObject jsonTransfer) {
        super(jsonTransfer);
        JSONArray jTransfers = hMarginList.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jTransfers.length(); j++)
            rows.add(new IsolatedTransfer(jTransfers.getJSONObject(j)));
    }

    /**
     * {@code TransferType} list of available transfer types
     **/
    public enum TransferType {

        /**
         * {@code "SPOT"} transfer type
         **/
        SPOT,

        /**
         * {@code "ISOLATED_MARGIN"} transfer type
         **/
        ISOLATED_MARGIN

    }

    /**
     * The {@code IsolatedTransfer} class is useful to obtain and format an isolated transfer type
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see MarginListItem
     **/
    public static class IsolatedTransfer extends MarginListItem {

        /**
         * {@code amount} is instance that memorizes amount to transfer
         **/
        private final double amount;

        /**
         * {@code transFrom} is instance that memorizes address of sender
         **/
        private final String transFrom;

        /**
         * {@code transTo} is instance that memorizes address of receiver
         **/
        private final String transTo;

        /**
         * Constructor to init {@link IsolatedTransfer} object
         *
         * @param asset:     asset
         * @param txId:      identifier of transaction
         * @param timestamp: timestamp of transaction
         * @param status:    status of transaction
         * @param amount:    amount to transfer
         * @param transFrom: address of sender
         * @param transTo:   address of receiver
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public IsolatedTransfer(String asset, long txId, long timestamp, Status status, double amount,
                                String transFrom, String transTo) {
            super(asset, txId, timestamp, status);
            this.amount = amount;
            this.transFrom = transFrom;
            this.transTo = transTo;
        }

        /**
         * Constructor to init {@link IsolatedTransfer} object
         *
         * @param isolatedTransfer: isolated transfer details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public IsolatedTransfer(JSONObject isolatedTransfer) {
            super(isolatedTransfer);
            amount = isolatedTransfer.getDouble("amount");
            transFrom = isolatedTransfer.getString("transFrom");
            transTo = isolatedTransfer.getString("transTo");
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
         * Method to get {@link #transFrom} instance <br>
         * Any params required
         *
         * @return {@link #transFrom} instance as {@link String}
         **/
        public String getTransFrom() {
            return transFrom;
        }

        /**
         * Method to get {@link #transTo} instance <br>
         * Any params required
         *
         * @return {@link #transTo} instance as {@link String}
         **/
        public String getTransTo() {
            return transTo;
        }

    }

}
