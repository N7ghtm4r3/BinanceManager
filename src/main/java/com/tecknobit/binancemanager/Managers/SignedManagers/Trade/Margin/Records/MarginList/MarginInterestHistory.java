package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginInterestHistory} class is useful to format Binance Margin Interest History request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginInterestHistory {

    public static final String ON_BORROW = "ON_BORROW";
    public static final String PERIODIC = "PERIODIC";
    public static final String PERIODIC_CONVERTED = "PERIODIC_CONVERTED";
    public static final String ON_BORROW_CONVERTED = "ON_BORROW_CONVERTED";
    private int total;
    private ArrayList<MarginInterestAsset> marginInterestAssetsList;

    public MarginInterestHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadMarginInterestAssets(jsonArray);
    }

    /** Method to load InterestAssets list
     * @param jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadMarginInterestAssets(JSONArray jsonArray) {
        marginInterestAssetsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginInterestAssetsList.add(new MarginInterestAsset(jsonObject.getString("isolatedSymbol"),
                    jsonObject.getString("asset"),
                    jsonObject.getDouble("interest"),
                    jsonObject.getLong("interestAccusedTime"),
                    jsonObject.getDouble("interestRate"),
                    jsonObject.getDouble("principal"),
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

    public ArrayList<MarginInterestAsset> getMarginInterestAssetsList() {
        return marginInterestAssetsList;
    }

    public void setMarginInterestAssetsList(ArrayList<MarginInterestAsset> marginInterestAssetsList) {
        this.marginInterestAssetsList = marginInterestAssetsList;
    }

    public void insertMarginInterestAsset(MarginInterestAsset marginInterestAsset){
        if(!marginInterestAssetsList.contains(marginInterestAsset)) {
            marginInterestAssetsList.add(marginInterestAsset);
            setTotal(total + 1);
        }
    }

    public boolean removeMarginInterestAsset(MarginInterestAsset marginInterestAsset){
        boolean removed = marginInterestAssetsList.remove(marginInterestAsset);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public MarginInterestAsset getMarginInterestAsset(int index) {
        return marginInterestAssetsList.get(index);
    }

    /**
     * The {@code MarginInterestAsset} class is useful to obtain and format MarginInterestAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * **/

    public static class MarginInterestAsset {

        private final String isolatedSymbol;
        private final String asset;
        private double interest;
        private long interestAccuredTime;
        private double interestRate;
        private double principal;
        private String type;

        public MarginInterestAsset(String isolatedSymbol, String asset, double interest, long interestAccuredTime,
                                   double interestRate, double principal, String type) {
            this.isolatedSymbol = isolatedSymbol;
            this.asset = asset;
            this.interest = interest;
            this.interestAccuredTime = interestAccuredTime;
            this.interestRate = interestRate;
            this.principal = principal;
            this.type = type;
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public String getAsset() {
            return asset;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

        public long getInterestAccuredTime() {
            return interestAccuredTime;
        }

        public void setInterestAccuredTime(long interestAccuredTime) {
            if(interestAccuredTime < 0)
                throw new IllegalArgumentException("Interest accured time value cannot be less than 0");
            this.interestAccuredTime = interestAccuredTime;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(double interestRate) {
            if(interestRate < 0)
                throw new IllegalArgumentException("Interest rate value cannot be less than 0");
            this.interestRate = interestRate;
        }

        public double getPrincipal() {
            return principal;
        }

        public void setPrincipal(double principal) {
            if(principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            this.principal = principal;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            if(type.equals(ON_BORROW) || type.equals(ON_BORROW_CONVERTED) || type.equals(PERIODIC)
                    || type.equals(PERIODIC_CONVERTED)) {
                this.type = type;
            }else
                throw new IllegalArgumentException("Type value can only be ON_BORROW,ON_BORROW_CONVERTED,PERIODIC " +
                        "or PERIODIC_CONVERTED");
        }

    }

}
