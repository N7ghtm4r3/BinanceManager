package com.tecknobit.binancemanager.Managers.Wallet.Records.Asset;

import java.util.ArrayList;

public class ConvertibleBNBAssets {

    private final ArrayList<AssetDetails> assetsDetails;
    private final double totalTransferBtc;
    private final double totalTransferBNB;
    private final double dribbletPercentage;

    public ConvertibleBNBAssets(ArrayList<AssetDetails> assetsDetails, double totalTransferBtc, double totalTransferBNB,
                                double dribbletPercentage) {
        this.assetsDetails = assetsDetails;
        this.totalTransferBtc = totalTransferBtc;
        this.totalTransferBNB = totalTransferBNB;
        this.dribbletPercentage = dribbletPercentage;
    }

    public ArrayList<AssetDetails> assetsDetails() {
        return assetsDetails;
    }

    public double totalTransferBtc() {
        return totalTransferBtc;
    }

    public double totalTransferBNB() {
        return totalTransferBNB;
    }

    public double dribbletPercentage() {
        return dribbletPercentage;
    }

    public static final class AssetDetails {

        private final String asset;
        private final String assetFullName;
        private final double amountFree;
        private final double toBTC;
        private final double toBNB;
        private final double toBNBOffExchange;
        private final double exchange;

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

        public String asset() {
            return asset;
        }

        public String assetFullName() {
            return assetFullName;
        }

        public double amountFree() {
            return amountFree;
        }

        public double toBTC() {
            return toBTC;
        }

        public double toBNB() {
            return toBNB;
        }

        public double toBNBOffExchange() {
            return toBNBOffExchange;
        }

        public double exchange() {
            return exchange;
        }

    }

}
