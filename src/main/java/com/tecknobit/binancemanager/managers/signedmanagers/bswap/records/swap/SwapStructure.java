package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SwapStructure} class is useful to format a swap structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
 *             Get Swap History (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
 *             Request Quote (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see SwapBaseStructure
 */
@Structure
public abstract class SwapStructure extends SwapBaseStructure {

    /**
     * {@code quoteQuantity} quote quantity of the swap
     */
    protected final double quoteQuantity;

    /**
     * {@code baseQuantity} quote quantity of the swap
     */
    protected final double baseQuantity;

    /**
     * Constructor to init {@link SwapStructure} object
     *
     * @param quoteAsset:    quote asset of the swap
     * @param baseAsset:     base asset of the swap
     * @param price:         price of the swap
     * @param fee:           fee of the swap
     * @param quoteQuantity: quote quantity of the swap
     * @param baseQuantity:  quote quantity of the swap
     */
    public SwapStructure(String quoteAsset, String baseAsset, double price, double fee, double quoteQuantity,
                         double baseQuantity) {
        super(quoteAsset, baseAsset, price, fee);
        this.quoteQuantity = quoteQuantity;
        this.baseQuantity = baseQuantity;
    }

    /**
     * Constructor to init {@link SwapStructure} object
     *
     * @param jSwapStructure: swap structure details as {@link JSONObject}
     */
    public SwapStructure(JSONObject jSwapStructure) {
        super(jSwapStructure);
        quoteQuantity = hItem.getDouble("quoteQuantity", 0);
        baseQuantity = hItem.getDouble("baseQuantity", 0);
    }

    /**
     * Method to get {@link #quoteQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #quoteQuantity} instance as double
     */
    public double getQuoteQuantity() {
        return quoteQuantity;
    }

    /**
     * Method to get {@link #quoteQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQuoteQuantity(int decimals) {
        return roundValue(quoteQuantity, decimals);
    }

    /**
     * Method to get {@link #baseQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #baseQuantity} instance as double
     */
    public double getBaseQuantity() {
        return baseQuantity;
    }

    /**
     * Method to get {@link #baseQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #baseQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBaseQuantity(int decimals) {
        return roundValue(baseQuantity, decimals);
    }

}
