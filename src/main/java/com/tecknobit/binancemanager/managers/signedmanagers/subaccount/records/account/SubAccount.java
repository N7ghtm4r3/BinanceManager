package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code SubAccount} class is useful to format a subaccount
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-list-for-master-account">
 * Query Sub-account List (For Master Account)</a>
 * @see BinanceItem
 **/
public class SubAccount extends BinanceItem {

    /**
     * {@code email} of the subaccount
     **/
    private final String email;

    /**
     * {@code isFreeze} whether the subaccount is frozen
     **/
    private final boolean isFreeze;

    /**
     * {@code createTime} create time of the subaccount
     **/
    private final long createTime;

    /**
     * {@code isManagedSubAccount} whether the subaccount is managed subaccount
     **/
    private final boolean isManagedSubAccount;

    /**
     * {@code isAssetManagementSubAccount} whether the subaccount is asset management subaccount
     **/
    private final boolean isAssetManagementSubAccount;

    /**
     * Constructor to init {@link SubAccount} object
     *
     * @param email                       : email of the subaccount
     * @param isFreeze                    : whether the subaccount is frozen
     * @param createTime                  : create time of the subaccount
     * @param isManagedSubAccount         : whether the subaccount is managed subaccount
     * @param isAssetManagementSubAccount : whether the subaccount is asset management subaccount
     **/
    public SubAccount(String email, boolean isFreeze, long createTime, boolean isManagedSubAccount,
                      boolean isAssetManagementSubAccount) {
        super(null);
        this.email = email;
        this.isFreeze = isFreeze;
        this.createTime = createTime;
        this.isManagedSubAccount = isManagedSubAccount;
        this.isAssetManagementSubAccount = isAssetManagementSubAccount;
    }

    /**
     * Constructor to init {@link SubAccount} object
     *
     * @param jSubAccount : subaccount details as {@link JSONObject}
     **/
    public SubAccount(JSONObject jSubAccount) {
        super(jSubAccount);
        email = hItem.getString("email");
        isFreeze = hItem.getBoolean("isFreeze");
        createTime = hItem.getLong("createTime", 0);
        isManagedSubAccount = hItem.getBoolean("isManagedSubAccount");
        isAssetManagementSubAccount = hItem.getBoolean("isAssetManagementSubAccount");
    }

    /**
     * Method to get {@link #email} instance <br>
     * No-any params required
     *
     * @return {@link #email} instance as {@link String}
     **/
    public String getEmail() {
        return email;
    }

    /**
     * Method to get {@link #isFreeze} instance <br>
     * No-any params required
     *
     * @return {@link #isFreeze} instance as boolean
     **/
    public boolean isFreeze() {
        return isFreeze;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as long
     **/
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as {@link Date}
     **/
    public Date getCreateDate() {
        return TimeFormatter.getDate(createTime);
    }

    /**
     * Method to get {@link #isManagedSubAccount} instance <br>
     * No-any params required
     *
     * @return {@link #isManagedSubAccount} instance as boolean
     **/
    public boolean isManagedSubAccount() {
        return isManagedSubAccount;
    }

    /**
     * Method to get {@link #isAssetManagementSubAccount} instance <br>
     * No-any params required
     *
     * @return {@link #isAssetManagementSubAccount} instance as boolean
     **/
    public boolean isAssetManagementSubAccount() {
        return isAssetManagementSubAccount;
    }

}
