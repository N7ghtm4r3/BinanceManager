package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import org.json.JSONObject;

public class BinanceRSAPublicKey extends GiftCardStructure<String> {

    public BinanceRSAPublicKey(boolean success, String data) {
        super(success, data);
    }

    public BinanceRSAPublicKey(JSONObject jBinanceRSAPublicKey) {
        super(jBinanceRSAPublicKey);
        data = hItem.getString("data");
    }

}
