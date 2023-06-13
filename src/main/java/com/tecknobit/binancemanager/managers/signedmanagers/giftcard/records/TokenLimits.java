package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.BinanceResponseStructure;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.TokenLimits.TokenLimit;

/**
 * The {@code TokenLimits} class is useful to create a token limits
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-token-limit-user_data">
 * Fetch Token Limit (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceManager.BinanceResponse
 * @see BinanceResponseStructure
 */
public class TokenLimits extends BinanceResponseStructure<ArrayList<TokenLimit>> {

    /**
     * Constructor to init {@link TokenLimits} object
     *
     * @param success: whether the operation has been successful
     * @param data:    data of the token limits
     */
    public TokenLimits(boolean success, ArrayList<TokenLimit> data) {
        super(success, data);
    }

    /**
     * Constructor to init {@link TokenLimits} object
     *
     * @param jTokenLimits: token limits details as {@link JSONObject}
     */
    public TokenLimits(JSONObject jTokenLimits) {
        super(jTokenLimits);
        data = new ArrayList<>();
        for (Object limit : hItem.fetchList("data"))
            data.add(new TokenLimit((JSONObject) limit));
    }

    /**
     * The {@code TokenLimit} class is useful to create a token limit
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class TokenLimit extends BinanceItem {

        /**
         * {@code coin} of the token limit
         */
        private final String coin;

        /**
         * {@code fromMin} from min of the token limit
         */
        private final double fromMin;

        /**
         * {@code fromMax} from max of the token limit
         */
        private final double fromMax;

        /**
         * Constructor to init {@link TokenLimit} object
         *
         * @param coin: coin of the token limit
         * @param fromMin: from min of the token limit
         * @param fromMax: from max of the token limit
         */
        public TokenLimit(String coin, double fromMin, double fromMax) {
            super(null);
            this.coin = coin;
            this.fromMin = fromMin;
            this.fromMax = fromMax;
        }

        /**
         * Constructor to init {@link TokenLimit} object
         *
         * @param jTokenLimit: token limit details as {@link JSONObject}
         */
        public TokenLimit(JSONObject jTokenLimit) {
            super(jTokenLimit);
            coin = hItem.getString("coin");
            fromMin = hItem.getDouble("fromMin", 0);
            fromMax = hItem.getDouble("fromMax", 0);
        }

        /**
         * Method to get {@link #coin} instance <br>
         * No-any params required
         *
         * @return {@link #coin} instance as {@link String}
         */
        public String getCoin() {
            return coin;
        }

        /**
         * Method to get {@link #fromMin} instance <br>
         * No-any params required
         *
         * @return {@link #fromMin} instance as double
         */
        public double getFromMin() {
            return fromMin;
        }

        /**
         * Method to get {@link #fromMin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #fromMin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getFromMin(int decimals) {
            return roundValue(fromMin, decimals);
        }

        /**
         * Method to get {@link #fromMax} instance <br>
         * No-any params required
         *
         * @return {@link #fromMax} instance as double
         */
        public double getFromMax() {
            return fromMax;
        }

        /**
         * Method to get {@link #fromMax} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #fromMax} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getFromMax(int decimals) {
            return roundValue(fromMax, decimals);
        }

    }


}
