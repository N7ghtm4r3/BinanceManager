package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties.IsolatedMarginFee.IsolatedData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CrossMarginFee} class is useful to format a {@code "Binance"}'s cross margin fee
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
 * Query Cross Margin Fee Data (USER_DATA)</a>
 * @see IsolatedData
 **/
public class CrossMarginFee extends IsolatedData {

    /**
     * {@code vipLevel} is instance that memorizes vip level
     **/
    private final int vipLevel;

    /**
     * {@code yearlyInterest} is instance that memorizes if is a transfer in
     **/
    private final boolean transferIn;

    /**
     * {@code borrowable} is instance that memorizes if is borrowable
     **/
    private final boolean borrowable;

    /**
     * {@code yearlyInterest} is instance that memorizes value of yearly interest
     **/
    private final double yearlyInterest;

    /**
     * {@code marginablePairsList} is instance that memorizes list of pairs data
     **/
    private final ArrayList<String> marginablePairsList;

    /** Constructor to init {@link CrossMarginFee} object
     * @param vipLevel: vip level
     * @param coin: coin
     * @param transferIn: is a transfer in
     * @param borrowable: is borrowable
     * @param dailyInterest: value of daily interest
     * @param yearlyInterest: value of yearly interest
     * @param borrowLimit: limit for borrow
     * @param marginablePairsList: list of pairs data
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public CrossMarginFee(String coin, double dailyInterest, double borrowLimit, int vipLevel, boolean transferIn,
                          boolean borrowable, double yearlyInterest, ArrayList<String> marginablePairsList) {
        super(coin, dailyInterest, borrowLimit);
        this.vipLevel = vipLevel;
        this.transferIn = transferIn;
        this.borrowable = borrowable;
        this.yearlyInterest = yearlyInterest;
        this.marginablePairsList = marginablePairsList;
    }

    /**
     * Constructor to init {@link CrossMarginFee} object
     *
     * @param marginFee: margin fee details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public CrossMarginFee(JSONObject marginFee) {
        super(marginFee);
        vipLevel = marginFee.getInt("vipLevel");
        if (vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        transferIn = marginFee.getBoolean("transferIn");
        borrowable = marginFee.getBoolean("borrowable");
        yearlyInterest = marginFee.getDouble("yearlyInterest");
        marginablePairsList = new ArrayList<>();
        JSONArray jPairs = marginFee.getJSONArray("marginablePairs");
        for (int j = 0; j < jPairs.length(); j++)
            marginablePairsList.add(jPairs.getString(j));
    }

    /**
     * Method to get {@link #vipLevel} instance <br>
     * No-any params required
     *
     * @return {@link #vipLevel} instance as int
     **/
    public int getVipLevel() {
        return vipLevel;
    }

    /**
     * Method to get {@link #transferIn} instance <br>
     * No-any params required
     *
     * @return {@link #transferIn} instance as boolean
     **/
    public boolean isTransferIn() {
        return transferIn;
    }

    /**
     * Method to get {@link #borrowable} instance <br>
     * No-any params required
     *
     * @return {@link #borrowable} instance as boolean
     **/
    public boolean isBorrowable() {
        return borrowable;
    }

    /**
     * Method to get {@link #yearlyInterest} instance <br>
     * No-any params required
     *
     * @return {@link #yearlyInterest} instance as double
     **/
    public double getYearlyInterest() {
        return yearlyInterest;
    }

    /**
     * Method to get {@link #yearlyInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #yearlyInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getYearlyInterest(int decimals) {
        return roundValue(yearlyInterest, decimals);
    }

    /**
     * Method to get {@link #marginablePairsList} instance <br>
     * No-any params required
     *
     * @return {@link #marginablePairsList} instance as {@link ArrayList} of {@link String}
     **/
    public ArrayList<String> getMarginablePairsList() {
        return marginablePairsList;
    }

    /**
     * Method to add a marginable pair to {@link #marginablePairsList}
     *
     * @param marginablePair: marginable pair to add
     **/
    public void insertMarginablePair(String marginablePair) {
        if (!marginablePairsList.contains(marginablePair))
            marginablePairsList.add(marginablePair);
    }

    /**
     * Method to remove a marginable pair from {@link #marginablePairsList}
     *
     * @param marginablePair: marginable pair to remove
     * @return result of operation as boolean
     **/
    public boolean removeMarginablePair(String marginablePair) {
        return marginablePairsList.remove(marginablePair);
    }

    /**
     * Method to get a marginable pair from {@link #marginablePairsList} list
     *
     * @param index: index to fetch the marginable pair
     * @return marginable pair as {@link String}
     **/
    public String getMarginablePair(int index) {
        return marginablePairsList.get(index);
    }

}
