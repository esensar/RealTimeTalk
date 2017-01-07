package com.smarthomies.realtimetalk.models.network;

import com.smarthomies.realtimetalk.models.db.User;

import java.util.List;

/**
 * Created by ensar on 06/12/16.
 */
public class UserResponse {
    public static final String TAG = UserResponse.class.getSimpleName();

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
