package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BalanceUpdate} class is useful to format a user balance update
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#payload-balance-update">
 * Payload: Balance Update</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
public class BalanceUpdate extends BinanceWebsocketResponse {

    /**
     * {@code asset} of the balance update
     **/
    private final String asset;

    /**
     * {@code balanceDelta} balance delta of the balance update
     **/
    private final double balanceDelta;

    /**
     * {@code clearTime} clear time of the balance update
     **/
    private final long clearTime;

    /**
     * Constructor to init {@link BalanceUpdate} object
     *
     * @param eventType:    event type of the balance update
     * @param eventTime:    event time of the balance update
     * @param asset:        asset of the balance update
     * @param balanceDelta: balance delta of the balance update
     * @param clearTime:    clear time of the balance update
     **/
    public BalanceUpdate(EventType eventType, long eventTime, String asset, double balanceDelta, long clearTime) {
        super(eventType, eventTime);
        this.asset = asset;
        this.balanceDelta = balanceDelta;
        this.clearTime = clearTime;
    }

    /**
     * Constructor to init {@link BalanceUpdate} object
     *
     * @param jBalanceUpdate: balance update details as {@link JSONObject}
     **/
    public BalanceUpdate(JSONObject jBalanceUpdate) {
        super(jBalanceUpdate);
        asset = hItem.getString("a");
        balanceDelta = hItem.getDouble("d", 0);
        clearTime = hItem.getLong("T", 0);
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     **/
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #balanceDelta} instance <br>
     * No-any params required
     *
     * @return {@link #balanceDelta} instance as double
     **/
    public double getBalanceDelta() {
        return balanceDelta;
    }

    /**
     * Method to get {@link #balanceDelta} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #balanceDelta} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBalanceDelta(int decimals) {
        return roundValue(balanceDelta, decimals);
    }

    /**
     * Method to get {@link #clearTime} instance <br>
     * No-any params required
     *
     * @return {@link #clearTime} instance as long
     **/
    public long getClearTime() {
        return clearTime;
    }

    /**
     * Method to get {@link #clearTime} instance <br>
     * No-any params required
     *
     * @return {@link #clearTime} instance as {@link Date}
     **/
    public Date getClearDate() {
        return TimeFormatter.getDate(clearTime);
    }

}
