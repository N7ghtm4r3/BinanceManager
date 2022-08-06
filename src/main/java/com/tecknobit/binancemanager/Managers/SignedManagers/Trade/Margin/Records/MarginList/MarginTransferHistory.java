package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginTransferHistory} class is useful to format Binance Margin Get Cross Transfer History request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginTransferHistory {

    /**
     * {@code TYPE_ROLL_IN} is constant for roll in type
     * **/
    public static final String TYPE_ROLL_IN = "ROLL_IN";

    /**
     * {@code TYPE_ROLL_OUT} is constant for roll out type
     * **/
    public static final String TYPE_ROLL_OUT = "ROLL_OUT";

    /**
     * {@code total} is instance that memorizes total size about {@link #marginTransferAssetsList}
     * **/
    private int total;

    /**
     * {@code marginTransferAssetsList} is instance that memorizes list of {@link MarginTransferAsset}
     * **/
    private ArrayList<MarginTransferAsset> marginTransferAssetsList;

    /** Constructor to init {@link MarginTransferHistory} object
     * @param jsonTransfer: transfer history details in JSON format
     * @throws IllegalArgumentException when transfers history details are not recoverable
     * **/
    public MarginTransferHistory(JSONObject jsonTransfer) {
        JSONArray jsonTransfers = new JsonHelper(jsonTransfer).getJSONArray("rows");
        if(jsonTransfers != null){
            total = jsonTransfers.length();
            loadMarginTransferAssets(jsonTransfers);
        }else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /** Method to load MarginTransferAssets list
     * @param jsonTransfers: obtained from Binance's request
     * any return
     * **/
    private void loadMarginTransferAssets(JSONArray jsonTransfers) {
        marginTransferAssetsList = new ArrayList<>();
        for (int j = 0; j < jsonTransfers.length(); j++){
            JSONObject jsonObject = jsonTransfers.getJSONObject(j);
            marginTransferAssetsList.add(new MarginTransferAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getDouble("amount"),
                    jsonObject.getString("status"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("type")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginTransferAsset> getMarginTransferAssetsList() {
        return marginTransferAssetsList;
    }

    public void setMarginTransferAssetsList(ArrayList<MarginTransferAsset> marginTransferAssetsList) {
        this.marginTransferAssetsList = marginTransferAssetsList;
    }

    public void insertMarginTransferAsset(MarginTransferAsset marginTransferAsset){
        if(!marginTransferAssetsList.contains(marginTransferAsset)){
            marginTransferAssetsList.add(marginTransferAsset);
            total += 1;
        }
    }

    public boolean removeMarginTransferAsset(MarginTransferAsset marginTransferAsset){
        boolean removed = marginTransferAssetsList.remove(marginTransferAsset);
        if(removed)
            total -= 1;
        return removed;
    }

    public MarginTransferAsset getMarginTransferAsset(int index) {
        return marginTransferAssetsList.get(index);
    }

    @Override
    public String toString() {
        return "MarginTransferHistory{" +
                "total=" + total +
                ", marginTransferAssetsList=" + marginTransferAssetsList +
                '}';
    }

    /**
     * The {@code MarginTransferAsset} class is useful to obtain and format MarginTransferAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * **/

    public static class MarginTransferAsset extends MarginAssetList {

        /**
         * {@code amount} is instance that memorizes amount to transfer
         * **/
        private double amount;

        /**
         * {@code type} is instance that memorizes type value
         * **/
        private String type;

        /** Constructor to init {@link MarginTransferAsset} object
         * @param asset: asset
         * @param txId: identifier of transaction
         * @param amount: amount to transfer
         * @param timestamp: timestamp of transaction
         * @param status: status of transaction
         * @param type: type value
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public MarginTransferAsset(String asset, long txId, double amount, String status, long timestamp, String type) {
            super(asset, txId, timestamp, status);
            if(amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            else
                this.amount = amount;
            if(typeIsValid(type))
                this.type = type;
            else
                throw new IllegalArgumentException("Type can only be ROLL_OUT or ROLL_IN");
        }

        public double getAmount() {
            return amount;
        }

        /** Method to set {@link #amount}
         * @param amount: amount to transfer
         * @throws IllegalArgumentException when amount to transfer value is less than 0
         * **/
        public void setAmount(double amount) {
            if(amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        /** Method to set {@link #type}
         * @param type: type value
         * @throws IllegalArgumentException when type value is not valid
         * **/
        public void setType(String type) {
            if(typeIsValid(type))
                this.type = type;
            else
                throw new IllegalArgumentException("Type can only be ROLL_OUT or ROLL_IN");
        }

        /** Method to check {@link #type} validity
         * @param type: type value
         * @return validity of type as boolean
         * **/
        private boolean typeIsValid(String type){
            return type.equals(TYPE_ROLL_OUT) || type.equals(TYPE_ROLL_IN);
        }

        @Override
        public String toString() {
            return "MarginTransferAsset{" +
                    "amount=" + amount +
                    ", type='" + type + '\'' +
                    ", asset='" + asset + '\'' +
                    ", txId=" + txId +
                    ", timestamp=" + timestamp +
                    ", status='" + status + '\'' +
                    '}';
        }

    }

}
