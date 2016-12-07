package com.smarthomies.realtimetalk.views.fragments;

import com.smarthomies.realtimetalk.viewmodels.SearchViewModel;

/**
 * Created by ensar on 15/11/16.
 */
public class SearchViewModelHolder extends ViewModelHolder {
    public static final String TAG = SearchViewModelHolder.class.getSimpleName();

    private SearchViewModel searchViewModel = new SearchViewModel();

    public SearchViewModel getSearchViewModel() {
        return searchViewModel;
    }

    public void setSearchViewModel(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchViewModel.clear();
    }
}
