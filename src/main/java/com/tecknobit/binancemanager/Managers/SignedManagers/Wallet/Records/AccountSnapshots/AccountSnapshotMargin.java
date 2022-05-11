package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code AccountSnapshotMargin} class is useful to obtain and format AccountSnapshotMargin object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshotMargin extends AccountSnapshot{

    private ArrayList<DataMargin> dataMarginsList;

    public AccountSnapshotMargin(int code, String msg, String type, JSONArray jsonArray) {
        super(code, msg, type, jsonArray);
    }

    public AccountSnapshotMargin(int code, String msg, String type, JSONArray jsonArray, ArrayList<DataMargin> dataMargins) {
        super(code, msg, type, jsonArray);
        this.dataMarginsList = dataMargins;
    }

    /** Method to get getAccountSnapshotMargin object
     * any params required
     * @return AccountSnapshotMargin object then to cast
     * **/
    public AccountSnapshotMargin getAccountSnapshotMargin() {
        dataMarginsList = new ArrayList<>();
        if(jsonArray != null) {
            for (int j=0; j < jsonArray.length(); j++){
                  JSONObject dataRow = jsonArray.getJSONObject(j);
                  long updateTime = dataRow.getLong("updateTime");
                  dataRow = dataRow.getJSONObject("data");
                  double marginLevel = dataRow.getDouble("marginLevel");;
                  double totalAssetOfBtc = dataRow.getDouble("totalAssetOfBtc");;
                  double totalLiabilityOfBtc = dataRow.getDouble("totalLiabilityOfBtc");;
                  double totalNetAssetOfBtc = dataRow.getDouble("totalNetAssetOfBtc");
                  dataMarginsList.add(new DataMargin(marginLevel,
                          totalAssetOfBtc,
                          totalLiabilityOfBtc,
                          totalNetAssetOfBtc,
                          updateTime,
                          assembleUserMarginAssetsList(dataRow.getJSONArray("userAssets"))
                  ));
            }
        }
        return new AccountSnapshotMargin(code, msg, type, jsonArray, dataMarginsList);
    }

    /** Method to assemble an UserAssetMargin list
     * @param #jsonArray: jsonArray obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return userAssetMargin list as ArrayList<UserAssetMargin>
     * **/
    public static ArrayList<UserAssetMargin> assembleUserMarginAssetsList(JSONArray jsonArray) {
        ArrayList<UserAssetMargin> userAssetMargins = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            userAssetMargins.add(UserAssetMargin.assembleUserMarginAsset(jsonArray.getJSONObject(j)));
        return userAssetMargins;
    }

    public ArrayList<DataMargin> getDataMarginsList() {
        return dataMarginsList;
    }

    /**
     *  The {@code DataMargin} class is useful to obtain and format DataMargin object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataMargin {

        private double marginLevel;
        private double totalAssetOfBtc;
        private double totalLiabilityOfBtc;
        private double totalNetAssetOfBtc;
        private long updateTime;
        private ArrayList<UserAssetMargin> userAssetMarginsList;

        public DataMargin(double marginLevel, double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                          long updateTime, ArrayList<UserAssetMargin> userAssetsMargin) {
            this.marginLevel = marginLevel;
            this.totalAssetOfBtc = totalAssetOfBtc;
            this.totalLiabilityOfBtc = totalLiabilityOfBtc;
            this.totalNetAssetOfBtc = totalNetAssetOfBtc;
            this.updateTime = updateTime;
            this.userAssetMarginsList = userAssetsMargin;
        }

        public double getMarginLevel() {
            return marginLevel;
        }

        public void setMarginLevel(double marginLevel) {
            if(marginLevel < 0)
                throw new IllegalArgumentException("Margin level value cannot be less than 0");
            this.marginLevel = marginLevel;
        }

        public double getTotalAssetOfBtc() {
            return totalAssetOfBtc;
        }

        public void setTotalAssetOfBtc(double totalAssetOfBtc) {
            if(totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            this.totalAssetOfBtc = totalAssetOfBtc;
        }

        public double getTotalLiabilityOfBtc() {
            return totalLiabilityOfBtc;
        }

        public void setTotalLiabilityOfBtc(double totalLiabilityOfBtc) {
            if(totalLiabilityOfBtc < 0)
                throw new IllegalArgumentException("Total liability of BTC value cannot be less than 0");
            this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        }

        public double getTotalNetAssetOfBtc() {
            return totalNetAssetOfBtc;
        }

        public void setTotalNetAssetOfBtc(double totalNetAssetOfBtc) {
            if(totalNetAssetOfBtc < 0)
                throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
            this.totalNetAssetOfBtc = totalNetAssetOfBtc;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            if(updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        public ArrayList<UserAssetMargin> getUserAssetMarginsList() {
            return userAssetMarginsList;
        }

        public void setUserAssetsMargin(ArrayList<UserAssetMargin> userAssetMargins) {
            this.userAssetMarginsList = userAssetMargins;
        }

        public void insertUserAssetMargin(UserAssetMargin userAssetMargin){
            if(!userAssetMarginsList.contains(userAssetMargin))
                userAssetMarginsList.add(userAssetMargin);
        }

        public boolean removeUserAssetMargin(UserAssetMargin userAssetMargin){
            return userAssetMarginsList.remove(userAssetMargin);
        }

        public UserAssetMargin getUserAssetMargin(int index) {
            return userAssetMarginsList.get(index);
        }

    }

    /**
     *  The {@code UserAssetMargin} class is useful to obtain and format UserAssetMargin object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class UserAssetMargin{

        private final String asset;
        private double borrowed;
        private double free;
        private double interest;
        private double locked;
        private double netAsset;

        public UserAssetMargin(String asset, double borrowed, double free, double interest, double locked, double netAsset) {
            this.asset = asset;
            this.borrowed = borrowed;
            this.free = free;
            this.interest = interest;
            this.locked = locked;
            this.netAsset = netAsset;
        }

        public static UserAssetMargin assembleUserMarginAsset(JSONObject userAsset){
            return new UserAssetMargin(userAsset.getString("asset"),
                    userAsset.getDouble("borrowed"),
                    userAsset.getDouble("free"),
                    userAsset.getDouble("interest"),
                    userAsset.getDouble("locked"),
                    userAsset.getDouble("netAsset")
            );
        }

        public String getAsset() {
            return asset;
        }

        public double getBorrowed() {
            return borrowed;
        }

        public void setBorrowed(double borrowed) {
            if(borrowed < 0)
                throw new IllegalArgumentException("Borrowed value cannot be less than 0");
            this.borrowed = borrowed;
        }

        public double getFree() {
            return free;
        }

        public void setFree(double free) {
            if(free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            this.free = free;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

        public double getLocked() {
            return locked;
        }

        public void setLocked(double locked) {
            if(locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
            this.locked = locked;
        }

        public double getNetAsset() {
            return netAsset;
        }

        public void setNetAsset(double netAsset) {
            if(netAsset < 0)
                throw new IllegalArgumentException("Net asset value cannot be less than 0");
            this.netAsset = netAsset;
        }

    }

}
