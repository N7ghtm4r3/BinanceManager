package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code SubAccountEnabledResult} class is useful to format a subaccount enabled result
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-margin-for-sub-account-for-master-account">
 *             Enable Margin for Sub-account (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-futures-for-sub-account-for-master-account">
 *             Enable Futures for Sub-account (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#enable-leverage-token-for-sub-account-for-master-account">
 *             Enable Leverage Token for Sub-account (For Master Account)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
public class SubAccountEnabledResult extends BinanceItem {

    /**
     * {@code email} of the subaccount
     */
    private final String email;

    /**
     * {@code isAccountEnabled} whether the subaccount has been enabled
     */
    private final boolean isAccountEnabled;

    /**
     * Constructor to init {@link SubAccountEnabledResult} object
     *
     * @param email            : email of the subaccount
     * @param isAccountEnabled : whether the subaccount has been enabled
     */
    public SubAccountEnabledResult(String email, boolean isAccountEnabled) {
        super(null);
        this.email = email;
        this.isAccountEnabled = isAccountEnabled;
    }

    /**
     * Constructor to init {@link SubAccountEnabledResult} object
     *
     * @param jSubAccountEnabledResult : sub account enabled result details as {@link JSONObject}
     */
    public SubAccountEnabledResult(JSONObject jSubAccountEnabledResult) {
        super(jSubAccountEnabledResult);
        email = hItem.getString("email");
        boolean enabled = hItem.getBoolean("isMarginEnabled");
        if (!enabled) {
            enabled = hItem.getBoolean("isFuturesEnabled");
            if (!enabled)
                isAccountEnabled = hItem.getBoolean("enableBlvt");
            else
                isAccountEnabled = true;
        } else
            isAccountEnabled = true;
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
     * Method to get {@link #isAccountEnabled} instance <br>
     * No-any params required
     *
     * @return {@link #isAccountEnabled} instance as boolean
     */
    public boolean isAccountEnabled() {
        return isAccountEnabled;
    }

}
