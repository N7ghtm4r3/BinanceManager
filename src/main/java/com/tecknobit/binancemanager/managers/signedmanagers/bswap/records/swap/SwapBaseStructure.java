package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SwapBaseStructure} class is useful to format a swap base structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
 *             Get Swap History (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
 *             Add Liquidity Preview (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
 *             Remove Liquidity Preview (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#request-quote-user_data">
 *             Request Quote (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
@Structure
public abstract class SwapBaseStructure extends BinanceItem {

    /**
     * {@code quoteAsset} quote asset of the swap
     **/
    protected final String quoteAsset;

    /**
     * {@code baseAsset} base asset of the swap
     **/
    protected final String baseAsset;

    /**
     * {@code price} of the swap
     **/
    protected final double price;

    /**
     * {@code fee} of the swap
     **/
    protected final double fee;

    /**
     * Constructor to init {@link SwapBaseStructure} object
     *
     * @param quoteAsset: quote asset of the swap
     * @param baseAsset:  base asset of the swap
     * @param price:      price of the swap
     * @param fee:        fee of the swap
     **/
    public SwapBaseStructure(String quoteAsset, String baseAsset, double price, double fee) {
        super(null);
        this.quoteAsset = quoteAsset;
        this.baseAsset = baseAsset;
        this.price = price;
        this.fee = fee;
    }

    /**
     * Constructor to init {@link SwapBaseStructure} object
     *
     * @param jSwapBaseStructure: swap base structure details as {@link JSONObject}
     **/
    public SwapBaseStructure(JSONObject jSwapBaseStructure) {
        super(jSwapBaseStructure);
        quoteAsset = hItem.getString("quoteAsset");
        baseAsset = hItem.getString("baseAsset");
        price = hItem.getDouble("price", 0);
        fee = hItem.getDouble("fee", 0);
    }

    /**
     * Method to get {@link #quoteAsset} instance <br>
     * No-any params required
     *
     * @return {@link #quoteAsset} instance as {@link String}
     **/
    public String getQuoteAsset() {
        return quoteAsset;
    }

    /**
     * Method to get {@link #baseAsset} instance <br>
     * No-any params required
     *
     * @return {@link #baseAsset} instance as {@link String}
     **/
    public String getBaseAsset() {
        return baseAsset;
    }

    /**
     * Method to get {@link #price} instance <br>
     * No-any params required
     *
     * @return {@link #price} instance as double
     **/
    public double getPrice() {
        return price;
    }

    /**
     * Method to get {@link #price} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #price} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPrice(int decimals) {
        return roundValue(price, decimals);
    }

    /**
     * Method to get {@link #fee} instance <br>
     * No-any params required
     *
     * @return {@link #fee} instance as double
     **/
    public double getFee() {
        return fee;
    }

    /**
     * Method to get {@link #fee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFee(int decimals) {
        return roundValue(fee, decimals);
    }

}
