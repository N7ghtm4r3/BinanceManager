package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.TokenGiftCard.TokenData;

public class TokenGiftCard extends GiftCardStructure<TokenData> {

    public TokenGiftCard(boolean success, TokenData data) {
        super(success, data);
    }

    public TokenGiftCard(JSONObject jBinanceTokenGiftCard) {
        super(jBinanceTokenGiftCard);
        data = new TokenData(hItem.getJSONObject("data"));
    }

    public static class TokenData extends BinanceItem {

        private final long referenceNo;
        private final String code;

        public TokenData(long referenceNo, String code) {
            super(null);
            this.referenceNo = referenceNo;
            this.code = code;
        }

        public TokenData(JSONObject jTokenData) {
            super(jTokenData);
            referenceNo = hItem.getLong("referenceNo", 0);
            code = hItem.getString("code");
        }

        public long getReferenceNo() {
            return referenceNo;
        }

        public String getCode() {
            return code;
        }

    }

}
