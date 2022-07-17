package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;

/**
 *  The {@code AccountSnapshot} class is useful to manage AccountSnapshot Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
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
     * {@code accountDetails} is instance that memorizes account details in JSON format
     * **/
    protected final JSONArray accountDetails;

    /** Constructor to init {@link AccountSnapshot} object
     * @param code: code of response
     * @param msg: message of response
     * @param type: type of account
     * @param accountDetails: details in JSON format
     * **/
    public AccountSnapshot(int code, String msg, String type, JSONArray accountDetails) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.accountDetails = accountDetails;
    }

    /** Method to get specific AccountSnapshot object
     * any params required
     * @return AccountSnapshot object then to cast
     * **/
    public AccountSnapshot getAccountSnapshot(){
        switch (type){
            case SPOT:
                return new AccountSnapshotSpot(code, msg, type, accountDetails).getAccountSnapshotSpot();
            case MARGIN:
                return new AccountSnapshotMargin(code, msg, type, accountDetails).getAccountSnapshotMargin();
            default:
                return new AccountSnapshotFutures(code, msg, type, accountDetails).getAccountSnapshotFutures();
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

}
