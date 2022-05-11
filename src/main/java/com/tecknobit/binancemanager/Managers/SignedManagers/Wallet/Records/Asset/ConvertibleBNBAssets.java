package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

import java.util.ArrayList;

/**
 *  The {@code ConvertibleBNBAssets} class is useful to manage ConvertibleBNBAssets Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
 * **/

public class ConvertibleBNBAssets {

    private ArrayList<AssetDetails> assetsDetailsList;
    private double totalTransferBtc;
    private double totalTransferBNB;
    private double dribbletPercentage;

    public ConvertibleBNBAssets(ArrayList<AssetDetails> assetsDetails, double totalTransferBtc, double totalTransferBNB,
                                double dribbletPercentage) {
        this.assetsDetailsList = assetsDetails;
        this.totalTransferBtc = totalTransferBtc;
        this.totalTransferBNB = totalTransferBNB;
        this.dribbletPercentage = dribbletPercentage;
    }

    public ArrayList<AssetDetails> getAssetsDetailsList() {
        return assetsDetailsList;
    }

    public void setAssetsDetailsList(ArrayList<AssetDetails> assetsDetailsList) {
        this.assetsDetailsList = assetsDetailsList;
    }

    public void insertAssetDetails(AssetDetails assetDetails){
        if(!assetsDetailsList.contains(assetDetails))
            assetsDetailsList.add(assetDetails);
    }

    public boolean removeAssetDetails(AssetDetails assetDetails){
        return assetsDetailsList.remove(assetDetails);
    }

    public AssetDetails getAssetDetails(int index){
        try{
            return assetsDetailsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(index);
        }
    }

    public double getTotalTransferBtc() {
        return totalTransferBtc;
    }

    public void setTotalTransferBtc(double totalTransferBtc) {
        if(totalTransferBtc < 0)
            throw new IllegalArgumentException("Total transfer BTC value cannot be less than 0");
        this.totalTransferBtc = totalTransferBtc;
    }

    public double getTotalTransferBNB() {
        return totalTransferBNB;
    }

    public void setTotalTransferBNB(double totalTransferBNB) {
        if(totalTransferBNB < 0)
            throw new IllegalArgumentException("Total transfer BNB value cannot be less than 0");
        this.totalTransferBNB = totalTransferBNB;
    }

    public double getDribbletPercentage() {
        return dribbletPercentage;
    }

    public void setDribbletPercentage(double dribbletPercentage) {
        if(dribbletPercentage < 0)
            throw new IllegalArgumentException("Dribblet percentage value cannot be less than 0");
        this.dribbletPercentage = dribbletPercentage;
    }

    /**
     *  The {@code AssetDetail} class is useful to obtain and format AssetDetail object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * **/

    public static final class AssetDetails {

        private final String asset;
        private final String assetFullName;
        private double amountFree;
        private double toBTC;
        private double toBNB;
        private double toBNBOffExchange;
        private double exchange;

        public AssetDetails(String asset, String assetFullName, double amountFree, double toBTC,
                            double toBNB, double toBNBOffExchange, double exchange) {
            this.asset = asset;
            this.assetFullName = assetFullName;
            this.amountFree = amountFree;
            this.toBTC = toBTC;
            this.toBNB = toBNB;
            this.toBNBOffExchange = toBNBOffExchange;
            this.exchange = exchange;
        }

        public String getAsset() {
            return asset;
        }

        public String getAssetFullName() {
            return assetFullName;
        }

        public double getAmountFree() {
            return amountFree;
        }

        public void setAmountFree(double amountFree) {
            if(amountFree < 0)
                throw new IllegalArgumentException("Amount free value cannot be less than 0");
            this.amountFree = amountFree;
        }

        public double getToBTC() {
            return toBTC;
        }

        public void setToBTC(double toBTC) {
            if(toBTC < 0)
                throw new IllegalArgumentException("To BTC value cannot be less than 0");
            this.toBTC = toBTC;
        }

        public double getToBNB() {
            return toBNB;
        }

        public void setToBNB(double toBNB) {
            if(toBNB < 0)
                throw new IllegalArgumentException("To BNB value cannot be less than 0");
            this.toBNB = toBNB;
        }

        public double getToBNBOffExchange() {
            return toBNBOffExchange;
        }

        public void setToBNBOffExchange(double toBNBOffExchange) {
            if(toBNBOffExchange < 0)
                throw new IllegalArgumentException("To BNB off exchange value cannot be less than 0");
            this.toBNBOffExchange = toBNBOffExchange;
        }

        public double getExchange() {
            return exchange;
        }

        public void setExchange(double exchange) {
            if(exchange < 0)
                throw new IllegalArgumentException("Exchange value cannot be less than 0");
            this.exchange = exchange;
        }

    }

}
