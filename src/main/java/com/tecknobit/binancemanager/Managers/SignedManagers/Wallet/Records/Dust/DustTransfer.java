package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import java.util.ArrayList;

/**
 *  The {@code DustTransfer} class is useful to manage DustTransfer Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
 * **/

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

    public double totalServiceCharge() {
        return totalServiceCharge;
    }

    public double totalTransfered() {
        return totalTransfered;
    }

    public ArrayList<DustItem> transferResults() {
        return transferResultsList;
    }

    public void setTransferResultsList(ArrayList<DustItem> transferResultsList) {
        this.transferResultsList = transferResultsList;
    }

    public void insertTransferResult(DustItem transferResult){
        if(!transferResultsList.contains(transferResult))
            transferResultsList.add(transferResult);
    }

    public boolean removeTransferResult(DustItem transferResult){
        return transferResultsList.remove(transferResult);
    }

    public DustItem getAssetDribbletDetails(int index){
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
