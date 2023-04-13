package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale.HashRateResaleDetail.HashRateDetail;

public class HashRateResaleDetail extends MiningResponse<HashRateDetail> {

    public HashRateResaleDetail(HashRateDetail data) {
        super(data);
    }

    public HashRateResaleDetail(JSONObject jHashRateResaleDetail) {
        super(jHashRateResaleDetail);
        data = new HashRateDetail(hItem.getJSONObject("data"));
    }

    public static class HashRateDetail extends DataListItem {

        private final ArrayList<ProfitTransferDetail> profitTransferDetails;

        public HashRateDetail(int totalNum, int pageSize, ArrayList<ProfitTransferDetail> profitTransferDetails) {
            super(totalNum, pageSize);
            this.profitTransferDetails = profitTransferDetails;
        }

        public HashRateDetail(JSONObject jHashRateDetail) {
            super(jHashRateDetail);
            profitTransferDetails = new ArrayList<>();
            for (Object detail : hItem.fetchList("profitTransferDetails"))
                profitTransferDetails.add(new ProfitTransferDetail((JSONObject) detail));
        }

        public ArrayList<ProfitTransferDetail> getProfitTransferDetails() {
            return profitTransferDetails;
        }

        public static class ProfitTransferDetail extends HashrateResaleStructure {

            private final long day;
            private final double amount;
            private final String coinName;

            public ProfitTransferDetail(String poolUsername, String toPoolUsername, String algoName, long hashRate,
                                        long day, double amount, String coinName) {
                super(poolUsername, toPoolUsername, algoName, hashRate);
                this.day = day;
                this.amount = amount;
                this.coinName = coinName;
            }

            public ProfitTransferDetail(JSONObject jProfitTransferDetail) {
                super(jProfitTransferDetail);
                day = hItem.getLong("day", 0);
                amount = hItem.getDouble("amount", 0);
                coinName = hItem.getString("coinName");
            }

            public long getDay() {
                return day;
            }

            public double getAmount() {
                return amount;
            }

            public String getCoinName() {
                return coinName;
            }

        }

    }

}
