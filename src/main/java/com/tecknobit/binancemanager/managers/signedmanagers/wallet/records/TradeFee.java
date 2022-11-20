package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code TradeFee} class is useful to manage TradeFee {@code "Binance"} request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#trade-fee-user_data</a>
 **/

public class TradeFee {

    /**
     * {@code symbol} is instance that memorizes symbol value
     * **/
    private final String symbol;

    /**
     * {@code makerCommission} is instance that memorizes maker commission value
     * **/
    private final double makerCommission;

    /**
     * {@code takerCommission} is instance that memorizes taker commission value
     * **/
    private final double takerCommission;

    /** Constructor to init {@link TradeFee} object
     * @param symbol: symbol value
     * @param makerCommission: maker commission value
     * @param takerCommission: taker commission value
     * **/
    public TradeFee(String symbol, double makerCommission, double takerCommission) {
        this.symbol = symbol;
        this.makerCommission = makerCommission;
        this.takerCommission = takerCommission;
    }

    /**
     * Constructor to init {@link TradeFee} object
     *
     * @param tradeFee: trade fee details as {@link JSONObject}
     **/
    public TradeFee(JSONObject tradeFee) {
        symbol = tradeFee.getString("symbol");
        makerCommission = tradeFee.getDouble("makerCommission");
        takerCommission = tradeFee.getDouble("takerCommission");
    }

    public String getSymbol() {
        return symbol;
    }

    public double getMakerCommission() {
        return makerCommission;
    }

    /**
     * Method to get {@link #makerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #makerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMakerCommission(int decimals) {
        return roundValue(makerCommission, decimals);
    }

    public double getTakerCommission() {
        return takerCommission;
    }

    /**
     * Method to get {@link #takerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTakerCommission(int decimals) {
        return roundValue(takerCommission, decimals);
    }

    @Override
    public String toString() {
        return "TradeFee{" +
                "symbol='" + symbol + '\'' +
                ", makerCommission=" + makerCommission +
                ", takerCommission=" + takerCommission +
                '}';
    }

}


