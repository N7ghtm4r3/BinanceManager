package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code DustItem} class is useful to format a {@code "Binance"}'s dust item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
 * DustLogList(USER_DATA)</a>
 **/
public class DustItem {

    /**
     * {@code transId} is instance that memorizes transaction identifier value
     * **/
    private final long transId;

    /**
     * {@code serviceChargeAmount} is instance that memorizes service charge amount value
     * **/
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
        this(jDustItem.getLong("tranId"), jDustItem.getDouble("serviceChargeAmount"),
                jDustItem.getDouble("amount"), jDustItem.getLong("operateTime"),
                jDustItem.getDouble("transferedAmount"), jDustItem.getString("fromAsset"));
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

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
