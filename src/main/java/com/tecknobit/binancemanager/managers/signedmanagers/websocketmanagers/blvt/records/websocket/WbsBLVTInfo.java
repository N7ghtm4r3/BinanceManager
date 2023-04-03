package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.websocket;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.ArrayList;

public class WbsBLVTInfo extends BinanceWebsocketResponse {

    private final String BLVTName;
    private final double tokenIssued;
    private final ArrayList<WbsBasket> baskets;
    private final double BLVTNav;
    private final double realLeverage;
    private final double targetLeverage;
    private final double fundingRatio;

    public WbsBLVTInfo(EventType eventType, long eventTime, String BLVTName, double tokenIssued,
                       ArrayList<WbsBasket> baskets, double BLVTNav, double realLeverage, double targetLeverage,
                       double fundingRatio) {
        super(eventType, eventTime);
        this.BLVTName = BLVTName;
        this.tokenIssued = tokenIssued;
        this.baskets = baskets;
        this.BLVTNav = BLVTNav;
        this.realLeverage = realLeverage;
        this.targetLeverage = targetLeverage;
        this.fundingRatio = fundingRatio;
    }

    public WbsBLVTInfo(JSONObject jWbsBLVTInfo) {
        super(jWbsBLVTInfo);
        BLVTName = hItem.getString("s");
        tokenIssued = hItem.getDouble("m", 0);
        baskets = new ArrayList<>();
        for (Object basket : hItem.fetchList("b"))
            baskets.add(new WbsBasket((JSONObject) basket));
        BLVTNav = hItem.getDouble("n", 0);
        realLeverage = hItem.getDouble("l", 0);
        targetLeverage = hItem.getDouble("t", 0);
        fundingRatio = hItem.getDouble("f", 0);
    }

    public String getBLVTName() {
        return BLVTName;
    }

    public double getTokenIssued() {
        return tokenIssued;
    }

    public ArrayList<WbsBasket> getBaskets() {
        return baskets;
    }

    public double getBLVTNav() {
        return BLVTNav;
    }

    public double getRealLeverage() {
        return realLeverage;
    }

    public double getTargetLeverage() {
        return targetLeverage;
    }

    public double getFundingRatio() {
        return fundingRatio;
    }

    public static class WbsBasket extends BinanceItem {

        private final String futuresSymbol;
        private final double position;

        public WbsBasket(String futuresSymbol, double position) {
            super(null);
            this.futuresSymbol = futuresSymbol;
            this.position = position;
        }

        public WbsBasket(JSONObject jWbsBasket) {
            super(jWbsBasket);
            futuresSymbol = hItem.getString("s");
            position = hItem.getDouble("n", 0);
        }

        public String getFuturesSymbol() {
            return futuresSymbol;
        }

        public double getPosition() {
            return position;
        }

    }

}
