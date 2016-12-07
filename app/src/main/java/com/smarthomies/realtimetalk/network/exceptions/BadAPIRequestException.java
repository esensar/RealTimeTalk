package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 01/11/16.
 */
public class BadAPIRequestException extends APIException {
    public static final String TAG = BadAPIRequestException.class.getSimpleName();

    public BadAPIRequestException() {
    }

    public BadAPIRequestException(Throwable cause) {
        super(cause);
    }
}
