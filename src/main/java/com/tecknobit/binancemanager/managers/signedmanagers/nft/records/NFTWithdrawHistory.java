package com.tecknobit.binancemanager.managers.signedmanagers.nft.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTDepositHistory.NFTDeposit;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.nft.records.NFTWithdrawHistory.NFTWithdraw;

/**
 * The {@code NFTWithdrawHistory} class is useful to create a NFT withdraw history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-withdraw-history-user_data">
 * Get NFT Withdraw History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 **/
public class NFTWithdrawHistory extends BinanceRowsList<NFTWithdraw> {

    /**
     * Constructor to init {@link NFTWithdrawHistory} object
     *
     * @param total       : number of withdrawals
     * @param withdrawals :  list of the withdrawals
     **/
    public NFTWithdrawHistory(int total, ArrayList<NFTWithdraw> withdrawals) {
        super(total, withdrawals);
    }

    /**
     * Constructor to init {@link NFTWithdrawHistory}
     *
     * @param jHistory : history details as {@link JSONObject}
     **/
    public NFTWithdrawHistory(JSONObject jHistory) {
        super(jHistory);
        for (Object withdraw : hItem.fetchList("list"))
            rows.add(new NFTWithdraw((JSONObject) withdraw));
    }

    /**
     * The {@code NFTWithdraw} class is useful to create a NFT withdraw
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see NFTAsset
     * @see NFTDeposit
     **/
    public static class NFTWithdraw extends NFTDeposit {

        /**
         * {@code fee} of the NFT withdraw
         **/
        private final double fee;

        /**
         * {@code feeAsset} fee asset of the NFT withdraw
         **/
        private final String feeAsset;

        /**
         * Constructor to init {@link NFTWithdraw} object
         *
         * @param network:         network of the NFT withdraw
         * @param tokenId:         token id of the NFT withdraw
         * @param contractAddress: contract address of the NFT withdraw
         * @param txId:            transaction id of the NFT withdraw
         * @param timestamp:       timestamp of the NFT withdraw
         * @param fee:             fee of the NFT withdraw
         * @param feeAsset:        fee asset of the NFT withdraw
         **/
        public NFTWithdraw(String network, long tokenId, String contractAddress, String txId, long timestamp, double fee,
                           String feeAsset) {
            super(network, tokenId, contractAddress, txId, timestamp);
            this.fee = fee;
            this.feeAsset = feeAsset;
        }

        /**
         * Constructor to init {@link NFTWithdraw} object
         *
         * @param jNFTWithdraw: NFT withdraw details as {@link JSONObject}
         **/
        public NFTWithdraw(JSONObject jNFTWithdraw) {
            super(jNFTWithdraw);
            fee = hItem.getDouble("fee", 0);
            feeAsset = hItem.getString("feeAsset");
        }

        /**
         * Method to get {@link #fee} instance <br>
         * No-any params required
         *
         * @return {@link #fee} instance as double
         **/
        public double getFee() {
            return fee;
        }

        /**
         * Method to get {@link #fee} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #fee} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getFee(int decimals) {
            return roundValue(fee, decimals);
        }

        /**
         * Method to get {@link #feeAsset} instance <br>
         * No-any params required
         *
         * @return {@link #feeAsset} instance as {@link String}
         **/
        public String getFeeAsset() {
            return feeAsset;
        }

    }

}
