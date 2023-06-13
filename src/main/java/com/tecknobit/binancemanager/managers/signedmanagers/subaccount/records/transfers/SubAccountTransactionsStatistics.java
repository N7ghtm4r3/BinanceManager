package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubAccountTransactionsStatistics} class is useful to format a subaccount transactions statistics
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-transaction-tatistics-for-master-account-user_data">
 * Query Sub-account Transaction Statistics (For Master Account) (USER_DATA)</a>
 * @see BinanceItem
 */
public class SubAccountTransactionsStatistics extends BinanceItem {

    /**
     * {@code recent30BtcTotal} recent 30 btc total of the subaccount transactions statistics
     */
    private final double recent30BtcTotal;

    /**
     * {@code recent30BtcFuturesTotal} recent 30 btc futures total of the subaccount transactions statistics
     */
    private final double recent30BtcFuturesTotal;

    /**
     * {@code recent30BtcMarginTotal} recent 30 btc margin total of the subaccount transactions statistics
     */
    private final double recent30BtcMarginTotal;

    /**
     * {@code recent30BusdTotal} recent 30 busd total of the subaccount transactions statistics
     */
    private final double recent30BusdTotal;

    /**
     * {@code recent30BusdFuturesTotal} recent 30 busd futures total of the subaccount transactions statistics
     */
    private final double recent30BusdFuturesTotal;

    /**
     * {@code recent30BusdMarginTotal} recent 30 busd margin total of the subaccount transactions statistics
     */
    private final double recent30BusdMarginTotal;

    /**
     * {@code tradeInfoVos} trade info vos of the subaccount transactions statistics
     */
    private final ArrayList<SubTradeInfo> tradeInfoVos;

    /**
     * Constructor to init {@link SubAccountTransactionsStatistics} object
     *
     * @param recent30BtcTotal     : recent 30 btc total of the subaccount transactions statistics
     * @param recent30BtcFuturesTotal : recent 30 btc futures total of the subaccount transactions statistics
     * @param recent30BtcMarginTotal     : recent 30 btc margin total of the subaccount transactions statistics
     * @param recent30BusdTotal     : recent 30 busd total of the subaccount transactions statistics
     * @param recent30BusdFuturesTotal :  recent 30 busd futures total of the subaccount transactions statistics
     * @param recent30BusdMarginTotal     : recent 30 busd margin total of the subaccount transactions statistics
     * @param tradeInfoVos     : trade info vos of the subaccount transactions statistics
     */
    public SubAccountTransactionsStatistics(double recent30BtcTotal, double recent30BtcFuturesTotal,
                                            double recent30BtcMarginTotal, double recent30BusdTotal,
                                            double recent30BusdFuturesTotal, double recent30BusdMarginTotal,
                                            ArrayList<SubTradeInfo> tradeInfoVos) {
        super(null);
        this.recent30BtcTotal = recent30BtcTotal;
        this.recent30BtcFuturesTotal = recent30BtcFuturesTotal;
        this.recent30BtcMarginTotal = recent30BtcMarginTotal;
        this.recent30BusdTotal = recent30BusdTotal;
        this.recent30BusdFuturesTotal = recent30BusdFuturesTotal;
        this.recent30BusdMarginTotal = recent30BusdMarginTotal;
        this.tradeInfoVos = tradeInfoVos;
    }

    /**
     * Constructor to init {@link SubAccountTransactionsStatistics} object
     *
     * @param jSubAccountTransactionsStatistics: subaccount transactions statistics details as {@link JSONObject}
     */
    public SubAccountTransactionsStatistics(JSONObject jSubAccountTransactionsStatistics) {
        super(jSubAccountTransactionsStatistics);
        recent30BtcTotal = hItem.getDouble("recent30BtcTotal", 0);
        recent30BtcFuturesTotal = hItem.getDouble("recent30BtcFuturesTotal", 0);
        recent30BtcMarginTotal = hItem.getDouble("recent30BtcMarginTotal", 0);
        recent30BusdTotal = hItem.getDouble("recent30BusdTotal", 0);
        recent30BusdFuturesTotal = hItem.getDouble("recent30BusdFuturesTotal", 0);
        recent30BusdMarginTotal = hItem.getDouble("recent30BusdMarginTotal", 0);
        tradeInfoVos = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("tradeInfoVos");
        if (jList != null)
            for (JSONObject item : jList)
                tradeInfoVos.add(new SubTradeInfo(item));
    }

    /**
     * Method to get {@link #recent30BtcTotal} instance <br>
     * No-any params required
     *
     * @return {@link #recent30BtcTotal} instance as double
     */
    public double getRecent30BtcTotal() {
        return recent30BtcTotal;
    }

    /**
     * Method to get {@link #recent30BtcTotal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #recent30BtcTotal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRecent30BtcTotal(int decimals) {
        return roundValue(recent30BtcTotal, decimals);
    }

    /**
     * Method to get {@link #recent30BtcFuturesTotal} instance <br>
     * No-any params required
     *
     * @return {@link #recent30BtcFuturesTotal} instance as double
     */
    public double getRecent30BtcFuturesTotal() {
        return recent30BtcFuturesTotal;
    }

    /**
     * Method to get {@link #recent30BtcFuturesTotal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #recent30BtcFuturesTotal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRecent30BtcFuturesTotal(int decimals) {
        return roundValue(recent30BtcFuturesTotal, decimals);
    }

    /**
     * Method to get {@link #recent30BtcMarginTotal} instance <br>
     * No-any params required
     *
     * @return {@link #recent30BtcMarginTotal} instance as double
     */
    public double getRecent30BtcMarginTotal() {
        return recent30BtcMarginTotal;
    }

    /**
     * Method to get {@link #recent30BtcMarginTotal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #recent30BtcMarginTotal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRecent30BtcMarginTotal(int decimals) {
        return roundValue(recent30BtcMarginTotal, decimals);
    }

    /**
     * Method to get {@link #recent30BusdTotal} instance <br>
     * No-any params required
     *
     * @return {@link #recent30BusdTotal} instance as double
     */
    public double getRecent30BusdTotal() {
        return recent30BusdTotal;
    }

    /**
     * Method to get {@link #recent30BusdTotal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #recent30BusdTotal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRecent30BusdTotal(int decimals) {
        return roundValue(recent30BusdTotal, decimals);
    }

    /**
     * Method to get {@link #recent30BusdFuturesTotal} instance <br>
     * No-any params required
     *
     * @return {@link #recent30BusdFuturesTotal} instance as double
     */
    public double getRecent30BusdFuturesTotal() {
        return recent30BusdFuturesTotal;
    }

    /**
     * Method to get {@link #recent30BusdFuturesTotal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #recent30BusdFuturesTotal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRecent30BusdFuturesTotal(int decimals) {
        return roundValue(recent30BusdFuturesTotal, decimals);
    }

    /**
     * Method to get {@link #recent30BusdMarginTotal} instance <br>
     * No-any params required
     *
     * @return {@link #recent30BusdMarginTotal} instance as double
     */
    public double getRecent30BusdMarginTotal() {
        return recent30BusdMarginTotal;
    }

    /**
     * Method to get {@link #recent30BusdMarginTotal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #recent30BusdMarginTotal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRecent30BusdMarginTotal(int decimals) {
        return roundValue(recent30BusdMarginTotal, decimals);
    }

    /**
     * Method to get {@link #tradeInfoVos} instance <br>
     * No-any params required
     *
     * @return {@link #tradeInfoVos} instance as {@link ArrayList} of {@link SubTradeInfo}
     */
    public ArrayList<SubTradeInfo> getTradeInfoVos() {
        return tradeInfoVos;
    }

    /**
     * The {@code SubTradeInfo} class is useful to format a sub trade info
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class SubTradeInfo extends BinanceItem {

        /**
         * {@code userId} user id of the sub trade info
         */
        private final long userId;

        /**
         * {@code btc} of the sub trade info
         */
        private final double btc;

        /**
         * {@code btcFutures} btc futures of the sub trade info
         */
        private final double btcFutures;

        /**
         * {@code btcMargin} btc margin of the sub trade info
         */
        private final double btcMargin;

        /**
         * {@code busd} of the sub trade info
         */
        private final double busd;

        /**
         * {@code busdFutures} busd futures of the sub trade info
         */
        private final double busdFutures;

        /**
         * {@code busdMargin} busd margin of the sub trade info
         */
        private final double busdMargin;

        /**
         * {@code date} of the sub trade info
         */
        private final long date;

        /**
         * Constructor to init {@link SubTradeInfo} object
         *
         * @param userId: user id of the sub trade info
         * @param btc: btc of the sub trade info
         * @param btcFutures: btc futures of the sub trade info
         * @param btcMargin: btc margin of the sub trade info
         * @param busd: busd of the sub trade info
         * @param busdFutures: busd futures of the sub trade info
         * @param busdMargin: busd margin of the sub trade info
         * @param date: date of the sub trade info
         */
        public SubTradeInfo(long userId, double btc, double btcFutures, double btcMargin, double busd, double busdFutures,
                            double busdMargin, long date) {
            super(null);
            this.userId = userId;
            this.btc = btc;
            this.btcFutures = btcFutures;
            this.btcMargin = btcMargin;
            this.busd = busd;
            this.busdFutures = busdFutures;
            this.busdMargin = busdMargin;
            this.date = date;
        }

        /**
         * Constructor to init {@link SubTradeInfo} object
         *
         * @param jSubTradeInfo: sub trade info details as {@link JSONObject}
         */
        public SubTradeInfo(JSONObject jSubTradeInfo) {
            super(jSubTradeInfo);
            userId = hItem.getLong("userId", 0);
            btc = hItem.getDouble("btc", 0);
            btcFutures = hItem.getDouble("btcFutures", 0);
            btcMargin = hItem.getDouble("btcMargin", 0);
            busd = hItem.getDouble("busd", 0);
            busdFutures = hItem.getDouble("busdFutures", 0);
            busdMargin = hItem.getDouble("busdMargin", 0);
            date = hItem.getLong("date", 0);
        }

        /**
         * Method to get {@link #userId} instance <br>
         * No-any params required
         *
         * @return {@link #userId} instance as long
         */
        public long getUserId() {
            return userId;
        }

        /**
         * Method to get {@link #btc} instance <br>
         * No-any params required
         *
         * @return {@link #btc} instance as double
         */
        public double getBtc() {
            return btc;
        }

        /**
         * Method to get {@link #btc} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #btc} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBtc(int decimals) {
            return roundValue(btc, decimals);
        }

        /**
         * Method to get {@link #btcFutures} instance <br>
         * No-any params required
         *
         * @return {@link #btcFutures} instance as double
         */
        public double getBtcFutures() {
            return btcFutures;
        }

        /**
         * Method to get {@link #btcFutures} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #btcFutures} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBtcFutures(int decimals) {
            return roundValue(btcFutures, decimals);
        }

        /**
         * Method to get {@link #btcMargin} instance <br>
         * No-any params required
         *
         * @return {@link #btcMargin} instance as double
         */
        public double getBtcMargin() {
            return btcMargin;
        }

        /**
         * Method to get {@link #btcMargin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #btcMargin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBtcMargin(int decimals) {
            return roundValue(btcMargin, decimals);
        }

        /**
         * Method to get {@link #busd} instance <br>
         * No-any params required
         *
         * @return {@link #busd} instance as double
         */
        public double getBusd() {
            return busd;
        }

        /**
         * Method to get {@link #busd} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #busd} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBusd(int decimals) {
            return roundValue(busd, decimals);
        }

        /**
         * Method to get {@link #busdFutures} instance <br>
         * No-any params required
         *
         * @return {@link #busdFutures} instance as double
         */
        public double getBusdFutures() {
            return busdFutures;
        }

        /**
         * Method to get {@link #busdFutures} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #busdFutures} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBusdFutures(int decimals) {
            return roundValue(busdFutures, decimals);
        }

        /**
         * Method to get {@link #busdMargin} instance <br>
         * No-any params required
         *
         * @return {@link #busdMargin} instance as double
         */
        public double getBusdMargin() {
            return busdMargin;
        }

        /**
         * Method to get {@link #busdMargin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #busdMargin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBusdMargin(int decimals) {
            return roundValue(busdMargin, decimals);
        }

        /**
         * Method to get {@link #date} instance <br>
         * No-any params required
         *
         * @return {@link #date} instance as long
         */
        public long getDate() {
            return date;
        }

        /**
         * Method to get {@link #date} instance <br>
         * No-any params required
         *
         * @return {@link #date} instance as {@link Date}
         */
        public Date getTimeDate() {
            return TimeFormatter.getDate(date);
        }

    }

}
