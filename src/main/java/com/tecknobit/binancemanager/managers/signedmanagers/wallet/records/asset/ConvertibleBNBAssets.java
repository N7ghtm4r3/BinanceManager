package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ConvertibleBNBAssets} class is useful to format a {@code "Binance"}'s convertible BNB Asset
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data">
 * Get Assets That Can Be Converted Into BNB (USER_DATA)</a>
 **/
public class ConvertibleBNBAssets {

    /**
     * {@code assetDividendDetailsList} is instance that memorizes list of {@link ConvertibleAssetDetails}
     **/
    private ArrayList<ConvertibleAssetDetails> assetsDetailsList;

    /**
     * {@code totalTransferBtc} is instance that memorizes total transfer of Bitcoin
     * **/
    private double totalTransferBtc;

    /**
     * {@code totalTransferBNB} is instance that memorizes total transfer of BNB
     * **/
    private double totalTransferBNB;

    /**
     * {@code dribbletPercentage} is instance that memorizes dribblet percentage value
     * **/
    private double dribbletPercentage;

    /**
     * Constructor to init {@link ConvertibleBNBAssets} object
     *
     * @param assetsDetails:      list of {@link ConvertibleAssetDetails}
     * @param totalTransferBtc:   total transfer of Bitcoin
     * @param totalTransferBNB:   total transfer of BNB
     * @param dribbletPercentage: dribblet percentage value
     **/
    public ConvertibleBNBAssets(ArrayList<ConvertibleAssetDetails> assetsDetails, double totalTransferBtc, double totalTransferBNB,
                                double dribbletPercentage) {
        this.assetsDetailsList = assetsDetails;
        this.totalTransferBtc = totalTransferBtc;
        this.totalTransferBNB = totalTransferBNB;
        this.dribbletPercentage = dribbletPercentage;
    }

    /**
     * Constructor to init {@link ConvertibleBNBAssets} object
     *
     * @param convertibleBNBAsset: convertible bnb asset details as {@link JSONObject}
     **/
    public ConvertibleBNBAssets(JSONObject convertibleBNBAsset) {
        assetsDetailsList = new ArrayList<>();
        JSONArray convertibleAssets = JsonHelper.getJSONArray(convertibleBNBAsset, "details", new JSONArray());
        for (int j = 0; j < convertibleAssets.length(); j++)
            assetsDetailsList.add(new ConvertibleAssetDetails(convertibleAssets.getJSONObject(j)));
        totalTransferBtc = convertibleBNBAsset.getDouble("totalTransferBtc");
        totalTransferBNB = convertibleBNBAsset.getDouble("totalTransferBNB");
        dribbletPercentage = convertibleBNBAsset.getDouble("dribbletPercentage");
    }

    /**
     * Method to get {@link #assetsDetailsList} instance <br>
     * No-any params required
     *
     * @return {@link #assetsDetailsList} instance as {@link ArrayList} of {@link ConvertibleAssetDetails}
     **/
    public ArrayList<ConvertibleAssetDetails> getAssetsDetailsList() {
        return assetsDetailsList;
    }

    /**
     * Method to set {@link #assetsDetailsList} instance <br>
     *
     * @param assetsDetailsList: list of {@link ConvertibleAssetDetails} to set
     **/
    public void setAssetsDetailsList(ArrayList<ConvertibleAssetDetails> assetsDetailsList) {
        this.assetsDetailsList = assetsDetailsList;
    }

    /**
     * Method to add a convertible asset details  to {@link #assetsDetailsList}
     *
     * @param assetDetails: convertible asset details to add
     **/
    public void insertAssetDetails(ConvertibleAssetDetails assetDetails) {
        if (!assetsDetailsList.contains(assetDetails))
            assetsDetailsList.add(assetDetails);
    }

    /**
     * Method to remove a convertible asset details  from {@link #assetsDetailsList}
     *
     * @param assetDetails: convertible asset details  to remove
     * @return result of operation as boolean
     **/
    public boolean removeAssetDetails(ConvertibleAssetDetails assetDetails) {
        return assetsDetailsList.remove(assetDetails);
    }

    /**
     * Method to get a convertible asset details from {@link #assetsDetailsList} list
     *
     * @param index: index to fetch the composed convertible asset details
     * @return convertible asset details as {@link ConvertibleAssetDetails}
     **/
    public ConvertibleAssetDetails getAssetDetails(int index) {
        return assetsDetailsList.get(index);
    }

    /**
     * Method to get {@link #totalTransferBtc} instance <br>
     * No-any params required
     *
     * @return {@link #totalTransferBtc} instance as double
     **/
    public double getTotalTransferBtc() {
        return totalTransferBtc;
    }

    /**
     * Method to get {@link #totalTransferBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalTransferBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalTransferBtc(int decimals) {
        return roundValue(totalTransferBtc, decimals);
    }

    /**
     * Method to set {@link #totalTransferBtc}
     *
     * @param totalTransferBtc: total transfer of Bitcoin
     * @throws IllegalArgumentException when total transfer of Bitcoin value is less than 0
     **/
    public void setTotalTransferBtc(double totalTransferBtc) {
        if (totalTransferBtc < 0)
            throw new IllegalArgumentException("Total transfer BTC value cannot be less than 0");
        this.totalTransferBtc = totalTransferBtc;
    }

    /**
     * Method to get {@link #totalTransferBNB} instance <br>
     * No-any params required
     *
     * @return {@link #totalTransferBNB} instance as double
     **/
    public double getTotalTransferBNB() {
        return totalTransferBNB;
    }

    /**
     * Method to get {@link #totalTransferBNB} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalTransferBNB} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalTransferBNB(int decimals) {
        return roundValue(totalTransferBNB, decimals);
    }

    /**
     * Method to set {@link #totalTransferBNB}
     *
     * @param totalTransferBNB: total transfer of BNB
     * @throws IllegalArgumentException when total transfer of BNB value is less than 0
     **/
    public void setTotalTransferBNB(double totalTransferBNB) {
        if (totalTransferBNB < 0)
            throw new IllegalArgumentException("Total transfer BNB value cannot be less than 0");
        this.totalTransferBNB = totalTransferBNB;
    }

    /**
     * Method to get {@link #dribbletPercentage} instance <br>
     * No-any params required
     *
     * @return {@link #dribbletPercentage} instance as double
     **/
    public double getDribbletPercentage() {
        return dribbletPercentage;
    }

    /**
     * Method to get {@link #dribbletPercentage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dribbletPercentage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getDribbletPercentage(int decimals) {
        return roundValue(dribbletPercentage, decimals);
    }

    /**
     * Method to set {@link #dribbletPercentage}
     *
     * @param dribbletPercentage: dribblet percentage value
     * @throws IllegalArgumentException when dribblet percentage value is less than 0
     **/
    public void setDribbletPercentage(double dribbletPercentage) {
        if (dribbletPercentage < 0)
            throw new IllegalArgumentException("Dribblet percentage value cannot be less than 0");
        this.dribbletPercentage = dribbletPercentage;
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

    /**
     * The {@code ConvertibleAssetDetails} class is useful to format a convertible asset with its details
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static final class ConvertibleAssetDetails {

        /**
         * {@code asset} is instance that memorizes asset value
         **/
        private final String asset;

        /**
         * {@code assetFullName} is instance that memorizes asset full name
         **/
        private final String assetFullName;

        /**
         * {@code amountFree} is instance that memorizes amount free value
         * **/
        private double amountFree;

        /**
         * {@code toBTC} is instance that memorizes to Bitcoin value
         * **/
        private double toBTC;

        /**
         * {@code toBNB} is instance that memorizes to BNB value
         * **/
        private double toBNB;

        /**
         * {@code toBNBOffExchange} is instance that memorizes to BNB of exchange value
         * **/
        private double toBNBOffExchange;

        /**
         * {@code exchange} is instance that memorizes exchange value
         * **/
        private double exchange;

        /**
         * Constructor to init {@link ConvertibleAssetDetails} object
         *
         * @param asset:            asset value
         * @param assetFullName:    asset full name
         * @param amountFree:       amount free value
         * @param toBTC:            to Bitcoin value
         * @param toBNB:            to BNB value
         * @param toBNBOffExchange: to BNB of exchange value
         * @param exchange:         exchange value
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public ConvertibleAssetDetails(String asset, String assetFullName, double amountFree, double toBTC,
                                       double toBNB, double toBNBOffExchange, double exchange) {
            this.asset = asset;
            this.assetFullName = assetFullName;
            if (amountFree < 0)
                throw new IllegalArgumentException("Amount free value cannot be less than 0");
            else
                this.amountFree = amountFree;
            if (toBTC < 0)
                throw new IllegalArgumentException("To BTC value cannot be less than 0");
            else
                this.toBTC = toBTC;
            if (toBNB < 0)
                throw new IllegalArgumentException("To BNB value cannot be less than 0");
            else
                this.toBNB = toBNB;
            if (toBNBOffExchange < 0)
                throw new IllegalArgumentException("To BNB off exchange value cannot be less than 0");
            else
                this.toBNBOffExchange = toBNBOffExchange;
            if (exchange < 0)
                throw new IllegalArgumentException("Exchange value cannot be less than 0");
            else
                this.exchange = exchange;
        }

        /**
         * Constructor to init {@link ConvertibleAssetDetails} object
         *
         * @param jAsset: asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public ConvertibleAssetDetails(JSONObject jAsset) {
            this(jAsset.getString("asset"), jAsset.getString("assetFullName"), jAsset.getDouble("amountFree"),
                    jAsset.getDouble("toBTC"), jAsset.getDouble("toBNB"), jAsset.getDouble("toBNBOffExchange"),
                    jAsset.getDouble("exchange"));
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
         * Method to get {@link #assetFullName} instance <br>
         * No-any params required
         *
         * @return {@link #assetFullName} instance as {@link String}
         **/
        public String getAssetFullName() {
            return assetFullName;
        }

        /**
         * Method to get {@link #amountFree} instance <br>
         * No-any params required
         *
         * @return {@link #amountFree} instance as double
         **/
        public double getAmountFree() {
            return amountFree;
        }

        /**
         * Method to get {@link #amountFree} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amountFree} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmountFree(int decimals) {
            return roundValue(amountFree, decimals);
        }

        /**
         * Method to set {@link #amountFree}
         *
         * @param amountFree: amount free value
         * @throws IllegalArgumentException when amount free value is less than 0
         **/
        public void setAmountFree(double amountFree) {
            if (amountFree < 0)
                throw new IllegalArgumentException("Amount free value cannot be less than 0");
            this.amountFree = amountFree;
        }

        /**
         * Method to get {@link #toBTC} instance <br>
         * No-any params required
         *
         * @return {@link #toBTC} instance as double
         **/
        public double getToBTC() {
            return toBTC;
        }

        /**
         * Method to get {@link #toBTC} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #toBTC} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getToBTC(int decimals) {
            return roundValue(toBTC, decimals);
        }

        /**
         * Method to set {@link #toBTC}
         *
         * @param toBTC: to Bitcoin value
         * @throws IllegalArgumentException when to Bitcoin value is less than 0
         **/
        public void setToBTC(double toBTC) {
            if (toBTC < 0)
                throw new IllegalArgumentException("To BTC value cannot be less than 0");
            this.toBTC = toBTC;
        }

        /**
         * Method to get {@link #toBNB} instance <br>
         * No-any params required
         *
         * @return {@link #toBNB} instance as double
         **/
        public double getToBNB() {
            return toBNB;
        }

        /**
         * Method to get {@link #toBNB} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #toBNB} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getToBNB(int decimals) {
            return roundValue(toBNB, decimals);
        }

        /**
         * Method to set {@link #toBNB}
         *
         * @param toBNB: to BNB value
         * @throws IllegalArgumentException when to BNB value is less than 0
         **/
        public void setToBNB(double toBNB) {
            if (toBNB < 0)
                throw new IllegalArgumentException("To BNB value cannot be less than 0");
            this.toBNB = toBNB;
        }

        /**
         * Method to get {@link #toBNBOffExchange} instance <br>
         * No-any params required
         *
         * @return {@link #toBNBOffExchange} instance as double
         **/
        public double getToBNBOffExchange() {
            return toBNBOffExchange;
        }

        /**
         * Method to get {@link #toBNBOffExchange} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #toBNBOffExchange} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getToBNBOffExchange(int decimals) {
            return roundValue(toBNBOffExchange, decimals);
        }

        /**
         * Method to set {@link #toBNBOffExchange}
         *
         * @param toBNBOffExchange: to BNB of exchange value
         * @throws IllegalArgumentException when to BNB of exchange value is less than 0
         **/
        public void setToBNBOffExchange(double toBNBOffExchange) {
            if (toBNBOffExchange < 0)
                throw new IllegalArgumentException("To BNB off exchange value cannot be less than 0");
            this.toBNBOffExchange = toBNBOffExchange;
        }

        /**
         * Method to get {@link #exchange} instance <br>
         * No-any params required
         *
         * @return {@link #exchange} instance as double
         **/
        public double getExchange() {
            return exchange;
        }

        /**
         * Method to get {@link #exchange} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #exchange} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getExchange(int decimals) {
            return roundValue(exchange, decimals);
        }

        /**
         * Method to set {@link #exchange}
         *
         * @param exchange: exchange value
         * @throws IllegalArgumentException when exchange value value is less than 0
         **/
        public void setExchange(double exchange) {
            if (exchange < 0)
                throw new IllegalArgumentException("Exchange value cannot be less than 0");
            this.exchange = exchange;
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
