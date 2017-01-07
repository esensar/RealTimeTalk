package com.smarthomies.realtimetalk.network.exceptions;

/**
 * Created by ensar on 01/11/16.
 */
public class ServerErrorException extends APIException {
    public static final String TAG = ServerErrorException.class.getSimpleName();

    public ServerErrorException() {
    }

    public ServerErrorException(Throwable cause) {
        super(cause);
    }
}
