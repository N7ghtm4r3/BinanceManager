package com.tecknobit.binancemanager.managers.signedmanagers.nft.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTTransactionHistory.NFTTransaction;

/**
 * The {@code NFTTransactionHistory} class is useful to create a NFT transaction history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-transaction-history-user_data">
 * Get NFT Transaction History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class NFTTransactionHistory extends BinanceRowsList<NFTTransaction> {

    /**
     * Constructor to init {@link NFTTransactionHistory} object
     *
     * @param total           : number of transactions
     * @param nftTransactions :  list of the transactions
     */
    public NFTTransactionHistory(int total, ArrayList<NFTTransaction> nftTransactions) {
        super(total, nftTransactions);
    }

    /**
     * Constructor to init {@link NFTTransactionHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public NFTTransactionHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object transaction : hItem.fetchList("list"))
            rows.add(new NFTTransaction((JSONObject) transaction));
    }

    /**
     * The {@code NFTTransaction} class is useful to create a NFT transaction
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class NFTTransaction extends BinanceItem {

        /**
         * {@code orderNo} order number of the NFT transaction
         */
        private final String orderNo;

        /**
         * {@code tokens} of the NFT transaction
         */
        private final ArrayList<NFTAsset> tokens;

        /**
         * {@code tradeTime} trade time of the NFT transaction
         */
        private final long tradeTime;

        /**
         * {@code tradeAmount} trade amount of the NFT transaction
         */
        private final double tradeAmount;

        /**
         * {@code tradeCurrency} trade currency of the NFT transaction
         */
        private final String tradeCurrency;

        /**
         * Constructor to init {@link NFTTransaction} object
         *
         * @param orderNo:       order number of the NFT transaction
         * @param tokens:        tokens of the NFT transaction
         * @param tradeTime:     trade time of the NFT transaction
         * @param tradeAmount:   trade amount of the NFT transaction
         * @param tradeCurrency: trade currency of the NFT transaction
         */
        public NFTTransaction(String orderNo, ArrayList<NFTAsset> tokens, long tradeTime, double tradeAmount,
                              String tradeCurrency) {
            super(null);
            this.orderNo = orderNo;
            this.tokens = tokens;
            this.tradeTime = tradeTime;
            this.tradeAmount = tradeAmount;
            this.tradeCurrency = tradeCurrency;
        }

        /**
         * Constructor to init {@link NFTTransaction} object
         *
         * @param jNFTTransaction: NFT transaction details as {@link JSONObject}
         */
        public NFTTransaction(JSONObject jNFTTransaction) {
            super(jNFTTransaction);
            orderNo = hItem.getString("orderNo");
            tokens = new ArrayList<>();
            for (Object token : hItem.fetchList("tokens"))
                tokens.add(new NFTAsset((JSONObject) token));
            tradeTime = hItem.getLong("tradeTime", 0);
            tradeAmount = hItem.getDouble("tradeAmount", 0);
            tradeCurrency = hItem.getString("tradeCurrency");
        }

        /**
         * Method to get {@link #orderNo} instance <br>
         * No-any params required
         *
         * @return {@link #orderNo} instance as {@link String}
         */
        public String getOrderNo() {
            return orderNo;
        }

        /**
         * Method to get {@link #tokens} instance <br>
         * No-any params required
         *
         * @return {@link #tokens} instance as {@link ArrayList} of {@link NFTAsset}
         */
        public ArrayList<NFTAsset> getTokens() {
            return tokens;
        }

        /**
         * Method to get {@link #tradeTime} instance <br>
         * No-any params required
         *
         * @return {@link #tradeTime} instance as long
         */
        public long getTradeTime() {
            return tradeTime;
        }

        /**
         * Method to get {@link #tradeTime} instance <br>
         * No-any params required
         *
         * @return {@link #tradeTime} instance as {@link Date}
         */
        public Date getTradeDate() {
            return TimeFormatter.getDate(tradeTime);
        }

        /**
         * Method to get {@link #tradeAmount} instance <br>
         * No-any params required
         *
         * @return {@link #tradeAmount} instance as double
         */
        public double getTradeAmount() {
            return tradeAmount;
        }

        /**
         * Method to get {@link #tradeAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #tradeAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTradeAmount(int decimals) {
            return roundValue(tradeAmount, decimals);
        }

        /**
         * Method to get {@link #tradeCurrency} instance <br>
         * No-any params required
         *
         * @return {@link #tradeCurrency} instance as {@link String}
         */
        public String getTradeCurrency() {
            return tradeCurrency;
        }

    }

}
