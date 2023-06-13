package com.tecknobit.binancemanager.managers.signedmanagers.pay.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceDataList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.pay.records.PayTradeHistory.PayTrade;

/**
 * The {@code PayTradeHistory} class is useful to format a pay trade history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pay-trade-history-user_data">
 * Get Pay Trade History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceDataList
 * @see BinanceResponse
 */
public class PayTradeHistory extends BinanceDataList<PayTrade> {

    /**
     * Constructor to init {@link PayTradeHistory} object
     *
     * @param total   : number of items
     * @param success :  whether the list is created with success
     * @param data    :  {@link PayTrade} in the list
     */
    public PayTradeHistory(int total, boolean success, ArrayList<PayTrade> data) {
        super(total, success, data);
    }

    /**
     * Constructor to init {@link PayTradeHistory} object
     *
     * @param jPayTradeHistory : pay trade history details as {@link JSONObject}
     */
    public PayTradeHistory(JSONObject jPayTradeHistory) {
        super(jPayTradeHistory);
        JSONArray jData = hItem.getJSONArray("data", new JSONArray());
        for (int j = 0; j < jData.length(); j++)
            data.add(new PayTrade(jData.getJSONObject(j)));
    }

    /**
     * The {@code PayTrade} class is useful to format a pay trade
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class PayTrade extends BinanceItem {

        /**
         * {@code PayTradeType} list of available pay trade types
         */
        public enum PayTradeType {

            /**
             * {@code PAY} C2B Merchant Acquiring Payment
             */
            PAY,

            /**
             * {@code PAY_REFUND} C2B Merchant Acquiring Payment,refund
             */
            PAY_REFUND,

            /**
             * {@code C2C} C2C Transfer Payment
             */
            C2C,

            /**
             * {@code CRYPTO_BOX} Crypto Box, refund
             */
            CRYPTO_BOX,

            /**
             * {@code CRYPTO_BOX_RF} C2B Merchant Acquiring Payment
             */
            CRYPTO_BOX_RF,

            /**
             * {@code C2C_HOLDING} Transfer to new Binance user
             */
            C2C_HOLDING,

            /**
             * {@code C2C_HOLDING_RF} Transfer to new Binance user,refund
             */
            C2C_HOLDING_RF,

            /**
             * {@code PAYOUT} B2C Disbursement Payment
             */
            PAYOUT

        }

        /**
         * {@code orderType} order type of the pay trade
         */
        private final PayTradeType orderType;

        /**
         * {@code transactionId} transaction id of the pay trade
         */
        private final String transactionId;

        /**
         * {@code transactionTime} transaction time of the pay trade
         */
        private final long transactionTime;

        /**
         * {@code amount} order amount(up to 8 decimal places), positive is income, negative is expenditure
         */
        private final double amount;

        /**
         * {@code currency} used in the pay trade
         */
        private final String currency;

        /**
         * {@code walletType} wallet type used int the pay trade
         */
        private final int walletType;

        /**
         * {@code fundsDetail} list of {@link FundDetail}
         */
        private final ArrayList<FundDetail> fundsDetail;

        /**
         * Constructor to init {@link PayTrade} object
         *
         * @param orderType       : order type of the pay trade
         * @param transactionId   : transaction id of the pay trade
         * @param transactionTime : transaction time of the pay trade
         * @param amount          : order amount(up to 8 decimal places), positive is income, negative is expenditure
         * @param currency        : currency used in the pay trade
         * @param walletType      : wallet type used int the pay trade
         * @param fundsDetail     : list of {@link FundDetail}
         */
        public PayTrade(PayTradeType orderType, String transactionId, long transactionTime, double amount, String currency,
                        int walletType, ArrayList<FundDetail> fundsDetail) {
            super(null);
            this.orderType = orderType;
            this.transactionId = transactionId;
            this.transactionTime = transactionTime;
            this.amount = amount;
            this.currency = currency;
            this.walletType = walletType;
            this.fundsDetail = fundsDetail;
        }

        /**
         * Constructor to init {@link PayTrade} object
         *
         * @param jPayTrade : pay trade details as {@link JSONObject}
         */
        public PayTrade(JSONObject jPayTrade) {
            super(jPayTrade);
            orderType = PayTradeType.valueOf(hItem.getString("orderType"));
            transactionId = hItem.getString("transactionId");
            transactionTime = hItem.getLong("transactionTime", 0);
            amount = hItem.getDouble("amount", 0);
            currency = hItem.getString("currency");
            walletType = hItem.getInt("walletType");
            fundsDetail = new ArrayList<>();
            JSONArray jFunds = hItem.getJSONArray("fundsDetail", new JSONArray());
            for (int j = 0; j < jFunds.length(); j++)
                fundsDetail.add(new FundDetail(jFunds.getJSONObject(j)));
        }

        /**
         * Method to get {@link #orderType} instance <br>
         * No-any params required
         *
         * @return {@link #orderType} instance as {@link PayTradeType}
         */
        public PayTradeType getOrderType() {
            return orderType;
        }

        /**
         * Method to get {@link #transactionId} instance <br>
         * No-any params required
         *
         * @return {@link #transactionId} instance as {@link String}
         */
        public String getTransactionId() {
            return transactionId;
        }

        /**
         * Method to get {@link #transactionTime} instance <br>
         * No-any params required
         *
         * @return {@link #transactionTime} instance as long
         */
        public long getTransactionTime() {
            return transactionTime;
        }

        /**
         * Method to get {@link #transactionTime} instance <br>
         * No-any params required
         *
         * @return {@link #transactionTime} instance as {@link Date}
         */
        public Date getTransactionDate() {
            return TimeFormatter.getDate(transactionTime);
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #currency} instance <br>
         * No-any params required
         *
         * @return {@link #currency} instance as {@link String}
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Method to get {@link #walletType} instance <br>
         * No-any params required
         *
         * @return {@link #walletType} instance as int
         */
        public int getWalletType() {
            return walletType;
        }

        /**
         * Method to get {@link #fundsDetail} instance <br>
         * No-any params required
         *
         * @return {@link #fundsDetail} instance as {@link ArrayList} of {@link FundDetail}
         */
        public ArrayList<FundDetail> getFundsDetail() {
            return fundsDetail;
        }

        /**
         * The {@code FundDetail} class is useful to format a fund detail
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         */
        public static class FundDetail extends BinanceItem {

            /**
             * {@code currency} asset of fund detail
             */
            private final String currency;

            /**
             * {@code amount} of fund detail
             */
            private final double amount;

            /**
             * {@code walletAssetCost} details of asset cost per wallet
             */
            private final ArrayList<Double> walletAssetCost;

            /**
             * Constructor to init {@link FundDetail} object
             *
             * @param currency         : asset of fund detail
             * @param amount           : amount of fund detail
             * @param walletAssetCost: details of asset cost per wallet
             */
            public FundDetail(String currency, double amount, ArrayList<Double> walletAssetCost) {
                super(null);
                this.currency = currency;
                this.amount = amount;
                this.walletAssetCost = walletAssetCost;
            }

            /**
             * Constructor to init {@link FundDetail} object
             *
             * @param jFundDetail : fund details as {@link JSONObject}
             */
            public FundDetail(JSONObject jFundDetail) {
                super(jFundDetail);
                currency = hItem.getString("currency");
                amount = hItem.getDouble("amount", 0);
                walletAssetCost = new ArrayList<>();
                ArrayList<JSONObject> jCosts = hItem.fetchList("walletAssetCost");
                for (int j = 0; j < jCosts.size(); j++)
                    walletAssetCost.add(jCosts.get(j).getDouble("" + (j + 1)));
            }

            /**
             * Method to get {@link #currency} instance <br>
             * No-any params required
             *
             * @return {@link #currency} instance as {@link String}
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * Method to get {@link #amount} instance <br>
             * No-any params required
             *
             * @return {@link #amount} instance as double
             */
            public double getAmount() {
                return amount;
            }

            /**
             * Method to get {@link #amount} instance
             *
             * @param decimals: number of digits to round final value
             * @return {@link #amount} instance rounded with decimal digits inserted
             * @throws IllegalArgumentException if decimalDigits is negative
             */
            public double getAmount(int decimals) {
                return roundValue(amount, decimals);
            }

            /**
             * Method to get {@link #walletAssetCost} instance <br>
             * No-any params required
             *
             * @return {@link #walletAssetCost} instance as {@link ArrayList} of {@link Double}
             */
            public ArrayList<Double> getWalletAssetCost() {
                return walletAssetCost;
            }

        }

    }

}
