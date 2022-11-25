package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code OpenMarginOrders} class is useful to format a {@code "Binance"}'s margin list of an open orders on a symbol
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
 * Margin Account Cancel all Open Orders on a Symbol (TRADE)</a>
 **/
public class OpenMarginOrders {

    /**
     * {@code marginOrdersDetails} is instance that memorizes details margin orders data list
     **/
    private ArrayList<MarginOrderDetails> marginOrdersDetails;

    /**
     * {@code composedMarginOrderDetails} is instance that memorizes composed margin orders data list
     **/
    private ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails;

    /**
     * Constructor to init {@link OpenMarginOrders} object
     *
     * @param marginOrderDetails:         margin orders data list
     * @param composedMarginOrderDetails: composed margin orders data list
     **/
    public OpenMarginOrders(ArrayList<MarginOrderDetails> marginOrderDetails,
                            ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails) {
        this.marginOrdersDetails = marginOrderDetails;
        this.composedMarginOrderDetails = composedMarginOrderDetails;
    }

    /**
     * Constructor to init {@link OpenMarginOrders} object
     *
     * @param jOpenMarginOrders: open margin orders details as {@link JSONObject}
     **/
    public OpenMarginOrders(JSONArray jOpenMarginOrders) {
        marginOrdersDetails = new ArrayList<>();
        composedMarginOrderDetails = new ArrayList<>();
        for (int j = 0; j < jOpenMarginOrders.length(); j++) {
            JSONObject openMarginOrder = jOpenMarginOrders.getJSONObject(j);
            if (openMarginOrder.has("contingencyType"))
                composedMarginOrderDetails.add(new ComposedMarginOrderDetails(openMarginOrder));
            else
                marginOrdersDetails.add(new MarginOrderDetails(openMarginOrder));
        }
    }

    /**
     * Method to get {@link #marginOrdersDetails} instance <br>
     * Any params required
     *
     * @return {@link #marginOrdersDetails} instance as {@link ArrayList} of {@link MarginOrderDetails}
     **/
    public ArrayList<MarginOrderDetails> getCancelMarginOrdersList() {
        return marginOrdersDetails;
    }

    /**
     * Method to set {@link #marginOrdersDetails} instance <br>
     *
     * @param marginOrdersDetails: list of {@link MarginOrderDetails} to set
     **/
    public void setCancelMarginOrdersList(ArrayList<MarginOrderDetails> marginOrdersDetails) {
        this.marginOrdersDetails = marginOrdersDetails;
    }

    /**
     * Method to add a margin orders details to {@link #marginOrdersDetails}
     *
     * @param marginOrderDetails: margin orders details to add
     **/
    public void insertCanceledMarginOrder(MarginOrderDetails marginOrderDetails) {
        if (!marginOrdersDetails.contains(marginOrderDetails))
            marginOrdersDetails.add(marginOrderDetails);
    }

    /**
     * Method to remove a margin orders details from {@link #marginOrdersDetails}
     *
     * @param marginOrderDetails: margin orders details to remove
     * @return result of operation as boolean
     **/
    public boolean removeCanceledMarginOrder(MarginOrderDetails marginOrderDetails) {
        return marginOrdersDetails.remove(marginOrderDetails);
    }

    /**
     * Method to get a margin orders details from {@link #marginOrdersDetails} list
     *
     * @param index: index to fetch the margin orders details
     * @return margin orders details as {@link MarginOrderDetails}
     **/
    public MarginOrderDetails getCanceledMarginOrder(int index) {
        return marginOrdersDetails.get(index);
    }

    /**
     * Method to get {@link #composedMarginOrderDetails} instance <br>
     * Any params required
     *
     * @return {@link #composedMarginOrderDetails} instance as {@link ArrayList} of {@link ComposedMarginOrderDetails}
     **/
    public ArrayList<ComposedMarginOrderDetails> getComposedMarginOrderDetails() {
        return composedMarginOrderDetails;
    }

    /**
     * Method to set {@link #composedMarginOrderDetails} instance <br>
     *
     * @param composedMarginOrderDetails: list of {@link ComposedMarginOrderDetails} to set
     **/
    public void setComposedMarginOrderDetails(ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails) {
        this.composedMarginOrderDetails = composedMarginOrderDetails;
    }

    /**
     * Method to add a composed order to {@link #composedMarginOrderDetails}
     *
     * @param composedOrder: composed order to add
     **/
    public void insertComposedMarginOrder(ComposedMarginOrderDetails composedOrder) {
        if (!composedMarginOrderDetails.contains(composedOrder))
            composedMarginOrderDetails.add(composedOrder);
    }

    /**
     * Method to remove a composed order from {@link #composedMarginOrderDetails}
     *
     * @param composedOrder: composed order to remove
     * @return result of operation as boolean
     **/
    public boolean removeComposedMarginOrder(ComposedMarginOrderDetails composedOrder) {
        return composedMarginOrderDetails.remove(composedOrder);
    }

    /**
     * Method to get a composed order from {@link #composedMarginOrderDetails} list
     *
     * @param index: index to fetch the composed order
     * @return composed order as {@link ComposedMarginOrderDetails}
     **/
    public ComposedMarginOrderDetails getComposedMarginOrderDetails(int index) {
        return composedMarginOrderDetails.get(index);
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
