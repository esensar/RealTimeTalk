package com.smarthomies.realtimetalk.models.network;

import com.smarthomies.realtimetalk.models.db.User;

import java.util.List;

/**
 * Created by ensar on 06/12/16.
 */
public class UsersResponse {
    public static final String TAG = UsersResponse.class.getSimpleName();

    private List<User> data;

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
