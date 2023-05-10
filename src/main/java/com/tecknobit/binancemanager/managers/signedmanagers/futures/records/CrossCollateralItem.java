package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code CrossCollateralItem} class is useful to create a cross collateral item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-borrow-history-user_data">
 *             Cross-Collateral Borrow History (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-repayment-history-user_data">
 *             Cross-Collateral Repayment History (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-cross-collateral-ltv-history-user_data">
 *             Adjust Cross-Collateral LTV History (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-collateral-liquidation-history-user_data">
 *             Cross-Collateral Liquidation History (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
@Structure
public abstract class CrossCollateralItem extends BinanceItem {

    /**
     * {@code coin} of the cross collateral
     **/
    protected final String coin;

    /**
     * {@code collateralCoin} collateral coin of the cross collateral
     **/
    protected final String collateralCoin;

    /**
     * Constructor to init {@link CrossCollateralItem} object
     *
     * @param coin:           coin of the cross collateral
     * @param collateralCoin: collateral coin of the cross collateral
     **/
    public CrossCollateralItem(String coin, String collateralCoin) {
        super(null);
        this.coin = coin;
        this.collateralCoin = collateralCoin;
    }

    /**
     * Constructor to init {@link CrossCollateralItem} object
     *
     * @param jCrossCollateralItem: cross collateral item details as {@link JSONObject}
     **/
    public CrossCollateralItem(JSONObject jCrossCollateralItem) {
        super(jCrossCollateralItem);
        coin = hItem.getString("coin");
        collateralCoin = hItem.getString("collateralCoin");
    }

    /**
     * Method to get {@link #coin} instance <br>
     * No-any params required
     *
     * @return {@link #coin} instance as {@link String}
     **/
    public String getCoin() {
        return coin;
    }

    /**
     * Method to get {@link #collateralCoin} instance <br>
     * No-any params required
     *
     * @return {@link #collateralCoin} instance as {@link String}
     **/
    public String getCollateralCoin() {
        return collateralCoin;
    }

}
