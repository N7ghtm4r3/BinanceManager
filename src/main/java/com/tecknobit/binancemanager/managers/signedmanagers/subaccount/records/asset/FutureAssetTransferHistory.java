package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code FutureAssetTransferHistory} class is useful to format a future asset transfer history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-futures-asset-transfer-history-for-master-account">
 * Query Sub-account Futures Asset Transfer History (For Master Account)</a>
 * @see BinanceItem
 * @see AssetTransfer
 **/
public class FutureAssetTransferHistory extends BinanceItem {

    /**
     * {@code FuturesType} list of available futures types
     **/
    public enum FuturesType {

        /**
         * {@code USDT_MARGINED} futures type
         **/
        USDT_MARGINED(1),

        /**
         * {@code USDT_MARGINED} futures type
         **/
        COIN_MARGINED(2);

        /**
         * {@code type} value
         **/
        private final int type;

        /**
         * {@code VALUES} list of the types
         **/
        private static final List<FuturesType> VALUES = Arrays.stream(FuturesType.values()).toList();

        /**
         * Constructor to init {@link FuturesType} object
         *
         * @param type: futures types value
         **/
        FuturesType(int type) {
            this.type = type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         **/
        public int getType() {
            return type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param type: type to reach
         * @return enum constant as {@link FuturesType}
         **/
        public static FuturesType reachEnumConstant(int type) {
            return VALUES.get(type - 1);
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         **/
        @Override
        public String toString() {
            return type + "";
        }

    }

    /**
     * {@code success} whether the request has been successful
     **/
    private final boolean success;

    /**
     * {@code futuresType} futures type of the transfers history
     **/
    private final FuturesType futuresType;

    /**
     * {@code transfers} list of transfers
     **/
    private final ArrayList<AssetTransfer> transfers;

    /**
     * Constructor to init {@link FutureAssetTransferHistory} object
     *
     * @param success:     whether the request has been successful
     * @param futuresType: futures type of the transfers history
     * @param transfers:   list of transfers
     **/
    public FutureAssetTransferHistory(boolean success, FuturesType futuresType, ArrayList<AssetTransfer> transfers) {
        super(null);
        this.success = success;
        this.futuresType = futuresType;
        this.transfers = transfers;
    }

    /**
     * Constructor to init {@link FutureAssetTransferHistory} object
     *
     * @param jFutureAssetTransferHistory: future asset transfer history details as {@link JSONObject}
     **/
    public FutureAssetTransferHistory(JSONObject jFutureAssetTransferHistory) {
        super(jFutureAssetTransferHistory);
        success = hItem.getBoolean("success");
        futuresType = FuturesType.reachEnumConstant(hItem.getInt("futuresType", 0));
        transfers = new ArrayList<>();
        ArrayList<JSONObject> jTransfers = hItem.fetchList("transfers");
        if (jTransfers != null)
            for (JSONObject transfer : jTransfers)
                transfers.add(new AssetTransfer(transfer));
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
     * Method to get {@link #futuresType} instance <br>
     * No-any params required
     *
     * @return {@link #futuresType} instance as {@link FuturesType}
     **/
    public FuturesType getFuturesType() {
        return futuresType;
    }

    /**
     * Method to get {@link #transfers} instance <br>
     * No-any params required
     *
     * @return {@link #transfers} instance as {@link ArrayList} of {@link AssetTransfer}
     **/
    public ArrayList<AssetTransfer> getTransfers() {
        return transfers;
    }

}
