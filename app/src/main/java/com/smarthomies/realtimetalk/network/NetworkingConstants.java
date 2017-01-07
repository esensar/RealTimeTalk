package com.smarthomies.realtimetalk.network;

/**
 * Created by ensar on 31/10/16.
 */
public class NetworkingConstants {
    public static final String TAG = NetworkingConstants.class.getSimpleName();

    public static final String API_BASE_URL = "https://realtimetalk.herokuapp.com/rest/";

    public static final String API_LOGIN_ENDPOINT = "prijava";
    public static final String API_LOGOUT_ENDPOINT = "odjava";
    public static final String API_REGISTRATION_ENDPOINT = "dodaj";
    public static final String API_CONTACTS_ENDPOINT = "contacts";
    public static final String API_PROFILE_ENDPOINT = "profile";
    public static final String API_PASSWORD_UPDATE_ENDPOINT = API_PROFILE_ENDPOINT + "/password";
    public static final String API_SEARCH_ENDPOINT = "search";

    public static final String AUTHORIZATION_HEADER = "Authorization";
}
