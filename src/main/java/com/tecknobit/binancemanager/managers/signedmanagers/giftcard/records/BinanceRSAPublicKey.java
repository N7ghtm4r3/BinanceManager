package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.BinanceResponseStructure;
import org.json.JSONObject;

/**
 * The {@code BinanceRSAPublicKey} class is useful to create a Binance RSA public key
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-rsa-public-key-user_data">
 * Fetch RSA Public Key (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceManager.BinanceResponse
 * @see BinanceResponseStructure
 */
public class BinanceRSAPublicKey extends BinanceResponseStructure<String> {

    /**
     * Constructor to init {@link BinanceRSAPublicKey} object
     *
     * @param success: whether the operation has been successful
     * @param data:    data of the public key
     */
    public BinanceRSAPublicKey(boolean success, String data) {
        super(success, data);
    }

    /**
     * Constructor to init {@link BinanceRSAPublicKey} object
     *
     * @param jBinanceRSAPublicKey: Binance RSA public key details as {@link JSONObject}
     */
    public BinanceRSAPublicKey(JSONObject jBinanceRSAPublicKey) {
        super(jBinanceRSAPublicKey);
        data = hItem.getString("data");
    }

}
