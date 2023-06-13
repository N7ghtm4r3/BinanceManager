package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.margin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account.MarginAccount;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.MarginAccountSnapshot.UserMarginAsset;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubMarginAccount} class is useful to format a sub margin account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-margin-account-for-master-account">
 * Get Detail on Sub-account's Margin Account (For Master Account)</a>
 * @see BinanceItem
 * @see MarginAccount
 */
public class SubMarginAccount extends MarginAccount {

    /**
     * {@code email} of the sub margin account
     */
    private final String email;

    /**
     * {@code marginLevel} margin level of the sub margin account
     */
    private final double marginLevel;

    /**
     * {@code marginTradeCoeffVo} margin trade coeff vo of the sub margin account
     */
    private final MarginTradeCoeffVo marginTradeCoeffVo;

    /**
     * {@code marginUserAssetVoList} margin user asset vo list of the sub margin account
     */
    private final ArrayList<UserMarginAsset> marginUserAssetVoList;

    /**
     * Constructor to init {@link SubMarginAccount} object
     *
     * @param totalAssetOfBtc:       total asset of Bitcoin
     * @param totalLiabilityOfBtc:   total liability of Bitcoin
     * @param totalNetAssetOfBtc:    total net asset of Bitcoin
     * @param email:                 email of the sub margin account
     * @param marginLevel:           margin level of the sub margin account
     * @param marginTradeCoeffVo:    margin trade coeff vo of the sub margin account
     * @param marginUserAssetVoList: margin user asset vo list of the sub margin account
     */
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
     * Constructor to init {@link SubMarginAccount} object
     *
     * @param jSubMarginAccount : sub margin account details as {@link JSONObject}
     */
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

    /**
     * Method to get {@link #email} instance <br>
     * No-any params required
     *
     * @return {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get {@link #marginLevel} instance <br>
     * No-any params required
     *
     * @return {@link #marginLevel} instance as double
     */
    public double getMarginLevel() {
        return marginLevel;
    }

    /**
     * Method to get {@link #marginLevel} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginLevel} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMarginLevel(int decimals) {
        return roundValue(marginLevel, decimals);
    }

    /**
     * Method to get {@link #marginTradeCoeffVo} instance <br>
     * No-any params required
     *
     * @return {@link #marginTradeCoeffVo} instance as {@link MarginTradeCoeffVo}
     */
    public MarginTradeCoeffVo getMarginTradeCoeffVo() {
        return marginTradeCoeffVo;
    }

    /**
     * Method to get {@link #marginUserAssetVoList} instance <br>
     * No-any params required
     *
     * @return {@link #marginUserAssetVoList} instance as {@link ArrayList} of {@link UserMarginAsset}
     */
    public ArrayList<UserMarginAsset> getMarginUserAssetVoList() {
        return marginUserAssetVoList;
    }

    /**
     * The {@code MarginTradeCoeffVo} class is useful to format a margin trade coeff vo
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class MarginTradeCoeffVo extends BinanceItem {

        /**
         * {@code forceLiquidationBar} force liquidation bar of the margin trade coeff vo
         */
        private final double forceLiquidationBar;

        /**
         * {@code marginCallBar} margin call bar of the margin trade coeff vo
         */
        private final double marginCallBar;

        /**
         * {@code normalBar} normal bar of the margin trade coeff vo
         */
        private final double normalBar;

        /**
         * Constructor to init {@link MarginTradeCoeffVo} object
         *
         * @param forceLiquidationBar : force liquidation bar of the margin trade coeff vo
         * @param marginCallBar       : margin call bar of the margin trade coeff vo
         * @param normalBar           : normal bar of the margin trade coeff vo
         */
        public MarginTradeCoeffVo(double forceLiquidationBar, double marginCallBar, double normalBar) {
            super(null);
            this.forceLiquidationBar = forceLiquidationBar;
            this.marginCallBar = marginCallBar;
            this.normalBar = normalBar;
        }

        /**
         * Constructor to init {@link MarginTradeCoeffVo} object
         *
         * @param jMarginTradeCoeffVo : margin trade coeff vo details as {@link JSONObject}
         */
        public MarginTradeCoeffVo(JSONObject jMarginTradeCoeffVo) {
            super(jMarginTradeCoeffVo);
            forceLiquidationBar = hItem.getDouble("forceLiquidationBar", 0);
            marginCallBar = hItem.getDouble("marginCallBar", 0);
            normalBar = hItem.getDouble("normalBar", 0);
        }

        /**
         * Method to get {@link #forceLiquidationBar} instance <br>
         * No-any params required
         *
         * @return {@link #forceLiquidationBar} instance as double
         */
        public double getForceLiquidationBar() {
            return forceLiquidationBar;
        }

        /**
         * Method to get {@link #forceLiquidationBar} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #forceLiquidationBar} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getForceLiquidationBar(int decimals) {
            return roundValue(forceLiquidationBar, decimals);
        }

        /**
         * Method to get {@link #marginCallBar} instance <br>
         * No-any params required
         *
         * @return {@link #marginCallBar} instance as double
         */
        public double getMarginCallBar() {
            return marginCallBar;
        }

        /**
         * Method to get {@link #marginCallBar} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #marginCallBar} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMarginCallBar(int decimals) {
            return roundValue(marginCallBar, decimals);
        }

        /**
         * Method to get {@link #normalBar} instance <br>
         * No-any params required
         *
         * @return {@link #normalBar} instance as double
         */
        public double getNormalBar() {
            return normalBar;
        }

        /**
         * Method to get {@link #normalBar} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #normalBar} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getNormalBar(int decimals) {
            return roundValue(normalBar, decimals);
        }

    }

}
