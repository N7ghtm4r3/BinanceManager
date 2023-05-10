package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code RiskStructure} class is useful to format a risk structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
 *             Get Futures Position-Risk of Sub-account (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
 *             Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
@Structure
public abstract class RiskStructure extends BinanceItem {

    /**
     * {@code entryPrice} entry price of the risk structure
     **/
    protected final double entryPrice;

    /**
     * {@code leverage} of the risk structure
     **/
    protected final double leverage;

    /**
     * {@code markPrice} mark price of the risk structure
     **/
    protected final double markPrice;

    /**
     * {@code positionAmount} position amount of the risk structure
     **/
    protected final double positionAmount;

    /**
     * {@code symbol} of the risk structure
     **/
    protected final String symbol;

    /**
     * {@code unrealizedProfit} unrealized profit of the risk structure
     **/
    protected final double unrealizedProfit;

    /**
     * Constructor to init {@link RiskStructure} object
     *
     * @param entryPrice:       entry price of the risk structure
     * @param leverage:         leverage of the risk structure
     * @param markPrice:        mark price of the risk structure
     * @param positionAmount:   position amount of the risk structure
     * @param symbol:           symbol of the risk structure
     * @param unrealizedProfit: unrealized profit of the risk structure
     **/
    public RiskStructure(double entryPrice, double leverage, double markPrice, double positionAmount, String symbol,
                         double unrealizedProfit) {
        super(null);
        this.entryPrice = entryPrice;
        this.leverage = leverage;
        this.markPrice = markPrice;
        this.positionAmount = positionAmount;
        this.symbol = symbol;
        this.unrealizedProfit = unrealizedProfit;
    }

    /**
     * Constructor to init {@link RiskStructure} object
     *
     * @param jFuturesPositionRisk: futures position risk details as {@link JSONObject}
     **/
    public RiskStructure(JSONObject jFuturesPositionRisk) {
        super(jFuturesPositionRisk);
        entryPrice = hItem.getDouble("entryPrice", 0);
        leverage = hItem.getDouble("leverage", 0);
        markPrice = hItem.getDouble("markPrice", 0);
        positionAmount = hItem.getDouble("positionAmount", 0);
        symbol = hItem.getString("symbol");
        unrealizedProfit = hItem.getDouble("unrealizedProfit", 0);
    }

    /**
     * Method to get {@link #entryPrice} instance <br>
     * No-any params required
     *
     * @return {@link #entryPrice} instance as double
     **/
    public double getEntryPrice() {
        return entryPrice;
    }

    /**
     * Method to get {@link #entryPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #entryPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getEntryPrice(int decimals) {
        return roundValue(entryPrice, decimals);
    }

    /**
     * Method to get {@link #leverage} instance <br>
     * No-any params required
     *
     * @return {@link #leverage} instance as double
     **/
    public double getLeverage() {
        return leverage;
    }

    /**
     * Method to get {@link #leverage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #leverage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLeverage(int decimals) {
        return roundValue(leverage, decimals);
    }

    /**
     * Method to get {@link #markPrice} instance <br>
     * No-any params required
     *
     * @return {@link #markPrice} instance as double
     **/
    public double getMarkPrice() {
        return markPrice;
    }

    /**
     * Method to get {@link #markPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #markPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMarkPrice(int decimals) {
        return roundValue(markPrice, decimals);
    }

    /**
     * Method to get {@link #positionAmount} instance <br>
     * No-any params required
     *
     * @return {@link #positionAmount} instance as double
     **/
    public double getPositionAmount() {
        return positionAmount;
    }

    /**
     * Method to get {@link #positionAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #positionAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPositionAmount(int decimals) {
        return roundValue(positionAmount, decimals);
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     **/
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #unrealizedProfit} instance <br>
     * No-any params required
     *
     * @return {@link #unrealizedProfit} instance as double
     **/
    public double getUnrealizedProfit() {
        return unrealizedProfit;
    }

    /**
     * Method to get {@link #unrealizedProfit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #unrealizedProfit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getUnrealizedProfit(int decimals) {
        return roundValue(unrealizedProfit, decimals);
    }

}
