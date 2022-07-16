package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code AccountSnapshotSpot} class is useful to obtain and format AccountSnapshotSpot object
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshotSpot extends AccountSnapshot{

    private ArrayList<DataSpot> assetsSpotData;

    public AccountSnapshotSpot(int code, String msg, String type, JSONArray jsonArray) {
        super(code, msg, type, jsonArray);
    }

    public AccountSnapshotSpot(int code, String msg, String type, JSONArray jsonArray, ArrayList<DataSpot> assetsData) {
        super(code, msg, type, jsonArray);
        this.assetsSpotData = assetsData;
    }

    /** Method to get getAccountSnapshotSpot object
     * any params required
     * @return AccountSnapshotSpot object then to cast
     * **/
    public AccountSnapshotSpot getAccountSnapshotSpot() {
        assetsSpotData = new ArrayList<>();
        if(jsonArray != null){
            for (int j=0; j < jsonArray.length(); j++){
                JSONObject dataSpotRow = jsonArray.getJSONObject(j);
                double updateTime = dataSpotRow.getLong("updateTime");
                dataSpotRow = dataSpotRow.getJSONObject("data");
                double totalAssetOfBtc = dataSpotRow.getDouble("totalAssetOfBtc");
                assetsSpotData.add(new DataSpot(totalAssetOfBtc, updateTime, getBalancesSpot(dataSpotRow.getJSONArray("balances"))));
            }
        }
        return new AccountSnapshotSpot(code,msg,type,jsonArray,assetsSpotData);
    }

    /** Method to assemble an BalanceSpot list
     * @param jsonArray: jsonArray obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * @return BalanceSpot list as ArrayList<BalanceSpot>
     * **/
    public static ArrayList<BalanceSpot> getBalancesSpot(JSONArray jsonArray){
        ArrayList<BalanceSpot> balanceSpots = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject balance = jsonArray.getJSONObject(j);
            balanceSpots.add(new BalanceSpot(balance.getString("asset"),
                    balance.getDouble("free"),
                    balance.getDouble("locked")
            ));
        }
        return balanceSpots;
    }

    public ArrayList<DataSpot> getAssetsSpotData() {
        return assetsSpotData;
    }

    public DataSpot getAssetSpotData(int index){
        return assetsSpotData.get(index);
    }

    /**
     *  The {@code DataSpot} class is useful to obtain and format DataSpot object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class DataSpot {

        private double totalAssetOfBtc;
        private double updateTime;
        private ArrayList<BalanceSpot> balanceSpotList;

        public DataSpot(double totalAssetOfBtc, double updateTime, ArrayList<BalanceSpot> balanceSpot) {
            this.totalAssetOfBtc = totalAssetOfBtc;
            this.updateTime = updateTime;
            this.balanceSpotList = balanceSpot;
        }

        public double getTotalAssetOfBtc() {
            return totalAssetOfBtc;
        }

        public void setTotalAssetOfBtc(double totalAssetOfBtc) {
            if(totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            this.totalAssetOfBtc = totalAssetOfBtc;
        }

        public double getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(double updateTime) {
            if(updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        public ArrayList<BalanceSpot> getBalancesSpotList() {
            return balanceSpotList;
        }

        public void setBalancesSpotList(ArrayList<BalanceSpot> balanceSpotList) {
            this.balanceSpotList = balanceSpotList;
        }

        public void insertBalanceSpot(BalanceSpot balanceSpot){
            if(!balanceSpotList.contains(balanceSpot))
                balanceSpotList.add(balanceSpot);
        }

        public boolean removeBalanceSpot(BalanceSpot balanceSpot){
            return balanceSpotList.remove(balanceSpot);
        }

        public BalanceSpot getBalanceSpot(int index){
            return balanceSpotList.get(index);
        }

    }

    /**
     *  The {@code BalanceSpot} class is useful to obtain and format BalanceSpot object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
     * **/

    public static class BalanceSpot {

        private final String asset;
        private double free;
        private double locked;

        public BalanceSpot(String asset, double free, double locked) {
            this.asset = asset;
            this.free = free;
            this.locked = locked;
        }

        public String getAsset() {
            return asset;
        }

        public double getFree() {
            return free;
        }

        public void setFree(double free) {
            if(free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            this.free = free;
        }

        public double getLocked() {
            return locked;
        }

        public void setLocked(double locked) {
            if(locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
            this.locked = locked;
        }

    }

}
