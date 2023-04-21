package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.SubFuturesAccount.SubFuturesAsset;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code CoinSubFuturesAccount} class is useful to format a coin sub futures account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-v2-for-master-account">
 * Get Detail on Sub-account's Futures Account V2 (For Master Account)</a>
 * @see BinanceItem
 **/
public class CoinSubFuturesAccount extends BinanceItem {

    /**
     * {@code email} of the coin sub futures account
     **/
    private final String email;

    /**
     * {@code assets} of the coin sub futures account
     **/
    private final ArrayList<SubFuturesAsset> assets;

    /**
     * {@code canDeposit} whether the coin sub futures account can deposit
     **/
    private final boolean canDeposit;

    /**
     * {@code canTrade} whether the coin sub futures account can trade
     **/
    private final boolean canTrade;

    /**
     * {@code canWithdraw} whether the coin sub futures account can withdraw
     **/
    private final boolean canWithdraw;

    /**
     * {@code feeTier} fee tier of the coin sub futures account
     **/
    private final int feeTier;

    /**
     * {@code updateTime} update time of the coin sub futures account
     **/
    private final long updateTime;

    /**
     * Constructor to init {@link CoinSubFuturesAccount} object
     *
     * @param email:       email of the coin sub futures account
     * @param assets:      assets of the coin sub futures account
     * @param canDeposit:  whether the coin sub futures account can deposit
     * @param canTrade:    whether the coin sub futures account can trade
     * @param canWithdraw: whether the coin sub futures account can withdraw
     * @param feeTier:     fee tier of the coin sub futures account
     * @param updateTime:  update time of the coin sub futures account
     **/
    public CoinSubFuturesAccount(String email, ArrayList<SubFuturesAsset> assets, boolean canDeposit, boolean canTrade,
                                 boolean canWithdraw, int feeTier, long updateTime) {
        super(null);
        this.email = email;
        this.assets = assets;
        this.canDeposit = canDeposit;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.feeTier = feeTier;
        this.updateTime = updateTime;
    }

    /**
     * Constructor to init {@link CoinSubFuturesAccount} object
     *
     * @param jCoinSubFuturesAccount: coin sub futures account details {@link JSONObject}
     **/
    public CoinSubFuturesAccount(JSONObject jCoinSubFuturesAccount) {
        super(jCoinSubFuturesAccount);
        email = hItem.getString("email");
        assets = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("assets");
        if (jList != null)
            for (JSONObject item : jList)
                assets.add(new SubFuturesAsset(item));
        canDeposit = hItem.getBoolean("canDeposit");
        canTrade = hItem.getBoolean("canTrade");
        canWithdraw = hItem.getBoolean("canWithdraw");
        feeTier = hItem.getInt("feeTier", 0);
        updateTime = hItem.getLong("updateTime", 0);
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
     * Method to get {@link #assets} instance <br>
     * No-any params required
     *
     * @return {@link #assets} instance as {@link ArrayList} of {@link SubFuturesAsset}
     **/
    public ArrayList<SubFuturesAsset> getAssets() {
        return assets;
    }

    /**
     * Method to get {@link #canDeposit} instance <br>
     * No-any params required
     *
     * @return {@link #canDeposit} instance as boolean
     **/
    public boolean canDeposit() {
        return canDeposit;
    }

    /**
     * Method to get {@link #canTrade} instance <br>
     * No-any params required
     *
     * @return {@link #canTrade} instance as boolean
     **/
    public boolean canTrade() {
        return canTrade;
    }

    /**
     * Method to get {@link #canWithdraw} instance <br>
     * No-any params required
     *
     * @return {@link #canWithdraw} instance as boolean
     **/
    public boolean canWithdraw() {
        return canWithdraw;
    }

    /**
     * Method to get {@link #feeTier} instance <br>
     * No-any params required
     *
     * @return {@link #feeTier} instance as int
     **/
    public int getFeeTier() {
        return feeTier;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     **/
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     **/
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

}
