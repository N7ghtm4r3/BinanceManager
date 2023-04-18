package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubMarginAccount extends MarginAccount {

    private final String email;
    private final double marginLevel;
    private final MarginTradeCoeffVo marginTradeCoeffVo;
    private final ArrayList<UserMarginAsset> marginUserAssetVoList;

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param totalAssetOfBtc     :     total asset of Bitcoin
     * @param totalLiabilityOfBtc : total liability of Bitcoin
     * @param totalNetAssetOfBtc  :  total net asset of Bitcoin
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SubMarginAccount(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc, String email,
                            double marginLevel, MarginTradeCoeffVo marginTradeCoeffVo,
                            ArrayList<UserMarginAsset> marginUserAssetVoList) {
        super(totalAssetOfBtc, totalLiabilityOfBtc, totalNetAssetOfBtc);
        this.email = email;
        this.marginLevel = marginLevel;
        this.marginTradeCoeffVo = marginTradeCoeffVo;
        this.marginUserAssetVoList = marginUserAssetVoList;
    }

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param jSubMarginAccount : margin account details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SubMarginAccount(JSONObject jSubMarginAccount) {
        super(jSubMarginAccount);
        email = hItem.getString("email");
        marginLevel = hItem.getDouble("marginLevel", 0);
        JSONObject jItem = hItem.getJSONObject("marginTradeCoeffVo");
        if (jItem != null)
            marginTradeCoeffVo = new MarginTradeCoeffVo(jItem);
        else
            marginTradeCoeffVo = null;
        marginUserAssetVoList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("marginUserAssetVoList");
        if (jList != null)
            for (JSONObject item : jList)
                marginUserAssetVoList.add(new UserMarginAsset(item));
    }

    public String getEmail() {
        return email;
    }

    public double getMarginLevel() {
        return marginLevel;
    }

    public MarginTradeCoeffVo getMarginTradeCoeffVo() {
        return marginTradeCoeffVo;
    }

    public ArrayList<UserMarginAsset> getMarginUserAssetVoList() {
        return marginUserAssetVoList;
    }

    public static class MarginTradeCoeffVo extends BinanceItem {

        private final double forceLiquidationBar;
        private final double marginCallBar;
        private final double normalBar;

        public MarginTradeCoeffVo(double forceLiquidationBar, double marginCallBar, double normalBar) {
            super(null);
            this.forceLiquidationBar = forceLiquidationBar;
            this.marginCallBar = marginCallBar;
            this.normalBar = normalBar;
        }

        public MarginTradeCoeffVo(JSONObject jMarginTradeCoeffVo) {
            super(jMarginTradeCoeffVo);
            forceLiquidationBar = hItem.getDouble("forceLiquidationBar", 0);
            marginCallBar = hItem.getDouble("marginCallBar", 0);
            normalBar = hItem.getDouble("normalBar", 0);
        }

        public double getForceLiquidationBar() {
            return forceLiquidationBar;
        }

        public double getMarginCallBar() {
            return marginCallBar;
        }

        public double getNormalBar() {
            return normalBar;
        }

    }

}
