package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;

/**
 *  The {@code AccountSnapshot} class is useful to manage AccountSnapshot Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class AccountSnapshot {

    public static final String SPOT = "SPOT";
    public static final String MARGIN = "MARGIN";
    public static final String FUTURES = "FUTURES";
    private final int code;
    private final String msg;
    private final String type;
    private final JSONArray jsonArray;

    public AccountSnapshot(int code, String msg, String type, JSONArray jsonArray) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.jsonArray = jsonArray;
    }

    /** Method to get specifical AccountSnapshot object
     * any params required
     * return AccountSnapshot object then to cast
     * **/
    public AccountSnapshot getAccountSnapshot(){
        switch (type){
            case SPOT:
                return new AccountSnapshotSpot(code,msg,type,jsonArray).getAccountSnapshotSpot();
            case MARGIN:
                return new AccountSnapshotMargin(code,msg,type,jsonArray).getAccountSnapshotMargin();
            default:
                return new AccountSnapshotFutures(code,msg,type,jsonArray).getAccountSnapshotFutures();
        }
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

}
