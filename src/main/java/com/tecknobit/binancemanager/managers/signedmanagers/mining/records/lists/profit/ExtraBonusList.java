package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExtraBonusList extends MiningResponse<ExtraBonusList.ExtraBonus> {

    public ExtraBonusList(ExtraBonus data) {
        super(data);
    }

    public ExtraBonusList(JSONObject jExtraBonusList) {
        super(jExtraBonusList);
        data = new ExtraBonus(hItem.getJSONObject("data"));
    }

    public static class ExtraBonus extends DataListItem {

        private final ArrayList<Profit> otherProfits;

        public ExtraBonus(int totalNum, int pageSize, ArrayList<Profit> otherProfits) {
            super(totalNum, pageSize);
            this.otherProfits = otherProfits;
        }

        public ExtraBonus(JSONObject jExtraBonus) {
            super(jExtraBonus);
            otherProfits = new ArrayList<>();
            for (Object profit : hItem.fetchList("otherProfits"))
                otherProfits.add(new Profit((JSONObject) profit));
        }

        public ArrayList<Profit> getOtherProfits() {
            return otherProfits;
        }

    }

}
