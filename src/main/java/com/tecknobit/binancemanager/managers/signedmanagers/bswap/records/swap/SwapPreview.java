package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SwapPreview} class is useful to format a swap preview
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#add-liquidity-preview-user_data">
 *             Add Liquidity Preview (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#remove-liquidity-preview-user_data">
 *             Remove Liquidity Preview (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see SwapBaseStructure
 **/
public class SwapPreview extends SwapBaseStructure {

    /**
     * {@code quoteAmount} quote amount of the swap preview
     **/
    private final double quoteAmount;

    /**
     * {@code baseAmount} quote amount of the swap preview
     **/
    private final double baseAmount;

    /**
     * {@code share} of the swap preview
     **/
    private final double share;

    /**
     * {@code slippage} of the swap preview
     **/
    private final double slippage;

    /**
     * Constructor to init {@link SwapPreview} object
     *
     * @param quoteAsset:  quote asset of the swap preview
     * @param baseAsset:   base asset of the swap preview
     * @param price:       price of the swap preview
     * @param fee:         fee of the swap preview
     * @param quoteAmount: quote amount of the swap preview
     * @param baseAmount:  base amount of the swap preview
     * @param share:       share of the swap preview
     * @param slippage:    slippage of the swap preview
     **/
    public SwapPreview(String quoteAsset, String baseAsset, double price, double fee, double quoteAmount,
                       double baseAmount, double share, double slippage) {
        super(quoteAsset, baseAsset, price, fee);
        this.quoteAmount = quoteAmount;
        this.baseAmount = baseAmount;
        this.share = share;
        this.slippage = slippage;
    }

    /**
     * Constructor to init {@link SwapPreview} object
     *
     * @param jSwapPreview: swap preview details as {@link JSONObject}
     **/
    public SwapPreview(JSONObject jSwapPreview) {
        super(jSwapPreview);
        quoteAmount = hItem.getDouble("quoteAmt", 0);
        baseAmount = hItem.getDouble("baseAmt", 0);
        share = hItem.getDouble("share", 0);
        slippage = hItem.getDouble("slippage", 0);
    }

    /**
     * Method to get {@link #quoteAmount} instance <br>
     * No-any params required
     *
     * @return {@link #quoteAmount} instance as double
     **/
    public double getQuoteAmount() {
        return quoteAmount;
    }

    /**
     * Method to get {@link #quoteAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #quoteAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getQuoteAmount(int decimals) {
        return roundValue(quoteAmount, decimals);
    }

    /**
     * Method to get {@link #baseAmount} instance <br>
     * No-any params required
     *
     * @return {@link #baseAmount} instance as double
     **/
    public double getBaseAmount() {
        return baseAmount;
    }

    /**
     * Method to get {@link #baseAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #baseAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBaseAmount(int decimals) {
        return roundValue(baseAmount, decimals);
    }

    /**
     * Method to get {@link #share} instance <br>
     * No-any params required
     *
     * @return {@link #share} instance as double
     **/
    public double getShare() {
        return share;
    }

    /**
     * Method to get {@link #share} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #share} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getShare(int decimals) {
        return roundValue(share, decimals);
    }

    /**
     * Method to get {@link #slippage} instance <br>
     * No-any params required
     *
     * @return {@link #slippage} instance as double
     **/
    public double getSlippage() {
        return slippage;
    }

    /**
     * Method to get {@link #slippage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #slippage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getSlippage(int decimals) {
        return roundValue(slippage, decimals);
    }

}
