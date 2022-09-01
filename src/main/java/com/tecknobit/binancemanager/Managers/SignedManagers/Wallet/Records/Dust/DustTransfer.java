package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust.DustItem.getListDribbletsDetails;

/**
 * The {@code DustTransfer} class is useful to manage DustTransfer Binance request
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
 **/

public class DustTransfer {

    /**
     * {@code totalServiceCharge} is instance that memorizes total service charge value
     * **/
    private final double totalServiceCharge;

    /**
     * {@code totalTransfered} is instance that memorizes total transferred value
     * **/
    private final double totalTransfered;

    /**
     * {@code transferResultsList} is instance that memorizes list of {@link DustItem}
     * **/
    private ArrayList<DustItem> transferResultsList;

    /** Constructor to init {@link DustLog} object
     * @param totalServiceCharge: total service charge value
     * @param totalTransfered: total transferred value
     * @param transferResults: list of {@link DustItem}
     * **/
    public DustTransfer(double totalServiceCharge, double totalTransfered, ArrayList<DustItem> transferResults) {
        this.totalServiceCharge = totalServiceCharge;
        this.totalTransfered = totalTransfered;
        this.transferResultsList = transferResults;
    }

    /**
     * Constructor to init {@link DustLog} object
     *
     * @param dustTransfer: dust transfer details as {@link JSONObject}
     **/
    public DustTransfer(JSONObject dustTransfer) {
        totalServiceCharge = dustTransfer.getDouble("totalServiceCharge");
        totalTransfered = dustTransfer.getDouble("totalTransfered");
        transferResultsList = getListDribbletsDetails(dustTransfer.getJSONArray("transferResult"));
    }

    public double getTotalServiceCharge() {
        return totalServiceCharge;
    }

    /**
     * Method to get {@link #totalServiceCharge} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalServiceCharge} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalServiceCharge(int decimals) {
        return roundValue(totalServiceCharge, decimals);
    }

    public double getTotalTransfered() {
        return totalTransfered;
    }

    /**
     * Method to get {@link #totalTransfered} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalTransfered} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalTransfered(int decimals) {
        return roundValue(totalTransfered, decimals);
    }

    public ArrayList<DustItem> getTransferResultsList() {
        return transferResultsList;
    }

    public void setTransferResultsList(ArrayList<DustItem> transferResultsList) {
        this.transferResultsList = transferResultsList;
    }

    public void insertTransferResult(DustItem transferResult) {
        if (!transferResultsList.contains(transferResult))
            transferResultsList.add(transferResult);
    }

    public boolean canRemoveTransferResult(DustItem transferResult) {
        return transferResultsList.remove(transferResult);
    }

    public DustItem getAssetDribbletDetails(int index) {
        return transferResultsList.get(index);
    }

    @Override
    public String toString() {
        return "DustTransfer{" +
                "totalServiceCharge=" + totalServiceCharge +
                ", totalTransfered=" + totalTransfered +
                ", transferResultsList=" + transferResultsList +
                '}';
    }

}
