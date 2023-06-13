package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.account;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code ComposedIMarginAccountInfo} class is useful to format a {@code "Binance"}'s isolated margin account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
 * Query Isolated Margin Account Info (USER_DATA)</a>
 * @see MarginAccount
 */
public class ComposedIMarginAccountInfo extends MarginAccount {

    /**
     * {@code isolatedMarginAccountInfoList} is instance that memorizes list of {@link IsolatedMarginAccountInfo}
     */
    private ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList;

    /**
     * Constructor to init {@link ComposedIMarginAccountInfo} object
     *
     * @param totalAssetOfBtc:               total asset of Bitcoin
     * @param totalLiabilityOfBtc:           total liability of Bitcoin
     * @param totalNetAssetOfBtc:            total net asset of Bitcoin
     * @param isolatedMarginAccountInfoList: list of {@link IsolatedMarginAccountInfo}
     * @throws IllegalArgumentException if parameters range is not respected
     */
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
     */
    public ComposedIMarginAccountInfo(JSONObject marginAccountInfo) {
        super(marginAccountInfo);
        isolatedMarginAccountInfoList = new ArrayList<>();
        JSONArray jAccounts = hItem.getJSONArray("assets", new JSONArray());
        for (int j = 0; j < jAccounts.length(); j++)
            isolatedMarginAccountInfoList.add(new IsolatedMarginAccountInfo(jAccounts.getJSONObject(j)));
    }

    /**
     * Method to get {@link #isolatedMarginAccountInfoList} instance <br>
     * No-any params required
     *
     * @return {@link #isolatedMarginAccountInfoList} instance as {@link ArrayList} of {@link IsolatedMarginAccountInfo}
     */
    public ArrayList<IsolatedMarginAccountInfo> getIsolatedMarginAccountInfoList() {
        return isolatedMarginAccountInfoList;
    }

    /**
     * Method to set {@link #isolatedMarginAccountInfoList}
     *
     * @param isolatedMarginAccountInfoList: list of {@link IsolatedMarginAccountInfo} to set
     */
    public void setIsolatedMarginAccountInfoList(ArrayList<IsolatedMarginAccountInfo> isolatedMarginAccountInfoList) {
        this.isolatedMarginAccountInfoList = isolatedMarginAccountInfoList;
    }

    /**
     * Method to add an {@link IsolatedMarginAccountInfo} to {@link #isolatedMarginAccountInfoList}
     *
     * @param info: isolated margin account info to add
     */
    public void insertIsolatedMarginAccountInfo(IsolatedMarginAccountInfo info) {
        if (!isolatedMarginAccountInfoList.contains(info))
            isolatedMarginAccountInfoList.add(info);
    }

    /**
     * Method to remove a {@link IsolatedMarginAccountInfo} from {@link #isolatedMarginAccountInfoList}
     *
     * @param info: isolated margin account info to remove
     * @return result of operation as boolean
     */
    public boolean removeUserAssetMargin(IsolatedMarginAccountInfo info) {
        return isolatedMarginAccountInfoList.remove(info);
    }

    /**
     * Method to get an isolated margin account info from {@link #isolatedMarginAccountInfoList} list
     *
     * @param index: index to fetch the isolated margin account info
     * @return isolated margin account info as {@link IsolatedMarginAccountInfo}
     */
    public IsolatedMarginAccountInfo getIsolatedMarginAccountInfo(int index) {
        return isolatedMarginAccountInfoList.get(index);
    }

}
