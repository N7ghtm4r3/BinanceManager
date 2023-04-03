package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.GiftCardVerification.VerificationData;

/**
 * The {@code GiftCardVerification} class is useful to create a gift card verification
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#verify-binance-gift-card-by-gift-card-number-user_data">
 * Verify Binance Gift Card by Gift Card Number (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see GiftCardStructure
 **/
public class GiftCardVerification extends GiftCardStructure<VerificationData> {

    /**
     * Constructor to init {@link GiftCardVerification} object
     *
     * @param success: whether the operation has been successful
     * @param data: data of the giftcard
     **/
    public GiftCardVerification(boolean success, VerificationData data) {
        super(success, data);
    }

    /**
     * Constructor to init {@link GiftCardVerification} object
     *
     * @param jGitCardVerification: giftcard verification details as {@link JSONObject}
     **/
    public GiftCardVerification(JSONObject jGitCardVerification) {
        super(jGitCardVerification);
        data = new VerificationData(hItem.getJSONObject("data"));
    }

    /**
     * The {@code VerificationData} class is useful to create a verification data
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class VerificationData extends BinanceItem {

        /**
         * {@code valid} whether the giftcard is valid
         **/
        private final boolean valid;

        /**
         * {@code token} of the verification data
         **/
        private final String token;

        /**
         * {@code amount} of the verification data
         **/
        private final double amount;

        /**
         * Constructor to init {@link VerificationData} object
         *
         * @param valid: whether the giftcard is valid
         * @param token: token of the verification data
         * @param amount: amount of the verification data
         **/
        public VerificationData(boolean valid, String token, double amount) {
            super(null);
            this.valid = valid;
            this.token = token;
            this.amount = amount;
        }

        /**
         * Constructor to init {@link VerificationData} object
         *
         * @param jVerificationData: verification data details as {@link JSONObject}
         **/
        public VerificationData(JSONObject jVerificationData) {
            super(jVerificationData);
            valid = hItem.getBoolean("valid");
            token = hItem.getString("token");
            amount = hItem.getDouble("amount", 0);
        }

        /**
         * Method to get {@link #valid} instance <br>
         * No-any params required
         *
         * @return {@link #valid} instance as boolean
         **/
        public boolean isValid() {
            return valid;
        }

        /**
         * Method to get {@link #token} instance <br>
         * No-any params required
         *
         * @return {@link #token} instance as {@link String}
         **/
        public String getToken() {
            return token;
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

    }

}
