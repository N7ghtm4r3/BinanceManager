package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FutureAssetTransferHistory extends BinanceItem {

    public enum FuturesType {

        USDT_MARGINED(1),
        COIN_MARGINED(2);

        private final int type;

        /**
         * {@code VALUES} list of the types
         **/
        private static final List<FuturesType> VALUES = Arrays.stream(FuturesType.values()).toList();

        FuturesType(int type) {
            this.type = type;
        }

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
            return VALUES.get(type);
        }

        @Override
        public String toString() {
            return type + "";
        }

    }

    private final boolean success;
    private final FuturesType futuresType;
    private final ArrayList<AssetTransfer> transfers;

    public FutureAssetTransferHistory(boolean success, FuturesType futuresType, ArrayList<AssetTransfer> transfers) {
        super(null);
        this.success = success;
        this.futuresType = futuresType;
        this.transfers = transfers;
    }

    public FutureAssetTransferHistory(JSONObject jFutureAssetTransferHistory) {
        super(jFutureAssetTransferHistory);
        success = hItem.getBoolean("");
        futuresType = FuturesType.reachEnumConstant(hItem.getInt("", 0));
        transfers = new ArrayList<>();
        ArrayList<JSONObject> jTransfers = hItem.fetchList("");
        if (jTransfers != null)
            for (JSONObject transfer : jTransfers)
                transfers.add(new AssetTransfer(transfer));
    }

    public boolean isSuccess() {
        return success;
    }

    public FuturesType getFuturesType() {
        return futuresType;
    }

    public ArrayList<AssetTransfer> getTransfers() {
        return transfers;
    }

}
