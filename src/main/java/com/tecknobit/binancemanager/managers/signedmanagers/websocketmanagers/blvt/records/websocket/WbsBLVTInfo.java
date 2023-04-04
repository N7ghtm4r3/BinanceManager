package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.websocket;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.websocketstream.BinanceWebsocketResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code WbsBLVTInfo} class is useful to format a Websocket BLVT info
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#websocket-blvt-info-streams">
 * Websocket BLVT Info Streams</a>
 * @see BinanceItem
 * @see BinanceWebsocketResponse
 **/
public class WbsBLVTInfo extends BinanceWebsocketResponse {

    /**
     * {@code BLVTName} BLVT name of the BLVT info
     **/
    private final String BLVTName;

    /**
     * {@code tokenIssued} token issued of the BLVT info
     **/
    private final double tokenIssued;

    /**
     * {@code baskets} baskets of the BLVT info
     **/
    private final ArrayList<WbsBasket> baskets;

    /**
     * {@code BLVTNav} BLVT nav of the BLVT info
     **/
    private final double BLVTNav;

    /**
     * {@code realLeverage} real leverage of the BLVT info
     **/
    private final double realLeverage;

    /**
     * {@code targetLeverage} target leverage of the BLVT info
     **/
    private final double targetLeverage;

    /**
     * {@code fundingRatio} funding ratio of the BLVT info
     **/
    private final double fundingRatio;

    /**
     * Constructor to init {@link WbsBLVTInfo} object
     *
     * @param eventType: type of the event
     * @param eventTime: when the event occurred
     * @param BLVTName: BLVT name of the BLVT info
     * @param tokenIssued: token issued of the BLVT info
     * @param baskets: baskets of the BLVT info
     * @param BLVTNav: BLVT nav of the BLVT info
     * @param realLeverage: real leverage of the BLVT info
     * @param targetLeverage: target leverage of the BLVT info
     * @param fundingRatio: funding ratio of the BLVT info
     **/
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

    /**
     * Constructor to init {@link WbsBLVTInfo} object
     *
     * @param jWbsBLVTInfo: websocket BLVT info details as {@link JSONObject}
     **/
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

    /**
     * Method to get {@link #BLVTName} instance <br>
     * No-any params required
     *
     * @return {@link #BLVTName} instance as {@link String}
     **/
    public String getBLVTName() {
        return BLVTName;
    }

    /**
     * Method to get {@link #tokenIssued} instance <br>
     * No-any params required
     *
     * @return {@link #tokenIssued} instance as double
     **/
    public double getTokenIssued() {
        return tokenIssued;
    }

    /**
     * Method to get {@link #tokenIssued} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #tokenIssued} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTokenIssued(int decimals) {
        return roundValue(tokenIssued, decimals);
    }

    /**
     * Method to get {@link #baskets} instance <br>
     * No-any params required
     *
     * @return {@link #baskets} instance as {@link ArrayList} of {@link WbsBasket}
     **/
    public ArrayList<WbsBasket> getBaskets() {
        return baskets;
    }

    /**
     * Method to get {@link #BLVTNav} instance <br>
     * No-any params required
     *
     * @return {@link #BLVTNav} instance as double
     **/
    public double getBLVTNav() {
        return BLVTNav;
    }

    /**
     * Method to get {@link #BLVTNav} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #BLVTNav} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBLVTNav(int decimals) {
        return roundValue(BLVTNav, decimals);
    }

    /**
     * Method to get {@link #realLeverage} instance <br>
     * No-any params required
     *
     * @return {@link #realLeverage} instance as double
     **/
    public double getRealLeverage() {
        return realLeverage;
    }

    /**
     * Method to get {@link #realLeverage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #realLeverage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRealLeverage(int decimals) {
        return roundValue(realLeverage, decimals);
    }

    /**
     * Method to get {@link #targetLeverage} instance <br>
     * No-any params required
     *
     * @return {@link #targetLeverage} instance as double
     **/
    public double getTargetLeverage() {
        return targetLeverage;
    }

    /**
     * Method to get {@link #targetLeverage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #targetLeverage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTargetLeverage(int decimals) {
        return roundValue(targetLeverage, decimals);
    }

    /**
     * Method to get {@link #fundingRatio} instance <br>
     * No-any params required
     *
     * @return {@link #fundingRatio} instance as double
     **/
    public double getFundingRatio() {
        return fundingRatio;
    }

    /**
     * Method to get {@link #fundingRatio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fundingRatio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFundingRatio(int decimals) {
        return roundValue(fundingRatio, decimals);
    }

    /**
     * The {@code WbsBasket} class is useful to format a websocket basket for a BLVT info
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class WbsBasket extends BinanceItem {

        /**
         * {@code futuresSymbol} futures symbol of the websocket basket
         **/
        private final String futuresSymbol;

        /**
         * {@code position} of the websocket basket
         **/
        private final double position;

        /**
         * Constructor to init {@link WbsBasket} object
         *
         * @param futuresSymbol: futures symbol of the websocket basket
         * @param position: position of the websocket basket
         **/
        public WbsBasket(String futuresSymbol, double position) {
            super(null);
            this.futuresSymbol = futuresSymbol;
            this.position = position;
        }

        /**
         * Constructor to init {@link WbsBasket} object
         *
         * @param jWbsBasket: websocket basket details as {@link JSONObject}
         **/
        public WbsBasket(JSONObject jWbsBasket) {
            super(jWbsBasket);
            futuresSymbol = hItem.getString("s");
            position = hItem.getDouble("n", 0);
        }

        /**
         * Method to get {@link #futuresSymbol} instance <br>
         * No-any params required
         *
         * @return {@link #futuresSymbol} instance as {@link String}
         **/
        public String getFuturesSymbol() {
            return futuresSymbol;
        }

        /**
         * Method to get {@link #position} instance <br>
         * No-any params required
         *
         * @return {@link #position} instance as double
         **/
        public double getPosition() {
            return position;
        }

        /**
         * Method to get {@link #position} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #position} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPosition(int decimals) {
            return roundValue(position, decimals);
        }

    }

}
