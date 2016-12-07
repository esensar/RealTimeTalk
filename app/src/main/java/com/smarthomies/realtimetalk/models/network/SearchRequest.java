package com.smarthomies.realtimetalk.models.network;

/**
 * Created by ensar on 06/12/16.
 */
public class SearchRequest {
    public static final String TAG = SearchRequest.class.getSimpleName();

    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
