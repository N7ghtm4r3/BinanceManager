package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code VIPLoanBaseStructure} class is useful to create a VIP loan base structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-ongoing-orders-user_data">
 *             Get VIP Loan Ongoing Orders (USER_DATA)</a>
 *     </li>
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
 **/
public abstract class VIPLoanBaseStructure extends BinanceItem {

    /**
     * {@code loanCoin} coin of the vip loan
     **/
    protected final String loanCoin;

    /**
     * {@code collateralCoin} collateral coin of the vip loan
     **/
    protected final String collateralCoin;

    /**
     * Constructor to init {@link VIPLoanBaseStructure} object
     *
     * @param loanCoin:       coin of the vip loan
     * @param collateralCoin: collateral coin of the vip loan
     **/
    public VIPLoanBaseStructure(String loanCoin, String collateralCoin) {
        super(null);
        this.loanCoin = loanCoin;
        this.collateralCoin = collateralCoin;
    }

    /**
     * Constructor to init {@link VIPLoanBaseStructure} object
     *
     * @param jVIPLoanBaseStructure: VIP loan base structure details as {@link JSONObject}
     **/
    public VIPLoanBaseStructure(JSONObject jVIPLoanBaseStructure) {
        super(jVIPLoanBaseStructure);
        loanCoin = hItem.getString("loanCoin");
        collateralCoin = hItem.getString("collateralCoin");
    }

    /**
     * Method to get {@link #loanCoin} instance <br>
     * No-any params required
     *
     * @return {@link #loanCoin} instance as {@link String}
     **/
    public String getLoanCoin() {
        return loanCoin;
    }

    /**
     * Method to get {@link #collateralCoin} instance <br>
     * No-any params required
     *
     * @return {@link #collateralCoin} instance as {@link String}
     **/
    public String getCollateralCoin() {
        return collateralCoin;
    }

}
