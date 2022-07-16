package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotMargin;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotMargin.UserAssetMargin;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 *  The {@code CrossMarginAccountDetails} class is useful to format Binance Cross Margin Account Detail response request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class CrossMarginAccountDetails extends MarginAccount{

    /**
     * {@code borrowEnabled} is instance that memorizes if borrow is enable
     * **/
    private boolean borrowEnabled;

    /**
     * {@code marginLevel} is instance that memorizes margin level
     * **/
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
     * {@code userAssetMargins} is instance that memorizes list of user margin assets
     * **/
    private ArrayList<UserAssetMargin> userAssetMargins;

    /** Constructor to init {@link CrossMarginAccountDetails} object
     * @param borrowEnabled: borrow is enable
     * @param marginLevel: margin level
     * @param totalAssetOfBtc: total asset of Bitcoin
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @param totalNetAssetOfBtc: total net asset of Bitcoin
     * @param tradeEnabled: trade is enable
     * @param transferEnabled: transfer is enable
     * @param jsonDetails: margin account details in JSON format
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public CrossMarginAccountDetails(boolean borrowEnabled, double marginLevel, double totalAssetOfBtc,
                                     double totalLiabilityOfBtc, double totalNetAssetOfBtc, boolean tradeEnabled,
                                     boolean transferEnabled, JSONArray jsonDetails) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.borrowEnabled = borrowEnabled;
        if(marginLevel < 0)
            throw new IllegalArgumentException("Margin level value cannot be less than 0");
        else
            this.marginLevel = marginLevel;
        this.tradeEnabled = tradeEnabled;
        this.transferEnabled = transferEnabled;
        userAssetMargins = AccountSnapshotMargin.assembleUserMarginAssetsList(jsonDetails);
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

    public double getTotalAssetOfBtc() {
        return totalAssetOfBtc;
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

    public ArrayList<UserAssetMargin> getUserAssetMargins() {
        return userAssetMargins;
    }

    public void setUserAssetMargins(ArrayList<UserAssetMargin> userAssetMargins) {
        this.userAssetMargins = userAssetMargins;
    }

    public void insertUserAssetMargin(UserAssetMargin assetMargin){
        if(!userAssetMargins.contains(assetMargin))
            userAssetMargins.add(assetMargin);
    }

    public boolean removeUserAssetMargin(UserAssetMargin assetMargin){
        return userAssetMargins.remove(assetMargin);
    }

    public UserAssetMargin getUserAssetMargin(int index) {
        return userAssetMargins.get(index);
    }

}