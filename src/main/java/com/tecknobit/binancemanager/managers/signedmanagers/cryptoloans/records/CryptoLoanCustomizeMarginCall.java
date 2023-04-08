package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CryptoLoanCustomizeMarginCall.MarginCall;

public class CryptoLoanCustomizeMarginCall extends BinanceRowsList<MarginCall> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param calls :  list of the items
     **/
    public CryptoLoanCustomizeMarginCall(int total, ArrayList<MarginCall> calls) {
        super(total, calls);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public CryptoLoanCustomizeMarginCall(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new MarginCall((JSONObject) row));
    }

    public static class MarginCall extends BinanceItem {

        private final long orderId;
        private final String collateralCoin;
        private final double preMarginCall;
        private final double afterMarginCall;
        private final long customizeTime;

        public MarginCall(long orderId, String collateralCoin, double preMarginCall, double afterMarginCall,
                          long customizeTime) {
            super(null);
            this.orderId = orderId;
            this.collateralCoin = collateralCoin;
            this.preMarginCall = preMarginCall;
            this.afterMarginCall = afterMarginCall;
            this.customizeTime = customizeTime;
        }

        public MarginCall(JSONObject jMarginCall) {
            super(jMarginCall);
            orderId = hItem.getLong("orderId", 0);
            collateralCoin = hItem.getString("collateralCoin");
            preMarginCall = hItem.getDouble("preMarginCall", 0);
            afterMarginCall = hItem.getDouble("afterMarginCall", 0);
            customizeTime = hItem.getLong("customizeTime", 0);
        }

        public long getOrderId() {
            return orderId;
        }

        public String getCollateralCoin() {
            return collateralCoin;
        }

        public double getPreMarginCall() {
            return preMarginCall;
        }

        public double getAfterMarginCall() {
            return afterMarginCall;
        }

        public long getCustomizeTime() {
            return customizeTime;
        }

    }

}
