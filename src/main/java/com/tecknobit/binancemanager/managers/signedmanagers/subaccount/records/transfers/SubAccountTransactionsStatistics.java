package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubAccountTransactionsStatistics extends BinanceItem {

    private final double recent30BtcTotal;
    private final double recent30BtcFuturesTotal;
    private final double recent30BtcMarginTotal;
    private final double recent30BusdTotal;
    private final double recent30BusdFuturesTotal;
    private final double recent30BusdMarginTotal;
    private final ArrayList<SubTradeInfo> tradeInfoVos;

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

    public double getRecent30BtcTotal() {
        return recent30BtcTotal;
    }

    public double getRecent30BtcFuturesTotal() {
        return recent30BtcFuturesTotal;
    }

    public double getRecent30BtcMarginTotal() {
        return recent30BtcMarginTotal;
    }

    public double getRecent30BusdTotal() {
        return recent30BusdTotal;
    }

    public double getRecent30BusdFuturesTotal() {
        return recent30BusdFuturesTotal;
    }

    public double getRecent30BusdMarginTotal() {
        return recent30BusdMarginTotal;
    }

    public ArrayList<SubTradeInfo> getTradeInfoVos() {
        return tradeInfoVos;
    }

    public static class SubTradeInfo extends BinanceItem {

        private final long userId;
        private final double btc;
        private final double btcFutures;
        private final double btcMargin;
        private final double busd;
        private final double busdFutures;
        private final double busdMargin;
        private final long date;

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

        public long getUserId() {
            return userId;
        }

        public double getBtc() {
            return btc;
        }

        public double getBtcFutures() {
            return btcFutures;
        }

        public double getBtcMargin() {
            return btcMargin;
        }

        public double getBusd() {
            return busd;
        }

        public double getBusdFutures() {
            return busdFutures;
        }

        public double getBusdMargin() {
            return busdMargin;
        }

        public long getDate() {
            return date;
        }

    }

}
