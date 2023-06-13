package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubAccountTransfer} class is useful to format a subaccount transfer
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#sub-account-transfer-history-for-sub-account">
 * Sub-account Transfer History (For Sub-account)</a>
 * @see BinanceItem
 * @see SubTransferStructure
 */
public class SubAccountTransfer extends SubTransferStructure {

    /**
     * {@code SubMarginTransfer} list of available sub margin transfers
     */
    public enum SubMarginTransfer {

        /**
         * {@code TRANSFER_FROM_SUB_SPOT_ACCOUNT_TO_MARGIN_ACCOUNT} sub margin transfer
         */
        TRANSFER_FROM_SUB_SPOT_ACCOUNT_TO_MARGIN_ACCOUNT(1),

        /**
         * {@code TRANSFER_FROM_SUB_MARGIN_ACCOUNT_TO_ITS_SPOT_ACCOUNT} sub margin transfer
         */
        TRANSFER_FROM_SUB_MARGIN_ACCOUNT_TO_ITS_SPOT_ACCOUNT(2);

        /**
         * {@code transfer} sub margin transfer value
         */
        private final int transfer;

        /**
         * Constructor to init {@link SubMarginTransfer} object
         *
         * @param transfer : sub margin transfer value
         */
        SubMarginTransfer(int transfer) {
            this.transfer = transfer;
        }

        /**
         * Method to get {@link #transfer} instance <br>
         * No-any params required
         *
         * @return {@link #transfer} instance as int
         */
        public int getTransfer() {
            return transfer;
        }

        /**
         * Method to get {@link #transfer} instance <br>
         * No-any params required
         *
         * @return {@link #transfer} instance as {@link String}
         */
        @Override
        public String toString() {
            return transfer + "";
        }

    }

    /**
     * {@code SubTransferType} list of available sub transfer types
     */
    public enum SubTransferType {

        /**
         * {@code transfer_in} sub transfer type
         */
        transfer_in(1),

        /**
         * {@code transfer_out} sub transfer type
         */
        transfer_out(2);

        /**
         * {@code type} sub transfer type
         */
        private final int type;

        /**
         * {@code VALUES} list of the statuses
         */
        private static final List<SubTransferType> VALUES = Arrays.stream(SubTransferType.values()).toList();

        /**
         * Constructor to init {@link SubTransferType} object
         *
         * @param type : sub transfer type
         */

        SubTransferType(int type) {
            this.type = type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         */
        public int getType() {
            return type;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constant as {@link SubTransferType}
         */
        public static SubTransferType reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         */
        @Override
        public String toString() {
            return type + "";
        }

    }

    /**
     * {@code counterParty} counterparty of the subaccount transfer
     */
    private final String counterParty;

    /**
     * {@code email} of the subaccount transfer
     */
    private final String email;

    /**
     * {@code type} of the subaccount transfer
     */
    private final SubTransferType type;

    /**
     * {@code qty} of the subaccount transfer
     */
    private final double qty;

    /**
     * {@code time} of the subaccount transfer
     */
    private final long time;

    /**
     * Constructor to init {@link SubAccountTransfer} object
     *
     * @param tranId          : transaction id of the subaccount transfer
     * @param asset           : asset of the subaccount transfer
     * @param fromAccountType : from account type of the subaccount transfer
     * @param toAccountType   : to account type of the subaccount transfer
     * @param status          : status of the subaccount transfer
     * @param counterParty    : counterparty of the subaccount transfer
     * @param email           : email of the subaccount transfer
     * @param type            : type of the subaccount transfer
     * @param qty             : quantity of the subaccount transfer
     * @param time            : time of the subaccount transfer
     */
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

    /**
     * Constructor to init {@link SubAccountTransfer} object
     *
     * @param jSubAccountTransfer: subaccount transfer details as {@link JSONObject}
     */
    public SubAccountTransfer(JSONObject jSubAccountTransfer) {
        super(jSubAccountTransfer);
        counterParty = hItem.getString("counterParty");
        email = hItem.getString("email");
        type = SubTransferType.reachEnumConstant(hItem.getInt("type"));
        qty = hItem.getDouble("qty", 0);
        time = hItem.getLong("time", 0);
    }

    /**
     * Method to get {@link #counterParty} instance <br>
     * No-any params required
     *
     * @return {@link #counterParty} instance as {@link String}
     */
    public String getCounterParty() {
        return counterParty;
    }

    /**
     * Method to get {@link #email} instance <br>
     * No-any params required
     *
     * @return {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link SubTransferType}
     */
    public SubTransferType getType() {
        return type;
    }

    /**
     * Method to get {@link #qty} instance <br>
     * No-any params required
     *
     * @return {@link #qty} instance as double
     */
    public double getQty() {
        return qty;
    }

    /**
     * Method to get {@link #qty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #qty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQty(int decimals) {
        return roundValue(qty, decimals);
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as long
     */
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(time);
    }

}
