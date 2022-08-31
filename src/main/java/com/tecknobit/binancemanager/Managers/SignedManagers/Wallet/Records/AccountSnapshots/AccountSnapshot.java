package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;

/**
 *  The {@code AccountSnapshot} class is useful to manage AccountSnapshot Binance request
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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
     * **/
    protected final String type;

    /**
     * {@code accountDetails} is instance that memorizes account details as {@link JSONObject}
     **/
    protected final JSONArray accountDetails;

    /** Constructor to init {@link AccountSnapshot} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details as {@link JSONObject}
     * **/
    public AccountSnapshot(int code, String msg, String type, JSONArray accountDetails) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.accountDetails = accountDetails;
    }

    /** Method to get specific AccountSnapshot object <br>
     * Any params required
     * @return AccountSnapshot object then to cast
     * **/
    public <T extends AccountSnapshot> T getAccountSnapshot(){
        switch (type){
            case SPOT:
                return (T) new AccountSnapshotSpot(code, msg, type, accountDetails);
            case MARGIN:
                return (T) new AccountSnapshotMargin(code, msg, type, accountDetails);
            default:
                return (T) new AccountSnapshotFutures(code, msg, type, accountDetails);
        }
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

    public JSONArray getAccountDetails() {
        return accountDetails;
    }

    @Override
    public String toString() {
        return "AccountSnapshot{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", accountDetails=" + accountDetails +
                '}';
    }

}
