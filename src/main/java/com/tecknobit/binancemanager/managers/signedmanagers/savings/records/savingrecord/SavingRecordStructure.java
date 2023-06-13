package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SavingRecordStructure} class is useful to format a saving record structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-purchase-record-user_data">
 *             Get Purchase Record (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
 *             Get Redemption Record (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see SavingStructure
 */
@Structure
public abstract class SavingRecordStructure extends SavingStructure {

    /**
     * {@code amount} of the saving record structure
     */
    protected final double amount;

    /**
     * {@code createTime} create time of the saving record structure
     */
    protected final long createTime;

    /**
     * {@code status} of the saving record structure
     */
    protected final String status;

    /**
     * Constructor to init {@link SavingRecordStructure} object
     *
     * @param asset:      asset of the saving record structure
     * @param amount:     amount of the saving record structure
     * @param createTime: create time of the saving record structure
     * @param status:     status of the saving record structure
     */
    public SavingRecordStructure(String asset, double amount, long createTime, String status) {
        super(asset);
        this.amount = amount;
        this.createTime = createTime;
        this.status = status;
    }

    /**
     * Constructor to init {@link SavingRecordStructure} object
     *
     * @param jSavingRecordStructure: saving record details as {@link JSONObject}
     */
    public SavingRecordStructure(JSONObject jSavingRecordStructure) {
        super(jSavingRecordStructure);
        amount = hItem.getDouble("amount", 0);
        createTime = hItem.getLong("createTime", 0);
        status = hItem.getString("status");
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as long
     */
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as {@link Date}
     */
    public Date getCreationDate() {
        return TimeFormatter.getDate(createTime);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link String}
     */
    public String getStatus() {
        return status;
    }

}
