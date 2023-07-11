package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class SimpleEarnAccount extends BinanceItem {

    private final double totalAmountInBTC;

    private final double totalAmountInUSDT;

    private final double totalFlexibleAmountInBTC;

    private final double totalFlexibleAmountInUSDT;

    private final double totalLockedInBTC;

    private final double totalLockedInUSDT;

    public SimpleEarnAccount(double totalAmountInBTC, double totalAmountInUSDT, double totalFlexibleAmountInBTC,
                             double totalFlexibleAmountInUSDT, double totalLockedInBTC, double totalLockedInUSDT) {
        super(null);
        this.totalAmountInBTC = totalAmountInBTC;
        this.totalAmountInUSDT = totalAmountInUSDT;
        this.totalFlexibleAmountInBTC = totalFlexibleAmountInBTC;
        this.totalFlexibleAmountInUSDT = totalFlexibleAmountInUSDT;
        this.totalLockedInBTC = totalLockedInBTC;
        this.totalLockedInUSDT = totalLockedInUSDT;
    }

    public SimpleEarnAccount(JSONObject jSimpleEarnAccount) {
        super(jSimpleEarnAccount);
        totalAmountInBTC = hItem.getDouble("totalAmountInBTC", 0);
        totalAmountInUSDT = hItem.getDouble("totalAmountInUSDT", 0);
        totalFlexibleAmountInBTC = hItem.getDouble("totalFlexibleAmountInBTC", 0);
        totalFlexibleAmountInUSDT = hItem.getDouble("totalFlexibleAmountInUSDT", 0);
        totalLockedInBTC = hItem.getDouble("totalLockedInBTC", 0);
        totalLockedInUSDT = hItem.getDouble("totalLockedInUSDT", 0);
    }

    public double getTotalAmountInBTC() {
        return totalAmountInBTC;
    }

    public double getTotalAmountInUSDT() {
        return totalAmountInUSDT;
    }

    public double getTotalFlexibleAmountInBTC() {
        return totalFlexibleAmountInBTC;
    }

    public double getTotalFlexibleAmountInUSDT() {
        return totalFlexibleAmountInUSDT;
    }

    public double getTotalLockedInBTC() {
        return totalLockedInBTC;
    }

    public double getTotalLockedInUSDT() {
        return totalLockedInUSDT;
    }

}
