package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

public abstract class SavingProductStructure extends SavingStructure {

    protected final TierAnnualInterestRate tierAnnualInterestRate;
    protected final boolean canRedeem;
    protected final String productId;
    protected final double averageAnnualInterestRate;

    public SavingProductStructure(String asset, TierAnnualInterestRate tierAnnualInterestRate, boolean canRedeem,
                                  String productId, double averageAnnualInterestRate) {
        super(asset);
        this.tierAnnualInterestRate = tierAnnualInterestRate;
        this.canRedeem = canRedeem;
        this.productId = productId;
        this.averageAnnualInterestRate = averageAnnualInterestRate;
    }

    public SavingProductStructure(JSONObject jSavingProductStructure) {
        super(jSavingProductStructure);
        JSONObject jTier = hItem.getJSONObject("tierAnnualInterestRate");
        if (jTier != null)
            tierAnnualInterestRate = new TierAnnualInterestRate(jTier);
        else
            tierAnnualInterestRate = null;
        canRedeem = hItem.getBoolean("canRedeem");
        productId = hItem.getString("productId");
        averageAnnualInterestRate = hItem.getDouble("avgAnnualInterestRate", 0);
    }

    public TierAnnualInterestRate getTierAnnualInterestRate() {
        return tierAnnualInterestRate;
    }

    public boolean canRedeem() {
        return canRedeem;
    }

    public String getProductId() {
        return productId;
    }

    public double getAverageAnnualInterestRate() {
        return averageAnnualInterestRate;
    }

    public static class TierAnnualInterestRate extends BinanceItem {

        private final double _0_5BTC;
        private final double _5_10BTC;
        private final double _m10BTC;

        public TierAnnualInterestRate(double _0_5BTC, double _5_10BTC, double _m10BTC) {
            super(null);
            this._0_5BTC = _0_5BTC;
            this._5_10BTC = _5_10BTC;
            this._m10BTC = _m10BTC;
        }

        public TierAnnualInterestRate(JSONObject jTierAnnualInterestRate) {
            super(jTierAnnualInterestRate);
            _0_5BTC = hItem.getDouble("0-5BTC", 0);
            _5_10BTC = hItem.getDouble("5-10BTC", 0);
            _m10BTC = hItem.getDouble(">10BTC", 0);
        }

        public double get_0_5BTC() {
            return _0_5BTC;
        }

        public double get_5_10BTC() {
            return _5_10BTC;
        }

        public double get_m10BTC() {
            return _m10BTC;
        }

    }

}
