package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class SubAccountTransfer extends SubTransferStructure {

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
    private final double qty;
    private final long time;

    public SubAccountTransfer(long tranId, String asset, PrincipalAccountType fromAccountType,
                              PrincipalAccountType toAccountType, TransferStatus status, String counterParty,
                              String email, SubTransferType type, double qty, long time) {
        super(tranId, asset, fromAccountType, toAccountType, status);
        this.counterParty = counterParty;
        this.email = email;
        this.type = type;
        this.qty = qty;
        this.time = time;
    }

    public SubAccountTransfer(JSONObject jSubAccountTransfer) {
        super(jSubAccountTransfer);
        counterParty = hItem.getString("counterParty");
        email = hItem.getString("email");
        type = SubTransferType.reachEnumConstant(hItem.getInt("type"));
        qty = hItem.getDouble("qty", 0);
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

    public double getQty() {
        return qty;
    }

    public long getTime() {
        return time;
    }

}
