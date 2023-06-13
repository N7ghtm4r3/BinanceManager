package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code SpotAssetTransfer} class is useful to format a spot asset transfer
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-asset-transfer-history-for-master-account">
 * Query Sub-account Spot Asset Transfer History (For Master Account)</a>
 * @see BinanceItem
 * @see AssetTransfer
 */
public class SpotAssetTransfer extends AssetTransfer {

    /**
     * {@code TransferStatus} list of available transfer statuses
     */
    public enum TransferStatus {

        /**
         * {@code PROCESS} transfer status
         */
        PROCESS,

        /**
         * {@code SUCCESS} transfer status
         */
        SUCCESS,

        /**
         * {@code FAILURE} transfer status
         */
        FAILURE

    }

    /**
     * {@code status} of the spot asset transfer
     */
    private final TransferStatus status;

    /**
     * Constructor to init {@link SpotAssetTransfer} object
     *
     * @param from   : from of the spot asset transfer
     * @param to     : to of the spot asset transfer
     * @param asset  : asset of the transfer
     * @param qty    : quantity of the spot asset transfer
     * @param tranId : transaction id of the spot asset transfer
     * @param time   : time of the spot asset transfer
     * @param status : status of the spot asset transfer
     */
    public SpotAssetTransfer(String from, String to, String asset, double qty, long tranId, long time,
                             TransferStatus status) {
        super(from, to, asset, qty, tranId, time);
        this.status = status;
    }

    /**
     * Constructor to init {@link SpotAssetTransfer} object
     *
     * @param jSpotAssetTransfer: spot asset transfer details as {@link JSONObject}
     */
    public SpotAssetTransfer(JSONObject jSpotAssetTransfer) {
        super(jSpotAssetTransfer);
        status = TransferStatus.valueOf(hItem.getString("status"));
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link TransferStatus}
     */
    public TransferStatus getStatus() {
        return status;
    }

}
