package com.smarthomies.realtimetalk.views.activities;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.databinding.ObservableField;
import java.util.List;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.databinding.ActivitySearchBinding;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.viewmodels.SearchViewModel;
import com.smarthomies.realtimetalk.views.adapters.UsersAdapter;
import com.smarthomies.realtimetalk.views.fragments.SearchViewModelHolder;

import java.util.ArrayList;

public class SearchActivity extends RTTActivity {

    private SearchViewModelHolder viewModelHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add retained to fragment manager
        if(getSupportFragmentManager().findFragmentByTag(SearchViewModelHolder.class.getName()) == null) {
            viewModelHolder = new SearchViewModelHolder();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(viewModelHolder, SearchViewModelHolder.class.getName())
                    .commit();
        }
        else {
            viewModelHolder = (SearchViewModelHolder)getSupportFragmentManager().findFragmentByTag(SearchViewModelHolder.class.getName());
        }

        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setViewModel(viewModelHolder.getSearchViewModel());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvSearchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchViewModel searchViewModel = viewModelHolder.getSearchViewModel();
        searchViewModel.getUsers().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                List<User> users = ((ObservableField<List<User>>)observable).get();
                UsersAdapter usersAdapter = new UsersAdapter(UsersAdapter.ViewType.SEARCH);
                usersAdapter.setUsers(users);
                recyclerView.setAdapter(usersAdapter);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getViewModelHolder().getSearchViewModel().getSearch().set(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getViewModelHolder().getSearchViewModel().getSearch().set(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rvSearchList) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public SearchViewModelHolder getViewModelHolder() {
        return viewModelHolder;
    }
}
