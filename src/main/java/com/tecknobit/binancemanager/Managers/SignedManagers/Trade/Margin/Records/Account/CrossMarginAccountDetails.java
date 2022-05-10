package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotMargin;
import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotMargin.UserAssetMargin;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 *  The {@code CrossMarginAccountDetails} class is useful to format Binance Cross Margin Account Detail response request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class CrossMarginAccountDetails {

    private boolean borrowEnabled;
    private double marginLevel;
    private double totalAssetOfBtc;
    private double totalLiabilityOfBtc;
    private double totalNetAssetOfBtc;
    private boolean tradeEnabled;
    private boolean transferEnabled;
    private ArrayList<UserAssetMargin> userAssetMargins;

    public CrossMarginAccountDetails(boolean borrowEnabled, double marginLevel, double totalAssetOfBtc,
                                     double totalLiabilityOfBtc, double totalNetAssetOfBtc, boolean tradeEnabled,
                                     boolean transferEnabled, JSONArray jsonArray) {
        this.borrowEnabled = borrowEnabled;
        this.marginLevel = marginLevel;
        this.totalAssetOfBtc = totalAssetOfBtc;
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
        this.tradeEnabled = tradeEnabled;
        this.transferEnabled = transferEnabled;
        userAssetMargins = AccountSnapshotMargin.assembleUserMarginAssetsList(jsonArray);
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

    public void setMarginLevel(double marginLevel) {
        this.marginLevel = marginLevel;
    }

    public double getTotalAssetOfBtc() {
        return totalAssetOfBtc;
    }

    public void setTotalAssetOfBtc(double totalAssetOfBtc) {
        if(totalAssetOfBtc < 0)
            throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
        this.totalAssetOfBtc = totalAssetOfBtc;
    }

    public double getTotalLiabilityOfBtc() {
        return totalLiabilityOfBtc;
    }

    public void setTotalLiabilityOfBtc(double totalLiabilityOfBtc) {
        if(totalLiabilityOfBtc < 0)
            throw new IllegalArgumentException("Total liability asset of BTC value cannot be less than 0");
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
    }

    public double getTotalNetAssetOfBtc() {
        return totalNetAssetOfBtc;
    }

    public void setTotalNetAssetOfBtc(double totalNetAssetOfBtc) {
        if(totalNetAssetOfBtc < 0)
            throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
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