package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.ExtraBonusList.ExtraBonus;

/**
 * The {@code ExtraBonusList} class is useful to create an extra bonus list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
 * Extra Bonus List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 **/
public class ExtraBonusList extends MiningResponse<ExtraBonus> {

    /**
     * Constructor to init {@link ExtraBonusList} object
     *
     * @param data: extra bonus list
     **/
    public ExtraBonusList(ExtraBonus data) {
        super(data);
    }

    /**
     * Constructor to init {@link ExtraBonusList} object
     *
     * @param jExtraBonusList: extra bonus list details as {@link JSONObject}
     **/
    public ExtraBonusList(JSONObject jExtraBonusList) {
        super(jExtraBonusList);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new ExtraBonus(jData);
        else
            data = null;
    }

    /**
     * The {@code ExtraBonus} class is useful to create an extra bonus
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see DataListItem
     **/
    public static class ExtraBonus extends DataListItem {

        /**
         * {@code otherProfits} other profits list
         **/
        private final ArrayList<Profit> otherProfits;

        /**
         * Constructor to init {@link ExtraBonus} object
         *
         * @param totalNum: total num of the other profits list
         * @param pageSize: page size of the other profits list
         * @param otherProfits: page size of the other profits list
         **/
        public ExtraBonus(int totalNum, int pageSize, ArrayList<Profit> otherProfits) {
            super(totalNum, pageSize);
            this.otherProfits = otherProfits;
        }

        /**
         * Constructor to init {@link ExtraBonus} object
         *
         * @param jExtraBonus: extra bonus details as {@link JSONObject}
         **/
        public ExtraBonus(JSONObject jExtraBonus) {
            super(jExtraBonus);
            otherProfits = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("otherProfits");
            if (jList != null)
                for (JSONObject profit : jList)
                    otherProfits.add(new Profit(profit));
        }

        /**
         * Method to get {@link #otherProfits} instance <br>
         * No-any params required
         *
         * @return {@link #otherProfits} instance as {@link ArrayList} of {@link Profit}
         **/
        public ArrayList<Profit> getOtherProfits() {
            return otherProfits;
        }

    }

}
