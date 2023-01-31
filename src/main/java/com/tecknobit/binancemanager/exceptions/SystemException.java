package com.tecknobit.binancemanager.exceptions;

/**
 * The {@code SystemException} class is useful to catch if {@code "Binance"} API service is working or not,
 * it throws an exception if service is not working.
 **/
public class SystemException extends Exception {

    /**
     * Constructor to init {@link SystemException} object  <br>
     * No-any params required
     **/
    public SystemException() {
        super("Service unavailable because Binance system is in maintenance");
    }

}
