package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginAccount;
import org.json.JSONArray;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountInfo.assembleIsolatedMarginAccountInfoList;

/**
 * The {@code ComposedIMarginAccountInfo} class is useful to format Binance Isolated Margin Account Status request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
 *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class ComposedIMarginAccountInfo extends MarginAccount {

    /**
     * {@code isolatedMarginAccountInfoList} is instance that memorizes list of isolated margin account info
     * **/
    private  ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList;

    /** Constructor to init {@link ComposedIMarginAccountInfo} object
     * @param totalAssetOfBtc: total asset of Bitcoin
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @param totalNetAssetOfBtc: total net asset of Bitcoin
     * @param jsonAccountInfo: margin account details in JSON format
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public ComposedIMarginAccountInfo(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                      JSONArray jsonAccountInfo) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        isolatedMarginAccountInfoList = assembleIsolatedMarginAccountInfoList(jsonAccountInfo);
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

}
