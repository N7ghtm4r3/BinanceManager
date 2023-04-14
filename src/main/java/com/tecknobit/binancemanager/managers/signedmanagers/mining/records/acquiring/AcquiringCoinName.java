package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring.AcquiringCoinName.AcquiringCoinNameItem;

/**
 * The {@code AcquiringCoinName} class is useful to create an acquiring coin name
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-coinname-market_data">
 * Acquiring CoinName (MARKET_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 **/
public class AcquiringCoinName extends MiningResponse<ArrayList<AcquiringCoinNameItem>> {

    /**
     * Constructor to init {@link AcquiringCoinName} object
     *
     * @param data: acquiring coin names
     **/
    public AcquiringCoinName(ArrayList<AcquiringCoinNameItem> data) {
        super(data);
    }

    /**
     * Constructor to init {@link AcquiringCoinName} object
     *
     * @param jAcquiringCoinName: acquiring coin name response as {@link JSONObject}
     **/
    public AcquiringCoinName(JSONObject jAcquiringCoinName) {
        super(jAcquiringCoinName);
        data = new ArrayList<>();
        for (Object rData : hItem.fetchList("data"))
            data.add(new AcquiringCoinNameItem((JSONObject) rData));
    }

    /**
     * The {@code AcquiringCoinNameItem} class is useful to create an acquiring coin name item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see AcquiringStructure
     **/
    public static class AcquiringCoinNameItem extends AcquiringStructure {

        /**
         * {@code coinName} name of the coin item
         **/
        private final String coinName;

        /**
         * {@code coinId} id of the coin name item
         **/
        private final long coinId;

        /**
         * Constructor to init {@link AcquiringCoinNameItem} object
         *
         * @param algoName: algo name of the coin name item
         * @param algoId: algo id of the coin name item
         * @param poolIndex: pool index of the coin name item
         * @param coinName: name of the coin item
         * @param coinId: id of the coin name item
         **/
        public AcquiringCoinNameItem(String algoName, long algoId, int poolIndex, String coinName, long coinId) {
            super(algoName, algoId, poolIndex);
            this.coinName = coinName;
            this.coinId = coinId;
        }

        /**
         * Constructor to init {@link AcquiringCoinNameItem} object
         *
         * @param jAcquiringCoinNameItem: acquiring coin name item details as {@link JSONObject}
         **/
        public AcquiringCoinNameItem(JSONObject jAcquiringCoinNameItem) {
            super(jAcquiringCoinNameItem);
            coinName = hItem.getString("coinName");
            coinId = hItem.getLong("coinId", 0);
        }

        /**
         * Method to get {@link #coinName} instance <br>
         * No-any params required
         *
         * @return {@link #coinName} instance as {@link String}
         **/
        public String getCoinName() {
            return coinName;
        }

        /**
         * Method to get {@link #coinId} instance <br>
         * No-any params required
         *
         * @return {@link #coinId} instance as long
         **/
        public long getCoinId() {
            return coinId;
        }

    }

}
