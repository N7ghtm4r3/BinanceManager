package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;
import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginIsolatedTransferHistory} class is useful to format Binance Isolated Transfer History request response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
 **/

public class MarginIsolatedTransferHistory {

    /**
     * {@code TRANSFER_SPOT} is constant for spot transfer
     * **/
    public static final String TRANSFER_SPOT = "SPOT";

    /**
     * {@code TRANSFER_ISOLATED_MARGIN} is constant for isolated margin transfer
     * **/
    public static final String TRANSFER_ISOLATED_MARGIN = "ISOLATED_MARGIN";

    /**
     * {@code total} is instance that memorizes total size about {@link #marginIsolatedTransfersList}
     * **/
    private int total;

    /**
     * {@code marginIsolatedTransfersList} is instance that memorizes list of {@link MarginIsolatedTransfer}
     **/
    private ArrayList<MarginIsolatedTransfer> marginIsolatedTransfersList;

    /**
     * Constructor to init {@link MarginIsolatedTransferHistory} object
     *
     * @param total:                       total size of transfers history
     * @param marginIsolatedTransfersList: list of {@link MarginIsolatedTransfer}
     **/
    public MarginIsolatedTransferHistory(int total, ArrayList<MarginIsolatedTransfer> marginIsolatedTransfersList) {
        this.total = total;
        this.marginIsolatedTransfersList = marginIsolatedTransfersList;
    }

    /**
     * Constructor to init {@link MarginIsolatedTransferHistory} object
     *
     * @param jsonTransfer: transfers details as {@link JSONObject}
     * @throws IllegalArgumentException when transfers details are not recoverable
     **/
    public MarginIsolatedTransferHistory(JSONObject jsonTransfer) {
        JSONArray jsonTransfers = getJSONArray(jsonTransfer, "rows");
        if (jsonTransfers != null) {
            total = jsonTransfers.length();
            loadIsolatedTransfersList(jsonTransfers);
        } else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /** Method to assemble a MarginIsolatedTransfer list
     * @param jsonTransfers: obtained from Binance's request
     * any return
     * **/
    private void loadIsolatedTransfersList(JSONArray jsonTransfers) {
        marginIsolatedTransfersList = new ArrayList<>();
        for (int j = 0; j < jsonTransfers.length(); j++)
            marginIsolatedTransfersList.add(new MarginIsolatedTransfer(jsonTransfers.getJSONObject(j)));
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginIsolatedTransfer> getMarginIsolatedTransfersList() {
        return marginIsolatedTransfersList;
    }

    public void setMarginIsolatedTransfersList(ArrayList<MarginIsolatedTransfer> marginIsolatedTransfersList) {
        this.marginIsolatedTransfersList = marginIsolatedTransfersList;
        total = marginIsolatedTransfersList.size();
    }

    public void insertMarginIsolatedTransfer(MarginIsolatedTransfer marginIsolatedTransfer){
        if(!marginIsolatedTransfersList.contains(marginIsolatedTransfer)){
            marginIsolatedTransfersList.add(marginIsolatedTransfer);
            total += 1;
        }
    }

    public boolean removeMarginIsolatedTransfer(MarginIsolatedTransfer marginIsolatedTransfer){
        boolean removed = marginIsolatedTransfersList.remove(marginIsolatedTransfer);
        if(removed)
            total -= 1;
        return removed;
    }

    public MarginIsolatedTransfer getMarginIsolatedTransfer(int index) {
        return marginIsolatedTransfersList.get(index);
    }

    @Override
    public String toString() {
        return "MarginIsolatedTransferHistory{" +
                "total=" + total +
                ", marginIsolatedTransfersList=" + marginIsolatedTransfersList +
                '}';
    }

    /**
     * The {@code MarginIsolatedTransfer} class is useful to obtain and format IsolatedData object
     * **/

    public static class MarginIsolatedTransfer extends MarginAssetList {

        /**
         * {@code amount} is instance that memorizes amount to transfer
         * **/
        private final double amount;

        /**
         * {@code transFrom} is instance that memorizes address of sender
         * **/
        private final String transFrom;

        /**
         * {@code transTo} is instance that memorizes address of receiver
         * **/
        private final String transTo;

        /** Constructor to init {@link MarginIsolatedTransfer} object
         * @param asset: asset
         * @param txId: identifier of transaction
         * @param timestamp: timestamp of transaction
         * @param status: status of transaction
         * @param amount: amount to transfer
         * @param transFrom: address of sender
         * @param transTo: address of receiver
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public MarginIsolatedTransfer(String asset, long txId, long timestamp, String status, double amount,
                                      String transFrom, String transTo) {
            super(asset, txId, timestamp, status);
            this.amount = amount;
            this.transFrom = transFrom;
            this.transTo = transTo;
        }

        /**
         * Constructor to init {@link MarginIsolatedTransfer} object
         *
         * @param isolatedTransfer: isolated transfer details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public MarginIsolatedTransfer(JSONObject isolatedTransfer) {
            super(isolatedTransfer);
            amount = isolatedTransfer.getDouble("amount");
            transFrom = isolatedTransfer.getString("transFrom");
            transTo = isolatedTransfer.getString("transTo");
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

        public String getTransFrom() {
            return transFrom;
        }

        public String getTransTo() {
            return transTo;
        }

        @Override
        public String toString() {
            return "MarginIsolatedTransfer{" +
                    "amount=" + amount +
                    ", transFrom='" + transFrom + '\'' +
                    ", transTo='" + transTo + '\'' +
                    ", asset='" + asset + '\'' +
                    ", txId=" + txId +
                    ", timestamp=" + timestamp +
                    ", status='" + status + '\'' +
                    '}';
        }

    }

}
