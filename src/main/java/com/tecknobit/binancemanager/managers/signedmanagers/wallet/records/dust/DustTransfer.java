package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustItem.getListDribbletsDetails;

/**
 * The {@code DustTransfer} class is useful to format a {@code "Binance"}'s dust transfer
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
 *             User Universal Transfer (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-assets-that-can-be-converted-into-bnb-user_data-2">
 *             Dust Transfer (TRADE)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
public class DustTransfer extends BinanceItem {

    /**
     * {@code totalServiceCharge} is instance that memorizes total service charge value
     */
    private final double totalServiceCharge;

    /**
     * {@code totalTransfered} is instance that memorizes total transferred value
     */
    private final double totalTransfered;

    /**
     * {@code transferResultsList} is instance that memorizes list of {@link DustItem}
     */
    private ArrayList<DustItem> transferResultsList;

    /**
     * Constructor to init {@link DustLogList} object
     *
     * @param totalServiceCharge: total service charge value
     * @param totalTransfered:    total transferred value
     * @param transferResults:    list of {@link DustItem}
     */
    public DustTransfer(double totalServiceCharge, double totalTransfered, ArrayList<DustItem> transferResults) {
        super(null);
        this.totalServiceCharge = totalServiceCharge;
        this.totalTransfered = totalTransfered;
        this.transferResultsList = transferResults;
    }

    /**
     * Constructor to init {@link DustLogList} object
     *
     * @param dustTransfer: dust transfer details as {@link JSONObject}
     */
    public DustTransfer(JSONObject dustTransfer) {
        super(dustTransfer);
        totalServiceCharge = hItem.getDouble("totalServiceCharge", 0);
        totalTransfered = hItem.getDouble("totalTransfered", 0);
        transferResultsList = getListDribbletsDetails(hItem.getJSONArray("transferResult"));
    }

    /**
     * Method to get {@link #totalServiceCharge} instance <br>
     * No-any params required
     *
     * @return {@link #totalServiceCharge} instance as double
     */
    public double getTotalServiceCharge() {
        return totalServiceCharge;
    }

    /**
     * Method to get {@link #totalServiceCharge} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalServiceCharge} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalServiceCharge(int decimals) {
        return roundValue(totalServiceCharge, decimals);
    }

    /**
     * Method to get {@link #totalTransfered} instance <br>
     * No-any params required
     *
     * @return {@link #totalTransfered} instance as double
     */
    public double getTotalTransfered() {
        return totalTransfered;
    }

    /**
     * Method to get {@link #totalTransfered} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalTransfered} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalTransfered(int decimals) {
        return roundValue(totalTransfered, decimals);
    }

    /**
     * Method to get {@link #transferResultsList} instance <br>
     * No-any params required
     *
     * @return {@link #transferResultsList} instance as {@link ArrayList} of {@link DustItem}
     */
    public ArrayList<DustItem> getTransferResults() {
        return transferResultsList;
    }

    /**
     * Method to set {@link #transferResultsList} instance <br>
     *
     * @param transferResultsList: list of {@link DustItem} to set
     */
    public void setTransferResultsList(ArrayList<DustItem> transferResultsList) {
        this.transferResultsList = transferResultsList;
    }

    /**
     * Method to add a transfer result   to {@link #transferResultsList}
     *
     * @param transferResult: transfer result  to add
     */
    public void insertTransferResult(DustItem transferResult) {
        if (!transferResultsList.contains(transferResult))
            transferResultsList.add(transferResult);
    }

    /**
     * Method to remove a transfer result   from {@link #transferResultsList}
     *
     * @param transferResult: transfer result   to remove
     * @return result of operation as boolean
     */
    public boolean removeTransferResult(DustItem transferResult) {
        return transferResultsList.remove(transferResult);
    }

    /**
     * Method to get a transfer result  from {@link #transferResultsList} list
     *
     * @param index: index to fetch the composed transfer result
     * @return transfer result  as {@link DustItem}
     */
    public DustItem getAssetDribbletDetails(int index) {
        return transferResultsList.get(index);
    }

    /**
     * Method to create a dust transfer
     *
     * @param assetsResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return dust transfer as {@code "format"} defines
     */
    @Returner
    public static <T> T returnDustTransfer(String assetsResponse, ReturnFormat format) {
        JSONObject response = new JSONObject(assetsResponse);
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new DustTransfer(response);
            default -> (T) assetsResponse;
        };
    }

}
