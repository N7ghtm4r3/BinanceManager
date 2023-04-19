package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubMarginAccountAssetDetails extends MarginAccount {

    private final double marginLevel;
    private final ArrayList<UserMarginAsset> userAssets;

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param totalAssetOfBtc     :     total asset of Bitcoin
     * @param totalLiabilityOfBtc : total liability of Bitcoin
     * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SubMarginAccountAssetDetails(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                        double marginLevel, ArrayList<UserMarginAsset> userAssets) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.marginLevel = marginLevel;
        this.userAssets = userAssets;
    }

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param jSubMarginAccountAssetDetails : margin account details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SubMarginAccountAssetDetails(JSONObject jSubMarginAccountAssetDetails) {
        super(jSubMarginAccountAssetDetails);
        marginLevel = hItem.getDouble("marginLevel", 0);
        userAssets = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("userAssets");
        if (jList != null)
            for (JSONObject item : jList)
                userAssets.add(new UserMarginAsset(item));
    }

    public double getMarginLevel() {
        return marginLevel;
    }

    public ArrayList<UserMarginAsset> getUserAssets() {
        return userAssets;
    }

}
