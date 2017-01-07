package com.smarthomies.realtimetalk.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ensar on 31/10/16.
 */
public class PasswordChangeRequest {
    public static final String TAG = PasswordChangeRequest.class.getSimpleName();

    @SerializedName("current_password")
    private String currentPassword;
    @SerializedName("new_password")
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
