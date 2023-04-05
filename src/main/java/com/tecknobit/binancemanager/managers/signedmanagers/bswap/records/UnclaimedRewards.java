package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UnclaimedRewards extends BinanceItem {

    private final HashMap<String, Double> totalUnclaimedRewards;
    private final ArrayList<Detail> details;

    public UnclaimedRewards(HashMap<String, Double> totalUnclaimedRewards, ArrayList<Detail> details) {
        super(null);
        this.totalUnclaimedRewards = totalUnclaimedRewards;
        this.details = details;
    }

    public UnclaimedRewards(JSONObject jUnclaimedRewards) {
        super(jUnclaimedRewards);
        totalUnclaimedRewards = new HashMap<>();
        JSONObject jItem = hItem.getJSONObject("totalUnclaimedRewards", new JSONObject());
        for (String asset : jItem.keySet())
            totalUnclaimedRewards.put(asset, jItem.getDouble(asset));
        details = new ArrayList<>();
        jItem = hItem.getJSONObject("details");
        for (String pair : jItem.keySet())
            details.add(new Detail(jItem.getJSONObject(pair).put("pair", pair)));
    }

    public HashMap<String, Double> getTotalUnclaimedRewards() {
        return totalUnclaimedRewards;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public static class Detail extends BinanceItem {

        private final String pair;
        private final HashMap<String, Double> rewards;

        public Detail(String pair, HashMap<String, Double> rewards) {
            super(null);
            this.pair = pair;
            this.rewards = rewards;
        }

        public Detail(JSONObject jDetail) {
            super(jDetail);
            pair = hItem.getString("pair");
            jDetail.remove("pair");
            rewards = new HashMap<>();
            for (String key : jDetail.keySet())
                rewards.put(key, jDetail.getDouble(key));
        }

        public String getPair() {
            return pair;
        }

        public HashMap<String, Double> getRewards() {
            return rewards;
        }

    }

}
