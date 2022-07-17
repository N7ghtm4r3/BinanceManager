package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code MarginIsolatedTransferHistory} class is useful to format Binance Isolated Transfer History request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

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
     * **/
    private ArrayList<MarginIsolatedTransfer> marginIsolatedTransfersList;

    /** Constructor to init {@link MarginIsolatedTransferHistory} object
     * @param jsonTransfer: transfers details in JSON format
     * @throws IllegalArgumentException when transfers details are not recoverable
     * **/
    public MarginIsolatedTransferHistory(JSONObject jsonTransfer) {
        JSONArray jsonTransfers = new JsonHelper(jsonTransfer).getJSONArray("rows");
        if(jsonTransfer != null){
            total = jsonTransfers.length();
            loadIsolatedTransfersList(jsonTransfers);
        }else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /** Method to assemble a MarginIsolatedTransfer list
     * @param jsonTransfers: obtained from Binance's request
     * any return
     * **/
    private void loadIsolatedTransfersList(JSONArray jsonTransfers) {
        marginIsolatedTransfersList = new ArrayList<>();
        for (int j = 0; j < jsonTransfers.length(); j++){
            JSONObject isolatedTransfer = jsonTransfers.getJSONObject(j);
            marginIsolatedTransfersList.add(new MarginIsolatedTransfer(isolatedTransfer.getString("asset"),
                    isolatedTransfer.getLong("txId"),
                    isolatedTransfer.getLong("timestamp"),
                    isolatedTransfer.getString("status"),
                    isolatedTransfer.getDouble("amount"),
                    isolatedTransfer.getString("transFrom"),
                    isolatedTransfer.getString("transTO")
            ));
        }
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

    /**
     * The {@code MarginIsolatedTransfer} class is useful to obtain and format IsolatedData object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
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

        public double getAmount() {
            return amount;
        }

        public String getTransFrom() {
            return transFrom;
        }

        public String getTransTo() {
            return transTo;
        }

    }

}
