package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.assembleUserMarginAssetsList;

/**
 * The {@code CrossMarginAccountDetails} class is useful to format {@code "Binance"} Cross Margin Account Detail response request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 **/

public class CrossMarginAccountDetails extends MarginAccount {

    /**
     * {@code borrowEnabled} is instance that memorizes if borrow is enable
     **/
    private boolean borrowEnabled;

    /**
     * {@code marginLevel} is instance that memorizes margin level
     **/
    private double marginLevel;

    /**
     * {@code tradeEnabled} is instance that memorizes if trade is enable
     * **/
    private boolean tradeEnabled;

    /**
     * {@code transferEnabled} is instance that memorizes if transfer is enable
     * **/
    private boolean transferEnabled;

    /**
     * {@code userMarginAssets} is instance that memorizes list of {@link UserMarginAsset}
     * **/
    private ArrayList<UserMarginAsset> userMarginAssets;

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

    public boolean isBorrowEnabled() {
        return borrowEnabled;
    }

    public void setBorrowEnabled(boolean borrowEnabled) {
        this.borrowEnabled = borrowEnabled;
    }

    public double getMarginLevel() {
        return marginLevel;
    }

    /** Method to set {@link #marginLevel}
     * @param marginLevel: margin level
     * @throws IllegalArgumentException when margin level value is less than 0
     * **/
    public void setMarginLevel(double marginLevel) {
        if(marginLevel < 0)
            throw new IllegalArgumentException("Margin level value cannot be less than 0");
        this.marginLevel = marginLevel;
    }

    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    public void setTradeEnabled(boolean tradeEnabled) {
        this.tradeEnabled = tradeEnabled;
    }

    public boolean isTransferEnabled() {
        return transferEnabled;
    }

    public void setTransferEnabled(boolean transferEnabled) {
        this.transferEnabled = transferEnabled;
    }

    public ArrayList<UserMarginAsset> getUserAssetMargins() {
        return userMarginAssets;
    }

    public void setUserAssetMargins(ArrayList<UserMarginAsset> userMarginAssets) {
        this.userMarginAssets = userMarginAssets;
    }

    public void insertUserAssetMargin(MarginAccountSnapshot.UserMarginAsset assetMargin) {
        if (!userMarginAssets.contains(assetMargin))
            userMarginAssets.add(assetMargin);
    }

    public boolean removeUserAssetMargin(MarginAccountSnapshot.UserMarginAsset assetMargin) {
        return userMarginAssets.remove(assetMargin);
    }

    public MarginAccountSnapshot.UserMarginAsset getUserAssetMargin(int index) {
        return userMarginAssets.get(index);
    }

    @Override
    public String toString() {
        return "CrossMarginAccountDetails{" +
                "borrowEnabled=" + borrowEnabled +
                ", marginLevel=" + marginLevel +
                ", tradeEnabled=" + tradeEnabled +
                ", transferEnabled=" + transferEnabled +
                ", userMarginAssets=" + userMarginAssets +
                ", totalAssetOfBtc=" + totalAssetOfBtc +
                ", totalLiabilityOfBtc=" + totalLiabilityOfBtc +
                ", totalNetAssetOfBtc=" + totalNetAssetOfBtc +
                '}';
    }

}