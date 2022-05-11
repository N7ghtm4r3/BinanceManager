package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginLoan} class is useful to format Binance Margin Query Loan repay request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginLoan {

    private int total;
    private ArrayList<MarginLoanAsset> marginLoanAssetsList;

    public MarginLoan(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadMarginLoanAssets(jsonArray);
    }

    /** Method to load LoanAssets list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadMarginLoanAssets(JSONArray jsonArray) {
        marginLoanAssetsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginLoanAssetsList.add(new MarginLoanAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("status"),
                    jsonObject.getString("isolatedSymbol"),
                    jsonObject.getDouble("principal")
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

    public ArrayList<MarginLoanAsset> getMarginLoanAssetsList() {
        return marginLoanAssetsList;
    }

    public void setMarginLoanAssetsList(ArrayList<MarginLoanAsset> marginLoanAssetsList) {
        this.marginLoanAssetsList = marginLoanAssetsList;
    }

    public void insertMarginIsolatedTransfer(MarginLoanAsset marginLoanAsset){
        if(!marginLoanAssetsList.contains(marginLoanAsset)){
            marginLoanAssetsList.add(marginLoanAsset);
            setTotal(total + 1);
        }
    }

    public boolean removeMarginIsolatedTransfer(MarginLoanAsset marginLoanAsset){
        boolean removed = marginLoanAssetsList.remove(marginLoanAsset);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public MarginLoanAsset getMarginLoanAsset(int index) {
        return marginLoanAssetsList.get(index);
    }

    /**
     * The {@code MarginLoanAsset} class is useful to obtain and format MarginLoanAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * **/

    public static class MarginLoanAsset extends MarginAssetList {

        private final String isolatedSymbol;
        private final double principal;

        public MarginLoanAsset(String asset, long txId, long timestamp, String status, String isolatedSymbol,
                               double principal) {
            super(asset, txId, timestamp, status);
            this.isolatedSymbol = isolatedSymbol;
            this.principal = principal;
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public double getPrincipal() {
            return principal;
        }

    }

}
