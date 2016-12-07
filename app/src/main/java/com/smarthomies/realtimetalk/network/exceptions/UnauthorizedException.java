package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 14/11/16.
 */
public class UnauthorizedException extends APIException {
    public static final String TAG = UnauthorizedException.class.getSimpleName();

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException() {
    }
}
