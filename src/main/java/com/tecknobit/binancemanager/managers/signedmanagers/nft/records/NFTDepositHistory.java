package com.tecknobit.binancemanager.managers.signedmanagers.nft.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTDepositHistory.NFTDeposit;

/**
 * The {@code NFTDepositHistory} class is useful to create a NFT deposit history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-deposit-history-user_data">
 * Get NFT Deposit History(USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class NFTDepositHistory extends BinanceRowsList<NFTDeposit> {

    /**
     * Constructor to init {@link NFTDepositHistory} object
     *
     * @param total    : number of deposits
     * @param deposits :  list of the deposits
     */
    public NFTDepositHistory(int total, ArrayList<NFTDeposit> deposits) {
        super(total, deposits);
    }

    /**
     * Constructor to init {@link NFTDepositHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     */
    public NFTDepositHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object deposit : hItem.fetchList("list"))
            rows.add(new NFTDeposit((JSONObject) deposit));
    }

    /**
     * The {@code NFTDeposit} class is useful to create a NFT deposit
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see NFTAsset
     */
    public static class NFTDeposit extends NFTAsset {

        /**
         * {@code txId} transaction id of the NFT deposit
         */
        protected final String txId;

        /**
         * {@code timestamp} of the NFT deposit
         */
        protected final long timestamp;

        /**
         * Constructor to init {@link NFTDeposit} object
         *
         * @param network:         network of the NFT deposit
         * @param tokenId:         token id of the NFT deposit
         * @param contractAddress: contract address of the NFT deposit
         * @param txId:            transaction id of the NFT deposit
         * @param timestamp:       timestamp of the NFT deposit
         */
        public NFTDeposit(String network, long tokenId, String contractAddress, String txId, long timestamp) {
            super(network, tokenId, contractAddress);
            this.txId = txId;
            this.timestamp = timestamp;
        }

        /**
         * Constructor to init {@link NFTDeposit} object
         *
         * @param jNFTDeposit: NFT deposit details as {@link JSONObject}
         */
        public NFTDeposit(JSONObject jNFTDeposit) {
            super(jNFTDeposit);
            txId = hItem.getString("txID");
            timestamp = hItem.getLong("timestamp", 0);
        }

        /**
         * Method to get {@link #txId} instance <br>
         * No-any params required
         *
         * @return {@link #txId} instance as {@link String}
         */
        public String getTxId() {
            return txId;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as long
         */
        public long getTimestamp() {
            return timestamp;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as {@link Date}
         */
        public Date getDate() {
            return TimeFormatter.getDate(timestamp);
        }

    }

}
