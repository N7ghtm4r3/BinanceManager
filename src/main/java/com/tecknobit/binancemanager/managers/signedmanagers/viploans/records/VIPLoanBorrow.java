package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code VIPLoanBorrow} class is useful to format a {@code Binance}'s VIP loan borrow
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-borrow-trade">
 * VIP Loan Borrow (TRADE)</a>
 * @see BinanceItem
 */
public class VIPLoanBorrow extends BinanceItem {

    /**
     * {@code loanAccountId} loan account identifier
     */
    private final long loanAccountId;

    /**
     * {@code requestId} request identifier
     */
    private final long requestId;

    /**
     * {@code loanCoin} loan coin value
     */
    private final String loanCoin;

    /**
     * {@code loanAmount} loan amount value
     */
    private final double loanAmount;

    /**
     * {@code collateralAccountId} collateral account identifier
     */
    private final String collateralAccountId;

    /**
     * {@code collateralCoin} collateral coin value
     */
    private final String collateralCoin;

    /**
     * {@code loanTerm} loan term value
     */
    private final int loanTerm;

    /**
     * Constructor to init a {@link VIPLoanBorrow} object
     *
     * @param loanAccountId:       loan account identifier
     * @param requestId:           request identifier
     * @param loanCoin:            loan coin value
     * @param loanAmount:          loan amount value
     * @param collateralAccountId: collateral account identifier
     * @param collateralCoin:      collateral coin value
     * @param loanTerm:            loan term value
     */
    public VIPLoanBorrow(long loanAccountId, long requestId, String loanCoin, double loanAmount, String collateralAccountId,
                         String collateralCoin, int loanTerm) {
        super(null);
        this.loanAccountId = loanAccountId;
        this.requestId = requestId;
        this.loanCoin = loanCoin;
        this.loanAmount = loanAmount;
        this.collateralAccountId = collateralAccountId;
        this.collateralCoin = collateralCoin;
        this.loanTerm = loanTerm;
    }

    /**
     * Constructor to init a {@link VIPLoanBorrow} object
     *
     * @param jVIPLoanBorrow: VIP loan borrow details as {@link JSONObject}
     */
    public VIPLoanBorrow(JSONObject jVIPLoanBorrow) {
        super(jVIPLoanBorrow);
        loanAccountId = hItem.getLong("loanAccountId", 0);
        requestId = hItem.getLong("requestId", 0);
        loanCoin = hItem.getString("loanCoin");
        loanAmount = hItem.getDouble("loanAmount", 0);
        collateralAccountId = hItem.getString("collateralAccountId");
        collateralCoin = hItem.getString("collateralCoin");
        loanTerm = hItem.getInt("loanTerm", 0);
    }

    /**
     * Method to get {@link #loanAccountId} instance <br>
     * No-any params required
     *
     * @return {@link #loanAccountId} instance as long
     */
    public long getLoanAccountId() {
        return loanAccountId;
    }

    /**
     * Method to get {@link #requestId} instance <br>
     * No-any params required
     *
     * @return {@link #requestId} instance as long
     */
    public long getRequestId() {
        return requestId;
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
     * Method to get {@link #loanAmount} instance <br>
     * No-any params required
     *
     * @return {@link #loanAmount} instance as double
     */
    public double getLoanAmount() {
        return loanAmount;
    }

    /**
     * Method to get {@link #loanAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #loanAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLoanAmount(int decimals) {
        return roundValue(loanAmount, decimals);
    }

    /**
     * Method to get {@link #collateralAccountId} instance <br>
     * No-any params required
     *
     * @return {@link #collateralAccountId} instance as {@link String}
     */
    public String getCollateralAccountId() {
        return collateralAccountId;
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

    /**
     * Method to get {@link #loanTerm} instance <br>
     * No-any params required
     *
     * @return {@link #loanTerm} instance as int
     */
    public int getLoanTerm() {
        return loanTerm;
    }

}
