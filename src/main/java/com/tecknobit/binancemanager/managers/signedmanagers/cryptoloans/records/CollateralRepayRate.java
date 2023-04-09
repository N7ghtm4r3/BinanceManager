package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CollateralRepayRate} class is useful to create a collateral repay rate
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#check-collateral-repay-rate-user_data">
 * Check Collateral Repay Rate (USER_DATA)</a>
 * @see BinanceItem
 * @see LoanBaseStructure
 **/
public class CollateralRepayRate extends LoanBaseStructure {

    /**
     * {@code repayAmount} repay amount of the collateral repay rate
     **/
    private final double repayAmount;

    /**
     * {@code rate} of the collateral repay rate
     **/
    private final double rate;

    /**
     * Constructor to init {@link CollateralRepayRate} object
     *
     * @param loanCoin:      coin of the collateral repay rate
     * @param repayAmount:   repay amount of the collateral repay rate
     * @param collateralCoin : collateral coin of the collateral repay rate
     **/
    public CollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount, double rate) {
        super(loanCoin, collateralCoin);
        this.repayAmount = repayAmount;
        this.rate = rate;
    }

    /**
     * Constructor to init {@link CollateralRepayRate} object
     *
     * @param jCollateralRepayRate : collateral repay rate details as {@link JSONObject}
     **/
    public CollateralRepayRate(JSONObject jCollateralRepayRate) {
        super(jCollateralRepayRate);
        repayAmount = hItem.getDouble("repayAmount", 0);
        rate = hItem.getDouble("rate", 0);
    }

    /**
     * Method to get {@link #repayAmount} instance <br>
     * No-any params required
     *
     * @return {@link #repayAmount} instance as double
     **/
    public double getRepayAmount() {
        return repayAmount;
    }

    /**
     * Method to get {@link #repayAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #repayAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRepayAmount(int decimals) {
        return roundValue(repayAmount, decimals);
    }

    /**
     * Method to get {@link #rate} instance <br>
     * No-any params required
     *
     * @return {@link #rate} instance as double
     **/
    public double getRate() {
        return rate;
    }

    /**
     * Method to get {@link #rate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #rate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRate(int decimals) {
        return roundValue(rate, decimals);
    }

}
