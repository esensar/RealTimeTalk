package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 01/11/16.
 */
public class ResourceForbiddenException extends APIException {
    public static final String TAG = ResourceForbiddenException.class.getSimpleName();

    public ResourceForbiddenException() {
    }

    public ResourceForbiddenException(Throwable cause) {
        super(cause);
    }
}
