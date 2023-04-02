package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.GiftCard.GiftCardData;

public class GiftCard extends GiftCardStructure<GiftCardData> {

    public GiftCard(boolean success, GiftCardData data) {
        super(success, data);
    }

    public GiftCard(JSONObject jBinanceGiftCard) {
        super(jBinanceGiftCard);
        data = new GiftCardData(hItem.getJSONObject("data"));
    }

    public static class GiftCardData extends BinanceItem {

        private final long referenceNo;
        private final String identifyNo;
        private final String token;
        private final double amount;

        public GiftCardData(long referenceNo, String identifyNo, String token, double amount) {
            super(null);
            this.referenceNo = referenceNo;
            this.identifyNo = identifyNo;
            this.token = token;
            this.amount = amount;
        }

        public GiftCardData(JSONObject jGiftCardData) {
            super(jGiftCardData);
            referenceNo = hItem.getLong("referenceNo", 0);
            identifyNo = hItem.getString("identityNo");
            token = hItem.getString("token");
            amount = hItem.getDouble("amount", 0);
        }

        public long getReferenceNo() {
            return referenceNo;
        }

        public String getIdentifyNo() {
            return identifyNo;
        }

        public String getToken() {
            return token;
        }

        public double getAmount() {
            return amount;
        }

    }

}
