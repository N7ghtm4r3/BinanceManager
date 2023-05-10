package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code SavingStructure} class is useful to format a saving structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 **/
@Structure
public abstract class SavingStructure extends BinanceItem {

    /**
     * {@code SavingStatus} list of available saving statuses
     **/
    public enum SavingStatus {

        /**
         * {@code PREHEATING} saving status
         **/
        PREHEATING,

        /**
         * {@code PURCHASING} saving status
         **/
        PURCHASING,

        /**
         * {@code RUNNING} saving status
         **/
        RUNNING,

        /**
         * {@code HOLDING} saving status
         **/
        HOLDING,

        /**
         * {@code REDEEMED} saving status
         **/
        REDEEMED,

        /**
         * {@code END} saving status
         **/
        END

    }

    /**
     * {@code asset} of the saving
     **/
    protected final String asset;

    /**
     * Constructor to init {@link SavingStructure} object
     *
     * @param asset: asset of the saving
     **/
    public SavingStructure(String asset) {
        super(null);
        this.asset = asset;
    }

    /**
     * Constructor to init {@link SavingStructure} object
     *
     * @param jSavingStructure: saving structure details as {@link JSONObject}
     **/
    public SavingStructure(JSONObject jSavingStructure) {
        super(jSavingStructure);
        asset = hItem.getString("asset");
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

}
