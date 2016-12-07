package com.smarthomies.realtimetalk.network;

import com.smarthomies.realtimetalk.network.exceptions.APIException;
import com.smarthomies.realtimetalk.network.exceptions.BadAPIRequestException;
import com.smarthomies.realtimetalk.network.exceptions.NetworkException;
import com.smarthomies.realtimetalk.network.exceptions.RemoteResourceNotFoundException;
import com.smarthomies.realtimetalk.network.exceptions.ResourceForbiddenException;
import com.smarthomies.realtimetalk.network.exceptions.UnauthorizedException;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by ensar on 31/10/16.
 */
public class APIErrorHandler {
    public static final String TAG = APIErrorHandler.class.getSimpleName();

    public static void handleGeneralAPIErrors(Throwable throwable)
            throws APIException {
        if(throwable instanceof HttpException) {
            HttpException httpException = ((HttpException)throwable);
            switch (httpException.response().code()) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    throw new BadAPIRequestException(throwable);
                case HttpURLConnection.HTTP_NOT_FOUND:
                    throw new RemoteResourceNotFoundException(throwable);
                case HttpURLConnection.HTTP_FORBIDDEN:
                    throw new ResourceForbiddenException(throwable);
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    throw new UnauthorizedException(throwable);
            }
        }
        if(throwable instanceof IOException) {
            throw new NetworkException(throwable);
        }
    }

    public static void handleLoginErrors(Throwable throwable)
            throws APIException {
        handleGeneralAPIErrors(throwable);
    }

    public static void handleRegistrationErrors(Throwable throwable)
            throws APIException {
        handleGeneralAPIErrors(throwable);
    }

    public static void handleSearchErrors(Throwable throwable)
            throws APIException {
        handleGeneralAPIErrors(throwable);
    }
}
