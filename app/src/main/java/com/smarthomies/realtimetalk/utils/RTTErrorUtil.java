package com.smarthomies.realtimetalk.utils;

import android.util.Log;
import android.widget.Toast;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.network.exceptions.APIConflictException;
import com.smarthomies.realtimetalk.network.exceptions.BadAPIRequestException;
import com.smarthomies.realtimetalk.network.exceptions.NetworkException;
import com.smarthomies.realtimetalk.network.exceptions.RemoteResourceNotFoundException;
import com.smarthomies.realtimetalk.network.exceptions.ResourceForbiddenException;
import com.smarthomies.realtimetalk.network.exceptions.ServerErrorException;
import com.smarthomies.realtimetalk.network.exceptions.UnauthorizedException;

/**
 * Created by ensar on 14/11/16.
 */
public class RTTErrorUtil {
    public static final String TAG = RTTErrorUtil.class.getSimpleName();

    public static int getErrorString(Throwable e) {
        if (e instanceof BadAPIRequestException) {
            return R.string.error_bad_request;
        } else if (e instanceof RemoteResourceNotFoundException) {
            return R.string.error_user_not_found;
        } else if (e instanceof ResourceForbiddenException) {
            return R.string.error_unknown;
        } else if (e instanceof UnauthorizedException) {
            return R.string.error_user_bad_credentials;
        } else if (e instanceof APIConflictException) {
            return R.string.error_user_already_exists;
        } else if (e instanceof NetworkException) {
            return R.string.error_no_internet;
        } else if (e instanceof ServerErrorException) {
            return R.string.error_server;
        }
        return 0;
    }
}
