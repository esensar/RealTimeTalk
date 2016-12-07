package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 01/11/16.
 */
public class NetworkException extends APIException {
    public static final String TAG = NetworkException.class.getSimpleName();

    public NetworkException() {
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }
}
