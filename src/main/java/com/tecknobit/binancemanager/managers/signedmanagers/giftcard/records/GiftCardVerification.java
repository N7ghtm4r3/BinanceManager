package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.GiftCardVerification.VerificationData;

public class GiftCardVerification extends GiftCardStructure<VerificationData> {

    public GiftCardVerification(boolean success, VerificationData data) {
        super(success, data);
    }

    public GiftCardVerification(JSONObject jGitCardVerification) {
        super(jGitCardVerification);
        data = new VerificationData(hItem.getJSONObject("data"));
    }

    public static class VerificationData extends BinanceItem {

        private final boolean valid;
        private final String token;
        private final double amount;

        public VerificationData(boolean valid, String token, double amount) {
            super(null);
            this.valid = valid;
            this.token = token;
            this.amount = amount;
        }

        public VerificationData(JSONObject jVerificationData) {
            super(jVerificationData);
            valid = hItem.getBoolean("valid");
            token = hItem.getString("token");
            amount = hItem.getDouble("amount", 0);
        }

        public boolean isValid() {
            return valid;
        }

        public String getToken() {
            return token;
        }

        public double getAmount() {
            return amount;
        }

    }

}
