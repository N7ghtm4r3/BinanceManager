package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SwapHistoryItem} class is useful to format a swap history item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
 * Request Quote (USER_DATA)</a>
 * @see BinanceItem
 * @see SwapBaseStructure
 * @see SwapStructure
 */
public class SwapQuote extends SwapStructure {

    /**
     * {@code slippage} of the swap quote
     */
    private final double slippage;

    /**
     * Constructor to init {@link SwapQuote} object
     *
     * @param quoteAsset:    quote asset of the swap quote
     * @param baseAsset:     base asset of the swap quote
     * @param quoteQuantity: quote quantity of the swap quote
     * @param baseQuantity:  quote quantity of the swap quote
     * @param price:         price of the swap quote
     * @param fee:           fee of the swap quote
     * @param slippage:      slippage of the swap quote
     */
    public SwapQuote(String quoteAsset, String baseAsset, double quoteQuantity, double baseQuantity, double price,
                     double fee, double slippage) {
        super(quoteAsset, baseAsset, quoteQuantity, baseQuantity, price, fee);
        this.slippage = slippage;
    }

    /**
     * Constructor to init {@link SwapQuote} object
     *
     * @param jSwapQuote: swap quote details as {@link JSONObject}
     */
    public SwapQuote(JSONObject jSwapQuote) {
        super(jSwapQuote);
        slippage = hItem.getDouble("slippage", 0);
    }

    /**
     * Method to get {@link #slippage} instance <br>
     * No-any params required
     *
     * @return {@link #slippage} instance as double
     */
    public double getSlippage() {
        return slippage;
    }

    /**
     * Method to get {@link #slippage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #slippage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getSlippage(int decimals) {
        return roundValue(slippage, decimals);
    }

}
