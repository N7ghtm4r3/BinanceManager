package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotMargin;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 *  The {@code CrossMarginAccountDetails} class is useful to format Binance Cross Margin Account Detail response request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class CrossMarginAccountDetails {

    private final boolean borrowEnabled;
    private final double marginLevel;
    private final double totalAssetOfBtc;
    private final double totalLiabilityOfBtc;
    private final double totalNetAssetOfBtc;
    private final boolean tradeEnabled;
    private final boolean transferEnabled;
    private final ArrayList<AccountSnapshotMargin.UserAssetMargin> userAssetMargins;

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

    public double getMarginLevel() {
        return marginLevel;
    }

    public double getTotalAssetOfBtc() {
        return totalAssetOfBtc;
    }

    public double getTotalLiabilityOfBtc() {
        return totalLiabilityOfBtc;
    }

    public double getTotalNetAssetOfBtc() {
        return totalNetAssetOfBtc;
    }

    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    public boolean isTransferEnabled() {
        return transferEnabled;
    }

    public ArrayList<AccountSnapshotMargin.UserAssetMargin> getUserAssetMarginsList() {
        return userAssetMargins;
    }

    public AccountSnapshotMargin.UserAssetMargin getUserAssetMargin(int index) {
        return userAssetMargins.get(index);
    }

}

