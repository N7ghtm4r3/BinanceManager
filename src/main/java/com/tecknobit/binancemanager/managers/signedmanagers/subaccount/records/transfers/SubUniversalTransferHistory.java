package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code SubUniversalTransferHistory} class is useful to format a sub universal transfer history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-universal-transfer-history-for-master-account">
 * Query Universal Transfer History (For Master Account)</a>
 * @see BinanceItem
 */
public class SubUniversalTransferHistory extends BinanceItem {

    /**
     * {@code totalCount} total count of the sub universal transfers
     */
    private final int totalCount;

    /**
     * {@code universalTransfers} list of the universal transfers
     */
    private final ArrayList<SubUniversalTransfer> universalTransfers;

    /**
     * Constructor to init {@link SubUniversalTransferHistory} object
     *
     * @param totalCount         : total count of the sub universal transfers
     * @param universalTransfers : list of the universal transfers
     */
    public SubUniversalTransferHistory(int totalCount, ArrayList<SubUniversalTransfer> universalTransfers) {
        super(null);
        this.totalCount = totalCount;
        this.universalTransfers = universalTransfers;
    }

    /**
     * Constructor to init {@link SubUniversalTransferHistory} object
     *
     * @param jSubUniversalTransferHistory: sub universal transfer history details as {@link JSONObject}
     */
    public SubUniversalTransferHistory(JSONObject jSubUniversalTransferHistory) {
        super(jSubUniversalTransferHistory);
        totalCount = hItem.getInt("totalCount", 0);
        universalTransfers = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("result");
        if (jList != null)
            for (JSONObject item : jList)
                universalTransfers.add(new SubUniversalTransfer(item));
    }

    /**
     * Method to get {@link #totalCount} instance <br>
     * No-any params required
     *
     * @return {@link #totalCount} instance as int
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Method to get {@link #universalTransfers} instance <br>
     * No-any params required
     *
     * @return {@link #universalTransfers} instance as {@link ArrayList} of {@link SubUniversalTransfer}
     */
    public ArrayList<SubUniversalTransfer> getUniversalTransfers() {
        return universalTransfers;
    }

}
