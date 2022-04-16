package com.tecknobit.binancemanager.Helpers.Request;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 *  The {@code RequestManager} class is useful to manage all request to fecth data from Binance API
 * **/

public class RequestManager {

    private HttpURLConnection httpURLConnection;
    private String errorResponse;
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String DELETE_METHOD = "DELETE";

    /** Method to start connection by an endpoint
     * @param #url: url used to make HTTP request
     * @param #method: method used to make HTTP request
     * any return
     * **/
    public void startConnection(String url, String method) throws IOException {
        setConnection(url,method);
        httpURLConnection.connect();
    }

    /** Method to start connection by an endpoint with apiKey
     * @param #url: url used to make HTTP request
     * @param #method: method used to make HTTP request
     * @param #apiKey: apiKey of Binance's account passed as body parameter of the HTTP request
     * any return
     * **/
    public void startConnection(String url, String method, String apiKey) throws IOException {
        setConnection(url,method);
        httpURLConnection.setRequestProperty("X-MBX-APIKEY",apiKey);
        httpURLConnection.connect();
    }

    /** Method to set up connection by an endpoint
     * @param #url: url used to make HTTP request
     * @param #method: method used to make HTTP request
     * any return
     * **/
    private void setConnection(String url, String method) throws IOException{
        httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
    }

    /** Method to get response of an HTTP request
     * any params required
     * @return response of the HTTP request, in case of error return error stream of the HTTP request
     * **/
    public String getResponse() throws IOException {
        BufferedReader bufferedReader;
        String response;
        boolean isInError = false;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        } catch (IOException e) {
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            isInError = true;
        }
        response = readStream(bufferedReader);
        if(isInError)
            errorResponse = response;
        return response;
    }

    /** Method get params signature of an HTTP request
     * @param #key: secreKey of Binance's account used to signature request
     * @param #data: data to sing
     * @return signature es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    public String getSignature(String key, String data) throws Exception {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        sha256.init(new SecretKeySpec(key.getBytes(UTF_8), "HmacSHA256"));
        return encodeHexString(sha256.doFinal(data.replace("?","").getBytes(UTF_8)));
    }

    /** Method to get formatted extraParams of an HTTP request
     * @param #params: mandatory params of the request
     * @param #extraParams: extra params of the request
     * @return formatted query params for the HTTP request
     * **/
    public String assembleExtraParams(String params, HashMap<String, Object> extraParams) {
        ArrayList<String> keys = new ArrayList<>(extraParams.keySet());
        StringBuilder paramsBuilder = new StringBuilder(params);
        for (String key : keys)
            paramsBuilder.append("&").append(key).append("=").append(extraParams.get(key));
        return paramsBuilder.toString();
    }

    public String assembleSymbolsParams(ArrayList<String> symbols){
        StringBuilder params = new StringBuilder();
        for (String symbol : symbols)
            params.append("%22").append(symbol).append("%22,");
        params.replace(params.length()-1,params.length(),"");
        return params.toString();
    }

    /** Method to get error response of an HTTP request
     * any params required
     * @return errorResponse
     * **/
    public String getErrorResponse() {
        if(errorResponse == null)
            return "No HTTP Binance request error, check your code";
        return errorResponse;
    }

    /** Method to format stream of a response of an HTTP request
     * @param #bufferedReader: object that contain actual stream response
     * @return formatted response of an HTTP request as String
     * **/
    private String readStream(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);
        return stringBuilder.toString();
    }

}
