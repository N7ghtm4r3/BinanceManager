package com.tecknobit.binancemanager.Managers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccountSnapshotSpot extends AccountSnapshot{

    private final int code;
    private final String msg;
    private final String type;
    private ArrayList<DataSpot> assetsSpotData;
    private JSONArray jsonArray;

    public AccountSnapshotSpot(int code, String msg, String type, JSONArray jsonArray) {
        super(code, msg, type, jsonArray);
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    public AccountSnapshotSpot(int code, String msg, String type, JSONArray jsonArray, ArrayList<DataSpot> assetsData) {
        super(code, msg, type, jsonArray);
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.assetsSpotData = assetsData;
        this.jsonArray = jsonArray;
    }

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

    private ArrayList<BalancesSpot> getBalancesSpot(JSONArray jsonArray){
        ArrayList<BalancesSpot> balancesSpots = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject balance = jsonArray.getJSONObject(j);
            balancesSpots.add(new BalancesSpot(balance.getString("asset"),
                    balance.getDouble("free"),
                    balance.getDouble("locked")
            ));
        }
        return balancesSpots;
    }

    public ArrayList<DataSpot> getAssetsSpotData() {
        return assetsSpotData;
    }

    public static class DataSpot {

        private final double totalAssetOfBtc;
        private final double updateTime;
        private final ArrayList<BalancesSpot> balancesSpots;

        public DataSpot(double totalAssetOfBtc, double updateTime, ArrayList<BalancesSpot> balancesSpots) {
            this.totalAssetOfBtc = totalAssetOfBtc;
            this.updateTime = updateTime;
            this.balancesSpots = balancesSpots;
        }

        public double getTotalAssetOfBtc() {
            return totalAssetOfBtc;
        }

        public ArrayList<BalancesSpot> getBalancesSpots() {
            return balancesSpots;
        }

        public double getUpdateTime() {
            return updateTime;
        }

    }

    public static class BalancesSpot {

        private final String asset;
        private final double free;
        private final double locked;

        public BalancesSpot(String asset, double free, double locked) {
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

        public double getLocked() {
            return locked;
        }

    }

}
