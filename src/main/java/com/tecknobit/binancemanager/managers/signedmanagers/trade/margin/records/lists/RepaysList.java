package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.binancemanager.managers.records.BinanceList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists.LoansList.Loan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code RepaysList} class is useful to format a {@code "Binance"}'s margin query repay record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
 * Query Repay Record (USER_DATA)</a>
 * @see BinanceList
 **/
public class RepaysList extends BinanceList<RepaysList.Repay> {

    /**
     * Constructor to init {@link RepaysList} object
     *
     * @param total:                 total size of repays
     * @param marginRepayAssetsList: list of {@link Repay}
     **/
    public RepaysList(int total, ArrayList<Repay> marginRepayAssetsList) {
        super(total, marginRepayAssetsList);
    }

    /**
     * Constructor to init {@link RepaysList} object
     *
     * @param jRepaysList: repay details as {@link JSONObject}
     **/
    public RepaysList(JSONObject jRepaysList) {
        super(jRepaysList);
        JSONArray jRepays = hItem.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jRepays.length(); j++)
            rows.add(new Repay(jRepays.getJSONObject(j)));
    }

    /**
     * The {@code Repay} class is useful to format a re-pay
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see MarginListItem
     * @see Loan
     **/
    public static class Repay extends Loan {

        /**
         * {@code amount} is instance that memorizes amount of asset
         **/
        private double amount;

        /**
         * {@code interest} is instance that memorizes interest on the asset
         **/
        private double interest;

        /**
         * Constructor to init {@link Repay} object
         *
         * @param asset:          asset
         * @param txId:           identifier of transaction
         * @param timestamp:      timestamp of transaction
         * @param status:         status of transaction
         * @param isolatedSymbol: symbol of the asset
         * @param principal:      principal value
         * @param amount:         amount of asset
         * @param interest:       interest on the asset
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Repay(String asset, long txId, long timestamp, Status status, String isolatedSymbol,
                     double principal, double amount, double interest) {
            super(asset, txId, timestamp, status, isolatedSymbol, principal);
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            else
                this.amount = amount;
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
        }

        /**
         * Constructor to init {@link Repay} object
         *
         * @param repayAsset: margin repay asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Repay(JSONObject repayAsset) {
            super(repayAsset);
            amount = repayAsset.getDouble("amount");
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            interest = repayAsset.getDouble("interest");
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
        }

        public double getAmount() {
            return amount;
        }

        /**
         * Method to set {@link #amount}
         *
         * @param amount: amount of asset
         * @throws IllegalArgumentException when amount value is less than 0
         **/
        public void setAmount(double amount) {
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        public double getInterest() {
            return interest;
        }

        /**
         * Method to set {@link #interest}
         *
         * @param interest: interest on the asset
         * @throws IllegalArgumentException when interest value is less than 0
         **/
        public void setInterest(double interest) {
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

        /**
         * Method to get {@link #interest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getInterest(int decimals) {
            return roundValue(interest, decimals);
        }

    }

}
