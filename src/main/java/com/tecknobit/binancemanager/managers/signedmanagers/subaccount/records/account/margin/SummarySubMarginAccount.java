package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import org.json.JSONObject;

import java.util.ArrayList;

public class SummarySubMarginAccount extends MarginAccount {

    private final ArrayList<SummarySubMarginAccountItem> subAccountList;

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param totalAssetOfBtc     :     total asset of Bitcoin
     * @param totalLiabilityOfBtc : total liability of Bitcoin
     * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SummarySubMarginAccount(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                   ArrayList<SummarySubMarginAccountItem> subAccountList) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.subAccountList = subAccountList;
    }

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param jSubMarginAccountSummary : margin account details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SummarySubMarginAccount(JSONObject jSubMarginAccountSummary) {
        super(jSubMarginAccountSummary);
        subAccountList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("subAccountList");
        if (jList != null)
            for (JSONObject item : jList)
                subAccountList.add(new SummarySubMarginAccountItem(item));
    }

    public ArrayList<SummarySubMarginAccountItem> getSubAccountList() {
        return subAccountList;
    }

    public static class SummarySubMarginAccountItem extends MarginAccount {

        private final String email;

        /**
         * Constructor to init {@link MarginAccount} object
         *
         * @param totalAssetOfBtc     :     total asset of Bitcoin
         * @param totalLiabilityOfBtc : total liability of Bitcoin
         * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SummarySubMarginAccountItem(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                           String email) {
            super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
            this.email = email;
        }

        /**
         * Constructor to init {@link MarginAccount} object
         *
         * @param jSubMarginAccountSummaryItem : margin account details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SummarySubMarginAccountItem(JSONObject jSubMarginAccountSummaryItem) {
            super(jSubMarginAccountSummaryItem);
            email = hItem.getString("email");
        }

        public String getEmail() {
            return email;
        }

    }

}
