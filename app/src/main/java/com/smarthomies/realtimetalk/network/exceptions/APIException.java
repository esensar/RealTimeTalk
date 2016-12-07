package com.smarthomies.realtimetalk.network.exceptions;

import java.net.HttpURLConnection;

/**
 * Created by ensar on 01/11/16.
 */
public class APIException extends Exception {
    public static final String TAG = APIException.class.getSimpleName();

    public APIException() {
    }

    public APIException(Throwable cause) {
        super(cause);
    }
}
