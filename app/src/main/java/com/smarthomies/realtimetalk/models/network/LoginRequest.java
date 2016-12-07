package com.smarthomies.realtimetalk.models.network;

/**
 * Created by ensar on 31/10/16.
 */
public class LoginRequest {
    public static final String TAG = LoginRequest.class.getSimpleName();

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
