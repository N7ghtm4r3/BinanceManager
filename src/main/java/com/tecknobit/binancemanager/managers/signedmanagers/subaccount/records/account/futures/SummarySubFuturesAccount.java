package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code SummarySubFuturesAccount} class is useful to format a summary sub futures account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
 * Get Detail on Sub-account's Futures Account (For Master Account)</a>
 * @see BinanceItem
 * @see SubFuturesAccountStructure
 */
public class SummarySubFuturesAccount extends SubFuturesAccountStructure {

    /**
     * {@code asset} of the summary sub futures account item
     */
    private final String asset;

    /**
     * {@code subAccountList} subaccount list of the summary sub futures account item
     */
    private final ArrayList<SummarySubFuturesAccountItem> subAccountList;

    /**
     * Constructor to init {@link SubFuturesAccountStructure} object
     *
     * @param totalInitialMargin: total initial margin of the summary sub futures account item
     * @param totalMaintenanceMargin: total maintenance margin of the summary sub futures account item
     * @param totalMarginBalance: total margin balance of the summary sub futures account item
     * @param totalOpenOrderInitialMargin: total open order initial margin of the summary sub futures account item
     * @param totalPositionInitialMargin: total position initial margin of the summary sub futures account item
     * @param totalUnrealizedProfit: total unrealized profit of the summary sub futures account item
     * @param totalWalletBalance: total wallet balance of the summary sub futures account item
     * @param asset: asset of the summary sub futures account item
     * @param subAccountList: subaccount list of the summary sub futures account item
     */
    public SummarySubFuturesAccount(double totalInitialMargin, double totalMaintenanceMargin, double totalMarginBalance,
                                    double totalOpenOrderInitialMargin, double totalPositionInitialMargin,
                                    double totalUnrealizedProfit, double totalWalletBalance, String asset,
                                    ArrayList<SummarySubFuturesAccountItem> subAccountList) {
        super(totalInitialMargin, totalMaintenanceMargin, totalMarginBalance, totalOpenOrderInitialMargin,
                totalPositionInitialMargin, totalUnrealizedProfit, totalWalletBalance);
        this.asset = asset;
        this.subAccountList = subAccountList;
    }

    /**
     * Constructor to init {@link SubFuturesAccountStructure} object
     *
     * @param jSummarySubFuturesAccount: summary sub futures account details as {@link JSONObject}
     */
    public SummarySubFuturesAccount(JSONObject jSummarySubFuturesAccount) {
        super(jSummarySubFuturesAccount);
        asset = hItem.getString("asset");
        subAccountList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("subAccountList");
        if (jList != null)
            for (JSONObject jItem : jList)
                subAccountList.add(new SummarySubFuturesAccountItem(jItem));
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #subAccountList} instance <br>
     * No-any params required
     *
     * @return {@link #subAccountList} instance as {@link ArrayList} of {@link SummarySubFuturesAccountItem}
     */
    public ArrayList<SummarySubFuturesAccountItem> getSubAccountList() {
        return subAccountList;
    }

    /**
     * The {@code SummarySubFuturesAccountItem} class is useful to format a summary sub futures account item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see SubFuturesAccountStructure
     */
    public static class SummarySubFuturesAccountItem extends SubFuturesAccountStructure {

        /**
         * {@code email} of the summary sub futures account item
         */
        private final String email;

        /**
         * {@code asset} of the summary sub futures account item
         */
        private final String asset;

        /**
         * Constructor to init {@link SummarySubFuturesAccountItem} object
         *
         * @param totalInitialMargin: total initial margin of the summary sub futures account item
         * @param totalMaintenanceMargin: total maintenance margin of the summary sub futures account item
         * @param totalMarginBalance: total margin balance of the summary sub futures account item
         * @param totalOpenOrderInitialMargin: total open order initial margin of the summary sub futures account item
         * @param totalPositionInitialMargin: total position initial margin of the summary sub futures account item
         * @param totalUnrealizedProfit: total unrealized profit of the summary sub futures account item
         * @param totalWalletBalance: total wallet balance of the summary sub futures account item
         * @param email: email of the summary sub futures account item
         * @param asset: asset of the summary sub futures account item
         */
        public SummarySubFuturesAccountItem(double totalInitialMargin, double totalMaintenanceMargin,
                                            double totalMarginBalance, double totalOpenOrderInitialMargin,
                                            double totalPositionInitialMargin, double totalUnrealizedProfit,
                                            double totalWalletBalance, String email, String asset) {
            super(totalInitialMargin, totalMaintenanceMargin, totalMarginBalance, totalOpenOrderInitialMargin,
                    totalPositionInitialMargin, totalUnrealizedProfit, totalWalletBalance);
            this.email = email;
            this.asset = asset;
        }

        /**
         * Constructor to init {@link SummarySubFuturesAccountItem} object
         *
         * @param jSummarySubFuturesAccountItem: summary sub futures account item details as {@link JSONObject}
         */
        public SummarySubFuturesAccountItem(JSONObject jSummarySubFuturesAccountItem) {
            super(jSummarySubFuturesAccountItem);
            email = hItem.getString("email");
            asset = hItem.getString("asset");
        }

        /**
         * Method to get {@link #email} instance <br>
         * No-any params required
         *
         * @return {@link #email} instance as {@link String}
         */
        public String getEmail() {
            return email;
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
        }

    }

}
