package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code MarginIsolatedTransferHistory} class is useful to format Binance Isolated Transfer History request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginIsolatedTransferHistory {

    public static final String TRANSFER_SPOT = "SPOT";
    public static final String TRANSFER_ISOLATED_MARGIN = "ISOLATED_MARGIN";
    private int total;
    private ArrayList<MarginIsolatedTransfer> marginIsolatedTransfersList;

    public MarginIsolatedTransferHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadIsolatedTransfersList(jsonArray);
    }

    /** Method to assemble a MarginIsolatedTransfer list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadIsolatedTransfersList(JSONArray jsonArray) {
        marginIsolatedTransfersList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject isolatedTransfer = jsonArray.getJSONObject(j);
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

    public void setTotal(int total) {
        if(total < 0)
            throw new IllegalArgumentException("Total value cannot be less than 0");
        this.total = total;
    }

    public ArrayList<MarginIsolatedTransfer> getMarginIsolatedTransfersList() {
        return marginIsolatedTransfersList;
    }

    public void setMarginIsolatedTransfersList(ArrayList<MarginIsolatedTransfer> marginIsolatedTransfersList) {
        this.marginIsolatedTransfersList = marginIsolatedTransfersList;
    }

    public void insertMarginIsolatedTransfer(MarginIsolatedTransfer marginIsolatedTransfer){
        if(!marginIsolatedTransfersList.contains(marginIsolatedTransfer)){
            marginIsolatedTransfersList.add(marginIsolatedTransfer);
            setTotal(total + 1);
        }
    }

    public boolean removeMarginIsolatedTransfer(MarginIsolatedTransfer marginIsolatedTransfer){
        boolean removed = marginIsolatedTransfersList.remove(marginIsolatedTransfer);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public MarginIsolatedTransfer getMarginIsolatedTransfer(int index) {
        try{
            return marginIsolatedTransfersList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * The {@code MarginIsolatedTransfer} class is useful to obtain and format IsolatedData object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * **/

    public static class MarginIsolatedTransfer extends MarginAssetList {

        private final double amount;
        private final String transFrom;
        private final String transTo;

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
