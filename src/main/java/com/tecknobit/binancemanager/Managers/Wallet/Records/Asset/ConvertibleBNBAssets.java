package com.tecknobit.binancemanager.Managers.Wallet.Records.Asset;

import java.util.ArrayList;

public record ConvertibleBNBAssets (ArrayList<AssetDetails> assetsDetails, double totalTransferBtc, double totalTransferBNB,
                                    double dribbletPercentage) {

    @Override
    public ArrayList<AssetDetails> assetsDetails() {
        return assetsDetails;
    }

    @Override
    public double totalTransferBtc() {
        return totalTransferBtc;
    }

    @Override
    public double totalTransferBNB() {
        return totalTransferBNB;
    }

    @Override
    public double dribbletPercentage() {
        return dribbletPercentage;
    }

    public record AssetDetails (String asset, String assetFullName, double amountFree, double toBTC,
                                double toBNB, double toBNBOffExchange, double exchange) {

        @Override
        public String asset() {
            return asset;
        }

        @Override
        public String assetFullName() {
            return assetFullName;
        }

        @Override
        public double amountFree() {
            return amountFree;
        }

        @Override
        public double toBTC() {
            return toBTC;
        }

        @Override
        public double toBNB() {
            return toBNB;
        }

        @Override
        public double toBNBOffExchange() {
            return toBNBOffExchange;
        }

        @Override
        public double exchange() {
            return exchange;
        }

    }

}
