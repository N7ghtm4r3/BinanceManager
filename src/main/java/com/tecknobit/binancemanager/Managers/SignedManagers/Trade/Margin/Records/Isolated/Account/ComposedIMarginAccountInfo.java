package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountInfo.assembleIsolatedMarginAccountInfoList;

/**
 * The {@code ComposedIMarginAccountInfo} class is useful to format Binance Isolated Margin Account Status request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedIMarginAccountInfo {

    private  ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList;
    private double totalAssetOfBtc;
    private double totalLiabilityOfBtc;
    private double totalNetAssetOfBtc;

    public ComposedIMarginAccountInfo(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                      JSONArray jsonArray) {
        this.totalAssetOfBtc = totalAssetOfBtc;
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
        isolatedMarginAccountInfoList = assembleIsolatedMarginAccountInfoList(jsonArray);
    }

    public ArrayList<IsolatedMarginAccountInfo> getIsolatedMarginAccountInfoList() {
        return isolatedMarginAccountInfoList;
    }

    public void setIsolatedMarginAccountInfoList(ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList) {
        this.isolatedMarginAccountInfoList = isolatedMarginAccountInfoList;
    }

    public void insertIsolatedMarginAccountInfo(IsolatedMarginAccountInfo info){
        if(!isolatedMarginAccountInfoList.contains(info))
            isolatedMarginAccountInfoList.add(info);
    }

    public boolean removeUserAssetMargin(IsolatedMarginAccountInfo info){
        return isolatedMarginAccountInfoList.remove(info);
    }

    public IsolatedMarginAccountInfo getIsolatedMarginAccountInfo(int index) {
        return isolatedMarginAccountInfoList.get(index);
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

}
