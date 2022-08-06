package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

import java.util.ArrayList;

/**
 *  The {@code ConvertibleBNBAssets} class is useful to manage ConvertibleBNBAssets Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
 * **/

public class ConvertibleBNBAssets {

    /**
     * {@code assetDividendDetailsList} is instance that memorizes list of {@link AssetDetails}
     * **/
    private ArrayList<AssetDetails> assetsDetailsList;

    /**
     * {@code totalTransferBtc} is instance that memorizes total transfer of Bitcoin
     * **/
    private double totalTransferBtc;

    /**
     * {@code totalTransferBNB} is instance that memorizes total transfer of BNB
     * **/
    private double totalTransferBNB;

    /**
     * {@code dribbletPercentage} is instance that memorizes dribblet percentage value
     * **/
    private double dribbletPercentage;

    /** Constructor to init {@link ConvertibleBNBAssets} object
     * @param assetsDetails: list of {@link AssetDetails}
     * @param totalTransferBtc: total transfer of Bitcoin
     * @param totalTransferBNB: total transfer of BNB
     * @param dribbletPercentage: dribblet percentage value
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
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
        return assetsDetailsList.get(index);
    }

    public double getTotalTransferBtc() {
        return totalTransferBtc;
    }

    /** Method to set {@link #totalTransferBtc}
     * @param totalTransferBtc: total transfer of Bitcoin
     * @throws IllegalArgumentException when total transfer of Bitcoin value is less than 0
     * **/
    public void setTotalTransferBtc(double totalTransferBtc) {
        if(totalTransferBtc < 0)
            throw new IllegalArgumentException("Total transfer BTC value cannot be less than 0");
        this.totalTransferBtc = totalTransferBtc;
    }

    public double getTotalTransferBNB() {
        return totalTransferBNB;
    }

    /** Method to set {@link #totalTransferBNB}
     * @param totalTransferBNB: total transfer of BNB
     * @throws IllegalArgumentException when total transfer of BNB value is less than 0
     * **/
    public void setTotalTransferBNB(double totalTransferBNB) {
        if(totalTransferBNB < 0)
            throw new IllegalArgumentException("Total transfer BNB value cannot be less than 0");
        this.totalTransferBNB = totalTransferBNB;
    }

    public double getDribbletPercentage() {
        return dribbletPercentage;
    }

    /** Method to set {@link #dribbletPercentage}
     * @param dribbletPercentage: dribblet percentage value
     * @throws IllegalArgumentException when dribblet percentage value is less than 0
     * **/
    public void setDribbletPercentage(double dribbletPercentage) {
        if(dribbletPercentage < 0)
            throw new IllegalArgumentException("Dribblet percentage value cannot be less than 0");
        this.dribbletPercentage = dribbletPercentage;
    }

    @Override
    public String toString() {
        return "ConvertibleBNBAssets{" +
                "assetsDetailsList=" + assetsDetailsList +
                ", totalTransferBtc=" + totalTransferBtc +
                ", totalTransferBNB=" + totalTransferBNB +
                ", dribbletPercentage=" + dribbletPercentage +
                '}';
    }

    /**
     *  The {@code AssetDetail} class is useful to obtain and format AssetDetail object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
     *      https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data</a>
     * **/

    public static final class AssetDetails {

        /**
         * {@code asset} is instance that memorizes asset value
         * **/
        private final String asset;

        /**
         * {@code assetFullName} is instance that memorizes asset full name
         * **/
        private final String assetFullName;

        /**
         * {@code amountFree} is instance that memorizes amount free value
         * **/
        private double amountFree;

        /**
         * {@code toBTC} is instance that memorizes to Bitcoin value
         * **/
        private double toBTC;

        /**
         * {@code toBNB} is instance that memorizes to BNB value
         * **/
        private double toBNB;

        /**
         * {@code toBNBOffExchange} is instance that memorizes to BNB of exchange value
         * **/
        private double toBNBOffExchange;

        /**
         * {@code exchange} is instance that memorizes exchange value
         * **/
        private double exchange;

        /** Constructor to init {@link AssetDetails} object
         * @param asset: asset value
         * @param assetFullName: asset full name
         * @param amountFree: amount free value
         * @param toBTC: to Bitcoin value
         * @param toBNB: to BNB value
         * @param toBNBOffExchange: to BNB of exchange value
         * @param exchange: exchange value
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public AssetDetails(String asset, String assetFullName, double amountFree, double toBTC,
                            double toBNB, double toBNBOffExchange, double exchange) {
            this.asset = asset;
            this.assetFullName = assetFullName;
            if(amountFree < 0)
                throw new IllegalArgumentException("Amount free value cannot be less than 0");
            else
                this.amountFree = amountFree;
            if(toBTC < 0)
                throw new IllegalArgumentException("To BTC value cannot be less than 0");
            else
                this.toBTC = toBTC;
            if(toBNB < 0)
                throw new IllegalArgumentException("To BNB value cannot be less than 0");
            else
                this.toBNB = toBNB;
            if(toBNBOffExchange < 0)
                throw new IllegalArgumentException("To BNB off exchange value cannot be less than 0");
            else
                this.toBNBOffExchange = toBNBOffExchange;
            if(exchange < 0)
                throw new IllegalArgumentException("Exchange value cannot be less than 0");
            else
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

        /** Method to set {@link #amountFree}
         * @param amountFree: amount free value
         * @throws IllegalArgumentException when amount free value is less than 0
         * **/
        public void setAmountFree(double amountFree) {
            if(amountFree < 0)
                throw new IllegalArgumentException("Amount free value cannot be less than 0");
            this.amountFree = amountFree;
        }

        public double getToBTC() {
            return toBTC;
        }

        /** Method to set {@link #toBTC}
         * @param toBTC: to Bitcoin value
         * @throws IllegalArgumentException when to Bitcoin value is less than 0
         * **/
        public void setToBTC(double toBTC) {
            if(toBTC < 0)
                throw new IllegalArgumentException("To BTC value cannot be less than 0");
            this.toBTC = toBTC;
        }

        public double getToBNB() {
            return toBNB;
        }

        /** Method to set {@link #toBNB}
         * @param toBNB: to BNB value
         * @throws IllegalArgumentException when to BNB value is less than 0
         * **/
        public void setToBNB(double toBNB) {
            if(toBNB < 0)
                throw new IllegalArgumentException("To BNB value cannot be less than 0");
            this.toBNB = toBNB;
        }

        public double getToBNBOffExchange() {
            return toBNBOffExchange;
        }

        /** Method to set {@link #toBNBOffExchange}
         * @param toBNBOffExchange: to BNB of exchange value
         * @throws IllegalArgumentException when to BNB of exchange value is less than 0
         * **/
        public void setToBNBOffExchange(double toBNBOffExchange) {
            if(toBNBOffExchange < 0)
                throw new IllegalArgumentException("To BNB off exchange value cannot be less than 0");
            this.toBNBOffExchange = toBNBOffExchange;
        }

        public double getExchange() {
            return exchange;
        }

        /** Method to set {@link #exchange}
         * @param exchange: exchange value
         * @throws IllegalArgumentException when exchange value value is less than 0
         * **/
        public void setExchange(double exchange) {
            if(exchange < 0)
                throw new IllegalArgumentException("Exchange value cannot be less than 0");
            this.exchange = exchange;
        }

        @Override
        public String toString() {
            return "AssetDetails{" +
                    "asset='" + asset + '\'' +
                    ", assetFullName='" + assetFullName + '\'' +
                    ", amountFree=" + amountFree +
                    ", toBTC=" + toBTC +
                    ", toBNB=" + toBNB +
                    ", toBNBOffExchange=" + toBNBOffExchange +
                    ", exchange=" + exchange +
                    '}';
        }

    }

}
