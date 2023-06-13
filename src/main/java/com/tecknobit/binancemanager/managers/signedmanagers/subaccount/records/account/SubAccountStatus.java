package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code SubAccountStatus} class is useful to format a subaccount status
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-39-s-status-on-margin-futures-for-master-account">
 * Get Sub-account's Status on Margin/Futures (For Master Account)</a>
 * @see BinanceItem
 */
public class SubAccountStatus extends BinanceItem {

    /**
     * {@code email} of the subaccount
     */
    private final String email;

    /**
     * {@code isSubUserEnabled} whether the subaccount is enabled
     */
    private final boolean isSubUserEnabled;

    /**
     * {@code isUserActive} whether the subaccount is user active
     */
    private final boolean isUserActive;

    /**
     * {@code insertTime} insert time of the subaccount
     */
    private final long insertTime;

    /**
     * {@code isMarginEnabled} whether the subaccount is margin enabled
     */
    private final boolean isMarginEnabled;

    /**
     * {@code isFutureEnabled} whether the subaccount is future enabled
     */
    private final boolean isFutureEnabled;

    /**
     * {@code mobile} phone number of the subaccount
     */
    private final long mobile;

    /**
     * Constructor to init {@link SubAccountStatus} object
     *
     * @param email            : email of the subaccount
     * @param isSubUserEnabled : whether the subaccount is enabled
     * @param isUserActive     : whether the subaccount is user active
     * @param insertTime       : insert time of the subaccount
     * @param isMarginEnabled: whether the subaccount is margin enabled
     * @param isFutureEnabled  : whether the subaccount is future enabled
     * @param mobile           : mobile phone number of the subaccount
     */
    public SubAccountStatus(String email, boolean isSubUserEnabled, boolean isUserActive, long insertTime,
                            boolean isMarginEnabled, boolean isFutureEnabled, long mobile) {
        super(null);
        this.email = email;
        this.isSubUserEnabled = isSubUserEnabled;
        this.isUserActive = isUserActive;
        this.insertTime = insertTime;
        this.isMarginEnabled = isMarginEnabled;
        this.isFutureEnabled = isFutureEnabled;
        this.mobile = mobile;
    }

    /**
     * Constructor to init {@link SubAccountStatus} object
     *
     * @param jSubAccountStatus : subaccount status details as {@link JSONObject}
     */
    public SubAccountStatus(JSONObject jSubAccountStatus) {
        super(jSubAccountStatus);
        email = hItem.getString("email");
        isSubUserEnabled = hItem.getBoolean("isSubUserEnabled");
        isUserActive = hItem.getBoolean("isUserActive");
        insertTime = hItem.getLong("insertTime", 0);
        isMarginEnabled = hItem.getBoolean("isMarginEnabled");
        isFutureEnabled = hItem.getBoolean("isFutureEnabled");
        mobile = hItem.getLong("mobile", 0);
    }

    /**
     * Method to get {@link #email} instance <br>
     * No-any params required
     *
     * @return {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get {@link #isSubUserEnabled} instance <br>
     * No-any params required
     *
     * @return {@link #isSubUserEnabled} instance as boolean
     */
    public boolean isSubUserEnabled() {
        return isSubUserEnabled;
    }

    /**
     * Method to get {@link #isUserActive} instance <br>
     * No-any params required
     *
     * @return {@link #isUserActive} instance as boolean
     */
    public boolean isUserActive() {
        return isUserActive;
    }

    /**
     * Method to get {@link #insertTime} instance <br>
     * No-any params required
     *
     * @return {@link #insertTime} instance as long
     */
    public long getInsertTime() {
        return insertTime;
    }

    /**
     * Method to get {@link #insertTime} instance <br>
     * No-any params required
     *
     * @return {@link #insertTime} instance as {@link Date}
     */
    public Date getInsertDate() {
        return TimeFormatter.getDate(insertTime);
    }

    /**
     * Method to get {@link #isMarginEnabled} instance <br>
     * No-any params required
     *
     * @return {@link #isMarginEnabled} instance as boolean
     */
    public boolean isMarginEnabled() {
        return isMarginEnabled;
    }

    /**
     * Method to get {@link #isFutureEnabled} instance <br>
     * No-any params required
     *
     * @return {@link #isFutureEnabled} instance as boolean
     */
    public boolean isFutureEnabled() {
        return isFutureEnabled;
    }

    /**
     * Method to get {@link #mobile} instance <br>
     * No-any params required
     *
     * @return {@link #mobile} instance as long
     */
    public long getMobile() {
        return mobile;
    }

}
