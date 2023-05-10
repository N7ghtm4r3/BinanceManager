package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code DustItem} class is useful to format a {@code "Binance"}'s dust item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 **/
public class DustItem extends BinanceItem {

    /**
     * {@code transId} is instance that memorizes transaction identifier value
     **/
    private final long transId;

    /**
     * {@code serviceChargeAmount} is instance that memorizes service charge amount value
     **/
    private final double serviceChargeAmount;

    /**
     * {@code amount} is instance that memorizes amount value
     * **/
    private final double amount;

    /**
     * {@code operateTime} is instance that memorizes operate time value
     * **/
    private final long operateTime;

    /**
     * {@code transferedAmount} is instance that memorizes transfered amount value
     * **/
    private final double transferedAmount;

    /**
     * {@code fromAsset} is instance that memorizes from asset value
     * **/
    private final String fromAsset;

    /**
     * Constructor to init {@link DustItem} object
     *
     * @param transId:             transaction identifier value
     * @param serviceChargeAmount: service charge amount value
     * @param amount:              amount value
     * @param operateTime:         operate time value
     * @param transferedAmount:    transfered amount value
     * @param fromAsset:           from asset value
     **/
    public DustItem(long transId, double serviceChargeAmount, double amount, long operateTime,
                    double transferedAmount, String fromAsset) {
        super(null);
        this.transId = transId;
        this.serviceChargeAmount = serviceChargeAmount;
        this.amount = amount;
        this.operateTime = operateTime;
        this.transferedAmount = transferedAmount;
        this.fromAsset = fromAsset;
    }

    /**
     * Constructor to init {@link DustItem} object
     *
     * @param jDustItem: dust item details as {@link JSONObject}
     **/
    public DustItem(JSONObject jDustItem) {
        super(jDustItem);
        transId = hItem.getLong("tranId", 0);
        serviceChargeAmount = hItem.getDouble("serviceChargeAmount", 0);
        amount = hItem.getDouble("amount", 0);
        operateTime = hItem.getLong("operateTime", 0);
        transferedAmount = hItem.getDouble("transferedAmount", 0);
        fromAsset = hItem.getString("fromAsset");
    }

    /**
     * Method to assemble an {@link DustItem} list
     *
     * @param userAssetDribbletDetails: list of items
     * @return list as {@link ArrayList} of {@link DustItem}
     **/
    @Returner
    public static ArrayList<DustItem> getListDribbletsDetails(JSONArray userAssetDribbletDetails) {
        ArrayList<DustItem> dustItems = new ArrayList<>();
        if (userAssetDribbletDetails != null)
            for (int j = 0; j < userAssetDribbletDetails.length(); j++)
                dustItems.add(new DustItem(userAssetDribbletDetails.getJSONObject(j)));
        return dustItems;
    }

    /**
     * Method to get {@link #transId} instance <br>
     * No-any params required
     *
     * @return {@link #transId} instance as long
     **/
    public long getTransId() {
        return transId;
    }

    /**
     * Method to get {@link #serviceChargeAmount} instance <br>
     * No-any params required
     *
     * @return {@link #serviceChargeAmount} instance as double
     **/
    public double getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    /**
     * Method to get {@link #serviceChargeAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #serviceChargeAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getServiceChargeAmount(int decimals) {
        return roundValue(serviceChargeAmount, decimals);
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     **/
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    /**
     * Method to get {@link #operateTime} instance <br>
     * No-any params required
     *
     * @return {@link #operateTime} instance as long
     **/
    public long getOperateTime() {
        return operateTime;
    }

    /**
     * Method to get {@link #operateTime} instance <br>
     * No-any params required
     *
     * @return {@link #operateTime} instance as {@link Date}
     **/
    public Date getOperateDate() {
        return TimeFormatter.getDate(operateTime);
    }

    /**
     * Method to get {@link #transferedAmount} instance <br>
     * No-any params required
     *
     * @return {@link #transferedAmount} instance as double
     **/
    public double getTransferedAmount() {
        return transferedAmount;
    }

    /**
     * Method to get {@link #transferedAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #transferedAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTransferedAmount(int decimals) {
        return roundValue(transferedAmount, decimals);
    }

    /**
     * Method to get {@link #fromAsset} instance <br>
     * No-any params required
     *
     * @return {@link #fromAsset} instance as {@link String}
     **/
    public String getFromAsset() {
        return fromAsset;
    }

}
