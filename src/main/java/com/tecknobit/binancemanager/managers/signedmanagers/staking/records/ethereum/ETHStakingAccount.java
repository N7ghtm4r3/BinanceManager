package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ETHStakingAccount} class is useful to format a {@code Binance}'s ETH staking account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#eth-staking-account-user_data">
 * ETH Staking account (USER_DATA)</a>
 * @see BinanceItem
 */
public class ETHStakingAccount extends BinanceItem {

    /**
     * {@code cumulativeProfitInBETH} cumulative profit in BETH
     */
    private final double cumulativeProfitInBETH;

    /**
     * {@code lastDayProfitInBETH} last day profit in BETH
     */
    private final double lastDayProfitInBETH;

    /**
     * Constructor to init a {@link ETHStakingAccount} object
     *
     * @param cumulativeProfitInBETH: cumulative profit in BETH
     * @param lastDayProfitInBETH:    last day profit in BETH
     */
    public ETHStakingAccount(double cumulativeProfitInBETH, double lastDayProfitInBETH) {
        super(null);
        this.cumulativeProfitInBETH = cumulativeProfitInBETH;
        this.lastDayProfitInBETH = lastDayProfitInBETH;
    }

    /**
     * Constructor to init a {@link ETHStakingAccount} object
     *
     * @param jETHStakingAccount: ETH staking account details as {@link JSONObject}
     */
    public ETHStakingAccount(JSONObject jETHStakingAccount) {
        super(jETHStakingAccount);
        this.cumulativeProfitInBETH = hItem.getDouble("cumulativeProfitInBETH", 0);
        this.lastDayProfitInBETH = hItem.getDouble("lastDayProfitInBETH", 0);
    }

    /**
     * Method to get {@link #cumulativeProfitInBETH} instance <br>
     * No-any params required
     *
     * @return {@link #cumulativeProfitInBETH} instance as double
     */
    public double getCumulativeProfitInBETH() {
        return cumulativeProfitInBETH;
    }

    /**
     * Method to get {@link #cumulativeProfitInBETH} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #cumulativeProfitInBETH} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getCumulativeProfitInBETH(int decimals) {
        return roundValue(cumulativeProfitInBETH, decimals);
    }

    /**
     * Method to get {@link #lastDayProfitInBETH} instance <br>
     * No-any params required
     *
     * @return {@link #lastDayProfitInBETH} instance as double
     */
    public double getLastDayProfitInBETH() {
        return lastDayProfitInBETH;
    }

    /**
     * Method to get {@link #lastDayProfitInBETH} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lastDayProfitInBETH} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLastDayProfitInBETH(int decimals) {
        return roundValue(lastDayProfitInBETH, decimals);
    }

}
