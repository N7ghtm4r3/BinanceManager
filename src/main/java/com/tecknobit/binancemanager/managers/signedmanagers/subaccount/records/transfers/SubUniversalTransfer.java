package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubUniversalTransfer} class is useful to format a sub universal transfer
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#universal-transfer-for-master-account">
 * Universal Transfer (For Master Account)</a>
 * @see BinanceItem
 * @see SubTransferStructure
 **/
public class SubUniversalTransfer extends SubTransferStructure {

    /**
     * {@code fromEmail} from email of the sub universal transfer
     **/
    private final String fromEmail;

    /**
     * {@code toEmail} to email of the sub universal transfer
     **/
    private final String toEmail;

    /**
     * {@code amount} of the sub universal transfer
     **/
    private final double amount;

    /**
     * {@code createTimestamp} create timestamp of the sub universal transfer
     **/
    private final long createTimestamp;

    /**
     * {@code clientTranId} client transaction identifier of the sub universal transfer
     **/
    private final String clientTranId;

    /**
     * Constructor to init {@link SubUniversalTransfer} object
     *
     * @param tranId          : transaction id of the sub universal transfer
     * @param asset           : asset of the sub universal transfer
     * @param fromAccountType : from account type of the sub universal transfer
     * @param toAccountType   : to account type of the sub universal transfer
     * @param status          : status of the sub universal transfer
     * @param fromEmail       : from email of the sub universal transfer
     * @param toEmail         : to email of the sub universal transfer
     * @param amount          : amount of the sub universal transfer
     * @param createTimestamp : create timestamp of the sub universal transfer
     * @param clientTranId    : client transaction identifier of the sub universal transfer
     **/
    public SubUniversalTransfer(long tranId, String asset, PrincipalAccountType fromAccountType,
                                PrincipalAccountType toAccountType, TransferStatus status, String fromEmail,
                                String toEmail, double amount, long createTimestamp, String clientTranId) {
        super(tranId, asset, fromAccountType, toAccountType, status);
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.amount = amount;
        this.createTimestamp = createTimestamp;
        this.clientTranId = clientTranId;
    }

    /**
     * Constructor to init {@link SubUniversalTransfer} object
     *
     * @param jSubUniversalTransfer: sub universal transfer details as {@link JSONObject}
     **/
    public SubUniversalTransfer(JSONObject jSubUniversalTransfer) {
        super(jSubUniversalTransfer);
        fromEmail = hItem.getString("fromEmail");
        toEmail = hItem.getString("toEmail");
        amount = hItem.getDouble("amount", 0);
        createTimestamp = hItem.getLong("createTimeStamp", 0);
        clientTranId = hItem.getString("clientTranId");
    }

    /**
     * Method to get {@link #fromEmail} instance <br>
     * No-any params required
     *
     * @return {@link #fromEmail} instance as {@link String}
     **/
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * Method to get {@link #toEmail} instance <br>
     * No-any params required
     *
     * @return {@link #toEmail} instance as {@link String}
     **/
    public String getToEmail() {
        return toEmail;
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     **/
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    /**
     * Method to get {@link #createTimestamp} instance <br>
     * No-any params required
     *
     * @return {@link #createTimestamp} instance as long
     **/
    public long getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Method to get {@link #createTimestamp} instance <br>
     * No-any params required
     *
     * @return {@link #createTimestamp} instance as {@link Date}
     **/
    public Date getCreateDate() {
        return TimeFormatter.getDate(createTimestamp);
    }

    /**
     * Method to get {@link #clientTranId} instance <br>
     * No-any params required
     *
     * @return {@link #clientTranId} instance as {@link String}
     **/
    public String getClientTranId() {
        return clientTranId;
    }

}
