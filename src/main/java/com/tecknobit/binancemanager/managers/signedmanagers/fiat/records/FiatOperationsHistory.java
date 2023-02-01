package com.tecknobit.binancemanager.managers.signedmanagers.fiat.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceDataList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.fiat.records.FiatOperationsHistory.FiatOperation;

/**
 * The {@code FiatOperationsHistory} class is useful to format a fiat operations (Deposit/Withdraw) list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data">
 * Get Fiat Deposit/Withdraw History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceDataList
 * @see BinanceResponse
 **/
public class FiatOperationsHistory extends BinanceDataList<FiatOperation> {

    /**
     * Constructor to init {@link FiatOperationsHistory} object
     *
     * @param total:   number of items
     * @param success: whether the list is created with success
     * @param data:    {@link FiatOperationsHistory} of the list
     **/
    public FiatOperationsHistory(int total, boolean success, ArrayList<FiatOperation> data) {
        super(total, success, data);
    }

    /**
     * Constructor to init {@link FiatOperationsHistory} object
     *
     * @param jFiatOperations: fiat operations list details as {@link JSONObject}
     **/
    public FiatOperationsHistory(JSONObject jFiatOperations) {
        super(jFiatOperations);
        JSONArray jData = hItem.getJSONArray("data", new JSONArray());
        for (int j = 0; j < jData.length(); j++)
            data.add(new FiatOperation(jData.getJSONObject(j)));
    }

    /**
     * The {@code FiatOperation} class is useful to format a fiat operation (Deposit/Withdraw)
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see FiatItem
     **/
    public static class FiatOperation extends FiatItem {

        /**
         * {@code indicatedAmount} the indicated amount in the operation
         **/
        private final double indicatedAmount;

        /**
         * {@code amount} of the operation
         **/
        private final double amount;

        /**
         * {@code method} trade method
         **/
        private final String method;

        /**
         * Constructor to init {@link FiatItem} object
         *
         * @param orderNo:         order number
         * @param fiatCurrency:    fiat token
         * @param totalFee:        trade fee
         * @param status:          fiat item status
         * @param createTime:      creation time of the fiat item
         * @param updateTime:      update time of the fiat item
         * @param indicatedAmount: the indicated amount in the operation
         * @param amount:          amount of the operation
         * @param method:          trade method
         **/
        public FiatOperation(String orderNo, String fiatCurrency, double totalFee, FiatStatus status, long createTime,
                             long updateTime, double indicatedAmount, double amount, String method) {
            super(orderNo, fiatCurrency, totalFee, status, createTime, updateTime);
            this.indicatedAmount = indicatedAmount;
            this.amount = amount;
            this.method = method;
        }

        /**
         * Constructor to init {@link FiatOperation} object
         *
         * @param jFiatOperation: fiat operation details as {@link JSONObject}
         **/
        public FiatOperation(JSONObject jFiatOperation) {
            super(jFiatOperation);
            indicatedAmount = hItem.getDouble("indicatedAmount", 0);
            amount = hItem.getDouble("amount", 0);
            method = hItem.getString("method");
        }

        /**
         * Method to get {@link #indicatedAmount} instance <br>
         * No-any params required
         *
         * @return {@link #indicatedAmount} instance as double
         **/
        public double getIndicatedAmount() {
            return indicatedAmount;
        }

        /**
         * Method to get {@link #indicatedAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #indicatedAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getIndicatedAmount(int decimals) {
            return roundValue(indicatedAmount, decimals);
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         **/
        public double getAmount() {
            return amount;
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

        /**
         * Method to get {@link #method} instance <br>
         * No-any params required
         *
         * @return {@link #method} instance as {@link String}
         **/
        public String getMethod() {
            return method;
        }

    }

}
