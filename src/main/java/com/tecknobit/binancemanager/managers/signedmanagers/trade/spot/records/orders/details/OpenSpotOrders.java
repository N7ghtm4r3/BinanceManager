package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details.OpenMarginOrders;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code OpenSpotOrders} class is useful to format a {@code "Binance"}'s open spot orders list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
 * Cancel all Open Orders on a Symbol (TRADE)</a>
 **/
public class OpenSpotOrders {

    /**
     * {@code spotOrdersDetails} is instance that memorizes spot orders data list
     **/
    private ArrayList<SpotOrderDetails> spotOrdersDetails;

    /**
     * {@code composedSpotOrderDetails} is instance that memorizes composed spot orders data list
     **/
    private ArrayList<ComposedSpotOrderDetails> composedSpotOrderDetails;

    /**
     * Constructor to init {@link OpenMarginOrders} object
     *
     * @param singleOrders:         spot orders data list
     * @param cancelOrderComposeds: composed spot orders data list
     **/
    public OpenSpotOrders(ArrayList<SpotOrderDetails> singleOrders,
                          ArrayList<ComposedSpotOrderDetails> cancelOrderComposeds) {
        this.spotOrdersDetails = singleOrders;
        this.composedSpotOrderDetails = cancelOrderComposeds;
    }

    /**
     * Constructor to init {@link OpenSpotOrders} object
     *
     * @param jOpenSpotOrders: open margin orders details as {@link JSONArray}
     **/
    public OpenSpotOrders(JSONArray jOpenSpotOrders) {
        spotOrdersDetails = new ArrayList<>();
        composedSpotOrderDetails = new ArrayList<>();
        for (int j = 0; j < jOpenSpotOrders.length(); j++) {
            JSONObject openSpotOrder = jOpenSpotOrders.getJSONObject(j);
            if (openSpotOrder.has("contingencyType"))
                composedSpotOrderDetails.add(new ComposedSpotOrderDetails(openSpotOrder));
            else
                spotOrdersDetails.add(new SpotOrderDetails(openSpotOrder));
        }
    }

    /**
     * Method to get {@link #spotOrdersDetails} instance <br>
     * Any params required
     *
     * @return {@link #spotOrdersDetails} instance as {@link ArrayList} of {@link SpotOrderDetails}
     **/
    public ArrayList<SpotOrderDetails> getDetailSpotOrdersList() {
        return spotOrdersDetails;
    }

    /**
     * Method to set {@link #spotOrdersDetails} instance <br>
     *
     * @param spotOrdersDetails: list of {@link SpotOrderDetails} to set
     **/
    public void setDetailSpotOrdersList(ArrayList<SpotOrderDetails> spotOrdersDetails) {
        this.spotOrdersDetails = spotOrdersDetails;
    }

    /**
     * Method to add a spot order details  to {@link #spotOrdersDetails}
     *
     * @param spotOrderDetails: spot order details to add
     **/
    public void insertDetailSpotOrder(SpotOrderDetails spotOrderDetails) {
        if (!spotOrdersDetails.contains(spotOrderDetails))
            spotOrdersDetails.add(spotOrderDetails);
    }

    /**
     * Method to remove a spot order details  from {@link #spotOrdersDetails}
     *
     * @param spotOrderDetails: spot order details  to remove
     * @return result of operation as boolean
     **/
    public boolean removeSpotOrderDetails(SpotOrderDetails spotOrderDetails) {
        return spotOrdersDetails.remove(spotOrderDetails);
    }

    /**
     * Method to get a spot order details from {@link #spotOrdersDetails} list
     *
     * @param index: index to fetch the composed spot order details
     * @return spot order details as {@link SpotOrderDetails}
     **/
    public SpotOrderDetails getDetailsSpotOrder(int index) {
        return spotOrdersDetails.get(index);
    }

    /**
     * Method to get {@link #composedSpotOrderDetails} instance <br>
     * Any params required
     *
     * @return {@link #composedSpotOrderDetails} instance as {@link ArrayList} of {@link ComposedSpotOrderDetails}
     **/
    public ArrayList<ComposedSpotOrderDetails> getComposedSpotOrderDetails() {
        return composedSpotOrderDetails;
    }

    /**
     * Method to set {@link #composedSpotOrderDetails} instance <br>
     *
     * @param composedSpotOrderDetails: list of {@link SpotOrderDetails} to set
     **/
    public void setComposedSpotOrderDetails(ArrayList<ComposedSpotOrderDetails> composedSpotOrderDetails) {
        this.composedSpotOrderDetails = composedSpotOrderDetails;
    }

    /**
     * Method to add a composed spot order details  to {@link #composedSpotOrderDetails}
     *
     * @param composedSpotOrderDetails: composed spot order details to add
     **/
    public void insertComposedSpotOrderDetail(ComposedSpotOrderDetails composedSpotOrderDetails) {
        if (!this.composedSpotOrderDetails.contains(composedSpotOrderDetails))
            this.composedSpotOrderDetails.add(composedSpotOrderDetails);
    }

    /**
     * Method to remove a composed spot order details  from {@link #composedSpotOrderDetails}
     *
     * @param composedSpotOrderDetails: composed spot order details  to remove
     * @return result of operation as boolean
     **/
    public boolean removeComposedSpotOrderDetail(ComposedSpotOrderDetails composedSpotOrderDetails) {
        return this.composedSpotOrderDetails.remove(composedSpotOrderDetails);
    }

    /**
     * Method to get a composed spot order details from {@link #composedSpotOrderDetails} list
     *
     * @param index: index to fetch the composed spot order details
     * @return composed spot order details as {@link ComposedSpotOrderDetails}
     **/
    public ComposedSpotOrderDetails getComposedSpotOrderDetails(int index) {
        return composedSpotOrderDetails.get(index);
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
