package com.tecknobit.binancemanager.managers.signedmanagers.nft.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code NFTAsset} class is useful to create a NFT asset
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
public class NFTAsset extends BinanceItem {

    /**
     * {@code NFTOrderType} list of available NFT order types
     */
    public enum NFTOrderType {

        /**
         * {@code PURCHASE_ORDER} NFT order type
         */
        PURCHASE_ORDER(0),

        /**
         * {@code SELL_ORDER} NFT order type
         */
        SELL_ORDER(1),

        /**
         * {@code ROYALTY_INCOME} NFT order type
         */
        ROYALTY_INCOME(2),

        /**
         * {@code PRIMARY_MARKET_ORDER} NFT order type
         */
        PRIMARY_MARKET_ORDER(3),

        /**
         * {@code MINT_FEE} NFT order type
         */
        MINT_FEE(4);

        /**
         * {@code type} value
         */
        private final int type;

        /**
         * Constructor to init {@link NFTOrderType} object
         *
         * @param type: type value
         */
        NFTOrderType(int type) {
            this.type = type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         */
        public int getType() {
            return type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         */
        @Override
        public String toString() {
            return "" + type;
        }

    }

    /**
     * {@code network} of the NFT
     */
    protected final String network;

    /**
     * {@code tokenId} token id of the NFT
     */
    protected final long tokenId;

    /**
     * {@code contractAddress} contract address of the NFT
     */
    protected final String contractAddress;

    /**
     * Constructor to init {@link NFTAsset} object
     *
     * @param network:         network of the NFT
     * @param tokenId:         token id of the NFT
     * @param contractAddress: contract address of the NFT
     */
    public NFTAsset(String network, long tokenId, String contractAddress) {
        super(null);
        this.network = network;
        this.tokenId = tokenId;
        this.contractAddress = contractAddress;
    }

    /**
     * Constructor to init {@link NFTAsset} object
     *
     * @param jNFTAsset: NFT asset details as {@link JSONObject}
     */
    public NFTAsset(JSONObject jNFTAsset) {
        super(jNFTAsset);
        network = hItem.getString("network");
        tokenId = hItem.getLong("tokenId", 0);
        contractAddress = hItem.getString("contractAddress");
    }

    /**
     * Method to get {@link #network} instance <br>
     * No-any params required
     *
     * @return {@link #network} instance as {@link String}
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Method to get {@link #tokenId} instance <br>
     * No-any params required
     *
     * @return {@link #tokenId} instance as long
     */
    public long getTokenId() {
        return tokenId;
    }

    /**
     * Method to get {@link #contractAddress} instance <br>
     * No-any params required
     *
     * @return {@link #contractAddress} instance as {@link String}
     */
    public String getContractAddress() {
        return contractAddress;
    }

}
