package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseRepayStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanRepayStructure;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CryptoLoanRepay} class is useful to create a crypto crypto loan repay
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#repay-crypto-loan-repay-trade">
 * Repay - Crypto crypto loan repay (TRADE)</a>
 * @see BinanceItem
 * @see LoanBaseStructure
 * @see LoanBaseRepayStructure
 * @see LoanRepayStructure
 **/
public class CryptoLoanRepay extends LoanRepayStructure {

    /**
     * {@code LoanRepayType} list of available crypto loan repay types
     **/
    public enum LoanRepayType {

        /**
         * {@code REPAY_WITH_BORROWED_COIN} crypto loan repay type
         **/
        REPAY_WITH_BORROWED_COIN(1),

        /**
         * {@code REPAY_WITH_COLLATERAL} crypto loan repay type
         **/
        REPAY_WITH_COLLATERAL(2);

        /**
         * {@code type} value
         **/
        private final int type;

        /**
         * {@code VALUES} list of the types
         **/
        private static final List<LoanRepayType> VALUES = Arrays.stream(LoanRepayType.values()).toList();

        /**
         * Constructor to init {@link LoanRepayType} object
         *
         * @param type : repay type
         **/
        LoanRepayType(int type) {
            this.type = type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         **/
        public int getType() {
            return type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constant as {@link LoanRepayType}
         **/
        public static LoanRepayType reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         **/
        @Override
        public String toString() {
            return type + "";
        }

    }

    /**
     * {@code remainingCollateral} of the crypto loan repay
     **/
    private final double remainingCollateral;

    /**
     * Constructor to init {@link CryptoLoanRepay} object
     *
     * @param loanCoin            :           coin of the vip loan
     * @param collateralCoin      :     collateral coin of the vip loan
     * @param repayStatus         :        repay status of the loan
     * @param remainingPrincipal  : remaining principal of the crypto loan repay
     * @param remainingInterest   :  remaining interest of the crypto loan repay
     * @param currentLTV          :         current LTV of the crypto loan repay
     * @param remainingCollateral :   of the crypto loan repay
     **/
    public CryptoLoanRepay(String loanCoin, String collateralCoin, RepayStatus repayStatus, double remainingPrincipal,
                           double remainingInterest, double currentLTV, double remainingCollateral) {
        super(loanCoin, collateralCoin, repayStatus, remainingPrincipal, remainingInterest, currentLTV);
        this.remainingCollateral = remainingCollateral;
    }

    /**
     * Constructor to init {@link CryptoLoanRepay} object
     *
     * @param jCryptoLoanRepay : crypto loan repay details as {@link JSONObject}
     **/
    public CryptoLoanRepay(JSONObject jCryptoLoanRepay) {
        super(jCryptoLoanRepay);
        remainingCollateral = hItem.getDouble("remainingCollateral", 0);
    }

    /**
     * Method to get {@link #remainingCollateral} instance <br>
     * No-any params required
     *
     * @return {@link #remainingCollateral} instance as double
     **/
    public double getRemainingCollateral() {
        return remainingCollateral;
    }

    /**
     * Method to get {@link #remainingCollateral} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #remainingCollateral} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRemainingCollateral(int decimals) {
        return roundValue(remainingCollateral, decimals);
    }

}
