package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties.MarginAsset;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.assembleUserMarginAssetsList;

/**
 * The {@code CrossMarginAccountDetails} class is useful to format a {@code "Binance"}'s cross margin account details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * Query Cross Margin Account Details (USER_DATA)</a>
 * @see MarginAccount
 **/
public class CrossMarginAccountDetails extends MarginAccount {

    /**
     * {@code borrowEnabled} is instance that memorizes if borrow is enable
     **/
    private final boolean borrowEnabled;

    /**
     * {@code marginLevel} is instance that memorizes margin level
     **/
    private final double marginLevel;

    /**
     * {@code tradeEnabled} is instance that memorizes if trade is enable
     **/
    private final boolean tradeEnabled;

    /**
     * {@code transferEnabled} is instance that memorizes if transfer is enable
     **/
    private final boolean transferEnabled;

    /**
     * {@code userMarginAssets} is instance that memorizes list of {@link UserMarginAsset}
     **/
    private final ArrayList<UserMarginAsset> userMarginAssets;

    /** Constructor to init {@link CrossMarginAccountDetails} object
     * @param borrowEnabled: borrow is enable
     * @param marginLevel: margin level
     * @param totalAssetOfBtc: total asset of Bitcoin
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @param totalNetAssetOfBtc: total net asset of Bitcoin
     * @param tradeEnabled: trade is enable
     * @param transferEnabled: transfer is enable
     * @param userMarginAssets: list of {@link UserMarginAsset}
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public CrossMarginAccountDetails(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                     boolean borrowEnabled, double marginLevel, boolean tradeEnabled,
                                     boolean transferEnabled, ArrayList<UserMarginAsset> userMarginAssets) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.borrowEnabled = borrowEnabled;
        this.marginLevel = marginLevel;
        this.tradeEnabled = tradeEnabled;
        this.transferEnabled = transferEnabled;
        this.userMarginAssets = userMarginAssets;
    }

    /**
     * Constructor to init {@link CrossMarginAccountDetails} object
     *
     * @param crossMarginAccount: cross margin account details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public CrossMarginAccountDetails(JSONObject crossMarginAccount) {
        super(crossMarginAccount);
        borrowEnabled = crossMarginAccount.getBoolean("borrowEnabled");
        marginLevel = crossMarginAccount.getDouble("marginLevel");
        if (marginLevel < 0)
            throw new IllegalArgumentException("Margin level value cannot be less than 0");
        tradeEnabled = crossMarginAccount.getBoolean("tradeEnabled");
        transferEnabled = crossMarginAccount.getBoolean("transferEnabled");
        userMarginAssets = assembleUserMarginAssetsList(getJSONArray(crossMarginAccount, "userAssets", new JSONArray()));
    }

    /**
     * Method to get {@link #borrowEnabled} instance <br>
     * Any params required
     *
     * @return {@link #borrowEnabled} instance as boolean
     **/
    public boolean isBorrowEnabled() {
        return borrowEnabled;
    }

    /**
     * Method to get {@link #marginLevel} instance <br>
     * Any params required
     *
     * @return {@link #marginLevel} instance as double
     **/
    public double getMarginLevel() {
        return marginLevel;
    }

    /**
     * Method to get {@link #tradeEnabled} instance <br>
     * Any params required
     *
     * @return {@link #tradeEnabled} instance as boolean
     **/
    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    /**
     * Method to get {@link #transferEnabled} instance <br>
     * Any params required
     *
     * @return {@link #transferEnabled} instance as boolean
     **/
    public boolean isTransferEnabled() {
        return transferEnabled;
    }

    /**
     * Method to get {@link #userMarginAssets} instance <br>
     * Any params required
     *
     * @return {@link #userMarginAssets} instance as {@link ArrayList} of {@link UserMarginAsset}
     **/
    public ArrayList<UserMarginAsset> getUserAssetMargins() {
        return userMarginAssets;
    }

    /**
     * Method to add a {@link MarginAsset} to {@link #userMarginAssets}
     *
     * @param assetMargin: asset margin to add
     **/
    public void insertUserAssetMargin(UserMarginAsset assetMargin) {
        if (!userMarginAssets.contains(assetMargin))
            userMarginAssets.add(assetMargin);
    }

    /**
     * Method to remove a {@link MarginAsset} from {@link #userMarginAssets}
     *
     * @param assetMargin: asset margin to remove
     * @return result of operation as boolean
     **/
    public boolean removeUserAssetMargin(UserMarginAsset assetMargin) {
        return userMarginAssets.remove(assetMargin);
    }

    /**
     * Method to get a margin asset from {@link #userMarginAssets} list
     *
     * @param index: index to fetch the order
     * @return margin asset as {@link UserMarginAsset}
     **/
    public UserMarginAsset getUserAssetMargin(int index) {
        return userMarginAssets.get(index);
    }

}