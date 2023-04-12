package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class LendingAccount extends BinanceItem {

    private final ArrayList<PositionAmount> positionAmountVos;
    private final double totalAmountInBTC;
    private final double totalAmountInUSDT;
    private final double totalFixedAmountInBTC;
    private final double totalFixedAmountInUSDT;
    private final double totalFlexibleInBTC;
    private final double totalFlexibleInUSDT;

    public LendingAccount(ArrayList<PositionAmount> positionAmountVos, double totalAmountInBTC, double totalAmountInUSDT,
                          double totalFixedAmountInBTC, double totalFixedAmountInUSDT, double totalFlexibleInBTC,
                          double totalFlexibleInUSDT) {
        super(null);
        this.positionAmountVos = positionAmountVos;
        this.totalAmountInBTC = totalAmountInBTC;
        this.totalAmountInUSDT = totalAmountInUSDT;
        this.totalFixedAmountInBTC = totalFixedAmountInBTC;
        this.totalFixedAmountInUSDT = totalFixedAmountInUSDT;
        this.totalFlexibleInBTC = totalFlexibleInBTC;
        this.totalFlexibleInUSDT = totalFlexibleInUSDT;
    }

    public LendingAccount(JSONObject jLendingAccount) {
        super(jLendingAccount);
        positionAmountVos = new ArrayList<>();
        for (Object position : hItem.fetchList("positionAmountVos"))
            positionAmountVos.add(new PositionAmount((JSONObject) position));
        totalAmountInBTC = hItem.getDouble("totalAmountInBTC", 0);
        totalAmountInUSDT = hItem.getDouble("totalAmountInUSDT", 0);
        totalFixedAmountInBTC = hItem.getDouble("totalFixedAmountInBTC", 0);
        totalFixedAmountInUSDT = hItem.getDouble("totalFixedAmountInUSDT", 0);
        totalFlexibleInBTC = hItem.getDouble("totalFlexibleInBTC", 0);
        totalFlexibleInUSDT = hItem.getDouble("totalFlexibleInUSDT", 0);
    }

    public ArrayList<PositionAmount> getPositionAmountVos() {
        return positionAmountVos;
    }

    public double getTotalAmountInBTC() {
        return totalAmountInBTC;
    }

    public double getTotalAmountInUSDT() {
        return totalAmountInUSDT;
    }

    public double getTotalFixedAmountInBTC() {
        return totalFixedAmountInBTC;
    }

    public double getTotalFixedAmountInUSDT() {
        return totalFixedAmountInUSDT;
    }

    public double getTotalFlexibleInBTC() {
        return totalFlexibleInBTC;
    }

    public double getTotalFlexibleInUSDT() {
        return totalFlexibleInUSDT;
    }

    public static class PositionAmount extends BinanceItem {

        private final double amount;
        private final double amountInBTC;
        private final double amountInUSDT;
        private final String asset;

        public PositionAmount(double amount, double amountInBTC, double amountInUSDT, String asset) {
            super(null);
            this.amount = amount;
            this.amountInBTC = amountInBTC;
            this.amountInUSDT = amountInUSDT;
            this.asset = asset;
        }

        public PositionAmount(JSONObject jPositionAmount) {
            super(jPositionAmount);
            amount = hItem.getDouble("amount", 0);
            amountInBTC = hItem.getDouble("amountInBTC", 0);
            amountInUSDT = hItem.getDouble("amountInUSDT", 0);
            asset = hItem.getString("asset");
        }

        public double getAmount() {
            return amount;
        }

        public double getAmountInBTC() {
            return amountInBTC;
        }

        public double getAmountInUSDT() {
            return amountInUSDT;
        }

        public String getAsset() {
            return asset;
        }

    }

}
