package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.convert.BUSDConvert;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.PaymentRefundHistory.PaymentRefund;

/**
 * The {@code PaymentRefundHistory} class is useful to format a {@code "Binance"}'s cloud mining payment and refund history list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cloud-mining-payment-and-refund-history-user_data">
 * Get Cloud-Mining payment and refund history (USER_DATA)</a>
 * @see BinanceRowsList
 */
public class PaymentRefundHistory extends BinanceRowsList<PaymentRefund> {

    /**
     * Constructor to init {@link PaymentRefundHistory} object
     *
     * @param total : number of payment / refund
     * @param rows  :  list of the payment / refund
     */
    public PaymentRefundHistory(int total, ArrayList<PaymentRefund> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link PaymentRefundHistory}
     *
     * @param jList : payment and refund history details as {@link JSONObject}
     */
    public PaymentRefundHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new PaymentRefund((JSONObject) row));
    }

    /**
     * The {@code PaymentRefund} class is useful to format a {@code "Binance"}'s payment / refund record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BUSDConvert
     */
    public static class PaymentRefund extends BUSDConvert {

        /**
         * {@code createTime} create time of the payment / refund record
         */
        private final long createTime;

        /**
         * {@code type} type of the payment / refund record
         */
        private final int type;

        /**
         * {@code asset} asset of the payment / refund record
         */
        private final String asset;

        /**
         * {@code amount} amount of the payment / refund record
         */
        private final double amount;

        /**
         * Constructor to init {@link BUSDConvert} object
         *
         * @param tranId      :  transaction id
         * @param status      :  status of the transaction
         * @param createTime: create time of the payment / refund record
         * @param type:       type of the payment / refund record
         * @param asset:      asset of the payment / refund record
         * @param amount:     amount of the payment / refund record
         */
        public PaymentRefund(long tranId, String status, long createTime, int type, String asset, double amount) {
            super(tranId, status);
            this.createTime = createTime;
            this.type = type;
            this.asset = asset;
            this.amount = amount;
        }

        /**
         * Constructor to init {@link BUSDConvert} object
         *
         * @param jConvert :  BUSD convert details as {@link JSONObject}
         */
        public PaymentRefund(JSONObject jConvert) {
            super(jConvert);
            createTime = jConvert.getLong("createTime");
            type = jConvert.getInt("type");
            asset = jConvert.getString("asset");
            amount = jConvert.getDouble("amount");
        }

        /**
         * Method to get {@link #createTime} instance <br>
         * No-any params required
         *
         * @return {@link #createTime} instance as long
         */
        public long getCreateTime() {
            return createTime;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         */
        public int getType() {
            return type;
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

    }

}
