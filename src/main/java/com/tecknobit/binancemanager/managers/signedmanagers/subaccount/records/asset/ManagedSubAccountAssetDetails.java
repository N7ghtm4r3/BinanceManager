package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ManagedSubAccountAssetDetails} class is useful to format a managed subaccount asset details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-asset-details-for-investor-master-account">
 * Query Managed Sub-account Asset Details（For Investor Master Account）</a>
 * @see BinanceItem
 * @see AssetTransfer
 **/
public class ManagedSubAccountAssetDetails extends BinanceItem {

    /**
     * {@code coin} of the managed subaccount asset details
     **/
    private final String coin;

    /**
     * {@code name} of the managed subaccount asset details
     **/
    private final String name;

    /**
     * {@code totalBalance} total balance of the managed subaccount asset details
     **/
    private final double totalBalance;

    /**
     * {@code availableBalance} available balance of the managed subaccount asset details
     **/
    private final double availableBalance;

    /**
     * {@code inOrder} in order of the managed subaccount asset details
     **/
    private final double inOrder;

    /**
     * {@code btcValue} btc value of the managed subaccount asset details
     **/
    private final double btcValue;

    /**
     * Constructor to init {@link ManagedSubAccountAssetDetails} object
     *
     * @param coin             : coin of the managed subaccount asset details
     * @param name             : name of the managed subaccount asset details
     * @param totalBalance     : total balance of the managed subaccount asset details
     * @param availableBalance : available balance of the managed subaccount asset details
     * @param inOrder          : in order of the managed subaccount asset details
     * @param btcValue         : btc value of the managed subaccount asset details
     **/
    public ManagedSubAccountAssetDetails(String coin, String name, double totalBalance, double availableBalance,
                                         double inOrder, double btcValue) {
        super(null);
        this.coin = coin;
        this.name = name;
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
        this.inOrder = inOrder;
        this.btcValue = btcValue;
    }

    /**
     * Constructor to init {@link ManagedSubAccountAssetDetails} object
     *
     * @param jManagedSubAccountAssetDetails: managed subaccount asset details as {@link JSONObject}
     **/
    public ManagedSubAccountAssetDetails(JSONObject jManagedSubAccountAssetDetails) {
        super(jManagedSubAccountAssetDetails);
        coin = hItem.getString("coin");
        name = hItem.getString("name");
        totalBalance = hItem.getDouble("totalBalance", 0);
        availableBalance = hItem.getDouble("availableBalance", 0);
        inOrder = hItem.getDouble("inOrder", 0);
        btcValue = hItem.getDouble("btcValue", 0);
    }

    /**
     * Method to get {@link #coin} instance <br>
     * No-any params required
     *
     * @return {@link #coin} instance as {@link String}
     **/
    public String getCoin() {
        return coin;
    }

    /**
     * Method to get {@link #name} instance <br>
     * No-any params required
     *
     * @return {@link #name} instance as {@link String}
     **/
    public String getName() {
        return name;
    }

    /**
     * Method to get {@link #totalBalance} instance <br>
     * No-any params required
     *
     * @return {@link #totalBalance} instance as double
     **/
    public double getTotalBalance() {
        return totalBalance;
    }

    /**
     * Method to get {@link #totalBalance} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalBalance} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalBalance(int decimals) {
        return roundValue(totalBalance, decimals);
    }

    /**
     * Method to get {@link #availableBalance} instance <br>
     * No-any params required
     *
     * @return {@link #availableBalance} instance as double
     **/
    public double getAvailableBalance() {
        return availableBalance;
    }

    /**
     * Method to get {@link #availableBalance} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #availableBalance} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAvailableBalance(int decimals) {
        return roundValue(availableBalance, decimals);
    }

    /**
     * Method to get {@link #inOrder} instance <br>
     * No-any params required
     *
     * @return {@link #inOrder} instance as double
     **/
    public double getInOrder() {
        return inOrder;
    }

    /**
     * Method to get {@link #inOrder} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #inOrder} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInOrder(int decimals) {
        return roundValue(inOrder, decimals);
    }

    /**
     * Method to get {@link #btcValue} instance <br>
     * No-any params required
     *
     * @return {@link #btcValue} instance as double
     **/
    public double getBtcValue() {
        return btcValue;
    }

    /**
     * Method to get {@link #btcValue} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #btcValue} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBtcValue(int decimals) {
        return roundValue(btcValue, decimals);
    }

}
