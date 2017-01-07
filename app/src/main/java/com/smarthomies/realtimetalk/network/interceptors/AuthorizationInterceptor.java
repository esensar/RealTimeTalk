package com.smarthomies.realtimetalk.network.interceptors;

import com.smarthomies.realtimetalk.network.NetworkingConstants;
import com.smarthomies.realtimetalk.utils.RTTAppHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ensar on 11/12/16.
 */
public class AuthorizationInterceptor implements Interceptor {
    public static final String TAG = AuthorizationInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .addHeader(NetworkingConstants.AUTHORIZATION_HEADER, RTTAppHelper.getInstance().getToken()).build();
        return chain.proceed(request);
    }
}
