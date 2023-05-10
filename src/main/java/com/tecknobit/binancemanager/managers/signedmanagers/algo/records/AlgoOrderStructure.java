package com.tecknobit.binancemanager.managers.signedmanagers.algo.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code AlgoOrderStructure} class is useful to create an algo order base structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data">
 *             Query Current Algo Open Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data">
 *             Query Historical Algo Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data">
 *             Query Sub Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-orders-user_data-2">
 *             Query Sub Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-algo-open-orders-user_data-2">
 *             Query Current Algo Open Orders (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-historical-algo-orders-user_data-2">
 *             Query Historical Algo Orders (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
@Structure
public abstract class AlgoOrderStructure extends BinanceItem {

    /**
     * {@code algoId} id of the algo order
     **/
    protected final long algoId;

    /**
     * {@code symbol} of the algo order
     **/
    protected final String symbol;

    /**
     * {@code side} of the algo order
     **/
    protected final Side side;

    /**
     * {@code executedQty} executed quantity of the algo order
     **/
    protected final double executedQty;

    /**
     * {@code executedAmt} executed amount of the algo order
     **/
    protected final double executedAmt;

    /**
     * {@code avgPrice} average price of the algo order
     **/
    protected final double avgPrice;

    /**
     * {@code bookTime} book time of the algo order
     **/
    protected final long bookTime;

    /**
     * Constructor to init {@link AlgoOrderStructure}
     *
     * @param algoId      : id of the algo order
     * @param symbol      : symbol of the algo order
     * @param side        : side of the algo order
     * @param executedQty : executed quantity of the algo order
     * @param executedAmt : executed amount of the algo order
     * @param avgPrice    : average price of the algo order
     * @param bookTime    : book time of the algo order
     **/
    public AlgoOrderStructure(long algoId, String symbol, Side side, double executedQty, double executedAmt,
                              double avgPrice, long bookTime) {
        super(null);
        this.algoId = algoId;
        this.symbol = symbol;
        this.side = side;
        this.executedQty = executedQty;
        this.executedAmt = executedAmt;
        this.avgPrice = avgPrice;
        this.bookTime = bookTime;
    }

    /**
     * Constructor to init {@link AlgoOrderStructure}
     *
     * @param jAlgoOrderStructure : algo order structure details as {@link JSONObject}
     **/
    public AlgoOrderStructure(JSONObject jAlgoOrderStructure) {
        super(jAlgoOrderStructure);
        algoId = hItem.getLong("algoId", 0);
        symbol = hItem.getString("symbol");
        side = Side.valueOf(hItem.getString("side"));
        executedQty = hItem.getDouble("executedQty", 0);
        executedAmt = hItem.getDouble("executedAmt", 0);
        avgPrice = hItem.getDouble("avgPrice", 0);
        bookTime = hItem.getLong("bookTime", 0);
    }

    /**
     * Method to get {@link #algoId} instance <br>
     * No-any params required
     *
     * @return {@link #algoId} instance as long
     **/
    public long getAlgoId() {
        return algoId;
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
     * Method to get {@link #side} instance <br>
     * No-any params required
     *
     * @return {@link #side} instance as {@link Side}
     **/
    public Side getSide() {
        return side;
    }

    /**
     * Method to get {@link #executedQty} instance <br>
     * No-any params required
     *
     * @return {@link #executedQty} instance as double
     **/
    public double getExecutedQty() {
        return executedQty;
    }

    /**
     * Method to get {@link #executedQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #executedQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getExecutedQty(int decimals) {
        return roundValue(executedQty, decimals);
    }

    /**
     * Method to get {@link #executedAmt} instance <br>
     * No-any params required
     *
     * @return {@link #executedAmt} instance as double
     **/
    public double getExecutedAmt() {
        return executedAmt;
    }

    /**
     * Method to get {@link #executedAmt} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #executedAmt} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getExecutedAmt(int decimals) {
        return roundValue(executedAmt, decimals);
    }

    /**
     * Method to get {@link #avgPrice} instance <br>
     * No-any params required
     *
     * @return {@link #avgPrice} instance as double
     **/
    public double getAvgPrice() {
        return avgPrice;
    }

    /**
     * Method to get {@link #avgPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #avgPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAvgPrice(int decimals) {
        return roundValue(avgPrice, decimals);
    }

    /**
     * Method to get {@link #bookTime} instance <br>
     * No-any params required
     *
     * @return {@link #bookTime} instance as long
     **/
    public long getBookTime() {
        return bookTime;
    }

    /**
     * Method to get {@link #bookTime} instance <br>
     * No-any params required
     *
     * @return {@link #bookTime} instance as {@link Date}
     **/
    public Date getBookDate() {
        return TimeFormatter.getDate(bookTime);
    }

}
