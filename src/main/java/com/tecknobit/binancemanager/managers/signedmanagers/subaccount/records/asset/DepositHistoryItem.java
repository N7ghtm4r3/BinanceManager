package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit;
import org.json.JSONObject;

public class DepositHistoryItem extends Deposit {

    private final long id;
    private final int walletType;

    /**
     * Constructor to init {@link Deposit} object
     *
     * @param amount        : amount value
     * @param coin          : coin value
     * @param network       : network value
     * @param status        : status value
     * @param address       : address value
     * @param addressTag    : address tag value
     * @param txId          : tx identifier value
     * @param insertTime    : insert time value
     * @param transferType  : transfer type value
     * @param unlockConfirm : unlock confirm value
     * @param confirmTimes  : confirm times value
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public DepositHistoryItem(double amount, String coin, String network, DepositStatus status, String address,
                              String addressTag, String txId, long insertTime, int transferType, int unlockConfirm,
                              String confirmTimes, long id, int walletType) {
        super(amount, coin, network, status, address, addressTag, txId, insertTime, transferType, unlockConfirm,
                confirmTimes);
        this.id = id;
        this.walletType = walletType;
    }

    /**
     * Constructor to init {@link Deposit} object
     *
     * @param jDepositHistoryItem : deposit details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public DepositHistoryItem(JSONObject jDepositHistoryItem) {
        super(jDepositHistoryItem);
        id = hItem.getLong("id", 0);
        walletType = hItem.getInt("walletType", 0);
    }

    public long getId() {
        return id;
    }

    public int getWalletType() {
        return walletType;
    }

}
