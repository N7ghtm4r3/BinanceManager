package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.userdatastreams.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.SpotBalance;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.getBalancesSpot;

/**
 * The {@code AccountUpdate} class is useful to format a user account update
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#payload-account-update">
 * Payload: Account Update</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
public class AccountUpdate extends BinanceWebsocketResponse {

    /**
     * {@code lastAccountUpdate} time of the last account update
     **/
    private final long lastAccountUpdate;

    /**
     * {@code balances} of the account update
     **/
    private final ArrayList<SpotBalance> balances;

    /**
     * Constructor to init {@link AccountUpdate} object
     *
     * @param eventType:         event type of the account update
     * @param eventTime:         event time of the account update
     * @param lastAccountUpdate: time of the last account update
     * @param balances:          balances of the account update
     **/
    public AccountUpdate(EventType eventType, long eventTime, long lastAccountUpdate, ArrayList<SpotBalance> balances) {
        super(eventType, eventTime);
        this.lastAccountUpdate = lastAccountUpdate;
        this.balances = balances;
    }

    /**
     * Constructor to init {@link AccountUpdate} object
     *
     * @param jAccountUpdate: account update details as {@link JSONObject}
     **/
    public AccountUpdate(JSONObject jAccountUpdate) {
        super(jAccountUpdate);
        lastAccountUpdate = hItem.getLong("u", 0);
        JSONArray jBalances = hItem.getJSONArray("B", new JSONArray());
        jBalances = new JSONArray(jBalances.toString().replaceAll("\"a\"", "asset")
                .replaceAll("\"f\"", "free")
                .replaceAll("\"l\"", "locked"));
        balances = getBalancesSpot(jBalances);
    }

    /**
     * Method to get {@link #lastAccountUpdate} instance <br>
     * No-any params required
     *
     * @return {@link #lastAccountUpdate} instance as long
     **/
    public long getLastAccountUpdate() {
        return lastAccountUpdate;
    }

    /**
     * Method to get {@link #lastAccountUpdate} instance <br>
     * No-any params required
     *
     * @return {@link #lastAccountUpdate} instance as {@link Date}
     **/
    public Date getLastAccountUpdateDate() {
        return TimeFormatter.getDate(lastAccountUpdate);
    }

    /**
     * Method to get {@link #balances} instance <br>
     * No-any params required
     *
     * @return {@link #balances} instance as {@link ArrayList} of {@link SpotBalance}
     **/
    public ArrayList<SpotBalance> getBalances() {
        return balances;
    }

}
