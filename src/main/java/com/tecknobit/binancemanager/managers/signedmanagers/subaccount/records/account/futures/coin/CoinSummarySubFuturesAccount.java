package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin;

import org.json.JSONObject;

import java.util.ArrayList;

public class CoinSummarySubFuturesAccount extends SubCoinStructure {

    private final ArrayList<SummarySubFuturesAccountCoinItem> subAccountList;

    public CoinSummarySubFuturesAccount(double totalMarginBalanceOfBTC, double totalUnrealizedProfitOfBTC,
                                        double totalWalletBalanceOfBTC, String asset,
                                        ArrayList<SummarySubFuturesAccountCoinItem> subAccountList) {
        super(totalMarginBalanceOfBTC, totalUnrealizedProfitOfBTC, totalWalletBalanceOfBTC, asset);
        this.subAccountList = subAccountList;
    }

    public CoinSummarySubFuturesAccount(JSONObject jCoinSummarySubFuturesAccount) {
        super(jCoinSummarySubFuturesAccount);
        subAccountList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("");
        if (jList != null)
            for (JSONObject item : jList)
                subAccountList.add(new SummarySubFuturesAccountCoinItem(item));
    }

    public static class SummarySubFuturesAccountCoinItem extends SubCoinStructure {

        private final String email;

        public SummarySubFuturesAccountCoinItem(double totalMarginBalanceOfBTC, double totalUnrealizedProfitOfBTC,
                                                double totalWalletBalanceOfBTC, String asset, String email) {
            super(totalMarginBalanceOfBTC, totalUnrealizedProfitOfBTC, totalWalletBalanceOfBTC, asset);
            this.email = email;
        }

        public SummarySubFuturesAccountCoinItem(JSONObject jSummarySubFuturesAccountCoinItem) {
            super(jSummarySubFuturesAccountCoinItem);
            email = hItem.getString("email");
        }

        public String getEmail() {
            return email;
        }

    }

}
