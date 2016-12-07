package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 01/11/16.
 */
public class RemoteResourceNotFoundException extends APIException {
    public static final String TAG = RemoteResourceNotFoundException.class.getSimpleName();

    public RemoteResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public RemoteResourceNotFoundException() {
    }
}
