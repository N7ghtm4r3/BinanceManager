package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account;

import com.tecknobit.binancemanager.managers.market.records.trade.Trade;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SpotAccountTradesList} class is useful to format a {@code "Binance"}'s spot account trades list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
 * Account Trade List (USER_DATA)</a>
 * @see Trade
 */
public class SpotAccountTradesList extends Trade {

    /**
     * {@code symbol} is instance that memorizes symbol
     */
    private final String symbol;

    /**
     * {@code orderId} is instance that memorizes order identifier
     */
    private final long orderId;

    /**
     * {@code orderListId} is instance that memorizes order list identifier
     */
    private final long orderListId;

    /**
     * {@code commission} is instance that memorizes commission value
     */
    private final double commission;

    /**
     * {@code commissionAsset} is instance that memorizes commission asset value
     */
    private final String commissionAsset;

    /**
     * {@code isMaker} is instance that memorizes if is maker
     */
    private final boolean isMaker;

    /**
     * Constructor to init {@link SpotAccountTradesList} object
     *
     * @param id:              trade id
     * @param price:           price in trade
     * @param qty:             quantity in trade
     * @param quoteQty:        quote quantity in trade
     * @param time:            time of the trade
     * @param isBuyerMaker:    trade is buyer maker
     * @param isBestMatch:     is best match of trade
     * @param symbol:          symbol
     * @param orderId:         order identifier
     * @param orderListId:     order list identifier
     * @param commission:      commission value
     * @param commissionAsset: commission asset value
     * @param isMaker:         is maker
     */
    public SpotAccountTradesList(String symbol, long id, long orderId, long orderListId, double price, double qty,
                                 double quoteQty, double commission, String commissionAsset, long time,
                                 boolean isBuyerMaker, boolean isMaker, boolean isBestMatch) {
        super(id, price, qty, quoteQty, time, isBuyerMaker, isBestMatch);
        this.symbol = symbol;
        this.orderId = orderId;
        this.orderListId = orderListId;
        this.commission = commission;
        this.commissionAsset = commissionAsset;
        this.isMaker = isMaker;
    }

    /**
     * Constructor to init {@link SpotAccountTradesList} object
     *
     * @param spotAccountTradeList: spot account trade list details as {@link JSONObject}
     */
    public SpotAccountTradesList(JSONObject spotAccountTradeList) {
        super(spotAccountTradeList);
        symbol = spotAccountTradeList.getString("symbol");
        orderId = spotAccountTradeList.getLong("orderId");
        orderListId = spotAccountTradeList.getLong("orderListId");
        commission = spotAccountTradeList.getDouble("commission");
        commissionAsset = spotAccountTradeList.getString("commissionAsset");
        isMaker = spotAccountTradeList.getBoolean("isMaker");
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #orderId} instance <br>
     * No-any params required
     *
     * @return {@link #orderId} instance as long
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Method to get {@link #orderListId} instance <br>
     * No-any params required
     *
     * @return {@link #orderListId} instance as long
     */
    public long getOrderListId() {
        return orderListId;
    }

    /**
     * Method to get {@link #commission} instance <br>
     * No-any params required
     *
     * @return {@link #commission} instance as double
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Method to get {@link #commission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #commission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getCommission(int decimals) {
        return roundValue(commission, decimals);
    }

    /**
     * Method to get {@link #commissionAsset} instance <br>
     * No-any params required
     *
     * @return {@link #commissionAsset} instance as {@link String}
     */
    public String getCommissionAsset() {
        return commissionAsset;
    }

    /**
     * Method to get {@link #isMaker} instance <br>
     * No-any params required
     *
     * @return {@link #isMaker} instance as boolean
     */
    public boolean isMaker() {
        return isMaker;
    }

}
