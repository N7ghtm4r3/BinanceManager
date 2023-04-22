package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubMarginAccountAssetDetails} class is useful to format a sub margin account asset details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-futures-asset-details-for-investor-master-account-user_data">
 * Query Managed Sub-account Margin Asset Details (For Investor Master Account) (USER_DATA)</a>
 * @see BinanceItem
 * @see MarginAccount
 **/
public class SubMarginAccountAssetDetails extends MarginAccount {

    /**
     * {@code marginLevel} margin level of the sub margin account asset details
     **/
    private final double marginLevel;

    /**
     * {@code userAssets} user assets of the sub margin account asset details
     **/
    private final ArrayList<UserMarginAsset> userAssets;

    /**
     * Constructor to init {@link SubMarginAccountAssetDetails} object
     *
     * @param totalAssetOfBtc     :     total asset of Bitcoin
     * @param totalLiabilityOfBtc : total liability of Bitcoin
     * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
     * @param marginLevel         : margin level of the sub margin account asset details
     * @param userAssets          :  user assets of the sub margin account asset details
     **/
    public SubMarginAccountAssetDetails(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                                        double marginLevel, ArrayList<UserMarginAsset> userAssets) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.marginLevel = marginLevel;
        this.userAssets = userAssets;
    }

    /**
     * Constructor to init {@link SubMarginAccountAssetDetails} object
     *
     * @param jSubMarginAccountAssetDetails : sub margin account asset details as {@link JSONObject}
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

    /**
     * Method to get {@link #marginLevel} instance <br>
     * No-any params required
     *
     * @return {@link #marginLevel} instance as double
     **/
    public double getMarginLevel() {
        return marginLevel;
    }

    /**
     * Method to get {@link #marginLevel} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginLevel} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMarginLevel(int decimals) {
        return roundValue(marginLevel, decimals);
    }

    /**
     * Method to get {@link #userAssets} instance <br>
     * No-any params required
     *
     * @return {@link #userAssets} instance as {@link ArrayList} of {@link UserMarginAsset}
     **/
    public ArrayList<UserMarginAsset> getUserAssets() {
        return userAssets;
    }

}
