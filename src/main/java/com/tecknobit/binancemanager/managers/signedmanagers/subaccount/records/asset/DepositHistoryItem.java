package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.deposit.Deposit;
import org.json.JSONObject;

/**
 * The {@code DepositHistoryItem} class is useful to format a deposit history item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-sub-account-deposit-history-for-master-account">
 * Get Sub-account Deposit History (For Master Account)</a>
 * @see BinanceItem
 **/
public class DepositHistoryItem extends Deposit {

    /**
     * {@code id} of the deposit
     **/
    private final long id;

    /**
     * {@code walletType} wallet type of the deposit
     **/
    private final int walletType;

    /**
     * Constructor to init {@link DepositHistoryItem} object
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
     * @param id : id of the deposit
     * @param walletType  : wallet type of the deposit
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
     * Constructor to init {@link DepositHistoryItem} object
     *
     * @param jDepositHistoryItem : deposit history item details as {@link JSONObject}
     **/
    public DepositHistoryItem(JSONObject jDepositHistoryItem) {
        super(jDepositHistoryItem);
        id = hItem.getLong("id", 0);
        walletType = hItem.getInt("walletType", 0);
    }

    /**
     * Method to get {@link #id} instance <br>
     * No-any params required
     *
     * @return {@link #id} instance as long
     **/
    public long getId() {
        return id;
    }

    /**
     * Method to get {@link #walletType} instance <br>
     * No-any params required
     *
     * @return {@link #walletType} instance as int
     **/
    public int getWalletType() {
        return walletType;
    }

}
