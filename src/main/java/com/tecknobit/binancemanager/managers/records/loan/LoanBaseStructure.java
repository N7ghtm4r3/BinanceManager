package com.tecknobit.binancemanager.managers.records.loan;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code LoanBaseStructure} class is useful to create a loan base structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
@Structure
public abstract class LoanBaseStructure extends BinanceItem {

    /**
     * {@code loanCoin} coin of the loan
     */
    protected final String loanCoin;

    /**
     * {@code collateralCoin} collateral coin of the loan
     */
    protected final String collateralCoin;

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param loanCoin:       coin of the loan
     * @param collateralCoin: collateral coin of the loan
     */
    public LoanBaseStructure(String loanCoin, String collateralCoin) {
        super(null);
        this.loanCoin = loanCoin;
        this.collateralCoin = collateralCoin;
    }

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param jLoanBaseStructure: loan base structure details as {@link JSONObject}
     */
    public LoanBaseStructure(JSONObject jLoanBaseStructure) {
        super(jLoanBaseStructure);
        loanCoin = hItem.getString("loanCoin");
        collateralCoin = hItem.getString("collateralCoin");
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
     * Method to get {@link #collateralCoin} instance <br>
     * No-any params required
     *
     * @return {@link #collateralCoin} instance as {@link String}
     */
    public String getCollateralCoin() {
        return collateralCoin;
    }

}
