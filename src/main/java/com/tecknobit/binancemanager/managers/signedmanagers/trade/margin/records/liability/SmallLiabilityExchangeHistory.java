package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.liability;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.liability.SmallLiabilityExchangeHistory.SmallLiabilityHistoryItem;

/**
 * The {@code SmallLiabilityExchangeHistory} class is useful to format a {@code "Binance"}'s small liability exchange history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-history-user_data">
 * Get Small Liability Exchange History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class SmallLiabilityExchangeHistory extends BinanceRowsList<SmallLiabilityHistoryItem> {

    /**
     * Constructor to init {@link SmallLiabilityExchangeHistory} object
     *
     * @param total                      : number of the small liability history items
     * @param smallLiabilityHistoryItems :  list of the small liability history items
     **/
    public SmallLiabilityExchangeHistory(int total, ArrayList<SmallLiabilityHistoryItem> smallLiabilityHistoryItems) {
        super(total, smallLiabilityHistoryItems);
    }

    /**
     * Constructor to init {@link SmallLiabilityExchangeHistory}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public SmallLiabilityExchangeHistory(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new SmallLiabilityHistoryItem((JSONObject) row));
    }

    /**
     * The {@code SmallLiabilityHistoryItem} class is useful to format a {@code "Binance"}'s small liability exchange history item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class SmallLiabilityHistoryItem extends BinanceItem {

        /**
         * {@code asset} of the history item
         **/
        private final String asset;

        /**
         * {@code amount} of the history item
         **/
        private final double amount;

        /**
         * {@code targetAsset} target asset of the history item
         **/
        private final String targetAsset;

        /**
         * {@code targetAmount} target amount of the history item
         **/
        private final double targetAmount;

        /**
         * {@code bizType} biz type of the history item
         **/
        private final String bizType;

        /**
         * {@code timestamp} of the history item
         **/
        private final long timestamp;

        /**
         * Constructor to init {@link SmallLiabilityExchangeHistory}
         *
         * @param asset        : asset of the history item
         * @param amount       : amount of the history item
         * @param targetAsset  : target asset of the history item
         * @param targetAmount : target amount of the history item
         * @param bizType      : biz type of the history item
         * @param timestamp    : timestamp of the history item
         **/
        public SmallLiabilityHistoryItem(String asset, double amount, String targetAsset, double targetAmount,
                                         String bizType, long timestamp) {
            super(null);
            this.asset = asset;
            this.amount = amount;
            this.targetAsset = targetAsset;
            this.targetAmount = targetAmount;
            this.bizType = bizType;
            this.timestamp = timestamp;
        }

        /**
         * Constructor to init {@link SmallLiabilityExchangeHistory}
         *
         * @param jSmallLiabilityHistoryItem : small liability exchange history item details as {@link JSONObject}
         **/
        public SmallLiabilityHistoryItem(JSONObject jSmallLiabilityHistoryItem) {
            super(jSmallLiabilityHistoryItem);
            asset = hItem.getString("asset");
            amount = hItem.getDouble("amount", 0);
            targetAsset = hItem.getString("targetAsset");
            targetAmount = hItem.getDouble("targetAmount", 0);
            bizType = hItem.getString("bizType");
            timestamp = hItem.getLong("timestamp", 0);
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         **/
        public String getAsset() {
            return asset;
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
         * Method to get {@link #bizType} instance <br>
         * No-any params required
         *
         * @return {@link #bizType} instance as {@link String}
         **/
        public String getBizType() {
            return bizType;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as long
         **/
        public long getTimestamp() {
            return timestamp;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as {@link Date}
         **/
        public Date getDate() {
            return TimeFormatter.getDate(timestamp);
        }

    }

}
