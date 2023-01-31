package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.convert;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.convert.ConvertHistory.Convert;

/**
 * The {@code ConvertHistory} class is useful to format a {@code "Binance"}'s convert history list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#busd-convert-history-user_data">
 * BUSD Convert History (USER_DATA)</a>
 * @see BinanceList
 **/
public class ConvertHistory extends BinanceList<Convert> {

    /**
     * Constructor to init {@link ConvertHistory} object
     *
     * @param total    : number of converts
     * @param converts :  list of the converts
     **/
    public ConvertHistory(int total, ArrayList<Convert> converts) {
        super(total, converts);
    }

    /**
     * Constructor to init {@link ConvertHistory}
     *
     * @param jConvertHistory : convert history details as {@link JSONObject}
     **/
    public ConvertHistory(JSONObject jConvertHistory) {
        super(jConvertHistory);
        JSONArray jConvert = jConvertHistory.getJSONArray("rows");
        for (int j = 0; j < jConvert.length(); j++)
            rows.add(new Convert(jConvert.getJSONObject(j)));
    }

    /**
     * The {@code Convert} class is useful to format a {@code "Binance"}'s convert record
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BUSDConvert
     **/
    public static class Convert extends BUSDConvert {

        /**
         * {@code type} type of the convert
         **/
        private final int type;

        /**
         * {@code time} time of the convert
         **/
        private final long time;

        /**
         * {@code deductedAsset} deducted asset of the convert
         **/
        private final String deductedAsset;

        /**
         * {@code deductedAmount} deducted amount of the convert
         **/
        private final double deductedAmount;

        /**
         * {@code targetAsset} target asset of the convert
         **/
        private final String targetAsset;

        /**
         * {@code targetAmount} target amount of the convert
         **/
        private final double targetAmount;

        /**
         * {@code accountType} account type of the convert
         **/
        private final Type accountType;

        /**
         * Constructor to init {@link Convert} object
         *
         * @param tranId             :  of the convert
         * @param status             :  status of the conversion
         * @param type:              type of the convert
         * @param time:              time of the convert
         * @param deductedAsset:     deducted asset of the convert
         * @param deductedAmount:    deducted amount of the convert
         * @param targetAsset:target asset of the convert
         * @param targetAmount:      target amount of the convert
         * @param accountType:       account type of the convert
         **/
        public Convert(long tranId, String status, int type, long time, String deductedAsset, double deductedAmount,
                       String targetAsset, double targetAmount, Type accountType) {
            super(tranId, status);
            this.type = type;
            this.time = time;
            this.deductedAsset = deductedAsset;
            this.deductedAmount = deductedAmount;
            this.targetAsset = targetAsset;
            this.targetAmount = targetAmount;
            this.accountType = accountType;
        }

        /**
         * Constructor to init {@link Convert} object
         *
         * @param jConvert : convert details as {@link JSONObject}
         **/
        public Convert(JSONObject jConvert) {
            super(jConvert);
            type = jConvert.getInt("type");
            time = jConvert.getLong("time");
            deductedAsset = jConvert.getString("deductedAsset");
            deductedAmount = jConvert.getDouble("deductedAmount");
            targetAsset = jConvert.getString("targetAsset");
            targetAmount = jConvert.getDouble("targetAmount");
            accountType = Type.valueOf(jConvert.getString("accountType"));
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link int}
         **/
        public int getType() {
            return type;
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as long
         **/
        public long getTime() {
            return time;
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as {@link Date}
         **/
        public Date getDate() {
            return TimeFormatter.getDate(time);
        }

        /**
         * Method to get {@link #deductedAsset} instance <br>
         * No-any params required
         *
         * @return {@link #deductedAsset} instance as {@link String}
         **/
        public String getDeductedAsset() {
            return deductedAsset;
        }

        /**
         * Method to get {@link #deductedAmount} instance <br>
         * No-any params required
         *
         * @return {@link #deductedAmount} instance as double
         **/
        public double getDeductedAmount() {
            return deductedAmount;
        }

        /**
         * Method to get {@link #deductedAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #deductedAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getDeductedAmount(int decimals) {
            return roundValue(deductedAmount, decimals);
        }

        /**
         * Method to get {@link #targetAsset} instance <br>
         * No-any params required
         *
         * @return {@link #targetAsset} instance as {@link String}
         **/
        public String getTargetAsset() {
            return targetAsset;
        }

        /**
         * Method to get {@link #targetAmount} instance <br>
         * No-any params required
         *
         * @return {@link #targetAmount} instance as double
         **/
        public double getTargetAmount() {
            return targetAmount;
        }

        /**
         * Method to get {@link #targetAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #targetAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTargetAmount(int decimals) {
            return roundValue(targetAmount, decimals);
        }

        /**
         * Method to get {@link #accountType} instance <br>
         * No-any params required
         *
         * @return {@link #accountType} instance as {@link Type}
         **/
        public Type getAccountType() {
            return accountType;
        }

    }

}
