package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class PortfolioMarginAccountInfo extends BinanceItem {

    public enum PortfolioStatus {

        NORMAL,
        MARGIN_CALL,
        SUPPLY_MARGIN,
        REDUCE_ONLY,
        ACTIVE_LIQUIDATION,
        FORCE_LIQUIDATION,
        BANKRUPTED

    }

    private final double uniMMR;
    private final double accountEquity;
    private final double accountMaintMargin;
    private final PortfolioStatus accountStatus;

    public PortfolioMarginAccountInfo(double uniMMR, double accountEquity, double accountMaintMargin,
                                      PortfolioStatus accountStatus) {
        super(null);
        this.uniMMR = uniMMR;
        this.accountEquity = accountEquity;
        this.accountMaintMargin = accountMaintMargin;
        this.accountStatus = accountStatus;
    }

    public PortfolioMarginAccountInfo(JSONObject jPortfolioMarginAccountInfo) {
        super(jPortfolioMarginAccountInfo);
        uniMMR = hItem.getDouble("uniMMR", 0);
        accountEquity = hItem.getDouble("accountEquity", 0);
        accountMaintMargin = hItem.getDouble("accountMaintMargin", 0);
        accountStatus = PortfolioStatus.valueOf(hItem.getString("accountStatus"));
    }

    public double getUniMMR() {
        return uniMMR;
    }

    public double getAccountEquity() {
        return accountEquity;
    }

    public double getAccountMaintMargin() {
        return accountMaintMargin;
    }

    public PortfolioStatus getAccountStatus() {
        return accountStatus;
    }

}
