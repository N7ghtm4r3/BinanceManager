package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.GiftCard.GiftCardData;

/**
 * The {@code GiftCard} class is useful to create a gift card
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-a-binance-gift-card-user_data">
 * Redeem a Binance Gift Card (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see GiftCardStructure
 **/
public class GiftCard extends GiftCardStructure<GiftCardData> {

    /**
     * Constructor to init {@link GiftCard} object
     *
     * @param success: whether the operation has been successful
     * @param data: data of the giftcard
     **/
    public GiftCard(boolean success, GiftCardData data) {
        super(success, data);
    }

    /**
     * Constructor to init {@link GiftCard} object
     *
     * @param jGiftCard: giftcard details as {@link JSONObject}
     **/
    public GiftCard(JSONObject jGiftCard) {
        super(jGiftCard);
        data = new GiftCardData(hItem.getJSONObject("data"));
    }

    /**
     * The {@code GiftCardData} class is useful to create a gift card data
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class GiftCardData extends BinanceItem {

        /**
         * {@code referenceNo} reference number of the giftcard data
         **/
        private final long referenceNo;

        /**
         * {@code identifyNo} identify number of the giftcard data
         **/
        private final String identifyNo;

        /**
         * {@code token} of the giftcard data
         **/
        private final String token;

        /**
         * {@code amount} of the giftcard data
         **/
        private final double amount;

        /**
         * Constructor to init {@link GiftCardData} object
         *
         * @param referenceNo: reference number of the giftcard data
         * @param identifyNo: identify number of the giftcard data
         * @param token: token of the giftcard data
         * @param amount: amount of the giftcard data
         **/
        public GiftCardData(long referenceNo, String identifyNo, String token, double amount) {
            super(null);
            this.referenceNo = referenceNo;
            this.identifyNo = identifyNo;
            this.token = token;
            this.amount = amount;
        }

        /**
         * Constructor to init {@link GiftCardData} object
         *
         * @param jGiftCardData: giftcard data details as {@link JSONObject}
         **/
        public GiftCardData(JSONObject jGiftCardData) {
            super(jGiftCardData);
            referenceNo = hItem.getLong("referenceNo", 0);
            identifyNo = hItem.getString("identityNo");
            token = hItem.getString("token");
            amount = hItem.getDouble("amount", 0);
        }

        /**
         * Method to get {@link #referenceNo} instance <br>
         * No-any params required
         *
         * @return {@link #referenceNo} instance as long
         **/
        public long getReferenceNo() {
            return referenceNo;
        }

        /**
         * Method to get {@link #identifyNo} instance <br>
         * No-any params required
         *
         * @return {@link #identifyNo} instance as {@link String}
         **/
        public String getIdentifyNo() {
            return identifyNo;
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
