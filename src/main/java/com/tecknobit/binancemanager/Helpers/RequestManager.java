package com.tecknobit.binancemanager.Helpers;

import com.tecknobit.apimanager.Manager.APIRequest;

import java.util.ArrayList;

/**
 *  The {@code RequestManager} class is useful to manage all request to fetch data from Binance API
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class RequestManager extends APIRequest {

    /** Method to get formatted symbols string for the HTTP request
     * @param symbols: symbols to concatenate
     * @return formatted symbols string for the HTTP request
     * **/
    public String assembleSymbolsParams(ArrayList<String> symbols){
        StringBuilder params = new StringBuilder();
        for (String symbol : symbols)
            params.append("%22").append(symbol).append("%22,");
        params.replace(params.length()-1,params.length(),"");
        return params.toString();
    }

}
