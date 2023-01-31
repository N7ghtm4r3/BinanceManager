package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.SelfTradePreventionMode;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PreventedMatch} class is useful to format a {@code "Binance"}'s prevented match
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-prevented-matches-user_data">
 * Query Prevented Matches (USER_DATA)</a>
 **/
public class PreventedMatch {

    /**
     * {@code symbol} symbol of the match
     **/
    private final String symbol;

    /**
     * {@code preventedMatchId} prevented match identifier
     **/
    private final long preventedMatchId;

    /**
     * {@code takerOrderId} taker order identifier
     **/
    private final long takerOrderId;

    /**
     * {@code makerOrderId} maker order identifier
     **/
    private final long makerOrderId;

    /**
     * {@code tradeGroupId} trade group identifier
     **/
    private final long tradeGroupId;

    /**
     * {@code selfTradePreventionMode} self trade prevention mode value
     **/
    private final SelfTradePreventionMode selfTradePreventionMode;

    /**
     * {@code price} price value
     **/
    private final double price;

    /**
     * {@code makerPreventedQuantity} maker prevented quantity value
     **/
    private final double makerPreventedQuantity;

    /**
     * {@code transactTime} transaction time value
     **/
    private final long transactTime;

    /**
     * Constructor to init {@link PreventedMatch} object
     *
     * @param symbol:                    symbol of the match
     * @param preventedMatchId:prevented match identifier
     * @param takerOrderId:              taker order identifier
     * @param makerOrderId:              maker order identifier
     * @param tradeGroupId:              trade group identifier
     * @param selfTradePreventionMode:   self trade prevention mode value
     * @param price:                     price value
     * @param makerPreventedQuantity:    maker prevented quantity value
     * @param transactTime:              transaction time value
     **/
    public PreventedMatch(String symbol, long preventedMatchId, long takerOrderId, long makerOrderId, long tradeGroupId,
                          SelfTradePreventionMode selfTradePreventionMode, double price, double makerPreventedQuantity,
                          long transactTime) {
        this.symbol = symbol;
        this.preventedMatchId = preventedMatchId;
        this.takerOrderId = takerOrderId;
        this.makerOrderId = makerOrderId;
        this.tradeGroupId = tradeGroupId;
        this.selfTradePreventionMode = selfTradePreventionMode;
        this.price = price;
        this.makerPreventedQuantity = makerPreventedQuantity;
        this.transactTime = transactTime;
    }

    /**
     * Constructor to init {@link PreventedMatch} object
     *
     * @param jPreventedMatch: prevented match details as {@link JSONObject}
     **/
    public PreventedMatch(JSONObject jPreventedMatch) {
        JsonHelper hPreventedMatch = new JsonHelper(jPreventedMatch);
        symbol = hPreventedMatch.getString("symbol");
        preventedMatchId = hPreventedMatch.getLong("preventedMatchId", 0);
        takerOrderId = hPreventedMatch.getLong("takerOrderId", 0);
        makerOrderId = hPreventedMatch.getLong("makerOrderId", 0);
        tradeGroupId = hPreventedMatch.getLong("tradeGroupId", 0);
        selfTradePreventionMode = SelfTradePreventionMode.valueOf(hPreventedMatch.getString("selfTradePreventionMode"));
        price = hPreventedMatch.getDouble("price", 0);
        makerPreventedQuantity = hPreventedMatch.getDouble("makerPreventedQuantity", 0);
        transactTime = hPreventedMatch.getLong("transactTime", 0);
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
     * Method to get {@link #preventedMatchId} instance <br>
     * No-any params required
     *
     * @return {@link #preventedMatchId} instance as long
     **/
    public long getPreventedMatchId() {
        return preventedMatchId;
    }

    /**
     * Method to get {@link #takerOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #takerOrderId} instance as long
     **/
    public long getTakerOrderId() {
        return takerOrderId;
    }

    /**
     * Method to get {@link #makerOrderId} instance <br>
     * No-any params required
     *
     * @return {@link #makerOrderId} instance as long
     **/
    public long getMakerOrderId() {
        return makerOrderId;
    }

    /**
     * Method to get {@link #tradeGroupId} instance <br>
     * No-any params required
     *
     * @return {@link #tradeGroupId} instance as long
     **/
    public long getTradeGroupId() {
        return tradeGroupId;
    }

    /**
     * Method to get {@link #selfTradePreventionMode} instance <br>
     * No-any params required
     *
     * @return {@link #selfTradePreventionMode} instance as {@link SelfTradePreventionMode}
     **/
    public SelfTradePreventionMode getSelfTradePreventionMode() {
        return selfTradePreventionMode;
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
     * Method to get {@link #makerPreventedQuantity} instance <br>
     * No-any params required
     *
     * @return {@link #makerPreventedQuantity} instance as double
     **/
    public double getMakerPreventedQuantity() {
        return makerPreventedQuantity;
    }

    /**
     * Method to get {@link #makerPreventedQuantity} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #makerPreventedQuantity} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMakerPreventedQuantity(int decimals) {
        return roundValue(makerPreventedQuantity, decimals);
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactTime} instance as long
     **/
    public long getTransactTime() {
        return transactTime;
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactTime} instance as {@link Date}
     **/
    public Date getTransactDate() {
        return TimeFormatter.getDate(transactTime);
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
