package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpotAssetsSummary extends BinanceItem {

    private final int totalCount;
    private final double masterAccountTotalAsset;
    private final ArrayList<SpotSubUserAsset> spotSubUserAssetBtcVoList;

    public SpotAssetsSummary(int totalCount, double masterAccountTotalAsset,
                             ArrayList<SpotSubUserAsset> spotSubUserAssetBtcVoList) {
        super(null);
        this.totalCount = totalCount;
        this.masterAccountTotalAsset = masterAccountTotalAsset;
        this.spotSubUserAssetBtcVoList = spotSubUserAssetBtcVoList;
    }

    public SpotAssetsSummary(JSONObject jSpotAssetsSummary) {
        super(jSpotAssetsSummary);
        totalCount = hItem.getInt("totalCount", 0);
        masterAccountTotalAsset = hItem.getDouble("masterAccountTotalAsset", 0);
        spotSubUserAssetBtcVoList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("spotSubUserAssetBtcVoList");
        if (jList != null)
            for (JSONObject item : jList)
                spotSubUserAssetBtcVoList.add(new SpotSubUserAsset(item));
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getMasterAccountTotalAsset() {
        return masterAccountTotalAsset;
    }

    public ArrayList<SpotSubUserAsset> getSpotSubUserAssetBtcVoList() {
        return spotSubUserAssetBtcVoList;
    }

    public static class SpotSubUserAsset extends BinanceItem {

        private final String email;
        private final double totalAsset;

        public SpotSubUserAsset(String email, double totalAsset) {
            super(null);
            this.email = email;
            this.totalAsset = totalAsset;
        }

        public SpotSubUserAsset(JSONObject jSpotSubUserAsset) {
            super(jSpotSubUserAsset);
            email = hItem.getString("email");
            totalAsset = hItem.getDouble("totalAsset", 0);
        }

        public String getEmail() {
            return email;
        }

        public double getTotalAsset() {
            return totalAsset;
        }

    }

}
