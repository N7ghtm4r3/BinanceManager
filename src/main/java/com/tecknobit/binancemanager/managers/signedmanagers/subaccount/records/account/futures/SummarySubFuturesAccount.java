package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import org.json.JSONObject;

import java.util.ArrayList;

public class SummarySubFuturesAccount extends SubFuturesAccountStructure {

    private final String asset;
    private final ArrayList<SummarySubFuturesAccountItem> subAccountList;

    public SummarySubFuturesAccount(double totalInitialMargin, double totalMaintenanceMargin, double totalMarginBalance,
                                    double totalOpenOrderInitialMargin, double totalPositionInitialMargin,
                                    double totalUnrealizedProfit, double totalWalletBalance, String asset,
                                    ArrayList<SummarySubFuturesAccountItem> subAccountList) {
        super(totalInitialMargin, totalMaintenanceMargin, totalMarginBalance, totalOpenOrderInitialMargin,
                totalPositionInitialMargin, totalUnrealizedProfit, totalWalletBalance);
        this.asset = asset;
        this.subAccountList = subAccountList;
    }

    public SummarySubFuturesAccount(JSONObject jSummarySubFuturesAccount) {
        super(jSummarySubFuturesAccount);
        asset = hItem.getString("asset");
        subAccountList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("subAccountList");
        if (jList != null)
            for (JSONObject jItem : jList)
                subAccountList.add(new SummarySubFuturesAccountItem(jItem));
    }

    public String getAsset() {
        return asset;
    }

    public ArrayList<SummarySubFuturesAccountItem> getSubAccountList() {
        return subAccountList;
    }

    public static class SummarySubFuturesAccountItem extends SubFuturesAccountStructure {

        private final String email;
        private final String asset;

        public SummarySubFuturesAccountItem(double totalInitialMargin, double totalMaintenanceMargin,
                                            double totalMarginBalance, double totalOpenOrderInitialMargin,
                                            double totalPositionInitialMargin, double totalUnrealizedProfit,
                                            double totalWalletBalance, String email, String asset) {
            super(totalInitialMargin, totalMaintenanceMargin, totalMarginBalance, totalOpenOrderInitialMargin,
                    totalPositionInitialMargin, totalUnrealizedProfit, totalWalletBalance);
            this.email = email;
            this.asset = asset;
        }

        public SummarySubFuturesAccountItem(JSONObject jSummarySubFuturesAccountItem) {
            super(jSummarySubFuturesAccountItem);
            email = hItem.getString("email");
            asset = hItem.getString("asset");
        }

        public String getEmail() {
            return email;
        }

        public String getAsset() {
            return asset;
        }

    }

}
