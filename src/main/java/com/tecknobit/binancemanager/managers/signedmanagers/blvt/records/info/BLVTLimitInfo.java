package com.tecknobit.binancemanager.managers.signedmanagers.blvt.records.info;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class BLVTLimitInfo extends BinanceItem {

    private final String tokenName;
    private final double userDailyTotalPurchaseLimit;
    private final double userDailyTotalRedeemLimit;

    public BLVTLimitInfo(String tokenName, double userDailyTotalPurchaseLimit, double userDailyTotalRedeemLimit) {
        super(null);
        this.tokenName = tokenName;
        this.userDailyTotalPurchaseLimit = userDailyTotalPurchaseLimit;
        this.userDailyTotalRedeemLimit = userDailyTotalRedeemLimit;
    }

    public BLVTLimitInfo(JSONObject jBLVTLimitInfo) {
        super(jBLVTLimitInfo);
        tokenName = hItem.getString("tokenName");
        userDailyTotalPurchaseLimit = hItem.getDouble("userDailyTotalPurchaseLimit", 0);
        userDailyTotalRedeemLimit = hItem.getDouble("userDailyTotalRedeemLimit", 0);
    }

    public String getTokenName() {
        return tokenName;
    }

    public double getUserDailyTotalPurchaseLimit() {
        return userDailyTotalPurchaseLimit;
    }

    public double getUserDailyTotalRedeemLimit() {
        return userDailyTotalRedeemLimit;
    }

}
