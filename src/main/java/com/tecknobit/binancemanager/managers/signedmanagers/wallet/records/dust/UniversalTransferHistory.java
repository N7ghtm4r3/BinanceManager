package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.UniversalTransferHistory.UniversalTransfer;

/**
 * The {@code UniversalTransferList} class is useful to format a {@code "Binance"}'s universal transfer
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-user-universal-transfer-history-user_data">
 * Query User Universal Transfer History (USER_DATA) </a>
 * @see BinanceRowsList
 **/
public class UniversalTransferHistory extends BinanceRowsList<UniversalTransfer> {

    /**
     * Constructor to init {@link UniversalTransferHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     **/
    public UniversalTransferHistory(int total, ArrayList<UniversalTransfer> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link UniversalTransferHistory}
     *
     * @param jTransfersList : list details as {@link JSONObject}
     **/
    public UniversalTransferHistory(JSONObject jTransfersList) {
        super(jTransfersList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new UniversalTransfer((JSONObject) row));
    }

    /**
     * {@code TransferType} list of available transfer types
     **/
    public enum TransferType {

        /**
         * {@code "MAIN_UMFUTURE"} transfer type
         **/
        MAIN_UMFUTURE,

        /**
         * {@code "MAIN_CMFUTURE"} transfer type
         **/
        MAIN_CMFUTURE,

        /**
         * {@code "MAIN_MARGIN"} transfer type
         **/
        MAIN_MARGIN,

        /**
         * {@code "UMFUTURE_MAIN"} transfer type
         **/
        UMFUTURE_MAIN,

        /**
         * {@code "UMFUTURE_MARGIN"} transfer type
         **/
        UMFUTURE_MARGIN,

        /**
         * {@code "CMFUTURE_MAIN"} transfer type
         **/
        CMFUTURE_MAIN,

        /**
         * {@code "CMFUTURE_MARGIN"} transfer type
         **/
        CMFUTURE_MARGIN,

        /**
         * {@code "MARGIN_MAIN"} transfer type
         **/
        MARGIN_MAIN,

        /**
         * {@code "MARGIN_UMFUTURE"} transfer type
         **/
        MARGIN_UMFUTURE,

        /**
         * {@code "MARGIN_CMFUTURE"} transfer type
         **/
        MARGIN_CMFUTURE,

        /**
         * {@code "ISOLATEDMARGIN_MARGIN"} transfer type
         **/
        ISOLATEDMARGIN_MARGIN,

        /**
         * {@code "MARGIN_ISOLATEDMARGIN"} transfer type
         **/
        MARGIN_ISOLATEDMARGIN,

        /**
         * {@code "ISOLATEDMARGIN_ISOLATEDMARGIN"} transfer type
         **/
        ISOLATEDMARGIN_ISOLATEDMARGIN,

        /**
         * {@code "MAIN_FUNDING"} transfer type
         **/
        MAIN_FUNDING,

        /**
         * {@code "FUNDING_MAIN"} transfer type
         **/
        FUNDING_MAIN,

        /**
         * {@code "FUNDING_UMFUTURE"} transfer type
         **/
        FUNDING_UMFUTURE,

        /**
         * {@code "UMFUTURE_FUNDING"} transfer type
         **/
        UMFUTURE_FUNDING,

        /**
         * {@code "MARGIN_FUNDING"} transfer type
         **/
        MARGIN_FUNDING,

        /**
         * {@code "FUNDING_MARGIN"} transfer type
         **/
        FUNDING_MARGIN,

        /**
         * {@code "FUNDING_CMFUTURE"} transfer type
         **/
        FUNDING_CMFUTURE,

        /**
         * {@code "CMFUTURE_FUNDING"} transfer type
         **/
        CMFUTURE_FUNDING

    }

    public static class UniversalTransfer {

        /**
         * {@code type} is instance that memorizes type value
         **/
        private final TransferType type;

        /**
         * {@code asset} is instance that memorizes asset value
         **/
        private final String asset;

        /**
         * {@code amount} is instance that memorizes amount value
         **/
        private final double amount;
        /**
         * {@code status} is instance that memorizes status value
         **/
        private final Order.Status status;
        /**
         * {@code tranId} is instance that memorizes transaction identifier value
         **/
        private final long tranId;
        /**
         * {@code timestamp} is instance that memorizes timestamp value
         **/
        private final long timestamp;

        /**
         * Constructor to init {@link UniversalTransfer} object
         *
         * @param asset:     asset value
         * @param amount:    amount value
         * @param type:      type value
         * @param status:    status value
         * @param tranId:    transaction identifier value
         * @param timestamp: timestamp value
         **/
        public UniversalTransfer(String asset, double amount, TransferType type, Order.Status status, long tranId, long timestamp) {
            this.asset = asset;
            this.amount = amount;
            this.type = type;
            this.status = status;
            this.tranId = tranId;
            this.timestamp = timestamp;
        }

        /**
         * Constructor to init {@link UniversalTransfer} object
         *
         * @param jTransfer: universal transfer details as {@link JSONObject}
         **/
        public UniversalTransfer(JSONObject jTransfer) {
            this(jTransfer.getString("asset"), jTransfer.getDouble("amount"),
                    TransferType.valueOf(jTransfer.getString("type")),
                    Order.Status.valueOf(jTransfer.getString("status")), jTransfer.getLong("tranId"),
                    jTransfer.getLong("timestamp"));
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
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         **/
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link TransferType}
         **/
        public TransferType getType() {
            return type;
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
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link Order.Status}
         **/
        public Order.Status getStatus() {
            return status;
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
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
