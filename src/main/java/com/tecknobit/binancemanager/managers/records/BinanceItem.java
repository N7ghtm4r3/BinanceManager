package com.tecknobit.binancemanager.managers.records;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code BinanceRowsList} class is useful to create a {@code "Binance"}'s record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.1.2
 */
@Structure
public abstract class BinanceItem {

    /**
     * {@code hItem} {@code "JSON"} helper
     */
    protected final JsonHelper hItem;

    /**
     * Constructor to init {@link BinanceItem} object
     *
     * @param jItem: {@code Binance}'s item details as {@link JSONObject}
     */
    public BinanceItem(JSONObject jItem) {
        if (jItem != null)
            hItem = new JsonHelper(jItem);
        else
            hItem = null;
    }

    /**
     * Method to create a list of {@link String}
     *
     * @param jList: obtained from Binance's response
     * @return strings as {@link ArrayList} of {@link String}
     */
    @Returner
    protected ArrayList<String> returnStringsList(JSONArray jList) {
        ArrayList<String> strings = new ArrayList<>();
        if (jList != null)
            for (int j = 0; j < jList.length(); j++)
                strings.add(jList.getString(j));
        return strings;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
