package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat;

/**
 * The {@code AccountSnapshot} class is useful to format a {@code "Binance"}'s account snapshot
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * Daily Account Snapshot (USER_DATA)</a>
 */
public class AccountSnapshot {

    /**
     * {@code code} is instance that memorizes code of response
     */
    protected final int code;

    /**
     * {@code msg} is instance that memorizes message of response
     */
    protected final String msg;

    /**
     * {@code type} is instance that memorizes type of account
     */
    protected final AccountType type;

    /**
     * {@code snapshotVos} is instance that memorizes account details as {@link JSONArray}
     */
    protected final JSONArray snapshotVos;

    /**
     * Constructor to init {@link AccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param type:        type of account
     * @param snapshotVos: details as {@link JSONArray}
     */
    public AccountSnapshot(int code, String msg, AccountType type, JSONArray snapshotVos) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.snapshotVos = snapshotVos;
    }

    /**
     * Constructor to init {@link AccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param type:        type of account
     * @param snapshotVos: details as {@link String}
     * @apiNote this constructor is useful to pass a {@code "JSON"} array in {@link String} format
     */
    public AccountSnapshot(int code, String msg, AccountType type, String snapshotVos) {
        this(code, msg, type, new JSONArray(snapshotVos));
    }

    /**
     * Constructor to init {@link AccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param type:        type of account
     * @param snapshotVos: details as {@link String}
     * @apiNote this constructor is useful to pass a {@code "JSON"} array in {@link String} format
     */
    public <T> AccountSnapshot(int code, String msg, AccountType type, T snapshotVos) {
        this(code, msg, type, new JSONArray(snapshotVos.toString()));
    }

    /**
     * Constructor to init {@link AccountSnapshot} object
     *
     * @param accountSnapshot: account snapshot details as {@link JSONObject}
     */
    public AccountSnapshot(JSONObject accountSnapshot) {
        JsonHelper hAccount = new JsonHelper(accountSnapshot);
        code = hAccount.getInt("code", 0);
        msg = hAccount.getString("msg");
        snapshotVos = hAccount.getJSONArray("snapshotVos", new JSONArray());
        hAccount.setJSONArraySource(snapshotVos);
        type = AccountType.valueOf(hAccount.getJSONObject(0).getString("type").toLowerCase());
    }

    /**
     * Method to get {@link #code} instance <br>
     * No-any params required
     *
     * @return {@link #code} instance as int
     */
    public int getCode() {
        return code;
    }

    /**
     * Method to get {@link #msg} instance <br>
     * No-any params required
     *
     * @return {@link #msg} instance as {@link String}
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link AccountType}
     */
    public AccountType getType() {
        return type;
    }

    /**
     * Method to get {@link #snapshotVos} instance <br>
     * No-any params required
     *
     * @return {@link #snapshotVos} instance as {@link JSONArray}
     */
    public JSONArray getSnapshotVos() {
        return snapshotVos;
    }

    /**
     * Method to get {@link #snapshotVos} instance <br>
     * No-any params required
     *
     * @return {@link #snapshotVos} instance as {@link String}
     */
    public String getSnapshotVosStringed() {
        return snapshotVos.toString();
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

    /**
     * Method to create an account object
     *
     * @param type:            SPOT, MARGIN OR FUTURES
     * @param accountResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return account as {@code "format"} defines
     */
    @Returner
    public static <T> T returnAccountSnapshot(AccountType type, String accountResponse, ReturnFormat format) {
        JSONObject jResponse = new JSONObject(accountResponse);
        switch (format) {
            case JSON:
                return (T) jResponse;
            case LIBRARY_OBJECT:
                switch (type) {
                    case spot:
                        return (T) new SpotAccountSnapshot(jResponse);
                    case margin:
                        return (T) new MarginAccountSnapshot(jResponse);
                    default:
                        return (T) new FuturesAccountSnapshot(jResponse);
                }
            default:
                return (T) accountResponse;
        }
    }

    /**
     * {@code AccountType} list of available account types
     */
    public enum AccountType {

        /**
         * {@code "spot"} account type
         */
        spot,

        /**
         * {@code "margin"} account type
         */
        margin,

        /**
         * {@code "futures"} account type
         */
        futures

    }

    /**
     * {@code PrincipalAccountType} list of available principal account types
     */
    public enum PrincipalAccountType {

        /**
         * {@code SPOT} principal account type
         */
        SPOT,

        /**
         * {@code USDT_FUTURE} principal account type
         */
        USDT_FUTURE,

        /**
         * {@code COIN_FUTURE} principal account type
         */
        COIN_FUTURE,

        /**
         * {@code MARGIN} principal account type
         */
        MARGIN,

        /**
         * {@code ISOLATED_MARGIN} principal account type
         */
        ISOLATED_MARGIN

    }

}
