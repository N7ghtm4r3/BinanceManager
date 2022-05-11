package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginTransferHistory} class is useful to format Binance Margin Get Cross Transfer History request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginTransferHistory {

    public static final String TYPE_ROLL_IN = "ROLL_IN";
    public static final String TYPE_ROLL_OUT = "ROLL_OUT";
    private int total;
    private ArrayList<MarginTransferAsset> marginTransferAssetsList;

    public MarginTransferHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        this.total = jsonArray.length();
        loadMarginTransferAssets(jsonArray);
    }

    /** Method to load MarginTransferAssets list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadMarginTransferAssets(JSONArray jsonArray) {
        marginTransferAssetsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
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

    public void setTotal(int total) {
        if(total < 0)
            throw new IllegalArgumentException("Total value cannot be less than 0");
        this.total = total;
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
            setTotal(total + 1);
        }
    }

    public boolean removeMarginTransferAsset(MarginTransferAsset marginTransferAsset){
        boolean removed = marginTransferAssetsList.remove(marginTransferAsset);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public MarginTransferAsset getMarginTransferAsset(int index) {
        try{
            return marginTransferAssetsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(" "+ index);
        }
    }

    /**
     * The {@code MarginTransferAsset} class is useful to obtain and format MarginTransferAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * **/

    public static class MarginTransferAsset extends MarginAssetList {

        private double amount;
        private String type;

        public MarginTransferAsset(String asset, long txId, double amount, String status, long timestamp, String type) {
            super(asset, txId, timestamp, status);
            this.amount = amount;
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            if(amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            if(type.equals(TYPE_ROLL_OUT) || type.equals(TYPE_ROLL_IN))
                this.type = type;
            else
                throw new IllegalArgumentException("Type can only be ROLL_OUT or ROLL_IN");
        }

    }

}
