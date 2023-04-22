package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code SummarySubMarginAccount} class is useful to format a summary sub margin account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-margin-account-for-master-account">
 * Get Summary of Sub-account's Margin Account (For Master Account)</a>
 * @see BinanceItem
 * @see MarginAccount
 **/
public class SummarySubMarginAccount extends MarginAccount {

    /**
     * {@code subAccountList} subaccount list the summary sub margin account
     **/
    private final ArrayList<SummarySubMarginAccountItem> subAccountList;

    /**
     * Constructor to init {@link SummarySubMarginAccount} object
     *
     * @param totalAssetOfBtc     :     total asset of Bitcoin
     * @param totalLiabilityOfBtc : total liability of Bitcoin
     * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
     * @param subAccountList      :  subaccount list the summary sub margin account
     **/
    public SummarySubMarginAccount(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                   ArrayList<SummarySubMarginAccountItem> subAccountList) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.subAccountList = subAccountList;
    }

    /**
     * Constructor to init {@link SummarySubMarginAccount} object
     *
     * @param jSummarySubMarginAccount : summary sub margin account details as {@link JSONObject}
     **/
    public SummarySubMarginAccount(JSONObject jSummarySubMarginAccount) {
        super(jSummarySubMarginAccount);
        subAccountList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("subAccountList");
        if (jList != null)
            for (JSONObject item : jList)
                subAccountList.add(new SummarySubMarginAccountItem(item));
    }

    /**
     * Method to get {@link #subAccountList} instance <br>
     * No-any params required
     *
     * @return {@link #subAccountList} instance as {@link ArrayList} of {@link SummarySubMarginAccountItem}
     **/
    public ArrayList<SummarySubMarginAccountItem> getSubAccountList() {
        return subAccountList;
    }

    /**
     * The {@code SummarySubMarginAccountItem} class is useful to format a summary sub margin account item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see MarginAccount
     **/
    public static class SummarySubMarginAccountItem extends MarginAccount {

        /**
         * {@code email} subaccount list the summary sub margin account item
         **/
        private final String email;

        /**
         * Constructor to init {@link SummarySubMarginAccountItem} object
         *
         * @param totalAssetOfBtc     :     total asset of Bitcoin
         * @param totalLiabilityOfBtc : total liability of Bitcoin
         * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
         * @param email  :  subaccount list the summary sub margin account item
         **/
        public SummarySubMarginAccountItem(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                           String email) {
            super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
            this.email = email;
        }

        /**
         * Constructor to init {@link SummarySubMarginAccountItem} object
         *
         * @param jSummarySubMarginAccountItem : summary sub margin account item details as {@link JSONObject}
         **/
        public SummarySubMarginAccountItem(JSONObject jSummarySubMarginAccountItem) {
            super(jSummarySubMarginAccountItem);
            email = hItem.getString("email");
        }

        /**
         * Method to get {@link #email} instance <br>
         * No-any params required
         *
         * @return {@link #email} instance as {@link String}
         **/
        public String getEmail() {
            return email;
        }

    }

}
