package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FuturesPositionRisk} class is useful to format a futures position risk
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
 * Get Futures Position-Risk of Sub-account (For Master Account)</a>
 * @see BinanceItem
 */
public class FuturesPositionRisk extends RiskStructure {

    /**
     * {@code maxNotional} max notional of the futures position risk
     */
    private final double maxNotional;

    /**
     * {@code liquidationPrice} liquidation price of the futures position risk
     */
    private final double liquidationPrice;

    /**
     * Constructor to init {@link FuturesPositionRisk} object
     *
     * @param entryPrice       : entry price of the futures position risk
     * @param leverage         : leverage of the futures position risk
     * @param markPrice        : mark price of the futures position risk
     * @param positionAmount   : position amount of the futures position risk
     * @param symbol           : symbol of the futures position risk
     * @param unrealizedProfit : unrealized profit of the futures position risk
     * @param maxNotional:     max notional of the futures position risk
     * @param liquidationPrice : liquidation price of the futures position risk
     */
    public FuturesPositionRisk(double entryPrice, double leverage, double markPrice, double positionAmount, String symbol,
                               double unrealizedProfit, double maxNotional, double liquidationPrice) {
        super(entryPrice, leverage, markPrice, positionAmount, symbol, unrealizedProfit);
        this.maxNotional = maxNotional;
        this.liquidationPrice = liquidationPrice;
    }

    /**
     * Constructor to init {@link FuturesPositionRisk} object
     *
     * @param jFuturesPositionRisk: futures position risk details as {@link JSONObject}
     */
    public FuturesPositionRisk(JSONObject jFuturesPositionRisk) {
        super(jFuturesPositionRisk);
        maxNotional = hItem.getDouble("maxNotional", 0);
        liquidationPrice = hItem.getDouble("liquidationPrice", 0);
    }

    /**
     * Method to get {@link #maxNotional} instance <br>
     * No-any params required
     *
     * @return {@link #maxNotional} instance as double
     */
    public double getMaxNotional() {
        return maxNotional;
    }

    /**
     * Method to get {@link #maxNotional} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #maxNotional} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMaxNotional(int decimals) {
        return roundValue(maxNotional, decimals);
    }

    /**
     * Method to get {@link #liquidationPrice} instance <br>
     * No-any params required
     *
     * @return {@link #liquidationPrice} instance as double
     */
    public double getLiquidationPrice() {
        return liquidationPrice;
    }

    /**
     * Method to get {@link #liquidationPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #liquidationPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLiquidationPrice(int decimals) {
        return roundValue(liquidationPrice, decimals);
    }

}
