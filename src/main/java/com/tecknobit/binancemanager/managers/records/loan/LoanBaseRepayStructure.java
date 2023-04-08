package com.tecknobit.binancemanager.managers.records.loan;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code LoanBaseRepayStructure} class is useful to create a VIP loan repay structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
 *             VIP Loan Repay (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
 *             Get VIP Loan Repayment History (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see LoanBaseStructure
 **/
public abstract class LoanBaseRepayStructure extends LoanBaseStructure {

    /**
     * {@code RepayStatus} list of available repay statuses
     **/
    public enum RepayStatus {

        /**
         * {@code Repaid} repay status
         **/
        Repaid,

        /**
         * {@code Repaying} repay status
         **/
        Repaying,

        /**
         * {@code Failed} repay status
         **/
        Failed

    }

    /**
     * {@code repayStatus} repay status of the loan
     **/
    protected final RepayStatus repayStatus;

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param loanCoin:       coin of the loan
     * @param collateralCoin: collateral coin of the loan
     * @param repayStatus:    repay status of the loan
     **/
    public LoanBaseRepayStructure(String loanCoin, String collateralCoin, RepayStatus repayStatus) {
        super(loanCoin, collateralCoin);
        this.repayStatus = repayStatus;
    }

    /**
     * Constructor to init {@link LoanBaseRepayStructure} object
     *
     * @param jLoanBaseRepayStructure: loan repay structure details as {@link JSONObject}
     **/
    public LoanBaseRepayStructure(JSONObject jLoanBaseRepayStructure) {
        super(jLoanBaseRepayStructure);
        repayStatus = RepayStatus.valueOf(hItem.getString("repayStatus"));
    }

    /**
     * Method to get {@link #repayStatus} instance <br>
     * No-any params required
     *
     * @return {@link #repayStatus} instance as {@link String}
     **/
    public RepayStatus getRepayStatus() {
        return repayStatus;
    }

}
