package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountInfo.assembleIsolatedMarginAccountInfoList;

/**
 * The {@code ComposedIMarginAccountInfo} class is useful to format Binance Isolated Margin Account Status request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedIMarginAccountInfo {

    private final ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfos;
    private final double totalAssetOfBtc;
    private final double totalLiabilityOfBtc;
    private final double totalNetAssetOfBtc;

    public ComposedIMarginAccountInfo(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                      JSONArray jsonArray) {
        this.totalAssetOfBtc = totalAssetOfBtc;
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
        isolatedMarginAccountInfos = assembleIsolatedMarginAccountInfoList(jsonArray);
    }

    public ArrayList<IsolatedMarginAccountInfo> getIsolatedMarginAccountInfoList() {
        return isolatedMarginAccountInfos;
    }

    public IsolatedMarginAccountInfo getIsolatedMarginAccountInfo(int index) {
        return isolatedMarginAccountInfos.get(index);
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

}
