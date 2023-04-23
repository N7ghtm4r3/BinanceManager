package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;

/**
 * The {@code SubTransferStructure} class is useful to format a sub transfer structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-transfer-history-for-sub-account">
 *             Sub-account Transfer History (For Sub-account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-universal-transfer-history-for-master-account">
 *             Query Universal Transfer History (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-investor-master-account-user_data">
 *             Query Managed Sub Account Transfer Log (For Investor Master Account) (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-managed-sub-account-transfer-log-for-trading-team-master-account-user_data">
 *             Query Managed Sub Account Transfer Log (For Trading Team Master Account) (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public abstract class SubTransferStructure extends BinanceItem {

    /**
     * {@code tranId} transaction id of the sub transfer
     **/
    protected final long tranId;

    /**
     * {@code asset} of the sub transfer
     **/
    protected final String asset;

    /**
     * {@code fromAccountType} from account type of the sub transfer
     **/
    protected final PrincipalAccountType fromAccountType;

    /**
     * {@code toAccountType} to account type of the sub transfer
     **/
    protected final PrincipalAccountType toAccountType;

    /**
     * {@code status} of the sub transfer
     **/
    protected final TransferStatus status;

    /**
     * Constructor to init {@link SubTransferStructure} object
     *
     * @param tranId          : transaction id of the sub transfer
     * @param asset           : asset of the sub transfer
     * @param fromAccountType : from account type of the sub transfer
     * @param toAccountType   : to account type of the sub transfer
     * @param status          : status of the sub transfer
     **/
    public SubTransferStructure(long tranId, String asset, PrincipalAccountType fromAccountType,
                                PrincipalAccountType toAccountType, TransferStatus status) {
        super(null);
        this.tranId = tranId;
        this.asset = asset;
        this.fromAccountType = fromAccountType;
        this.toAccountType = toAccountType;
        this.status = status;
    }

    /**
     * Constructor to init {@link SubTransferStructure} object
     *
     * @param jSubAccountTransfer: subaccount transfer details as {@link JSONObject}
     **/
    public SubTransferStructure(JSONObject jSubAccountTransfer) {
        super(jSubAccountTransfer);
        asset = hItem.getString("asset");
        String sEnum = hItem.getString("fromAccountType");
        if (sEnum != null)
            fromAccountType = PrincipalAccountType.valueOf(sEnum);
        else
            fromAccountType = null;
        sEnum = hItem.getString("toAccountType");
        if (sEnum != null)
            toAccountType = PrincipalAccountType.valueOf(sEnum);
        else
            toAccountType = null;
        sEnum = hItem.getString("status");
        if (sEnum != null)
            status = TransferStatus.valueOf(sEnum);
        else
            status = null;
        tranId = hItem.getLong("tranId", 0);
    }

    /**
     * Method to get {@link #tranId} instance <br>
     * No-any params required
     *
     * @return {@link #tranId} instance as long
     **/
    public long getTranId() {
        return tranId;
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

    /**
     * Method to get {@link #fromAccountType} instance <br>
     * No-any params required
     *
     * @return {@link #fromAccountType} instance as {@link PrincipalAccountType}
     **/
    public PrincipalAccountType getFromAccountType() {
        return fromAccountType;
    }

    /**
     * Method to get {@link #toAccountType} instance <br>
     * No-any params required
     *
     * @return {@link #toAccountType} instance as {@link PrincipalAccountType}
     **/
    public PrincipalAccountType getToAccountType() {
        return toAccountType;
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link TransferStatus}
     **/
    public TransferStatus getStatus() {
        return status;
    }

}
