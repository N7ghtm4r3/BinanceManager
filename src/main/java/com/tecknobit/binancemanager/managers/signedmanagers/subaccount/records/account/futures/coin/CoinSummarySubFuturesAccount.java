package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code CoinSummarySubFuturesAccount} class is useful to format a coin summary of a sub futures account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
 * Get Summary of Sub-account's Futures Account V2 (For Master Account)</a>
 * @see BinanceItem
 * @see SubCoinStructure
 **/
public class CoinSummarySubFuturesAccount extends SubCoinStructure {

    /**
     * {@code subAccountList} list of subaccounts
     **/
    private final ArrayList<SummarySubFuturesAccountCoinItem> subAccountList;

    /**
     * Constructor to init {@link CoinSummarySubFuturesAccount} object
     *
     * @param totalMarginBalanceOfBTC: total margin balance of BTC of the coin summary of a sub futures account
     * @param totalUnrealizedProfitOfBTC: total unrealized profit of BTC coin summary of a sub futures account
     * @param totalWalletBalanceOfBTC: total wallet balance of BTC of the coin summary of a sub futures account
     * @param asset: asset of the coin summary of a sub futures account
     * @param subAccountList: list of subaccounts
     **/
    public CoinSummarySubFuturesAccount(double totalMarginBalanceOfBTC, double totalUnrealizedProfitOfBTC,
                                        double totalWalletBalanceOfBTC, String asset,
                                        ArrayList<SummarySubFuturesAccountCoinItem> subAccountList) {
        super(totalMarginBalanceOfBTC, totalUnrealizedProfitOfBTC, totalWalletBalanceOfBTC, asset);
        this.subAccountList = subAccountList;
    }

    /**
     * Constructor to init {@link CoinSummarySubFuturesAccount} object
     *
     * @param jCoinSummarySubFuturesAccount: coin summary of a sub futures account details as {@link JSONObject}
     **/
    public CoinSummarySubFuturesAccount(JSONObject jCoinSummarySubFuturesAccount) {
        super(jCoinSummarySubFuturesAccount);
        subAccountList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("subAccountList");
        if (jList != null)
            for (JSONObject item : jList)
                subAccountList.add(new SummarySubFuturesAccountCoinItem(item));
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link ArrayList} of {@link SummarySubFuturesAccountCoinItem}
     **/
    public ArrayList<SummarySubFuturesAccountCoinItem> getSubAccountList() {
        return subAccountList;
    }


    /**
     * The {@code SummarySubFuturesAccountCoinItem} class is useful to format a summary sub futures account coin item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SubCoinStructure
     **/
    public static class SummarySubFuturesAccountCoinItem extends SubCoinStructure {

        /**
         * {@code email} of the summary sub futures account coin item
         **/
        private final String email;

        /**
         * Constructor to init {@link SummarySubFuturesAccountCoinItem} object
         *
         * @param totalMarginBalanceOfBTC: total margin balance of BTC of the summary sub futures account coin item
         * @param totalUnrealizedProfitOfBTC: total unrealized profit of BTC summary sub futures account coin item
         * @param totalWalletBalanceOfBTC: total wallet balance of BTC of the summary sub futures account coin item
         * @param asset: asset of the coin summary sub futures account coin item
         * @param email: email of the summary sub futures account coin item
         **/
        public SummarySubFuturesAccountCoinItem(double totalMarginBalanceOfBTC, double totalUnrealizedProfitOfBTC,
                                                double totalWalletBalanceOfBTC, String asset, String email) {
            super(totalMarginBalanceOfBTC, totalUnrealizedProfitOfBTC, totalWalletBalanceOfBTC, asset);
            this.email = email;
        }

        /**
         * Constructor to init {@link SummarySubFuturesAccountCoinItem} object
         *
         * @param jSummarySubFuturesAccountCoinItem: summary sub futures account coin item details as {@link JSONObject}
         **/
        public SummarySubFuturesAccountCoinItem(JSONObject jSummarySubFuturesAccountCoinItem) {
            super(jSummarySubFuturesAccountCoinItem);
            email = hItem.getString("email");
        }

        /**
         * Method to get {@link #email} instance <br>
         * No-any params required
         *
         * @return {@link #email} instance as {@link String}
         * */
        public String getEmail() {
            return email;
        }

    }

}
