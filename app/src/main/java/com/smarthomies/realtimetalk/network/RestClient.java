package com.smarthomies.realtimetalk.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ensar on 31/10/16.
 */
public class RestClient {
    public static final String TAG = RestClient.class.getSimpleName();

    private static RestClient instance;

    private Retrofit retrofit;
    private AuthenticationAPI authenticationAPI;
    private ContactsAPI contactsAPI;

    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        retrofit = new Retrofit.Builder()
                .baseUrl(NetworkingConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static RestClient getInstance() {
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public AuthenticationAPI getAuthenticationAPI() {
        if(authenticationAPI == null) {
            authenticationAPI = retrofit.create(AuthenticationAPI.class);
        }
        return authenticationAPI;
    }

    public ContactsAPI getContactsAPI() {
        if(contactsAPI == null) {
            contactsAPI = retrofit.create(ContactsAPI.class);
        }
        return contactsAPI;
    }
}
