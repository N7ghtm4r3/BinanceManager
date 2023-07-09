package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code SubAccountOptions} class is useful to format a {@code Binance}'s subaccount options
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-managed-sub-account-deposit-address-for-investor-master-account-user_data">
 * Enable Options for Sub-account (For Master Account)(USER_DATA)</a>
 * @see BinanceItem
 */
public class SubAccountOptions extends BinanceItem {

    /**
     * {@code email} of the subaccount
     */
    private final String email;

    /**
     * {@code isOptionEnabled} whether the option is enabled
     */
    private final boolean isOptionEnabled;

    /**
     * Constructor to init a {@link SubAccountOptions} object
     *
     * @param email:           email of the subaccount
     * @param isOptionEnabled: whether the option is enabled
     */
    public SubAccountOptions(String email, boolean isOptionEnabled) {
        super(null);
        this.email = email;
        this.isOptionEnabled = isOptionEnabled;
    }

    /**
     * Constructor to init a {@link SubAccountOptions} object
     *
     * @param jSubAccountOptions: subaccount options details as {@link JSONObject}
     */
    public SubAccountOptions(JSONObject jSubAccountOptions) {
        super(jSubAccountOptions);
        email = hItem.getString("email");
        isOptionEnabled = hItem.getBoolean("isEOptionsEnabled");
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
     * Method to get {@link #isOptionEnabled} instance <br>
     * No-any params required
     *
     * @return {@link #isOptionEnabled} instance as boolean
     */
    public boolean isOptionEnabled() {
        return isOptionEnabled;
    }

}