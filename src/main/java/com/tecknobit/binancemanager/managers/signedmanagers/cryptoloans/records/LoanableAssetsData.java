package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.LoanableAssetsData.LoanableAsset;

public class LoanableAssetsData extends BinanceRowsList<LoanableAsset> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total  : number of items
     * @param assets :  list of the items
     **/
    public LoanableAssetsData(int total, ArrayList<LoanableAsset> assets) {
        super(total, assets);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public LoanableAssetsData(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new LoanableAsset((JSONObject) row));
    }

    public static class LoanableAsset extends BinanceItem {

        private final String loanCoin;
        private final double _7dHourlyInterestRate;
        private final double _7dDailyInterestRate;
        private final double _14dHourlyInterestRate;
        private final double _14dDailyInterestRate;
        private final double _30dHourlyInterestRate;
        private final double _30dDailyInterestRate;
        private final double _90dHourlyInterestRate;
        private final double _90dDailyInterestRate;
        private final double _180dHourlyInterestRate;
        private final double _180dDailyInterestRate;
        private final double minLimit;
        private final double maxLimit;
        private final int vipLevel;

        public LoanableAsset(String loanCoin, double _7dHourlyInterestRate, double _7dDailyInterestRate,
                             double _14dHourlyInterestRate, double _14dDailyInterestRate, double _30dHourlyInterestRate,
                             double _30dDailyInterestRate, double _90dHourlyInterestRate, double _90dDailyInterestRate,
                             double _180dHourlyInterestRate, double _180dDailyInterestRate, double minLimit,
                             double maxLimit, int vipLevel) {
            super(null);
            this.loanCoin = loanCoin;
            this._7dHourlyInterestRate = _7dHourlyInterestRate;
            this._7dDailyInterestRate = _7dDailyInterestRate;
            this._14dHourlyInterestRate = _14dHourlyInterestRate;
            this._14dDailyInterestRate = _14dDailyInterestRate;
            this._30dHourlyInterestRate = _30dHourlyInterestRate;
            this._30dDailyInterestRate = _30dDailyInterestRate;
            this._90dHourlyInterestRate = _90dHourlyInterestRate;
            this._90dDailyInterestRate = _90dDailyInterestRate;
            this._180dHourlyInterestRate = _180dHourlyInterestRate;
            this._180dDailyInterestRate = _180dDailyInterestRate;
            this.minLimit = minLimit;
            this.maxLimit = maxLimit;
            this.vipLevel = vipLevel;
        }

        public LoanableAsset(JSONObject jLoanableAsset) {
            super(jLoanableAsset);
            loanCoin = hItem.getString("loanCoin");
            _7dHourlyInterestRate = hItem.getDouble("_7dHourlyInterestRate", 0);
            _7dDailyInterestRate = hItem.getDouble("_7dDailyInterestRate", 0);
            _14dHourlyInterestRate = hItem.getDouble("_14dHourlyInterestRate", 0);
            _14dDailyInterestRate = hItem.getDouble("_14dDailyInterestRate", 0);
            _30dHourlyInterestRate = hItem.getDouble("_30dHourlyInterestRate", 0);
            _30dDailyInterestRate = hItem.getDouble("_30dDailyInterestRate", 0);
            _90dHourlyInterestRate = hItem.getDouble("_90dHourlyInterestRate", 0);
            _90dDailyInterestRate = hItem.getDouble("_90dDailyInterestRate", 0);
            _180dHourlyInterestRate = hItem.getDouble("_180dHourlyInterestRate", 0);
            _180dDailyInterestRate = hItem.getDouble("_180dDailyInterestRate", 0);
            minLimit = hItem.getDouble("minLimit", 0);
            maxLimit = hItem.getDouble("maxLimit", 0);
            vipLevel = hItem.getInt("vipLevel", 0);
        }

        public String getLoanCoin() {
            return loanCoin;
        }

        public double get_7dHourlyInterestRate() {
            return _7dHourlyInterestRate;
        }

        public double get_7dDailyInterestRate() {
            return _7dDailyInterestRate;
        }

        public double get_14dHourlyInterestRate() {
            return _14dHourlyInterestRate;
        }

        public double get_14dDailyInterestRate() {
            return _14dDailyInterestRate;
        }

        public double get_30dHourlyInterestRate() {
            return _30dHourlyInterestRate;
        }

        public double get_30dDailyInterestRate() {
            return _30dDailyInterestRate;
        }

        public double get_90dHourlyInterestRate() {
            return _90dHourlyInterestRate;
        }

        public double get_90dDailyInterestRate() {
            return _90dDailyInterestRate;
        }

        public double get_180dHourlyInterestRate() {
            return _180dHourlyInterestRate;
        }

        public double get_180dDailyInterestRate() {
            return _180dDailyInterestRate;
        }

        public double getMinLimit() {
            return minLimit;
        }

        public double getMaxLimit() {
            return maxLimit;
        }

        public int getVipLevel() {
            return vipLevel;
        }

    }

}
