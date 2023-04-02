package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.TokenLimits.TokenLimit;

public class TokenLimits extends GiftCardStructure<ArrayList<TokenLimit>> {

    public TokenLimits(boolean success, ArrayList<TokenLimit> data) {
        super(success, data);
    }

    public TokenLimits(JSONObject jTokenLimits) {
        super(jTokenLimits);
        data = new ArrayList<>();
        for (Object limit : hItem.fetchList("data"))
            data.add(new TokenLimit((JSONObject) limit));
    }

    public static class TokenLimit extends BinanceItem {

        private final String coin;
        private final double fromMin;
        private final double fromMax;

        public TokenLimit(String coin, double fromMin, double fromMax) {
            super(null);
            this.coin = coin;
            this.fromMin = fromMin;
            this.fromMax = fromMax;
        }

        public TokenLimit(JSONObject jTokenLimit) {
            super(jTokenLimit);
            coin = hItem.getString("coin");
            fromMin = hItem.getDouble("fromMin", 0);
            fromMax = hItem.getDouble("fromMax", 0);
        }

        public String getCoin() {
            return coin;
        }

        public double getFromMin() {
            return fromMin;
        }

        public double getFromMax() {
            return fromMax;
        }

    }


}
