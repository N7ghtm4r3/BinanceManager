package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.loan.LoanBaseRepayStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

public class CollateralRepayRate extends LoanBaseStructure {

    private final double repayAmount;
    private final double rate;

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param loanCoin       :       coin of the vip loan
     * @param repayAmount:
     * @param collateralCoin : collateral coin of the loan
     **/
    public CollateralRepayRate(String loanCoin, String collateralCoin, double repayAmount, double rate) {
        super(loanCoin, collateralCoin);
        this.repayAmount = repayAmount;
        this.rate = rate;
    }

    /**
     * Constructor to init {@link LoanBaseRepayStructure} object
     *
     * @param jCollateralRepayRate : loan repay structure details as {@link JSONObject}
     **/
    public CollateralRepayRate(JSONObject jCollateralRepayRate) {
        super(jCollateralRepayRate);
        repayAmount = hItem.getDouble("repayAmount", 0);
        rate = hItem.getDouble("rate", 0);
    }

    public double getRepayAmount() {
        return repayAmount;
    }

    public double getRate() {
        return rate;
    }

}
