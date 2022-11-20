package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;

/**
 * The {@code AccountSnapshot} class is useful to manage AccountSnapshot {@code "Binance"} request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 **/

public class AccountSnapshot {

    /**
     * {@code SPOT} is constant for spot type account
     * **/
    public static final String SPOT = "SPOT";

    /**
     * {@code MARGIN} is constant for margin type account
     * **/
    public static final String MARGIN = "MARGIN";

    /**
     * {@code FUTURES} is constant for futures type account
     * **/
    public static final String FUTURES = "FUTURES";

    /**
     * {@code code} is instance that memorizes code of response
     * **/
    protected int code;

    /**
     * {@code msg} is instance that memorizes message of response
     * **/
    protected String msg;

    /**
     * {@code type} is instance that memorizes type of account
     **/
    protected final String type;

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
    public AccountSnapshot(int code, String msg, String type, JSONArray snapshotVos) {
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
    public AccountSnapshot(JSONObject accountSnapshot, String type) {
        snapshotVos = getJSONArray(accountSnapshot, "snapshotVos", new JSONArray());
        code = accountSnapshot.getInt("code");
        msg = accountSnapshot.getString("msg");
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public JSONArray getSnapshotVos() {
        return snapshotVos;
    }

    @Override
    public String toString() {
        return "AccountSnapshot{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
