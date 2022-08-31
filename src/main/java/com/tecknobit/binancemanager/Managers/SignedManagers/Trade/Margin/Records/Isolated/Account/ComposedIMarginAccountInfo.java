package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Formatters.JsonHelper.getJSONArray;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountInfo.createIsolatedMarginAccountInfoList;

/**
 * The {@code ComposedIMarginAccountInfo} class is useful to format Binance Isolated Margin Account Status request response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
 **/

public class ComposedIMarginAccountInfo extends MarginAccount {

    /**
     * {@code isolatedMarginAccountInfoList} is instance that memorizes list of {@link IsolatedMarginAccountInfo}
     **/
    private ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList;

    /**
     * Constructor to init {@link ComposedIMarginAccountInfo} object
     *
     * @param totalAssetOfBtc:               total asset of Bitcoin
     * @param totalLiabilityOfBtc:           total liability of Bitcoin
     * @param totalNetAssetOfBtc:            total net asset of Bitcoin
     * @param isolatedMarginAccountInfoList: list of {@link IsolatedMarginAccountInfo}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public ComposedIMarginAccountInfo(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                      ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.isolatedMarginAccountInfoList = isolatedMarginAccountInfoList;
    }

    /**
     * Constructor to init {@link ComposedIMarginAccountInfo} object
     *
     * @param marginAccountInfo: total asset of Bitcoin
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public ComposedIMarginAccountInfo(JSONObject marginAccountInfo) {
        super(marginAccountInfo);
        isolatedMarginAccountInfoList = createIsolatedMarginAccountInfoList(getJSONArray(marginAccountInfo, "assets",
                new JSONArray()));
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

    @Override
    public String toString() {
        return "ComposedIMarginAccountInfo{" +
                "isolatedMarginAccountInfoList=" + isolatedMarginAccountInfoList +
                ", totalAssetOfBtc=" + totalAssetOfBtc +
                ", totalLiabilityOfBtc=" + totalLiabilityOfBtc +
                ", totalNetAssetOfBtc=" + totalNetAssetOfBtc +
                '}';
    }

}
