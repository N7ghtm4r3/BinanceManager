package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.TokenGiftCard.TokenData;

/**
 * The {@code TokenGiftCard} class is useful to create a token gift card
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-single-token-gift-card-user_data">
 *             Create a single-token gift card (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-dual-token-gift-card-fixed-value-discount-feature-trade">
 *             Create a dual-token gift card (fixed value, discount feature) (TRADE)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceResponse
 * @see GiftCardStructure
 **/
public class TokenGiftCard extends GiftCardStructure<TokenData> {

    /**
     * Constructor to init {@link TokenGiftCard} object
     *
     * @param success: whether the operation has been successful
     * @param data:    data of the token giftcard
     **/
    public TokenGiftCard(boolean success, TokenData data) {
        super(success, data);
    }

    /**
     * Constructor to init {@link TokenGiftCard} object
     *
     * @param jTokenGiftCard: token giftcard details as {@link JSONObject}
     **/
    public TokenGiftCard(JSONObject jTokenGiftCard) {
        super(jTokenGiftCard);
        data = new TokenData(hItem.getJSONObject("data"));
    }

    /**
     * The {@code TokenData} class is useful to create a token data
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class TokenData extends BinanceItem {

        /**
         * {@code referenceNo} reference number of the token data
         **/
        private final long referenceNo;

        /**
         * {@code code} of the token data
         **/
        private final String code;

        /**
         * Constructor to init {@link TokenData} object
         *
         * @param referenceNo: reference number of the token data
         * @param code:        code of the token data
         **/
        public TokenData(long referenceNo, String code) {
            super(null);
            this.referenceNo = referenceNo;
            this.code = code;
        }

        /**
         * Constructor to init {@link TokenData} object
         *
         * @param jTokenData: token data details as {@link JSONObject}
         **/
        public TokenData(JSONObject jTokenData) {
            super(jTokenData);
            referenceNo = hItem.getLong("referenceNo", 0);
            code = hItem.getString("code");
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
         * Method to get {@link #code} instance <br>
         * No-any params required
         *
         * @return {@link #code} instance as {@link String}
         **/
        public String getCode() {
            return code;
        }

    }

}
