package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 01/11/16.
 */
public class APIConflictException extends APIException {
    public static final String TAG = APIConflictException.class.getSimpleName();

    public APIConflictException() {
    }

    public APIConflictException(Throwable cause) {
        super(cause);
    }
}
