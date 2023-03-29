package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset.AssetsDividendList.AssetDividendDetails;

/**
 * The {@code AssetsDividendList} class is useful to format a {@code "Binance"}'s asset dividend
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data">
 * Asset Dividend Record (USER_DATA)</a>
 * @see BinanceRowsList
 **/
public class AssetsDividendList extends BinanceRowsList<AssetDividendDetails> {

    /**
     * Constructor to init {@link AssetsDividendList} object
     *
     * @param total:                total size of {@link #rows}
     * @param assetDividendDetails: list of {@link AssetDividendDetails}
     **/
    public AssetsDividendList(int total, ArrayList<AssetDividendDetails> assetDividendDetails) {
        super(total, assetDividendDetails);
    }

    /**
     * Constructor to init {@link AssetsDividendList} object
     *
     * @param jAssetDividend : asset dividend details as {@link JSONObject}
     **/
    public AssetsDividendList(JSONObject jAssetDividend) {
        super(jAssetDividend);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new AssetDividendDetails((JSONObject) row));
    }

    /**
     * The {@code AssetDividendDetails} class is useful to create an asset with its dividend details
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static final class AssetDividendDetails {

        /**
         * {@code id} is instance that memorizes asset dividend details identifier
         * **/
        private final long id;

        /**
         * {@code id} is instance that memorizes amount value
         * **/
        private final double amount;

        /**
         * {@code asset} is instance that memorizes asset value
         * **/
        private final String asset;

        /**
         * {@code divTime} is instance that memorizes division time value
         * **/
        private final long divTime;

        /**
         * {@code enInfo} is instance that memorizes en info value
         * **/
        private final String enInfo;

        /**
         * {@code tranId} is instance that memorizes transaction identifier
         * **/
        private final long tranId;

        /**
         * Constructor to init {@link AssetDividendDetails} object
         *
         * @param id:      identifier of the asset
         * @param amount:  asset dividend details identifier
         * @param asset:   asset value
         * @param divTime: division time value
         * @param enInfo:  en info value
         * @param tranId:  transaction identifier
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public AssetDividendDetails(long id, double amount, String asset, long divTime, String enInfo, long tranId) {
            this.id = id;
            this.amount = amount;
            this.asset = asset;
            this.divTime = divTime;
            this.enInfo = enInfo;
            this.tranId = tranId;
        }

        /**
         * Constructor to init {@link AssetDividendDetails} object
         *
         * @param jDividend: dividend details as {@link JSONObject}
         **/
        public AssetDividendDetails(JSONObject jDividend) {
            this(jDividend.getLong("id"), jDividend.getDouble("amount"), jDividend.getString("asset"),
                    jDividend.getLong("divTime"), jDividend.getString("enInfo"), jDividend.getLong("tranId"));
        }

        /**
         * Method to get {@link #id} instance <br>
         * No-any params required
         *
         * @return {@link #id} instance as long
         **/
        public long getId() {
            return id;
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

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         **/
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #divTime} instance <br>
         * No-any params required
         *
         * @return {@link #divTime} instance as long
         **/
        public long getDivTime() {
            return divTime;
        }

        /**
         * Method to get {@link #divTime} instance <br>
         * No-any params required
         *
         * @return {@link #divTime} instance as {@link Date}
         **/
        public Date getDivDate() {
            return TimeFormatter.getDate(divTime);
        }

        /**
         * Method to get {@link #enInfo} instance <br>
         * No-any params required
         *
         * @return {@link #enInfo} instance as {@link String}
         **/
        public String getEnInfo() {
            return enInfo;
        }

        /**
         * Method to get {@link #tranId} instance <br>
         * No-any params required
         *
         * @return {@link #tranId} instance as long
         **/
        public long getTranId() {
            return tranId;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
