package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LoansList} class is useful to format a {@code "Binance"}'s margin query loan
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
 * Query Loan Record (USER_DATA)</a>
 * @see BinanceRowsList
 **/
public class LoansList extends BinanceRowsList<LoansList.Loan> {

    /**
     * Constructor to init {@link LoansList} object
     *
     * @param total:          total size of loans
     * @param loanAssetsList: list of {@link Loan}
     **/
    public LoansList(int total, ArrayList<Loan> loanAssetsList) {
        super(total, loanAssetsList);
    }

    /**
     * Constructor to init {@link LoansList} object
     *
     * @param jLoansList: loan details as {@link JSONObject}
     **/
    public LoansList(JSONObject jLoansList) {
        super(jLoansList);
        JSONArray jLoans = hItem.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jLoans.length(); j++)
            rows.add(new Loan(jLoans.getJSONObject(j)));
    }

    /**
     * The {@code Loan} class is useful to format a loan
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see MarginListItem
     **/
    public static class Loan extends MarginListItem {

        /**
         * {@code isolatedSymbol} is instance that memorizes isolated symbol of the asset
         **/
        private final String isolatedSymbol;

        /**
         * {@code principal} is instance that memorizes principal value
         **/
        private double principal;

        /**
         * Constructor to init {@link Loan} object
         *
         * @param asset:          asset
         * @param txId:           identifier of transaction
         * @param timestamp:      timestamp of transaction
         * @param status:         status of transaction
         * @param isolatedSymbol: symbol of the asset
         * @param principal:      principal value
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Loan(String asset, long txId, long timestamp, Status status, String isolatedSymbol,
                    double principal) {
            super(asset, txId, timestamp, status);
            this.isolatedSymbol = isolatedSymbol;
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            else
                this.principal = principal;
        }

        /**
         * Constructor to init {@link Loan} object
         *
         * @param loanAsset: loan asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Loan(JSONObject loanAsset) {
            super(loanAsset);
            isolatedSymbol = loanAsset.getString("isolatedSymbol");
            principal = loanAsset.getDouble("principal");
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
        }

        /**
         * Method to get {@link #isolatedSymbol} instance <br>
         * No-any params required
         *
         * @return {@link #isolatedSymbol} instance as {@link String}
         **/
        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        /**
         * Method to get {@link #principal} instance <br>
         * No-any params required
         *
         * @return {@link #principal} instance as double
         **/
        public double getPrincipal() {
            return principal;
        }

        /**
         * Method to set {@link #principal}
         *
         * @param principal: principal value
         * @throws IllegalArgumentException when principal value is less than 0
         **/
        public void setPrincipal(double principal) {
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            this.principal = principal;
        }

        /**
         * Method to get {@link #principal} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #principal} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPrincipal(int decimals) {
            return roundValue(principal, decimals);
        }

    }

}
