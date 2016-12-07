package com.smarthomies.realtimetalk.utils;

import android.text.TextUtils;
import android.util.Patterns;

import com.smarthomies.realtimetalk.R;

import java.util.regex.Pattern;

/**
 * Created by ensar on 03/11/16.
 */
public class RTTUtil {
    public static final String TAG = RTTUtil.class.getSimpleName();

    private static final Pattern hasUppercase = Pattern.compile("\\p{javaUpperCase}");
    private static final Pattern hasLowercase = Pattern.compile("\\p{javaLowerCase}");
    private static final Pattern hasNumber = Pattern.compile("\\p{javaDigit}");
    private static final Pattern hasSpecialChar = Pattern.compile("\\p{javaLetterOrDigit}");

    public static final int PASSWORD_MINIMUM_LENGTH = 6;
    public static final int PASSWORD_MAXIMUM_LENGTH = 20;

    public static boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }

        if (password.length() < 6 || password.length() > 20) {
            return false;
        }

        boolean hasUppercase = RTTUtil.hasUppercase.matcher(password).matches();
        boolean hasLowercase = RTTUtil.hasLowercase.matcher(password).matches();
        boolean hasNumber = RTTUtil.hasNumber.matcher(password).matches();
        boolean hasSpecialChar = RTTUtil.hasSpecialChar.matcher(password).matches();

        if (!hasNumber || !(hasUppercase || hasLowercase)) {
            return false;
        }

        return true;
    }

    public static int getRequiredFieldError(String value) {
        if (TextUtils.isEmpty(value)) {
            return R.string.error_required;
        }

        return 0;
    }

    public static int getPasswordError(String password) {
        if (TextUtils.isEmpty(password)) {
            return R.string.error_required;
        }

        if (password.length() < 6 || password.length() > 20) {
            return R.string.error_password_short;
        }


        boolean hasUppercase = RTTUtil.hasUppercase.matcher(password).matches();
        boolean hasLowercase = RTTUtil.hasLowercase.matcher(password).matches();
        boolean hasNumber = RTTUtil.hasNumber.matcher(password).matches();
        boolean hasSpecialChar = RTTUtil.hasSpecialChar.matcher(password).matches();

//        if (!hasNumber || !(hasUppercase || hasLowercase)) {
//            return R.string.error_password_invalid;
//        }

        return 0;
    }

    public static int getPasswordConfirmationError(String password, String passwordConfirmation) {
        int confirmationError = getPasswordError(passwordConfirmation);

        if(confirmationError == 0 && !passwordConfirmation.equalsIgnoreCase(password)) {
            return R.string.error_password_confirmation;
        }

        return confirmationError;
    }

    public static boolean isEmailValid(String email) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        return emailPattern.matcher(email).matches();
    }



}
