package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.risk;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.algo.records.AlgoOrdersList.AlgoOrder.PositionSide;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CoinFuturesPositionRisk} class is useful to format a coin futures position risk
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
 * Get Futures Position-Risk of Sub-account V2 (For Master Account)</a>
 * @see BinanceItem
 **/
public class CoinFuturesPositionRisk extends RiskStructure {

    /**
     * {@code isolated} whether the coin futures position risk is isolated
     **/
    private final boolean isolated;

    /**
     * {@code isolatedWallet} isolated wallet of the coin futures position risk
     **/
    private final double isolatedWallet;

    /**
     * {@code isolatedMargin} isolated margin of the coin futures position risk
     **/
    private final double isolatedMargin;

    /**
     * {@code maxNotional} whether the coin futures position risk is auto add margin
     **/
    private final boolean isAutoAddMargin;

    /**
     * {@code positionSide} position side of the coin futures position risk
     **/
    private final PositionSide positionSide;

    /**
     * Constructor to init {@link FuturesPositionRisk} object
     *
     * @param entryPrice       : entry price of the coin futures position risk
     * @param leverage         : leverage of the coin futures position risk
     * @param markPrice        : mark price of the coin futures position risk
     * @param positionAmount   : position amount of the coin futures position risk
     * @param symbol           : symbol of the coin futures position risk
     * @param unrealizedProfit : unrealized profit of the coin futures position risk
     * @param isolated         : whether the coin futures position risk is isolated
     * @param isolatedWallet:  isolated wallet of the coin futures position risk
     * @param isolatedMargin   : isolated margin of the coin futures position risk
     * @param isAutoAddMargin: whether the coin futures position risk is auto add margin
     * @param positionSide     : position side of the coin futures position risk
     **/
    public CoinFuturesPositionRisk(double entryPrice, double leverage, double markPrice, double positionAmount,
                                   String symbol, double unrealizedProfit, boolean isolated, double isolatedWallet,
                                   double isolatedMargin, boolean isAutoAddMargin, PositionSide positionSide) {
        super(entryPrice, leverage, markPrice, positionAmount, symbol, unrealizedProfit);
        this.isolated = isolated;
        this.isolatedWallet = isolatedWallet;
        this.isolatedMargin = isolatedMargin;
        this.isAutoAddMargin = isAutoAddMargin;
        this.positionSide = positionSide;
    }

    /**
     * Constructor to init {@link FuturesPositionRisk} object
     *
     * @param jCoinFuturesPositionRisk: coin futures position risk details as {@link JSONObject}
     **/
    public CoinFuturesPositionRisk(JSONObject jCoinFuturesPositionRisk) {
        super(jCoinFuturesPositionRisk);
        isolated = hItem.getBoolean("isolated");
        isolatedWallet = hItem.getDouble("isolatedWallet", 0);
        isolatedMargin = hItem.getDouble("isolatedMargin", 0);
        isAutoAddMargin = hItem.getBoolean("isAutoAddMargin");
        positionSide = PositionSide.valueOf(hItem.getString("positionSide"));
    }

    /**
     * Method to get {@link #isolated} instance <br>
     * No-any params required
     *
     * @return {@link #isolated} instance as boolean
     **/
    public boolean isIsolated() {
        return isolated;
    }

    /**
     * Method to get {@link #isolatedWallet} instance <br>
     * No-any params required
     *
     * @return {@link #isolatedWallet} instance as double
     **/
    public double getIsolatedWallet() {
        return isolatedWallet;
    }

    /**
     * Method to get {@link #isolatedWallet} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #isolatedWallet} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIsolatedWallet(int decimals) {
        return roundValue(isolatedWallet, decimals);
    }

    /**
     * Method to get {@link #isolatedMargin} instance <br>
     * No-any params required
     *
     * @return {@link #isolatedMargin} instance as double
     **/
    public double getIsolatedMargin() {
        return isolatedMargin;
    }

    /**
     * Method to get {@link #isolatedMargin} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #isolatedMargin} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIsolatedMargin(int decimals) {
        return roundValue(isolatedMargin, decimals);
    }

    /**
     * Method to get {@link #isAutoAddMargin} instance <br>
     * No-any params required
     *
     * @return {@link #isAutoAddMargin} instance as boolean
     **/
    public boolean isAutoAddMargin() {
        return isAutoAddMargin;
    }

    /**
     * Method to get {@link #positionSide} instance <br>
     * No-any params required
     *
     * @return {@link #positionSide} instance as {@link PositionSide}
     **/
    public PositionSide getPositionSide() {
        return positionSide;
    }

}
