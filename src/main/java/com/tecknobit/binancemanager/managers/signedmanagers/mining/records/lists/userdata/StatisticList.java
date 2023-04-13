package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.HashMap;

public class StatisticList extends MiningResponse<StatisticList.MiningStatistic> {

    public StatisticList(MiningStatistic data) {
        super(data);
    }

    public StatisticList(JSONObject jStatisticList) {
        super(jStatisticList);
        data = new MiningStatistic(hItem.getJSONObject("data"));
    }

    public static class MiningStatistic extends BinanceItem {

        private final String fifteenMinHashRate;
        private final String dayHashRate;
        private final int validNum;
        private final int invalidNum;
        private final HashMap<String, Double> profitToday;
        private final HashMap<String, Double> profitYesterday;
        private final String userName;
        private final String unit;
        private final String algo;

        public MiningStatistic(String fifteenMinHashRate, String dayHashRate, int validNum, int invalidNum,
                               HashMap<String, Double> profitToday, HashMap<String, Double> profitYesterday,
                               String userName, String unit, String algo) {
            super(null);
            this.fifteenMinHashRate = fifteenMinHashRate;
            this.dayHashRate = dayHashRate;
            this.validNum = validNum;
            this.invalidNum = invalidNum;
            this.profitToday = profitToday;
            this.profitYesterday = profitYesterday;
            this.userName = userName;
            this.unit = unit;
            this.algo = algo;
        }

        public MiningStatistic(JSONObject jMiningStatistic) {
            super(jMiningStatistic);
            fifteenMinHashRate = hItem.getString("fifteenMinHashRate");
            dayHashRate = hItem.getString("dayHashRate");
            validNum = hItem.getInt("validNum", 0);
            invalidNum = hItem.getInt("invalidNum", 0);
            profitToday = loadProfitMap(hItem.getJSONObject("profitToday"));
            profitYesterday = loadProfitMap(hItem.getJSONObject("profitYesterday"));
            userName = hItem.getString("userName");
            unit = hItem.getString("unit");
            algo = hItem.getString("algo");
        }

        private HashMap<String, Double> loadProfitMap(JSONObject jMap) {
            HashMap<String, Double> profitMap = new HashMap<>();
            for (String key : jMap.keySet())
                profitMap.put(key, jMap.getDouble(key));
            return profitMap;
        }

        public String getFifteenMinHashRate() {
            return fifteenMinHashRate;
        }

        public String getDayHashRate() {
            return dayHashRate;
        }

        public int getValidNum() {
            return validNum;
        }

        public int getInvalidNum() {
            return invalidNum;
        }

        public HashMap<String, Double> getProfitToday() {
            return profitToday;
        }

        public HashMap<String, Double> getProfitYesterday() {
            return profitYesterday;
        }

        public String getUserName() {
            return userName;
        }

        public String getUnit() {
            return unit;
        }

        public String getAlgo() {
            return algo;
        }

    }

}
