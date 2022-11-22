package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;

/**
 * The {@code AccountSnapshot} class is useful to format a {@code "Binance"}'s account snapshot
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * Daily Account Snapshot (USER_DATA)</a>
 **/
public class AccountSnapshot {

    /**
     * {@code SPOT} is constant for spot type account
     **/
    public static final String SPOT = "SPOT";
    /**
     * {@code MARGIN} is constant for margin type account
     **/
    public static final String MARGIN = "MARGIN";
    /**
     * {@code code} is instance that memorizes code of response
     **/
    protected final int code;

    /**
     * {@code FUTURES} is constant for futures type account
     **/
    public static final String FUTURES = "FUTURES";
    /**
     * {@code msg} is instance that memorizes message of response
     **/
    protected final String msg;
    /**
     * {@code type} is instance that memorizes type of account
     **/
    protected final AccountType type;
    /**
     * {@code snapshotVos} is instance that memorizes account details as {@link JSONArray}
     **/
    protected final JSONArray snapshotVos;

    /**
     * Constructor to init {@link AccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param type:        type of account
     * @param snapshotVos: details as {@link JSONObject}
     **/
    // TODO: 22/11/2022 TRANSFORM JSONARRAY IN SOMEONE ELSE
    public AccountSnapshot(int code, String msg, AccountType type, JSONArray snapshotVos) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.snapshotVos = snapshotVos;
    }

    /**
     * Constructor to init {@link AccountSnapshot} object
     *
     * @param accountSnapshot: account snapshot details as {@link JSONObject}
     **/
    // TODO: 22/11/2022 CHECK TO REMOVE TYPE
    public AccountSnapshot(JSONObject accountSnapshot, AccountType type) {
        snapshotVos = getJSONArray(accountSnapshot, "snapshotVos", new JSONArray());
        code = accountSnapshot.getInt("code");
        msg = accountSnapshot.getString("msg");
        this.type = type;
    }

    /**
     * Method to get {@link #code} instance <br>
     * Any params required
     *
     * @return {@link #code} instance as int
     **/
    public int getCode() {
        return code;
    }

    /**
     * Method to get {@link #msg} instance <br>
     * Any params required
     *
     * @return {@link #msg} instance as {@link String}
     **/
    public String getMsg() {
        return msg;
    }

    /**
     * Method to get {@link #type} instance <br>
     * Any params required
     *
     * @return {@link #type} instance as {@link AccountType}
     **/
    public AccountType getType() {
        return type;
    }

    /**
     * Method to get {@link #snapshotVos} instance <br>
     * Any params required
     *
     * @return {@link #snapshotVos} instance as {@link JSONArray}
     **/
    // TODO: 22/11/2022 CHECK TO REMOVE TYPE
    public JSONArray getSnapshotVos() {
        return snapshotVos;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * {@code AccountType} list of available account types
     **/
    public enum AccountType {

        /**
         * {@code "SPOT"} account type
         **/
        SPOT,

        /**
         * {@code "MARGIN"} account type
         **/
        MARGIN,

        /**
         * {@code "FUTURES"} account type
         **/
        FUTURES

    }

}
