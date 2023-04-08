package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.loan.LoanRepayStructure;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class CryptoLoanRepay extends LoanRepayStructure {

    public enum LoanRepayType {

        REPAY_WITH_BORROWED_COIN(1),
        REPAY_WITH_COLLATERAL(2);

        private final int type;

        /**
         * {@code VALUES} list of the statuses
         **/
        private static final List<LoanRepayType> VALUES = Arrays.stream(LoanRepayType.values()).toList();

        LoanRepayType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constants as {@link LoanRepayType}
         **/
        public static LoanRepayType reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        @Override
        public String toString() {
            return type + "";
        }

    }

    private final double remainingCollateral;

    /**
     * Constructor to init {@link LoanRepayStructure} object
     *
     * @param loanCoin           :           coin of the vip loan
     * @param collateralCoin     :     collateral coin of the vip loan
     * @param repayStatus        :        repay status of the loan
     * @param remainingPrincipal : remaining principal of the loan repay
     * @param remainingInterest  :  remaining interest of the loan repay
     * @param currentLTV         :         current LTV of the loan repay
     **/
    public CryptoLoanRepay(String loanCoin, String collateralCoin, RepayStatus repayStatus, double remainingPrincipal,
                           double remainingInterest, double currentLTV, double remainingCollateral) {
        super(loanCoin, collateralCoin, repayStatus, remainingPrincipal, remainingInterest, currentLTV);
        this.remainingCollateral = remainingCollateral;
    }

    /**
     * Constructor to init {@link LoanRepayStructure} object
     *
     * @param jCryptoLoanRepay : loan repay details as {@link JSONObject}
     **/
    public CryptoLoanRepay(JSONObject jCryptoLoanRepay) {
        super(jCryptoLoanRepay);
        remainingCollateral = hItem.getDouble("remainingCollateral", 0);
    }

    public double getRemainingCollateral() {
        return remainingCollateral;
    }

}
