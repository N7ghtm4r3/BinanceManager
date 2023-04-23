package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code FutureAssetTransferResult} class is useful to format a future asset transfer result
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-futures-asset-transfer-for-master-account">
 * Sub-account Futures Asset Transfer (For Master Account)</a>
 * @see BinanceItem
 * @see AssetTransfer
 **/
public class FutureAssetTransferResult extends BinanceItem {

    /**
     * {@code success} whether the transfer has been successful
     **/
    private final boolean success;

    /**
     * {@code txnId} transaction identifier of the transfer
     **/
    private final long txnId;

    /**
     * Constructor to init {@link FutureAssetTransferResult} object
     *
     * @param success : whether the transfer has been successful
     * @param txnId   : transaction identifier of the transfer
     **/
    public FutureAssetTransferResult(boolean success, long txnId) {
        super(null);
        this.success = success;
        this.txnId = txnId;
    }

    /**
     * Constructor to init {@link FutureAssetTransferResult} object
     *
     * @param jFutureAssetTransferResult: future asset transfer result details as {@link JSONObject}
     **/
    public FutureAssetTransferResult(JSONObject jFutureAssetTransferResult) {
        super(jFutureAssetTransferResult);
        success = hItem.getBoolean("success");
        txnId = hItem.getLong("txnId", 0);
    }

    /**
     * Method to get {@link #success} instance <br>
     * No-any params required
     *
     * @return {@link #success} instance as boolean
     **/
    public boolean isSuccess() {
        return success;
    }

    /**
     * Method to get {@link #txnId} instance <br>
     * No-any params required
     *
     * @return {@link #txnId} instance as long
     **/
    public long getTxnId() {
        return txnId;
    }

}
