package com.tecknobit.binancemanager.managers.records.loan;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LoanAssetStructure} class is useful to create a loan asset structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 * @see LoanAssetStructure
 */
@Structure
public abstract class LoanAssetStructure extends BinanceItem {

    /**
     * {@code loanCoin} loan coin of the loanable asset
     */
    protected final String loanCoin;

    /**
     * {@code _30dDailyInterestRate} 30 daily interest rate of the loanable asset
     */
    protected final double _30dDailyInterestRate;

    /**
     * {@code minLimit} min limit of the loanable asset
     */
    protected final double minLimit;

    /**
     * {@code maxLimit} max limit of the loanable asset
     */
    protected final double maxLimit;

    /**
     * {@code vipLevel} vip level of the loanable asset
     */
    protected final int vipLevel;

    /**
     * Constructor to init a {@link LoanAssetStructure} object
     *
     * @param loanCoin:              {@code loanCoin}
     * @param _30dDailyInterestRate: 30 daily interest rate of the loanable asset
     * @param minLimit:              min limit of the loanable asset
     * @param maxLimit:              max limit of the loanable asset
     * @param vipLevel:              vip level of the loanable asset
     */
    public LoanAssetStructure(String loanCoin, double _30dDailyInterestRate, double minLimit, double maxLimit,
                              int vipLevel) {
        super(null);
        this.loanCoin = loanCoin;
        this._30dDailyInterestRate = _30dDailyInterestRate;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        this.vipLevel = vipLevel;
    }

    /**
     * Constructor to init {@link LoanAssetStructure}
     *
     * @param jLoanAssetStructure : loanable asset structure details as {@link JSONObject}
     */
    public LoanAssetStructure(JSONObject jLoanAssetStructure) {
        super(jLoanAssetStructure);
        loanCoin = hItem.getString("loanCoin");
        _30dDailyInterestRate = hItem.getDouble("_30dDailyInterestRate", 0);
        minLimit = hItem.getDouble("minLimit", 0);
        maxLimit = hItem.getDouble("maxLimit", 0);
        vipLevel = hItem.getInt("vipLevel", 0);
    }

    /**
     * Method to get {@link #loanCoin} instance <br>
     * No-any params required
     *
     * @return {@link #loanCoin} instance as {@link String}
     */
    public String getLoanCoin() {
        return loanCoin;
    }

    /**
     * Method to get {@link #_30dDailyInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #_30dDailyInterestRate} instance as double
     */
    public double get30dDailyInterestRate() {
        return _30dDailyInterestRate;
    }

    /**
     * Method to get {@link #_30dDailyInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #_30dDailyInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double get30dDailyInterestRate(int decimals) {
        return roundValue(_30dDailyInterestRate, decimals);
    }

    /**
     * Method to get {@link #minLimit} instance <br>
     * No-any params required
     *
     * @return {@link #minLimit} instance as double
     */
    public double getMinLimit() {
        return minLimit;
    }

    /**
     * Method to get {@link #minLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #minLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMinLimit(int decimals) {
        return roundValue(minLimit, decimals);
    }

    /**
     * Method to get {@link #maxLimit} instance <br>
     * No-any params required
     *
     * @return {@link #maxLimit} instance as double
     */
    public double getMaxLimit() {
        return maxLimit;
    }

    /**
     * Method to get {@link #maxLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #maxLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMaxLimit(int decimals) {
        return roundValue(maxLimit, decimals);
    }

    /**
     * Method to get {@link #vipLevel} instance <br>
     * No-any params required
     *
     * @return {@link #vipLevel} instance as int
     */
    public int getVipLevel() {
        return vipLevel;
    }

}
