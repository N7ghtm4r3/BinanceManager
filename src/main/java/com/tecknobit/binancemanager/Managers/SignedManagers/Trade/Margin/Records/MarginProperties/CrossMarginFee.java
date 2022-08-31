package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties.IsolatedMarginFee;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code CrossMarginFee} class is useful to format Binance Cross Margin Fee request response
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
 **/

public class CrossMarginFee extends IsolatedMarginFee.IsolatedData {

    /**
     * {@code vipLevel} is instance that memorizes vip level
     * **/
    private int vipLevel;

    /**
     * {@code yearlyInterest} is instance that memorizes if is a transfer in
     * **/
    private boolean transferIn;

    /**
     * {@code borrowable} is instance that memorizes if is borrowable
     * **/
    private boolean borrowable;

    /**
     * {@code yearlyInterest} is instance that memorizes value of yearly interest
     * **/
    private double yearlyInterest;

    /**
     * {@code marginablePairsList} is instance that memorizes list of pairs data
     * **/
    private ArrayList<String> marginablePairsList;

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
        loadMarginablePairsList(marginFee.getJSONArray("marginablePairs"));
    }

    /**
     * Method to load MarginablePairs list
     *
     * @param jsonAssets: obtained from Binance's request
     **/
    private void loadMarginablePairsList(JSONArray jsonAssets) {
        marginablePairsList = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            marginablePairsList.add(jsonAssets.getString(j));
    }

    public int getVipLevel() {
        return vipLevel;
    }

    /** Method to set {@link #vipLevel}
     * @param vipLevel: vip level
     * @throws IllegalArgumentException when vip level value is less than 0
     * **/
    public void setVipLevel(int vipLevel) {
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        this.vipLevel = vipLevel;
    }

    public boolean isTransferIn() {
        return transferIn;
    }

    public void setTransferIn(boolean transferIn) {
        this.transferIn = transferIn;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

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
     * Method to set {@link #yearlyInterest}
     *
     * @param yearlyInterest: value of yearly interest
     * @throws IllegalArgumentException when value of yearly interest is less than 0
     **/
    public void setYearlyInterest(double yearlyInterest) {
        if (yearlyInterest < 0)
            throw new IllegalArgumentException("Yearly interest value cannot be less than 0");
        this.yearlyInterest = yearlyInterest;
    }

    public ArrayList<String> getMarginablePairsList() {
        return marginablePairsList;
    }

    public void setMarginablePairsList(ArrayList<String> marginablePairsList) {
        this.marginablePairsList = marginablePairsList;
    }

    public void insertMarginablePair(String marginablePair){
        if(!marginablePairsList.contains(marginablePair))
            marginablePairsList.add(marginablePair);
    }

    public boolean removeMarginablePair(String marginablePair){
        return marginablePairsList.remove(marginablePair);
    }

    public String getMarginablePair(int index){
        return marginablePairsList.get(index);
    }

    @Override
    public String toString() {
        return "CrossMarginFee{" +
                "vipLevel=" + vipLevel +
                ", transferIn=" + transferIn +
                ", borrowable=" + borrowable +
                ", yearlyInterest=" + yearlyInterest +
                ", marginablePairsList=" + marginablePairsList +
                ", coin='" + coin + '\'' +
                ", dailyInterest=" + dailyInterest +
                ", borrowLimit=" + borrowLimit +
                '}';
    }

}
