package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class SubAccountTransfer extends BinanceItem {

    public enum SubTransferType {

        transfer_in(1),
        transfer_out(2);

        private final int type;

        /**
         * {@code VALUES} list of the statuses
         **/
        private static final List<SubTransferType> VALUES = Arrays.stream(SubTransferType.values()).toList();

        SubTransferType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constant as {@link SubTransferType}
         **/
        public static SubTransferType reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        @Override
        public String toString() {
            return type + "";
        }

    }

    private final String counterParty;
    private final String email;
    private final SubTransferType type;
    private final String asset;
    private final double qty;
    private final PrincipalAccountType fromAccountType;
    private final PrincipalAccountType toAccountType;
    private final TransferStatus status;
    private final long tranId;
    private final long time;

    public SubAccountTransfer(String counterParty, String email, SubTransferType type, String asset, double qty,
                              PrincipalAccountType fromAccountType, PrincipalAccountType toAccountType,
                              TransferStatus status, long tranId, long time) {
        super(null);
        this.counterParty = counterParty;
        this.email = email;
        this.type = type;
        this.asset = asset;
        this.qty = qty;
        this.fromAccountType = fromAccountType;
        this.toAccountType = toAccountType;
        this.status = status;
        this.tranId = tranId;
        this.time = time;
    }

    public SubAccountTransfer(JSONObject jSubAccountTransfer) {
        super(jSubAccountTransfer);
        counterParty = hItem.getString("counterParty");
        email = hItem.getString("email");
        type = SubTransferType.reachEnumConstant(hItem.getInt("type"));
        asset = hItem.getString("asset");
        qty = hItem.getDouble("qty", 0);
        fromAccountType = PrincipalAccountType.valueOf(hItem.getString("fromAccountType"));
        toAccountType = PrincipalAccountType.valueOf(hItem.getString("toAccountType"));
        status = TransferStatus.valueOf(hItem.getString("status"));
        tranId = hItem.getLong("tranId", 0);
        time = hItem.getLong("time", 0);
    }

    public String getCounterParty() {
        return counterParty;
    }

    public String getEmail() {
        return email;
    }

    public SubTransferType getType() {
        return type;
    }

    public String getAsset() {
        return asset;
    }

    public double getQty() {
        return qty;
    }

    public PrincipalAccountType getFromAccountType() {
        return fromAccountType;
    }

    public PrincipalAccountType getToAccountType() {
        return toAccountType;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public long getTranId() {
        return tranId;
    }

    public long getTime() {
        return time;
    }

}
