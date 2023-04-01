package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PortfolioMarginAccountInfo} class is useful to create a portfolio margin account info
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
 * Get Portfolio Margin Account Info (USER_DATA)</a>
 * @see BinanceItem
 **/
public class PortfolioMarginAccountInfo extends BinanceItem {

    /**
     * {@code PortfolioStatus} list of the available portfolio statuses
     **/
    public enum PortfolioStatus {

        /**
         * {@code NORMAL} portfolio status
         **/
        NORMAL,

        /**
         * {@code MARGIN_CALL} portfolio status
         **/
        MARGIN_CALL,

        /**
         * {@code SUPPLY_MARGIN} portfolio status
         **/
        SUPPLY_MARGIN,

        /**
         * {@code REDUCE_ONLY} portfolio status
         **/
        REDUCE_ONLY,

        /**
         * {@code ACTIVE_LIQUIDATION} portfolio status
         **/
        ACTIVE_LIQUIDATION,

        /**
         * {@code FORCE_LIQUIDATION} portfolio status
         **/
        FORCE_LIQUIDATION,

        /**
         * {@code BANKRUPTED} portfolio status
         **/
        BANKRUPTED

    }

    /**
     * {@code uniMMR} portfolio margin account maintenance margin rate
     **/
    private final double uniMMR;

    /**
     * {@code accountEquity} account equity
     **/
    private final double accountEquity;

    /**
     * {@code accountMaintMargin} portfolio margin account maintenance margin
     **/
    private final double accountMaintMargin;

    /**
     * {@code accountStatus} portfolio margin account status
     **/
    private final PortfolioStatus accountStatus;

    /**
     * Constructor to init {@link PortfolioMarginAccountInfo}
     *
     * @param uniMMR             : portfolio margin account maintenance margin rate
     * @param accountEquity      : account equity
     * @param accountMaintMargin : portfolio margin account maintenance margin
     * @param accountStatus      : portfolio margin account status
     **/
    public PortfolioMarginAccountInfo(double uniMMR, double accountEquity, double accountMaintMargin,
                                      PortfolioStatus accountStatus) {
        super(null);
        this.uniMMR = uniMMR;
        this.accountEquity = accountEquity;
        this.accountMaintMargin = accountMaintMargin;
        this.accountStatus = accountStatus;
    }

    /**
     * Constructor to init {@link PortfolioMarginAccountInfo}
     *
     * @param jPortfolioMarginAccountInfo : portfolio margin account info details as {@link JSONObject}
     **/
    public PortfolioMarginAccountInfo(JSONObject jPortfolioMarginAccountInfo) {
        super(jPortfolioMarginAccountInfo);
        uniMMR = hItem.getDouble("uniMMR", 0);
        accountEquity = hItem.getDouble("accountEquity", 0);
        accountMaintMargin = hItem.getDouble("accountMaintMargin", 0);
        accountStatus = PortfolioStatus.valueOf(hItem.getString("accountStatus"));
    }

    /**
     * Method to get {@link #uniMMR} instance <br>
     * No-any params required
     *
     * @return {@link #uniMMR} instance as double
     **/
    public double getUniMMR() {
        return uniMMR;
    }

    /**
     * Method to get {@link #uniMMR} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #uniMMR} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getUniMMR(int decimals) {
        return roundValue(uniMMR, decimals);
    }

    /**
     * Method to get {@link #accountEquity} instance <br>
     * No-any params required
     *
     * @return {@link #accountEquity} instance as double
     **/
    public double getAccountEquity() {
        return accountEquity;
    }

    /**
     * Method to get {@link #accountEquity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #accountEquity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAccountEquity(int decimals) {
        return roundValue(accountEquity, decimals);
    }

    /**
     * Method to get {@link #accountMaintMargin} instance <br>
     * No-any params required
     *
     * @return {@link #accountMaintMargin} instance as double
     **/
    public double getAccountMaintMargin() {
        return accountMaintMargin;
    }

    /**
     * Method to get {@link #accountMaintMargin} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #accountMaintMargin} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAccountMaintMargin(int decimals) {
        return roundValue(accountMaintMargin, decimals);
    }

    /**
     * Method to get {@link #accountStatus} instance <br>
     * No-any params required
     *
     * @return {@link #accountStatus} instance as {@link PortfolioStatus}
     **/
    public PortfolioStatus getAccountStatus() {
        return accountStatus;
    }

}
