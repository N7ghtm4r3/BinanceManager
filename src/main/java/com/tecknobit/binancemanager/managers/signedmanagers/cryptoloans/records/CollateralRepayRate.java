package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.loan.LoanBaseRepayStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

public class CollateralRepayRate extends LoanBaseRepayStructure {

    private final double rate;

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param loanCoin       :       coin of the vip loan
     * @param collateralCoin : collateral coin of the loan
     * @param repayStatus    :    repay status of the loan
     **/
    public CollateralRepayRate(String loanCoin, String collateralCoin, RepayStatus repayStatus, double rate) {
        super(loanCoin, collateralCoin, repayStatus);
        this.rate = rate;
    }

    /**
     * Constructor to init {@link LoanBaseRepayStructure} object
     *
     * @param jVIPLoanRepayStructure : loan repay structure details as {@link JSONObject}
     **/
    public CollateralRepayRate(JSONObject jVIPLoanRepayStructure) {
        super(jVIPLoanRepayStructure);
        rate = hItem.getDouble("rate", 0);
    }

    public double getRate() {
        return rate;
    }

}
